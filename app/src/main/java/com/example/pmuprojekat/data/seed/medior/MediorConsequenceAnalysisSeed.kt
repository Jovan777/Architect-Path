package com.example.pmuprojekat.data.seed.medior

import com.example.pmuprojekat.data.seed.SeedQuestion

object MediorConsequenceAnalysisSeed {
    val questions: List<SeedQuestion> = listOf(
        MediorSeedBuilders.consequenceQuestion(
            questionId = "M5.1",
            title = "Analiziraj posledice prelaska na događajnu komunikaciju",
            prompt = "Sistem porudžbina je promenjen tako da centralni modul emituje događaj, a ostali moduli reaguju preko event bus-a.",
            consequenceStep = MediorChoiceStepSeed(
                title = "Korak 1 - Izaberi najrelevantnije posledice",
                instruction = "Izaberi sve tačne odgovore.",
                options = listOf(
                    "manja direktna povezanost između emitera i potrošača",
                    "lakše dodavanje novih reakcija bez izmene emitera",
                    "složenije praćenje toka izvršavanja kroz sistem",
                    "moguće asinhrono kašnjenje pojedinih reakcija",
                    "automatski manji broj tabela u bazi",
                    "veća potreba za dobrim monitoringom i tracing-om",
                    "garantovano jednostavnije debagovanje u svim slučajevima",
                    "smanjenje potrebe za definisanjem ugovora događaja"
                ),
                correctAnswers = listOf(
                    "manja direktna povezanost između emitera i potrošača",
                    "lakše dodavanje novih reakcija bez izmene emitera",
                    "složenije praćenje toka izvršavanja kroz sistem",
                    "moguće asinhrono kašnjenje pojedinih reakcija",
                    "veća potreba za dobrim monitoringom i tracing-om"
                )
            ),
            categories = listOf(
                MediorCategorySeed(
                    title = "Prednosti",
                    items = listOf(
                        "manja direktna povezanost između emitera i potrošača",
                        "lakše dodavanje novih reakcija bez izmene emitera"
                    )
                ),
                MediorCategorySeed(
                    title = "Ograničenja / rizici",
                    items = listOf(
                        "složenije praćenje toka izvršavanja kroz sistem",
                        "moguće asinhrono kašnjenje pojedinih reakcija",
                        "veća potreba za dobrim monitoringom i tracing-om"
                    )
                )
            ),
            aiFollowUp = "Koja bi komponenta operativnog okruženja najviše dobila na važnosti nakon ovakve promene i zašto?",
            wave = 1,
            orderIndex = 9
        ),

        MediorSeedBuilders.consequenceQuestion(
            questionId = "M5.2",
            title = "Analiziraj posledice zamene velikog konstruktora Builder pristupom",
            prompt = "Tim je odlučio da složen objekat Report više ne pravi kroz konstruktor sa mnogo parametara, već kroz Builder.",
            consequenceStep = MediorChoiceStepSeed(
                title = "Korak 1 - Izaberi najrelevantnije posledice",
                instruction = "Izaberi sve tačne odgovore.",
                options = listOf(
                    "veća čitljivost procesa konstrukcije",
                    "lakše kreiranje više varijanti istog objekta",
                    "bolja kontrola opcionih delova",
                    "povećanje broja pomoćnih klasa ili interfejsa",
                    "automatsko rešavanje performansi prilikom eksportovanja",
                    "manja verovatnoća pogrešnog redosleda prosleđivanja parametara",
                    "garantovano manji broj linija koda u svakom projektu",
                    "lakše održavanje kada se objekat dalje komplikuje"
                ),
                correctAnswers = listOf(
                    "veća čitljivost procesa konstrukcije",
                    "lakše kreiranje više varijanti istog objekta",
                    "bolja kontrola opcionih delova",
                    "povećanje broja pomoćnih klasa ili interfejsa",
                    "manja verovatnoća pogrešnog redosleda prosleđivanja parametara",
                    "lakše održavanje kada se objekat dalje komplikuje"
                )
            ),
            categories = listOf(
                MediorCategorySeed(
                    title = "Prednosti",
                    items = listOf(
                        "veća čitljivost procesa konstrukcije",
                        "lakše kreiranje više varijanti istog objekta",
                        "bolja kontrola opcionih delova",
                        "manja verovatnoća pogrešnog redosleda prosleđivanja parametara",
                        "lakše održavanje kada se objekat dalje komplikuje"
                    )
                ),
                MediorCategorySeed(
                    title = "Ograničenja / rizici",
                    items = listOf(
                        "povećanje broja pomoćnih klasa ili interfejsa"
                    )
                )
            ),
            aiFollowUp = """
            U kom tipu sistema povećanje broja pomoćnih klasa možda ne bi bilo vredno koristi koje Builder donosi?

            1. Prvi tip zadatka: Sequence Logic i izbor pristupa
            """.trimIndent(),
            wave = 1,
            orderIndex = 10
        ),

        MediorSeedBuilders.consequenceQuestion(
            questionId = "M5.3",
            title = "Analiziraj posledice razdvajanja odgovornosti u reporting modulu",
            prompt = """
            Tim je promenio reporting modul tako da:
            •	logika “šta je izveštaj” više nije spojena sa logikom “kako se prikazuje”, 
            •	novi renderer može da se uvede bez diranja postojećih vrsta izveštaja, 
            •	novi tip izveštaja može da koristi postojeće renderere.
            """.trimIndent(),
            consequenceStep = MediorChoiceStepSeed(
                title = "Korak 1 - Izaberi najrelevantnije posledice",
                instruction = "Izaberi sve tačne odgovore.",
                options = listOf(
                    "lakše raspoređivanje odgovornosti između domena izveštaja i tehničkog prikaza",
                    "manji pritisak da se za svaku novu kombinaciju uvodi posebna klasa",
                    "potreba da tim preciznije definiše granicu između poslovne logike i prikaza",
                    "veća verovatnoća da novi član tima u početku sporije razume dizajn",
                    "automatsko rešavanje integracije sa starim bibliotekama koje imaju nekompatibilan interfejs",
                    "lakše paralelno razvijanje različitih renderer-a i različitih vrsta izveštaja",
                    "garantovano manja dubina poziva u izvozu izveštaja",
                    "veći značaj dobrog imenovanja apstrakcija i odgovornosti"
                ),
                correctAnswers = listOf(
                    "lakše raspoređivanje odgovornosti između domena izveštaja i tehničkog prikaza",
                    "manji pritisak da se za svaku novu kombinaciju uvodi posebna klasa",
                    "potreba da tim preciznije definiše granicu između poslovne logike i prikaza",
                    "veća verovatnoća da novi član tima u početku sporije razume dizajn",
                    "lakše paralelno razvijanje različitih renderer-a i različitih vrsta izveštaja",
                    "veći značaj dobrog imenovanja apstrakcija i odgovornosti"
                )
            ),
            categories = listOf(
                MediorCategorySeed(
                    title = "Dobici za organizaciju dizajna",
                    items = listOf(
                        "lakše raspoređivanje odgovornosti između domena izveštaja i tehničkog prikaza",
                        "manji pritisak da se za svaku novu kombinaciju uvodi posebna klasa",
                        "lakše paralelno razvijanje različitih renderer-a i različitih vrsta izveštaja"
                    )
                ),
                MediorCategorySeed(
                    title = "Cena / složenost dizajna",
                    items = listOf(
                        "potreba da tim preciznije definiše granicu između poslovne logike i prikaza",
                        "veća verovatnoća da novi član tima u početku sporije razume dizajn",
                        "veći značaj dobrog imenovanja apstrakcija i odgovornosti"
                    )
                )
            ),
            aiFollowUp = "Šta je veći rizik u ovom dizajnu: “više klasa” ili “loše postavljena granica odgovornosti” — i zašto?",
            wave = 2,
            orderIndex = 19
        ),

        MediorSeedBuilders.consequenceQuestion(
            questionId = "M5.4",
            title = "Analiziraj posledice uvođenja wrapper pristupa u content pipeline",
            prompt = """
            Tim je odlučio da dodatne obrade sadržaja, kao što su:
            •	audit zapis, 
            •	retry logika, 
            •	kompresija, 
            •	enkripcija, 
            ne uvodi kroz centralnu klasu sa flag-ovima, već kroz više omotača oko osnovnog procesora.
            """.trimIndent(),
            consequenceStep = MediorChoiceStepSeed(
                title = "Korak 1 - Izaberi najrelevantnije posledice",
                instruction = "Izaberi sve tačne odgovore.",
                options = listOf(
                    "nova obrada može da se doda bez promene osnovnog procesora",
                    "različiti klijenti mogu dobiti različit redosled obrada",
                    "tim mora da vodi računa da redosled omotača ne promeni semantiku rezultata",
                    "tok izvršavanja može postati manje očigledan kada se složi više slojeva",
                    "sve dodatne obrade će prirodno završiti centralizovane u jednoj klasi",
                    "testovi mogu biti precizniji jer se pojedinačne obrade mogu izolovati",
                    "garantovano manji broj objekata u runtime-u",
                    "greške u konfiguraciji pipeline-a mogu dati ispravan kod, ali pogrešan redosled ponašanja"
                ),
                correctAnswers = listOf(
                    "nova obrada može da se doda bez promene osnovnog procesora",
                    "različiti klijenti mogu dobiti različit redosled obrada",
                    "tim mora da vodi računa da redosled omotača ne promeni semantiku rezultata",
                    "tok izvršavanja može postati manje očigledan kada se složi više slojeva",
                    "testovi mogu biti precizniji jer se pojedinačne obrade mogu izolovati",
                    "greške u konfiguraciji pipeline-a mogu dati ispravan kod, ali pogrešan redosled ponašanja"
                )
            ),
            categories = listOf(
                MediorCategorySeed(
                    title = "Dobici za proširivanje i testiranje",
                    items = listOf(
                        "nova obrada može da se doda bez promene osnovnog procesora",
                        "različiti klijenti mogu dobiti različit redosled obrada",
                        "testovi mogu biti precizniji jer se pojedinačne obrade mogu izolovati"
                    )
                ),
                MediorCategorySeed(
                    title = "Operativni i razvojni rizici",
                    items = listOf(
                        "tim mora da vodi računa da redosled omotača ne promeni semantiku rezultata",
                        "tok izvršavanja može postati manje očigledan kada se složi više slojeva",
                        "greške u konfiguraciji pipeline-a mogu dati ispravan kod, ali pogrešan redosled ponašanja"
                    )
                )
            ),
            aiFollowUp = """
            Koji je opasniji problem u ovakvom sistemu: “previše klasa” ili “ispravan skup obrada u pogrešnom redosledu” — i zašto?

            TALAS 3
            1. Prvi tip zadatka: Sequence Logic i izbor pristupa
            """.trimIndent(),
            wave = 2,
            orderIndex = 20
        ),

        MediorSeedBuilders.consequenceQuestion(
            questionId = "M5.5",
            title = "Analiziraj posledice uvođenja objekata akcija u editor",
            prompt = """
            Tim je odlučio da akcije poput copy, paste, delete, duplicate i format ne budu više direktno vezane za toolbar i meni, već modelovane kao zasebni objekti akcija.
            """.trimIndent(),
            consequenceStep = MediorChoiceStepSeed(
                title = "Korak 1 - Izaberi najrelevantnije posledice",
                instruction = "Izaberi sve tačne odgovore.",
                options = listOf(
                    "ista akcija može da se koristi iz više ulaznih tačaka bez dupliranja logike",
                    "lakše je uvoditi istoriju akcija i eventualni undo sloj",
                    "broj malih klasa u sistemu može porasti",
                    "UI sloj postaje manje opterećen detaljima izvršavanja",
                    "automatski nestaje potreba za servisom koji stvarno izvršava posao",
                    "nova akcija obično zahteva novu klasu ili novi objekat odgovornosti",
                    "tok “ko je šta pokrenuo” može zahtevati dodatno praćenje u debagovanju",
                    "smanjuje se potreba za bilo kakvim interfejsima među akcijama"
                ),
                correctAnswers = listOf(
                    "ista akcija može da se koristi iz više ulaznih tačaka bez dupliranja logike",
                    "lakše je uvoditi istoriju akcija i eventualni undo sloj",
                    "broj malih klasa u sistemu može porasti",
                    "UI sloj postaje manje opterećen detaljima izvršavanja",
                    "nova akcija obično zahteva novu klasu ili novi objekat odgovornosti",
                    "tok “ko je šta pokrenuo” može zahtevati dodatno praćenje u debagovanju"
                )
            ),
            categories = listOf(
                MediorCategorySeed(
                    title = "Dobici u modularnosti i proširivanju",
                    items = listOf(
                        "ista akcija može da se koristi iz više ulaznih tačaka bez dupliranja logike",
                        "lakše je uvoditi istoriju akcija i eventualni undo sloj",
                        "UI sloj postaje manje opterećen detaljima izvršavanja"
                    )
                ),
                MediorCategorySeed(
                    title = "Cena u organizaciji koda i praćenju toka",
                    items = listOf(
                        "broj malih klasa u sistemu može porasti",
                        "nova akcija obično zahteva novu klasu ili novi objekat odgovornosti",
                        "tok “ko je šta pokrenuo” može zahtevati dodatno praćenje u debagovanju"
                    )
                )
            ),
            aiFollowUp = """
            Zašto rast broja manjih klasa nekad jeste prihvatljiva cena ako time dobijaš jasnije odgovornosti i uniforman model akcija?
            """.trimIndent(),
            wave = 3,
            orderIndex = 29
        ),

        MediorSeedBuilders.consequenceQuestion(
            questionId = "M5.6",
            title = "Analiziraj posledice uvođenja snapshot modela za undo",
            prompt = """
            Tim je odlučio da sistem istorije ne barata više direktno poljima draft-a, već da čuva snapshot objekte koje kreira sam draft.
            """.trimIndent(),
            consequenceStep = MediorChoiceStepSeed(
                title = "Korak 1 - Izaberi najrelevantnije posledice",
                instruction = "Izaberi sve tačne odgovore.",
                options = listOf(
                    "istorija manje zavisi od konkretne unutrašnje strukture draft objekta",
                    "sam draft zadržava veću kontrolu nad time šta ulazi u snapshot",
                    "undo mehanizam postaje otporniji na rast internog modela",
                    "caretaker sloj mora direktno da menja snapshot da bi sistem radio",
                    "moguć je veći broj snapshot objekata ako se istorija čuva često",
                    "jasnoća granice između “ko čuva istoriju” i “ko zna pravo stanje” postaje važnija",
                    "automatski se rešava i redo logika bez ikakvog dodatnog dizajna",
                    "tim mora da razmisli o politici čuvanja istorije i njenom trošku"
                ),
                correctAnswers = listOf(
                    "istorija manje zavisi od konkretne unutrašnje strukture draft objekta",
                    "sam draft zadržava veću kontrolu nad time šta ulazi u snapshot",
                    "undo mehanizam postaje otporniji na rast internog modela",
                    "moguć je veći broj snapshot objekata ako se istorija čuva često",
                    "jasnoća granice između “ko čuva istoriju” i “ko zna pravo stanje” postaje važnija",
                    "tim mora da razmisli o politici čuvanja istorije i njenom trošku"
                )
            ),
            categories = listOf(
                MediorCategorySeed(
                    title = "Dobici u enkapsulaciji i održavanju modela",
                    items = listOf(
                        "istorija manje zavisi od konkretne unutrašnje strukture draft objekta",
                        "sam draft zadržava veću kontrolu nad time šta ulazi u snapshot",
                        "undo mehanizam postaje otporniji na rast internog modela"
                    )
                ),
                MediorCategorySeed(
                    title = "Troškovi i projektne obaveze",
                    items = listOf(
                        "moguć je veći broj snapshot objekata ako se istorija čuva često",
                        "jasnoća granice između “ko čuva istoriju” i “ko zna pravo stanje” postaje važnija",
                        "tim mora da razmisli o politici čuvanja istorije i njenom trošku"
                    )
                )
            ),
            aiFollowUp = """
            Zašto problem kod undo sistema često nije samo “kako vratiti stanje”, nego i “koliko često i koliko dugo to stanje čuvati”?

            MEDIOR — TALAS 4
            1. Prvi tip zadatka: Sequence Logic i izbor pristupa
            """.trimIndent(),
            wave = 3,
            orderIndex = 30
        ),

        MediorSeedBuilders.consequenceQuestion(
            questionId = "M5.7",
            title = "Analiziraj posledice izdvajanja logike prolaska kroz kolekcije",
            prompt = """
            Tim je odlučio da klijent više ne obilazi kolekcije direktno preko njihovih internih struktura, već kroz poseban mehanizam prolaska.
            """.trimIndent(),
            consequenceStep = MediorChoiceStepSeed(
                title = "Korak 1 - Izaberi najrelevantnije posledice",
                instruction = "Izaberi sve tačne odgovore.",
                options = listOf(
                    "klijentski kod manje zavisi od unutrašnje organizacije kolekcije",
                    "veći broj podržanih načina prolaska može povećati složenost održavanja iteratora",
                    "promene unutrašnje strukture kolekcije manje pogađaju potrošački kod",
                    "automatski se rešava grupisanje elemenata u stablo",
                    "tim mora jasnije da definiše ugovor prolaska kroz elemente",
                    "pri debagovanju je ponekad manje očigledno gde je tačno sakrivena logika iteracije",
                    "garantovano se smanjuje broj elemenata u kolekciji",
                    "lakše je standardizovati način prolaska kroz više različitih izvora podataka"
                ),
                correctAnswers = listOf(
                    "klijentski kod manje zavisi od unutrašnje organizacije kolekcije",
                    "veći broj podržanih načina prolaska može povećati složenost održavanja iteratora",
                    "promene unutrašnje strukture kolekcije manje pogađaju potrošački kod",
                    "tim mora jasnije da definiše ugovor prolaska kroz elemente",
                    "pri debagovanju je ponekad manje očigledno gde je tačno sakrivena logika iteracije",
                    "lakše je standardizovati način prolaska kroz više različitih izvora podataka"
                )
            ),
            categories = listOf(
                MediorCategorySeed(
                    title = "Dobici u fleksibilnosti i stabilnosti klijentskog koda",
                    items = listOf(
                        "klijentski kod manje zavisi od unutrašnje organizacije kolekcije",
                        "promene unutrašnje strukture kolekcije manje pogađaju potrošački kod",
                        "lakše je standardizovati način prolaska kroz više različitih izvora podataka"
                    )
                ),
                MediorCategorySeed(
                    title = "Cena u jasnoći i ugovoru apstrakcije",
                    items = listOf(
                        "moguće je uvesti više različitih načina prolaska kroz istu strukturu",
                        "tim mora jasnije da definiše ugovor prolaska kroz elemente",
                        "pri debagovanju je ponekad manje očigledno gde je tačno sakrivena logika iteracije"
                    )
                )
            ),
            aiFollowUp = """
            Zašto ista osobina sistema — “više načina prolaska kroz istu strukturu” — može istovremeno biti i dobitak i izvor dodatne složenosti?
            """.trimIndent(),
            wave = 4,
            orderIndex = 39
        ),

        MediorSeedBuilders.consequenceQuestion(
            questionId = "M5.8",
            title = "Analiziraj posledice uvođenja konzistentnih paketa UI komponenti",
            prompt = "Tim je odlučio da svaki podržani brend dobije svoj izvor komponenti koji isporučuje ceo konzistentan skup UI elemenata.",
            consequenceStep = MediorChoiceStepSeed(
                title = "Korak 1 - Izaberi najrelevantnije posledice",
                instruction = "Izaberi sve tačne odgovore.",
                options = listOf(
                    "manja verovatnoća mešanja komponenti različitih stilskih paketa",
                    "lakše širenje sistema novim brendovima kroz dodatne pakete komponenti",
                    "veća potreba da tim disciplinovano održava ugovor svih komponenti unutar paketa",
                    "svaki pojedinačni ekran automatski postaje manji",
                    "veći značaj doslednog modelovanja zajedničkih interfejsa",
                    "onboarding novih developera može biti sporiji ako ne razumeju hijerarhiju paketa",
                    "automatsko rešavanje problema runtime promene algoritama",
                    "lakše paralelno razvijanje celina po brendovima"
                ),
                correctAnswers = listOf(
                    "manja verovatnoća mešanja komponenti različitih stilskih paketa",
                    "lakše širenje sistema novim brendovima kroz dodatne pakete komponenti",
                    "veća potreba da tim disciplinovano održava ugovor svih komponenti unutar paketa",
                    "veći značaj doslednog modelovanja zajedničkih interfejsa",
                    "onboarding novih developera može biti sporiji ako ne razumeju hijerarhiju paketa",
                    "lakše paralelno razvijanje celina po brendovima"
                )
            ),
            categories = listOf(
                MediorCategorySeed(
                    title = "Dobici u organizaciji razvoja i konzistentnosti",
                    items = listOf(
                        "manja verovatnoća mešanja komponenti različitih stilskih paketa",
                        "lakše širenje sistema novim brendovima kroz dodatne pakete komponenti",
                        "lakše paralelno razvijanje celina po brendovima"
                    )
                ),
                MediorCategorySeed(
                    title = "Cena u disciplini dizajna i učenju sistema",
                    items = listOf(
                        "veća potreba da tim disciplinovano održava ugovor svih komponenti unutar paketa",
                        "veći značaj doslednog modelovanja zajedničkih interfejsa",
                        "onboarding novih developera može biti sporiji ako ne razumeju hijerarhiju paketa"
                    )
                )
            ),
            aiFollowUp = """
            Zašto sistem može dobiti na skalabilnosti po brendovima, a istovremeno postati zahtevniji za članove tima koji tek ulaze u projekat?

            MEDIOR — ZAVRŠNI TALAS
            1. Prvi tip zadatka: Sequence Logic i izbor pristupa
            """.trimIndent(),
            wave = 4,
            orderIndex = 40
        ),

        MediorSeedBuilders.consequenceQuestion(
            questionId = "M5.9",
            title = "Analiziraj posledice uvođenja jedinstvene ulazne tačke za složen proces",
            prompt = """
            Tim je odlučio da složen proces registracije klijenta više ne pokreće kroz direktne pozive više servisa iz aplikacionog sloja, već kroz jednu objedinjenu ulaznu tačku koja orkestrira proveru identiteta, proveru adrese, scoring, audit zapis i kreiranje naloga.
            """.trimIndent(),
            consequenceStep = MediorChoiceStepSeed(
                title = "Korak 1 - Izaberi najrelevantnije posledice",
                instruction = "Izaberi sve tačne odgovore.",
                options = listOf(
                    "aplikacioni sloj manje zavisi od detalja saradnje internih servisa",
                    "promene u internom toku registracije manje pogađaju spoljne klijente",
                    "sistem dobija jednostavniji ulaz u složen proces",
                    "postoji rizik da objedinjena tačka vremenom postane previše opterećena odgovornostima",
                    "automatski se rešava problem nekompatibilnih interfejsa svih spoljnih sistema",
                    "tim mora pažljivo da održi granicu između pojednostavljenog ulaza i stvarne poslovne orkestracije",
                    "garantovano nestaje potreba za pojedinačnim podsistemima",
                    "novim članovima tima ponekad može biti manje vidljivo šta se tačno dešava iza jednostavnog ulaza"
                ),
                correctAnswers = listOf(
                    "aplikacioni sloj manje zavisi od detalja saradnje internih servisa",
                    "promene u internom toku registracije manje pogađaju spoljne klijente",
                    "sistem dobija jednostavniji ulaz u složen proces",
                    "postoji rizik da objedinjena tačka vremenom postane previše opterećena odgovornostima",
                    "tim mora pažljivo da održi granicu između pojednostavljenog ulaza i stvarne poslovne orkestracije",
                    "novim članovima tima ponekad može biti manje vidljivo šta se tačno dešava iza jednostavnog ulaza"
                )
            ),
            categories = listOf(
                MediorCategorySeed(
                    title = "Dobici u jednostavnosti i smanjenju sprege",
                    items = listOf(
                        "aplikacioni sloj manje zavisi od detalja saradnje internih servisa",
                        "promene u internom toku registracije manje pogađaju spoljne klijente",
                        "sistem dobija jednostavniji ulaz u složen proces"
                    )
                ),
                MediorCategorySeed(
                    title = "Rizici u organizaciji odgovornosti",
                    items = listOf(
                        "postoji rizik da objedinjena tačka vremenom postane previše opterećena odgovornostima",
                        "tim mora pažljivo da održi granicu između pojednostavljenog ulaza i stvarne poslovne orkestracije",
                        "novim članovima tima ponekad može biti manje vidljivo šta se tačno dešava iza jednostavnog ulaza"
                    )
                )
            ),
            aiFollowUp = "Zašto jednostavniji ulaz u sistem nije automatski dobar ako iza njega počne da raste previše nepovezane logike?",
            wave = 5,
            orderIndex = 49
        ),

        MediorSeedBuilders.consequenceQuestion(
            questionId = "M5.10",
            title = "Analiziraj posledice deljenja internog stanja u vizuelnom sistemu",
            prompt = """
            Tim je odlučio da veliki broj sličnih vizuelnih objekata više ne čuva kompletan skup prikaznih podataka po instanci, već da se zajednički deo deli.
            """.trimIndent(),
            consequenceStep = MediorChoiceStepSeed(
                title = "Korak 1 - Izaberi najrelevantnije posledice",
                instruction = "Izaberi sve tačne odgovore.",
                options = listOf(
                    "manji memorijski trošak kada veliki broj instanci deli isti interni prikaz",
                    "veći značaj jasne podele na deljeno i spoljašnje stanje",
                    "sistem može postati osetljiviji na greške ako se spoljašnje stanje pomeša sa deljenim",
                    "lakše je dodavati nove operacije nad stabilnom strukturom elemenata",
                    "fabrika deljenih objekata postaje važna za očuvanje stvarne uštede",
                    "dobit od ovog pristupa opada ako objekti zapravo dele vrlo malo zajedničkih podataka",
                    "automatski nestaje potreba za kreiranjem instanci",
                    "deo tima može teže razumeti zašto dva vizuelno različita objekta koriste isti interni objekat"
                ),
                correctAnswers = listOf(
                    "manji memorijski trošak kada veliki broj instanci deli isti interni prikaz",
                    "veći značaj jasne podele na deljeno i spoljašnje stanje",
                    "sistem može postati osetljiviji na greške ako se spoljašnje stanje pomeša sa deljenim",
                    "fabrika deljenih objekata postaje važna za očuvanje stvarne uštede",
                    "dobit od ovog pristupa opada ako objekti zapravo dele vrlo malo zajedničkih podataka",
                    "deo tima može teže razumeti zašto dva vizuelno različita objekta koriste isti interni objekat"
                )
            ),
            categories = listOf(
                MediorCategorySeed(
                    title = "Dobici u efikasnosti i kontroli deljenog stanja",
                    items = listOf(
                        "manji memorijski trošak kada veliki broj instanci deli isti interni prikaz",
                        "fabrika deljenih objekata postaje važna za očuvanje stvarne uštede",
                        "veći značaj jasne podele na deljeno i spoljašnje stanje"
                    )
                ),
                MediorCategorySeed(
                    title = "Rizici u ispravnosti i razumevanju modela",
                    items = listOf(
                        "sistem može postati osetljiviji na greške ako se spoljašnje stanje pomeša sa deljenim",
                        "dobit od ovog pristupa opada ako objekti zapravo dele vrlo malo zajedničkih podataka",
                        "deo tima može teže razumeti zašto dva vizuelno različita objekta koriste isti interni objekat"
                    )
                )
            ),
            aiFollowUp = """
            Zašto Flyweight nije dobar izbor samo zato što “ima mnogo objekata”, već tek kada postoji dovoljno smislenog zajedničkog internog stanja?
            """.trimIndent(),
            wave = 5,
            orderIndex = 50
        )
    )
}
