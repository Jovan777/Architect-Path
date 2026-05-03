package com.example.pmuprojekat.data.seed.architect

import com.example.pmuprojekat.core.model.LearningLevel
import com.example.pmuprojekat.core.model.QuestionType
import com.example.pmuprojekat.data.seed.SeedQuestion

object ArchitectScalingSeed {
    val questions: List<SeedQuestion> = listOf(
        SeedQuestion(
                    questionId = "A5.1",
                    level = LearningLevel.ARCHITECT.id,
                    type = QuestionType.SCALING_ASSESSMENT.id,
                    title = "Skaliranje aplikacije za dostavu lekova tokom sezonskog talasa potražnje",
                    prompt = """
                    Mobilna aplikacija omogućava korisnicima da naruče lekove iz partnerskih apoteka.
                    Postojeća arhitektura:
                    •	Mobile App poziva Backend API; 
                    •	Backend API komunicira sa Pharmacy Integration modulom; 
                    •	dostupnost lekova se proverava sinhrono kod partnerskih apoteka; 
                    •	porudžbine se čuvaju u glavnoj bazi; 
                    •	status isporuke se dobija od Delivery Provider-a; 
                    •	obaveštenja se šalju kroz Notification modul; 
                    •	nema posebnog cache-a za katalog lekova; 
                    •	nema read modela za dostupnost po apotekama; 
                    •	partneri imaju različitu brzinu i pouzdanost API-ja. 
                    Novi zahtev:
                    Tokom sezonskog talasa bolesti očekuje se 8–10 puta više korisnika.
                    Najveći pritisak biće na:
                    •	pretragu lekova; 
                    •	proveru dostupnosti; 
                    •	rezervaciju u apoteci; 
                    •	praćenje statusa isporuke. 
                    Posebno ograničenje:
                    Korisniku ne sme biti potvrđena porudžbina ako lek nije zaista rezervisan u apoteci.
                    """.trimIndent(),
                    aiFollowUp = """
                    Ako sistem prikaže da je lek “verovatno dostupan”, ali konačna rezervacija ne uspe, kako bi dizajnirao korisnički tok da bude pošten prema korisniku, a da ne blokira celu pretragu zbog spore integracije sa apotekom?
                    """.trimIndent(),
                    wave = 1,
                    difficulty = "expert",
                    orderIndex = 5,
                    estimatedMinutes = 10,
                    steps = listOf(
                    ArchitectSeedBuilders.orderedCardsStep(
                        questionId = "A5.1",
                        stepNumber = 1,
                        title = "Izaberi i poređaj 5 najvažnijih mera skaliranja",
                        instruction = "Izaberi i poređaj najvažnije mere skaliranja u skladu sa rizikom sistema.",
                        cards = listOf(
                            "uvesti katalog/read model za lekove koji se često pretražuju",
                            "uvesti cache ili kratkoživeći availability snapshot za prikaz dostupnosti",
                            "jasno razdvojiti “prikaz dostupnosti” od “konačne rezervacije leka”",
                            "zaštititi integracije sa apotekama circuit breaker i rate limit mehanizmima",
                            "potvrdu porudžbine vezati za uspešnu rezervaciju kod apoteke",
                            """
                            horizontalno skalirati Backend API bez promene odnosa prema partnerskim API-jima Tačan izbor i redosled: 1.	uvesti katalog/read model za lekove koji se često pretražuju 2.	uvesti cache ili kratkoživeći availability snapshot za prikaz dostupnosti 3.	jasno razdvojiti “prikaz dostupnosti” od “konačne rezervacije leka” 4.	zaštititi integracije sa apotekama circuit breaker i rate limit mehanizmima 5.	potvrdu porudžbine vezati za uspešnu rezervaciju kod apoteke
                            """.trimIndent()
                        ),
                        correctOrder = listOf(
                            "uvesti katalog/read model za lekove koji se često pretražuju",
                            "uvesti cache ili kratkoživeći availability snapshot za prikaz dostupnosti",
                            "jasno razdvojiti “prikaz dostupnosti” od “konačne rezervacije leka”",
                            "zaštititi integracije sa apotekama circuit breaker i rate limit mehanizmima",
                            "potvrdu porudžbine vezati za uspešnu rezervaciju kod apoteke"
                        )
                    ),
                    ArchitectSeedBuilders.multiChoiceStep(
                        questionId = "A5.1",
                        stepNumber = 2,
                        title = "Izaberi 3 najopasnije zamke pri skaliranju",
                        instruction = "Izaberi zamke koje mogu napraviti poslovno ili tehnički opasan sistem.",
                        options = listOf(
                            "cache dostupnosti može prikazati lek kao dostupan iako je u međuvremenu rezervisan",
                            "povećanje broja Backend API instanci može pojačati pritisak na partnerske apoteke",
                            "sistem može pogrešno potvrditi porudžbinu ako informativni snapshot tretira kao rezervaciju",
                            "read model za katalog lekova automatski rešava problem stvarne rezervacije leka",
                            "circuit breaker bez jasnog fallback-a može dovesti do zbunjujućih poruka korisniku",
                            "asinhrona obrada uvek znači da korisnik može odmah dobiti konačnu potvrdu"
                        ),
                        correctAnswers = listOf(
                            "cache dostupnosti može prikazati lek kao dostupan iako je u međuvremenu rezervisan",
                            "povećanje broja Backend API instanci može pojačati pritisak na partnerske apoteke",
                            "sistem može pogrešno potvrditi porudžbinu ako informativni snapshot tretira kao rezervaciju"
                        )
                    ),
                    ArchitectSeedBuilders.categoryStep(
                        questionId = "A5.1",
                        stepNumber = 3,
                        title = "Rasporedi odluke po tipu efekta",
                        instruction = "Razvrstaj odluke prema tipu efekta koji imaju na skaliranje, kritični tok ili rizike.",
                        categories = listOf(
                            ArchitectCategorySeed(
                                title = "Smanjenje čitanja i opterećenja",
                                items = listOf(
                                    "katalog/read model za često pretraživane lekove",
                                    "cache ili availability snapshot za prikaz dostupnosti"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Zaštita kritičnog toka porudžbine",
                                items = listOf(
                                    "razdvajanje informativne dostupnosti od konačne rezervacije",
                                    "potvrda porudžbine tek nakon uspešne rezervacije kod apoteke"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Zaštita od nestabilnih partnera",
                                items = listOf(
                                    "circuit breaker",
                                    "rate limit",
                                    "fallback poruke korisniku kada partner nije dostupan"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Nedovoljno kao samostalno rešenje",
                                items = listOf(
                                    "samo horizontalno skaliranje Backend API-ja"
                                )
                            )
                        )
                    ),
                    ArchitectSeedBuilders.freeTextStep(
                        questionId = "A5.1",
                        stepNumber = 4,
                        title = "Kratko obrazloženje",
                        instruction = """
                    Objasni šta sme biti eventualno konzistentno, a šta mora ostati strogo kontrolisano u kritičnom toku.
                    """.trimIndent()
                    )
                )
                ),
        SeedQuestion(
                    questionId = "A5.2",
                    level = LearningLevel.ARCHITECT.id,
                    type = QuestionType.SCALING_ASSESSMENT.id,
                    title = "Skaliranje platforme za online konsultacije lekara tokom epidemijskog talasa",
                    prompt = """
                    Zdravstvena ustanova koristi platformu za online konsultacije između pacijenata i lekara.
                    Postojeća arhitektura:
                    • pacijent se prijavljuje u mobilnu aplikaciju;
                    • bira slobodan termin ili ulazi u virtuelnu čekaonicu;
                    • Backend API proverava identitet pacijenta i dostupnost lekara;
                    • video poziv se pokreće preko centralnog signaling servisa;
                    • beleška lekara se čuva u glavnoj bazi;
                    • recept ili uput se generiše nakon konsultacije;
                    • notifikacije se šalju kroz Notification modul;
                    • svi pacijenti, lekari, termini, čekaonica i beleške prolaze kroz isti Backend API;
                    • nema posebnog mehanizma za kontrolu ulaska u video sesije;
                    • nema prioritetnog tretmana za hitnije slučajeve;
                    • sistem trenutno dobro radi pri uobičajenom broju konsultacija.
                    Novi zahtev:
                    Tokom epidemijskog talasa očekuje se 6–8 puta više pacijenata dnevno.
                    Najveći pritisak biće na:
                    • prijavu pacijenata u kratkom vremenu;
                    • virtuelnu čekaonicu;
                    • pokretanje video konsultacija;
                    • dostupnost lekara;
                    • čuvanje beleški i generisanje dokumenata nakon konsultacije;
                    • slanje obaveštenja pacijentima.
                    Posebno ograničenje:
                    Pacijent ne sme dobiti potvrdu da je konsultacija započeta ako lekar i video sesija nisu stvarno dodeljeni. Međutim, pacijent može čekati u virtuelnoj čekaonici uz jasno prikazan status.
                    """.trimIndent(),
                    aiFollowUp = """
                    Ako pacijent uđe u virtuelnu čekaonicu, čeka 20 minuta, a zatim lekar postane nedostupan zbog hitnog slučaja, kako bi dizajnirao korisnički tok i arhitekturu statusa da sistem ostane pošten prema pacijentu, a da ne blokira kapacitete drugim pacijentima?
                    """.trimIndent(),
                    wave = 2,
                    difficulty = "expert",
                    orderIndex = 11,
                    estimatedMinutes = 10,
                    steps = listOf(
                    ArchitectSeedBuilders.orderedCardsStep(
                        questionId = "A5.2",
                        stepNumber = 1,
                        title = "Izaberi i poređaj 5 najvažnijih mera skaliranja",
                        instruction = "Izaberi i poređaj najvažnije mere skaliranja u skladu sa rizikom sistema.",
                        cards = listOf(
                            "uvesti virtuelnu čekaonicu sa kontrolisanim ulaskom u konsultacije",
                            "razdvojiti tok zakazivanja/čekanja od stvarnog pokretanja video sesije",
                            "izdvojiti signaling/video-session servis od opšteg Backend API-ja",
                            "uvesti prioritetna pravila za hitnije slučajeve i ranjive grupe",
                            "asinhrono obrađivati post-konsultacione zadatke kao što su notifikacije i dokumenti",
                            """
                            horizontalno skalirati Backend API bez promene upravljanja čekaonicom i video sesijama Tačan izbor i redosled: 1.	uvesti virtuelnu čekaonicu sa kontrolisanim ulaskom u konsultacije 2.	razdvojiti tok zakazivanja/čekanja od stvarnog pokretanja video sesije 3.	izdvojiti signaling/video-session servis od opšteg Backend API-ja 4.	uvesti prioritetna pravila za hitnije slučajeve i ranjive grupe 5.	asinhrono obrađivati post-konsultacione zadatke kao što su notifikacije i dokumenti
                            """.trimIndent()
                        ),
                        correctOrder = listOf(
                            "uvesti virtuelnu čekaonicu sa kontrolisanim ulaskom u konsultacije",
                            "razdvojiti tok zakazivanja/čekanja od stvarnog pokretanja video sesije",
                            "izdvojiti signaling/video-session servis od opšteg Backend API-ja",
                            "uvesti prioritetna pravila za hitnije slučajeve i ranjive grupe",
                            "asinhrono obrađivati post-konsultacione zadatke kao što su notifikacije i dokumenti"
                        )
                    ),
                    ArchitectSeedBuilders.multiChoiceStep(
                        questionId = "A5.2",
                        stepNumber = 2,
                        title = "Izaberi 3 najopasnije zamke pri skaliranju",
                        instruction = "Izaberi zamke koje mogu napraviti poslovno ili tehnički opasan sistem.",
                        options = listOf(
                            "tretirati ulazak u čekaonicu kao potvrdu da je konsultacija stvarno započeta",
                            "dozvoliti neograničen broj pacijenata da istovremeno pokreće video sesije",
                            "skalirati samo Backend API, a ostaviti isti uski grlo u signaling/video-session toku",
                            "asinhrono slanje notifikacija znači da se i medicinska odluka može obraditi bez kontrole lekara",
                            "virtuelna čekaonica može smanjiti pritisak, ali mora jasno prikazati status pacijentu",
                            "prioritetna pravila nisu potrebna jer svi pacijenti treba uvek da se obrađuju redom dolaska"
                        ),
                        correctAnswers = listOf(
                            "tretirati ulazak u čekaonicu kao potvrdu da je konsultacija stvarno započeta",
                            "dozvoliti neograničen broj pacijenata da istovremeno pokreće video sesije",
                            "skalirati samo Backend API, a ostaviti isti uski grlo u signaling/video-session toku"
                        )
                    ),
                    ArchitectSeedBuilders.categoryStep(
                        questionId = "A5.2",
                        stepNumber = 3,
                        title = "Rasporedi izmene po cilju",
                        instruction = "Razvrstaj odluke prema tipu efekta koji imaju na skaliranje, kritični tok ili rizike.",
                        categories = listOf(
                            ArchitectCategorySeed(
                                title = "Kontrola vršnog opterećenja",
                                items = listOf(
                                    "virtuelna čekaonica sa kontrolisanim ulazom",
                                    "prioritetna pravila za hitnije slučajeve",
                                    "monitoring zauzetosti lekara i kapaciteta video sesija"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Skaliranje video toka",
                                items = listOf(
                                    "izdvojen signaling/video-session servis"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Zaštita korisničkog toka i statusa",
                                items = listOf(
                                    "jasno razlikovanje statusa “čeka”, “dodeljen lekar” i “konsultacija započeta”"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Rasterećenje sporednih procesa",
                                items = listOf(
                                    "asinhrona obrada notifikacija i dokumenata nakon konsultacije"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Kartica koja nije dobro rešenje",
                                items = listOf(
                                    "neograničeno pokretanje video sesija čim pacijent klikne dugme"
                                )
                            )
                        )
                    ),
                    ArchitectSeedBuilders.freeTextStep(
                        questionId = "A5.2",
                        stepNumber = 4,
                        title = "Kratko obrazloženje",
                        instruction = """
                    Objasni šta sme biti eventualno konzistentno, a šta mora ostati strogo kontrolisano u kritičnom toku.
                    """.trimIndent()
                    )
                )
                ),
        SeedQuestion(
                    questionId = "A5.3",
                    level = LearningLevel.ARCHITECT.id,
                    type = QuestionType.SCALING_ASSESSMENT.id,
                    title = "Skaliranje platforme za live kodiranje tokom masovnog online ispita",
                    prompt = """
                    Fakultet koristi platformu za online ispit iz programiranja. Studenti rešavaju zadatke u browser editoru, pokreću testove i šalju konačna rešenja.
                    Postojeća arhitektura:
                    • student se prijavljuje preko fakultetskog naloga;
                    • browser editor periodično šalje autosave koda Backend API-ju;
                    • student može pokrenuti javne testove tokom rada;
                    • konačno rešenje se šalje na evaluaciju;
                    • backend pokreće testove u izolovanom execution okruženju;
                    • rezultati testova se upisuju u glavnu bazu;
                    • nastavnici prate tok ispita kroz admin dashboard;
                    • svi autosave zahtevi, test run zahtevi, final submission i dashboard čitanja prolaze kroz isti Backend API;
                    • nema posebnog reda za izvršavanje testova;
                    • nema prioriteta između autosave-a, probnog pokretanja testova i finalne predaje;
                    • nema jasne zaštite od situacije u kojoj veliki broj studenata istovremeno klikne “Run tests”.
                    Novi zahtev:
                    Platforma treba da podrži masovni ispit na nivou celog fakulteta, sa nekoliko hiljada studenata u isto vreme.
                    Najveći pritisak biće na:
                    • autosave koda;
                    • istovremeno pokretanje testova;
                    • finalnu predaju rešenja u poslednjim minutima ispita;
                    • izolovano izvršavanje studentskog koda;
                    • prikaz statusa studentima;
                    • nastavnički dashboard.
                    Posebno ograničenje:
                    Finalna predaja rešenja mora imati veći prioritet od probnog pokretanja testova. Autosave može kasniti nekoliko sekundi, ali final submission ne sme biti izgubljen.
                    """.trimIndent(),
                    aiFollowUp = """
                    Ako student klikne “Submit” u poslednjih deset sekundi ispita, a evaluacioni red je već pun zbog hiljada probnih pokretanja testova, kako bi sistem trebalo da obezbedi da finalna predaja bude prihvaćena, zabeležena i kasnije evaluirana bez nepravednog gubitka rada studenta?
                    """.trimIndent(),
                    wave = 3,
                    difficulty = "expert",
                    orderIndex = 17,
                    estimatedMinutes = 10,
                    steps = listOf(
                    ArchitectSeedBuilders.orderedCardsStep(
                        questionId = "A5.3",
                        stepNumber = 1,
                        title = "Izaberi i poređaj 5 najvažnijih mera skaliranja",
                        instruction = "Izaberi i poređaj najvažnije mere skaliranja u skladu sa rizikom sistema.",
                        cards = listOf(
                            "razdvojiti autosave, probno pokretanje testova i final submission kao različite tokove",
                            """
                            uvesti prioritetni red za izvršavanje evaluacija, gde final submission ima prednost nad probnim testovima
                            """.trimIndent(),
                            "ograničiti broj istovremenih test run zahteva po studentu i po ispitu",
                            "izolovati execution worker-e od glavnog Backend API-ja",
                            "uvesti pouzdan zapis finalne predaje pre pokretanja evaluacije",
                            """
                            horizontalno skalirati Backend API bez promene toka izvršavanja testova Tačan izbor i redosled: 1.	razdvojiti autosave, probno pokretanje testova i final submission kao različite tokove 2.	uvesti pouzdan zapis finalne predaje pre pokretanja evaluacije 3.	uvesti prioritetni red za izvršavanje evaluacija, gde final submission ima prednost nad probnim testovima 4.	izolovati execution worker-e od glavnog Backend API-ja 5.	ograničiti broj istovremenih test run zahteva po studentu i po ispitu
                            """.trimIndent()
                        ),
                        correctOrder = listOf(
                            "razdvojiti autosave, probno pokretanje testova i final submission kao različite tokove",
                            "uvesti pouzdan zapis finalne predaje pre pokretanja evaluacije",
                            """
                            uvesti prioritetni red za izvršavanje evaluacija, gde final submission ima prednost nad probnim testovima
                            """.trimIndent(),
                            "izolovati execution worker-e od glavnog Backend API-ja",
                            "ograničiti broj istovremenih test run zahteva po studentu i po ispitu"
                        )
                    ),
                    ArchitectSeedBuilders.multiChoiceStep(
                        questionId = "A5.3",
                        stepNumber = 2,
                        title = "Izaberi 3 najopasnije zamke pri skaliranju",
                        instruction = "Izaberi zamke koje mogu napraviti poslovno ili tehnički opasan sistem.",
                        options = listOf(
                            "tretirati probno pokretanje testova i finalnu predaju kao zahteve iste važnosti",
                            "pokretati studentski kod direktno u Backend API procesu radi manjeg kašnjenja",
                            "dozvoliti da final submission postoji samo kao zahtev u memoriji dok se testovi ne završe",
                            "autosave može biti eventualno konzistentan, ali mora jasno prikazati status čuvanja studentu",
                            "ograničavanje broja probnih testova može zaštititi sistem, ali ne sme blokirati finalnu predaju",
                            """
                            nastavnički dashboard treba da osvežava podatke direktnim čitanjem svih aktivnih sesija svakih nekoliko sekundi
                            """.trimIndent()
                        ),
                        correctAnswers = listOf(
                            "tretirati probno pokretanje testova i finalnu predaju kao zahteve iste važnosti",
                            "pokretati studentski kod direktno u Backend API procesu radi manjeg kašnjenja",
                            "dozvoliti da final submission postoji samo kao zahtev u memoriji dok se testovi ne završe"
                        )
                    ),
                    ArchitectSeedBuilders.categoryStep(
                        questionId = "A5.3",
                        stepNumber = 3,
                        title = "Rasporedi izmene po cilju",
                        instruction = "Razvrstaj odluke prema tipu efekta koji imaju na skaliranje, kritični tok ili rizike.",
                        categories = listOf(
                            ArchitectCategorySeed(
                                title = "Zaštita finalne predaje",
                                items = listOf(
                                    "pouzdan zapis final submission-a pre evaluacije",
                                    "prioritetni evaluation queue"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Skaliranje izvršavanja koda",
                                items = listOf(
                                    "izdvojeni execution worker-i"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Kontrola prekomernog opterećenja",
                                items = listOf(
                                    "rate limit za probno pokretanje testova",
                                    "posebni limiti po studentu i po ispitu"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Vidljivost statusa korisnicima i nastavnicima",
                                items = listOf(
                                    "status autosave-a prikazan studentu",
                                    "nastavnički dashboard koji čita agregirane statuse"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Kartica koja nije dobro rešenje",
                                items = listOf(
                                    "izvršavanje studentskog koda direktno u Backend API-ju"
                                )
                            )
                        )
                    ),
                    ArchitectSeedBuilders.freeTextStep(
                        questionId = "A5.3",
                        stepNumber = 4,
                        title = "Kratko obrazloženje",
                        instruction = """
                    Objasni šta sme biti eventualno konzistentno, a šta mora ostati strogo kontrolisano u kritičnom toku.
                    """.trimIndent()
                    )
                )
                ),
        SeedQuestion(
                    questionId = "A5.4",
                    level = LearningLevel.ARCHITECT.id,
                    type = QuestionType.SCALING_ASSESSMENT.id,
                    title = """
                    Skaliranje sistema za pametno upravljanje punjačima električnih vozila tokom večernog opterećenja mreže
                    """.trimIndent(),
                    prompt = """
                    Kompanija upravlja mrežom punjača za električna vozila u više gradova. Korisnici preko mobilne aplikacije pronalaze slobodan punjač, rezervišu termin, započinju punjenje i prate potrošnju energije.
                    Postojeća arhitektura:
                    • mobilna aplikacija prikazuje mapu punjača;
                    • Backend API proverava status punjača;
                    • svaki punjač periodično šalje stanje: slobodan, zauzet, u kvaru, van mreže;
                    • korisnik može rezervisati punjač na ograničeno vreme;
                    • sesija punjenja se pokreće preko Charging Controller modula;
                    • obračun potrošnje se čuva u glavnoj bazi;
                    • administracija prati zauzetost i kvarove kroz dashboard;
                    • nema posebnog read modela za prikaz mape;
                    • status punjača se često proverava sinhrono;
                    • svi zahtevi za mapu, rezervaciju, početak punjenja i dashboard prolaze kroz isti Backend API;
                    • nema posebnog mehanizma za kontrolu opterećenja kada mnogo korisnika traži punjač u istom delu grada.
                    Novi zahtev:
                    Tokom večernih sati očekuje se nagli rast korišćenja, posebno između 18h i 22h.
                    Najveći pritisak biće na:
                    • prikaz mape dostupnih punjača;
                    • proveru trenutnog statusa punjača;
                    • rezervaciju punjača;
                    • pokretanje sesije punjenja;
                    • obračun potrošnje;
                    • administrativni dashboard za kvarove i opterećenje mreže.
                    Posebno ograničenje:
                    Korisniku se može prikazati da je punjač “verovatno slobodan”, ali mu se ne sme potvrditi rezervacija ili početak punjenja ako punjač nije stvarno zaključan za njega.
                    """.trimIndent(),
                    aiFollowUp = """
                    Ako aplikacija prikaže da je punjač slobodan, ali korisnik tek pri rezervaciji sazna da ga je drugi korisnik upravo zauzeo, kako bi dizajnirao korisnički tok i arhitekturu statusa da sistem ostane brz, pošten i konzistentan bez zaključavanja svih punjača već pri prikazu mape?
                    """.trimIndent(),
                    wave = 4,
                    difficulty = "expert",
                    orderIndex = 23,
                    estimatedMinutes = 10,
                    steps = listOf(
                    ArchitectSeedBuilders.orderedCardsStep(
                        questionId = "A5.4",
                        stepNumber = 1,
                        title = "Izaberi i poređaj 5 najvažnijih mera skaliranja",
                        instruction = "Izaberi i poređaj najvažnije mere skaliranja u skladu sa rizikom sistema.",
                        cards = listOf(
                            "uvesti geo-particionisan read model za mapu punjača i osnovni status dostupnosti",
                            "uvesti kratkoživeći status snapshot za prikaz dostupnosti punjača",
                            "razdvojiti prikaz dostupnosti od konačne rezervacije i zaključavanja punjača",
                            "uvesti kontrolisan reservation/lock tok za punjač pre potvrde korisniku",
                            "odvojiti telemetriju i administrativni dashboard od kritičnog toka rezervacije i početka punjenja",
                            """
                            horizontalno skalirati Backend API bez promene modela statusa, rezervacije i komunikacije sa punjačima Tačan izbor i redosled: 1.	uvesti geo-particionisan read model za mapu punjača i osnovni status dostupnosti 2.	uvesti kratkoživeći status snapshot za prikaz dostupnosti punjača 3.	razdvojiti prikaz dostupnosti od konačne rezervacije i zaključavanja punjača 4.	uvesti kontrolisan reservation/lock tok za punjač pre potvrde korisniku 5.	odvojiti telemetriju i administrativni dashboard od kritičnog toka rezervacije i početka punjenja
                            """.trimIndent()
                        ),
                        correctOrder = listOf(
                            "uvesti geo-particionisan read model za mapu punjača i osnovni status dostupnosti",
                            "uvesti kratkoživeći status snapshot za prikaz dostupnosti punjača",
                            "razdvojiti prikaz dostupnosti od konačne rezervacije i zaključavanja punjača",
                            "uvesti kontrolisan reservation/lock tok za punjač pre potvrde korisniku",
                            "odvojiti telemetriju i administrativni dashboard od kritičnog toka rezervacije i početka punjenja"
                        )
                    ),
                    ArchitectSeedBuilders.multiChoiceStep(
                        questionId = "A5.4",
                        stepNumber = 2,
                        title = "Izaberi 3 najopasnije zamke pri skaliranju",
                        instruction = "Izaberi zamke koje mogu napraviti poslovno ili tehnički opasan sistem.",
                        options = listOf(
                            "tretirati status sa mape kao garantovanu dostupnost punjača",
                            "dozvoliti da više korisnika istovremeno dobije potvrdu rezervacije za isti punjač",
                            "skalirati samo Backend API, a ostaviti isti uski grlo u sinhronoj komunikaciji sa punjačima",
                            "status snapshot može ubrzati prikaz mape, ali mora imati kratak rok važenja",
                            "administrativni dashboard treba da ima isti prioritet kao zaključavanje punjača za korisnika",
                            "ako je punjač poslednji put bio slobodan, sistem može pokrenuti punjenje bez dodatne provere"
                        ),
                        correctAnswers = listOf(
                            "tretirati status sa mape kao garantovanu dostupnost punjača",
                            "dozvoliti da više korisnika istovremeno dobije potvrdu rezervacije za isti punjač",
                            "ako je punjač poslednji put bio slobodan, sistem može pokrenuti punjenje bez dodatne provere"
                        )
                    ),
                    ArchitectSeedBuilders.categoryStep(
                        questionId = "A5.4",
                        stepNumber = 3,
                        title = "Rasporedi izmene po cilju",
                        instruction = "Razvrstaj odluke prema tipu efekta koji imaju na skaliranje, kritični tok ili rizike.",
                        categories = listOf(
                            ArchitectCategorySeed(
                                title = "Skaliranje prikaza dostupnosti",
                                items = listOf(
                                    "geo-particionisan read model za mapu punjača",
                                    "kratkoživeći status snapshot"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Zaštita kritične rezervacije",
                                items = listOf(
                                    "reservation/lock tok za punjač",
                                    "potvrda rezervacije samo nakon uspešnog zaključavanja punjača"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Rasterećenje sporednih tokova",
                                items = listOf(
                                    "odvojena obrada telemetrije punjača",
                                    "izdvojen administrativni dashboard za kvarove i opterećenje"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Kontrola lokalnih vršnih opterećenja",
                                items = listOf(
                                    "kontrola opterećenja po gradskoj zoni"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Kartica koja nije dobro rešenje",
                                items = listOf(
                                    "pokretanje punjenja na osnovu poslednjeg poznatog statusa bez provere"
                                )
                            )
                        )
                    ),
                    ArchitectSeedBuilders.freeTextStep(
                        questionId = "A5.4",
                        stepNumber = 4,
                        title = "Kratko obrazloženje",
                        instruction = """
                    Objasni šta sme biti eventualno konzistentno, a šta mora ostati strogo kontrolisano u kritičnom toku.
                    """.trimIndent()
                    )
                )
                ),
        SeedQuestion(
                    questionId = "A5.5",
                    level = LearningLevel.ARCHITECT.id,
                    type = QuestionType.SCALING_ASSESSMENT.id,
                    title = "Skaliranje sistema za proveru karantinskih uslova u međunarodnom lancu isporuke hrane",
                    prompt = """
                    Međunarodni distributer hrane koristi sistem za proveru pošiljki koje prolaze kroz više granica i skladišta. Svaka pošiljka mora imati validne sertifikate, temperaturni zapis i dozvolu za ulazak u određenu zemlju.
                    Postojeća arhitektura:
                    • skladišta registruju dolazak i odlazak pošiljke;
                    • IoT senzori šalju temperaturu tokom transporta;
                    • Backend API proverava sertifikate pošiljke;
                    • Compliance modul proverava pravila zemlje uvoza;
                    • eksterni regulatorni servisi vraćaju status dozvole za određene kategorije hrane;
                    • status pošiljke se čuva u glavnoj bazi;
                    • operateri prate pošiljke kroz dashboard;
                    • alarmi se šalju kada temperatura pređe dozvoljeni prag;
                    • svi tokovi trenutno prolaze kroz isti Backend API;
                    • provera regulatornih pravila se radi sinhrono u trenutku obrade pošiljke;
                    • dashboard često čita glavnu bazu kako bi prikazao skoro realno stanje.
                    Novi zahtev:
                    Sistem treba da podrži značajno veći broj pošiljki tokom praznične sezone i širenje na nova tržišta.
                    Najveći pritisak biće na:
                    • obradu velikog broja IoT temperaturnih očitavanja;
                    • proveru sertifikata za pošiljke;
                    • proveru regulatornih pravila po zemlji;
                    • otkrivanje temperaturnih incidenata;
                    • dashboard za operatere;
                    • alarme za pošiljke koje ne smeju nastaviti transport.
                    Posebno ograničenje:
                    Rutinska temperaturna očitavanja mogu se obrađivati agregirano, ali temperaturni incident ili nevažeća regulatorna dozvola mora brzo zaustaviti dalji tok pošiljke.
                    """.trimIndent(),
                    aiFollowUp = """
                    Ako IoT senzori pokazuju kratko prekoračenje temperature, ali regulatorni servis trenutno ne odgovara za tu kategoriju hrane, kako bi sistem trebalo da odluči da li pošiljku privremeno blokira, označi za ručnu proveru ili pusti dalje? Objasni kompromis između skaliranja, bezbednosti hrane i poslovnog kontinuiteta.
                    """.trimIndent(),
                    wave = 5,
                    difficulty = "expert",
                    orderIndex = 29,
                    estimatedMinutes = 10,
                    steps = listOf(
                    ArchitectSeedBuilders.orderedCardsStep(
                        questionId = "A5.5",
                        stepNumber = 1,
                        title = "Izaberi i poređaj 5 najvažnijih mera skaliranja",
                        instruction = "Izaberi i poređaj najvažnije mere skaliranja u skladu sa rizikom sistema.",
                        cards = listOf(
                            "razdvojiti rutinsku IoT telemetriju od kritičnih compliance događaja",
                            "uvesti stream processing za temperaturna očitavanja i detekciju prekoračenja praga",
                            "keširati regulatorna pravila koja imaju jasno definisan rok važenja i verziju",
                            "uvesti prioritetni tok za pošiljke sa temperaturnim incidentom ili nevažećom dozvolom",
                            "izdvojiti operaterski dashboard u read model koji se ažurira iz događaja pošiljke",
                            """
                            tretirati svako temperaturno očitavanje kao jednako hitan zahtev prema glavnoj bazi Tačan izbor i redosled: 1.	razdvojiti rutinsku IoT telemetriju od kritičnih compliance događaja 2.	uvesti stream processing za temperaturna očitavanja i detekciju prekoračenja praga 3.	uvesti prioritetni tok za pošiljke sa temperaturnim incidentom ili nevažećom dozvolom 4.	keširati regulatorna pravila koja imaju jasno definisan rok važenja i verziju 5.	izdvojiti operaterski dashboard u read model koji se ažurira iz događaja pošiljke
                            """.trimIndent()
                        ),
                        correctOrder = listOf(
                            "razdvojiti rutinsku IoT telemetriju od kritičnih compliance događaja",
                            "uvesti stream processing za temperaturna očitavanja i detekciju prekoračenja praga",
                            "uvesti prioritetni tok za pošiljke sa temperaturnim incidentom ili nevažećom dozvolom",
                            "keširati regulatorna pravila koja imaju jasno definisan rok važenja i verziju",
                            "izdvojiti operaterski dashboard u read model koji se ažurira iz događaja pošiljke"
                        )
                    ),
                    ArchitectSeedBuilders.multiChoiceStep(
                        questionId = "A5.5",
                        stepNumber = 2,
                        title = "Izaberi 3 najopasnije zamke pri skaliranju",
                        instruction = "Izaberi zamke koje mogu napraviti poslovno ili tehnički opasan sistem.",
                        options = listOf(
                            "obrađivati rutinska očitavanja i temperaturne incidente istim prioritetom",
                            "koristiti keširana regulatorna pravila bez verzije, roka važenja i mehanizma invalidacije",
                            "dozvoliti nastavak transporta dok je status regulatorne dozvole nepoznat za rizičnu kategoriju hrane",
                            "agregacija temperaturnih očitavanja može smanjiti opterećenje ako ne sakrije kritična prekoračenja",
                            "read model dashboard-a može kasniti, ali ne sme biti jedini izvor odluke o blokadi pošiljke",
                            """
                            dashboard operatera treba direktno da čita glavnu bazu svakih nekoliko sekundi tokom najvećeg opterećenja
                            """.trimIndent()
                        ),
                        correctAnswers = listOf(
                            "obrađivati rutinska očitavanja i temperaturne incidente istim prioritetom",
                            "koristiti keširana regulatorna pravila bez verzije, roka važenja i mehanizma invalidacije",
                            "dozvoliti nastavak transporta dok je status regulatorne dozvole nepoznat za rizičnu kategoriju hrane"
                        )
                    ),
                    ArchitectSeedBuilders.categoryStep(
                        questionId = "A5.5",
                        stepNumber = 3,
                        title = "Rasporedi izmene po cilju",
                        instruction = "Razvrstaj odluke prema tipu efekta koji imaju na skaliranje, kritični tok ili rizike.",
                        categories = listOf(
                            ArchitectCategorySeed(
                                title = "Skaliranje IoT ulaza",
                                items = listOf(
                                    "stream processing za temperaturna očitavanja",
                                    "agregacija rutinskih očitavanja",
                                    "odvajanje rutinske telemetrije od compliance odluka"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Zaštita kritičnih compliance odluka",
                                items = listOf(
                                    "prioritetni tok za temperaturne incidente",
                                    "blokada pošiljke kada je kritičan compliance status nepoznat"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Smanjenje opterećenja regulatornih provera",
                                items = listOf(
                                    "keš regulatornih pravila sa verzijom i rokom važenja"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Rasterećenje operaterskog nadzora",
                                items = listOf(
                                    "read model za operaterski dashboard"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Kartica koja nije dobro rešenje",
                                items = listOf(
                                    "direktno dashboard čitanje glavne baze u vršnom opterećenju"
                                )
                            )
                        )
                    ),
                    ArchitectSeedBuilders.freeTextStep(
                        questionId = "A5.5",
                        stepNumber = 4,
                        title = "Kratko obrazloženje",
                        instruction = """
                    Objasni šta sme biti eventualno konzistentno, a šta mora ostati strogo kontrolisano u kritičnom toku.
                    """.trimIndent()
                    )
                )
                )
    )
}
