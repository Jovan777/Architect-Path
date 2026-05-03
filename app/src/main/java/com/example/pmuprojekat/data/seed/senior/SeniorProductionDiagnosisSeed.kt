package com.example.pmuprojekat.data.seed.senior

import com.example.pmuprojekat.data.seed.SeedQuestion

object SeniorProductionDiagnosisSeed {
    val questions: List<SeedQuestion> = listOf(
        SeniorSeedBuilders.diagnosisQuestion(
            questionId = "S1.1",
            title = "Pronađi glavni izvor problema u toku završetka kupovine",
            prompt = """
            E-commerce sistem u produkciji koristi sledeće ključne komponente:
            •	Checkout API 
            •	OrderService 
            •	PaymentService 
            •	NotificationService 
            •	Order DB 
            Pojednostavljen tok rada izgleda ovako:
            1.	Checkout API prima zahtev korisnika 
            2.	OrderService kreira početnu porudžbinu 
            3.	PaymentService obrađuje naplatu 
            4.	NotificationService šalje potvrdu korisniku 
            5.	OrderService upisuje finalni status porudžbine u bazu 
            U poslednjih sat vremena tim primećuje sledeće:
            •	deo korisnika dobija dve potvrde kupovine, 
            •	u nekim slučajevima naplata je uspešna, ali porudžbina ostaje u statusu PENDING, 
            •	broj timeout-a između OrderService i PaymentService je porastao, 
            •	problem se javlja češće kada je promet veći, 
            •	u logovima se vidi da NotificationService ponekad reaguje pre nego što je finalni status porudžbine trajno upisan.
            """.trimIndent(),
            symptomCards = listOf(
                "duplirane potvrde kupovine",
                "povećan broj timeout-a između servisa",
                "porudžbina ostaje u PENDING statusu nakon uspešne naplate",
                "događaj za slanje potvrde emituje se pre nego što je porudžbina transakciono finalizovana",
                "incident je učestaliji pod većim opterećenjem",
                "loše definisana granica između potvrde naplate i potvrde porudžbine"
            ),
            symptoms = listOf(
                "duplirane potvrde kupovine",
                "povećan broj timeout-a između servisa",
                "porudžbina ostaje u PENDING statusu nakon uspešne naplate",
                "incident je učestaliji pod većim opterećenjem"
            ),
            causes = listOf(
                "događaj za slanje potvrde emituje se pre nego što je porudžbina transakciono finalizovana",
                "loše definisana granica između potvrde naplate i potvrde porudžbine"
            ),
            componentOptions = listOf(
                "Checkout API",
                "NotificationService kao izolovani problem",
                "tok između PaymentService, OrderService i finalizacije porudžbine",
                "Order DB kao jedini glavni uzrok"
            ),
            correctComponent = "tok između PaymentService, OrderService i finalizacije porudžbine",
            consequenceOptions = listOf(
                "rizik poslovno nekonzistentnog stanja između naplate i porudžbine",
                "pad poverenja korisnika zbog pogrešnih potvrda",
                "automatsko smanjenje opterećenja baze",
                "teže debagovanje zbog mešanja uspešne naplate i neuspešne finalizacije",
                "garantovan potpuni gubitak svih porudžbina"
            ),
            correctConsequences = listOf(
                "rizik poslovno nekonzistentnog stanja između naplate i porudžbine",
                "pad poverenja korisnika zbog pogrešnih potvrda",
                "teže debagovanje zbog mešanja uspešne naplate i neuspešne finalizacije"
            ),
            interventionOptions = listOf(
                "odmah povećati broj instanci NotificationService",
                "prvo proveriti uslov i trenutak u kome se emituje potvrda korisniku",
                "preimenovati status PENDING",
                "isključiti logovanje da bi sistem radio brže"
            ),
            correctIntervention = "prvo proveriti uslov i trenutak u kome se emituje potvrda korisniku",
            aiFollowUp = """
            Zašto je opasno gasiti samo simptom dupliranih notifikacija, a ne istražiti granicu između potvrde naplate i finalizacije porudžbine?

            2. Drugi tip — Izbor strategije optimizacije postojećeg sistema
            """.trimIndent(),
            wave = 1,
            orderIndex = 1
        ),

        SeniorSeedBuilders.diagnosisQuestion(
            questionId = "S1.2",
            title = "Pronađi glavni izvor problema u toku rezervisanja robe",
            prompt = """
            Logistička platforma za obradu porudžbina koristi sledeće komponente:
            •	OrderImportService prima porudžbine iz više prodajnih kanala 
            •	InventoryService vodi trenutno dostupne količine robe 
            •	ReservationService rezerviše artikle za konkretnu porudžbinu 
            •	WarehousePickingService šalje naloge skladištu za pripremu robe 
            •	ShipmentService organizuje isporuku 
            U poslednja dva dana tim primećuje:
            •	deo porudžbina ulazi u pripremu, ali skladište kasnije prijavljuje da artikla nema dovoljno; 
            •	isti artikal je ponekad rezervisan za više porudžbina u kratkom vremenskom razmaku; 
            •	problem se češće javlja tokom velikih akcija i sezonskih popusta; 
            •	InventoryService prikazuje dostupnu količinu koja ne odgovara stvarnom stanju nakon rezervacija; 
            •	nema značajnog rasta grešaka pri samom uvozu porudžbina; 
            •	kašnjenja u isporuci nastaju tek nakon što porudžbina već deluje uspešno obrađena. 
            ________________________________________
            """.trimIndent(),
            symptomCards = listOf(
                "porudžbine ulaze u pripremu iako robe kasnije nema dovoljno",
                "isti artikal se rezerviše za više porudžbina",
                "problem se češće javlja tokom velikih akcija",
                "dostupna količina ne odgovara stvarnom stanju nakon rezervacija",
                "rezervisanje zaliha nije dovoljno atomski izvedeno",
                "sistem dozvoljava konkurentne rezervacije bez pouzdane zaštite od preklapanja"
            ),
            symptoms = listOf(
                "porudžbine ulaze u pripremu iako robe kasnije nema dovoljno",
                "isti artikal se rezerviše za više porudžbina",
                "problem se češće javlja tokom velikih akcija",
                "dostupna količina ne odgovara stvarnom stanju nakon rezervacija"
            ),
            causes = listOf(
                "rezervisanje zaliha nije dovoljno atomski izvedeno",
                "sistem dozvoljava konkurentne rezervacije bez pouzdane zaštite od preklapanja"
            ),
            componentOptions = listOf(
                "OrderImportService",
                "ReservationService",
                "ShipmentService",
                "WarehousePickingService kao jedini glavni uzrok"
            ),
            correctComponent = "ReservationService",
            consequenceOptions = listOf(
                "porudžbine mogu biti prihvaćene iako realna zaliha nije obezbeđena",
                "skladište i prodajni sistem mogu imati različitu sliku dostupnosti robe",
                "raste broj ručnih korekcija, otkazivanja i intervencija podrške",
                "automatski se povećava stvarna količina robe na stanju",
                "problem je ograničen samo na prikaz u korisničkom interfejsu"
            ),
            correctConsequences = listOf(
                "porudžbine mogu biti prihvaćene iako realna zaliha nije obezbeđena",
                "skladište i prodajni sistem mogu imati različitu sliku dostupnosti robe",
                "raste broj ručnih korekcija, otkazivanja i intervencija podrške"
            ),
            interventionOptions = listOf(
                """
                prvo proveriti da li se rezervacija artikla izvršava atomski i da li postoji zaštita od konkurentnih rezervacija iste zalihe
                """.trimIndent(),
                "odmah povećati broj skladišnih radnika",
                "sakriti količine robe sa korisničkog interfejsa",
                "povećati broj instanci ShipmentService-a"
            ),
            correctIntervention = """
prvo proveriti da li se rezervacija artikla izvršava atomski i da li postoji zaštita od konkurentnih rezervacija iste zalihe
""".trimIndent(),
            aiFollowUp = """
            Zašto je kod sistema sa ograničenim zalihama opasno osloniti se samo na trenutno prikazanu količinu, ako rezervacija nije izvedena kao pouzdana i atomska operacija?

            2. Drugi tip — Izbor strategije optimizacije postojećeg sistema
            """.trimIndent(),
            wave = 2,
            orderIndex = 6
        ),

        SeniorSeedBuilders.diagnosisQuestion(
            questionId = "S1.3",
            title = "Pronađi glavni izvor greške u obračunu dnevne potrošnje",
            prompt = """
            Sistem za obračun električne energije koristi sledeće komponente:
            •	MeterIngestService prima očitavanja sa pametnih brojila 
            •	MeterValidationService proverava format i opseg vrednosti 
            •	TimeNormalizationService prevodi očitavanja u obračunske vremenske intervale 
            •	AggregationService računa dnevnu i mesečnu potrošnju 
            •	BillingService koristi agregirane podatke za obračun 
            U prethodnih nekoliko dana tim primećuje:
            •	kod korisnika iz pojedinih regiona dnevna potrošnja povremeno “preskače” u naredni dan; 
            •	mesečni zbir je uglavnom približno tačan, ali dnevni grafikoni imaju nelogične skokove; 
            •	problem se češće javlja kod brojila koja šalju očitavanja blizu ponoći; 
            •	validacija vrednosti prolazi bez greške; 
            •	nema povećanja broja timeout-a niti pada servisa; 
            •	ručna provera pokazuje da su sirova očitavanja uglavnom ispravna, ali su svrstana u pogrešan obračunski interval.
            """.trimIndent(),
            symptomCards = listOf(
                "dnevna potrošnja se pojavljuje u pogrešnom danu",
                "mesečni zbir je približno tačan, ali dnevni prikaz nije",
                "problem je izražen kod očitavanja blizu ponoći",
                "validacija sirovih vrednosti prolazi bez greške",
                "vremenska normalizacija pogrešno mapira timestamp u obračunski period",
                "sistem tretira tehnički ispravno očitavanje kao da pripada pogrešnom poslovnom danu"
            ),
            symptoms = listOf(
                "dnevna potrošnja se pojavljuje u pogrešnom danu",
                "mesečni zbir je približno tačan, ali dnevni prikaz nije",
                "problem je izražen kod očitavanja blizu ponoći",
                "validacija sirovih vrednosti prolazi bez greške"
            ),
            causes = listOf(
                "vremenska normalizacija pogrešno mapira timestamp u obračunski period",
                "sistem tretira tehnički ispravno očitavanje kao da pripada pogrešnom poslovnom danu"
            ),
            componentOptions = listOf(
                "MeterIngestService",
                "MeterValidationService",
                "TimeNormalizationService",
                "BillingService kao jedini glavni uzrok"
            ),
            correctComponent = "TimeNormalizationService",
            consequenceOptions = listOf(
                "korisnici mogu videti pogrešnu raspodelu potrošnje po danima",
                "analitika potrošnje i upozorenja mogu donositi pogrešne zaključke",
                "finansijski obračun može biti teško proverljiv iako ukupan zbir deluje približno tačno",
                "sistem automatski smanjuje broj očitavanja",
                "svi mesečni računi su garantovano pogrešni"
            ),
            correctConsequences = listOf(
                "korisnici mogu videti pogrešnu raspodelu potrošnje po danima",
                "analitika potrošnje i upozorenja mogu donositi pogrešne zaključke",
                "finansijski obračun može biti teško proverljiv iako ukupan zbir deluje približno tačno"
            ),
            interventionOptions = listOf(
                "povećati broj instanci AggregationService-a",
                "prvo proveriti mapiranje timestamp-a u lokalni obračunski period po regionima",
                "isključiti dnevne grafikone za sve korisnike",
                "povećati toleranciju validacije potrošnje"
            ),
            correctIntervention = "prvo proveriti mapiranje timestamp-a u lokalni obračunski period po regionima",
            aiFollowUp = """
            Zašto je moguće da sistem ima uglavnom tačne sirove podatke, a da ipak proizvodi pogrešnu poslovnu sliku potrošnje?
            """.trimIndent(),
            wave = 3,
            orderIndex = 11
        ),

        SeniorSeedBuilders.diagnosisQuestion(
            questionId = "S1.4",
            title = "Pronađi glavni izvor nekonzistentnog završavanja ispita",
            prompt = """
            Platforma za onlajn ispite koristi sledeće komponente:
            •	AuthService upravlja prijavom studenta 
            •	ExamSessionService vodi aktivni pokušaj 
            •	AutoSaveService periodično čuva odgovore 
            •	SubmitService obrađuje završnu predaju testa 
            •	ResultService računa i upisuje rezultat 
            U poslednjih nekoliko sati primećeno je:
            •	kod malog broja studenata isti pokušaj ima dva zapisa o završetku; 
            •	u nekim slučajevima rezultat odgovara ranijoj verziji odgovora, a ne poslednjem autosave-u; 
            •	problem se javlja najčešće kada student klikne “Predaj” neposredno pre automatskog isteka vremena; 
            •	AutoSaveService i SubmitService u logovima ponekad upisuju promene u razmaku manjem od jedne sekunde; 
            •	nema dokaza da je AuthService izdao pogrešan identitet; 
            •	većina pokušaja se završi ispravno.
            """.trimIndent(),
            symptomCards = listOf(
                "isti pokušaj ima dva zapisa o završetku",
                "rezultat ponekad odgovara starijoj verziji odgovora",
                "problem se javlja kada ručna predaja i automatski istek dolaze skoro istovremeno",
                "većina pokušaja se završi ispravno",
                "završavanje pokušaja nije dovoljno idempotentno",
                "konkurentni upisi autosave-a i submit-a nisu jasno usklađeni"
            ),
            symptoms = listOf(
                "isti pokušaj ima dva zapisa o završetku",
                "rezultat ponekad odgovara starijoj verziji odgovora",
                "problem se javlja kada ručna predaja i automatski istek dolaze skoro istovremeno",
                "većina pokušaja se završi ispravno"
            ),
            causes = listOf(
                "završavanje pokušaja nije dovoljno idempotentno",
                "konkurentni upisi autosave-a i submit-a nisu jasno usklađeni"
            ),
            componentOptions = listOf(
                "ExamSessionService / upravljanje stanjem pokušaja",
                "AutoSaveService / konkurentno čuvanje odgovora",
                "SubmitService / obrada završne predaje",
                "ResultService / izračunavanje i upis rezultata"
            ),
            correctComponent = "SubmitService / logika zatvaranja pokušaja",
            consequenceOptions = listOf(
                "student može dobiti rezultat koji ne odgovara poslednjem sačuvanom stanju",
                "podrška teško može da utvrdi koji završetak pokušaja je važeći",
                "sistem postaje ranjiv na retke, ali ozbiljne race condition situacije",
                "automatski se poboljšava brzina obrade rezultata",
                "svi studenti dobijaju duplirane pokušaje"
            ),
            correctConsequences = listOf(
                "student može dobiti rezultat koji ne odgovara poslednjem sačuvanom stanju",
                "podrška teško može da utvrdi koji završetak pokušaja je važeći",
                "sistem postaje ranjiv na retke, ali ozbiljne race condition situacije"
            ),
            interventionOptions = listOf(
                "prvo proveriti da li je završavanje pokušaja idempotentno i zaključano po jedinstvenom session/pokušaj ID-u",
                "produžiti trajanje svih ispita",
                "sakriti dugme “Predaj” u poslednjem minutu",
                "obrisati autosave mehanizam"
            ),
            correctIntervention = "prvo proveriti da li je završavanje pokušaja idempotentno i zaključano po jedinstvenom session/pokušaj ID-u",
            aiFollowUp = """
            Zašto je kod završavanja ispita važno da operacija “predaj pokušaj” bude idempotentna, čak i ako korisnik ili sistem pošalju zahtev više puta?
            """.trimIndent(),
            wave = 4,
            orderIndex = 16
        ),

        SeniorSeedBuilders.diagnosisQuestion(
            questionId = "S1.5",
            title = "Pronađi glavni izvor degradacije u sistemu za render obradu",
            prompt = """
            Platforma za industrijsku 3D obradu modela koristi sledeće komponente:
            •	UploadService prima fajlove modela 
            •	PreprocessEngine radi validaciju i pripremu geometrije 
            •	RenderScheduler raspoređuje poslove na radnike 
            •	WorkerPool izvršava CPU-intenzivne render poslove 
            •	PreviewService prikazuje preliminarni rezultat korisniku 
            U poslednjih nekoliko dana tim primećuje:
            •	kratki render poslovi često završavaju mnogo kasnije nego ranije, 
            •	dugi poslovi i dalje uglavnom uspevaju, ali prosečno vreme završetka celog sistema postaje nepredvidivo, 
            •	iskorišćenost CPU resursa je visoka, ali broj završenih kratkih poslova po satu pada, 
            •	pri istovremenom prisustvu velikih i malih poslova čini se da mali poslovi “nestaju” iz toka i čekaju disproporcionalno dugo, 
            •	nema jasnog rasta broja grešaka niti padova servisa.
            """.trimIndent(),
            symptomCards = listOf(
                "kratki poslovi završavaju sporije nego ranije",
                "dugi poslovi i dalje uglavnom uspevaju",
                "throughput kratkih poslova opada iako je CPU zauzet",
                "mali poslovi disproporcionalno dugo čekaju kada ima mnogo velikih poslova",
                "scheduler ne raspoređuje fer izvršavanje između različitih klasa poslova",
                "visoka iskorišćenost CPU-a sama po sebi ne znači da je raspodela rada zdrava"
            ),
            symptoms = listOf(
                "kratki poslovi završavaju sporije nego ranije",
                "dugi poslovi i dalje uglavnom uspevaju",
                "throughput kratkih poslova opada iako je CPU zauzet",
                "mali poslovi disproporcionalno dugo čekaju kada ima mnogo velikih poslova"
            ),
            causes = listOf(
                "scheduler ne raspoređuje fer izvršavanje između različitih klasa poslova",
                "visoka iskorišćenost CPU-a sama po sebi ne znači da je raspodela rada zdrava"
            ),
            componentOptions = listOf(
                "UploadService",
                "PreviewService",
                "RenderScheduler",
                "PreprocessEngine"
            ),
            correctComponent = "RenderScheduler",
            consequenceOptions = listOf(
                "degradacija korisničkog iskustva za interaktivne / kratke operacije",
                "nefer korišćenje radničkog kapaciteta između različitih vrsta poslova",
                "pogrešan utisak da sistem „ima dovoljno CPU-a“, iako je raspodela loša",
                "automatsko poboljšanje kvaliteta rendera dugih poslova",
                "garantovan gubitak svih velikih poslova"
            ),
            correctConsequences = listOf(
                "degradacija korisničkog iskustva za interaktivne / kratke operacije",
                "nefer korišćenje radničkog kapaciteta između različitih vrsta poslova",
                "pogrešan utisak da sistem „ima dovoljno CPU-a“, iako je raspodela loša"
            ),
            interventionOptions = listOf(
                "povećati broj worker instanci bez daljeg profilisanja",
                "prvo analizirati politiku raspodele i čekanja po klasama poslova u scheduler-u",
                "smanjiti rezoluciju svih rendera",
                "prebaciti PreviewService na drugi server"
            ),
            correctIntervention = "prvo analizirati politiku raspodele i čekanja po klasama poslova u scheduler-u",
            aiFollowUp = """
            Zašto je u ovakvom sistemu opasno meriti zdravlje samo kroz ukupnu zauzetost resursa, a ne i kroz raspodelu vremena čekanja po vrstama poslova?
            """.trimIndent(),
            wave = 5,
            orderIndex = 21
        ),

        SeniorSeedBuilders.diagnosisQuestion(
            questionId = "S1.6",
            title = "Pronađi glavni izvor problema u kontroli pristupa predmetima",
            prompt = """
            Sistem javne uprave za elektronsku obradu predmeta koristi sledeće komponente:
            •	CaseRegistryService vodi osnovne podatke o predmetima 
            •	UserRoleService čuva uloge zaposlenih i organizacione jedinice 
            •	AccessControlService odlučuje ko sme da vidi ili menja predmet 
            •	DocumentService čuva priložene dokumente 
            •	AuditLogService beleži pristupe i izmene 
            U poslednjih nekoliko dana tim primećuje:
            •	pojedini službenici vide predmete koji pripadaju drugoj organizacionoj jedinici; 
            •	problem se ne javlja kod svih korisnika, već uglavnom kod onih koji su nedavno promenili sektor ili dobili privremenu zamenu; 
            •	AuditLogService uredno beleži pristupe, ali ne označava ih kao neispravne; 
            •	DocumentService vraća dokumente kada dobije zahtev, bez dodatne provere poslovnog konteksta; 
            •	uloge u UserRoleService-u izgledaju ispravno kada se gledaju pojedinačno; 
            •	problem se najčešće javlja kod predmeta koji su premeštani između organizacionih jedinica. 
            ________________________________________
            """.trimIndent(),
            symptomCards = listOf(
                "službenici vide predmete iz druge organizacione jedinice",
                "problem se javlja kod korisnika sa promenjenom ili privremenom ulogom",
                "pristupi se uredno beleže, ali se ne prepoznaju kao neispravni",
                "problem je čest kod predmeta koji su premeštani između organizacionih jedinica",
                "pravila pristupa ne uzimaju dovoljno u obzir trenutni odnos korisnika, uloge i organizacione jedinice predmeta",
                "provera pristupa oslanja se na formalnu ulogu, ali ne proverava dovoljno poslovni kontekst predmeta"
            ),
            symptoms = listOf(
                "službenici vide predmete iz druge organizacione jedinice",
                "problem se javlja kod korisnika sa promenjenom ili privremenom ulogom",
                "pristupi se uredno beleže, ali se ne prepoznaju kao neispravni",
                "problem je čest kod predmeta koji su premeštani između organizacionih jedinica"
            ),
            causes = listOf(
                "pravila pristupa ne uzimaju dovoljno u obzir trenutni odnos korisnika, uloge i organizacione jedinice predmeta",
                "provera pristupa oslanja se na formalnu ulogu, ali ne proverava dovoljno poslovni kontekst predmeta"
            ),
            componentOptions = listOf(
                "DocumentService",
                "AuditLogService",
                "AccessControlService",
                "CaseRegistryService kao jedini glavni uzrok"
            ),
            correctComponent = "AccessControlService",
            consequenceOptions = listOf(
                "neovlašćen ili preširok pristup poverljivim predmetima",
                "audit trag može postojati, ali bez jasnog signala da je pristup bio poslovno neosnovan",
                "promene sektora i privremene zamene mogu stvarati rizične kombinacije prava pristupa",
                "sistem automatski povećava bezbednost zato što beleži sve pristupe",
                "problem je isključivo u skladištenju dokumenata"
            ),
            correctConsequences = listOf(
                "neovlašćen ili preširok pristup poverljivim predmetima",
                "audit trag može postojati, ali bez jasnog signala da je pristup bio poslovno neosnovan",
                "promene sektora i privremene zamene mogu stvarati rizične kombinacije prava pristupa"
            ),
            interventionOptions = listOf(
                """
                prvo proveriti pravila AccessControlService-a za korisnike sa promenjenim ulogama, privremenim zamenama i predmetima premeštenim između jedinica
                """.trimIndent(),
                "odmah obrisati sve privremene uloge iz sistema",
                "povećati kapacitet DocumentService-a",
                "isključiti audit logove dok se problem ne reši"
            ),
            correctIntervention = """
prvo proveriti pravila AccessControlService-a za korisnike sa promenjenim ulogama, privremenim zamenama i predmetima premeštenim između jedinica
""".trimIndent(),
            aiFollowUp = """
            Zašto audit log nije dovoljan kao zaštita, ako sistem prethodno ne proverava da li je pristup predmetu zaista poslovno opravdan?
            """.trimIndent(),
            wave = 6,
            orderIndex = 26
        ),

        SeniorSeedBuilders.diagnosisQuestion(
            questionId = "S1.7",
            title = "Pronađi glavni izvor degradacije u AI sistemu koji i dalje „radi“",
            prompt = """
            AI sistem za pravnu analizu koristi sledeće komponente:
            •	Document Ingestion Pipeline obrađuje nove dokumente 
            •	Chunking Service deli dokumente na semantičke celine 
            •	Embedding Service generiše reprezentacije 
            •	Vector Index služi za retrieval 
            •	Answer Composer formira konačan odgovor korisniku 
            U poslednjih 10 dana tim primećuje:
            •	korisnici sve češće dobijaju odgovore koji zvuče tečno, ali propuštaju ključne odredbe iz relevantnih dokumenata, 
            •	latencija sistema nije značajno porasla, 
            •	broj uspešnih odgovora po tehničkim kriterijumima ostaje visok, 
            •	problem je izraženiji kod novijih dokumenata sa složenijom strukturom i dužim odeljcima, 
            •	A/B poređenje pokazuje da generator i dalje proizvodi jezički kvalitetne odgovore, ali je retrieval deo sve manje precizan, 
            •	broj dokumenata u indeksu raste stabilno, bez očiglednih padova ingest procesa.
            """.trimIndent(),
            symptomCards = listOf(
                "odgovori zvuče uverljivo, ali propuštaju ključne pravne odredbe",
                "problem je izraženiji kod novijih i strukturno složenijih dokumenata",
                "latencija sistema ostaje približno ista",
                "generator ostaje jezički stabilan, ali retrieval opada po kvalitetu",
                "chunking strategija verovatno lošije čuva semantičke granice u novijim dokumentima",
                "rast indeksa sam po sebi ne znači da je kvalitet reprezentacije i segmentacije očuvan"
            ),
            symptoms = listOf(
                "odgovori zvuče uverljivo, ali propuštaju ključne pravne odredbe",
                "problem je izraženiji kod novijih i strukturno složenijih dokumenata",
                "latencija sistema ostaje približno ista",
                "generator ostaje jezički stabilan, ali retrieval opada po kvalitetu"
            ),
            causes = listOf(
                "chunking strategija verovatno lošije čuva semantičke granice u novijim dokumentima",
                "rast indeksa sam po sebi ne znači da je kvalitet reprezentacije i segmentacije očuvan"
            ),
            componentOptions = listOf(
                "Answer Composer",
                "Vector Index kao jedini problem",
                "tok segmentacije i reprezentacije dokumenata pre retrieval-a",
                "korisnički interfejs za prikaz odgovora"
            ),
            correctComponent = "tok segmentacije i reprezentacije dokumenata pre retrieval-a",
            consequenceOptions = listOf(
                "sistem može delovati kvalitetno na površini, a zapravo padati u domenskoj tačnosti",
                "evaluacija zasnovana samo na fluentnosti može sakriti ozbiljnu degradaciju retrieval sloja",
                "noviji dokumenti mogu sistematski biti potcenjeni u odnosu na starije i bolje segmentirane izvore",
                "automatski se smanjuje cena inferencije po upitu",
                "garantovano dolazi do potpunog raspada vektorskog indeksa"
            ),
            correctConsequences = listOf(
                "sistem može delovati kvalitetno na površini, a zapravo padati u domenskoj tačnosti",
                "evaluacija zasnovana samo na fluentnosti može sakriti ozbiljnu degradaciju retrieval sloja",
                "noviji dokumenti mogu sistematski biti potcenjeni u odnosu na starije i bolje segmentirane izvore"
            ),
            interventionOptions = listOf(
                "odmah povećati context window generatora",
                "prvo uporediti retrieval kvalitet po starim i novim dokumentima uz analizu chunk granica i recall-a po pitanju",
                "smanjiti temperaturu modela da odgovori zvuče formalnije",
                "povećati broj istovremenih korisnika u testu da bi se problem lakše reprodukovao"
            ),
            correctIntervention = "prvo uporediti retrieval kvalitet po starim i novim dokumentima uz analizu chunk granica i recall-a po pitanju",
            aiFollowUp = """
            Zašto je kod RAG sistema opasno oslanjati se na utisak da je odgovor „dobar“, ako retrieval deo tiho gubi relevantan kontekst?
            """.trimIndent(),
            wave = 7,
            orderIndex = 31
        )
    )
}
