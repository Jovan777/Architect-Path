package com.example.pmuprojekat.data.seed.architect

import com.example.pmuprojekat.core.model.LearningLevel
import com.example.pmuprojekat.core.model.QuestionType
import com.example.pmuprojekat.data.seed.SeedQuestion

object ArchitectExtensionSeed {
    val questions: List<SeedQuestion> = listOf(
        SeedQuestion(
                    questionId = "A1.1",
                    level = LearningLevel.ARCHITECT.id,
                    type = QuestionType.ARCHITECTURE_EXTENSION.id,
                    title = "Uvođenje personalizovanog learning feed-a u postojeću platformu za učenje",
                    prompt = """
                    Platforma za online učenje već funkcioniše u produkciji.
                    Postojeće celine sistema:
                    •	User Profile modul čuva osnovne podatke o korisniku, nivou znanja i podešavanjima; 
                    •	Course modul čuva kurseve, lekcije i zadatke; 
                    •	Progress modul beleži završene lekcije, rezultate testova i pokušaje rešavanja zadataka; 
                    •	Search modul omogućava pretragu lekcija i kurseva; 
                    •	Notification modul šalje obaveštenja korisnicima; 
                    •	podaci o kursevima su u relacionoj bazi; 
                    •	podaci o napretku korisnika sve više rastu i postaju složeni za analizu. 
                    Novi zahtev:
                    Platforma treba da uvede personalizovani learning feed koji korisniku predlaže sledeće lekcije, zadatke i mini-testove na osnovu:
                    •	prethodnog napretka; 
                    •	grešaka koje često pravi; 
                    •	oblasti koje je preskočio; 
                    •	nivoa težine koji trenutno može da savlada; 
                    •	ponašanja korisnika sličnog profila. 
                    Ograničenja:
                    •	osnovno rešavanje lekcija i testova ne sme zavisiti od dostupnosti personalizacionog sistema; 
                    •	feed može biti osvežen sa manjim kašnjenjem; 
                    •	potrebno je kasnije testirati više algoritama preporuke; 
                    •	Progress modul ne sme postati usko grlo za svaki prikaz početnog ekrana; 
                    •	sistem treba da podrži veliki broj događaja o učenju.
                    """.trimIndent(),
                    aiFollowUp = """
                    Ako korisnik upravo pogreši tri zadatka iz iste oblasti, da li feed mora odmah u realnom vremenu da promeni preporuke, ili je dovoljno da se promena vidi nakon kratke obrade događaja? Objasni arhitektonski, ne samo korisnički.
                    """.trimIndent(),
                    wave = 1,
                    difficulty = "expert",
                    orderIndex = 1,
                    estimatedMinutes = 10,
                    steps = listOf(
                    ArchitectSeedBuilders.singleChoiceStep(
                        questionId = "A1.1",
                        stepNumber = 1,
                        title = "Izaberi najpogodniji pravac proširenja",
                        instruction = "Izaberi arhitektonski pravac koji najbolje uvodi novu sposobnost bez narušavanja postojećeg sistema.",
                        options = listOf(
                            """
                            A. Personalization read model nad event tokom učenja
                            Uvesti poseban personalizacioni sloj koji prima događaje o učenju, gradi korisnički learning profil i čuva feed u posebnom read modelu optimizovanom za čitanje.
                            """.trimIndent(),
                            """
                            B. Direktno proširenje Progress modula analitičkom logikom
                            Progress modul ostaje glavni izvor podataka i pri svakom otvaranju početnog ekrana računa preporuke iz sveže istorije korisnika.
                            """.trimIndent(),
                            """
                            C. Search-first pristup
                            Search modul dobija dodatne signale o korisniku i koristi ih za rangiranje postojećih lekcija, bez posebnog modela personalizacije.
                            """.trimIndent(),
                            """
                            D. Periodični batch proces nad glavnom bazom
                            Jednom dnevno se obrađuju svi rezultati korisnika i generiše lista preporuka za naredni dan.
                            """.trimIndent(),
                            """
                            E. Personalizacija na klijentskoj strani
                            Mobilna aplikacija dobija širi skup podataka o korisniku i lokalno bira šta će sledeće prikazati.
                            """.trimIndent()
                        ),
                        correctAnswer = """
                    A. Personalization read model nad event tokom učenja
                    Uvesti poseban personalizacioni sloj koji prima događaje o učenju, gradi korisnički learning profil i čuva feed u posebnom read modelu optimizovanom za čitanje.
                    """.trimIndent()
                    ),
                    ArchitectSeedBuilders.orderedCardsStep(
                        questionId = "A1.1",
                        stepNumber = 2,
                        title = "Poređaj tok implementacije",
                        instruction = "Poređaj korake od izvora događaja do korisničkog prikaza i fallback ponašanja.",
                        cards = listOf(
                            "Course i Progress tokovi objavljuju događaje o učenju, pokušajima i rezultatima",
                            "event tok se skladišti ili prosleđuje kroz stream/message infrastrukturu",
                            "Personalization komponenta računa korisničke signale i nivo spremnosti",
                            "feed se upisuje u NoSQL/read model optimizovan za brzo čitanje po korisniku",
                            "mobilna aplikacija čita pripremljeni feed, a ne računa ga sama",
                            """
                            ako feed nije dostupan, korisnik i dalje može da nastavi poslednju lekciju ili koristi standardnu listu kurseva Tačan redosled: 1.	Course i Progress tokovi objavljuju događaje o učenju, pokušajima i rezultatima 2.	event tok se skladišti ili prosleđuje kroz stream/message infrastrukturu 3.	Personalization komponenta računa korisničke signale i nivo spremnosti 4.	feed se upisuje u NoSQL/read model optimizovan za brzo čitanje po korisniku 5.	mobilna aplikacija čita pripremljeni feed, a ne računa ga sama 6.	ako feed nije dostupan, korisnik i dalje može da nastavi poslednju lekciju ili koristi standardnu listu kurseva ________________________________________
                            """.trimIndent()
                        ),
                        correctOrder = listOf(
                            "Course i Progress tokovi objavljuju događaje o učenju, pokušajima i rezultatima",
                            "event tok se skladišti ili prosleđuje kroz stream/message infrastrukturu",
                            "Personalization komponenta računa korisničke signale i nivo spremnosti",
                            "feed se upisuje u NoSQL/read model optimizovan za brzo čitanje po korisniku",
                            "mobilna aplikacija čita pripremljeni feed, a ne računa ga sama",
                            """
                            ako feed nije dostupan, korisnik i dalje može da nastavi poslednju lekciju ili koristi standardnu listu kurseva Tačan redosled: 1.	Course i Progress tokovi objavljuju događaje o učenju, pokušajima i rezultatima 2.	event tok se skladišti ili prosleđuje kroz stream/message infrastrukturu 3.	Personalization komponenta računa korisničke signale i nivo spremnosti 4.	feed se upisuje u NoSQL/read model optimizovan za brzo čitanje po korisniku 5.	mobilna aplikacija čita pripremljeni feed, a ne računa ga sama 6.	ako feed nije dostupan, korisnik i dalje može da nastavi poslednju lekciju ili koristi standardnu listu kurseva ________________________________________
                            """.trimIndent()
                        )
                    ),
                    ArchitectSeedBuilders.multiChoiceStep(
                        questionId = "A1.1",
                        stepNumber = 3,
                        title = "Izaberi 3 najvažnije arhitektonske odluke",
                        instruction = "Izaberi odluke koje najbolje objašnjavaju prednosti izabranog proširenja.",
                        options = listOf(
                            "personalizacija može da se razvija nezavisnije od osnovnog toka učenja",
                            "read model omogućava brzo učitavanje feed-a bez teških upita nad Progress modulom",
                            "sistem lakše podržava eksperimentisanje sa više algoritama preporuke",
                            "svi podaci o učenju postaju trenutno i strogo konzistentni u svim prikazima",
                            "uklanja se potreba za observability mehanizmima jer je feed unapred izračunat",
                            "mobilna aplikacija dobija potpunu domensku odgovornost za izbor sadržaja"
                        ),
                        correctAnswers = listOf(
                            "personalizacija može da se razvija nezavisnije od osnovnog toka učenja",
                            "read model omogućava brzo učitavanje feed-a bez teških upita nad Progress modulom",
                            "sistem lakše podržava eksperimentisanje sa više algoritama preporuke"
                        )
                    ),
                    ArchitectSeedBuilders.singleChoiceStep(
                        questionId = "A1.1",
                        stepNumber = 4,
                        title = "Označi glavni rizik koji ova arhitektura uvodi",
                        instruction = "Izaberi rizik koji uvodi izabrani pravac proširenja.",
                        options = listOf(
                            "korisnik može videti feed koji blago kasni za poslednjom aktivnošću",
                            "sistem više ne može da koristi relacionu bazu ni za jedan deo aplikacije",
                            "Search modul mora biti potpuno uklonjen iz arhitekture",
                            "personalizacija mora biti sinhrona da bi sistem uopšte radio Tačan odgovor:",
                            """
                            korisnik može videti feed koji blago kasni za poslednjom aktivnošću ________________________________________
                            """.trimIndent()
                        ),
                        correctAnswer = """
                    korisnik može videti feed koji blago kasni za poslednjom aktivnošću ________________________________________
                    """.trimIndent()
                    ),
                    ArchitectSeedBuilders.freeTextStep(
                        questionId = "A1.1",
                        stepNumber = 5,
                        title = "Kratko obrazloženje",
                        instruction = "Objasni arhitektonsku odluku, prihvaćeni kompromis i zašto osnovni tok sistema ostaje nezavisan."
                    )
                )
                ),
        SeedQuestion(
                    questionId = "A1.2",
                    level = LearningLevel.ARCHITECT.id,
                    type = QuestionType.ARCHITECTURE_EXTENSION.id,
                    title = "Uvođenje feature flag i controlled rollout sistema u postojeću SaaS platformu",
                    prompt = """
                    Kompanija već ima SaaS platformu koju koriste različite organizacije. Platforma ima više modula: korisnici, timovi, dokumenti, zadaci, izveštaji i administracija.
                    Postojeće celine sistema:
                    • User Service upravlja korisnicima i ulogama;
                    • Organization Service čuva podatke o organizacijama i timovima;
                    • Task Service upravlja zadacima i statusima;
                    • Document Service čuva dokumente i priloge;
                    • Reporting modul prikazuje izveštaje administratorima;
                    • Admin Panel omogućava podešavanja organizacije;
                    • frontend trenutno prikazuje funkcionalnosti na osnovu hardkodovanih pravila;
                    • nove funkcionalnosti se najčešće puštaju svim korisnicima odjednom;
                    • rollback nove funkcionalnosti uglavnom zahteva novi deployment.
                    Novi zahtev:
                    Kompanija želi da uvede sistem za kontrolisano uključivanje novih funkcionalnosti.
                    Sistem treba da podrži:
                    • uključivanje funkcionalnosti samo za određene organizacije;
                    • uključivanje funkcionalnosti samo za određene korisničke uloge;
                    • postepeni rollout novog modula na 5%, 25%, 50% i 100% korisnika;
                    • brzo gašenje problematične funkcionalnosti bez novog deployment-a;
                    • A/B testiranje određenih funkcionalnosti;
                    • audit ko je promenio pravilo, kada i za koju organizaciju;
                    • različita pravila po okruženju: test, staging i produkcija.
                    Ograničenja:
                    • postojeći moduli ne treba da hardkoduju sva rollout pravila;
                    • frontend ne sme biti jedino mesto gde se proverava dostupnost funkcionalnosti;
                    • sistem mora imati predvidljiv fallback ako feature flag servis nije dostupan;
                    • promene flag-ova u produkciji moraju biti auditabilne;
                    • nova infrastruktura ne sme učiniti osnovne tokove aplikacije nestabilnim;
                    • neke funkcionalnosti smeju biti sakrivene u UI-ju, ali backend i dalje mora proveravati pravo korišćenja.
                    """.trimIndent(),
                    aiFollowUp = """
                    Ako je nova funkcionalnost sakrivena u frontend-u za neku organizaciju, ali korisnik ručno pogodi backend endpoint te funkcionalnosti, kako arhitektura treba da odluči da li se zahtev izvršava, odbija ili evidentira kao bezbednosno relevantan pokušaj?
                    """.trimIndent(),
                    wave = 2,
                    difficulty = "expert",
                    orderIndex = 7,
                    estimatedMinutes = 10,
                    steps = listOf(
                    ArchitectSeedBuilders.singleChoiceStep(
                        questionId = "A1.2",
                        stepNumber = 1,
                        title = "Izaberi najbolji pravac proširenja",
                        instruction = "Izaberi arhitektonski pravac koji najbolje uvodi novu sposobnost bez narušavanja postojećeg sistema.",
                        options = listOf(
                            """
                            A. Poseban Feature Flag / Rollout sloj sa centralnim pravilima, evaluacijom i audit-om
                            Uvesti poseban sloj koji čuva definicije flag-ova, pravila rollout-a, ciljane organizacije, korisničke segmente i audit promena. Frontend i backend koriste ovaj sloj za kontrolisanu evaluaciju dostupnosti funkcionalnosti.
                            """.trimIndent(),
                            """
                            B. Hardkodovanje uslova u svakom postojećem modulu
                            Svaki servis i frontend ekran samostalno proverava da li je funkcionalnost uključena za korisnika, organizaciju ili ulogu.
                            """.trimIndent(),
                            """
                            C. Frontend-only feature flag sistem
                            Svi flag-ovi se proveravaju samo u web/mobilnoj aplikaciji, pa se funkcionalnosti sakrivaju ili prikazuju bez promene backend-a.
                            """.trimIndent(),
                            """
                            D. Poseban deployment za svaku organizaciju
                            Svaka organizacija dobija sopstvenu verziju aplikacije sa uključenim ili isključenim funkcionalnostima.
                            """.trimIndent(),
                            """
                            E. Ručno uključivanje funkcionalnosti kroz bazu svakog modula
                            Administratori direktno menjaju zapise u bazama pojedinačnih modula kako bi uključili ili isključili funkcionalnosti.
                            """.trimIndent()
                        ),
                        correctAnswer = """
                    A. Poseban Feature Flag / Rollout sloj sa centralnim pravilima, evaluacijom i audit-om
                    Uvesti poseban sloj koji čuva definicije flag-ova, pravila rollout-a, ciljane organizacije, korisničke segmente i audit promena. Frontend i backend koriste ovaj sloj za kontrolisanu evaluaciju dostupnosti funkcionalnosti.
                    """.trimIndent()
                    ),
                    ArchitectSeedBuilders.orderedCardsStep(
                        questionId = "A1.2",
                        stepNumber = 2,
                        title = "Poređaj tok implementacije",
                        instruction = "Poređaj korake od izvora događaja do korisničkog prikaza i fallback ponašanja.",
                        cards = listOf(
                            "definisati Feature Flag sloj koji čuva flag-ove, pravila, segmente i okruženja",
                            "povezati korisnika, organizaciju, ulogu i okruženje u jedinstven evaluacioni kontekst",
                            "frontend koristi flag rezultate za prikaz ili sakrivanje funkcionalnosti",
                            "backend proverava flag i pravo korišćenja pre izvršavanja zaštićene funkcionalnosti",
                            "promene flag-ova se beleže kroz audit log sa korisnikom, vremenom i razlogom izmene",
                            """
                            ako Feature Flag sloj nije dostupan, sistem koristi unapred definisan fallback za kritične i nekritične funkcionalnosti Tačan redosled: 1.	definisati Feature Flag sloj koji čuva flag-ove, pravila, segmente i okruženja 2.	povezati korisnika, organizaciju, ulogu i okruženje u jedinstven evaluacioni kontekst 3.	frontend koristi flag rezultate za prikaz ili sakrivanje funkcionalnosti 4.	backend proverava flag i pravo korišćenja pre izvršavanja zaštićene funkcionalnosti 5.	promene flag-ova se beleže kroz audit log sa korisnikom, vremenom i razlogom izmene 6.	ako Feature Flag sloj nije dostupan, sistem koristi unapred definisan fallback za kritične i nekritične funkcionalnosti ________________________________________
                            """.trimIndent()
                        ),
                        correctOrder = listOf(
                            "definisati Feature Flag sloj koji čuva flag-ove, pravila, segmente i okruženja",
                            "povezati korisnika, organizaciju, ulogu i okruženje u jedinstven evaluacioni kontekst",
                            "frontend koristi flag rezultate za prikaz ili sakrivanje funkcionalnosti",
                            "backend proverava flag i pravo korišćenja pre izvršavanja zaštićene funkcionalnosti",
                            "promene flag-ova se beleže kroz audit log sa korisnikom, vremenom i razlogom izmene",
                            """
                            ako Feature Flag sloj nije dostupan, sistem koristi unapred definisan fallback za kritične i nekritične funkcionalnosti Tačan redosled: 1.	definisati Feature Flag sloj koji čuva flag-ove, pravila, segmente i okruženja 2.	povezati korisnika, organizaciju, ulogu i okruženje u jedinstven evaluacioni kontekst 3.	frontend koristi flag rezultate za prikaz ili sakrivanje funkcionalnosti 4.	backend proverava flag i pravo korišćenja pre izvršavanja zaštićene funkcionalnosti 5.	promene flag-ova se beleže kroz audit log sa korisnikom, vremenom i razlogom izmene 6.	ako Feature Flag sloj nije dostupan, sistem koristi unapred definisan fallback za kritične i nekritične funkcionalnosti ________________________________________
                            """.trimIndent()
                        )
                    ),
                    ArchitectSeedBuilders.multiChoiceStep(
                        questionId = "A1.2",
                        stepNumber = 3,
                        title = "Izaberi 3 najvažnije arhitektonske odluke",
                        instruction = "Izaberi odluke koje najbolje objašnjavaju prednosti izabranog proširenja.",
                        options = listOf(
                            "feature flag evaluacija treba da koristi kontekst korisnika, organizacije, uloge i okruženja",
                            "backend mora proveravati dostupnost funkcionalnosti za kritične operacije, a ne samo frontend",
                            "promene rollout pravila moraju biti auditabilne i reverzibilne",
                            "frontend sakrivanje dugmeta je dovoljno da se spreči korišćenje nove funkcionalnosti",
                            "svaki servis treba da ima sopstveni nepovezani sistem flag-ova bez zajedničkog modela",
                            "rollback problematične funkcionalnosti treba uvek raditi novim deployment-om",
                            "produkcioni flag-ovi se mogu menjati bez traga jer su samo konfiguracija",
                            "ako feature flag servis nije dostupan, sistem treba nasumično da uključi ili isključi funkcionalnost"
                        ),
                        correctAnswers = listOf(
                            "feature flag evaluacija treba da koristi kontekst korisnika, organizacije, uloge i okruženja",
                            "backend mora proveravati dostupnost funkcionalnosti za kritične operacije, a ne samo frontend",
                            "promene rollout pravila moraju biti auditabilne i reverzibilne"
                        )
                    ),
                    ArchitectSeedBuilders.singleChoiceStep(
                        questionId = "A1.2",
                        stepNumber = 4,
                        title = "Označi glavni arhitektonski rizik koji ova arhitektura uvodi",
                        instruction = "Izaberi rizik koji uvodi izabrani pravac proširenja.",
                        options = listOf(
                            """
                            pogrešno projektovani feature flag sistem može dovesti do nekonzistentnog ponašanja između frontend prikaza, backend provere i stvarnih prava korisnika
                            """.trimIndent(),
                            "svi postojeći moduli moraju biti obrisani i ponovo napisani da bi feature flag sistem radio",
                            "feature flag sistem uklanja potrebu za testiranjem nove funkcionalnosti",
                            "svaka funkcionalnost mora zauvek ostati iza feature flag-a",
                            "audit promena nije potreban jer se flag-ovi ne smatraju poslovnim podacima Tačan odgovor:",
                            """
                            pogrešno projektovani feature flag sistem može dovesti do nekonzistentnog ponašanja između frontend prikaza, backend provere i stvarnih prava korisnika ________________________________________
                            """.trimIndent()
                        ),
                        correctAnswer = """
                    pogrešno projektovani feature flag sistem može dovesti do nekonzistentnog ponašanja između frontend prikaza, backend provere i stvarnih prava korisnika ________________________________________
                    """.trimIndent()
                    ),
                    ArchitectSeedBuilders.freeTextStep(
                        questionId = "A1.2",
                        stepNumber = 5,
                        title = "Kratko obrazloženje",
                        instruction = "Objasni arhitektonsku odluku, prihvaćeni kompromis i zašto osnovni tok sistema ostaje nezavisan."
                    )
                )
                ),
        SeedQuestion(
                    questionId = "A1.3",
                    level = LearningLevel.ARCHITECT.id,
                    type = QuestionType.ARCHITECTURE_EXTENSION.id,
                    title = "Uvođenje objašnjivog risk scoring sistema u platformu za osiguranje",
                    prompt = """
                    Osiguravajuća kuća već ima digitalnu platformu za obradu polisa i šteta.
                    Postojeće celine sistema:
                    • Policy Service upravlja polisama osiguranja;
                    • Customer Service čuva podatke o korisnicima;
                    • Claims Service obrađuje prijave štete;
                    • Document Service čuva dokumentaciju, fotografije i izveštaje;
                    • Payment Service evidentira uplate i isplate;
                    • Reporting modul pravi izveštaje za menadžment;
                    • većina operativnih tokova je sinhrona;
                    • istorijski podaci o štetama, polisama i isplatama uglavnom su u relacionim bazama.
                    Novi zahtev:
                    Kompanija želi da uvede risk scoring sistem koji pomaže službenicima da procene rizik korisnika, polise i konkretnog štetnog zahteva.
                    Risk scoring koristi signale kao što su:
                    • istorija prijavljenih šteta;
                    • tip i trajanje polise;
                    • učestalost izmena korisničkih podataka;
                    • iznosi prethodnih isplata;
                    • potpunost i kvalitet dostavljene dokumentacije;
                    • slični obrasci ponašanja kod drugih korisnika;
                    • razlika između očekivanog i prijavljenog obrasca štete.
                    Ograničenja:
                    • risk score ne sme automatski odbiti korisnika ili štetni zahtev;
                    • odluka mora ostati proverljiva, objašnjiva i vezana za ljudsku procenu;
                    • službenik mora videti ne samo score, već i razloge koji su uticali na procenu;
                    • score može kasniti, ali njegovo kašnjenje mora biti jasno označeno;
                    • Claims Service ne sme postati preopterećen analitičkim upitima nad istorijskim podacima;
                    • kasnije treba podržati različite modele za auto, putno i imovinsko osiguranje;
                    • sistem mora čuvati trag: koji model, koja verzija pravila i koji signali su korišćeni pri proceni.
                    """.trimIndent(),
                    aiFollowUp = """
                    Ako risk score pokazuje visok rizik, ali su dokumentacija i istorija korisnika uredni, kako bi arhitektura trebalo da podrži ljudsko obrazloženje odluke bez narušavanja korisnosti automatizovane procene?
                    """.trimIndent(),
                    wave = 3,
                    difficulty = "expert",
                    orderIndex = 13,
                    estimatedMinutes = 10,
                    steps = listOf(
                    ArchitectSeedBuilders.singleChoiceStep(
                        questionId = "A1.3",
                        stepNumber = 1,
                        title = "Izaberi najbolji pravac proširenja",
                        instruction = "Izaberi arhitektonski pravac koji najbolje uvodi novu sposobnost bez narušavanja postojećeg sistema.",
                        options = listOf(
                            """
                            A. Poseban Risk Assessment sloj sa objašnjivim score-om, read modelom i verzionisanim modelima
                            Sistem prikuplja relevantne događaje iz Policy, Claims, Customer, Document i Payment domena, gradi risk profil po korisniku, polisi i štetnom zahtevu, izlaže score sa razlozima i čuva verziju modela korišćenu pri proceni.
                            """.trimIndent(),
                            """
                            B. Proširenje Claims Service-a pravilima za rizik
                            Claims Service dobija dodatna pravila i direktne upite nad postojećim tabelama kako bi u trenutku obrade štete izračunao rizik.
                            """.trimIndent(),
                            """
                            C. Reporting modul kao osnova za risk scoring
                            Pošto već agregira podatke za menadžment, Reporting modul se proširuje tako da računa risk score i vraća ga operativnim servisima.
                            """.trimIndent(),
                            """
                            D. Eksterni AI servis za automatsku odluku
                            Svi podaci o korisniku i šteti šalju se eksternom AI servisu koji vraća odluku: prihvatiti, odbiti ili poslati na dodatnu proveru.
                            """.trimIndent(),
                            """
                            E. Jedan zbirni risk score unutar Customer profila
                            Customer Service periodično čuva jedan opšti score po korisniku, koji ostali servisi koriste pri obradi svih budućih šteta.
                            """.trimIndent()
                        ),
                        correctAnswer = """
                    A. Poseban Risk Assessment sloj sa objašnjivim score-om, read modelom i verzionisanim modelima
                    Sistem prikuplja relevantne događaje iz Policy, Claims, Customer, Document i Payment domena, gradi risk profil po korisniku, polisi i štetnom zahtevu, izlaže score sa razlozima i čuva verziju modela korišćenu pri proceni.
                    """.trimIndent()
                    ),
                    ArchitectSeedBuilders.orderedCardsStep(
                        questionId = "A1.3",
                        stepNumber = 2,
                        title = "Poređaj tok implementacije",
                        instruction = "Poređaj korake od izvora događaja do korisničkog prikaza i fallback ponašanja.",
                        cards = listOf(
                            "domeni polisa, šteta, korisnika, dokumenata i isplata objavljuju relevantne događaje",
                            "događaji se obrađuju kroz poseban risk pipeline",
                            "Risk Assessment sloj gradi profile rizika po korisniku, polisi i konkretnom štetnom zahtevu",
                            "score, razlozi procene, verzija modela i korišćeni signali čuvaju se u posebnom read modelu",
                            """
                            službenik vidi risk score kao podršku odlučivanju, zajedno sa objašnjenjem i statusom svežine procene
                            """.trimIndent(),
                            """
                            ako score kasni ili nije dostupan, obrada se nastavlja kroz standardni ručni tok uz jasnu oznaku da procena nije ažurna Tačan redosled: 1.	domeni polisa, šteta, korisnika, dokumenata i isplata objavljuju relevantne događaje 2.	događaji se obrađuju kroz poseban risk pipeline 3.	Risk Assessment sloj gradi profile rizika po korisniku, polisi i konkretnom štetnom zahtevu 4.	score, razlozi procene, verzija modela i korišćeni signali čuvaju se u posebnom read modelu 5.	službenik vidi risk score kao podršku odlučivanju, zajedno sa objašnjenjem i statusom svežine procene 6.	ako score kasni ili nije dostupan, obrada se nastavlja kroz standardni ručni tok uz jasnu oznaku da procena nije ažurna ________________________________________
                            """.trimIndent()
                        ),
                        correctOrder = listOf(
                            "domeni polisa, šteta, korisnika, dokumenata i isplata objavljuju relevantne događaje",
                            "događaji se obrađuju kroz poseban risk pipeline",
                            "Risk Assessment sloj gradi profile rizika po korisniku, polisi i konkretnom štetnom zahtevu",
                            "score, razlozi procene, verzija modela i korišćeni signali čuvaju se u posebnom read modelu",
                            """
                            službenik vidi risk score kao podršku odlučivanju, zajedno sa objašnjenjem i statusom svežine procene
                            """.trimIndent(),
                            """
                            ako score kasni ili nije dostupan, obrada se nastavlja kroz standardni ručni tok uz jasnu oznaku da procena nije ažurna Tačan redosled: 1.	domeni polisa, šteta, korisnika, dokumenata i isplata objavljuju relevantne događaje 2.	događaji se obrađuju kroz poseban risk pipeline 3.	Risk Assessment sloj gradi profile rizika po korisniku, polisi i konkretnom štetnom zahtevu 4.	score, razlozi procene, verzija modela i korišćeni signali čuvaju se u posebnom read modelu 5.	službenik vidi risk score kao podršku odlučivanju, zajedno sa objašnjenjem i statusom svežine procene 6.	ako score kasni ili nije dostupan, obrada se nastavlja kroz standardni ručni tok uz jasnu oznaku da procena nije ažurna ________________________________________
                            """.trimIndent()
                        )
                    ),
                    ArchitectSeedBuilders.multiChoiceStep(
                        questionId = "A1.3",
                        stepNumber = 3,
                        title = "Izaberi 3 najvažnije arhitektonske odluke",
                        instruction = "Izaberi odluke koje najbolje objašnjavaju prednosti izabranog proširenja.",
                        options = listOf(
                            "risk score mora biti odvojen od konačne poslovne odluke",
                            """
                            read model treba da sadrži razloge procene, verziju modela i korišćene signale, a ne samo numerički rezultat
                            """.trimIndent(),
                            "Claims Service ne treba da izvršava teške analitičke upite nad istorijom pri svakoj obradi štete",
                            "Reporting modul treba da postane operativni izvor odluka za obradu štete",
                            "jedan zbirni score po korisniku dovoljan je za sve vrste osiguranja i sve buduće štete",
                            "eksterni AI servis može donositi konačne odluke ako ima dovoljno visok procenat tačnosti",
                            "različiti tipovi osiguranja mogu zahtevati različite modele, pragove i objašnjenja",
                            "kašnjenje risk score-a treba sakriti od službenika kako ne bi uticalo na njegovo poverenje u sistem"
                        ),
                        correctAnswers = listOf(
                            "risk score mora biti odvojen od konačne poslovne odluke",
                            """
                            read model treba da sadrži razloge procene, verziju modela i korišćene signale, a ne samo numerički rezultat
                            """.trimIndent(),
                            "Claims Service ne treba da izvršava teške analitičke upite nad istorijom pri svakoj obradi štete"
                        )
                    ),
                    ArchitectSeedBuilders.singleChoiceStep(
                        questionId = "A1.3",
                        stepNumber = 4,
                        title = "Označi glavni arhitektonski rizik koji ova arhitektura uvodi",
                        instruction = "Izaberi rizik koji uvodi izabrani pravac proširenja.",
                        options = listOf(
                            """
                            službenik može dobiti risk score koji kasni u odnosu na najnovije događaje, pa sistem mora jasno prikazati svežinu procene
                            """.trimIndent(),
                            "Claims Service više ne može da obrađuje štete bez Risk Assessment sloja",
                            "svi podaci iz relacionih baza moraju se premestiti u NoSQL bazu",
                            "ljudska odluka postaje nepotrebna jer sistem već računa rizik",
                            "Reporting modul mora biti potpuno uklonjen iz sistema Tačan odgovor:",
                            """
                            službenik može dobiti risk score koji kasni u odnosu na najnovije događaje, pa sistem mora jasno prikazati svežinu procene ________________________________________
                            """.trimIndent()
                        ),
                        correctAnswer = """
                    službenik može dobiti risk score koji kasni u odnosu na najnovije događaje, pa sistem mora jasno prikazati svežinu procene ________________________________________
                    """.trimIndent()
                    ),
                    ArchitectSeedBuilders.freeTextStep(
                        questionId = "A1.3",
                        stepNumber = 5,
                        title = "Kratko obrazloženje",
                        instruction = "Objasni arhitektonsku odluku, prihvaćeni kompromis i zašto osnovni tok sistema ostaje nezavisan."
                    )
                )
                ),
        SeedQuestion(
                    questionId = "A1.4",
                    level = LearningLevel.ARCHITECT.id,
                    type = QuestionType.ARCHITECTURE_EXTENSION.id,
                    title = "Uvođenje subscription i entitlement modela u postojeću platformu za profesionalne alate",
                    prompt = """
                    Kompanija već ima web platformu za profesionalne alate koje koriste pojedinci, timovi i organizacije. Platforma trenutno omogućava registraciju korisnika, korišćenje osnovnih alata i jednokratnu kupovinu pojedinih dodatnih funkcionalnosti.
                    Postojeće celine sistema:
                    • User Service upravlja korisničkim nalozima;
                    • Organization Service čuva timove, članove i uloge;
                    • Tool Service omogućava pristup različitim alatima platforme;
                    • Payment Service obrađuje jednokratne uplate;
                    • Invoice Service generiše račune;
                    • Admin Panel omogućava ručno uključivanje pojedinih funkcionalnosti;
                    • Reporting modul prikazuje prihode i korišćenje alata;
                    • trenutno ne postoji jedinstven model plana, pretplate i prava korišćenja;
                    • pojedini alati sami proveravaju da li korisnik ima pravo pristupa.
                    Novi zahtev:
                    Kompanija želi da uvede pretplatnički model sa više planova.
                    Planovi uključuju:
                    • Free plan sa ograničenim brojem projekata;
                    • Pro plan za pojedince;
                    • Team plan za organizacije;
                    • Enterprise plan sa posebnim ugovorima;
                    • trial period od 14 dana;
                    • grace period nakon neuspele naplate;
                    • add-on funkcionalnosti koje se mogu posebno uključiti;
                    • ograničenja po broju korisnika, projekata, izvoznih operacija i naprednih alata.
                    Ograničenja:
                    • Payment Service ne sme biti jedino mesto koje odlučuje šta korisnik sme da koristi;
                    • Tool Service ne treba da sadrži sva pravila svih planova;
                    • sistem mora razlikovati naplatu, pretplatu, plan i pravo korišćenja funkcionalnosti;
                    • korisnik ne sme izgubiti pristup bez jasnog statusa pretplate i grace period pravila;
                    • Enterprise izuzeci moraju biti kontrolisani i auditabilni;
                    • različiti alati treba da mogu brzo da provere pravo korišćenja bez komplikovanih upita kroz payment istoriju;
                    • kasnije se planira promena paketa i uvođenje novih add-on funkcionalnosti.
                    """.trimIndent(),
                    aiFollowUp = """
                    Ako korisniku ne uspe obnova pretplate, ali je organizacija u enterprise ugovoru koji predviđa grace period od 30 dana, kako arhitektura treba da odluči da li korisnik i dalje ima pristup naprednim alatima i gde se ta odluka proverava?
                    """.trimIndent(),
                    wave = 4,
                    difficulty = "expert",
                    orderIndex = 19,
                    estimatedMinutes = 10,
                    steps = listOf(
                    ArchitectSeedBuilders.singleChoiceStep(
                        questionId = "A1.4",
                        stepNumber = 1,
                        title = "Izaberi najbolji pravac proširenja",
                        instruction = "Izaberi arhitektonski pravac koji najbolje uvodi novu sposobnost bez narušavanja postojećeg sistema.",
                        options = listOf(
                            """
                            A. Poseban Subscription / Entitlement sloj sa planovima, statusima pretplate i pravima korišćenja
                            Uvesti sloj koji razdvaja naplatu od prava pristupa. Payment Service evidentira naplatu, Subscription sloj upravlja planom i statusom pretplate, a Entitlement model izlaže jasna prava korišćenja alatima platforme.
                            """.trimIndent(),
                            """
                            B. Proširenje Payment Service-a svim pravilima planova i pristupa
                            Payment Service odlučuje koji alat korisnik sme da koristi, na osnovu poslednje uplate, plana, add-on funkcionalnosti i enterprise izuzetaka.
                            """.trimIndent(),
                            """
                            C. Proširenje svakog Tool Service modula zasebnim pravilima planova
                            Svaki alat samostalno proverava plan korisnika, istoriju plaćanja, trial, grace period i enterprise ugovore.
                            """.trimIndent(),
                            """
                            D. Ručno podešavanje prava kroz Admin Panel
                            Administratori ručno uključuju i isključuju funkcionalnosti po korisniku ili organizaciji, a sistem ne uvodi poseban model pretplate.
                            """.trimIndent(),
                            """
                            E. Jedan billing flag u User Service-u
                            User Service čuva jednostavno polje isPaidUser, koje svi alati koriste da odluče da li korisnik ima pristup naprednim funkcionalnostima.
                            """.trimIndent()
                        ),
                        correctAnswer = """
                    A. Poseban Subscription / Entitlement sloj sa planovima, statusima pretplate i pravima korišćenja
                    Uvesti sloj koji razdvaja naplatu od prava pristupa. Payment Service evidentira naplatu, Subscription sloj upravlja planom i statusom pretplate, a Entitlement model izlaže jasna prava korišćenja alatima platforme.
                    """.trimIndent()
                    ),
                    ArchitectSeedBuilders.orderedCardsStep(
                        questionId = "A1.4",
                        stepNumber = 2,
                        title = "Poređaj tok implementacije",
                        instruction = "Poređaj korake od izvora događaja do korisničkog prikaza i fallback ponašanja.",
                        cards = listOf(
                            "definisati planove, add-on funkcionalnosti, limite i statuse pretplate",
                            """
                            Payment Service šalje rezultat naplate Subscription sloju, ali ne odlučuje direktno o svim pravima korišćenja
                            """.trimIndent(),
                            """
                            Subscription sloj ažurira status pretplate: active, trial, grace, expired, cancelled ili enterprise override
                            """.trimIndent(),
                            "Entitlement model prevodi plan i status pretplate u konkretna prava korišćenja funkcionalnosti",
                            "Tool Service proverava entitlement pre izvršavanja funkcionalnosti ili dostizanja limita",
                            """
                            Reporting povezuje korišćenje alata, plan, status pretplate, naplate i enterprise izuzetke Tačan redosled: 1.	definisati planove, add-on funkcionalnosti, limite i statuse pretplate 2.	Payment Service šalje rezultat naplate Subscription sloju, ali ne odlučuje direktno o svim pravima korišćenja 3.	Subscription sloj ažurira status pretplate: active, trial, grace, expired, cancelled ili enterprise override 4.	Entitlement model prevodi plan i status pretplate u konkretna prava korišćenja funkcionalnosti 5.	Tool Service proverava entitlement pre izvršavanja funkcionalnosti ili dostizanja limita 6.	Reporting povezuje korišćenje alata, plan, status pretplate, naplate i enterprise izuzetke ________________________________________
                            """.trimIndent()
                        ),
                        correctOrder = listOf(
                            "definisati planove, add-on funkcionalnosti, limite i statuse pretplate",
                            """
                            Payment Service šalje rezultat naplate Subscription sloju, ali ne odlučuje direktno o svim pravima korišćenja
                            """.trimIndent(),
                            """
                            Subscription sloj ažurira status pretplate: active, trial, grace, expired, cancelled ili enterprise override
                            """.trimIndent(),
                            "Entitlement model prevodi plan i status pretplate u konkretna prava korišćenja funkcionalnosti",
                            "Tool Service proverava entitlement pre izvršavanja funkcionalnosti ili dostizanja limita",
                            """
                            Reporting povezuje korišćenje alata, plan, status pretplate, naplate i enterprise izuzetke Tačan redosled: 1.	definisati planove, add-on funkcionalnosti, limite i statuse pretplate 2.	Payment Service šalje rezultat naplate Subscription sloju, ali ne odlučuje direktno o svim pravima korišćenja 3.	Subscription sloj ažurira status pretplate: active, trial, grace, expired, cancelled ili enterprise override 4.	Entitlement model prevodi plan i status pretplate u konkretna prava korišćenja funkcionalnosti 5.	Tool Service proverava entitlement pre izvršavanja funkcionalnosti ili dostizanja limita 6.	Reporting povezuje korišćenje alata, plan, status pretplate, naplate i enterprise izuzetke ________________________________________
                            """.trimIndent()
                        )
                    ),
                    ArchitectSeedBuilders.multiChoiceStep(
                        questionId = "A1.4",
                        stepNumber = 3,
                        title = "Izaberi 3 najvažnije arhitektonske odluke",
                        instruction = "Izaberi odluke koje najbolje objašnjavaju prednosti izabranog proširenja.",
                        options = listOf(
                            "sistem mora razlikovati payment, subscription status i entitlement kao različite odgovornosti",
                            """
                            pojedinačni alati treba da proveravaju pravo korišćenja preko entitlement modela, a ne kroz direktnu analizu payment istorije
                            """.trimIndent(),
                            "trial, grace period, add-on i enterprise override moraju biti jasno modelovani i auditabilni",
                            "Payment Service treba da odlučuje o svim pravima pristupa jer zna da li je korisnik platio",
                            "svaki alat treba sam da implementira sopstvena pravila za planove, limite i grace period",
                            "jedno polje isPaidUser je dovoljno za sve planove, organizacije i enterprise ugovore",
                            "ako naplata ne uspe, korisniku treba odmah ukinuti svaki pristup bez posebnog statusa",
                            "Reporting modul treba da bude autoritativni izvor prava jer vidi prihode i korišćenje"
                        ),
                        correctAnswers = listOf(
                            "sistem mora razlikovati payment, subscription status i entitlement kao različite odgovornosti",
                            """
                            pojedinačni alati treba da proveravaju pravo korišćenja preko entitlement modela, a ne kroz direktnu analizu payment istorije
                            """.trimIndent(),
                            "trial, grace period, add-on i enterprise override moraju biti jasno modelovani i auditabilni"
                        )
                    ),
                    ArchitectSeedBuilders.singleChoiceStep(
                        questionId = "A1.4",
                        stepNumber = 4,
                        title = "Označi glavni arhitektonski rizik koji ova arhitektura uvodi",
                        instruction = "Izaberi rizik koji uvodi izabrani pravac proširenja.",
                        options = listOf(
                            """
                            ako se payment, subscription i entitlement statusi ne modeluju jasno, korisnik može biti pogrešno blokiran ili može zadržati pristup funkcionalnosti koju više ne bi smeo da koristi
                            """.trimIndent(),
                            "Tool Service više ne može da radi ako postoji bilo kakav sistem pretplate",
                            "Invoice Service mora da odlučuje koje funkcionalnosti korisnik sme da koristi",
                            "Enterprise korisnici uvek moraju zaobići sve kontrole pristupa",
                            "Reporting modul treba da računa entitlement u realnom vremenu jer ima pregled prihoda Tačan odgovor:",
                            """
                            ako se payment, subscription i entitlement statusi ne modeluju jasno, korisnik može biti pogrešno blokiran ili može zadržati pristup funkcionalnosti koju više ne bi smeo da koristi ________________________________________
                            """.trimIndent()
                        ),
                        correctAnswer = """
                    ako se payment, subscription i entitlement statusi ne modeluju jasno, korisnik može biti pogrešno blokiran ili može zadržati pristup funkcionalnosti koju više ne bi smeo da koristi ________________________________________
                    """.trimIndent()
                    ),
                    ArchitectSeedBuilders.freeTextStep(
                        questionId = "A1.4",
                        stepNumber = 5,
                        title = "Kratko obrazloženje",
                        instruction = "Objasni arhitektonsku odluku, prihvaćeni kompromis i zašto osnovni tok sistema ostaje nezavisan."
                    )
                )
                ),
        SeedQuestion(
                    questionId = "A1.5",
                    level = LearningLevel.ARCHITECT.id,
                    type = QuestionType.ARCHITECTURE_EXTENSION.id,
                    title = "Uvođenje dinamičkog određivanja cena u platformu za železnički prevoz",
                    prompt = """
                    Železnička kompanija već ima digitalni sistem za prodaju karata.
                    Postojeće celine sistema:
                    •	Schedule Service čuva red vožnje, stanice i linije; 
                    •	Seat Inventory Service prati raspoloživost mesta po vozu, vagonu i klasi; 
                    •	Ticketing Service kreira i izdaje karte; 
                    •	Payment Service obrađuje naplate; 
                    •	Customer Service čuva korisničke profile i pogodnosti; 
                    •	Reporting modul prati prodaju, popunjenost i prihode; 
                    •	cene se trenutno određuju statički prema relaciji, klasi i tipu karte. 
                    Novi zahtev:
                    Kompanija želi da uvede dinamičko određivanje cena koje uzima u obzir:
                    •	popunjenost voza; 
                    •	vreme do polaska; 
                    •	sezonu i praznike; 
                    •	istoriju potražnje; 
                    •	tip korisnika; 
                    •	kanal prodaje; 
                    •	promotivne kampanje. 
                    Ograničenja:
                    •	korisnik mora videti jasnu cenu pre plaćanja; 
                    •	cena ne sme da se promeni usred checkout-a bez kontrole; 
                    •	Ticketing Service ne sme postati mesto za sve algoritme cena; 
                    •	kasnije treba podržati različite pricing strategije po zemlji, liniji i tipu voza; 
                    •	reporting treba da može da objasni zašto je karta prodata po određenoj ceni; 
                    •	sistem mora razlikovati “ponuđenu cenu” od “konačno naplaćene cene”.
                    """.trimIndent(),
                    aiFollowUp = """
                    Ako se cena promeni između trenutka kada korisnik otvori ponudu i trenutka kada klikne “plati”, kako bi arhitektura trebalo da odluči da li važi stari quote, novi quote ili kontrolisano ponovno potvrđivanje cene?
                    """.trimIndent(),
                    wave = 5,
                    difficulty = "expert",
                    orderIndex = 25,
                    estimatedMinutes = 10,
                    steps = listOf(
                    ArchitectSeedBuilders.singleChoiceStep(
                        questionId = "A1.5",
                        stepNumber = 1,
                        title = "Izaberi najbolji pravac proširenja",
                        instruction = "Izaberi arhitektonski pravac koji najbolje uvodi novu sposobnost bez narušavanja postojećeg sistema.",
                        options = listOf(
                            """
                            A. Pricing Decision sloj sa verzionisanim pravilima i price quote modelom
                            Poseban sloj računa cenu na osnovu relevantnih signala, izdaje vremenski ograničen price quote i čuva razloge i verziju pravila korišćenu za cenu.
                            """.trimIndent(),
                            """
                            B. Proširenje Ticketing Service-a pricing logikom
                            Ticketing Service pri svakom kreiranju karte direktno računa cenu koristeći podatke o popunjenosti, korisniku i kampanjama.
                            """.trimIndent(),
                            """
                            C. Reporting modul kao izvor dinamičkih cena
                            Pošto već sadrži istoriju prodaje i popunjenosti, Reporting modul se proširuje tako da vraća cenu tokom kupovine.
                            """.trimIndent(),
                            """
                            D. Price cache po relaciji i datumu polaska
                            Sistem periodično izračunava cene po relacijama i datumima, a checkout samo čita poslednju izračunatu vrednost.
                            """.trimIndent(),
                            """
                            E. Payment Service određuje konačnu cenu u trenutku naplate
                            Pošto je plaćanje poslednji korak, Payment Service dobija konačnu odgovornost da izračuna i naplati aktuelnu cenu.
                            """.trimIndent()
                        ),
                        correctAnswer = """
                    A. Pricing Decision sloj sa verzionisanim pravilima i price quote modelom
                    Poseban sloj računa cenu na osnovu relevantnih signala, izdaje vremenski ograničen price quote i čuva razloge i verziju pravila korišćenu za cenu.
                    """.trimIndent()
                    ),
                    ArchitectSeedBuilders.orderedCardsStep(
                        questionId = "A1.5",
                        stepNumber = 2,
                        title = "Poređaj tok implementacije",
                        instruction = "Poređaj korake od izvora događaja do korisničkog prikaza i fallback ponašanja.",
                        cards = listOf(
                            """
                            Pricing Decision sloj prima signale o popunjenosti, vremenu do polaska, korisničkom segmentu i kampanji
                            """.trimIndent(),
                            "pricing pravila i modeli se verzionišu kako bi kasnije bilo moguće objasniti odluku",
                            "sistem generiše price quote sa rokom važenja i razlogom formiranja cene",
                            "korisnik u checkout-u potvrđuje kartu na osnovu važećeg price quote-a",
                            "Ticketing Service kreira kartu vezanu za konkretan quote i konačno naplaćenu cenu",
                            """
                            Reporting čuva vezu između karte, quote-a, verzije pravila i naplaćenog iznosa Tačan redosled: 1.	Pricing Decision sloj prima signale o popunjenosti, vremenu do polaska, korisničkom segmentu i kampanji 2.	pricing pravila i modeli se verzionišu kako bi kasnije bilo moguće objasniti odluku 3.	sistem generiše price quote sa rokom važenja i razlogom formiranja cene 4.	korisnik u checkout-u potvrđuje kartu na osnovu važećeg price quote-a 5.	Ticketing Service kreira kartu vezanu za konkretan quote i konačno naplaćenu cenu 6.	Reporting čuva vezu između karte, quote-a, verzije pravila i naplaćenog iznosa ________________________________________
                            """.trimIndent()
                        ),
                        correctOrder = listOf(
                            """
                            Pricing Decision sloj prima signale o popunjenosti, vremenu do polaska, korisničkom segmentu i kampanji
                            """.trimIndent(),
                            "pricing pravila i modeli se verzionišu kako bi kasnije bilo moguće objasniti odluku",
                            "sistem generiše price quote sa rokom važenja i razlogom formiranja cene",
                            "korisnik u checkout-u potvrđuje kartu na osnovu važećeg price quote-a",
                            "Ticketing Service kreira kartu vezanu za konkretan quote i konačno naplaćenu cenu",
                            """
                            Reporting čuva vezu između karte, quote-a, verzije pravila i naplaćenog iznosa Tačan redosled: 1.	Pricing Decision sloj prima signale o popunjenosti, vremenu do polaska, korisničkom segmentu i kampanji 2.	pricing pravila i modeli se verzionišu kako bi kasnije bilo moguće objasniti odluku 3.	sistem generiše price quote sa rokom važenja i razlogom formiranja cene 4.	korisnik u checkout-u potvrđuje kartu na osnovu važećeg price quote-a 5.	Ticketing Service kreira kartu vezanu za konkretan quote i konačno naplaćenu cenu 6.	Reporting čuva vezu između karte, quote-a, verzije pravila i naplaćenog iznosa ________________________________________
                            """.trimIndent()
                        )
                    ),
                    ArchitectSeedBuilders.multiChoiceStep(
                        questionId = "A1.5",
                        stepNumber = 3,
                        title = "Izaberi 3 najvažnije arhitektonske odluke",
                        instruction = "Izaberi odluke koje najbolje objašnjavaju prednosti izabranog proširenja.",
                        options = listOf(
                            "sistem mora razlikovati izračunatu ponudu cene od konačno naplaćene cene",
                            "pricing pravila treba verzionisati zbog objašnjivosti i kasnijeg audita",
                            "Ticketing Service ne treba da sadrži sve pricing algoritme i kampanjska pravila",
                            "cena u Payment Service-u treba uvek ponovo da se izračuna bez obzira na quote",
                            "price cache je dovoljan kao jedini autoritativni izvor cene za sve situacije",
                            "Reporting modul može računati cenu u checkout-u jer već ima istorijske podatke",
                            "price quote može isteći, ali taj istek mora biti jasno modelovan u toku kupovine",
                            "korisnik ne mora znati da se cena promenila ako sistem smatra da je nova cena optimalna"
                        ),
                        correctAnswers = listOf(
                            "sistem mora razlikovati izračunatu ponudu cene od konačno naplaćene cene",
                            "pricing pravila treba verzionisati zbog objašnjivosti i kasnijeg audita",
                            "Ticketing Service ne treba da sadrži sve pricing algoritme i kampanjska pravila"
                        )
                    ),
                    ArchitectSeedBuilders.singleChoiceStep(
                        questionId = "A1.5",
                        stepNumber = 4,
                        title = "Označi glavni arhitektonski rizik koji ova arhitektura uvodi",
                        instruction = "Izaberi rizik koji uvodi izabrani pravac proširenja.",
                        options = listOf(
                            """
                            cena može postati nekonzistentna između prikaza ponude, checkout-a, izdate karte, naplate i reportinga ako sistem jasno ne modeluje važenje price quote-a
                            """.trimIndent(),
                            "Payment Service treba uvek ponovo da izračuna cenu jer se nalazi na kraju procesa kupovine",
                            "korisnik ne mora da vidi promenu cene ako sistem proceni da je nova cena optimalnija",
                            "Reporting modul treba da bude autoritativni izvor cene jer sadrži istoriju prodaje i popunjenosti",
                            "price quote treba čuvati samo lokalno u mobilnoj aplikaciji kako bi checkout bio brži Tačan odgovor:",
                            """
                            cena može postati nekonzistentna između prikaza ponude, checkout-a, izdate karte, naplate i reportinga ako sistem jasno ne modeluje važenje price quote-a
                            """.trimIndent()
                        ),
                        correctAnswer = """
                    cena može postati nekonzistentna između prikaza ponude, checkout-a, izdate karte, naplate i reportinga ako sistem jasno ne modeluje važenje price quote-a
                    """.trimIndent()
                    ),
                    ArchitectSeedBuilders.freeTextStep(
                        questionId = "A1.5",
                        stepNumber = 5,
                        title = "Kratko obrazloženje",
                        instruction = "Objasni arhitektonsku odluku, prihvaćeni kompromis i zašto osnovni tok sistema ostaje nezavisan."
                    )
                )
                )
    )
}
