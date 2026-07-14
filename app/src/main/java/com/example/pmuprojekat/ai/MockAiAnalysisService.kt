package com.example.pmuprojekat.ai

import javax.inject.Inject

class MockAiAnalysisService @Inject constructor() : AiAnalysisService {

    override suspend fun analyze(request: AiAnalysisRequest): Result<String> {
        val correctStep = request.steps.firstOrNull { it.wasCorrect }
        val incorrectStep = request.steps.firstOrNull { !it.wasCorrect }
        val focusStep = incorrectStep ?: request.steps.firstOrNull()
        val scoreSummary = when (request.scorePercent) {
            in Int.MIN_VALUE..40 ->
                "fokus treba da ostane na jednoj osnovnoj zameni pojmova iz tvog pokušaja"

            in 41..75 ->
                "imaš dobru osnovu, ali najslabiji korak traži jednu ciljanu korekciju"

            else ->
                "osnovu si savladao, pa sledeći korak treba da bude precizniji edge case ili trade-off"
        }

        val goodEvidence = if (correctStep != null) {
            "U koraku ${request.steps.indexOf(correctStep) + 1} (${correctStep.title}) tačno si odgovorio: " +
                shorten(correctStep.userAnswer)
        } else {
            "Nijedan korak nije označen kao potpuno tačan. Na osnovu dostupnog konteksta ne mogu precizno da " +
                "izdvojim sigurno usvojenu odluku, pa se fokus zadržava na prvoj proverljivoj razlici."
        }

        val mistakeEvidence = if (incorrectStep != null) {
            "U koraku ${request.steps.indexOf(incorrectStep) + 1} (${incorrectStep.title}) izabrao si: " +
                "${shorten(incorrectStep.userAnswer)} Očekivano je: ${shorten(incorrectStep.correctAnswer)}"
        } else {
            "Svi koraci su označeni kao tačni. Zato nema osnova da ti pripišem grešku; cilj je samo da preciziraš " +
                "odluku u zahtevnijem slučaju."
        }

        val followUpComment = if (request.aiFollowUpAnswer.isBlank()) {
            "Nisi uneo odgovor na AI pitanje, pa nema pisanog dokaza na osnovu kog bi se procenilo kako obrazlažeš " +
                "odluku ili trade-off."
        } else {
            "U odgovoru na AI pitanje napisao si: ${shorten(request.aiFollowUpAnswer)} Mock odgovor ovo koristi kao " +
                "dokaz, ali pravi servis dodatno proverava koji tačan koncept iz zadatka si pomenuo ili izostavio."
        }

        val architecturalExplanation = taskSpecificArchitecturalExplanation(request, focusStep)
        val reasoningSequence = taskSpecificReasoningSequence(request, focusStep)
        val nextLearningStep = taskSpecificNextStep(request, focusStep)

        return Result.success(
            """
            Ovo je mock analiza jer API ključ nije podešen. Tekst pokazuje očekivani oblik konkretne mentorske analize.

            Kratak zaključak

            Na zadatku ${request.questionId} postigao si ${request.scorePercent}%, pa $scoreSummary.

            Šta je dobro urađeno

            $goodEvidence

            Gde je nastala greška

            $mistakeEvidence

            Arhitektonsko objašnjenje

            $architecturalExplanation

            Kako bi bolje razmišljanje izgledalo

            $reasoningSequence

            Komentar na tvoj AI odgovor

            $followUpComment

            Sledeći korak za učenje

            ${nextLearningStep.render()}
            """.trimIndent()
        )
    }

    private fun taskSpecificArchitecturalExplanation(
        request: AiAnalysisRequest,
        focusStep: AiAnalysisStepContext?
    ): String {
        val context = combinedContext(request)

        return when {
            isAvailabilityReservationContext(context) ->
                "Katalog/read model i availability snapshot daju informaciju koja može kratko da kasni. " +
                    "Pokušaj rezervacije kod apoteke ostaje autoritativna poslovna radnja; mešanje ta dva nivoa " +
                    "može prikazati lek kao rezervisan iako apoteka rezervaciju nije potvrdila."

            isTransactionBrokerContext(context) ->
                "Transaction Service/Store ostaje izvor istine za finansijski zapis. OCR rezultat je predlog, a " +
                    "Message Broker prenosi događaj ka asinhronoj obradi; nijedan od njih ne preuzima validaciju, " +
                    "vlasništvo nad transakcijom ili audit pravila."

            isVideoDeliveryContext(context) ->
                "Backend API poseduje odluku o autorizaciji i izdaje vremenski ograničen signed URL. Object " +
                    "storage/CDN isporučuje video, ali ne odlučuje samostalno ko ima pravo pristupa."

            isFeedContext(context) ->
                "Personalizovani feed/read model je izvedeni prikaz koji može kasniti ili biti nedostupan. Osnovni " +
                    "tok učenja ostaje nezavisan, dok Personalization komponenta asinhrono računa signale i osvežava feed."

            isSeniorTradeOffTask(request) ->
                "U trade-off koraku nije dovoljno imenovati rešenje. Odluka mora biti povezana sa konkretnom " +
                    "koristi, prihvaćenim rizikom i uslovom pod kojim bi druga alternativa postala bolja."

            focusStep?.type == "code_completion" ->
                "Kod dopune pseudo-koda tačna linija mora da poštuje ugovor okolnog koda. Razlika između tvog i " +
                    "očekivanog unosa menja pozvanu odgovornost, tip ili redosled izvršavanja, a ne arhitekturu celog sistema."

            focusStep?.type == "ordered_cards" ->
                "Kod redosleda je ključna zavisnost: korak koji proizvodi ili potvrđuje podatak mora prethoditi " +
                    "koraku koji taj podatak koristi. Zamena tih mesta pravi preuranjenu odluku ili nepotpun tok."

            focusStep?.type == "categorization" ->
                "Kod zona svaka kartica mora biti svrstana po istom kriterijumu. Greška nastaje kada se posledica, " +
                    "uzrok, korist ili rizik procenjuju po različitim pravilima u istom koraku."

            focusStep?.type == "role_mapping" || focusStep?.type == "visual_mapping" ->
                "Mapiranje proverava odgovornost ili odnos između dve konkretne strane. Pogrešan par prebacuje " +
                    "ponašanje na ulogu koja ga ne poseduje."

            focusStep?.type == "mini_adr" ->
                "Mini ADR mora odvojiti kontekst, odluku, posledice i signal za promenu. Slab odgovor obično meša " +
                    "razlog za odluku sa posledicom ili ne navodi kada odluku treba ponovo otvoriti."

            focusStep != null ->
                "U koraku ${focusStep.title} treba uporediti tačan kriterijum iz očekivanog odgovora sa detaljem " +
                    "koji je tvoj izbor uveo ili izostavio. To je uži i proverljiviji zaključak od opšte tvrdnje o sistemu."

            else ->
                "Na osnovu dostupnog konteksta ne mogu precizno da tvrdim koji je koncept bio cilj zadatka."
        }
    }

    private fun taskSpecificReasoningSequence(
        request: AiAnalysisRequest,
        focusStep: AiAnalysisStepContext?
    ): String {
        val context = combinedContext(request)

        return when {
            isAvailabilityReservationContext(context) ->
                "Prvo utvrdi šta pretraga samo prikazuje iz kataloga/read modela. Zatim proveri koja radnja menja " +
                    "poslovno stanje: rezervacija kod apoteke. Na kraju definiši potvrdu ili fallback kada snapshot " +
                    "nije dovoljno svež."

            isTransactionBrokerContext(context) ->
                "Prvo sačuvaj validiran finansijski zapis u Transaction Service/Store. OCR tretiraj kao predlog za " +
                    "podatke, a Message Broker kao transport događaja ka analitici ili drugoj naknadnoj obradi."

            isVideoDeliveryContext(context) ->
                "Prvo proveri pravo pristupa u Backend API-ju, zatim izdvoj vremenski ograničen signed URL, pa prepusti " +
                    "isporuku object storage/CDN sloju bez prenošenja vlasništva nad autorizacijom."

            isFeedContext(context) ->
                "Prvo sačuvaj događaj iz osnovnog toka učenja. Zatim asinhrono izračunaj personalizacione signale i " +
                    "ažuriraj read model, a aplikaciji ostavi fallback kada pripremljeni feed nije dostupan."

            isSeniorTradeOffTask(request) ->
                "Prvo imenuj odluku i zahtev koji ona štiti. Zatim navedi merljivu korist, prihvaćeni rizik i jednu " +
                    "promenu ograničenja koja bi opravdala drugu odluku."

            focusStep?.type == "code_completion" ->
                "Pročitaj statički kod neposredno pre i posle praznog mesta, odredi ugovor koji nedostaje, pa unesi " +
                    "samo izraz ili liniju koja taj ugovor ispunjava."

            focusStep?.type == "ordered_cards" ->
                "Za svaki par susednih koraka pitaj koji podatak ili uslov prvi korak proizvodi i zašto ga sledeći " +
                    "korak mora sačekati."

            focusStep?.type == "categorization" ->
                "Najpre definiši po jednu kratku granicu za svaku zonu. Zatim svaku karticu svrstavanja proveri " +
                    "isključivo prema tim granicama."

            focusStep?.type == "role_mapping" || focusStep?.type == "visual_mapping" ->
                "Najpre imenuj odgovornost leve strane, zatim pronađi desnu stranu koja tu odgovornost tačno opisuje, " +
                    "pa proveri da li nijedna druga uloga nije preuzela isti posao."

            focusStep != null ->
                "Pođi od instrukcije koraka ${focusStep.title}, izdvoji kriterijum iz očekivanog odgovora i njime " +
                    "proveri svoj odgovor pre nego što doneseš širi zaključak."

            else ->
                "Najpre proveri koja informacija nedostaje u kontekstu, pa tek onda izvodi zaključak."
        }
    }

    private fun taskSpecificNextStep(
        request: AiAnalysisRequest,
        focusStep: AiAnalysisStepContext?
    ): LearningAction {
        val context = combinedContext(request)
        val highScore = request.scorePercent >= 76

        if (isAvailabilityReservationContext(context)) {
            return if (highScore) {
                LearningAction(
                    action = "opiši slučaj u kome availability snapshot prikazuje lek kao dostupan, ali apoteka odbije rezervaciju, i napiši očekivani fallback",
                    goal = "razliku između informativnog katalog/read model prikaza i autoritativne rezervacije kod apoteke",
                    success = "zašto snapshot sme da informiše korisnika, ali samo potvrda apoteke sme da potvrdi rezervaciju"
                )
            } else {
                LearningAction(
                    action = "napiši dve rečenice koje porede availability snapshot/read model sa pokušajem rezervacije kod apoteke iz pogrešnog koraka",
                    goal = "razliku između izvedene informacije i autoritativne poslovne odluke",
                    success = "koji deo sistema prikazuje moguću dostupnost, a koji jedini potvrđuje rezervaciju"
                )
            }
        }

        if (isTransactionBrokerContext(context)) {
            return if (highScore) {
                LearningAction(
                    action = "opiši šta se dešava kada je finansijski zapis sačuvan u Transaction Store-u, a Message Broker ili OCR privremeno nisu dostupni",
                    goal = "odvajanje autoritativnog finansijskog toka od sekundarne asinhrone obrade",
                    success = "zašto transakcija ostaje validna i koji se poslovi bezbedno ponavljaju kasnije"
                )
            } else {
                LearningAction(
                    action = "napiši po jednu odgovornost za Transaction Service/Store, OCR rezultat i Message Broker, pa označi jedini izvor istine",
                    goal = "precizno vlasništvo nad finansijskim zapisom naspram predloga i prenosa događaja",
                    success = "zašto OCR i Message Broker ne smeju potvrditi ili posedovati finansijsku transakciju"
                )
            }
        }

        if (isVideoDeliveryContext(context)) {
            return LearningAction(
                action = if (highScore) {
                    "objasni kako sistem reaguje kada signed URL istekne tokom gledanja, bez prebacivanja autorizacije na CDN"
                } else {
                    "napiši dve rečenice: jednu o autorizaciji i izdavanju signed URL-a u Backend API-ju, a drugu o isporuci videa preko object storage/CDN sloja"
                },
                goal = "razdvajanje vlasnika pristupa od sloja za isporuku sadržaja",
                success = "zašto CDN isporučuje video, ali Backend API odlučuje ko dobija novi signed URL"
            )
        }

        if (isFeedContext(context)) {
            return LearningAction(
                action = if (highScore) {
                    "opiši jedan fallback kada Personalization komponenta ili read model kasne, a osnovni tok učenja mora ostati dostupan"
                } else {
                    "napiši jednu sekvencu: događaj o učenju → stream/message sloj → Personalization → read model → čitanje feed-a, i uz poslednju stavku dodaj fallback"
                },
                goal = "event-driven personalizacioni tok i nezavisnost osnovnog toka učenja",
                success = "gde se računaju signali, gde se čuva izvedeni feed i šta aplikacija radi kada on nije spreman"
            )
        }

        if (isSeniorTradeOffTask(request)) {
            return LearningAction(
                action = if (highScore) {
                    "promeni jedno ograničenje iz ${request.title} i ponovo napiši odluku, korist, rizik i uslov promene"
                } else {
                    "za odluku iz ${focusStep?.title ?: request.title} napiši četiri kratke stavke: odluka, korist, rizik i uslov pod kojim bi promenio odluku"
                },
                goal = "trade-off koji je proveravan u konkretnom zadatku",
                success = "zašto izabrana korist opravdava rizik i kada druga alternativa postaje bolja"
            )
        }

        val selected = shorten(focusStep?.userAnswer.orEmpty(), 120)
        val expected = shorten(focusStep?.correctAnswer.orEmpty(), 120)
        val stepTitle = focusStep?.title ?: request.title

        if (highScore) {
            return highScoreLearningAction(focusStep, stepTitle, expected)
        }

        return when (focusStep?.type) {
            "single_choice", "multi_choice", "visual_mapping" -> LearningAction(
                action = "uporedi svoj izbor „$selected” sa očekivanim „$expected” i napiši jedno pravilo za prepoznavanje tačne opcije",
                goal = "konkretnu razliku proveravanu u koraku $stepTitle",
                success = "koji detalj čini očekivanu opciju tačnom, a tvoj izbor slabijim"
            )

            "code_completion" -> LearningAction(
                action = "prepiši samo pogrešno ili prazno mesto u koraku $stepTitle kao „$expected” i jednom rečenicom objasni njegovu ulogu",
                goal = "ugovor koji nedostajuća linija mora da ispuni",
                success = "zašto upravo taj izraz pripada između statičkih linija pseudo-koda"
            )

            "ordered_cards" -> LearningAction(
                action = "uporedi redosled „$selected” sa „$expected”, izdvoji prva dva zamenjena koraka i napiši zašto drugi zavisi od prvog",
                goal = "konkretnu zavisnost u redosledu koraka $stepTitle",
                success = "koji podatak ili uslov prvi korak obezbeđuje sledećem"
            )

            "categorization" -> LearningAction(
                action = "napravi dve kratke kolone za zone iz koraka $stepTitle i ispravi mapiranje „$selected” prema očekivanom „$expected”",
                goal = "kriterijum koji razdvaja konkretne zone iz zadatka",
                success = "zašto pogrešno svrstana kartica pripada očekivanoj zoni"
            )

            "role_mapping" -> LearningAction(
                action = "prepiši jedan pogrešan par iz „$selected” kao tačan par iz „$expected” i dodaj pravilo povezivanja",
                goal = "odgovornost koja povezuje dve strane mapiranja",
                success = "zašto ta uloga pripada baš tom saradniku ili elementu"
            )

            "hotspot" -> LearningAction(
                action = "prepiši samo problematični fragment iz koraka $stepTitle u ispravnom obliku „$expected” i navedi razlog izmene",
                goal = "tačan uzrok greške u označenom delu",
                success = "koje pogrešno ponašanje minimalna izmena uklanja"
            )

            "mini_adr" -> LearningAction(
                action = "prepiši samo slabu ili nedostajuću ADR sekciju u koraku $stepTitle koristeći očekivano „$expected”",
                goal = "razliku između odluke, posledice i signala za promenu",
                success = "koja tvrdnja pripada svakoj od tih ADR uloga"
            )

            "free_text" -> LearningAction(
                action = "prepravi odgovor iz koraka $stepTitle u dve rečenice tako da eksplicitno uključi „$expected”",
                goal = "koncept koji je izostao iz tvog pisanog odgovora",
                success = "kako taj koncept menja ili precizira zaključak"
            )

            else -> LearningAction(
                action = "napiši jednu rečenicu koja poredi „$selected” sa očekivanim „$expected” u koraku $stepTitle",
                goal = "najužu proverljivu razliku između svog i očekivanog odgovora",
                success = "koji konkretan kriterijum treba primeniti sledeći put"
            )
        }
    }

    private fun highScoreLearningAction(
        focusStep: AiAnalysisStepContext?,
        stepTitle: String,
        expected: String
    ): LearningAction {
        return when (focusStep?.type) {
            "code_completion" -> LearningAction(
                action = "napiši jednu alternativnu implementaciju za „$expected” u koraku $stepTitle koja čuva isti ugovor",
                goal = "razliku između sintakse rešenja i ponašanja koje ugovor zahteva",
                success = "zašto su obe varijante ispravne i kada bi izabrao jednu od njih"
            )

            "ordered_cards" -> LearningAction(
                action = "izaberi jedan susedni par iz očekivanog redosleda „$expected” i predvidi tačan kvar ako im zameniš mesta",
                goal = "skrivenu zavisnost između koraka $stepTitle",
                success = "koji uslov više ne bi bio ispunjen posle zamene"
            )

            "categorization" -> LearningAction(
                action = "smisli jedan granični primer za zone iz koraka $stepTitle, svrstaj ga i odbrani kriterijum",
                goal = "finu granicu između dve bliske kategorije",
                success = "zašto primer pripada jednoj zoni iako liči na drugu"
            )

            "mini_adr" -> LearningAction(
                action = "dodaj jedan merljiv signal zbog kog bi ponovo otvorio odluku iz koraka $stepTitle",
                goal = "razliku između trenutne odluke i uslova za njenu promenu",
                success = "kada posledice više ne opravdavaju postojeću odluku"
            )

            else -> LearningAction(
                action = "uporedi očekivano „$expected” sa najbližom alternativom iz koraka $stepTitle i promeni jedan uslov koji bi alternativu učinio boljom",
                goal = "granicu važenja tačnog odgovora",
                success = "koji konkretan trade-off menja odluku bez vraćanja na osnovnu definiciju"
            )
        }
    }

    private fun isSeniorTradeOffTask(request: AiAnalysisRequest): Boolean {
        val type = request.type.lowercase()
        return type.contains("trade_off") ||
            type.contains("trade-off") ||
            type.contains("optimization_strategy") ||
            type.contains("prioritization") ||
            type.contains("architectural_compromise")
    }

    private fun combinedContext(request: AiAnalysisRequest): String {
        return buildString {
            append(request.title)
            append(' ')
            append(request.prompt)
            append(' ')
            request.steps.forEach { step ->
                append(step.title)
                append(' ')
                append(step.instruction)
                append(' ')
                append(step.correctAnswer)
                append(' ')
                append(step.userAnswer)
                append(' ')
            }
            append(request.aiFollowUpQuestion.orEmpty())
            append(' ')
            append(request.aiFollowUpAnswer)
        }.lowercase()
    }

    private fun isTransactionBrokerContext(context: String): Boolean {
        return (context.contains("message broker") || context.contains("broker")) &&
            listOf("transaction", "transakc", "expense", "troš", "ocr", "analytics", "analitik")
                .any(context::contains)
    }

    private fun isAvailabilityReservationContext(context: String): Boolean {
        return listOf("availability", "dostup", "snapshot", "apot", "pharmacy", "lek", "medicine")
            .any(context::contains) &&
            listOf("rezerv", "reservation", "read model", "katalog", "catalog").any(context::contains)
    }

    private fun isVideoDeliveryContext(context: String): Boolean {
        return listOf("signed url", "cdn", "object storage", "video").any(context::contains)
    }

    private fun isFeedContext(context: String): Boolean {
        return listOf("feed", "personaliz", "progress module").any(context::contains)
    }

    private fun shorten(value: String, maxLength: Int = 220): String {
        val normalized = value.replace(Regex("\\s+"), " ").trim()
        if (normalized.isBlank()) return "nema odgovora"
        return if (normalized.length <= maxLength) normalized else normalized.take(maxLength).trimEnd() + "..."
    }

    private data class LearningAction(
        val action: String,
        val goal: String,
        val success: String
    ) {
        fun render(): String {
            return """
                Sledeće uradi: ${action.trimEnd('.')}.
                Cilj je da učvrstiš: ${goal.trimEnd('.')}.
                Proveri sebe tako što ćeš moći da objasniš: ${success.trimEnd('.')}.
            """.trimIndent()
        }
    }
}
