package com.example.pmuprojekat.data.seed.architect

import com.example.pmuprojekat.core.model.LearningLevel
import com.example.pmuprojekat.core.model.QuestionType
import com.example.pmuprojekat.data.seed.SeedQuestion

object ArchitectStyleSeed {
    val questions: List<SeedQuestion> = listOf(
        SeedQuestion(
                    questionId = "A2.1",
                    level = LearningLevel.ARCHITECT.id,
                    type = QuestionType.ARCHITECTURE_STYLE.id,
                    title = "Arhitektura za platformu koja povezuje više servisa za rezervaciju putovanja",
                    prompt = """
                    Kompanija razvija platformu koja korisnicima omogućava da na jednom mestu pretražuju i rezervišu putovanja.
                    Platforma treba da se integriše sa različitim spoljnim sistemima:
                    • avio-kompanijama;
                    • hotelima;
                    • rent-a-car servisima;
                    • payment providerima;
                    • servisima za putno osiguranje;
                    • sistemima za izdavanje vaučera i potvrda rezervacije.
                    Svaki spoljni partner ima drugačiji API, drugačije formate grešaka, drugačija pravila otkazivanja i drugačiju pouzdanost.
                    Zahtevi:
                    • osnovna poslovna logika rezervacije ne sme zavisiti od konkretnog API-ja jednog partnera;
                    • sistem mora omogućiti zamenu payment providera bez prepisivanja celog booking toka;
                    • različiti hoteli i avio-kompanije mogu imati različite integracione protokole;
                    • poslovna pravila za rezervaciju, otkazivanje i refundaciju treba da ostanu u centralnom domenskom sloju;
                    • integracije sa partnerima treba testirati odvojeno od osnovne poslovne logike;
                    • sistem treba da podrži dodavanje novih partnera bez velikog rizika po postojeći kod;
                    • korisnički interfejs, partnerski API-ji i baza podataka ne treba da direktno oblikuju poslovni model rezervacije.
                    Prioritetni kvalitetni atributi:
                    • održivost
                    • testabilnost
                    • zamenljivost eksternih servisa
                    • jasne granice domenske logike
                    • otpornost na promene partnerskih API-ja
                    • smanjenje zavisnosti od infrastrukture i konkretnih providera
                    """.trimIndent(),
                    aiFollowUp = """
                    Ako payment provider promeni API za refundaciju, kako bi hexagonal arhitektura trebalo da ograniči posledice te promene tako da se ne menja osnovna poslovna logika rezervacije i otkazivanja?
                    """.trimIndent(),
                    wave = 1,
                    difficulty = "expert",
                    orderIndex = 2,
                    estimatedMinutes = 10,
                    steps = listOf(
                    ArchitectSeedBuilders.singleChoiceStep(
                        questionId = "A2.1",
                        stepNumber = 1,
                        title = "Izaberi najpogodniji arhitektonski pristup",
                        instruction = "Izaberi arhitektonski stil koji najbolje odgovara zahtevima i kvalitetnim atributima.",
                        options = listOf(
                            """
                            A. Hexagonal / Ports and Adapters arhitektura
                            Poslovna logika rezervacije se nalazi u jezgru sistema, dok se baze, payment provideri, avio-kompanije, hoteli i korisnički interfejs povezuju preko jasno definisanih portova i adaptera.
                            """.trimIndent(),
                            """
                            B. Direktna integraciona arhitektura iz kontrolera ka svim partnerima
                            Backend kontroleri direktno pozivaju API-je hotela, avio-kompanija, payment providera i osiguranja, kako bi tok bio što kraći i jednostavniji.
                            """.trimIndent(),
                            """
                            C. Database-centric arhitektura
                            Baza podataka postaje centralni deo sistema, a poslovna pravila, integracije i statusi rezervacija organizuju se oko zajedničkih tabela i procedura.
                            """.trimIndent(),
                            """
                            D. Frontend-orchestrated arhitektura
                            Mobilna/web aplikacija direktno poziva različite partnerske API-je i sklapa korisnički tok rezervacije na klijentskoj strani.
                            """.trimIndent(),
                            """
                            E. Jedan veliki integration service za sve partnere
                            Svi pozivi ka hotelima, avio-kompanijama, plaćanjima i osiguranju smeštaju se u jedan veliki servis koji sadrži i poslovna pravila i tehničke detalje integracija.
                            """.trimIndent()
                        ),
                        correctAnswer = """
                    A. Hexagonal / Ports and Adapters arhitektura
                    Poslovna logika rezervacije se nalazi u jezgru sistema, dok se baze, payment provideri, avio-kompanije, hoteli i korisnički interfejs povezuju preko jasno definisanih portova i adaptera.
                    """.trimIndent()
                    ),
                    ArchitectSeedBuilders.categoryStep(
                        questionId = "A2.1",
                        stepNumber = 2,
                        title = "Razvrstaj arhitektonske elemente po ulozi",
                        instruction = "Prevuci elemente u odgovarajuće arhitektonske zone.",
                        categories = listOf(
                            ArchitectCategorySeed(
                                title = "Domensko jezgro",
                                items = listOf(
                                    "Booking Domain Core"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Ulazni adapteri",
                                items = listOf(
                                    "Web/Mobile API Adapter"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Izlazni portovi",
                                items = listOf(
                                    "Payment Port",
                                    "Booking Repository Port"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Izlazni adapteri",
                                items = listOf(
                                    "Hotel Provider Adapter",
                                    "Airline Provider Adapter",
                                    "Payment Provider Adapter",
                                    "SQL Booking Repository Adapter"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Poslovna pravila rezervacije i refundacije",
                                items = listOf(
                                    "Reservation Rules",
                                    "Refund Policy Service"
                                )
                            )
                        )
                    ),
                    ArchitectSeedBuilders.multiChoiceStep(
                        questionId = "A2.1",
                        stepNumber = 3,
                        title = "Izaberi 3 posledice izabranog pristupa",
                        instruction = "Izaberi posledice koje najbolje prate izabrani arhitektonski stil.",
                        options = listOf(
                            """
                            poslovna logika rezervacije može se testirati bez stvarnih poziva ka hotelima, avio-kompanijama i payment providerima
                            """.trimIndent(),
                            "zamena eksternog providera uglavnom zahteva novi adapter, a ne prepisivanje domenskog jezgra",
                            "baza podataka i partnerski API-ji ne treba da diktiraju strukturu osnovnog booking modela",
                            "svaki kontroler treba direktno da zna detalje API-ja svih partnera kako bi tok bio brži",
                            "domenska pravila treba preseliti u adaptere jer oni najbolje znaju kako partneri rade",
                            "frontend treba da odlučuje koji partner i payment provider se koriste u svakom koraku"
                        ),
                        correctAnswers = listOf(
                            """
                            poslovna logika rezervacije može se testirati bez stvarnih poziva ka hotelima, avio-kompanijama i payment providerima
                            """.trimIndent(),
                            "zamena eksternog providera uglavnom zahteva novi adapter, a ne prepisivanje domenskog jezgra",
                            "baza podataka i partnerski API-ji ne treba da diktiraju strukturu osnovnog booking modela"
                        )
                    ),
                    ArchitectSeedBuilders.freeTextStep(
                        questionId = "A2.1",
                        stepNumber = 4,
                        title = "Kratko obrazloženje",
                        instruction = "Objasni zašto izabrani stil čuva granice odgovornosti, zamenljivost i testabilnost."
                    )
                )
                ),
        SeedQuestion(
                    questionId = "A2.2",
                    level = LearningLevel.ARCHITECT.id,
                    type = QuestionType.ARCHITECTURE_STYLE.id,
                    title = "Arhitektura za platformu koja podržava različite proverivače akademskih radova",
                    prompt = """
                    Univerzitet razvija platformu za tehničku i sadržinsku proveru akademskih radova pre predaje.
                    Platforma treba da podrži različite vrste provera:
                    • proveru formatiranja rada;
                    • proveru citiranja i bibliografije;
                    • proveru plagijarizma;
                    • proveru upotrebe AI-generisanog teksta;
                    • proveru strukture naučnog rada;
                    • proveru usklađenosti sa pravilima različitih fakulteta;
                    • proveru jezika i stila;
                    • kasnije dodavanje novih proverivača bez menjanja osnovnog sistema.
                    Različiti fakulteti imaju različita pravila. Neki koriste APA stil, neki IEEE, neki imaju sopstvena tehnička uputstva. Takođe, pojedini proverivači mogu biti interni moduli, dok drugi mogu koristiti spoljne servise.
                    Zahtevi:
                    • osnovna platforma treba da upravlja učitavanjem rada, korisnicima, rezultatima i izveštajem;
                    • pojedinačni proverivači treba da budu zamenljivi i proširivi;
                    • novi proverivač treba dodati bez promene jezgra platforme;
                    • fakultet može uključiti ili isključiti određene provere;
                    • različiti proverivači mogu imati različitu implementaciju, ali moraju vratiti rezultat u zajedničkom formatu;
                    • greška jednog proverivača ne sme oboriti celu platformu;
                    • osnovna platforma ne treba da zna sve detalje svakog pravila citiranja ili svakog spoljnog servisa.
                    Prioritetni kvalitetni atributi:
                    • proširivost
                    • modularnost
                    • izolacija dodataka
                    • konfigurabilnost po fakultetu
                    • održivost
                    • stabilno jezgro sistema
                    """.trimIndent(),
                    aiFollowUp = """
                    Ako novi plugin za proveru citiranja počne da vraća rezultate u drugačijem formatu od ostalih proverivača, koji deo arhitekture treba da spreči da to pokvari objedinjeni izveštaj i zašto?
                    """.trimIndent(),
                    wave = 2,
                    difficulty = "expert",
                    orderIndex = 8,
                    estimatedMinutes = 10,
                    steps = listOf(
                    ArchitectSeedBuilders.singleChoiceStep(
                        questionId = "A2.2",
                        stepNumber = 1,
                        title = "Izaberi najpogodniji arhitektonski pristup",
                        instruction = "Izaberi arhitektonski stil koji najbolje odgovara zahtevima i kvalitetnim atributima.",
                        options = listOf(
                            """
                            A. Microkernel / Plugin arhitektura
                            Platforma ima stabilno jezgro koje upravlja korisnicima, dokumentima, konfiguracijom i objedinjavanjem rezultata, dok se pojedinačne provere dodaju kao plugin moduli sa jasno definisanim ugovorom.
                            """.trimIndent(),
                            """
                            B. Jedan veliki monolit sa svim proverama u istom sloju
                            Sve provere, pravila fakulteta, spoljne integracije i generisanje izveštaja implementiraju se u jednom zajedničkom kodu bez posebnog plugin modela.
                            """.trimIndent(),
                            """
                            C. Posebna aplikacija za svaki fakultet
                            Svaki fakultet dobija sopstvenu kopiju sistema sa sopstvenim proverama, pravilima i kodom.
                            """.trimIndent(),
                            """
                            D. Frontend-driven proveravanje
                            Web aplikacija odlučuje koje provere se izvršavaju i direktno poziva module ili spoljne servise za proveru rada.
                            """.trimIndent(),
                            """
                            E. Jedan AI servis koji zamenjuje sve proverivače
                            Svi aspekti rada šalju se jednom AI servisu koji daje ukupnu ocenu bez potrebe za posebnim proverivačima i pravilima.
                            """.trimIndent()
                        ),
                        correctAnswer = """
                    A. Microkernel / Plugin arhitektura
                    Platforma ima stabilno jezgro koje upravlja korisnicima, dokumentima, konfiguracijom i objedinjavanjem rezultata, dok se pojedinačne provere dodaju kao plugin moduli sa jasno definisanim ugovorom.
                    """.trimIndent()
                    ),
                    ArchitectSeedBuilders.categoryStep(
                        questionId = "A2.2",
                        stepNumber = 2,
                        title = "Razvrstaj arhitektonske elemente po ulozi",
                        instruction = "Prevuci elemente u odgovarajuće arhitektonske zone.",
                        categories = listOf(
                            ArchitectCategorySeed(
                                title = "Jezgro platforme",
                                items = listOf(
                                    "Platform Core",
                                    "Document Upload Module"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Upravljanje konfiguracijom i dostupnim pluginovima",
                                items = listOf(
                                    "Faculty Configuration Service",
                                    "Plugin Registry"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Izolovano izvršavanje provera",
                                items = listOf(
                                    "Plugin Execution Sandbox"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Plugin proverivači",
                                items = listOf(
                                    "Formatting Checker Plugin",
                                    "Citation Checker Plugin",
                                    "Plagiarism Checker Plugin",
                                    "AI-Text Risk Checker Plugin"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Standardizacija i objedinjavanje rezultata",
                                items = listOf(
                                    "Plugin Result Contract",
                                    "Unified Report Generator"
                                )
                            )
                        )
                    ),
                    ArchitectSeedBuilders.multiChoiceStep(
                        questionId = "A2.2",
                        stepNumber = 3,
                        title = "Izaberi 3 posledice izabranog pristupa",
                        instruction = "Izaberi posledice koje najbolje prate izabrani arhitektonski stil.",
                        options = listOf(
                            "novi proverivač se može dodati kao plugin ako poštuje ugovor za ulaz, izlaz i greške",
                            "jezgro platforme ostaje stabilnije jer ne mora da sadrži detalje svake pojedinačne provere",
                            "različiti fakulteti mogu imati različite skupove aktivnih pluginova kroz konfiguraciju",
                            "svaki plugin treba direktno da menja bazu korisnika i konačni status rada",
                            "frontend treba da odlučuje kako se tumače rezultati svakog plugin-a",
                            "greška u jednom plugin-u treba automatski da prekine ceo proces i obriše prethodne rezultate"
                        ),
                        correctAnswers = listOf(
                            "novi proverivač se može dodati kao plugin ako poštuje ugovor za ulaz, izlaz i greške",
                            "jezgro platforme ostaje stabilnije jer ne mora da sadrži detalje svake pojedinačne provere",
                            "različiti fakulteti mogu imati različite skupove aktivnih pluginova kroz konfiguraciju"
                        )
                    ),
                    ArchitectSeedBuilders.freeTextStep(
                        questionId = "A2.2",
                        stepNumber = 4,
                        title = "Kratko obrazloženje",
                        instruction = "Objasni zašto izabrani stil čuva granice odgovornosti, zamenljivost i testabilnost."
                    )
                )
                ),
        SeedQuestion(
                    questionId = "A2.3",
                    level = LearningLevel.ARCHITECT.id,
                    type = QuestionType.ARCHITECTURE_STYLE.id,
                    title = "Arhitektura za sistem digitalnog lanca snabdevanja hranom",
                    prompt = """
                    Kompanija razvija sistem za praćenje hrane od proizvođača do prodavnice.
                    Sistem treba da prati:
                    •	farmu ili proizvođača; 
                    •	seriju proizvoda; 
                    •	transport; 
                    •	skladištenje; 
                    •	temperaturu tokom transporta; 
                    •	ulazak u distributivni centar; 
                    •	isporuku maloprodaji; 
                    •	eventualno povlačenje proizvoda iz prodaje. 

                    Zahtevi:
                    •	svaki učesnik u lancu dodaje događaje o proizvodu; 
                    •	istorija kretanja mora biti proverljiva; 
                    •	ne sme se izgubiti trag o tome ko je i kada uneo podatak; 
                    •	korisnik u prodavnici može skenirati QR kod i videti poreklo proizvoda; 
                    •	interni timovi moraju brzo pronaći sve serije koje su prošle kroz rizično skladište; 
                    •	neki učesnici šalju podatke sa kašnjenjem; 
                    •	sistem mora podržati različite prikaze: javni prikaz za potrošače, regulatorni prikaz i interni operativni prikaz. 
                    Prioritetni kvalitetni atributi:
                    •	proverljivost 
                    •	integritet istorije 
                    •	različiti read modeli 
                    •	eventualna konzistentnost 
                    •	skalabilna pretraga po serijama i događajima
                    """.trimIndent(),
                    aiFollowUp = """
                    Ako partner naknadno pošalje ispravku za temperaturu transporta, da li treba izmeniti stari događaj ili dodati korektivni događaj? Objasni posledice po audit, regulatorni prikaz i poverenje u sistem.
                    """.trimIndent(),
                    wave = 3,
                    difficulty = "expert",
                    orderIndex = 14,
                    estimatedMinutes = 10,
                    steps = listOf(
                    ArchitectSeedBuilders.singleChoiceStep(
                        questionId = "A2.3",
                        stepNumber = 1,
                        title = "Izaberi najpogodniji arhitektonski pristup",
                        instruction = "Izaberi arhitektonski stil koji najbolje odgovara zahtevima i kvalitetnim atributima.",
                        options = listOf(
                            """
                            A. Event-centric arhitektura sa nepromenljivim događajima i projekcijama
                            Svaki korak u lancu beleži se kao događaj, a različiti prikazi se grade kao projekcije nad tim događajima.
                            """.trimIndent(),
                            """
                            B. Centralni CRUD sistem nad jednom tabelom proizvoda
                            Svaki proizvod ima trenutno stanje, a poslednji učesnik u lancu ažurira zapis.
                            """.trimIndent(),
                            """
                            C. Dokument baza sa jednim velikim dokumentom po proizvodu
                            Svi događaji, učesnici i metapodaci čuvaju se u jednom dokumentu koji se menja kroz vreme.
                            """.trimIndent(),
                            """
                            D. Search-first arhitektura
                            Svi podaci se primarno unose u search indeks kako bi pretraga po serijama i skladištima bila najbrža.
                            """.trimIndent(),
                            """
                            E. Periodični batch uvoz podataka od svih učesnika
                            Učesnici šalju fajlove jednom dnevno, a sistem iz njih pravi izveštaje i javne prikaze.
                            """.trimIndent()
                        ),
                        correctAnswer = """
                    A. Event-centric arhitektura sa nepromenljivim događajima i projekcijama
                    Svaki korak u lancu beleži se kao događaj, a različiti prikazi se grade kao projekcije nad tim događajima.
                    """.trimIndent()
                    ),
                    ArchitectSeedBuilders.categoryStep(
                        questionId = "A2.3",
                        stepNumber = 2,
                        title = "Razvrstaj arhitektonske elemente po ulozi",
                        instruction = "Prevuci elemente u odgovarajuće arhitektonske zone.",
                        categories = listOf(
                            ArchitectCategorySeed(
                                title = "Ulaz i validacija događaja",
                                items = listOf(
                                    "Partner Integration Gateway",
                                    "Event Validation Layer"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Izvor istorije",
                                items = listOf(
                                    "Product Event Log"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Javni prikaz",
                                items = listOf(
                                    "Consumer QR Read Model"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Regulatorni prikaz",
                                items = listOf(
                                    "Regulatory Audit View"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Operativna pretraga",
                                items = listOf(
                                    "Internal Traceability Search Model"
                                )
                            )
                        )
                    ),
                    ArchitectSeedBuilders.multiChoiceStep(
                        questionId = "A2.3",
                        stepNumber = 3,
                        title = "Izaberi 3 posledice izabranog pristupa",
                        instruction = "Izaberi posledice koje najbolje prate izabrani arhitektonski stil.",
                        options = listOf(
                            "moguće je rekonstruisati put proizvoda kroz lanac na osnovu istorije događaja",
                            "različiti prikazi mogu biti optimizovani za različite korisnike sistema",
                            "kašnjenje pojedinih partnera mora biti jasno modelovano kao zakašneli događaj",
                            "poslednje stanje proizvoda uvek je jedini podatak koji ima poslovnu vrednost",
                            "search indeks treba da postane izvor istine jer omogućava najbržu pretragu po serijama i skladištima",
                            "događaji se mogu slobodno menjati ako partner naknadno ispravi podatke"
                        ),
                        correctAnswers = listOf(
                            "moguće je rekonstruisati put proizvoda kroz lanac na osnovu istorije događaja",
                            "različiti prikazi mogu biti optimizovani za različite korisnike sistema",
                            "kašnjenje pojedinih partnera mora biti jasno modelovano kao zakašneli događaj"
                        )
                    ),
                    ArchitectSeedBuilders.freeTextStep(
                        questionId = "A2.3",
                        stepNumber = 4,
                        title = "Kratko obrazloženje",
                        instruction = "Objasni zašto izabrani stil čuva granice odgovornosti, zamenljivost i testabilnost."
                    )
                )
                ),
        SeedQuestion(
                    questionId = "A2.4",
                    level = LearningLevel.ARCHITECT.id,
                    type = QuestionType.ARCHITECTURE_STYLE.id,
                    title = "Arhitektura za matchmaking i lobby sistem u multiplayer igri",
                    prompt = """
                    Game studio razvija online multiplayer igru u kojoj igrači ulaze u mečeve od 5 do 20 minuta. Igra ima casual i ranked režim, različite regione, više tipova mečeva i sistem rangiranja igrača.
                    Sistem treba da podrži:
                    • veliki broj igrača koji istovremeno traže meč;
                    • grupisanje igrača po regionu, modu igre, nivou veštine i trenutnom opterećenju servera;
                    • formiranje lobby-ja pre početka meča;
                    • kratko čekanje na meč, ali bez potpuno nasumičnog spajanja igrača;
                    • mogućnost da se matchmaking pravila vremenom menjaju;
                    • razdvajanje procesa traženja meča od samog game servera na kome se meč igra;
                    • otpornost na nagle skokove broja igrača nakon update-a, turnira ili promocije;
                    • praćenje neuspelih pokušaja spajanja, otkazivanja i prosečnog vremena čekanja.
                    Prioritetni kvalitetni atributi:
                    • skalabilnost
                    • niska latencija za korisnički tok čekanja
                    • otpornost na skokove opterećenja
                    • fleksibilnost matchmaking pravila
                    • razdvajanje koordinacije igrača od izvršavanja igre
                    • observability nad kvalitetom spajanja igrača
                    """.trimIndent(),
                    aiFollowUp = """
                    Ako se posle novog update-a broj igrača u ranked modu naglo poveća, ali samo u jednom regionu, kako bi arhitektura trebalo da skalira matchmaking bez toga da nepotrebno povećava broj game servera u svim regionima?
                    """.trimIndent(),
                    wave = 4,
                    difficulty = "expert",
                    orderIndex = 20,
                    estimatedMinutes = 10,
                    steps = listOf(
                    ArchitectSeedBuilders.singleChoiceStep(
                        questionId = "A2.4",
                        stepNumber = 1,
                        title = "Izaberi najpogodniji arhitektonski pristup",
                        instruction = "Izaberi arhitektonski stil koji najbolje odgovara zahtevima i kvalitetnim atributima.",
                        options = listOf(
                            """
                            A. Event-driven matchmaking arhitektura sa queue-ovima, rule engine-om i odvojenim lobby servisom
                            Zahtevi za meč ulaze u matchmaking queue, pravila spajanja se primenjuju kroz poseban rule/matching sloj, lobby se formira odvojeno, a game server se rezerviše tek kada je meč spreman.
                            """.trimIndent(),
                            """
                            B. Game server kao centralni matchmaking servis
                            Svaki game server sam prima igrače, odlučuje koga će spojiti i istovremeno izvršava logiku meča.
                            """.trimIndent(),
                            """
                            C. Jedan sinhroni matchmaking API koji odmah vraća rezultat
                            Mobilna/desktop aplikacija šalje zahtev centralnom API-ju, a API u istom request-response pozivu pokušava odmah da pronađe sve igrače i vrati lobby.
                            """.trimIndent(),
                            """
                            D. Klasičan CRUD sistem sa tabelom “players_waiting”
                            Svi igrači koji traže meč upisuju se u jednu tabelu, a aplikacija periodično skenira tabelu i formira grupe.
                            """.trimIndent(),
                            """
                            E. Potpuno klijentski matchmaking
                            Klijentske aplikacije igrača same razmenjuju podatke, biraju odgovarajuće protivnike i zatim traže slobodan game server.
                            """.trimIndent()
                        ),
                        correctAnswer = """
                    A. Event-driven matchmaking arhitektura sa queue-ovima, rule engine-om i odvojenim lobby servisom
                    Zahtevi za meč ulaze u matchmaking queue, pravila spajanja se primenjuju kroz poseban rule/matching sloj, lobby se formira odvojeno, a game server se rezerviše tek kada je meč spreman.
                    """.trimIndent()
                    ),
                    ArchitectSeedBuilders.categoryStep(
                        questionId = "A2.4",
                        stepNumber = 2,
                        title = "Razvrstaj arhitektonske elemente po ulozi",
                        instruction = "Prevuci elemente u odgovarajuće arhitektonske zone.",
                        categories = listOf(
                            ArchitectCategorySeed(
                                title = "Ulaz igrača u matchmaking",
                                items = listOf(
                                    "Player Client",
                                    "Region Router",
                                    "Matchmaking Queue"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Pravila spajanja i signali o igraču",
                                items = listOf(
                                    "Matchmaking Rule Engine",
                                    "Player Skill/Rank Service"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Formiranje lobby-ja",
                                items = listOf(
                                    "Lobby Service"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Rezervacija i izvršavanje meča",
                                items = listOf(
                                    "Game Server Allocator",
                                    "Game Server Fleet"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Praćenje kvaliteta matchmaking-a",
                                items = listOf(
                                    "Matchmaking Metrics Collector"
                                )
                            )
                        )
                    ),
                    ArchitectSeedBuilders.multiChoiceStep(
                        questionId = "A2.4",
                        stepNumber = 3,
                        title = "Izaberi 3 posledice izabranog pristupa",
                        instruction = "Izaberi posledice koje najbolje prate izabrani arhitektonski stil.",
                        options = listOf(
                            "matchmaking se može skalirati i menjati nezavisnije od game servera koji izvršavaju mečeve",
                            "sistem mora pratiti vreme čekanja, neuspešna spajanja i kvalitet formiranih mečeva",
                            "pravila spajanja igrača mogu se menjati bez prebacivanja cele odgovornosti na klijentsku aplikaciju",
                            """
                            svaki game server treba sam da odlučuje koje će igrače spojiti kako bi se smanjila centralna koordinacija
                            """.trimIndent(),
                            "jedan sinhroni API poziv je dovoljan jer se matchmaking uvek završava trenutno",
                            "tabela igrača koji čekaju meč treba da bude jedini izvor koordinacije za sve regione i modove igre"
                        ),
                        correctAnswers = listOf(
                            "matchmaking se može skalirati i menjati nezavisnije od game servera koji izvršavaju mečeve",
                            "sistem mora pratiti vreme čekanja, neuspešna spajanja i kvalitet formiranih mečeva",
                            "pravila spajanja igrača mogu se menjati bez prebacivanja cele odgovornosti na klijentsku aplikaciju"
                        )
                    ),
                    ArchitectSeedBuilders.freeTextStep(
                        questionId = "A2.4",
                        stepNumber = 4,
                        title = "Kratko obrazloženje",
                        instruction = "Objasni zašto izabrani stil čuva granice odgovornosti, zamenljivost i testabilnost."
                    )
                )
                ),
        SeedQuestion(
                    questionId = "A2.5",
                    level = LearningLevel.ARCHITECT.id,
                    type = QuestionType.ARCHITECTURE_STYLE.id,
                    title = "Arhitektura za obradu satelitskih snimaka i izradu mapa promene terena",
                    prompt = """
                    Istraživački centar razvija sistem koji obrađuje satelitske snimke i pravi mape promena terena. Sistem treba da pomogne u praćenju urbanog širenja, seče šuma, promena vodostaja i posledica velikih vremenskih nepogoda.
                    Satelitski snimci dolaze u velikim fajlovima i moraju proći više faza obrade pre nego što postanu korisni za analizu.
                    Tipične faze obrade:
                    • prijem sirovog satelitskog snimka;
                    • provera formata i metapodataka;
                    • korekcija boja i atmosferskih smetnji;
                    • sečenje snimka na manje pločice;
                    • uklanjanje oblaka i neupotrebljivih delova;
                    • poređenje sa ranijim snimcima istog područja;
                    • generisanje mape promena;
                    • priprema slojeva za GIS prikaz;
                    • izrada izveštaja za istraživače i institucije.
                    Zahtevi:
                    • svaka faza obrade ima jasno definisan ulaz i izlaz;
                    • pojedine faze mogu biti zamenjene boljim algoritmom bez promene celog sistema;
                    • neke faze su CPU/GPU intenzivne i treba ih skalirati odvojeno;
                    • neuspeh jedne faze treba jasno prijaviti, a ne sakriti u ukupnom rezultatu;
                    • istraživači žele da vide u kojoj fazi je obrada snimka zapela;
                    • isti sirovi snimak može se ponovo obraditi kroz novu verziju pipeline-a;
                    • sistem treba da podrži različite pipeline konfiguracije za šume, vodostaje i urbana područja.
                    Prioritetni kvalitetni atributi:
                    • modularnost obrade
                    • zamenljivost faza
                    • praćenje toka obrade
                    • skaliranje zahtevnih faza
                    • reproduktivnost rezultata
                    • jasno razdvajanje ulaza i izlaza svake faze
                    """.trimIndent(),
                    aiFollowUp = """
                    Ako nova verzija algoritma za uklanjanje oblaka daje bolji rezultat, ali menja ulaz koji očekuje faza za detekciju promena, kako bi pipeline arhitektura trebalo da podrži zamenu te faze bez rušenja celog sistema?
                    """.trimIndent(),
                    wave = 5,
                    difficulty = "expert",
                    orderIndex = 26,
                    estimatedMinutes = 10,
                    steps = listOf(
                    ArchitectSeedBuilders.singleChoiceStep(
                        questionId = "A2.5",
                        stepNumber = 1,
                        title = "Izaberi najpogodniji arhitektonski pristup",
                        instruction = "Izaberi arhitektonski stil koji najbolje odgovara zahtevima i kvalitetnim atributima.",
                        options = listOf(
                            """
                            A. Pipe-and-Filter / Processing Pipeline arhitektura
                            Snimak prolazi kroz niz jasno definisanih faza obrade. Svaka faza ima poznat ulaz i izlaz, može se menjati ili skalirati odvojeno, a rezultat jedne faze postaje ulaz za narednu.
                            """.trimIndent(),
                            """
                            B. Jedan veliki Image Processing Service
                            Sva obrada, od prijema sirovog snimka do finalne mape, nalazi se u jednom servisu koji izvršava sve algoritme redom bez jasnih granica između faza.
                            """.trimIndent(),
                            """
                            C. CRUD aplikacija nad tabelom satelitskih snimaka
                            Sistem čuva snimke i njihove statuse u bazi, a obrada se pokreće kao pomoćna funkcija bez jasnog modela faza.
                            """.trimIndent(),
                            """
                            D. Frontend-driven obrada snimaka
                            Korisnička aplikacija odlučuje koje algoritme treba pokrenuti i sama kombinuje rezultate obrade.
                            """.trimIndent(),
                            """
                            E. Jedan AI model kao kompletna arhitektura
                            Sirovi satelitski snimak se šalje jednom modelu koji odmah vraća konačnu mapu promena bez posebnih faza, validacije i reproduktivnog toka.
                            """.trimIndent()
                        ),
                        correctAnswer = """
                    A. Pipe-and-Filter / Processing Pipeline arhitektura
                    Snimak prolazi kroz niz jasno definisanih faza obrade. Svaka faza ima poznat ulaz i izlaz, može se menjati ili skalirati odvojeno, a rezultat jedne faze postaje ulaz za narednu.
                    """.trimIndent()
                    ),
                    ArchitectSeedBuilders.categoryStep(
                        questionId = "A2.5",
                        stepNumber = 2,
                        title = "Razvrstaj arhitektonske elemente po ulozi",
                        instruction = "Prevuci elemente u odgovarajuće arhitektonske zone.",
                        categories = listOf(
                            ArchitectCategorySeed(
                                title = "Prijem i validacija ulaza",
                                items = listOf(
                                    "Raw Image Ingestion",
                                    "Metadata Validator"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Faze transformacije snimka",
                                items = listOf(
                                    "Atmospheric Correction Filter",
                                    "Tile Generation Filter",
                                    "Cloud Masking Filter",
                                    "Change Detection Filter",
                                    "GIS Layer Generator"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Orkestracija i praćenje toka",
                                items = listOf(
                                    "Pipeline Orchestrator",
                                    "Processing Status Tracker"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Čuvanje međurezultata",
                                items = listOf(
                                    "Intermediate Artifact Store"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Finalni rezultat i isporuka",
                                items = listOf(
                                    "Final Map Repository"
                                )
                            )
                        )
                    ),
                    ArchitectSeedBuilders.multiChoiceStep(
                        questionId = "A2.5",
                        stepNumber = 3,
                        title = "Izaberi 3 posledice izabranog pristupa",
                        instruction = "Izaberi posledice koje najbolje prate izabrani arhitektonski stil.",
                        options = listOf(
                            "svaka faza obrade može imati jasno definisan ulaz, izlaz i grešku",
                            "zahtevne faze, kao što su uklanjanje oblaka ili detekcija promena, mogu se skalirati odvojeno",
                            "isti sirovi snimak može se ponovo obraditi kroz drugu verziju pipeline-a radi reproduktivnosti",
                            "svi algoritmi treba da budu spojeni u jedan servis da bi se izbegli međurezultati",
                            "finalna mapa treba da bude jedini sačuvani rezultat jer međurezultati samo zauzimaju prostor",
                            "frontend treba da odlučuje redosled faza jer istraživač najbolje vidi šta mu treba"
                        ),
                        correctAnswers = listOf(
                            "svaka faza obrade može imati jasno definisan ulaz, izlaz i grešku",
                            "zahtevne faze, kao što su uklanjanje oblaka ili detekcija promena, mogu se skalirati odvojeno",
                            "isti sirovi snimak može se ponovo obraditi kroz drugu verziju pipeline-a radi reproduktivnosti"
                        )
                    ),
                    ArchitectSeedBuilders.freeTextStep(
                        questionId = "A2.5",
                        stepNumber = 4,
                        title = "Kratko obrazloženje",
                        instruction = "Objasni zašto izabrani stil čuva granice odgovornosti, zamenljivost i testabilnost."
                    )
                )
                )
    )
}
