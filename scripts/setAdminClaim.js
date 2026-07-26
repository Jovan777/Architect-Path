#!/usr/bin/env node

const fs = require("fs");
const path = require("path");
const admin = require("firebase-admin");

const scriptDirectory = __dirname;
const defaultServiceAccountPath = path.join(
  scriptDirectory,
  "serviceAccountKey.json"
);
const serviceAccountPath =
  process.env.FIREBASE_SERVICE_ACCOUNT || defaultServiceAccountPath;

function printUsage() {
  console.log(
    "Usage: node setAdminClaim.js admin@example.com\n" +
      "From project root: node scripts/setAdminClaim.js admin@example.com"
  );
}

function loadServiceAccount(filePath) {
  if (!fs.existsSync(filePath)) {
    throw new Error(
      "Service account key nije pronađen. Postavite ga u " +
        "scripts/serviceAccountKey.json ili koristite FIREBASE_SERVICE_ACCOUNT."
    );
  }

  const serviceAccount = JSON.parse(fs.readFileSync(filePath, "utf8"));
  for (const requiredField of ["project_id", "client_email", "private_key"]) {
    if (!serviceAccount[requiredField]) {
      throw new Error(
        `Service account key nema obavezno polje: ${requiredField}`
      );
    }
  }
  return serviceAccount;
}

async function main() {
  const email = process.argv[2]?.trim();
  if (!email || process.argv.length !== 3) {
    printUsage();
    process.exitCode = 1;
    return;
  }

  const serviceAccount = loadServiceAccount(serviceAccountPath);
  admin.initializeApp({
    credential: admin.credential.cert(serviceAccount)
  });

  const user = await admin.auth().getUserByEmail(email);
  const existingClaims = user.customClaims || {};

  await admin.auth().setCustomUserClaims(user.uid, {
    ...existingClaims,
    admin: true
  });

  console.log(`Admin claim je uspešno dodeljen nalogu: ${email}`);
  console.log("Korisnik treba ponovo da otvori admin pristup radi osvežavanja tokena.");
}

main().catch((error) => {
  if (error?.code === "auth/user-not-found") {
    console.error("Firebase Authentication nalog sa tom e-mail adresom ne postoji.");
  } else {
    console.error(`Dodela admin claim-a nije uspela: ${error.message}`);
  }
  process.exitCode = 1;
});
