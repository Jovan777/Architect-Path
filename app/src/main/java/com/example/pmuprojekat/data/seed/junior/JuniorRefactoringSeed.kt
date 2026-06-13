package com.example.pmuprojekat.data.seed.junior

internal object JuniorRefactoringSeed {
    val questions = listOf(
        JuniorSeedBuilders.refactoringQuestion(
            questionId = "J3.1",
            title = "Ukloni previÅ¡e uslova za obraÄun popusta",
            prompt = "Sistem za popuste koristi jednu klasu sa velikim brojem if-else grana za razliÄite vrste kupaca.",
            codeBlock = """
                class DiscountService {
                    calculate(type, price) {
                        if (type == "student") return price * 0.9
                        if (type == "premium") return price * 0.8
                        if (type == "guest") return price
                    }
                }
            """,
            steps = listOf(
                JuniorChoiceStepSeed("Korak 1 â€” problem", "Koji je glavni problem postojeÄ‡eg dizajna?", listOf("prevelika povezanost i teÅ¡ko proÅ¡irivanje", "nedostatak privatnog konstruktora", "previÅ¡e observer-a", "nepotrebno Äuvanje stanja"), listOf("prevelika povezanost i teÅ¡ko proÅ¡irivanje")),
                JuniorChoiceStepSeed("Korak 2 â€” izbor obrasca", "Koji obrazac bi najviÅ¡e unapredio reÅ¡enje?", listOf("Strategy", "Singleton", "Memento", "Composite"), listOf("Strategy")),
                JuniorChoiceStepSeed("Korak 3 â€” elementi unapreÄ‘enog reÅ¡enja", "Izaberi elemente koji treba da postoje u boljem reÅ¡enju.", listOf("interfejs DiscountStrategy", "konkretne klase StudentDiscount, PremiumDiscount", "DiscountContext koji koristi strategiju", "jedna velika klasa sa joÅ¡ viÅ¡e if-else"), listOf("interfejs DiscountStrategy", "konkretne klase StudentDiscount, PremiumDiscount", "DiscountContext koji koristi strategiju"))
            ),
            aiFollowUp = "Kako Strategy menja naÄin proÅ¡irivanja sistema kada se pojavi nova vrsta popusta?",
            orderIndex = 401
        ),
        JuniorSeedBuilders.refactoringQuestion(
            questionId = "J3.2",
            title = "ViÅ¡e direktnih poziva pri promeni cene",
            prompt = "Kada se cena proizvoda promeni, klasa ProductService direktno poziva viÅ¡e drugih servisa.",
            codeBlock = """
                class ProductService {
                    updatePrice(product, newPrice) {
                        product.price = newPrice
                        emailService.sendPriceChange(product)
                        mobileApp.refresh(product)
                        analyticsService.track(product)
                    }
                }
            """,
            steps = listOf(
                JuniorChoiceStepSeed("Korak 1 â€” problem", "Koji je glavni problem?", listOf("jaka sprega izmeÄ‘u ProductService i viÅ¡e konkretnih servisa", "nedostaje Builder", "pogreÅ¡no koriÅ¡Ä‡enje Iterator-a", "problem je samo naziv metode"), listOf("jaka sprega izmeÄ‘u ProductService i viÅ¡e konkretnih servisa")),
                JuniorChoiceStepSeed("Korak 2 â€” izbor obrasca", "Koji obrazac je najpogodniji?", listOf("Observer", "Proxy", "Template Method", "Bridge"), listOf("Observer")),
                JuniorChoiceStepSeed("Korak 3 â€” elementi unapreÄ‘enog reÅ¡enja", "Izaberi potrebne elemente.", listOf("Observer interfejs", "lista pretplaÄ‡enih posmatraÄa", "notifyObservers()", "direktni poziv svake konkretne klase iz ProductService"), listOf("Observer interfejs", "lista pretplaÄ‡enih posmatraÄa", "notifyObservers()"))
            ),
            aiFollowUp = "Å ta se dobija kada se doda novi servis koji Å¾eli da reaguje na promenu cene?",
            orderIndex = 402
        ),
        JuniorSeedBuilders.refactoringQuestion(
            questionId = "J3.3",
            title = "PreviÅ¡e parametara u konstruktoru",
            prompt = "Sistem koristi konstruktor sa velikim brojem parametara za pravljenje objekta Report.",
            codeBlock = """
                new Report(title, author, includeCharts, includeSummary, includeFooter, language, format, watermark)
            """,
            steps = listOf(
                JuniorChoiceStepSeed("Korak 1 â€” problem", "Koji je glavni problem?", listOf("objekat je sloÅ¾en za kreiranje i nepregledan za koriÅ¡Ä‡enje", "nedostaje lanac handler-a", "problem je samo ime klase", "nedostaje Subject"), listOf("objekat je sloÅ¾en za kreiranje i nepregledan za koriÅ¡Ä‡enje")),
                JuniorChoiceStepSeed("Korak 2 â€” izbor obrasca", "Koji obrazac je najpogodniji?", listOf("Builder", "Command", "State", "Flyweight"), listOf("Builder")),
                JuniorChoiceStepSeed("Korak 3 â€” elementi unapreÄ‘enog reÅ¡enja", "Izaberi elemente koji treba da postoje.", listOf("ReportBuilder", "koraci poput setTitle(), setFormat()", "zavrÅ¡na metoda build()", "viÅ¡e nasumiÄnih konstruktora bez strukture"), listOf("ReportBuilder", "koraci poput setTitle(), setFormat()", "zavrÅ¡na metoda build()"))
            ),
            aiFollowUp = "Kako Builder utiÄe na Äitljivost i odrÅ¾avanje koda kada objekat ima mnogo opcionalnih delova?",
            orderIndex = 403
        ),
        JuniorSeedBuilders.refactoringQuestion(
            questionId = "J3.4",
            title = "Spoljni servis sa pogreÅ¡nim interfejsom",
            prompt = "Tvoj sistem oÄekuje metodu send(message), ali nova biblioteka za slanje poruka ima metodu dispatch(text).",
            codeBlock = """
                class NotificationService {
                    private externalApi = new ExternalMessageApi()

                    sendNotification(message) {
                        externalApi.dispatch(message)
                    }
                }
            """,
            steps = listOf(
                JuniorChoiceStepSeed("Korak 1 â€” problem", "Koji je glavni problem prikazanog reÅ¡enja?", listOf("sistem je direktno vezan za konkretan spoljaÅ¡nji interfejs", "postoji previÅ¡e stanja", "objekat ima previÅ¡e opcionalnih polja", "koristi se previÅ¡e memorije"), listOf("sistem je direktno vezan za konkretan spoljaÅ¡nji interfejs")),
                JuniorChoiceStepSeed("Korak 2 â€” izbor obrasca", "Koji obrazac je najpogodniji?", listOf("Adapter", "Visitor", "Memento", "Singleton"), listOf("Adapter")),
                JuniorChoiceStepSeed("Korak 3 â€” elementi unapreÄ‘enog reÅ¡enja", "Izaberi 5 ponuÄ‘enih odgovora, od kojih su 3 taÄna.", listOf("interfejs koji sistem oÄekuje, npr. MessageSender", "adapter klasa koja implementira oÄekivani interfejs", "referenca na spoljaÅ¡nji API unutar adaptera", "direktno koriÅ¡Ä‡enje ExternalMessageApi svuda u kodu", "velika if-else grana za sve moguÄ‡e biblioteke"), listOf("interfejs koji sistem oÄekuje, npr. MessageSender", "adapter klasa koja implementira oÄekivani interfejs", "referenca na spoljaÅ¡nji API unutar adaptera"))
            ),
            aiFollowUp = "Kako Adapter olakÅ¡ava kasniju zamenu spoljnog servisa?",
            orderIndex = 404
        ),
        JuniorSeedBuilders.refactoringQuestion(
            questionId = "J3.5",
            title = "Dugmad direktno izvrÅ¡avaju logiku",
            prompt = "U editoru svako dugme direktno sadrÅ¾i logiku akcije koju izvrÅ¡ava.",
            codeBlock = """
                class SaveButton {
                    click() {
                        editor.validate()
                        editor.save()
                    }
                }

                class PrintButton {
                    click() {
                        printer.prepare()
                        printer.print()
                    }
                }
            """,
            steps = listOf(
                JuniorChoiceStepSeed("Korak 1 â€” problem", "Koji je glavni problem?", listOf("UI elementi su previÅ¡e vezani za konkretnu logiku izvrÅ¡avanja", "postoji previÅ¡e observer-a", "nedostaje jedna jedina instanca", "problem je Å¡to nema liste elemenata"), listOf("UI elementi su previÅ¡e vezani za konkretnu logiku izvrÅ¡avanja")),
                JuniorChoiceStepSeed("Korak 2 â€” izbor obrasca", "Koji obrazac je najpogodniji?", listOf("Command", "Composite", "Flyweight", "State"), listOf("Command")),
                JuniorChoiceStepSeed("Korak 3 â€” elementi unapreÄ‘enog reÅ¡enja", "Izaberi 5 ponuÄ‘enih odgovora, od kojih su 3 taÄna.", listOf("interfejs Command sa metodom execute()", "konkretne komande poput SaveCommand", "UI elementi koji samo pozivaju komandu", "dugmad koja i dalje sadrÅ¾e kompletnu poslovnu logiku", "obavezna privatna konstrukcija svih komandi"), listOf("interfejs Command sa metodom execute()", "konkretne komande poput SaveCommand", "UI elementi koji samo pozivaju komandu"))
            ),
            aiFollowUp = "Kako ovaj pristup olakÅ¡ava dodavanje undo funkcionalnosti ili redova akcija?",
            orderIndex = 405
        ),
        JuniorSeedBuilders.refactoringQuestion(
            questionId = "J3.6",
            title = "PreviÅ¡e grananja po statusu dokumenta",
            prompt = "Sistem za dokumente koristi veliku granajuÄ‡u logiku u zavisnosti od statusa dokumenta.",
            codeBlock = """
                class Document {
                    status

                    publish() {
                        if (status == "draft") { ... }
                        else if (status == "review") { ... }
                        else if (status == "published") { ... }
                    }

                    edit() {
                        if (status == "draft") { ... }
                        else if (status == "review") { ... }
                        else if (status == "published") { ... }
                    }
                }
            """,
            steps = listOf(
                JuniorChoiceStepSeed("Korak 1 â€” problem", "Koji je glavni problem?", listOf("ponaÅ¡anje zavisi od stanja, ali je logika rasuta kroz mnogo uslova", "sistem je previÅ¡e generiÄki", "nema dovoljan broj konstruktora", "koristi se previÅ¡e memorije"), listOf("ponaÅ¡anje zavisi od stanja, ali je logika rasuta kroz mnogo uslova")),
                JuniorChoiceStepSeed("Korak 2 â€” izbor obrasca", "Koji obrazac je najpogodniji?", listOf("State", "Adapter", "Iterator", "Builder"), listOf("State")),
                JuniorChoiceStepSeed("Korak 3 â€” elementi unapreÄ‘enog reÅ¡enja", "Izaberi 4 ponuÄ‘ena odgovora, od kojih su 2 taÄna.", listOf("interfejs ili apstraktna klasa stanja", "konkretna stanja poput DraftState i PublishedState", "lista observer-a za sva stanja", "kloniranje svakog stanja metodom clone()"), listOf("interfejs ili apstraktna klasa stanja", "konkretna stanja poput DraftState i PublishedState"))
            ),
            aiFollowUp = "Kako ovaj pristup pomaÅ¾e kada se kasnije doda novo stanje dokumenta?",
            orderIndex = 406
        ),
        JuniorSeedBuilders.refactoringQuestion(
            questionId = "J3.7",
            title = "Poseban kod za list i grupu elemenata",
            prompt = "Sistem menija posebno obraÄ‘uje pojedinaÄne stavke i grupe stavki, pa klijent mora stalno da proverava sa Äim radi.",
            codeBlock = """
                class MenuRenderer {
                    render(item) {
                        if (item is MenuItem) {
                            item.render()
                        } else if (item is List<MenuItem>) {
                            for each i in item {
                                i.render()
                            }
                        }
                    }
                }
            """,
            steps = listOf(
                JuniorChoiceStepSeed("Korak 1 â€” problem", "Koji je glavni problem?", listOf("klijent mora da razlikuje pojedinaÄne i grupne objekte", "postoji previÅ¡e snapshot-a", "koristi se pogreÅ¡an tip baze podataka", "nedostaje privatni konstruktor"), listOf("klijent mora da razlikuje pojedinaÄne i grupne objekte")),
                JuniorChoiceStepSeed("Korak 2 â€” izbor obrasca", "Koji obrazac je najpogodniji?", listOf("Composite", "Prototype", "Memento", "Proxy"), listOf("Composite")),
                JuniorChoiceStepSeed("Korak 3 â€” elementi unapreÄ‘enog reÅ¡enja", "Izaberi 4 ponuÄ‘ena odgovora, od kojih su 2 taÄna.", listOf("zajedniÄki interfejs za list i grupu", "kompozit koji sadrÅ¾i listu istog osnovnog tipa", "jedna centralna klasa sa ogromnim if-else grananjem", "poseban konstruktor za svaku dubinu stabla"), listOf("zajedniÄki interfejs za list i grupu", "kompozit koji sadrÅ¾i listu istog osnovnog tipa"))
            ),
            aiFollowUp = "Kako Composite utiÄe na proÅ¡irivost kada kasnije dodaÅ¡ novu vrstu elementa menija?",
            orderIndex = 407
        ),
        JuniorSeedBuilders.refactoringQuestion(
            questionId = "J3.8",
            title = "UI komponente su previÅ¡e meÄ‘usobno povezane",
            prompt = "U formi svako polje direktno menja stanje viÅ¡e drugih polja, pa postoji mnogo meÄ‘usobnih poziva.",
            codeBlock = """
                class NameField {
                    onChange() {
                        submitButton.enable()
                        discountCheckbox.disable()
                        addressField.highlight()
                    }
                }

                class DiscountCheckbox {
                    onToggle() {
                        submitButton.disable()
                        nameField.clearWarning()
                    }
                }
            """,
            steps = listOf(
                JuniorChoiceStepSeed("Korak 1 â€” problem", "Koji je glavni problem?", listOf("UI komponente su Ävrsto vezane jedna za drugu", "sistem koristi previÅ¡e memorije", "objekti nisu dovoljno veliki", "logika je previÅ¡e generiÄka"), listOf("UI komponente su Ävrsto vezane jedna za drugu")),
                JuniorChoiceStepSeed("Korak 2 â€” izbor obrasca", "Koji obrazac je najpogodniji?", listOf("Mediator", "Observer", "Builder", "Flyweight"), listOf("Mediator")),
                JuniorChoiceStepSeed("Korak 3 â€” elementi unapreÄ‘enog reÅ¡enja", "Izaberi 5 ponuÄ‘enih odgovora, od kojih su 3 taÄna.", listOf("centralni objekat koji koordinira reakcije komponenti", "komponente koje prijavljuju dogaÄ‘aje mediatoru", "logika interakcije premeÅ¡tena iz pojedinaÄnih polja u mediator", "svaka komponenta mora direktno znati sve ostale komponente", "obavezno kloniranje svih UI elemenata"), listOf("centralni objekat koji koordinira reakcije komponenti", "komponente koje prijavljuju dogaÄ‘aje mediatoru", "logika interakcije premeÅ¡tena iz pojedinaÄnih polja u mediator"))
            ),
            aiFollowUp = "Kako bi broj direktnih zavisnosti rastao kada bi se forma dodatno proÅ¡irivala bez Mediatora?",
            orderIndex = 408
        ),
        JuniorSeedBuilders.refactoringQuestion(
            questionId = "J3.9",
            title = "Dve dimenzije promena u jednoj hijerarhiji",
            prompt = "Sistem za notifikacije pokuÅ¡ava da podrÅ¾i i viÅ¡e tipova poruka i viÅ¡e kanala slanja kroz nasleÄ‘ivanje, pa nastaje previÅ¡e klasa.",
            codeBlock = """
                EmailAlert
                SmsAlert
                PushAlert
                EmailWarning
                SmsWarning
                PushWarning
                EmailReminder
                SmsReminder
                PushReminder
            """,
            steps = listOf(
                JuniorChoiceStepSeed("Korak 1 â€” problem", "Koji je glavni problem?", listOf("kombinovanje dve nezavisne dimenzije kroz nasleÄ‘ivanje dovodi do eksplozije broja klasa", "nedostaje observer lista", "nema dovoljno konstruktora", "sistem koristi previÅ¡e undo snapshot-a"), listOf("kombinovanje dve nezavisne dimenzije kroz nasleÄ‘ivanje dovodi do eksplozije broja klasa")),
                JuniorChoiceStepSeed("Korak 2 â€” izbor obrasca", "Koji obrazac je najpogodniji?", listOf("Bridge", "Adapter", "Command", "Iterator"), listOf("Bridge")),
                JuniorChoiceStepSeed("Korak 3 â€” elementi unapreÄ‘enog reÅ¡enja", "Izaberi 5 ponuÄ‘enih odgovora, od kojih su 3 taÄna.", listOf("apstrakcija poruke koja sadrÅ¾i referencu na implementaciju slanja", "interfejs za kanal slanja", "konkretne implementacije kanala kao Email, SMS, Push", "po jedna nova klasa za svaku kombinaciju tipa poruke i kanala", "privatni konstruktor za svaku poruku"), listOf("apstrakcija poruke koja sadrÅ¾i referencu na implementaciju slanja", "interfejs za kanal slanja", "konkretne implementacije kanala kao Email, SMS, Push"))
            ),
            aiFollowUp = "Kako Bridge pomaÅ¾e kada kasnije dodaÅ¡ novi kanal ili novi tip poruke?",
            orderIndex = 409
        ),
        JuniorSeedBuilders.refactoringQuestion(
            questionId = "J3.10",
            title = "Nove operacije se stalno dodaju unutar svih klasa elemenata",
            prompt = "Sistem ima viÅ¡e tipova AST Ävorova i za svaku novu operaciju menja se veliki broj postojeÄ‡ih klasa.",
            codeBlock = """
                class LiteralNode {
                    print()
                    validate()
                    optimize()
                }

                class AddNode {
                    print()
                    validate()
                    optimize()
                }

                class MultiplyNode {
                    print()
                    validate()
                    optimize()
                }
            """,
            steps = listOf(
                JuniorChoiceStepSeed("Korak 1 â€” problem", "Koji je glavni problem?", listOf("nove operacije zahtevaju izmene u svim klasama elemenata", "sistem koristi previÅ¡e memorije zbog deljenog stanja", "postoji previÅ¡e konstruktora", "interfejs je nekompatibilan sa spoljnim API-jem"), listOf("nove operacije zahtevaju izmene u svim klasama elemenata")),
                JuniorChoiceStepSeed("Korak 2 â€” izbor obrasca", "Koji obrazac je najpogodniji?", listOf("Visitor", "Proxy", "Builder", "State"), listOf("Visitor")),
                JuniorChoiceStepSeed("Korak 3 â€” elementi unapreÄ‘enog reÅ¡enja", "Izaberi 5 ponuÄ‘enih odgovora, od kojih su 3 taÄna.", listOf("interfejs ili apstrakcija visitor-a", "accept(visitor) metoda u elementima", "konkretni visitor-i za razliÄite operacije", "velika klasa sa svim operacijama ugraÄ‘enim u svaku granu if-else", "privatni konstruktor za svaki Ävor"), listOf("interfejs ili apstrakcija visitor-a", "accept(visitor) metoda u elementima", "konkretni visitor-i za razliÄite operacije"))
            ),
            aiFollowUp = "U kom sluÄaju Visitor moÅ¾da ne bi bio najbolji izbor?",
            orderIndex = 410
        ),
        JuniorSeedBuilders.refactoringQuestion(
            questionId = "J3.11",
            title = "PreviÅ¡e memorije za sliÄne objekte",
            prompt = "Sistem prikazuje veliki broj projektila koji dele iste vizuelne podatke, ali ih svaki objekat Äuva zasebno.",
            codeBlock = """
                class Bullet {
                    sprite
                    color
                    animation
                    x
                    y
                    velocity
                }
            """,
            steps = listOf(
                JuniorChoiceStepSeed("Korak 1 â€” problem", "Koji je glavni problem?", listOf("svaki mali objekat Äuva ponovljene podatke koji mogu biti deljeni", "sistem nema dovoljno interfejsa", "nema podrÅ¡ku za undo", "objekat zavisi od previÅ¡e UI komponenti"), listOf("svaki mali objekat Äuva ponovljene podatke koji mogu biti deljeni")),
                JuniorChoiceStepSeed("Korak 2 â€” izbor obrasca", "Koji obrazac je najpogodniji?", listOf("Flyweight", "Memento", "Command", "Adapter"), listOf("Flyweight")),
                JuniorChoiceStepSeed("Korak 3 â€” elementi unapreÄ‘enog reÅ¡enja", "Izaberi 4 ponuÄ‘ena odgovora, od kojih su 2 taÄna.", listOf("deljeni objekat za zajedniÄke vizuelne podatke", "spoljaÅ¡nje stanje kao pozicija i brzina van flyweight jezgra", "posebna klasa stanja za draft i published reÅ¾im", "obavezna lista observer-a za svaki projektil"), listOf("deljeni objekat za zajedniÄke vizuelne podatke", "spoljaÅ¡nje stanje kao pozicija i brzina van flyweight jezgra"))
            ),
            aiFollowUp = "Koji su rizici ako se pogreÅ¡no pomeÅ¡aju deljeno i spoljaÅ¡nje stanje?",
            orderIndex = 411
        ),
        JuniorSeedBuilders.refactoringQuestion(
            questionId = "J3.12",
            title = "Undo se implementira ruÄno i nepregledno",
            prompt = "Editor ruÄno Äuva mnogo odvojenih promenljivih za vraÄ‡anje stanja.",
            codeBlock = """
                class EditorHistory {
                    oldText
                    oldCursor
                    oldSelection
                }
            """,
            steps = listOf(
                JuniorChoiceStepSeed("Korak 1 â€” problem", "Koji je glavni problem?", listOf("istorija stanja je rasuta i slabo enkapsulirana", "sistem ima previÅ¡e kanala slanja poruka", "postoji eksplozija broja klasa zbog nasleÄ‘ivanja", "koristi se previÅ¡e adaptera"), listOf("istorija stanja je rasuta i slabo enkapsulirana")),
                JuniorChoiceStepSeed("Korak 2 â€” izbor obrasca", "Koji obrazac je najpogodniji?", listOf("Memento", "Bridge", "Composite", "Decorator"), listOf("Memento")),
                JuniorChoiceStepSeed("Korak 3 â€” elementi unapreÄ‘enog reÅ¡enja", "Izaberi 5 ponuÄ‘enih odgovora, od kojih su 3 taÄna.", listOf("originator koji pravi snapshot svog stanja", "memento objekat koji Äuva stanje", "caretaker koji skladiÅ¡ti istoriju snapshot-a", "UI dugme koje direktno menja sva interna polja editora", "jedna globalna statiÄka promenljiva za staro stanje"), listOf("originator koji pravi snapshot svog stanja", "memento objekat koji Äuva stanje", "caretaker koji skladiÅ¡ti istoriju snapshot-a"))
            ),
            aiFollowUp = "ZaÅ¡to je vaÅ¾no da snapshot bude vezan za sam objekat koji zna svoje stanje?",
            orderIndex = 412
        )
    )
}

