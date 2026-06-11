package com.example.pmuprojekat.data.seed.architect

import com.example.pmuprojekat.core.model.LearningLevel
import com.example.pmuprojekat.core.model.QuestionType
import com.example.pmuprojekat.data.seed.SeedQuestion

object ArchitectCompromiseSeed {
    val questions: List<SeedQuestion> = listOf(
        SeedQuestion(
                    questionId = "A6.1",
                    level = LearningLevel.ARCHITECT.id,
                    type = QuestionType.ARCHITECTURAL_COMPROMISE.id,
                    title = "Izbor arhitekture za startup koji razvija platformu za rezervaciju sportskih terena",
                    prompt = """
                    Startup razvija platformu za rezervaciju sportskih terena.
                    Planirane funkcionalnosti:
                    • klubovi objavljuju dostupne termine;
                    • korisnici rezervišu termine;
                    • korisnici mogu platiti rezervaciju;
                    • klubovi upravljaju cenama i pravilima otkazivanja;
                    • sistem šalje podsetnike;
                    • kasnije se planira preporuka termina i terena;
                    • kasnije se planira aplikacija za više gradova.
                    Ograničenja:
                    • tim ima 3 developera;
                    • rok za prvu verziju je 3 meseca;
                    • očekuje se nekoliko hiljada korisnika u početku;
                    • najkritičniji problem je sprečiti duplu rezervaciju termina;
                    • tim nema poseban DevOps tim;
                    • poslovna pravila će se menjati;
                    • investitori žele brzo lansiranje, ali ne žele haotičan kod koji se ne može razvijati;
                    • plaćanje je važno, ali može u prvoj verziji koristiti eksternog providera.
                    """.trimIndent(),
                    aiFollowUp = """
                    Ako znaš da će možda jednog dana Booking modul postati poseban servis, koja pravila bi uveo već sada u modularnom monolitu da njegovo kasnije izdvajanje ne bude praktično prepisivanje celog sistema?
                    """.trimIndent(),
                    wave = 1,
                    difficulty = "expert",
                    orderIndex = 6,
                    estimatedMinutes = 10,
                    steps = listOf(
                    ArchitectSeedBuilders.singleChoiceStep(
                        questionId = "A6.1",
                        stepNumber = 1,
                        title = "Izaberi početnu arhitektonsku odluku",
                        instruction = "Izaberi odluku koja najbolje balansira rok, tim, rizik i evoluciju sistema.",
                        options = listOf(
                            """
                            A. Modularni monolit sa jasno izdvojenim domenima
                            Rezervacije, korisnici, klubovi, plaćanja i obaveštenja razvijaju se u jednoj deploy jedinici, ali sa jasnim granicama modula i kontrolisanim zavisnostima.
                            """.trimIndent(),
                            """
                            B. Mikroservisna arhitektura od prvog dana
                            Booking, User, Club, Payment, Notification i Reporting se odmah razvijaju kao zasebni servisi sa posebnim deployment-om, monitoringom i komunikacionim ugovorima.
                            """.trimIndent(),
                            """
                            C. Low-code/no-code platforma kao osnovni sistem
                            Prva verzija se gradi najbrže kroz gotovu platformu, uz prihvatanje ograničenja u prilagođavanju pravila rezervacija, otkazivanja i plaćanja.
                            """.trimIndent(),
                            """
                            D. Serverless/event-first arhitektura za sve funkcionalnosti
                            Svaka akcija se implementira kao posebna funkcija ili događaj, uključujući rezervacije, plaćanja, podsetnike i izveštaje.
                            """.trimIndent(),
                            """
                            E. Jednostavan CRUD monolit bez domenskih granica
                            Sistem se pravi brzo kroz standardne tabele i servise, bez posebnog izdvajanja Booking, Club, User i Payment odgovornosti.
                            """.trimIndent()
                        ),
                        correctAnswer = """
                    A. Modularni monolit sa jasno izdvojenim domenima
                    Rezervacije, korisnici, klubovi, plaćanja i obaveštenja razvijaju se u jednoj deploy jedinici, ali sa jasnim granicama modula i kontrolisanim zavisnostima.
                    """.trimIndent()
                    ),
                    ArchitectSeedBuilders.singleChoiceStep(
                        questionId = "A6.1",
                        stepNumber = 2,
                        title = """
                    Pritisak 1 — Biznis traži brzo lansiranje
                    """.trimIndent(),
                        instruction = "Izaberi najbolji odgovor arhitekte na ovaj pritisak.",
                        options = listOf(
                            "A. Mikroservisi su najbolji izbor jer investitorima pokazuju da je sistem enterprise-ready.",
                            """
                            B. Modularni monolit omogućava brže lansiranje uz dovoljno jasne granice da se sistem kasnije razvija bez haosa.
                            """.trimIndent(),
                            "C. Low-code je najbolji jer je najbrži, čak i ako pravila rezervacije postanu teško prilagodljiva.",
                            "D. Treba prvo napraviti AI preporuke termina, jer će to investitorima pokazati inovativnost."
                        ),
                        correctAnswer = """
                    B. Modularni monolit omogućava brže lansiranje uz dovoljno jasne granice da se sistem kasnije razvija bez haosa.
                    """.trimIndent()
                    ),
                    ArchitectSeedBuilders.singleChoiceStep(
                        questionId = "A6.1",
                        stepNumber = 3,
                        title = """
                    Pritisak 2 — Tim se plaši haotičnog koda
                    """.trimIndent(),
                        instruction = "Izaberi najbolji odgovor arhitekte na ovaj pritisak.",
                        options = listOf(
                            "A. Dovoljno je da se kod podeli po folderima, bez kontrole zavisnosti između modula.",
                            """
                            B. Treba definisati granice modula, pravila zavisnosti i jasne ugovore između Booking, Club, User i Payment delova.
                            """.trimIndent(),
                            "C. Treba odmah napraviti poseban servis za svaki modul kako bi se sprečio haos.",
                            "D. Najbolje je da svi moduli dele iste interne klase i tabele, jer je tim mali."
                        ),
                        correctAnswer = """
                    B. Treba definisati granice modula, pravila zavisnosti i jasne ugovore između Booking, Club, User i Payment delova.
                    """.trimIndent()
                    ),
                    ArchitectSeedBuilders.singleChoiceStep(
                        questionId = "A6.1",
                        stepNumber = 4,
                        title = """
                    Pritisak 3 — Kritični rizik je dupla rezervacija
                    """.trimIndent(),
                        instruction = "Izaberi najbolji odgovor arhitekte na ovaj pritisak.",
                        options = listOf(
                            """
                            A. Sprečavanje duple rezervacije treba rešiti samo na frontendu onemogućavanjem dugmeta nakon prvog klika.
                            """.trimIndent(),
                            """
                            B. Booking tok mora imati jaču konzistentnost i kontrolisan upis, čak i ako izveštaji i preporuke mogu kasniti.
                            """.trimIndent(),
                            "C. Duplu rezervaciju treba rešiti kasnije, kada broj korisnika poraste.",
                            "D. Preporuka termina treba da odlučuje koji korisnik ima prioritet ako više njih želi isti termin."
                        ),
                        correctAnswer = """
                    B. Booking tok mora imati jaču konzistentnost i kontrolisan upis, čak i ako izveštaji i preporuke mogu kasniti.
                    """.trimIndent()
                    ),
                    ArchitectSeedBuilders.categoryStep(
                        questionId = "A6.1",
                        stepNumber = 5,
                        title = "Rasporedi odluke: sada / kasnije / nikako",
                        instruction = "Razvrstaj odluke prema tome šta treba uraditi odmah, šta ostaviti za kasnije, a šta ne treba raditi.",
                        categories = listOf(
                            ArchitectCategorySeed(
                                title = "Uraditi sada",
                                items = listOf(
                                    "modularni monolit sa jasnim Booking, Club, User, Payment i Notification modulima",
                                    "kontrolisan Booking tok koji sprečava duplu rezervaciju termina",
                                    "eksterni payment provider za prvu verziju",
                                    "pravila zavisnosti između modula već od prve verzije",
                                    "osnovni izveštaji za klubove kroz supporting modul"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Ostaviti za kasnije",
                                items = listOf(
                                    "AI preporuka termina i terena",
                                    "interni payment sistem umesto eksternog providera u prvoj verziji"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Ne raditi / pogrešan pravac",
                                items = listOf(
                                    "poseban mikroservis za svaki modul od prvog dana",
                                    "direktno povezivanje mobilne aplikacije na bazu termina radi brzine",
                                    "deljenje istih internih modela između svih modula radi bržeg razvoja"
                                )
                            )
                        )
                    ),
                    ArchitectSeedBuilders.categoryStep(
                        questionId = "A6.1",
                        stepNumber = 6,
                        title = "Prepoznaj signale za promenu odluke",
                        instruction = "Razvrstaj tvrdnje u prave signale, lažne signale i opasne signale koji traže oprez.",
                        categories = listOf(
                            ArchitectCategorySeed(
                                title = "Pravi signal za promenu arhitektonske odluke",
                                items = listOf(
                                    "Booking modul ima višestruko veće opterećenje od ostatka sistema i traži nezavisno skaliranje",
                                    "tim je porastao i ima kapacitet za CI/CD, monitoring i operativno održavanje više servisa",
                                    "granice Booking modula su stabilne i komunikacija sa drugim modulima može se jasno ugovoriti"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Lažni signal",
                                items = listOf(
                                    "investitori žele da prezentacija koristi reč “microservices”",
                                    "frontend tim želi drugačiji prikaz termina za mobilnu i web aplikaciju",
                                    "broj korisnika je i dalje mali, a poslovna pravila su jednostavna"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Opasan signal koji traži oprez, ali ne nužno promenu arhitekture",
                                items = listOf(
                                    "eksterni payment provider često kasni ili povremeno ne odgovara",
                                    "User i Booking modul počinju da dele sve više internih tabela i pravila"
                                )
                            )
                        )
                    ),
                    ArchitectSeedBuilders.miniAdrStep(
                        questionId = "A6.1",
                        stepNumber = 7,
                        title = "Mini ADR obrazloženje",
                        instruction = "Popuni kratak arhitektonski zapis: odluka, razlog, prihvaćena cena i signal za promenu odluke."
                    )
                )
                ),
        SeedQuestion(
                    questionId = "A6.2",
                    level = LearningLevel.ARCHITECT.id,
                    type = QuestionType.ARCHITECTURAL_COMPROMISE.id,
                    title = "Izbor arhitekture za multi-tenant SaaS platformu za online obuke zaposlenih",
                    prompt = """
                    Kompanija razvija SaaS platformu za online obuke zaposlenih koju će koristiti različite organizacije kao klijenti.
                    Planirane funkcionalnosti:
                    • svaka organizacija ima svoje administratore, zaposlene i kurseve;
                    • administratori organizacije kreiraju obuke i prate napredak zaposlenih;
                    • zaposleni rešavaju lekcije, testove i zadatke;
                    • sistem čuva rezultate, sertifikate i istoriju aktivnosti;
                    • neki klijenti traže prilagođen izgled platforme;
                    • veći klijenti žele posebne izveštaje;
                    • kasnije se planira preporuka obuka na osnovu profila zaposlenog;
                    • kasnije se planira integracija sa HR sistemima klijenata.
                    Ograničenja:
                    • tim ima 5 developera;
                    • prva verzija mora biti lansirana za 4 meseca;
                    • očekuje se 20–30 manjih klijenata u prvoj godini;
                    • jedan ili dva velika klijenta mogu imati znatno veći broj zaposlenih;
                    • podaci različitih organizacija ne smeju se pomešati;
                    • sistem mora omogućiti lak onboarding novih klijenata;
                    • potpuna izolacija baze po klijentu povećava operativnu složenost;
                    • zajednička baza bez jasne tenant izolacije nosi veliki bezbednosni rizik.
                    """.trimIndent(),
                    aiFollowUp = """
                    Ako platforma koristi zajedničku bazu za više organizacija, koja pravila bi uveo u modelu podataka, API sloju, autorizaciji, testovima i audit-u da greška jednog developera ne dovede do curenja podataka između tenant-a?
                    """.trimIndent(),
                    wave = 2,
                    difficulty = "expert",
                    orderIndex = 12,
                    estimatedMinutes = 10,
                    steps = listOf(
                    ArchitectSeedBuilders.singleChoiceStep(
                        questionId = "A6.2",
                        stepNumber = 1,
                        title = "Izaberi početnu arhitektonsku odluku",
                        instruction = "Izaberi odluku koja najbolje balansira rok, tim, rizik i evoluciju sistema.",
                        options = listOf(
                            """
                            A. Multi-tenant modularni SaaS sa zajedničkom aplikacijom i logičkom izolacijom tenant-a
                            Platforma koristi jednu aplikacionu instancu i zajedničku bazu ili zajednički klaster, ali svaki podatak je jasno vezan za tenant kontekst, uz strogu kontrolu pristupa, tenant-aware upite, audit i mogućnost kasnijeg izdvajanja velikih klijenata.
                            """.trimIndent(),
                            """
                            B. Posebna aplikacija i posebna baza za svakog klijenta od prvog dana
                            Za svaku organizaciju se deploy-uje posebna kopija aplikacije i posebna baza, čime se postiže jaka izolacija, ali onboarding, održavanje, migracije i monitoring postaju znatno složeniji.
                            """.trimIndent(),
                            """
                            C. Jedna zajednička aplikacija i jedna zajednička baza bez tenant granica
                            Svi korisnici, kursevi, rezultati i izveštaji čuvaju se zajedno, a razdvajanje organizacija rešava se kroz filtere na frontendu.
                            """.trimIndent(),
                            """
                            D. Mikroservisna arhitektura po funkcionalnosti i po klijentu od prvog dana
                            Za svaki modul i za svakog većeg klijenta prave se posebni servisi, kako bi se odmah omogućila maksimalna fleksibilnost.
                            """.trimIndent(),
                            """
                            E. Potpuno custom rešenje za svakog klijenta
                            Za svakog klijenta pravi se posebna varijanta platforme, sa različitim kodom, posebnim pravilima i posebnim deployment-om.
                            """.trimIndent()
                        ),
                        correctAnswer = """
                    A. Multi-tenant modularni SaaS sa zajedničkom aplikacijom i logičkom izolacijom tenant-a
                    Platforma koristi jednu aplikacionu instancu i zajedničku bazu ili zajednički klaster, ali svaki podatak je jasno vezan za tenant kontekst, uz strogu kontrolu pristupa, tenant-aware upite, audit i mogućnost kasnijeg izdvajanja velikih klijenata.
                    """.trimIndent()
                    ),
                    ArchitectSeedBuilders.singleChoiceStep(
                        questionId = "A6.2",
                        stepNumber = 2,
                        title = """
                    Pritisak 1 — Prodaja želi brzo uključivanje novih klijenata
                    """.trimIndent(),
                        instruction = "Izaberi najbolji odgovor arhitekte na ovaj pritisak.",
                        options = listOf(
                            """
                            A. Za svakog klijenta treba napraviti posebnu aplikaciju, jer je to najčistiji način da se izbegne mešanje podataka.
                            """.trimIndent(),
                            """
                            B. Platforma treba da podrži tenant konfiguraciju, gde se novi klijent otvara kroz podešavanja, role, branding i inicijalne podatke, bez posebnog deployment-a.
                            """.trimIndent(),
                            "C. Novi klijent treba da koristi postojeći demo nalog dok ne dobije sopstvenu verziju sistema.",
                            "D. Klijenti treba da se razdvajaju samo po nazivu kompanije prikazanom u interfejsu."
                        ),
                        correctAnswer = """
                    B. Platforma treba da podrži tenant konfiguraciju, gde se novi klijent otvara kroz podešavanja, role, branding i inicijalne podatke, bez posebnog deployment-a.
                    """.trimIndent()
                    ),
                    ArchitectSeedBuilders.singleChoiceStep(
                        questionId = "A6.2",
                        stepNumber = 3,
                        title = """
                    Pritisak 2 — Bezbednost upozorava na mešanje podataka
                    """.trimIndent(),
                        instruction = "Izaberi najbolji odgovor arhitekte na ovaj pritisak.",
                        options = listOf(
                            """
                            A. Dovoljno je sakriti tuđe podatke na frontendu, jer korisnik svakako neće znati ID druge organizacije.
                            """.trimIndent(),
                            """
                            B. Svaki upit, akcija i izveštaj moraju biti tenant-aware, uz proveru tenant konteksta na backend-u, audit i testove koji proveravaju izolaciju podataka.
                            """.trimIndent(),
                            """
                            C. Problem se može rešiti tako što se svim klijentima daju različiti URL-ovi, čak i ako backend ne proverava tenant izolaciju.
                            """.trimIndent(),
                            "D. Najbolje je da svi administratori vide sve podatke, pa da se ugovorom reguliše poverljivost."
                        ),
                        correctAnswer = """
                    B. Svaki upit, akcija i izveštaj moraju biti tenant-aware, uz proveru tenant konteksta na backend-u, audit i testove koji proveravaju izolaciju podataka.
                    """.trimIndent()
                    ),
                    ArchitectSeedBuilders.singleChoiceStep(
                        questionId = "A6.2",
                        stepNumber = 4,
                        title = """
                    Pritisak 3 — Jedan veliki klijent traži posebne performanse
                    """.trimIndent(),
                        instruction = "Izaberi najbolji odgovor arhitekte na ovaj pritisak.",
                        options = listOf(
                            "A. Odmah treba sve klijente prebaciti na posebne baze i posebne aplikacije.",
                            """
                            B. Početna arhitektura treba da ostane multi-tenant, ali treba predvideti jasne granice, metrike i mogućnost kasnijeg izdvajanja velikog tenant-a ako opterećenje ili ugovor to opravdaju.
                            """.trimIndent(),
                            "C. Velikom klijentu treba zabraniti korišćenje izveštaja kako ne bi opteretio sistem.",
                            """
                            D. Sistem treba optimizovati samo za najvećeg klijenta, čak i ako se time uspori onboarding manjih klijenata.
                            """.trimIndent()
                        ),
                        correctAnswer = """
                    B. Početna arhitektura treba da ostane multi-tenant, ali treba predvideti jasne granice, metrike i mogućnost kasnijeg izdvajanja velikog tenant-a ako opterećenje ili ugovor to opravdaju.
                    """.trimIndent()
                    ),
                    ArchitectSeedBuilders.categoryStep(
                        questionId = "A6.2",
                        stepNumber = 5,
                        title = "Rasporedi odluke: sada / kasnije / nikako",
                        instruction = "Razvrstaj odluke prema tome šta treba uraditi odmah, šta ostaviti za kasnije, a šta ne treba raditi.",
                        categories = listOf(
                            ArchitectCategorySeed(
                                title = "Uraditi sada",
                                items = listOf(
                                    "uvesti obavezan tenant_id ili tenant kontekst u ključne entitete sistema",
                                    "proveravati tenant izolaciju na backend-u, ne samo na frontendu",
                                    "definisati role po tenant-u: owner, admin, instructor, employee",
                                    "omogućiti osnovni branding po klijentu kroz konfiguraciju",
                                    "uvesti audit za administrativne akcije i pristup osetljivim podacima"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Ostaviti za kasnije",
                                items = listOf(
                                    """
                                    razmatrati izdvajanje velikog tenant-a tek ako opterećenje, ugovor ili regulatorni zahtevi to opravdaju
                                    """.trimIndent()
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Ne raditi / pogrešan pravac",
                                items = listOf(
                                    "odmah napraviti posebnu aplikaciju i posebnu bazu za svakog klijenta",
                                    "razdvajati podatke samo kroz filtere u mobilnoj/web aplikaciji",
                                    "omogućiti administratoru jednog klijenta da pristupa podacima drugog klijenta radi “lakše podrške”",
                                    "AI preporuke obuka razviti pre tenant izolacije i osnovne kontrole pristupa"
                                )
                            )
                        )
                    ),
                    ArchitectSeedBuilders.categoryStep(
                        questionId = "A6.2",
                        stepNumber = 6,
                        title = "Prepoznaj signale za promenu odluke",
                        instruction = "Razvrstaj tvrdnje u prave signale, lažne signale i opasne signale koji traže oprez.",
                        categories = listOf(
                            ArchitectCategorySeed(
                                title = "Pravi signal za promenu arhitektonske odluke",
                                items = listOf(
                                    """
                                    jedan tenant pravi većinu opterećenja i zahteva nezavisno skaliranje ili poseban ugovorni nivo usluge
                                    """.trimIndent(),
                                    "regulatorni ili ugovorni zahtevi traže fizičku izolaciju podataka za određene klijente",
                                    """
                                    migracije šeme postaju rizične jer veliki klijent ima specifične zahteve koji ne odgovaraju zajedničkom modelu
                                    """.trimIndent()
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Lažni signal",
                                items = listOf(
                                    "prodaja želi da u prezentaciji kaže da svaki klijent ima “svoj sistem”",
                                    "jedan klijent traži drugačiju boju portala i logo na login strani",
                                    "broj klijenata je mali, svi koriste iste osnovne funkcionalnosti i opterećenje je stabilno"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Opasan signal koji traži oprez, ali ne nužno promenu arhitekture",
                                items = listOf(
                                    "zaposleni jednog klijenta slučajno vidi naziv kursa drugog klijenta u izveštaju",
                                    "podrška traži globalni administratorski nalog bez jasnog audit-a i ograničenja"
                                )
                            )
                        )
                    ),
                    ArchitectSeedBuilders.miniAdrStep(
                        questionId = "A6.2",
                        stepNumber = 7,
                        title = "Mini ADR obrazloženje",
                        instruction = "Popuni kratak arhitektonski zapis: odluka, razlog, prihvaćena cena i signal za promenu odluke."
                    )
                )
                ),
        SeedQuestion(
                    questionId = "A6.3",
                    level = LearningLevel.ARCHITECT.id,
                    type = QuestionType.ARCHITECTURAL_COMPROMISE.id,
                    title = "Offline-first mobilna aplikacija za terenske servisere",
                    prompt = """
                    Kompanija razvija mobilnu aplikaciju za terenske servisere koji održavaju industrijsku opremu kod klijenata.
                    Planirane funkcionalnosti:
                    • serviser dobija dnevnu listu radnih naloga;
                    • svaki nalog sadrži lokaciju, opis problema, opremu, istoriju intervencija i bezbednosne napomene;
                    • serviser na terenu unosi potrošene delove, fotografije, komentar i status intervencije;
                    • klijent potpisuje zapisnik na uređaju;
                    • aplikacija treba da radi i kada nema interneta;
                    • podaci se sinhronizuju kada se konekcija vrati;
                    • dispečeri u kancelariji prate status naloga;
                    • kasnije se planira optimizacija ruta i automatsko predlaganje rezervnih delova.
                    Ograničenja:
                    • serviseri često rade u halama, podrumima i udaljenim lokacijama bez stabilne mreže;
                    • korisnik mora moći da završi intervenciju i bez interneta;
                    • neke promene mogu nastati istovremeno: dispečer promeni nalog dok je serviser offline;
                    • fotografije i potpisi ne smeju se izgubiti;
                    • sistem mora jasno prikazati šta je sinhronizovano, šta čeka slanje i gde postoji konflikt;
                    • backend tim je mali i ne može odmah izgraditi kompleksan event-sourcing sistem;
                    • poslovanje ne može zavisiti od toga da mreža uvek radi.
                    """.trimIndent(),
                    aiFollowUp = """
                    Ako serviser offline označi nalog kao završen i klijent potpiše zapisnik, ali je dispečer u međuvremenu promenio prioritet, opis problema ili dodelu naloga, kako bi dizajnirao statusni model i sinhronizaciju da se ne izgubi rad servisera, ali da backend ne prihvati nekonzistentno stanje?
                    """.trimIndent(),
                    wave = 3,
                    difficulty = "expert",
                    orderIndex = 18,
                    estimatedMinutes = 10,
                    steps = listOf(
                    ArchitectSeedBuilders.singleChoiceStep(
                        questionId = "A6.3",
                        stepNumber = 1,
                        title = "Izaberi početnu arhitektonsku odluku",
                        instruction = "Izaberi odluku koja najbolje balansira rok, tim, rizik i evoluciju sistema.",
                        options = listOf(
                            """
                            A. Offline-first mobilna arhitektura sa lokalnim skladištem, redom promena i kontrolisanom sinhronizacijom
                            Mobilna aplikacija lokalno čuva radne naloge, izmene, fotografije i potpise, omogućava rad bez interneta i kasnije sinhronizuje promene sa backend-om kroz jasan status, retry mehanizam i pravila rešavanja konflikata.
                            """.trimIndent(),
                            """
                            B. Online-only mobilna aplikacija koja stalno poziva backend
                            Aplikacija prikazuje i menja podatke samo dok postoji internet konekcija, jer se time izbegava složenost lokalnog skladištenja i konflikata.
                            """.trimIndent(),
                            """
                            C. Potpuno lokalna aplikacija bez centralne sinhronizacije
                            Serviser sve podatke čuva na telefonu, a izveštaji se kasnije izvoze ručno kao PDF ili Excel fajl.
                            """.trimIndent(),
                            """
                            D. Event-sourcing i CQRS za ceo sistem od prvog dana
                            Svaka promena u sistemu se modeluje kao događaj, sa posebnim projekcijama za mobilnu aplikaciju, dispečere, izveštaje i analitiku.
                            """.trimIndent(),
                            """
                            E. Chat aplikacija kao osnovni kanal rada
                            Serviseri šalju slike i statuse kroz grupni chat, a administracija kasnije ručno ažurira radne naloge u sistemu.
                            """.trimIndent()
                        ),
                        correctAnswer = """
                    A. Offline-first mobilna arhitektura sa lokalnim skladištem, redom promena i kontrolisanom sinhronizacijom
                    Mobilna aplikacija lokalno čuva radne naloge, izmene, fotografije i potpise, omogućava rad bez interneta i kasnije sinhronizuje promene sa backend-om kroz jasan status, retry mehanizam i pravila rešavanja konflikata.
                    """.trimIndent()
                    ),
                    ArchitectSeedBuilders.singleChoiceStep(
                        questionId = "A6.3",
                        stepNumber = 2,
                        title = """
                    Pritisak 1 — Biznis kaže da aplikacija mora raditi bez mreže
                    """.trimIndent(),
                        instruction = "Izaberi najbolji odgovor arhitekte na ovaj pritisak.",
                        options = listOf(
                            """
                            A. Aplikacija treba da blokira završetak naloga dok se ne pojavi internet, jer je centralna baza uvek najtačnija.
                            """.trimIndent(),
                            """
                            B. Aplikacija treba lokalno da omogući pregled naloga, unos rada, fotografija, potpisa i završetak intervencije, uz jasno označavanje da podaci čekaju sinhronizaciju.
                            """.trimIndent(),
                            "C. Serviser treba da zapiše podatke na papir, pa ih kasnije unese u sistem.",
                            "D. Aplikacija treba da radi samo u gradovima gde postoji dobra mrežna pokrivenost."
                        ),
                        correctAnswer = """
                    B. Aplikacija treba lokalno da omogući pregled naloga, unos rada, fotografija, potpisa i završetak intervencije, uz jasno označavanje da podaci čekaju sinhronizaciju.
                    """.trimIndent()
                    ),
                    ArchitectSeedBuilders.singleChoiceStep(
                        questionId = "A6.3",
                        stepNumber = 3,
                        title = """
                    Pritisak 2 — Tehnika želi da izbegne konflikte
                    """.trimIndent(),
                        instruction = "Izaberi najbolji odgovor arhitekte na ovaj pritisak.",
                        options = listOf(
                            "A. Offline rad treba zabraniti jer su konflikti nemogući za rešavanje.",
                            """
                            B. Treba definisati koja polja serviser sme menjati offline, koja polja su autoritativna na backend-u i kako se konflikt prikazuje ili rešava pri sinhronizaciji.
                            """.trimIndent(),
                            "C. Uvek treba prihvatiti poslednju pristiglu promenu, bez obzira ko ju je napravio i šta menja.",
                            "D. Konflikte treba sakriti od korisnika i automatski prepisati backend stanje lokalnim stanjem."
                        ),
                        correctAnswer = """
                    B. Treba definisati koja polja serviser sme menjati offline, koja polja su autoritativna na backend-u i kako se konflikt prikazuje ili rešava pri sinhronizaciji.
                    """.trimIndent()
                    ),
                    ArchitectSeedBuilders.singleChoiceStep(
                        questionId = "A6.3",
                        stepNumber = 4,
                        title = """
                    Pritisak 3 — Menadžment želi naprednu optimizaciju ruta odmah
                    """.trimIndent(),
                        instruction = "Izaberi najbolji odgovor arhitekte na ovaj pritisak.",
                        options = listOf(
                            """
                            A. Optimizacija ruta i preporuka delova treba da budu prvi korak, jer su najatraktivnije funkcionalnosti.
                            """.trimIndent(),
                            """
                            B. Prvo treba stabilizovati osnovni offline tok radnog naloga, lokalno čuvanje, sinhronizaciju, fotografije, potpis i status konflikta; optimizacija ruta može doći kasnije.
                            """.trimIndent(),
                            "C. Optimizacija ruta treba da zameni dispečere već u prvoj verziji.",
                            "D. Predlog rezervnih delova treba da odlučuje koji radni nalog je validan."
                        ),
                        correctAnswer = """
                    B. Prvo treba stabilizovati osnovni offline tok radnog naloga, lokalno čuvanje, sinhronizaciju, fotografije, potpis i status konflikta; optimizacija ruta može doći kasnije.
                    """.trimIndent()
                    ),
                    ArchitectSeedBuilders.categoryStep(
                        questionId = "A6.3",
                        stepNumber = 5,
                        title = "Rasporedi odluke: sada / kasnije / nikako",
                        instruction = "Razvrstaj odluke prema tome šta treba uraditi odmah, šta ostaviti za kasnije, a šta ne treba raditi.",
                        categories = listOf(
                            ArchitectCategorySeed(
                                title = "Uraditi sada",
                                items = listOf(
                                    "lokalno čuvati preuzete radne naloge potrebne za dnevni rad servisera",
                                    "omogućiti unos komentara, potrošenih delova, fotografija i potpisa bez interneta",
                                    "uvesti red lokalnih promena koje čekaju sinhronizaciju",
                                    "jasno prikazati statuse: sinhronizovano, čeka slanje, greška, konflikt",
                                    "definisati pravila konflikta između promene dispečera i promene servisera"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Ostaviti za kasnije",
                                items = listOf(
                                    "optimizaciju ruta razviti nakon stabilizacije osnovnog offline toka",
                                    "automatsko predlaganje rezervnih delova uvesti nakon prikupljanja dovoljno pouzdanih podataka"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Ne raditi / pogrešan pravac",
                                items = listOf(
                                    "zabraniti završetak intervencije ako uređaj trenutno nema internet",
                                    "fotografije i potpise čuvati samo u memoriji aplikacije dok se ne pošalju backend-u",
                                    "uvek automatski prihvatiti poslednju promenu bez prikaza konflikta"
                                )
                            )
                        )
                    ),
                    ArchitectSeedBuilders.categoryStep(
                        questionId = "A6.3",
                        stepNumber = 6,
                        title = "Prepoznaj signale za promenu odluke",
                        instruction = "Razvrstaj tvrdnje u prave signale, lažne signale i opasne signale koji traže oprez.",
                        categories = listOf(
                            ArchitectCategorySeed(
                                title = "Pravi signal za promenu arhitektonske odluke",
                                items = listOf(
                                    """
                                    lokalni red promena postaje toliko složen da zahteva poseban sinhronizacioni servis, verzionisanje i napredni conflict-resolution model
                                    """.trimIndent(),
                                    """
                                    konflikti između dispečera i servisera postaju česti i zahtevaju precizniji model verzionisanja ili statusa naloga
                                    """.trimIndent(),
                                    "količina fotografija i potpisa raste toliko da je potreban poseban upload, retry i storage tok"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Lažni signal",
                                items = listOf(
                                    "menadžment želi moderniji izgled ekrana radnog naloga",
                                    "jedan serviser traži tamnu temu aplikacije",
                                    """
                                    mreža je stabilna u kancelariji, pa neko predlaže uklanjanje offline podrške za sve terenske korisnike
                                    """.trimIndent()
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Opasan signal koji traži oprez, ali ne nužno promenu arhitekture",
                                items = listOf(
                                    """
                                    aplikacija ponekad prikaže da je nalog završen, ali backend kasnije odbije sinhronizaciju zbog konflikta
                                    """.trimIndent(),
                                    "korisnici ne razumeju razliku između “završeno lokalno” i “potvrđeno na serveru”"
                                )
                            )
                        )
                    ),
                    ArchitectSeedBuilders.miniAdrStep(
                        questionId = "A6.3",
                        stepNumber = 7,
                        title = "Mini ADR obrazloženje",
                        instruction = "Popuni kratak arhitektonski zapis: odluka, razlog, prihvaćena cena i signal za promenu odluke."
                    )
                )
                ),
        SeedQuestion(
                    questionId = "A6.4",
                    level = LearningLevel.ARCHITECT.id,
                    type = QuestionType.ARCHITECTURAL_COMPROMISE.id,
                    title = "Event-driven arhitektura za sistem hitnih obaveštenja tokom kriznih situacija",
                    prompt = """
                    Grad razvija platformu za hitna obaveštenja građana tokom kriznih situacija: poplave, požari, nestanci vode, zatvaranje puteva, ekstremne vremenske prilike i evakuacije.
                    Planirane funkcionalnosti:
                    • nadležne službe kreiraju hitna obaveštenja;
                    • građani dobijaju poruke preko mobilne aplikacije, SMS-a, email-a i web portala;
                    • obaveštenja mogu biti vezana za određenu lokaciju ili zonu;
                    • neka obaveštenja su informativna, a neka zahtevaju hitnu reakciju;
                    • sistem mora podržati veliki skok opterećenja tokom krize;
                    • poruke ne smeju biti izgubljene;
                    • neke poruke moraju imati prioritet nad drugim porukama;
                    • službe moraju videti status isporuke i greške po kanalima;
                    • kasnije se planira automatsko generisanje predloga obaveštenja na osnovu senzora i vremenskih podataka.
                    Ograničenja:
                    • u normalnom radu sistem ima malo opterećenje;
                    • tokom krize broj poruka može naglo porasti;
                    • SMS provajder može kasniti ili privremeno biti nedostupan;
                    • mobilne push notifikacije nisu uvek garantovane;
                    • isti građanin ne sme dobiti deset dupliranih poruka za istu kriznu situaciju;
                    • najkritičnije poruke moraju imati prioritet;
                    • tim nema kapacitet da razvije potpuno kompleksnu streaming platformu od prvog dana;
                    • sistem mora jasno razlikovati kreiranje obaveštenja, pokušaj isporuke i potvrdu isporuke.
                    """.trimIndent(),
                    aiFollowUp = """
                    Ako se tokom poplave istovremeno šalju informativne poruke, upozorenja i hitne evakuacione poruke, kako bi dizajnirao prioritete, redove, retry politiku i deduplikaciju da najvažnije poruke ne čekaju iza manje važnih, ali da građani ne budu zatrpani duplikatima?

                    ARHITEKTA — TALAS 5
                    """.trimIndent(),
                    wave = 4,
                    difficulty = "expert",
                    orderIndex = 24,
                    estimatedMinutes = 10,
                    steps = listOf(
                    ArchitectSeedBuilders.singleChoiceStep(
                        questionId = "A6.4",
                        stepNumber = 1,
                        title = "Izaberi početnu arhitektonsku odluku",
                        instruction = "Izaberi odluku koja najbolje balansira rok, tim, rizik i evoluciju sistema.",
                        options = listOf(
                            """
                            A. Event-driven arhitektura sa centralnim tokom obaveštenja, prioritetnim redovima i kanalnim adapterima
                            Obaveštenje se prvo validira i upisuje kao poslovni događaj, zatim se kroz redove poruka prosleđuje kanalnim adapterima za SMS, push, email i web, uz retry, deduplikaciju, prioritete i praćenje statusa isporuke.
                            """.trimIndent(),
                            """
                            B. Sinhroni monolit koji odmah šalje sve poruke kroz sve kanale
                            Kada službenik klikne “pošalji”, aplikacija direktno poziva SMS, push i email provajdere u istom zahtevu i korisniku prikazuje uspeh tek kada svi kanali odgovore.
                            """.trimIndent(),
                            """
                            C. Mobilna push notifikacija kao jedini kanal
                            Sistem koristi samo push notifikacije jer su najjeftinije i najbrže, a ostali kanali se izbegavaju radi jednostavnosti.
                            """.trimIndent(),
                            """
                            D. Batch sistem koji šalje poruke jednom na svakih 30 minuta
                            Sva obaveštenja se skupljaju u batch i periodično šalju građanima kako bi sistem bio jednostavniji i manje opterećen.
                            """.trimIndent(),
                            """
                            E. AI-first sistem koji automatski generiše i šalje krizna obaveštenja
                            Sistem odmah koristi AI za prepoznavanje krize, generisanje poruke i automatsko slanje bez obavezne ljudske potvrde.
                            """.trimIndent()
                        ),
                        correctAnswer = """
                    A. Event-driven arhitektura sa centralnim tokom obaveštenja, prioritetnim redovima i kanalnim adapterima
                    Obaveštenje se prvo validira i upisuje kao poslovni događaj, zatim se kroz redove poruka prosleđuje kanalnim adapterima za SMS, push, email i web, uz retry, deduplikaciju, prioritete i praćenje statusa isporuke.
                    """.trimIndent()
                    ),
                    ArchitectSeedBuilders.singleChoiceStep(
                        questionId = "A6.4",
                        stepNumber = 2,
                        title = """
                    Pritisak 1 — Krizne poruke ne smeju čekati spore kanale
                    """.trimIndent(),
                        instruction = "Izaberi najbolji odgovor arhitekte na ovaj pritisak.",
                        options = listOf(
                            """
                            A. Sve kanale treba pozivati sinhrono, jer je jedino tako moguće znati da je obaveštenje zaista poslato.
                            """.trimIndent(),
                            """
                            B. Obaveštenje treba razdvojiti od isporuke po kanalima: kreiranje događaja je jedno, a slanje kroz SMS, push, email i web adaptere ide asinhrono, sa statusima i ponovnim pokušajima.
                            """.trimIndent(),
                            "C. Ako SMS kasni, treba zaustaviti i ostale kanale da bi svi građani dobili poruku u isto vreme.",
                            "D. Treba koristiti samo email, jer je najlakše pratiti status isporuke."
                        ),
                        correctAnswer = """
                    B. Obaveštenje treba razdvojiti od isporuke po kanalima: kreiranje događaja je jedno, a slanje kroz SMS, push, email i web adaptere ide asinhrono, sa statusima i ponovnim pokušajima.
                    """.trimIndent()
                    ),
                    ArchitectSeedBuilders.singleChoiceStep(
                        questionId = "A6.4",
                        stepNumber = 3,
                        title = """
                    Pritisak 2 — Sistem mora izdržati nagli skok opterećenja
                    """.trimIndent(),
                        instruction = "Izaberi najbolji odgovor arhitekte na ovaj pritisak.",
                        options = listOf(
                            """
                            A. Sistem treba da šalje svaku poruku direktno iz korisničkog zahteva službenika, jer je to najjednostavnije.
                            """.trimIndent(),
                            """
                            B. Treba koristiti redove poruka, kontrolisan throughput, prioritete i mogućnost horizontalnog skaliranja worker-a koji šalju poruke po kanalima.
                            """.trimIndent(),
                            "C. Treba ograničiti broj građana koji mogu dobiti hitnu poruku kako sistem ne bi bio preopterećen.",
                            "D. Treba ručno izvoziti brojeve telefona i slati poruke iz eksternog alata."
                        ),
                        correctAnswer = """
                    B. Treba koristiti redove poruka, kontrolisan throughput, prioritete i mogućnost horizontalnog skaliranja worker-a koji šalju poruke po kanalima.
                    """.trimIndent()
                    ),
                    ArchitectSeedBuilders.singleChoiceStep(
                        questionId = "A6.4",
                        stepNumber = 4,
                        title = """
                    Pritisak 3 — Menadžment želi AI automatsko slanje odmah
                    """.trimIndent(),
                        instruction = "Izaberi najbolji odgovor arhitekte na ovaj pritisak.",
                        options = listOf(
                            """
                            A. AI treba odmah da dobije pravo da šalje krizna obaveštenja, jer brzina ima prednost nad kontrolom.
                            """.trimIndent(),
                            """
                            B. AI može kasnije pomagati u predlogu teksta ili detekciji rizika, ali u prvoj verziji mora postojati ljudska validacija, jasan audit i kontrolisan tok slanja.
                            """.trimIndent(),
                            "C. AI treba da zameni prioritete poruka, jer zna bolje od službi šta je hitno.",
                            "D. AI treba da šalje samo SMS poruke, jer su one najvažnije."
                        ),
                        correctAnswer = """
                    B. AI može kasnije pomagati u predlogu teksta ili detekciji rizika, ali u prvoj verziji mora postojati ljudska validacija, jasan audit i kontrolisan tok slanja.
                    """.trimIndent()
                    ),
                    ArchitectSeedBuilders.categoryStep(
                        questionId = "A6.4",
                        stepNumber = 5,
                        title = "Rasporedi odluke: sada / kasnije / nikako",
                        instruction = "Razvrstaj odluke prema tome šta treba uraditi odmah, šta ostaviti za kasnije, a šta ne treba raditi.",
                        categories = listOf(
                            ArchitectCategorySeed(
                                title = "Uraditi sada",
                                items = listOf(
                                    "obaveštenje prvo upisati kao validiran poslovni događaj pre pokušaja isporuke",
                                    "uvesti odvojene kanalne adaptere za SMS, push, email i web",
                                    "podržati prioritete poruka za hitna, važna i informativna obaveštenja",
                                    "uvesti retry mehanizam i status isporuke po kanalu",
                                    "uvesti deduplikaciju da građanin ne dobije istu poruku više puta kroz isti kanal"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Ostaviti za kasnije",
                                items = listOf(
                                    "AI predloge teksta razmatrati nakon stabilizacije ljudski kontrolisanog toka slanja"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Ne raditi / pogrešan pravac",
                                items = listOf(
                                    "slati sve poruke sinhrono iz jednog korisničkog zahteva službenika",
                                    "zaustaviti sve kanale ako jedan provajder trenutno ne odgovara",
                                    "koristiti push notifikacije kao jedini kanal za hitna obaveštenja",
                                    "dozvoliti AI sistemu da automatski šalje evakuacione poruke bez ljudske potvrde u prvoj verziji"
                                )
                            )
                        )
                    ),
                    ArchitectSeedBuilders.categoryStep(
                        questionId = "A6.4",
                        stepNumber = 6,
                        title = "Prepoznaj signale za promenu odluke",
                        instruction = "Razvrstaj tvrdnje u prave signale, lažne signale i opasne signale koji traže oprez.",
                        categories = listOf(
                            ArchitectCategorySeed(
                                title = "Pravi signal za promenu arhitektonske odluke",
                                items = listOf(
                                    "SMS adapter često kasni i pravi veliki backlog, dok ostali kanali mogu normalno da rade",
                                    "hitne poruke čekaju iza informativnih poruka i stižu prekasno korisnicima",
                                    "isti građanin dobija više kopija istog obaveštenja zbog ponovnih pokušaja i više kanala"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Lažni signal",
                                items = listOf(
                                    "službe žele drugačiju boju oznake za hitna obaveštenja u administratorskom panelu",
                                    "građani traže lepši prikaz istorije obaveštenja u aplikaciji",
                                    "jedan provajder predlaže da se sve poruke šalju samo preko njegovog kanala"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Opasan signal koji traži oprez, ali ne nužno promenu arhitekture",
                                items = listOf(
                                    """
                                    operator slučajno pošalje test poruku građanima jer nije postojala jasna razlika između draft, test i live režima
                                    """.trimIndent(),
                                    "AI predlog teksta sadrži netačnu ili previše dramatičnu formulaciju krizne poruke"
                                )
                            )
                        )
                    ),
                    ArchitectSeedBuilders.miniAdrStep(
                        questionId = "A6.4",
                        stepNumber = 7,
                        title = "Mini ADR obrazloženje",
                        instruction = "Popuni kratak arhitektonski zapis: odluka, razlog, prihvaćena cena i signal za promenu odluke."
                    )
                )
                ),
        SeedQuestion(
                    questionId = "A6.5",
                    level = LearningLevel.ARCHITECT.id,
                    type = QuestionType.ARCHITECTURAL_COMPROMISE.id,
                    title = "Privacy-by-design arhitektura za AI personalizaciju u obrazovnoj aplikaciji",
                    prompt = """
                    Tim razvija obrazovnu mobilnu aplikaciju za srednjoškolce koja personalizuje zadatke iz programiranja i softverskog projektovanja.
                    Planirane funkcionalnosti:
                    • učenik rešava zadatke različitih težina;
                    • aplikacija prati pokušaje, greške, vreme rešavanja i oblasti u kojima učenik napreduje;
                    • sistem predlaže sledeći zadatak na osnovu znanja, prethodnih pokušaja i slabih tačaka;
                    • nastavnici mogu videti agregirani napredak odeljenja;
                    • učenik može videti sopstvenu istoriju rada;
                    • roditelji mogu dobiti osnovni pregled napretka ako škola to dozvoli;
                    • kasnije se planira AI tutor koji daje personalizovane savete;
                    • kasnije se planira analiza stilova učenja i predikcija rizika od odustajanja.
                    Ograničenja:
                    • korisnici su maloletna lica;
                    • aplikacija ne sme prikupljati više podataka nego što je potrebno za učenje;
                    • personalizacija mora raditi dovoljno dobro i bez invazivnog profilisanja;
                    • nastavnici ne treba da vide nepotrebno detaljne lične podatke;
                    • roditeljski uvid mora biti kontrolisan i ograničen;
                    • AI tutor ne sme davati neproverene zaključke o sposobnostima, ličnosti ili budućem uspehu učenika;
                    • podaci o učenju su osetljivi i moraju imati jasnu svrhu, rok čuvanja i kontrolu pristupa;
                    • tim želi brz MVP, ali ne sme naknadno “dodavati privatnost” kao zakrpu.
                    """.trimIndent(),
                    aiFollowUp = """
                    Ako AI tutor želi da koristi istoriju grešaka, vreme rešavanja, broj pokušaja i poređenje sa prosekom odeljenja da bi dao personalizovan savet učeniku, kako bi dizajnirao granice podataka, objašnjivost, pristup nastavnika i roditelja, tako da savet pomogne učenju, ali ne postane skriveno profilisanje maloletnog korisnika?
                    """.trimIndent(),
                    wave = 5,
                    difficulty = "expert",
                    orderIndex = 30,
                    estimatedMinutes = 10,
                    steps = listOf(
                    ArchitectSeedBuilders.singleChoiceStep(
                        questionId = "A6.5",
                        stepNumber = 1,
                        title = "Izaberi početnu arhitektonsku odluku",
                        instruction = "Izaberi odluku koja najbolje balansira rok, tim, rizik i evoluciju sistema.",
                        options = listOf(
                            """
                            A. Privacy-by-design modularna arhitektura sa minimalnim profilom učenika, kontrolisanim pristupom i odvojenim slojem za personalizaciju
                            Sistem prikuplja samo podatke potrebne za učenje, razdvaja identitet učenika od analitičkih signala gde je moguće, koristi agregirane prikaze za nastavnike i uvodi personalizaciju kroz kontrolisan sloj sa jasnim pravilima, audit-om, objašnjivošću i ograničenjima.
                            """.trimIndent(),
                            """
                            B. Maksimalno prikupljanje podataka radi bolje AI personalizacije
                            Aplikacija prikuplja sve moguće podatke o ponašanju učenika, uključujući detaljne obrasce korišćenja, emocionalne zaključke, poređenja sa vršnjacima i dugoročne profile, kako bi AI imao što više informacija.
                            """.trimIndent(),
                            """
                            C. Jednostavna aplikacija bez personalizacije i bez praćenja napretka
                            Radi zaštite privatnosti, sistem ne čuva pokušaje, rezultate niti istoriju rada, pa svaki učenik dobija iste zadatke bez prilagođavanja.
                            """.trimIndent(),
                            """
                            D. Nastavnički dashboard sa potpunim uvidom u sve aktivnosti svakog učenika
                            Nastavnici imaju pristup svim pokušajima, greškama, vremenu rada, komentarima, detaljnim događajima i AI procenama za svakog učenika bez posebnog ograničenja.
                            """.trimIndent(),
                            """
                            E. AI tutor kao centralni autoritativni sloj od prvog dana
                            AI tutor odmah odlučuje nivo učenika, slabosti, preporuke, rizik od neuspeha i komunikaciju sa nastavnicima i roditeljima, bez obaveznog ljudskog nadzora i jasne objašnjivosti.
                            """.trimIndent()
                        ),
                        correctAnswer = """
                    A. Privacy-by-design modularna arhitektura sa minimalnim profilom učenika, kontrolisanim pristupom i odvojenim slojem za personalizaciju
                    Sistem prikuplja samo podatke potrebne za učenje, razdvaja identitet učenika od analitičkih signala gde je moguće, koristi agregirane prikaze za nastavnike i uvodi personalizaciju kroz kontrolisan sloj sa jasnim pravilima, audit-om, objašnjivošću i ograničenjima.
                    """.trimIndent()
                    ),
                    ArchitectSeedBuilders.singleChoiceStep(
                        questionId = "A6.5",
                        stepNumber = 2,
                        title = """
                    Pritisak 1 — Product tim želi što više podataka za bolju personalizaciju
                    """.trimIndent(),
                        instruction = "Izaberi najbolji odgovor arhitekte na ovaj pritisak.",
                        options = listOf(
                            "A. Treba prikupiti što više podataka sada, jer se kasnije može odlučiti šta je korisno.",
                            """
                            B. Treba definisati minimalne signale potrebne za personalizaciju, svrhu svakog podatka i rok čuvanja, umesto prikupljanja podataka “za svaki slučaj”.
                            """.trimIndent(),
                            "C. Treba potpuno ukinuti praćenje napretka, jer je svaki podatak o učeniku prevelik rizik.",
                            """
                            D. Treba dozvoliti AI tutor-u da sam odluči koje podatke treba čuvati, jer će model najbolje znati šta mu je korisno.
                            """.trimIndent()
                        ),
                        correctAnswer = """
                    B. Treba definisati minimalne signale potrebne za personalizaciju, svrhu svakog podatka i rok čuvanja, umesto prikupljanja podataka “za svaki slučaj”.
                    """.trimIndent()
                    ),
                    ArchitectSeedBuilders.singleChoiceStep(
                        questionId = "A6.5",
                        stepNumber = 3,
                        title = """
                    Pritisak 2 — Nastavnici žele detaljan uvid u svakog učenika
                    """.trimIndent(),
                        instruction = "Izaberi najbolji odgovor arhitekte na ovaj pritisak.",
                        options = listOf(
                            "A. Nastavnicima treba dati sve podatke jer su odgovorni za nastavu.",
                            """
                            B. Nastavnici treba da vide pedagoški korisne uvide i agregate, uz ograničen detaljan uvid samo kada postoji jasna obrazovna svrha i odgovarajuća dozvola.
                            """.trimIndent(),
                            "C. Nastavnici ne treba da vide ništa, čak ni agregirani napredak odeljenja.",
                            "D. Nastavnici treba da vide i podatke drugih škola radi poređenja kvaliteta nastave."
                        ),
                        correctAnswer = """
                    B. Nastavnici treba da vide pedagoški korisne uvide i agregate, uz ograničen detaljan uvid samo kada postoji jasna obrazovna svrha i odgovarajuća dozvola.
                    """.trimIndent()
                    ),
                    ArchitectSeedBuilders.singleChoiceStep(
                        questionId = "A6.5",
                        stepNumber = 4,
                        title = """
                    Pritisak 3 — Menadžment želi AI tutor kao glavni prodajni argument
                    """.trimIndent(),
                        instruction = "Izaberi najbolji odgovor arhitekte na ovaj pritisak.",
                        options = listOf(
                            "A. AI tutor treba odmah da rangira učenike i javlja nastavnicima ko ima slab potencijal.",
                            """
                            B. AI tutor može kasnije davati pomoć u učenju, ali ne sme biti autoritativni izvor procena o sposobnosti učenika, posebno bez objašnjivosti, ograničenja i ljudskog nadzora.
                            """.trimIndent(),
                            "C. AI tutor treba da šalje roditeljima direktne psihološke zaključke o učeniku.",
                            "D. AI tutor treba da zameni nastavnika u proceni napretka, jer može analizirati više podataka."
                        ),
                        correctAnswer = """
                    B. AI tutor može kasnije davati pomoć u učenju, ali ne sme biti autoritativni izvor procena o sposobnosti učenika, posebno bez objašnjivosti, ograničenja i ljudskog nadzora.
                    """.trimIndent()
                    ),
                    ArchitectSeedBuilders.categoryStep(
                        questionId = "A6.5",
                        stepNumber = 5,
                        title = "Rasporedi odluke: sada / kasnije / nikako",
                        instruction = "Razvrstaj odluke prema tome šta treba uraditi odmah, šta ostaviti za kasnije, a šta ne treba raditi.",
                        categories = listOf(
                            ArchitectCategorySeed(
                                title = "Uraditi sada",
                                items = listOf(
                                    "definisati minimalne signale učenja potrebne za preporuku sledećeg zadatka",
                                    "odvojiti identitet učenika od analitičkih signala gde god je moguće",
                                    "uvesti role i dozvole za učenika, nastavnika, školu i roditelja",
                                    "nastavnicima prikazivati agregirane i pedagoški korisne uvide, ne sirove detalje bez potrebe",
                                    "dokumentovati svrhu, pristup i rok čuvanja podataka o učenju",
                                    "uvesti audit pristupa osetljivim podacima i promenama dozvola"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Ostaviti za kasnije",
                                items = listOf(
                                    "AI tutor uvesti nakon stabilizacije osnovne personalizacije, ograničenja i nadzora",
                                    """
                                    predikciju rizika od odustajanja razmatrati tek kada postoji jasna pedagoška svrha, objašnjivost i kontrola pristupa
                                    """.trimIndent()
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Ne raditi / pogrešan pravac",
                                items = listOf(
                                    "prikupljati sve moguće podatke o ponašanju učenika jer će možda zatrebati kasnije",
                                    "čuvati detaljne događaje zauvek jer skladište trenutno nije skupo",
                                    "rangirati učenike javno po “talentu” na osnovu AI procene",
                                    "roditeljima automatski slati detaljne greške i vreme rešavanja bez kontrole škole i učenika",
                                    "dozvoliti nastavnicima iz jedne škole da vide podatke učenika druge škole radi poređenja"
                                )
                            )
                        )
                    ),
                    ArchitectSeedBuilders.categoryStep(
                        questionId = "A6.5",
                        stepNumber = 6,
                        title = "Prepoznaj signale za promenu odluke",
                        instruction = "Razvrstaj tvrdnje u prave signale, lažne signale i opasne signale koji traže oprez.",
                        categories = listOf(
                            ArchitectCategorySeed(
                                title = "Pravi signal za promenu arhitektonske odluke",
                                items = listOf(
                                    """
                                    osnovni signali učenja nisu dovoljni, pa je potrebno preispitati model personalizacije i dodati samo pedagoški opravdane signale
                                    """.trimIndent(),
                                    "škole traže različite režime pristupa za nastavnike, roditelje i administratore",
                                    "AI tutor počinje da koristi podatke ili zaključke koji nisu jasno povezani sa obrazovnom svrhom"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Lažni signal",
                                items = listOf(
                                    "učenici traže drugačije ikonice za oblasti zadataka",
                                    "nastavnici žele tamnu temu dashboard-a",
                                    "product tim želi drugačiji raspored kartica u prikazu preporučenih zadataka"
                                )
                            ),
                            ArchitectCategorySeed(
                                title = "Opasan signal koji traži oprez, ali ne nužno promenu arhitekture",
                                items = listOf(
                                    "marketing želi da prikaže “najtalentovanije učenike” kao javnu rang-listu",
                                    "roditelji pogrešno tumače preporuku zadataka kao konačnu procenu sposobnosti deteta",
                                    "tim predlaže da se svi detaljni događaji čuvaju zauvek jer skladište trenutno nije skupo"
                                )
                            )
                        )
                    ),
                    ArchitectSeedBuilders.miniAdrStep(
                        questionId = "A6.5",
                        stepNumber = 7,
                        title = "Mini ADR obrazloženje",
                        instruction = "Popuni kratak arhitektonski zapis: odluka, razlog, prihvaćena cena i signal za promenu odluke."
                    )
                )
                )
    )
}
