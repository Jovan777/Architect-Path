# Firebase progres i rang-lista — Phase 1

Phase 1 čuva lokalni progres kao izvor istine i asinhrono šalje samo rezultat pokušaja.
Odgovori, AI razgovori, slike i drugi privatni sadržaji zadatka ne šalju se.

## Firestore struktura

Privatni profil:

```text
users/{anonymousFirebaseUid}
  uid
  displayName
  totalPoints
  totalAttempts
  uniqueTasksAttempted
  completedTasksCount
  lastActiveAt
  progressByLevel
  schemaVersion
  updatedAt
```

Privatna istorija:

```text
users/{anonymousFirebaseUid}/task_attempts/{attemptId}
  attemptId
  taskId
  taskTitle
  level
  taskType
  taskSource
  percentage
  pointsAwarded
  attemptNumber
  completedAt
  schemaVersion
```

Javna rang-lista:

```text
leaderboard/{anonymousFirebaseUid}
  uid
  displayName
  totalPoints
  updatedAt
```

`totalPoints` se ne dobija sabiranjem pokušaja. Aplikacija ga izračunava iz postojećih
`bestEarnedXp` vrednosti, istim putem kojim se dobija XP prikazan u aplikaciji.

## Offline i ponovni pokušaj

Svaki završetak zadatka u istoj Room transakciji čuva lokalni progres i
`task_attempt_sync` zapis sa stabilnim UUID identifikatorom. Firestore dokument koristi isti
`attemptId`.

Ako aplikacija ne zna da li je prethodni upis uspeo, prvo proverava dokument sa tim ID-em:

- ako postoji, lokalni zapis označava kao `UPLOADED`;
- ako ne postoji, kreira ga;
- ako mreža nije dostupna, ostaje `PENDING`.

Pending zapisi se ponovo šalju pri pokretanju aplikacije, sledećem završetku zadatka i otvaranju
rang-liste.

## Objavljivanje Firestore pravila

Aktuelna pravila su u korenskom fajlu:

```text
firestore.rules
```

U Firebase Console otvorite:

```text
Firestore Database > Rules
```

Zamenite sadržaj sadržajem iz `firestore.rules`, zatim kliknite `Publish`.

Ako projekat kasnije dobije konfigurisan Firebase CLI deployment, ista pravila se mogu objaviti
komandom:

```bash
firebase deploy --only firestore:rules
```

Pravila ne daju običnom korisniku pristup tuđem privatnom progresu. Admin čitanje zahteva token
sa `admin = true`. Pokušaji ne mogu da se menjaju niti brišu iz Android klijenta.

## Granice Phase 1

Opcija `Korisnički zadaci` u Admin panelu ostaje placeholder. Moderacija, odobravanje i promocija
zadataka nisu deo ove faze.
