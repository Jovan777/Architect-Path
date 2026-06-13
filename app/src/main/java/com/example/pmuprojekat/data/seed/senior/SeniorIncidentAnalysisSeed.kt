package com.example.pmuprojekat.data.seed.senior

import com.example.pmuprojekat.data.seed.SeedQuestion

object SeniorIncidentAnalysisSeed {
    val questions: List<SeedQuestion> = listOf(
        SeniorSeedBuilders.incidentQuestion(
            questionId = "S3.1",
            title = "Odredi prioritetnu intervenciju kod problema sa cache-om cena",
            prompt = """
            Retail sistem koristi cache za prikaz cena proizvoda. Posle masovne promotivne izmene cena tim primećuje:
            •	deo korisnika na listi proizvoda i dalje vidi stare cene, 
            •	checkout u pojedinim slučajevima koristi nove cene, 
            •	korisnička podrška prijavljuje pritužbe zbog razlike između prikaza i korpe, 
            •	problem se najčešće javlja kod najprodavanijih artikala. 
            Tim sumnja da je problem u cache invalidaciji i propagaciji promene kroz više slojeva sistema.
            """.trimIndent(),
            interventionOptions = listOf(
                "odmah povećati broj instanci frontend aplikacije",
                "privremeno smanjiti TTL i proveriti invalidaciju cache ključeva za promenu cena",
                "dodati novu vizuelnu oznaku za promotivne proizvode",
                "isključiti audit log da bi sistem bio brži"
            ),
            correctIntervention = "privremeno smanjiti TTL i proveriti invalidaciju cache ključeva za promenu cena",
            monitorOptions = listOf(
                "procenat zahteva u kojima se prikazana cena i checkout cena razlikuju",
                "broj pregleda proizvoda po satu",
                "učestalost cache refresh / miss događaja za pogođene artikle",
                "prosečnu dužinu naziva proizvoda"
            ),
            correctMonitorOptions = listOf(
                "procenat zahteva u kojima se prikazana cena i checkout cena razlikuju",
                "učestalost cache refresh / miss događaja za pogođene artikle"
            ),
            aiFollowUp = """
            Zašto je kod ovog incidenta važnije pratiti usklađenost prikaza i checkout-a nego samo prosečno vreme odgovora sistema?

            """.trimIndent(),
            wave = 1,
            orderIndex = 3
        ),

        SeniorSeedBuilders.incidentQuestion(
            questionId = "S3.2",
            title = "Odredi prioritetnu intervenciju kada dashboard prikazuje skokove lokacije",
            prompt = """
            Sistem za praćenje voznog parka prima GPS događaje sa hiljada vozila. Svaki događaj ima:
            •	ID vozila, 
            •	lokaciju, 
            •	brzinu, 
            •	timestamp nastanka događaja na uređaju, 
            •	timestamp prijema u sistemu. 
            U poslednjih sat vremena operateri prijavljuju:
            •	pojedina vozila na mapi “skaču” napred-nazad između dve lokacije; 
            •	istorija kretanja za deo vozila ima nelogičan redosled tačaka; 
            •	alarmi za napuštanje rute se aktiviraju iako ručna provera pokazuje da vozilo nije stvarno skrenulo; 
            •	ingest servis radi bez potpunog pada; 
            •	problem se češće javlja kod vozila sa nestabilnom mobilnom vezom; 
            •	analiza pokazuje da zakašnjeli GPS događaji ponekad prepisuju novije stanje vozila. 
            Tim sumnja da sistem u incidentu koristi vreme prijema događaja umesto vremena nastanka događaja, ili ne odbacuje/označava zakašnjele događaje pravilno.
            ________________________________________
            """.trimIndent(),
            interventionOptions = listOf(
                "povećati broj instanci ingest servisa kako bi se smanjilo kašnjenje u prijemu GPS događaja",
                "uvesti proveru event timestamp-a po vozilu i sprečiti da stariji događaj prepiše novije validno stanje",
                "povećati veličinu batch-a u stream procesoru kako bi se ubrzala obrada većeg broja događaja odjednom",
                """
                privremeno prikazivati samo poslednju poznatu stabilnu lokaciju vozila dok se ne potvrdi redosled novih događaja
                """.trimIndent()
            ),
            correctIntervention = "uvesti proveru event timestamp-a po vozilu i sprečiti da stariji događaj prepiše novije validno stanje",
            monitorOptions = listOf(
                "broj događaja koji su odbijeni, odloženi ili označeni jer su stariji od poslednjeg validnog stanja vozila",
                "učestalost nelogičnih promena lokacije za isto vozilo u kratkom vremenskom prozoru",
                "prosečno kašnjenje od nastanka GPS događaja do prikaza na dashboard-u",
                "ukupan throughput ingest servisa po minuti",
                "procenat alarma za napuštanje rute koji su nastali iz out-of-order sekvence"
            ),
            correctMonitorOptions = listOf(
                "broj događaja koji su odbijeni, odloženi ili označeni jer su stariji od poslednjeg validnog stanja vozila",
                "učestalost nelogičnih promena lokacije za isto vozilo u kratkom vremenskom prozoru"
            ),
            aiFollowUp = """
            Zašto je kod telemetrijskih sistema često važnije pravilno tumačiti vreme nastanka događaja nego samo gledati redosled kojim su događaji stigli u sistem?

            """.trimIndent(),
            wave = 2,
            orderIndex = 8
        ),

        SeniorSeedBuilders.incidentQuestion(
            questionId = "S3.3",
            title = "Odredi prioritetnu intervenciju kada fraud sistem blokira ispravne transakcije",
            prompt = """
            Fintech sistem koristi fraud servis koji procenjuje rizik kartičnih transakcija. U toku dana uvedena je nova verzija pravila za procenu rizika. Nakon promene tim primećuje:
            •	stopa odbijenih transakcija naglo raste kod korisnika iz jednog regiona; 
            •	broj potvrđenih fraud slučajeva nije porastao u istoj meri; 
            •	korisnička podrška prijavljuje veliki broj žalbi korisnika čije su uobičajene transakcije odbijene; 
            •	latencija plaćanja je stabilna; 
            •	problem se javlja najviše kod manjih transakcija u poznatim trgovinama; 
            •	logovi pokazuju da nova verzija pravila drugačije tretira kombinaciju lokacije, valute i istorije korisnika. 
            Tim sumnja da nova fraud pravila proizvode previše false positive blokada u određenom segmentu.
            ________________________________________
            """.trimIndent(),
            interventionOptions = listOf(
                "povećati kapacitet payment gateway-a kako bi se smanjilo vreme obrade odbijenih i prihvaćenih transakcija",
                """
                vratiti prethodnu verziju fraud pravila za pogođeni segment ili smanjiti uticaj novog pravila kroz feature flag
                """.trimIndent(),
                "privremeno podići prag za sve fraud provere kako bi se smanjio broj odbijenih transakcija",
                "pokrenuti hitan retraining fraud modela na novim podacima iz incidenta"
            ),
            correctIntervention = """
vratiti prethodnu verziju fraud pravila za pogođeni segment ili smanjiti uticaj novog pravila kroz feature flag
""".trimIndent(),
            monitorOptions = listOf(
                "stopu odbijenih transakcija u pogođenom segmentu u odnosu na kontrolni segment",
                "odnos potvrđenih fraud slučajeva i žalbi korisnika na pogrešno odbijene transakcije",
                "ukupnu latenciju autorizacije transakcija nakon rollback-a pravila",
                "broj transakcija koje prolaze bez dodatne fraud provere",
                "promenu approval rate-a za male transakcije u poznatim trgovinama"
            ),
            correctMonitorOptions = listOf(
                "stopu odbijenih transakcija u pogođenom segmentu u odnosu na kontrolni segment",
                "odnos potvrđenih fraud slučajeva i žalbi korisnika na pogrešno odbijene transakcije"
            ),
            aiFollowUp = """
            Zašto kod fraud sistema nije dovoljno pratiti samo broj blokiranih transakcija, već i odnos između stvarno rizičnih slučajeva, lažno blokiranih korisnika i promena po konkretnom segmentu?
            """.trimIndent(),
            wave = 3,
            orderIndex = 13
        ),

        SeniorSeedBuilders.incidentQuestion(
            questionId = "S3.4",
            title = "Odredi prioritetnu intervenciju kada fajlovi postaju dostupni pre skeniranja",
            prompt = """
            Sistem za elektronsku predaju zahteva u javnoj upravi omogućava korisnicima da prilože dokumente uz predmet. Tok obrade uključuje:
            •	upload fajla, 
            •	čuvanje u DocumentStore, 
            •	antivirus/malware skeniranje, 
            •	povezivanje dokumenta sa predmetom, 
            •	prikaz dokumenta službeniku. 
            U incidentu je primećeno:
            •	mali broj dokumenata postaje vidljiv službenicima pre nego što je skeniranje završeno; 
            •	problem se javlja najčešće kada je red za skeniranje zagušen; 
            •	većina fajlova kasnije prođe proveru, ali status u trenutku prikaza nije pouzdan; 
            •	nema dokaza da je DocumentStore izgubio fajlove; 
            •	logovi pokazuju da aplikacija ponekad tretira “upload uspešan” kao da znači “dokument bezbedan za otvaranje”. 
            Tim sumnja da je problem u tome što sistem ne razlikuje dovoljno jasno tehnički primljen dokument od dokumenta koji je bezbednosno odobren.
            ________________________________________
            """.trimIndent(),
            interventionOptions = listOf(
                "blokirati otvaranje dokumenata dok antivirus/malware skeniranje ne potvrdi bezbedan status",
                "tretirati upload uspešan kao dovoljan uslov za prikaz službeniku",
                "povećati broj instanci DocumentStore-a kao jedinu intervenciju",
                "isključiti antivirus skeniranje za male fajlove dok se red ne isprazni"
            ),
            correctIntervention = "blokirati otvaranje dokumenata dok antivirus/malware skeniranje ne potvrdi bezbedan status",
            monitorOptions = listOf(
                "broj blokiranih pokušaja otvaranja dokumenata bez potvrđenog statusa skeniranja",
                "vreme od upload-a do bezbednosno odobrenog statusa dokumenta",
                "veličinu reda za antivirus skeniranje po tipu fajla",
                "broj dokumenata koji su uploadovani, ali još nisu povezani sa predmetom",
                "procenat dokumenata koji su službenici pokušali da otvore pre završene provere"
            ),
            correctMonitorOptions = listOf(
                "broj blokiranih pokušaja otvaranja dokumenata bez potvrđenog statusa skeniranja",
                "vreme od upload-a do bezbednosno odobrenog statusa dokumenta"
            ),
            aiFollowUp = """
            Zašto “fajl je uspešno uploadovan” ne sme automatski da znači i “fajl je bezbedan za otvaranje”? 

            """.trimIndent(),
            wave = 4,
            orderIndex = 18
        ),

        SeniorSeedBuilders.incidentQuestion(
            questionId = "S3.5",
            title = "Odredi prioritetnu intervenciju kada broj slobodnih mesta postane nepouzdan",
            prompt = """
            Avio-sistem koristi centralni servis za upravljanje raspoloživim mestima na letu. U normalnom radu više kanala koristi isti fond mesta:
            •	web prodaja, 
            •	agencijska prodaja, 
            •	interna promena rezervacija, 
            •	automatska preraspodela putnika pri promeni aviona. 
            U incidentu je primećeno sledeće:
            •	za mali broj letova broj slobodnih mesta povremeno ne odgovara stvarnom stanju rezervacija, 
            •	problem se češće javlja kada se nad istim letom istovremeno rade prodaja i promena postojećih rezervacija, 
            •	latencija sistema nije drastično porasla, 
            •	audit pokazuje da pojedine operacije čitanja i upisa koriste različite verzije stanja sedišta u kratkom vremenskom prozoru, 
            •	problem nije masovan, ali poslovno može biti veoma skup jer vodi overbooking-u ili pogrešnom blokiranju prodaje. 
            Tim sumnja da incident nastaje zbog nekonzistentnog rukovanja konkurentnim izmenama nad istim fondom mesta.
            """.trimIndent(),
            interventionOptions = listOf(
                "povećati broj instanci servisa za rezervacije kako bi zahtevi brže prolazili",
                """
                uvesti strožu kontrolu verzije / konkurentnosti nad izmenom fonda mesta i zaustaviti operacije koje rade nad zastarelim stanjem
                """.trimIndent(),
                "proširiti audit log da beleži još više tehničkih detalja pre bilo kakve promene",
                "prebaciti sav saobraćaj sa web kanala na agencijski kanal dok traje istraga"
            ),
            correctIntervention = """
uvesti strožu kontrolu verzije / konkurentnosti nad izmenom fonda mesta i zaustaviti operacije koje rade nad zastarelim stanjem
""".trimIndent(),
            monitorOptions = listOf(
                "broj konflikata odbijenih zbog zastarele verzije stanja",
                "odstupanje između knjigovodstvenog broja rezervisanih mesta i stvarnog fonda po letu",
                "prosečno vreme generisanja PDF karte",
                "broj novih log zapisa po minuti"
            ),
            correctMonitorOptions = listOf(
                "broj konflikata odbijenih zbog zastarele verzije stanja",
                "odstupanje između knjigovodstvenog broja rezervisanih mesta i stvarnog fonda po letu"
            ),
            aiFollowUp = """
            Zašto u sistemu rezervacija nije dovoljno da svaka pojedinačna operacija izgleda ispravna, ako dve istovremene operacije mogu da koriste različitu verziju istog poslovnog stanja?

            """.trimIndent(),
            wave = 5,
            orderIndex = 23
        ),

        SeniorSeedBuilders.incidentQuestion(
            questionId = "S3.6",
            title = "Odredi prioritetnu intervenciju kada sistem povremeno dodeli duple rewards",
            prompt = """
            Multiplayer igra posle svakog meča obrađuje sledeće korake:
            •	zatvaranje meča, 
            •	potvrdu rezultata, 
            •	obračun XP i valute, 
            •	upis nagrada na nalog igrača, 
            •	ažuriranje istorije mečeva. 
            U incidentu je primećeno:
            •	mali broj igrača dobija duple nagrade za isti meč, 
            •	problem se češće javlja kada igrač brzo reconnect-uje ili kada klijent ponovi zahtev posle kratkog prekida, 
            •	sami mečevi nisu duplirani u istoriji, ali reward upis ponekad jeste, 
            •	sistem nema veliki pad performansi niti masovna kašnjenja, 
            •	logovi pokazuju da isti završetak meča u retkim slučajevima aktivira više pokušaja finalize koraka. 
            Tim sumnja da završni korak obračuna nije dovoljno zaštićen od ponovnog izvršavanja nad istim mečom.
            """.trimIndent(),
            interventionOptions = listOf(
                "povećati broj instanci match servisa da finalize koraci brže prolaze",
                "uvesti strožu idempotency zaštitu nad reward finalization korakom po jedinstvenom match ID-u",
                "sakriti prikaz XP promena iz klijenta dok traje istraga",
                "povećati broj retry pokušaja za reward servis da nijedan upis ne bude propušten"
            ),
            correctIntervention = "uvesti strožu idempotency zaštitu nad reward finalization korakom po jedinstvenom match ID-u",
            monitorOptions = listOf(
                "broj odbijenih ponovljenih finalize pokušaja po istom match ID-u",
                "odnos između broja završenih mečeva i broja reward upisa",
                "prosečan broj skinova po igraču",
                "broj reconnect događaja po regionu bez veze sa finalize korakom"
            ),
            correctMonitorOptions = listOf(
                "broj odbijenih ponovljenih finalize pokušaja po istom match ID-u",
                "odnos između broja završenih mečeva i broja reward upisa"
            ),
            aiFollowUp = """
            Zašto je u multiplayer ekonomiji često skuplje dozvoliti i retko dupliranje nagrada nego povremeno uvesti strožu kontrolu završnog koraka?

            """.trimIndent(),
            wave = 6,
            orderIndex = 28
        ),

        SeniorSeedBuilders.incidentQuestion(
            questionId = "S3.7",
            title = "Odredi prioritetnu intervenciju kada recommendation model koristi pogrešno interpretirane feature-e",
            prompt = """
            Preporučivački AI sistem koristi sledeći tok:
            •	Feature Pipeline prikuplja korisničke signale, 
            •	Feature Store čuva online i offline feature-e, 
            •	Ranking Model generiše preporuke, 
            •	Serving Layer vraća top rezultate korisniku. 
            U incidentu je primećeno:
            •	CTR je pao samo u jednoj grupi korisnika, i to naglo posle promene u feature pipeline-u, 
            •	latencija preporuka je ostala stabilna, 
            •	model nije menjan, ali je uvedena nova verzija obrade ponašajnih feature-a, 
            •	offline evaluacija i dalje deluje „normalno“, ali online ponašanje za pogođeni segment odstupa, 
            •	preliminarna analiza pokazuje da deo feature-a verovatno ima promenjeno značenje, a ne samo novu distribuciju. 
            Tim sumnja da serving koristi feature-e koji su tehnički validni, ali semantički neodgovarajući u odnosu na ono na čemu je model treniran.
            """.trimIndent(),
            interventionOptions = listOf(
                "povećati broj kandidata koje ranking model razmatra po korisniku",
                """
                vratiti prethodnu verziju obrade pogođenih feature-a za online serving segment i uporediti ponašanje sa kontrolom
                """.trimIndent(),
                "pojačati regularizaciju modela bez menjanja feature pipeline-a",
                "povećati budžet za retraining da bi model „naučio“ novo ponašanje feature-a"
            ),
            correctIntervention = """
vratiti prethodnu verziju obrade pogođenih feature-a za online serving segment i uporediti ponašanje sa kontrolom
""".trimIndent(),
            monitorOptions = listOf(
                "CTR i downstream engagement metrike za pogođeni segment u odnosu na kontrolu",
                "odstupanje ključnih feature vrednosti između stare i nove obrade za isti skup korisnika",
                "prosečan broj tokena u internim log porukama",
                "ukupnu veličinu embedding vektora u model repozitorijumu"
            ),
            correctMonitorOptions = listOf(
                "CTR i downstream engagement metrike za pogođeni segment u odnosu na kontrolu",
                "odstupanje ključnih feature vrednosti između stare i nove obrade za isti skup korisnika"
            ),
            aiFollowUp = """
            Zašto je u ovakvom incidentu opasno misliti da će „još malo treninga“ rešiti problem, ako je model počeo da dobija pogrešno značenje ulaznih signala?
            """.trimIndent(),
            wave = 7,
            orderIndex = 33
        )
    )
}
