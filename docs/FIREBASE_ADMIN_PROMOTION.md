# Firebase admin promotion utility

This repository includes a local admin-only script for promoting a submitted task into an
official remote admin task.

This is not an Android app feature. Normal app users must not be able to write to
`remote_tasks`. The script runs only on the project owner's computer with a local Firebase
service account key.

## What the script does

The script reads:

```text
task_submissions/{submissionId}
```

and creates:

```text
remote_tasks/{remoteTaskId}
```

The created remote task uses admin metadata:

```text
source = ADMIN
status = ACTIVE
publicationMode = MAIN_TASK_LIST
createdByRole = ADMIN
isPublic = true
```

It also stores:

```text
originalSubmissionId = <submissionId>
promotedBy = local-admin-script
```

By default, the original submission is marked as approved and linked to the promoted task, but
it is kept non-public to avoid showing the same task twice.

## Install dependencies

From the project root:

```bash
cd scripts
npm install
```

This installs the Firebase Admin SDK only for local scripts. It does not affect the Android app.

## Service account key

Download a Firebase service account key manually from Firebase / Google Cloud project settings.

Save the real key here:

```text
scripts/serviceAccountKey.json
```

This file is ignored by Git. Do not commit it.

You can also use another path:

```bash
FIREBASE_SERVICE_ACCOUNT=C:\path\to\serviceAccountKey.json node scripts/promoteTaskSubmission.js <submissionId> <remoteTaskId>
```

## Dry run

First run a dry run:

```bash
node scripts/promoteTaskSubmission.js local_123 admin_test_001 --dry-run
```

The script will:

1. Load the service account.
2. Read `task_submissions/local_123`.
3. Validate required fields.
4. Print the `remote_tasks/admin_test_001` document that would be created.
5. Print the source submission update that would be applied.
6. Perform no Firestore writes.

Example dry-run output:

```text
Service account path: C:\Users\...\PMUProjekat\scripts\serviceAccountKey.json
Source submission id: local_123
Target remote task id: admin_test_001
Dry run: yes
Overwrite existing remote task: no
Publish original user copy: no
Level: BEGINNER
Task type: pattern_recognition
Title: Izaberi obrazac za različite načine plaćanja

Remote task document that would be created:
{
  "id": "admin_test_001",
  "title": "Izaberi obrazac za različite načine plaćanja",
  "level": "BEGINNER",
  "taskType": "pattern_recognition",
  "source": "ADMIN",
  "status": "ACTIVE",
  "publicationMode": "MAIN_TASK_LIST",
  "createdByRole": "ADMIN",
  "isPublic": true,
  "originalSubmissionId": "local_123",
  "updatedAt": "<serverTimestamp()>",
  "promotedAt": "<serverTimestamp()>"
}

Dry run complete. No Firestore writes were performed.
```

## Real promotion

If the dry run looks correct:

```bash
node scripts/promoteTaskSubmission.js local_123 admin_beginner_002
```

The app will fetch `remote_tasks/admin_beginner_002` as an official remote admin task. It should
appear in the normal task list for its level after the app refreshes remote tasks.

## Overwriting an existing remote task

The script refuses to overwrite an existing `remote_tasks/{remoteTaskId}` by default.

Use this only when you intentionally want to replace the remote document:

```bash
node scripts/promoteTaskSubmission.js local_123 admin_beginner_002 --overwrite
```

## Publishing the original user copy too

Default behavior prevents duplicates:

```text
remote_tasks/{remoteTaskId}
  -> appears in the normal official task list

task_submissions/{submissionId}
  -> approved and linked, but not public
```

If you also want the original submission to appear under **Korisnički zadaci**, run:

```bash
node scripts/promoteTaskSubmission.js local_123 admin_beginner_002 --publish-user-copy
```

This sets:

```text
reviewStatus = APPROVED
isPublic = true
```

## Approved user task vs promoted admin task

Approved user task:

```text
task_submissions reviewStatus = APPROVED
task_submissions isPublic = true
```

Result:

```text
Appears under "Korisnički zadaci".
```

Promoted admin task:

```text
remote_tasks source = ADMIN
remote_tasks status = ACTIVE
remote_tasks publicationMode = MAIN_TASK_LIST
```

Result:

```text
Appears in the normal official task list.
```

## Safety notes

- Do not add this promotion flow to the Android app.
- Do not allow client writes to `remote_tasks`.
- Do not commit `scripts/serviceAccountKey.json`.
- Use `--dry-run` before every real promotion.
