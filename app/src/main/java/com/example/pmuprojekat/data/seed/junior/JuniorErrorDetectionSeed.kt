package com.example.pmuprojekat.data.seed.junior

internal object JuniorErrorDetectionSeed {
    val questions = listOf(
        JuniorSeedBuilders.errorDetectionQuestion(
            questionId = "J4.1",
            title = "PogreÅ¡na primena Singleton obrasca",
            prompt = "Analiziraj pseudo-kod i oznaÄi problematiÄan deo.",
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
            explanation = "Ako je konstruktor javan, bilo ko moÅ¾e praviti nove instance i time se ruÅ¡i suÅ¡tina Singleton obrasca.",
            aiFollowUp = "Kako bi ispravio ovu klasu da zaista garantuje jednu instancu?",
            orderIndex = 501
        ),
        JuniorSeedBuilders.errorDetectionQuestion(
            questionId = "J4.2",
            title = "PogreÅ¡na primena Observer obrasca",
            prompt = "UoÄi problem u sledeÄ‡em reÅ¡enju.",
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
            explanation = "Klasa Product ne radi preko apstrakcije i nema listu observer-a, pa je Ävrsto vezana za konkretne implementacije.",
            aiFollowUp = "Kako bi ovo reÅ¡enje izmenio da novi observer moÅ¾e da se doda bez menjanja Product klase?",
            orderIndex = 502
        ),
        JuniorSeedBuilders.errorDetectionQuestion(
            questionId = "J4.3",
            title = "PogreÅ¡na primena Strategy obrasca",
            prompt = "PronaÄ‘i greÅ¡ku u sledeÄ‡em pokuÅ¡aju primene Strategy obrasca.",
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
            options = listOf("Sve strategije su i dalje ugraÄ‘ene u jednu klasu kroz if-else", "Klasa se zove PaymentContext", "Metoda prima amount", "Postoji viÅ¡e naÄina plaÄ‡anja"),
            correctAnswers = listOf("Sve strategije su i dalje ugraÄ‘ene u jednu klasu kroz if-else"),
            explanation = "Iako naziv sugeriÅ¡e Strategy, algoritmi nisu izdvojeni u posebne klase sa zajedniÄkim interfejsom.",
            aiFollowUp = "Kako bi izgledalo pravilnije reÅ¡enje sa Strategy obrascem u ovom sluÄaju?",
            orderIndex = 503
        ),
        JuniorSeedBuilders.errorDetectionQuestion(
            questionId = "J4.4",
            title = "PogreÅ¡na primena Command obrasca",
            prompt = "Analiziraj pseudo-kod i pronaÄ‘i problem.",
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
            options = listOf("Dugme i dalje sadrÅ¾i granajuÄ‡u logiku svih akcija umesto da koristi komande", "Metoda se zove click", "Editor ima metode copy i paste", "Parametar actionType je tekstualan"),
            correctAnswers = listOf("Dugme i dalje sadrÅ¾i granajuÄ‡u logiku svih akcija umesto da koristi komande"),
            explanation = "Iako problem liÄi na Command, akcije nisu izdvojene u objekte, pa je UI i dalje Ävrsto vezan za izvrÅ¡enje.",
            aiFollowUp = "Kako bi izgledalo pravilnije reÅ¡enje u kome je svaka akcija izdvojena u poseban objekat?",
            orderIndex = 504
        ),
        JuniorSeedBuilders.errorDetectionQuestion(
            questionId = "J4.5",
            title = "PogreÅ¡na primena Builder obrasca",
            prompt = "UoÄi problem u pokuÅ¡aju primene Builder obrasca.",
            codeBlock = """
                class ReportBuilder {
                    build(title, author, footer, chart, summary, lang, format, watermark) {
                        return new Report(title, author, footer, chart, summary, lang, format, watermark)
                    }
                }
            """,
            options = listOf("Builder ne gradi objekat postepeno, veÄ‡ samo skriva isti veliki konstruktor iza druge metode", "Klasa se zove ReportBuilder", "Metoda vraÄ‡a Report", "Postoji format parametar"),
            correctAnswers = listOf("Builder ne gradi objekat postepeno, veÄ‡ samo skriva isti veliki konstruktor iza druge metode"),
            explanation = "SuÅ¡tina Builder obrasca nije samo preimenovanje konstruktora, veÄ‡ postepena i Äitljiva izgradnja objekta.",
            aiFollowUp = "Å ta bi trebalo da postoji u pravom Builder reÅ¡enju da bi imalo smisla?",
            orderIndex = 505
        ),
        JuniorSeedBuilders.errorDetectionQuestion(
            questionId = "J4.6",
            title = "PogreÅ¡na primena Adapter obrasca",
            prompt = "PronaÄ‘i greÅ¡ku u sledeÄ‡em reÅ¡enju.",
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
            options = listOf("Jedna klasa glumi adapter za sve moguÄ‡e sisteme kroz if-else grananje", "Adapter ima metodu pay", "Postoji StripeApi", "KoriÅ¡Ä‡en je amount parametar"),
            correctAnswers = listOf("Jedna klasa glumi adapter za sve moguÄ‡e sisteme kroz if-else grananje"),
            explanation = "Ovo viÅ¡e liÄi na centralizovanu proceduralnu logiku nego na Äist Adapter pristup sa jasnim prevodom jednog konkretnog interfejsa.",
            aiFollowUp = "Kako bi izgledalo Äistije reÅ¡enje ako bi svaki spoljaÅ¡nji servis imao svoj adapter?",
            orderIndex = 506
        ),
        JuniorSeedBuilders.errorDetectionQuestion(
            questionId = "J4.10",
            title = "PogreÅ¡na primena Bridge obrasca",
            prompt = "PronaÄ‘i konkretnu liniju ili linije koje ruÅ¡e pravilnu primenu Bridge obrasca.",
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
            explanation = "AlertMessage direktno ugraÄ‘uje konkretne kanale slanja umesto da poseduje referencu na apstrakciju Sender. Time se ruÅ¡i razdvajanje apstrakcije i implementacije.",
            aiFollowUp = "Kako bi AlertMessage trebalo da izgleda da bi pravilno koristio Bridge?",
            orderIndex = 510
        ),
        JuniorSeedBuilders.errorDetectionQuestion(
            questionId = "J4.11",
            title = "PogreÅ¡na primena Visitor obrasca",
            prompt = "ObeleÅ¾i liniju ili linije koje prave problem.",
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
            explanation = "Operacije koje bi mogle biti izdvojene u visitor-e ugraÄ‘ene su u sam interfejs elemenata. To vodi Å¡irenju svih klasa elemenata pri dodavanju novih operacija.",
            aiFollowUp = "Koje bi operacije ovde bile dobar kandidat da se izdvoje u visitor klase?",
            orderIndex = 511
        ),
        JuniorSeedBuilders.errorDetectionQuestion(
            questionId = "J4.12",
            title = "PogreÅ¡na primena Memento obrasca",
            prompt = "ObeleÅ¾i taÄnu liniju ili linije koje ruÅ¡e dobru primenu obrasca.",
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
            explanation = "Memento izlaÅ¾e unutraÅ¡nje stanje spolja i dopuÅ¡ta caretaker-u da ga menja. Time se gubi enkapsulacija koja je suÅ¡tina Memento obrasca.",
            aiFollowUp = "Kako bi zaÅ¡titio stanje unutar mementa da spoljne klase ne mogu da ga menjaju?",
            orderIndex = 512
        ),
        JuniorSeedBuilders.errorDetectionQuestion(
            questionId = "J4.13",
            title = "PogreÅ¡na primena Composite obrasca",
            prompt = "ObeleÅ¾i konkretnu liniju ili linije koje su problematiÄne.",
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
            explanation = "MenuGroup prihvata samo MenuItem, a ne zajedniÄki tip MenuComponent. Zbog toga ne moÅ¾e da sadrÅ¾i druge grupe i ne formira pravi kompozit.",
            aiFollowUp = "Kako bi izmenio ove linije da grupa moÅ¾e da sadrÅ¾i i stavke i podgrupe?",
            orderIndex = 513
        ),
        JuniorSeedBuilders.errorDetectionQuestion(
            questionId = "J4.14",
            title = "PogreÅ¡na primena Flyweight obrasca",
            prompt = "ObeleÅ¾i liniju ili linije koje prave problem.",
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
            explanation = "ZajedniÄki podaci o fontu i boji se Äuvaju u svakom objektu i svaki put se pravi kompletan novi objekat. Time se propuÅ¡ta deljenje zajedniÄkog stanja.",
            aiFollowUp = "Koji podaci bi trebalo da budu deo deljenog flyweight objekta, a koji deo spoljaÅ¡njeg stanja?",
            orderIndex = 514
        ),
        JuniorSeedBuilders.errorDetectionQuestion(
            questionId = "J4.15",
            title = "PogreÅ¡na primena Mediator obrasca",
            prompt = "ObeleÅ¾i konkretnu liniju ili linije koje ruÅ¡e ideju obrasca.",
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
            explanation = "Komponente direktno poznaju i kontroliÅ¡u jedna drugu. Time komunikacija nije centralizovana kroz mediator, veÄ‡ ostaje rasuta meÄ‘u kolegama.",
            aiFollowUp = "Kako bi ova interakcija izgledala kada bi obe komponente komunicirale iskljuÄivo preko mediatora?",
            orderIndex = 515
        )
    )
}

