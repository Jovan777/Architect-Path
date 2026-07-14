package com.example.pmuprojekat.data.encyclopedia

object EncyclopediaSeed {
    private const val AI_ML_RAG = "ai_ml_rag"
    private const val SECURITY_ACCESS_AUDIT = "security_access_audit"
    private const val INTEGRATIONS_BUSINESS_DOMAINS = "integrations_business_domains"
    private const val COMMUNICATION_EVENTS_PROCESSING = "communication_events_processing"
    private const val CONSISTENCY_CONCURRENCY = "consistency_concurrency"
    private const val DESIGN_PATTERNS = "design_patterns"
    private const val OBSERVABILITY_DEVOPS_INCIDENTS = "observability_devops_incidents"
    private const val PROGRAMMING_OOP_BASICS = "programming_oop_basics"
    private const val PERFORMANCE_SCALING_RESILIENCE = "performance_scaling_resilience"
    private const val DATA_STORAGE_SEARCH = "data_storage_search"
    private const val SOFTWARE_ARCHITECTURE = "software_architecture"
    private const val PATTERN_ROLES = "pattern_roles"

    val categories: List<EncyclopediaCategory> = listOf(
        EncyclopediaCategory(
            id = AI_ML_RAG,
            title = "AI, ML i RAG",
            badgeText = "AI",
            description = "Modeli, evaluacija, pretraga znanja i produkcioni AI sistemi.",
            terms = aiMlRagTerms()
        ),
        EncyclopediaCategory(
            id = SECURITY_ACCESS_AUDIT,
            title = "Bezbednost, pristup i audit",
            badgeText = "SEC",
            description = "Kontrola pristupa, revizijski tragovi, bezbednosne provere i incidenti.",
            terms = securityAccessAuditTerms()
        ),
        EncyclopediaCategory(
            id = INTEGRATIONS_BUSINESS_DOMAINS,
            title = "Integracije i poslovni domeni",
            badgeText = "INT",
            description = "Integracije, domenski procesi, plaćanja, logistika, mape, igre i IoT sistemi.",
            terms = integrationsBusinessDomainsTerms()
        ),
        EncyclopediaCategory(
            id = COMMUNICATION_EVENTS_PROCESSING,
            title = "Komunikacija, događaji i obrada",
            badgeText = "EVT",
            description = "Asinhrona komunikacija, događaji, brokeri, redovi, scheduler-i i pozadinska obrada.",
            terms = communicationEventsProcessingTerms()
        ),
        EncyclopediaCategory(
            id = CONSISTENCY_CONCURRENCY,
            title = "Konzistentnost i konkurentnost",
            badgeText = "CONS",
            description = "Transakcije, konkurentni zahtevi, rezervacije, konzistentnost i razrešavanje konflikata.",
            terms = consistencyConcurrencyTerms()
        ),
        EncyclopediaCategory(
            id = DESIGN_PATTERNS,
            title = "Obrasci projektovanja",
            badgeText = "PAT",
            description = "Kreacioni, strukturni i ponašajni obrasci za fleksibilan dizajn softvera.",
            terms = designPatternsTerms()
        ),
        EncyclopediaCategory(
            id = OBSERVABILITY_DEVOPS_INCIDENTS,
            title = "Observability, DevOps i incidenti",
            badgeText = "OPS",
            description = "Monitoring, logovi, tracing, release procesi, incidenti i operativna stabilnost.",
            terms = observabilityDevopsIncidentsTerms()
        ),
        EncyclopediaCategory(
            id = PROGRAMMING_OOP_BASICS,
            title = "Osnove programiranja i OOP",
            badgeText = "OOP",
            description = "Klase, objekti, interfejsi, OOP principi, refaktorisanje i osnovni pojmovi programiranja.",
            terms = programmingOopBasicsTerms()
        ),
        EncyclopediaCategory(
            id = PERFORMANCE_SCALING_RESILIENCE,
            title = "Performanse, skaliranje i otpornost",
            badgeText = "PERF",
            description = "Performanse, skaliranje, dostupnost, otpornost, kapacitet i degradirani režimi rada.",
            terms = performanceScalingResilienceTerms()
        ),
        EncyclopediaCategory(
            id = DATA_STORAGE_SEARCH,
            title = "Podaci, skladištenje i pretraga",
            badgeText = "DATA",
            description = "Baze podataka, keš, skladišta fajlova, indeksi, replike i modeli za čitanje.",
            terms = dataStorageSearchTerms()
        ),
        EncyclopediaCategory(
            id = SOFTWARE_ARCHITECTURE,
            title = "Softverska arhitektura",
            badgeText = "ARCH",
            description = "Arhitektonske odluke, stilovi, slojevi, domeni, tokovi i granice sistema.",
            terms = softwareArchitectureTerms()
        ),
        EncyclopediaCategory(
            id = PATTERN_ROLES,
            title = "Uloge u obrascima",
            badgeText = "ROLE",
            description = "Uloge, učesnici i saradnici unutar klasičnih obrazaca projektovanja.",
            terms = patternRolesTerms()
        )
    )

    private fun aiMlRagTerms(): List<EncyclopediaTerm> = listOf(
        term(
            id = "aggregate_metric",
            titleSr = "Agregatna metrika",
            titleEn = "Aggregate metric",
            explanation = "Agregatna metrika prikazuje zajednički rezultat izračunat na osnovu većeg broja pojedinačnih rezultata. Ona omogućava da se performanse modela predstave jednom vrednošću, kao što je prosečna tačnost na celom evaluacionom skupu. Takva vrednost može sakriti loše ponašanje modela u manjim ili specifičnim grupama podataka."
        ),
        term(
            id = "image_analysis",
            titleSr = "Analiza slike",
            titleEn = "Image analysis",
            explanation = "Analiza slike je proces u kome računar ispituje sadržaj fotografije, skena ili drugog vizuelnog zapisa. Sistem može da prepoznaje objekte, tekst, lica, oblike, odnose između elemenata ili nepravilnosti na slici. U aplikaciji za učenje ovaj postupak može da se koristi za procenu arhitektonskog dijagrama koji je korisnik nacrtao na papiru."
        ),
        term(
            id = "batch_inference",
            titleSr = "Batch inference",
            titleEn = "Batch scoring",
            explanation = "Batch inference je obrada većeg broja podataka ili zahteva u jednoj grupi, umesto pojedinačno u trenutku njihovog nastanka. Najčešće se pokreće periodično i koristi kada rezultat nije potreban korisniku odmah. Primer je noćno izračunavanje preporuka za sve korisnike platforme."
        ),
        term(
            id = "chunk",
            titleSr = "Chunk",
            titleEn = "Segment dokumenta",
            explanation = "Chunk je manji deo dokumenta koji se obrađuje kao zasebna celina. Dokument se deli na chunk-ove zato što AI model često ne može ili ne treba da obradi ceo dokument odjednom. Dobro formiran chunk treba da sadrži dovoljno konteksta da bi njegovo značenje bilo razumljivo."
        ),
        term(
            id = "chunking",
            titleSr = "Chunking",
            titleEn = "Segmentacija dokumenta",
            explanation = "Chunking je postupak deljenja velikog dokumenta na manje segmente koji se nazivaju chunk-ovi. Podela može da se vrši prema broju reči, pasusima, naslovima, rečenicama ili značenju sadržaja. Kvalitet chunking-a direktno utiče na to da li će sistem kasnije pronaći odgovarajući deo dokumenta."
        ),
        term(
            id = "context_window",
            titleSr = "Context window",
            titleEn = "Kontekstni prozor",
            explanation = "Context window predstavlja maksimalnu količinu teksta ili drugih podataka koje model može istovremeno da uzme u obzir. U njega ulaze korisničko pitanje, sistemske instrukcije, istorija razgovora i dodatni dokumenti prosleđeni modelu. Kada se ovaj prostor popuni, deo sadržaja mora da se izbaci, skrati ili sažme."
        ),
        term(
            id = "click_through_rate",
            titleSr = "CTR",
            titleEn = "Click-Through Rate",
            explanation = "CTR je procenat korisnika koji su kliknuli na prikazanu preporuku, oglas ili drugi element. Izračunava se poređenjem broja klikova sa brojem prikazivanja tog elementa. U sistemima preporuka CTR može da pokaže koliko su ponuđeni sadržaji privlačni korisnicima."
        ),
        term(
            id = "document_ingestion_pipeline",
            titleSr = "Document ingestion pipeline",
            titleEn = "Pipeline za unos dokumenata",
            explanation = "Pipeline za unos dokumenata predstavlja niz koraka kroz koje dokument prolazi pre nego što postane dostupan AI sistemu. Ti koraci mogu da obuhvate učitavanje fajla, izdvajanje teksta, čišćenje, chunking, generisanje embedding-a i upis u indeks. Greška u bilo kom delu pipeline-a može da dovede do toga da dokument bude nepotpun, pogrešno obrađen ili nevidljiv tokom pretrage."
        ),
        term(
            id = "domain_accuracy",
            titleSr = "Domenska tačnost",
            titleEn = "Domain accuracy",
            explanation = "Domenska tačnost pokazuje koliko je rezultat modela ispravan u konkretnoj stručnoj oblasti. Odgovor može da bude gramatički dobar i uverljiv, ali da istovremeno bude pogrešan sa pravnog, medicinskog ili tehničkog stanovišta. Zbog toga se domenska tačnost često proverava pomoću stručnjaka i specijalizovanih evaluacionih primera."
        ),
        term(
            id = "edge_case",
            titleSr = "Edge case",
            titleEn = "Rubni slučaj",
            explanation = "Edge case je redak ili neuobičajen slučaj koji se nalazi na granici očekivanog ponašanja sistema. Takvi slučajevi često nisu dobro zastupljeni u testnim podacima, iako mogu da izazovu ozbiljne greške u produkciji. Primer je korisnički zahtev koji sadrži veoma dugačak dokument, neobičan format ili kombinaciju podataka koju sistem ranije nije video."
        ),
        term(
            id = "embedding",
            titleSr = "Embedding",
            titleEn = "Vektorska reprezentacija",
            explanation = "Embedding je brojčana reprezentacija teksta, slike ili drugog podatka u obliku vektora. Slični sadržaji obično dobijaju vektore koji su međusobno blizu u višedimenzionalnom prostoru. Embedding omogućava semantičku pretragu, grupisanje i pronalaženje sličnih sadržaja."
        ),
        term(
            id = "engagement_metric",
            titleSr = "Engagement metrika",
            titleEn = "Engagement metric",
            explanation = "Engagement metrika meri način na koji korisnici stupaju u interakciju sa sadržajem ili funkcionalnošću sistema. Primeri su vreme provedeno na stranici, broj otvorenih preporuka, završene lekcije ili ponovni dolasci korisnika. Ona pokazuje da li je sadržaj korisniku bio dovoljno zanimljiv ili koristan da nastavi interakciju."
        ),
        term(
            id = "evaluation_metric",
            titleSr = "Evaluaciona metrika",
            titleEn = "Evaluation metric",
            explanation = "Evaluaciona metrika je brojčana mera koja se koristi za procenu kvaliteta modela ili sistema. Različiti zadaci koriste različite metrike, kao što su tačnost, preciznost, recall ili prosečna greška. Izbor pogrešne metrike može da stvori utisak da je model dobar iako ne rešava najvažniji poslovni problem."
        ),
        term(
            id = "evaluation_pipeline",
            titleSr = "Evaluacioni pipeline",
            titleEn = "Evaluation pipeline",
            explanation = "Evaluacioni pipeline je automatizovani tok kojim se model testira pre ili nakon puštanja u produkciju. On može da učita evaluacioni skup, pokrene model, izračuna metrike i proveri da li su ispunjeni definisani kriterijumi. Dobro projektovan pipeline sprečava da očigledno lošija verzija modela neprimećeno zameni stabilnu verziju."
        ),
        term(
            id = "evaluation_dataset",
            titleSr = "Evaluacioni skup",
            titleEn = "Evaluation dataset",
            explanation = "Evaluacioni skup je kolekcija primera koja se koristi za proveru kvaliteta modela. Ti primeri treba da predstavljaju realne, važne i problematične situacije sa kojima će se sistem susretati. Ako skup ne obuhvata određene korisnike ili rubne slučajeve, evaluacija može dati previše optimističan rezultat."
        ),
        term(
            id = "false_positive",
            titleSr = "False positive",
            titleEn = "Lažno pozitivan rezultat",
            explanation = "False positive nastaje kada sistem označi slučaj kao pozitivan ili problematičan iako to u stvarnosti nije. Fraud sistem, na primer, može da blokira potpuno ispravnu transakciju zato što ju je pogrešno procenio kao prevaru. Veliki broj lažno pozitivnih rezultata smanjuje poverenje korisnika i stvara nepotreban ručni rad."
        ),
        term(
            id = "feature",
            titleSr = "Feature",
            titleEn = "Ulazna karakteristika modela",
            explanation = "Feature je pojedinačna informacija koju model koristi kao ulaz pri donošenju predikcije. To mogu biti starost korisnika, iznos transakcije, broj prethodnih kupovina ili vreme poslednje aktivnosti. Kvalitet, značenje i pouzdanost feature-a značajno utiču na kvalitet rezultata modela."
        ),
        term(
            id = "feature_drift",
            titleSr = "Feature drift",
            titleEn = "Drift feature-a",
            explanation = "Feature drift predstavlja promenu raspodele vrednosti nekog feature-a tokom vremena. Model je možda treniran kada su korisnici imali određeno ponašanje, dok su u produkciji njihove navike ili okolnosti postale drugačije. Veći drift može da smanji tačnost modela čak i kada se sam model nije menjao."
        ),
        term(
            id = "feature_pipeline",
            titleSr = "Feature Pipeline",
            titleEn = "Pipeline za feature-e",
            explanation = "Feature Pipeline je niz koraka kojima se sirovi podaci pretvaraju u feature-e pogodne za korišćenje u modelu. On može da obuhvati čišćenje podataka, normalizaciju, agregaciju i izračunavanje novih vrednosti. Promena pipeline-a mora pažljivo da se kontroliše jer može da promeni značenje podataka koje model dobija."
        ),
        term(
            id = "feature_store",
            titleSr = "Feature Store",
            titleEn = "Skladište feature-a",
            explanation = "Feature Store je sistem za centralno čuvanje, upravljanje i isporuku feature-a modelima. On pomaže da isti način izračunavanja feature-a bude korišćen tokom treniranja i u produkciji. Time se smanjuje rizik da model u radu dobija drugačije podatke od onih na kojima je treniran."
        ),
        term(
            id = "fine_tuning",
            titleSr = "Fine-tuning",
            titleEn = "Dodatno treniranje modela",
            explanation = "Fine-tuning je dodatno treniranje već postojećeg modela na užem i specifičnijem skupu podataka. Koristi se kada model treba bolje da usvoji određeni stil, terminologiju, format odgovora ili vrstu zadatka. Fine-tuning nije idealno rešenje za činjenice koje se često menjaju jer bi model morao ponovo da se trenira nakon svake veće izmene."
        ),
        term(
            id = "answer_fluency",
            titleSr = "Fluentnost odgovora",
            titleEn = "Answer fluency",
            explanation = "Fluentnost odgovora opisuje koliko tekst deluje prirodno, jasno i gramatički pravilno. Veoma fluentan odgovor može da zvuči uverljivo čak i kada sadrži netačne informacije. Zbog toga fluentnost ne treba poistovećivati sa činjenicama, relevantnošću ili domenskom tačnošću."
        ),
        term(
            id = "fraud_detection",
            titleSr = "Fraud detekcija",
            titleEn = "Fraud detection",
            explanation = "Fraud detekcija je proces prepoznavanja mogućih prevara, zloupotreba ili sumnjivih aktivnosti. Sistem može da analizira iznos, lokaciju, uređaj, ponašanje korisnika i istoriju transakcija. Rezultat se često koristi za odobravanje, blokiranje ili slanje transakcije na dodatnu proveru."
        ),
        term(
            id = "gpu",
            titleSr = "GPU",
            titleEn = "Graphics Processing Unit",
            explanation = "GPU je procesorska jedinica posebno pogodna za veliki broj paralelnih matematičkih operacija. Zbog toga se često koristi za treniranje i izvršavanje velikih AI modela. GPU resursi su skupi, pa je važno pravilno rasporediti kratke, interaktivne i batch zahteve."
        ),
        term(
            id = "grounding",
            titleSr = "Grounding",
            titleEn = "Zasnivanje odgovora na izvorima",
            explanation = "Grounding znači da se odgovor AI sistema zasniva na konkretnim podacima ili dokumentima, a ne samo na opštem znanju modela. Sistem može da prikaže izvore ili delove teksta koji podržavaju generisani odgovor. Time se povećavaju proverljivost odgovora i mogućnost uočavanja greške."
        ),
        term(
            id = "human_in_the_loop",
            titleSr = "Human-in-the-loop",
            titleEn = "Čovek u petlji odlučivanja",
            explanation = "Human-in-the-loop je pristup u kome čovek učestvuje u proveri, potvrđivanju ili ispravljanju rezultata automatizovanog sistema. Posebno je važan kada AI donosi predloge koji mogu imati ozbiljne pravne, finansijske, medicinske ili bezbednosne posledice. Model može dati preporuku, ali konačna odluka ostaje na ovlašćenom korisniku."
        ),
        term(
            id = "inference",
            titleSr = "Inference",
            titleEn = "AI inference",
            explanation = "Inference je proces u kome istrenirani model obrađuje novi ulaz i proizvodi rezultat. Rezultat može biti klasifikacija, preporuka, odgovor, procena rizika ili generisani sadržaj. Za razliku od treniranja, inference koristi već naučene parametre modela bez njihovog menjanja."
        ),
        term(
            id = "online_inference",
            titleSr = "Interaktivni inference",
            titleEn = "Online inference",
            explanation = "Interaktivni inference je izvršavanje modela u trenutku kada korisnik ili drugi sistem pošalje zahtev. Rezultat je potreban brzo, pa su latencija i stabilnost veoma važni. Primer je AI asistent koji treba odmah da odgovori na korisničko pitanje."
        ),
        term(
            id = "case_classification",
            titleSr = "Klasifikacija slučaja",
            titleEn = "Case classification",
            explanation = "Klasifikacija slučaja je postupak svrstavanja konkretnog zahteva ili podatka u jednu od unapred definisanih kategorija. Rezultat klasifikacije može da odredi koja pravila, modeli ili dalji koraci treba da se primene. Pogrešna klasifikacija može da pošalje slučaj kroz neodgovarajući tok obrade."
        ),
        term(
            id = "long_context_approach",
            titleSr = "Long-context pristup",
            titleEn = "Long-context approach",
            explanation = "Long-context pristup koristi model koji može da obradi veoma veliku količinu teksta u jednom zahtevu. Time se modelu može proslediti više dokumenata ili duža istorija razgovora bez prethodnog velikog skraćivanja. Veći kontekst ipak ne garantuje da će model pravilno pronaći i koristiti najvažniji deo sadržaja."
        ),
        term(
            id = "machine_learning",
            titleSr = "Mašinsko učenje",
            titleEn = "Machine Learning",
            explanation = "Mašinsko učenje je oblast veštačke inteligencije u kojoj sistemi uče obrasce iz podataka umesto da za svaki slučaj dobiju ručno napisana pravila. Model tokom treniranja prilagođava svoje parametre kako bi bolje rešavao zadatak. Primeri primene su preporuke, klasifikacija slika, predviđanje potražnje i otkrivanje prevara."
        ),
        term(
            id = "machine_learning_model",
            titleSr = "Model",
            titleEn = "Machine learning model",
            explanation = "Model je matematička ili računarska struktura koja je naučila obrasce iz podataka. On prima ulazne podatke i na osnovu naučenih parametara proizvodi predikciju ili drugi rezultat. Kvalitet modela zavisi od podataka, načina treniranja, izabranih feature-a i postupka evaluacije."
        ),
        term(
            id = "model_rollout",
            titleSr = "Model rollout",
            titleEn = "Puštanje modela u produkciju",
            explanation = "Model rollout je kontrolisano uvođenje nove verzije modela u produkciono okruženje. Nova verzija se često prvo uključuje za manji procenat korisnika kako bi se uporedila sa postojećom verzijom. Ako se pojave problemi, rollout se može zaustaviti ili vratiti na prethodni model."
        ),
        term(
            id = "model_serving",
            titleSr = "Model serving",
            titleEn = "Serving Layer",
            explanation = "Model serving je deo sistema koji čini istrenirani model dostupnim aplikacijama i korisnicima. On prima zahteve, priprema ulaze, pokreće inference i vraća rezultat. Serving sloj mora da rešava skaliranje, verzionisanje, latenciju, monitoring i raspodelu resursa."
        ),
        term(
            id = "multimodal_model",
            titleSr = "Multimodalni model",
            titleEn = "Multimodal model",
            explanation = "Multimodalni model može da obrađuje više vrsta podataka, kao što su tekst, slike, zvuk ili video. Takav model može da poveže informacije iz različitih formata u jednom zadatku. Primer je sistem koji dobija tekst zadatka i fotografiju nacrtane arhitekture, pa zatim daje zajedničku procenu."
        ),
        term(
            id = "model_explainability",
            titleSr = "Objašnjivost modela",
            titleEn = "Model explainability",
            explanation = "Objašnjivost modela predstavlja mogućnost da se razume zašto je model dao određeni rezultat. Objašnjenje može da pokaže najvažnije feature-e, korišćena pravila ili izvore koji su uticali na odluku. Ona je posebno važna kada korisnik mora da proveri, ospori ili opravda odluku sistema."
        ),
        term(
            id = "ocr",
            titleSr = "OCR",
            titleEn = "Optical Character Recognition",
            explanation = "OCR je tehnologija koja prepoznaje tekst na fotografijama, skenovima i drugim slikama. Ona pretvara vizuelni prikaz slova u tekst koji računar može da pretražuje i obrađuje. Kvalitet rezultata zavisi od rezolucije slike, fonta, osvetljenja, ugla fotografisanja i jezika dokumenta."
        ),
        term(
            id = "offline_feature",
            titleSr = "Offline feature",
            titleEn = "Offline feature",
            explanation = "Offline feature je feature koji se izračunava unapred, najčešće nad istorijskim podacima i van trenutnog korisničkog zahteva. Koristi se tokom treniranja modela, evaluacije ili periodičnog batch scoring-a. Primer je prosečna vrednost kupovina korisnika tokom prethodnih šest meseci."
        ),
        term(
            id = "online_feature",
            titleSr = "Online feature",
            titleEn = "Online feature",
            explanation = "Online feature je podatak koji mora da bude dostupan modelu u trenutku izvršavanja inference-a. On često predstavlja najnovije stanje korisnika, transakcije ili sistema. Primer je broj neuspešnih pokušaja plaćanja tokom poslednjih pet minuta."
        ),
        term(
            id = "personalization",
            titleSr = "Personalizacija",
            titleEn = "Personalization",
            explanation = "Personalizacija je prilagođavanje sadržaja, preporuka ili ponašanja sistema konkretnom korisniku. Sistem može da koristi istoriju aktivnosti, interesovanja, lokaciju ili prethodne izbore korisnika. Cilj je da svaki korisnik dobije relevantnije i korisnije iskustvo."
        ),
        term(
            id = "pass_threshold",
            titleSr = "Prag prolaska",
            titleEn = "Pass threshold",
            explanation = "Prag prolaska je minimalna vrednost koju model ili sistem mora da ostvari da bi prošao evaluaciju. Može biti definisan za jednu metriku ili za više kritičnih uslova istovremeno. Previše blag prag može propustiti loš model, dok previše strog prag može zaustaviti korisna poboljšanja."
        ),
        term(
            id = "risk_threshold",
            titleSr = "Prag rizika",
            titleEn = "Risk threshold",
            explanation = "Prag rizika je vrednost iznad ili ispod koje sistem pokreće određenu odluku ili dodatnu proveru. Fraud sistem može da blokira transakciju kada procenjeni rizik pređe definisani prag. Izbor praga predstavlja kompromis između propuštanja stvarnih rizika i stvaranja lažno pozitivnih rezultata."
        ),
        term(
            id = "rag",
            titleSr = "RAG",
            titleEn = "Retrieval-Augmented Generation",
            explanation = "RAG je pristup u kome sistem prvo pronalazi relevantne informacije iz dokumenata, a zatim ih prosleđuje generativnom modelu. Model na osnovu pronađenog konteksta formira odgovor koji može da bude zasnovan na aktuelnim i proverljivim izvorima. Kvalitet RAG sistema zavisi i od retrieval dela i od sposobnosti modela da pravilno koristi pronađeni sadržaj."
        ),
        term(
            id = "ranking_model",
            titleSr = "Ranking model",
            titleEn = "Model za rangiranje",
            explanation = "Ranking model određuje redosled kandidata, preporuka ili rezultata prema procenjenoj relevantnosti. On svakom kandidatu dodeljuje skor i na vrh postavlja one za koje očekuje da su najkorisniji korisniku. Koristi se u sistemima preporuka, internet pretrazi, oglašavanju i prikazu proizvoda."
        ),
        term(
            id = "recall",
            titleSr = "Recall",
            titleEn = "Odziv pretrage",
            explanation = "Recall meri koliko je stvarno relevantnih rezultata sistem uspeo da pronađe. Visok recall znači da je pronađen veliki deo korisnih dokumenata, čak i ako su među njima prisutni i manje relevantni rezultati. U RAG sistemu nizak recall može da dovede do toga da model nikada ne dobije ključni dokument potreban za odgovor."
        ),
        term(
            id = "recommendation_engine",
            titleSr = "Recommendation engine",
            titleEn = "Sistem preporuka",
            explanation = "Recommendation engine je sistem koji bira i rangira sadržaje, proizvode ili aktivnosti koje bi mogle biti relevantne korisniku. On koristi podatke o korisniku, sadržaju i ponašanju drugih korisnika. Primeri su preporuke filmova, proizvoda, kurseva ili sledećih zadataka za učenje."
        ),
        term(
            id = "regression_test",
            titleSr = "Regresioni test",
            titleEn = "Regression test",
            explanation = "Regresioni test proverava da li je nova izmena pokvarila funkcionalnost koja je ranije ispravno radila. U AI sistemu može da proverava da li nova verzija modela i dalje pravilno rešava poznate primere. Takvi testovi su važni zato što poboljšanje jednog segmenta može nenamerno da pogorša drugi."
        ),
        term(
            id = "ai_release_gate",
            titleSr = "Release gate",
            titleEn = "AI release gate",
            explanation = "Release gate je kontrolna tačka koja odlučuje da li model sme da bude pušten u produkciju. On proverava evaluacione metrike, kritične segmente, bezbednosne uslove i druge zahteve sistema. Model koji ne ispunjava definisane kriterijume mora biti zaustavljen bez obzira na dobar prosečan rezultat."
        ),
        term(
            id = "retrieval",
            titleSr = "Retrieval",
            titleEn = "Pronalaženje relevantnog konteksta",
            explanation = "Retrieval je proces pronalaženja dokumenata ili delova teksta koji su najrelevantniji za korisničko pitanje. Najčešće koristi ključne reči, embedding-e, vektorsku pretragu ili kombinaciju više metoda. U RAG sistemu retrieval određuje koji sadržaj će generativni model dobiti kao osnovu za odgovor."
        ),
        term(
            id = "risk_scoring",
            titleSr = "Risk scoring",
            titleEn = "Bodovanje rizika",
            explanation = "Risk scoring je postupak dodeljivanja brojčane procene rizika konkretnom slučaju. Rezultat može da predstavlja verovatnoću prevare, neplaćanja, kvara ili drugog neželjenog događaja. Skor se zatim poredi sa pragovima koji određuju automatsku odluku ili potrebu za ručnom proverom."
        ),
        term(
            id = "rule_engine",
            titleSr = "Rule engine",
            titleEn = "Mehanizam poslovnih pravila",
            explanation = "Rule engine je komponenta koja izvršava skup poslovnih pravila nad ulaznim podacima. Pravila mogu da odluče da li se zahtev odobrava, koji popust se primenjuje ili kojim tokom slučaj treba da nastavi. Izdvajanje pravila iz glavnog koda olakšava njihove izmene, testiranje i upravljanje."
        ),
        term(
            id = "rule_set",
            titleSr = "Rule set",
            titleEn = "Skup pravila",
            explanation = "Rule set je organizovana grupa pravila koja se primenjuju u određenom kontekstu. Različite vrste korisnika, proizvoda ili regiona mogu da koriste različite skupove pravila. Dobra organizacija rule set-ova sprečava da sistem pri svakom zahtevu proverava veliki broj potpuno nerelevantnih pravila."
        ),
        term(
            id = "segment_metric",
            titleSr = "Segmentna metrika",
            titleEn = "Segment metric",
            explanation = "Segmentna metrika prikazuje performanse modela za određenu grupu korisnika ili podataka. Segment može biti definisan prema regionu, uzrastu, tipu uređaja, vrsti zahteva ili drugoj osobini. Ona pomaže da se otkrije problem koji bi bio sakriven u dobroj agregatnoj metrici."
        ),
        term(
            id = "semantic_chunking",
            titleSr = "Semantički chunking",
            titleEn = "Semantic chunking",
            explanation = "Semantički chunking deli dokument prema značenju i logičkim celinama, a ne samo prema fiksnom broju znakova ili reči. Cilj je da se povezane rečenice, definicije i odeljci zadrže u istom segmentu. Time se povećava verovatnoća da retrieval vrati celovit i razumljiv kontekst."
        ),
        term(
            id = "semantic_drift",
            titleSr = "Semantički drift",
            titleEn = "Semantic drift",
            explanation = "Semantički drift nastaje kada feature ili podatak zadrži isti naziv i format, ali mu se promeni stvarno značenje. Model tada dobija tehnički validnu vrednost koju tumači na način naučen tokom treniranja, iako ta vrednost više ne predstavlja istu pojavu. Ovaj problem je opasniji od obične promene raspodele jer može ostati neprimećen u standardnim tehničkim proverama."
        ),
        term(
            id = "sla",
            titleSr = "SLA",
            titleEn = "Service Level Agreement",
            explanation = "SLA je dogovoreni nivo kvaliteta usluge koji sistem treba da obezbedi. Može da definiše maksimalnu latenciju, dostupnost, procenat uspešnih zahteva ili vreme rešavanja incidenta. Različite vrste AI zahteva mogu imati različite SLA ciljeve u zavisnosti od njihove važnosti i hitnosti."
        ),
        term(
            id = "evaluation_blind_spot",
            titleSr = "Slepa tačka evaluacije",
            titleEn = "Evaluation blind spot",
            explanation = "Slepa tačka evaluacije je važan problem ili grupa slučajeva koju postojeća evaluacija ne prepoznaje. Model može prolaziti sve testove, a zatim praviti ozbiljne greške u produkciji jer takvi primeri nisu zastupljeni u evaluacionom skupu. Slepe tačke se često otkrivaju analizom produkcionih incidenata i ponašanja različitih korisničkih segmenata."
        ),
        term(
            id = "metric_weighting",
            titleSr = "Težina metrike",
            titleEn = "Metric weighting",
            explanation = "Težina metrike određuje koliko određena metrika utiče na ukupan rezultat evaluacije. Kritična bezbednosna metrika može dobiti veću težinu od manje važne metrike korisničkog iskustva. Loše postavljene težine mogu omogućiti da dobar rezultat u jednoj oblasti prikrije ozbiljan problem u drugoj."
        ),
        term(
            id = "training_serving_skew",
            titleSr = "Training-serving skew",
            titleEn = "Neusklađenost treninga i serving-a",
            explanation = "Training-serving skew nastaje kada model tokom rada dobija drugačije podatke ili drugačije izračunate feature-e nego tokom treniranja. Model može biti kvalitetno istreniran, ali će u produkciji davati lošije rezultate zbog ove razlike. Problem se smanjuje korišćenjem zajedničkih definicija feature-a, kontrolom verzija i pažljivim monitoringom podataka."
        ),
        term(
            id = "transcription",
            titleSr = "Transkripcija",
            titleEn = "Transcription",
            explanation = "Transkripcija je pretvaranje govora ili zvučnog zapisa u pisani tekst. AI sistem može da prepozna izgovorene reči, govornike, vremenske oznake i ponekad druge osobine govora. Koristi se za sastanke, predavanja, intervjue, video-sadržaje i glasovne komande."
        ),
        term(
            id = "vector_index",
            titleSr = "Vektorski indeks",
            titleEn = "Vector index",
            explanation = "Vektorski indeks je struktura koja omogućava brzo pronalaženje embedding-a sličnih zadatom vektoru. Umesto potpunog poređenja sa svakim zapisom, indeks koristi optimizovane metode za približno pronalaženje najbližih rezultata. U RAG sistemu on se često koristi za nalaženje semantički relevantnih chunk-ova dokumenata."
        ),
        term(
            id = "model_version",
            titleSr = "Verzija modela",
            titleEn = "Model version",
            explanation = "Verzija modela je jasno označena varijanta modela sa određenim parametrima, kodom, podacima i konfiguracijom. Verzionisanje omogućava da se utvrdi koji model je proizveo određeni rezultat i da se sistem vrati na raniju stabilnu verziju. Bez kontrole verzija teško je pouzdano porediti modele, analizirati incidente i ponoviti rezultate."
        ),
        term(
            id = "artificial_intelligence",
            titleSr = "Veštačka inteligencija",
            titleEn = "Artificial Intelligence",
            explanation = "Veštačka inteligencija je oblast računarstva koja razvija sisteme sposobne da obavljaju zadatke koji obično zahtevaju ljudsko zaključivanje ili opažanje. Takvi zadaci mogu da uključuju razumevanje jezika, prepoznavanje slika, planiranje, preporuke i donošenje odluka. Mašinsko učenje je jedan od glavnih pristupa za izgradnju sistema veštačke inteligencije."
        )
    )

    private fun securityAccessAuditTerms(): List<EncyclopediaTerm> = listOf(
        term(
            id = "anti_cheat",
            titleSr = "Anti-cheat",
            titleEn = "Sprečavanje varanja",
            explanation = "Anti-cheat je skup pravila, provera i tehničkih mehanizama koji otkrivaju ili sprečavaju varanje u video-igrama. Sistem može da proverava neobične akcije, nedozvoljene izmene klijenta ili razlike između klijentskog i serverskog stanja. Cilj je da svi igrači učestvuju pod jednakim uslovima i da rezultat igre ostane pouzdan.",
            categoryId = SECURITY_ACCESS_AUDIT
        ),
        term(
            id = "antivirus_scanning",
            titleSr = "Antivirusno skeniranje",
            titleEn = "Antivirus scanning",
            explanation = "Antivirusno skeniranje je proces provere fajla radi pronalaženja poznatih virusa i drugih štetnih sadržaja. Provera se obično obavlja pre nego što dokument postane dostupan korisnicima ili drugim delovima sistema. Ako se pronađe pretnja, fajl se može blokirati, izolovati ili poslati na dodatnu analizu.",
            categoryId = SECURITY_ACCESS_AUDIT
        ),
        term(
            id = "audit",
            titleSr = "Audit",
            titleEn = "Revizijski trag",
            explanation = "Audit je sistematsko beleženje i proveravanje važnih aktivnosti u sistemu. Omogućava da se utvrdi ko je izvršio određenu operaciju, kada je to urađeno i nad kojim podacima. Koristi se za bezbednost, rešavanje sporova, regulatornu usklađenost i analizu incidenata.",
            categoryId = SECURITY_ACCESS_AUDIT
        ),
        term(
            id = "audit_event",
            titleSr = "Audit događaj",
            titleEn = "Audit event",
            explanation = "Audit događaj je strukturisan zapis o jednoj aktivnosti koja je važna za proveru rada sistema. Može da sadrži identitet korisnika, vrstu operacije, vreme, rezultat i pogođeni resurs. Primer audit događaja je zapis da je administrator promenio korisničku ulogu.",
            categoryId = SECURITY_ACCESS_AUDIT
        ),
        term(
            id = "audit_log",
            titleSr = "Audit log",
            titleEn = "Dnevnik revizije",
            explanation = "Audit log je skladište u kome se čuvaju audit događaji nastali tokom rada sistema. On omogućava naknadno pretraživanje aktivnosti korisnika, servisa i administratora. Audit log treba da bude zaštićen od neovlašćenog menjanja ili brisanja.",
            categoryId = SECURITY_ACCESS_AUDIT
        ),
        term(
            id = "audit_schema",
            titleSr = "Audit schema",
            titleEn = "Šema audit zapisa",
            explanation = "Audit schema definiše obavezna i opciona polja koja svaki audit događaj treba da sadrži. Zajednička šema omogućava da različiti servisi beleže aktivnosti na ujednačen način. Primer polja su vreme događaja, korisnik, akcija, resurs, prethodno stanje i novo stanje.",
            categoryId = SECURITY_ACCESS_AUDIT
        ),
        term(
            id = "audit_trail",
            titleSr = "Audit trail",
            titleEn = "Audit trag",
            explanation = "Audit trail je hronološki niz zapisa koji prikazuje kako je određeni podatak, predmet ili proces menjan kroz vreme. On povezuje pojedinačne audit događaje u razumljivu istoriju aktivnosti. Pomoću njega se može rekonstruisati ceo tok od početnog do trenutnog stanja.",
            categoryId = SECURITY_ACCESS_AUDIT
        ),
        term(
            id = "authentication",
            titleSr = "Autentikacija",
            titleEn = "Authentication",
            explanation = "Autentikacija je proces provere identiteta korisnika, uređaja ili drugog sistema. Najčešće se zasniva na lozinci, tokenu, sertifikatu, biometrijskom podatku ili kombinaciji više faktora. Njeno osnovno pitanje je: „Ko pokušava da pristupi sistemu?“",
            categoryId = SECURITY_ACCESS_AUDIT
        ),
        term(
            id = "authorization",
            titleSr = "Autorizacija",
            titleEn = "Authorization",
            explanation = "Autorizacija određuje koje operacije autentifikovani korisnik sme da izvrši. Ona proverava dozvole, uloge i pravila pristupa konkretnom resursu. Njeno osnovno pitanje je: „Da li ovaj korisnik sme da uradi traženu radnju?“",
            categoryId = SECURITY_ACCESS_AUDIT
        ),
        term(
            id = "security_incident",
            titleSr = "Bezbednosni incident",
            titleEn = "Security incident",
            explanation = "Bezbednosni incident je događaj koji ugrožava poverljivost, integritet ili dostupnost sistema i podataka. Primeri su neovlašćen pristup, krađa naloga, zaraženi fajl ili curenje podataka. Incident zahteva evidentiranje, analizu, ograničavanje štete i odgovarajuću sanaciju.",
            categoryId = SECURITY_ACCESS_AUDIT
        ),
        term(
            id = "security_cleared_document",
            titleSr = "Bezbednosno odobren dokument",
            titleEn = "Security-cleared document",
            explanation = "Bezbednosno odobren dokument je fajl koji je prošao sve propisane bezbednosne provere. Sistem je potvrdio da u dokumentu nisu pronađene poznate pretnje ili nedozvoljen sadržaj. Tek nakon takve potvrde dokument može biti prosleđen korisnicima ili daljoj obradi.",
            categoryId = SECURITY_ACCESS_AUDIT
        ),
        term(
            id = "biometric_verification",
            titleSr = "Biometrijska verifikacija",
            titleEn = "Biometric verification",
            explanation = "Biometrijska verifikacija proverava identitet osobe pomoću fizičkih ili ponašajnih karakteristika. Može da koristi lice, otisak prsta, glas, šarenicu oka ili drugi biometrijski podatak. Rezultat se obično poredi sa prethodno sačuvanim referentnim uzorkom.",
            categoryId = SECURITY_ACCESS_AUDIT
        ),
        term(
            id = "central_audit_service",
            titleSr = "Centralizovani audit servis",
            titleEn = "Central audit service",
            explanation = "Centralizovani audit servis prima i čuva audit događaje iz više delova sistema. On obezbeđuje zajednički format, pravila pristupa, pretragu i politiku čuvanja zapisa. Time se izbegava da svaki servis nezavisno i neujednačeno rešava audit.",
            categoryId = SECURITY_ACCESS_AUDIT
        ),
        term(
            id = "effective_permission",
            titleSr = "Efektivno pravo pristupa",
            titleEn = "Effective permission",
            explanation = "Efektivno pravo pristupa predstavlja stvarnu dozvolu koju korisnik ima nakon primene svih uloga i pravila. Ono može zavisiti od direktnih dozvola, članstva u grupama, privremenih uloga i ograničenja konkretnog resursa. Korisnik može formalno imati određenu ulogu, ali ipak nemati efektivno pravo nad određenim podatkom.",
            categoryId = SECURITY_ACCESS_AUDIT
        ),
        term(
            id = "incident_escalation",
            titleSr = "Eskalacija incidenta",
            titleEn = "Incident escalation",
            explanation = "Eskalacija incidenta je prosleđivanje incidenta višem nivou odgovornosti ili stručnijem timu. Pokreće se kada je problem ozbiljan, dugo traje ili prevazilazi ovlašćenja prvog tima. Cilj je da incident dobije odgovarajući prioritet, resurse i pažnju.",
            categoryId = SECURITY_ACCESS_AUDIT
        ),
        term(
            id = "user_identity",
            titleSr = "Identitet korisnika",
            titleEn = "User identity",
            explanation = "Identitet korisnika je skup podataka koji u sistemu predstavlja jednu osobu ili nalog. Može da uključuje jedinstveni identifikator, korisničko ime, kontakt podatke i povezane uloge. Sistem koristi identitet da poveže aktivnosti, dozvole i podatke sa odgovarajućim korisnikom.",
            categoryId = SECURITY_ACCESS_AUDIT
        ),
        term(
            id = "device_identity",
            titleSr = "Identitet uređaja",
            titleEn = "Device identity",
            explanation = "Identitet uređaja predstavlja jedinstveno prepoznatljiv telefon, računar, senzor ili drugi uređaj. Može se utvrđivati pomoću sertifikata, ključeva, tokena ili registrovanog identifikatora. Na osnovu njega sistem može da proveri da li je uređaj pouzdan i ovlašćen za komunikaciju.",
            categoryId = SECURITY_ACCESS_AUDIT
        ),
        term(
            id = "compression",
            titleSr = "Kompresija",
            titleEn = "Compression",
            explanation = "Kompresija je postupak smanjivanja veličine podataka radi lakšeg čuvanja ili prenosa. Može biti bez gubitaka, kada se originalni sadržaj potpuno obnavlja, ili sa gubicima, kada se deo informacija odbacuje. Kompresija ne štiti tajnost podataka i zato nije zamena za šifrovanje.",
            categoryId = SECURITY_ACCESS_AUDIT
        ),
        term(
            id = "context_aware_access_control",
            titleSr = "Kontekstualna kontrola pristupa",
            titleEn = "Context-aware access control",
            explanation = "Kontekstualna kontrola pristupa donosi odluku na osnovu više informacija od same korisničke uloge. Može da proverava lokaciju, vreme, uređaj, trenutno zaduženje, vlasništvo nad predmetom ili nivo rizika. Tako se sprečava pristup koji je formalno dozvoljen, ali nije opravdan u konkretnoj situaciji.",
            categoryId = SECURITY_ACCESS_AUDIT
        ),
        term(
            id = "access_control",
            titleSr = "Kontrola pristupa",
            titleEn = "Access control",
            explanation = "Kontrola pristupa je skup pravila i mehanizama koji određuju ko može da koristi određene podatke ili funkcije. Ona obuhvata identifikovanje korisnika, proveru dozvola i sprovođenje odluke o pristupu. Dobra kontrola pristupa primenjuje princip da korisnik dobije samo dozvole koje su mu stvarno potrebne.",
            categoryId = SECURITY_ACCESS_AUDIT
        ),
        term(
            id = "user_role",
            titleSr = "Korisnička uloga",
            titleEn = "User role",
            explanation = "Korisnička uloga predstavlja skup dozvola povezanih sa određenom odgovornošću u sistemu. Primeri uloga su korisnik, operater, menadžer i administrator. Dodeljivanjem uloge korisniku pojednostavljuje se upravljanje većim brojem povezanih dozvola.",
            categoryId = SECURITY_ACCESS_AUDIT
        ),
        term(
            id = "malware_scanning",
            titleSr = "Malware skeniranje",
            titleEn = "Malware scanning",
            explanation = "Malware skeniranje proverava fajlove, procese ili mrežni sadržaj radi pronalaženja različitih vrsta zlonamernog softvera. Pored klasičnih virusa, može da otkriva trojance, ransomware, spyware i sumnjivo ponašanje. Napredna provera može kombinovati poznate potpise, analizu ponašanja i izvršavanje u izolovanom okruženju.",
            categoryId = SECURITY_ACCESS_AUDIT
        ),
        term(
            id = "unauthorized_access",
            titleSr = "Neovlašćen pristup",
            titleEn = "Unauthorized access",
            explanation = "Neovlašćen pristup nastaje kada osoba, uređaj ili servis koristi resurs bez odgovarajuće dozvole. Može biti posledica ukradenih podataka za prijavu, greške u pravilima ili namernog zaobilaženja zaštite. Takav pristup treba blokirati, evidentirati i analizirati kao mogući bezbednosni incident.",
            categoryId = SECURITY_ACCESS_AUDIT
        ),
        term(
            id = "immutable_history",
            titleSr = "Nepromenljiva istorija",
            titleEn = "Immutable history",
            explanation = "Nepromenljiva istorija je evidencija čiji postojeći zapisi ne mogu biti naknadno menjani ili brisani bez vidljivog traga. Ispravke se obično dodaju kao novi događaji umesto prepravljanja starih zapisa. Time se čuvaju poverenje, sledljivost i mogućnost pouzdane rekonstrukcije prošlih aktivnosti.",
            categoryId = SECURITY_ACCESS_AUDIT
        ),
        term(
            id = "unscanned_document",
            titleSr = "Neproveren dokument",
            titleEn = "Unscanned document",
            explanation = "Neproveren dokument je fajl koji još nije prošao obavezne bezbednosne provere. Njegov sadržaj se zato ne smatra pouzdanim, čak i kada naziv i format izgledaju ispravno. Sistem treba da ograniči otvaranje, preuzimanje ili dalju obradu takvog dokumenta.",
            categoryId = SECURITY_ACCESS_AUDIT
        ),
        term(
            id = "data_retention_policy",
            titleSr = "Politika zadržavanja podataka",
            titleEn = "Data retention policy",
            explanation = "Politika zadržavanja podataka određuje koliko dugo se određeni podaci čuvaju u sistemu. Ona može da definiše rokove za audit zapise, korisničke podatke, dokumente, rezervne kopije i tehničke logove. Nakon isteka roka podaci se brišu, anonimizuju ili arhiviraju prema poslovnim i zakonskim pravilima.",
            categoryId = SECURITY_ACCESS_AUDIT
        ),
        term(
            id = "business_context_of_access",
            titleSr = "Poslovni kontekst pristupa",
            titleEn = "Business context of access",
            explanation = "Poslovni kontekst pristupa opisuje razlog i okolnosti zbog kojih korisnik pristupa određenom resursu. Sistem može da proveri da li je korisnik zadužen za predmet, pripada odgovarajućoj jedinici ili trenutno obavlja relevantnu ulogu. Time se razlikuje tehnički mogući pristup od poslovno opravdanog pristupa.",
            categoryId = SECURITY_ACCESS_AUDIT
        ),
        term(
            id = "legal_verifiability",
            titleSr = "Pravna proverljivost",
            titleEn = "Legal verifiability",
            explanation = "Pravna proverljivost znači da sistem može pouzdano da dokaže kako je određena odluka ili promena nastala. Zahteva jasne zapise o korisniku, vremenu, korišćenim podacima, pravilima i rezultatu operacije. Takva evidencija je važna kod ugovora, finansijskih transakcija, regulatornih postupaka i drugih pravno značajnih procesa.",
            categoryId = SECURITY_ACCESS_AUDIT
        ),
        term(
            id = "overprivileged_access",
            titleSr = "Preširoko pravo pristupa",
            titleEn = "Overprivileged access",
            explanation = "Preširoko pravo pristupa postoji kada korisnik ima više dozvola nego što mu je potrebno za posao. Takva prava povećavaju rizik od slučajne greške, zloupotrebe ili većeg uticaja kompromitovanog naloga. Problem se smanjuje redovnim pregledom dozvola i primenom principa najmanjih privilegija.",
            categoryId = SECURITY_ACCESS_AUDIT
        ),
        term(
            id = "temporary_role",
            titleSr = "Privremena uloga",
            titleEn = "Temporary role",
            explanation = "Privremena uloga je skup dozvola dodeljen korisniku na ograničeno vreme ili za konkretan zadatak. Koristi se, na primer, kada zaposleni menja odsutnog kolegu ili kratkotrajno radi na posebnom predmetu. Uloga treba automatski da istekne ili bude uklonjena kada prestane razlog zbog kog je dodeljena.",
            categoryId = SECURITY_ACCESS_AUDIT
        ),
        term(
            id = "rbac",
            titleSr = "RBAC",
            titleEn = "Role-Based Access Control",
            explanation = "RBAC je model kontrole pristupa u kome se dozvole dodeljuju ulogama, a korisnici se povezuju sa tim ulogama. Administrator tako ne mora posebno da podešava svaku dozvolu za svakog korisnika. RBAC olakšava upravljanje pristupom, ali ponekad mora biti dopunjen proverom poslovnog konteksta.",
            categoryId = SECURITY_ACCESS_AUDIT
        ),
        term(
            id = "incident_remediation",
            titleSr = "Sanacija incidenta",
            titleEn = "Incident remediation",
            explanation = "Sanacija incidenta obuhvata aktivnosti kojima se uklanjaju uzrok i posledice bezbednosnog problema. To može uključivati uklanjanje zlonamernog sadržaja, promenu kompromitovanih pristupa, ispravku ranjivosti i vraćanje podataka. Nakon sanacije sistem se pažljivo prati kako bi se potvrdilo da je problem zaista uklonjen.",
            categoryId = SECURITY_ACCESS_AUDIT
        ),
        term(
            id = "security_watchlist",
            titleSr = "Security watchlist",
            titleEn = "Bezbednosna lista za proveru",
            explanation = "Security watchlist je lista identiteta, uređaja, adresa ili drugih entiteta koji zahtevaju posebnu proveru. Sistem može da je koristi tokom autentikacije, prelaska granice, finansijske transakcije ili pristupa zaštićenom resursu. Podudaranje sa listom ne mora automatski značiti zabranu, ali često pokreće dodatnu ručnu kontrolu.",
            categoryId = SECURITY_ACCESS_AUDIT
        ),
        term(
            id = "traceability",
            titleSr = "Sledljivost",
            titleEn = "Traceability",
            explanation = "Sledljivost je mogućnost praćenja porekla, promena i kretanja podatka ili objekta kroz sistem. Ona povezuje događaje, korisnike, verzije, procese i rezultate u razumljiv lanac. Dobra sledljivost olakšava analizu grešaka, audit, povlačenje proizvoda i dokazivanje ispravnosti procesa.",
            categoryId = SECURITY_ACCESS_AUDIT
        ),
        term(
            id = "encryption",
            titleSr = "Šifrovanje",
            titleEn = "Encryption",
            explanation = "Šifrovanje pretvara čitljive podatke u oblik koji nije razumljiv bez odgovarajućeg ključa. Koristi se za zaštitu podataka tokom prenosa i tokom čuvanja. Za razliku od kompresije, njegov osnovni cilj je očuvanje poverljivosti sadržaja.",
            categoryId = SECURITY_ACCESS_AUDIT
        )
    )

    private fun integrationsBusinessDomainsTerms(): List<EncyclopediaTerm> = listOf(
        term(
            id = "3d_model",
            titleSr = "3D model",
            titleEn = "3D model",
            explanation = "3D model je digitalni prikaz objekta u trodimenzionalnom prostoru. On sadrži informacije o obliku, dimenzijama, površinama i često teksturama objekta. Koristi se u igrama, industrijskom dizajnu, arhitekturi, medicini i vizuelnim simulacijama.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "admin_portal",
            titleSr = "Admin portal",
            titleEn = "Administratorski portal",
            explanation = "Admin portal je deo sistema namenjen korisnicima sa administratorskim ili operaterskim ovlašćenjima. Kroz njega se upravlja korisnicima, sadržajem, podešavanjima, izveštajima i poslovnim procesima. Pristup njegovim funkcijama mora biti ograničen odgovarajućim ulogama i dozvolama.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "api",
            titleSr = "API",
            titleEn = "Application Programming Interface",
            explanation = "API je definisan način na koji različite aplikacije ili komponente međusobno razmenjuju podatke i pokreću operacije. On određuje koje zahteve sistem prihvata, koje podatke očekuje i kakav odgovor vraća. Zahvaljujući API-ju, klijent ne mora da poznaje unutrašnju implementaciju servisa koji koristi.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "api_endpoint",
            titleSr = "API endpoint",
            titleEn = "Endpoint",
            explanation = "API endpoint je konkretna adresa ili ulazna tačka preko koje se pristupa jednoj funkciji API-ja. Na primer, jedan endpoint može vraćati korisnike, dok drugi kreira novu porudžbinu. Endpoint obično definiše putanju, HTTP metodu, ulazne podatke i očekivani odgovor.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "api_gateway",
            titleSr = "API Gateway",
            titleEn = "API prolaz",
            explanation = "API Gateway je centralna ulazna tačka preko koje klijenti pristupaju različitim backend servisima. Može da obavlja autentikaciju, rutiranje, ograničavanje zahteva, logovanje i objedinjavanje odgovora. Njegova uloga je da klijentima sakrije složenost unutrašnje organizacije sistema.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "asocijacija",
            titleSr = "Asocijacija",
            titleEn = "Association",
            explanation = "Asocijacija je UML veza koja pokazuje da dva objekta ili dve klase međusobno sarađuju ili se poznaju. Ona ne mora da znači da jedan objekat poseduje životni ciklus drugog objekta. Primer je veza između korisnika i porudžbine koju je korisnik kreirao.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "ast",
            titleSr = "AST",
            titleEn = "Abstract Syntax Tree",
            explanation = "AST je stablasta struktura koja predstavlja sintaksu programskog koda ili drugog formalnog izraza. Svaki čvor stabla predstavlja element kao što su operator, promenljiva, broj ili naredba. Kompajleri, interpreteri i alati za statičku analizu koriste AST da bi razumeli i obrađivali kod.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "autonomni_robot",
            titleSr = "Autonomni robot",
            titleEn = "Autonomous robot",
            explanation = "Autonomni robot je uređaj koji može da donosi odluke i izvršava zadatke bez stalne neposredne kontrole čoveka. On koristi senzore, softver i modele okruženja da bi se kretao i reagovao na promene. Primeri su roboti za dostavu, skladišni roboti i autonomna vozila.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "autoritativna_serverska_simulacija",
            titleSr = "Autoritativna serverska simulacija",
            titleEn = "Server-authoritative simulation",
            explanation = "Autoritativna serverska simulacija je pristup u kome server određuje konačno i zvanično stanje igre ili simulacije. Klijenti mogu da šalju akcije i prikazuju privremene rezultate, ali ne mogu samostalno da potvrde konačan ishod. Ovaj pristup smanjuje mogućnost varanja i neslaganja između igrača.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "autoritativni_dns_server",
            titleSr = "Autoritativni DNS server",
            titleEn = "Authoritative DNS server",
            explanation = "Autoritativni DNS server čuva zvanične DNS zapise za određeni internet domen. Kada dobije upit, on vraća podatak iz zone za koju je odgovoran, a ne iz privremenog keša. Njegov odgovor predstavlja konačan izvor informacija o adresama i drugim zapisima domena.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "autorizacija_sredstava",
            titleSr = "Autorizacija sredstava",
            titleEn = "Payment authorization",
            explanation = "Autorizacija sredstava je provera kojom payment provider potvrđuje da plaćanje može biti izvršeno. Iznos se često privremeno rezerviše na računu korisnika, ali još nije konačno naplaćen. Nakon autorizacije sledi potvrda, naplata ili oslobađanje rezervisanih sredstava.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "binarni_izraz",
            titleSr = "Binarni izraz",
            titleEn = "Binary expression",
            explanation = "Binarni izraz je izraz koji povezuje dva operanda jednim operatorom. Primeri su `5 + 3`, `a > b` i `x AND y`. U AST strukturi obično se predstavlja čvorom koji ima levi operand, operator i desni operand.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "cdn",
            titleSr = "CDN",
            titleEn = "Content Delivery Network",
            explanation = "CDN je distribuirana mreža servera koja korisnicima isporučuje sadržaj sa geografski bliže lokacije. Najčešće se koristi za slike, video-snimke, skripte i druge statičke fajlove. Time se smanjuju latencija, opterećenje glavnog servera i vreme učitavanja sadržaja.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "checkout",
            titleSr = "Checkout",
            titleEn = "Završetak kupovine",
            explanation = "Checkout je završni deo procesa kupovine u kome korisnik potvrđuje sadržaj korpe i podatke za plaćanje. Tok može da obuhvati obračun cene, izbor dostave, naplatu i kreiranje porudžbine. Greške u checkout-u direktno utiču na prihod i korisničko poverenje.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "client_side_prediction",
            titleSr = "Client-side prediction",
            titleEn = "Predikcija na klijentu",
            explanation = "Client-side prediction omogućava klijentu da odmah prikaže očekivani rezultat korisničke akcije bez čekanja odgovora servera. Koristi se u igrama i drugim interaktivnim sistemima radi boljeg osećaja odziva. Server kasnije potvrđuje ili ispravlja predviđeno stanje.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "crm",
            titleSr = "CRM",
            titleEn = "Customer Relationship Management",
            explanation = "CRM je sistem za upravljanje odnosima sa postojećim i potencijalnim klijentima. Čuva kontakte, istoriju komunikacije, prodajne prilike, aktivnosti i druge poslovne podatke. Cilj mu je da poboljša prodaju, korisničku podršku i dugoročni odnos sa klijentima.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "data_import",
            titleSr = "Data import",
            titleEn = "Uvoz podataka",
            explanation = "Data import je proces unošenja podataka iz spoljnog izvora u ciljni sistem. Izvor može biti fajl, baza podataka, API ili drugi poslovni sistem. Tok uvoza obično uključuje učitavanje, validaciju, transformaciju i upis podataka.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "data_quarantine",
            titleSr = "Data quarantine",
            titleEn = "Karantin podataka",
            explanation = "Data quarantine je izdvojeno mesto u kome se čuvaju podaci čija ispravnost ili bezbednost nije potvrđena. Takvi podaci se ne koriste u redovnim poslovnim procesima dok se ne pregledaju ili poprave. Karantin sprečava širenje grešaka i nepouzdanih zapisa kroz sistem.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "device_gateway",
            titleSr = "Device Gateway",
            titleEn = "Prolaz za uređaje",
            explanation = "Device Gateway je komponenta koja povezuje veliki broj uređaja sa centralnim servisima sistema. On može da proverava identitet uređaja, prima telemetriju i prosleđuje komande. Time se uređaji odvajaju od unutrašnje strukture backend sistema.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "digital_twin",
            titleSr = "Digital twin",
            titleEn = "Digitalni blizanac",
            explanation = "Digitalni blizanac je digitalni model stvarnog uređaja, objekta ili procesa. On se ažurira podacima iz senzora i prikazuje trenutno ili očekivano ponašanje stvarnog sistema. Koristi se za praćenje, simulaciju, održavanje i predviđanje problema.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "digitalna_mapa",
            titleSr = "Digitalna mapa",
            titleEn = "Digital map",
            explanation = "Digitalna mapa je računarski prikaz geografskog prostora i objekata koji se u njemu nalaze. Može da sadrži puteve, zgrade, granice, tačke interesa i druge prostorne informacije. Sistem može da je pretražuje, ažurira i koristi za navigaciju ili analizu.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "dijagram_klasa",
            titleSr = "Dijagram klasa",
            titleEn = "Class diagram",
            explanation = "Dijagram klasa je UML dijagram koji prikazuje klase, njihove atribute, metode i međusobne veze. Koristi se za opisivanje statičke strukture objektno-orijentisanog sistema. Pomaže timu da razume odgovornosti klasa i odnose kao što su nasleđivanje, asocijacija i kompozicija.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "dns",
            titleSr = "DNS",
            titleEn = "Domain Name System",
            explanation = "DNS je sistem koji prevodi čitljive nazive domena u mrežne adrese koje računari koriste. Zahvaljujući njemu korisnik može da unese naziv sajta umesto numeričke IP adrese. DNS je organizovan kao distribuirana hijerarhija servera i zapisa.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "dns_propagacija",
            titleSr = "DNS propagacija",
            titleEn = "DNS propagation",
            explanation = "DNS propagacija je period tokom koga se nova ili izmenjena DNS informacija širi kroz mrežu DNS servera i keševa. Različiti korisnici u tom periodu mogu dobijati različite rezultate za isti domen. Trajanje zavisi od TTL vrednosti i načina keširanja zapisa.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "dns_zona",
            titleSr = "DNS zona",
            titleEn = "DNS zone",
            explanation = "DNS zona je skup DNS zapisa za određeni domen ili njegov deo. Može da sadrži zapise za IP adrese, email servere, poddomene i druge mrežne informacije. Zonom upravlja autoritativni DNS server.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "domenski_workflow",
            titleSr = "Domenski workflow",
            titleEn = "Domain workflow",
            explanation = "Domenski workflow je poslovni tok specifičan za određenu oblast ili proces. On definiše stanja, dozvoljene akcije, pravila i prelaze kroz koje poslovni slučaj prolazi. Primeri su tok odobravanja kredita, reklamacije ili laboratorijskog rezultata.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "downstream_sistem",
            titleSr = "Downstream sistem",
            titleEn = "Nizvodni sistem",
            explanation = "Downstream sistem je sistem koji prima i koristi podatke proizvedene u prethodnom delu toka. Može da bude izveštajni servis, analitika, naplata ili druga integracija. Greška u izvoru može se preneti i izazvati probleme u svim nizvodnim sistemima.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "dsl",
            titleSr = "DSL",
            titleEn = "Domain-Specific Language",
            explanation = "DSL je jezik napravljen za rešavanje zadataka u jednoj određenoj oblasti. Obično je jednostavniji i ograničeniji od opštih programskih jezika. Primeri su jezik za poslovna pravila, upite, konfiguraciju ili definisanje workflow procesa.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "ekonomija_igre",
            titleSr = "Ekonomija igre",
            titleEn = "Game economy",
            explanation = "Ekonomija igre predstavlja sistem virtuelnih vrednosti, nagrada, troškova i razmena unutar video-igre. Obuhvata valute, predmete, iskustvo, cene i pravila napredovanja. Greške poput dupliranja nagrada mogu narušiti ravnotežu i poverenje igrača.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "eta",
            titleSr = "ETA",
            titleEn = "Estimated Time of Arrival",
            explanation = "ETA je procenjeno vreme dolaska osobe, vozila ili pošiljke na odredište. Izračunava se na osnovu trenutne lokacije, brzine, rute, saobraćaja i drugih podataka. Procena se može menjati kako sistem dobija novije informacije.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "event_time",
            titleSr = "Event time",
            titleEn = "Vreme nastanka događaja",
            explanation = "Event time je trenutak kada se događaj stvarno dogodio u izvoru ili poslovnom procesu. Ne mora biti jednak vremenu kada je sistem primio ili obradio taj događaj. Važan je kod zakašnjelih poruka, analitike i rekonstrukcije pravilnog redosleda događaja.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "finalizacija_porudzbine",
            titleSr = "Finalizacija porudžbine",
            titleEn = "Order finalization",
            explanation = "Finalizacija porudžbine je korak u kome sistem potvrđuje da su ključni uslovi kupovine ispunjeni. Obično uključuje potvrdu naplate, rezervaciju zaliha i prelazak porudžbine u konačno stanje. Ovaj korak mora biti zaštićen od duplog ili nedovršenog izvršavanja.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "generisanje_koda",
            titleSr = "Generisanje koda",
            titleEn = "Code generation",
            explanation = "Generisanje koda je automatsko stvaranje programskog koda na osnovu modela, šablona ili druge strukture. Kompajler može, na primer, da generiše izvršni kod iz AST stabla. Ovaj pristup se koristi i za pravljenje API klijenata, konfiguracija i ponavljajućih delova aplikacije.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "geografska_koordinata",
            titleSr = "Geografska koordinata",
            titleEn = "Geographic coordinate",
            explanation = "Geografska koordinata određuje položaj tačke na Zemljinoj površini. Najčešće se izražava geografskom širinom i dužinom. Koristi se u mapama, navigaciji, praćenju vozila i prostornim analizama.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "gps",
            titleSr = "GPS",
            titleEn = "Global Positioning System",
            explanation = "GPS je satelitski sistem koji omogućava određivanje lokacije, brzine i vremena. Uređaj izračunava položaj na osnovu signala više satelita. GPS se koristi u navigaciji, logistici, mobilnim aplikacijama i praćenju uređaja.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "gps_dogadjaj",
            titleSr = "GPS događaj",
            titleEn = "GPS event",
            explanation = "GPS događaj je zapis o lokaciji ili kretanju uređaja u određenom trenutku. Obično sadrži koordinate, vremensku oznaku, brzinu i identitet uređaja. Više GPS događaja može se koristiti za rekonstrukciju rute i izračunavanje ETA vrednosti.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "gramatika",
            titleSr = "Gramatika",
            titleEn = "Grammar",
            explanation = "Gramatika definiše pravila po kojima se izrazi nekog jezika mogu pravilno sastaviti. Ona određuje dozvoljene simbole, strukture i njihove kombinacije. Parser i Interpreter koriste gramatiku da bi razumeli i izvršili ulazne izraze.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "ingestion_time",
            titleSr = "Ingestion time",
            titleEn = "Vreme prijema događaja",
            explanation = "Ingestion time je trenutak kada je sistem primio događaj ili podatak. Može biti kasniji od stvarnog vremena nastanka zbog mreže, offline rada ili zadržavanja u redu. Razlika između event time i ingestion time važna je za pravilnu obradu vremenskih tokova.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "integration_gateway",
            titleSr = "Integration Gateway",
            titleEn = "Integracioni prolaz",
            explanation = "Integration Gateway je centralna komponenta kroz koju sistem komunicira sa spoljnim servisima i partnerima. Može da prilagođava formate, autentikuje zahteve, rutira poruke i kontroliše greške. Njegova svrha je da poslovne module odvoji od tehničkih detalja svake integracije.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "interest_management",
            titleSr = "Interest Management",
            titleEn = "Upravljanje relevantnošću entiteta",
            explanation = "Interest Management određuje koje objekte i događaje određeni klijent treba da primi. Posebno se koristi u velikim multiplayer igrama kako se igraču ne bi slali podaci iz celog sveta. Filtriranje se može zasnivati na lokaciji, vidljivosti, udaljenosti ili zoni interesa.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "internet_domen",
            titleSr = "Internet domen",
            titleEn = "Domain name",
            explanation = "Internet domen je čitljiv naziv koji predstavlja adresu sajta ili druge internet usluge. On se preko DNS sistema povezuje sa odgovarajućim serverima i mrežnim adresama. Domen se registruje na određeni period i mora se redovno obnavljati.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "inventory",
            titleSr = "Inventory",
            titleEn = "Zalihe",
            explanation = "Inventory predstavlja količinu proizvoda, materijala ili drugih raspoloživih jedinica u sistemu. Sistem zaliha prati dostupne, rezervisane, prodate i vraćene količine. Tačno stanje je važno kako bi se izbegle pogrešne prodaje i nestašice.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "iot",
            titleSr = "IoT",
            titleEn = "Internet of Things",
            explanation = "IoT označava mrežu fizičkih uređaja koji prikupljaju podatke i komuniciraju putem interneta ili druge mreže. Takvi uređaji mogu biti senzori, brojila, vozila, kućni aparati ili industrijske mašine. IoT sistemi povezuju uređaje, komunikacionu infrastrukturu, skladištenje podataka i upravljačke aplikacije.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "kiosk",
            titleSr = "Kiosk",
            titleEn = "Self-service kiosk",
            explanation = "Kiosk je samouslužni uređaj preko kog korisnik samostalno izvršava određenu operaciju. Može da služi za naručivanje hrane, prijavu, kupovinu karte ili plaćanje. Njegov interfejs mora biti jednostavan, otporan na greške i prilagođen javnoj upotrebi.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "kompoziciona_veza",
            titleSr = "Kompoziciona veza",
            titleEn = "Composition relationship",
            explanation = "Kompoziciona veza je jaka veza između celine i njenih sastavnih delova. Deo obično ne postoji nezavisno od celine i njegov životni ciklus zavisi od nje. U UML dijagramu se prikazuje punim rombom na strani objekta koji predstavlja celinu.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "lobby",
            titleSr = "Lobby",
            titleEn = "Čekaonica meča",
            explanation = "Lobby je prostor u multiplayer sistemu u kome se igrači okupljaju pre početka meča. U njemu se mogu birati timovi, podešavanja, mapa i druga pravila igre. Nakon što su uslovi ispunjeni, lobby pokreće ili prosleđuje igrače odgovarajućem game serveru.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "lokalni_poslovni_dan",
            titleSr = "Lokalni poslovni dan",
            titleEn = "Local business day",
            explanation = "Lokalni poslovni dan predstavlja kalendarski dan prema vremenskoj zoni određene poslovne lokacije ili korisnika. On može biti drugačiji od dana prema UTC vremenu. Važan je za dnevne izveštaje, obračune, rokove i regulatorne evidencije.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "loyalty_program",
            titleSr = "Loyalty program",
            titleEn = "Program lojalnosti",
            explanation = "Loyalty program nagrađuje korisnike za kupovine, aktivnosti ili dugoročnu saradnju. Nagrade mogu biti poeni, popusti, statusni nivoi ili posebne pogodnosti. Sistem mora pouzdano da evidentira sticanje, trošenje i isticanje pogodnosti.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "mapiranje_identiteta",
            titleSr = "Mapiranje identiteta",
            titleEn = "Identity mapping",
            explanation = "Mapiranje identiteta je povezivanje zapisa iz različitih sistema koji predstavljaju isti entitet. Na primer, isti korisnik može imati različite identifikatore u starom i novom sistemu. Pogrešno mapiranje može izazvati duplikate, spajanje različitih korisnika ili gubitak veza između podataka.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "matchmaking",
            titleSr = "Matchmaking",
            titleEn = "Uparivanje igrača",
            explanation = "Matchmaking je proces pronalaženja odgovarajućih protivnika ili saigrača za multiplayer meč. Sistem može da uzima u obzir veštinu, region, vreme čekanja, tip igre i druge kriterijume. Cilj je da meč bude dovoljno brz za formiranje i istovremeno što uravnoteženiji.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "merge_pravilo",
            titleSr = "Merge pravilo",
            titleEn = "Merge rule",
            explanation = "Merge pravilo određuje kako se dva ili više zapisa spajaju u jedan. Ono definiše koji podatak ima prednost, kako se rešavaju razlike i kada je spajanje dozvoljeno. Pogrešno pravilo može spojiti različite entitete ili izgubiti važan podatak.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "migracija_podataka",
            titleSr = "Migracija podataka",
            titleEn = "Data migration",
            explanation = "Migracija podataka je prenos podataka iz jednog sistema, formata ili skladišta u drugo. Obično obuhvata izdvajanje, čišćenje, mapiranje, transformaciju i proveru podataka. Proces mora omogućiti praćenje grešaka i poređenje izvornog i ciljnog stanja.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "migracioni_talas",
            titleSr = "Migracioni talas",
            titleEn = "Migration wave",
            explanation = "Migracioni talas je jedna kontrolisana etapa u kojoj se prenosi određeni deo podataka. Podela migracije na talase smanjuje rizik i omogućava proveru rezultata pre nastavka. Ako se otkrije problem, naredni talasi mogu biti zaustavljeni bez prekidanja celog sistema.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "mini_jezik",
            titleSr = "Mini-jezik",
            titleEn = "Mini-language",
            explanation = "Mini-jezik je mali i ograničen jezik napravljen za određenu vrstu izraza ili komandi. Može da služi za filtere, poslovna pravila, pretragu ili konfiguraciju. Njegovu sintaksu i značenje obično obrađuju parser i Interpreter.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "mmo",
            titleSr = "MMO",
            titleEn = "Massively Multiplayer Online",
            explanation = "MMO je vrsta online igre u kojoj istovremeno učestvuje veoma veliki broj igrača. Sistem mora da upravlja svetom igre, korisnicima, mrežnim događajima i velikim količinama stanja. Skaliranje i kontrola relevantnih poruka posebno su važni u gusto naseljenim zonama.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "mobilna_aplikacija",
            titleSr = "Mobilna aplikacija",
            titleEn = "Mobile application",
            explanation = "Mobilna aplikacija je softver namenjen telefonima, tabletima i sličnim prenosivim uređajima. Može koristiti kameru, GPS, senzore, lokalno skladište i mobilne notifikacije. Često mora da podrži nestabilnu mrežu, ograničene resurse i različite veličine ekrana.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "moderacija_izmene",
            titleSr = "Moderacija izmene",
            titleEn = "Change moderation",
            explanation = "Moderacija izmene je postupak pregleda i odobravanja predložene promene pre njenog objavljivanja. Moderator proverava tačnost, kvalitet, bezbednost i usklađenost predloga sa pravilima. Koristi se u mapama, enciklopedijama, sadržajnim platformama i drugim zajednički uređivanim sistemima.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "nalog_za_pripremu_robe",
            titleSr = "Nalog za pripremu robe",
            titleEn = "Picking order",
            explanation = "Nalog za pripremu robe je instrukcija skladištu da izdvoji određene artikle za porudžbinu. Sadrži proizvode, količine, lokacije u skladištu i prioritet obrade. Nakon izvršenja, roba se prosleđuje pakovanju i isporuci.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "nameserver",
            titleSr = "Nameserver",
            titleEn = "Name server",
            explanation = "Nameserver je DNS server koji učestvuje u pronalaženju informacija o domenima. Za domen se podešavaju nameserver-i koji upućuju na servere odgovorne za njegovu DNS zonu. Pogrešno podešen nameserver može učiniti domen nedostupnim.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "nejasan_status_placanja",
            titleSr = "Nejasan status plaćanja",
            titleEn = "Ambiguous payment state",
            explanation = "Nejasan status plaćanja nastaje kada lokalni sistem ne zna da li je payment provider uspešno izvršio operaciju. To se može dogoditi zbog timeout-a, prekida mreže ili zakašnjelog webhook-a. Sistem ne sme automatski ponoviti naplatu dok ne proveri stvarno stanje kod providera.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "nivo_zumiranja",
            titleSr = "Nivo zumiranja",
            titleEn = "Zoom level",
            explanation = "Nivo zumiranja određuje koliko detaljno se prikazuje mapa, slika ili drugi veliki vizuelni sadržaj. Niži nivo prikazuje šire područje sa manje detalja, dok viši nivo prikazuje manju oblast sa više detalja. Sistemi mapa često čuvaju različite tile-ove za svaki nivo zumiranja.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "normalizacija_vremena",
            titleSr = "Normalizacija vremena",
            titleEn = "Time normalization",
            explanation = "Normalizacija vremena je pretvaranje vremenskih podataka u zajednički format i referentnu vremensku zonu. Time se omogućava pravilno poređenje događaja nastalih u različitim regionima. Prikaz se kasnije može ponovo prilagoditi lokalnoj vremenskoj zoni korisnika.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "objavljena_verzija_mape",
            titleSr = "Objavljena verzija mape",
            titleEn = "Published map version",
            explanation = "Objavljena verzija mape je verzija geografskih podataka koja je odobrena i dostupna korisnicima. Predložene izmene ne utiču na zvaničnu mapu dok ne prođu validaciju i moderaciju. Sistem treba da čuva vezu između objavljene verzije i promena od kojih je nastala.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "obracunski_interval",
            titleSr = "Obračunski interval",
            titleEn = "Billing interval",
            explanation = "Obračunski interval je vremenski period za koji se izračunavaju troškovi, naknade ili potrošnja. Može biti dnevni, mesečni, godišnji ili definisan posebnim poslovnim pravilom. Njegove granice moraju biti jasno određene, posebno kada sistem radi u više vremenskih zona.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "optimizacija_rute",
            titleSr = "Optimizacija rute",
            titleEn = "Route optimization",
            explanation = "Optimizacija rute je pronalaženje povoljnog redosleda kretanja između više lokacija. Cilj može biti smanjenje vremena, udaljenosti, troška ili potrošnje energije. Koristi se u dostavi, logistici, transportu i upravljanju autonomnim robotima.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "overbooking",
            titleSr = "Overbooking",
            titleEn = "Prekoračenje kapaciteta rezervacija",
            explanation = "Overbooking nastaje kada sistem potvrdi više rezervacija nego što stvarno postoji raspoloživih mesta ili resursa. Može biti posledica poslovne strategije ili greške u konkurentnom upravljanju stanjem. U avio-sistemu može dovesti do toga da broj putnika premaši broj sedišta.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "parser",
            titleSr = "Parser",
            titleEn = "Parser",
            explanation = "Parser je komponenta koja analizira ulazni tekst ili podatke prema definisanoj gramatici. On prepoznaje strukturu izraza i često od nje pravi AST ili drugi interni model. Parser može da prijavi grešku kada ulaz ne odgovara pravilima jezika.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "payment_gateway",
            titleSr = "Payment gateway",
            titleEn = "Platni prolaz",
            explanation = "Payment gateway je tehnička komponenta koja povezuje aplikaciju sa sistemima za elektronsko plaćanje. On bezbedno prenosi podatke i pokreće operacije kao što su autorizacija, naplata i refundacija. Aplikacija preko njega ne mora direktno da komunicira sa svakom bankom ili kartičnom mrežom.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "payment_provider",
            titleSr = "Payment provider",
            titleEn = "Pružalac usluge plaćanja",
            explanation = "Payment provider je kompanija ili servis koji omogućava obradu elektronskih plaćanja. On može da podržava kartice, bankovne transfere, digitalne novčanike i druge metode. Provider vraća statuse operacija i često šalje naknadne potvrde preko webhook-a.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "pending_status",
            titleSr = "PENDING status",
            titleEn = "Pending state",
            explanation = "PENDING status znači da je operacija započeta ili prihvaćena, ali njen konačan rezultat još nije poznat. Može se koristiti kod plaćanja, porudžbina, migracija i asinhronih procesa. Sistem mora jasno definisati kako se stanje kasnije potvrđuje, odbija ili prekida.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "pokusaj_placanja",
            titleSr = "Pokušaj plaćanja",
            titleEn = "Payment attempt",
            explanation = "Pokušaj plaćanja je pojedinačno pokretanje operacije naplate za određenu porudžbinu ili obavezu. Jedna porudžbina može imati više pokušaja ako prethodni nije uspeo ili je istekao. Svaki pokušaj treba da ima svoj identifikator, status i vezu sa providerom.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "pravilo_eskalacije",
            titleSr = "Pravilo eskalacije",
            titleEn = "Escalation rule",
            explanation = "Pravilo eskalacije definiše kada slučaj treba proslediti višem nivou odgovornosti. Može zavisiti od prioriteta, isteka roka, iznosa, rizika ili nerešenog statusa. Dobra pravila sprečavaju da ozbiljni ili dugo otvoreni slučajevi ostanu neprimećeni.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "priprema_geometrije",
            titleSr = "Priprema geometrije",
            titleEn = "Geometry preprocessing",
            explanation = "Priprema geometrije je obrada 3D podataka pre renderovanja ili druge analize. Može da uključuje proveru modela, pojednostavljivanje mreže, normalizaciju i izračunavanje pomoćnih struktura. Cilj je da kasnija obrada bude ispravna i efikasna.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "product_feed",
            titleSr = "Product feed",
            titleEn = "Feed proizvoda",
            explanation = "Product feed je strukturisana lista proizvoda i njihovih podataka namenjena drugim sistemima. Može da sadrži naziv, cenu, stanje zaliha, opis, kategoriju i adresu slike. Koristi se za oglase, marketplace platforme, preporuke i razmenu podataka sa partnerima.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "refundacija",
            titleSr = "Refundacija",
            titleEn = "Refund",
            explanation = "Refundacija je vraćanje prethodno naplaćenog novca korisniku. Može biti potpuna ili delimična i obično se vezuje za konkretnu payment transakciju. Sistem mora da prati zahtev, odluku, status kod providera i konačni rezultat povraćaja.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "registrant",
            titleSr = "Registrant",
            titleEn = "Vlasnik/korisnik domena",
            explanation = "Registrant je osoba ili organizacija na čije ime je internet domen registrovan. On ima pravo korišćenja domena dok je registracija važeća. Njegovi podaci se vode kod registrara i moraju biti ažurni.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "registrar",
            titleSr = "Registrar",
            titleEn = "Registrar domena",
            explanation = "Registrar je ovlašćena organizacija preko koje korisnici registruju i upravljaju internet domenima. On komunicira sa centralnim registrom i omogućava obnovu, prenos i promenu podataka domena. Registrar nije isto što i DNS hosting, iako može nuditi obe usluge.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "regulatorna_procedura",
            titleSr = "Regulatorna procedura",
            titleEn = "Regulatory procedure",
            explanation = "Regulatorna procedura je formalno definisan proces koji mora biti usklađen sa zakonom, pravilnikom ili zahtevima nadležnog tela. Ona može propisivati obavezne korake, dokumente, rokove i odobrenja. Sistem mora da obezbedi da se ti koraci ne mogu proizvoljno preskočiti.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "render_posao",
            titleSr = "Render posao",
            titleEn = "Render job",
            explanation = "Render posao je zadatak kojim se 3D scena, model ili drugi grafički sadržaj pretvara u konačnu sliku ili video. Može biti veoma zahtevan za CPU, GPU i memoriju. Scheduler ga raspoređuje na odgovarajuće radnike prema prioritetu i potrebnim resursima.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "renderovanje",
            titleSr = "Renderovanje",
            titleEn = "Rendering",
            explanation = "Renderovanje je proces stvaranja slike ili animacije na osnovu digitalnog modela, scene i vizuelnih pravila. Sistem izračunava geometriju, osvetljenje, materijale, boje i perspektivu. Koristi se u igrama, filmovima, dizajnu i arhitektonskoj vizualizaciji.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "rezervacija_sredstava",
            titleSr = "Rezervacija sredstava",
            titleEn = "Funds hold",
            explanation = "Rezervacija sredstava je privremeno blokiranje određenog iznosa na računu korisnika. Novac još nije konačno prenet trgovcu, ali korisnik njime ne može slobodno raspolagati. Rezervacija se kasnije potvrđuje kao naplata ili se oslobađa.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "rezervacija_zaliha",
            titleSr = "Rezervacija zaliha",
            titleEn = "Inventory reservation",
            explanation = "Rezervacija zaliha privremeno odvaja određenu količinu proizvoda za konkretnu porudžbinu. Time se sprečava da isti artikal bude obećan drugom kupcu dok traje checkout. Ako porudžbina ne bude završena, rezervacija se oslobađa nakon definisanog vremena.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "satelitski_snimak",
            titleSr = "Satelitski snimak",
            titleEn = "Satellite image",
            explanation = "Satelitski snimak je slika Zemljine površine napravljena senzorima na satelitu. Može prikazivati vidljivu svetlost, toplotu, vegetaciju, oblake i druge osobine prostora. Koristi se u mapiranju, poljoprivredi, meteorologiji i praćenju promena u okruženju.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "senzor",
            titleSr = "Senzor",
            titleEn = "Sensor",
            explanation = "Senzor je uređaj koji meri neku fizičku pojavu i pretvara je u podatak. Može da meri temperaturu, pritisak, pokret, svetlost, lokaciju ili druge vrednosti. U IoT sistemu senzorski podaci se šalju servisima za praćenje, analizu i reagovanje.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "server_reconciliation",
            titleSr = "Server reconciliation",
            titleEn = "Usklađivanje sa serverom",
            explanation = "Server reconciliation je postupak kojim klijent usklađuje svoje predviđeno stanje sa zvaničnim stanjem servera. Ako se stanja razlikuju, klijent primenjuje korekciju položaja, akcije ili drugog podatka. Ovaj mehanizam omogućava brz odziv uz zadržavanje autoriteta servera.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "signed_url",
            titleSr = "Signed URL",
            titleEn = "Potpisani URL",
            explanation = "Signed URL je adresa koja sadrži kriptografski potpis i vremensko ograničenje pristupa. Omogućava privremeno preuzimanje ili slanje fajla bez trajnog javnog otvaranja resursa. Nakon isteka ili izmene parametara, URL više ne važi.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "silent_data_corruption",
            titleSr = "Silent data corruption",
            titleEn = "Tiha korupcija podataka",
            explanation = "Tiha korupcija podataka je promena ili oštećenje podataka koje sistem ne prepoznaje kao grešku. Aplikacija može nastaviti normalno da radi dok izveštaji i odluke koriste netačno stanje. Posebno je opasna tokom migracija, spajanja zapisa i automatskih transformacija.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "smart_meter",
            titleSr = "Smart meter",
            titleEn = "Pametno brojilo",
            explanation = "Pametno brojilo je digitalni uređaj koji meri potrošnju energije, vode, gasa ili drugog resursa. Podatke periodično šalje centralnom sistemu bez ručnog očitavanja. Omogućava precizniji obračun, praćenje potrošnje i otkrivanje nepravilnosti.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "staticka_analiza",
            titleSr = "Statička analiza",
            titleEn = "Static analysis",
            explanation = "Statička analiza ispituje programski kod bez njegovog izvršavanja. Može da pronađe greške, bezbednosne probleme, nekorišćene delove i kršenje pravila kvaliteta. Često koristi AST kako bi razumela strukturu i značenje koda.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "status_ceka_se_potvrda",
            titleSr = "Status čeka se potvrda",
            titleEn = "Processing / awaiting confirmation",
            explanation = "Status čeka se potvrda označava da je zahtev poslat, ali konačan odgovor još nije dobijen. Koristi se kada spoljni servis ili asinhroni proces naknadno potvrđuje ishod. Sistem treba da spreči dupliranje operacije dok je ovaj status aktivan.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "telemetrija",
            titleSr = "Telemetrija",
            titleEn = "Telemetry",
            explanation = "Telemetrija je automatsko prikupljanje i slanje podataka o stanju uređaja ili sistema. Može da obuhvati lokaciju, temperaturu, greške, potrošnju i radne parametre. Koristi se za monitoring, dijagnostiku, analitiku i donošenje operativnih odluka.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "timestamp",
            titleSr = "Timestamp",
            titleEn = "Vremenska oznaka",
            explanation = "Timestamp je podatak koji predstavlja tačan trenutak nastanka ili evidentiranja događaja. Može sadržati datum, vreme, vremensku zonu i preciznost do milisekundi ili manjih jedinica. Neophodan je za redosled događaja, audit, sinhronizaciju i analitiku.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "tokenizovani_pristup",
            titleSr = "Tokenizovani pristup",
            titleEn = "Tokenized access",
            explanation = "Tokenizovani pristup koristi privremeni ili ograničeni token kao dokaz da korisnik sme da pristupi resursu. Token može sadržati dozvole, identitet i vreme isteka. Time se izbegava direktno izlaganje trajnih pristupnih podataka ili samog resursa.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "transformacija_podataka",
            titleSr = "Transformacija podataka",
            titleEn = "Data transformation",
            explanation = "Transformacija podataka je menjanje formata, strukture ili vrednosti podataka tokom obrade. Može da uključuje konverziju tipova, normalizaciju, spajanje polja i izračunavanje novih vrednosti. Cilj je da podaci odgovaraju potrebama ciljnog sistema.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "turnirski_bracket",
            titleSr = "Turnirski bracket",
            titleEn = "Tournament bracket",
            explanation = "Turnirski bracket je struktura koja prikazuje mečeve, runde i napredovanje učesnika kroz turnir. Rezultat jednog meča određuje ko prelazi u sledeću granu ili rundu. Pogrešno stanje bracket-a može automatski proizvesti pogrešne naredne mečeve.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "uml",
            titleSr = "UML",
            titleEn = "Unified Modeling Language",
            explanation = "UML je standardizovan vizuelni jezik za opisivanje strukture i ponašanja softverskih sistema. Obuhvata dijagrame klasa, sekvenci, aktivnosti, komponenti i druge vrste prikaza. UML pomaže timu da razume i komunicira dizajn pre ili tokom implementacije.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "virtuelna_valuta",
            titleSr = "Virtuelna valuta",
            titleEn = "Virtual currency",
            explanation = "Virtuelna valuta je digitalna vrednost koja se koristi unutar igre ili druge platforme. Korisnik je može zaraditi, kupiti ili potrošiti na predmete i funkcionalnosti. Sistem mora zaštititi njen integritet jer greške mogu narušiti ekonomiju i poverenje korisnika.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "vremenska_zona",
            titleSr = "Vremenska zona",
            titleEn = "Time zone",
            explanation = "Vremenska zona je oblast koja koristi isto standardno lokalno vreme. Jedan isti trenutak ima različit prikaz u različitim vremenskim zonama. Sistemi obično čuvaju vreme u zajedničkom formatu, a korisniku ga prikazuju prema njegovoj zoni.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "warehouse_picking",
            titleSr = "Warehouse picking",
            titleEn = "Skladišno izdvajanje robe",
            explanation = "Warehouse picking je proces pronalaženja i izdvajanja artikala iz skladišta za konkretnu porudžbinu. Radnik ili automatizovani uređaj prati nalog i uzima robu sa označenih lokacija. Tačnost ovog koraka direktno utiče na ispravnost isporuke.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "web_aplikacija",
            titleSr = "Web aplikacija",
            titleEn = "Web application",
            explanation = "Web aplikacija je softver kome korisnik pristupa preko internet pregledača. Njeni klijentski delovi izvršavaju se u pregledaču, dok backend obrađuje podatke i poslovnu logiku. Prednost je što korisnik najčešće ne mora posebno da instalira aplikaciju.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "workflow",
            titleSr = "Workflow",
            titleEn = "Poslovni tok",
            explanation = "Workflow je definisan niz koraka, stanja i odluka kroz koje prolazi poslovni slučaj. Određuje ko izvršava akciju, šta je sledeći korak i pod kojim uslovima proces može napredovati. Primeri su obrada reklamacije, odobravanje dokumenta i registracija korisnika.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "workflow_engine",
            titleSr = "Workflow engine",
            titleEn = "Mehanizam poslovnih tokova",
            explanation = "Workflow engine je komponenta koja izvršava i prati definisane poslovne tokove. On upravlja statusima, rokovima, zadacima, prelazima i pravilima eskalacije. Može biti generički ili posebno prilagođen jednom poslovnom domenu.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "xp",
            titleSr = "XP",
            titleEn = "Experience Points",
            explanation = "XP su poeni iskustva koje igrač dobija za aktivnosti i uspehe u igri. Koriste se za napredovanje nivoa, otključavanje sadržaja ili rangiranje igrača. Dodela XP-a mora biti zaštićena od ponavljanja i zloupotrebe.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        ),
        term(
            id = "zona_interesa",
            titleSr = "Zona interesa",
            titleEn = "Area of Interest",
            explanation = "Zona interesa je deo virtuelnog ili geografskog prostora koji je trenutno relevantan određenom korisniku ili uređaju. Sistem šalje detaljne informacije samo o entitetima koji se nalaze u toj zoni ili utiču na nju. Time se smanjuju mrežni saobraćaj i nepotrebna obrada.",
            categoryId = INTEGRATIONS_BUSINESS_DOMAINS
        )
    )

    private fun communicationEventsProcessingTerms(): List<EncyclopediaTerm> = listOf(
        term(
            id = "asinhrona_komunikacija",
            titleSr = "Asinhrona komunikacija",
            titleEn = "Asynchronous communication",
            explanation = "Asinhrona komunikacija omogućava pošiljaocu da nastavi rad bez čekanja da primalac odmah obradi zahtev. Poruka se obično smešta u red, broker ili drugi posrednički sistem. Ovaj pristup povećava fleksibilnost, ali otežava praćenje toka i konačnog rezultata.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "asinhrono_obavestavanje",
            titleSr = "Asinhrono obaveštavanje",
            titleEn = "Asynchronous notification",
            explanation = "Asinhrono obaveštavanje znači da se notifikacija šalje nezavisno od glavnog poslovnog toka. Korisnik ili drugi servis može je primiti sa određenim zakašnjenjem. Primer je slanje email potvrde nakon što je porudžbina već uspešno završena.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "backpressure",
            titleSr = "Backpressure",
            titleEn = "Povratni pritisak",
            explanation = "Backpressure je mehanizam kojim sporiji deo sistema signalizira da više ne može da prima podatke trenutnom brzinom. Time se sprečava nekontrolisano punjenje redova i trošenje memorije. Sistem može usporiti proizvođača, odbiti deo zahteva ili privremeno skladištiti podatke.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "batch_obrada",
            titleSr = "Batch obrada",
            titleEn = "Batch processing",
            explanation = "Batch obrada je izvršavanje većeg broja sličnih zadataka ili podataka u jednoj grupi. Najčešće se koristi kada rezultat nije potreban odmah. Primer je noćni obračun računa za sve korisnike.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "batch_posao",
            titleSr = "Batch posao",
            titleEn = "Batch job",
            explanation = "Batch posao je konkretan zadatak koji obrađuje grupu podataka bez neposredne interakcije sa korisnikom. Može se pokretati ručno, periodično ili prema rasporedu. Primer je generisanje mesečnih izveštaja za sve poslovnice.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "centralizovani_scheduler",
            titleSr = "Centralizovani scheduler",
            titleEn = "Central scheduler",
            explanation = "Centralizovani scheduler je jedna komponenta koja upravlja raspoređivanjem poslova za više delova sistema. On određuje vreme izvršavanja, prioritete i dostupne resurse. Prednost je jedinstvena kontrola, dok mana može biti veliko opterećenje i složena pravila.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "deduplikacija",
            titleSr = "Deduplikacija",
            titleEn = "Deduplication",
            explanation = "Deduplikacija je proces prepoznavanja i uklanjanja ponovljenih podataka, poruka ili operacija. Obično koristi jedinstveni identifikator ili kombinaciju važnih polja. Time se sprečavaju dupli upisi, višestruke naplate i ponavljanje istog poslovnog događaja.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "dogadjaj",
            titleSr = "Događaj",
            titleEn = "Event",
            explanation = "Događaj je zapis da se u sistemu nešto značajno dogodilo. On obično sadrži vrstu događaja, vreme, identifikator i povezane podatke. Drugi delovi sistema mogu reagovati na događaj bez direktnog poziva izvornog modula.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "domenski_dogadjaj",
            titleSr = "Domenski događaj",
            titleEn = "Domain event",
            explanation = "Domenski događaj predstavlja važnu promenu unutar poslovnog domena. Primeri su kreirana porudžbina, odobrena reklamacija ili poslata pošiljka. Njegov naziv i sadržaj treba da odražavaju poslovno značenje, a ne samo tehničku operaciju.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "duplirani_dogadjaj",
            titleSr = "Duplirani događaj",
            titleEn = "Duplicate event",
            explanation = "Duplirani događaj je ista poruka ili poslovna informacija primljena više puta. Može nastati zbog retry mehanizma, prekida mreže ili ponovnog slanja iz brokera. Potrošač mora biti idempotentan ili koristiti deduplikaciju kako bi sprečio višestruke posledice.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "event_bus",
            titleSr = "Event Bus",
            titleEn = "Sabirnica događaja",
            explanation = "Event Bus je komponenta preko koje proizvođači objavljuju događaje, a zainteresovani potrošači ih primaju. Proizvođač ne mora direktno da poznaje sve primaoce. Ovaj pristup smanjuje spregnutost, ali zahteva dobar monitoring i jasno definisane ugovore događaja.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "event_consumer",
            titleSr = "Event consumer",
            titleEn = "Potrošač događaja",
            explanation = "Event consumer je komponenta koja prima i obrađuje objavljene događaje. Na osnovu događaja može ažurirati podatke, poslati obaveštenje ili pokrenuti drugi proces. Potrošač mora pravilno rešavati duplikate, greške i događaje koji stignu van redosleda.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "event_log",
            titleSr = "Event log",
            titleEn = "Dnevnik događaja",
            explanation = "Event log je hronološki zapis događaja koji su se dogodili u sistemu. Može služiti za audit, rekonstrukciju stanja i analizu problema. Za razliku od običnog tehničkog loga, često sadrži strukturisane poslovne događaje.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "event_publisher",
            titleSr = "Event publisher",
            titleEn = "Izdavač događaja",
            explanation = "Event publisher je komponenta koja kreira i objavljuje događaje drugim delovima sistema. On mora obezbediti da događaj bude pravilno formiran i poslat odgovarajućem brokeru ili event bus-u. Pouzdanost izdavača je važna kako se poslovna promena ne bi izgubila.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "event_stream",
            titleSr = "Event stream",
            titleEn = "Tok događaja",
            explanation = "Event stream je kontinuirani niz događaja poređanih prema vremenu ili redosledu nastanka. Potrošači mogu pratiti tok i obrađivati događaje čim stignu. Koristi se za telemetriju, finansijske transakcije, korisničke aktivnosti i druge podatke koji stalno pristižu.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "fair_scheduling",
            titleSr = "Fair scheduling",
            titleEn = "Pravično raspoređivanje",
            explanation = "Fair scheduling raspoređuje resurse tako da nijedna važna klasa poslova ne bude trajno zapostavljena. Sistem može uzimati u obzir prioritet, vreme čekanja i količinu već dobijenih resursa. Cilj je da veliki ili dugotrajni poslovi ne blokiraju sve kratke zahteve.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "idempotentnost",
            titleSr = "Idempotentnost",
            titleEn = "Idempotency",
            explanation = "Idempotentnost znači da ponavljanje iste operacije ne menja rezultat nakon njenog prvog uspešnog izvršavanja. Posebno je važna kada sistem koristi retry ili može primiti istu poruku više puta. Primer je zahtev za završetak porudžbine koji ne sme dva puta naplatiti korisnika.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "ingestion",
            titleSr = "Ingestion",
            titleEn = "Unos podataka",
            explanation = "Ingestion je proces prihvatanja podataka iz spoljnog izvora u sistem za dalju obradu. Podaci mogu dolaziti iz uređaja, fajlova, API-ja, događaja ili baza podataka. Nakon prijema obično slede validacija, transformacija i skladištenje.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "ingestion_sloj",
            titleSr = "Ingestion sloj",
            titleEn = "Ingestion layer",
            explanation = "Ingestion sloj je deo arhitekture zadužen za prijem podataka iz različitih izvora. On može proveravati format, identitet izvora, duplikate i osnovnu ispravnost podataka. Njegova uloga je da ostatku sistema obezbedi pouzdan i ujednačen ulaz.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "izolovani_worker_pool",
            titleSr = "Izolovani worker pool",
            titleEn = "Isolated worker pool",
            explanation = "Izolovani worker pool je posebna grupa radnika namenjena određenoj vrsti poslova. Time se sprečava da zahtevni batch zadaci zauzmu resurse potrebne interaktivnim korisnicima. Svaki pool može imati sopstvene kvote, prioritete i pravila skaliranja.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "izvrsni_red",
            titleSr = "Izvršni red",
            titleEn = "Execution queue",
            explanation = "Izvršni red čuva poslove koji čekaju da budu obrađeni. Scheduler ili worker uzima poslove iz reda prema definisanoj politici. Red omogućava kontrolu opterećenja, ali može postati usko grlo ako raste brže nego što se prazni.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "klasa_posla",
            titleSr = "Klasa posla",
            titleEn = "Job class",
            explanation = "Klasa posla predstavlja grupu zadataka sa sličnim zahtevima, prioritetom ili očekivanim vremenom izvršavanja. Primeri su interaktivni, batch, kratki i CPU-intenzivni poslovi. Razdvajanje po klasama omogućava pravednije i predvidljivije raspoređivanje.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "kontrolisani_retry",
            titleSr = "Kontrolisani retry",
            titleEn = "Controlled retry",
            explanation = "Kontrolisani retry je ponovno izvršavanje neuspele operacije prema jasno definisanim pravilima. On ograničava broj pokušaja, uvodi pauzu i razlikuje privremene od trajnih grešaka. Time se sprečava da retry dodatno optereti već nestabilan sistem.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "korelacija_dogadjaja",
            titleSr = "Korelacija događaja",
            titleEn = "Event correlation",
            explanation = "Korelacija događaja povezuje više događaja koji pripadaju istoj poslovnoj operaciji ili procesu. Za povezivanje se često koristi correlation ID, identifikator korisnika ili identifikator porudžbine. Tako se može pratiti ceo tok kroz više servisa i asinhronih koraka.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "kvota_resursa",
            titleSr = "Kvota resursa",
            titleEn = "Resource quota",
            explanation = "Kvota resursa određuje maksimalnu količinu CPU-a, memorije, vremena ili drugih resursa koju određeni korisnik ili klasa posla može koristiti. Ona sprečava da jedan deo sistema potroši sav raspoloživ kapacitet. Loše podešena kvota može izazvati blokiranje važnih poslova ili neiskorišćenost resursa.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "message_broker",
            titleSr = "Message broker",
            titleEn = "Posrednik poruka",
            explanation = "Message broker je sistem koji prima, čuva i prosleđuje poruke između proizvođača i potrošača. On omogućava da pošiljalac i primalac ne moraju biti dostupni u istom trenutku. Broker može podržavati redove, teme, potvrdu prijema i ponovno slanje poruka.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "message_queue",
            titleSr = "Message queue",
            titleEn = "Red poruka",
            explanation = "Message queue je struktura u kojoj poruke čekaju obradu. Proizvođači dodaju poruke, dok ih potrošači preuzimaju i obrađuju. Red pomaže pri raspodeli opterećenja i odvajanju brzine pošiljaoca od brzine primaoca.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "notifikacija",
            titleSr = "Notifikacija",
            titleEn = "Notification",
            explanation = "Notifikacija je poruka kojom sistem obaveštava korisnika ili drugi sistem o važnom događaju. Može biti poslata putem emaila, SMS-a, push poruke ili internog prikaza. Treba da sadrži relevantnu informaciju bez otkrivanja podataka korisnicima koji nemaju odgovarajuća prava.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "offline_queue",
            titleSr = "Offline queue",
            titleEn = "Offline red",
            explanation = "Offline queue privremeno čuva operacije ili poruke kada uređaj nema mrežnu vezu. Nakon ponovnog povezivanja, sačuvane stavke se šalju serveru. Sistem mora rešavati duplikate, promene redosleda i konflikte nastale tokom offline rada.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "out_of_order_dogadjaj",
            titleSr = "Out-of-order događaj",
            titleEn = "Događaj van redosleda",
            explanation = "Out-of-order događaj je događaj koji stigne nakon događaja koji se zapravo desio kasnije. To se može dogoditi zbog mrežnih kašnjenja, različitih redova ili paralelne obrade. Sistem mora koristiti vremenske oznake, verzije ili poslovna pravila da bi pravilno odredio redosled.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "outbox_publisher",
            titleSr = "Outbox publisher",
            titleEn = "Proces za objavljivanje outbox zapisa",
            explanation = "Outbox publisher čita neobjavljene zapise iz outbox skladišta i šalje ih message brokeru. Nakon uspešnog slanja označava zapis kao obrađen ili objavljen. Time se odvajaju transakcioni upis poslovnih podataka i pouzdano slanje događaja.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "outbox_zapis",
            titleSr = "Outbox zapis",
            titleEn = "Outbox record",
            explanation = "Outbox zapis je događaj sačuvan u istoj transakciji baze kao i poslovna promena koja ga je proizvela. Time se sprečava situacija u kojoj su podaci upisani, ali događaj nije objavljen. Poseban proces kasnije čita zapis i šalje ga brokeru.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "payment_webhook",
            titleSr = "Payment webhook",
            titleEn = "Webhook payment providera",
            explanation = "Payment webhook je poruka koju payment provider šalje aplikaciji kada se promeni status plaćanja. Njime se mogu potvrditi naplata, refundacija, neuspeh ili istek autorizacije. Aplikacija mora proveriti autentičnost webhook-a i idempotentno obraditi ponovljene poruke.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "pipeline",
            titleSr = "Pipeline",
            titleEn = "Processing pipeline",
            explanation = "Pipeline je niz povezanih koraka kroz koje podatak ili zahtev prolazi tokom obrade. Svaki korak obavlja određenu funkciju i prosleđuje rezultat sledećem koraku. Primer je tok učitavanja, validacije, transformacije i čuvanja dokumenta.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "politika_prioriteta",
            titleSr = "Politika prioriteta",
            titleEn = "Priority policy",
            explanation = "Politika prioriteta određuje kojim redosledom scheduler ili red obrađuje različite poslove. Može uzimati u obzir hitnost, tip korisnika, vreme čekanja i poslovni značaj. Politika mora sprečiti da poslovi nižeg prioriteta čekaju neograničeno dugo.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "poslovni_dogadjaj",
            titleSr = "Poslovni događaj",
            titleEn = "Business event",
            explanation = "Poslovni događaj označava promenu koja je značajna za poslovni proces. Primeri su izvršena naplata, odobren zahtev ili istekao ugovor. Za razliku od tehničkog događaja, njegov smisao je razumljiv i poslovnim korisnicima.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "pouzdana_isporuka_dogadjaja",
            titleSr = "Pouzdana isporuka događaja",
            titleEn = "Reliable event delivery",
            explanation = "Pouzdana isporuka događaja znači da sistem smanjuje mogućnost gubitka važnih poruka. Obično koristi trajno skladištenje, potvrdu prijema, retry i obrasce poput Transactional Outbox-a. Pouzdana isporuka ne znači nužno da će događaj stići tačno jednom, pa potrošači i dalje moraju biti idempotentni.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "pozadinski_zadatak",
            titleSr = "Pozadinski zadatak",
            titleEn = "Background job",
            explanation = "Pozadinski zadatak je posao koji se izvršava bez neposrednog čekanja korisnika. Koristi se za slanje emailova, obradu fajlova, izveštaje i druge sporije operacije. Njegov status i greške moraju se pratiti iako se ne izvršava u glavnom korisničkom zahtevu.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "precompute",
            titleSr = "Precompute",
            titleEn = "Precomputed data",
            explanation = "Precompute je unapred izračunavanje rezultata koji će kasnije biti često korišćeni. Time se smanjuje vreme potrebno da se odgovor pripremi u trenutku korisničkog zahteva. Cena je dodatno skladištenje i potreba da se rezultat osveži kada se izvorni podaci promene.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "preempcija",
            titleSr = "Preempcija",
            titleEn = "Preemption",
            explanation = "Preempcija je privremeno zaustavljanje jednog posla kako bi se resursi dodelili važnijem poslu. Koristi se kada hitni ili interaktivni zahtevi moraju dobiti prednost. Zaustavljeni posao se kasnije nastavlja ili ponovo pokreće prema pravilima sistema.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "priority_queue",
            titleSr = "Priority queue",
            titleEn = "Prioritetni red",
            explanation = "Priority queue je red u kome se stavke ne uzimaju samo prema vremenu dolaska, već i prema prioritetu. Važniji poslovi mogu biti obrađeni pre ranije pristiglih manje važnih poslova. Potrebno je uvesti zaštitu kako zadaci niskog prioriteta ne bi trajno ostali neobrađeni.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "redosled_dogadjaja",
            titleSr = "Redosled događaja",
            titleEn = "Event ordering",
            explanation = "Redosled događaja određuje kojim se poretkom promene primenjuju u sistemu. Važan je kada kasniji događaj zavisi od rezultata prethodnog događaja. Redosled se može čuvati sekvencnim brojevima, verzijama ili pravilima po ključu entiteta.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "request_response_komunikacija",
            titleSr = "Request-response komunikacija",
            titleEn = "Request–response",
            explanation = "Request-response je oblik komunikacije u kome klijent šalje zahtev i čeka odgovor servera. Pogodan je kada je rezultat potreban odmah za nastavak korisničkog toka. Njegova mana je što klijent direktno zavisi od dostupnosti i brzine servera.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "retry",
            titleSr = "Retry",
            titleEn = "Ponovni pokušaj",
            explanation = "Retry je ponovno pokretanje operacije koja nije uspela. Koristan je kod privremenih problema poput kratkog prekida mreže ili zauzetog servisa. Bez ograničenja i idempotentnosti može izazvati dupliranje operacija ili dodatno preopterećenje.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "scheduler",
            titleSr = "Scheduler",
            titleEn = "Planer poslova",
            explanation = "Scheduler određuje kada i gde će određeni posao biti izvršen. On bira raspoložive radnike, upravlja prioritetima i poštuje ograničenja resursa. Dobar scheduler povećava iskorišćenost sistema bez zanemarivanja važnih ili kratkih poslova.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "scheduler_po_domenu",
            titleSr = "Scheduler po domenu",
            titleEn = "Domain scheduler",
            explanation = "Scheduler po domenu upravlja poslovima koji pripadaju jednom određenom domenu ili funkcionalnoj oblasti. On može koristiti pravila prilagođena specifičnoj vrsti zadataka. Time se smanjuje složenost centralnog schedulera, ali raste potreba za koordinacijom između više planera.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "sinhrona_komunikacija",
            titleSr = "Sinhrona komunikacija",
            titleEn = "Synchronous communication",
            explanation = "Sinhrona komunikacija zahteva da pošiljalac sačeka odgovor primaoca pre nastavka rada. Jednostavna je za razumevanje i pogodna kada je rezultat odmah potreban. Ako je primalac spor ili nedostupan, pošiljalac takođe čeka ili dobija grešku.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "starvation",
            titleSr = "Starvation",
            titleEn = "Gladovanje zadatka",
            explanation = "Starvation nastaje kada određeni zadatak veoma dugo ne dobija resurse potrebne za izvršavanje. Najčešći uzrok je stalno davanje prednosti drugim poslovima. Sistem ga sprečava pravičnim raspoređivanjem, ograničenjem prioriteta ili povećavanjem prioriteta prema vremenu čekanja.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "stream_processing",
            titleSr = "Stream processing",
            titleEn = "Obrada toka",
            explanation = "Stream processing je obrada podataka dok neprekidno pristižu, bez čekanja da se formira cela grupa. Koristi se za telemetriju, transakcije, klikove i druge događaje u realnom ili skoro realnom vremenu. Sistem mora pravilno rešavati zakašnjele, duplirane i nepravilno poređane događaje.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "stream_processor",
            titleSr = "Stream processor",
            titleEn = "Procesor toka",
            explanation = "Stream processor je komponenta koja čita, transformiše i analizira događaje iz kontinuiranog toka. Može filtrirati podatke, računati agregate ili otkrivati obrasce. Često održava privremeno stanje kako bi povezivao događaje iz određenog vremenskog perioda.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "ugovor_dogadjaja",
            titleSr = "Ugovor događaja",
            titleEn = "Event contract",
            explanation = "Ugovor događaja definiše naziv, strukturu, značenje i obavezna polja događaja. On omogućava da publisher i consumer imaju zajedničko razumevanje poruke. Promene ugovora moraju biti verzionisane kako ne bi pokvarile postojeće potrošače.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "validacija_dogadjaja",
            titleSr = "Validacija događaja",
            titleEn = "Event validation",
            explanation = "Validacija događaja proverava da li događaj odgovara očekivanoj strukturi i poslovnim pravilima. Može kontrolisati obavezna polja, tipove podataka, identifikatore i vremenske oznake. Neispravni događaji se odbijaju, beleže ili šalju u poseban red za analizu.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "webhook",
            titleSr = "Webhook",
            titleEn = "Webhook callback",
            explanation = "Webhook je HTTP poruka koju jedan sistem automatski šalje drugom kada se dogodi određena promena. Primalac ne mora stalno da proverava status jer dobija obaveštenje čim ga pošiljalac pošalje. Webhook mora biti autentifikovan, ponovljiv i otporan na duplirane isporuke.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "worker",
            titleSr = "Worker",
            titleEn = "Worker proces",
            explanation = "Worker je proces ili komponenta koja preuzima poslove iz reda i izvršava ih. Može obrađivati fajlove, slati poruke, računati rezultate ili izvršavati druge pozadinske zadatke. Broj worker-a se često povećava ili smanjuje prema trenutnom opterećenju.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "worker_pool",
            titleSr = "Worker pool",
            titleEn = "Bazen radnika",
            explanation = "Worker pool je grupa worker procesa koji zajedno obrađuju isti ili sličan skup poslova. Poslovi se raspoređuju među radnicima radi paralelizacije i boljeg korišćenja resursa. Veličina pool-a mora biti usklađena sa kapacitetom baze, mreže i drugih zavisnih servisa.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        ),
        term(
            id = "zakasneli_dogadjaj",
            titleSr = "Zakasneli događaj",
            titleEn = "Late event",
            explanation = "Zakasneli događaj je događaj koji stigne značajno kasnije od vremena kada se stvarno dogodio. Može biti posledica offline uređaja, zagušenja mreže ili sporog posredničkog sistema. Obrada mora odlučiti da li će događaj promeniti postojeći rezultat, biti ignorisan ili pokrenuti korekciju.",
            categoryId = COMMUNICATION_EVENTS_PROCESSING
        )
    )

    private fun consistencyConcurrencyTerms(): List<EncyclopediaTerm> = listOf(
        term(
            id = "atomicnost",
            titleSr = "Atomičnost",
            titleEn = "Atomicity",
            explanation = "Atomarnost znači da se grupa povezanih operacija izvršava kao jedna nedeljiva celina. Sve operacije moraju uspešno da se završe ili se sistem vraća u stanje pre njihovog početka. Primer je plaćanje kod kog naplata i evidentiranje porudžbine ne smeju ostati samo delimično izvršeni.",
            categoryId = CONSISTENCY_CONCURRENCY
        ),
        term(
            id = "atomska_operacija",
            titleSr = "Atomska operacija",
            titleEn = "Atomic operation",
            explanation = "Atomska operacija je operacija koja se sa stanovišta drugih procesa izvršava odjednom i ne može se videti u delimično završenom stanju. Drugi zahtevi mogu videti stanje pre ili nakon operacije, ali ne i njene međukorake. Primer je atomsko smanjivanje dostupne količine proizvoda tokom rezervacije zaliha.",
            categoryId = CONSISTENCY_CONCURRENCY
        ),
        term(
            id = "delimican_uspeh",
            titleSr = "Delimičan uspeh",
            titleEn = "Partial success",
            explanation = "Delimičan uspeh nastaje kada neki koraci složene operacije uspeju, dok drugi ne budu završeni. Takvo stanje može ostaviti sistem poslovno nekonzistentnim i zahtevati kompenzaciju ili nastavak obrade. Primer je uspešna naplata nakon koje sistem nije uspeo da kreira potvrdu porudžbine.",
            categoryId = CONSISTENCY_CONCURRENCY
        ),
        term(
            id = "dupla_naplata",
            titleSr = "Dupla naplata",
            titleEn = "Double charge",
            explanation = "Dupla naplata nastaje kada se korisniku isti iznos naplati više puta za istu obavezu. Može biti izazvana ponovljenim zahtevom, timeout-om, dupliranim webhook-om ili nedostatkom idempotentnosti. Sistem je sprečava jedinstvenim identifikatorima pokušaja plaćanja i proverom prethodno izvršenih operacija.",
            categoryId = CONSISTENCY_CONCURRENCY
        ),
        term(
            id = "dupla_rezervacija",
            titleSr = "Dupla rezervacija",
            titleEn = "Double booking",
            explanation = "Dupla rezervacija nastaje kada isti ograničeni resurs bude potvrđen za više korisnika u istom periodu. Najčešće je posledica konkurentnih zahteva koji istovremeno vide resurs kao slobodan. Sprečava se transakcijama, zaključavanjem, jedinstvenim ograničenjima ili atomskom rezervacijom.",
            categoryId = CONSISTENCY_CONCURRENCY
        ),
        term(
            id = "dupli_upis",
            titleSr = "Dupli upis",
            titleEn = "Duplicate write",
            explanation = "Dupli upis je višestruko čuvanje istog podatka ili poslovne operacije. Može nastati kada se zahtev ponovi nakon mrežne greške ili kada se ista poruka obradi više puta. Idempotentnost, deduplikacija i jedinstvena ograničenja pomažu da se takav upis spreči.",
            categoryId = CONSISTENCY_CONCURRENCY
        ),
        term(
            id = "duplo_glasanje",
            titleSr = "Duplo glasanje",
            titleEn = "Double voting",
            explanation = "Duplo glasanje nastaje kada isti korisnik ili identitet više puta glasa u procesu u kome je dozvoljen samo jedan glas. Uzrok može biti konkurentno slanje zahteva, greška u identifikaciji ili nedovoljna kontrola na nivou baze. Sistem ga sprečava jedinstvenim pravilom koje povezuje glasača i konkretno glasanje.",
            categoryId = CONSISTENCY_CONCURRENCY
        ),
        term(
            id = "eventualna_konzistentnost",
            titleSr = "Eventualna konzistentnost",
            titleEn = "Eventual consistency",
            explanation = "Eventualna konzistentnost znači da različiti delovi sistema privremeno mogu prikazivati različita stanja. Ako prestanu nove promene i poruke se uspešno obrade, svi delovi će vremenom doći do istog rezultata. Ovaj model je pogodan za distribuirane sisteme u kojima je kratkotrajno kašnjenje prihvatljivo.",
            categoryId = CONSISTENCY_CONCURRENCY
        ),
        term(
            id = "informativna_dostupnost",
            titleSr = "Informativna dostupnost",
            titleEn = "Informational availability",
            explanation = "Informativna dostupnost je procena da je resurs slobodan zasnovana na read modelu, kešu ili poslednjem poznatom stanju. Ona korisniku pomaže u pretrazi, ali ne predstavlja garanciju da će rezervacija uspeti. Stvarna dostupnost mora ponovo da se proveri tokom konačne rezervacije.",
            categoryId = CONSISTENCY_CONCURRENCY
        ),
        term(
            id = "integritet_podataka",
            titleSr = "Integritet podataka",
            titleEn = "Data integrity",
            explanation = "Integritet podataka znači da su podaci tačni, potpuni, povezani i zaštićeni od nedozvoljenih promena. Sistem mora sprečiti nevažeće vrednosti, izgubljene veze i međusobno protivrečne zapise. Integritet se čuva validacijom, ograničenjima baze, transakcijama, auditom i kontrolom pristupa.",
            categoryId = CONSISTENCY_CONCURRENCY
        ),
        term(
            id = "istek_rezervacije",
            titleSr = "Istek rezervacije",
            titleEn = "Reservation expiry",
            explanation = "Istek rezervacije nastaje kada privremeno zadržan resurs nije potvrđen u dozvoljenom roku. Sistem tada oslobađa resurs kako bi ponovo bio dostupan drugim korisnicima. Rok sprečava da napušteni checkout ili prekinuti proces trajno blokira kapacitet.",
            categoryId = CONSISTENCY_CONCURRENCY
        ),
        term(
            id = "jaka_konzistentnost",
            titleSr = "Jaka konzistentnost",
            titleEn = "Strong consistency",
            explanation = "Jaka konzistentnost obezbeđuje da svaki uspešan upis odmah bude vidljiv svim narednim čitanjima. Korisnik ne bi trebalo da dobije zastarelo stanje nakon potvrđene promene. Ovaj model olakšava razumevanje sistema, ali može povećati latenciju i smanjiti dostupnost u distribuiranom okruženju.",
            categoryId = CONSISTENCY_CONCURRENCY
        ),
        term(
            id = "konacna_rezervacija",
            titleSr = "Konačna rezervacija",
            titleEn = "Confirmed reservation",
            explanation = "Konačna rezervacija je potvrđeno zauzimanje resursa koje više nije samo privremeno zadržavanje. Nastaje nakon ispunjavanja potrebnih uslova, kao što su uspešna naplata ili odobrenje zahteva. Sistem mora da je čuva kao autoritativno stanje i spreči njeno nenamerno dupliranje.",
            categoryId = CONSISTENCY_CONCURRENCY
        ),
        term(
            id = "konflikt_izmena",
            titleSr = "Konflikt izmena",
            titleEn = "Edit conflict",
            explanation = "Konflikt izmena nastaje kada više korisnika ili procesa menja isti podatak na međusobno neusklađen način. Jedna izmena može biti zasnovana na staroj verziji i prepisati novije podatke. Sistem može tražiti ručno spajanje, odbiti zastareli upis ili primeniti unapred definisano pravilo.",
            categoryId = CONSISTENCY_CONCURRENCY
        ),
        term(
            id = "konflikt_podataka",
            titleSr = "Konflikt podataka",
            titleEn = "Data conflict",
            explanation = "Konflikt podataka postoji kada dva izvora ili zapisa daju različite vrednosti za istu činjenicu. Može nastati tokom offline rada, sinhronizacije, migracije ili paralelnih izmena. Za njegovo rešavanje potreban je autoritativni izvor ili jasno definisana strategija razrešavanja.",
            categoryId = CONSISTENCY_CONCURRENCY
        ),
        term(
            id = "konflikt_verzija",
            titleSr = "Konflikt verzija",
            titleEn = "Version conflict",
            explanation = "Konflikt verzija nastaje kada se pokušava izmena objekta na osnovu verzije koja više nije aktuelna. Sistem prepoznaje da je drugi proces u međuvremenu već promenio isti objekat. Zastareli zahtev se tada odbija, ponovo učitava ili šalje na razrešavanje konflikta.",
            categoryId = CONSISTENCY_CONCURRENCY
        ),
        term(
            id = "konkurentni_upis",
            titleSr = "Konkurentni upis",
            titleEn = "Concurrent write",
            explanation = "Konkurentni upis označava situaciju u kojoj više procesa skoro istovremeno pokušava da promeni iste podatke. Bez odgovarajuće kontrole jedan upis može prepisati ili poništiti rezultat drugog. Problem se rešava transakcijama, zaključavanjem, verzionisanjem ili atomskim operacijama.",
            categoryId = CONSISTENCY_CONCURRENCY
        ),
        term(
            id = "konkurentni_zahtev",
            titleSr = "Konkurentni zahtev",
            titleEn = "Concurrent request",
            explanation = "Konkurentni zahtevi su zahtevi koji se izvršavaju istovremeno ili se vremenski preklapaju. Oni mogu nezavisno pročitati isto početno stanje i zatim doneti međusobno suprotne odluke. Sistem mora pretpostaviti da takvi zahtevi postoje i zaštititi kritične poslovne operacije.",
            categoryId = CONSISTENCY_CONCURRENCY
        ),
        term(
            id = "kontrola_verzije_poslovnog_stanja",
            titleSr = "Kontrola verzije poslovnog stanja",
            titleEn = "Version-based concurrency control",
            explanation = "Kontrola verzije poslovnog stanja dodaje objektu broj ili oznaku verzije koja se proverava prilikom izmene. Upis je dozvoljen samo ako se očekivana verzija podudara sa trenutnom verzijom u skladištu. Time se otkrivaju zastareli zahtevi bez dugotrajnog zaključavanja resursa.",
            categoryId = CONSISTENCY_CONCURRENCY
        ),
        term(
            id = "konzistentnost",
            titleSr = "Konzistentnost",
            titleEn = "Consistency",
            explanation = "Konzistentnost opisuje stepen u kome različiti delovi sistema prikazuju usklađene i poslovno ispravne podatke. Njeno tačno značenje zavisi od pravila domena i modela distribuirane komunikacije. Sistem može zahtevati trenutnu konzistentnost za plaćanja, a prihvatiti kratko kašnjenje kod analitike.",
            categoryId = CONSISTENCY_CONCURRENCY
        ),
        term(
            id = "last_write_wins",
            titleSr = "Last write wins",
            titleEn = "Poslednji upis pobeđuje",
            explanation = "Last write wins je strategija u kojoj se kod konflikta zadržava izmena sa najnovijom vremenskom oznakom ili verzijom. Jednostavna je za automatizaciju jer ne zahteva ručno spajanje podataka. Može ipak izgubiti važnu raniju izmenu ako vreme ili redosled događaja nisu pouzdani.",
            categoryId = CONSISTENCY_CONCURRENCY
        ),
        term(
            id = "nepoznat_rezultat_operacije",
            titleSr = "Nepoznat rezultat operacije",
            titleEn = "Unknown operation outcome",
            explanation = "Nepoznat rezultat operacije nastaje kada klijent ne zna da li je zahtev uspešno izvršen. To se često događa kada server obradi zahtev, ali se odgovor izgubi zbog timeout-a ili prekida mreže. Pre ponavljanja operacije sistem mora proveriti autoritativno stanje ili koristiti idempotentni ključ.",
            categoryId = CONSISTENCY_CONCURRENCY
        ),
        term(
            id = "optimisticko_zakljucavanje",
            titleSr = "Optimističko zaključavanje",
            titleEn = "Optimistic locking",
            explanation = "Optimističko zaključavanje pretpostavlja da su konflikti relativno retki i ne zaključava podatak tokom čitanja. Prilikom upisa proverava se da li je verzija podatka ostala nepromenjena. Ako je drugi proces već izvršio izmenu, upis se odbija kao konfliktan.",
            categoryId = CONSISTENCY_CONCURRENCY
        ),
        term(
            id = "potvrdjena_dostupnost",
            titleSr = "Potvrđena dostupnost",
            titleEn = "Confirmed availability",
            explanation = "Potvrđena dostupnost znači da je sistem u autoritativnom izvoru proverio da je resurs zaista raspoloživ. Provera se obavlja neposredno pre rezervacije ili kao deo iste atomske operacije. Za razliku od informativne dostupnosti, ona može biti osnova za pouzdanu poslovnu potvrdu.",
            categoryId = CONSISTENCY_CONCURRENCY
        ),
        term(
            id = "privremena_rezervacija",
            titleSr = "Privremena rezervacija",
            titleEn = "Temporary reservation",
            explanation = "Privremena rezervacija kratkotrajno zadržava resurs dok korisnik završava povezani proces. Resurs se ne nudi drugim korisnicima, ali rezervacija još nije konačno potvrđena. Ako korisnik ne završi kupovinu ili postupak, rezervacija ističe i resurs se oslobađa.",
            categoryId = CONSISTENCY_CONCURRENCY
        ),
        term(
            id = "race_condition",
            titleSr = "Race condition",
            titleEn = "Uslov trke",
            explanation = "Race condition nastaje kada rezultat zavisi od nepredvidivog redosleda izvršavanja više konkurentnih operacija. Isti kod može nekada raditi ispravno, a nekada proizvesti duplikate ili pogrešno stanje. Sprečava se sinhronizacijom, transakcijama, zaključavanjem i pravilnim oblikovanjem atomskih operacija.",
            categoryId = CONSISTENCY_CONCURRENCY
        ),
        term(
            id = "razresavanje_konflikata",
            titleSr = "Razrešavanje konflikata",
            titleEn = "Conflict resolution",
            explanation = "Razrešavanje konflikata je postupak odlučivanja koja vrednost ili izmena treba da bude sačuvana. Rešenje može biti automatsko, zasnovano na verziji, vremenu, prioritetu ili poslovnom pravilu. Kada automatska odluka nije bezbedna, konflikt se prosleđuje korisniku ili operateru.",
            categoryId = CONSISTENCY_CONCURRENCY
        ),
        term(
            id = "reconciliation",
            titleSr = "Reconciliation",
            titleEn = "Usklađivanje stanja",
            explanation = "Reconciliation je poređenje podataka između dva sistema radi pronalaženja i ispravljanja razlika. Često se koristi za usklađivanje lokalnih payment zapisa sa zvaničnim stanjem kod providera. Proces može potvrditi nedovršenu operaciju, prijaviti neslaganje ili pokrenuti korektivnu obradu.",
            categoryId = CONSISTENCY_CONCURRENCY
        ),
        term(
            id = "rekonstrukcija_stanja",
            titleSr = "Rekonstrukcija stanja",
            titleEn = "State reconstruction",
            explanation = "Rekonstrukcija stanja je ponovno izračunavanje trenutnog stanja na osnovu sačuvanih događaja, verzija ili istorijskih zapisa. Koristi se kada direktno stanje nedostaje, nije pouzdano ili treba proveriti kako je nastalo. Tačnost rekonstrukcije zavisi od potpunosti i pravilnog redosleda izvornih zapisa.",
            categoryId = CONSISTENCY_CONCURRENCY
        ),
        term(
            id = "rezervacija_resursa",
            titleSr = "Rezervacija resursa",
            titleEn = "Resource reservation",
            explanation = "Rezervacija resursa je postupak kojim se ograničeni kapacitet dodeljuje određenom korisniku ili procesu. Resurs može biti sedište, termin, proizvod, soba ili računarski kapacitet. Sistem mora kontrolisati konkurentne zahteve kako isti resurs ne bi bio obećan više puta.",
            categoryId = CONSISTENCY_CONCURRENCY
        ),
        term(
            id = "stale_write",
            titleSr = "Stale write",
            titleEn = "Zastareli upis",
            explanation = "Stale write je pokušaj čuvanja izmene zasnovane na staroj verziji podataka. Takav upis može neprimećeno prepisati novije promene drugog korisnika ili procesa. Kontrola verzija omogućava da se zastareli upis otkrije i odbije.",
            categoryId = CONSISTENCY_CONCURRENCY
        ),
        term(
            id = "unique_constraint",
            titleSr = "Unique constraint",
            titleEn = "Jedinstveno ograničenje",
            explanation = "Unique constraint je pravilo baze koje zahteva da određena vrednost ili kombinacija vrednosti bude jedinstvena. Baza odbija novi zapis ako bi njime nastao nedozvoljeni duplikat. Može se koristiti za sprečavanje duplog glasanja, ponovljene rezervacije ili više naloga sa istim identifikatorom.",
            categoryId = CONSISTENCY_CONCURRENCY
        )
    )

    private fun designPatternsTerms(): List<EncyclopediaTerm> = listOf(
        term(
            id = "abstract_factory",
            titleSr = "Abstract Factory",
            titleEn = "Apstraktna fabrika",
            explanation = "Abstract Factory je kreacioni obrazac koji omogućava pravljenje porodica međusobno povezanih objekata. Klijent koristi zajednički interfejs fabrike i ne mora da poznaje konkretne klase proizvoda. Koristan je kada svi objekti iz jednog paketa moraju biti međusobno usklađeni, kao kod različitih UI tema.",
            categoryId = DESIGN_PATTERNS
        ),
        term(
            id = "adapter",
            titleSr = "Adapter",
            titleEn = "Adapter pattern",
            explanation = "Adapter je strukturni obrazac koji omogućava saradnju klasa sa nekompatibilnim interfejsima. On prihvata poziv u formatu koji sistem očekuje i prevodi ga u poziv koji razume postojeća ili spoljna komponenta. Koristi se kada nova biblioteka treba da se uključi bez izmene ostatka aplikacije.",
            categoryId = DESIGN_PATTERNS
        ),
        term(
            id = "bridge",
            titleSr = "Bridge",
            titleEn = "Bridge pattern",
            explanation = "Bridge je strukturni obrazac koji razdvaja apstrakciju od njene implementacije. Obe strane mogu da se razvijaju nezavisno bez pravljenja posebne klase za svaku moguću kombinaciju. Pogodan je kada sistem ima dve nezavisne dimenzije promena, kao što su tip poruke i kanal slanja.",
            categoryId = DESIGN_PATTERNS
        ),
        term(
            id = "builder",
            titleSr = "Builder",
            titleEn = "Builder pattern",
            explanation = "Builder je kreacioni obrazac za postepeno i kontrolisano sastavljanje složenog objekta. Omogućava da se različite varijante istog proizvoda naprave korišćenjem jasnih koraka. Koristan je kada objekat ima mnogo opcionih delova ili bi njegov konstruktor imao previše parametara.",
            categoryId = DESIGN_PATTERNS
        ),
        term(
            id = "chain_of_responsibility",
            titleSr = "Chain of Responsibility",
            titleEn = "Lanac odgovornosti",
            explanation = "Chain of Responsibility je obrazac ponašanja u kome zahtev prolazi kroz niz mogućih obrađivača. Svaki obrađivač može da obradi zahtev, zaustavi tok ili ga prosledi sledećem članu lanca. Koristi se za validaciju, autentikaciju, autorizaciju i druge višekoračne provere.",
            categoryId = DESIGN_PATTERNS
        ),
        term(
            id = "command",
            titleSr = "Command",
            titleEn = "Command pattern",
            explanation = "Command je obrazac ponašanja koji zahtev ili akciju predstavlja kao poseban objekat. Takav objekat može da se čuva, prosleđuje, stavlja u red, ponavlja ili poništava. Koristan je za toolbar dugmad, istoriju akcija, undo i automatizovane tokove.",
            categoryId = DESIGN_PATTERNS
        ),
        term(
            id = "composite",
            titleSr = "Composite",
            titleEn = "Composite pattern",
            explanation = "Composite je strukturni obrazac koji omogućava isti tretman pojedinačnih objekata i grupa objekata. Pojedinačni element i kompozit implementiraju zajednički interfejs, dok kompozit može sadržati druge elemente istog tipa. Koristi se za stabla, menije, foldere i druge hijerarhijske strukture.",
            categoryId = DESIGN_PATTERNS
        ),
        term(
            id = "decorator",
            titleSr = "Decorator",
            titleEn = "Decorator pattern",
            explanation = "Decorator je strukturni obrazac koji dinamički dodaje ponašanje postojećem objektu. Dekorater koristi isti interfejs kao osnovni objekat i prosleđuje mu pozive uz dodatnu obradu. Koristan je za kombinovanje logovanja, kompresije, šifrovanja i sličnih opcionih funkcionalnosti.",
            categoryId = DESIGN_PATTERNS
        ),
        term(
            id = "facade",
            titleSr = "Facade",
            titleEn = "Facade pattern",
            explanation = "Facade je strukturni obrazac koji pruža jednostavan interfejs prema složenom podsistemu. Klijent poziva jednu objedinjenu komponentu umesto da direktno upravlja velikim brojem unutrašnjih servisa. Koristi se za smanjivanje spregnutosti i skrivanje detalja složenog procesa.",
            categoryId = DESIGN_PATTERNS
        ),
        term(
            id = "factory_method",
            titleSr = "Factory Method",
            titleEn = "Fabrička metoda",
            explanation = "Factory Method je kreacioni obrazac koji definiše zajednički način pravljenja objekta, ali izbor konkretne klase prepušta podklasi ili konkretnoj fabrici. Klijentski kod radi sa apstraktnim proizvodom i ne mora da zna koja je tačna klasa napravljena. Koristan je kada se očekuje dodavanje novih tipova proizvoda bez izmene koda koji ih koristi.",
            categoryId = DESIGN_PATTERNS
        ),
        term(
            id = "flyweight",
            titleSr = "Flyweight",
            titleEn = "Flyweight pattern",
            explanation = "Flyweight je strukturni obrazac koji smanjuje potrošnju memorije deljenjem zajedničkog stanja između velikog broja objekata. Deljeni podaci čuvaju se u Flyweight objektu, dok se jedinstveno stanje prosleđuje ili čuva odvojeno. Pogodan je za veliki broj sitnih i sličnih objekata, kao što su karakteri teksta, stabla ili projektili u igri.",
            categoryId = DESIGN_PATTERNS
        ),
        term(
            id = "gof_obrasci",
            titleSr = "GoF obrasci",
            titleEn = "Gang of Four patterns",
            explanation = "GoF obrasci su skup od 23 klasična obrasca projektovanja opisana u knjizi autora poznatih kao Gang of Four. Podeljeni su na kreacione, strukturne i obrasce ponašanja. Predstavljaju zajednički rečnik za rešavanje često ponavljanih problema objektno-orijentisanog dizajna.",
            categoryId = DESIGN_PATTERNS
        ),
        term(
            id = "interpreter",
            titleSr = "Interpreter",
            titleEn = "Interpreter pattern",
            explanation = "Interpreter je obrazac ponašanja koji definiše način predstavljanja i tumačenja pravila jednostavnog jezika. Svaka vrsta izraza obično ima klasu koja zna kako da interpretira svoj deo strukture. Koristi se za mini-jezike, filtere, matematičke izraze i jednostavna poslovna pravila.",
            categoryId = DESIGN_PATTERNS
        ),
        term(
            id = "iterator",
            titleSr = "Iterator",
            titleEn = "Iterator pattern",
            explanation = "Iterator je obrazac ponašanja koji omogućava prolazak kroz kolekciju bez otkrivanja njene unutrašnje strukture. Klijent koristi operacije kao što su `hasNext()` i `next()` umesto direktnog rada sa skladištenjem elemenata. Koristan je kada ista kolekcija treba da podrži više različitih načina obilaska.",
            categoryId = DESIGN_PATTERNS
        ),
        term(
            id = "kreacioni_obrazac",
            titleSr = "Kreacioni obrazac",
            titleEn = "Creational pattern",
            explanation = "Kreacioni obrazac se bavi načinom nastanka objekata i odvajanjem kreiranja od njihovog korišćenja. Njegov cilj je da smanji zavisnost klijenta od konkretnih klasa i složenih konstruktora. U ovu grupu spadaju Singleton, Factory Method, Abstract Factory, Builder i Prototype.",
            categoryId = DESIGN_PATTERNS
        ),
        term(
            id = "mediator",
            titleSr = "Mediator",
            titleEn = "Mediator pattern",
            explanation = "Mediator je obrazac ponašanja koji centralizuje komunikaciju između većeg broja međuzavisnih objekata. Komponente komuniciraju preko posrednika umesto da direktno poznaju sve ostale komponente. Koristan je u složenim formama, dijalozima i drugim sistemima sa mnogo pravila međusobne saradnje.",
            categoryId = DESIGN_PATTERNS
        ),
        term(
            id = "memento",
            titleSr = "Memento",
            titleEn = "Memento pattern",
            explanation = "Memento je obrazac ponašanja koji omogućava čuvanje i vraćanje prethodnog stanja objekta. Stanje se čuva u posebnom snapshot objektu bez izlaganja unutrašnjih detalja drugim delovima sistema. Najčešće se koristi za undo, istoriju verzija i save-state funkcionalnosti.",
            categoryId = DESIGN_PATTERNS
        ),
        term(
            id = "obrazac_ponasanja",
            titleSr = "Obrazac ponašanja",
            titleEn = "Behavioral pattern",
            explanation = "Obrazac ponašanja opisuje način saradnje objekata i raspodele odgovornosti tokom izvršavanja sistema. Fokus je na komunikaciji, algoritmima, promenama stanja i obradi zahteva. U ovu grupu spadaju Strategy, Observer, Command, State, Mediator, Visitor i drugi obrasci.",
            categoryId = DESIGN_PATTERNS
        ),
        term(
            id = "obrazac_projektovanja",
            titleSr = "Obrazac projektovanja",
            titleEn = "Design Pattern",
            explanation = "Obrazac projektovanja je provereno i ponovljivo rešenje za čest problem u dizajnu softvera. On ne predstavlja gotov kod, već opis strukture, uloga i saradnje objekata. Obrasci pomažu timu da koristi zajednički jezik i izbegne ponavljanje poznatih projektantskih grešaka.",
            categoryId = DESIGN_PATTERNS
        ),
        term(
            id = "observer",
            titleSr = "Observer",
            titleEn = "Observer pattern",
            explanation = "Observer je obrazac ponašanja u kome jedan objekat obaveštava više pretplaćenih objekata kada mu se promeni stanje. Subject radi preko zajedničkog Observer interfejsa i ne mora da poznaje detalje konkretnih posmatrača. Koristan je za notifikacije, osvežavanje prikaza i reakcije više modula na jednu promenu.",
            categoryId = DESIGN_PATTERNS
        ),
        term(
            id = "prototype",
            titleSr = "Prototype",
            titleEn = "Prototype pattern",
            explanation = "Prototype je kreacioni obrazac koji pravi nove objekte kopiranjem već postojećeg objekta. Pogodan je kada je početno kreiranje složeno, skupo ili zahteva mnogo podešavanja. Klonirana instanca se nakon kopiranja može dodatno prilagoditi konkretnim potrebama.",
            categoryId = DESIGN_PATTERNS
        ),
        term(
            id = "proxy",
            titleSr = "Proxy",
            titleEn = "Proxy pattern",
            explanation = "Proxy je strukturni obrazac koji postavlja zastupnika ispred stvarnog objekta. Zastupnik može kontrolisati pristup, odložiti učitavanje, dodati keširanje ili upravljati udaljenom komunikacijom. Klijent koristi isti interfejs i često ne zna da ne radi direktno sa stvarnim objektom.",
            categoryId = DESIGN_PATTERNS
        ),
        term(
            id = "publish_subscribe",
            titleSr = "Publish/Subscribe",
            titleEn = "Pub/Sub",
            explanation = "Publish/Subscribe je obrazac komunikacije u kome izdavač objavljuje poruke ili događaje bez poznavanja konkretnih primalaca. Pretplatnici biraju teme ili vrste događaja koje žele da primaju. Pogodan je za slabo povezane i proširive sisteme sa velikim brojem nezavisnih reakcija.",
            categoryId = DESIGN_PATTERNS
        ),
        term(
            id = "singleton",
            titleSr = "Singleton",
            titleEn = "Singleton pattern",
            explanation = "Singleton je kreacioni obrazac koji obezbeđuje postojanje samo jedne instance određene klase. Klasa obično ima privatni konstruktor i javnu metodu preko koje se pristupa zajedničkoj instanci. Koristi se za centralne konfiguracije ili koordinatore, ali može otežati testiranje ako se primenjuje bez potrebe.",
            categoryId = DESIGN_PATTERNS
        ),
        term(
            id = "state",
            titleSr = "State",
            titleEn = "State pattern",
            explanation = "State je obrazac ponašanja koji menja ponašanje objekta u zavisnosti od njegovog trenutnog unutrašnjeg stanja. Svako stanje se može predstaviti posebnom klasom sa sopstvenim dozvoljenim operacijama i prelazima. Koristan je za dokumente, narudžbine, tikete i druge objekte sa jasno definisanim životnim ciklusom.",
            categoryId = DESIGN_PATTERNS
        ),
        term(
            id = "strangler_obrazac",
            titleSr = "Strangler obrazac",
            titleEn = "Strangler Fig pattern",
            explanation = "Strangler obrazac je arhitektonski pristup postepenoj zameni starog sistema novim komponentama. Nove funkcionalnosti se uvode oko postojećeg sistema, a delovi starog rešenja se vremenom gase. Time se smanjuje rizik u odnosu na potpunu zamenu sistema u jednom koraku.",
            categoryId = DESIGN_PATTERNS
        ),
        term(
            id = "strategy",
            titleSr = "Strategy",
            titleEn = "Strategy pattern",
            explanation = "Strategy je obrazac ponašanja koji porodicu algoritama izdvaja iza zajedničkog interfejsa. Klijent može da promeni algoritam bez menjanja glavnog toka obrade. Koristi se za obračun cena, popuste, rutiranje, sortiranje i druga promenljiva poslovna pravila.",
            categoryId = DESIGN_PATTERNS
        ),
        term(
            id = "strukturni_obrazac",
            titleSr = "Strukturni obrazac",
            titleEn = "Structural pattern",
            explanation = "Strukturni obrazac opisuje način povezivanja klasa i objekata u veće i fleksibilnije celine. Njegov cilj je da pojednostavi zavisnosti i omogući menjanje strukture bez velikih izmena postojećeg koda. U ovu grupu spadaju Adapter, Bridge, Composite, Decorator, Facade, Flyweight i Proxy.",
            categoryId = DESIGN_PATTERNS
        ),
        term(
            id = "template_method",
            titleSr = "Template Method",
            titleEn = "Template Method pattern",
            explanation = "Template Method je obrazac ponašanja koji definiše fiksni kostur algoritma u osnovnoj klasi. Podklase mogu menjati samo određene korake, dok redosled celog procesa ostaje isti. Koristi se kada više procesa deli zajednički tok, ali se razlikuje u pojedinim fazama.",
            categoryId = DESIGN_PATTERNS
        ),
        term(
            id = "transactional_outbox",
            titleSr = "Transactional Outbox",
            titleEn = "Outbox pattern",
            explanation = "Transactional Outbox je obrazac za pouzdano objavljivanje događaja nakon promene poslovnih podataka. Poslovni podatak i outbox zapis čuvaju se u istoj transakciji baze, a poseban proces kasnije objavljuje događaj. Time se sprečava da podaci budu uspešno upisani, a odgovarajući događaj izgubljen.",
            categoryId = DESIGN_PATTERNS
        ),
        term(
            id = "visitor",
            titleSr = "Visitor",
            titleEn = "Visitor pattern",
            explanation = "Visitor je obrazac ponašanja koji omogućava dodavanje novih operacija nad postojećom strukturom objekata bez menjanja njihovih klasa. Elementi prihvataju Visitor objekat, dok konkretni visitor-i sadrže logiku različitih operacija. Pogodan je kada je struktura elemenata stabilna, a nove operacije se dodaju često.",
            categoryId = DESIGN_PATTERNS
        )
    )

    private fun observabilityDevopsIncidentsTerms(): List<EncyclopediaTerm> = listOf(
        term(
            id = "analiza_incidenta",
            titleSr = "Analiza incidenta",
            titleEn = "Incident analysis",
            explanation = "Analiza incidenta je sistematsko ispitivanje problema koji se dogodio u produkcionom sistemu. Njome se utvrđuju tok događaja, pogođene komponente, posledice i faktori koji su doveli do problema. Cilj nije samo uklanjanje trenutne greške, već i sprečavanje njenog ponavljanja.",
            categoryId = OBSERVABILITY_DEVOPS_INCIDENTS
        ),
        term(
            id = "ci_cd",
            titleSr = "CI/CD",
            titleEn = "Continuous Integration / Continuous Delivery",
            explanation = "CI/CD je skup automatizovanih praksi za integraciju, testiranje i isporučivanje izmena softvera. Continuous Integration često proverava kod nakon svake izmene, dok Continuous Delivery priprema proverenu verziju za postavljanje. Ovaj pristup ubrzava razvoj i smanjuje rizik od ručnih grešaka prilikom izdavanja.",
            categoryId = OBSERVABILITY_DEVOPS_INCIDENTS
        ),
        term(
            id = "dashboard",
            titleSr = "Dashboard",
            titleEn = "Kontrolna tabla",
            explanation = "Dashboard je vizuelni prikaz najvažnijih podataka, metrika i statusa sistema. On objedinjuje grafikone, brojače, upozorenja i druge informacije na jednom mestu. Korisniku omogućava da brzo uoči stanje sistema i moguće probleme.",
            categoryId = OBSERVABILITY_DEVOPS_INCIDENTS
        ),
        term(
            id = "debagovanje",
            titleSr = "Debagovanje",
            titleEn = "Debugging",
            explanation = "Debagovanje je proces pronalaženja, razumevanja i ispravljanja grešaka u softveru. Programer analizira kod, promenljive, logove i redosled izvršavanja kako bi pronašao uzrok neočekivanog ponašanja. Cilj je da se ukloni stvarni uzrok problema, a ne samo njegov vidljivi simptom.",
            categoryId = OBSERVABILITY_DEVOPS_INCIDENTS
        ),
        term(
            id = "deployment",
            titleSr = "Deployment",
            titleEn = "Postavljanje aplikacije",
            explanation = "Deployment je proces postavljanja nove verzije aplikacije u određeno okruženje. Može obuhvatiti instaliranje koda, konfiguraciju servisa, migraciju baze i pokretanje potrebne infrastrukture. Deployment može biti ručni ili automatizovan kroz CI/CD pipeline.",
            categoryId = OBSERVABILITY_DEVOPS_INCIDENTS
        ),
        term(
            id = "developer_onboarding",
            titleSr = "Developer onboarding",
            titleEn = "Uvođenje developera u projekat",
            explanation = "Developer onboarding je proces kojim se novi programer upoznaje sa projektom, timom i načinom rada. Obuhvata podešavanje okruženja, pregled arhitekture, pravila razvoja i prve praktične zadatke. Dobar onboarding skraćuje vreme potrebno da novi član počne samostalno da doprinosi.",
            categoryId = OBSERVABILITY_DEVOPS_INCIDENTS
        ),
        term(
            id = "distribuirani_tracing",
            titleSr = "Distribuirani tracing",
            titleEn = "Distributed tracing",
            explanation = "Distribuirani tracing prati jedan zahtev dok prolazi kroz više servisa distribuiranog sistema. Svakom zahtevu se obično dodeljuje zajednički identifikator kojim se povezuju njegovi pojedinačni koraci. Na taj način se mogu pronaći spor servis, greška ili prekid u složenom toku.",
            categoryId = OBSERVABILITY_DEVOPS_INCIDENTS
        ),
        term(
            id = "feature_flag",
            titleSr = "Feature flag",
            titleEn = "Zastavica funkcionalnosti",
            explanation = "Feature flag je konfiguracioni prekidač kojim se funkcionalnost uključuje ili isključuje bez izmene i ponovnog postavljanja koda. Nova funkcija može biti dostupna samo testerima, određenim korisnicima ili malom procentu saobraćaja. Ovaj mehanizam olakšava postepeni rollout i brzo povlačenje problematične funkcionalnosti.",
            categoryId = OBSERVABILITY_DEVOPS_INCIDENTS
        ),
        term(
            id = "incident",
            titleSr = "Incident",
            titleEn = "Production incident",
            explanation = "Incident je neočekivani događaj koji narušava normalan rad produkcionog sistema. Može izazvati nedostupnost, pogrešne podatke, usporenje ili bezbednosni problem. Incident se evidentira, procenjuje prema ozbiljnosti i rešava prema definisanom postupku.",
            categoryId = OBSERVABILITY_DEVOPS_INCIDENTS
        ),
        term(
            id = "kriva_ucenja",
            titleSr = "Kriva učenja",
            titleEn = "Learning curve",
            explanation = "Kriva učenja opisuje koliko vremena i napora je potrebno da osoba ovlada novim sistemom ili veštinom. Složena arhitektura, nedostatak dokumentacije i nejasne odgovornosti mogu je učiniti strmijom. Jednostavniji dizajn i kvalitetan onboarding ubrzavaju učenje novih članova tima.",
            categoryId = OBSERVABILITY_DEVOPS_INCIDENTS
        ),
        term(
            id = "logovanje",
            titleSr = "Logovanje",
            titleEn = "Logging",
            explanation = "Logovanje je beleženje informacija o izvršavanju aplikacije i važnim događajima. Logovi mogu sadržati greške, upozorenja, identifikatore zahteva i tehničke detalje potrebne za analizu. Dobri logovi pomažu pri debagovanju, monitoringu i rekonstrukciji incidenta.",
            categoryId = OBSERVABILITY_DEVOPS_INCIDENTS
        ),
        term(
            id = "metrika",
            titleSr = "Metrika",
            titleEn = "Metric",
            explanation = "Metrika je brojčana vrednost koja opisuje određeno ponašanje ili stanje sistema. Primeri su vreme odziva, broj grešaka, zauzeće memorije i broj obrađenih zahteva. Praćenjem metrike kroz vreme mogu se uočiti trendovi, odstupanja i posledice izmena.",
            categoryId = OBSERVABILITY_DEVOPS_INCIDENTS
        ),
        term(
            id = "monitoring",
            titleSr = "Monitoring",
            titleEn = "Nadzor sistema",
            explanation = "Monitoring je kontinuirano praćenje tehničkog i poslovnog stanja sistema. On prikuplja metrike, proverava dostupnost i pokreće upozorenja kada vrednosti pređu definisane pragove. Monitoring pomaže timu da otkrije problem pre nego što ga prijavi veliki broj korisnika.",
            categoryId = OBSERVABILITY_DEVOPS_INCIDENTS
        ),
        term(
            id = "observability",
            titleSr = "Observability",
            titleEn = "Opservabilnost",
            explanation = "Observability je sposobnost da se unutrašnje stanje sistema razume na osnovu njegovih spoljašnjih signala. Najčešće se oslanja na logove, metrike i tracing podatke. Za razliku od unapred definisanog monitoringa, omogućava istraživanje i problema koji nisu bili očekivani.",
            categoryId = OBSERVABILITY_DEVOPS_INCIDENTS
        ),
        term(
            id = "operativni_dashboard",
            titleSr = "Operativni dashboard",
            titleEn = "Operational dashboard",
            explanation = "Operativni dashboard prikazuje trenutno stanje servisa i poslovnih tokova važnih za svakodnevni rad. Može sadržati broj neuspelih zahteva, dužinu redova, latenciju i status spoljnih integracija. Operativni tim ga koristi za brzo uočavanje odstupanja i donošenje odluka tokom incidenta.",
            categoryId = OBSERVABILITY_DEVOPS_INCIDENTS
        ),
        term(
            id = "osnovni_uzrok",
            titleSr = "Osnovni uzrok",
            titleEn = "Root cause",
            explanation = "Osnovni uzrok je najdublji uzrok koji je omogućio da se problem dogodi. On se razlikuje od neposrednog simptoma, kao što je greška koju vidi korisnik. Uklanjanjem osnovnog uzroka smanjuje se verovatnoća ponavljanja istog tipa incidenta.",
            categoryId = OBSERVABILITY_DEVOPS_INCIDENTS
        ),
        term(
            id = "pracenje_rezultata_intervencije",
            titleSr = "Praćenje rezultata intervencije",
            titleEn = "Post-intervention monitoring",
            explanation = "Praćenje rezultata intervencije proverava ponašanje sistema nakon primenjene popravke ili promene. Tim posmatra relevantne metrike, logove i korisničke tokove kako bi potvrdio da je problem uklonjen. Ova provera može otkriti da je intervencija samo privremeno sakrila problem ili izazvala novu posledicu.",
            categoryId = OBSERVABILITY_DEVOPS_INCIDENTS
        ),
        term(
            id = "prioritetna_intervencija",
            titleSr = "Prioritetna intervencija",
            titleEn = "Priority intervention",
            explanation = "Prioritetna intervencija je promena ili akcija koja se sprovodi pre ostalih zbog ozbiljnosti problema. Obično je usmerena na vraćanje dostupnosti, sprečavanje gubitka podataka ili ograničavanje štete. Nakon stabilizacije sistema sledi detaljnija i dugoročnija popravka.",
            categoryId = OBSERVABILITY_DEVOPS_INCIDENTS
        ),
        term(
            id = "razumljivost_arhitekture",
            titleSr = "Razumljivost arhitekture",
            titleEn = "Architecture comprehensibility",
            explanation = "Razumljivost arhitekture opisuje koliko lako članovi tima mogu da shvate strukturu i ponašanje sistema. Na nju utiču jasne granice modula, dosledni obrasci, dokumentacija i jednostavni tokovi. Razumljiva arhitektura olakšava održavanje, onboarding i bezbedno uvođenje izmena.",
            categoryId = OBSERVABILITY_DEVOPS_INCIDENTS
        ),
        term(
            id = "rca",
            titleSr = "RCA",
            titleEn = "Root Cause Analysis",
            explanation = "RCA je strukturisan postupak pronalaženja osnovnog uzroka incidenta ili ponavljajućeg problema. Analiza povezuje simptome, događaje i organizacione ili tehničke faktore koji su omogućili kvar. Rezultat treba da sadrži konkretne korektivne i preventivne mere, a ne samo opis greške.",
            categoryId = OBSERVABILITY_DEVOPS_INCIDENTS
        ),
        term(
            id = "release",
            titleSr = "Release",
            titleEn = "Izdanje",
            explanation = "Release je označena verzija softvera pripremljena za korišćenje ili postavljanje u određeno okruženje. Može sadržati nove funkcionalnosti, ispravke grešaka i tehničke promene. Svako izdanje treba da bude proverljivo, dokumentovano i povezano sa konkretnom verzijom koda.",
            categoryId = OBSERVABILITY_DEVOPS_INCIDENTS
        ),
        term(
            id = "release_ciklus",
            titleSr = "Release ciklus",
            titleEn = "Release cycle",
            explanation = "Release ciklus je ponavljajući proces pripreme, testiranja, odobravanja i objavljivanja softverskog izdanja. Njegova učestalost može biti dnevna, nedeljna, mesečna ili prilagođena potrebama projekta. Dobro organizovan ciklus omogućava redovne izmene bez ugrožavanja stabilnosti sistema.",
            categoryId = OBSERVABILITY_DEVOPS_INCIDENTS
        ),
        term(
            id = "rollback",
            titleSr = "Rollback",
            titleEn = "Vraćanje prethodne verzije",
            explanation = "Rollback je vraćanje sistema na prethodnu stabilnu verziju nakon problematičnog deployment-a. Koristi se kada nova verzija izazove greške koje nije moguće dovoljno brzo ispraviti. Rollback mora uzeti u obzir promene baze, konfiguracije i drugih zavisnih komponenti.",
            categoryId = OBSERVABILITY_DEVOPS_INCIDENTS
        ),
        term(
            id = "rollout",
            titleSr = "Rollout",
            titleEn = "Postepeno puštanje",
            explanation = "Rollout je kontrolisano uvođenje nove verzije ili funkcionalnosti među korisnike. Promena se može prvo uključiti za mali procenat saobraćaja i zatim postepeno proširivati. Tako se problemi mogu otkriti pre nego što utiču na sve korisnike.",
            categoryId = OBSERVABILITY_DEVOPS_INCIDENTS
        ),
        term(
            id = "simptom_sistema",
            titleSr = "Simptom sistema",
            titleEn = "System symptom",
            explanation = "Simptom sistema je vidljiva posledica problema koju korisnik ili monitoring može da primeti. Primeri su spor odgovor, greška pri prijavi ili rast broja neuspešnih poruka. Simptom ukazuje da problem postoji, ali ne mora otkrivati njegov stvarni uzrok.",
            categoryId = OBSERVABILITY_DEVOPS_INCIDENTS
        ),
        term(
            id = "tehnicki_dug",
            titleSr = "Tehnički dug",
            titleEn = "Technical debt",
            explanation = "Tehnički dug predstavlja budući trošak nastao zbog brzih, privremenih ili nekvalitetnih tehničkih odluka. Može se ogledati u dupliranom kodu, zastarelim bibliotekama, nedostatku testova ili previše složenoj arhitekturi. Ako se ne kontroliše, usporava razvoj i povećava rizik od grešaka.",
            categoryId = OBSERVABILITY_DEVOPS_INCIDENTS
        ),
        term(
            id = "tracing",
            titleSr = "Tracing",
            titleEn = "Praćenje toka",
            explanation = "Tracing beleži putanju jednog zahteva ili operacije kroz sistem. Svaki korak može sadržati vreme početka, trajanje, rezultat i povezane tehničke podatke. Ovi podaci pomažu da se utvrdi gde je zahtev usporen ili prekinut.",
            categoryId = OBSERVABILITY_DEVOPS_INCIDENTS
        ),
        term(
            id = "uzrok_problema",
            titleSr = "Uzrok problema",
            titleEn = "Cause",
            explanation = "Uzrok problema je događaj ili uslov koji je doprineo nastanku neželjenog ponašanja. Jedan incident može imati više neposrednih i posrednih uzroka. Analiza treba da razlikuje uzrok od simptoma i da utvrdi koji faktori se mogu ukloniti ili kontrolisati.",
            categoryId = OBSERVABILITY_DEVOPS_INCIDENTS
        )
    )

    private fun programmingOopBasicsTerms(): List<EncyclopediaTerm> = listOf(
        term(
            id = "algoritam",
            titleSr = "Algoritam",
            titleEn = "Algorithm",
            explanation = "Algoritam je precizno definisan niz koraka za rešavanje određenog problema. Prima ulazne podatke, obrađuje ih prema pravilima i proizvodi rezultat. Isti problem može imati više algoritama koji se razlikuju po brzini, složenosti i potrošnji resursa.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "apstraktna_klasa",
            titleSr = "Apstraktna klasa",
            titleEn = "Abstract class",
            explanation = "Apstraktna klasa je klasa koja predstavlja zajedničku osnovu za povezane podklase. Može sadržati implementirane metode, stanje i apstraktne metode koje podklase moraju definisati. Obično se ne može direktno instancirati.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "apstraktna_metoda",
            titleSr = "Apstraktna metoda",
            titleEn = "Abstract method",
            explanation = "Apstraktna metoda definiše naziv, parametre i povratni tip operacije bez konkretne implementacije. Podklase su odgovorne da obezbede njeno stvarno ponašanje. Koristi se kada sve povezane klase moraju podržati istu operaciju, ali je izvršavaju drugačije.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "boolean_flag_smell",
            titleSr = "Boolean flag smell",
            titleEn = "Flag argument smell",
            explanation = "Boolean flag smell je problem dizajna kod kog Boolean parametar menja čitav tok ili odgovornost metode. Poziv poput `generate(true)` ne objašnjava jasno koje se ponašanje aktivira. Često je bolje koristiti dve jasno imenovane metode, posebne objekte ili Strategy obrazac.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "boolean_parametar",
            titleSr = "Boolean parametar",
            titleEn = "Boolean flag",
            explanation = "Boolean parametar je parametar koji može imati vrednost `true` ili `false`. Koristi se za uključivanje ili isključivanje jednostavne opcije u metodi ili konfiguraciji. Kada upravlja velikim brojem različitih ponašanja, može učiniti kod teško razumljivim i proširivim.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "code_smell",
            titleSr = "Code smell",
            titleEn = "Miris koda",
            explanation = "Code smell je osobina koda koja ukazuje na mogući problem u dizajnu ili održavanju. Kod može tehnički raditi ispravno, ali biti dupliran, previše složen ili čvrsto povezan. Miris koda nije automatski greška, već signal da treba razmotriti refaktorisanje.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "dupliranje_logike",
            titleSr = "Dupliranje logike",
            titleEn = "Duplicated logic",
            explanation = "Dupliranje logike nastaje kada se isto ili veoma slično pravilo ponavlja na više mesta u kodu. Promena pravila tada zahteva usklađivanje svih kopija i povećava mogućnost greške. Problem se rešava izdvajanjem zajedničke metode, klase, komponente ili pravila.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "ekstenziona_tacka",
            titleSr = "Ekstenziona tačka",
            titleEn = "Extension point",
            explanation = "Ekstenziona tačka je unapred predviđeno mesto na kome se ponašanje sistema može proširiti ili zameniti. Može biti interfejs, apstraktna metoda, događaj, plugin ili konfiguracioni ugovor. Dobro definisana ekstenziona tačka omogućava dodavanje funkcionalnosti bez menjanja stabilnog jezgra.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "enkapsulacija",
            titleSr = "Enkapsulacija",
            titleEn = "Encapsulation",
            explanation = "Enkapsulacija objedinjuje podatke i operacije nad njima unutar jedne klase ili komponente. Unutrašnji detalji se skrivaju, dok se spolja nudi kontrolisan interfejs. Time se sprečava nevažeća izmena stanja i smanjuje zavisnost drugih delova sistema od implementacije.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "finalna_metoda",
            titleSr = "Finalna metoda",
            titleEn = "Final method",
            explanation = "Finalna metoda je metoda koju podklase ne mogu da prepišu. Koristi se kada redosled ili ponašanje operacije mora ostati nepromenjeno. U Template Method obrascu glavna metoda može biti finalna kako podklase ne bi narušile definisani tok.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "genericka_kolekcija",
            titleSr = "Generička kolekcija",
            titleEn = "Generic collection",
            explanation = "Generička kolekcija je struktura za čuvanje elemenata čiji se tip navodi kao parametar. Primer je `List<User>`, koja treba da sadrži objekte tipa `User`. Generički tip omogućava proveru tipova i smanjuje potrebu za ručnim pretvaranjem objekata.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "genericki_tip",
            titleSr = "Generički tip",
            titleEn = "Generic type",
            explanation = "Generički tip omogućava da ista klasa, interfejs ili metoda radi sa različitim tipovima podataka. Konkretan tip se navodi prilikom korišćenja, umesto da implementacija bude vezana za jednu klasu. Primeri su liste, mape i metode koje obrađuju različite vrste objekata na isti način.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "globalna_promenljiva",
            titleSr = "Globalna promenljiva",
            titleEn = "Global variable",
            explanation = "Globalna promenljiva je podatak dostupan velikom delu ili čitavoj aplikaciji. Olakšava pristup zajedničkom stanju, ali otežava praćenje mesta sa kojih se ono menja. Preterana upotreba globalnih promenljivih povećava spregnutost i otežava testiranje.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "granica_odgovornosti",
            titleSr = "Granica odgovornosti",
            titleEn = "Responsibility boundary",
            explanation = "Granica odgovornosti određuje koji deo sistema je zadužen za određene podatke i ponašanje. Jasna granica sprečava da se ista poslovna logika rasipa kroz više komponenti. Ona olakšava testiranje, održavanje i nezavisno menjanje modula.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "if_else_grananje",
            titleSr = "If-else grananje",
            titleEn = "Conditional branching",
            explanation = "If-else grananje bira deo koda koji će se izvršiti na osnovu određenog uslova. Pogodno je za jednostavne i ograničene odluke. Veliki broj povezanih grana može ukazivati da treba primeniti polimorfizam, State, Strategy ili drugi oblik refaktorisanja.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "implementiranje_interfejsa",
            titleSr = "Implementiranje interfejsa",
            titleEn = "Interface implementation",
            explanation = "Implementiranje interfejsa znači da klasa pruža konkretno ponašanje za operacije definisane interfejsom. Klasa se time obavezuje da poštuje ugovor koji očekuju njeni klijenti. Više različitih klasa može implementirati isti interfejs na različite načine.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "instanca_objekta",
            titleSr = "Instanca objekta",
            titleEn = "Object instance",
            explanation = "Instanca objekta je konkretan objekat napravljen prema definiciji određene klase. Više instanci iste klase može imati različite vrednosti svojih polja. Na primer, dva korisnika mogu biti različite instance klase `User`.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "interfejs",
            titleSr = "Interfejs",
            titleEn = "Interface",
            explanation = "Interfejs definiše skup operacija koje neka klasa ili komponenta treba da podrži. On opisuje šta objekat može da uradi, ali ne mora određivati kako se to izvršava. Programiranje prema interfejsu omogućava lakšu zamenu implementacija i slabiju spregnutost.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "javni_konstruktor",
            titleSr = "Javni konstruktor",
            titleEn = "Public constructor",
            explanation = "Javni konstruktor može biti pozvan iz bilo kog dela programa koji ima pristup klasi. Omogućava slobodno pravljenje novih instanci objekta. Nije pogodan kada klasa mora da kontroliše broj ili način nastanka svojih instanci.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "klasa",
            titleSr = "Klasa",
            titleEn = "Class",
            explanation = "Klasa je programska definicija koja opisuje podatke i ponašanje određene vrste objekata. Ona može sadržati polja, konstruktore i metode. Konkretni objekti nastaju kao instance klase.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "kloniranje_objekta",
            titleSr = "Kloniranje objekta",
            titleEn = "Object cloning",
            explanation = "Kloniranje objekta je pravljenje novog objekta kopiranjem stanja postojećeg objekta. Kopija zatim može biti nezavisno izmenjena, u zavisnosti od načina na koji je kloniranje sprovedeno. Prototype obrazac koristi kloniranje kada je pravljenje objekta od početka složeno ili skupo.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "kombinatorni_rast_klasa",
            titleSr = "Kombinatorni rast klasa",
            titleEn = "Combinatorial class explosion",
            explanation = "Kombinatorni rast klasa nastaje kada svaka kombinacija više nezavisnih osobina zahteva posebnu klasu. Ako postoje tri tipa poruka i četiri kanala, nasledni pristup može zahtevati dvanaest kombinovanih klasa. Obrasci poput Bridge-a i Decorator-a smanjuju ovaj problem razdvajanjem dimenzija promene.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "kompozicija_objekata",
            titleSr = "Kompozicija objekata",
            titleEn = "Object composition",
            explanation = "Kompozicija objekata gradi složenije ponašanje povezivanjem manjih objekata. Jedan objekat čuva reference na druge objekte i koristi njihove funkcionalnosti. Ovaj pristup je često fleksibilniji od nasleđivanja jer se saradnici mogu lakše zameniti.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "konkretna_klasa",
            titleSr = "Konkretna klasa",
            titleEn = "Concrete class",
            explanation = "Konkretna klasa ima dovoljno definisanu implementaciju da se iz nje mogu praviti objekti. Ona obezbeđuje ponašanje za sve obavezne apstraktne operacije. Može implementirati interfejs ili naslediti apstraktnu klasu.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "konstruktor",
            titleSr = "Konstruktor",
            titleEn = "Constructor",
            explanation = "Konstruktor je posebna operacija koja se poziva prilikom pravljenja objekta. Njime se postavljaju početne vrednosti i obavezne zavisnosti objekta. Dobro projektovan konstruktor ne bi trebalo da obavlja složene ili spore poslovne operacije.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "metoda",
            titleSr = "Metoda",
            titleEn = "Method",
            explanation = "Metoda je operacija definisana unutar klase ili objekta. Ona može primati parametre, menjati stanje i vraćati rezultat. Metode predstavljaju ponašanje koje objekat nudi drugim delovima programa.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "modifikator_pristupa",
            titleSr = "Modifikator pristupa",
            titleEn = "Access modifier",
            explanation = "Modifikator pristupa određuje odakle se može pristupiti klasi, polju, konstruktoru ili metodi. Uobičajeni modifikatori su `public`, `private` i `protected`. Njihovom pravilnom upotrebom štite se unutrašnji detalji objekta.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "nasledjivanje",
            titleSr = "Nasleđivanje",
            titleEn = "Inheritance",
            explanation = "Nasleđivanje omogućava da podklasa preuzme osobine i ponašanje nadređene klase. Podklasa zatim može dodati novo ponašanje ili prepisati dozvoljene metode. Preterana upotreba nasleđivanja može stvoriti krute hijerarhije i jaku spregnutost.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "null_vrednost",
            titleSr = "Null vrednost",
            titleEn = "null",
            explanation = "Null označava da promenljiva ili referenca trenutno ne pokazuje ni na jedan objekat. Pristup metodi ili polju preko takve reference može izazvati grešku. Sistem može koristiti provere, opcione tipove ili podrazumevane objekte kako bi bezbednije upravljao odsustvom vrednosti.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "objekat",
            titleSr = "Objekat",
            titleEn = "Object",
            explanation = "Objekat je konkretna programska celina koja ima stanje i ponašanje. Stanje se čuva u poljima, dok se ponašanje izražava metodama. Objekti međusobno sarađuju kako bi sistem izvršio poslovne zadatke.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "opcioni_parametar",
            titleSr = "Opcioni parametar",
            titleEn = "Optional parameter",
            explanation = "Opcioni parametar nije obavezan prilikom pozivanja metode ili konstruktora. Ako nije prosleđen, koristi se podrazumevana vrednost ili se odgovarajuća funkcionalnost preskače. Veliki broj opcionih parametara može otežati poziv i ukazivati na potrebu za Builder obrascem.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "open_closed_principle",
            titleSr = "Open/Closed Principle",
            titleEn = "OCP",
            explanation = "Open/Closed Principle kaže da softverska komponenta treba da bude otvorena za proširivanje, ali zatvorena za menjanje. Novo ponašanje bi trebalo dodavati kroz nove implementacije ili ekstenzione tačke, bez čestog menjanja stabilnog koda. Obrasci Strategy, Decorator i Factory Method često pomažu primenu ovog principa.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "parametar",
            titleSr = "Parametar",
            titleEn = "Parameter",
            explanation = "Parametar je imenovana vrednost koju metoda ili konstruktor očekuje od pozivaoca. Njime se ulazni podaci prosleđuju operaciji. Jasni nazivi i dobro izabrani tipovi parametara čine pozive razumljivijim i bezbednijim.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "podklasa",
            titleSr = "Podklasa",
            titleEn = "Subclass",
            explanation = "Podklasa je klasa koja nasleđuje drugu, nadređenu klasu. Ona dobija dostupna polja i metode roditelja i može proširiti njegovo ponašanje. Podklasa treba da poštuje očekivanja i ugovor nadređenog tipa.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "polimorfizam",
            titleSr = "Polimorfizam",
            titleEn = "Polymorphism",
            explanation = "Polimorfizam omogućava da se različiti objekti koriste preko zajedničkog interfejsa ili nadređenog tipa. Isti poziv metode može izazvati različito ponašanje u zavisnosti od konkretnog objekta. Ovaj mehanizam uklanja potrebu za velikim grananjima po tipu objekta.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "potpis_metode",
            titleSr = "Potpis metode",
            titleEn = "Method signature",
            explanation = "Potpis metode određuje način njenog prepoznavanja u programu. Obično obuhvata naziv metode i tipove ili redosled njenih parametara. Jezik na osnovu potpisa razlikuje preopterećene metode i proverava ispravnost poziva.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "povratni_tip",
            titleSr = "Povratni tip",
            titleEn = "Return type",
            explanation = "Povratni tip određuje kakvu vrednost metoda vraća pozivaocu. Može biti broj, tekst, objekat, kolekcija ili oznaka da metoda ne vraća vrednost. Jasno definisan povratni tip predstavlja deo ugovora metode.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "preoptereceni_konstruktor",
            titleSr = "Preopterećeni konstruktor",
            titleEn = "Overloaded constructor",
            explanation = "Preopterećeni konstruktor je dodatna verzija konstruktora sa drugačijim skupom parametara. Omogućava pravljenje objekta na više načina. Veliki broj ovakvih konstruktora može postati nepregledan i dovesti do problema poznatog kao telescoping constructor.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "prepisivanje_metode",
            titleSr = "Prepisivanje metode",
            titleEn = "Method overriding",
            explanation = "Prepisivanje metode omogućava podklasi da zameni nasleđenu implementaciju sopstvenim ponašanjem. Potpis metode ostaje usklađen sa metodom iz nadređene klase ili interfejsa. Ovaj mehanizam je važan za polimorfizam i Template Method obrazac.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "private_pristup",
            titleSr = "Private pristup",
            titleEn = "private",
            explanation = "Private pristup ograničava korišćenje člana na klasu u kojoj je definisan. Njime se skrivaju unutrašnja polja i pomoćne metode od ostatka sistema. To omogućava klasi da kontroliše sopstveno stanje i zaštiti invarijante.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "privatni_konstruktor",
            titleSr = "Privatni konstruktor",
            titleEn = "Private constructor",
            explanation = "Privatni konstruktor može biti pozvan samo iz same klase ili dozvoljenog unutrašnjeg konteksta. Koristi se kada klasa želi da kontroliše pravljenje instanci. Singleton i fabričke metode često koriste privatni konstruktor.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "proceduralni_pristup",
            titleSr = "Proceduralni pristup",
            titleEn = "Procedural approach",
            explanation = "Proceduralni pristup organizuje program kao niz funkcija, koraka i grananja koja obrađuju podatke. Može biti jednostavan i efikasan za manje ili jasno sekvencijalne probleme. U velikim sistemima centralne procedure često postaju preopterećene različitim odgovornostima.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "programiranje_prema_interfejsu",
            titleSr = "Programiranje prema interfejsu",
            titleEn = "Program to an interface",
            explanation = "Programiranje prema interfejsu znači da komponenta zavisi od ugovora, a ne od jedne konkretne implementacije. Konkretni objekat se može zameniti drugim koji poštuje isti interfejs. Time se povećavaju fleksibilnost, testabilnost i proširivost sistema.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "protected_pristup",
            titleSr = "Protected pristup",
            titleEn = "protected",
            explanation = "Protected pristup omogućava korišćenje člana u samoj klasi i njenim podklasama. Koristi se kada naslednici treba da pristupe određenom delu implementacije koji nije namenjen svim klijentima. Preširoka upotreba može ipak čvrsto povezati podklase sa unutrašnjom strukturom roditelja.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "pseudokod",
            titleSr = "Pseudokod",
            titleEn = "Pseudocode",
            explanation = "Pseudokod je neformalni zapis algoritma koji liči na programski kod, ali nije vezan za preciznu sintaksu jednog jezika. Koristi se za objašnjavanje logike, redosleda i strukture rešenja. Njegova svrha je razumljivost, a ne direktno izvršavanje na računaru.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "public_pristup",
            titleSr = "Public pristup",
            titleEn = "public",
            explanation = "Public pristup čini klasu, metodu ili polje dostupnim drugim delovima programa. Najčešće se koristi za operacije koje predstavljaju javni ugovor komponente. Izlaganje prevelikog broja članova otežava kasnije menjanje unutrašnje implementacije.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "rasuta_poslovna_logika",
            titleSr = "Rasuta poslovna logika",
            titleEn = "Scattered business logic",
            explanation = "Rasuta poslovna logika nastaje kada se jedno pravilo sprovodi na mnogo nepovezanih mesta. Promena pravila tada zahteva pronalaženje i izmenu svih njegovih delova. Izdvajanje logike u odgovarajući domen, servis ili objekat smanjuje rizik od nedoslednosti.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "razdvajanje_odgovornosti",
            titleSr = "Razdvajanje odgovornosti",
            titleEn = "Separation of concerns",
            explanation = "Razdvajanje odgovornosti deli sistem na delove koji se bave različitim problemima. Korisnički interfejs, poslovna pravila, skladištenje i integracije ne bi trebalo nekontrolisano mešati. Jasna podela olakšava razumevanje, testiranje i nezavisno menjanje komponenti.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "redosled_izvrsavanja",
            titleSr = "Redosled izvršavanja",
            titleEn = "Execution order",
            explanation = "Redosled izvršavanja određuje kojim se poretkom naredbe ili koraci algoritma obavljaju. Promena redosleda može promeniti rezultat čak i kada su korišćene iste operacije. Posebno je važan kod validacije, transakcija i procesa sa zavisnim koracima.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "refaktorisanje",
            titleSr = "Refaktorisanje",
            titleEn = "Refactoring",
            explanation = "Refaktorisanje je menjanje unutrašnje strukture koda bez promene njegovog očekivanog spoljašnjeg ponašanja. Cilj je da kod postane jasniji, jednostavniji i pogodniji za dalje proširivanje. Refaktorisanje treba podržati testovima kako bi se potvrdilo da funkcionalnost nije narušena.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "referenca_na_objekat",
            titleSr = "Referenca na objekat",
            titleEn = "Object reference",
            explanation = "Referenca na objekat je vrednost preko koje program pristupa određenoj instanci. Više promenljivih može pokazivati na isti objekat i zato deliti njegove izmene. Referenca može biti prazna ili `null` kada objekat nije dodeljen.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "resolver",
            titleSr = "Resolver",
            titleEn = "Resolver",
            explanation = "Resolver je komponenta koja na osnovu ulaza ili konteksta bira odgovarajuću vrednost, objekat ili implementaciju. Može odabrati Strategy objekat, konfiguraciju, servis ili konkretno pravilo. Njegova upotreba uklanja logiku izbora iz klijentskog koda, ali sam resolver ne bi trebalo da postane velika centralna `if-else` klasa.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "runtime",
            titleSr = "Runtime",
            titleEn = "Run time",
            explanation = "Runtime je period u kome se program stvarno izvršava. Odluke donete u runtime-u zavise od trenutnih podataka, korisničkih akcija ili konfiguracije. Menjanje strategije ili dinamičko dodavanje dekoratera primeri su ponašanja tokom izvršavanja.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "sekvencijalna_logika",
            titleSr = "Sekvencijalna logika",
            titleEn = "Sequence logic",
            explanation = "Sekvencijalna logika opisuje proces u kome se koraci izvršavaju jedan za drugim određenim redosledom. Svaki sledeći korak može zavisiti od rezultata prethodnog. Koristi se za opisivanje poslovnih tokova, algoritama i sekvenci poziva.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "skrivanje_informacija",
            titleSr = "Skrivanje informacija",
            titleEn = "Information hiding",
            explanation = "Skrivanje informacija ograničava pristup detaljima za koje drugi delovi sistema ne treba da znaju. Komponenta izlaže stabilan ugovor, dok način rada može menjati bez uticaja na klijente. Ovaj princip je osnova enkapsulacije i smanjenja spregnutosti.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "slaba_spregnutost",
            titleSr = "Slaba spregnutost",
            titleEn = "Loose coupling",
            explanation = "Slaba spregnutost znači da komponente imaju mali broj jasnih zavisnosti jedna od druge. Promena jedne komponente tada ima ograničen uticaj na ostatak sistema. Interfejsi, događaji i dependency injection često se koriste za postizanje slabije spregnutosti.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "spoljasnje_stanje_objekta",
            titleSr = "Spoljašnje stanje objekta",
            titleEn = "External state",
            explanation = "Spoljašnje stanje je podatak koji se ne čuva unutar deljenog objekta, već mu se prosleđuje pri korišćenju. U Flyweight obrascu to može biti pozicija, brzina ili identitet konkretne instance. Razdvajanje spoljašnjeg stanja omogućava da više objekata koristi isti zajednički unutrašnji deo.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "spregnutost",
            titleSr = "Spregnutost",
            titleEn = "Coupling",
            explanation = "Spregnutost opisuje koliko jedna komponenta zavisi od detalja druge komponente. Visoka spregnutost znači da promena jednog dela često zahteva izmene na drugim mestima. Cilj dobrog dizajna nije uklanjanje svih zavisnosti, već njihovo jasno i kontrolisano oblikovanje.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "staticka_metoda",
            titleSr = "Statička metoda",
            titleEn = "Static method",
            explanation = "Statička metoda pripada klasi, a ne pojedinačnoj instanci objekta. Može se pozvati bez pravljenja objekta te klase. Najčešće se koristi za pomoćne operacije ili kontrolisano kreiranje instanci.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "staticko_polje",
            titleSr = "Statičko polje",
            titleEn = "Static field",
            explanation = "Statičko polje pripada klasi i deli se između svih njenih instanci. Postoji jedna zajednička vrednost bez obzira na broj napravljenih objekata. Singleton obrazac može koristiti statičko polje za čuvanje jedinstvene instance.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "switch_grananje",
            titleSr = "Switch grananje",
            titleEn = "Switch statement",
            explanation = "Switch grananje bira jednu granu izvršavanja prema vrednosti izraza. Često je preglednije od velikog niza jednostavnih `if-else` provera. Kada se stalno proširuje novim poslovnim tipovima, može ukazivati na potrebu za polimorfizmom ili odgovarajućim obrascem.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "ubrizgavanje_kroz_konstruktor",
            titleSr = "Ubrizgavanje kroz konstruktor",
            titleEn = "Constructor Injection",
            explanation = "Ubrizgavanje kroz konstruktor prosleđuje zavisnosti objektu prilikom njegovog kreiranja. Time se obezbeđuje da objekat od početka ima sve obavezne saradnike. Zavisnosti su jasno vidljive, a u testovima se lako mogu zameniti lažnim implementacijama.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "ubrizgavanje_zavisnosti",
            titleSr = "Ubrizgavanje zavisnosti",
            titleEn = "Dependency Injection",
            explanation = "Ubrizgavanje zavisnosti znači da objekat potrebne saradnike dobija spolja umesto da ih sam kreira. Time se odvaja korišćenje servisa od izbora njegove konkretne implementacije. Ovaj pristup smanjuje spregnutost i olakšava testiranje.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "unutrasnje_stanje_objekta",
            titleSr = "Unutrašnje stanje objekta",
            titleEn = "Internal state",
            explanation = "Unutrašnje stanje objekta čine podaci koje objekat čuva u svojim poljima. To stanje može uticati na rezultate metoda i trenutno ponašanje objekta. Enkapsulacija kontroliše način na koji se unutrašnje stanje čita i menja.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "veliki_konstruktor",
            titleSr = "Veliki konstruktor",
            titleEn = "Telescoping constructor",
            explanation = "Veliki konstruktor zahteva mnogo parametara, često sličnih tipova i različite obaveznosti. Takav poziv je teško pročitati i lako je zameniti redosled prosleđenih vrednosti. Builder obrazac može ponuditi jasniju i postepenu izgradnju objekta.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "wrapper",
            titleSr = "Wrapper",
            titleEn = "Omotač",
            explanation = "Wrapper je objekat koji sadrži ili okružuje drugi objekat i kontroliše način njegovog korišćenja. Može prilagoditi interfejs, dodati ponašanje ili kontrolisati pristup. Adapter, Decorator i Proxy koriste različite vrste wrapper objekata za različite ciljeve.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "zavisnost_od_apstrakcije",
            titleSr = "Zavisnost od apstrakcije",
            titleEn = "Depend on abstractions",
            explanation = "Zavisnost od apstrakcije znači da komponenta koristi interfejs ili apstraktni tip umesto konkretne klase. Na taj način se konkretna implementacija može zameniti bez izmene klijenta. Ovaj princip podržava fleksibilnost i lakše testiranje.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "zavisnost_od_konkretne_implementacije",
            titleSr = "Zavisnost od konkretne implementacije",
            titleEn = "Concrete dependency",
            explanation = "Zavisnost od konkretne implementacije nastaje kada komponenta direktno kreira ili koristi tačno određenu klasu. Promena biblioteke ili načina rada tada zahteva izmenu klijentskog koda. Interfejsi, fabrike i dependency injection mogu smanjiti ovu vrstu zavisnosti.",
            categoryId = PROGRAMMING_OOP_BASICS
        ),
        term(
            id = "cvrsta_sprega",
            titleSr = "Čvrsta sprega",
            titleEn = "Tight coupling",
            explanation = "Čvrsta sprega postoji kada komponenta poznaje mnogo unutrašnjih detalja druge komponente. Promena jedne strane zbog toga često razbija ili zahteva menjanje druge strane. Ovaj problem se ublažava jasnim ugovorima, enkapsulacijom i zavisnošću od apstrakcija.",
            categoryId = PROGRAMMING_OOP_BASICS
        )
    )

    private fun performanceScalingResilienceTerms(): List<EncyclopediaTerm> = listOf(
        term(
            id = "admission_control",
            titleSr = "Admission control",
            titleEn = "Kontrola prijema zahteva",
            explanation = "Admission control je mehanizam koji odlučuje da li sistem trenutno može da prihvati novi zahtev. Kada nema dovoljno kapaciteta, zahtev se može odbiti, odložiti ili preusmeriti u red čekanja. Time se sprečava da preveliko opterećenje ugrozi sve korisnike i potpuno zaguši sistem.",
            categoryId = PERFORMANCE_SCALING_RESILIENCE
        ),
        term(
            id = "circuit_breaker",
            titleSr = "Circuit breaker",
            titleEn = "Prekidač strujnog kola u integracijama",
            explanation = "Circuit breaker privremeno prekida pozive ka servisu koji često greši ili ne odgovara. Nakon određenog vremena može dozvoliti ograničen broj probnih zahteva kako bi proverio da li se servis oporavio. Ovaj mehanizam sprečava beskorisno čekanje i širenje problema kroz povezane komponente.",
            categoryId = PERFORMANCE_SCALING_RESILIENCE
        ),
        term(
            id = "cpu_opterecenje",
            titleSr = "CPU opterećenje",
            titleEn = "CPU load",
            explanation = "CPU opterećenje pokazuje koliko je procesor zauzet izvršavanjem poslova. Visoka vrednost tokom dužeg perioda može izazvati sporiji odziv i rast redova zadataka. Praćenje opterećenja pomaže da se utvrdi da li su potrebni optimizacija ili dodatni procesorski resursi.",
            categoryId = PERFORMANCE_SCALING_RESILIENCE
        ),
        term(
            id = "degradirani_rezim",
            titleSr = "Degradirani režim",
            titleEn = "Degraded mode",
            explanation = "Degradirani režim je stanje u kome sistem nastavlja da radi sa ograničenim skupom funkcionalnosti. Aktivira se kada važna komponenta nije dostupna ili nema dovoljno resursa za normalan rad. Korisnik može, na primer, videti poslednje poznate podatke bez mogućnosti njihovog menjanja.",
            categoryId = PERFORMANCE_SCALING_RESILIENCE
        ),
        term(
            id = "disk_i_o",
            titleSr = "Disk I/O",
            titleEn = "Disk input/output",
            explanation = "Disk I/O predstavlja operacije čitanja podataka sa diska i njihovog upisivanja na disk. Veliki broj sporih operacija može postati usko grlo čak i kada procesor nije potpuno opterećen. Performanse zavise od vrste diska, obrasca pristupa i količine podataka.",
            categoryId = PERFORMANCE_SCALING_RESILIENCE
        ),
        term(
            id = "dostupnost",
            titleSr = "Dostupnost",
            titleEn = "Availability",
            explanation = "Dostupnost pokazuje u kom procentu vremena je sistem ili usluga spremna za korišćenje. Na nju utiču kvarovi, održavanje, mrežni problemi i vreme potrebno za oporavak. Visoka dostupnost zahteva uklanjanje kritičnih tačaka otkaza i dobro planirane mehanizme oporavka.",
            categoryId = PERFORMANCE_SCALING_RESILIENCE
        ),
        term(
            id = "fallback",
            titleSr = "Fallback",
            titleEn = "Rezervni tok",
            explanation = "Fallback je alternativni način rada koji se koristi kada glavni servis ili izvor podataka nije dostupan. Može vratiti podrazumevani odgovor, podatke iz keša ili ograničenu funkcionalnost. Njegov cilj je da sistem ostane koristan umesto da svaki problem završi potpunom greškom.",
            categoryId = PERFORMANCE_SCALING_RESILIENCE
        ),
        term(
            id = "flash_sale",
            titleSr = "Flash sale",
            titleEn = "Nagla prodajna kampanja",
            explanation = "Flash sale je kratkotrajna prodajna kampanja koja može izazvati veoma veliki broj istovremenih korisnika. Najveće opterećenje često pogađa katalog, zalihe, checkout i sistem plaćanja. Sistem mora kontrolisati saobraćaj i rezervacije kako bi izbegao pad i prodaju nedostupnih proizvoda.",
            categoryId = PERFORMANCE_SCALING_RESILIENCE
        ),
        term(
            id = "gpu_opterecenje",
            titleSr = "GPU opterećenje",
            titleEn = "GPU load",
            explanation = "GPU opterećenje pokazuje koliko su resursi grafičkog procesora zauzeti obradom zadataka. Posebno je važno kod renderovanja, treniranja modela i AI inference-a. Stalno visoko opterećenje može povećati vreme čekanja i zahtevati više GPU instanci.",
            categoryId = PERFORMANCE_SCALING_RESILIENCE
        ),
        term(
            id = "horizontalno_skaliranje",
            titleSr = "Horizontalno skaliranje",
            titleEn = "Horizontal scaling",
            explanation = "Horizontalno skaliranje povećava kapacitet dodavanjem novih instanci servera, servisa ili worker-a. Zahtevi se zatim raspoređuju između više paralelnih jedinica. Ovaj pristup zahteva da stanje i koordinacija budu organizovani tako da više instanci može bezbedno da radi zajedno.",
            categoryId = PERFORMANCE_SCALING_RESILIENCE
        ),
        term(
            id = "iskoriscenost_resursa",
            titleSr = "Iskorišćenost resursa",
            titleEn = "Resource utilization",
            explanation = "Iskorišćenost resursa pokazuje koliki deo raspoloživog CPU-a, memorije, GPU-a ili drugog kapaciteta sistem koristi. Preniska iskorišćenost može značiti nepotrebne troškove, dok previsoka ostavlja malo prostora za iznenadne skokove. Cilj je održati efikasnu upotrebu uz dovoljno rezervnog kapaciteta.",
            categoryId = PERFORMANCE_SCALING_RESILIENCE
        ),
        term(
            id = "izolacija_resursa",
            titleSr = "Izolacija resursa",
            titleEn = "Resource isolation",
            explanation = "Izolacija resursa razdvaja kapacitete namenjene različitim korisnicima, servisima ili vrstama poslova. Time se sprečava da jedan zahtevan proces potroši sve resurse i ugrozi ostatak sistema. Može se ostvariti posebnim worker pool-ovima, kvotama, kontejnerima ili odvojenim instancama.",
            categoryId = PERFORMANCE_SCALING_RESILIENCE
        ),
        term(
            id = "kontinuitet_poslovanja",
            titleSr = "Kontinuitet poslovanja",
            titleEn = "Business continuity",
            explanation = "Kontinuitet poslovanja predstavlja sposobnost organizacije da nastavi najvažnije aktivnosti tokom ozbiljnog prekida. Plan obuhvata rezervne sisteme, procedure oporavka, odgovornosti i alternativne načine rada. Cilj nije samo tehnički oporavak, već očuvanje ključnih poslovnih procesa.",
            categoryId = PERFORMANCE_SCALING_RESILIENCE
        ),
        term(
            id = "kontrolisana_degradacija",
            titleSr = "Kontrolisana degradacija",
            titleEn = "Graceful degradation",
            explanation = "Kontrolisana degradacija omogućava sistemu da pri problemu postepeno ograniči manje važne funkcionalnosti. Kritični tokovi ostaju dostupni, dok se zahtevne ili pomoćne funkcije privremeno isključuju. Primer je isključivanje preporuka kako bi pretraga i checkout nastavili da rade.",
            categoryId = PERFORMANCE_SCALING_RESILIENCE
        ),
        term(
            id = "kontrolisani_checkout",
            titleSr = "Kontrolisani checkout",
            titleEn = "Controlled checkout",
            explanation = "Kontrolisani checkout ograničava broj korisnika koji istovremeno ulaze u završni proces kupovine. Zahtevi se mogu propuštati postepeno prema dostupnom kapacitetu plaćanja i rezervacije zaliha. Time se smanjuju zagušenje, nepoznati statusi operacija i rizik od duplih rezervacija.",
            categoryId = PERFORMANCE_SCALING_RESILIENCE
        ),
        term(
            id = "latencija",
            titleSr = "Latencija",
            titleEn = "Latency",
            explanation = "Latencija je vreme koje protekne od slanja zahteva do početka ili prijema odgovora. Može nastati zbog mreže, redova, obrade, baze podataka ili spoljnog servisa. Niska latencija je posebno važna za interaktivne aplikacije u kojima korisnik odmah čeka rezultat.",
            categoryId = PERFORMANCE_SCALING_RESILIENCE
        ),
        term(
            id = "memorijski_trosak",
            titleSr = "Memorijski trošak",
            titleEn = "Memory overhead",
            explanation = "Memorijski trošak predstavlja dodatnu memoriju potrebnu za čuvanje objekata, metapodataka i pomoćnih struktura. Veliki broj malih objekata može zajedno potrošiti značajnu količinu memorije. Optimizacija može obuhvatiti deljenje podataka, smanjivanje objekata ili kontrolu trajanja keša.",
            categoryId = PERFORMANCE_SCALING_RESILIENCE
        ),
        term(
            id = "oporavak_nakon_prekida",
            titleSr = "Oporavak nakon prekida",
            titleEn = "Recovery after failure",
            explanation = "Oporavak nakon prekida je vraćanje sistema u ispravno stanje posle kvara ili nedostupnosti. Može uključivati ponovno pokretanje servisa, vraćanje podataka i obradu zaostalih poruka. Sistem mora proveriti da li su tokom prekida nastali duplikati, gubici ili nekonzistentna stanja.",
            categoryId = PERFORMANCE_SCALING_RESILIENCE
        ),
        term(
            id = "otpornost_na_greske",
            titleSr = "Otpornost na greške",
            titleEn = "Fault tolerance",
            explanation = "Otpornost na greške je sposobnost sistema da nastavi rad i kada neke komponente otkažu. Ostvaruje se redundansom, izolacijom, retry mehanizmima, fallback-om i automatskim preusmeravanjem. Cilj je da pojedinačna greška ne izazove potpuni prekid usluge.",
            categoryId = PERFORMANCE_SCALING_RESILIENCE
        ),
        term(
            id = "otpornost_na_prekid_mreze",
            titleSr = "Otpornost na prekid mreže",
            titleEn = "Network partition tolerance",
            explanation = "Otpornost na prekid mreže znači da distribuirani sistem može da reaguje kada njegove komponente privremeno ne mogu međusobno da komuniciraju. Različiti delovi sistema tada mogu imati nepotpuno ili neusklađeno stanje. Arhitektura mora odrediti koje operacije ostaju dozvoljene i kako će se podaci kasnije uskladiti.",
            categoryId = PERFORMANCE_SCALING_RESILIENCE
        ),
        term(
            id = "performanse",
            titleSr = "Performanse",
            titleEn = "Performance",
            explanation = "Performanse opisuju koliko brzo i efikasno sistem izvršava zadatke. Najčešće se mere latencijom, vremenom odziva, propusnošću i potrošnjom resursa. Dobre performanse zavise od koda, infrastrukture, baze, mreže i arhitektonskih odluka.",
            categoryId = PERFORMANCE_SCALING_RESILIENCE
        ),
        term(
            id = "pouzdanost",
            titleSr = "Pouzdanost",
            titleEn = "Reliability",
            explanation = "Pouzdanost je sposobnost sistema da tokom vremena ispravno i dosledno izvršava očekivane funkcije. Pouzdan sistem ne proizvodi često greške, gubitke podataka ili pogrešne rezultate. Ona se povećava testiranjem, kontrolom grešaka, monitoringom i pažljivim upravljanjem promenama.",
            categoryId = PERFORMANCE_SCALING_RESILIENCE
        ),
        term(
            id = "predvidivo_vreme_izvrsavanja",
            titleSr = "Predvidivo vreme izvršavanja",
            titleEn = "Predictable execution time",
            explanation = "Predvidivo vreme izvršavanja znači da zadatak obično završava u poznatom i prihvatljivom vremenskom opsegu. Nije dovoljno da prosečno izvršavanje bude brzo ako pojedini zahtevi ponekad traju veoma dugo. Predvidivost je važna za SLA, korisničko iskustvo i planiranje kapaciteta.",
            categoryId = PERFORMANCE_SCALING_RESILIENCE
        ),
        term(
            id = "profilisanje_performansi",
            titleSr = "Profilisanje performansi",
            titleEn = "Performance profiling",
            explanation = "Profilisanje performansi meri gde program troši procesorsko vreme, memoriju i druge resurse. Alati za profilisanje pomažu da se pronađu spore metode, česti pozivi i nepotrebne alokacije. Optimizacija treba da se zasniva na izmerenim uskim grlima, a ne samo na pretpostavkama.",
            categoryId = PERFORMANCE_SCALING_RESILIENCE
        ),
        term(
            id = "propusnost",
            titleSr = "Propusnost",
            titleEn = "Throughput",
            explanation = "Propusnost predstavlja količinu posla koju sistem može da obradi u određenom vremenu. Može se meriti brojem zahteva, poruka, transakcija ili obrađenih zapisa u sekundi. Visoka propusnost ne znači nužno i nizak odziv za pojedinačnog korisnika.",
            categoryId = PERFORMANCE_SCALING_RESILIENCE
        ),
        term(
            id = "rate_limiting",
            titleSr = "Rate limiting",
            titleEn = "Ograničavanje učestalosti zahteva",
            explanation = "Rate limiting ograničava broj zahteva koje korisnik, uređaj ili servis može poslati u određenom periodu. Štiti sistem od zloupotrebe, greškom izazvanih petlji i nekontrolisanog opterećenja. Kada se granica prekorači, zahtev se odbija ili odlaže do narednog dozvoljenog intervala.",
            categoryId = PERFORMANCE_SCALING_RESILIENCE
        ),
        term(
            id = "regionalno_skaliranje",
            titleSr = "Regionalno skaliranje",
            titleEn = "Regional scaling",
            explanation = "Regionalno skaliranje raspoređuje aplikaciju i njene resurse u više geografskih regiona. Korisnici se usmeravaju ka bližem regionu radi manje latencije i veće dostupnosti. Ovaj pristup otežava sinhronizaciju podataka, upravljanje konzistentnošću i prebacivanje saobraćaja tokom kvara.",
            categoryId = PERFORMANCE_SCALING_RESILIENCE
        ),
        term(
            id = "single_point_of_failure",
            titleSr = "Single point of failure",
            titleEn = "Jedinstvena tačka otkaza",
            explanation = "Single point of failure je komponenta čiji kvar može zaustaviti ceo sistem ili ključni poslovni tok. To može biti jedan server, baza, gateway ili mrežna veza bez zamene. Problem se uklanja redundansom, replikacijom i automatskim preusmeravanjem.",
            categoryId = PERFORMANCE_SCALING_RESILIENCE
        ),
        term(
            id = "skalabilnost",
            titleSr = "Skalabilnost",
            titleEn = "Scalability",
            explanation = "Skalabilnost je sposobnost sistema da podnese rast korisnika, podataka ili zahteva dodavanjem resursa. Skalabilan sistem zadržava prihvatljive performanse i stabilnost pri većem opterećenju. Ona može biti horizontalna, vertikalna, regionalna ili ograničena na pojedine komponente.",
            categoryId = PERFORMANCE_SCALING_RESILIENCE
        ),
        term(
            id = "skok_opterecenja",
            titleSr = "Skok opterećenja",
            titleEn = "Load spike",
            explanation = "Skok opterećenja je naglo i kratkotrajno povećanje broja zahteva ili količine obrade. Može nastati zbog kampanje, važnog događaja, automatskog procesa ili napada. Sistem ga podnosi rezervnim kapacitetom, skaliranjem, redovima i kontrolom prijema.",
            categoryId = PERFORMANCE_SCALING_RESILIENCE
        ),
        term(
            id = "skup_mrezni_poziv",
            titleSr = "Skup mrežni poziv",
            titleEn = "Expensive network call",
            explanation = "Skup mrežni poziv zahteva mnogo vremena, propusnog opsega, novca ili drugih resursa. Često se odnosi na udaljeni servis sa velikom latencijom ili ograničenom cenom korišćenja. Broj poziva se može smanjiti keširanjem, grupisanjem zahteva ili odloženim učitavanjem.",
            categoryId = PERFORMANCE_SCALING_RESILIENCE
        ),
        term(
            id = "skup_upit",
            titleSr = "Skup upit",
            titleEn = "Expensive query",
            explanation = "Skup upit troši mnogo vremena, procesora, memorije ili diskovnih operacija u bazi podataka. Uzrok mogu biti veliki skupovi podataka, nedostatak indeksa, složena spajanja ili neefikasni filteri. Takav upit može usporiti i druge korisnike koji koriste istu bazu.",
            categoryId = PERFORMANCE_SCALING_RESILIENCE
        ),
        term(
            id = "takmicenje_za_resurse",
            titleSr = "Takmičenje za resurse",
            titleEn = "Resource contention",
            explanation = "Takmičenje za resurse nastaje kada više poslova istovremeno pokušava da koristi isti ograničeni kapacitet. Oni se mogu takmičiti za CPU, disk, memoriju, konekcije baze ili zaključavanje podataka. Posledice su čekanje, veća latencija i nepredvidivo vreme izvršavanja.",
            categoryId = PERFORMANCE_SCALING_RESILIENCE
        ),
        term(
            id = "timeout",
            titleSr = "Timeout",
            titleEn = "Vremensko ograničenje",
            explanation = "Timeout je maksimalno vreme tokom kog sistem čeka završetak zahteva ili operacije. Kada rok istekne, čekanje se prekida i vraća se greška ili aktivira rezervni tok. Timeout ne dokazuje da operacija nije izvršena, pa rezultat ponekad može ostati nepoznat.",
            categoryId = PERFORMANCE_SCALING_RESILIENCE
        ),
        term(
            id = "usko_grlo",
            titleSr = "Usko grlo",
            titleEn = "Bottleneck",
            explanation = "Usko grlo je deo sistema čiji ograničeni kapacitet određuje performanse celog toka. Dodavanje resursa drugim komponentama ne pomaže dok se ne poboljša upravo taj deo. Usko grlo se pronalazi merenjem latencije, redova, propusnosti i iskorišćenosti resursa.",
            categoryId = PERFORMANCE_SCALING_RESILIENCE
        ),
        term(
            id = "vertikalno_skaliranje",
            titleSr = "Vertikalno skaliranje",
            titleEn = "Vertical scaling",
            explanation = "Vertikalno skaliranje povećava kapacitet jedne instance dodavanjem procesora, memorije ili bržeg diska. Jednostavnije je od raspoređivanja rada na mnogo instanci jer aplikacija često ne zahteva velike promene. Ograničeno je maksimalnim kapacitetom jedne mašine i može zadržati jedinstvenu tačku otkaza.",
            categoryId = PERFORMANCE_SCALING_RESILIENCE
        ),
        term(
            id = "visoka_dostupnost",
            titleSr = "Visoka dostupnost",
            titleEn = "High availability",
            explanation = "Visoka dostupnost je projektovanje sistema tako da ostane dostupan i tokom kvarova ili održavanja. Obično koristi više instanci, replikaciju i automatsko prebacivanje na zdrave komponente. Cilj se izražava veoma visokim procentom vremena u kome usluga radi.",
            categoryId = PERFORMANCE_SCALING_RESILIENCE
        ),
        term(
            id = "vreme_odziva",
            titleSr = "Vreme odziva",
            titleEn = "Response time",
            explanation = "Vreme odziva je ukupno vreme od slanja zahteva do prijema kompletnog odgovora. Uključuje mrežno čekanje, vreme u redu i stvarnu obradu na serveru. Korisničko iskustvo najčešće zavisi od viših i najsporijih vrednosti, a ne samo od proseka.",
            categoryId = PERFORMANCE_SCALING_RESILIENCE
        ),
        term(
            id = "vrsno_opterecenje",
            titleSr = "Vršno opterećenje",
            titleEn = "Peak load",
            explanation = "Vršno opterećenje je najveći očekivani ili izmereni nivo korišćenja sistema. Planiranje kapaciteta mora uzeti u obzir vršne periode, a ne samo prosečan saobraćaj. Primeri su praznične prodaje, prijave za ispit i objavljivanje važnih rezultata.",
            categoryId = PERFORMANCE_SCALING_RESILIENCE
        ),
        term(
            id = "waiting_room",
            titleSr = "Waiting room",
            titleEn = "Virtuelna čekaonica",
            explanation = "Waiting room privremeno zadržava korisnike pre ulaska u preopterećen deo sistema. Korisnici se propuštaju kontrolisanom brzinom prema dostupnom kapacitetu. Ovaj pristup se često koristi za prodaju karata, flash sale kampanje i druge nagle skokove potražnje.",
            categoryId = PERFORMANCE_SCALING_RESILIENCE
        ),
        term(
            id = "zagusenje_sistema",
            titleSr = "Zagušenje sistema",
            titleEn = "System congestion",
            explanation = "Zagušenje sistema nastaje kada zahtevi pristižu brže nego što mogu biti obrađeni. Redovi rastu, latencija se povećava i mogu početi timeout-i i ponovljeni pokušaji. Admission control, backpressure i skaliranje pomažu da se zagušenje ograniči pre potpunog pada.",
            categoryId = PERFORMANCE_SCALING_RESILIENCE
        )
    )

    private fun dataStorageSearchTerms(): List<EncyclopediaTerm> = listOf(
        term(
            id = "analytics_store",
            titleSr = "Analytics store",
            titleEn = "Analitičko skladište",
            explanation = "Analytics store je skladište podataka prilagođeno analizi, agregacijama i izveštavanju. U njega se često prenose podaci iz operativnih sistema kako složeni analitički upiti ne bi opterećivali glavne baze. Može sadržati istorijske i unapred obrađene podatke namenjene analitičarima i BI alatima.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "attachment_storage",
            titleSr = "Attachment storage",
            titleEn = "Skladište priloga",
            explanation = "Attachment storage je mesto namenjeno čuvanju fajlova koji su povezani sa porukama, dokumentima ili poslovnim zapisima. U njemu se mogu nalaziti slike, PDF dokumenti, tabele i drugi prilozi. Baza obično čuva samo metapodatke i referencu na stvarni fajl.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "availability_snapshot",
            titleSr = "Availability snapshot",
            titleEn = "Snimak raspoloživosti",
            explanation = "Availability snapshot je zapis raspoloživih resursa u određenom trenutku. Može prikazivati slobodna sedišta, proizvode, termine ili kapacitete prema poslednjem izračunatom stanju. Pošto je snimak vremenski ograničen, ne mora garantovati da je resurs i dalje dostupan.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "baza_podataka",
            titleSr = "Baza podataka",
            titleEn = "Database",
            explanation = "Baza podataka je organizovano skladište podataka kojim se upravlja pomoću specijalizovanog sistema. Omogućava čuvanje, pronalaženje, menjanje i povezivanje velikog broja zapisa. Izbor vrste baze zavisi od strukture podataka, načina korišćenja i zahteva konzistentnosti.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "cache",
            titleSr = "Cache",
            titleEn = "Keš",
            explanation = "Cache je brzo privremeno skladište u kome se čuvaju često korišćeni ili skupo izračunati podaci. Sistem prvo proverava keš, a zatim sporiji izvor ako traženi podatak nije pronađen. Keš smanjuje latenciju i opterećenje, ali zahteva pravilno osvežavanje zastarelih vrednosti.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "cache_hit",
            titleSr = "Cache hit",
            titleEn = "Pogodak keša",
            explanation = "Cache hit nastaje kada se traženi podatak pronađe u kešu. Sistem tada ne mora da poziva bazu, spoljni servis ili skupu operaciju. Visok procenat pogodaka obično poboljšava brzinu i smanjuje opterećenje izvornog sistema.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "cache_invalidacija",
            titleSr = "Cache invalidacija",
            titleEn = "Cache invalidation",
            explanation = "Cache invalidacija je uklanjanje ili označavanje keširane vrednosti kao nevažeće. Potrebna je kada se izvorni podatak promeni i stara verzija više ne sme da se koristi. Pogrešna invalidacija može izazvati zastarele podatke ili nepotrebno često ponovno učitavanje.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "cache_kljuc",
            titleSr = "Cache ključ",
            titleEn = "Cache key",
            explanation = "Cache ključ je jedinstvena oznaka pomoću koje se podatak upisuje i pronalazi u kešu. Može sadržati identifikator korisnika, proizvoda, jezika, verzije ili drugih parametara zahteva. Loše definisan ključ može vratiti podatak pogrešnom korisniku ili spojiti rezultate različitih upita.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "cache_miss",
            titleSr = "Cache miss",
            titleEn = "Promašaj keša",
            explanation = "Cache miss nastaje kada traženi podatak nije pronađen u kešu. Sistem tada mora da ga dobije iz baze, spoljnog servisa ili da ga ponovo izračuna. Dobijeni rezultat se često zatim smešta u keš za buduće zahteve.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "cache_refresh",
            titleSr = "Cache refresh",
            titleEn = "Osvežavanje keša",
            explanation = "Cache refresh je postupak ponovnog učitavanja ili izračunavanja keširane vrednosti. Može se pokrenuti istekom TTL-a, promenom izvornog podatka ili periodičnim rasporedom. Cilj je da keš ostane dovoljno svež bez prevelikog opterećenja izvora.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "checksum",
            titleSr = "Checksum",
            titleEn = "Kontrolni zbir",
            explanation = "Checksum je kratka vrednost izračunata iz sadržaja fajla ili skupa podataka. Poređenjem kontrolnih zbirova može se proveriti da li je sadržaj promenjen ili oštećen. On pomaže pri prenosu, migraciji i otkrivanju tihe korupcije podataka.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "csv",
            titleSr = "CSV",
            titleEn = "Comma-Separated Values",
            explanation = "CSV je tekstualni format za predstavljanje tabelarnih podataka. Svaki red obično predstavlja jedan zapis, dok su vrednosti razdvojene zarezom ili drugim izabranim separatorom. Jednostavan je za razmenu podataka, ali ne čuva složene tipove i veze kao baza.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "document_storage",
            titleSr = "Document storage",
            titleEn = "Skladište dokumenata",
            explanation = "Document storage je sistem namenjen čuvanju poslovnih ili korisničkih dokumenata. Pored sadržaja može upravljati verzijama, metapodacima, pravima pristupa i periodom zadržavanja. Dokumenti mogu biti smešteni u object storage-u, fajl sistemu ili specijalizovanom servisu.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "doi",
            titleSr = "DOI",
            titleEn = "Digital Object Identifier",
            explanation = "DOI je trajni digitalni identifikator koji se najčešće dodeljuje naučnim radovima i drugim publikacijama. On ostaje isti čak i kada se promeni internet adresa sadržaja. DOI omogućava pouzdano citiranje i dugoročno pronalaženje publikacije.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "dokument_baza",
            titleSr = "Dokument baza",
            titleEn = "Document database",
            explanation = "Dokument baza čuva podatke u obliku fleksibilnih dokumenata, često sličnih JSON strukturi. Jedan dokument može sadržati ugnježdene objekte i liste bez razdvajanja u veliki broj tabela. Pogodna je kada se struktura zapisa menja ili se podaci često čitaju kao celina.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "edge_cache",
            titleSr = "Edge cache",
            titleEn = "Keš na ivici mreže",
            explanation = "Edge cache čuva kopije sadržaja na serverima koji su geografski bliži korisnicima. Time se smanjuje vreme prenosa i opterećenje glavnog servera. Često se koristi u okviru CDN mreže za slike, video-snimke i statičke fajlove.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "geografski_raster",
            titleSr = "Geografski raster",
            titleEn = "Geospatial raster",
            explanation = "Geografski raster predstavlja prostor kao mrežu ćelija ili piksela. Svaka ćelija sadrži vrednost kao što su visina, temperatura, vegetacija ili boja satelitskog snimka. Koristi se za mape, meteorologiju, daljinsko osmatranje i prostorne analize.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "gis_podatak",
            titleSr = "GIS podatak",
            titleEn = "Geospatial data",
            explanation = "GIS podatak opisuje objekte, pojave ili vrednosti povezane sa geografskom lokacijom. Može biti predstavljen tačkama, linijama, poligonima ili rasterima. Koristi se za navigaciju, planiranje prostora, logistiku i analizu okruženja.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "izvedeni_fajl",
            titleSr = "Izvedeni fajl",
            titleEn = "Derived file",
            explanation = "Izvedeni fajl nastaje obradom originalnog fajla. Primeri su umanjena slika, kompresovani video, OCR tekst ili PDF generisan iz izvornog dokumenta. Može se ponovo napraviti ako su sačuvani original i pravila obrade.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "json",
            titleSr = "JSON",
            titleEn = "JavaScript Object Notation",
            explanation = "JSON je tekstualni format za predstavljanje strukturisanih podataka pomoću objekata, nizova i osnovnih vrednosti. Često se koristi za razmenu podataka između web aplikacija i API servisa. Lako ga čitaju i ljudi i programi, ali sam format ne definiše poslovna pravila podataka.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "lazy_fetching",
            titleSr = "Lazy fetching",
            titleEn = "Lenjo dohvatanje",
            explanation = "Lazy fetching znači da se podatak preuzima tek kada je zaista potreban. Sistem ne učitava unapred sve povezane ili udaljene podatke. Time se početni zahtev ubrzava, ali kasniji pristup može izazvati dodatni poziv.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "lazy_loading",
            titleSr = "Lazy loading",
            titleEn = "Odloženo učitavanje",
            explanation = "Lazy loading je pristup u kome se sadržaj ili objekat učitava tek kada korisnik ili program dođe do njega. Može se koristiti za slike, module, podatke ili skupe resurse. Smanjuje početno vreme učitavanja i potrošnju resursa.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "lokalna_baza",
            titleSr = "Lokalna baza",
            titleEn = "Local database",
            explanation = "Lokalna baza je baza koja se nalazi na korisničkom uređaju ili u lokalnoj instalaciji aplikacije. Omogućava brz pristup i rad kada mreža nije dostupna. Podaci se kasnije mogu sinhronizovati sa centralnim sistemom.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "lokalno_skladiste_fajlova",
            titleSr = "Lokalno skladište fajlova",
            titleEn = "Local file storage",
            explanation = "Lokalno skladište fajlova čuva dokumente direktno na disku uređaja ili servera. Jednostavno je za korišćenje, ali može otežati skaliranje na više instanci aplikacije. Potrebni su rezervne kopije, kontrola pristupa i plan za kvar uređaja.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "map_tile",
            titleSr = "Map tile",
            titleEn = "Pločica mape",
            explanation = "Map tile je mali pravougaoni deo velike digitalne mape. Klijent učitava samo pločice koje su potrebne za trenutno vidljivo područje. Time se izbegava prenos cele mape pri svakom prikazu.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "metadata_store",
            titleSr = "Metadata store",
            titleEn = "Skladište metapodataka",
            explanation = "Metadata store čuva opisne informacije o drugim podacima ili fajlovima. Može sadržati naziv, vlasnika, veličinu, tip, verziju, lokaciju i prava pristupa. Stvarni sadržaj se često nalazi u drugom skladištu, dok metadata store omogućava njegovo pronalaženje i upravljanje.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "metapodatak",
            titleSr = "Metapodatak",
            titleEn = "Metadata",
            explanation = "Metapodatak je podatak koji opisuje drugi podatak ili resurs. Primeri su autor dokumenta, datum nastanka, format fajla i geografska lokacija slike. Omogućava organizovanje, filtriranje, pretragu i upravljanje sadržajem.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "nosql_baza",
            titleSr = "NoSQL baza",
            titleEn = "NoSQL database",
            explanation = "NoSQL baza je baza koja ne koristi isključivo klasični relacioni model tabela i veza. Može biti dokumentna, key-value, grafovska ili time-series baza. Pogodna je za određene obrasce skaliranja, fleksibilne strukture i velike količine podataka.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "object_storage",
            titleSr = "Object storage",
            titleEn = "Skladište objekata",
            explanation = "Object storage čuva fajlove kao objekte sa jedinstvenim ključem i metapodacima. Pogodan je za slike, video, rezervne kopije i druge velike binarne sadržaje. Obično se koristi preko API-ja i lako se skalira na veoma velike količine podataka.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "originalni_fajl",
            titleSr = "Originalni fajl",
            titleEn = "Original file",
            explanation = "Originalni fajl je izvorna verzija sadržaja pre dodatne obrade ili konverzije. Iz njega se mogu praviti izvedene verzije različitih veličina i formata. Treba ga sačuvati kada je potrebna ponovna obrada ili dokazivanje autentičnog sadržaja.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "paginacija",
            titleSr = "Paginacija",
            titleEn = "Pagination",
            explanation = "Paginacija deli veliki skup rezultata na manje stranice. Klijent dobija ograničen broj zapisa i zatim zahteva sledeću grupu. Time se smanjuju vreme odgovora, potrošnja memorije i količina prenetih podataka.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "pem",
            titleSr = "PEM",
            titleEn = "Privacy-Enhanced Mail format",
            explanation = "PEM je tekstualni format za čuvanje i razmenu kriptografskih ključeva i sertifikata. Sadržaj je Base64 kodiran i okružen oznakama koje opisuju njegov tip. Često se koristi za privatne ključeve, javne ključeve i X.509 sertifikate.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "persistencija",
            titleSr = "Persistencija",
            titleEn = "Persistence",
            explanation = "Persistencija je sposobnost sistema da sačuva podatke i nakon završetka procesa ili gašenja aplikacije. Može se ostvariti bazom, fajlom, object storage-om ili drugim trajnim skladištem. Bez persistencije stanje postoji samo privremeno u memoriji.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "persistirano_stanje",
            titleSr = "Persistirano stanje",
            titleEn = "Persisted state",
            explanation = "Persistirano stanje je stanje objekta ili procesa koje je trajno sačuvano u skladištu. Ono može biti ponovo učitano nakon ponovnog pokretanja aplikacije. Mora biti usklađeno sa poslovnim pravilima i verzijom modela podataka.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "pipeline_indeksiranja",
            titleSr = "Pipeline indeksiranja",
            titleEn = "Indexing pipeline",
            explanation = "Pipeline indeksiranja je niz koraka kojima se izvorni podaci pripremaju i upisuju u search index. Može uključivati čišćenje, transformaciju, tokenizaciju, obogaćivanje i slanje zapisa. Njegov kvalitet direktno utiče na potpunost i relevantnost rezultata pretrage.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "projekcija",
            titleSr = "Projekcija",
            titleEn = "Projection",
            explanation = "Projekcija je izvedeni prikaz podataka prilagođen određenom načinu čitanja. Može se izgraditi iz događaja, operativnih tabela ili više povezanih izvora. Često sadrži unapred spojene i izračunate vrednosti radi bržeg prikaza.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "rangiranje_rezultata",
            titleSr = "Rangiranje rezultata",
            titleEn = "Search ranking",
            explanation = "Rangiranje rezultata određuje redosled pronađenih stavki prema njihovoj procenjenoj relevantnosti. Može koristiti podudaranje teksta, popularnost, svežinu i korisnički kontekst. Dobar ranking postavlja najkorisnije rezultate bliže vrhu liste.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "read_model",
            titleSr = "Read model",
            titleEn = "Model za čitanje",
            explanation = "Read model je struktura podataka oblikovana za efikasno čitanje i prikazivanje. Ne mora imati isti oblik kao model koji se koristi za poslovne upise. U CQRS sistemu read model može biti posebna projekcija ažurirana događajima.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "read_replica",
            titleSr = "Read replica",
            titleEn = "Replika za čitanje",
            explanation = "Read replica je kopija baze namenjena prvenstveno upitima za čitanje. Ona smanjuje opterećenje glavne baze na kojoj se izvršavaju upisi. Zbog kašnjenja replikacije može kratkotrajno prikazivati zastarele podatke.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "registar_verzija",
            titleSr = "Registar verzija",
            titleEn = "Version registry",
            explanation = "Registar verzija je evidencija dostupnih verzija fajlova, modela, šema ili drugih artefakata. Čuva identifikatore, vreme nastanka, status i veze sa prethodnim verzijama. Omogućava izbor aktivne verzije, poređenje i vraćanje na ranije stanje.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "relaciona_baza",
            titleSr = "Relaciona baza",
            titleEn = "Relational database",
            explanation = "Relaciona baza organizuje podatke u tabele sastavljene od redova i kolona. Veze između tabela definišu se ključevima i ograničenjima. Pogodna je za strukturisane podatke, transakcije i složene upite pomoću SQL jezika.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "relevantnost_rezultata",
            titleSr = "Relevantnost rezultata",
            titleEn = "Search relevance",
            explanation = "Relevantnost rezultata opisuje koliko pronađena stavka odgovara korisničkom upitu i nameri. Ne zavisi samo od prisustva iste reči, već i od značenja, konteksta i važnosti dokumenta. Meri se evaluacionim skupovima, korisničkim ponašanjem i ručnom procenom.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "replication_lag",
            titleSr = "Replication lag",
            titleEn = "Kašnjenje replikacije",
            explanation = "Replication lag je vremenska razlika između upisa na glavnoj bazi i pojavljivanja iste promene na replici. Tokom tog perioda replika vraća stariju verziju podatka. Veliko kašnjenje može izazvati pogrešne izveštaje i zbunjujuće korisničko iskustvo.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "replikacija_baze",
            titleSr = "Replikacija baze",
            titleEn = "Database replication",
            explanation = "Replikacija baze je kopiranje podataka sa jedne baze na jednu ili više drugih instanci. Koristi se za dostupnost, oporavak i raspodelu upita za čitanje. Replikacija može biti sinhrona ili asinhrona, što utiče na konzistentnost i latenciju.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "reporting_store",
            titleSr = "Reporting store",
            titleEn = "Skladište za izveštavanje",
            explanation = "Reporting store je skladište podataka pripremljeno za izradu poslovnih izveštaja. Sadrži strukture i agregate koji olakšavaju složene upite nad dužim periodima. Odvajanje od operativne baze sprečava da izveštaji uspore svakodnevne transakcije.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "search_index",
            titleSr = "Search index",
            titleEn = "Indeks za pretragu",
            explanation = "Search index je posebna struktura podataka optimizovana za brzo pronalaženje sadržaja. U njega se upisuju termini, polja i druge informacije potrebne za filtriranje i rangiranje. Indeks je izveden iz izvornog sistema i mora se osvežavati kada se podaci promene.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "shared_storage",
            titleSr = "Shared storage",
            titleEn = "Deljeno skladište",
            explanation = "Shared storage je skladište kome može pristupiti više servera ili aplikacionih instanci. Omogućava da svi koriste iste fajlove i podatke bez lokalnog kopiranja. Mora imati pouzdanu kontrolu konkurentnog pristupa, dostupnosti i prava korišćenja.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "snapshot",
            titleSr = "Snapshot",
            titleEn = "Snimak stanja",
            explanation = "Snapshot je zabeleženo stanje sistema, objekta ili skupa podataka u određenom trenutku. Koristi se za oporavak, poređenje, audit ili ubrzavanje rekonstrukcije. Za razliku od kompletne istorije, prikazuje samo stanje u izabranoj tački vremena.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "specijalizovani_filter",
            titleSr = "Specijalizovani filter",
            titleEn = "Specialized filter",
            explanation = "Specijalizovani filter je filter prilagođen određenom tipu podataka ili poslovnom pravilu. Može koristiti geografsku udaljenost, opseg cena, status ili druge domenske uslove. Njegova implementacija često zahteva posebno indeksiranje i optimizaciju.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "tile_pyramid",
            titleSr = "Tile pyramid",
            titleEn = "Piramida nivoa zumiranja",
            explanation = "Tile pyramid je skup map tile-ova pripremljenih za različite nivoe zumiranja. Svaki viši nivo sadrži više pločica i prikazuje sitnije detalje. Klijent bira odgovarajući nivo prema trenutnom prikazu i veličini ekrana.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "time_series_baza",
            titleSr = "Time-series baza",
            titleEn = "Time-series database",
            explanation = "Time-series baza je optimizovana za podatke koji imaju vremensku oznaku i neprekidno pristižu. Često se koristi za metrike, senzore, finansijske vrednosti i telemetriju. Podržava brzo upisivanje i agregacije po vremenskim intervalima.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "transakcija_baze",
            titleSr = "Transakcija baze",
            titleEn = "Database transaction",
            explanation = "Transakcija baze grupiše više operacija koje treba da se izvrše kao jedna logička celina. Ako neki korak ne uspe, prethodne promene se mogu poništiti. Koristi se za očuvanje atomarnosti i integriteta poslovnih podataka.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "ttl",
            titleSr = "TTL",
            titleEn = "Time to Live",
            explanation = "TTL određuje koliko dugo podatak, zapis ili keširana vrednost ostaje važeća. Nakon isteka može biti automatski obrisana, osvežena ili zanemarena. Koristi se u kešu, DNS-u, privremenim rezervacijama i drugim vremenski ograničenim podacima.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "verzionisanje",
            titleSr = "Verzionisanje",
            titleEn = "Versioning",
            explanation = "Verzionisanje je vođenje različitih izdanja istog podatka, dokumenta, API-ja ili modela. Omogućava praćenje promena, kompatibilnost i vraćanje na prethodnu verziju. Svaka verzija treba da ima jedinstvenu oznaku i jasno definisano značenje.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "write_through_cache",
            titleSr = "Write-through cache",
            titleEn = "Write-through caching",
            explanation = "Write-through cache istovremeno upisuje promenu u keš i trajno skladište. Time se smanjuje rizik da keš i baza prikazuju različite vrednosti. Upis može biti sporiji jer mora sačekati oba koraka.",
            categoryId = DATA_STORAGE_SEARCH
        ),
        term(
            id = "sifrovana_lokalna_baza",
            titleSr = "Šifrovana lokalna baza",
            titleEn = "Encrypted local database",
            explanation = "Šifrovana lokalna baza čuva podatke na uređaju u zaštićenom obliku. Bez odgovarajućeg ključa sadržaj baze nije čitljiv čak i ako neko pristupi fajlu. Koristi se za osetljive podatke u mobilnim, desktop i offline aplikacijama.",
            categoryId = DATA_STORAGE_SEARCH
        )
    )

    private fun softwareArchitectureTerms(): List<EncyclopediaTerm> = listOf(
        term(
            id = "adr",
            titleSr = "ADR",
            titleEn = "Architecture Decision Record",
            explanation = "ADR je kratak dokument koji beleži važnu arhitektonsku odluku. Sadrži kontekst, razmatrane mogućnosti, izabrano rešenje i njegove posledice. Omogućava budućim članovima tima da razumeju zašto je arhitektura oblikovana na određeni način.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "aplikacioni_sloj",
            titleSr = "Aplikacioni sloj",
            titleEn = "Application layer",
            explanation = "Aplikacioni sloj koordinira izvršavanje korisničkih i poslovnih slučajeva. On povezuje domenske objekte, skladišta i spoljne servise bez preuzimanja njihove unutrašnje logike. Primer je servis koji upravlja tokom kreiranja i potvrđivanja porudžbine.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "arhitektonska_komponenta",
            titleSr = "Arhitektonska komponenta",
            titleEn = "Architectural component",
            explanation = "Arhitektonska komponenta je veća funkcionalna celina sa jasno definisanom odgovornošću i interfejsom. Može biti servis, modul, baza, gateway ili drugi važan deo sistema. Dijagrami arhitekture prikazuju kako komponente sarađuju i razmenjuju podatke.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "arhitektonska_odluka",
            titleSr = "Arhitektonska odluka",
            titleEn = "Architecture decision",
            explanation = "Arhitektonska odluka je izbor koji značajno utiče na strukturu, ponašanje ili razvoj sistema. Primeri su izbor baze, komunikacionog modela ili granice između servisa. Odluka treba da bude zasnovana na zahtevima, ograničenjima i prihvaćenim kompromisima.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "arhitektonski_dijagram",
            titleSr = "Arhitektonski dijagram",
            titleEn = "Architecture diagram",
            explanation = "Arhitektonski dijagram je vizuelni prikaz glavnih delova sistema i njihovih veza. Može prikazivati komponente, servise, skladišta, korisnike i tokove komunikacije. Njegov nivo detalja treba prilagoditi pitanju koje dijagram objašnjava.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "arhitektonski_kompromis",
            titleSr = "Arhitektonski kompromis",
            titleEn = "Architecture trade-off",
            explanation = "Arhitektonski kompromis nastaje kada poboljšanje jedne osobine sistema pogorša ili poskupi drugu. Na primer, jaka konzistentnost može povećati latenciju i smanjiti dostupnost. Dobra odluka jasno navodi koje koristi i troškove tim prihvata.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "arhitektonski_obrazac",
            titleSr = "Arhitektonski obrazac",
            titleEn = "Architectural pattern",
            explanation = "Arhitektonski obrazac je proverena organizacija velikih delova softverskog sistema. On opisuje glavne komponente, odgovornosti i način njihove komunikacije. Primeri su event-driven arhitektura, mikroservisi i slojevita arhitektura.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "arhitektonski_rizik",
            titleSr = "Arhitektonski rizik",
            titleEn = "Architecture risk",
            explanation = "Arhitektonski rizik je mogućnost da izabrani dizajn ne zadovolji važan zahtev ili izazove ozbiljan problem. Može se odnositi na skaliranje, bezbednost, dostupnost, složenost ili zavisnost od spoljnog sistema. Rizici se procenjuju, dokumentuju i smanjuju prototipovima, testovima i rezervnim planovima.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "arhitektonski_sloj",
            titleSr = "Arhitektonski sloj",
            titleEn = "Architectural layer",
            explanation = "Arhitektonski sloj grupiše komponente sa sličnom vrstom odgovornosti. Uobičajeni slojevi su korisnički, aplikacioni, domenski, integracioni i infrastrukturni. Jasne veze između slojeva sprečavaju nekontrolisano mešanje poslovne i tehničke logike.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "arhitektonski_stil",
            titleSr = "Arhitektonski stil",
            titleEn = "Architectural style",
            explanation = "Arhitektonski stil je opšti način organizovanja sistema i njegovih glavnih odnosa. On postavlja pravila o komponentama, komunikaciji i raspodeli odgovornosti. Monolit, mikroservisi i event-driven pristup predstavljaju različite arhitektonske stilove.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "arhitektonsko_ogranicenje",
            titleSr = "Arhitektonsko ograničenje",
            titleEn = "Architecture constraint",
            explanation = "Arhitektonsko ograničenje je uslov koji sužava skup mogućih rešenja. Može poticati iz zakona, postojeće tehnologije, budžeta, roka ili organizacione politike. Arhitektura mora poštovati ograničenja čak i kada bi tehnički postojalo elegantnije rešenje.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "autoritativni_zapis",
            titleSr = "Autoritativni zapis",
            titleEn = "Authoritative record",
            explanation = "Autoritativni zapis je zvaničan zapis kome sistem veruje kada različiti izvori prikazuju različite podatke. On predstavlja konačnu osnovu za poslovne odluke i usklađivanje izvedenih modela. Primer je potvrđena rezervacija u glavnom sistemu za upravljanje kapacitetom.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "autoritativno_stanje",
            titleSr = "Autoritativno stanje",
            titleEn = "Authoritative state",
            explanation = "Autoritativno stanje je zvanično i konačno prihvaćeno stanje entiteta ili procesa. Ostali prikazi mogu biti izvedeni, keširani ili privremeno zastareli. Sve važne odluke treba potvrditi prema autoritativnom stanju.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "big_bang_migracija",
            titleSr = "Big bang migracija",
            titleEn = "Big bang migration",
            explanation = "Big bang migracija zamenjuje stari sistem novim u jednom velikom koraku. Zahteva da novi sistem, podaci i korisnici istovremeno pređu na novo rešenje. Brža je kao završni prelaz, ali nosi visok rizik i otežan povratak ako se pojavi problem.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "centralizovana_arhitektura",
            titleSr = "Centralizovana arhitektura",
            titleEn = "Centralized architecture",
            explanation = "Centralizovana arhitektura smešta većinu logike, podataka ili kontrole u jednu glavnu komponentu ili sistem. Jednostavnija je za upravljanje i održavanje konzistentnosti. Može ipak postati usko grlo i jedinstvena tačka otkaza.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "distribuirani_sistem",
            titleSr = "Distribuirani sistem",
            titleEn = "Distributed system",
            explanation = "Distribuirani sistem čini više računara ili servisa koji zajedno izvršavaju funkcije jednog sistema. Komponente komuniciraju preko mreže i mogu nezavisno otkazati ili kasniti. Takav sistem mora rešavati konzistentnost, parcijalne greške, vreme i pouzdanu komunikaciju.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "domen",
            titleSr = "Domen",
            titleEn = "Domain",
            explanation = "Domen je poslovna ili problemska oblast koju softverski sistem podržava. Sadrži pojmove, pravila, procese i entitete karakteristične za tu oblast. Primeri domena su osiguranje, plaćanja, logistika i upravljanje dokumentima.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "edge_first_arhitektura",
            titleSr = "Edge-first arhitektura",
            titleEn = "Edge-first architecture",
            explanation = "Edge-first arhitektura pomera deo obrade i odluka bliže uređajima i izvorima podataka. Time se smanjuju latencija, mrežni saobraćaj i zavisnost od centralne veze. Centralni sistem se koristi za koordinaciju, agregaciju i dugoročno čuvanje.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "eksterna_integracija",
            titleSr = "Eksterna integracija",
            titleEn = "External integration",
            explanation = "Eksterna integracija povezuje sistem sa servisom ili platformom izvan njegove direktne kontrole. Komunikacija se odvija preko API-ja, poruka, fajlova ili drugih ugovorenih mehanizama. Arhitektura mora očekivati kašnjenja, promene ugovora i privremenu nedostupnost partnera.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "event_driven_arhitektura",
            titleSr = "Event-driven arhitektura",
            titleEn = "Event-driven architecture",
            explanation = "Event-driven arhitektura organizuje saradnju komponenti oko objavljivanja i obrade događaja. Proizvođači ne moraju direktno poznavati sve potrošače koji reaguju na promenu. Ovaj pristup povećava fleksibilnost, ali otežava praćenje toka i upravljanje konzistentnošću.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "event_sourced_arhitektura",
            titleSr = "Event-sourced arhitektura",
            titleEn = "Event-sourced architecture",
            explanation = "Event-sourced arhitektura čuva niz događaja kao primarni zapis promena sistema. Trenutno stanje se dobija ponovnim izvršavanjem ili projekcijom tih događaja. Omogućava potpunu istoriju, ali zahteva pažljivo verzionisanje događaja i rekonstrukciju stanja.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "granica_domena",
            titleSr = "Granica domena",
            titleEn = "Domain boundary",
            explanation = "Granica domena odvaja jednu poslovnu oblast i njena pravila od drugih oblasti. Unutar granice pojmovi i modeli imaju dosledno značenje. Komunikacija preko granice treba da koristi jasno definisane ugovore i prevode između modela.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "granica_sistema",
            titleSr = "Granica sistema",
            titleEn = "System boundary",
            explanation = "Granica sistema određuje šta pripada posmatranom sistemu, a šta je korisnik, partner ili spoljna zavisnost. Ona pomaže da se jasno definišu odgovornosti i integracione tačke. Bez jasne granice teško je proceniti bezbednost, dostupnost i vlasništvo nad podacima.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "hibridni_model",
            titleSr = "Hibridni model",
            titleEn = "Hybrid architecture",
            explanation = "Hibridni model kombinuje više arhitektonskih pristupa u jednom sistemu. Na primer, deo sistema može biti modularni monolit, a zahtevne funkcije nezavisni servisi. Cilj je koristiti različite modele tamo gde njihove prednosti najviše odgovaraju stvarnim potrebama.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "informativno_stanje",
            titleSr = "Informativno stanje",
            titleEn = "Informational state",
            explanation = "Informativno stanje je prikaz podataka namenjen informisanju, ali ne i konačnoj poslovnoj potvrdi. Može poticati iz keša, read modela ili poslednjeg poznatog stanja. Pre kritične operacije mora se proveriti autoritativni izvor.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "integracioni_sloj",
            titleSr = "Integracioni sloj",
            titleEn = "Integration layer",
            explanation = "Integracioni sloj upravlja komunikacijom sa spoljnim servisima, bazama i tehničkim protokolima. On prevodi formate, kontroliše greške i štiti ostatak sistema od detalja partnera. Adapteri, gateway-i i klijenti spoljnih API-ja često pripadaju ovom sloju.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "izvedeni_model",
            titleSr = "Izvedeni model",
            titleEn = "Derived model",
            explanation = "Izvedeni model nastaje obradom podataka iz jednog ili više autoritativnih izvora. Prilagođen je pretrazi, prikazu, analitici ili drugoj posebnoj potrebi. Ako se izgubi, često se može ponovo izgraditi iz izvornih podataka.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "izvor_istine",
            titleSr = "Izvor istine",
            titleEn = "Source of truth",
            explanation = "Izvor istine je mesto koje se smatra zvaničnim autoritetom za određenu vrstu podataka. Kada se različiti sistemi ne slažu, njegova vrednost ima prednost. Za svaki važan podatak treba jasno odrediti koji servis ili baza imaju ovu ulogu.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "klijentski_sloj",
            titleSr = "Klijentski sloj",
            titleEn = "Client layer",
            explanation = "Klijentski sloj predstavlja deo sistema sa kojim korisnik ili spoljašnja aplikacija neposredno komunicira. Može biti web, mobilna, desktop ili druga korisnička aplikacija. Njegova uloga je prikaz podataka, prikupljanje ulaza i pozivanje aplikacionih servisa.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "kontekst_odluke",
            titleSr = "Kontekst odluke",
            titleEn = "Decision context",
            explanation = "Kontekst odluke opisuje problem, zahteve i okolnosti u kojima je arhitektonska odluka doneta. Uključuje poslovne ciljeve, postojeći sistem, ograničenja i važne rizike. Bez konteksta budući tim može pogrešno proceniti da je ranija odluka bila nerazumna.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "korisnicki_tok",
            titleSr = "Korisnički tok",
            titleEn = "User flow",
            explanation = "Korisnički tok je niz koraka koje korisnik prolazi da bi ostvario određeni cilj. Može obuhvatiti više ekrana, servisa i poslovnih operacija. Arhitektura treba posebno zaštititi i optimizovati tokove koji su najvažniji korisnicima.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "kriticni_domen",
            titleSr = "Kritični domen",
            titleEn = "Core domain",
            explanation = "Kritični domen je deo poslovanja koji donosi najveću vrednost ili razlikuje organizaciju od konkurencije. Njegova pravila zaslužuju najveću pažnju, stručnost i kvalitet modelovanja. Pomoćne funkcije se mogu rešiti jednostavnije ili osloniti na gotova rešenja.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "kriticni_tok",
            titleSr = "Kritični tok",
            titleEn = "Critical path",
            explanation = "Kritični tok je niz operacija čiji kvar direktno ugrožava osnovnu funkciju sistema. Primeri su naplata, potvrda rezervacije i prijem hitnog poziva. Za takav tok prioritet imaju pouzdanost, monitoring, kontrolisana degradacija i oporavak.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "legacy_jezgro",
            titleSr = "Legacy jezgro",
            titleEn = "Legacy core",
            explanation = "Legacy jezgro je centralni deo starog sistema koji i dalje sadrži ključne podatke ili poslovna pravila. Često je teško menjati ga zbog zastarele tehnologije i velikog broja zavisnosti. Modernizacija obično prvo gradi jasne interfejse oko njega, a zatim postepeno izdvaja funkcije.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "legacy_sistem",
            titleSr = "Legacy sistem",
            titleEn = "Legacy system",
            explanation = "Legacy sistem je stariji softverski sistem koji je i dalje važan za poslovanje. Može koristiti zastarelu tehnologiju, imati slabu dokumentaciju i biti težak za održavanje. Njegova zamena zahteva pažljivo očuvanje podataka i postojećih poslovnih pravila.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "mikroservisna_arhitektura",
            titleSr = "Mikroservisna arhitektura",
            titleEn = "Microservices architecture",
            explanation = "Mikroservisna arhitektura deli sistem na više malih i samostalnih servisa. Svaki servis obično upravlja svojim domenom, podacima i deployment-om. Donosi nezavisno skaliranje, ali povećava složenost komunikacije, observability-ja i operativnog upravljanja.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "modul",
            titleSr = "Modul",
            titleEn = "Module",
            explanation = "Modul je izdvojena funkcionalna celina unutar aplikacije ili sistema. Ima jasnu odgovornost i kontrolisan skup javnih interfejsa. Dobro projektovani moduli smanjuju spregnutost i omogućavaju lakše testiranje i održavanje.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "modularni_monolit",
            titleSr = "Modularni monolit",
            titleEn = "Modular monolith",
            explanation = "Modularni monolit je jedna aplikacija podeljena na jasno odvojene unutrašnje module. Moduli se zajedno postavljaju, ali imaju kontrolisane granice i odgovornosti. Pruža jednostavnije operacije od mikroservisa uz bolju strukturu od neraščlanjenog monolita.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "monolit",
            titleSr = "Monolit",
            titleEn = "Monolith",
            explanation = "Monolit je aplikacija u kojoj je većina funkcionalnosti objedinjena u jednoj jedinici za izgradnju i deployment. Jednostavan je za početni razvoj, lokalno testiranje i transakcije. Kako raste, može postati težak za menjanje, skaliranje i razumevanje ako nema dobre unutrašnje granice.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "mvp",
            titleSr = "MVP",
            titleEn = "Minimum Viable Product",
            explanation = "MVP je najjednostavnija verzija proizvoda koja rešava osnovni korisnički problem i omogućava proveru ideje. Ne znači nekvalitetan proizvod, već kontrolisan minimalni obim funkcionalnosti. Povratne informacije iz MVP-a usmeravaju sledeće odluke i ulaganja.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "nezavisni_deployment",
            titleSr = "Nezavisni deployment",
            titleEn = "Independent deployment",
            explanation = "Nezavisni deployment znači da se jedna komponenta može postaviti bez istovremenog izdavanja ostatka sistema. Time tim može brže isporučivati promene i izolovati rizik. Zahteva stabilne ugovore i kompatibilnost između različitih verzija komponenti.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "nezavisno_skaliranje",
            titleSr = "Nezavisno skaliranje",
            titleEn = "Independent scaling",
            explanation = "Nezavisno skaliranje omogućava povećavanje kapaciteta samo za komponentu kojoj je to potrebno. Na primer, servis za pretragu može dobiti više instanci bez skaliranja administrativnog portala. Ovaj pristup smanjuje troškove, ali povećava infrastrukturnu složenost.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "offline_first_arhitektura",
            titleSr = "Offline-first arhitektura",
            titleEn = "Offline-first architecture",
            explanation = "Offline-first arhitektura tretira rad bez mreže kao osnovni, a ne izuzetan scenario. Podaci i operacije se prvo čuvaju lokalno, a zatim sinhronizuju kada veza postane dostupna. Sistem mora rešavati redosled, duplikate i konflikte izmena.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "operativni_tok",
            titleSr = "Operativni tok",
            titleEn = "Operational flow",
            explanation = "Operativni tok opisuje kako sistem izvršava konkretan proces od početka do kraja. Uključuje komponente, poruke, podatke, odluke i moguće greške. Koristi se za razumevanje stvarnog ponašanja sistema, a ne samo njegove statičke strukture.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "paralelni_rad_starog_i_novog_sistema",
            titleSr = "Paralelni rad starog i novog sistema",
            titleEn = "Parallel run",
            explanation = "Paralelni rad znači da stari i novi sistem određeno vreme rade istovremeno. Rezultati se porede kako bi se proverila tačnost novog rešenja pre potpunog prelaska. Ovaj pristup smanjuje rizik, ali zahteva sinhronizaciju i dodatne operativne troškove.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "pomocni_tok",
            titleSr = "Pomoćni tok",
            titleEn = "Secondary flow",
            explanation = "Pomoćni tok podržava glavni poslovni proces, ali nije njegov najkritičniji deo. Primeri su analitika, preporuke, dodatna obaveštenja i generisanje pomoćnih izveštaja. Tok se može privremeno ograničiti kako bi kritične funkcije ostale dostupne.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "poslednje_poznato_stanje",
            titleSr = "Poslednje poznato stanje",
            titleEn = "Last known state",
            explanation = "Poslednje poznato stanje je najnoviji podatak koji je sistem uspešno primio ili sačuvao. Može se prikazati kada trenutno stanje nije dostupno zbog prekida mreže ili servisa. Mora biti jasno označeno vreme kada je podatak poslednji put potvrđen.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "poslovno_pravilo",
            titleSr = "Poslovno pravilo",
            titleEn = "Business rule",
            explanation = "Poslovno pravilo definiše uslov, ograničenje ili odluku važnu za poslovni domen. Primer je pravilo da refundaciju iznad određenog iznosa mora odobriti menadžer. Pravila treba držati jasno izdvojena i testirati nezavisno od korisničkog interfejsa.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "postepena_modernizacija",
            titleSr = "Postepena modernizacija",
            titleEn = "Incremental modernization",
            explanation = "Postepena modernizacija zamenjuje ili unapređuje stari sistem u manjim, kontrolisanim koracima. Svaka faza donosi konkretnu vrednost i omogućava proveru pre nastavka. Smanjuje rizik u odnosu na veliku jednokratnu migraciju.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "potvrdjeno_stanje",
            titleSr = "Potvrđeno stanje",
            titleEn = "Confirmed state",
            explanation = "Potvrđeno stanje je stanje koje je autoritativni sistem uspešno proverio i prihvatio. Ono se može koristiti za nastavak kritičnog poslovnog toka. Razlikuje se od procenjenog, keširanog ili stanja koje još čeka potvrdu.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "prelaz_stanja",
            titleSr = "Prelaz stanja",
            titleEn = "State transition",
            explanation = "Prelaz stanja je promena entiteta iz jednog definisanog stanja u drugo. Dozvoljen je samo kada su ispunjena odgovarajuća poslovna pravila. Primer je prelazak porudžbine iz stanja „Plaćena“ u stanje „Poslata“.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "prihvacena_cena_odluke",
            titleSr = "Prihvaćena cena odluke",
            titleEn = "Accepted trade-off",
            explanation = "Prihvaćena cena odluke je nedostatak ili trošak koji je tim svesno prihvatio radi važnije koristi. Može biti veća latencija, dodatna infrastruktura ili ograničena fleksibilnost. Njeno dokumentovanje sprečava da se poznata posledica kasnije pogrešno tumači kao previd.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "search_first_pristup",
            titleSr = "Search-first pristup",
            titleEn = "Search-first architecture",
            explanation = "Search-first pristup tretira pretragu kao centralni način pristupa informacijama u sistemu. Podaci se organizuju i indeksiraju prvenstveno prema potrebama pronalaženja, filtriranja i rangiranja. Pogodan je za velike arhive, kataloge i sisteme sa raznovrsnim upitima.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "serverless_arhitektura",
            titleSr = "Serverless arhitektura",
            titleEn = "Serverless architecture",
            explanation = "Serverless arhitektura koristi upravljane funkcije i servise koje cloud platforma automatski pokreće i skalira. Tim ne upravlja direktno serverima, već plaća korišćenje prema izvršavanju ili kapacitetu. Pogodna je za promenljivo opterećenje, ali uvodi zavisnost od platforme i ograničenja izvršnog okruženja.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "servis",
            titleSr = "Servis",
            titleEn = "Service",
            explanation = "Servis je komponenta koja pruža jasno definisanu funkcionalnost drugim delovima sistema. Može biti unutrašnja klasa, aplikacioni servis ili nezavisni mrežni proces. Njegov ugovor treba da sakrije detalje implementacije i vlasništva nad podacima.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "softverska_arhitektura",
            titleSr = "Softverska arhitektura",
            titleEn = "Software architecture",
            explanation = "Softverska arhitektura predstavlja osnovnu strukturu sistema i najvažnije odluke koje oblikuju njegov razvoj. Obuhvata komponente, granice, komunikaciju, podatke i kvalitativne zahteve. Dobra arhitektura omogućava da sistem zadovolji poslovne potrebe uz prihvatljive rizike i troškove.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "spoljni_provider",
            titleSr = "Spoljni provider",
            titleEn = "External provider",
            explanation = "Spoljni provider je eksterni servis koji organizaciji pruža određenu funkcionalnost. Primeri su payment provider, servis za slanje poruka i cloud skladište. Pošto nije pod direktnom kontrolom tima, integracija mora očekivati promene, greške i ograničenja korišćenja.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "spoljni_sistem",
            titleSr = "Spoljni sistem",
            titleEn = "External system",
            explanation = "Spoljni sistem je aplikacija ili platforma izvan granice posmatranog sistema. Sa njim se razmenjuju podaci preko ugovorenog interfejsa ili formata. Njegova dostupnost i ponašanje ne mogu se potpuno kontrolisati iz unutrašnje aplikacije.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "stale_podatak",
            titleSr = "Stale podatak",
            titleEn = "Zastareli podatak",
            explanation = "Stale podatak je vrednost koja više ne odgovara najnovijem autoritativnom stanju. Može se pojaviti u kešu, replici, read modelu ili offline uređaju. Sistem mora znati koliko je zastarelost prihvatljiva za određenu operaciju.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "statusni_model",
            titleSr = "Statusni model",
            titleEn = "State model",
            explanation = "Statusni model definiše moguća stanja entiteta i dozvoljene prelaze između njih. Može sadržati pravila, akcije i ograničenja vezana za svako stanje. Koristi se za narudžbine, dokumente, reklamacije i druge procese sa životnim ciklusom.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "svezina_podataka",
            titleSr = "Svežina podataka",
            titleEn = "Data freshness",
            explanation = "Svežina podataka pokazuje koliko je vremena prošlo od poslednjeg ažuriranja ili potvrde podatka. Različiti poslovni procesi mogu tolerisati različit nivo zastarelosti. Sistem treba prikazati vreme ažuriranja ili odbiti odluku kada je podatak previše star.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "tacka_izdvajanja_servisa",
            titleSr = "Tačka izdvajanja servisa",
            titleEn = "Service extraction boundary",
            explanation = "Tačka izdvajanja servisa je granica oko dela monolita koji može postati samostalni servis. Dobar kandidat ima jasno vlasništvo nad pravilima, podacima i komunikacionim ugovorima. Izdvajanje treba da donese stvarnu korist kao što su nezavisno skaliranje ili razvoj.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "tok_dogadjaja",
            titleSr = "Tok događaja",
            titleEn = "Event flow",
            explanation = "Tok događaja opisuje redosled objavljivanja i obrade događaja kroz sistem. Prikazuje proizvođače, brokere, potrošače i posledice svakog događaja. Pomaže u razumevanju asinhronih procesa, duplikata i eventualne konzistentnosti.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "tok_komandi",
            titleSr = "Tok komandi",
            titleEn = "Command flow",
            explanation = "Tok komandi opisuje kako zahtev za izvršenje određene akcije prolazi kroz sistem. Komanda izražava nameru da se stanje promeni, za razliku od događaja koji opisuje da se promena već dogodila. Tok obuhvata prijem, validaciju, izvršenje i vraćanje rezultata.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "tok_podataka",
            titleSr = "Tok podataka",
            titleEn = "Data flow",
            explanation = "Tok podataka prikazuje kako se podaci kreću između korisnika, komponenti, servisa i skladišta. Može uključivati transformacije, validaciju, agregaciju i promenu formata. Koristi se za analizu integracija, bezbednosti, performansi i vlasništva nad podacima.",
            categoryId = SOFTWARE_ARCHITECTURE
        ),
        term(
            id = "zivotni_ciklus_entiteta",
            titleSr = "Životni ciklus entiteta",
            titleEn = "Entity lifecycle",
            explanation = "Životni ciklus entiteta opisuje stanja i promene kroz koje poslovni objekat prolazi od nastanka do završetka. Uključuje dozvoljene operacije, prelaze i uslove za svaku fazu. Primer je put porudžbine od kreiranja, preko plaćanja i slanja, do isporuke ili otkazivanja.",
            categoryId = SOFTWARE_ARCHITECTURE
        )
    )

    private fun patternRolesTerms(): List<EncyclopediaTerm> = listOf(
        term(
            id = "abstract_class_u_template_method_obrascu",
            titleSr = "Abstract Class u Template Method obrascu",
            titleEn = "AbstractClass",
            explanation = "Abstract Class definiše zajednički kostur algoritma u Template Method obrascu. Ona sadrži template metodu i operacije koje podklase treba da implementiraju ili prošire. Time obezbeđuje da osnovni redosled izvršavanja ostane isti za sve varijante procesa.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "abstract_decorator",
            titleSr = "Abstract Decorator",
            titleEn = "Apstraktni dekorater",
            explanation = "Abstract Decorator je osnovna klasa dekoratera koja implementira isti interfejs kao objekat koji omotava. U sebi čuva referencu na Component i obično mu prosleđuje pozive. Konkretni dekorateri ga nasleđuju kako bi dodali sopstveno ponašanje.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "abstract_expression",
            titleSr = "Abstract Expression",
            titleEn = "Apstraktni izraz",
            explanation = "Abstract Expression definiše zajedničku operaciju za tumačenje izraza u Interpreter obrascu. Najčešće sadrži metodu poput `interpret()` koju implementiraju konkretni izrazi. Klijentski kod zato može jednako koristiti različite delove gramatike.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "abstract_factory_interfejs",
            titleSr = "Abstract Factory interfejs",
            titleEn = "Abstract factory interface",
            explanation = "Abstract Factory interfejs definiše metode za kreiranje više povezanih proizvoda. On ne određuje konkretne klase objekata koji će biti napravljeni. Klijent preko njega može menjati celu porodicu proizvoda bez promene glavne logike.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "abstraction",
            titleSr = "Abstraction",
            titleEn = "Apstrakcija u Bridge obrascu",
            explanation = "Abstraction predstavlja viši nivo funkcionalnosti koji klijent koristi u Bridge obrascu. U sebi čuva referencu na Implementor objekat kome delegira tehnički deo posla. Zahvaljujući tome, apstrakcija i implementacija mogu se menjati nezavisno.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "adaptee",
            titleSr = "Adaptee",
            titleEn = "Prilagođavani objekat",
            explanation = "Adaptee je postojeća klasa čiji interfejs nije kompatibilan sa onim što klijent očekuje. Ona obično već sadrži korisnu funkcionalnost koju nije poželjno menjati. Adapter prevodi pozive ciljnog interfejsa u operacije koje Adaptee razume.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "adapter_klasa",
            titleSr = "Adapter klasa",
            titleEn = "Adapter class",
            explanation = "Adapter klasa povezuje očekivani Target interfejs sa nekompatibilnim Adaptee objektom. Ona prima poziv od klijenta i prevodi ga u odgovarajući oblik. Tako se postojeća klasa može koristiti bez promene klijentskog koda.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "aggregate",
            titleSr = "Aggregate",
            titleEn = "Agregat; kolekcija",
            explanation = "Aggregate je kolekcija čiji elementi mogu da se obilaze pomoću Iterator obrasca. Ona definiše način za dobijanje odgovarajućeg iteratora. Klijent ne mora da zna kako su elementi interno sačuvani.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "builder_interfejs",
            titleSr = "Builder interfejs",
            titleEn = "Builder interface",
            explanation = "Builder interfejs definiše korake za postepenu izgradnju složenog objekta. Različiti Concrete Builder objekti mogu iste korake realizovati na različite načine. Klijent zato može koristiti isti proces za pravljenje više varijanti proizvoda.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "caretaker",
            titleSr = "Caretaker",
            titleEn = "Čuvar istorije",
            explanation = "Caretaker čuva Memento objekte u Memento obrascu. On ne treba da zna niti menja unutrašnji sadržaj sačuvanog stanja. Kada je potrebno vraćanje, odgovarajući Memento prosleđuje Originator objektu.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "colleague",
            titleSr = "Colleague",
            titleEn = "Kolega u Mediator obrascu",
            explanation = "Colleague je objekat koji učestvuje u komunikaciji preko Mediator-a. Umesto direktnog poznavanja svih drugih objekata, on obaveštava centralnog posrednika. Na taj način se smanjuje broj direktnih veza između komponenti.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "command_interfejs",
            titleSr = "Command interfejs",
            titleEn = "Command interface",
            explanation = "Command interfejs definiše zajedničku operaciju za izvršavanje zahteva. Najčešće sadrži metodu kao što je `execute()`, a ponekad i `undo()`. Invoker zato može pokretati različite komande na isti način.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "component_u_composite_obrascu",
            titleSr = "Component u Composite obrascu",
            titleEn = "Component",
            explanation = "Component definiše zajednički interfejs za pojedinačne i složene objekte u Composite obrascu. Leaf i Composite implementiraju iste osnovne operacije. Klijent zato može jednako koristiti jedan element i celu grupu elemenata.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "component_u_decorator_obrascu",
            titleSr = "Component u Decorator obrascu",
            titleEn = "Decorator component",
            explanation = "Component predstavlja zajednički interfejs osnovnog objekta i njegovih dekoratera. Concrete Component pruža osnovno ponašanje, dok dekorateri dodaju nove odgovornosti. Zajednički interfejs omogućava njihovo međusobno ulančavanje.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "composite_objekat",
            titleSr = "Composite objekat",
            titleEn = "Composite node",
            explanation = "Composite objekat predstavlja složeni element koji sadrži druge Component objekte. Njegova deca mogu biti pojedinačni Leaf objekti ili drugi Composite objekti. Operacije često prosleđuje deci i objedinjuje njihove rezultate.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "concrete_aggregate",
            titleSr = "Concrete Aggregate",
            titleEn = "Konkretna kolekcija",
            explanation = "Concrete Aggregate je konkretna kolekcija koja čuva elemente i kreira odgovarajući iterator. Ona može koristiti listu, stablo ili drugu internu strukturu. Iterator omogućava obilazak bez otkrivanja te strukture klijentu.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "concrete_builder",
            titleSr = "Concrete Builder",
            titleEn = "Konkretni Builder",
            explanation = "Concrete Builder implementira korake definisane Builder interfejsom. On čuva trenutno stanje proizvoda i postepeno dodaje njegove delove. Na kraju vraća konkretnu izgrađenu instancu.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "concrete_class_u_template_method_obrascu",
            titleSr = "Concrete Class u Template Method obrascu",
            titleEn = "ConcreteClass",
            explanation = "Concrete Class nasleđuje Abstract Class i implementira promenljive korake algoritma. Ona ne menja zajednički redosled definisan template metodom. Različite konkretne klase zato mogu prilagoditi samo potrebne faze procesa.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "concrete_command",
            titleSr = "Concrete Command",
            titleEn = "Konkretna komanda",
            explanation = "Concrete Command predstavlja jednu konkretnu akciju kao objekat. U sebi obično čuva Receiver i podatke potrebne za izvršavanje zahteva. Njena `execute()` metoda poziva odgovarajuću operaciju Receiver objekta.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "concrete_component",
            titleSr = "Concrete Component",
            titleEn = "Konkretna komponenta",
            explanation = "Concrete Component je osnovni objekat kome dekorateri dodaju ponašanje. On implementira zajednički Component interfejs i pruža početnu funkcionalnost. Može se koristiti samostalno ili omotati jednim ili više dekoratera.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "concrete_creator",
            titleSr = "Concrete Creator",
            titleEn = "Konkretni kreator",
            explanation = "Concrete Creator implementira ili prepisuje fabričku metodu u Factory Method obrascu. Njegova odgovornost je da napravi određeni Concrete Product. Ostatak logike može raditi sa opštim Product interfejsom.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "concrete_decorator",
            titleSr = "Concrete Decorator",
            titleEn = "Konkretni dekorater",
            explanation = "Concrete Decorator dodaje određeno ponašanje objektu koji omotava. Može izvršiti dodatnu logiku pre ili nakon prosleđenog poziva. Više konkretnih dekoratera može se kombinovati u različitom redosledu.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "concrete_element",
            titleSr = "Concrete Element",
            titleEn = "Konkretni element",
            explanation = "Concrete Element je konkretna vrsta objekta nad kojom Visitor izvršava operaciju. On implementira `accept(visitor)` i poziva odgovarajuću `visit` metodu. Na taj način Visitor dobija pristup ponašanju specifičnom za tačan tip elementa.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "concrete_factory",
            titleSr = "Concrete Factory",
            titleEn = "Konkretna fabrika",
            explanation = "Concrete Factory pravi konkretnu porodicu međusobno usklađenih proizvoda. Ona implementira sve metode Abstract Factory interfejsa. Primer je fabrika koja kreira samo tamne verzije dugmadi, prozora i menija.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "concrete_handler",
            titleSr = "Concrete Handler",
            titleEn = "Konkretni obrađivač",
            explanation = "Concrete Handler implementira određenu proveru ili način obrade zahteva. Ako može da obradi zahtev, izvršava odgovarajuću logiku. U suprotnom ga prosleđuje sledećem Handler objektu u lancu.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "concrete_implementor",
            titleSr = "Concrete Implementor",
            titleEn = "Konkretni implementor",
            explanation = "Concrete Implementor pruža konkretnu tehničku implementaciju Implementor interfejsa. Može predstavljati određeni kanal slanja, renderer ili platformu. Abstraction ga koristi bez zavisnosti od njegove konkretne klase.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "concrete_iterator",
            titleSr = "Concrete Iterator",
            titleEn = "Konkretni iterator",
            explanation = "Concrete Iterator implementira prolazak kroz određenu vrstu kolekcije. Čuva trenutnu poziciju i zna kako da pronađe sledeći element. Klijentu izlaže samo operacije definisane Iterator interfejsom.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "concrete_mediator",
            titleSr = "Concrete Mediator",
            titleEn = "Konkretni mediator",
            explanation = "Concrete Mediator implementira pravila komunikacije između Colleague objekata. On prima obaveštenja od jedne komponente i odlučuje koje druge komponente treba da reaguju. Time centralizuje složenu koordinaciju sistema.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "concrete_observer",
            titleSr = "Concrete Observer",
            titleEn = "Konkretni posmatrač",
            explanation = "Concrete Observer reaguje na promene koje objavljuje Subject. On implementira Observer interfejs i sadrži konkretnu logiku ažuriranja. Primeri su UI prikaz, email servis ili analitička komponenta.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "concrete_product",
            titleSr = "Concrete Product",
            titleEn = "Konkretni proizvod",
            explanation = "Concrete Product je konkretna klasa objekta napravljena pomoću Factory Method obrasca. Ona implementira zajednički Product interfejs. Klijent je može koristiti bez poznavanja njenog tačnog tipa.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "concrete_state",
            titleSr = "Concrete State",
            titleEn = "Konkretno stanje",
            explanation = "Concrete State implementira ponašanje objekta za jedno određeno stanje. Može odlučiti koje operacije su dozvoljene i u koje stanje objekat može preći. Context delegira ponašanje trenutno aktivnom Concrete State objektu.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "concrete_strategy",
            titleSr = "Concrete Strategy",
            titleEn = "Konkretna strategija",
            explanation = "Concrete Strategy implementira jedan konkretan algoritam iz porodice strategija. Sve strategije poštuju isti Strategy interfejs. Context može menjati aktivnu strategiju bez menjanja sopstvene glavne logike.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "concrete_visitor",
            titleSr = "Concrete Visitor",
            titleEn = "Konkretni posetilac",
            explanation = "Concrete Visitor implementira jednu operaciju nad svim podržanim vrstama elemenata. Sadrži posebnu `visit` metodu za svaki Concrete Element. Dodavanje nove operacije tada zahteva novu Visitor klasu, a ne promenu postojećih elemenata.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "context",
            titleSr = "Context",
            titleEn = "Kontekst obrasca",
            explanation = "Context je objekat koji koristi Strategy ili State objekat za izvršavanje promenljivog ponašanja. On čuva referencu na trenutno izabranu implementaciju i prosleđuje joj odgovarajuće pozive. Klijent može promeniti strategiju, dok se stanje često menja kroz sam životni ciklus Context objekta.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "creator",
            titleSr = "Creator",
            titleEn = "Kreator",
            explanation = "Creator je osnovna klasa koja definiše Factory Method za pravljenje proizvoda. Može sadržati i poslovnu logiku koja koristi napravljeni Product. Konkretne podklase određuju koju vrstu proizvoda fabrička metoda vraća.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "director",
            titleSr = "Director",
            titleEn = "Director u Builder obrascu",
            explanation = "Director upravlja redosledom koraka izgradnje proizvoda. On koristi Builder interfejs i ne mora da zna tačan tip proizvoda koji nastaje. Isti Director može sa različitim builder-ima napraviti različite varijante objekta.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "double_dispatch",
            titleSr = "Double Dispatch",
            titleEn = "Duplo prosleđivanje",
            explanation = "Double Dispatch je mehanizam kojim izbor operacije zavisi od tipa dva objekta. U Visitor obrascu prvo se poziva `accept()` nad elementom, a zatim odgovarajuća `visit()` metoda nad visitor-om. Time se bira logika prilagođena i konkretnom Visitor-u i konkretnom Element-u.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "element_u_visitor_obrascu",
            titleSr = "Element u Visitor obrascu",
            titleEn = "Element",
            explanation = "Element definiše operaciju `accept(visitor)` u Visitor obrascu. Svaki konkretni element prosleđuje sebe odgovarajućoj Visitor metodi. Ova uloga omogućava izdvajanje operacija izvan same strukture objekata.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "facade_klasa",
            titleSr = "Facade klasa",
            titleEn = "Facade class",
            explanation = "Facade klasa pruža pojednostavljen ulaz u složen podsistem. Ona koordinira više unutrašnjih klasa i skriva potreban redosled njihovih poziva. Klijent zato koristi mali broj jasnih operacija umesto direktnog upravljanja podsistemom.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "flyweight_context",
            titleSr = "Flyweight Context",
            titleEn = "Kontekst Flyweight objekta",
            explanation = "Flyweight Context čuva jedinstveno, spoljašnje stanje konkretne instance. On povezuje to stanje sa deljenim Flyweight objektom. Primer je objekat stabla koji čuva koordinate, dok izgled dobija iz zajedničkog tipa stabla.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "flyweight_factory",
            titleSr = "Flyweight Factory",
            titleEn = "Fabrika deljenih objekata",
            explanation = "Flyweight Factory kreira, pronalazi i ponovo koristi deljene Flyweight objekte. Za isti skup unutrašnjih podataka vraća već postojeću instancu kada je moguće. Time sprečava nepotrebno pravljenje velikog broja identičnih objekata.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "flyweight_objekat",
            titleSr = "Flyweight objekat",
            titleEn = "Flyweight",
            explanation = "Flyweight objekat čuva unutrašnje stanje koje može biti zajedničko za više instanci. To stanje treba da bude stabilno i nezavisno od konkretnog konteksta korišćenja. Deljenjem se smanjuje memorijski trošak sistema.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "handler",
            titleSr = "Handler",
            titleEn = "Obrađivač",
            explanation = "Handler definiše zajednički interfejs za članove lanca odgovornosti. Obično čuva referencu na sledeći Handler objekat. Konkretni obrađivači preko njega formiraju lanac kroz koji zahtev prolazi.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "implementor",
            titleSr = "Implementor",
            titleEn = "Implementor interface",
            explanation = "Implementor definiše osnovne tehničke operacije u Bridge obrascu. Abstraction koristi ovaj interfejs umesto konkretne implementacije. Različiti Concrete Implementor objekti mogu predstavljati različite platforme ili načine izvršavanja.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "interpreter_context",
            titleSr = "Interpreter Context",
            titleEn = "Kontekst interpretacije",
            explanation = "Interpreter Context čuva informacije potrebne tokom tumačenja izraza. Može sadržati vrednosti promenljivih, ulazne podatke ili zajedničke postavke. Expression objekti koriste isti kontekst dok obrađuju svoje delove izraza.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "invoker",
            titleSr = "Invoker",
            titleEn = "Pokretač komande",
            explanation = "Invoker je objekat koji čuva ili pokreće Command objekat. On ne mora da zna kako se konkretna akcija izvršava. Primeri su dugme, meni, red poslova ili scheduler koji poziva `execute()`.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "iterator_interfejs",
            titleSr = "Iterator interfejs",
            titleEn = "Iterator interface",
            explanation = "Iterator interfejs definiše operacije za prolazak kroz kolekciju. Najčešće uključuje proveru postojanja sledećeg elementa i njegovo preuzimanje. Concrete Iterator implementira te operacije prema konkretnoj strukturi kolekcije.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "klasa_podsistema",
            titleSr = "Klasa podsistema",
            titleEn = "Subsystem class",
            explanation = "Klasa podsistema izvršava jednu konkretnu funkciju unutar složenog sistema. Facade je može pozivati zajedno sa drugim klasama radi realizacije šire operacije. Ona ne mora znati da Facade postoji i može ostati dostupna naprednim klijentima.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "leaf",
            titleSr = "Leaf",
            titleEn = "List; pojedinačni element",
            explanation = "Leaf je pojedinačni objekat u Composite strukturi koji nema sopstvenu decu. Implementira isti Component interfejs kao složeni Composite objekat. Njegova operacija izvršava se neposredno, bez prosleđivanja drugim elementima.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "mediator_interfejs",
            titleSr = "Mediator interfejs",
            titleEn = "Mediator interface",
            explanation = "Mediator interfejs definiše način na koji Colleague objekti prijavljuju događaje ili traže koordinaciju. On odvaja komponente od konkretne implementacije posrednika. Concrete Mediator zatim određuje stvarna pravila saradnje.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "memento_objekat",
            titleSr = "Memento objekat",
            titleEn = "Snapshot objekat",
            explanation = "Memento objekat čuva sačuvano stanje Originator objekta. Njegova unutrašnja struktura nije namenjena drugim delovima sistema. Caretaker ga samo čuva i kasnije vraća Originator-u.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "nonterminal_expression",
            titleSr = "Nonterminal Expression",
            titleEn = "Neterminalni izraz",
            explanation = "Nonterminal Expression predstavlja složeno pravilo gramatike sastavljeno od drugih izraza. On može sadržati više Terminal ili Nonterminal Expression objekata. Primer je izraz sabiranja koji interpretira levi i desni podizraz.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "observer_interfejs",
            titleSr = "Observer interfejs",
            titleEn = "Observer interface",
            explanation = "Observer interfejs definiše operaciju kojom Subject obaveštava pretplaćene objekte. Sve konkretne observer klase implementiraju isti način prijema promene. Subject zato ne mora da zna njihove unutrašnje detalje.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "originator",
            titleSr = "Originator",
            titleEn = "Objekat koji pravi i vraća snapshot",
            explanation = "Originator je objekat čije se stanje čuva u Memento obrascu. On jedini zna kako da napravi Memento i kako da iz njega obnovi svoje stanje. Time se čuva enkapsulacija unutrašnjih podataka.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "porodica_proizvoda",
            titleSr = "Porodica proizvoda",
            titleEn = "Product family",
            explanation = "Porodica proizvoda je grupa objekata koji su dizajnirani da rade ili izgledaju usklađeno. Abstract Factory pravi sve članove jedne porodice preko zajedničkog interfejsa. Primer su dugme, meni i prozor iste UI teme.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "posiljalac_zahteva",
            titleSr = "Pošiljalac zahteva",
            titleEn = "Request sender",
            explanation = "Pošiljalac zahteva pokreće obradu u Chain of Responsibility obrascu. On šalje zahtev prvom Handler objektu bez znanja ko će ga konačno obraditi. Time ostaje odvojen od konkretnih provera i obrađivača u lancu.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "product_u_builder_obrascu",
            titleSr = "Product u Builder obrascu",
            titleEn = "Builder product",
            explanation = "Product je složeni objekat koji nastaje postepenim radom Builder-a. Može sadržati mnogo delova i opcionih podešavanja. Concrete Builder ga sastavlja i na kraju vraća klijentu.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "product_u_factory_method_obrascu",
            titleSr = "Product u Factory Method obrascu",
            titleEn = "Factory Method product",
            explanation = "Product definiše zajednički interfejs objekata koje kreira Factory Method. Poslovna logika Creator klase koristi ovaj tip umesto konkretnih proizvoda. Concrete Product klase pružaju različite implementacije tog ugovora.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "prototype_objekat",
            titleSr = "Prototype objekat",
            titleEn = "Prototype object",
            explanation = "Prototype objekat predstavlja postojeću instancu koja može da napravi svoju kopiju. On obično pruža operaciju poput `clone()`. Klijent dobija novi objekat bez direktnog pozivanja složenog konstruktora.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "proxy_objekat",
            titleSr = "Proxy objekat",
            titleEn = "Zastupnik",
            explanation = "Proxy objekat kontroliše pristup stvarnom objektu preko istog interfejsa. Može proveravati dozvole, odložiti učitavanje ili upravljati udaljenim pozivom. Klijent ga koristi gotovo isto kao Real Subject.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "publisher",
            titleSr = "Publisher",
            titleEn = "Izdavač događaja",
            explanation = "Publisher objavljuje događaje ili poruke zainteresovanim pretplatnicima. Ne mora da zna identitet svih Subscriber objekata. Komunikacija se često odvija preko brokera, teme ili event bus-a.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "real_subject",
            titleSr = "Real Subject",
            titleEn = "Stvarni objekat",
            explanation = "Real Subject je objekat koji zaista izvršava osnovnu funkcionalnost u Proxy obrascu. Proxy mu prosleđuje zahtev kada su ispunjeni potrebni uslovi. Njegovo kreiranje ili korišćenje može biti skupo, udaljeno ili bezbednosno osetljivo.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "receiver",
            titleSr = "Receiver",
            titleEn = "Izvršilac komande",
            explanation = "Receiver je objekat koji zna kako da stvarno izvrši akciju predstavljenu Command objektom. Concrete Command čuva referencu na njega i poziva odgovarajuću metodu. Na primer, komanda za kopiranje može koristiti editor kao Receiver.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "refined_abstraction",
            titleSr = "Refined Abstraction",
            titleEn = "Proširena apstrakcija",
            explanation = "Refined Abstraction proširuje osnovnu Abstraction dodatnim ili specifičnijim ponašanjem. I dalje koristi Implementor interfejs za tehničko izvršavanje. Tako se nove vrste apstrakcije dodaju nezavisno od konkretnih implementacija.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "state_interfejs",
            titleSr = "State interfejs",
            titleEn = "State interface",
            explanation = "State interfejs definiše operacije koje mogu zavisiti od trenutnog stanja objekta. Sve Concrete State klase implementiraju isti ugovor. Context zato može delegirati poziv aktivnom stanju bez velikog `if-else` grananja.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "strategy_interfejs",
            titleSr = "Strategy interfejs",
            titleEn = "Strategy interface",
            explanation = "Strategy interfejs definiše zajedničku operaciju za porodicu zamenjivih algoritama. Concrete Strategy klase pružaju različite načine njenog izvršavanja. Context može promeniti algoritam bez menjanja ostatka svog koda.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "subject",
            titleSr = "Subject",
            titleEn = "Observable; izdavač promene",
            explanation = "Subject čuva listu Observer objekata i obaveštava ih kada se promeni relevantno stanje. On pruža operacije za pretplatu i odjavu posmatrača. Ne mora znati šta svaki observer radi nakon obaveštenja.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "subject_interfejs_u_proxy_obrascu",
            titleSr = "Subject interfejs u Proxy obrascu",
            titleEn = "Proxy subject interface",
            explanation = "Subject interfejs definiše zajedničke operacije za Proxy i Real Subject. Klijent zato može koristiti oba objekta na isti način. Proxy može presresti poziv pre nego što ga prosledi stvarnom objektu.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "subscriber",
            titleSr = "Subscriber",
            titleEn = "Pretplatnik; potrošač događaja",
            explanation = "Subscriber se prijavljuje za određene poruke ili teme u Publish/Subscribe obrascu. Kada publisher objavi odgovarajući događaj, subscriber ga prima i obrađuje. Više pretplatnika može nezavisno reagovati na istu poruku.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "target",
            titleSr = "Target",
            titleEn = "Ciljni interfejs",
            explanation = "Target je interfejs koji klijentski kod očekuje u Adapter obrascu. Adapter ga implementira i njegove pozive prevodi za Adaptee objekat. Klijent zato ne mora da se prilagođava nekompatibilnoj klasi.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "template_metoda",
            titleSr = "Template metoda",
            titleEn = "Template method",
            explanation = "Template metoda definiše fiksni redosled koraka nekog algoritma. Neki koraci su već implementirani, dok druge pružaju podklase. Često je finalna kako podklase ne bi promenile osnovnu strukturu procesa.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "terminal_expression",
            titleSr = "Terminal Expression",
            titleEn = "Terminalni izraz",
            explanation = "Terminal Expression predstavlja najjednostavnije pravilo gramatike koje se više ne razlaže. Može predstavljati broj, ključnu reč, promenljivu ili drugi osnovni simbol. Njegova `interpret()` metoda direktno vraća značenje tog elementa.",
            categoryId = PATTERN_ROLES
        ),
        term(
            id = "visitor_interfejs",
            titleSr = "Visitor interfejs",
            titleEn = "Visitor interface",
            explanation = "Visitor interfejs definiše `visit` operacije za različite vrste elemenata. Svaka metoda odgovara jednom Concrete Element tipu. Concrete Visitor implementira te metode za konkretnu operaciju nad strukturom.",
            categoryId = PATTERN_ROLES
        )
    )

    private fun term(
        id: String,
        titleSr: String,
        titleEn: String,
        explanation: String,
        categoryId: String = AI_ML_RAG
    ) = EncyclopediaTerm(
        id = id,
        categoryId = categoryId,
        titleSr = titleSr,
        titleEn = titleEn,
        shortExplanation = explanation
    )
}
