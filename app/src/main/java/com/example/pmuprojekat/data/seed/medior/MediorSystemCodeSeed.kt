package com.example.pmuprojekat.data.seed.medior

import com.example.pmuprojekat.data.seed.SeedQuestion

object MediorSystemCodeSeed {
    val questions: List<SeedQuestion> = listOf(
        MediorSeedBuilders.codeDecisionQuestion(
            questionId = "M2.1",
            title = "Dopuni postojeći booking servis tako da podrži nova pricing pravila",
            prompt = """
            Tim ima rezervacioni servis koji trenutno radi, ali dolazi novi zahtev: pravilo obračuna cene mora da ima mogućnost da se menja u zavisnosti od konteksta rezervacije, bez grananja kroz glavnu servisnu klasu.
            """.trimIndent(),
            codeBlock = """
            class ReservationService {
                private repository
                private notifier

                createReservation(request) {
                    room = repository.findRoom(request.roomId)

                    if (!room.isAvailable(request.from, request.to)) {
                        return "Unavailable"
                    }

                    days = dateDiff(request.from, request.to)
                    basePrice = room.pricePerDay * days

                    finalPrice = ???   // ovde treba rešiti novi zahtev

                    reservation = new Reservation(
                        request.userId,
                        room.id,
                        request.from,
                        request.to,
                        finalPrice
                    )

                    repository.save(reservation)
                    notifier.sendReservationCreated(reservation)

                    return reservation
                }
            }
            """.trimIndent(),
            solutionCards = listOf(
                "A" to """
                if (request.isVip) finalPrice = basePrice * 0.8
                else if (request.isWeekend) finalPrice = basePrice * 1.15
                else finalPrice = basePrice
                """.trimIndent(),
                "B" to """
                pricingStrategy = request.pricingStrategy
                finalPrice = pricingStrategy.apply(basePrice)
                """.trimIndent(),
                "C" to """
                strategy = pricingResolver.resolve(request, room)
                finalPrice = strategy.calculate(basePrice, request, room)
                """.trimIndent(),
                "D" to """
                finalPrice = basePrice
                log("pricing handled externally")
                """.trimIndent()
            ),
            correctLetter = "C",
            aiFollowUp = """
            Zašto je pricingResolver.resolve(...) bolje mesto za izbor pravila nego da se logika grananja vrati u ReservationService?
            """.trimIndent(),
            wave = 1,
            orderIndex = 3
        ),

        MediorSeedBuilders.codeDecisionQuestion(
            questionId = "M2.2",
            title = "Dopuni gateway obradu tako da validacija bude proširiva",
            prompt = """
            API tim želi da zahtevi prolaze kroz više koraka:
            •	autentifikacija, 
            •	autorizacija, 
            •	validacija sadržaja, 
            •	eventualne dodatne provere kasnije. 
            Ne žele centralnu metodu koja će stalno rasti novim granama.
            """.trimIndent(),
            codeBlock = """
            abstract class RequestCheck {
                protected next

                setNext(next) {
                    this.next = next
                    return next
                }

                handle(request) {
                    if (!passes(request)) {
                        return failure(request)
                    }

                    if (next != null) {
                        return next.handle(request)
                    }

                    return success(request)
                }

                abstract passes(request)
                abstract failure(request)

                success(request) {
                    return "OK"
                }
            }

            class AuthCheck extends RequestCheck { ... }
            class PermissionCheck extends RequestCheck { ... }
            class PayloadCheck extends RequestCheck { ... }

            class ApiGateway {
                process(request) {
                    // ovde nedostaje slaganje rešenja
                    return ???
                }
            }
            """.trimIndent(),
            solutionCards = listOf(
                "A" to """
                if (!auth(request)) return "Unauthorized"
                if (!permissions(request)) return "Forbidden"
                if (!payloadValid(request)) return "Bad Request"
                return "OK"
                """.trimIndent(),
                "B" to """
                chain = new AuthCheck()
                chain.setNext(new PermissionCheck()).setNext(new PayloadCheck())
                return chain.handle(request)
                """.trimIndent(),
                "C" to """
                checks = [new AuthCheck(), new PermissionCheck(), new PayloadCheck()]
                return checks
                """.trimIndent(),
                "D" to """
                handler = new PayloadCheck()
                return handler.handle(request)
                """.trimIndent()
            ),
            correctLetter = "B",
            aiFollowUp = """
            Koja je najvažnija prednost ovog pristupa kada se kasnije doda još jedan korak validacije između postojećih?

            3. Treći tip zadatka: Povezivanje zahteva sistema sa obrascem projektovanja
            """.trimIndent(),
            wave = 1,
            orderIndex = 4
        ),

        MediorSeedBuilders.codeDecisionQuestion(
            questionId = "M2.3",
            title = "Dopuni pipeline za obradu sadržaja bez menjanja osnovne obrade",
            prompt = """
            Sistem već ima osnovnu komponentu koja priprema sadržaj za slanje klijentu. Novi zahtevi traže da se po potrebi uključe:
            •	kompresija, 
            •	enkripcija, 
            •	audit zapis, 
            ali tim ne želi:
            •	da proširuje osnovnu klasu pri svakom novom zahtevu, 
            •	niti da pravi posebnu klasu za svaku kombinaciju funkcionalnosti.
            """.trimIndent(),
            codeBlock = """
            interface ContentProcessor {
                process(content)
            }

            class BaseContentProcessor implements ContentProcessor {
                process(content) {
                    return content
                }
            }

            class DeliveryService {
                private processor: ContentProcessor

                constructor(processor: ContentProcessor) {
                    this.processor = processor
                }

                deliver(content) {
                    prepared = processor.process(content)
                    send(prepared)
                }
            }
            """.trimIndent(),
            solutionCards = listOf(
                "A" to """
                class WrappedProcessor implements ContentProcessor {
                    protected inner: ContentProcessor

                    constructor(inner: ContentProcessor) {
                        this.inner = inner
                    }

                    process(content) {
                        return inner.process(content)
                    }
                }

                class CompressedProcessor extends WrappedProcessor {
                    process(content) {
                        processed = inner.process(content)
                        return compress(processed)
                    }
                }
                """.trimIndent(),
                "B" to """
                class DeliveryService {
                    deliver(content, useCompression, useEncryption, useAudit) {
                        processed = content

                        if (useCompression) {
                            processed = compress(processed)
                        }

                        if (useEncryption) {
                            processed = encrypt(processed)
                        }

                        if (useAudit) {
                            writeAudit(processed)
                        }

                        send(processed)
                    }
                }
                """.trimIndent(),
                "C" to """
                class ExtendedContentProcessor extends BaseContentProcessor {
                    private compressionEnabled
                    private encryptionEnabled
                    private auditEnabled

                    constructor(compressionEnabled, encryptionEnabled, auditEnabled) {
                        this.compressionEnabled = compressionEnabled
                        this.encryptionEnabled = encryptionEnabled
                        this.auditEnabled = auditEnabled
                    }

                    process(content) {
                        result = content

                        if (compressionEnabled) {
                            result = compress(result)
                        }

                        if (encryptionEnabled) {
                            result = encrypt(result)
                        }

                        if (auditEnabled) {
                            writeAudit(result)
                        }

                        return result
                    }
                }
                """.trimIndent(),
                "D" to """
                class ProcessingCoordinator {
                    private processor = new BaseContentProcessor()

                    process(content) {
                        result = processor.process(content)
                        result = compress(result)
                        result = encrypt(result)
                        writeAudit(result)
                        return result
                    }
                }
                """.trimIndent()
            ),
            correctLetter = "A",
            aiFollowUp = "Zašto je arhitektonski loš znak kada broj boolean parametara raste zajedno sa brojem opcionalnih ponašanja?",
            wave = 2,
            orderIndex = 13
        ),

        MediorSeedBuilders.codeDecisionQuestion(
            questionId = "M2.4",
            title = "Dopuni pristup generisanju izveštaja tako da se uvede kontrola bez izmene klijenta",
            prompt = """
            Sistem ima komponentu za generisanje finansijskih izveštaja. Generisanje je skupo i ne sme biti dostupno svakom korisniku. Tim želi da:
            •	zadrži isti interfejs prema klijentu, 
            •	uvede proveru pristupa pre izvršenja, 
            •	po potrebi kasnije doda logovanje i keširanje, 
            •	ne menja postojeći kod ekrana koji koristi generator izveštaja.
            """.trimIndent(),
            codeBlock = """
            interface ReportGenerator {
                generate(reportId)
            }

            class RealReportGenerator implements ReportGenerator {
                generate(reportId) {
                    return buildHeavyReport(reportId)
                }
            }

            class FinanceScreen {
                private generator: ReportGenerator

                constructor(generator: ReportGenerator) {
                    this.generator = generator
                }

                openReport(reportId) {
                    return generator.generate(reportId)
                }
            }
            """.trimIndent(),
            solutionCards = listOf(
                "A" to """
                class SecuredReportGenerator implements ReportGenerator {
                    private realGenerator
                    private authService

                    constructor(realGenerator, authService) {
                        this.realGenerator = realGenerator
                        this.authService = authService
                    }

                    generate(reportId) {
                        if (!authService.canAccess(reportId)) {
                            return "Access denied"
                        }

                        return realGenerator.generate(reportId)
                    }
                }
                """.trimIndent(),
                "B" to """
                class FinanceScreen {
                    openReport(reportId) {
                        if (!authService.canAccess(reportId)) {
                            return "Access denied"
                        }

                        return realGenerator.generate(reportId)
                    }
                }
                """.trimIndent(),
                "C" to """
                class ReportFactory {
                    create(reportId) {
                        return new RealReportGenerator()
                    }
                }
                """.trimIndent(),
                "D" to """
                class CachedReportData {
                    reportId
                    content
                }
                """.trimIndent()
            ),
            correctLetter = "A",
            aiFollowUp = """
            Zašto je bolje da kontrola pristupa bude ugrađena u objekat koji “stoji ispred” realnog generatora, nego da svaki ekran posebno proverava dozvole?

            3. Treći tip zadatka: Povezivanje zahteva sistema sa obrascem projektovanja
            """.trimIndent(),
            wave = 2,
            orderIndex = 14
        ),

        MediorSeedBuilders.codeDecisionQuestion(
            questionId = "M2.5",
            title = "Dopuni sistem akcija nad kalendarom bez rasipanja logike po UI sloju",
            prompt = """
            Kalendar aplikacija treba da podrži više akcija nad događajima:
            •	kreiranje, 
            •	pomeranje termina, 
            •	otkazivanje, 
            •	dupliranje događaja. 
            Iste akcije treba da se pokreću iz:
            •	toolbar-a, 
            •	desnog klika, 
            •	prečica na tastaturi, 
            •	automatizovanih pravila. 
            Tim želi da UI ne zna detalje izvršenja svake akcije i da sistem kasnije može podržati istoriju akcija.
            """.trimIndent(),
            codeBlock = """
            class CalendarService {
                createEvent(data) { ... }
                moveEvent(eventId, slot) { ... }
                cancelEvent(eventId) { ... }
                duplicateEvent(eventId) { ... }
            }

            class Toolbar {
                onCreateClick(data) { ??? }
                onMoveClick(eventId, slot) { ??? }
            }
            """.trimIndent(),
            solutionCards = listOf(
                "A" to """
                class UserAction {
                    private service
                    private type
                    private payload

                    constructor(service, type, payload) {
                        this.service = service
                        this.type = type
                        this.payload = payload
                    }

                    execute() {
                        if (type == "create") service.createEvent(payload)
                        else if (type == "move") service.moveEvent(payload.id, payload.slot)
                        else if (type == "cancel") service.cancelEvent(payload.id)
                    }
                }
                """.trimIndent(),
                "B" to """
                interface ActionRequest {
                    execute()
                }

                class CreateEventAction implements ActionRequest {
                    private service
                    private data

                    constructor(service, data) {
                        this.service = service
                        this.data = data
                    }

                    execute() {
                        service.createEvent(data)
                    }
                }

                class MoveEventAction implements ActionRequest {
                    private service
                    private eventId
                    private slot

                    constructor(service, eventId, slot) {
                        this.service = service
                        this.eventId = eventId
                        this.slot = slot
                    }

                    execute() {
                        service.moveEvent(eventId, slot)
                    }
                }
                """.trimIndent(),
                "C" to """
                class Toolbar {
                    onCreateClick(data) {
                        service.createEvent(data)
                    }

                    onMoveClick(eventId, slot) {
                        service.moveEvent(eventId, slot)
                    }
                }
                """.trimIndent(),
                "D" to """
                class CalendarFacade {
                    run(actionName, payload) {
                        log(actionName)
                        return payload
                    }
                }
                """.trimIndent()
            ),
            correctLetter = "B",
            aiFollowUp = "Zašto je arhitektonski bolji problem imati više malih klasa akcija nego jednu veliku “action switch” klasu?",
            wave = 3,
            orderIndex = 23
        ),

        MediorSeedBuilders.codeDecisionQuestion(
            questionId = "M2.6",
            title = "Dopuni dizajn za veliki broj sličnih markera na mapi",
            prompt = """
            Aplikacija prikazuje veliki broj oznaka na mapi. Mnoge oznake dele:
            •	istu ikonicu, 
            •	istu boju, 
            •	isti stil prikaza,
            dok se razlikuju po: 
            •	koordinatama, 
            •	labeli, 
            •	eventualno statusu. 
            Tim želi da smanji memorijski trošak, ali bez gubitka fleksibilnosti za pojedinačne instance.
            """.trimIndent(),
            codeBlock = """
            class MapMarker {
                icon
                color
                style
                lat
                lng
                label

                draw() {
                    render(icon, color, style, lat, lng, label)
                }
            }
            """.trimIndent(),
            solutionCards = listOf(
                "A" to """
                class MarkerVisual {
                    icon
                    color
                    style

                    constructor(icon, color, style) {
                        this.icon = icon
                        this.color = color
                        this.style = style
                    }

                    drawAt(lat, lng, label) {
                        render(icon, color, style, lat, lng, label)
                    }
                }

                class MapMarker {
                    visual
                    lat
                    lng
                    label

                    constructor(visual, lat, lng, label) {
                        this.visual = visual
                        this.lat = lat
                        this.lng = lng
                        this.label = label
                    }

                    draw() {
                        visual.drawAt(lat, lng, label)
                    }
                }
                """.trimIndent(),
                "B" to """
                class MapMarker {
                    icon
                    color
                    style
                    lat
                    lng
                    label
                    isShared = true
                }
                """.trimIndent(),
                "C" to """
                class MarkerFactory {
                    create(lat, lng, label) {
                        return new MapMarker("pin", "blue", "default", lat, lng, label)
                    }
                }
                """.trimIndent(),
                "D" to """
                class MarkerPrototype {
                    clone() { ... }
                }
                """.trimIndent()
            ),
            correctLetter = "A",
            aiFollowUp = "Zašto “praviti objekte brže” i “držati manje zajedničkih podataka po objektu” nisu isti problem?",
            wave = 3,
            orderIndex = 24
        ),

        MediorSeedBuilders.codeDecisionQuestion(
            questionId = "M2.7",
            title = "Dopuni dizajn za više konzistentnih paketa UI komponenti",
            prompt = """
            Portal treba da podrži više brendova. Kada se aktivira jedan brend, sistem mora da koristi odgovarajući skup:
            •	dugmadi, 
            •	input polja, 
            •	dijaloga. 
            Tim želi da spreči mešanje komponenti različitih brendova u istoj sesiji.
            """.trimIndent(),
            codeBlock = """
            interface Button {
                render()
            }

            interface InputField {
                render()
            }

            interface Dialog {
                render()
            }

            class BrandScreen {
                private button
                private input
                private dialog

                constructor(button, input, dialog) {
                    this.button = button
                    this.input = input
                    this.dialog = dialog
                }

                render() {
                    button.render()
                    input.render()
                    dialog.render()
                }
            }
            """.trimIndent(),
            solutionCards = listOf(
                "A" to """
                interface UiBundleProvider {
                    createButton()
                    createInput()
                    createDialog()
                }

                class PremiumBundleProvider implements UiBundleProvider {
                    createButton() { return new PremiumButton() }
                    createInput() { return new PremiumInput() }
                    createDialog() { return new PremiumDialog() }
                }
                """.trimIndent(),
                "B" to """
                class ScreenBuilder {
                    buttonType
                    inputType
                    dialogType

                    build() {
                        return new BrandScreen(
                            new Button(buttonType),
                            new InputField(inputType),
                            new Dialog(dialogType)
                        )
                    }
                }
                """.trimIndent(),
                "C" to """
                class ComponentSelector {
                    get(type) {
                        if (type == "button") return new PremiumButton()
                        if (type == "input") return new DefaultInput()
                        return new LegacyDialog()
                    }
                }
                """.trimIndent(),
                "D" to """
                class SingleUiComponent {
                    renderButton() { ... }
                    renderInput() { ... }
                    renderDialog() { ... }
                }
                """.trimIndent()
            ),
            correctLetter = "A",
            aiFollowUp = """
            Zašto je za ovaj sistem veći problem “mešanje nepovezanih komponenti” nego samo “način na koji se pojedinačno prave objekti”?
            """.trimIndent(),
            wave = 4,
            orderIndex = 33
        ),

        MediorSeedBuilders.codeDecisionQuestion(
            questionId = "M2.8",
            title = "Dopuni dizajn za ponašanje dokumenta po statusu",
            prompt = """
            Dokument u sistemu može biti:
            •	draft, 
            •	review, 
            •	approved, 
            •	archived. 
            Operacije edit(), submit(), approve() i archive() ne smeju da se ponašaju isto u svim statusima. Tim želi da doda nova stanja kasnije bez širenja centralne klase.
            """.trimIndent(),
            codeBlock = """
            class Document {
                private state

                constructor(state) {
                    this.state = state
                }

                edit() {
                    return state.edit(this)
                }

                submit() {
                    return state.submit(this)
                }

                approve() {
                    return state.approve(this)
                }

                changeState(nextState) {
                    this.state = nextState
                }
            }
            """.trimIndent(),
            solutionCards = listOf(
                "A" to """
                interface DocumentMode {
                    edit(document)
                    submit(document)
                    approve(document)
                }

                class DraftMode implements DocumentMode {
                    edit(document) { return "edited" }

                    submit(document) {
                        document.changeState(new ReviewMode())
                        return "submitted"
                    }

                    approve(document) { return "not allowed" }
                }
                """.trimIndent(),
                "B" to """
                class DocumentRules {
                    handle(document, action) {
                        if (document.state == "draft" && action == "submit") { ... }
                        if (document.state == "review" && action == "approve") { ... }
                    }
                }
                """.trimIndent(),
                "C" to """
                class DocumentFactory {
                    create(status) {
                        return new Document(status)
                    }
                }
                """.trimIndent(),
                "D" to """
                class HistorySnapshot {
                    save(document) { ... }
                }
                """.trimIndent()
            ),
            correctLetter = "A",
            aiFollowUp = """
            Zašto je arhitektonski korisno da prelaz iz draft u review bude deo logike stanja, a ne samo spoljašnja izmena string vrednosti?
            """.trimIndent(),
            wave = 4,
            orderIndex = 34
        ),

        MediorSeedBuilders.codeDecisionQuestion(
            questionId = "M2.9",
            title = "Dopuni eksport tok tako da različiti formati dele isti kostur obrade",
            prompt = """
            Sistem izvozi podatke u različite formate. Za svaki eksport tok uvek postoje isti koraci:
            •	učitavanje podataka, 
            •	osnovna priprema, 
            •	formatiranje, 
            •	upis rezultata. 
            Tim želi da:
            •	zadrži isti opšti tok obrade, 
            •	dozvoli da se pojedini koraci razlikuju po formatu, 
            •	izbegne kopiranje celog algoritma u svakoj eksport klasi.
            """.trimIndent(),
            codeBlock = """
            abstract class DataExport {
                export(source) {
                    data = load(source)
                    prepared = prepare(data)
                    formatted = format(prepared)
                    return write(formatted)
                }

                load(source) {
                    return source.read()
                }

                prepare(data) {
                    return cleanup(data)
                }

                abstract format(data)

                write(data) {
                    return saveToFile(data)
                }
            }
            """.trimIndent(),
            solutionCards = listOf(
                "A" to """
                class CsvExport extends DataExport {
                    format(data) {
                        return toCsv(data)
                    }
                }
                """.trimIndent(),
                "B" to """
                class ExportService {
                    export(type, source) {
                        if (type == "csv") {
                            data = source.read()
                            data = cleanup(data)
                            data = toCsv(data)
                            return saveToFile(data)
                        }

                        if (type == "json") {
                            data = source.read()
                            data = cleanup(data)
                            data = toJson(data)
                            return saveToFile(data)
                        }
                    }
                }
                """.trimIndent(),
                "C" to """
                class ExportFactory {
                    create(type) {
                        if (type == "csv") return new CsvExport()
                        return new JsonExport()
                    }
                }
                """.trimIndent(),
                "D" to """
                class ExportSnapshot {
                    save(data) {
                        return data
                    }
                }
                """.trimIndent()
            ),
            correctLetter = "A",
            aiFollowUp = """
            Zašto je korisno da redosled koraka ostane u baznoj klasi, čak i kada različiti eksporti imaju različitu logiku formatiranja?
            """.trimIndent(),
            wave = 5,
            orderIndex = 43
        ),

        MediorSeedBuilders.codeDecisionQuestion(
            questionId = "M2.10",
            title = "Dopuni dizajn za više analiza nad istom strukturom elemenata",
            prompt = """
            Sistem ima stabilnu strukturu čvorova dokumenta. Tim često dodaje nove operacije nad tim čvorovima:
            •	validaciju, 
            •	eksport, 
            •	statističku analizu, 
            •	pravila označavanja. 
            Ne žele da svaka nova operacija traži izmene u svim klasama elemenata.
            """.trimIndent(),
            codeBlock = """
            interface Node {
                accept(operation)
            }

            class TextNode implements Node {
                accept(operation) {
                    return operation.handleText(this)
                }
            }

            class TableNode implements Node {
                accept(operation) {
                    return operation.handleTable(this)
                }
            }
            """.trimIndent(),
            solutionCards = listOf(
                "A" to """
                interface NodeOperation {
                    handleText(node)
                    handleTable(node)
                }

                class ExportOperation implements NodeOperation {
                    handleText(node) { ... }
                    handleTable(node) { ... }
                }
                """.trimIndent(),
                "B" to """
                class NodeRules {
                    execute(node, mode) {
                        if (mode == "export" && node.type == "text") { ... }
                        if (mode == "validate" && node.type == "table") { ... }
                    }
                }
                """.trimIndent(),
                "C" to """
                class NodeFactory {
                    create(type) {
                        if (type == "text") return new TextNode()
                        return new TableNode()
                    }
                }
                """.trimIndent(),
                "D" to """
                class NodeSnapshot {
                    save(node) { ... }
                }
                """.trimIndent()
            ),
            correctLetter = "A",
            aiFollowUp = """
            Zašto je za tim bitno što se nova operacija dodaje kao nova celina, a ne razliva kroz sve klase čvorova?

            3. Treći tip zadatka: Povezivanje zahteva sistema sa obrascem projektovanja
            """.trimIndent(),
            wave = 5,
            orderIndex = 44
        )
    )
}
