# Firebase / Cloud Firestore setup

The Android app uses anonymous Firebase Authentication and these collections:

- `remote_tasks`: published tasks managed by the administrator.
- `task_submissions`: user proposals awaiting review.

The project currently pins Firebase BoM `32.7.1` because its Firebase artifacts are compatible
with the app's Kotlin `2.0.21` toolchain. The latest Firebase BoM requires a newer Kotlin compiler.

## Required console actions

1. Keep `google-services.json` in `app/google-services.json`.
2. In Firebase Console, open **Authentication > Sign-in method** and enable **Anonymous**.
3. Open **Firestore Database > Rules**, paste the contents of `firestore.rules`, and publish them.
4. Open **Storage**, create/enable the default bucket, then paste `storage.rules` under
   **Storage > Rules** and publish them.
5. Add the sample below as `remote_tasks/admin_beginner_001`.

## A3 diagram attachments

User-selected A3 diagrams are uploaded before the Firestore submission is created. Files are
stored under:

```text
task_submission_attachments/{submissionId}/diagram/{localFileName}
```

The app writes both `remoteStoragePath` and `downloadUrl` into
`payload.question.diagramImage`. Raw image bytes are never stored in Firestore. If Storage is not
enabled or the rules are not published, an A3 submission is kept locally and is not sent as an
incomplete remote task.

The supplied Storage rules allow only authenticated uploads whose metadata owner matches the
Firebase anonymous user and limit files to PNG/JPEG/WEBP up to 10 MB. Reads are allowed to
authenticated app clients for this diploma prototype. A production setup should tighten reads by
checking the related approved/public Firestore document through a trusted publishing pipeline.

Normal Android clients cannot write `remote_tasks` or approve submissions. Approval remains an
administrator action in Firebase Console.

## Playable Beginner sample

Document path: `remote_tasks/admin_beginner_001`

```json
{
  "title": "Izaberi obrazac za različite načine plaćanja",
  "level": "BEGINNER",
  "taskType": "pattern_recognition",
  "source": "ADMIN",
  "status": "ACTIVE",
  "publicationMode": "MAIN_TASK_LIST",
  "schemaVersion": 1,
  "createdAt": "Firestore timestamp",
  "updatedAt": "Firestore timestamp",
  "payload": {
    "level": "beginner",
    "type": "pattern_recognition",
    "title": "Izaberi obrazac za različite načine plaćanja",
    "prompt": "Prodavnica podržava karticu, uplatu na račun i plaćanje pouzećem. Checkout treba da bira način plaćanja bez menjanja glavnog toka kupovine.",
    "aiFollowUp": "Zašto je korisno da glavni checkout tok zavisi od zajedničkog ugovora za plaćanje?",
    "wave": 4,
    "difficulty": "easy",
    "orderIndex": 1,
    "estimatedMinutes": 2,
    "steps": [
      {
        "type": "single_choice",
        "title": "Izbor obrasca",
        "instruction": "Izaberi obrazac koji najbolje odvaja različite algoritme plaćanja.",
        "requiredCount": 1,
        "explanation": "Strategy omogućava da svaki način plaćanja implementira isti ugovor, dok checkout bira odgovarajuću implementaciju.",
        "options": [
          {
            "label": "A",
            "text": "Strategy",
            "optionOrder": 1,
            "isCorrect": true
          },
          {
            "label": "B",
            "text": "Singleton",
            "optionOrder": 2,
            "isCorrect": false
          },
          {
            "label": "C",
            "text": "Observer",
            "optionOrder": 3,
            "isCorrect": false
          },
          {
            "label": "D",
            "text": "Decorator",
            "optionOrder": 4,
            "isCorrect": false
          }
        ]
      }
    ]
  }
}
```

Use native Firestore Timestamp values for `createdAt` and `updatedAt` in Firebase Console.

## Manual approval

To publish a submitted task in the separate **Korisnički zadaci** section, update its
`task_submissions/{id}` document in Firebase Console:

```text
reviewStatus = APPROVED
isPublic = true
approvedAt = <Firestore timestamp>
approvedBy = <administrator identifier>
```

The task payload must still match a playable app schema. Invalid or unsupported documents are
logged and skipped without affecting local tasks.
