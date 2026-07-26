# Firebase admin pristup u Android aplikaciji

Admin panel koristi poseban Firebase Authentication klijent. Običan korisnik i dalje ostaje
anonimno prijavljen preko podrazumevanog Firebase klijenta, dok se admin prijavljuje preko
imenovanog klijenta `adminAuthApp`.

Sama e-mail/lozinka prijava nije dovoljna. Android aplikacija otvara panel tek kada svež Firebase
ID token sadrži custom claim:

```text
admin = true
```

## 1. Uključite Email/Password prijavu

U Firebase Console otvorite:

```text
Authentication > Sign-in method > Email/Password
```

Uključite prvi `Email/Password` provider i sačuvajte promenu.

## 2. Napravite admin nalog

U Firebase Console otvorite:

```text
Authentication > Users > Add user
```

Unesite e-mail adresu i jaku lozinku. Lozinka se ne upisuje u projekat, skriptu, Git niti
`local.properties`.

## 3. Dodelite admin claim

Pravi service account ključ treba lokalno da postoji kao:

```text
scripts/serviceAccountKey.json
```

Fajl je ignorisan u `.gitignore` i ne sme se commit-ovati.

Zavisnosti za skripte su već definisane u `scripts/package.json`. Ako nisu instalirane:

```bash
cd scripts
npm install
```

Zatim iz `scripts` direktorijuma pokrenite:

```bash
node setAdminClaim.js admin@example.com
```

Ili iz korena projekta:

```bash
node scripts/setAdminClaim.js admin@example.com
```

Skripta pronalazi korisnika po e-mail adresi, čuva sve njegove postojeće custom claim-ove i dodaje
`admin: true`. Ne ispisuje UID, tokene, private key ni lozinku.

## 4. Prijava u aplikaciji

Otvorite:

```text
Profil > Admin pristup
```

Unesite admin e-mail i lozinku. Aplikacija zatim osvežava ID token i proverava `admin = true`.
Nalog bez tog claim-a se odmah odjavljuje samo iz admin dela.

Admin odjava ne odjavljuje anonimnog korisnika i ne utiče na progres, XP, Room podatke ili
Firebase task sinhronizaciju.
