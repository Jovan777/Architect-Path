package com.example.pmuprojekat.data.seed.medior

import com.example.pmuprojekat.data.seed.SeedQuestion

object MediorRequirementMappingSeed {
    val questions: List<SeedQuestion> = listOf(
        MediorSeedBuilders.mappingQuestion(
            questionId = "M3.1",
            title = "Poveži potrebe checkout i content sistema sa odgovarajućim obrascima",
            prompt = """
            U nastavku su opisane četiri potrebe iz različitih delova sistema. Za svaku potrebu poveži najpogodniji obrazac.
            Situacije
            1. Checkout modul
            Tim želi da pravila za obračun dodatnih troškova može da menja bez izmene glavnog toka naplate. Pravila zavise od kampanje, tipa korisnika i perioda u godini, a očekuje se da će ih biti sve više.
            2. Shipment modul
            Kada pošiljka promeni status, više delova sistema mora da reaguje: korisnički interfejs, email obaveštenja, analitika i interni audit.
            3. Content processing servis
            Postojeća obrada sadržaja treba da dobije dodatne mogućnosti kao što su logovanje, kompresija i enkripcija, u različitim kombinacijama, bez pravljenja velikog broja naslednika.
            4. Navigation modul
            Sistem menija treba da omogući da se ista operacija primeni i nad jednom stavkom i nad grupom stavki, bez posebne logike u klijentskom kodu.
            """.trimIndent(),
            zones = listOf(
                "Strategy",
                "Observer",
                "Decorator",
                "Composite"
            ),
            items = listOf(
                MediorMappingItemSeed(
                    text = """
                    1. Checkout modul Tim želi da pravila za obračun dodatnih troškova može da menja bez izmene glavnog toka naplate. Pravila zavise od kampanje, tipa korisnika i perioda u godini, a očekuje se da će ih biti sve više.
                    """.trimIndent(),
                    correctZone = "Strategy"
                ),
                MediorMappingItemSeed(
                    text = """
                    2. Shipment modul Kada pošiljka promeni status, više delova sistema mora da reaguje: korisnički interfejs, email obaveštenja, analitika i interni audit.
                    """.trimIndent(),
                    correctZone = "Observer"
                ),
                MediorMappingItemSeed(
                    text = """
                    3. Content processing servis Postojeća obrada sadržaja treba da dobije dodatne mogućnosti kao što su logovanje, kompresija i enkripcija, u različitim kombinacijama, bez pravljenja velikog broja naslednika.
                    """.trimIndent(),
                    correctZone = "Decorator"
                ),
                MediorMappingItemSeed(
                    text = """
                    4. Navigation modul Sistem menija treba da omogući da se ista operacija primeni i nad jednom stavkom i nad grupom stavki, bez posebne logike u klijentskom kodu.
                    """.trimIndent(),
                    correctZone = "Composite"
                )
            ),
            multiStep = MediorChoiceStepSeed(
                title = "Korak 2 - Multi-choice",
                instruction = "Izaberi sve tačne odgovore.",
                options = listOf(
                    """
                    Ako bi se u situaciji 1 sva pricing pravila držala u jednom servisu kroz grananje, najveći problem bi bio rast centralne poslovne logike i teže testiranje pojedinačnih pravila.
                    """.trimIndent(),
                    """
                    Ako bi se u situaciji 2 koristio Mediator umesto Observer-a, sistem bi prirodno bio pogodniji za veliki broj nezavisnih reakcija na isti događaj u različitim modulima.
                    """.trimIndent(),
                    """
                    Ako bi se u situaciji 3 koristilo nasleđivanje za svaku kombinaciju funkcionalnosti, najverovatnija posledica bio bi brz rast broja klasa i teže održavanje.
                    """.trimIndent(),
                    """
                    Ako bi se u situaciji 4 koristio Proxy umesto Composite-a, klijent bi jednostavnije radio i sa grupom i sa pojedinačnim elementom kroz isti model.
                    """.trimIndent()
                ),
                correctAnswers = listOf(
                    """
                    Ako bi se u situaciji 1 sva pricing pravila držala u jednom servisu kroz grananje, najveći problem bi bio rast centralne poslovne logike i teže testiranje pojedinačnih pravila.
                    """.trimIndent(),
                    """
                    Ako bi se u situaciji 3 koristilo nasleđivanje za svaku kombinaciju funkcionalnosti, najverovatnija posledica bio bi brz rast broja klasa i teže održavanje.
                    """.trimIndent()
                )
            ),
            aiFollowUp = """
            U situaciji 2, objasni zašto je za analytics i audit module korisno da budu slabo povezani sa modulom koji menja status pošiljke.
            """.trimIndent(),
            wave = 1,
            orderIndex = 5
        ),

        MediorSeedBuilders.mappingQuestion(
            questionId = "M3.2",
            title = "Poveži arhitektonske potrebe sa najpogodnijim obrascima",
            prompt = """
            Za svaku od sledećih potreba izaberi najpogodniji obrazac.
            Situacije
            1. White-label UI platforma
            Sistem mora da podrži više brendova, tako da izbor jednog brenda automatski određuje odgovarajuće dugme, formu, dijalog i ostale UI elemente tog paketa.
            2. Integracija stare biblioteke
            Nova payment biblioteka mora da bude uključena u sistem, ali ima interfejs koji nije kompatibilan sa postojećim servisnim slojem.
            3. Reporting modul
            Postoje dve nezavisne ose promena: vrsta izveštaja i način prikaza. Tim želi da izbegne da za svaku kombinaciju mora da pravi posebnu klasu.
            4. Remote document pristup
            Pristup udaljenom dokument servisu je skup i treba ga kontrolisati, uz mogućnost da se stvarni objekat aktivira tek kada je stvarno potreban.
            """.trimIndent(),
            zones = listOf(
                "Abstract Factory",
                "Adapter",
                "Bridge",
                "Proxy"
            ),
            items = listOf(
                MediorMappingItemSeed(
                    text = """
                    1. White-label UI platforma Sistem mora da podrži više brendova, tako da izbor jednog brenda automatski određuje odgovarajuće dugme, formu, dijalog i ostale UI elemente tog paketa.
                    """.trimIndent(),
                    correctZone = "Abstract Factory"
                ),
                MediorMappingItemSeed(
                    text = """
                    2. Integracija stare biblioteke Nova payment biblioteka mora da bude uključena u sistem, ali ima interfejs koji nije kompatibilan sa postojećim servisnim slojem.
                    """.trimIndent(),
                    correctZone = "Adapter"
                ),
                MediorMappingItemSeed(
                    text = """
                    3. Reporting modul Postoje dve nezavisne ose promena: vrsta izveštaja i način prikaza. Tim želi da izbegne da za svaku kombinaciju mora da pravi posebnu klasu.
                    """.trimIndent(),
                    correctZone = "Bridge"
                ),
                MediorMappingItemSeed(
                    text = """
                    4. Remote document pristup Pristup udaljenom dokument servisu je skup i treba ga kontrolisati, uz mogućnost da se stvarni objekat aktivira tek kada je stvarno potreban.
                    """.trimIndent(),
                    correctZone = "Proxy"
                )
            ),
            multiStep = MediorChoiceStepSeed(
                title = "Korak 2 - Multi-choice",
                instruction = "Izaberi sve tačne odgovore.",
                options = listOf(
                    """
                    Ako bi se situacija 1 rešavala Factory Method obrascem bez dodatne organizacije porodice komponenti, najveći rizik bio bi gubitak konzistentnosti između povezanih UI elemenata istog brenda.
                    """.trimIndent(),
                    """
                    Ako bi se situacija 2 rešavala Bridge obrascem, to bi bio prirodan izbor zato što Bridge prvenstveno rešava problem nekompatibilnog postojećeg interfejsa.
                    """.trimIndent(),
                    "Ako bi se situacija 3 rešavala samo nasleđivanjem, najverovatnija posledica bio bi kombinatorni rast broja klasa.",
                    "Ako bi se situacija 4 rešavala Composite obrascem, sistem bi lakše kontrolisao pristup udaljenom resursu."
                ),
                correctAnswers = listOf(
                    """
                    Ako bi se situacija 1 rešavala Factory Method obrascem bez dodatne organizacije porodice komponenti, najveći rizik bio bi gubitak konzistentnosti između povezanih UI elemenata istog brenda.
                    """.trimIndent(),
                    "Ako bi se situacija 3 rešavala samo nasleđivanjem, najverovatnija posledica bio bi kombinatorni rast broja klasa."
                )
            ),
            aiFollowUp = """
            U situaciji 3, objasni zašto je problem pogrešno posmatrati samo kao “treba nam još nekoliko novih klasa”, umesto kao problem dve nezavisne dimenzije razvoja sistema.
            """.trimIndent(),
            wave = 1,
            orderIndex = 6
        ),

        MediorSeedBuilders.mappingQuestion(
            questionId = "M3.3",
            title = "Poveži razvojne probleme modula sa najpogodnijim obrascima",
            prompt = """
            Za svaku situaciju izaberi najpogodniji obrazac.
            Situacije
            1. Document editor modul
            Sistem podržava više operacija nad dokumentom, kao što su copy, paste, delete, format, a tim želi da te akcije mogu da:
            •	budu stavljene u red, 
            •	budu vezane za toolbar dugmad i shortcut-e, 
            •	i kasnije podrže undo mehanizam. 
            2. Search engine modul
            Sistem treba da prolazi kroz rezultate pretrage i kroz hijerarhiju foldera na isti konceptualni način, ali bez otkrivanja unutrašnje strukture kolekcije spolja.
            3. Rule evaluation modul
            Tim uvodi jednostavan interni izrazni jezik za filtere, npr.:
            •	price > 100 AND inStock 
            •	country == 'RS' OR vip == true 
            Potrebno je da sistem zna da tumači i izvršava te izraze.
            4. Editor state recovery modul
            Korisnik treba da može da vrati dokument na ranije stanje bez toga da ostatak sistema direktno barata svim internim detaljima stanja dokumenta.
            """.trimIndent(),
            zones = listOf(
                "Command",
                "Iterator",
                "Interpreter",
                "Memento"
            ),
            items = listOf(
                MediorMappingItemSeed(
                    text = """
                    1. Document editor modul Sistem podržava više operacija nad dokumentom, kao što su copy, paste, delete, format, a tim želi da te akcije mogu da: budu stavljene u red, budu vezane za toolbar dugmad i shortcut-e, i kasnije podrže undo mehanizam.
                    """.trimIndent(),
                    correctZone = "Command"
                ),
                MediorMappingItemSeed(
                    text = """
                    2. Search engine modul Sistem treba da prolazi kroz rezultate pretrage i kroz hijerarhiju foldera na isti konceptualni način, ali bez otkrivanja unutrašnje strukture kolekcije spolja.
                    """.trimIndent(),
                    correctZone = "Iterator"
                ),
                MediorMappingItemSeed(
                    text = """
                    3. Rule evaluation modul Tim uvodi jednostavan interni izrazni jezik za filtere, npr.: price > 100 AND inStock country == 'RS' OR vip == true Potrebno je da sistem zna da tumači i izvršava te izraze.
                    """.trimIndent(),
                    correctZone = "Interpreter"
                ),
                MediorMappingItemSeed(
                    text = """
                    4. Editor state recovery modul Korisnik treba da može da vrati dokument na ranije stanje bez toga da ostatak sistema direktno barata svim internim detaljima stanja dokumenta.
                    """.trimIndent(),
                    correctZone = "Memento"
                )
            ),
            multiStep = MediorChoiceStepSeed(
                title = "Korak 2 - Multi-choice",
                instruction = "Izaberi sve tačne odgovore.",
                options = listOf(
                    """
                    Ako bi se situacija 1 rešavala samo običnim direktnim pozivima metoda iz toolbar-a, sistem bi teže podržao uniformno čuvanje i ponavljanje akcija.
                    """.trimIndent(),
                    """
                    Ako bi se situacija 2 rešavala Composite obrascem, glavni dobitak bio bi odvajanje logike prolaska kroz kolekciju od same kolekcije.
                    """.trimIndent(),
                    "Ako bi se situacija 3 rešavala Strategy obrascem, to bi prirodno modelovalo gramatiku i pravila parsiranja izraza.",
                    """
                    Ako bi se situacija 4 rešavala tako što caretaker direktno menja interna polja editora, izgubila bi se enkapsulacija stanja.
                    """.trimIndent()
                ),
                correctAnswers = listOf(
                    """
                    Ako bi se situacija 1 rešavala samo običnim direktnim pozivima metoda iz toolbar-a, sistem bi teže podržao uniformno čuvanje i ponavljanje akcija.
                    """.trimIndent(),
                    """
                    Ako bi se situacija 4 rešavala tako što caretaker direktno menja interna polja editora, izgubila bi se enkapsulacija stanja.
                    """.trimIndent()
                )
            ),
            aiFollowUp = """
            U situaciji 1, objasni zašto je za undo važnije da je akcija modelovana kao objekat nego kao običan poziv metode iz UI sloja.
            """.trimIndent(),
            wave = 2,
            orderIndex = 15
        ),

        MediorSeedBuilders.mappingQuestion(
            questionId = "M3.4",
            title = "Poveži tehničke potrebe sistema sa odgovarajućim obrascima",
            prompt = """
            Za svaku situaciju izaberi najpogodniji obrazac.
            Situacije
            1. Access pipeline modul
            Zahtev prolazi kroz više koraka obrade: proveru tokena, proveru dozvola, proveru tenant pravila i validaciju ulaza. Svaki korak može da odbije zahtev ili da ga prepusti sledećem.
            2. UI collaboration modul
            Dijalog za rezervaciju ima više elemenata koji međusobno utiču jedni na druge, ali tim želi da izbegne direktnu mrežu zavisnosti između svih tih elemenata.
            3. Graphics engine modul
            Sistem prikazuje ogroman broj vrlo sličnih vizuelnih objekata. Tim želi da smanji memorijski trošak deljenjem zajedničkih podataka, dok pojedinačni objekti zadržavaju samo spoljašnje stanje.
            4. Analytics over object tree modul
            Postoji stabilna struktura objekata nad kojom se s vremena na vreme dodaju nove operacije, poput eksportovanja, validacije i statističke analize, bez izmene postojećih klasa elemenata.
            """.trimIndent(),
            zones = listOf(
                "Chain of Responsibility",
                "Mediator",
                "Flyweight",
                "Visitor"
            ),
            items = listOf(
                MediorMappingItemSeed(
                    text = """
                    1. Access pipeline modul Zahtev prolazi kroz više koraka obrade: proveru tokena, proveru dozvola, proveru tenant pravila i validaciju ulaza. Svaki korak može da odbije zahtev ili da ga prepusti sledećem.
                    """.trimIndent(),
                    correctZone = "Chain of Responsibility"
                ),
                MediorMappingItemSeed(
                    text = """
                    2. UI collaboration modul Dijalog za rezervaciju ima više elemenata koji međusobno utiču jedni na druge, ali tim želi da izbegne direktnu mrežu zavisnosti između svih tih elemenata.
                    """.trimIndent(),
                    correctZone = "Mediator"
                ),
                MediorMappingItemSeed(
                    text = """
                    3. Graphics engine modul Sistem prikazuje ogroman broj vrlo sličnih vizuelnih objekata. Tim želi da smanji memorijski trošak deljenjem zajedničkih podataka, dok pojedinačni objekti zadržavaju samo spoljašnje stanje.
                    """.trimIndent(),
                    correctZone = "Flyweight"
                ),
                MediorMappingItemSeed(
                    text = """
                    4. Analytics over object tree modul Postoji stabilna struktura objekata nad kojom se s vremena na vreme dodaju nove operacije, poput eksportovanja, validacije i statističke analize, bez izmene postojećih klasa elemenata.
                    """.trimIndent(),
                    correctZone = "Visitor"
                )
            ),
            multiStep = MediorChoiceStepSeed(
                title = "Korak 2 - Multi-choice",
                instruction = "Izaberi sve tačne odgovore.",
                options = listOf(
                    """
                    Ako bi se situacija 1 rešavala jednom centralnom metodom sa rastućim grananjem, najverovatnija posledica bio bi teže proširiv i slabije segmentiran validation tok.
                    """.trimIndent(),
                    """
                    Ako bi se situacija 2 rešavala Observer obrascem, to bi prirodno centralizovalo sva pravila saradnje između komponenti na jednom mestu.
                    """.trimIndent(),
                    """
                    Ako bi se situacija 3 rešavala Prototype obrascem, glavni dobitak bio bi deljenje zajedničkog internog stanja između velikog broja sličnih objekata.
                    """.trimIndent(),
                    """
                    Ako bi se situacija 4 rešavala stalnim dodavanjem novih metoda u sve klase elemenata, cena bi bila rast spregnutosti između strukture elemenata i operacija nad njima.
                    """.trimIndent()
                ),
                correctAnswers = listOf(
                    """
                    Ako bi se situacija 1 rešavala jednom centralnom metodom sa rastućim grananjem, najverovatnija posledica bio bi teže proširiv i slabije segmentiran validation tok.
                    """.trimIndent(),
                    """
                    Ako bi se situacija 4 rešavala stalnim dodavanjem novih metoda u sve klase elemenata, cena bi bila rast spregnutosti između strukture elemenata i operacija nad njima.
                    """.trimIndent()
                )
            ),
            aiFollowUp = """
            U situaciji 3, objasni razliku između “pravljenja više sličnih objekata” i “deljenja istog internog stanja između velikog broja objekata”.
            """.trimIndent(),
            wave = 2,
            orderIndex = 16
        ),

        MediorSeedBuilders.mappingQuestion(
            questionId = "M3.5",
            title = "Poveži potrebe workflow, editora i složenih servisa sa odgovarajućim obrascima",
            prompt = """
            Za svaku situaciju izaberi najpogodniji obrazac.
            Situacije
            1. Document orchestration modul
            Sistem za obradu ugovora koristi više internih podsistema: OCR, proveru potpisa, klasifikaciju, arhiviranje i audit zapis. Tim želi da ostatak aplikacije pokrene ceo proces kroz jednostavan ulaz, bez poznavanja svih internih koraka i zavisnosti.
            2. Export pipeline modul
            Sistem generiše različite vrste eksportovanja podataka. Uvek postoji isti osnovni tok: priprema podataka, validacija, formatiranje i zapis izlaza, ali se konkretna implementacija pojedinih koraka razlikuje po tipu eksporta.
            3. Ticket workflow modul
            Ponašanje tiketa zavisi od njegovog trenutnog statusa. Kada je tiket otvoren, dozvoljene su jedne akcije, kada je u obradi druge, a kada je zatvoren treće. Očekuje se da će se kasnije uvoditi nova stanja i nova pravila po stanju.
            4. Report assembly modul
            Sistem pravi složene izveštaje koji mogu imati različite opcione sekcije: summary, grafikone, tabele, komentare i dodatke. Tim želi kontrolisanu izgradnju izveštaja bez eksplozije konstruktora i fabričkih varijanti.
            """.trimIndent(),
            zones = listOf(
                "Facade",
                "Template Method",
                "State",
                "Builder"
            ),
            items = listOf(
                MediorMappingItemSeed(
                    text = """
                    1. Document orchestration modul Sistem za obradu ugovora koristi više internih podsistema: OCR, proveru potpisa, klasifikaciju, arhiviranje i audit zapis. Tim želi da ostatak aplikacije pokrene ceo proces kroz jednostavan ulaz, bez poznavanja svih internih koraka i zavisnosti.
                    """.trimIndent(),
                    correctZone = "Facade"
                ),
                MediorMappingItemSeed(
                    text = """
                    2. Export pipeline modul Sistem generiše različite vrste eksportovanja podataka. Uvek postoji isti osnovni tok: priprema podataka, validacija, formatiranje i zapis izlaza, ali se konkretna implementacija pojedinih koraka razlikuje po tipu eksporta.
                    """.trimIndent(),
                    correctZone = "Template Method"
                ),
                MediorMappingItemSeed(
                    text = """
                    3. Ticket workflow modul Ponašanje tiketa zavisi od njegovog trenutnog statusa. Kada je tiket otvoren, dozvoljene su jedne akcije, kada je u obradi druge, a kada je zatvoren treće. Očekuje se da će se kasnije uvoditi nova stanja i nova pravila po stanju.
                    """.trimIndent(),
                    correctZone = "State"
                ),
                MediorMappingItemSeed(
                    text = """
                    4. Report assembly modul Sistem pravi složene izveštaje koji mogu imati različite opcione sekcije: summary, grafikone, tabele, komentare i dodatke. Tim želi kontrolisanu izgradnju izveštaja bez eksplozije konstruktora i fabričkih varijanti.
                    """.trimIndent(),
                    correctZone = "Builder"
                )
            ),
            multiStep = MediorChoiceStepSeed(
                title = "Korak 2 - Multi-choice",
                instruction = "Izaberi sve tačne odgovore.",
                options = listOf(
                    """
                    Ako bi se situacija 1 rešavala tako da aplikacioni sloj direktno poziva sve podsisteme redom, glavni problem bio bi rast spregnutosti sa internom logikom procesa.
                    """.trimIndent(),
                    """
                    Ako bi se situacija 2 rešavala Strategy obrascem, sistem bi automatski dobio stabilan kostur procesa i samo promenljive korake po tipu eksporta.
                    """.trimIndent(),
                    """
                    Ako bi se situacija 3 rešavala velikom centralnom klasom sa grananjem po statusima, vremenom bi postalo teže jasno odvojiti odgovornosti ponašanja po stanju.
                    """.trimIndent(),
                    """
                    Ako bi se situacija 4 rešavala Abstract Factory obrascem, glavni dobitak bio bi postepena konstrukcija izveštaja sa opcionim sekcijama.
                    """.trimIndent()
                ),
                correctAnswers = listOf(
                    """
                    Ako bi se situacija 1 rešavala tako da aplikacioni sloj direktno poziva sve podsisteme redom, glavni problem bio bi rast spregnutosti sa internom logikom procesa.
                    """.trimIndent(),
                    """
                    Ako bi se situacija 3 rešavala velikom centralnom klasom sa grananjem po statusima, vremenom bi postalo teže jasno odvojiti odgovornosti ponašanja po stanju.
                    """.trimIndent()
                )
            ),
            aiFollowUp = """
            U situaciji 2, objasni zašto je problem pre svega „stabilan tok sa promenljivim koracima“, a ne samo „više različitih implementacija“.
            """.trimIndent(),
            wave = 3,
            orderIndex = 25
        ),

        MediorSeedBuilders.mappingQuestion(
            questionId = "M3.6",
            title = "Poveži potrebe kreiranja, izbora ponašanja i rada sa strukturom sa odgovarajućim obrascima",
            prompt = """
            Za svaku situaciju izaberi najpogodniji obrazac.
            Situacije
            1. Parser creation modul
            Sistem podržava više tipova ulaznih fajlova, a za svaki tip treba kreirati odgovarajući parser objekat. Tim želi da dodavanje novog formata ne menja postojeći klijentski kod koji koristi parsere.
            2. Template cloning modul
            Editor podržava početne šablone elemenata koje korisnik može brzo umnožavati i zatim blago menjati. Bitno je da se novi elementi dobijaju kopiranjem postojećih šablona, a ne ručnim sastavljanjem od nule.
            3. Routing rules modul
            Način usmeravanja zahteva zavisi od regiona, tipa korisnika i opterećenja sistema. Pravila se menjaju kroz vreme i tim želi da ih menja bez izmene glavnog toka obrade.
            4. Page structure modul
            Stranica se sastoji od pojedinačnih elemenata i grupa elemenata, a klijentski kod treba da primenjuje iste operacije nad oba slučaja bez posebnog grananja.
            """.trimIndent(),
            zones = listOf(
                "Factory Method",
                "Prototype",
                "Strategy",
                "Composite"
            ),
            items = listOf(
                MediorMappingItemSeed(
                    text = """
                    1. Parser creation modul Sistem podržava više tipova ulaznih fajlova, a za svaki tip treba kreirati odgovarajući parser objekat. Tim želi da dodavanje novog formata ne menja postojeći klijentski kod koji koristi parsere.
                    """.trimIndent(),
                    correctZone = "Factory Method"
                ),
                MediorMappingItemSeed(
                    text = """
                    2. Template cloning modul Editor podržava početne šablone elemenata koje korisnik može brzo umnožavati i zatim blago menjati. Bitno je da se novi elementi dobijaju kopiranjem postojećih šablona, a ne ručnim sastavljanjem od nule.
                    """.trimIndent(),
                    correctZone = "Prototype"
                ),
                MediorMappingItemSeed(
                    text = """
                    3. Routing rules modul Način usmeravanja zahteva zavisi od regiona, tipa korisnika i opterećenja sistema. Pravila se menjaju kroz vreme i tim želi da ih menja bez izmene glavnog toka obrade.
                    """.trimIndent(),
                    correctZone = "Strategy"
                ),
                MediorMappingItemSeed(
                    text = """
                    4. Page structure modul Stranica se sastoji od pojedinačnih elemenata i grupa elemenata, a klijentski kod treba da primenjuje iste operacije nad oba slučaja bez posebnog grananja.
                    """.trimIndent(),
                    correctZone = "Composite"
                )
            ),
            multiStep = MediorChoiceStepSeed(
                title = "Korak 2 - Multi-choice",
                instruction = "Izaberi sve tačne odgovore.",
                options = listOf(
                    """
                    Ako bi se situacija 1 rešavala centralnim grananjem po tipu fajla unutar jedne velike fabrike, glavni problem bio bi rast centralne odgovornosti za kreiranje parsera.
                    """.trimIndent(),
                    """
                    Ako bi se situacija 2 rešavala Builder obrascem, glavni dobitak bio bi deljenje istog internog stanja između velikog broja objekata.
                    """.trimIndent(),
                    """
                    Ako bi se situacija 3 rešavala nizom if-else pravila u jednom servisu, sistem bi postao teži za testiranje i izolovanu promenu pojedinačnih pravila.
                    """.trimIndent(),
                    """
                    Ako bi se situacija 4 rešavala Iterator obrascem, klijent bi automatski dobio uniforman model rada i sa grupom i sa pojedinačnim elementom.
                    """.trimIndent()
                ),
                correctAnswers = listOf(
                    """
                    Ako bi se situacija 1 rešavala centralnim grananjem po tipu fajla unutar jedne velike fabrike, glavni problem bio bi rast centralne odgovornosti za kreiranje parsera.
                    """.trimIndent(),
                    """
                    Ako bi se situacija 3 rešavala nizom if-else pravila u jednom servisu, sistem bi postao teži za testiranje i izolovanu promenu pojedinačnih pravila.
                    """.trimIndent()
                )
            ),
            aiFollowUp = """
            U situaciji 2, objasni zašto je problem bliži „kopiranju gotovog uzorka“ nego „postepenoj izgradnji složenog objekta“.

            4. Četvrti tip zadatka: Izbor pristupa pod ograničenjima sistema
            """.trimIndent(),
            wave = 3,
            orderIndex = 26
        ),

        MediorSeedBuilders.mappingQuestion(
            questionId = "M3.7",
            title = "Poveži potrebe koordinacije, pristupa resursima i generisanja porodica objekata sa odgovarajućim obrascima",
            prompt = """
            Za svaku situaciju izaberi najpogodniji obrazac.

            Situacije
            1. Form interaction modul
            U kompleksnom formularu više UI elemenata međusobno utiče jedni na druge. Promena jednog polja može aktivirati, deaktivirati ili promeniti ponašanje drugih polja. Tim želi da izbegne mrežu direktnih zavisnosti između svih komponenti.
            2. Brand component family modul
            Sistem podržava više vizuelnih stilova aplikacije. Kada se izabere jedan stil, ceo skup međusobno usklađenih komponenti — dugmad, dijalozi, forme i meniji — mora biti kreiran iz iste porodice.
            3. Controlled file access modul
            Pristup velikim fajlovima je skup i treba ga odložiti dok korisnik zaista ne zatraži sadržaj, uz mogućnost dodatne kontrole pristupa istom objektu.
            4. Multi-channel rendering modul
            Sistem ima dve nezavisne dimenzije razvoja: vrstu sadržaja i kanal prikaza. Tim želi da izbegne rast hijerarhije klasa za svaku novu kombinaciju.
            """.trimIndent(),
            zones = listOf(
                "Mediator",
                "Abstract Factory",
                "Proxy",
                "Bridge"
            ),
            items = listOf(
                MediorMappingItemSeed(
                    text = """
                    1. Form interaction modul U kompleksnom formularu više UI elemenata međusobno utiče jedni na druge. Promena jednog polja može aktivirati, deaktivirati ili promeniti ponašanje drugih polja. Tim želi da izbegne mrežu direktnih zavisnosti između svih komponenti.
                    """.trimIndent(),
                    correctZone = "Mediator"
                ),
                MediorMappingItemSeed(
                    text = """
                    2. Brand component family modul Sistem podržava više vizuelnih stilova aplikacije. Kada se izabere jedan stil, ceo skup međusobno usklađenih komponenti — dugmad, dijalozi, forme i meniji — mora biti kreiran iz iste porodice.
                    """.trimIndent(),
                    correctZone = "Abstract Factory"
                ),
                MediorMappingItemSeed(
                    text = """
                    3. Controlled file access modul Pristup velikim fajlovima je skup i treba ga odložiti dok korisnik zaista ne zatraži sadržaj, uz mogućnost dodatne kontrole pristupa istom objektu.
                    """.trimIndent(),
                    correctZone = "Proxy"
                ),
                MediorMappingItemSeed(
                    text = """
                    4. Multi-channel rendering modul Sistem ima dve nezavisne dimenzije razvoja: vrstu sadržaja i kanal prikaza. Tim želi da izbegne rast hijerarhije klasa za svaku novu kombinaciju.
                    """.trimIndent(),
                    correctZone = "Bridge"
                )
            ),
            multiStep = MediorChoiceStepSeed(
                title = "Korak 2 - Multi-choice",
                instruction = "Izaberi sve tačne odgovore.",
                options = listOf(
                    """
                    Ako bi se situacija 1 rešavala tako da svaka UI komponenta direktno zna za sve ostale, vremenom bi porasla složenost međuzavisnosti i teže bi se pratila pravila saradnje.
                    """.trimIndent(),
                    """
                    Ako bi se situacija 2 rešavala Builder obrascem, glavni dobitak bio bi razdvajanje dve nezavisne ose promena u sistemu komponenti.
                    """.trimIndent(),
                    """
                    Ako bi se situacija 3 rešavala Adapter obrascem, sistem bi prirodno dobio model za odloženo učitavanje i kontrolu pristupa resursu.
                    """.trimIndent(),
                    "Ako bi se situacija 4 rešavala isključivo podklasama, broj kombinacija sadržaja i kanala bi brzo rastao."
                ),
                correctAnswers = listOf(
                    """
                    Ako bi se situacija 1 rešavala tako da svaka UI komponenta direktno zna za sve ostale, vremenom bi porasla složenost međuzavisnosti i teže bi se pratila pravila saradnje.
                    """.trimIndent(),
                    "Ako bi se situacija 4 rešavala isključivo podklasama, broj kombinacija sadržaja i kanala bi brzo rastao."
                )
            ),
            aiFollowUp = """
            U situaciji 1, objasni zašto je ovde veći problem koordinacija međuzavisnih komponenti nego samo emitovanje jednog događaja ka više posmatrača.
            """.trimIndent(),
            wave = 4,
            orderIndex = 35
        ),

        MediorSeedBuilders.mappingQuestion(
            questionId = "M3.8",
            title = "Poveži potrebe stabilnog toka, uprošćenog interfejsa, prilagođavanja i obilaska strukture sa odgovarajućim obrascima",
            prompt = """
            Za svaku situaciju izaberi najpogodniji obrazac.
            Situacije
            1. Data import workflow modul
            Sistem uvek prolazi kroz isti osnovni tok uvoza podataka: učitavanje, osnovna validacija, transformacija i upis. Ipak, konkretna realizacija pojedinih koraka zavisi od izvora podataka.
            2. Complex subsystem access modul
            Modul za registraciju klijenta koristi više servisa: proveru identiteta, proveru adrese, scoring, audit i kreiranje naloga. Tim želi jednostavnu ulaznu tačku za ostatak sistema, bez izlaganja svih internih poziva.
            3. Legacy service integration modul
            Stari eksterni servis mora da se uključi u novi sistem, ali njegov interfejs nije usklađen sa internim ugovorom koji aplikacija očekuje.
            4. Result traversal modul
            Klijentski kod treba da prolazi kroz elemente složene kolekcije bez poznavanja njene konkretne unutrašnje organizacije.
            """.trimIndent(),
            zones = listOf(
                "Template Method",
                "Facade",
                "Adapter",
                "Iterator"
            ),
            items = listOf(
                MediorMappingItemSeed(
                    text = """
                    1. Data import workflow modul Sistem uvek prolazi kroz isti osnovni tok uvoza podataka: učitavanje, osnovna validacija, transformacija i upis. Ipak, konkretna realizacija pojedinih koraka zavisi od izvora podataka.
                    """.trimIndent(),
                    correctZone = "Template Method"
                ),
                MediorMappingItemSeed(
                    text = """
                    2. Complex subsystem access modul Modul za registraciju klijenta koristi više servisa: proveru identiteta, proveru adrese, scoring, audit i kreiranje naloga. Tim želi jednostavnu ulaznu tačku za ostatak sistema, bez izlaganja svih internih poziva.
                    """.trimIndent(),
                    correctZone = "Facade"
                ),
                MediorMappingItemSeed(
                    text = """
                    3. Legacy service integration modul Stari eksterni servis mora da se uključi u novi sistem, ali njegov interfejs nije usklađen sa internim ugovorom koji aplikacija očekuje.
                    """.trimIndent(),
                    correctZone = "Adapter"
                ),
                MediorMappingItemSeed(
                    text = """
                    4. Result traversal modul Klijentski kod treba da prolazi kroz elemente složene kolekcije bez poznavanja njene konkretne unutrašnje organizacije.
                    """.trimIndent(),
                    correctZone = "Iterator"
                )
            ),
            multiStep = MediorChoiceStepSeed(
                title = "Korak 2 - Multi-choice",
                instruction = "Izaberi sve tačne odgovore.",
                options = listOf(
                    """
                    Ako bi se situacija 1 rešavala kroz potpuno odvojene nepovezane implementacije bez zajedničkog kostura, sistem bi teže održavao konzistentan tok obrade.
                    """.trimIndent(),
                    """
                    Ako bi se situacija 2 rešavala Proxy obrascem, glavni dobitak bio bi prilagođavanje nekompatibilnog interfejsa više podsistema.
                    """.trimIndent(),
                    """
                    Ako bi se situacija 3 rešavala Bridge obrascem, to bi bio prirodan izbor zato što Bridge prvenstveno služi prilagođavanju postojećeg nekompatibilnog interfejsa.
                    """.trimIndent(),
                    "Ako bi se situacija 4 rešavala Composite obrascem, klijent bi automatski dobio odvojenu logiku prolaska kroz strukturu."
                ),
                correctAnswers = listOf(
                    """
                    Ako bi se situacija 1 rešavala kroz potpuno odvojene nepovezane implementacije bez zajedničkog kostura, sistem bi teže održavao konzistentan tok obrade.
                    """.trimIndent(),
                    "Ako bi se situacija 4 rešavala Composite obrascem, klijent bi automatski dobio odvojenu logiku prolaska kroz strukturu."
                )
            ),
            aiFollowUp = """
            Objasni razliku između problema „uprošćen pristup složenom podsistemu“ i problema „prilagođavanje nekompatibilnog interfejsa“.

            4. Četvrti tip zadatka: Izbor pristupa pod ograničenjima sistema
            """.trimIndent(),
            wave = 4,
            orderIndex = 36
        ),

        MediorSeedBuilders.mappingQuestion(
            questionId = "M3.9",
            title = "Poveži potrebe kreiranja, saradnje komponenti i kontrole pristupa sa odgovarajućim obrascima",
            prompt = """
            Za svaku situaciju izaberi najpogodniji obrazac.
            Situacije
            1. Notification channel creation modul
            Sistem podržava više tipova notifikacionih kanala, kao što su email, SMS i push. Klijentski kod treba da radi sa apstraktnim kanalom, dok se konkretan kanal kreira u zavisnosti od konfiguracije, bez menjanja koda koji ga koristi.
            2. Dialog coordination modul
            U dijalogu za rezervaciju više UI elemenata utiče jedni na druge. Promena jednog polja može promeniti dostupnost ili ponašanje drugih, ali tim želi da izbegne da svaka komponenta direktno poznaje sve ostale.
            3. Secure document access modul
            Pristup dokumentu treba kontrolisati tako da se pravi objekat učita tek kada je zaista potreban, uz mogućnost dodatne kontrole pristupa i evidentiranja korišćenja.
            4. Batch processing skeleton modul
            Sistem ima više tipova batch obrada. Sve uvek prolaze kroz isti opšti tok: učitavanje, validacija, obrada i finalizacija, ali se konkretni koraci razlikuju po tipu obrade.
            """.trimIndent(),
            zones = listOf(
                "Factory Method",
                "Mediator",
                "Proxy",
                "Template Method"
            ),
            items = listOf(
                MediorMappingItemSeed(
                    text = """
                    1. Notification channel creation modul Sistem podržava više tipova notifikacionih kanala, kao što su email, SMS i push. Klijentski kod treba da radi sa apstraktnim kanalom, dok se konkretan kanal kreira u zavisnosti od konfiguracije, bez menjanja koda koji ga koristi.
                    """.trimIndent(),
                    correctZone = "Factory Method"
                ),
                MediorMappingItemSeed(
                    text = """
                    2. Dialog coordination modul U dijalogu za rezervaciju više UI elemenata utiče jedni na druge. Promena jednog polja može promeniti dostupnost ili ponašanje drugih, ali tim želi da izbegne da svaka komponenta direktno poznaje sve ostale.
                    """.trimIndent(),
                    correctZone = "Mediator"
                ),
                MediorMappingItemSeed(
                    text = """
                    3. Secure document access modul Pristup dokumentu treba kontrolisati tako da se pravi objekat učita tek kada je zaista potreban, uz mogućnost dodatne kontrole pristupa i evidentiranja korišćenja.
                    """.trimIndent(),
                    correctZone = "Proxy"
                ),
                MediorMappingItemSeed(
                    text = """
                    4. Batch processing skeleton modul Sistem ima više tipova batch obrada. Sve uvek prolaze kroz isti opšti tok: učitavanje, validacija, obrada i finalizacija, ali se konkretni koraci razlikuju po tipu obrade.
                    """.trimIndent(),
                    correctZone = "Template Method"
                )
            ),
            multiStep = MediorChoiceStepSeed(
                title = "Korak 2 - Multi-choice",
                instruction = "Izaberi sve tačne odgovore.",
                options = listOf(
                    """
                    Ako bi se situacija 1 rešavala centralnim grananjem u jednoj velikoj klasi za kreiranje svih kanala, vremenom bi rasla odgovornost jednog mesta za sve tipove objekata.
                    """.trimIndent(),
                    """
                    Ako bi se situacija 2 rešavala Observer obrascem, sva pravila saradnje između UI elemenata bi prirodno bila centralizovana na jednom mestu.
                    """.trimIndent(),
                    """
                    Ako bi se situacija 3 rešavala Adapter obrascem, sistem bi prirodno dobio odloženo učitavanje i kontrolu pristupa resursu.
                    """.trimIndent(),
                    """
                    Ako bi se situacija 4 rešavala potpuno odvojenim implementacijama bez zajedničkog kostura, bilo bi teže održati konzistentan tok obrade kroz sve tipove batch procesa.
                    """.trimIndent()
                ),
                correctAnswers = listOf(
                    """
                    Ako bi se situacija 1 rešavala centralnim grananjem u jednoj velikoj klasi za kreiranje svih kanala, vremenom bi rasla odgovornost jednog mesta za sve tipove objekata.
                    """.trimIndent(),
                    """
                    Ako bi se situacija 4 rešavala potpuno odvojenim implementacijama bez zajedničkog kostura, bilo bi teže održati konzistentan tok obrade kroz sve tipove batch procesa.
                    """.trimIndent()
                )
            ),
            aiFollowUp = "Zašto je u situaciji 2 veći problem koordinacija međuzavisnih elemenata nego samo slanje obaveštenja između njih?",
            wave = 5,
            orderIndex = 45
        ),

        MediorSeedBuilders.mappingQuestion(
            questionId = "M3.10",
            title = "Poveži potrebe kloniranja, sastavljanja, promenljivih pravila i rada sa hijerarhijom sa odgovarajućim obrascima",
            prompt = """
            Za svaku situaciju izaberi najpogodniji obrazac.
            Situacije
            1. Starter template modul
            Editor sadrži unapred pripremljene šablone kartica i sekcija koje korisnik često umnožava i zatim blago prilagođava. Tim želi da se novi elementi dobijaju kopiranjem postojećih uzoraka, a ne ručnim pravljenjem od nule.
            2. Configurable report modul
            Sistem pravi složene izveštaje koji mogu imati različite opcione sekcije, kao što su summary, grafikoni, tabele i komentari. Potrebna je kontrolisana izgradnja objekta bez eksplozije konstruktora.
            3. Dynamic pricing modul
            Pravila obračuna zavise od regiona, tipa korisnika, aktivne kampanje i opterećenja sistema. Tim želi da menja pricing pravila bez izmene glavnog toka naplate.
            4. Page composition modul
            Stranica se sastoji od pojedinačnih elemenata i grupa elemenata, a klijentski kod treba da primenjuje iste operacije nad oba slučaja bez posebne logike za razlikovanje listova i grupa.
            """.trimIndent(),
            zones = listOf(
                "Prototype",
                "Builder",
                "Strategy",
                "Composite"
            ),
            items = listOf(
                MediorMappingItemSeed(
                    text = """
                    1. Starter template modul Editor sadrži unapred pripremljene šablone kartica i sekcija koje korisnik često umnožava i zatim blago prilagođava. Tim želi da se novi elementi dobijaju kopiranjem postojećih uzoraka, a ne ručnim pravljenjem od nule.
                    """.trimIndent(),
                    correctZone = "Prototype"
                ),
                MediorMappingItemSeed(
                    text = """
                    2. Configurable report modul Sistem pravi složene izveštaje koji mogu imati različite opcione sekcije, kao što su summary, grafikoni, tabele i komentari. Potrebna je kontrolisana izgradnja objekta bez eksplozije konstruktora.
                    """.trimIndent(),
                    correctZone = "Builder"
                ),
                MediorMappingItemSeed(
                    text = """
                    3. Dynamic pricing modul Pravila obračuna zavise od regiona, tipa korisnika, aktivne kampanje i opterećenja sistema. Tim želi da menja pricing pravila bez izmene glavnog toka naplate.
                    """.trimIndent(),
                    correctZone = "Strategy"
                ),
                MediorMappingItemSeed(
                    text = """
                    4. Page composition modul Stranica se sastoji od pojedinačnih elemenata i grupa elemenata, a klijentski kod treba da primenjuje iste operacije nad oba slučaja bez posebne logike za razlikovanje listova i grupa.
                    """.trimIndent(),
                    correctZone = "Composite"
                )
            ),
            multiStep = MediorChoiceStepSeed(
                title = "Korak 2 - Multi-choice",
                instruction = "Izaberi sve tačne odgovore.",
                options = listOf(
                    """
                    Ako bi se situacija 1 rešavala tako što se svaki novi element ručno konstruiše od nule, izgubila bi se prednost brzog umnožavanja gotovih uzoraka.
                    """.trimIndent(),
                    """
                    Ako bi se situacija 2 rešavala Abstract Factory obrascem, glavni dobitak bio bi uniforman rad nad pojedinačnim i grupnim elementima iste strukture.
                    """.trimIndent(),
                    """
                    Ako bi se situacija 3 rešavala velikim servisom sa rastućim grananjem, pojedinačna pricing pravila bi bila teže izolovati i testirati.
                    """.trimIndent(),
                    """
                    Ako bi se situacija 4 rešavala Iterator obrascem, klijent bi prirodno dobio model u kome i grupa i pojedinačni element dele isti strukturni interfejs.
                    """.trimIndent()
                ),
                correctAnswers = listOf(
                    """
                    Ako bi se situacija 1 rešavala tako što se svaki novi element ručno konstruiše od nule, izgubila bi se prednost brzog umnožavanja gotovih uzoraka.
                    """.trimIndent(),
                    """
                    Ako bi se situacija 3 rešavala velikim servisom sa rastućim grananjem, pojedinačna pricing pravila bi bila teže izolovati i testirati.
                    """.trimIndent()
                )
            ),
            aiFollowUp = """
            Objasni razliku između problema „pravljenje objekta kroz više koraka“ i problema „kopiranje već pripremljenog uzorka“.

            4. Četvrti tip zadatka: Izbor pristupa pod ograničenjima sistema
            """.trimIndent(),
            wave = 5,
            orderIndex = 46
        )
    )
}
