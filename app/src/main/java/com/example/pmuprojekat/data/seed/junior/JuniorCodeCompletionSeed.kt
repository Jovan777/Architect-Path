package com.example.pmuprojekat.data.seed.junior

internal object JuniorCodeCompletionSeed {
    val questions = listOf(
        JuniorSeedBuilders.codeCompletionQuestion(
            questionId = "J1.1",
            title = "Dopuni pseudo-kod za izbor algoritma popusta",
            prompt = "Sistem za naplatu treba da podrži više načina obračuna popusta. Dovrši pseudo-kod tako da odgovara Strategy obrascu.",
            codeBlock = """
                interface DiscountStrategy {
                    __________ calculate(price)
                }

                class StudentDiscount implements DiscountStrategy {
                    override calculate(price) {
                        return price * 0.9
                    }
                }

                class PremiumDiscount implements DiscountStrategy {
                    override calculate(price) {
                        return price * 0.8
                    }
                }

                class CheckoutContext {
                    private strategy: __________

                    constructor(strategy: __________) {
                        this.strategy = strategy
                    }

                    finalPrice(price) {
                        return strategy.__________(price)
                    }
                }
            """,
            blanks = listOf("number", "DiscountStrategy", "DiscountStrategy", "calculate"),
            aiFollowUp = "Objasni zašto je CheckoutContext povezan sa interfejsom strategije, a ne sa konkretnom klasom popusta.",
            orderIndex = 101
        ),
        JuniorSeedBuilders.codeCompletionQuestion(
            questionId = "J1.2",
            title = "Dopuni pseudo-kod za sistem pretplate na promene",
            prompt = "Sistem treba da obavesti više objekata kada se promeni stanje proizvoda. Dopuni pseudo-kod tako da odgovara Observer obrascu.",
            codeBlock = """
                interface Observer {
                    update(newPrice)
                }

                class Product {
                    private observers: List<__________>
                    private price

                    subscribe(obs: __________) {
                        observers.add(obs)
                    }

                    setPrice(newPrice) {
                        this.price = newPrice
                        __________()
                    }

                    private notifyObservers() {
                        for each obs in observers {
                            obs.__________(price)
                        }
                    }
                }
            """,
            blanks = listOf("Observer", "Observer", "notifyObservers", "update"),
            aiFollowUp = "Zašto Product ne treba direktno da zna detalje svake konkretne klase koja prima obaveštenje?",
            orderIndex = 102
        ),
        JuniorSeedBuilders.codeCompletionQuestion(
            questionId = "J1.3",
            title = "Dopuni pseudo-kod za postepenu izgradnju objekta",
            prompt = "Sistem pravi objekat Computer kroz više koraka. Dopuni pseudo-kod tako da odgovara Builder obrascu.",
            codeBlock = """
                class Computer {
                    cpu
                    ram
                    storage
                }

                interface ComputerBuilder {
                    __________
                    setRam(ram)
                    setStorage(storage)
                    build(): Computer
                }

                class GamingComputerBuilder implements ComputerBuilder {
                    private computer = new Computer()

                    setCpu(cpu) {
                        computer.cpu = cpu
                    }

                    setRam(ram) {
                        computer.ram = ram
                    }

                    setStorage(storage) {
                        computer.storage = storage
                    }

                    build() {
                        return __________
                    }
                }
            """,
            blanks = listOf("setCpu(cpu)", "computer"),
            aiFollowUp = "Zašto je Builder pogodniji od jednog konstruktora sa mnogo parametara u ovom slučaju?",
            orderIndex = 103
        ),
        JuniorSeedBuilders.codeCompletionQuestion(
            questionId = "J1.4",
            title = "Dopuni pseudo-kod za komande u editoru",
            prompt = "Sistem za editor treba da koristi Command obrazac kako bi dugmad mogla da pokreću različite akcije. Dopuni pseudo-kod.",
            codeBlock = """
                interface Command {
                    __________()
                }

                class SaveCommand implements Command {
                    private editor: TextEditor

                    constructor(editor: TextEditor) {
                        this.editor = editor
                    }

                    override __________() {
                        editor.save()
                    }
                }

                class Button {
                    private command: __________

                    constructor(command: __________) {
                        this.command = command
                    }

                    click() {
                        command.__________()
                    }
                }
            """,
            blanks = listOf("execute", "execute", "Command", "Command", "execute"),
            aiFollowUp = "Zašto je korisno da Button ne zna ništa o konkretnoj akciji koju pokreće?",
            orderIndex = 104
        ),
        JuniorSeedBuilders.codeCompletionQuestion(
            questionId = "J1.5",
            title = "Dopuni pseudo-kod za obradu dokumenta",
            prompt = "Sistem obrađuje više tipova dokumenata, ali svi dele isti opšti tok rada. Dopuni pseudo-kod tako da odgovara Template Method obrascu.",
            codeBlock = """
                abstract class DocumentProcessor {

                    final process() {
                        load()
                        __________()
                        save()
                    }

                    private load() {
                        print("Loading document")
                    }

                    abstract __________()

                    private save() {
                        print("Saving document")
                    }
                }

                class PdfProcessor extends DocumentProcessor {
                    override parse() {
                        print("Parsing PDF")
                    }
                }
            """,
            blanks = listOf("parse", "parse"),
            aiFollowUp = "Šta je glavna razlika između fiksnih i promenljivih koraka u ovom obrascu?",
            orderIndex = 105
        ),
        JuniorSeedBuilders.codeCompletionQuestion(
            questionId = "J1.6",
            title = "Dopuni pseudo-kod za uklapanje spoljne biblioteke",
            prompt = "Tvoj sistem očekuje interfejs PaymentProcessor, ali spoljašnja biblioteka ima klasu LegacyPaymentGateway sa drugačijom metodom. Dopuni pseudo-kod da odgovara Adapter obrascu.",
            codeBlock = """
                interface PaymentProcessor {
                    pay(amount)
                }

                class LegacyPaymentGateway {
                    makePayment(value) {
                        print("Legacy payment: " + value)
                    }
                }

                class PaymentAdapter implements __________ {
                    private gateway: LegacyPaymentGateway

                    constructor(gateway: LegacyPaymentGateway) {
                        this.gateway = gateway
                    }

                    override __________(amount) {
                        gateway.__________(amount)
                    }
                }
            """,
            blanks = listOf("PaymentProcessor", "pay", "makePayment"),
            aiFollowUp = "Zašto je Adapter bolji od izmene ostatka sistema da radi direktno sa starim interfejsom?",
            orderIndex = 106
        ),
        JuniorSeedBuilders.codeCompletionQuestion(
            questionId = "J1.7",
            title = "Dopuni pseudo-kod za razdvajanje apstrakcije i implementacije",
            prompt = "Sistem za slanje obaveštenja treba da podrži više tipova poruka i više kanala slanja. Dopuni pseudo-kod tako da odgovara Bridge obrascu.",
            codeBlock = """
                interface MessageSender {
                    sendMessage(text)
                }

                class EmailSender implements MessageSender {
                    override sendMessage(text) {
                        print("Email: " + text)
                    }
                }

                class SmsSender implements MessageSender {
                    override sendMessage(text) {
                        print("SMS: " + text)
                    }
                }

                abstract class Message {
                    protected sender: __________

                    constructor(sender: __________) {
                        this.sender = sender
                    }

                    abstract send(content)
                }

                class AlertMessage extends Message {
                    override send(content) {
                        sender.__________(content)
                    }
                }
            """,
            blanks = listOf("MessageSender", "MessageSender", "sendMessage"),
            aiFollowUp = "Zašto je korisno da se tip poruke i kanal slanja mogu menjati nezavisno?",
            orderIndex = 107
        ),
        JuniorSeedBuilders.codeCompletionQuestion(
            questionId = "J1.8",
            title = "Dopuni pseudo-kod za hijerarhiju fajlova i foldera",
            prompt = "Sistem fajlova treba da tretira i fajl i folder na isti način. Dopuni pseudo-kod tako da odgovara Composite obrascu.",
            codeBlock = """
                interface FileSystemItem {
                    showSize()
                }

                class File implements FileSystemItem {
                    private size

                    constructor(size) {
                        this.size = size
                    }

                    override showSize() {
                        print(size)
                    }
                }

                class Folder implements __________ {
                    private items: List<__________>

                    add(item: __________) {
                        items.add(item)
                    }

                    override showSize() {
                        for each item in items {
                            item.__________()
                        }
                    }
                }
            """,
            blanks = listOf("FileSystemItem", "FileSystemItem", "FileSystemItem", "showSize"),
            aiFollowUp = "Zašto je važno da i File i Folder implementiraju isti interfejs?",
            orderIndex = 108
        ),
        JuniorSeedBuilders.codeCompletionQuestion(
            questionId = "J1.9",
            title = "Dopuni pseudo-kod za čuvanje stanja dokumenta",
            prompt = "Sistem za dokumente treba da podrži vraćanje na prethodno stanje. Dopuni pseudo-kod tako da odgovara Memento obrascu.",
            codeBlock = """
                class DocumentMemento {
                    private content

                    constructor(content) {
                        this.content = content
                    }

                    getSavedContent() {
                        return content
                    }
                }

                class Document {
                    private content

                    setContent(content) {
                        this.content = content
                    }

                    save() {
                        return new __________(content)
                    }

                    restore(memento: __________) {
                        this.content = memento.__________()
                    }
                }
            """,
            blanks = listOf("DocumentMemento", "DocumentMemento", "getSavedContent"),
            aiFollowUp = "Zašto je bolje čuvati snapshot stanja nego ručno rekonstruisati staro stanje svaki put?",
            orderIndex = 109
        ),
        JuniorSeedBuilders.codeCompletionQuestion(
            questionId = "J1.10",
            title = "Dopuni pseudo-kod za dodavanje operacija nad elementima",
            prompt = "Sistem sadrži više tipova geometrijskih oblika. Potrebno je dodati operacije kao što su računanje površine i eksport bez menjanja postojećih klasa oblika. Dopuni pseudo-kod tako da odgovara Visitor obrascu.",
            codeBlock = """
                interface ShapeVisitor {
                    visitCircle(circle: Circle)
                    __________
                }

                interface Shape {
                    __________(visitor: ShapeVisitor)
                }

                class Circle implements Shape {
                    override accept(visitor: ShapeVisitor) {
                        visitor.__________(this)
                    }
                }

                class Rectangle implements Shape {
                    override accept(visitor: ShapeVisitor) {
                        visitor.__________(this)
                    }
                }
            """,
            blanks = listOf("visitRectangle(rectangle: Rectangle)", "accept", "visitCircle", "visitRectangle"),
            aiFollowUp = "Zašto se nova operacija dodaje kroz visitor, a ne kroz izmene svih klasa oblika?",
            orderIndex = 110
        ),
        JuniorSeedBuilders.codeCompletionQuestion(
            questionId = "J1.11",
            title = "Dopuni pseudo-kod za deljenje zajedničkog stanja",
            prompt = "Sistem za mapu igre sadrži mnogo stabala. Potrebno je deliti zajedničke podatke između njih. Dopuni pseudo-kod tako da odgovara Flyweight obrascu.",
            codeBlock = """
                class TreeType {
                    texture
                    color

                    constructor(texture, color) {
                        this.texture = texture
                        this.color = color
                    }

                    draw(x, y) {
                        print("Draw " + texture + " at " + x + "," + y)
                    }
                }

                class Tree {
                    private type: __________
                    private x
                    private y

                    constructor(type: __________, x, y) {
                        this.type = type
                        this.x = x
                        this.y = y
                    }

                    draw() {
                        type.__________(x, y)
                    }
                }
            """,
            blanks = listOf("TreeType", "TreeType", "draw"),
            aiFollowUp = "Koji deo stanja pripada flyweight objektu, a koji pojedinačnom objektu Tree?",
            orderIndex = 111
        ),
        JuniorSeedBuilders.codeCompletionQuestion(
            questionId = "J1.12",
            title = "Dopuni pseudo-kod za koordinaciju UI elemenata",
            prompt = "Forma ima više elemenata koji ne treba direktno da komuniciraju međusobno. Dopuni pseudo-kod tako da odgovara Mediator obrascu.",
            codeBlock = """
                interface FormMediator {
                    notify(sender, event)
                }

                class SubmitButton {
                    private mediator: __________

                    constructor(mediator: __________) {
                        this.mediator = mediator
                    }

                    click() {
                        mediator.__________(this, "click")
                    }
                }

                class NameField {
                    private mediator: FormMediator

                    onChange() {
                        mediator.notify(this, __________)
                    }
                }
            """,
            blanks = listOf("FormMediator", "FormMediator", "notify", "\"change\""),
            aiFollowUp = "Zašto je bolje da SubmitButton i NameField komuniciraju preko mediatora nego direktno?",
            orderIndex = 112
        )
    )
}
