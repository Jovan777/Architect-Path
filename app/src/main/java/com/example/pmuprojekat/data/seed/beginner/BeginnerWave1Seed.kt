package com.example.pmuprojekat.data.seed.beginner

import com.example.pmuprojekat.data.seed.SeedQuestion

object BeginnerWave1Seed {
    val questions: List<SeedQuestion> = listOf(
        BeginnerSeedBuilders.patternRecognitionQuestion(
            questionId = "P1.1",
            title = "Jedna instanca za ceo program",
            prompt = "U aplikaciji postoji klasa za logovanje i potrebno je da tokom rada celog sistema postoji samo jedna njena instanca kojoj svi delovi sistema pristupaju. Koji obrazac projektovanja najbolje odgovara ovoj situaciji?",
            options = listOf("Builder", "Singleton", "Prototype", "Facade"),
            correctAnswer = "Singleton",
            aiFollowUp = "Zašto je važno da u ovom slučaju postoji samo jedna instanca?",
            wave = 1,
            orderIndex = 1,
            difficulty = "easy"
        ),

        BeginnerSeedBuilders.patternRecognitionQuestion(
            questionId = "P1.2",
            title = "Kreiranje objekata bez direktnog navođenja konkretne klase",
            prompt = "Aplikacija treba da kreira različite tipove notifikacija, ali glavni kod ne treba direktno da zna da li se pravi EmailNotification, SmsNotification ili PushNotification. Koji obrazac je najpogodniji?",
            options = listOf("Factory Method", "Observer", "Proxy", "State"),
            correctAnswer = "Factory Method",
            aiFollowUp = "Koja je prednost toga što glavni kod ne zna konkretnu klasu objekta koji se kreira?",
            wave = 1,
            orderIndex = 2,
            difficulty = "easy"
        ),

        BeginnerSeedBuilders.patternRecognitionQuestion(
            questionId = "P1.3",
            title = "Postepeno sastavljanje složenog objekta",
            prompt = "Potrebno je napraviti složen objekat „Computer“ koji može imati procesor, RAM, disk, grafičku kartu i dodatne opcije, pri čemu se različite verzije računara sastavljaju korak po korak. Koji obrazac najbolje odgovara?",
            options = listOf("Builder", "Singleton", "Adapter", "Observer"),
            correctAnswer = "Builder",
            aiFollowUp = "Zašto je Builder pogodniji od jednog ogromnog konstruktora sa mnogo parametara?",
            wave = 1,
            orderIndex = 3,
            difficulty = "easy"
        ),

        BeginnerSeedBuilders.patternRecognitionQuestion(
            questionId = "P1.4",
            title = "Povezivanje nekompatibilnih interfejsa",
            prompt = "Nova biblioteka za plaćanje ima drugačiji interfejs od onog koji koristi tvoja aplikacija, ali želiš da je uklopiš bez menjanja ostatka sistema. Koji obrazac projektovanja koristiš?",
            options = listOf("Adapter", "Facade", "Strategy", "Prototype"),
            correctAnswer = "Adapter",
            aiFollowUp = "Šta Adapter radi između postojećeg sistema i nove biblioteke?",
            wave = 1,
            orderIndex = 4,
            difficulty = "easy"
        ),

        BeginnerSeedBuilders.patternRecognitionQuestion(
            questionId = "P1.5",
            title = "Dodavanje funkcionalnosti objektu u toku rada",
            prompt = "Potrebno je da postojećem objektu poruke dodaš šifrovanje, kompresiju ili logovanje, bez menjanja njegove osnovne klase i bez pravljenja velikog broja naslednika. Koji obrazac je najpogodniji?",
            options = listOf("Decorator", "Proxy", "Template Method", "Composite"),
            correctAnswer = "Decorator",
            aiFollowUp = "Zašto je ovo bolje od pravljenja mnogo podklasa?",
            wave = 1,
            orderIndex = 5,
            difficulty = "easy"
        ),

        BeginnerSeedBuilders.patternRecognitionQuestion(
            questionId = "P1.6",
            title = "Jednostavan ulaz u složen podsistem",
            prompt = "Korisnik treba da pokrene kompleksan sistem za obradu videa, ali želiš da mu izložiš samo jednu jednostavnu klasu sa metodom startProcessing(). Koji obrazac najbolje odgovara?",
            options = listOf("Facade", "Bridge", "Iterator", "State"),
            correctAnswer = "Facade",
            aiFollowUp = "Koji problem Facade rešava sa stanovišta korisnika sistema?",
            wave = 1,
            orderIndex = 6,
            difficulty = "easy"
        ),

        BeginnerSeedBuilders.patternRecognitionQuestion(
            questionId = "P1.7",
            title = "Kontrola pristupa stvarnom objektu",
            prompt = "U sistemu postoji objekat koji učitava velike fajlove. Želiš da se pravi objekat učita tek kada je stvarno potreban i da pristup njemu bude kontrolisan preko pomoćnog objekta. Koji obrazac odgovara?",
            options = listOf("Proxy", "Decorator", "Facade", "Observer"),
            correctAnswer = "Proxy",
            aiFollowUp = "Po čemu se Proxy razlikuje od Decorator obrasca?",
            wave = 1,
            orderIndex = 7,
            difficulty = "easy"
        ),

        BeginnerSeedBuilders.patternRecognitionQuestion(
            questionId = "P1.8",
            title = "Automatsko obaveštavanje više objekata",
            prompt = "Kada se promeni cena proizvoda, više delova sistema treba automatski da dobije obaveštenje: korisnički interfejs, servis za email i servis za analitiku. Koji obrazac najbolje odgovara?",
            options = listOf("Observer", "Strategy", "Command", "Adapter"),
            correctAnswer = "Observer",
            aiFollowUp = "Ko je u ovom obrascu „subject“, a ko su „observers“?",
            wave = 1,
            orderIndex = 8,
            difficulty = "easy"
        ),

        BeginnerSeedBuilders.patternRecognitionQuestion(
            questionId = "P1.9",
            title = "Zamena algoritma u toku rada",
            prompt = "Aplikacija za plaćanje treba da omogući izbor različitih načina obračuna popusta: bez popusta, studentski popust, sezonski popust ili premium popust. Algoritam treba lako menjati u toku rada. Koji obrazac odgovara?",
            options = listOf("Strategy", "State", "Singleton", "Memento"),
            correctAnswer = "Strategy",
            aiFollowUp = "Zašto je korisno da svaki način obračuna bude izdvojen u posebnu klasu?",
            wave = 1,
            orderIndex = 9,
            difficulty = "easy"
        ),

        BeginnerSeedBuilders.patternRecognitionQuestion(
            questionId = "P1.10",
            title = "Promena ponašanja u zavisnosti od stanja",
            prompt = "Objekat narudžbine se ponaša drugačije kada je u stanju “Kreirana”, drugačije kada je “Plaćena”, a drugačije kada je “Poslata”. Koji obrazac projektovanja je najpogodniji?",
            options = listOf("State", "Strategy", "Observer", "Builder"),
            correctAnswer = "State",
            aiFollowUp = "Zašto ovo nije isto što i običan niz if-else grana?",
            wave = 1,
            orderIndex = 10,
            difficulty = "easy"
        ),

        BeginnerSeedBuilders.visualMappingQuestion(
            questionId = "P2.1",
            targetPattern = "Singleton",
            options = listOf(
                VisualOptionSeed(label = "Singleton", text = "jedna klasa Logger; privatni konstruktor; statičko polje instance; javna metoda getInstance(); svi klijenti pristupaju istoj instanci.", isCorrect = true),
                VisualOptionSeed(label = "Adapter", text = "klijent očekuje interfejs PaymentProcessor; postoji klasa OldPaymentGateway; klasa PaymentAdapter implementira PaymentProcessor; adapter interno koristi OldPaymentGateway.", isCorrect = false),
                VisualOptionSeed(label = "Observer", text = "Subject čuva listu Observer; metode attach(), detach(), notify(); više konkretnih observer klasa; kada se stanje promeni, subject obaveštava sve posmatrače.", isCorrect = false),
                VisualOptionSeed(label = "Decorator", text = "zajednički interfejs Message; osnovna klasa SimpleMessage; apstraktni MessageDecorator koji sadrži referencu na Message; konkretni dekoratori poput EncryptedMessageDecorator i CompressedMessageDecorator.", isCorrect = false)
            ),
            aiFollowUp = "Koji element dijagrama ti je bio glavni signal da je u pitanju Singleton?",
            wave = 1,
            orderIndex = 101,
            difficulty = "easy"
        ),

        BeginnerSeedBuilders.visualMappingQuestion(
            questionId = "P2.2",
            targetPattern = "Adapter",
            options = listOf(
                VisualOptionSeed(label = "Adapter", text = "klijent očekuje interfejs PaymentProcessor; postoji klasa OldPaymentGateway; klasa PaymentAdapter implementira PaymentProcessor; adapter interno koristi OldPaymentGateway.", isCorrect = true),
                VisualOptionSeed(label = "Singleton", text = "jedna klasa Logger; privatni konstruktor; statičko polje instance; javna metoda getInstance(); svi klijenti pristupaju istoj instanci.", isCorrect = false),
                VisualOptionSeed(label = "Observer", text = "Subject čuva listu Observer; metode attach(), detach(), notify(); više konkretnih observer klasa; kada se stanje promeni, subject obaveštava sve posmatrače.", isCorrect = false),
                VisualOptionSeed(label = "Decorator", text = "zajednički interfejs Message; osnovna klasa SimpleMessage; apstraktni MessageDecorator koji sadrži referencu na Message; konkretni dekoratori poput EncryptedMessageDecorator i CompressedMessageDecorator.", isCorrect = false)
            ),
            aiFollowUp = "Koja klasa u dijagramu služi kao „prevodilac“ između dva interfejsa?",
            wave = 1,
            orderIndex = 102,
            difficulty = "easy"
        ),

        BeginnerSeedBuilders.visualMappingQuestion(
            questionId = "P2.3",
            targetPattern = "Observer",
            options = listOf(
                VisualOptionSeed(label = "Observer", text = "Subject čuva listu Observer; metode attach(), detach(), notify(); više konkretnih observer klasa; kada se stanje promeni, subject obaveštava sve posmatrače.", isCorrect = true),
                VisualOptionSeed(label = "Singleton", text = "jedna klasa Logger; privatni konstruktor; statičko polje instance; javna metoda getInstance(); svi klijenti pristupaju istoj instanci.", isCorrect = false),
                VisualOptionSeed(label = "Adapter", text = "klijent očekuje interfejs PaymentProcessor; postoji klasa OldPaymentGateway; klasa PaymentAdapter implementira PaymentProcessor; adapter interno koristi OldPaymentGateway.", isCorrect = false),
                VisualOptionSeed(label = "Decorator", text = "zajednički interfejs Message; osnovna klasa SimpleMessage; apstraktni MessageDecorator koji sadrži referencu na Message; konkretni dekoratori poput EncryptedMessageDecorator i CompressedMessageDecorator.", isCorrect = false)
            ),
            aiFollowUp = "Zašto prisustvo liste pretplaćenih objekata ukazuje na Observer?",
            wave = 1,
            orderIndex = 103,
            difficulty = "easy"
        ),

        BeginnerSeedBuilders.visualMappingQuestion(
            questionId = "P2.4",
            targetPattern = "Decorator",
            options = listOf(
                VisualOptionSeed(label = "Decorator", text = "zajednički interfejs Message; osnovna klasa SimpleMessage; apstraktni MessageDecorator koji sadrži referencu na Message; konkretni dekoratori poput EncryptedMessageDecorator i CompressedMessageDecorator.", isCorrect = true),
                VisualOptionSeed(label = "Singleton", text = "jedna klasa Logger; privatni konstruktor; statičko polje instance; javna metoda getInstance(); svi klijenti pristupaju istoj instanci.", isCorrect = false),
                VisualOptionSeed(label = "Adapter", text = "klijent očekuje interfejs PaymentProcessor; postoji klasa OldPaymentGateway; klasa PaymentAdapter implementira PaymentProcessor; adapter interno koristi OldPaymentGateway.", isCorrect = false),
                VisualOptionSeed(label = "Observer", text = "Subject čuva listu Observer; metode attach(), detach(), notify(); više konkretnih observer klasa; kada se stanje promeni, subject obaveštava sve posmatrače.", isCorrect = false)
            ),
            aiFollowUp = "Koji deo dijagrama pokazuje da se funkcionalnost dodaje dinamički?",
            wave = 1,
            orderIndex = 104,
            difficulty = "easy"
        ),

        BeginnerSeedBuilders.visualMappingQuestion(
            questionId = "P2.5",
            targetPattern = "Strategy",
            options = listOf(
                VisualOptionSeed(label = "Strategy", text = "interfejs DiscountStrategy; više implementacija: NoDiscount, StudentDiscount, PremiumDiscount; klasa CheckoutContext čuva referencu na DiscountStrategy; context delegira obračun strategiji.", isCorrect = true),
                VisualOptionSeed(label = "Singleton", text = "jedna klasa Logger; privatni konstruktor; statičko polje instance; javna metoda getInstance(); svi klijenti pristupaju istoj instanci.", isCorrect = false),
                VisualOptionSeed(label = "Adapter", text = "klijent očekuje interfejs PaymentProcessor; postoji klasa OldPaymentGateway; klasa PaymentAdapter implementira PaymentProcessor; adapter interno koristi OldPaymentGateway.", isCorrect = false),
                VisualOptionSeed(label = "Observer", text = "Subject čuva listu Observer; metode attach(), detach(), notify(); više konkretnih observer klasa; kada se stanje promeni, subject obaveštava sve posmatrače.", isCorrect = false)
            ),
            aiFollowUp = "Koji deo dijagrama pokazuje da algoritam može biti zamenjen?",
            wave = 1,
            orderIndex = 105,
            difficulty = "easy"
        ),

        BeginnerSeedBuilders.visualMappingQuestion(
            questionId = "P2.6",
            targetPattern = "Facade",
            options = listOf(
                VisualOptionSeed(label = "Facade", text = "više podsistemskih klasa: VideoDecoder, AudioProcessor, SubtitleLoader; jedna klasa VideoFacade; klijent komunicira samo sa VideoFacade.", isCorrect = true),
                VisualOptionSeed(label = "Singleton", text = "jedna klasa Logger; privatni konstruktor; statičko polje instance; javna metoda getInstance(); svi klijenti pristupaju istoj instanci.", isCorrect = false),
                VisualOptionSeed(label = "Adapter", text = "klijent očekuje interfejs PaymentProcessor; postoji klasa OldPaymentGateway; klasa PaymentAdapter implementira PaymentProcessor; adapter interno koristi OldPaymentGateway.", isCorrect = false),
                VisualOptionSeed(label = "Observer", text = "Subject čuva listu Observer; metode attach(), detach(), notify(); više konkretnih observer klasa; kada se stanje promeni, subject obaveštava sve posmatrače.", isCorrect = false)
            ),
            aiFollowUp = "Šta u dijagramu pokazuje da korisnik ne mora direktno da radi sa kompleksnim podsistemom?",
            wave = 1,
            orderIndex = 106,
            difficulty = "easy"
        ),

        BeginnerSeedBuilders.multiStepUnderstandingQuestion(
            questionId = "P3.1",
            patternName = "Singleton",
            prompt = "Odgovori na tri kratka koraka i proveri da li razumeš svrhu, karakteristike i primenu obrasca Singleton.",
            steps = listOf(
                ChoiceStepSeed(title = "Korak 1 — svrha", instruction = "Koja je osnovna svrha Singleton obrasca?", options = listOf("da kreira više sličnih objekata", "da obezbedi samo jednu instancu klase", "da omogući promenu algoritma", "da spoji nekompatibilne interfejse"), correctAnswer = "da obezbedi samo jednu instancu klase"),
                ChoiceStepSeed(title = "Korak 2 — karakteristika", instruction = "Koja karakteristika je tipična za Singleton?", options = listOf("javni konstruktor i više instanci", "privatni konstruktor i globalni pristup instanci", "lista observer objekata", "mogućnost ulančavanja dekoratora"), correctAnswer = "privatni konstruktor i globalni pristup instanci"),
                ChoiceStepSeed(title = "Korak 3 — primena", instruction = "U kojoj situaciji je Singleton najpogodniji?", options = listOf("kada želiš više varijanti istog proizvoda", "kada želiš samo jedan logger ili config manager u aplikaciji", "kada želiš da dodaš funkcionalnost poruci", "kada želiš da sakriješ složen podsistem"), correctAnswer = "kada želiš samo jedan logger ili config manager u aplikaciji")
            ),
            aiFollowUp = "Zašto bi više instanci loggera ili config managera moglo da bude problem?",
            wave = 1,
            orderIndex = 201,
            difficulty = "easy"
        ),

        BeginnerSeedBuilders.multiStepUnderstandingQuestion(
            questionId = "P3.2",
            patternName = "Builder",
            prompt = "Odgovori na tri kratka koraka i proveri da li razumeš svrhu, karakteristike i primenu obrasca Builder.",
            steps = listOf(
                ChoiceStepSeed(title = "Korak 1 — svrha", instruction = "Koja je osnovna svrha Builder obrasca?", options = listOf("da jedan objekat obaveštava druge", "da složen objekat bude sastavljen korak po korak", "da ograniči broj instanci klase", "da omogući obilazak kolekcije"), correctAnswer = "da složen objekat bude sastavljen korak po korak"),
                ChoiceStepSeed(title = "Korak 2 — karakteristika", instruction = "Koja tvrdnja najbolje opisuje Builder?", options = listOf("isti proces može praviti različite verzije objekta", "koristi listu pretplatnika", "sakriva pravi objekat iza posrednika", "menja ponašanje objekta prema stanju"), correctAnswer = "isti proces može praviti različite verzije objekta"),
                ChoiceStepSeed(title = "Korak 3 — primena", instruction = "U kojoj situaciji je Builder najkorisniji?", options = listOf("kada praviš objekat sa mnogo opcionalnih delova", "kada hoćeš da obavestiš više objekata", "kada želiš da prevedeš interfejs", "kada želiš da kontrolišeš pristup objektu"), correctAnswer = "kada praviš objekat sa mnogo opcionalnih delova")
            ),
            aiFollowUp = "Zašto Builder olakšava rad sa složenim objektima koji imaju mnogo opcija?",
            wave = 1,
            orderIndex = 202,
            difficulty = "easy"
        ),

        BeginnerSeedBuilders.multiStepUnderstandingQuestion(
            questionId = "P3.3",
            patternName = "Observer",
            prompt = "Odgovori na tri kratka koraka i proveri da li razumeš svrhu, karakteristike i primenu obrasca Observer.",
            steps = listOf(
                ChoiceStepSeed(title = "Korak 1 — svrha", instruction = "Šta Observer prvenstveno omogućava?", options = listOf("čuvanje prethodnog stanja objekta", "automatsko obaveštavanje više objekata kada se stanje promeni", "pravljenje samo jedne instance", "gradnju složenog objekta"), correctAnswer = "automatsko obaveštavanje više objekata kada se stanje promeni"),
                ChoiceStepSeed(title = "Korak 2 — karakteristika", instruction = "Koja osobina je tipična za Observer?", options = listOf("centralni konstruktor objekata", "subject čuva listu observer-a", "jedan objekat menja klasu u runtime-u", "koristi kloniranje objekta"), correctAnswer = "subject čuva listu observer-a"),
                ChoiceStepSeed(title = "Korak 3 — primena", instruction = "Gde je Observer dobar izbor?", options = listOf("kod sistema za pretplatu na promene cene ili notifikacije", "kod kreiranja složenih konfiguracija", "kod pravljenja jedne jedine baze konekcije", "kod povezivanja dve nekompatibilne biblioteke"), correctAnswer = "kod sistema za pretplatu na promene cene ili notifikacije")
            ),
            aiFollowUp = "Zašto je Observer dobar kada više delova sistema reaguje na jednu promenu?",
            wave = 1,
            orderIndex = 203,
            difficulty = "easy"
        ),

        BeginnerSeedBuilders.multiStepUnderstandingQuestion(
            questionId = "P3.4",
            patternName = "Strategy",
            prompt = "Odgovori na tri kratka koraka i proveri da li razumeš svrhu, karakteristike i primenu obrasca Strategy.",
            steps = listOf(
                ChoiceStepSeed(title = "Korak 1 — svrha", instruction = "Koja je osnovna svrha Strategy obrasca?", options = listOf("da algoritam izdvoji u posebne zamjenjive klase", "da sačuva stanje objekta", "da sakrije složen podsistem", "da doda dodatne odgovornosti objektu"), correctAnswer = "da algoritam izdvoji u posebne zamjenjive klase"),
                ChoiceStepSeed(title = "Korak 2 — karakteristika", instruction = "Šta je tipično za Strategy?", options = listOf("jedna klasa ima privatni konstruktor", "postoji više algoritama koji imaju zajednički interfejs", "objekti se organizuju u stablo", "koristi se jedan posrednik za komunikaciju"), correctAnswer = "postoji više algoritama koji imaju zajednički interfejs"),
                ChoiceStepSeed(title = "Korak 3 — primena", instruction = "Kada se Strategy često koristi?", options = listOf("kada postoji više načina obračuna, sortiranja ili validacije", "kada postoji samo jedna instanca klase", "kada je potrebno sačuvati staro stanje", "kada treba pojednostaviti složen API"), correctAnswer = "kada postoji više načina obračuna, sortiranja ili validacije")
            ),
            aiFollowUp = "Zašto je bolje menjati strategiju nego stalno širiti jednu klasu novim granama logike?",
            wave = 1,
            orderIndex = 204,
            difficulty = "easy"
        ),

        BeginnerSeedBuilders.patternComparisonQuestion(
            questionId = "P4.1",
            title = "Razlikovanje obrazaca — Strategy vs State",
            scenario = "Aplikacija za dostavu menja ponašanje narudžbine u zavisnosti od toga da li je narudžbina kreirana, plaćena, poslata ili otkazana.",
            steps = listOf(
                ChoiceStepSeed(title = "Korak 1 — izbor obrasca", instruction = "Koji od ponuđenih obrazaca bolje odgovara scenariju?", options = listOf("Strategy", "State"), correctAnswer = "State"),
                ChoiceStepSeed(title = "Korak 2 — obrazloženje izbora", instruction = "Zašto je State pogodniji od Strategy u ovoj situaciji?", options = listOf("zato što sistem bira između različitih nezavisnih algoritama popusta", "zato što se ponašanje objekta menja u zavisnosti od njegovog unutrašnjeg stanja", "zato što treba da postoji samo jedna instanca", "zato što se želi jednostavan interfejs ka podsistemu"), correctAnswer = "zato što se ponašanje objekta menja u zavisnosti od njegovog unutrašnjeg stanja"),
                ChoiceStepSeed(title = "Korak 3 — razlikovanje obrazaca", instruction = "Šta bi više ukazivalo na Strategy nego na State?", options = listOf("objekt ima životni ciklus i menja ponašanje kroz stanja", "korisnik ili sistem bira jedan od više algoritama za isti zadatak", "postoji lista pretplatnika", "postoji privatni konstruktor"), correctAnswer = "korisnik ili sistem bira jedan od više algoritama za isti zadatak")
            ),
            aiFollowUp = "Objasni jednom rečenicom glavnu razliku između State i Strategy.",
            wave = 1,
            orderIndex = 301,
            difficulty = "easy"
        ),

        BeginnerSeedBuilders.patternComparisonQuestion(
            questionId = "P4.2",
            title = "Razlikovanje obrazaca — Decorator vs Proxy",
            scenario = "Želiš da objektu za slanje poruka dodaš enkripciju i kompresiju bez menjanja njegove osnovne klase.",
            steps = listOf(
                ChoiceStepSeed(title = "Korak 1 — izbor obrasca", instruction = "Koji od ponuđenih obrazaca bolje odgovara scenariju?", options = listOf("Decorator", "Proxy"), correctAnswer = "Decorator"),
                ChoiceStepSeed(title = "Korak 2 — obrazloženje izbora", instruction = "Zašto je Decorator pogodniji?", options = listOf("zato što kontroliše pristup objektu zbog bezbednosti", "zato što dodaje novu funkcionalnost postojećem objektu dinamički", "zato što omogućava samo jednu instancu", "zato što prevodi interfejs"), correctAnswer = "zato što dodaje novu funkcionalnost postojećem objektu dinamički"),
                ChoiceStepSeed(title = "Korak 3 — razlikovanje obrazaca", instruction = "U kojoj situaciji bi Proxy bio pogodniji od Decorator-a?", options = listOf("kada želiš da više objekata reaguje na promenu", "kada želiš da odložiš učitavanje pravog objekta ili kontrolišeš pristup", "kada želiš više algoritama", "kada želiš gradnju složenog objekta"), correctAnswer = "kada želiš da odložiš učitavanje pravog objekta ili kontrolišeš pristup")
            ),
            aiFollowUp = "Kako bi ukratko objasnio razliku između „dodavanja funkcionalnosti“ i „kontrole pristupa“?",
            wave = 1,
            orderIndex = 302,
            difficulty = "easy"
        ),

        BeginnerSeedBuilders.patternComparisonQuestion(
            questionId = "P4.3",
            title = "Razlikovanje obrazaca — Factory Method vs Builder",
            scenario = "Praviš sistem koji treba da generiše različite tipove dokumenata: PDF, Word i HTML. Glavni kod ne treba da zna koju konkretnu klasu dokumenta kreira.",
            steps = listOf(
                ChoiceStepSeed(title = "Korak 1 — izbor obrasca", instruction = "Koji od ponuđenih obrazaca bolje odgovara scenariju?", options = listOf("Factory Method", "Builder"), correctAnswer = "Factory Method"),
                ChoiceStepSeed(title = "Korak 2 — obrazloženje izbora", instruction = "Zašto je Factory Method pogodniji?", options = listOf("zato što se bira koji tip objekta treba kreirati", "zato što se objekat sastavlja korak po korak", "zato što se čuva staro stanje", "zato što se dodaju nove funkcionalnosti"), correctAnswer = "zato što se bira koji tip objekta treba kreirati"),
                ChoiceStepSeed(title = "Korak 3 — razlikovanje obrazaca", instruction = "Kada bi Builder bio pogodniji?", options = listOf("kada praviš složen objekat sa mnogo opcionalnih delova", "kada želiš samo jednu instancu", "kada želiš obaveštavanje više objekata", "kada želiš prevođenje interfejsa"), correctAnswer = "kada praviš složen objekat sa mnogo opcionalnih delova")
            ),
            aiFollowUp = "Zašto izbor tipa objekta i postepena konstrukcija nisu isti problem?",
            wave = 1,
            orderIndex = 303,
            difficulty = "easy"
        ),

        BeginnerSeedBuilders.patternComparisonQuestion(
            questionId = "P4.4",
            title = "Razlikovanje obrazaca — Adapter vs Facade",
            scenario = "U sistem želiš da uključiš spoljnu biblioteku čiji interfejs nije kompatibilan sa interfejsom koji tvoja aplikacija očekuje.",
            steps = listOf(
                ChoiceStepSeed(title = "Korak 1 — izbor obrasca", instruction = "Koji od ponuđenih obrazaca bolje odgovara scenariju?", options = listOf("Adapter", "Facade"), correctAnswer = "Adapter"),
                ChoiceStepSeed(title = "Korak 2 — obrazloženje izbora", instruction = "Zašto je Adapter pogodniji?", options = listOf("zato što sakriva složen podsistem iza jednostavne klase", "zato što prevodi jedan interfejs u drugi kompatibilan interfejs", "zato što ograničava broj instanci", "zato što čuva stanje objekta"), correctAnswer = "zato što prevodi jedan interfejs u drugi kompatibilan interfejs"),
                ChoiceStepSeed(title = "Korak 3 — razlikovanje obrazaca", instruction = "Kada bi Facade bio pogodniji od Adapter-a?", options = listOf("kada korisniku želiš da ponudiš jednostavan ulaz u složen podsistem", "kada želiš promenu algoritma", "kada želiš kontrolu pristupa", "kada želiš kloniranje objekta"), correctAnswer = "kada korisniku želiš da ponudiš jednostavan ulaz u složen podsistem")
            ),
            aiFollowUp = "U jednoj kratkoj rečenici objasni razliku između Adapter i Facade obrasca.",
            wave = 1,
            orderIndex = 304,
            difficulty = "easy"
        )
    )
}
