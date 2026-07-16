#!/usr/bin/env node

const fs = require("fs");
const path = require("path");

let admin;

const SCRIPT_DIR = __dirname;
const DEFAULT_SERVICE_ACCOUNT_PATH = path.join(SCRIPT_DIR, "serviceAccountKey.json");
const SERVICE_ACCOUNT_PATH = process.env.FIREBASE_SERVICE_ACCOUNT ||
  DEFAULT_SERVICE_ACCOUNT_PATH;

const FLAGS = new Set([
  "--dry-run",
  "--overwrite",
  "--publish-user-copy",
  "--help"
]);

function usage(exitCode = 0) {
  const text = `
Usage:
  node scripts/promoteTaskSubmission.js <submissionId> <remoteTaskId> [--dry-run] [--overwrite] [--publish-user-copy]

Examples:
  node scripts/promoteTaskSubmission.js local_128b3c48-9ea6-4a8a-8242-a5af709b0e28 admin_beginner_002 --dry-run
  node scripts/promoteTaskSubmission.js local_128b3c48-9ea6-4a8a-8242-a5af709b0e28 admin_beginner_002

Flags:
  --dry-run            Validate and print the document without writing.
  --overwrite          Replace remote_tasks/<remoteTaskId> if it already exists.
  --publish-user-copy  Also make the original submission public under "Korisnički zadaci".

Service account:
  Put the real key at scripts/serviceAccountKey.json or set FIREBASE_SERVICE_ACCOUNT.
`;
  console.log(text.trim());
  process.exit(exitCode);
}

function parseArgs(argv) {
  const positional = [];
  const flags = new Set();

  for (const arg of argv) {
    if (arg.startsWith("--")) {
      if (!FLAGS.has(arg)) {
        throw new Error(`Unknown flag: ${arg}`);
      }
      flags.add(arg);
    } else {
      positional.push(arg);
    }
  }

  if (flags.has("--help")) {
    usage(0);
  }

  if (positional.length !== 2) {
    usage(1);
  }

  return {
    submissionId: positional[0],
    remoteTaskId: positional[1],
    dryRun: flags.has("--dry-run"),
    overwrite: flags.has("--overwrite"),
    publishUserCopy: flags.has("--publish-user-copy")
  };
}

function loadServiceAccount(filePath) {
  if (!fs.existsSync(filePath)) {
    throw new Error(
      `Service account file not found: ${filePath}\n` +
      "Download it manually and save it as scripts/serviceAccountKey.json, " +
      "or set FIREBASE_SERVICE_ACCOUNT."
    );
  }

  const raw = fs.readFileSync(filePath, "utf8");
  const parsed = JSON.parse(raw);
  for (const key of ["project_id", "client_email", "private_key"]) {
    if (!parsed[key]) {
      throw new Error(`Service account file is missing required field: ${key}`);
    }
  }
  return parsed;
}

function initializeFirestore(serviceAccount) {
  admin = require("firebase-admin");
  admin.initializeApp({
    credential: admin.credential.cert(serviceAccount)
  });
  return admin.firestore();
}

function requireNonBlank(value, fieldName) {
  if (typeof value !== "string" || value.trim() === "") {
    throw new Error(`Submission is missing required field: ${fieldName}`);
  }
  return value.trim();
}

function resolvePayload(submission) {
  if (isPlainObject(submission.payload)) {
    return deepClone(submission.payload);
  }

  if (typeof submission.payloadJson === "string" && submission.payloadJson.trim()) {
    try {
      return JSON.parse(submission.payloadJson);
    } catch (error) {
      throw new Error("submission.payload is missing and payloadJson is not valid JSON.");
    }
  }

  throw new Error("Submission is missing required field: payload");
}

function validateSubmission(submission) {
  const title = requireNonBlank(submission.title, "title");
  const level = requireNonBlank(submission.level, "level");
  const taskType = requireNonBlank(submission.taskType, "taskType");
  const payload = resolvePayload(submission);

  if (!isPlainObject(payload)) {
    throw new Error("Submission payload must be an object.");
  }

  return {
    title,
    level,
    taskType,
    templateId: typeof submission.templateId === "string"
      ? submission.templateId.trim()
      : "",
    schemaVersion: Number.isInteger(submission.schemaVersion)
      ? submission.schemaVersion
      : Number.isInteger(payload.schemaVersion)
        ? payload.schemaVersion
        : 1,
    payload,
    payloadJson: typeof submission.payloadJson === "string"
      ? submission.payloadJson
      : null
  };
}

function buildAdminPayload(payload, normalized) {
  const adminPayload = deepClone(payload);

  adminPayload.schemaVersion = normalized.schemaVersion;
  adminPayload.createdByRole = "ADMIN";
  adminPayload.source = "ADMIN";
  adminPayload.publicationMode = "MAIN_TASK_LIST";
  adminPayload.level = normalized.level;
  adminPayload.taskType = normalized.taskType;
  if (normalized.templateId) {
    adminPayload.templateId = normalized.templateId;
  }
  delete adminPayload.reviewStatus;
  delete adminPayload.isPublic;

  if (isPlainObject(adminPayload.question)) {
    adminPayload.question.level = adminPayload.question.level || normalized.level;
    adminPayload.question.type = adminPayload.question.type || normalized.taskType;
    adminPayload.question.title = adminPayload.question.title || normalized.title;
  }

  return adminPayload;
}

function buildRemoteTaskDocument(submissionId, remoteTaskId, submission) {
  const normalized = validateSubmission(submission);
  const adminPayload = buildAdminPayload(normalized.payload, normalized);

  return removeUndefined({
    id: remoteTaskId,
    title: normalized.title,
    level: normalized.level,
    taskType: normalized.taskType,
    templateId: normalized.templateId || undefined,
    source: "ADMIN",
    status: "ACTIVE",
    publicationMode: "MAIN_TASK_LIST",
    createdByRole: "ADMIN",
    isPublic: true,
    schemaVersion: normalized.schemaVersion,
    payload: adminPayload,
    payloadJson: normalized.payloadJson ? JSON.stringify(adminPayload) : undefined,
    originalSubmissionId: submissionId,
    createdAt: submission.createdAt || serverTimestamp(),
    updatedAt: serverTimestamp(),
    promotedAt: serverTimestamp(),
    promotedBy: "local-admin-script"
  });
}

function buildSubmissionUpdate(remoteTaskId, publishUserCopy) {
  return {
    reviewStatus: "APPROVED",
    isPublic: Boolean(publishUserCopy),
    promotedToRemoteTaskId: remoteTaskId,
    promotedAt: serverTimestamp(),
    approvedBy: "local-admin-script",
    updatedAt: serverTimestamp()
  };
}

function warnIfA3DiagramIsLocalOnly(remoteDoc) {
  const question = isPlainObject(remoteDoc.payload?.question)
    ? remoteDoc.payload.question
    : remoteDoc.payload;
  const diagramImage = isPlainObject(question?.diagramImage)
    ? question.diagramImage
    : null;
  const isA3 = remoteDoc.templateId === "architect_a3_review" ||
    (typeof question?.questionIdPattern === "string" &&
      question.questionIdPattern.startsWith("A3."));

  if (!isA3 || !diagramImage) {
    return;
  }

  const hasRemoteImage = Boolean(
    (typeof diagramImage.downloadUrl === "string" && diagramImage.downloadUrl.trim()) ||
    (typeof diagramImage.remoteStoragePath === "string" && diagramImage.remoteStoragePath.trim())
  );
  const hasLocalImage = Boolean(
    (typeof diagramImage.localPath === "string" && diagramImage.localPath.trim()) ||
    (typeof diagramImage.localUri === "string" && diagramImage.localUri.trim())
  );

  if (hasLocalImage && !hasRemoteImage) {
    console.warn(
      "Warning: This A3 task has only a local image path and may not display on other devices. " +
      "Upload image to Firebase Storage first."
    );
  }
}

function serverTimestamp() {
  return admin.firestore.FieldValue.serverTimestamp();
}

async function main() {
  let args;
  try {
    args = parseArgs(process.argv.slice(2));
  } catch (error) {
    console.error(`Error: ${error.message}`);
    usage(1);
  }

  console.log(`Service account path: ${SERVICE_ACCOUNT_PATH}`);
  console.log(`Source submission id: ${args.submissionId}`);
  console.log(`Target remote task id: ${args.remoteTaskId}`);
  console.log(`Dry run: ${args.dryRun ? "yes" : "no"}`);
  console.log(`Overwrite existing remote task: ${args.overwrite ? "yes" : "no"}`);
  console.log(`Publish original user copy: ${args.publishUserCopy ? "yes" : "no"}`);

  const serviceAccount = loadServiceAccount(SERVICE_ACCOUNT_PATH);
  const db = initializeFirestore(serviceAccount);

  const submissionRef = db.collection("task_submissions").doc(args.submissionId);
  const remoteTaskRef = db.collection("remote_tasks").doc(args.remoteTaskId);

  const submissionSnapshot = await submissionRef.get();
  if (!submissionSnapshot.exists) {
    throw new Error(`Submission not found: task_submissions/${args.submissionId}`);
  }

  const submission = submissionSnapshot.data();
  const remoteDoc = buildRemoteTaskDocument(
    args.submissionId,
    args.remoteTaskId,
    submission
  );
  const submissionUpdate = buildSubmissionUpdate(args.remoteTaskId, args.publishUserCopy);

  console.log(`Level: ${remoteDoc.level}`);
  console.log(`Task type: ${remoteDoc.taskType}`);
  console.log(`Title: ${remoteDoc.title}`);
  warnIfA3DiagramIsLocalOnly(remoteDoc);

  const remoteSnapshot = await remoteTaskRef.get();
  if (remoteSnapshot.exists && !args.overwrite) {
    throw new Error(
      `remote_tasks/${args.remoteTaskId} already exists. Use --overwrite to replace it.`
    );
  }

  if (args.dryRun) {
    console.log("\nRemote task document that would be created:");
    console.log(JSON.stringify(toPrintable(remoteDoc), null, 2));
    console.log("\nOriginal submission update that would be applied:");
    console.log(JSON.stringify(toPrintable(submissionUpdate), null, 2));
    console.log("\nDry run complete. No Firestore writes were performed.");
    return;
  }

  await db.runTransaction(async (transaction) => {
    const currentRemoteSnapshot = await transaction.get(remoteTaskRef);
    if (currentRemoteSnapshot.exists && !args.overwrite) {
      throw new Error(
        `remote_tasks/${args.remoteTaskId} already exists. Use --overwrite to replace it.`
      );
    }
    transaction.set(remoteTaskRef, remoteDoc);
    transaction.update(submissionRef, submissionUpdate);
  });

  console.log("\nPromotion complete.");
  console.log(`Created remote task: remote_tasks/${args.remoteTaskId}`);
  console.log(`Updated source submission: task_submissions/${args.submissionId}`);
  if (!args.publishUserCopy) {
    console.log("Original submission was approved but kept non-public to avoid duplicates.");
  }
}

function isPlainObject(value) {
  return value !== null && typeof value === "object" && !Array.isArray(value);
}

function deepClone(value) {
  return JSON.parse(JSON.stringify(value));
}

function removeUndefined(value) {
  if (Array.isArray(value)) {
    return value.map(removeUndefined);
  }
  if (isPlainObject(value)) {
    return Object.fromEntries(
      Object.entries(value)
        .filter(([, item]) => item !== undefined)
        .map(([key, item]) => [key, removeUndefined(item)])
    );
  }
  return value;
}

function toPrintable(value) {
  if (Array.isArray(value)) {
    return value.map(toPrintable);
  }
  if (isFieldValue(value)) {
    return "<serverTimestamp()>";
  }
  if (isPlainObject(value)) {
    return Object.fromEntries(
      Object.entries(value).map(([key, item]) => [key, toPrintable(item)])
    );
  }
  return value;
}

function isFieldValue(value) {
  return value &&
    typeof value === "object" &&
    (
      (value.constructor &&
        value.constructor.name &&
        (value.constructor.name.includes("FieldValue") ||
          value.constructor.name.includes("ServerTimestamp"))) ||
      String(value._methodName || value.methodName || "")
        .toLowerCase()
        .includes("servertimestamp")
    );
}

main().catch((error) => {
  console.error(`\nPromotion failed: ${error.message}`);
  process.exit(1);
});
