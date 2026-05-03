package com.example.pmuprojekat.data.seed.medior

import com.example.pmuprojekat.data.seed.SeedQuestion

object MediorConstraintDecisionSeed {
    val questions: List<SeedQuestion> = listOf(
        MediorSeedBuilders.constraintQuestion(
            questionId = "M4.1",
            title = "Izbor pristupa za koordinaciju složenog administratorskog ekrana",
            prompt = """
            Tim razvija ekran za administraciju ugovora.
            Zahtevi sistema
            •	promene u jednom polju mogu menjati stanje više drugih polja, 
            •	određena dugmad treba da budu omogućena ili onemogućena u zavisnosti od validacije, 
            •	checkbox-i otkrivaju ili sakrivaju dodatne sekcije forme, 
            •	pravila interakcije treba da budu pregledna i laka za izmenu. 
            Ograničenja sistema
            •	ekran će rasti i dobijati nova polja, 
            •	tim je mali i održavanje mora biti jednostavno, 
            •	želi se izbeći da svaka UI komponenta direktno poznaje veliki broj drugih komponenti.
            """.trimIndent(),
            alternatives = listOf(
                "Observer",
                "Mediator"
            ),
            correctAlternative = "Mediator",
            choiceSteps = listOf(
                MediorChoiceStepSeed(
                    title = "Korak 2 - Koje zahteve ovo rešenje najbolje pokriva?",
                    instruction = "Izaberi sve tačne odgovore.",
                    options = listOf(
                        "centralizaciju pravila ponašanja između više UI komponenti",
                        "automatsko emitovanje događaja ka velikom broju potpuno nezavisnih modula",
                        "lakšu kontrolu složenih međuzavisnosti unutar jednog ekrana",
                        "deljenje memorije između velikog broja sitnih objekata"
                    ),
                    correctAnswers = listOf(
                        "centralizaciju pravila ponašanja između više UI komponenti",
                        "lakšu kontrolu složenih međuzavisnosti unutar jednog ekrana"
                    )
                ),
                MediorChoiceStepSeed(
                    title = "Korak 3 - Kako ovo rešenje utiče na ograničenja sistema?",
                    instruction = "Izaberi sve tačne odgovore.",
                    options = listOf(
                        "smanjuje broj direktnih veza između pojedinačnih komponenti",
                        "olakšava održavanje kada broj pravila interakcije raste",
                        "automatski smanjuje broj renderovanja forme bez dodatne optimizacije",
                        "uklanja potrebu za bilo kakvom validacionom logikom"
                    ),
                    correctAnswers = listOf(
                        "smanjuje broj direktnih veza između pojedinačnih komponenti",
                        "olakšava održavanje kada broj pravila interakcije raste"
                    )
                )
            ),
            aiFollowUp = """
            Ako bi svako polje direktno znalo za više drugih polja i dugmadi, koji bi problem najpre postao vidljiv kada se ekran proširi novim pravilima?
            """.trimIndent(),
            wave = 1,
            orderIndex = 7
        ),

        MediorSeedBuilders.constraintQuestion(
            questionId = "M4.2",
            title = "Izbor pristupa za kombinovanje ponašanja u message pipeline-u",
            prompt = """
            Sistem obrađuje poruke pre slanja.
            Zahtevi sistema
            •	obrada treba da podrži opciono logovanje, 
            •	opcionu enkripciju, 
            •	opcionu kompresiju, 
            •	i mogućnost kombinovanja tih ponašanja u različitim redosledima za različite klijente. 
            Ograničenja sistema
            •	tim ne želi veliki broj posebnih klasa za svaku kombinaciju, 
            •	osnovna obrada poruke treba da ostane jednostavna, 
            •	nova ponašanja će se vremenom dodavati.
            """.trimIndent(),
            alternatives = listOf(
                "Chain of Responsibility",
                "Decorator"
            ),
            correctAlternative = "Decorator",
            choiceSteps = listOf(
                MediorChoiceStepSeed(
                    title = "Korak 2 - Koje zahteve ovo rešenje najbolje pokriva?",
                    instruction = "Izaberi sve tačne odgovore.",
                    options = listOf(
                        "kombinovanje dodatnih ponašanja po potrebi",
                        "održavanje fiksnog i nepromenljivog skupa algoritama unutar jedne klase",
                        "mogućnost da se funkcionalnosti dodaju bez menjanja osnovne klase",
                        "upravljanje životnim ciklusom objekta kroz stanja"
                    ),
                    correctAnswers = listOf(
                        "kombinovanje dodatnih ponašanja po potrebi",
                        "mogućnost da se funkcionalnosti dodaju bez menjanja osnovne klase"
                    )
                ),
                MediorChoiceStepSeed(
                    title = "Korak 3 - Kako ovo rešenje utiče na ograničenja sistema?",
                    instruction = "Izaberi sve tačne odgovore.",
                    options = listOf(
                        "smanjuje potrebu za velikim brojem kombinacionih podklasa",
                        "olakšava dodavanje novih ponašanja u budućnosti",
                        "automatski rešava distribuiranu komunikaciju između servisa",
                        "uklanja potrebu za definisanjem interfejsa osnovne obrade"
                    ),
                    correctAnswers = listOf(
                        "smanjuje potrebu za velikim brojem kombinacionih podklasa",
                        "olakšava dodavanje novih ponašanja u budućnosti"
                    )
                )
            ),
            aiFollowUp = """
            Koja bi bila najverovatnija posledica kada bi svaka kombinacija log + encrypt + compress dobila svoju posebnu klasu?

            5. Peti tip zadatka: Analiza posledica izabranog rešenja
            """.trimIndent(),
            wave = 1,
            orderIndex = 8
        ),

        MediorSeedBuilders.constraintQuestion(
            questionId = "M4.3",
            title = "Izbor pristupa za akcije u kompleksnom editoru",
            prompt = """
            Zahtevi sistema
            •	akcije editora treba da budu pokretane iz toolbar-a, menija i tastaturnih prečica, 
            •	iste akcije treba da budu logički ujednačene bez obzira odakle su pokrenute, 
            •	sistem u narednoj fazi treba da podrži undo i eventualno makro-komande. 
            Ograničenja sistema
            •	tim ne želi da UI sloj sadrži poslovnu logiku svake akcije, 
            •	nove akcije će se često dodavati, 
            •	potrebno je da mapiranje UI događaja na poslovne operacije ostane pregledno.
            """.trimIndent(),
            alternatives = listOf(
                "Decorator",
                "Command"
            ),
            correctAlternative = "Command",
            choiceSteps = listOf(
                MediorChoiceStepSeed(
                    title = "Korak 2 - Koje zahteve ovo rešenje najbolje pokriva?",
                    instruction = "Izaberi sve tačne odgovore.",
                    options = listOf(
                        "ujednačavanje pozivanja akcija iz više različitih ulaznih tačaka",
                        "mogućnost da se akcije kasnije beleže, čuvaju ili vraćaju",
                        "deljenje memorijskog stanja između velikog broja sličnih elemenata",
                        "podršku za hijerarhijski tretman grupa i pojedinačnih elemenata"
                    ),
                    correctAnswers = listOf(
                        "ujednačavanje pozivanja akcija iz više različitih ulaznih tačaka",
                        "mogućnost da se akcije kasnije beleže, čuvaju ili vraćaju"
                    )
                ),
                MediorChoiceStepSeed(
                    title = "Korak 3 - Kako ovo rešenje utiče na ograničenja sistema?",
                    instruction = "Izaberi sve tačne odgovore.",
                    options = listOf(
                        "smanjuje količinu poslovne logike rasute po UI komponentama",
                        "olakšava dodavanje novih akcija bez menjanja postojećih UI elemenata",
                        "automatski rešava sve probleme sa konkurentnim pristupom dokumentu",
                        "uklanja potrebu za klasom koja stvarno izvršava posao"
                    ),
                    correctAnswers = listOf(
                        "smanjuje količinu poslovne logike rasute po UI komponentama",
                        "olakšava dodavanje novih akcija bez menjanja postojećih UI elemenata"
                    )
                )
            ),
            aiFollowUp = """
            Zašto bi sistem sa direktnim pozivima iz svakog dugmeta postao teže proširiv čim se isti skup akcija koristi i iz menija, shortcut-a i automatizovanih tokova?
            """.trimIndent(),
            wave = 2,
            orderIndex = 17
        ),

        MediorSeedBuilders.constraintQuestion(
            questionId = "M4.4",
            title = "Izbor pristupa za dodavanje novih operacija nad stabilnom strukturom",
            prompt = """
            Zahtevi sistema
            •	sistem ima stabilnu strukturu čvorova koju koristi parser, 
            •	tim često dodaje nove operacije nad tim čvorovima: validaciju, eksport, optimizaciju i statičku analizu, 
            •	nove operacije treba uvoditi bez stalne izmene svih postojećih klasa čvorova, 
            •	poželjno je da jedna operacija bude razvijana i testirana kao zaokružena celina. 
            Ograničenja sistema
            •	struktura čvorova se ne menja često, 
            •	broj operacija raste brže od broja tipova čvorova, 
            •	više timova može paralelno raditi na različitim operacijama, 
            •	tim želi da smanji rizik da svaka nova funkcija zahteva izmene na velikom broju mesta.
            """.trimIndent(),
            alternatives = listOf(
                "Composite",
                "Visitor"
            ),
            correctAlternative = "Visitor",
            choiceSteps = listOf(
                MediorChoiceStepSeed(
                    title = "Korak 2 - Koje zahteve ovo rešenje najbolje pokriva?",
                    instruction = "Izaberi sve tačne odgovore.",
                    options = listOf(
                        "dodavanje novih operacija bez stalnog širenja svih klasa elemenata",
                        "grupisanje logike jedne operacije na jednom mestu",
                        "modelovanje životnog ciklusa objekta kroz promene stanja",
                        "enkapsulaciju istorije prethodnih snapshot-a"
                    ),
                    correctAnswers = listOf(
                        "dodavanje novih operacija bez stalnog širenja svih klasa elemenata",
                        "grupisanje logike jedne operacije na jednom mestu"
                    )
                ),
                MediorChoiceStepSeed(
                    title = "Korak 3 - Kako ovo rešenje utiče na ograničenja sistema?",
                    instruction = "Izaberi sve tačne odgovore.",
                    options = listOf(
                        "smanjuje potrebu da se svaka nova operacija razliva kroz mnoge klase elemenata",
                        "dobro odgovara situaciji u kojoj je struktura elemenata relativno stabilna",
                        "automatski pojednostavljuje parsiranje jezika bez dodatnih klasa",
                        "uklanja potrebu da elementi prihvate spoljne operacije"
                    ),
                    correctAnswers = listOf(
                        "smanjuje potrebu da se svaka nova operacija razliva kroz mnoge klase elemenata",
                        "dobro odgovara situaciji u kojoj je struktura elemenata relativno stabilna"
                    )
                )
            ),
            aiFollowUp = "Zašto Visitor postaje manje privlačan ako se tipovi elemenata menjaju češće nego operacije nad njima?",
            wave = 2,
            orderIndex = 18
        ),

        MediorSeedBuilders.constraintQuestion(
            questionId = "M4.5",
            title = "Izbor pristupa za prolazak kroz rezultate iz više izvora",
            prompt = """
            Zahtevi sistema
            •	klijent treba da prolazi kroz rezultate bez znanja da li dolaze iz liste, stabla ili straničnog izvora, 
            •	isti API treba da podrži više oblika kolekcija, 
            •	moguće je da se kasnije uvedu različiti načini obilaska, 
            •	deo izvora može podržavati lenjo dohvatanje elemenata. 
            Ograničenja sistema
            •	unutrašnja struktura kolekcija može da se menja, 
            •	tim ne želi da klijentski kod bude pun uslova po tipu kolekcije, 
            •	poželjno je da se logika prolaska ne rasipa kroz više komponenti, 
            •	način obilaska ne bi smeo da tera potrošače da poznaju internu organizaciju izvora.
            """.trimIndent(),
            alternatives = listOf(
                "Prototype",
                "Iterator"
            ),
            correctAlternative = "Iterator",
            choiceSteps = listOf(
                MediorChoiceStepSeed(
                    title = "Korak 2 - Koje zahteve ovo rešenje najbolje pokriva?",
                    instruction = "Izaberi sve tačne odgovore.",
                    options = listOf(
                        "jedinstven način prolaska kroz različite kolekcije",
                        "mogućnost da se logika obilaska izdvoji iz same kolekcije",
                        "automatsko rešavanje grupisanja objekata u stablo",
                        "centralizaciju UI koordinacije između komponenti"
                    ),
                    correctAnswers = listOf(
                        "jedinstven način prolaska kroz različite kolekcije",
                        "mogućnost da se logika obilaska izdvoji iz same kolekcije"
                    )
                ),
                MediorChoiceStepSeed(
                    title = "Korak 3 - Kako ovo rešenje utiče na ograničenja sistema?",
                    instruction = "Izaberi sve tačne odgovore.",
                    options = listOf(
                        "smanjuje zavisnost klijenta od konkretne unutrašnje implementacije kolekcije",
                        "olakšava promenu strukture kolekcije bez razbijanja potrošačkog koda",
                        "garantuje manji broj objekata u memoriji",
                        "uklanja potrebu za definisanjem ugovora iteracije"
                    ),
                    correctAnswers = listOf(
                        "smanjuje zavisnost klijenta od konkretne unutrašnje implementacije kolekcije",
                        "olakšava promenu strukture kolekcije bez razbijanja potrošačkog koda"
                    )
                )
            ),
            aiFollowUp = "Zašto je za tim opasno kada klijent „zna previše” o tome kako su elementi interno organizovani?",
            wave = 3,
            orderIndex = 27
        ),

        MediorSeedBuilders.constraintQuestion(
            questionId = "M4.6",
            title = "Izbor pristupa za vraćanje prethodnih verzija složenog objekta",
            prompt = """
            Zahtevi sistema
            •	korisnik mora da ima mogućnost da vrati prethodne verzije draft-a, 
            •	sistem mora čuvati dovoljno podataka za povratak na starije stanje, 
            •	ostatak aplikacije ne bi trebalo da direktno menja unutrašnja polja draft objekta. 
            Ograničenja sistema
            •	objekat draft vremenom dobija nova interna polja, 
            •	tim želi da smanji spregu između istorije i same strukture draft-a, 
            •	undo mehanizam mora ostati održiv kada model poraste.
            """.trimIndent(),
            alternatives = listOf(
                "Command",
                "Memento"
            ),
            correctAlternative = "Memento",
            choiceSteps = listOf(
                MediorChoiceStepSeed(
                    title = "Korak 2 - Koje zahteve ovo rešenje najbolje pokriva?",
                    instruction = "Izaberi sve tačne odgovore.",
                    options = listOf(
                        "enkapsulisano čuvanje stanja objekta",
                        "povratak na prethodnu verziju bez izlaganja svih internih detalja",
                        "modelovanje više nezavisnih algoritama obrade",
                        "deljenje istog internog stanja između velikog broja instanci"
                    ),
                    correctAnswers = listOf(
                        "enkapsulisano čuvanje stanja objekta",
                        "povratak na prethodnu verziju bez izlaganja svih internih detalja"
                    )
                ),
                MediorChoiceStepSeed(
                    title = "Korak 3 - Kako ovo rešenje utiče na ograničenja sistema?",
                    instruction = "Izaberi sve tačne odgovore.",
                    options = listOf(
                        "smanjuje spregu između mehanizma istorije i strukture samog objekta",
                        "bolje podnosi rast internog modela draft-a kroz vreme",
                        "automatski rešava problem izbora algoritma za validaciju",
                        "uklanja potrebu da sam objekat zna kako da obnovi svoje stanje"
                    ),
                    correctAnswers = listOf(
                        "smanjuje spregu između mehanizma istorije i strukture samog objekta",
                        "bolje podnosi rast internog modela draft-a kroz vreme"
                    )
                )
            ),
            aiFollowUp = """
            Zašto undo sistem postaje krhkiji kada istorija direktno barata svim internim poljima umesto da stanje dolazi iz samog objekta?
            """.trimIndent(),
            wave = 3,
            orderIndex = 28
        ),

        MediorSeedBuilders.constraintQuestion(
            questionId = "M4.7",
            title = "Izbor pristupa za pojednostavljen ulaz u složen proces registracije",
            prompt = """
            Zahtevi sistema
            •	registracija klijenta uključuje proveru identiteta, proveru adrese, scoring, audit zapis i kreiranje naloga, 
            •	ostatak sistema treba da pokreće ovaj proces kroz jednostavnu ulaznu tačku, 
            •	aplikacioni sloj ne bi trebalo da zna detaljan redosled svih internih poziva, 
            •	tim želi da proširenje toka registracije ne zahteva izmene u svakom klijentu koji ga koristi. 
            Ograničenja sistema
            •	interni tok će se vremenom širiti novim proverama, 
            •	više timova održava različite podsisteme, 
            •	želi se smanjenje spregnutosti između aplikacionog sloja i internih servisa, 
            •	promene u orkestraciji procesa ne bi smele da razbijaju spoljne pozive.
            """.trimIndent(),
            alternatives = listOf(
                "Mediator",
                "Facade"
            ),
            correctAlternative = "Facade",
            choiceSteps = listOf(
                MediorChoiceStepSeed(
                    title = "Korak 2 - Koje zahteve ovo rešenje najbolje pokriva?",
                    instruction = "Izaberi sve tačne odgovore.",
                    options = listOf(
                        "pojednostavljen ulaz u složen skup internih operacija",
                        "sakrivanje detalja saradnje više podsistema od klijenta",
                        "odloženo učitavanje udaljenog resursa",
                        "modelovanje više nezavisnih algoritama obrade"
                    ),
                    correctAnswers = listOf(
                        "pojednostavljen ulaz u složen skup internih operacija",
                        "sakrivanje detalja saradnje više podsistema od klijenta"
                    )
                ),
                MediorChoiceStepSeed(
                    title = "Korak 3 - Kako ovo rešenje utiče na ograničenja sistema?",
                    instruction = "Izaberi sve tačne odgovore.",
                    options = listOf(
                        "smanjuje spregu između aplikacionog sloja i internih servisa",
                        "olakšava promene u internom toku bez velikog uticaja na klijente",
                        "automatski rešava neusklađenost eksternih interfejsa",
                        "uklanja potrebu za postojanjem pojedinačnih podsistema"
                    ),
                    correctAnswers = listOf(
                        "smanjuje spregu između aplikacionog sloja i internih servisa",
                        "olakšava promene u internom toku bez velikog uticaja na klijente"
                    )
                )
            ),
            aiFollowUp = "Zašto sistem postaje teže održiv kada aplikacioni sloj mora da zna redosled i detalje svih internih servisa?",
            wave = 4,
            orderIndex = 37
        ),

        MediorSeedBuilders.constraintQuestion(
            questionId = "M4.8",
            title = "Izbor pristupa za konzistentan white-label interfejs",
            prompt = """
            Zahtevi sistema
            •	kada je aktivan određeni brend, svi glavni UI elementi moraju pratiti isti stil i pravila, 
            •	tim želi da promena brenda utiče na ceo skup komponenti kao celinu, 
            •	ne sme dolaziti do slučajnog mešanja komponenti različitih stilskih paketa. 
            Ograničenja sistema
            •	broj podržanih brendova će rasti, 
            •	više timova radi paralelno na komponentama, 
            •	greške konzistentnosti između komponenti teško se primećuju u ranoj fazi.
            """.trimIndent(),
            alternatives = listOf(
                "Factory Method",
                "Abstract Factory"
            ),
            correctAlternative = "Abstract Factory",
            choiceSteps = listOf(
                MediorChoiceStepSeed(
                    title = "Korak 2 - Koje zahteve ovo rešenje najbolje pokriva?",
                    instruction = "Izaberi sve tačne odgovore.",
                    options = listOf(
                        "dobijanje konzistentnog paketa međusobno usklađenih komponenti",
                        "promena celog seta komponenti kroz jedan izbor",
                        "odloženo učitavanje skupog udaljenog resursa",
                        "obilazak kolekcije bez znanja o njenoj strukturi"
                    ),
                    correctAnswers = listOf(
                        "dobijanje konzistentnog paketa međusobno usklađenih komponenti",
                        "promena celog seta komponenti kroz jedan izbor"
                    )
                ),
                MediorChoiceStepSeed(
                    title = "Korak 3 - Kako ovo rešenje utiče na ograničenja sistema?",
                    instruction = "Izaberi sve tačne odgovore.",
                    options = listOf(
                        "smanjuje rizik da paralelni timovi slučajno spoje komponente iz različitih paketa",
                        "olakšava skaliranje broja podržanih brendova kroz jasne fabrike po paketu",
                        "automatski smanjuje broj UI ekrana",
                        "uklanja potrebu za interfejsima komponenti"
                    ),
                    correctAnswers = listOf(
                        "smanjuje rizik da paralelni timovi slučajno spoje komponente iz različitih paketa",
                        "olakšava skaliranje broja podržanih brendova kroz jasne fabrike po paketu"
                    )
                )
            ),
            aiFollowUp = """
            Zašto su kod white-label sistema greške konzistentnosti između komponenti često opasnije od pojedinačnih grešaka u jednoj komponenti?
            """.trimIndent(),
            wave = 4,
            orderIndex = 38
        ),

        MediorSeedBuilders.constraintQuestion(
            questionId = "M4.9",
            title = "Izbor pristupa za payment integraciju koja raste u dve dimenzije",
            prompt = """
            Zahtevi sistema
            •	sistem mora podržati više payment provajdera, 
            •	istovremeno postoje više vrsta payment tokova: jednokratno plaćanje, refund, rezervacija sredstava, 
            •	tim želi da i provajderi i payment tokovi mogu da rastu nezavisno. 
            Ograničenja sistema
            •	deo provajdera ima neusklađene interfejse, 
            •	očekuje se rast broja provajdera i tipova tokova, 
            •	tim želi da izbegne eksploziju broja kombinovanih klasa.
            """.trimIndent(),
            alternatives = listOf(
                "Adapter",
                "Bridge"
            ),
            correctAlternative = "Bridge",
            choiceSteps = listOf(
                MediorChoiceStepSeed(
                    title = "Korak 2 - Koje zahteve ovo rešenje najbolje pokriva?",
                    instruction = "Izaberi sve tačne odgovore.",
                    options = listOf(
                        "nezavisno širenje payment tokova i provajdera",
                        "izbegavanje vezivanja jedne ose razvoja za drugu",
                        "čuvanje istorije prethodnih stanja transakcije",
                        "emitovanje događaja velikom broju nezavisnih potrošača"
                    ),
                    correctAnswers = listOf(
                        "nezavisno širenje payment tokova i provajdera",
                        "izbegavanje vezivanja jedne ose razvoja za drugu"
                    )
                ),
                MediorChoiceStepSeed(
                    title = "Korak 3 - Kako ovo rešenje utiče na ograničenja sistema?",
                    instruction = "Izaberi sve tačne odgovore.",
                    options = listOf(
                        "smanjuje rizik od kombinatornog rasta klasa",
                        "bolje podnosi situaciju u kojoj i provajderi i tokovi posebno evoluiraju",
                        "automatski rešava sve neusklađene ugovore bez dodatnog sloja",
                        "uklanja potrebu za zajedničkim interfejsom provajdera"
                    ),
                    correctAnswers = listOf(
                        "smanjuje rizik od kombinatornog rasta klasa",
                        "bolje podnosi situaciju u kojoj i provajderi i tokovi posebno evoluiraju"
                    )
                )
            ),
            aiFollowUp = "Zašto u ovom sistemu “dve ose rasta” postaju važnije od pojedinačne neusklađenosti jednog provajdera?",
            wave = 5,
            orderIndex = 47
        ),

        MediorSeedBuilders.constraintQuestion(
            questionId = "M4.10",
            title = "Izbor pristupa za sastavljanje složenih izveštaja sa opcionim delovima",
            prompt = """
            Zahtevi sistema
            •	sistem generiše izveštaje koji mogu imati summary, tabele, grafikone, komentare i dodatke, 
            •	isti tip izveštaja može imati više različitih kombinacija sekcija, 
            •	tim želi da izgradnja izveštaja ostane pregledna i kontrolisana, 
            •	nove sekcije treba uvoditi bez razbijanja postojećeg načina sastavljanja izveštaja. 
            Ograničenja sistema
            •	broj kombinacija će vremenom rasti, 
            •	tim ne želi eksploziju konstruktora i fabričkih varijanti, 
            •	nova sekcija ne bi smela da zahteva prepravku velikog broja postojećih klasa, 
            •	potrebno je da sastavljanje izveštaja ostane razumljivo i kada broj opcija poraste.
            """.trimIndent(),
            alternatives = listOf(
                "Abstract Factory",
                "Builder"
            ),
            correctAlternative = "Builder",
            choiceSteps = listOf(
                MediorChoiceStepSeed(
                    title = "Korak 2 - Koje zahteve ovo rešenje najbolje pokriva?",
                    instruction = "Izaberi sve tačne odgovore.",
                    options = listOf(
                        "postepenu i kontrolisanu izgradnju složenog objekta",
                        "lako kombinovanje opcionih delova istog proizvoda",
                        "deljenje internog memorijskog stanja između mnogih instanci",
                        "modelovanje prelaska objekta kroz životni ciklus"
                    ),
                    correctAnswers = listOf(
                        "postepenu i kontrolisanu izgradnju složenog objekta",
                        "lako kombinovanje opcionih delova istog proizvoda"
                    )
                ),
                MediorChoiceStepSeed(
                    title = "Korak 3 - Kako ovo rešenje utiče na ograničenja sistema?",
                    instruction = "Izaberi sve tačne odgovore.",
                    options = listOf(
                        "smanjuje potrebu za velikim brojem kombinacionih konstruktora",
                        "olakšava dodavanje novih sekcija i varijanti izveštaja",
                        "automatski rešava problem nekompatibilnih interfejsa",
                        "uklanja potrebu za objektom koji predstavlja konačni izveštaj"
                    ),
                    correctAnswers = listOf(
                        "smanjuje potrebu za velikim brojem kombinacionih konstruktora",
                        "olakšava dodavanje novih sekcija i varijanti izveštaja"
                    )
                )
            ),
            aiFollowUp = """
            Zašto problem ovde nije samo „napraviti objekat“, već „kontrolisano sastaviti mnogo mogućih varijanti istog objekta“?

            5. Peti tip zadatka: Analiza posledica izabranog rešenja
            """.trimIndent(),
            wave = 5,
            orderIndex = 48
        )
    )
}
