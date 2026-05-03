package com.example.pmuprojekat.data.seed.medior

import com.example.pmuprojekat.data.seed.SeedQuestion

object MediorSequenceLogicSeed {
    val questions: List<SeedQuestion> = listOf(
        MediorSeedBuilders.sequenceQuestion(
            questionId = "M1.1",
            title = "Širenje toka porudžbine bez rasta spregnutosti",
            prompt = """
            Klijent opisuje problem ovako:
            Kada kupac završi kupovinu, sistem treba da zapamti porudžbinu, potvrdi naplatu i tek onda pošalje potvrdu korisniku. Uz to, poslovni tim traži da se iz iste akcije kasnije lako povežu dodatne stvari kao što su loyalty poeni, CRM evidencija, fraud provera i audit zapis. Trenutno tim ne želi da svaki novi zahtev menja centralnu logiku za porudžbine.
            """.trimIndent(),
            cards = listOf(
                "Notification module sends confirmation to customer",
                "Payment module confirms successful transaction",
                "Customer finishes checkout",
                "Order module persists order",
                "Additional business reactions are triggered"
            ),
            correctOrder = listOf(
                "Customer finishes checkout",
                "Order module persists order",
                "Payment module confirms successful transaction",
                "Notification module sends confirmation to customer",
                "Additional business reactions are triggered"
            ),
            alternatives = listOf(
                "Observer",
                "Pub/Sub"
            ),
            correctAlternative = "Pub/Sub",
            aiFollowUp = "Koja bi bila najverovatnija negativna posledica ako bi Order modul ostao direktno povezan sa svakim novim potrošačem?",
            wave = 1,
            orderIndex = 1
        ),

        MediorSeedBuilders.sequenceQuestion(
            questionId = "M1.2",
            title = "Kada izbor tipa nije dovoljan, već je važna kontrolisana izgradnja",
            prompt = """
            Tim razvija modul za generisanje poslovnih izveštaja. Korisnik najpre bira tip izveštaja, ali zatim za isti tip može uključiti ili isključiti različite sekcije: summary, detaljne tabele, grafikone, komentare i izvozne formate. Dodatno, pojedine kombinacije sekcija nisu obavezne, a očekuje se da će u budućnosti nastajati nove varijante istog tipa izveštaja.
            Arhitekta upozorava da tim ne sme završiti sa velikim brojem fabričkih metoda, preopterećenih konstruktora i nepreglednim grananjem u kodu.
            """.trimIndent(),
            cards = listOf(
                "System assembles selected report parts",
                "User chooses report configuration",
                "System exports final report",
                "System loads domain data",
                "System creates final report instance"
            ),
            correctOrder = listOf(
                "User chooses report configuration",
                "System loads domain data",
                "System assembles selected report parts",
                "System creates final report instance",
                "System exports final report"
            ),
            alternatives = listOf(
                "Factory Method",
                "Builder"
            ),
            correctAlternative = "Builder",
            aiFollowUp = """
            Koji bi tehnički problem najpre postao vidljiv ako bi se svaka nova kombinacija sekcija rešavala dodatnim fabričkim metodama i konstruktorima?

            2. Drugi tip zadatka: Dopuna pseudo-koda u kontekstu sistema
            """.trimIndent(),
            wave = 1,
            orderIndex = 2
        ),

        MediorSeedBuilders.sequenceQuestion(
            questionId = "M1.3",
            title = "Promena ponašanja pretplate i izbor pristupa",
            prompt = """
            Tim radi sistem za digitalne pretplate. U jednom delu sistema korisnička pretplata prolazi kroz više faza: kreiranje, aktivacija nakon uspešne naplate, eventualna suspenzija zbog problema sa plaćanjem i ponovno aktiviranje. Paralelno s tim, poslovni tim traži da se način obračuna cene može menjati u zavisnosti od tipa korisnika, kampanje i regiona.
            Developer koji je dobio zadatak kaže da bi “jedan isti obrazac mogao rešiti i ponašanje pretplate i obračun cene”, ali arhitekta sumnja da su to zapravo dva različita problema.
            """.trimIndent(),
            cards = listOf(
                "Subscription becomes Active",
                "Payment is confirmed",
                "Subscription request is created",
                "Subscription may move to Suspended if payment fails later",
                "Customer selects plan"
            ),
            correctOrder = listOf(
                "Customer selects plan",
                "Subscription request is created",
                "Payment is confirmed",
                "Subscription becomes Active",
                "Subscription may move to Suspended if payment fails later"
            ),
            alternatives = listOf(
                "Strategy",
                "State"
            ),
            correctAlternative = "Strategy",
            aiFollowUp = """
            Objasni u jednoj do dve rečenice zašto je “promena faze pretplate” problem za State, a “promena pricing pravila” problem za Strategy.
            """.trimIndent(),
            wave = 2,
            orderIndex = 11
        ),

        MediorSeedBuilders.sequenceQuestion(
            questionId = "M1.4",
            title = "Integracija provajdera i razdvajanje dve ose promena",
            prompt = """
            Platforma treba da šalje obaveštenja kroz više kanala: email, SMS i push. Istovremeno, postoje različite vrste poruka: transakcione, promotivne i sistemske. Tim najpre pokušava da reši sve preko nasleđivanja i dobija kombinacije kao što su:
            •	TransactionalEmailMessage 
            •	SystemSmsMessage 
            •	PromoPushMessage 
            U isto vreme, jedan od SMS provajdera ima stari interfejs koji nije kompatibilan sa ostatkom sistema.
            """.trimIndent(),
            cards = listOf(
                "Concrete provider sends payload",
                "Message content is prepared",
                "System selects message category",
                "Rendering/sending mechanism is chosen",
                "Delivery request is issued"
            ),
            correctOrder = listOf(
                "System selects message category",
                "Message content is prepared",
                "Rendering/sending mechanism is chosen",
                "Delivery request is issued",
                "Concrete provider sends payload"
            ),
            alternatives = listOf(
                "Adapter",
                "Bridge"
            ),
            correctAlternative = "Bridge",
            aiFollowUp = "Koja bi bila najverovatnija posledica ako bi se problem dve ose promena nastavio rešavati samo novim podklasama?",
            wave = 2,
            orderIndex = 12
        ),

        MediorSeedBuilders.sequenceQuestion(
            questionId = "M1.5",
            title = "Obrada registracije partnera kroz više nezavisnih provera",
            prompt = """
            Kompanija uvodi onboarding za poslovne partnere gde se svaka prijava obrađuje kroz više nezavisnih provera: validaciju formata podataka, proveru postojanja firme, regulatornu usklađenost i odluku o automatskom odobrenju ili slanju na ručnu obradu.
            Tim očekuje da će se kasnije dodavati nova pravila, menjati redosled pojedinih provera i uvoditi specifični koraci za različite tipove partnera. Takođe, pojedini koraci mogu zaustaviti obradu čim utvrde da prijava ne ispunjava uslove.
            Potrebno je osmisliti rešenje koje omogućava jasan tok obrade i lako dodavanje ili izmenu koraka bez rasta jedne centralne funkcije.
            """.trimIndent(),
            cards = listOf(
                "System validates submitted data format",
                "Partner submits onboarding request",
                "System checks regulatory constraints",
                "System checks whether company already exists",
                "System decides automatic approval or manual review"
            ),
            correctOrder = listOf(
                "Partner submits onboarding request",
                "System validates submitted data format",
                "System checks whether company already exists",
                "System checks regulatory constraints",
                "System decides automatic approval or manual review"
            ),
            alternatives = listOf(
                "Decorator",
                "Chain of Responsibility"
            ),
            correctAlternative = "Chain of Responsibility",
            aiFollowUp = """
            Koji bi problem najpre postao kritičan ako bi se broj validacionih pravila udvostručio, a sva logika ostala u jednoj centralnoj funkciji?
            """.trimIndent(),
            wave = 3,
            orderIndex = 21
        ),

        MediorSeedBuilders.sequenceQuestion(
            questionId = "M1.6",
            title = "Povratak na ranije stanje kompleksnog draft-a",
            prompt = """
            Tim razvija editor za pravljenje ponuda. Korisnik menja:
            •	tekst ponude, 
            •	stavke, 
            •	popuste, 
            •	status nacrta, 
            •	odabrane priloge. 
            Poslovni zahtev kaže da korisnik treba da ima mogućnost da vrati prethodno stanje nacrta, ali tim ne želi da spoljne komponente ručno barataju svim internim detaljima objekta draft-a niti da poznaju strukturu svih njegovih polja.
            """.trimIndent(),
            cards = listOf(
                "System stores previous draft snapshot",
                "User edits current draft",
                "System restores previous draft state if requested",
                "Draft object changes internal state",
                "User triggers undo"
            ),
            correctOrder = listOf(
                "User edits current draft",
                "System stores previous draft snapshot",
                "Draft object changes internal state",
                "User triggers undo",
                "System restores previous draft state if requested"
            ),
            alternatives = listOf(
                "Command",
                "Memento"
            ),
            correctAlternative = "Memento",
            aiFollowUp = """
            Zašto postaje rizično kada spoljne klase počnu da znaju previše o unutrašnjem stanju objekta koji treba vratiti na prethodnu verziju?
            """.trimIndent(),
            wave = 3,
            orderIndex = 22
        ),

        MediorSeedBuilders.sequenceQuestion(
            questionId = "M1.7",
            title = "Workflow obrade zahteva i izbor pristupa za ponašanje po statusu",
            prompt = """
            Tim razvija sistem za obradu reklamacija. Kada korisnik pošalje reklamaciju, sistem prvo evidentira zahtev, zatim dodeljuje operatera, potom reklamacija prelazi u proveru, nakon toga može biti odobrena ili odbijena, a na kraju se zatvara. Poslovni tim najavljuje da će se kasnije dodavati nova pravila ponašanja za različite statuse, kao i nova stanja poput „na čekanju” i „eskalirano”.
            """.trimIndent(),
            cards = listOf(
                "Claim moves to review",
                "Claim is recorded",
                "Claim gets assigned",
                "Claim is resolved",
                "Customer submits claim"
            ),
            correctOrder = listOf(
                "Customer submits claim",
                "Claim is recorded",
                "Claim gets assigned",
                "Claim moves to review",
                "Claim is resolved"
            ),
            alternatives = listOf(
                "Template Method",
                "State"
            ),
            correctAlternative = "State",
            aiFollowUp = """
            Zašto kod sistema sa rastućim brojem statusa problem nije samo “više if-else grana”, nego i teže razumevanje odgovornosti po stanju?
            """.trimIndent(),
            wave = 4,
            orderIndex = 31
        ),

        MediorSeedBuilders.sequenceQuestion(
            questionId = "M1.8",
            title = "Prikaz dokumenata i izbor pristupa za skupe udaljene resurse",
            prompt = """
            Aplikacija za pravne dokumente prikazuje listu ugovora. Korisnik često vidi samo osnovne informacije, a puni sadržaj ugovora otvara tek kad klikne na detalje. U pozadini, puni dokument dolazi sa udaljenog servisa koji je spor i skup za pozivanje. Tim želi da početni prikaz liste ostane brz i da pristup punom dokumentu može biti kontrolisan.
            """.trimIndent(),
            cards = listOf(
                "Remote service returns full document",
                "System loads list metadata",
                "User opens document details",
                "System requests full document content",
                "List of documents is displayed"
            ),
            correctOrder = listOf(
                "System loads list metadata",
                "List of documents is displayed",
                "User opens document details",
                "System requests full document content",
                "Remote service returns full document"
            ),
            alternatives = listOf(
                "Flyweight",
                "Proxy"
            ),
            correctAlternative = "Proxy",
            aiFollowUp = """
            Koja bi bila najverovatnija mana kada bi aplikacija pri svakom otvaranju liste odmah učitavala pun sadržaj svih dokumenata?
            """.trimIndent(),
            wave = 4,
            orderIndex = 32
        ),

        MediorSeedBuilders.sequenceQuestion(
            questionId = "M1.9",
            title = "Tok sinhronizacije korisničkog profila i izbor pristupa za reakcije sistema",
            prompt = """
            U korisničkom portalu ekran za izmenu profila uključuje više povezanih UI komponenti (forma, status prikaz, notifikacije i audit prikaz) koje moraju reagovati na promenu podataka kada korisnik sačuva izmene; trenutna implementacija vodi ka sve većem broju direktnih međusobnih zavisnosti između komponenti kako sistem raste, pa tim želi da uvede pristup koji smanjuje direktno povezivanje i centralizuje koordinaciju njihovih reakcija na promene.
            """.trimIndent(),
            cards = listOf(
                "Audit module records profile update",
                "Profile data is persisted",
                "User submits profile changes",
                "UI refreshes visible profile data",
                "Notification center shows success message"
            ),
            correctOrder = listOf(
                "User submits profile changes",
                "Profile data is persisted",
                "UI refreshes visible profile data",
                "Audit module records profile update",
                "Notification center shows success message"
            ),
            alternatives = listOf(
                "Observer",
                "Mediator"
            ),
            correctAlternative = "Mediator",
            aiFollowUp = "Zašto je “mnogo međusobnih UI pravila” drugačiji problem od “jedan događaj, više pretplaćenih reakcija”?",
            wave = 5,
            orderIndex = 41
        ),

        MediorSeedBuilders.sequenceQuestion(
            questionId = "M1.10",
            title = "Jednostavan ulaz u složen proces obrade dokumenta",
            prompt = """
            Sistem za obradu poslovne dokumentacije koristi više internih podsistema: OCR servis, proveru potpisa, klasifikaciju dokumenta, arhiviranje i audit zapis. Trenutno aplikacioni sloj mora direktno da poziva svaki od tih servisa redom, zbog čega je tok komplikovan, teško ga je pratiti i još teže održavati.
            Tim želi da ostatak sistema dobije jednostavan način da pokrene obradu dokumenta bez poznavanja svih internih koraka i međuzavisnosti između podsistema.
            """.trimIndent(),
            cards = listOf(
                "Document is uploaded by user",
                "System starts document processing",
                "OCR and validation services are invoked",
                "Document is archived and audit is recorded",
                "Processing result is returned to application"
            ),
            correctOrder = listOf(
                "Document is uploaded by user",
                "System starts document processing",
                "OCR and validation services are invoked",
                "Document is archived and audit is recorded",
                "Processing result is returned to application"
            ),
            alternatives = listOf(
                "Facade",
                "Adapter"
            ),
            correctAlternative = "Facade",
            aiFollowUp = "U kom slučaju bi Adapter postao važniji od Facade u ovom istom sistemu?",
            wave = 5,
            orderIndex = 42
        )
    )
}
