package com.example.pmuprojekat.data.seed.junior

internal object JuniorRefactoringSeed {
    val questions = listOf(
        JuniorSeedBuilders.refactoringQuestion(
            questionId = "J4.1",
            title = "Ukloni previše uslova za obračun popusta",
            prompt = "Sistem za popuste koristi jednu klasu sa velikim brojem if-else grana za različite vrste kupaca.",
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
                JuniorChoiceStepSeed("Korak 1 — problem", "Koji je glavni problem postojećeg dizajna?", listOf("prevelika povezanost i teško proširivanje", "nedostatak privatnog konstruktora", "previše observer-a", "nepotrebno čuvanje stanja"), listOf("prevelika povezanost i teško proširivanje")),
                JuniorChoiceStepSeed("Korak 2 — izbor obrasca", "Koji obrazac bi najviše unapredio rešenje?", listOf("Strategy", "Singleton", "Memento", "Composite"), listOf("Strategy")),
                JuniorChoiceStepSeed("Korak 3 — elementi unapređenog rešenja", "Izaberi elemente koji treba da postoje u boljem rešenju.", listOf("interfejs DiscountStrategy", "konkretne klase StudentDiscount, PremiumDiscount", "DiscountContext koji koristi strategiju", "jedna velika klasa sa još više if-else"), listOf("interfejs DiscountStrategy", "konkretne klase StudentDiscount, PremiumDiscount", "DiscountContext koji koristi strategiju"))
            ),
            aiFollowUp = "Kako Strategy menja način proširivanja sistema kada se pojavi nova vrsta popusta?",
            orderIndex = 401
        ),
        JuniorSeedBuilders.refactoringQuestion(
            questionId = "J4.2",
            title = "Više direktnih poziva pri promeni cene",
            prompt = "Kada se cena proizvoda promeni, klasa ProductService direktno poziva više drugih servisa.",
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
                JuniorChoiceStepSeed("Korak 1 — problem", "Koji je glavni problem?", listOf("jaka sprega između ProductService i više konkretnih servisa", "nedostaje Builder", "pogrešno korišćenje Iterator-a", "problem je samo naziv metode"), listOf("jaka sprega između ProductService i više konkretnih servisa")),
                JuniorChoiceStepSeed("Korak 2 — izbor obrasca", "Koji obrazac je najpogodniji?", listOf("Observer", "Proxy", "Template Method", "Bridge"), listOf("Observer")),
                JuniorChoiceStepSeed("Korak 3 — elementi unapređenog rešenja", "Izaberi potrebne elemente.", listOf("Observer interfejs", "lista pretplaćenih posmatrača", "notifyObservers()", "direktni poziv svake konkretne klase iz ProductService"), listOf("Observer interfejs", "lista pretplaćenih posmatrača", "notifyObservers()"))
            ),
            aiFollowUp = "Šta se dobija kada se doda novi servis koji želi da reaguje na promenu cene?",
            orderIndex = 402
        ),
        JuniorSeedBuilders.refactoringQuestion(
            questionId = "J4.3",
            title = "Previše parametara u konstruktoru",
            prompt = "Sistem koristi konstruktor sa velikim brojem parametara za pravljenje objekta Report.",
            codeBlock = """
                new Report(title, author, includeCharts, includeSummary, includeFooter, language, format, watermark)
            """,
            steps = listOf(
                JuniorChoiceStepSeed("Korak 1 — problem", "Koji je glavni problem?", listOf("objekat je složen za kreiranje i nepregledan za korišćenje", "nedostaje lanac handler-a", "problem je samo ime klase", "nedostaje Subject"), listOf("objekat je složen za kreiranje i nepregledan za korišćenje")),
                JuniorChoiceStepSeed("Korak 2 — izbor obrasca", "Koji obrazac je najpogodniji?", listOf("Builder", "Command", "State", "Flyweight"), listOf("Builder")),
                JuniorChoiceStepSeed("Korak 3 — elementi unapređenog rešenja", "Izaberi elemente koji treba da postoje.", listOf("ReportBuilder", "koraci poput setTitle(), setFormat()", "završna metoda build()", "više nasumičnih konstruktora bez strukture"), listOf("ReportBuilder", "koraci poput setTitle(), setFormat()", "završna metoda build()"))
            ),
            aiFollowUp = "Kako Builder utiče na čitljivost i održavanje koda kada objekat ima mnogo opcionalnih delova?",
            orderIndex = 403
        ),
        JuniorSeedBuilders.refactoringQuestion(
            questionId = "J4.4",
            title = "Spoljni servis sa pogrešnim interfejsom",
            prompt = "Tvoj sistem očekuje metodu send(message), ali nova biblioteka za slanje poruka ima metodu dispatch(text).",
            codeBlock = """
                class NotificationService {
                    private externalApi = new ExternalMessageApi()

                    sendNotification(message) {
                        externalApi.dispatch(message)
                    }
                }
            """,
            steps = listOf(
                JuniorChoiceStepSeed("Korak 1 — problem", "Koji je glavni problem prikazanog rešenja?", listOf("sistem je direktno vezan za konkretan spoljašnji interfejs", "postoji previše stanja", "objekat ima previše opcionalnih polja", "koristi se previše memorije"), listOf("sistem je direktno vezan za konkretan spoljašnji interfejs")),
                JuniorChoiceStepSeed("Korak 2 — izbor obrasca", "Koji obrazac je najpogodniji?", listOf("Adapter", "Visitor", "Memento", "Singleton"), listOf("Adapter")),
                JuniorChoiceStepSeed("Korak 3 — elementi unapređenog rešenja", "Izaberi 5 ponuđenih odgovora, od kojih su 3 tačna.", listOf("interfejs koji sistem očekuje, npr. MessageSender", "adapter klasa koja implementira očekivani interfejs", "referenca na spoljašnji API unutar adaptera", "direktno korišćenje ExternalMessageApi svuda u kodu", "velika if-else grana za sve moguće biblioteke"), listOf("interfejs koji sistem očekuje, npr. MessageSender", "adapter klasa koja implementira očekivani interfejs", "referenca na spoljašnji API unutar adaptera"))
            ),
            aiFollowUp = "Kako Adapter olakšava kasniju zamenu spoljnog servisa?",
            orderIndex = 404
        ),
        JuniorSeedBuilders.refactoringQuestion(
            questionId = "J4.5",
            title = "Dugmad direktno izvršavaju logiku",
            prompt = "U editoru svako dugme direktno sadrži logiku akcije koju izvršava.",
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
                JuniorChoiceStepSeed("Korak 1 — problem", "Koji je glavni problem?", listOf("UI elementi su previše vezani za konkretnu logiku izvršavanja", "postoji previše observer-a", "nedostaje jedna jedina instanca", "problem je što nema liste elemenata"), listOf("UI elementi su previše vezani za konkretnu logiku izvršavanja")),
                JuniorChoiceStepSeed("Korak 2 — izbor obrasca", "Koji obrazac je najpogodniji?", listOf("Command", "Composite", "Flyweight", "State"), listOf("Command")),
                JuniorChoiceStepSeed("Korak 3 — elementi unapređenog rešenja", "Izaberi 5 ponuđenih odgovora, od kojih su 3 tačna.", listOf("interfejs Command sa metodom execute()", "konkretne komande poput SaveCommand", "UI elementi koji samo pozivaju komandu", "dugmad koja i dalje sadrže kompletnu poslovnu logiku", "obavezna privatna konstrukcija svih komandi"), listOf("interfejs Command sa metodom execute()", "konkretne komande poput SaveCommand", "UI elementi koji samo pozivaju komandu"))
            ),
            aiFollowUp = "Kako ovaj pristup olakšava dodavanje undo funkcionalnosti ili redova akcija?",
            orderIndex = 405
        ),
        JuniorSeedBuilders.refactoringQuestion(
            questionId = "J4.6",
            title = "Previše grananja po statusu dokumenta",
            prompt = "Sistem za dokumente koristi veliku granajuću logiku u zavisnosti od statusa dokumenta.",
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
                JuniorChoiceStepSeed("Korak 1 — problem", "Koji je glavni problem?", listOf("ponašanje zavisi od stanja, ali je logika rasuta kroz mnogo uslova", "sistem je previše generički", "nema dovoljan broj konstruktora", "koristi se previše memorije"), listOf("ponašanje zavisi od stanja, ali je logika rasuta kroz mnogo uslova")),
                JuniorChoiceStepSeed("Korak 2 — izbor obrasca", "Koji obrazac je najpogodniji?", listOf("State", "Adapter", "Iterator", "Builder"), listOf("State")),
                JuniorChoiceStepSeed("Korak 3 — elementi unapređenog rešenja", "Izaberi 4 ponuđena odgovora, od kojih su 2 tačna.", listOf("interfejs ili apstraktna klasa stanja", "konkretna stanja poput DraftState i PublishedState", "lista observer-a za sva stanja", "kloniranje svakog stanja metodom clone()"), listOf("interfejs ili apstraktna klasa stanja", "konkretna stanja poput DraftState i PublishedState"))
            ),
            aiFollowUp = "Kako ovaj pristup pomaže kada se kasnije doda novo stanje dokumenta?",
            orderIndex = 406
        ),
        JuniorSeedBuilders.refactoringQuestion(
            questionId = "J4.7",
            title = "Poseban kod za list i grupu elemenata",
            prompt = "Sistem menija posebno obrađuje pojedinačne stavke i grupe stavki, pa klijent mora stalno da proverava sa čim radi.",
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
                JuniorChoiceStepSeed("Korak 1 — problem", "Koji je glavni problem?", listOf("klijent mora da razlikuje pojedinačne i grupne objekte", "postoji previše snapshot-a", "koristi se pogrešan tip baze podataka", "nedostaje privatni konstruktor"), listOf("klijent mora da razlikuje pojedinačne i grupne objekte")),
                JuniorChoiceStepSeed("Korak 2 — izbor obrasca", "Koji obrazac je najpogodniji?", listOf("Composite", "Prototype", "Memento", "Proxy"), listOf("Composite")),
                JuniorChoiceStepSeed("Korak 3 — elementi unapređenog rešenja", "Izaberi 4 ponuđena odgovora, od kojih su 2 tačna.", listOf("zajednički interfejs za list i grupu", "kompozit koji sadrži listu istog osnovnog tipa", "jedna centralna klasa sa ogromnim if-else grananjem", "poseban konstruktor za svaku dubinu stabla"), listOf("zajednički interfejs za list i grupu", "kompozit koji sadrži listu istog osnovnog tipa"))
            ),
            aiFollowUp = "Kako Composite utiče na proširivost kada kasnije dodaš novu vrstu elementa menija?",
            orderIndex = 407
        ),
        JuniorSeedBuilders.refactoringQuestion(
            questionId = "J4.8",
            title = "UI komponente su previše međusobno povezane",
            prompt = "U formi svako polje direktno menja stanje više drugih polja, pa postoji mnogo međusobnih poziva.",
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
                JuniorChoiceStepSeed("Korak 1 — problem", "Koji je glavni problem?", listOf("UI komponente su čvrsto vezane jedna za drugu", "sistem koristi previše memorije", "objekti nisu dovoljno veliki", "logika je previše generička"), listOf("UI komponente su čvrsto vezane jedna za drugu")),
                JuniorChoiceStepSeed("Korak 2 — izbor obrasca", "Koji obrazac je najpogodniji?", listOf("Mediator", "Observer", "Builder", "Flyweight"), listOf("Mediator")),
                JuniorChoiceStepSeed("Korak 3 — elementi unapređenog rešenja", "Izaberi 5 ponuđenih odgovora, od kojih su 3 tačna.", listOf("centralni objekat koji koordinira reakcije komponenti", "komponente koje prijavljuju događaje mediatoru", "logika interakcije premeštena iz pojedinačnih polja u mediator", "svaka komponenta mora direktno znati sve ostale komponente", "obavezno kloniranje svih UI elemenata"), listOf("centralni objekat koji koordinira reakcije komponenti", "komponente koje prijavljuju događaje mediatoru", "logika interakcije premeštena iz pojedinačnih polja u mediator"))
            ),
            aiFollowUp = "Kako bi broj direktnih zavisnosti rastao kada bi se forma dodatno proširivala bez Mediatora?",
            orderIndex = 408
        ),
        JuniorSeedBuilders.refactoringQuestion(
            questionId = "J4.9",
            title = "Dve dimenzije promena u jednoj hijerarhiji",
            prompt = "Sistem za notifikacije pokušava da podrži i više tipova poruka i više kanala slanja kroz nasleđivanje, pa nastaje previše klasa.",
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
                JuniorChoiceStepSeed("Korak 1 — problem", "Koji je glavni problem?", listOf("kombinovanje dve nezavisne dimenzije kroz nasleđivanje dovodi do eksplozije broja klasa", "nedostaje observer lista", "nema dovoljno konstruktora", "sistem koristi previše undo snapshot-a"), listOf("kombinovanje dve nezavisne dimenzije kroz nasleđivanje dovodi do eksplozije broja klasa")),
                JuniorChoiceStepSeed("Korak 2 — izbor obrasca", "Koji obrazac je najpogodniji?", listOf("Bridge", "Adapter", "Command", "Iterator"), listOf("Bridge")),
                JuniorChoiceStepSeed("Korak 3 — elementi unapređenog rešenja", "Izaberi 5 ponuđenih odgovora, od kojih su 3 tačna.", listOf("apstrakcija poruke koja sadrži referencu na implementaciju slanja", "interfejs za kanal slanja", "konkretne implementacije kanala kao Email, SMS, Push", "po jedna nova klasa za svaku kombinaciju tipa poruke i kanala", "privatni konstruktor za svaku poruku"), listOf("apstrakcija poruke koja sadrži referencu na implementaciju slanja", "interfejs za kanal slanja", "konkretne implementacije kanala kao Email, SMS, Push"))
            ),
            aiFollowUp = "Kako Bridge pomaže kada kasnije dodaš novi kanal ili novi tip poruke?",
            orderIndex = 409
        ),
        JuniorSeedBuilders.refactoringQuestion(
            questionId = "J4.10",
            title = "Nove operacije se stalno dodaju unutar svih klasa elemenata",
            prompt = "Sistem ima više tipova AST čvorova i za svaku novu operaciju menja se veliki broj postojećih klasa.",
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
                JuniorChoiceStepSeed("Korak 1 — problem", "Koji je glavni problem?", listOf("nove operacije zahtevaju izmene u svim klasama elemenata", "sistem koristi previše memorije zbog deljenog stanja", "postoji previše konstruktora", "interfejs je nekompatibilan sa spoljnim API-jem"), listOf("nove operacije zahtevaju izmene u svim klasama elemenata")),
                JuniorChoiceStepSeed("Korak 2 — izbor obrasca", "Koji obrazac je najpogodniji?", listOf("Visitor", "Proxy", "Builder", "State"), listOf("Visitor")),
                JuniorChoiceStepSeed("Korak 3 — elementi unapređenog rešenja", "Izaberi 5 ponuđenih odgovora, od kojih su 3 tačna.", listOf("interfejs ili apstrakcija visitor-a", "accept(visitor) metoda u elementima", "konkretni visitor-i za različite operacije", "velika klasa sa svim operacijama ugrađenim u svaku granu if-else", "privatni konstruktor za svaki čvor"), listOf("interfejs ili apstrakcija visitor-a", "accept(visitor) metoda u elementima", "konkretni visitor-i za različite operacije"))
            ),
            aiFollowUp = "U kom slučaju Visitor možda ne bi bio najbolji izbor?",
            orderIndex = 410
        ),
        JuniorSeedBuilders.refactoringQuestion(
            questionId = "J4.11",
            title = "Previše memorije za slične objekte",
            prompt = "Sistem prikazuje veliki broj projektila koji dele iste vizuelne podatke, ali ih svaki objekat čuva zasebno.",
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
                JuniorChoiceStepSeed("Korak 1 — problem", "Koji je glavni problem?", listOf("svaki mali objekat čuva ponovljene podatke koji mogu biti deljeni", "sistem nema dovoljno interfejsa", "nema podršku za undo", "objekat zavisi od previše UI komponenti"), listOf("svaki mali objekat čuva ponovljene podatke koji mogu biti deljeni")),
                JuniorChoiceStepSeed("Korak 2 — izbor obrasca", "Koji obrazac je najpogodniji?", listOf("Flyweight", "Memento", "Command", "Adapter"), listOf("Flyweight")),
                JuniorChoiceStepSeed("Korak 3 — elementi unapređenog rešenja", "Izaberi 4 ponuđena odgovora, od kojih su 2 tačna.", listOf("deljeni objekat za zajedničke vizuelne podatke", "spoljašnje stanje kao pozicija i brzina van flyweight jezgra", "posebna klasa stanja za draft i published režim", "obavezna lista observer-a za svaki projektil"), listOf("deljeni objekat za zajedničke vizuelne podatke", "spoljašnje stanje kao pozicija i brzina van flyweight jezgra"))
            ),
            aiFollowUp = "Koji su rizici ako se pogrešno pomešaju deljeno i spoljašnje stanje?",
            orderIndex = 411
        ),
        JuniorSeedBuilders.refactoringQuestion(
            questionId = "J4.12",
            title = "Undo se implementira ručno i nepregledno",
            prompt = "Editor ručno čuva mnogo odvojenih promenljivih za vraćanje stanja.",
            codeBlock = """
                class EditorHistory {
                    oldText
                    oldCursor
                    oldSelection
                }
            """,
            steps = listOf(
                JuniorChoiceStepSeed("Korak 1 — problem", "Koji je glavni problem?", listOf("istorija stanja je rasuta i slabo enkapsulirana", "sistem ima previše kanala slanja poruka", "postoji eksplozija broja klasa zbog nasleđivanja", "koristi se previše adaptera"), listOf("istorija stanja je rasuta i slabo enkapsulirana")),
                JuniorChoiceStepSeed("Korak 2 — izbor obrasca", "Koji obrazac je najpogodniji?", listOf("Memento", "Bridge", "Composite", "Decorator"), listOf("Memento")),
                JuniorChoiceStepSeed("Korak 3 — elementi unapređenog rešenja", "Izaberi 5 ponuđenih odgovora, od kojih su 3 tačna.", listOf("originator koji pravi snapshot svog stanja", "memento objekat koji čuva stanje", "caretaker koji skladišti istoriju snapshot-a", "UI dugme koje direktno menja sva interna polja editora", "jedna globalna statička promenljiva za staro stanje"), listOf("originator koji pravi snapshot svog stanja", "memento objekat koji čuva stanje", "caretaker koji skladišti istoriju snapshot-a"))
            ),
            aiFollowUp = "Zašto je važno da snapshot bude vezan za sam objekat koji zna svoje stanje?",
            orderIndex = 412
        )
    )
}
