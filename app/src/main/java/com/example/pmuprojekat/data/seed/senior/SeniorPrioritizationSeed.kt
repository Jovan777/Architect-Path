package com.example.pmuprojekat.data.seed.senior

import com.example.pmuprojekat.data.seed.SeedQuestion

object SeniorPrioritizationSeed {
    val questions: List<SeedQuestion> = listOf(
        SeniorSeedBuilders.prioritizationQuestion(
            questionId = "S5.1",
            title = "Odredi prioritetni redosled intervencija tokom produkcionog zagušenja",
            prompt = """
            Tokom promotivne kampanje checkout sistem trpi ozbiljan pad performansi:
            •	broj timeout-a prema bazi naglo raste, 
            •	queue za sekundarne notifikacije se naglo puni, 
            •	korisnici prijavljuju sporu kupovinu i povremene neuspešne završetke porudžbine, 
            •	analitika i audit zapisi dodatno opterećuju isti tok upisa.
            """.trimIndent(),
            interventionCards = listOf(
                """
                prebaciti checkout u degradirani režim: zadržati samo kreiranje porudžbine, naplatu i minimalni audit, a odložiti sporedne obrade
                """.trimIndent(),
                "identifikovati i ograničiti najskuplje upite koji blokiraju završetak porudžbine",
                "uvesti asinhronu obradu sporednih efekata nakon stabilizacije kritičnog toka",
                "povećati broj API instanci samo ako metrika pokaže da je aplikacioni sloj usko grlo",
                """
                povećati timeout povećati timeout prema bazi za najsporije checkout operacije kako bi se smanjio broj neuspešno završenih zahteva
                """.trimIndent()
            ),
            correctOrder = listOf(
                """
                prebaciti checkout u degradirani režim: zadržati samo kreiranje porudžbine, naplatu i minimalni audit, a odložiti sporedne obrade
                """.trimIndent(),
                "identifikovati i ograničiti najskuplje upite koji blokiraju završetak porudžbine",
                "uvesti asinhronu obradu sporednih efekata nakon stabilizacije kritičnog toka",
                "povećati broj API instanci samo ako metrika pokaže da je aplikacioni sloj usko grlo",
                """
                povećati timeout povećati timeout prema bazi za najsporije checkout operacije kako bi se smanjio broj neuspešno završenih zahteva
                """.trimIndent()
            ),
            aiFollowUp = """
            Zašto povećanje timeout-a često samo produžava patnju sistema umesto da ukloni uzrok zagušenja?

            """.trimIndent(),
            wave = 1,
            orderIndex = 5
        ),

        SeniorSeedBuilders.prioritizationQuestion(
            questionId = "S5.2",
            title = "Odredi redosled intervencija kada sistem javnog prevoza prikazuje pogrešne dolaske",
            prompt = """
            Sistem za javni prevoz prikazuje procenu dolaska vozila na stanicu kroz mobilnu aplikaciju i stanične displeje. Tokom jutarnjeg špica primećeno je:
            •	za deo linija ETA pokazuje dolazak za 2 minuta, ali vozilo realno kasni mnogo duže; 
            •	problem se javlja uglavnom kod vozila koja šalju neredovne GPS pakete; 
            •	mobilna aplikacija i displeji koriste isti ETA servis, ali različite cache slojeve; 
            •	operateri vide da neka vozila imaju “poslednju poznatu lokaciju” staru više minuta; 
            •	sistem nije pao, ali pogrešne procene direktno utiču na putnike. 

            Tim ima sledeće moguće intervencije.
            """.trimIndent(),
            interventionCards = listOf(
                """
                označiti ETA kao nepouzdan ili prikazati “poslednje ažuriranje pre X min” za vozila čiji GPS podaci nisu dovoljno sveži
                """.trimIndent(),
                "povećati broj ETA worker-a kako bi se procene brže računale",
                "uskladiti cache politiku mobilne aplikacije i staničnih displeja za iste linije",
                "prebaciti sve linije na konzervativniji ETA model sa ređim osvežavanjem dok se ne stabilizuje GPS tok",
                """
                izolovati vozila i linije kod kojih starost GPS podatka prelazi dozvoljeni prag i sprečiti da takvi podaci ulaze u normalan ETA model
                """.trimIndent()
            ),
            correctOrder = listOf(
                """
                izolovati vozila i linije kod kojih starost GPS podatka prelazi dozvoljeni prag i sprečiti da takvi podaci ulaze u normalan ETA model
                """.trimIndent(),
                """
                označiti ETA kao nepouzdan ili prikazati “poslednje ažuriranje pre X min” za vozila čiji GPS podaci nisu dovoljno sveži
                """.trimIndent(),
                "uskladiti cache politiku mobilne aplikacije i staničnih displeja za iste linije",
                "povećati broj ETA worker-a kako bi se procene brže računale",
                "prebaciti sve linije na konzervativniji ETA model sa ređim osvežavanjem dok se ne stabilizuje GPS tok"
            ),
            aiFollowUp = """
            Zašto je u sistemu javnog prevoza ponekad bolje prikazati “podatak nije dovoljno svež” nego preciznu ETA vrednost koja deluje uverljivo, ali je zasnovana na zastarelom GPS signalu?

            """.trimIndent(),
            wave = 2,
            orderIndex = 10
        ),

        SeniorSeedBuilders.prioritizationQuestion(
            questionId = "S5.3",
            title = "Odredi redosled intervencija kada sistem ne zna pouzdano status uplata",
            prompt = """
            Fintech sistem obrađuje uplate preko eksternog payment providera. Tokom incidenta primećeno je:
            •	deo korisnika vidi da uplata “nije uspela”, iako je provider kasnije potvrđuje kao uspešnu; 
            •	webhook događaji od providera stižu sa zakašnjenjem i povremeno van redosleda; 
            •	korisnici ponavljaju uplatu jer ne vide pouzdanu potvrdu; 
            •	support vidi više slučajeva gde jedan korisnik ima dve pokušane uplate za istu obavezu; 
            •	nije jasno da li je problem u provider webhook-u, lokalnoj obradi statusa ili u UI statusu koji prerano prikazuje neuspeh. 
            Tim ima sledeće moguće intervencije.
            """.trimIndent(),
            interventionCards = listOf(
                "uvesti status “obrada u toku / čeka se potvrda” za nejasne slučajeve umesto prikaza konačnog neuspeha",
                "zaustaviti automatsko ponavljanje naplate za istu obavezu dok se ne potvrdi konačan status prethodnog pokušaja",
                "pokrenuti reconciliation sa providerom za sporne transakcije i uporediti lokalni status sa provider statusom",
                "povećati timeout čekanja u korisničkom UI-ju pre prikaza rezultata plaćanja",
                "omogućiti korisnicima da odmah pokušaju novu uplatu ako prvi pokušaj nema lokalnu potvrdu"
            ),
            correctOrder = listOf(
                "zaustaviti automatsko ponavljanje naplate za istu obavezu dok se ne potvrdi konačan status prethodnog pokušaja",
                "uvesti status “obrada u toku / čeka se potvrda” za nejasne slučajeve umesto prikaza konačnog neuspeha",
                "pokrenuti reconciliation sa providerom za sporne transakcije i uporediti lokalni status sa provider statusom",
                "povećati timeout čekanja u korisničkom UI-ju pre prikaza rezultata plaćanja",
                "omogućiti korisnicima da odmah pokušaju novu uplatu ako prvi pokušaj nema lokalnu potvrdu"
            ),
            aiFollowUp = """
            Zašto je kod plaćanja opasno prerano prikazati konačan neuspeh, ako sistem još nije uskladio lokalni status sa statusom payment providera?

            """.trimIndent(),
            wave = 3,
            orderIndex = 15
        ),

        SeniorSeedBuilders.prioritizationQuestion(
            questionId = "S5.4",
            title = "Odredi redosled intervencija kada sistem za hitne ekipe ne može pouzdano da potvrdi raspoloživost",
            prompt = """
            Sistem za koordinaciju hitnih medicinskih ekipa koristi prijem poziva, procenu prioriteta, dodelu tima i praćenje raspoloživosti vozila. Tokom povećanog broja poziva primećeno je:
            •	status raspoloživosti pojedinih ekipa kasni nekoliko minuta; 
            •	sistem ponekad predlaže ekipu koja je u međuvremenu već angažovana; 
            •	kritični slučajevi moraju i dalje da se obrađuju bez odlaganja; 
            •	operateri više ne veruju da automatska preporuka uvek odražava realno stanje; 
            •	bolničke notifikacije i interni dashboard kasne, ali najveći rizik je pogrešna dodela ekipe. 
            Tim ima sledeće moguće intervencije.
            """.trimIndent(),
            interventionCards = listOf(
                """
                prebaciti kritične slučajeve u režim obavezne operaterske potvrde dodele dok se ne povrati pouzdanost stanja ekipa
                """.trimIndent(),
                "blokirati automatsku dodelu ekipa čiji status nije osvežen u dozvoljenom vremenskom prozoru",
                "ručno proveriti i osvežiti stanje najrizičnijih ekipa / zona sa najvećim brojem kritičnih poziva",
                "povećati broj instanci dashboard servisa kako bi prikaz bio brži",
                "nastaviti automatsku dodelu samo uz naknadnu operatersku proveru spornih slučajeva"
            ),
            correctOrder = listOf(
                "blokirati automatsku dodelu ekipa čiji status nije osvežen u dozvoljenom vremenskom prozoru",
                """
                prebaciti kritične slučajeve u režim obavezne operaterske potvrde dodele dok se ne povrati pouzdanost stanja ekipa
                """.trimIndent(),
                "ručno proveriti i osvežiti stanje najrizičnijih ekipa / zona sa najvećim brojem kritičnih poziva",
                "povećati broj instanci dashboard servisa kako bi prikaz bio brži",
                "nastaviti automatsku dodelu samo uz naknadnu operatersku proveru spornih slučajeva"
            ),
            aiFollowUp = """
            Zašto je kod dispečinga hitnih ekipa opasno nastaviti sa automatskom dodelom ako sistem ne može da dokaže da je podatak o raspoloživosti dovoljno svež? 

            """.trimIndent(),
            wave = 4,
            orderIndex = 20
        ),

        SeniorSeedBuilders.prioritizationQuestion(
            questionId = "S5.5",
            title = "Odredi redosled intervencija kada migracija podataka ne ruši sistem, ali kvari poslovno stanje",
            prompt = """
            Velika organizacija migrira istorijske podatke o ugovorima iz starog sistema u novi. Migracija ide u talasima i sistem tehnički ostaje dostupan. Međutim, posle jednog talasa primećeno je:
            •	mali broj ugovora se pojavljuje duplo u novom sistemu, 
            •	deo duplikata nije identičan, već se razlikuje u pojedinim pomoćnim poljima, 
            •	korisnici još uvek mogu da rade sa sistemom, ali downstream izveštaji počinju da daju kontradiktorne rezultate, 
            •	novi import talasi su već zakazani, 
            •	još nije jasno da li duplikati nastaju u mapiranju identiteta, replay-u batch-a ili u pogrešnom merge pravilu. 
            Tim ima sledeće moguće korake:
            Predložena finalna verzija kartica
            •	zaustaviti naredne migracione talase dok se ne razjasni mehanizam nastanka duplikata 
            •	obeležiti sve novonastale zapise iz poslednjeg talasa kao „pod sumnjom“ za dalju proveru i filtriranje u downstream obradi 
            •	izolovati i uporediti identitet / merge logiku na konkretnim primerima već nastalih duplikata 
            •	filtrirati potencijalne duplikate iz korisničkog prikaza uz zadržavanje originalnih zapisa za proveru 
            •	pokrenuti automatsko spajanje sumnjivih duplikata na osnovu trenutno dostupnih merge pravila
            Redosled
            1.	zaustaviti naredne migracione talase dok se ne razjasni mehanizam nastanka duplikata 
            2.	obeležiti sve novonastale zapise iz poslednjeg talasa kao „pod sumnjom“ za dalju proveru i filtriranje u downstream obradi 
            3.	izolovati i uporediti identitet / merge logiku na konkretnim primerima već nastalih duplikata 
            4.	filtrirati potencijalne duplikate iz korisničkog prikaza uz zadržavanje originalnih zapisa za proveru 
            5.	pokrenuti automatsko spajanje sumnjivih duplikata na osnovu trenutno dostupnih merge pravila
            Kratko obrazloženje
            Korisnik objašnjava zašto prvi prioritet nije „očistiti posledicu po svaku cenu“, nego prvo zaustaviti dalje širenje kvara, obeležiti sumnjivo stanje i tek onda precizno utvrđivati mehanizam nastanka.
            AI Provocation (easy)
            Zašto je kod tihih korupcija podataka često opasnije prerano „čistiti“ stanje nego najpre zaustaviti dalje kvarenje i obeležiti šta je nepouzdano?

            """.trimIndent(),
            interventionCards = listOf(
                "zaustaviti naredne migracione talase dok se ne razjasni mehanizam nastanka duplikata",
                """
                obeležiti sve novonastale zapise iz poslednjeg talasa kao „pod sumnjom“ za dalju proveru i filtriranje u downstream obradi
                """.trimIndent(),
                "izolovati i uporediti identitet / merge logiku na konkretnim primerima već nastalih duplikata",
                "filtrirati potencijalne duplikate iz korisničkog prikaza uz zadržavanje originalnih zapisa za proveru",
                "pokrenuti automatsko spajanje sumnjivih duplikata na osnovu trenutno dostupnih merge pravila"
            ),
            correctOrder = listOf(
                "zaustaviti naredne migracione talase dok se ne razjasni mehanizam nastanka duplikata",
                """
                obeležiti sve novonastale zapise iz poslednjeg talasa kao „pod sumnjom“ za dalju proveru i filtriranje u downstream obradi
                """.trimIndent(),
                "izolovati i uporediti identitet / merge logiku na konkretnim primerima već nastalih duplikata",
                "filtrirati potencijalne duplikate iz korisničkog prikaza uz zadržavanje originalnih zapisa za proveru",
                "pokrenuti automatsko spajanje sumnjivih duplikata na osnovu trenutno dostupnih merge pravila"
            ),
            aiFollowUp = """
            Zašto je kod tihih korupcija podataka često opasnije prerano „čistiti“ stanje nego najpre zaustaviti dalje kvarenje i obeležiti šta je nepouzdano?

            """.trimIndent(),
            wave = 5,
            orderIndex = 25
        ),

        SeniorSeedBuilders.prioritizationQuestion(
            questionId = "S5.6",
            title = "Odredi redosled intervencija kada turnirski sistem prikazuje pogrešne prolaze dalje",
            prompt = """
            Online esports platforma vodi turnire kroz:
            •	prijavu timova, 
            •	generisanje bracket-a, 
            •	unos rezultata mečeva, 
            •	prolazak pobednika u narednu rundu, 
            •	javni prikaz turnirskog stabla. 
            U incidentu je primećeno:
            •	za mali broj mečeva pobednik u bracket prikazu ne odgovara pobedniku iz match rezultata, 
            •	operateri i dalje mogu ručno da unose rezultate, 
            •	novi mečevi se i dalje generišu iz postojećeg bracket stanja, 
            •	nije jasno da li problem nastaje pri unosu rezultata, propagaciji pobednika ili pri rekonstrukciji bracket prikaza, 
            •	sistem nije pao, ali svaka nova runda može uvećati poslovnu i reputacionu štetu. 
            Tim ima sledeće moguće korake:
            """.trimIndent(),
            interventionCards = listOf(
                "zamrznuti automatsko generisanje narednih rundi dok se ne proveri konzistentnost postojećeg bracket stanja",
                "dozvoliti samo unos rezultata, ali bez automatskog promovisanja pobednika u narednu rundu",
                "izolovati mečeve kod kojih se rezultat i promoted winner razilaze i uporediti izvor istine po rundi",
                "ručno ispraviti javni prikaz za prijavljene slučajeve dok se backend stanje analizira",
                "nastaviti generisanje narednih rundi samo za delove bracket-a koji nisu povezani sa spornim mečevima"
            ),
            correctOrder = listOf(
                "zamrznuti automatsko generisanje narednih rundi dok se ne proveri konzistentnost postojećeg bracket stanja",
                "izolovati mečeve kod kojih se rezultat i promoted winner razilaze i uporediti izvor istine po rundi",
                "dozvoliti samo unos rezultata, ali bez automatskog promovisanja pobednika u narednu rundu",
                "ručno ispraviti javni prikaz za prijavljene slučajeve dok se backend stanje analizira",
                "nastaviti generisanje narednih rundi samo za delove bracket-a koji nisu povezani sa spornim mečevima"
            ),
            aiFollowUp = """
            Zašto je kod turnirskih sistema često važnije prvo zaustaviti dalje grananje pogrešnog stanja nego odmah „ulepšati“ ono što korisnici trenutno vide?
            """.trimIndent(),
            wave = 6,
            orderIndex = 30
        ),

        SeniorSeedBuilders.prioritizationQuestion(
            questionId = "S5.7",
            title = "Odredi redosled intervencija kada AI release gate više ne odvaja dovoljno dobre modele od rizičnih",
            prompt = """
            AI platforma koristi evaluacioni pipeline pre puštanja modela u produkciju. Posle poslednjih nekoliko release ciklusa tim primećuje:
            •	modeli prolaze evaluaciju, a zatim u produkciji pokazuju primetno lošije ponašanje na pojedinim segmentima korisnika, 
            •	osnovne aggregate metrike deluju zadovoljavajuće, 
            •	postoje indicije da evaluacioni skup više ne pokriva dovoljno dobro kritične rubne slučajeve, 
            •	rollout mehanizam i dalje funkcioniše, ali release gate očigledno više nije dovoljno selektivan, 
            •	tim ne zna da li je problem u evaluacionom skupu, pragu prolaska, weighting-u metrika ili u prevelikom oslanjanju na prosečne rezultate. 
            Tim ima sledeće moguće korake:
            """.trimIndent(),
            interventionCards = listOf(
                "zaustaviti nova automatska promovisanja modela dok se ne revidira evaluacioni gate",
                """
                izolovati koje vrste grešaka i koji segmenti korisnika najčešće prolaze ispod radara postojećeg evaluacionog skupa
                """.trimIndent(),
                """
                proširiti odluku o prolazu modela tako da kritični segmenti i edge-case metrike imaju zasebnu težinu, a ne samo agregatni skor
                """.trimIndent(),
                "proširiti evaluacioni skup dodatnim primerima iz istih izvora i istog scoring modela",
                "pooštriti globalni rollback prag za sve modele dok se ne završi analiza"
            ),
            correctOrder = listOf(
                "zaustaviti nova automatska promovisanja modela dok se ne revidira evaluacioni gate",
                """
                izolovati koje vrste grešaka i koji segmenti korisnika najčešće prolaze ispod radara postojećeg evaluacionog skupa
                """.trimIndent(),
                """
                proširiti odluku o prolazu modela tako da kritični segmenti i edge-case metrike imaju zasebnu težinu, a ne samo agregatni skor
                """.trimIndent(),
                "proširiti evaluacioni skup dodatnim primerima iz istih izvora i istog scoring modela",
                "pooštriti globalni rollback prag za sve modele dok se ne završi analiza"
            ),
            aiFollowUp = """
            Zašto evaluacioni sistem može izgledati „statistički dobar“, a ipak biti opasan za produkciju ako ne vidi dovoljno jasno kritične segmente i edge-case ponašanja?
            """.trimIndent(),
            wave = 7,
            orderIndex = 35
        )
    )
}
