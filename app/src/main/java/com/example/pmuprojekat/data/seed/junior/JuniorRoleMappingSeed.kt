package com.example.pmuprojekat.data.seed.junior

internal object JuniorRoleMappingSeed {
    val questions = listOf(
        JuniorSeedBuilders.roleMappingQuestion(
            questionId = "J2.1",
            title = "Odredi uloge klasa u Strategy obrascu",
            prompt = "Prikazane su klase iz sistema za obračun dostave. Odredi ulogu svake klase.",
            roles = listOf("Context", "Strategy", "ConcreteStrategy", "Subject"),
            cards = listOf(
                JuniorRoleCardSeed("DeliveryStrategy — metoda: calculate(order)", "Strategy"),
                JuniorRoleCardSeed("FastDelivery — metoda: calculate(order)", "ConcreteStrategy"),
                JuniorRoleCardSeed("EconomyDelivery — metoda: calculate(order)", "ConcreteStrategy"),
                JuniorRoleCardSeed("DeliveryCalculator — atribut: strategy: DeliveryStrategy; metoda: total(order)", "Context")
            ),
            aiFollowUp = "Kako DeliveryCalculator koristi strategiju bez zavisnosti od konkretne implementacije?",
            orderIndex = 201
        ),
        JuniorSeedBuilders.roleMappingQuestion(
            questionId = "J2.2",
            title = "Odredi uloge klasa u Observer obrascu",
            prompt = "Prikazane su klase iz sistema za praćenje cene proizvoda. Odredi njihove uloge.",
            roles = listOf("Subject", "Observer", "ConcreteObserver", "Context"),
            cards = listOf(
                JuniorRoleCardSeed("PriceObserver — metoda: update(price)", "Observer"),
                JuniorRoleCardSeed("MobileAppNotifier — metoda: update(price)", "ConcreteObserver"),
                JuniorRoleCardSeed("EmailNotifier — metoda: update(price)", "ConcreteObserver"),
                JuniorRoleCardSeed("Product — atribut: observers; metode: subscribe(), unsubscribe(), notifyObservers()", "Subject")
            ),
            aiFollowUp = "Zašto je važno da Product komunicira preko PriceObserver interfejsa?",
            orderIndex = 202
        ),
        JuniorSeedBuilders.roleMappingQuestion(
            questionId = "J2.3",
            title = "Odredi uloge klasa u Command obrascu",
            prompt = "Prikazane su klase iz aplikacije za tekst editor. Odredi njihove uloge.",
            roles = listOf("Receiver", "Command", "ConcreteCommand", "Invoker"),
            cards = listOf(
                JuniorRoleCardSeed("Command — metoda: execute()", "Command"),
                JuniorRoleCardSeed("CopyCommand — metoda: execute()", "ConcreteCommand"),
                JuniorRoleCardSeed("TextEditor — metode: copy(), paste()", "Receiver"),
                JuniorRoleCardSeed("ToolbarButton — atribut: command; metoda: click()", "Invoker")
            ),
            aiFollowUp = "Koja je razlika između klase koja pokreće komandu i klase koja zaista obavlja posao?",
            orderIndex = 203
        ),
        JuniorSeedBuilders.roleMappingQuestion(
            questionId = "J2.4",
            title = "Odredi uloge klasa u Template Method obrascu",
            prompt = "Prikazane su klase iz sistema za obradu fajlova. Odredi njihove uloge.",
            roles = listOf("AbstractClass", "ConcreteClass", "Strategy", "Receiver"),
            cards = listOf(
                JuniorRoleCardSeed("FileProcessor — metoda: process(); metode: load(), save(), parse()", "AbstractClass"),
                JuniorRoleCardSeed("CsvProcessor — override: parse()", "ConcreteClass"),
                JuniorRoleCardSeed("JsonProcessor — override: parse()", "ConcreteClass")
            ),
            aiFollowUp = "Zašto je važno da osnovna klasa zadrži redosled koraka algoritma?",
            orderIndex = 204
        ),
        JuniorSeedBuilders.roleMappingQuestion(
            questionId = "J2.5",
            title = "Odredi uloge klasa u Adapter obrascu",
            prompt = "Prikazane su klase iz sistema za plaćanje. Odredi njihove uloge.",
            roles = listOf("Target", "Adaptee", "Adapter", "Subject"),
            cards = listOf(
                JuniorRoleCardSeed("PaymentProcessor — metoda: pay(amount)", "Target"),
                JuniorRoleCardSeed("LegacyGateway — metoda: makePayment(amount)", "Adaptee"),
                JuniorRoleCardSeed("GatewayAdapter — koristi LegacyGateway i implementira PaymentProcessor", "Adapter")
            ),
            aiFollowUp = "Koja klasa ovde prevodi poziv iz jednog interfejsa u drugi?",
            orderIndex = 205
        ),
        JuniorSeedBuilders.roleMappingQuestion(
            questionId = "J2.6",
            title = "Odredi uloge klasa u Builder obrascu",
            prompt = "Prikazane su klase iz sistema za kreiranje izveštaja. Odredi njihove uloge.",
            roles = listOf("Builder", "ConcreteBuilder", "Director", "Product", "Context"),
            cards = listOf(
                JuniorRoleCardSeed("ReportBuilder — metode: setTitle(), setFooter(), build()", "Builder"),
                JuniorRoleCardSeed("PdfReportBuilder — implementira ReportBuilder", "ConcreteBuilder"),
                JuniorRoleCardSeed("ReportDirector — metoda: constructStandardReport(builder)", "Director"),
                JuniorRoleCardSeed("Report", "Product")
            ),
            aiFollowUp = "Koja je uloga Director klase i da li je uvek neophodna?",
            orderIndex = 206
        ),
        JuniorSeedBuilders.roleMappingQuestion(
            questionId = "J2.7",
            title = "Odredi uloge klasa u Bridge obrascu",
            prompt = "Prikazane su klase iz sistema za generisanje izveštaja. Odredi njihove uloge.",
            roles = listOf("Abstraction", "RefinedAbstraction", "Implementor", "ConcreteImplementor", "Receiver"),
            cards = listOf(
                JuniorRoleCardSeed("Report — atribut: renderer; metoda: display()", "Abstraction"),
                JuniorRoleCardSeed("DetailedReport — override: display()", "RefinedAbstraction"),
                JuniorRoleCardSeed("Renderer — metoda: render(text)", "Implementor"),
                JuniorRoleCardSeed("PdfRenderer — metoda: render(text)", "ConcreteImplementor"),
                JuniorRoleCardSeed("HtmlRenderer — metoda: render(text)", "ConcreteImplementor")
            ),
            aiFollowUp = "Koja je glavna prednost toga što Report radi sa Renderer apstrakcijom?",
            orderIndex = 207
        ),
        JuniorSeedBuilders.roleMappingQuestion(
            questionId = "J2.8",
            title = "Odredi uloge klasa u Composite obrascu",
            prompt = "Prikazane su klase iz sistema menija aplikacije. Odredi njihove uloge.",
            roles = listOf("Component", "Leaf", "Composite", "Invoker"),
            cards = listOf(
                JuniorRoleCardSeed("MenuComponent — metoda: render()", "Component"),
                JuniorRoleCardSeed("MenuItem — metoda: render()", "Leaf"),
                JuniorRoleCardSeed("MenuGroup — atribut: children; metode: add(), remove(), render()", "Composite")
            ),
            aiFollowUp = "Zašto je MenuGroup složeniji od MenuItem, iako oba imaju isti osnovni interfejs?",
            orderIndex = 208
        ),
        JuniorSeedBuilders.roleMappingQuestion(
            questionId = "J2.9",
            title = "Odredi uloge klasa u Memento obrascu",
            prompt = "Prikazane su klase iz sistema za undo u editoru. Odredi njihove uloge.",
            roles = listOf("Originator", "Memento", "Caretaker", "ConcreteObserver"),
            cards = listOf(
                JuniorRoleCardSeed("TextEditor — metode: createSnapshot(), restore(snapshot)", "Originator"),
                JuniorRoleCardSeed("EditorSnapshot — čuva sadržaj i stanje kursora", "Memento"),
                JuniorRoleCardSeed("HistoryManager — čuva listu snapshot-a", "Caretaker")
            ),
            aiFollowUp = "Zašto HistoryManager ne bi trebalo da zna unutrašnje detalje stanja editora?",
            orderIndex = 209
        ),
        JuniorSeedBuilders.roleMappingQuestion(
            questionId = "J2.10",
            title = "Odredi uloge klasa u Visitor obrascu",
            prompt = "Prikazane su klase iz sistema za obradu AST stabla. Odredi njihove uloge.",
            roles = listOf("Element", "ConcreteElement", "Visitor", "ConcreteVisitor", "Caretaker"),
            cards = listOf(
                JuniorRoleCardSeed("Node — metoda: accept(visitor)", "Element"),
                JuniorRoleCardSeed("BinaryExpressionNode — override: accept(visitor)", "ConcreteElement"),
                JuniorRoleCardSeed("PrintVisitor — metode: visitBinaryExpression(node), visitLiteral(node)", "ConcreteVisitor"),
                JuniorRoleCardSeed("OptimizationVisitor — metode: visitBinaryExpression(node), visitLiteral(node)", "ConcreteVisitor")
            ),
            aiFollowUp = "Zašto je accept(visitor) ključan deo ovog obrasca?",
            orderIndex = 210
        ),
        JuniorSeedBuilders.roleMappingQuestion(
            questionId = "J2.11",
            title = "Odredi uloge klasa u Flyweight obrascu",
            prompt = "Prikazane su klase iz sistema za prikaz stabala u igri. Odredi njihove uloge.",
            roles = listOf("Flyweight", "Context", "FlyweightFactory", "Invoker"),
            cards = listOf(
                JuniorRoleCardSeed("TreeType — polja: texture, color; metoda: draw(x, y)", "Flyweight"),
                JuniorRoleCardSeed("Tree — polja: x, y, type", "Context"),
                JuniorRoleCardSeed("TreeFactory — metoda: getTreeType(texture, color)", "FlyweightFactory")
            ),
            aiFollowUp = "Zašto TreeFactory ne bi trebalo svaki put da pravi novi TreeType objekat?",
            orderIndex = 211
        ),
        JuniorSeedBuilders.roleMappingQuestion(
            questionId = "J2.12",
            title = "Odredi uloge klasa u Mediator obrascu",
            prompt = "Prikazane su klase iz sistema za formu za registraciju. Odredi njihove uloge.",
            roles = listOf("Mediator", "Colleague", "ConcreteMediator", "ConcreteObserver"),
            cards = listOf(
                JuniorRoleCardSeed("DialogMediator — metoda: notify(sender, event)", "ConcreteMediator"),
                JuniorRoleCardSeed("UsernameField — metoda: onChange()", "Colleague"),
                JuniorRoleCardSeed("SubmitButton — metoda: click()", "Colleague"),
                JuniorRoleCardSeed("AgreementCheckbox — metoda: toggle()", "Colleague")
            ),
            aiFollowUp = "Šta kolege dobijaju time što ne poznaju direktno sve ostale komponente?",
            orderIndex = 212
        )
    )
}
