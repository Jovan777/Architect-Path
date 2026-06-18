package com.example.pmuprojekat.ai

import javax.inject.Inject

class MockAiAnalysisService @Inject constructor() : AiAnalysisService {

    override suspend fun analyze(request: AiAnalysisRequest): Result<String> {
        val scoreBand = when {
            request.scorePercent >= 80 -> "imaš solidnu osnovu, pa je najkorisnije da sada tražiš finije arhitektonske razlike"
            request.scorePercent >= 50 -> "dobro si uradio deo rezonovanja, ali u konkretnim koracima treba oštrije da razlikuješ uzrok, posledicu i redosled odluke"
            else -> "najkorisnije je da se fokusiraš na jedan glavni obrazac greške, umesto na sve detalje odjednom"
        }

        val correctStep = request.steps.firstOrNull { it.wasCorrect }
        val incorrectStep = request.steps.firstOrNull { !it.wasCorrect }
        val evidenceStep = incorrectStep ?: request.steps.firstOrNull()

        val goodEvidence = if (correctStep != null) {
            "U koraku ${request.steps.indexOf(correctStep) + 1} (${correctStep.title}) dobro si došao do tačnog odgovora: ${shorten(correctStep.userAnswer)}"
        } else {
            "U ovom pokušaju nema koraka koji je označen kao potpuno tačan, pa je korisno da prvo pronađeš najmanji stabilan deo rezonovanja."
        }

        val mistakeEvidence = if (incorrectStep != null) {
            "U koraku ${request.steps.indexOf(incorrectStep) + 1} (${incorrectStep.title}) izabrao si: ${shorten(incorrectStep.userAnswer)} Očekivano je: ${shorten(incorrectStep.correctAnswer)}"
        } else if (evidenceStep != null) {
            "Svi koraci su označeni kao tačni, pa ovde ima smisla da tražiš samo finija poboljšanja u objašnjenju i trade-off analizi."
        } else {
            "Nema dostupnih koraka za konkretnu analizu."
        }

        val followUpComment = if (request.aiFollowUpAnswer.isBlank()) {
            "Nisi uneo odgovor na AI follow-up. Zato mock ne može da proveri da li umeš verbalno da povežeš odluku sa trade-offom iz zadatka."
        } else {
            "U follow-up odgovoru napisao si: ${shorten(request.aiFollowUpAnswer)} Pravi model bi proverio da li taj odgovor pominje iste koncepte koji se vide u tačnim i pogrešnim koracima."
        }

        val nextLearningStep = taskSpecificNextStep(request, incorrectStep)
        val architecturalExplanation = taskSpecificArchitecturalExplanation(request)
        val reasoningSequence = taskSpecificReasoningSequence(request)

        return Result.success(
            """
            Ovo je mock analiza jer API ključ nije podešen. Tekst ispod pokazuje kako treba da izgleda konkretna, evidence-based mentorska analiza.

            Kratak zaključak

            Na osnovu zadatka ${request.questionId} i rezultata ${request.scorePercent}%, $scoreBand. Mock koristi konkretne korake iz tvog pokušaja, umesto opštih rečenica.

            Šta je dobro urađeno

            $goodEvidence. To je signal da bar deo zadatka nisi rešavao napamet, već si pogodio vezu između zahteva i ponuđenog rešenja.

            Gde je nastala greška

            $mistakeEvidence. Ovde bi bilo korisno jasnije povezati šta tvoj izbor premešta ili preskače u odnosu na očekivani tok.

            Arhitektonsko objašnjenje

            $architecturalExplanation

            Kako bi bolje razmišljanje izgledalo

            $reasoningSequence

            Komentar na tvoj AI odgovor

            $followUpComment

            Sledeći korak za učenje

            $nextLearningStep
            """.trimIndent()
        )
    }

    private fun taskSpecificArchitecturalExplanation(request: AiAnalysisRequest): String {
        val context = combinedContext(request)

        return when {
            isAvailabilityReservationContext(context) -> {
                "Katalog/read model i availability snapshot služe da brzo prikažu informaciju o leku i mogućoj dostupnosti, ali ta informacija može biti zastarela. Autoritativni deo toka je pokušaj rezervacije kod apoteke: tek potvrda apoteke menja stvarno poslovno stanje i govori da je lek zaista rezervisan."
            }

            isTransactionBrokerContext(context) -> {
                "Transaction Service ili Transaction Store treba da ostane autoritativni izvor finansijskog zapisa. Message Broker je važan za pokretanje naknadne obrade i razdvajanje sporijih procesa kao što su analitika, OCR ili notifikacije, ali ne sme preuzeti ulogu izvora istine niti zameniti validaciju, autorizaciju i audit pravila."
            }

            isVideoDeliveryContext(context) -> {
                "Backend API treba da odluči da li imaš pravo pristupa, ali ne treba da nosi ceo tok isporuke velikog video sadržaja. Preciznija podela je: API proverava pravo, izdaje vremenski ograničen signed URL, a object storage/CDN sloj preuzima isporuku sadržaja."
            }

            isFeedContext(context) -> {
                "Kod personalizovanog feed-a važno je razdvojiti osnovni tok učenja od naknadnog računanja signala. Događaji mogu asinhrono ažurirati read model, dok aplikacija čita već pripremljen prikaz i ima fallback ako personalizovani feed nije spreman."
            }

            else -> {
                "Razliku između svog i očekivanog odgovora veži za konkretan trade-off iz zadatka: ko je izvor istine, koji tok mora ostati brz, šta može biti asinhrono i koja komponenta samo reaguje na događaj."
            }
        }
    }

    private fun taskSpecificReasoningSequence(request: AiAnalysisRequest): String {
        val context = combinedContext(request)

        return when {
            isAvailabilityReservationContext(context) -> {
                "Prvo izdvoji informativni tok: pretraga leka čita katalog/read model i availability snapshot da bi brzo prikazala moguće opcije. Zatim izdvoji autoritativni tok: rezervacija mora ići ka apoteci, gde se potvrđuje stvarna dostupnost. Ako potvrda ne uspe, aplikacija treba da prikaže fallback poruku umesto da tretira snapshot kao garanciju."
            }

            isTransactionBrokerContext(context) -> {
                "Prvo izdvoji kritični tok: unos troška i čuvanje finansijskog zapisa. Zatim odvoji sporije ili sekundarne procese, kao što su OCR, analitika i upozorenja, u asinhroni tok preko Message Broker-a. Tako Transaction Service ostaje izvor istine, a ostale komponente reaguju na događaj bez blokiranja osnovnog unosa."
            }

            isVideoDeliveryContext(context) -> {
                "Prvo izdvoji poslovnu odluku: da li imaš pravo da gledaš video. Zatim odvoji tehničku isporuku velikog fajla: object storage/CDN treba da servira sadržaj preko vremenski ograničenog URL-a, dok Backend API ostaje zadužen za autorizaciju i izdavanje tog URL-a."
            }

            isFeedContext(context) -> {
                "Prvo izdvoji događaj iz osnovnog toka učenja. Zatim pusti da stream/message sloj prenese signal do Personalization komponente, koja ažurira read model. Na kraju aplikacija čita pripremljeni feed, ali osnovni tok učenja ne sme zavisiti od toga da feed uvek bude svež ili dostupan."
            }

            else -> {
                "Za konkretan korak prvo odredi koji tok mora biti tačan i brz, zatim ko je vlasnik podataka, a tek onda šta sme da bude asinhrono, keširano, izračunato naknadno ili prebačeno u read model."
            }
        }
    }

    private fun taskSpecificNextStep(
        request: AiAnalysisRequest,
        incorrectStep: AiAnalysisStepContext?
    ): String {
        val combinedContext = combinedContext(request)

        return when {
            isAvailabilityReservationContext(combinedContext) -> {
                "Nacrtaj tok: pretraga leka -> katalog/read model -> availability snapshot -> pokušaj rezervacije kod apoteke -> potvrda ili fallback poruka. Posebno označi da je snapshot samo informativan, a rezervacija kod apoteke autoritativna."
            }

            isTransactionBrokerContext(combinedContext) -> {
                "Nacrtaj tok: unos troška -> čuvanje transakcije u Transaction Service/Store -> čuvanje slike računa kao odvojenog artefakta -> slanje događaja preko Message Broker-a -> asinhrono ažuriranje analitike ili OCR obrada. Posebno označi šta je izvor istine, a šta samo reaguje na događaj."
            }

            isVideoDeliveryContext(combinedContext) -> {
                "Nacrtaj tok: Backend API proverava pravo pristupa -> izdaje vremenski ograničen signed URL -> video se isporučuje preko object storage/CDN sloja -> pristup se ponovo proverava pri sledećem zahtevu za URL."
            }

            isFeedContext(combinedContext) -> {
                "Nacrtaj tok: događaj o učenju -> stream/message sloj -> Personalization komponenta računa signale -> read model se ažurira -> aplikacija čita pripremljeni feed uz fallback ako feed nije dostupan."
            }

            else -> {
                val stepLabel = incorrectStep?.let {
                    "korak ${request.steps.indexOf(it) + 1}"
                } ?: "jedan provereni korak"
                "Ponovi $stepLabel i napiši jednu rečenicu: \"Moj izbor menja ___, dok očekivani odgovor čuva ili poboljšava ___.\""
            }
        }
    }

    private fun combinedContext(request: AiAnalysisRequest): String {
        return buildString {
            append(request.title)
            append(" ")
            append(request.prompt)
            append(" ")
            request.steps.forEach { step ->
                append(step.title)
                append(" ")
                append(step.instruction)
                append(" ")
                append(step.correctAnswer)
                append(" ")
                append(step.userAnswer)
                append(" ")
            }
            append(request.aiFollowUpQuestion.orEmpty())
            append(" ")
            append(request.aiFollowUpAnswer)
        }.lowercase()
    }

    private fun isTransactionBrokerContext(context: String): Boolean {
        return (context.contains("message broker") || context.contains("broker")) &&
                (
                        context.contains("transaction") ||
                                context.contains("transakc") ||
                                context.contains("expense") ||
                                context.contains("troš") ||
                                context.contains("ocr") ||
                                context.contains("analytics") ||
                                context.contains("analitik")
                        )
    }

    private fun isAvailabilityReservationContext(context: String): Boolean {
        return (
                context.contains("availability") ||
                        context.contains("dostup") ||
                        context.contains("snapshot") ||
                        context.contains("apot") ||
                        context.contains("pharmacy") ||
                        context.contains("lek") ||
                        context.contains("medicine")
                ) &&
                (
                        context.contains("rezerv") ||
                                context.contains("reservation") ||
                                context.contains("read model") ||
                                context.contains("katalog") ||
                                context.contains("catalog")
                        )
    }

    private fun isVideoDeliveryContext(context: String): Boolean {
        return context.contains("signed url") ||
                context.contains("cdn") ||
                context.contains("object storage") ||
                context.contains("video")
    }

    private fun isFeedContext(context: String): Boolean {
        return context.contains("feed") ||
                context.contains("read model") ||
                context.contains("personaliz") ||
                context.contains("progress")
    }

    private fun shorten(value: String, maxLength: Int = 220): String {
        val normalized = value
            .replace(Regex("\\s+"), " ")
            .trim()

        return if (normalized.length <= maxLength) {
            normalized
        } else {
            normalized.take(maxLength).trimEnd() + "..."
        }
    }
}
