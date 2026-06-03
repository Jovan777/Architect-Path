package com.example.pmuprojekat.data.seed.architect

import com.example.pmuprojekat.core.model.LearningLevel
import com.example.pmuprojekat.core.model.QuestionType
import com.example.pmuprojekat.data.seed.SeedQuestion

object ArchitectReviewSeed {
    val questions: List<SeedQuestion> = listOf(
        SeedQuestion(
                    questionId = "A3.1",
                    level = LearningLevel.ARCHITECT.id,
                    type = QuestionType.ARCHITECTURE_REVIEW.id,
                    title = "Revizija arhitekture platforme za video kurseve",
                    prompt = """
                    Platforma za video kurseve ima sledeći pojednostavljeni dijagram. Pogledaj sliku ispod.
                    Opis sistema:
                    •	Backend API obrađuje login, kurseve, testove, plaćanja i video lekcije; 
                    •	video fajlovi se čuvaju na lokalnom storage-u istog servera; 
                    •	isti Backend API učestvuje i u autorizaciji korisnika i u isporuci video sadržaja; 
                    •	nema CDN-a; 
                    •	kada mnogo korisnika gleda video, usporavaju se login, testovi i plaćanja; 
                    •	očekuje se rast broja korisnika iz više regiona.
                    """.trimIndent(),
                    diagramImageName = "a3_1_slika",
                    aiFollowUp = """
                    Ako korisnik plati kurs, dobije signed URL za video, a zatim mu se uplata poništi, kako bi arhitektonski kontrolisao da pristup video sadržaju ne ostane otvoren duže nego što treba?
                    """.trimIndent(),
                    wave = 1,
                    difficulty = "expert",
                    orderIndex = 3,
                    estimatedMinutes = 10,
                    steps = listOf(
                    ArchitectSeedBuilders.hotspotStep(
                        questionId = "A3.1",
                        stepNumber = 1,
                        title = "Označi kritične tačke na dijagramu",
                        instruction = "Označi elemente ili tokove koji predstavljaju glavnu arhitektonsku slabost.",
                        options = listOf(
                            """
                            1. Mobile/Web App → Backend API
                            """.trimIndent(),
                            "2. Backend API kao centralna tačka za poslovnu logiku i video isporuku",
                            """
                            3. Backend API → SQL DB
                            """.trimIndent(),
                            """
                            4. Backend API → Local File Storage
                            """.trimIndent(),
                            """
                            5. Local File Storage → Video Streaming
                            """.trimIndent(),
                            """
                            6. Backend API → Payment Provider
                            """.trimIndent(),
                            "7. nedostatak CDN/media delivery sloja",
                            "8. SQL DB kao izvor podataka o korisnicima i kursevima"
                        ),
                        correctAnswers = listOf(
                            "2. Backend API kao centralna tačka za poslovnu logiku i video isporuku",
                            """
                            4. Backend API → Local File Storage
                            """.trimIndent(),
                            """
                            5. Local File Storage → Video Streaming
                            """.trimIndent(),
                            "7. nedostatak CDN/media delivery sloja"
                        )
                    ),
                    ArchitectSeedBuilders.categoryStep(
                        questionId = "A3.1",
                        stepNumber = 2,
                        title = "Razdvoji simptome od arhitektonskih uzroka",
                        instruction = "Razvrstaj kartice u posledice koje se vide i uzroke koji ih stvaraju.",
                        categories = listOf(
                            ArchitectCategorySeed(
                                title = "Simptomi",
                                items = listOf(
                                    "login i testovi usporavaju kada raste gledanje videa",
                                    "korisnici iz udaljenih regiona mogu imati lošije video iskustvo",
                                    "plaćanja dele deo runtime opterećenja sa video saobraćajem"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Arhitektonski uzroci",
                                items = listOf(
                                    "video fajlovi su vezani za lokalni storage aplikacionog servera",
                                    "isti Backend API učestvuje u poslovnim operacijama i video isporuci",
                                    "ne postoji poseban media delivery/CDN sloj"
                                )
                            )
                        )
                    ),
                    ArchitectSeedBuilders.singleChoiceStep(
                        questionId = "A3.1",
                        stepNumber = 3,
                        title = "Izaberi najbolju prioritetnu reviziju",
                        instruction = "Izaberi reviziju koja najbolje rešava dominantni arhitektonski problem.",
                        options = listOf(
                            """
                            A. Object storage + CDN + tokenizovan pristup video sadržaju
                            Video fajlovi se premeštaju u object storage, isporuka ide preko CDN-a, a Backend API izdaje vremenski ograničen pristup za ovlašćene korisnike.
                            """.trimIndent(),
                            """
                            B. Read replica za SQL bazu i dodatni indeksi nad tabelama kurseva
                            Baza se rasterećuje za čitanje kurseva i testova, ali video ostaje na istom aplikacionom serveru.
                            """.trimIndent(),
                            """
                            C. Horizontalno skaliranje Backend API-ja uz zajednički file storage
                            Dodaje se više instanci Backend API-ja, ali video fajlovi i dalje moraju biti dostupni svim instancama kroz zajednički storage.
                            """.trimIndent(),
                            """
                            D. Izdvajanje Payment servisa pre rešavanja video sloja
                            Plaćanja se izdvajaju u poseban servis, dok video i dalje prolazi kroz postojeći Backend API.
                            """.trimIndent()
                        ),
                        correctAnswer = """
                    A. Object storage + CDN + tokenizovan pristup video sadržaju
                    Video fajlovi se premeštaju u object storage, isporuka ide preko CDN-a, a Backend API izdaje vremenski ograničen pristup za ovlašćene korisnike.
                    """.trimIndent()
                    ),
                    ArchitectSeedBuilders.categoryStep(
                        questionId = "A3.1",
                        stepNumber = 4,
                        title = "Mapiraj dobitke i nove rizike",
                        instruction = "Rasporedi posledice revizije u dobitke i nove rizike koje treba projektovati.",
                        categories = listOf(
                            ArchitectCategorySeed(
                                title = "Dobici",
                                items = listOf(
                                    "Backend API se rasterećuje direktne isporuke velikih video fajlova",
                                    "video se može isporučivati bliže korisnicima iz različitih regiona",
                                    "testovi, login i plaćanja manje se takmiče sa video streaming-om za iste resurse"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Novi rizici / stvari koje treba projektovati",
                                items = listOf(
                                    "potrebno je osmisliti kontrolu pristupa video sadržaju kroz signed URL/token mehanizam",
                                    "potrebno je rešiti invalidaciju pristupa kada korisnik izgubi pravo na kurs",
                                    "uvodi se zavisnost od spoljnog storage/CDN sloja"
                                )
                            )
                        )
                    ),
                    ArchitectSeedBuilders.freeTextStep(
                        questionId = "A3.1",
                        stepNumber = 5,
                        title = "Kratko obrazloženje",
                        instruction = "Objasni zašto je izabrana revizija prioritetna i koji novi rizik uvodi."
                    )
                )
                ),
        SeedQuestion(
                    questionId = "A3.2",
                    level = LearningLevel.ARCHITECT.id,
                    type = QuestionType.ARCHITECTURE_REVIEW.id,
                    title = "Revizija arhitekture sistema za distribuciju humanitarne pomoći",
                    prompt = """
                    Organizacija koristi sistem za distribuciju humanitarne pomoći u kriznim situacijama.
                    Pojednostavljen dijagram je prikazan na slici ispod.
                    Opis sistema:
                    •	terenski radnici preko mobilne aplikacije registruju korisnike pomoći; 
                    •	sistem proverava da li je korisnik već dobio paket pomoći; 
                    •	skladišta ažuriraju stanje zaliha; 
                    •	SMS Provider šalje obaveštenja korisnicima; 
                    •	Reporting Dashboard prikazuje broj podeljenih paketa; 
                    •	sav rad zavisi od dostupnosti Central Backend API-ja; 
                    •	terenski rad često se odvija u zonama sa slabom ili nestabilnom internet konekcijom; 
                    •	kada mreža padne, terenski radnici zapisuju podatke na papir i kasnije ih ručno unose; 
                    •	to dovodi do duplih korisnika, kašnjenja i nepouzdanih izveštaja. 

                    Problem:
                    Sistem treba unaprediti tako da terenski rad može da se nastavi i kada konekcija nije stabilna, ali bez nekontrolisanog dupliranja korisnika i paketa pomoći.
                    """.trimIndent(),
                    diagramImageName = "a3_2_slika",
                    aiFollowUp = """
                    Ako dva terenska tima offline registruju istog korisnika na različitim lokacijama, kako bi arhitektonski razlikovao “stvarni duplikat” od legitimne potrebe za dodatnom pomoći?
                    """.trimIndent(),
                    wave = 2,
                    difficulty = "expert",
                    orderIndex = 9,
                    estimatedMinutes = 10,
                    steps = listOf(
                    ArchitectSeedBuilders.hotspotStep(
                        questionId = "A3.2",
                        stepNumber = 1,
                        title = "Označi kritične tačke ili tokove",
                        instruction = "Označi elemente ili tokove koji predstavljaju glavnu arhitektonsku slabost.",
                        options = listOf(
                            """
                            1. Field Mobile App → Central Backend API kao jedini način rada na terenu
                            """.trimIndent(),
                            """
                            2. Central Backend API → Beneficiary SQL DB
                            """.trimIndent(),
                            """
                            3. Central Backend API → Inventory DB
                            """.trimIndent(),
                            """
                            4. Central Backend API → SMS Provider
                            """.trimIndent(),
                            "5. Reporting Dashboard čita centralne podatke",
                            "6. nepostojanje offline queue/local sync mehanizma u Field Mobile App-u",
                            "7. ručni papirni fallback van sistema",
                            "8. SMS Provider kao eksterni servis"
                        ),
                        correctAnswers = listOf(
                            """
                            1. Field Mobile App → Central Backend API kao jedini način rada na terenu
                            """.trimIndent(),
                            "6. nepostojanje offline queue/local sync mehanizma u Field Mobile App-u",
                            "7. ručni papirni fallback van sistema"
                        )
                    ),
                    ArchitectSeedBuilders.categoryStep(
                        questionId = "A3.2",
                        stepNumber = 2,
                        title = "Razdvoji simptome od arhitektonskih uzroka",
                        instruction = "Razvrstaj kartice u posledice koje se vide i uzroke koji ih stvaraju.",
                        categories = listOf(
                            ArchitectCategorySeed(
                                title = "Operativne posledice",
                                items = listOf(
                                    "terenski radnici prelaze na papir kada mreža nije dostupna",
                                    "moguće je dupliranje korisnika pomoći",
                                    "izveštaji kasne i ne prikazuju realno stanje"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Arhitektonski uzroci",
                                items = listOf(
                                    "nema lokalnog reda događaja za kasniju sinhronizaciju",
                                    "centralni API je jedina tačka kroz koju se može izvršiti registracija",
                                    "sistem nema pravila za razrešavanje konflikata nakon povratka konekcije"
                                )
                            )
                        )
                    ),
                    ArchitectSeedBuilders.singleChoiceStep(
                        questionId = "A3.2",
                        stepNumber = 3,
                        title = "Izaberi najbolju prioritetnu reviziju",
                        instruction = "Izaberi reviziju koja najbolje rešava dominantni arhitektonski problem.",
                        options = listOf(
                            """
                            A. Offline-first terenski tok sa lokalnim event queue-om i kontrolisanom sinhronizacijom
                            Mobilna aplikacija lokalno beleži pokušaje registracije i podele pomoći, a zatim ih sinhronizuje sa centralnim sistemom uz pravila za konflikte.
                            """.trimIndent(),
                            """
                            B. Veći centralni server i stabilniji hosting
                            Centralni Backend API se premešta na jaču infrastrukturu kako bi ređe bio nedostupan.
                            """.trimIndent(),
                            """
                            C. Read-only režim za terensku aplikaciju kada nema interneta
                            Terenski radnik može da pregleda ranije preuzete podatke, ali ne može da evidentira novu podelu pomoći dok se veza ne vrati.
                            """.trimIndent(),
                            """
                            D. Direktan pristup mobilne aplikacije centralnim bazama
                            Aplikacija dobija mogućnost direktnog pisanja u Beneficiary i Inventory baze kada API nije dostupan.
                            """.trimIndent()
                        ),
                        correctAnswer = """
                    A. Offline-first terenski tok sa lokalnim event queue-om i kontrolisanom sinhronizacijom
                    Mobilna aplikacija lokalno beleži pokušaje registracije i podele pomoći, a zatim ih sinhronizuje sa centralnim sistemom uz pravila za konflikte.
                    """.trimIndent()
                    ),
                    ArchitectSeedBuilders.categoryStep(
                        questionId = "A3.2",
                        stepNumber = 4,
                        title = "Mapiraj dobitke i nove rizike",
                        instruction = "Rasporedi posledice revizije u dobitke i nove rizike koje treba projektovati.",
                        categories = listOf(
                            ArchitectCategorySeed(
                                title = "Dobici revizije",
                                items = listOf(
                                    "terenski rad može da se nastavi i bez stabilne internet konekcije",
                                    "lokalni događaji mogu se kasnije proverljivo sinhronizovati sa centralnim sistemom",
                                    "sistem može razlikovati potvrđene i nepotvrđene terenske događaje"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Novi rizici / pravila koja treba projektovati",
                                items = listOf(
                                    "potrebno je projektovati pravila za duplikate i konflikte pri sinhronizaciji",
                                    "SMS obaveštenja i umanjenje centralnih zaliha moraju čekati centralnu potvrdu"
                                )
                            )
                        )
                    ),
                    ArchitectSeedBuilders.freeTextStep(
                        questionId = "A3.2",
                        stepNumber = 5,
                        title = "Kratko obrazloženje",
                        instruction = "Objasni zašto je izabrana revizija prioritetna i koji novi rizik uvodi."
                    )
                )
                ),
        SeedQuestion(
                    questionId = "A3.3",
                    level = LearningLevel.ARCHITECT.id,
                    type = QuestionType.ARCHITECTURE_REVIEW.id,
                    title = "Revizija arhitekture sistema za online aukcije",
                    prompt = """
                    Platforma za online aukcije omogućava korisnicima da licitiraju za proizvode u realnom vremenu.
                    Pojednostavljen dijagram je prikazan na slici ispod.
                    Opis sistema:
                    •	korisnici šalju ponude preko Web/Mobile aplikacije; 
                    •	Auction Backend API proverava korisnika, učitava aukciju, poredi ponudu i upisuje novu najveću ponudu; 
                    •	stanje aukcije se čuva u SQL bazi; 
                    •	svi klijenti osvežavaju stanje periodičnim polling-om; 
                    •	Email/SMS Service šalje obaveštenja kada korisnik bude nadmašen; 
                    •	tokom popularnih aukcija dolazi do velikog broja istovremenih ponuda; 
                    •	korisnici ponekad vide zastarelu najveću ponudu; 
                    •	sistem povremeno prihvati ponudu koja je u trenutku prikaza delovala validno, ali je u međuvremenu nadmašena; 
                    •	operateri žele jasan trag svih pokušaja licitiranja. 
                    Problem:
                    Sistem treba unaprediti tako da bolje podrži aukcije sa velikim brojem učesnika, real-time prikaz i proverljiv tok ponuda.
                    """.trimIndent(),
                    diagramImageName = "a3_3_slika",
                    aiFollowUp = """
                    Ako dve ponude stignu skoro istovremeno iz različitih regiona, kako bi arhitektura trebalo da odluči koja je prihvaćena, a da sistem ostane proverljiv i razumljiv korisnicima?
                    """.trimIndent(),
                    wave = 3,
                    difficulty = "expert",
                    orderIndex = 15,
                    estimatedMinutes = 10,
                    steps = listOf(
                    ArchitectSeedBuilders.hotspotStep(
                        questionId = "A3.3",
                        stepNumber = 1,
                        title = "Označi kritične tačke ili tokove",
                        instruction = "Označi elemente ili tokove koji predstavljaju glavnu arhitektonsku slabost.",
                        options = listOf(
                            "1. Web/Mobile Client koristi polling za stanje aukcije",
                            "2. Auction Backend API istovremeno validira ponude i distribuira stanje klijentima",
                            "3. Auction SQL DB čuva trenutno stanje aukcije",
                            "4. ne postoji poseban real-time kanal za prikaz promena ponuda",
                            "5. nema eksplicitnog loga svih pokušaja licitiranja kao događaja",
                            "6. Email/SMS Service šalje obaveštenja korisnicima",
                            "7. Admin Reporting čita podatke za operatere",
                            "8. User DB čuva korisničke naloge"
                        ),
                        correctAnswers = listOf(
                            "1. Web/Mobile Client koristi polling za stanje aukcije",
                            "2. Auction Backend API istovremeno validira ponude i distribuira stanje klijentima",
                            "4. ne postoji poseban real-time kanal za prikaz promena ponuda",
                            "5. nema eksplicitnog loga svih pokušaja licitiranja kao događaja"
                        )
                    ),
                    ArchitectSeedBuilders.categoryStep(
                        questionId = "A3.3",
                        stepNumber = 2,
                        title = "Razdvoji simptome od arhitektonskih uzroka",
                        instruction = "Razvrstaj kartice u posledice koje se vide i uzroke koji ih stvaraju.",
                        categories = listOf(
                            ArchitectCategorySeed(
                                title = "Simptomi",
                                items = listOf(
                                    "korisnici vide zastarelu najveću ponudu",
                                    "popularne aukcije stvaraju veliki pritisak na API i bazu",
                                    "korisnik nekad misli da je njegova ponuda validna iako je stanje već promenjeno"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Arhitektonski uzroci",
                                items = listOf(
                                    "sistem nema real-time distribuciju promena stanja aukcije",
                                    "svi pokušaji licitiranja nisu jasno modelovani kao proverljiv tok događaja",
                                    "polling povećava broj zahteva i kašnjenje prikaza"
                                )
                            )
                        )
                    ),
                    ArchitectSeedBuilders.singleChoiceStep(
                        questionId = "A3.3",
                        stepNumber = 3,
                        title = "Izaberi najbolju prioritetnu reviziju",
                        instruction = "Izaberi reviziju koja najbolje rešava dominantni arhitektonski problem.",
                        options = listOf(
                            """
                            A. Real-time bidding sloj sa event logom ponuda i projekcijom trenutnog stanja aukcije
                            Ponude se tretiraju kao događaji, validiraju se kroz kontrolisan tok, a klijenti dobijaju promene kroz real-time kanal.
                            """.trimIndent(),
                            """
                            B. Read replica nad Auction SQL bazom
                            Čitanje trenutnog stanja se prebacuje na repliku, dok se upisi ponuda i dalje rade kroz postojeći API.
                            """.trimIndent(),
                            """
                            C. Agresivniji polling sa kraćim intervalom osvežavanja
                            Klijenti češće proveravaju stanje aukcije kako bi brže videli promenu najveće ponude.
                            """.trimIndent(),
                            """
                            D. Direktan WebSocket pristup Auction SQL bazi
                            Klijenti se povezuju na real-time sloj koji direktno prati promene u bazi i emituje ih korisnicima.
                            """.trimIndent()
                        ),
                        correctAnswer = """
                    A. Real-time bidding sloj sa event logom ponuda i projekcijom trenutnog stanja aukcije
                    Ponude se tretiraju kao događaji, validiraju se kroz kontrolisan tok, a klijenti dobijaju promene kroz real-time kanal.
                    """.trimIndent()
                    ),
                    ArchitectSeedBuilders.categoryStep(
                        questionId = "A3.3",
                        stepNumber = 4,
                        title = "Mapiraj dobitke i rizike",
                        instruction = "Rasporedi posledice revizije u dobitke i nove rizike koje treba projektovati.",
                        categories = listOf(
                            ArchitectCategorySeed(
                                title = "Dobici revizije",
                                items = listOf(
                                    "svi pokušaji licitiranja mogu se auditovati",
                                    "trenutni prikaz aukcije može biti projekcija iz toka validiranih ponuda",
                                    "real-time kanal smanjuje potrebu za čestim polling-om"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Novi rizici / pravila koja treba projektovati",
                                items = listOf(
                                    "potrebno je rešiti redosled ponuda i konkurentno prihvatanje",
                                    "sistem mora jasno razlikovati “ponuda primljena” i “ponuda prihvaćena”"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Kartica koja nije dobar zaključak",
                                items = listOf(
                                    "klijent više ne mora da razume nikakvo stanje aukcije"
                                )
                            )
                        )
                    ),
                    ArchitectSeedBuilders.freeTextStep(
                        questionId = "A3.3",
                        stepNumber = 5,
                        title = "Kratko obrazloženje",
                        instruction = "Objasni zašto je izabrana revizija prioritetna i koji novi rizik uvodi."
                    )
                )
                ),
        SeedQuestion(
                    questionId = "A3.4",
                    level = LearningLevel.ARCHITECT.id,
                    type = QuestionType.ARCHITECTURE_REVIEW.id,
                    title = "Revizija arhitekture platforme za upravljanje bezbednosnim incidentima",
                    prompt = """
                    Organizacija koristi internu platformu za upravljanje bezbednosnim incidentima.
                    Pojednostavljen dijagram je prikazan na slici ispod.
                    Opis sistema:
                    •	alerti iz više bezbednosnih alata ulaze kroz isti Incident Backend API; 
                    •	analitičari kroz web konzolu otvaraju, povezuju i zatvaraju incidente; 
                    •	log fajlovi i prilozi čuvaju se na deljenom storage-u; 
                    •	Dashboard & Reports čita iz iste operativne baze; 
                    •	obaveštenja idu preko Email/Chat Alerts; 
                    •	kada stigne veliki broj alerta, usporava se rad analitičara nad slučajevima; 
                    •	više alata može poslati alert o istom problemu; 
                    •	analitičari žele jasan trag: alert primljen, incident otvoren, eskalacija, sanacija, zatvaranje; 
                    •	sistem trenutno meša ingestion alerta, upravljanje slučajem i izveštavanje u isti tok. 
                    Problem:
                    Sistem treba unaprediti tako da podrži veći obim bezbednosnih događaja, jasniji tok incidenta i bolju odvojenost između prijema alerta i rada nad slučajem.
                    """.trimIndent(),
                    diagramImageName = "a3_4_slika",
                    aiFollowUp = """
                    Ako tri različita alata pošalju alerte koji liče na isti napad, ali se kasnije ispostavi da jedan od njih predstavlja zaseban incident, kako bi arhitektura trebalo da podrži i korelaciju i naknadno razdvajanje bez gubitka istorije odluke?
                    """.trimIndent(),
                    wave = 4,
                    difficulty = "expert",
                    orderIndex = 21,
                    estimatedMinutes = 10,
                    steps = listOf(
                    ArchitectSeedBuilders.hotspotStep(
                        questionId = "A3.4",
                        stepNumber = 1,
                        title = "Označi kritične tačke ili tokove",
                        instruction = "Označi elemente ili tokove koji predstavljaju glavnu arhitektonsku slabost.",
                        options = listOf(
                            "1. više izvora alerta ulazi kroz isti Incident Backend API koji koriste i analitičari",
                            "2. Dashboard & Reports čita iz iste operativne baze kao i rad analitičara",
                            "3. Shared File Storage čuva priloge i logove",
                            "4. ne postoji poseban ingestion i korelacioni sloj za alert događaje",
                            "5. nema jasnog event toka za životni ciklus incidenta",
                            "6. Email/Chat Alerts šalje obaveštenja",
                            "7. Analyst Web Console prikazuje slučajeve",
                            "8. Case SQL DB čuva podatke o incidentima"
                        ),
                        correctAnswers = listOf(
                            "1. više izvora alerta ulazi kroz isti Incident Backend API koji koriste i analitičari",
                            "2. Dashboard & Reports čita iz iste operativne baze kao i rad analitičara",
                            "4. ne postoji poseban ingestion i korelacioni sloj za alert događaje",
                            "5. nema jasnog event toka za životni ciklus incidenta"
                        )
                    ),
                    ArchitectSeedBuilders.categoryStep(
                        questionId = "A3.4",
                        stepNumber = 2,
                        title = "Razdvoji simptome od arhitektonskih uzroka",
                        instruction = "Razvrstaj kartice u posledice koje se vide i uzroke koji ih stvaraju.",
                        categories = listOf(
                            ArchitectCategorySeed(
                                title = "Simptomi",
                                items = listOf(
                                    "rad analitičara usporava kada raste broj pristiglih alerta",
                                    "više alata može otvoriti duplirane ili fragmentisane incidente",
                                    "analitičar teže razume kako je incident evoluirao kroz vreme"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Arhitektonski uzroci",
                                items = listOf(
                                    "nema odvojenog ingestion/korelacionog toka za alert događaje",
                                    "lifecycle incidenta nije modelovan kao jasan niz događaja",
                                    "izveštaji i dashboard opterećuju istu bazu koja podržava operativni rad"
                                )
                            )
                        )
                    ),
                    ArchitectSeedBuilders.singleChoiceStep(
                        questionId = "A3.4",
                        stepNumber = 3,
                        title = "Izaberi najbolju prioritetnu reviziju",
                        instruction = "Izaberi reviziju koja najbolje rešava dominantni arhitektonski problem.",
                        options = listOf(
                            """
                            A. Uvesti alert-ingestion i correlation sloj, uz odvojeni incident lifecycle model
                            Prijem visokog obima alerta i njihova korelacija izdvajaju se u poseban tok, dok incident lifecycle ostaje kontrolisan poslovni proces sa posebnim prikazima za analitičare i izveštavanje.
                            """.trimIndent(),
                            """
                            B. Skalirati Incident Backend API uz read replike i keširanje
                            Horizontalno skaliranje API sloja, uvođenje read replika i keširanja poboljšava performanse sistema i smanjuje opterećenje baze, uz minimalne promene postojeće arhitekture.
                            """.trimIndent(),
                            """
                            C. Uvesti analitički sloj sa asinhronim osvežavanjem podataka
                            Analitičari rade preko posebnog Dashboard & Reports sloja koji koristi asinhrono osvežavanje i agregacije, čime se smanjuje opterećenje operativnog sistema i poboljšava preglednost podataka.
                            """.trimIndent(),
                            """
                            D. Migrirati Case bazu na hibridni model (SQL + dokument store)
                            Uvođenje dokument baze uz postojeći relacijski model omogućava fleksibilnije čuvanje alert priloga i kompleksnih struktura podataka, uz zadržavanje transakcionog integriteta gde je potreban.
                            """.trimIndent()
                        ),
                        correctAnswer = """
                    A. Uvesti alert-ingestion i correlation sloj, uz odvojeni incident lifecycle model
                    Prijem visokog obima alerta i njihova korelacija izdvajaju se u poseban tok, dok incident lifecycle ostaje kontrolisan poslovni proces sa posebnim prikazima za analitičare i izveštavanje.
                    """.trimIndent()
                    ),
                    ArchitectSeedBuilders.categoryStep(
                        questionId = "A3.4",
                        stepNumber = 4,
                        title = "Mapiraj dobitke i rizike",
                        instruction = "Rasporedi posledice revizije u dobitke i nove rizike koje treba projektovati.",
                        categories = listOf(
                            ArchitectCategorySeed(
                                title = "Dobici revizije",
                                items = listOf(
                                    "ingestion alerta više ne mora da se takmiči sa radom analitičara nad slučajevima",
                                    "korelacija može smanjiti duplirane incidente i fragmentisane prikaze istog problema",
                                    "incident lifecycle postaje auditabilniji i lakši za rekonstrukciju"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Novi rizici / pravila koja treba projektovati",
                                items = listOf(
                                    "potrebno je projektovati pravila kada alert postaje incident, a kada samo dopunjuje postojeći slučaj",
                                    "read modeli za izveštavanje mogu kasniti u odnosu na operativni tok"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Kartica koja nije dobar zaključak",
                                items = listOf(
                                    "menjanjem baze automatski nestaje potreba za korelacijom događaja"
                                )
                            )
                        )
                    ),
                    ArchitectSeedBuilders.freeTextStep(
                        questionId = "A3.4",
                        stepNumber = 5,
                        title = "Kratko obrazloženje",
                        instruction = "Objasni zašto je izabrana revizija prioritetna i koji novi rizik uvodi."
                    )
                )
                ),
        SeedQuestion(
                    questionId = "A3.5",
                    level = LearningLevel.ARCHITECT.id,
                    type = QuestionType.ARCHITECTURE_REVIEW.id,
                    title = "Revizija arhitekture operativnog sistema za obradu velikog broja pozadinskih zadataka",
                    prompt = """
                    Kompanija koristi interni serverski operativni sistem za izvršavanje velikog broja pozadinskih zadataka. Sistem se koristi za obradu fajlova, slanje obaveštenja, generisanje izveštaja, indeksiranje dokumenata i periodične sistemske provere.
                    Pojednostavljen dijagram je prikazan na slici ispod.
                    Opis sistema:
                    • svi korisnički i sistemski zadaci ulaze u jedan globalni red čekanja;
                    • isti red koriste kratki interaktivni zadaci i dugi batch poslovi;
                    • Kernel Scheduler uzima zadatke redom, bez jasnog razlikovanja prioriteta;
                    • Worker procesi obrađuju sve tipove zadataka na isti način;
                    • svi zadaci intenzivno koriste isti shared disk storage;
                    • sistemski logovi se upisuju na isti disk koji koriste i radni zadaci;
                    • kada se pokrene veliki batch posao, interaktivni zadaci počinju da kasne;
                    • logovanje ponekad dodatno usporava obradu zadataka;
                    • nema jasne izolacije između kritičnih sistemskih zadataka i niskoprioritetnih poslova;
                    • administratori teško utvrđuju da li je problem u scheduler-u, disk I/O opterećenju ili nekoj klasi zadataka.
                    Problem:
                    Sistem treba revidirati tako da bolje podrži različite klase zadataka, prioritete, izolaciju resursa i stabilan rad pod opterećenjem, bez toga da jedan veliki batch posao ugrozi ceo operativni sistem.
                    """.trimIndent(),
                    diagramImageName = "a3_5_slika",
                    aiFollowUp = """
                    Ako batch posao niskog prioriteta već dugo čeka, ali stalno pristižu kratki interaktivni zadaci višeg prioriteta, kako bi arhitektura scheduler-a trebalo da spreči gladovanje batch posla, a da pritom ne ugrozi odziv kritičnih i interaktivnih zadataka?
                    """.trimIndent(),
                    wave = 5,
                    difficulty = "expert",
                    orderIndex = 27,
                    estimatedMinutes = 10,
                    steps = listOf(
                    ArchitectSeedBuilders.hotspotStep(
                        questionId = "A3.5",
                        stepNumber = 1,
                        title = "Označi kritične tačke ili tokove",
                        instruction = "Označi elemente ili tokove koji predstavljaju glavnu arhitektonsku slabost.",
                        options = emptyList(),
                        correctAnswers = listOf(
                            "1. svi zadaci ulaze u Single Global Task Queue bez razdvajanja po prioritetu ili tipu",
                            "2. Kernel Scheduler obrađuje zadatke bez jasne politike prioriteta, kvota i preempcije",
                            "3. Worker procesi izvršavaju sve tipove zadataka bez izolacije resursa",
                            "4. Shared Disk Storage koriste i radni zadaci i sistemski logovi",
                            """
                            8. ne postoji poseban mehanizam za monitoring scheduler-a, I/O opterećenja i čekanja po klasama zadataka
                            """.trimIndent()
                        )
                    ),
                    ArchitectSeedBuilders.categoryStep(
                        questionId = "A3.5",
                        stepNumber = 2,
                        title = "Razdvoji simptome od arhitektonskih uzroka",
                        instruction = "Razvrstaj kartice u posledice koje se vide i uzroke koji ih stvaraju.",
                        categories = listOf(
                            ArchitectCategorySeed(
                                title = "Simptomi",
                                items = listOf(
                                    "interaktivni zadaci kasne kada se pokrene veliki batch posao",
                                    "logovanje ponekad dodatno usporava obradu zadataka",
                                    "administratori teško utvrđuju gde nastaje usko grlo"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Arhitektonski uzroci",
                                items = listOf(
                                    "svi zadaci koriste jedan globalni red bez klasifikacije i prioriteta",
                                    "worker procesi nemaju jasna ograničenja po CPU, memoriji ili I/O opterećenju",
                                    "sistemski logovi i radni zadaci dele isti disk bez odvojene I/O politike"
                                )
                            )
                        )
                    ),
                    ArchitectSeedBuilders.singleChoiceStep(
                        questionId = "A3.5",
                        stepNumber = 3,
                        title = "Izaberi najbolju prioritetnu reviziju",
                        instruction = "Izaberi reviziju koja najbolje rešava dominantni arhitektonski problem.",
                        options = listOf(
                            """
                            A. Uvesti višeklasni scheduling model sa prioritetnim redovima, izolacijom worker pool-ova i odvojenom I/O politikom
                            Zadaci se razdvajaju po klasi, prioritetu i tipu opterećenja. Kritični i interaktivni zadaci dobijaju odvojene redove i garantovane resurse, batch poslovi se ograničavaju kvotama, a logovanje i radni I/O se razdvajaju kroz jasnu I/O politiku.
                            """.trimIndent(),
                            """
                            B. Povećati broj worker procesa i ostaviti jedan globalni red čekanja
                            Sistem dobija više paralelnih worker-a, ali svi zadaci i dalje ulaze u isti red i takmiče se bez jasne politike prioriteta.
                            """.trimIndent(),
                            """
                            C. Premestiti System Logs u bržu bazu bez promene scheduling modela
                            Logovanje postaje brže, ali se kratki, dugi i kritični zadaci i dalje izvršavaju kroz isti red i iste worker-e.
                            """.trimIndent(),
                            """
                            D. Uvesti FIFO red sa većim kapacitetom
                            Globalni red može primiti više zadataka, ali se zadaci i dalje izvršavaju redosledom dolaska, bez razumevanja prioriteta i trajanja posla.
                            """.trimIndent(),
                            """
                            E. Zabraniti batch poslove tokom radnog vremena
                            Sistem administrativno ograničava izvršavanje dugih poslova, ali ne rešava osnovni problem scheduling-a, izolacije resursa i observability-ja.
                            """.trimIndent()
                        ),
                        correctAnswer = """
                    A. Uvesti višeklasni scheduling model sa prioritetnim redovima, izolacijom worker pool-ova i odvojenom I/O politikom
                    Zadaci se razdvajaju po klasi, prioritetu i tipu opterećenja. Kritični i interaktivni zadaci dobijaju odvojene redove i garantovane resurse, batch poslovi se ograničavaju kvotama, a logovanje i radni I/O se razdvajaju kroz jasnu I/O politiku.
                    """.trimIndent()
                    ),
                    ArchitectSeedBuilders.categoryStep(
                        questionId = "A3.5",
                        stepNumber = 4,
                        title = "Mapiraj dobitke i nove rizike",
                        instruction = "Rasporedi posledice revizije u dobitke i nove rizike koje treba projektovati.",
                        categories = listOf(
                            ArchitectCategorySeed(
                                title = "Dobici revizije",
                                items = listOf(
                                    "interaktivni i kritični sistemski zadaci mogu dobiti predvidljivije vreme izvršavanja",
                                    "batch poslovi više ne mogu nekontrolisano zauzeti sve worker-e i I/O kapacitet",
                                    "monitoring po klasama zadataka olakšava otkrivanje uskih grla"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Novi rizici / pravila koja treba projektovati",
                                items = listOf(
                                    "potrebno je pažljivo projektovati prioritete da niskoprioritetni zadaci ne gladuju zauvek",
                                    "izolacija worker pool-ova uvodi dodatnu konfiguracionu i operativnu složenost"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Kartica koja nije dobar zaključak",
                                items = listOf(
                                    "veći broj worker procesa automatski rešava problem prioriteta i disk I/O zagušenja"
                                )
                            )
                        )
                    ),
                    ArchitectSeedBuilders.freeTextStep(
                        questionId = "A3.5",
                        stepNumber = 5,
                        title = "Kratko obrazloženje",
                        instruction = "Objasni zašto je izabrana revizija prioritetna i koji novi rizik uvodi."
                    )
                )
                )
    )
}
