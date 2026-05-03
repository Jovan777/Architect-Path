package com.example.pmuprojekat.data.seed.junior

internal object JuniorReasoningSeed {
    val questions = listOf(
        JuniorSeedBuilders.reasoningQuestion(
            questionId = "J3.1",
            title = "Rezonovanje o Decorator obrascu",
            prompt = "Analiziraj svrhu, posledice i primenu Decorator obrasca.",
            steps = listOf(
                JuniorChoiceStepSeed(
                    title = "Korak 1 — svrha",
                    instruction = "Koja je osnovna svrha Decorator obrasca?",
                    options = listOf("da omogući samo jednu instancu klase", "da dinamički dodaje nove funkcionalnosti objektu", "da definiše familiju algoritama", "da čuva prethodno stanje objekta"),
                    correctAnswers = listOf("da dinamički dodaje nove funkcionalnosti objektu")
                ),
                JuniorChoiceStepSeed(
                    title = "Korak 2 — karakteristike i posledice",
                    instruction = "Izaberi tačne tvrdnje.",
                    options = listOf("Decorator koristi isti interfejs kao i objekat koji proširuje", "Decorator uvek menja originalnu klasu", "Više dekoratora može biti ulančano", "Decorator je dobra alternativa velikom broju podklasa"),
                    correctAnswers = listOf("Decorator koristi isti interfejs kao i objekat koji proširuje", "Više dekoratora može biti ulančano", "Decorator je dobra alternativa velikom broju podklasa")
                ),
                JuniorChoiceStepSeed(
                    title = "Korak 3 — situacija primene",
                    instruction = "U kojoj situaciji je Decorator najpogodniji?",
                    options = listOf("kada želiš da dodaš logovanje, keširanje ili enkripciju postojećem objektu", "kada želiš da objekt prolazi kroz više stanja", "kada želiš da biraš između SQL i NoSQL baze", "kada želiš da obilaziš listu elemenata"),
                    correctAnswers = listOf("kada želiš da dodaš logovanje, keširanje ili enkripciju postojećem objektu")
                )
            ),
            aiFollowUp = "Zašto bi nasleđivanje bilo lošije rešenje od Decorator pristupa u ovom primeru?",
            orderIndex = 301
        ),
        JuniorSeedBuilders.reasoningQuestion(
            questionId = "J3.2",
            title = "Rezonovanje o Abstract Factory obrascu",
            prompt = "Analiziraj svrhu, posledice i primenu Abstract Factory obrasca.",
            steps = listOf(
                JuniorChoiceStepSeed("Korak 1 — svrha", "Koja je osnovna svrha Abstract Factory obrasca?", listOf("da kreira porodicu povezanih objekata", "da pravi jednu instancu", "da pretvori akciju u objekat", "da čuva stanje objekta"), listOf("da kreira porodicu povezanih objekata")),
                JuniorChoiceStepSeed("Korak 2 — karakteristike i posledice", "Izaberi tačne tvrdnje.", listOf("Jedna fabrika može kreirati više povezanih proizvoda", "Klijent mora da zna sve konkretne klase proizvoda", "Obrazac pomaže konzistentnosti među povezanim objektima", "Pogodan je kada postoji više tema, stilova ili platformi"), listOf("Jedna fabrika može kreirati više povezanih proizvoda", "Obrazac pomaže konzistentnosti među povezanim objektima", "Pogodan je kada postoji više tema, stilova ili platformi")),
                JuniorChoiceStepSeed("Korak 3 — situacija primene", "Koji scenario najbolje odgovara Abstract Factory obrascu?", listOf("kreiranje Dark i Light UI porodica komponenti", "dodavanje enkripcije postojećoj poruci", "prolazak kroz kolekciju knjiga", "čuvanje snapshot-a dokumenta"), listOf("kreiranje Dark i Light UI porodica komponenti"))
            ),
            aiFollowUp = "Zašto je prednost što klijent radi sa fabrikom, a ne direktno sa konkretnim klasama dugmadi i prozora?",
            orderIndex = 302
        ),
        JuniorSeedBuilders.reasoningQuestion(
            questionId = "J3.3",
            title = "Rezonovanje o Chain of Responsibility obrascu",
            prompt = "Analiziraj svrhu, posledice i primenu Chain of Responsibility obrasca.",
            steps = listOf(
                JuniorChoiceStepSeed("Korak 1 — svrha", "Šta ovaj obrazac prvenstveno omogućava?", listOf("da više objekata redom dobija šansu da obradi zahtev", "da se algoritam bira u runtime-u", "da se pravi kopija objekta", "da se dinamički dodaju funkcionalnosti"), listOf("da više objekata redom dobija šansu da obradi zahtev")),
                JuniorChoiceStepSeed("Korak 2 — karakteristike i posledice", "Izaberi tačne tvrdnje.", listOf("Svaki handler može obraditi zahtev ili ga proslediti dalje", "Pošiljalac zahteva mora unapred znati koji handler će ga obraditi", "Lanac može smanjiti spregu između pošiljaoca i obrađivača", "Obrazac je koristan u validaciji i autorizaciji"), listOf("Svaki handler može obraditi zahtev ili ga proslediti dalje", "Lanac može smanjiti spregu između pošiljaoca i obrađivača", "Obrazac je koristan u validaciji i autorizaciji")),
                JuniorChoiceStepSeed("Korak 3 — situacija primene", "Koji scenario najbolje odgovara ovom obrascu?", listOf("autentifikacija → provera dozvola → validacija zahteva", "izbor jedne strategije popusta", "sastavljanje računara korak po korak", "kreiranje porodice UI komponenti"), listOf("autentifikacija → provera dozvola → validacija zahteva"))
            ),
            aiFollowUp = "Zašto je korisno da pošiljalac ne zavisi direktno od jednog konkretnog obrađivača?",
            orderIndex = 303
        ),
        JuniorSeedBuilders.reasoningQuestion(
            questionId = "J3.4",
            title = "Rezonovanje o Proxy obrascu",
            prompt = "Analiziraj svrhu, posledice i primenu Proxy obrasca.",
            steps = listOf(
                JuniorChoiceStepSeed("Korak 1 — svrha", "Koja je osnovna svrha Proxy obrasca?", listOf("da doda nove funkcionalnosti objektu", "da kontroliše pristup stvarnom objektu preko zastupnika", "da čuva stanje objekta", "da definiše kostur algoritma"), listOf("da kontroliše pristup stvarnom objektu preko zastupnika")),
                JuniorChoiceStepSeed("Korak 2 — karakteristike i posledice", "Izaberi tačne tvrdnje.", listOf("Proxy može odložiti kreiranje ili učitavanje pravog objekta", "Proxy mora uvek menjati originalnu klasu", "Proxy može služiti za kontrolu pristupa", "Proxy i klijent često koriste isti interfejs"), listOf("Proxy može odložiti kreiranje ili učitavanje pravog objekta", "Proxy može služiti za kontrolu pristupa", "Proxy i klijent često koriste isti interfejs")),
                JuniorChoiceStepSeed("Korak 3 — situacija primene", "Izaberi 2 tačna odgovora.", listOf("učitavanje velike slike tek kada je stvarno potrebna", "dodavanje više popusta u runtime-u", "kontrola pristupa udaljenom servisu", "kreiranje porodice UI komponenti"), listOf("učitavanje velike slike tek kada je stvarno potrebna", "kontrola pristupa udaljenom servisu"))
            ),
            aiFollowUp = "Po čemu se Proxy razlikuje od Decorator obrasca iako oba često „omotavaju” objekat?",
            orderIndex = 304
        ),
        JuniorSeedBuilders.reasoningQuestion(
            questionId = "J3.5",
            title = "Rezonovanje o Iterator obrascu",
            prompt = "Analiziraj svrhu, posledice i primenu Iterator obrasca.",
            steps = listOf(
                JuniorChoiceStepSeed("Korak 1 — svrha", "Koja je osnovna svrha Iterator obrasca?", listOf("da sekvencijalno prolazi kroz elemente kolekcije bez otkrivanja njene implementacije", "da deli stanje između mnogo objekata", "da centralizuje komunikaciju između UI komponenti", "da omogući jednu instancu klase"), listOf("da sekvencijalno prolazi kroz elemente kolekcije bez otkrivanja njene implementacije")),
                JuniorChoiceStepSeed("Korak 2 — karakteristike i posledice", "Izaberi tačne tvrdnje.", listOf("Iterator odvaja logiku prolaska od same kolekcije", "Klijent mora da zna unutrašnju strukturu kolekcije", "Metode poput hasNext() i next() su tipične", "Iterator može olakšati više različitih načina prolaska kroz istu kolekciju"), listOf("Iterator odvaja logiku prolaska od same kolekcije", "Metode poput hasNext() i next() su tipične", "Iterator može olakšati više različitih načina prolaska kroz istu kolekciju")),
                JuniorChoiceStepSeed("Korak 3 — situacija primene", "Izaberi 2 tačna odgovora.", listOf("prolazak kroz listu pesama bez znanja kako su interno sačuvane", "prelazak između stanja narudžbine", "obilazak stabla direktorijuma posebnim iteratorom", "kreiranje novih objekata kloniranjem"), listOf("prolazak kroz listu pesama bez znanja kako su interno sačuvane", "obilazak stabla direktorijuma posebnim iteratorom"))
            ),
            aiFollowUp = "Zašto je korisno odvojiti način prolaska kroz kolekciju od same kolekcije?",
            orderIndex = 305
        ),
        JuniorSeedBuilders.reasoningQuestion(
            questionId = "J3.6",
            title = "Rezonovanje o State obrascu",
            prompt = "Analiziraj svrhu, posledice i primenu State obrasca.",
            steps = listOf(
                JuniorChoiceStepSeed("Korak 1 — svrha", "Koja je osnovna svrha State obrasca?", listOf("da objekt menja ponašanje kada mu se promeni unutrašnje stanje", "da kreira porodice objekata", "da omogući undo", "da pretvori zahtev u objekat"), listOf("da objekt menja ponašanje kada mu se promeni unutrašnje stanje")),
                JuniorChoiceStepSeed("Korak 2 — karakteristike i posledice", "Izaberi tačne tvrdnje.", listOf("različita stanja mogu biti predstavljena posebnim klasama", "State se koristi za različite nezavisne algoritme koje korisnik bira", "smanjuje potrebu za velikim if-else granama vezanim za stanje", "ponašanje objekta zavisi od trenutnog internog stanja"), listOf("različita stanja mogu biti predstavljena posebnim klasama", "smanjuje potrebu za velikim if-else granama vezanim za stanje", "ponašanje objekta zavisi od trenutnog internog stanja")),
                JuniorChoiceStepSeed("Korak 3 — situacija primene", "Izaberi 2 tačna odgovora.", listOf("narudžbina se ponaša različito kada je kreirana, plaćena ili poslata", "korisnik bira između više algoritama sortiranja", "dokument se drugačije ponaša kada je draft, review ili published", "više objekata treba da reaguje na promenu jednog objekta"), listOf("narudžbina se ponaša različito kada je kreirana, plaćena ili poslata", "dokument se drugačije ponaša kada je draft, review ili published"))
            ),
            aiFollowUp = "Zašto bi klasičan pristup sa mnogo uslovnih grana bio lošiji kako sistem raste?",
            orderIndex = 306
        ),
        JuniorSeedBuilders.reasoningQuestion(
            questionId = "J3.7",
            title = "Rezonovanje o Mediator obrascu",
            prompt = "Analiziraj svrhu, posledice i primenu Mediator obrasca.",
            steps = listOf(
                JuniorChoiceStepSeed("Korak 1 — svrha", "Koja je osnovna svrha Mediator obrasca?", listOf("da centralizuje komunikaciju između više objekata", "da omogući prolazak kroz kolekciju", "da deli zajedničko stanje između objekata", "da kreira porodice povezanih objekata"), listOf("da centralizuje komunikaciju između više objekata")),
                JuniorChoiceStepSeed("Korak 2 — karakteristike i posledice", "Izaberi tačne tvrdnje.", listOf("Mediator smanjuje direktne zavisnosti između objekata", "Svi objekti uvek moraju direktno da znaju jedni za druge", "Pogodan je kada postoji mnogo međusobno povezanih UI elemenata", "Mediator je isto što i Observer, samo sa drugim imenom"), listOf("Mediator smanjuje direktne zavisnosti između objekata", "Pogodan je kada postoji mnogo međusobno povezanih UI elemenata")),
                JuniorChoiceStepSeed("Korak 3 — situacija primene", "Izaberi 2 tačna odgovora.", listOf("forma gde promena jednog polja menja dostupnost drugih polja", "sistem pretplate na promene cene proizvoda", "dijalog prozor gde dugmad, input polja i checkbox-i utiču jedni na druge", "kreiranje objekta kopiranjem postojećeg"), listOf("forma gde promena jednog polja menja dostupnost drugih polja", "dijalog prozor gde dugmad, input polja i checkbox-i utiču jedni na druge"))
            ),
            aiFollowUp = "Kada bi ovakav sistem bez Mediator obrasca postao težak za održavanje?",
            orderIndex = 307
        ),
        JuniorSeedBuilders.reasoningQuestion(
            questionId = "J3.8",
            title = "Rezonovanje o Visitor obrascu",
            prompt = "Analiziraj svrhu, posledice i primenu Visitor obrasca.",
            steps = listOf(
                JuniorChoiceStepSeed("Korak 1 — svrha", "Koja je osnovna svrha Visitor obrasca?", listOf("da doda novu operaciju nad postojećom strukturom objekata bez menjanja njihovih klasa", "da omogući samo jednu instancu klase", "da zameni više algoritama u runtime-u", "da sastavi složen objekat korak po korak"), listOf("da doda novu operaciju nad postojećom strukturom objekata bez menjanja njihovih klasa")),
                JuniorChoiceStepSeed("Korak 2 — karakteristike i posledice", "Izaberi tačne tvrdnje.", listOf("Visitor je koristan kada često dodaješ nove operacije nad istim tipovima elemenata", "Visitor je najbolji kada često menjaš samu strukturu elemenata", "Elementi obično imaju accept(visitor) metodu", "Operacije se izdvajaju iz samih klasa elemenata"), listOf("Visitor je koristan kada često dodaješ nove operacije nad istim tipovima elemenata", "Elementi obično imaju accept(visitor) metodu", "Operacije se izdvajaju iz samih klasa elemenata")),
                JuniorChoiceStepSeed("Korak 3 — situacija primene", "Izaberi 2 tačna odgovora.", listOf("nad AST stablom želiš posebno eksportovanje, validaciju i generisanje koda", "želiš da svaki objekat menja ponašanje u zavisnosti od internog stanja", "nad geometrijskim oblicima dodaješ operacije za eksport i računanje površine", "želiš da sakriješ složen podsistem iza jedne klase"), listOf("nad AST stablom želiš posebno eksportovanje, validaciju i generisanje koda", "nad geometrijskim oblicima dodaješ operacije za eksport i računanje površine"))
            ),
            aiFollowUp = "Zašto Visitor može biti dobar kada se operacije menjaju češće od strukture objekata?",
            orderIndex = 308
        ),
        JuniorSeedBuilders.reasoningQuestion(
            questionId = "J3.9",
            title = "Rezonovanje o Flyweight obrascu",
            prompt = "Analiziraj svrhu, posledice i primenu Flyweight obrasca.",
            steps = listOf(
                JuniorChoiceStepSeed("Korak 1 — svrha", "Koja je osnovna svrha Flyweight obrasca?", listOf("da smanji potrošnju memorije deljenjem zajedničkog stanja", "da čuva prethodno stanje objekta", "da kontroliše pristup objektu", "da pretvori akciju u objekat"), listOf("da smanji potrošnju memorije deljenjem zajedničkog stanja")),
                JuniorChoiceStepSeed("Korak 2 — karakteristike i posledice", "Izaberi tačne tvrdnje.", listOf("Flyweight razdvaja deljeno i spoljašnje stanje", "Svaki objekat mora čuvati kompletno stanje zasebno", "Koristan je kada postoji ogroman broj sličnih sitnih objekata", "Njegova primena može smanjiti memorijske troškove"), listOf("Flyweight razdvaja deljeno i spoljašnje stanje", "Koristan je kada postoji ogroman broj sličnih sitnih objekata", "Njegova primena može smanjiti memorijske troškove")),
                JuniorChoiceStepSeed("Korak 3 — situacija primene", "Izaberi 2 tačna odgovora.", listOf("hiljade karaktera u editoru dele iste font podatke", "svaki korisnik sistema mora imati potpuno nezavisno stanje sesije", "šuma sa ogromnim brojem stabala istog modela i teksture", "generisanje različitih popusta kroz više algoritama"), listOf("hiljade karaktera u editoru dele iste font podatke", "šuma sa ogromnim brojem stabala istog modela i teksture"))
            ),
            aiFollowUp = "Koji problem bi nastao kada bi svaki mali objekat čuvao kompletan skup istih podataka?",
            orderIndex = 309
        ),
        JuniorSeedBuilders.reasoningQuestion(
            questionId = "J3.10",
            title = "Rezonovanje o Bridge obrascu",
            prompt = "Analiziraj svrhu, posledice i primenu Bridge obrasca.",
            steps = listOf(
                JuniorChoiceStepSeed("Korak 1 — svrha", "Koja je osnovna svrha Bridge obrasca?", listOf("da razdvoji apstrakciju i implementaciju kako bi mogle nezavisno da se menjaju", "da čuva prethodno stanje objekta", "da jedan objekat obavesti više drugih", "da omogući jednu instancu klase"), listOf("da razdvoji apstrakciju i implementaciju kako bi mogle nezavisno da se menjaju")),
                JuniorChoiceStepSeed("Korak 2 — karakteristike i posledice", "Izaberi tačne tvrdnje.", listOf("Bridge je koristan kada postoje dve nezavisne dimenzije promena", "Bridge je isto što i Adapter jer oba povezuju dve klase", "Bridge smanjuje eksploziju broja klasa izazvanu kombinovanjem naslednih hijerarhija", "Apstrakcija obično sadrži referencu na implementora"), listOf("Bridge je koristan kada postoje dve nezavisne dimenzije promena", "Bridge smanjuje eksploziju broja klasa izazvanu kombinovanjem naslednih hijerarhija", "Apstrakcija obično sadrži referencu na implementora")),
                JuniorChoiceStepSeed("Korak 3 — situacija primene", "Izaberi 2 tačna odgovora.", listOf("sistem ima tipove poruka i kanale slanja koji treba da se šire nezavisno", "sistem treba da uklopi staru biblioteku sa nekompatibilnim interfejsom", "aplikacija ima vrste izveštaja i više renderer-a", "sistem treba da sačuva prethodno stanje dokumenta"), listOf("sistem ima tipove poruka i kanale slanja koji treba da se šire nezavisno", "aplikacija ima vrste izveštaja i više renderer-a"))
            ),
            aiFollowUp = "Po čemu se Bridge razlikuje od Adapter obrasca iako oba koriste kompoziciju?",
            orderIndex = 310
        ),
        JuniorSeedBuilders.reasoningQuestion(
            questionId = "J3.11",
            title = "Rezonovanje o Composite obrascu",
            prompt = "Analiziraj svrhu, posledice i primenu Composite obrasca.",
            steps = listOf(
                JuniorChoiceStepSeed("Korak 1 — svrha", "Koja je osnovna svrha Composite obrasca?", listOf("da omogući isti tretman za pojedinačne objekte i njihove grupe", "da kontroliše pristup objektu", "da pretvori zahtev u objekat", "da doda novu operaciju nad klasama bez izmene"), listOf("da omogući isti tretman za pojedinačne objekte i njihove grupe")),
                JuniorChoiceStepSeed("Korak 2 — karakteristike i posledice", "Izaberi tačne tvrdnje.", listOf("Composite je koristan za hijerarhijske strukture poput menija, foldera i stabala", "Composite zahteva da klijent stalno proverava da li radi sa grupom ili listom", "Leaf i Composite dele zajednički interfejs", "Composite objekat obično sadrži kolekciju elemenata istog osnovnog tipa"), listOf("Composite je koristan za hijerarhijske strukture poput menija, foldera i stabala", "Leaf i Composite dele zajednički interfejs", "Composite objekat obično sadrži kolekciju elemenata istog osnovnog tipa")),
                JuniorChoiceStepSeed("Korak 3 — situacija primene", "Izaberi 2 tačna odgovora.", listOf("renderovanje pojedinačne stavke menija i grupe stavki kroz isti API", "izbor jednog algoritma sortiranja iz više ponuđenih", "prikaz fajla i foldera kroz zajednički interfejs", "čuvanje snapshot-a stanja editora"), listOf("renderovanje pojedinačne stavke menija i grupe stavki kroz isti API", "prikaz fajla i foldera kroz zajednički interfejs"))
            ),
            aiFollowUp = "Zašto Composite pojednostavljuje rad klijenta u hijerarhijskim strukturama?",
            orderIndex = 311
        ),
        JuniorSeedBuilders.reasoningQuestion(
            questionId = "J3.12",
            title = "Rezonovanje o Memento obrascu",
            prompt = "Analiziraj svrhu, posledice i primenu Memento obrasca.",
            steps = listOf(
                JuniorChoiceStepSeed("Korak 1 — svrha", "Koja je osnovna svrha Memento obrasca?", listOf("da omogući vraćanje objekta na prethodno stanje bez otkrivanja unutrašnjih detalja", "da doda funkcionalnost objektu u runtime-u", "da prosleđuje zahtev kroz niz obrađivača", "da deli zajedničko stanje između objekata"), listOf("da omogući vraćanje objekta na prethodno stanje bez otkrivanja unutrašnjih detalja")),
                JuniorChoiceStepSeed("Korak 2 — karakteristike i posledice", "Izaberi tačne tvrdnje.", listOf("Originator pravi memento i može da vrati svoje stanje iz njega", "Caretaker treba da menja unutrašnji sadržaj mementa direktno", "Memento je koristan za undo funkcionalnost", "Caretaker obično čuva istoriju snapshot-a"), listOf("Originator pravi memento i može da vrati svoje stanje iz njega", "Memento je koristan za undo funkcionalnost", "Caretaker obično čuva istoriju snapshot-a")),
                JuniorChoiceStepSeed("Korak 3 — situacija primene", "Izaberi 2 tačna odgovora.", listOf("editor teksta treba da podrži undo", "forma ima mnogo međuzavisnih polja", "igrica treba da čuva save-state", "sistem treba da izračunava popust kroz više algoritama"), listOf("editor teksta treba da podrži undo", "igrica treba da čuva save-state"))
            ),
            aiFollowUp = "Zašto nije dobro da caretaker zna i menja internu strukturu sačuvanog stanja?",
            orderIndex = 312
        )
    )
}
