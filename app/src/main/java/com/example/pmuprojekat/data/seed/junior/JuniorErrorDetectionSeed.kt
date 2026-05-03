package com.example.pmuprojekat.data.seed.junior

internal object JuniorErrorDetectionSeed {
    val questions = listOf(
        JuniorSeedBuilders.errorDetectionQuestion(
            questionId = "J5.1",
            title = "Pogrešna primena Singleton obrasca",
            prompt = "Analiziraj pseudo-kod i označi problematičan deo.",
            codeBlock = """
                class Logger {
                    public constructor() {}

                    static getInstance() {
                        if (instance == null) {
                            instance = new Logger()
                        }
                        return instance
                    }
                }
            """,
            options = listOf("public constructor() {}", "static getInstance()", "if (instance == null)", "return instance"),
            correctAnswers = listOf("public constructor() {}"),
            explanation = "Ako je konstruktor javan, bilo ko može praviti nove instance i time se ruši suština Singleton obrasca.",
            aiFollowUp = "Kako bi ispravio ovu klasu da zaista garantuje jednu instancu?",
            orderIndex = 501
        ),
        JuniorSeedBuilders.errorDetectionQuestion(
            questionId = "J5.2",
            title = "Pogrešna primena Observer obrasca",
            prompt = "Uoči problem u sledećem rešenju.",
            codeBlock = """
                class Product {
                    private emailNotifier = new EmailNotifier()
                    private smsNotifier = new SmsNotifier()

                    setPrice(price) {
                        this.price = price
                        emailNotifier.update(price)
                        smsNotifier.update(price)
                    }
                }
            """,
            options = listOf("Direktno vezivanje Product klase za konkretne notifikatore", "Postojanje setPrice metode", "Promena vrednosti price", "Pozivanje update metode na observer-u"),
            correctAnswers = listOf("Direktno vezivanje Product klase za konkretne notifikatore"),
            explanation = "Klasa Product ne radi preko apstrakcije i nema listu observer-a, pa je čvrsto vezana za konkretne implementacije.",
            aiFollowUp = "Kako bi ovo rešenje izmenio da novi observer može da se doda bez menjanja Product klase?",
            orderIndex = 502
        ),
        JuniorSeedBuilders.errorDetectionQuestion(
            questionId = "J5.3",
            title = "Pogrešna primena Strategy obrasca",
            prompt = "Pronađi grešku u sledećem pokušaju primene Strategy obrasca.",
            codeBlock = """
                class PaymentContext {
                    pay(type, amount) {
                        if (type == "card") {
                            // card payment logic
                        } else if (type == "paypal") {
                            // paypal logic
                        } else if (type == "crypto") {
                            // crypto logic
                        }
                    }
                }
            """,
            options = listOf("Sve strategije su i dalje ugrađene u jednu klasu kroz if-else", "Klasa se zove PaymentContext", "Metoda prima amount", "Postoji više načina plaćanja"),
            correctAnswers = listOf("Sve strategije su i dalje ugrađene u jednu klasu kroz if-else"),
            explanation = "Iako naziv sugeriše Strategy, algoritmi nisu izdvojeni u posebne klase sa zajedničkim interfejsom.",
            aiFollowUp = "Kako bi izgledalo pravilnije rešenje sa Strategy obrascem u ovom slučaju?",
            orderIndex = 503
        ),
        JuniorSeedBuilders.errorDetectionQuestion(
            questionId = "J5.4",
            title = "Pogrešna primena Command obrasca",
            prompt = "Analiziraj pseudo-kod i pronađi problem.",
            codeBlock = """
                class ToolbarButton {
                    click(actionType) {
                        if (actionType == "copy") {
                            editor.copy()
                        } else if (actionType == "paste") {
                            editor.paste()
                        } else if (actionType == "cut") {
                            editor.cut()
                        }
                    }
                }
            """,
            options = listOf("Dugme i dalje sadrži granajuću logiku svih akcija umesto da koristi komande", "Metoda se zove click", "Editor ima metode copy i paste", "Parametar actionType je tekstualan"),
            correctAnswers = listOf("Dugme i dalje sadrži granajuću logiku svih akcija umesto da koristi komande"),
            explanation = "Iako problem liči na Command, akcije nisu izdvojene u objekte, pa je UI i dalje čvrsto vezan za izvršenje.",
            aiFollowUp = "Kako bi izgledalo pravilnije rešenje u kome je svaka akcija izdvojena u poseban objekat?",
            orderIndex = 504
        ),
        JuniorSeedBuilders.errorDetectionQuestion(
            questionId = "J5.5",
            title = "Pogrešna primena Builder obrasca",
            prompt = "Uoči problem u pokušaju primene Builder obrasca.",
            codeBlock = """
                class ReportBuilder {
                    build(title, author, footer, chart, summary, lang, format, watermark) {
                        return new Report(title, author, footer, chart, summary, lang, format, watermark)
                    }
                }
            """,
            options = listOf("Builder ne gradi objekat postepeno, već samo skriva isti veliki konstruktor iza druge metode", "Klasa se zove ReportBuilder", "Metoda vraća Report", "Postoji format parametar"),
            correctAnswers = listOf("Builder ne gradi objekat postepeno, već samo skriva isti veliki konstruktor iza druge metode"),
            explanation = "Suština Builder obrasca nije samo preimenovanje konstruktora, već postepena i čitljiva izgradnja objekta.",
            aiFollowUp = "Šta bi trebalo da postoji u pravom Builder rešenju da bi imalo smisla?",
            orderIndex = 505
        ),
        JuniorSeedBuilders.errorDetectionQuestion(
            questionId = "J5.6",
            title = "Pogrešna primena Adapter obrasca",
            prompt = "Pronađi grešku u sledećem rešenju.",
            codeBlock = """
                class PaymentAdapter {
                    private stripeApi = new StripeApi()
                    private paypalApi = new PaypalApi()
                    private bankApi = new BankApi()

                    pay(type, amount) {
                        if (type == "stripe") stripeApi.charge(amount)
                        else if (type == "paypal") paypalApi.pay(amount)
                        else if (type == "bank") bankApi.transfer(amount)
                    }
                }
            """,
            options = listOf("Jedna klasa glumi adapter za sve moguće sisteme kroz if-else grananje", "Adapter ima metodu pay", "Postoji StripeApi", "Korišćen je amount parametar"),
            correctAnswers = listOf("Jedna klasa glumi adapter za sve moguće sisteme kroz if-else grananje"),
            explanation = "Ovo više liči na centralizovanu proceduralnu logiku nego na čist Adapter pristup sa jasnim prevodom jednog konkretnog interfejsa.",
            aiFollowUp = "Kako bi izgledalo čistije rešenje ako bi svaki spoljašnji servis imao svoj adapter?",
            orderIndex = 506
        ),
        JuniorSeedBuilders.errorDetectionQuestion(
            questionId = "J5.10",
            title = "Pogrešna primena Bridge obrasca",
            prompt = "Pronađi konkretnu liniju ili linije koje ruše pravilnu primenu Bridge obrasca.",
            codeBlock = """
                1  interface Sender {
                2      send(text)
                3  }
                4
                5  class EmailSender implements Sender {
                6      send(text) { ... }
                7  }
                8
                9  class SmsSender implements Sender {
                10     send(text) { ... }
                11 }
                12
                13 class AlertMessage {
                14     sendByEmail(text) { ... }
                15     sendBySms(text) { ... }
                16 }
            """,
            options = listOf("Linija 1", "Linija 2", "Linija 5", "Linija 9", "Linija 14", "Linija 15"),
            correctAnswers = listOf("Linija 14", "Linija 15"),
            explanation = "AlertMessage direktno ugrađuje konkretne kanale slanja umesto da poseduje referencu na apstrakciju Sender. Time se ruši razdvajanje apstrakcije i implementacije.",
            aiFollowUp = "Kako bi AlertMessage trebalo da izgleda da bi pravilno koristio Bridge?",
            orderIndex = 510
        ),
        JuniorSeedBuilders.errorDetectionQuestion(
            questionId = "J5.11",
            title = "Pogrešna primena Visitor obrasca",
            prompt = "Obeleži liniju ili linije koje prave problem.",
            codeBlock = """
                1  interface Shape {
                2      area()
                3      exportJson()
                4      exportXml()
                5      validate()
                6  }
                7
                8  class Circle implements Shape {
                9      area() { ... }
                10     exportJson() { ... }
                11     exportXml() { ... }
                12     validate() { ... }
                13 }
            """,
            options = listOf("Linija 2", "Linija 3", "Linija 4", "Linija 5", "Linija 8", "Linija 9"),
            correctAnswers = listOf("Linija 3", "Linija 4", "Linija 5"),
            explanation = "Operacije koje bi mogle biti izdvojene u visitor-e ugrađene su u sam interfejs elemenata. To vodi širenju svih klasa elemenata pri dodavanju novih operacija.",
            aiFollowUp = "Koje bi operacije ovde bile dobar kandidat da se izdvoje u visitor klase?",
            orderIndex = 511
        ),
        JuniorSeedBuilders.errorDetectionQuestion(
            questionId = "J5.12",
            title = "Pogrešna primena Memento obrasca",
            prompt = "Obeleži tačnu liniju ili linije koje ruše dobru primenu obrasca.",
            codeBlock = """
                1  class EditorMemento {
                2      public text
                3      public cursorPosition
                4  }
                5
                6  class HistoryManager {
                7      editSnapshot(snapshot: EditorMemento) {
                8          snapshot.text = "changed externally"
                9      }
                10 }
            """,
            options = listOf("Linija 1", "Linija 2", "Linija 3", "Linija 6", "Linija 7", "Linija 8"),
            correctAnswers = listOf("Linija 2", "Linija 3", "Linija 8"),
            explanation = "Memento izlaže unutrašnje stanje spolja i dopušta caretaker-u da ga menja. Time se gubi enkapsulacija koja je suština Memento obrasca.",
            aiFollowUp = "Kako bi zaštitio stanje unutar mementa da spoljne klase ne mogu da ga menjaju?",
            orderIndex = 512
        ),
        JuniorSeedBuilders.errorDetectionQuestion(
            questionId = "J5.13",
            title = "Pogrešna primena Composite obrasca",
            prompt = "Obeleži konkretnu liniju ili linije koje su problematične.",
            codeBlock = """
                1  interface MenuComponent {
                2      render()
                3  }
                4
                5  class MenuGroup implements MenuComponent {
                6      private children: List<MenuItem>
                7
                8      add(item: MenuItem) {
                9          children.add(item)
                10     }
                11
                12     render() {
                13         for each child in children {
                14             child.render()
                15         }
                16     }
                17 }
            """,
            options = listOf("Linija 1", "Linija 2", "Linija 5", "Linija 6", "Linija 8", "Linija 14"),
            correctAnswers = listOf("Linija 6", "Linija 8"),
            explanation = "MenuGroup prihvata samo MenuItem, a ne zajednički tip MenuComponent. Zbog toga ne može da sadrži druge grupe i ne formira pravi kompozit.",
            aiFollowUp = "Kako bi izmenio ove linije da grupa može da sadrži i stavke i podgrupe?",
            orderIndex = 513
        ),
        JuniorSeedBuilders.errorDetectionQuestion(
            questionId = "J5.14",
            title = "Pogrešna primena Flyweight obrasca",
            prompt = "Obeleži liniju ili linije koje prave problem.",
            codeBlock = """
                1  class CharacterGlyph {
                2      letter
                3      fontFamily
                4      fontSize
                5      fontColor
                6      x
                7      y
                8  }
                9
                10 for each character in text {
                11     glyphs.add(new CharacterGlyph(letter, "Arial", 12, "black", x, y))
                12 }
            """,
            options = listOf("Linija 2", "Linija 3", "Linija 4", "Linija 5", "Linija 6", "Linija 7", "Linija 11"),
            correctAnswers = listOf("Linija 3", "Linija 4", "Linija 5", "Linija 11"),
            explanation = "Zajednički podaci o fontu i boji se čuvaju u svakom objektu i svaki put se pravi kompletan novi objekat. Time se propušta deljenje zajedničkog stanja.",
            aiFollowUp = "Koji podaci bi trebalo da budu deo deljenog flyweight objekta, a koji deo spoljašnjeg stanja?",
            orderIndex = 514
        ),
        JuniorSeedBuilders.errorDetectionQuestion(
            questionId = "J5.15",
            title = "Pogrešna primena Mediator obrasca",
            prompt = "Obeleži konkretnu liniju ili linije koje ruše ideju obrasca.",
            codeBlock = """
                1  class NameField {
                2      private submitButton
                3      private discountField
                4
                5      onChange() {
                6          submitButton.enable()
                7          discountField.hide()
                8      }
                9  }
                10
                11 class SubmitButton {
                12     private nameField
                13
                14     click() {
                15         nameField.validate()
                16     }
                17 }
            """,
            options = listOf("Linija 2", "Linija 3", "Linija 6", "Linija 7", "Linija 12", "Linija 15"),
            correctAnswers = listOf("Linija 2", "Linija 3", "Linija 6", "Linija 7", "Linija 12", "Linija 15"),
            explanation = "Komponente direktno poznaju i kontrolišu jedna drugu. Time komunikacija nije centralizovana kroz mediator, već ostaje rasuta među kolegama.",
            aiFollowUp = "Kako bi ova interakcija izgledala kada bi obe komponente komunicirale isključivo preko mediatora?",
            orderIndex = 515
        )
    )
}
