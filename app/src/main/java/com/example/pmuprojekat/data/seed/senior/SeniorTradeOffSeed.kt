package com.example.pmuprojekat.data.seed.senior

import com.example.pmuprojekat.data.seed.SeedQuestion

object SeniorTradeOffSeed {
    val questions: List<SeedQuestion> = listOf(
        SeniorSeedBuilders.tradeOffQuestion(
            questionId = "S4.1",
            title = "Izaberi najprikladniji kompromis za pouzdano objavljivanje događaja nakon kreiranja porudžbine",
            prompt = """
            E-commerce sistem nakon uspešno kreirane porudžbine treba da obavesti druge delove sistema:
            •	servis za plaćanje, 
            •	servis za notifikacije, 
            •	sistem za logistiku, 
            •	analitiku prodaje, 
            •	korisnički portal. 
            Trenutno OrderService upisuje porudžbinu u bazu, a zatim direktno poziva nekoliko drugih servisa. Sistem radi, ali tim primećuje da kod kratkih prekida mreže ili pada zavisnog servisa nastaju teški slučajevi za obradu:
            •	porudžbina postoji u bazi, ali neki servisi nisu dobili informaciju; 
            •	ponavljanje direktnih poziva može dovesti do duplih efekata ako zavisni servis nije idempotentan; 
            •	checkout tok postaje osetljiv na dostupnost sistema koji nisu nužni za samo kreiranje porudžbine; 
            •	tim želi pouzdanije objavljivanje događaja bez toga da svaka sporedna integracija blokira osnovni poslovni upis.
            """.trimIndent(),
            solutionOptions = listOf(
                """
                A. Direktni sinhroni pozivi iz OrderService-a ka svim zavisnim servisima
                OrderService nakon upisa porudžbine odmah poziva sve servise koji treba da reaguju na novu porudžbinu. Tok je jednostavniji za praćenje, ali je checkout zavisan od dostupnosti više sistema.
                """.trimIndent(),
                """
                B. Običan queue publish odmah nakon upisa porudžbine
                OrderService upisuje porudžbinu, a zatim objavljuje događaj u queue. Time se smanjuje direktna zavisnost od drugih servisa, ali i dalje postoji rizik da upis u bazu uspe, a publish događaja ne uspe.
                """.trimIndent(),
                """
                C. Transactional outbox obrazac
                OrderService u istoj transakciji upisuje porudžbinu i outbox zapis o događaju. Poseban proces kasnije pouzdano objavljuje događaj ka queue sistemu, uz kontrolu ponovnog slanja i idempotentne potrošače.
                """.trimIndent()
            ),
            correctSolution = "C. Transactional outbox obrazac",
            gains = listOf(
                "pouzdanija veza između kreiranja porudžbine i objavljivanja događaja drugim sistemima",
                "checkout tok se manje oslanja na trenutnu dostupnost sporednih servisa",
                "lakše je kontrolisati ponovno slanje događaja bez gubitka poslovne namere"
            ),
            costs = listOf(
                "sistem dobija dodatni outbox storage i proces za objavljivanje događaja",
                "potrošači događaja moraju biti projektovani idempotentno zbog mogućih ponovljenih isporuka",
                "debagovanje toka postaje složenije jer se posledice porudžbine dešavaju asinhrono"
            ),
            aiFollowUp = """
            Zašto tehnički elegantnije rešenje nije automatski i najadekvatnije ako narušava granicu između kritičnog poslovnog toka i sporednih reakcija?

            """.trimIndent(),
            wave = 1,
            orderIndex = 4
        ),

        SeniorSeedBuilders.tradeOffQuestion(
            questionId = "S4.2",
            title = "Izaberi najprikladniji kompromis za pouzdan audit trag u javnom sistemu",
            prompt = """
            Javni informacioni sistem obrađuje predmete kroz više modula: prijem zahteva, obradu, odobravanje, promenu statusa i arhiviranje. Sistem mora da obezbedi audit trag za poslovno kritične radnje, jer se ti zapisi koriste za:
            •	internu kontrolu, 
            •	rešavanje žalbi, 
            •	pravnu proverljivost postupka, 
            •	inspekcijski nadzor, 
            •	rekonstrukciju odgovornosti korisnika.
            """.trimIndent(),
            solutionOptions = listOf(
                """
                A. Centralizovan audit servis za poslovno kritične događaje
                Svi moduli šalju standardizovane audit događaje u namenski servis koji obezbeđuje jedinstven format, politiku čuvanja, kontrolu pristupa i proverljivost zapisa.
                """.trimIndent(),
                """
                B. Lokalni audit po modulima uz zajednički audit schema standard
                Svaki modul sam upisuje audit događaje u svoj storage, ali svi koriste isti model događaja, iste obavezne atribute i centralno definisana pravila zadržavanja.
                """.trimIndent(),
                """
                C. Event streaming audit model sa naknadnom konsolidacijom u audit skladište
                Moduli emituju audit događaje u event stream, a posebni consumer-i ih obrađuju, validiraju i upisuju u centralno audit skladište.
                """.trimIndent()
            ),
            correctSolution = "A. Centralizovan audit servis za poslovno kritične događaje",
            gains = listOf(
                "jedinstven i proverljiv audit trag za kritične poslovne radnje",
                "lakša rekonstrukcija toka predmeta kroz više modula",
                "manji rizik da različiti moduli različito tumače šta mora biti auditovano"
            ),
            costs = listOf(
                "centralni audit servis postaje kritična komponenta koju treba pažljivo projektovati",
                "svi moduli moraju poštovati zajednički ugovor audit događaja",
                "potrebno je rešiti pouzdanu isporuku audit događaja bez blokiranja celog poslovnog toka"
            ),
            aiFollowUp = """
            Kada je centralizacija prihvatljiva cena, ako sistem mora da obezbedi pravno proverljiv i jedinstven trag poslovnih odluka?

            """.trimIndent(),
            wave = 2,
            orderIndex = 9
        ),

        SeniorSeedBuilders.tradeOffQuestion(
            questionId = "S4.3",
            title = "Izaberi najprikladniji kompromis za razdvajanje operativnog i analitičkog opterećenja",
            prompt = """
            Sistem za upravljanje akademskim procesima koristi jednu glavnu bazu za:
            •	upis studenata, 
            •	prijavu ispita, 
            •	unos ocena, 
            •	izveštaje za upravu i nastavnike. 
            Kako broj izveštaja raste, opterećenje na čitanju počinje da utiče na operativne tokove. Tim razmatra tri pristupa:
            """.trimIndent(),
            solutionOptions = listOf(
                """
                A. Optimizovati postojeću glavnu bazu i ograničiti najskuplje izveštaje
                Tim zadržava jednu bazu, uvodi bolje indekse, optimizuje najsporije upite i ograničava izvršavanje teških izveštaja u periodima najvećeg operativnog opterećenja.
                """.trimIndent(),
                """
                B. Uvesti read replica za izveštajne i analitičke upite
                Operativni upisi ostaju na glavnoj bazi, dok se izveštaji i analitička čitanja preusmeravaju na repliku koja može imati blago kašnjenje u odnosu na izvor.
                """.trimIndent(),
                """
                C. Uvesti poseban reporting store sa periodičnom sinhronizacijom
                Podaci potrebni za izveštaje se transformišu i upisuju u zaseban reporting model optimizovan za analitiku, umesto da izveštaji direktno čitaju operativnu šemu.
                """.trimIndent()
            ),
            correctSolution = "B. Uvesti read replica za izveštajne i analitičke upite",
            gains = listOf(
                "rasterećenje glavne baze za kritične write operacije",
                "bolje razdvajanje operativnog i izveštajnog čitanja",
                "relativno manja arhitektonska promena u odnosu na potpuno zaseban reporting store"
            ),
            costs = listOf(
                "podaci na replici mogu kasniti u odnosu na glavnu bazu",
                "monitoring, rutiranje upita i dijagnostika postaju složeniji",
                "tim mora jasno odrediti koji izveštaji smeju da čitaju blago zastarele podatke"
            ),
            aiFollowUp = """
            Zašto read replica često nije samo infrastrukturna odluka, već i odluka o tome koji delovi sistema smeju da žive sa blagim kašnjenjem podataka?

            """.trimIndent(),
            wave = 3,
            orderIndex = 14
        ),

        SeniorSeedBuilders.tradeOffQuestion(
            questionId = "S4.4",
            title = "Izaberi najprikladniji kompromis za zakazivanje poslova u velikom internom sistemu",
            prompt = """
            Veliki interni poslovni sistem ima više vrsta poslova:
            •	obračunske, 
            •	notifikacione, 
            •	integracione, 
            •	sinhronizacione, 
            •	izveštajne.
            """.trimIndent(),
            solutionOptions = listOf(
                """
                A. Centralna kontrola planiranja uz odvojene izvršne redove po klasi posla
                Sistem zadržava jedno mesto za pregled, pravila prioriteta i administraciju poslova, ali se izvršavanje razdvaja po klasama kao što su obračun, integracije, notifikacije i izveštaji.
                """.trimIndent(),
                """
                B. Jedan centralni scheduler sa prioritetima i limitima po tipu posla
                Svi poslovi ostaju u jednom scheduler-u, ali se uvode prioriteti, rate limit-i i kvote kako teži poslovi ne bi potpuno blokirali lakše ili važnije tokove.
                """.trimIndent(),
                """
                C. Posebni scheduler-i po domenima sa lokalnim pravilima izvršavanja
                Svaki domen dobija sopstveni scheduler, sopstvena pravila i veću nezavisnost u radu, uz cenu većeg operativnog broja komponenti.
                """.trimIndent()
            ),
            correctSolution = "A. Centralna kontrola planiranja uz odvojene izvršne redove po klasi posla",
            gains = listOf(
                "bolja izolacija opterećenja po klasi posla nego kod potpuno zajedničkog izvršavanja",
                "zadržava se jedinstven pregled nad rasporedom, statusima i pravilima prioriteta",
                "lakše je sprečiti da jedna klasa poslova, na primer izveštaji, ugrozi obračunske ili integracione tokove"
            ),
            costs = listOf(
                "dizajn je složeniji od jednog centralnog scheduler-a",
                "tim mora jasno definisati klase poslova, prioritete i granice izvršnih redova",
                "monitoring mora prikazivati i celinu sistema i stanje pojedinačnih redova"
            ),
            aiFollowUp = """
            Zašto samo uvođenje prioriteta u jednom scheduler-u možda nije dovoljno ako različite klase poslova imaju potpuno različite profile trajanja, važnosti i opterećenja?

            """.trimIndent(),
            wave = 4,
            orderIndex = 19
        ),

        SeniorSeedBuilders.tradeOffQuestion(
            questionId = "S4.5",
            title = "Izaberi najprikladniji kompromis za sistem sa više regulatornih procedura",
            prompt = """
            Velika organizacija ima više procedura koje prolaze kroz tokove odobravanja, provere i zatvaranja slučaja, ali se međusobno razlikuju po:
            •	pravilima eskalacije, 
            •	potrebnim odobrenjima, 
            •	granama odlučivanja, 
            •	regulatornim izuzecima, 
            •	rokovima i obaveznim audit koracima.
            """.trimIndent(),
            solutionOptions = listOf(
                """
                A. Konfigurabilan generički workflow engine sa strogo kontrolisanim DSL pravilima
                Svi procesi se modeluju kroz isti engine, ali se razlike izražavaju kroz formalno definisana pravila, validacije i konfiguracione šablone koje kontroliše centralni tim.
                """.trimIndent(),
                """
                B. Zasebni workflow tokovi po regulatornom domenu sa zajedničkim minimalnim standardima
                Svaki domen implementira sopstveni tok odobravanja i eskalacije, ali svi moraju poštovati zajedničke standarde za audit, statuse, rokove i izveštavanje.
                """.trimIndent(),
                """
                C. Zajednička workflow osnova sa ograničenim domenskim ekstenzijama
                Osnovni mehanizmi, poput statusa, audita, rokova i orkestracije, ostaju zajednički, dok se specifične domenske razlike dodaju kroz kontrolisane ekstenzije.
                """.trimIndent()
            ),
            correctSolution = "C. Zajednička workflow osnova sa ograničenim domenskim ekstenzijama",
            gains = listOf(
                "zadržavanje zajedničkih mehanizama kao što su audit, status tracking, rokovi i osnovna orkestracija",
                "manji rizik da generički engine postane previše apstraktan i pun izuzetaka",
                "bolja usklađenost sa domenskim razlikama nego kod strogo jedinstvenog modela"
            ),
            costs = listOf(
                "granica između zajedničkog i domenskog dela mora biti pažljivo definisana",
                "sistem je složeniji od potpuno jedinstvenog engine-a",
                "postoji rizik da timovi vremenom preterano prošire lokalne ekstenzije i oslabe konzistentnost osnove"
            ),
            aiFollowUp = """
            Zašto je kod workflow sistema često opasnije „prisilno ujednačiti različite procese“ nego održavati malo više kontrolisane raznolikosti?

            """.trimIndent(),
            wave = 5,
            orderIndex = 24
        ),

        SeniorSeedBuilders.tradeOffQuestion(
            questionId = "S4.6",
            title = "Izaberi najprikladniji kompromis za odziv i integritet u multiplayer igri",
            prompt = """
            Multiplayer igra ima brze borbene interakcije. Igrači očekuju da se kretanje i osnovne akcije osećaju trenutno, ali sistem mora da spreči varanje i očuva autoritativno stanje meča.
            """.trimIndent(),
            solutionOptions = listOf(
                """
                A. Strogo serverska potvrda akcija pre prikaza rezultata
                Server potvrđuje svaku važnu promenu stanja pre nego što je klijent prikaže. Time se čuva integritet, ali korisnik može osećati kašnjenje kod brzih akcija.
                """.trimIndent(),
                """
                B. Client-side prediction uz server reconciliation
                Klijent odmah prikazuje očekivani rezultat akcije, ali server ostaje autoritativan i naknadno ispravlja stanje ako postoji odstupanje.
                """.trimIndent(),
                """
                C. Lokalna simulacija na klijentu uz periodičnu serversku validaciju snapshot-a
                Klijent privremeno vodi deo simulacije radi boljeg odziva, a server periodično proverava snapshot-e i koriguje veća odstupanja.
                """.trimIndent()
            ),
            correctSolution = "B. Client-side prediction uz server reconciliation",
            gains = listOf(
                "bolji osećaj odziva za igrača nego kod čekanja svake serverske potvrde",
                "server i dalje ostaje autoritativan izvor istine za stanje meča",
                "sistem može da ublaži uticaj mrežne latencije bez potpunog odricanja od kontrole"
            ),
            costs = listOf(
                "implementacija je složenija zbog korekcija i usklađivanja stanja",
                "mogu se pojaviti vidljive korekcije pozicije ili akcije ako se klijent i server raziđu",
                "potrebno je pažljivo testirati granične slučajeve kod loše mreže i brzih promena stanja"
            ),
            aiFollowUp = """
            Zašto kod multiplayer igara nije dovoljno izabrati samo najbrži pristup, ako taj pristup smanjuje poverenje u autoritativno stanje meča?

            """.trimIndent(),
            wave = 6,
            orderIndex = 29
        ),

        SeniorSeedBuilders.tradeOffQuestion(
            questionId = "S4.7",
            title = "Izaberi najprikladniji kompromis za AI asistenta nad internim dokumentima",
            prompt = """
            Kompanija razvija AI asistenta koji odgovara na pitanja zaposlenih na osnovu internih pravilnika, procedura i tehničke dokumentacije. Dokumenti se često menjaju, a korisnici očekuju da odgovori budu zasnovani na najnovijim verzijama izvora.
            """.trimIndent(),
            solutionOptions = listOf(
                """
                A. Fine-tuning modela na internim dokumentima i terminologiji
                Model se dodatno trenira na internim materijalima kako bi bolje usvojio terminologiju, stil odgovora i tipične obrasce pitanja.
                """.trimIndent(),
                """
                B. RAG pristup sa retrieval-om relevantnih izvora pri svakom pitanju
                Sistem pre odgovora pronalazi najrelevantnije delove aktuelnih dokumenata i daje ih modelu kao kontekst.
                """.trimIndent(),
                """
                C. Long-context pristup sa selekcijom većeg paketa kandidata pre odgovora
                Sistem koristi širi kontekst i ubacuje veći skup potencijalno relevantnih dokumenata ili odeljaka, oslanjajući se na model da izdvoji šta je važno.
                """.trimIndent()
            ),
            correctSolution = "B. RAG pristup sa retrieval-om relevantnih izvora pri svakom pitanju",
            gains = listOf(
                "odgovori se mogu zasnivati na aktuelnijim dokumentima bez stalnog treniranja modela",
                "lakše je prikazati ili proveriti izvore na osnovu kojih je odgovor formiran",
                "sistem je pogodniji za sadržaj koji se često menja"
            ),
            costs = listOf(
                "kvalitet odgovora zavisi od kvaliteta retrieval-a i segmentacije dokumenata",
                "uvodi se dodatna složenost kroz indeksiranje, osvežavanje i evaluaciju izvora",
                "model može dati slab odgovor ako relevantan dokument nije pronađen ili je izvučen pogrešan kontekst"
            ),
            aiFollowUp = """
            Zašto kod AI asistenta nad često promenljivim dokumentima nije dovoljno da model “zna stil i terminologiju”, već mora imati pristup aktuelnom i proverljivom izvoru?

            """.trimIndent(),
            wave = 7,
            orderIndex = 34
        )
    )
}
