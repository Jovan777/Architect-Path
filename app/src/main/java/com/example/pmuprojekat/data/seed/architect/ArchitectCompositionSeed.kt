package com.example.pmuprojekat.data.seed.architect

import com.example.pmuprojekat.core.model.LearningLevel
import com.example.pmuprojekat.core.model.QuestionType
import com.example.pmuprojekat.data.seed.SeedQuestion

object ArchitectCompositionSeed {
    val questions: List<SeedQuestion> = listOf(
        SeedQuestion(
                    questionId = "A4.1",
                    level = LearningLevel.ARCHITECT.id,
                    type = QuestionType.ARCHITECTURE_COMPOSITION.id,
                    title = "Sastavi arhitekturu za aplikaciju za praćenje ličnih finansija",
                    prompt = """
                    Mobilna aplikacija pomaže korisnicima da prate lične finansije.
                    Zahtevi:
                    • korisnik unosi prihode i troškove;
                    • troškovi se kategorišu;
                    • korisnik vidi mesečni pregled potrošnje;
                    • aplikacija daje upozorenje ako korisnik brzo troši budžet;
                    • korisnik može dodati fotografiju računa;
                    • kasnije se planira automatsko prepoznavanje podataka sa računa;
                    • finansijski podaci moraju biti zaštićeni;
                    • analitički prikazi ne moraju uvek biti trenutno ažurni;
                    • osnovni unos troška mora ostati brz i pouzdan.
                    Dostupne komponente:
                    • Mobile App
                    • API Gateway
                    • Auth Service
                    • Transaction Service
                    • Budget Service
                    • Document/Receipt Storage
                    • Message Broker
                    • NoSQL Transaction Store
                    • Analytics Read Model
                    • Notification Service
                    • OCR/Receipt Processing Service
                    """.trimIndent(),
                    aiFollowUp = """
                    Ako OCR pogrešno pročita iznos sa računa, koji deo sistema sme da predloži izmenu, a koji deo mora ostati izvor istine za korisnikov finansijski zapis?
                    """.trimIndent(),
                    wave = 1,
                    difficulty = "expert",
                    orderIndex = 4,
                    estimatedMinutes = 10,
                    steps = listOf(
                    ArchitectSeedBuilders.multiChoiceStep(
                        questionId = "A4.1",
                        stepNumber = 1,
                        title = "Izaberi 9 komponenti za osnovnu arhitekturu",
                        instruction = "Izaberi komponente potrebne za prvu stabilnu produkcionu verziju.",
                        options = emptyList(),
                        correctAnswers = listOf(
                            "Mobile App",
                            "API Gateway",
                            "Auth Service",
                            "Transaction Service",
                            "Budget Service",
                            "Document/Receipt Storage",
                            "Message Broker",
                            "NoSQL Transaction Store",
                            "Analytics Read Model"
                        )
                    ),
                    ArchitectSeedBuilders.orderedCardsStep(
                        questionId = "A4.1",
                        stepNumber = 2,
                        title = "Sastavi glavni arhitektonski tok",
                        instruction = "Izaberi i poređaj kartice koje čine najispravniji glavni tok sistema.",
                        cards = listOf(
                            "Mobile App šalje zahtev kroz API Gateway",
                            "Auth Service proverava korisnika pre obrade zahteva",
                            "Transaction Service upisuje finansijski zapis u NoSQL Transaction Store",
                            "Document/Receipt Storage čuva sliku računa kao odvojeni artefakt",
                            "Message Broker prima događaj o novom trošku za naknadnu obradu",
                            "Analytics Read Model se ažurira asinhrono za mesečne preglede",
                            "Analytics Read Model prvo računa novi mesečni zbir, pa tek onda dozvoljava upis troška",
                            "OCR/Receipt Processing Service mora završiti pre nego što Transaction Service sačuva trošak",
                            """
                            Notification Service direktno menja budžetsko stanje ako proceni da korisnik brzo troši Tačan raspored: 1.	Mobile App šalje zahtev kroz API Gateway 2.	Auth Service proverava korisnika pre obrade zahteva 3.	Transaction Service upisuje finansijski zapis u NoSQL Transaction Store 4.	Document/Receipt Storage čuva sliku računa kao odvojeni artefakt 5.	Message Broker prima događaj o novom trošku za naknadnu obradu 6.	Analytics Read Model se ažurira asinhrono za mesečne preglede
                            """.trimIndent()
                        ),
                        correctOrder = listOf(
                            "Mobile App šalje zahtev kroz API Gateway",
                            "Auth Service proverava korisnika pre obrade zahteva",
                            "Transaction Service upisuje finansijski zapis u NoSQL Transaction Store",
                            "Document/Receipt Storage čuva sliku računa kao odvojeni artefakt",
                            "Message Broker prima događaj o novom trošku za naknadnu obradu",
                            "Analytics Read Model se ažurira asinhrono za mesečne preglede"
                        )
                    ),
                    ArchitectSeedBuilders.multiChoiceStep(
                        questionId = "A4.1",
                        stepNumber = 3,
                        title = "Izaberi 3 ključna arhitektonska pravila",
                        instruction = "Izaberi pravila koja najviše čuvaju pouzdanost, odgovornosti i granice sistema.",
                        options = listOf(
                            "osnovni unos troška treba da ostane nezavisan od kašnjenja analitičkog prikaza",
                            "Transaction Service treba da ostane izvor istine za finansijski zapis korisnika",
                            "slika računa treba da bude čuvana odvojeno od osnovnog zapisa transakcije",
                            """
                            Analytics Read Model treba da bude autoritativni izvor za stanje transakcija jer korisnik najčešće gleda mesečni pregled
                            """.trimIndent(),
                            """
                            OCR rezultat treba automatski da prepiše korisnikov unos ako sistem ima veću pouzdanost od ručnog unosa
                            """.trimIndent(),
                            "Budget Service treba direktno da menja istoriju transakcija kada korisnik probije budžet",
                            "Document/Receipt Storage treba da čuva i finansijsku logiku, jer je račun dokaz troška",
                            """
                            Message Broker može podržati naknadnu obradu, ali ne sme postati jedino mesto gde postoji finansijski zapis
                            """.trimIndent()
                        ),
                        correctAnswers = listOf(
                            "osnovni unos troška treba da ostane nezavisan od kašnjenja analitičkog prikaza",
                            "Transaction Service treba da ostane izvor istine za finansijski zapis korisnika",
                            "slika računa treba da bude čuvana odvojeno od osnovnog zapisa transakcije"
                        )
                    ),
                    ArchitectSeedBuilders.singleChoiceStep(
                        questionId = "A4.1",
                        stepNumber = 4,
                        title = "Izaberi najbolju odluku za spornu tačku arhitekture",
                        instruction = "Izaberi najbolju odluku za spornu tačku arhitekture.",
                        options = listOf(
                            """
                            A. OCR kao asinhroni proces koji obrađuje račun i predlaže dopunu transakcije
                            Račun se čuva kao artefakt, događaj pokreće OCR obradu, a rezultat se vraća kao predlog ili nacrt izmene koji korisnik ili Transaction Service kontrolisano prihvata.
                            """.trimIndent(),
                            """
                            B. OCR kao obavezni korak u mobilnoj aplikaciji pre čuvanja svakog troška
                            Aplikacija ne dozvoljava čuvanje transakcije dok OCR ne pročita račun i ne popuni iznos, datum i kategoriju.
                            """.trimIndent(),
                            """
                            C. OCR kao deo Transaction Service-a, da bi trošak, račun i OCR rezultat bili sačuvani u jednoj tehničkoj transakciji
                            Time se dobija centralizovan tok, ali Transaction Service preuzima sporu i promenljivu obradu slike.
                            """.trimIndent(),
                            """
                            D. OCR kao deo Analytics Read Model-a, jer se OCR rezultat najviše koristi za preglede i kategorizaciju potrošnje
                            Analitički model bi pri izradi mesečnog pregleda čitao račune i izvlačio podatke iz njih.
                            """.trimIndent(),
                            """
                            E. OCR kao deo Budget Service-a, jer pročitani iznos direktno utiče na budžetsko stanje korisnika
                            Budget Service bi sam obrađivao račune i prilagođavao budžetske izračune.
                            """.trimIndent()
                        ),
                        correctAnswer = """
                    A. OCR kao asinhroni proces koji obrađuje račun i predlaže dopunu transakcije
                    Račun se čuva kao artefakt, događaj pokreće OCR obradu, a rezultat se vraća kao predlog ili nacrt izmene koji korisnik ili Transaction Service kontrolisano prihvata.
                    """.trimIndent()
                    ),
                    ArchitectSeedBuilders.freeTextStep(
                        questionId = "A4.1",
                        stepNumber = 5,
                        title = "Kratko obrazloženje",
                        instruction = """
                    Objasni zašto je predloženi tok bolji od alternativa koje blokiraju osnovni sistem ili mešaju odgovornosti.
                    """.trimIndent()
                    )
                )
                ),
        SeedQuestion(
                    questionId = "A4.2",
                    level = LearningLevel.ARCHITECT.id,
                    type = QuestionType.ARCHITECTURE_COMPOSITION.id,
                    title = "Sastavi arhitekturu za real-time platformu za zajedničko projektovanje softverskih dijagrama",
                    prompt = """
                    Tim razvija platformu u kojoj studenti i mentori zajedno prave dijagrame softverske arhitekture u realnom vremenu. Više korisnika može istovremeno uređivati isti dijagram, dodavati komponente, povezivati ih strelicama, komentarisati odluke i kasnije izvoziti dijagram kao sliku ili dokumentaciju.
                    Zahtevi:
                    • više korisnika može istovremeno uređivati isti dijagram;
                    • korisnici vide promene drugih učesnika skoro u realnom vremenu;
                    • sistem mora sprečiti gubitak rada ako korisnik izgubi konekciju;
                    • korisnik može videti istoriju verzija dijagrama;
                    • mentor može komentarisati određenu komponentu ili vezu;
                    • dijagram se može izvesti kao PNG/PDF;
                    • prava pristupa zavise od projekta, grupe i uloge korisnika;
                    • kratkotrajno kašnjenje u prikazu prisustva korisnika je prihvatljivo;
                    • trajno stanje dijagrama mora biti pouzdano sačuvano;
                    • kasnije se planira AI pomoćnik koji predlaže arhitektonske korekcije.
                    Dostupne komponente:
                    • Web/Mobile Diagram App
                    • API Gateway
                    • Auth Service
                    • Workspace/Permission Service
                    • Real-time Collaboration Gateway
                    • Diagram Sync Service
                    • Diagram Event Log
                    • Diagram Snapshot Store
                    • Comment Service
                    • Presence Service
                    • Export/Rendering Worker
                    • Message Broker
                    • Notification Service
                    • AI Architecture Assistant
                    • Template Marketplace Service
                    """.trimIndent(),
                    aiFollowUp = """
                    Ako dva korisnika istovremeno izmene istu vezu između dve komponente u dijagramu, kako bi arhitektura trebalo da odluči šta se čuva, šta se prikazuje drugim korisnicima i kako se korisniku objašnjava eventualni konflikt?
                    """.trimIndent(),
                    wave = 2,
                    difficulty = "expert",
                    orderIndex = 10,
                    estimatedMinutes = 10,
                    steps = listOf(
                    ArchitectSeedBuilders.multiChoiceStep(
                        questionId = "A4.2",
                        stepNumber = 1,
                        title = "Izaberi 12 komponenti za osnovnu arhitekturu",
                        instruction = "Izaberi komponente potrebne za prvu stabilnu produkcionu verziju.",
                        options = emptyList(),
                        correctAnswers = listOf(
                            "Web/Mobile Diagram App",
                            "API Gateway",
                            "Auth Service",
                            "Workspace/Permission Service",
                            "Real-time Collaboration Gateway",
                            "Diagram Sync Service",
                            "Diagram Event Log",
                            "Diagram Snapshot Store",
                            "Comment Service",
                            "Presence Service",
                            "Export/Rendering Worker",
                            "Message Broker"
                        )
                    ),
                    ArchitectSeedBuilders.orderedCardsStep(
                        questionId = "A4.2",
                        stepNumber = 2,
                        title = "Sastavi glavni arhitektonski tok",
                        instruction = "Izaberi i poređaj kartice koje čine najispravniji glavni tok sistema.",
                        cards = listOf(
                            "Web/Mobile Diagram App šalje zahtev za ulazak u projekat kroz API Gateway",
                            "Auth Service proverava identitet korisnika",
                            "Workspace/Permission Service proverava pravo pristupa konkretnom dijagramu",
                            "Real-time Collaboration Gateway uspostavlja kanal za zajedničko uređivanje",
                            "Diagram Sync Service prima izmene i usklađuje redosled promena više korisnika",
                            "Diagram Event Log beleži promene kao istoriju rada na dijagramu",
                            "Diagram Snapshot Store periodično čuva stabilno stanje dijagrama",
                            "Presence Service prikazuje ko je trenutno aktivan u dijagramu",
                            "AI Architecture Assistant automatski menja dijagram ako prepozna lošu arhitekturu",
                            "Export/Rendering Worker mora završiti eksport pre nego što se korisniku prikaže promena na tabli",
                            """
                            Template Marketplace Service odlučuje da li korisnik sme da menja dijagram Tačan raspored: 1.	Web/Mobile Diagram App šalje zahtev za ulazak u projekat kroz API Gateway 2.	Auth Service proverava identitet korisnika 3.	Workspace/Permission Service proverava pravo pristupa konkretnom dijagramu 4.	Real-time Collaboration Gateway uspostavlja kanal za zajedničko uređivanje 5.	Diagram Sync Service prima izmene i usklađuje redosled promena više korisnika 6.	Diagram Event Log beleži promene kao istoriju rada na dijagramu 7.	Diagram Snapshot Store periodično čuva stabilno stanje dijagrama 8.	Presence Service prikazuje ko je trenutno aktivan u dijagramu
                            """.trimIndent()
                        ),
                        correctOrder = listOf(
                            "Web/Mobile Diagram App šalje zahtev za ulazak u projekat kroz API Gateway",
                            "Auth Service proverava identitet korisnika",
                            "Workspace/Permission Service proverava pravo pristupa konkretnom dijagramu",
                            "Real-time Collaboration Gateway uspostavlja kanal za zajedničko uređivanje",
                            "Diagram Sync Service prima izmene i usklađuje redosled promena više korisnika",
                            "Diagram Event Log beleži promene kao istoriju rada na dijagramu",
                            "Diagram Snapshot Store periodično čuva stabilno stanje dijagrama",
                            "Presence Service prikazuje ko je trenutno aktivan u dijagramu"
                        )
                    ),
                    ArchitectSeedBuilders.multiChoiceStep(
                        questionId = "A4.2",
                        stepNumber = 3,
                        title = "Izaberi 3 ključna arhitektonska pravila",
                        instruction = "Izaberi pravila koja najviše čuvaju pouzdanost, odgovornosti i granice sistema.",
                        options = listOf(
                            "real-time prikaz promena ne sme biti jedino mesto gde postoji trajno stanje dijagrama",
                            "prava pristupa projektu moraju se proveravati pre ulaska u real-time kanal",
                            "istorija promena i stabilni snapshot treba da se dopunjuju, a ne da isključuju jedno drugo",
                            """
                            Presence Service treba da bude izvor istine za sadržaj dijagrama jer najbolje zna ko trenutno uređuje
                            """.trimIndent(),
                            "Export/Rendering Worker treba da blokira svaku izmenu dok se ne pripremi najnoviji PDF",
                            "AI pomoćnik sme automatski da menja dijagram bez potvrde korisnika ako prepozna bolji obrazac",
                            """
                            Template Marketplace može zameniti Workspace/Permission Service jer šabloni već imaju pravila korišćenja
                            """.trimIndent(),
                            "Diagram Snapshot Store ne treba koristiti ako postoji real-time kanal između korisnika"
                        ),
                        correctAnswers = listOf(
                            "real-time prikaz promena ne sme biti jedino mesto gde postoji trajno stanje dijagrama",
                            "prava pristupa projektu moraju se proveravati pre ulaska u real-time kanal",
                            "istorija promena i stabilni snapshot treba da se dopunjuju, a ne da isključuju jedno drugo"
                        )
                    ),
                    ArchitectSeedBuilders.singleChoiceStep(
                        questionId = "A4.2",
                        stepNumber = 4,
                        title = "Izaberi najbolju odluku za spornu tačku arhitekture",
                        instruction = "Izaberi najbolju odluku za spornu tačku arhitekture.",
                        options = listOf(
                            """
                            A. Real-time izmene prolaze kroz Diagram Sync Service, beleže se u Event Log-u, a stabilno stanje se periodično čuva u Snapshot Store-u
                            Ovaj pristup omogućava saradnju u realnom vremenu, istoriju promena i pouzdano obnavljanje stanja ako korisnik izgubi konekciju ili se sesija prekine.
                            """.trimIndent(),
                            """
                            B. Svaki klijent lokalno menja dijagram, a server povremeno prihvata poslednju verziju koja stigne
                            Ovo deluje jednostavno, ali može dovesti do gubitka rada drugih korisnika i nekontrolisanog prepisivanja promena.
                            """.trimIndent(),
                            """
                            C. Presence Service odlučuje čija promena ima prednost jer zna ko je trenutno aktivan
                            Presence može pomoći korisničkom prikazu, ali ne treba da odlučuje o redosledu i validnosti izmena dijagrama.
                            """.trimIndent(),
                            """
                            D. Export/Rendering Worker treba da bude glavni izvor istine jer generiše konačan prikaz dijagrama
                            Eksport je izvedeni prikaz, a ne osnovni model rada i istorije izmena.
                            """.trimIndent(),
                            """
                            E. AI Architecture Assistant treba da rešava konflikte tako što automatski bira arhitektonski bolju verziju dijagrama
                            AI može pomoći kao savetnik, ali ne sme nevidljivo preuzeti kontrolu nad korisničkim radom i verzijama.
                            """.trimIndent()
                        ),
                        correctAnswer = """
                    A. Real-time izmene prolaze kroz Diagram Sync Service, beleže se u Event Log-u, a stabilno stanje se periodično čuva u Snapshot Store-u
                    Ovaj pristup omogućava saradnju u realnom vremenu, istoriju promena i pouzdano obnavljanje stanja ako korisnik izgubi konekciju ili se sesija prekine.
                    """.trimIndent()
                    ),
                    ArchitectSeedBuilders.freeTextStep(
                        questionId = "A4.2",
                        stepNumber = 5,
                        title = "Kratko obrazloženje",
                        instruction = """
                    Objasni zašto je predloženi tok bolji od alternativa koje blokiraju osnovni sistem ili mešaju odgovornosti.
                    """.trimIndent()
                    )
                )
                ),
        SeedQuestion(
                    questionId = "A4.3",
                    level = LearningLevel.ARCHITECT.id,
                    type = QuestionType.ARCHITECTURE_COMPOSITION.id,
                    title = "Sastavi arhitekturu za pametno upravljanje klimom i potrošnjom energije u velikoj zgradi**",
                    prompt = """
                    Kompanija razvija sistem za pametno upravljanje klimom, potrošnjom energije i komforom u velikoj poslovnoj zgradi. Sistem prima podatke sa senzora, prati zauzetost prostorija, reguliše grejanje, hlađenje i ventilaciju, i omogućava facility menadžerima da nadgledaju stanje zgrade.
                    Zahtevi:
                    • senzori šalju temperaturu, vlažnost, kvalitet vazduha i zauzetost prostorija;
                    • sistem prikazuje trenutno stanje po spratovima i prostorijama;
                    • facility menadžer može podesiti pravila komfora i štednje energije;
                    • sistem može poslati komandu HVAC uređajima;
                    • kritična odstupanja, poput previsoke temperature u server sobi, moraju brzo stići do nadzora;
                    • istorijski podaci se koriste za analizu potrošnje energije;
                    • korisnici u kancelarijama mogu prijaviti da im je pretoplo ili prehladno;
                    • komande ka uređajima moraju biti kontrolisane i auditabilne;
                    • dashboard ne sme blokirati prijem senzorskih podataka;
                    • kasnije se planira optimizacija potrošnje pomoću prediktivnog modela.
                    Dostupne komponente:
                    • Facility Manager Console
                    • Occupant Feedback App
                    • API Gateway
                    • Auth Service
                    • Building IoT Gateway
                    • Sensor Telemetry Stream
                    • Building State Read Model
                    • Comfort Policy Service
                    • HVAC Command Service
                    • Alerting Service
                    • Time-series Sensor Store
                    • Energy Analytics Store
                    • Access/Audit Log
                    • Predictive Optimization Engine
                    • Billing/Cost Allocation Service
                    """.trimIndent(),
                    aiFollowUp = """
                    Ako prediktivni model predloži smanjenje hlađenja radi uštede energije, ali senzori pokazuju da se server soba približava kritičnoj temperaturi, koji sloj sistema treba da ima prednost i kako bi arhitektura trebalo da spreči opasnu automatsku odluku?
                    """.trimIndent(),
                    wave = 3,
                    difficulty = "expert",
                    orderIndex = 16,
                    estimatedMinutes = 10,
                    steps = listOf(
                    ArchitectSeedBuilders.multiChoiceStep(
                        questionId = "A4.3",
                        stepNumber = 1,
                        title = "Izaberi 12 komponenti za osnovnu arhitekturu",
                        instruction = "Izaberi komponente potrebne za prvu stabilnu produkcionu verziju.",
                        options = emptyList(),
                        correctAnswers = listOf(
                            "Facility Manager Console",
                            "Occupant Feedback App",
                            "API Gateway",
                            "Auth Service",
                            "Building IoT Gateway",
                            "Sensor Telemetry Stream",
                            "Building State Read Model",
                            "Comfort Policy Service",
                            "HVAC Command Service",
                            "Alerting Service",
                            "Time-series Sensor Store",
                            "Access/Audit Log"
                        )
                    ),
                    ArchitectSeedBuilders.orderedCardsStep(
                        questionId = "A4.3",
                        stepNumber = 2,
                        title = "Sastavi glavni arhitektonski tok",
                        instruction = "Izaberi i poređaj kartice koje čine najispravniji glavni tok sistema.",
                        cards = listOf(
                            "Building IoT Gateway prima podatke sa senzora i uređaja u zgradi",
                            "Sensor Telemetry Stream razdvaja rutinska očitavanja od kritičnih odstupanja",
                            "Building State Read Model čuva poslednje poznato stanje prostorija i spratova",
                            "Alerting Service obrađuje kritična odstupanja i obaveštava nadzor",
                            "Facility Manager Console šalje promenu pravila kroz API Gateway",
                            "Auth Service proverava identitet i pravo korisnika da menja pravila ili šalje komande",
                            "Comfort Policy Service proverava da li je tražena akcija u skladu sa pravilima komfora i štednje",
                            "HVAC Command Service šalje kontrolisanu komandu uređaju i upisuje audit trag",
                            "Occupant Feedback App direktno šalje komandu klima uređaju u prostoriji",
                            "Predictive Optimization Engine automatski menja pravila komfora bez odobrenja facility menadžera",
                            """
                            Building State Read Model direktno komanduje HVAC uređajima jer ima najnovije stanje Tačan raspored: 1.	Building IoT Gateway prima podatke sa senzora i uređaja u zgradi 2.	Sensor Telemetry Stream razdvaja rutinska očitavanja od kritičnih odstupanja 3.	Building State Read Model čuva poslednje poznato stanje prostorija i spratova 4.	Alerting Service obrađuje kritična odstupanja i obaveštava nadzor 5.	Facility Manager Console šalje promenu pravila kroz API Gateway 6.	Auth Service proverava identitet i pravo korisnika da menja pravila ili šalje komande 7.	Comfort Policy Service proverava da li je tražena akcija u skladu sa pravilima komfora i štednje 8.	HVAC Command Service šalje kontrolisanu komandu uređaju i upisuje audit trag
                            """.trimIndent()
                        ),
                        correctOrder = listOf(
                            "Building IoT Gateway prima podatke sa senzora i uređaja u zgradi",
                            "Sensor Telemetry Stream razdvaja rutinska očitavanja od kritičnih odstupanja",
                            "Building State Read Model čuva poslednje poznato stanje prostorija i spratova",
                            "Alerting Service obrađuje kritična odstupanja i obaveštava nadzor",
                            "Facility Manager Console šalje promenu pravila kroz API Gateway",
                            "Auth Service proverava identitet i pravo korisnika da menja pravila ili šalje komande",
                            "Comfort Policy Service proverava da li je tražena akcija u skladu sa pravilima komfora i štednje",
                            "HVAC Command Service šalje kontrolisanu komandu uređaju i upisuje audit trag"
                        )
                    ),
                    ArchitectSeedBuilders.multiChoiceStep(
                        questionId = "A4.3",
                        stepNumber = 3,
                        title = "Izaberi 3 ključna arhitektonska pravila",
                        instruction = "Izaberi pravila koja najviše čuvaju pouzdanost, odgovornosti i granice sistema.",
                        options = listOf(
                            "tok senzorske telemetrije treba odvojiti od toka komandi ka HVAC uređajima",
                            "dashboard treba da čita iz Building State Read Model-a, a ne direktno iz sirovog toka senzora",
                            "komande ka uređajima moraju proći proveru prava, pravila komfora i audit",
                            """
                            Occupant Feedback App treba direktno da kontroliše uređaje jer korisnik najbolje zna da li mu je toplo ili hladno
                            """.trimIndent(),
                            """
                            Predictive Optimization Engine treba odmah da postane autoritativni servis za sve odluke o temperaturi
                            """.trimIndent(),
                            "Building State Read Model treba da šalje komande uređajima jer ima najbrži prikaz stanja",
                            "istorijska analitika potrošnje može zameniti trenutna pravila komfora",
                            "Alerting Service treba da čeka dnevni izveštaj pre nego što prijavi kritično odstupanje"
                        ),
                        correctAnswers = listOf(
                            "tok senzorske telemetrije treba odvojiti od toka komandi ka HVAC uređajima",
                            "dashboard treba da čita iz Building State Read Model-a, a ne direktno iz sirovog toka senzora",
                            "komande ka uređajima moraju proći proveru prava, pravila komfora i audit"
                        )
                    ),
                    ArchitectSeedBuilders.singleChoiceStep(
                        questionId = "A4.3",
                        stepNumber = 4,
                        title = "Izaberi najbolju odluku za spornu tačku arhitekture",
                        instruction = "Izaberi najbolju odluku za spornu tačku arhitekture.",
                        options = listOf(
                            """
                            A. Comfort Policy Service ostaje autoritativni sloj za pravila, dok prediktivni model kasnije može davati preporuke koje se kontrolisano primenjuju
                            Ovim se omogućava optimizacija potrošnje bez toga da neproveren model direktno komanduje uređajima i narušava komfor ili bezbednost.
                            """.trimIndent(),
                            """
                            B. Predictive Optimization Engine odmah direktno šalje komande HVAC uređajima, jer najbolje prepoznaje obrasce potrošnje
                            Ovo može smanjiti potrošnju, ali uvodi rizik da model donosi odluke bez jasne kontrole, audita i poslovnih pravila.
                            """.trimIndent(),
                            """
                            C. Building State Read Model odlučuje o komandama jer ima najnovije podatke o temperaturi i zauzetosti
                            Read model je dobar za prikaz stanja, ali ne treba da bude autoritativni sloj za donošenje i slanje komandi.
                            """.trimIndent(),
                            """
                            D. Occupant Feedback App direktno komanduje uređajima, a sistem kasnije izveštava facility menadžera
                            Ovo može delovati brzo za korisnika, ali zaobilazi pravila zgrade, bezbednosne granice i audit.
                            """.trimIndent(),
                            """
                            E. Energy Analytics Store jednom dnevno izračunava optimalne komande za sledeći dan i automatski ih primenjuje
                            Dnevna analitika može pomoći planiranju, ali ne sme bez kontrole preuzeti real-time komande nad uređajima.
                            """.trimIndent()
                        ),
                        correctAnswer = """
                    A. Comfort Policy Service ostaje autoritativni sloj za pravila, dok prediktivni model kasnije može davati preporuke koje se kontrolisano primenjuju
                    Ovim se omogućava optimizacija potrošnje bez toga da neproveren model direktno komanduje uređajima i narušava komfor ili bezbednost.
                    """.trimIndent()
                    ),
                    ArchitectSeedBuilders.freeTextStep(
                        questionId = "A4.3",
                        stepNumber = 5,
                        title = "Kratko obrazloženje",
                        instruction = """
                    Objasni zašto je predloženi tok bolji od alternativa koje blokiraju osnovni sistem ili mešaju odgovornosti.
                    """.trimIndent()
                    )
                )
                ),
        SeedQuestion(
                    questionId = "A4.4",
                    level = LearningLevel.ARCHITECT.id,
                    type = QuestionType.ARCHITECTURE_COMPOSITION.id,
                    title = "Sastavi arhitekturu za sistem orkestracije kuhinje u restoranskom lancu",
                    prompt = """
                    Restoranski lanac razvija platformu koja povezuje narudžbine iz mobilne aplikacije, samouslužnih kioska, konobarskog sistema i dostavnih partnera. Cilj je da kuhinja dobije jedinstven, pouzdan i prioritetno organizovan tok pripreme hrane.
                    Zahtevi:
                    • narudžbine stižu iz više kanala: mobilna aplikacija, kiosk, konobar i dostavni partner;
                    • sistem mora proveriti meni, dostupnost artikala i osnovna pravila narudžbine;
                    • kuhinja vidi narudžbine po stanicama: grill, salate, pića, deserti;
                    • neke stavke iz iste narudžbine pripremaju se paralelno;
                    • korisnik mora videti status narudžbine;
                    • konobar ili menadžer može izmeniti ili otkazati narudžbinu pre određene faze pripreme;
                    • promene statusa moraju biti auditabilne;
                    • izveštaji o prodaji ne smeju usporiti rad kuhinje;
                    • sistem mora sprečiti da kuhinja dobije narudžbinu koja nije validna;
                    • kasnije se planira automatska optimizacija redosleda pripreme na osnovu opterećenja kuhinje.
                    Dostupne komponente:
                    • Customer App / Kiosk App
                    • Waiter POS App
                    • Delivery Partner Adapter
                    • API Gateway
                    • Auth Service
                    • Menu Service
                    • Order Service
                    • Kitchen Orchestration Service
                    • Kitchen Display Read Model
                    • Station Task Queue
                    • Inventory Availability Service
                    • Payment Adapter
                    • Order Status Notification Service
                    • Access/Audit Log
                    • Sales Analytics Store
                    • Kitchen Optimization Engine
                    """.trimIndent(),
                    aiFollowUp = """
                    Ako je jedna stavka iz narudžbine već počela da se priprema na grill stanici, a korisnik pokuša da izmeni celu narudžbinu preko aplikacije, koji servis treba da odluči da li je izmena dozvoljena i kako arhitektura treba da spreči nekonzistentno stanje između aplikacije, kuhinje i statusa narudžbine?
                    """.trimIndent(),
                    wave = 4,
                    difficulty = "expert",
                    orderIndex = 22,
                    estimatedMinutes = 10,
                    steps = listOf(
                    ArchitectSeedBuilders.multiChoiceStep(
                        questionId = "A4.4",
                        stepNumber = 1,
                        title = "Izaberi 13 komponenti za osnovnu arhitekturu",
                        instruction = "Izaberi komponente potrebne za prvu stabilnu produkcionu verziju.",
                        options = emptyList(),
                        correctAnswers = listOf(
                            "Customer App / Kiosk App",
                            "Waiter POS App",
                            "Delivery Partner Adapter",
                            "API Gateway",
                            "Auth Service",
                            "Menu Service",
                            "Order Service",
                            "Kitchen Orchestration Service",
                            "Kitchen Display Read Model",
                            "Station Task Queue",
                            "Inventory Availability Service",
                            "Payment Adapter",
                            "Access/Audit Log"
                        )
                    ),
                    ArchitectSeedBuilders.orderedCardsStep(
                        questionId = "A4.4",
                        stepNumber = 2,
                        title = "Sastavi glavni arhitektonski tok",
                        instruction = "Izaberi i poređaj kartice koje čine najispravniji glavni tok sistema.",
                        cards = listOf(
                            """
                            Narudžbina stiže iz Customer/Kiosk aplikacije, Waiter POS-a ili Delivery Partner Adapter-a kroz API Gateway
                            """.trimIndent(),
                            "Auth Service proverava identitet kanala ili korisnika koji šalje narudžbinu",
                            "Menu Service proverava da li su izabrani artikli validni za taj restoran i vreme prodaje",
                            "Inventory Availability Service proverava osnovnu dostupnost artikala ili sastojaka",
                            "Order Service kreira validnu narudžbinu i čuva njen osnovni status",
                            "Kitchen Orchestration Service deli narudžbinu na zadatke po kuhinjskim stanicama",
                            "Station Task Queue prosleđuje zadatke odgovarajućim stanicama za pripremu",
                            "Kitchen Display Read Model prikazuje kuhinji aktivne zadatke i statuse pripreme",
                            """
                            Sales Analytics Store prvo izračunava dnevni promet, pa tek onda dozvoljava kuhinji da vidi narudžbinu
                            """.trimIndent(),
                            """
                            Kitchen Optimization Engine automatski menja redosled pripreme svake narudžbine bez pravila restorana
                            """.trimIndent(),
                            "Delivery Partner Adapter direktno šalje zadatke kuhinji da bi se ubrzala dostava",
                            """
                            Payment Adapter odlučuje koje stanice treba da pripreme hranu Tačan raspored: 1.	Narudžbina stiže iz Customer/Kiosk aplikacije, Waiter POS-a ili Delivery Partner Adapter-a kroz API Gateway 2.	Auth Service proverava identitet kanala ili korisnika koji šalje narudžbinu 3.	Menu Service proverava da li su izabrani artikli validni za taj restoran i vreme prodaje 4.	Inventory Availability Service proverava osnovnu dostupnost artikala ili sastojaka 5.	Order Service kreira validnu narudžbinu i čuva njen osnovni status 6.	Kitchen Orchestration Service deli narudžbinu na zadatke po kuhinjskim stanicama 7.	Station Task Queue prosleđuje zadatke odgovarajućim stanicama za pripremu 8.	Kitchen Display Read Model prikazuje kuhinji aktivne zadatke i statuse pripreme
                            """.trimIndent()
                        ),
                        correctOrder = listOf(
                            """
                            Narudžbina stiže iz Customer/Kiosk aplikacije, Waiter POS-a ili Delivery Partner Adapter-a kroz API Gateway
                            """.trimIndent(),
                            "Auth Service proverava identitet kanala ili korisnika koji šalje narudžbinu",
                            "Menu Service proverava da li su izabrani artikli validni za taj restoran i vreme prodaje",
                            "Inventory Availability Service proverava osnovnu dostupnost artikala ili sastojaka",
                            "Order Service kreira validnu narudžbinu i čuva njen osnovni status",
                            "Kitchen Orchestration Service deli narudžbinu na zadatke po kuhinjskim stanicama",
                            "Station Task Queue prosleđuje zadatke odgovarajućim stanicama za pripremu",
                            "Kitchen Display Read Model prikazuje kuhinji aktivne zadatke i statuse pripreme"
                        )
                    ),
                    ArchitectSeedBuilders.multiChoiceStep(
                        questionId = "A4.4",
                        stepNumber = 3,
                        title = "Izaberi 3 ključna arhitektonska pravila",
                        instruction = "Izaberi pravila koja najviše čuvaju pouzdanost, odgovornosti i granice sistema.",
                        options = listOf(
                            """
                            kuhinja treba da dobije samo narudžbinu koja je prošla validaciju menija, dostupnosti i osnovnih pravila
                            """.trimIndent(),
                            "Order Service treba da bude izvor istine za osnovni status narudžbine",
                            """
                            Kitchen Orchestration Service može deliti narudžbinu na zadatke, ali ne treba da zaobiđe pravila validacije
                            """.trimIndent(),
                            "Delivery Partner Adapter sme direktno da šalje zadatke kuhinji ako je narudžbina hitna",
                            """
                            Sales Analytics Store treba da odlučuje o redosledu pripreme jer ima pregled najprodavanijih artikala
                            """.trimIndent(),
                            """
                            Kitchen Display Read Model može postati izvor istine za status narudžbine jer ga kuhinja najčešće koristi
                            """.trimIndent(),
                            "Payment Adapter treba da određuje kuhinjske stanice jer zna da li je narudžbina plaćena",
                            "Kitchen Optimization Engine treba odmah da menja tok pripreme bez audit-a da bi smanjio čekanje"
                        ),
                        correctAnswers = listOf(
                            """
                            kuhinja treba da dobije samo narudžbinu koja je prošla validaciju menija, dostupnosti i osnovnih pravila
                            """.trimIndent(),
                            "Order Service treba da bude izvor istine za osnovni status narudžbine",
                            """
                            Kitchen Orchestration Service može deliti narudžbinu na zadatke, ali ne treba da zaobiđe pravila validacije
                            """.trimIndent()
                        )
                    ),
                    ArchitectSeedBuilders.singleChoiceStep(
                        questionId = "A4.4",
                        stepNumber = 4,
                        title = "Izaberi najbolju odluku za spornu tačku arhitekture",
                        instruction = "Izaberi najbolju odluku za spornu tačku arhitekture.",
                        options = listOf(
                            """
                            A. Order Service i Kitchen Orchestration Service čine osnovni kontrolisani tok, dok Kitchen Optimization Engine kasnije može davati preporuke u okviru pravila restorana
                            Ovim se zadržava pouzdan tok validacije i pripreme, dok se optimizacija uvodi kontrolisano, bez preuzimanja autoriteta nad narudžbinama.
                            """.trimIndent(),
                            """
                            B. Kitchen Optimization Engine odmah postaje glavni servis koji odlučuje redosled, izmene i otkazivanje narudžbina
                            Ovo može izgledati efikasno, ali uvodi rizik da optimizacija zaobiđe poslovna pravila, status narudžbine i audit.
                            """.trimIndent(),
                            """
                            C. Kitchen Display Read Model treba da bude izvor istine jer kuhinja u njemu vidi stvarno stanje pripreme
                            Read model je važan za prikaz, ali ne treba da bude autoritativni izvor osnovnog statusa narudžbine.
                            """.trimIndent(),
                            """
                            D. Delivery Partner Adapter treba da zaobiđe Order Service za hitne dostave
                            Ovo može skratiti put narudžbine, ali ruši jedinstven tok validacije, statusa i audita.
                            """.trimIndent(),
                            """
                            E. Sales Analytics Store treba da odlučuje koje narudžbine imaju prioritet jer zna šta se najviše prodaje
                            Analitika može pomoći menadžmentu, ali nije servis za operativno upravljanje pojedinačnim narudžbinama.
                            """.trimIndent()
                        ),
                        correctAnswer = """
                    A. Order Service i Kitchen Orchestration Service čine osnovni kontrolisani tok, dok Kitchen Optimization Engine kasnije može davati preporuke u okviru pravila restorana
                    Ovim se zadržava pouzdan tok validacije i pripreme, dok se optimizacija uvodi kontrolisano, bez preuzimanja autoriteta nad narudžbinama.
                    """.trimIndent()
                    ),
                    ArchitectSeedBuilders.freeTextStep(
                        questionId = "A4.4",
                        stepNumber = 5,
                        title = "Kratko obrazloženje",
                        instruction = """
                    Objasni zašto je predloženi tok bolji od alternativa koje blokiraju osnovni sistem ili mešaju odgovornosti.
                    """.trimIndent()
                    )
                )
                ),
        SeedQuestion(
                    questionId = "A4.5",
                    level = LearningLevel.ARCHITECT.id,
                    type = QuestionType.ARCHITECTURE_COMPOSITION.id,
                    title = "Sastavi arhitekturu za platformu za upravljanje flotom autonomnih dostavnih robota",
                    prompt = """
                    Kompanija razvija platformu za upravljanje flotom autonomnih dostavnih robota u urbanim zonama.
                    Zahtevi:
                    • operater vidi trenutnu lokaciju robota;
                    • sistem prima telemetriju: baterija, brzina, status senzora, greške;
                    • korisnik može pratiti dostavu u aplikaciji;
                    • robot može dobiti komandu: pauziraj, nastavi, vrati se u bazu;
                    • kritične greške moraju brzo stići do operatera;
                    • istorijski podaci se koriste za analizu performansi i održavanje;
                    • komande ka robotu moraju biti kontrolisane i auditabilne;
                    • dashboard ne sme blokirati prijem telemetrije;
                    • veliki broj telemetrijskih događaja ne sme ugušiti tok kritičnih alarma.
                    Dostupne komponente:
                    • Customer App
                    • Operator Console
                    • Robot Gateway
                    • Telemetry Stream
                    • Command Service
                    • Fleet State Read Model
                    • Alerting Service
                    • Time-series / NoSQL Telemetry Store
                    • Analytics/Reporting Store
                    • Access/Audit Log
                    • Object Storage for Logs
                    • Route Optimization Engine
                    • Digital Twin / Simulation Service
                    • Billing Service
                    • Public Tracking Cache
                    """.trimIndent(),
                    aiFollowUp = """
                    Ako robot izgubi vezu dok ima aktivnu dostavu, kako bi arhitektura trebalo da razlikuje poslednje poznato stanje, stvarno trenutno stanje i operativnu odluku koju operater sme da donese?
                    """.trimIndent(),
                    wave = 5,
                    difficulty = "expert",
                    orderIndex = 28,
                    estimatedMinutes = 10,
                    steps = listOf(
                    ArchitectSeedBuilders.multiChoiceStep(
                        questionId = "A4.5",
                        stepNumber = 1,
                        title = "Izaberi 10 komponenti za osnovnu arhitekturu",
                        instruction = "Izaberi komponente potrebne za prvu stabilnu produkcionu verziju.",
                        options = emptyList(),
                        correctAnswers = listOf(
                            "Customer App",
                            "Operator Console",
                            "Robot Gateway",
                            "Telemetry Stream",
                            "Command Service",
                            "Fleet State Read Model",
                            "Alerting Service",
                            "Time-series / NoSQL Telemetry Store",
                            "Analytics/Reporting Store",
                            "Access/Audit Log"
                        )
                    ),
                    ArchitectSeedBuilders.orderedCardsStep(
                        questionId = "A4.5",
                        stepNumber = 2,
                        title = "Sastavi glavni arhitektonski tok",
                        instruction = "Izaberi i poređaj kartice koje čine najispravniji glavni tok sistema.",
                        cards = listOf(
                            "Robot Gateway prima telemetriju i statusne poruke od robota",
                            "Telemetry Stream razdvaja rutinsku telemetriju od događaja koji mogu biti kritični",
                            "Fleet State Read Model čuva poslednje poznato stanje robota za operaterski i korisnički prikaz",
                            "Alerting Service obrađuje kritične greške i obaveštava operatera",
                            "Operator Console šalje komandu kroz Command Service, ne direktno robotu",
                            "Access/Audit Log beleži ko je izdao komandu, kada i sa kojim razlogom",
                            """
                            Time-series / NoSQL Telemetry Store i Analytics/Reporting Store čuvaju podatke za analizu i održavanje
                            """.trimIndent(),
                            "Customer App direktno šalje komandu robotu ako je dostava aktivna",
                            "Route Optimization Engine odlučuje da li kritična greška treba da zaustavi robota",
                            "Public Tracking Cache postaje autoritativni izvor trenutne lokacije robota",
                            """
                            Billing Service potvrđuje komandu pre nego što robot promeni stanje Tačan raspored: 1.	Robot Gateway prima telemetriju i statusne poruke od robota 2.	Telemetry Stream razdvaja rutinsku telemetriju od događaja koji mogu biti kritični 3.	Fleet State Read Model čuva poslednje poznato stanje robota za operaterski i korisnički prikaz 4.	Alerting Service obrađuje kritične greške i obaveštava operatera 5.	Operator Console šalje komandu kroz Command Service, ne direktno robotu 6.	Access/Audit Log beleži ko je izdao komandu, kada i sa kojim razlogom 7.	Time-series / NoSQL Telemetry Store i Analytics/Reporting Store čuvaju podatke za analizu i održavanje
                            """.trimIndent()
                        ),
                        correctOrder = listOf(
                            "Robot Gateway prima telemetriju i statusne poruke od robota",
                            "Telemetry Stream razdvaja rutinsku telemetriju od događaja koji mogu biti kritični",
                            "Fleet State Read Model čuva poslednje poznato stanje robota za operaterski i korisnički prikaz",
                            "Alerting Service obrađuje kritične greške i obaveštava operatera",
                            "Operator Console šalje komandu kroz Command Service, ne direktno robotu",
                            "Access/Audit Log beleži ko je izdao komandu, kada i sa kojim razlogom",
                            """
                            Time-series / NoSQL Telemetry Store i Analytics/Reporting Store čuvaju podatke za analizu i održavanje
                            """.trimIndent()
                        )
                    ),
                    ArchitectSeedBuilders.multiChoiceStep(
                        questionId = "A4.5",
                        stepNumber = 3,
                        title = "Izaberi 3 ključna arhitektonska pravila",
                        instruction = "Izaberi pravila koja najviše čuvaju pouzdanost, odgovornosti i granice sistema.",
                        options = listOf(
                            "tok komandi ka robotima treba odvojiti od toka telemetrije",
                            "dashboard treba da čita iz Fleet State Read Model-a, a ne direktno iz sirove telemetrije",
                            "kritični alarmi treba da imaju prioritet u odnosu na rutinsku telemetriju",
                            "Customer App može slati komande robotu ako komanda utiče samo na korisnikovu dostavu",
                            """
                            Route Optimization Engine treba da bude autoritativni servis za bezbednosnu odluku u slučaju kritične greške
                            """.trimIndent(),
                            "Public Tracking Cache može postati izvor istine za lokaciju jer je optimizovan za brzo čitanje",
                            "Command Service ne mora da beleži komande ako robot kasnije pošalje potvrdu izvršenja",
                            """
                            Analytics/Reporting Store može odlučivati u realnom vremenu da li robot sme da nastavi vožnju na osnovu istorijskih obrazaca
                            """.trimIndent()
                        ),
                        correctAnswers = listOf(
                            "tok komandi ka robotima treba odvojiti od toka telemetrije",
                            "dashboard treba da čita iz Fleet State Read Model-a, a ne direktno iz sirove telemetrije",
                            "kritični alarmi treba da imaju prioritet u odnosu na rutinsku telemetriju"
                        )
                    ),
                    ArchitectSeedBuilders.singleChoiceStep(
                        questionId = "A4.5",
                        stepNumber = 4,
                        title = "Izaberi najbolju odluku za spornu tačku arhitekture",
                        instruction = "Izaberi najbolju odluku za spornu tačku arhitekture.",
                        options = listOf(
                            """
                            A. Kritične greške idu kroz prioritetni alert tok, dok se puna telemetrija i dalje čuva za analizu i održavanje
                            Sistem omogućava brzu operativnu reakciju bez gubitka istorijskih podataka potrebnih za analizu performansi i održavanje.
                            """.trimIndent(),
                            """
                            B. Sve telemetrijske poruke idu istim redom, jer redosled dolaska garantuje pravednu obradu
                            Ovo pojednostavljuje pipeline, ali rutinska telemetrija može odložiti kritične alarme.
                            """.trimIndent(),
                            """
                            C. Kritične greške se šalju samo u Analytics/Reporting Store, gde se kasnije analiziraju kroz izveštaje
                            Ovo je korisno za istoriju, ali ne rešava trenutnu operativnu reakciju.
                            """.trimIndent(),
                            """
                            D. Robot sam odlučuje da li će kritičnu grešku javiti korisniku, operateru ili nikome
                            Ovo smanjuje centralnu složenost, ali slabi kontrolu nad bezbednosnim i operativnim tokom.
                            """.trimIndent(),
                            """
                            E. Public Tracking Cache označava robota kao problematičnog ako korisnička aplikacija prestane da dobija lokaciju
                            Ovo može biti signal za korisnički prikaz, ali nije pouzdan mehanizam za obradu kritičnih grešaka.
                            """.trimIndent()
                        ),
                        correctAnswer = """
                    A. Kritične greške idu kroz prioritetni alert tok, dok se puna telemetrija i dalje čuva za analizu i održavanje
                    Sistem omogućava brzu operativnu reakciju bez gubitka istorijskih podataka potrebnih za analizu performansi i održavanje.
                    """.trimIndent()
                    ),
                    ArchitectSeedBuilders.freeTextStep(
                        questionId = "A4.5",
                        stepNumber = 5,
                        title = "Kratko obrazloženje",
                        instruction = """
                    Objasni zašto je predloženi tok bolji od alternativa koje blokiraju osnovni sistem ili mešaju odgovornosti.
                    """.trimIndent()
                    )
                )
                )
    )
}
