package com.example.pmuprojekat.data.seed.beginner

import com.example.pmuprojekat.data.seed.SeedQuestion

object BeginnerWave2Seed {
    val questions: List<SeedQuestion> = listOf(
        BeginnerSeedBuilders.patternRecognitionQuestion(
            questionId = "P1.11",
            title = "Kreiranje porodice povezanih objekata",
            prompt = "Aplikacija treba da podrži više tema korisničkog interfejsa. Kada korisnik izabere “Dark Theme”, sistem treba da kreira odgovarajuće dugme, meni i prozor za tu temu. Kada izabere “Light Theme”, treba da kreira drugu grupu istih tih elemenata. Koji obrazac projektovanja najbolje odgovara?",
            options = listOf("Builder", "Abstract Factory", "Prototype", "Facade"),
            correctAnswer = "Abstract Factory",
            aiFollowUp = "Zašto je ovde bitno da se kreira cela porodica povezanih objekata, a ne samo jedan objekat?",
            wave = 2,
            orderIndex = 11,
            difficulty = "medium"
        ),

        BeginnerSeedBuilders.patternRecognitionQuestion(
            questionId = "P1.12",
            title = "Kreiranje kopiranjem postojećeg objekta",
            prompt = "U igri postoji složen neprijatelj sa mnogo podešavanja. Umesto da se svaki put pravi od nule, želiš da napraviš novu verziju tako što ćeš kopirati već postojećeg neprijatelja i po potrebi malo ga izmeniti. Koji obrazac odgovara?",
            options = listOf("Factory Method", "Prototype", "Singleton", "Strategy"),
            correctAnswer = "Prototype",
            aiFollowUp = "Koja je glavna prednost kopiranja postojećeg objekta umesto kreiranja od nule?",
            wave = 2,
            orderIndex = 12,
            difficulty = "medium"
        ),

        BeginnerSeedBuilders.patternRecognitionQuestion(
            questionId = "P1.13",
            title = "Odvajanje apstrakcije i implementacije",
            prompt = "Imaš aplikaciju za slanje poruka koja podržava više tipova poruka, ali i više kanala slanja, kao što su email i SMS. Želiš da se tip poruke i način slanja mogu razvijati nezavisno jedan od drugog. Koji obrazac je najpogodniji?",
            options = listOf("Bridge", "Adapter", "Observer", "Composite"),
            correctAnswer = "Bridge",
            aiFollowUp = "Šta znači da se apstrakcija i implementacija menjaju nezavisno?",
            wave = 2,
            orderIndex = 13,
            difficulty = "medium"
        ),

        BeginnerSeedBuilders.patternRecognitionQuestion(
            questionId = "P1.14",
            title = "Pojedinačni objekti i grupe objekata isto",
            prompt = "U sistemu za fajlove želiš da i pojedinačni fajl i folder koji sadrži više fajlova možeš da tretiraš na isti način, na primer da pozoveš istu operaciju showSize(). Koji obrazac odgovara?",
            options = listOf("Composite", "Decorator", "Proxy", "Command"),
            correctAnswer = "Composite",
            aiFollowUp = "Zašto je korisno da fajl i folder imaju isti način korišćenja iz ugla klijenta?",
            wave = 2,
            orderIndex = 14,
            difficulty = "medium"
        ),

        BeginnerSeedBuilders.patternRecognitionQuestion(
            questionId = "P1.15",
            title = "Prolazak kroz kolekciju bez otkrivanja njene unutrašnje strukture",
            prompt = "Korisnik treba da prolazi kroz elemente liste jedan po jedan, ali bez znanja o tome kako su oni interno sačuvani. Koji obrazac najbolje odgovara?",
            options = listOf("Iterator", "Mediator", "State", "Prototype"),
            correctAnswer = "Iterator",
            aiFollowUp = "Zašto je dobro da korisnik kolekcije ne zna njenu unutrašnju implementaciju?",
            wave = 2,
            orderIndex = 15,
            difficulty = "medium"
        ),

        BeginnerSeedBuilders.patternRecognitionQuestion(
            questionId = "P1.16",
            title = "Akcija kao objekat",
            prompt = "U aplikaciji za tekst editor želiš da operacije kao što su Copy, Paste i Undo budu predstavljene kao posebni objekti koje možeš pozivati, čuvati i ponavljati. Koji obrazac odgovara?",
            options = listOf("Strategy", "Command", "Template Method", "Memento"),
            correctAnswer = "Command",
            aiFollowUp = "Zašto je korisno da akcija bude predstavljena kao objekat?",
            wave = 2,
            orderIndex = 16,
            difficulty = "medium"
        ),

        BeginnerSeedBuilders.patternRecognitionQuestion(
            questionId = "P1.17",
            title = "Kostur algoritma u osnovnoj klasi",
            prompt = "Postoji opšti proces obrade dokumenta: učitaj, obradi, sačuvaj. Želiš da osnovni redosled bude isti, ali da različite podklase mogu drugačije implementirati korak obrade. Koji obrazac najbolje odgovara?",
            options = listOf("Strategy", "Template Method", "Builder", "Observer"),
            correctAnswer = "Template Method",
            aiFollowUp = "Šta ostaje isto, a šta se menja kod Template Method obrasca?",
            wave = 2,
            orderIndex = 17,
            difficulty = "medium"
        ),

        BeginnerSeedBuilders.visualMappingQuestion(
            questionId = "P2.7",
            targetPattern = "Abstract Factory",
            options = listOf(
                VisualOptionSeed(label = "Abstract Factory", text = "interfejs GUIFactory; metode createButton() i createWindow(); konkretne fabrike DarkThemeFactory i LightThemeFactory; konkretni proizvodi DarkButton, LightButton, DarkWindow, LightWindow.", isCorrect = true),
                VisualOptionSeed(label = "Prototype", text = "interfejs ili klasa Prototype; metoda clone(); konkretne klase koje implementiraju clone(); klijent dobija novi objekat kopiranjem postojećeg.", isCorrect = false),
                VisualOptionSeed(label = "Bridge", text = "apstrakcija Message; proširene apstrakcije ShortMessage, DetailedMessage; implementor interfejs MessageSender; konkretne implementacije EmailSender, SmsSender; Message sadrži referencu na MessageSender.", isCorrect = false),
                VisualOptionSeed(label = "Composite", text = "zajednički interfejs FileSystemItem; File i Folder implementiraju isti interfejs; Folder sadrži listu FileSystemItem; klijent koristi isti interfejs za oba.", isCorrect = false)
            ),
            aiFollowUp = "Koji deo dijagrama pokazuje da se kreira više povezanih proizvoda?",
            wave = 2,
            orderIndex = 107,
            difficulty = "medium"
        ),

        BeginnerSeedBuilders.visualMappingQuestion(
            questionId = "P2.8",
            targetPattern = "Prototype",
            options = listOf(
                VisualOptionSeed(label = "Prototype", text = "interfejs ili klasa Prototype; metoda clone(); konkretne klase koje implementiraju clone(); klijent dobija novi objekat kopiranjem postojećeg.", isCorrect = true),
                VisualOptionSeed(label = "Abstract Factory", text = "interfejs GUIFactory; metode createButton() i createWindow(); konkretne fabrike DarkThemeFactory i LightThemeFactory; konkretni proizvodi DarkButton, LightButton, DarkWindow, LightWindow.", isCorrect = false),
                VisualOptionSeed(label = "Bridge", text = "apstrakcija Message; proširene apstrakcije ShortMessage, DetailedMessage; implementor interfejs MessageSender; konkretne implementacije EmailSender, SmsSender; Message sadrži referencu na MessageSender.", isCorrect = false),
                VisualOptionSeed(label = "Composite", text = "zajednički interfejs FileSystemItem; File i Folder implementiraju isti interfejs; Folder sadrži listu FileSystemItem; klijent koristi isti interfejs za oba.", isCorrect = false)
            ),
            aiFollowUp = "Koji element dijagrama pokazuje da se novi objekat dobija kopiranjem?",
            wave = 2,
            orderIndex = 108,
            difficulty = "medium"
        ),

        BeginnerSeedBuilders.visualMappingQuestion(
            questionId = "P2.9",
            targetPattern = "Bridge",
            options = listOf(
                VisualOptionSeed(label = "Bridge", text = "apstrakcija Message; proširene apstrakcije ShortMessage, DetailedMessage; implementor interfejs MessageSender; konkretne implementacije EmailSender, SmsSender; Message sadrži referencu na MessageSender.", isCorrect = true),
                VisualOptionSeed(label = "Abstract Factory", text = "interfejs GUIFactory; metode createButton() i createWindow(); konkretne fabrike DarkThemeFactory i LightThemeFactory; konkretni proizvodi DarkButton, LightButton, DarkWindow, LightWindow.", isCorrect = false),
                VisualOptionSeed(label = "Prototype", text = "interfejs ili klasa Prototype; metoda clone(); konkretne klase koje implementiraju clone(); klijent dobija novi objekat kopiranjem postojećeg.", isCorrect = false),
                VisualOptionSeed(label = "Composite", text = "zajednički interfejs FileSystemItem; File i Folder implementiraju isti interfejs; Folder sadrži listu FileSystemItem; klijent koristi isti interfejs za oba.", isCorrect = false)
            ),
            aiFollowUp = "Koji deo dijagrama pokazuje razdvajanje dve nezavisne hijerarhije?",
            wave = 2,
            orderIndex = 109,
            difficulty = "medium"
        ),

        BeginnerSeedBuilders.visualMappingQuestion(
            questionId = "P2.10",
            targetPattern = "Composite",
            options = listOf(
                VisualOptionSeed(label = "Composite", text = "zajednički interfejs FileSystemItem; File i Folder implementiraju isti interfejs; Folder sadrži listu FileSystemItem; klijent koristi isti interfejs za oba.", isCorrect = true),
                VisualOptionSeed(label = "Abstract Factory", text = "interfejs GUIFactory; metode createButton() i createWindow(); konkretne fabrike DarkThemeFactory i LightThemeFactory; konkretni proizvodi DarkButton, LightButton, DarkWindow, LightWindow.", isCorrect = false),
                VisualOptionSeed(label = "Prototype", text = "interfejs ili klasa Prototype; metoda clone(); konkretne klase koje implementiraju clone(); klijent dobija novi objekat kopiranjem postojećeg.", isCorrect = false),
                VisualOptionSeed(label = "Bridge", text = "apstrakcija Message; proširene apstrakcije ShortMessage, DetailedMessage; implementor interfejs MessageSender; konkretne implementacije EmailSender, SmsSender; Message sadrži referencu na MessageSender.", isCorrect = false)
            ),
            aiFollowUp = "Koji deo dijagrama pokazuje da grupa i pojedinačni objekat mogu da se tretiraju isto?",
            wave = 2,
            orderIndex = 110,
            difficulty = "medium"
        ),

        BeginnerSeedBuilders.visualMappingQuestion(
            questionId = "P2.11",
            targetPattern = "Iterator",
            options = listOf(
                VisualOptionSeed(label = "Iterator", text = "kolekcija BookCollection; interfejs Iterator; metode hasNext() i next(); konkretan iterator prolazi kroz kolekciju bez otkrivanja njene strukture.", isCorrect = true),
                VisualOptionSeed(label = "Abstract Factory", text = "interfejs GUIFactory; metode createButton() i createWindow(); konkretne fabrike DarkThemeFactory i LightThemeFactory; konkretni proizvodi DarkButton, LightButton, DarkWindow, LightWindow.", isCorrect = false),
                VisualOptionSeed(label = "Prototype", text = "interfejs ili klasa Prototype; metoda clone(); konkretne klase koje implementiraju clone(); klijent dobija novi objekat kopiranjem postojećeg.", isCorrect = false),
                VisualOptionSeed(label = "Bridge", text = "apstrakcija Message; proširene apstrakcije ShortMessage, DetailedMessage; implementor interfejs MessageSender; konkretne implementacije EmailSender, SmsSender; Message sadrži referencu na MessageSender.", isCorrect = false)
            ),
            aiFollowUp = "Koje dve metode su glavni signal da je u pitanju Iterator?",
            wave = 2,
            orderIndex = 111,
            difficulty = "medium"
        ),

        BeginnerSeedBuilders.visualMappingQuestion(
            questionId = "P2.12",
            targetPattern = "Command",
            options = listOf(
                VisualOptionSeed(label = "Command", text = "interfejs Command; metoda execute(); konkretne komande CopyCommand, PasteCommand; Invoker poziva komandu; Receiver stvarno izvršava akciju.", isCorrect = true),
                VisualOptionSeed(label = "Abstract Factory", text = "interfejs GUIFactory; metode createButton() i createWindow(); konkretne fabrike DarkThemeFactory i LightThemeFactory; konkretni proizvodi DarkButton, LightButton, DarkWindow, LightWindow.", isCorrect = false),
                VisualOptionSeed(label = "Prototype", text = "interfejs ili klasa Prototype; metoda clone(); konkretne klase koje implementiraju clone(); klijent dobija novi objekat kopiranjem postojećeg.", isCorrect = false),
                VisualOptionSeed(label = "Bridge", text = "apstrakcija Message; proširene apstrakcije ShortMessage, DetailedMessage; implementor interfejs MessageSender; konkretne implementacije EmailSender, SmsSender; Message sadrži referencu na MessageSender.", isCorrect = false)
            ),
            aiFollowUp = "Koja je razlika između objekta koji pokreće komandu i objekta koji stvarno izvršava posao?",
            wave = 2,
            orderIndex = 112,
            difficulty = "medium"
        ),

        BeginnerSeedBuilders.visualMappingQuestion(
            questionId = "P2.13",
            targetPattern = "Template Method",
            options = listOf(
                VisualOptionSeed(label = "Template Method", text = "apstraktna klasa DocumentProcessor; metoda processDocument() definiše redosled koraka; pojedini koraci poput parse() ili format() su apstraktni; podklase implementiraju te korake.", isCorrect = true),
                VisualOptionSeed(label = "Abstract Factory", text = "interfejs GUIFactory; metode createButton() i createWindow(); konkretne fabrike DarkThemeFactory i LightThemeFactory; konkretni proizvodi DarkButton, LightButton, DarkWindow, LightWindow.", isCorrect = false),
                VisualOptionSeed(label = "Prototype", text = "interfejs ili klasa Prototype; metoda clone(); konkretne klase koje implementiraju clone(); klijent dobija novi objekat kopiranjem postojećeg.", isCorrect = false),
                VisualOptionSeed(label = "Bridge", text = "apstrakcija Message; proširene apstrakcije ShortMessage, DetailedMessage; implementor interfejs MessageSender; konkretne implementacije EmailSender, SmsSender; Message sadrži referencu na MessageSender.", isCorrect = false)
            ),
            aiFollowUp = "Šta u dijagramu pokazuje da redosled algoritma ostaje isti?",
            wave = 2,
            orderIndex = 113,
            difficulty = "medium"
        ),

        BeginnerSeedBuilders.multiStepUnderstandingQuestion(
            questionId = "P3.5",
            patternName = "Abstract Factory",
            prompt = "Odgovori na tri kratka koraka i proveri da li razumeš svrhu, karakteristike i primenu obrasca Abstract Factory.",
            steps = listOf(
                ChoiceStepSeed(title = "Korak 1 — svrha", instruction = "Koja je osnovna svrha Abstract Factory obrasca?", options = listOf("da pravi jednu jedinu instancu klase", "da kreira porodice povezanih objekata", "da obilazi kolekciju", "da čuva staro stanje objekta"), correctAnswer = "da kreira porodice povezanih objekata"),
                ChoiceStepSeed(title = "Korak 2 — karakteristika", instruction = "Koja tvrdnja najbolje opisuje Abstract Factory?", options = listOf("svaka akcija je zaseban objekat", "jedna fabrika pravi skup međusobno povezanih objekata", "objekti se kopiraju pomoću clone()", "ponašanje zavisi od unutrašnjeg stanja"), correctAnswer = "jedna fabrika pravi skup međusobno povezanih objekata"),
                ChoiceStepSeed(title = "Korak 3 — primena", instruction = "Kada je Abstract Factory dobar izbor?", options = listOf("kada želiš više stilova UI komponenti koje moraju biti međusobno usklađene", "kada želiš da dodaš funkcionalnost objektu", "kada želiš jednu instancu loggera", "kada želiš prolazak kroz listu"), correctAnswer = "kada želiš više stilova UI komponenti koje moraju biti međusobno usklađene")
            ),
            aiFollowUp = "Zašto bi bilo problematično da se dugme, meni i prozor kreiraju potpuno nepovezano?",
            wave = 2,
            orderIndex = 205,
            difficulty = "medium"
        ),

        BeginnerSeedBuilders.multiStepUnderstandingQuestion(
            questionId = "P3.6",
            patternName = "Prototype",
            prompt = "Odgovori na tri kratka koraka i proveri da li razumeš svrhu, karakteristike i primenu obrasca Prototype.",
            steps = listOf(
                ChoiceStepSeed(title = "Korak 1 — svrha", instruction = "Koja je osnovna svrha Prototype obrasca?", options = listOf("kreiranje novih objekata kloniranjem postojećih", "dinamičko dodavanje funkcionalnosti", "kontrola pristupa objektu", "odvajanje algoritma u posebne klase"), correctAnswer = "kreiranje novih objekata kloniranjem postojećih"),
                ChoiceStepSeed(title = "Korak 2 — karakteristika", instruction = "Koja osobina je tipična za Prototype?", options = listOf("privatni konstruktor", "metoda clone()", "lista observer-a", "interfejs execute()"), correctAnswer = "metoda clone()"),
                ChoiceStepSeed(title = "Korak 3 — primena", instruction = "Kada je Prototype posebno koristan?", options = listOf("kada je kreiranje objekta skupo ili komplikovano, pa je lakše kopirati postojeći", "kada želiš jedan jednostavan interfejs ka složenom sistemu", "kada želiš više pretplaćenih objekata", "kada menjaš ponašanje objekta po stanju"), correctAnswer = "kada je kreiranje objekta skupo ili komplikovano, pa je lakše kopirati postojeći")
            ),
            aiFollowUp = "Zašto je ponekad kopiranje postojećeg objekta efikasnije od pravljenja novog od nule?",
            wave = 2,
            orderIndex = 206,
            difficulty = "medium"
        ),

        BeginnerSeedBuilders.multiStepUnderstandingQuestion(
            questionId = "P3.7",
            patternName = "Composite",
            prompt = "Odgovori na tri kratka koraka i proveri da li razumeš svrhu, karakteristike i primenu obrasca Composite.",
            steps = listOf(
                ChoiceStepSeed(title = "Korak 1 — svrha", instruction = "Šta Composite prvenstveno omogućava?", options = listOf("da se jedan objekat ponaša različito po stanju", "da se pojedinačni objekti i grupe objekata tretiraju isto", "da se algoritam menja u letu", "da se kreira porodica proizvoda"), correctAnswer = "da se pojedinačni objekti i grupe objekata tretiraju isto"),
                ChoiceStepSeed(title = "Korak 2 — karakteristika", instruction = "Koja osobina je tipična za Composite?", options = listOf("jedan objekat sadrži listu objekata istog zajedničkog tipa", "svaki objekat ima clone()", "postoji metoda execute()", "koristi se privatni konstruktor"), correctAnswer = "jedan objekat sadrži listu objekata istog zajedničkog tipa"),
                ChoiceStepSeed(title = "Korak 3 — primena", instruction = "Gde je Composite dobar izbor?", options = listOf("u hijerarhijama poput foldera i fajlova", "u izboru algoritma za popust", "u kreiranju jedne instance konfiguracije", "u povezivanju nekompatibilnih biblioteka"), correctAnswer = "u hijerarhijama poput foldera i fajlova")
            ),
            aiFollowUp = "Zašto je važno da klijent ne mora stalno da razlikuje da li radi sa jednim objektom ili grupom?",
            wave = 2,
            orderIndex = 207,
            difficulty = "medium"
        ),

        BeginnerSeedBuilders.multiStepUnderstandingQuestion(
            questionId = "P3.8",
            patternName = "Command",
            prompt = "Odgovori na tri kratka koraka i proveri da li razumeš svrhu, karakteristike i primenu obrasca Command.",
            steps = listOf(
                ChoiceStepSeed(title = "Korak 1 — svrha", instruction = "Koja je osnovna svrha Command obrasca?", options = listOf("da zahtev pretvori u objekat", "da čuva prethodno stanje objekta", "da pravi familije objekata", "da povezuje dve hijerarhije"), correctAnswer = "da zahtev pretvori u objekat"),
                ChoiceStepSeed(title = "Korak 2 — karakteristika", instruction = "Šta je tipično za Command?", options = listOf("postoje komande sa metodom execute()", "postoji metoda clone()", "postoji lista children elemenata", "postoji getInstance()"), correctAnswer = "postoje komande sa metodom execute()"),
                ChoiceStepSeed(title = "Korak 3 — primena", instruction = "Kada se Command često koristi?", options = listOf("kada želiš undo, red čekanja akcija ili mapiranje dugmadi na akcije", "kada želiš više tema korisničkog interfejsa", "kada želiš odvojiti apstrakciju od implementacije", "kada želiš prolazak kroz kolekciju"), correctAnswer = "kada želiš undo, red čekanja akcija ili mapiranje dugmadi na akcije")
            ),
            aiFollowUp = "Zašto je korisno da akcija može da se čuva i ponovo izvršava?",
            wave = 2,
            orderIndex = 208,
            difficulty = "medium"
        ),

        BeginnerSeedBuilders.multiStepUnderstandingQuestion(
            questionId = "P3.9",
            patternName = "Template Method",
            prompt = "Odgovori na tri kratka koraka i proveri da li razumeš svrhu, karakteristike i primenu obrasca Template Method.",
            steps = listOf(
                ChoiceStepSeed(title = "Korak 1 — svrha", instruction = "Koja je osnovna svrha Template Method obrasca?", options = listOf("da definiše kostur algoritma, a neke korake prepusti podklasama", "da kreira kopiju objekta", "da doda funkcionalnost objektu", "da omogući obaveštavanje više objekata"), correctAnswer = "da definiše kostur algoritma, a neke korake prepusti podklasama"),
                ChoiceStepSeed(title = "Korak 2 — karakteristika", instruction = "Šta je tipično za Template Method?", options = listOf("osnovna klasa definiše redosled koraka", "postoji više zamjenjivih algoritama", "objekat menja ponašanje po stanju", "postoji posrednik za komunikaciju"), correctAnswer = "osnovna klasa definiše redosled koraka"),
                ChoiceStepSeed(title = "Korak 3 — primena", instruction = "Kada je Template Method dobar izbor?", options = listOf("kada više procesa deli istu strukturu, ali se razlikuje u nekim koracima", "kada želiš da korisnik bira jedan od više algoritama u runtime-u", "kada želiš da sačuvaš snapshot stanja", "kada želiš da sakriješ složen sistem"), correctAnswer = "kada više procesa deli istu strukturu, ali se razlikuje u nekim koracima")
            ),
            aiFollowUp = "Šta ostaje zajedničko svim podklasama kod Template Method obrasca?",
            wave = 2,
            orderIndex = 209,
            difficulty = "medium"
        ),

        BeginnerSeedBuilders.patternComparisonQuestion(
            questionId = "P4.5",
            title = "Razlikovanje obrazaca — Pitanje P4.5 Abstract Factory vs Factory Method",
            scenario = "Sistem treba da podrži dve kompletne teme interfejsa. Za svaku temu treba kreirati odgovarajuće dugme, checkbox i prozor tako da svi elementi budu međusobno usklađeni.",
            steps = listOf(
                ChoiceStepSeed(title = "Korak 1 — izbor obrasca", instruction = "Koji od ponuđenih obrazaca bolje odgovara scenariju?", options = listOf("Abstract Factory", "Factory Method"), correctAnswer = "Abstract Factory"),
                ChoiceStepSeed(title = "Korak 2 — obrazloženje izbora", instruction = "Zašto je Abstract Factory pogodniji?", options = listOf("zato što pravi celu porodicu povezanih objekata", "zato što pravi kopiju postojećeg objekta", "zato što dodaje funkcionalnost u runtime-u", "zato što obezbeđuje samo jednu instancu"), correctAnswer = "zato što pravi celu porodicu povezanih objekata"),
                ChoiceStepSeed(title = "Korak 3 — razlikovanje obrazaca", instruction = "Kada bi Factory Method bio pogodniji?", options = listOf("kada biraš koji jedan konkretan proizvod treba kreirati", "kada imaš hijerarhiju stabla", "kada želiš obilazak kolekcije", "kada želiš snapshot stanja"), correctAnswer = "kada biraš koji jedan konkretan proizvod treba kreirati")
            ),
            aiFollowUp = "U jednoj rečenici objasni razliku između kreiranja jednog proizvoda i cele porodice proizvoda.",
            wave = 2,
            orderIndex = 305,
            difficulty = "medium"
        ),

        BeginnerSeedBuilders.patternComparisonQuestion(
            questionId = "P4.6",
            title = "Razlikovanje obrazaca — Pitanje P4.6 Prototype vs Builder",
            scenario = "Imaš već podešen objekat “EnemyBoss” sa mnogo atributa. Želiš brzo da napraviš novu sličnu verziju tog objekta i da promeniš samo nekoliko osobina.",
            steps = listOf(
                ChoiceStepSeed(title = "Korak 1 — izbor obrasca", instruction = "Koji od ponuđenih obrazaca bolje odgovara scenariju?", options = listOf("Prototype", "Builder"), correctAnswer = "Prototype"),
                ChoiceStepSeed(title = "Korak 2 — obrazloženje izbora", instruction = "Zašto je Prototype pogodniji?", options = listOf("zato što koristi kopiranje postojećeg objekta", "zato što sastavlja objekat korak po korak", "zato što uvodi jednostavan interfejs", "zato što omogućava pretplatu na promene"), correctAnswer = "zato što koristi kopiranje postojećeg objekta"),
                ChoiceStepSeed(title = "Korak 3 — razlikovanje obrazaca", instruction = "Kada bi Builder bio pogodniji?", options = listOf("kada se složen objekat postepeno sastavlja iz delova", "kada želiš jednu instancu", "kada želiš akciju kao objekat", "kada želiš isti tretman za grupu i pojedinačni objekat"), correctAnswer = "kada se složen objekat postepeno sastavlja iz delova")
            ),
            aiFollowUp = "Zašto kopiranje i postepena konstrukcija rešavaju dva različita problema?",
            wave = 2,
            orderIndex = 306,
            difficulty = "medium"
        ),

        BeginnerSeedBuilders.patternComparisonQuestion(
            questionId = "P4.7",
            title = "Razlikovanje obrazaca — Pitanje P4.7 Bridge vs Adapter",
            scenario = "Imaš dve dimenzije promena: vrste izveštaja i načine prikaza tih izveštaja. Želiš da obe dimenzije možeš menjati nezavisno.",
            steps = listOf(
                ChoiceStepSeed(title = "Korak 1 — izbor obrasca", instruction = "Koji od ponuđenih obrazaca bolje odgovara scenariju?", options = listOf("Bridge", "Adapter"), correctAnswer = "Bridge"),
                ChoiceStepSeed(title = "Korak 2 — obrazloženje izbora", instruction = "Zašto je Bridge pogodniji?", options = listOf("zato što prevodi nekompatibilan interfejs", "zato što razdvaja apstrakciju i implementaciju u dve hijerarhije", "zato što ograničava broj instanci", "zato što dodaje odgovornosti objektu"), correctAnswer = "zato što razdvaja apstrakciju i implementaciju u dve hijerarhije"),
                ChoiceStepSeed(title = "Korak 3 — razlikovanje obrazaca", instruction = "Kada bi Adapter bio pogodniji?", options = listOf("kada želiš uklopiti postojeću klasu sa drugačijim interfejsom", "kada želiš porodicu proizvoda", "kada želiš prolazak kroz listu", "kada želiš isti algoritamski kostur"), correctAnswer = "kada želiš uklopiti postojeću klasu sa drugačijim interfejsom")
            ),
            aiFollowUp = "Kako bi najkraće razlikovao Bridge i Adapter?",
            wave = 2,
            orderIndex = 307,
            difficulty = "medium"
        ),

        BeginnerSeedBuilders.patternComparisonQuestion(
            questionId = "P4.8",
            title = "Razlikovanje obrazaca — Pitanje P4.8 Iterator vs Composite",
            scenario = "U hijerarhiji foldera i fajlova želiš da klijent može isto da radi sa jednim fajlom i sa celim folderom.",
            steps = listOf(
                ChoiceStepSeed(title = "Korak 1 — izbor obrasca", instruction = "Koji od ponuđenih obrazaca bolje odgovara scenariju?", options = listOf("Iterator", "Composite"), correctAnswer = "Composite"),
                ChoiceStepSeed(title = "Korak 2 — obrazloženje izbora", instruction = "Zašto je Composite pogodniji?", options = listOf("zato što omogućava isti tretman za pojedinačne i grupne objekte", "zato što omogućava prolazak kroz kolekciju redom", "zato što čuva staro stanje", "zato što akcije pretvara u objekte"), correctAnswer = "zato što omogućava isti tretman za pojedinačne i grupne objekte"),
                ChoiceStepSeed(title = "Korak 3 — razlikovanje obrazaca", instruction = "Kada bi Iterator bio pogodniji?", options = listOf("kada želiš sekvencijalno prolaziti kroz elemente kolekcije", "kada želiš proširivati sistem novim komponentama", "kada želiš više tipova proizvoda", "kada želiš odvojiti implementaciju od apstrakcije"), correctAnswer = "kada želiš sekvencijalno prolaziti kroz elemente kolekcije")
            ),
            aiFollowUp = "Šta je fokus Composite-a, a šta Iterator-a?",
            wave = 2,
            orderIndex = 308,
            difficulty = "medium"
        ),

        BeginnerSeedBuilders.patternComparisonQuestion(
            questionId = "P4.9",
            title = "Razlikovanje obrazaca — Pitanje P4.9 Strategy vs Template Method",
            scenario = "Imaš proces obrade fajla koji uvek ide redom: učitaj, validiraj, obradi, sačuvaj. Neke korake želeš da različite podklase implementiraju drugačije, ali redosled mora ostati isti.",
            steps = listOf(
                ChoiceStepSeed(title = "Korak 1 — izbor obrasca", instruction = "Koji od ponuđenih obrazaca bolje odgovara scenariju?", options = listOf("Strategy", "Template Method"), correctAnswer = "Template Method"),
                ChoiceStepSeed(title = "Korak 2 — obrazloženje izbora", instruction = "Zašto je Template Method pogodniji?", options = listOf("zato što redosled algoritma ostaje isti, a menjaju se pojedini koraci", "zato što korisnik bira između više potpuno odvojenih algoritama", "zato što se koristi centralni posrednik", "zato što se pravi kopija objekta"), correctAnswer = "zato što redosled algoritma ostaje isti, a menjaju se pojedini koraci"),
                ChoiceStepSeed(title = "Korak 3 — razlikovanje obrazaca", instruction = "Kada bi Strategy bio pogodniji?", options = listOf("kada želiš da u runtime-u biraš između više celih algoritama", "kada želiš da svi koriste isti kostur algoritma", "kada želiš da grupa i pojedinačni objekat izgledaju isto", "kada želiš da obilaziš listu"), correctAnswer = "kada želiš da u runtime-u biraš između više celih algoritama")
            ),
            aiFollowUp = "U jednoj kratkoj rečenici objasni razliku između Template Method i Strategy.",
            wave = 2,
            orderIndex = 309,
            difficulty = "medium"
        )
    )
}
