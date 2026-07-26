# Firebase Admin Phase 2

Phase 2 omogućava administratoru da u aplikaciji pregleda i moderira dokumente iz
`task_submissions`.

## Preduslovi

1. Email/Password prijava je uključena u Firebase Authentication.
2. Admin nalog ima custom claim `admin = true`.
3. Aplikacija koristi sekundarni Firebase app `adminAuthApp`.
4. Pravila iz korenskog fajla `firestore.rules` su objavljena.

## Objavljivanje Firestore pravila

U Firebase Console otvori:

`Firestore Database -> Rules`

Zameni sadržaj pravilima iz `firestore.rules`, zatim izaberi **Publish**.

Ako je Firebase CLI prijavljen na odgovarajući projekat, ista pravila mogu da se
objave iz korena projekta:

```powershell
firebase deploy --only firestore:rules
```

## Tok moderacije

- **Odobri kao korisnički zadatak** menja postojeći predlog u
  `reviewStatus = APPROVED` i `isPublic = true`.
- **Promoviši u zvanične zadatke** atomski pravi dokument u `remote_tasks` i
  original postavlja na `isPublic = false`, tako da nema duplikata.
- **Odbij zadatak** zadržava dokument, postavlja `reviewStatus = REJECTED` i
  `isPublic = false`.

Promocija koristi stabilan ID `admin_<submissionId>` ako predlog već nema
`promotedToRemoteTaskId`. Ponovljena promocija prepoznaje postojeći zvanični
zadatak i ne pravi novu kopiju.

## A3 slike

Za A3 promociju payload mora da sadrži najmanje jedno od:

- `diagramImage.downloadUrl`
- `diagramImage.remoteStoragePath`

Lokalni `localPath` i `localUri` nisu dovoljni za drugi uređaj. Admin detalj
prikazuje upozorenje, a promocija bez udaljene slike je blokirana.

## Admin probni režim

`Pregledaj zadatak` i `Probno reši zadatak` koriste isti renderer kao redovni
zadaci. Probni režim ne upisuje:

- Room odgovore ili napredak
- XP
- leaderboard poene
- Firestore pokušaje

