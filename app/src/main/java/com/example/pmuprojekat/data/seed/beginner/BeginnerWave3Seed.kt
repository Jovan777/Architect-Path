package com.example.pmuprojekat.data.seed.beginner

import com.example.pmuprojekat.data.seed.SeedQuestion

object BeginnerWave3Seed {
    val questions: List<SeedQuestion> = listOf(
        BeginnerSeedBuilders.patternRecognitionQuestion(
            questionId = "P1.18",
            title = "Deljenje zajedničkih podataka radi uštede memorije",
            prompt = "U igri postoji ogroman broj stabala na mapi. Mnoga stabla imaju isti izgled, boju i model, a razlikuju se samo po poziciji. Želiš da smanjiš potrošnju memorije tako što će više stabala deli iste zajedničke podatke. Koji obrazac najbolje odgovara?",
            options = listOf("Flyweight", "Prototype", "Composite", "Decorator"),
            correctAnswer = "Flyweight",
            aiFollowUp = "Koji deo stanja bi ovde trebalo deliti između više objekata?",
            wave = 3,
            orderIndex = 18,
            difficulty = "medium"
        ),

        BeginnerSeedBuilders.patternRecognitionQuestion(
            questionId = "P1.19",
            title = "Prosleđivanje zahteva kroz niz objekata",
            prompt = "U sistemu za obradu korisničkog zahteva, poruka prvo ide kroz proveru autentifikacije, zatim proveru dozvola, pa proveru validacije podataka. Svaki korak može da obradi zahtev ili da ga prosledi dalje. Koji obrazac odgovara?",
            options = listOf("Mediator", "Chain of Responsibility", "Command", "Strategy"),
            correctAnswer = "Chain of Responsibility",
            aiFollowUp = "Zašto je korisno da zahtev prolazi kroz više obrađivača redom?",
            wave = 3,
            orderIndex = 19,
            difficulty = "medium"
        ),

        BeginnerSeedBuilders.patternRecognitionQuestion(
            questionId = "P1.20",
            title = "Centralni objekat za komunikaciju",
            prompt = "U grafičkom interfejsu više elemenata forme međusobno utiče jedan na drugi. Kada korisnik promeni jednu vrednost, više drugih polja treba da reaguje, ali ne želiš da svako polje direktno zna za sva ostala. Koji obrazac je najpogodniji?",
            options = listOf("Observer", "Mediator", "Bridge", "State"),
            correctAnswer = "Mediator",
            aiFollowUp = "Zašto je bolje da komunikacija ide preko centralnog objekta nego direktno između svih elemenata?",
            wave = 3,
            orderIndex = 20,
            difficulty = "medium"
        ),

        BeginnerSeedBuilders.patternRecognitionQuestion(
            questionId = "P1.21",
            title = "Vraćanje objekta na prethodno stanje",
            prompt = "U aplikaciji za crtanje korisnik želi opciju Undo, tako da se objekat može vratiti na prethodno stanje bez ručnog pamćenja svih detalja spolja. Koji obrazac odgovara?",
            options = listOf("Command", "Memento", "Prototype", "Iterator"),
            correctAnswer = "Memento",
            aiFollowUp = "Šta se zapravo čuva kada koristiš Memento obrazac?",
            wave = 3,
            orderIndex = 21,
            difficulty = "medium"
        ),

        BeginnerSeedBuilders.patternRecognitionQuestion(
            questionId = "P1.22",
            title = "Dodavanje nove operacije bez izmene postojećih klasa",
            prompt = "Imaš strukturu objekata kao što su Circle, Rectangle i Triangle. Želiš da dodaš novu operaciju, na primer izračunavanje površine ili eksport u JSON, bez menjanja postojećih klasa figura. Koji obrazac najbolje odgovara?",
            options = listOf("Visitor", "Template Method", "Adapter", "Proxy"),
            correctAnswer = "Visitor",
            aiFollowUp = "Zašto je korisno da se nova operacija izdvoji van samih klasa objekata?",
            wave = 3,
            orderIndex = 22,
            difficulty = "medium"
        ),

        BeginnerSeedBuilders.patternRecognitionQuestion(
            questionId = "P1.23",
            title = "Tumačenje jednostavnog jezika ili izraza",
            prompt = "Praviš mali sistem koji treba da razume i izvrši jednostavne izraze kao što su “5 + 3” ili komande specifičnog mini-jezika. Koji obrazac je najpogodniji?",
            options = listOf("Interpreter", "Iterator", "Strategy", "Builder"),
            correctAnswer = "Interpreter",
            aiFollowUp = "Šta znači da obrazac definiše gramatiku i tumačenje jezika?",
            wave = 3,
            orderIndex = 23,
            difficulty = "medium"
        ),

        BeginnerSeedBuilders.visualMappingQuestion(
            questionId = "P2.14",
            targetPattern = "Flyweight",
            options = listOf(
                VisualOptionSeed(label = "Flyweight", text = "mnogo objekata koristi isti zajednički objekat; zajednički deo stanja se čuva na jednom mestu; spoljašnje stanje, kao pozicija ili veličina, dolazi odvojeno.", isCorrect = true),
                VisualOptionSeed(label = "Chain of Responsibility", text = "više handler objekata povezanih u lanac; svaki handler ima referencu na sledeći handler; zahtev se prosleđuje dok ga neko ne obradi.", isCorrect = false),
                VisualOptionSeed(label = "Mediator", text = "više kolega objekata (Colleague); svi komuniciraju sa centralnim Mediator objektom; nema mnogo direktnih veza između samih kolega.", isCorrect = false),
                VisualOptionSeed(label = "Memento", text = "Originator može da napravi snapshot svog stanja; Memento čuva to stanje; Caretaker čuva mementa, ali ne zna unutrašnje detalje stanja.", isCorrect = false)
            ),
            aiFollowUp = "Koji deo dijagrama pokazuje da se memorija štedi deljenjem istih podataka?",
            wave = 3,
            orderIndex = 114,
            difficulty = "medium"
        ),

        BeginnerSeedBuilders.visualMappingQuestion(
            questionId = "P2.15",
            targetPattern = "Chain of Responsibility",
            options = listOf(
                VisualOptionSeed(label = "Chain of Responsibility", text = "više handler objekata povezanih u lanac; svaki handler ima referencu na sledeći handler; zahtev se prosleđuje dok ga neko ne obradi.", isCorrect = true),
                VisualOptionSeed(label = "Flyweight", text = "mnogo objekata koristi isti zajednički objekat; zajednički deo stanja se čuva na jednom mestu; spoljašnje stanje, kao pozicija ili veličina, dolazi odvojeno.", isCorrect = false),
                VisualOptionSeed(label = "Mediator", text = "više kolega objekata (Colleague); svi komuniciraju sa centralnim Mediator objektom; nema mnogo direktnih veza između samih kolega.", isCorrect = false),
                VisualOptionSeed(label = "Memento", text = "Originator može da napravi snapshot svog stanja; Memento čuva to stanje; Caretaker čuva mementa, ali ne zna unutrašnje detalje stanja.", isCorrect = false)
            ),
            aiFollowUp = "Koji element dijagrama pokazuje da nijedan objekat ne mora unapred znati ko će na kraju obraditi zahtev?",
            wave = 3,
            orderIndex = 115,
            difficulty = "medium"
        ),

        BeginnerSeedBuilders.visualMappingQuestion(
            questionId = "P2.16",
            targetPattern = "Mediator",
            options = listOf(
                VisualOptionSeed(label = "Mediator", text = "više kolega objekata (Colleague); svi komuniciraju sa centralnim Mediator objektom; nema mnogo direktnih veza između samih kolega.", isCorrect = true),
                VisualOptionSeed(label = "Flyweight", text = "mnogo objekata koristi isti zajednički objekat; zajednički deo stanja se čuva na jednom mestu; spoljašnje stanje, kao pozicija ili veličina, dolazi odvojeno.", isCorrect = false),
                VisualOptionSeed(label = "Chain of Responsibility", text = "više handler objekata povezanih u lanac; svaki handler ima referencu na sledeći handler; zahtev se prosleđuje dok ga neko ne obradi.", isCorrect = false),
                VisualOptionSeed(label = "Memento", text = "Originator može da napravi snapshot svog stanja; Memento čuva to stanje; Caretaker čuva mementa, ali ne zna unutrašnje detalje stanja.", isCorrect = false)
            ),
            aiFollowUp = "Šta u dijagramu pokazuje da je komunikacija centralizovana?",
            wave = 3,
            orderIndex = 116,
            difficulty = "medium"
        ),

        BeginnerSeedBuilders.visualMappingQuestion(
            questionId = "P2.17",
            targetPattern = "Memento",
            options = listOf(
                VisualOptionSeed(label = "Memento", text = "Originator može da napravi snapshot svog stanja; Memento čuva to stanje; Caretaker čuva mementa, ali ne zna unutrašnje detalje stanja.", isCorrect = true),
                VisualOptionSeed(label = "Flyweight", text = "mnogo objekata koristi isti zajednički objekat; zajednički deo stanja se čuva na jednom mestu; spoljašnje stanje, kao pozicija ili veličina, dolazi odvojeno.", isCorrect = false),
                VisualOptionSeed(label = "Chain of Responsibility", text = "više handler objekata povezanih u lanac; svaki handler ima referencu na sledeći handler; zahtev se prosleđuje dok ga neko ne obradi.", isCorrect = false),
                VisualOptionSeed(label = "Mediator", text = "više kolega objekata (Colleague); svi komuniciraju sa centralnim Mediator objektom; nema mnogo direktnih veza između samih kolega.", isCorrect = false)
            ),
            aiFollowUp = "Koja uloga u dijagramu služi samo za čuvanje prethodnih stanja?",
            wave = 3,
            orderIndex = 117,
            difficulty = "medium"
        ),

        BeginnerSeedBuilders.visualMappingQuestion(
            questionId = "P2.18",
            targetPattern = "Visitor",
            options = listOf(
                VisualOptionSeed(label = "Visitor", text = "više elemenata, npr. Circle, Rectangle; interfejs Visitor; konkretni visitor-i, npr. AreaVisitor, ExportVisitor; elementi imaju accept(visitor) metodu.", isCorrect = true),
                VisualOptionSeed(label = "Flyweight", text = "mnogo objekata koristi isti zajednički objekat; zajednički deo stanja se čuva na jednom mestu; spoljašnje stanje, kao pozicija ili veličina, dolazi odvojeno.", isCorrect = false),
                VisualOptionSeed(label = "Chain of Responsibility", text = "više handler objekata povezanih u lanac; svaki handler ima referencu na sledeći handler; zahtev se prosleđuje dok ga neko ne obradi.", isCorrect = false),
                VisualOptionSeed(label = "Mediator", text = "više kolega objekata (Colleague); svi komuniciraju sa centralnim Mediator objektom; nema mnogo direktnih veza između samih kolega.", isCorrect = false)
            ),
            aiFollowUp = "Koji deo dijagrama pokazuje da se operacija dodaje spolja, a ne unutar samih elemenata?",
            wave = 3,
            orderIndex = 118,
            difficulty = "medium"
        ),

        BeginnerSeedBuilders.visualMappingQuestion(
            questionId = "P2.19",
            targetPattern = "Interpreter",
            options = listOf(
                VisualOptionSeed(label = "Interpreter", text = "apstraktni izraz Expression; konkretni izrazi poput NumberExpression, AddExpression; svaka klasa zna kako da interpretira svoj deo izraza.", isCorrect = true),
                VisualOptionSeed(label = "Flyweight", text = "mnogo objekata koristi isti zajednički objekat; zajednički deo stanja se čuva na jednom mestu; spoljašnje stanje, kao pozicija ili veličina, dolazi odvojeno.", isCorrect = false),
                VisualOptionSeed(label = "Chain of Responsibility", text = "više handler objekata povezanih u lanac; svaki handler ima referencu na sledeći handler; zahtev se prosleđuje dok ga neko ne obradi.", isCorrect = false),
                VisualOptionSeed(label = "Mediator", text = "više kolega objekata (Colleague); svi komuniciraju sa centralnim Mediator objektom; nema mnogo direktnih veza između samih kolega.", isCorrect = false)
            ),
            aiFollowUp = "Koji deo dijagrama pokazuje da sistem razlaže i tumači jezik kroz pravila?",
            wave = 3,
            orderIndex = 119,
            difficulty = "medium"
        ),

        BeginnerSeedBuilders.multiStepUnderstandingQuestion(
            questionId = "P3.10",
            patternName = "Flyweight",
            prompt = "Odgovori na tri kratka koraka i proveri da li razumeš svrhu, karakteristike i primenu obrasca Flyweight.",
            steps = listOf(
                ChoiceStepSeed(title = "Korak 1 — svrha", instruction = "Koja je osnovna svrha Flyweight obrasca?", options = listOf("da omogući samo jednu instancu objekta", "da uštedi memoriju deljenjem zajedničkog stanja", "da doda funkcionalnost objektu", "da omogući undo"), correctAnswer = "da uštedi memoriju deljenjem zajedničkog stanja"),
                ChoiceStepSeed(title = "Korak 2 — karakteristika", instruction = "Koja osobina je tipična za Flyweight?", options = listOf("svaki objekat čuva potpuno isto stanje zasebno", "zajednički deo stanja se deli između više objekata", "postoji lanac handler-a", "postoji centralni posrednik"), correctAnswer = "zajednički deo stanja se deli između više objekata"),
                ChoiceStepSeed(title = "Korak 3 — primena", instruction = "Kada je Flyweight dobar izbor?", options = listOf("kada imaš ogroman broj sitnih sličnih objekata", "kada želiš da biraš algoritam u runtime-u", "kada želiš da obavestiš više objekata", "kada želiš da prolaziš kroz listu"), correctAnswer = "kada imaš ogroman broj sitnih sličnih objekata")
            ),
            aiFollowUp = "Koja je razlika između zajedničkog i spoljašnjeg stanja u ovom obrascu?",
            wave = 3,
            orderIndex = 210,
            difficulty = "medium"
        ),

        BeginnerSeedBuilders.multiStepUnderstandingQuestion(
            questionId = "P3.11",
            patternName = "Chain of Responsibility",
            prompt = "Odgovori na tri kratka koraka i proveri da li razumeš svrhu, karakteristike i primenu obrasca Chain of Responsibility.",
            steps = listOf(
                ChoiceStepSeed(title = "Korak 1 — svrha", instruction = "Šta Chain of Responsibility prvenstveno omogućava?", options = listOf("da jedan objekat obavesti više drugih", "da zahtev prolazi kroz lanac mogućih obrađivača", "da se napravi kopija objekta", "da se doda nova operacija klasama"), correctAnswer = "da zahtev prolazi kroz lanac mogućih obrađivača"),
                ChoiceStepSeed(title = "Korak 2 — karakteristika", instruction = "Koja osobina je tipična za ovaj obrazac?", options = listOf("svaki obrađivač može da obradi zahtev ili da ga prosledi dalje", "svi objekti koriste isti prototip", "postoji metoda clone()", "postoji snapshot stanja"), correctAnswer = "svaki obrađivač može da obradi zahtev ili da ga prosledi dalje"),
                ChoiceStepSeed(title = "Korak 3 — primena", instruction = "Gde je ovaj obrazac koristan?", options = listOf("u lancima validacije, autorizacije ili obrade događaja", "u kreiranju porodice UI elemenata", "u obilasku kolekcije", "u gradnji složenog objekta"), correctAnswer = "u lancima validacije, autorizacije ili obrade događaja")
            ),
            aiFollowUp = "Zašto je korisno da pošiljalac zahteva ne mora da zna ko će ga tačno obraditi?",
            wave = 3,
            orderIndex = 211,
            difficulty = "medium"
        ),

        BeginnerSeedBuilders.multiStepUnderstandingQuestion(
            questionId = "P3.12",
            patternName = "Mediator",
            prompt = "Odgovori na tri kratka koraka i proveri da li razumeš svrhu, karakteristike i primenu obrasca Mediator.",
            steps = listOf(
                ChoiceStepSeed(title = "Korak 1 — svrha", instruction = "Koja je osnovna svrha Mediator obrasca?", options = listOf("da sačuva stanje objekta", "da centralizuje komunikaciju između više objekata", "da deli zajedničko stanje", "da kreira porodice proizvoda"), correctAnswer = "da centralizuje komunikaciju između više objekata"),
                ChoiceStepSeed(title = "Korak 2 — karakteristika", instruction = "Šta je tipično za Mediator?", options = listOf("objekti ne komuniciraju direktno mnogo jedni sa drugima, već preko posrednika", "svi objekti imaju clone() metodu", "postoji lanac handler-a", "jedan objekat menja stanje u runtime-u"), correctAnswer = "objekti ne komuniciraju direktno mnogo jedni sa drugima, već preko posrednika"),
                ChoiceStepSeed(title = "Korak 3 — primena", instruction = "Kada je Mediator dobar izbor?", options = listOf("kada postoji mnogo međuzavisnih UI elemenata ili komponenti", "kada želiš da dodaš funkcionalnost objektu", "kada želiš da praviš kopije", "kada želiš da prolaziš kroz kolekciju"), correctAnswer = "kada postoji mnogo međuzavisnih UI elemenata ili komponenti")
            ),
            aiFollowUp = "Kako Mediator pomaže da sistem bude manje povezan?",
            wave = 3,
            orderIndex = 212,
            difficulty = "medium"
        ),

        BeginnerSeedBuilders.multiStepUnderstandingQuestion(
            questionId = "P3.13",
            patternName = "Memento",
            prompt = "Odgovori na tri kratka koraka i proveri da li razumeš svrhu, karakteristike i primenu obrasca Memento.",
            steps = listOf(
                ChoiceStepSeed(title = "Korak 1 — svrha", instruction = "Koja je osnovna svrha Memento obrasca?", options = listOf("da omogući vraćanje objekta na prethodno stanje", "da jedan objekat obavesti druge", "da definiše kostur algoritma", "da tumači jezik"), correctAnswer = "da omogući vraćanje objekta na prethodno stanje"),
                ChoiceStepSeed(title = "Korak 2 — karakteristika", instruction = "Šta je tipično za Memento?", options = listOf("čuva se snapshot stanja objekta", "postoji više algoritama sa zajedničkim interfejsom", "koristi se centralni mediator", "objekti su organizovani u stablo"), correctAnswer = "čuva se snapshot stanja objekta"),
                ChoiceStepSeed(title = "Korak 3 — primena", instruction = "Kada je Memento posebno koristan?", options = listOf("za undo ili vraćanje prethodnih verzija stanja", "za dodavanje novih funkcionalnosti", "za smanjenje potrošnje memorije deljenjem stanja", "za pravljenje cele porodice proizvoda"), correctAnswer = "za undo ili vraćanje prethodnih verzija stanja")
            ),
            aiFollowUp = "Zašto nije dobro da spoljašnji objekti direktno znaju sve detalje unutrašnjeg stanja?",
            wave = 3,
            orderIndex = 213,
            difficulty = "medium"
        ),

        BeginnerSeedBuilders.multiStepUnderstandingQuestion(
            questionId = "P3.14",
            patternName = "Visitor",
            prompt = "Odgovori na tri kratka koraka i proveri da li razumeš svrhu, karakteristike i primenu obrasca Visitor.",
            steps = listOf(
                ChoiceStepSeed(title = "Korak 1 — svrha", instruction = "Koja je osnovna svrha Visitor obrasca?", options = listOf("da omogući novu operaciju nad skupom objekata bez menjanja njihovih klasa", "da omogući samo jednu instancu", "da kontroliše pristup pravom objektu", "da pravi kopiju objekta"), correctAnswer = "da omogući novu operaciju nad skupom objekata bez menjanja njihovih klasa"),
                ChoiceStepSeed(title = "Korak 2 — karakteristika", instruction = "Šta je tipično za Visitor?", options = listOf("elementi prihvataju visitor objekat", "svi elementi čuvaju istu deljenu memoriju", "postoji lanac handler-a", "koristi se privatni konstruktor"), correctAnswer = "elementi prihvataju visitor objekat"),
                ChoiceStepSeed(title = "Korak 3 — primena", instruction = "Kada je Visitor dobar izbor?", options = listOf("kada želiš dodavati nove operacije nad postojećom strukturom objekata", "kada želiš menjati stanje objekta", "kada želiš prolaz kroz listu", "kada želiš centralnu komunikaciju"), correctAnswer = "kada želiš dodavati nove operacije nad postojećom strukturom objekata")
            ),
            aiFollowUp = "Zašto je nekad bolje dodati novu operaciju kroz Visitor nego menjati svaku klasu pojedinačno?",
            wave = 3,
            orderIndex = 214,
            difficulty = "medium"
        ),

        BeginnerSeedBuilders.patternComparisonQuestion(
            questionId = "P4.10",
            title = "Razlikovanje obrazaca — Pitanje P4.10 Mediator vs Observer",
            scenario = "U formi za registraciju promena jednog polja treba da utiče na više drugih UI elemenata, ali želiš da izbegneš da svaki element direktno komunicira sa svim ostalima.",
            steps = listOf(
                ChoiceStepSeed(title = "Korak 1 — izbor obrasca", instruction = "Koji od ponuđenih obrazaca bolje odgovara scenariju?", options = listOf("Mediator", "Observer"), correctAnswer = "Mediator"),
                ChoiceStepSeed(title = "Korak 2 — obrazloženje izbora", instruction = "Zašto je Mediator pogodniji?", options = listOf("zato što postoji centralni objekat koji upravlja komunikacijom", "zato što jedan objekat obaveštava svoje pretplatnike", "zato što čuva snapshot stanja", "zato što obilazi kolekciju"), correctAnswer = "zato što postoji centralni objekat koji upravlja komunikacijom"),
                ChoiceStepSeed(title = "Korak 3 — razlikovanje obrazaca", instruction = "Kada bi Observer bio pogodniji?", options = listOf("kada jedan objekat promeni stanje i više pretplaćenih objekata treba automatski da bude obavešteno", "kada želiš da složiš arhitekturu od blokova", "kada želiš da dodaš funkcionalnost objektu", "kada želiš da praviš kopije objekta"), correctAnswer = "kada jedan objekat promeni stanje i više pretplaćenih objekata treba automatski da bude obavešteno")
            ),
            aiFollowUp = "Ukratko objasni razliku između centralne koordinacije i pretplatničkog obaveštavanja.",
            wave = 3,
            orderIndex = 310,
            difficulty = "medium"
        ),

        BeginnerSeedBuilders.patternComparisonQuestion(
            questionId = "P4.11",
            title = "Razlikovanje obrazaca — Pitanje P4.11 Memento vs Command",
            scenario = "U editoru želiš da korisnik može da vrati dokument na prethodno stanje.",
            steps = listOf(
                ChoiceStepSeed(title = "Korak 1 — izbor obrasca", instruction = "Koji od ponuđenih obrazaca bolje odgovara scenariju?", options = listOf("Memento", "Command"), correctAnswer = "Memento"),
                ChoiceStepSeed(title = "Korak 2 — obrazloženje izbora", instruction = "Zašto je Memento pogodniji?", options = listOf("zato što čuva stanje objekta za kasniji povratak", "zato što svaku akciju pretvara u objekat", "zato što prevodi interfejs", "zato što deli zajedničke podatke"), correctAnswer = "zato što čuva stanje objekta za kasniji povratak"),
                ChoiceStepSeed(title = "Korak 3 — razlikovanje obrazaca", instruction = "Kada bi Command bio pogodniji?", options = listOf("kada želiš da akcije poput Copy, Paste ili Delete budu predstavljene kao objekti", "kada želiš da sačuvaš snapshot stanja", "kada želiš lanac obrade zahteva", "kada želiš više povezanih proizvoda"), correctAnswer = "kada želiš da akcije poput Copy, Paste ili Delete budu predstavljene kao objekti")
            ),
            aiFollowUp = "Koja je razlika između čuvanja stanja i predstavljanja akcije kao objekta?",
            wave = 3,
            orderIndex = 311,
            difficulty = "medium"
        ),

        BeginnerSeedBuilders.patternComparisonQuestion(
            questionId = "P4.12",
            title = "Razlikovanje obrazaca — Pitanje P4.12 Flyweight vs Prototype",
            scenario = "U igri imaš hiljade sličnih metaka koji dele isti model i boju, ali se razlikuju po poziciji i brzini.",
            steps = listOf(
                ChoiceStepSeed(title = "Korak 1 — izbor obrasca", instruction = "Koji od ponuđenih obrazaca bolje odgovara scenariju?", options = listOf("Flyweight", "Prototype"), correctAnswer = "Flyweight"),
                ChoiceStepSeed(title = "Korak 2 — obrazloženje izbora", instruction = "Zašto je Flyweight pogodniji?", options = listOf("zato što više objekata deli zajednički deo stanja", "zato što pravi kopiju postojećeg objekta", "zato što omogućava undo", "zato što uvodi centralni posrednik"), correctAnswer = "zato što više objekata deli zajednički deo stanja"),
                ChoiceStepSeed(title = "Korak 3 — razlikovanje obrazaca", instruction = "Kada bi Prototype bio pogodniji?", options = listOf("kada želiš da napraviš novi objekat kloniranjem postojećeg", "kada želiš da uštediš memoriju deljenjem istog stanja", "kada želiš da prolaziš kroz kolekciju", "kada želiš da dodaš novu operaciju spolja"), correctAnswer = "kada želiš da napraviš novi objekat kloniranjem postojećeg")
            ),
            aiFollowUp = "U jednoj rečenici objasni razliku između deljenja stanja i kloniranja objekta.",
            wave = 3,
            orderIndex = 312,
            difficulty = "medium"
        ),

        BeginnerSeedBuilders.patternComparisonQuestion(
            questionId = "P4.13",
            title = "Razlikovanje obrazaca — Pitanje P4.13 Chain of Responsibility vs Command",
            scenario = "Zahtev prolazi kroz više koraka provere i svaki od njih može da ga zaustavi ili prosledi dalje.",
            steps = listOf(
                ChoiceStepSeed(title = "Korak 1 — izbor obrasca", instruction = "Koji od ponuđenih obrazaca bolje odgovara scenariju?", options = listOf("Chain of Responsibility", "Command"), correctAnswer = "Chain of Responsibility"),
                ChoiceStepSeed(title = "Korak 2 — obrazloženje izbora", instruction = "Zašto je Chain of Responsibility pogodniji?", options = listOf("zato što zahtev prolazi kroz niz obrađivača", "zato što se svaka akcija pretvara u poseban objekat", "zato što postoji snapshot stanja", "zato što se koristi clone()"), correctAnswer = "zato što zahtev prolazi kroz niz obrađivača"),
                ChoiceStepSeed(title = "Korak 3 — razlikovanje obrazaca", instruction = "Kada bi Command bio pogodniji?", options = listOf("kada želiš da predstavljaš akcije kao objekte koje možeš čuvati i izvršavati", "kada želiš da više handler-a proverava isti zahtev", "kada želiš da čuvaš stanje", "kada želiš da jedan objekat obaveštava druge"), correctAnswer = "kada želiš da predstavljaš akcije kao objekte koje možeš čuvati i izvršavati")
            ),
            aiFollowUp = "Šta je fokus Chain of Responsibility, a šta Command obrasca?",
            wave = 3,
            orderIndex = 313,
            difficulty = "medium"
        ),

        BeginnerSeedBuilders.patternComparisonQuestion(
            questionId = "P4.14",
            title = "Razlikovanje obrazaca — Pitanje P4.14 Visitor vs Template Method",
            scenario = "Imaš više različitih tipova elemenata i želiš da dodaš novu operaciju nad svima njima bez izmene njihovih postojećih klasa.",
            steps = listOf(
                ChoiceStepSeed(title = "Korak 1 — izbor obrasca", instruction = "Koji od ponuđenih obrazaca bolje odgovara scenariju?", options = listOf("Visitor", "Template Method"), correctAnswer = "Visitor"),
                ChoiceStepSeed(title = "Korak 2 — obrazloženje izbora", instruction = "Zašto je Visitor pogodniji?", options = listOf("zato što operaciju izdvaja van postojećih klasa objekata", "zato što redosled algoritma ostaje isti u osnovnoj klasi", "zato što koristi listu observer-a", "zato što pravi jednu instancu"), correctAnswer = "zato što operaciju izdvaja van postojećih klasa objekata"),
                ChoiceStepSeed(title = "Korak 3 — razlikovanje obrazaca", instruction = "Kada bi Template Method bio pogodniji?", options = listOf("kada želiš isti kostur algoritma, a različite implementacije pojedinih koraka", "kada želiš novu operaciju nad postojećim strukturama", "kada želiš da tumačiš jezik", "kada želiš da deliš memoriju"), correctAnswer = "kada želiš isti kostur algoritma, a različite implementacije pojedinih koraka")
            ),
            aiFollowUp = "Objasni kratko razliku između dodavanja nove operacije i menjanja pojedinih koraka istog algoritma.",
            wave = 3,
            orderIndex = 314,
            difficulty = "medium"
        ),

        BeginnerSeedBuilders.patternComparisonQuestion(
            questionId = "P4.15",
            title = "Razlikovanje obrazaca — Pitanje P4.15 Interpreter vs Strategy",
            scenario = "Praviš mali jezik za filtere pretrage, gde korisnik može unositi izraze poput price > 100 AND inStock.",
            steps = listOf(
                ChoiceStepSeed(title = "Korak 1 — izbor obrasca", instruction = "Koji od ponuđenih obrazaca bolje odgovara scenariju?", options = listOf("Interpreter", "Strategy"), correctAnswer = "Interpreter"),
                ChoiceStepSeed(title = "Korak 2 — obrazloženje izbora", instruction = "Zašto je Interpreter pogodniji?", options = listOf("zato što definiše pravila za tumačenje izraza nekog jezika", "zato što bira jedan od više algoritama", "zato što čuva prethodno stanje", "zato što uvodi centralni objekat za komunikaciju"), correctAnswer = "zato što definiše pravila za tumačenje izraza nekog jezika"),
                ChoiceStepSeed(title = "Korak 3 — razlikovanje obrazaca", instruction = "Kada bi Strategy bio pogodniji?", options = listOf("kada želiš da menjaš algoritam, na primer način sortiranja ili obračuna", "kada želiš da tumačiš mini-jezik", "kada želiš da obilaziš kolekciju", "kada želiš da grupišeš objekte u stablo"), correctAnswer = "kada želiš da menjaš algoritam, na primer način sortiranja ili obračuna")
            ),
            aiFollowUp = "Koja je osnovna razlika između tumačenja jezika i izbora algoritma?",
            wave = 3,
            orderIndex = 315,
            difficulty = "medium"
        )
    )
}
