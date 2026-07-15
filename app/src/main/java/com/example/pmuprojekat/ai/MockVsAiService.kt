package com.example.pmuprojekat.ai

import javax.inject.Inject
import kotlin.math.roundToInt

class MockVsAiService @Inject constructor() : VsAiService {

    override suspend fun generateChallenge(
        request: VsAiStartRequest
    ): Result<VsAiGeneratedChallenge> = Result.success(
        mockChallenges.getValue(request.levelContext.levelId)
    )

    override suspend fun evaluateAnswer(
        request: VsAiEvaluationRequest
    ): Result<VsAiEvaluationResult> = runCatching {
        val answer = request.conversation
            .lastOrNull { it.role == VsAiConversationRole.USER }
            ?.content
            .orEmpty()
        val score = scoreAnswer(request.levelContext.levelId, answer)
        val isSatisfied = score >= request.levelContext.minimumExpectedAnswerQuality
        val reachedMaximum = request.currentRound >= request.levelContext.maximumRounds
        val shouldContinue = !isSatisfied && !reachedMaximum

        val strengths = buildList {
            if (answer.split(Regex("\\s+")).count { it.isNotBlank() } >= 8) {
                add("Dao si obrazložen odgovor, a ne samo naziv rešenja.")
            }
            if (containsReasoningConnector(answer)) {
                add("Povezao si odluku sa razlogom ili posledicom.")
            }
        }
        val weaknesses = if (isSatisfied) {
            emptyList()
        } else {
            listOf(weaknessFor(request.levelContext.levelId))
        }

        VsAiEvaluationResult(
            visibleMessage = when {
                isSatisfied -> "Odgovor je dovoljno precizan za nivo ${request.levelContext.displayName}."
                reachedMaximum -> "Dostigao si maksimalan broj rundi; završavam izazov na osnovu dosadašnjih odgovora."
                else -> "Dobar početak, ali odgovor još nema jednu važnu vezu potrebnu za ovaj nivo."
            },
            shouldContinue = shouldContinue,
            isSatisfied = isSatisfied,
            satisfactionScore = score,
            nextQuestion = if (shouldContinue) followUpFor(request.levelContext.levelId) else null,
            targetConcepts = listOf(request.challenge.targetConcept),
            detectedStrengths = strengths,
            detectedWeaknesses = weaknesses,
            reasonForContinuingOrStopping = when {
                isSatisfied -> "Odgovor je prešao prag za izabrani nivo."
                reachedMaximum -> "Dostignut je maksimalan broj rundi."
                else -> "Nedostaje: ${weaknesses.first()}"
            }
        )
    }

    override suspend fun generateFinalAnalysis(
        request: VsAiFinalAnalysisRequest
    ): Result<VsAiFinalAnalysis> = runCatching {
        val signalScore = request.evaluationSignals
            .map(VsAiEvaluationSignal::satisfactionScore)
            .takeIf(List<Int>::isNotEmpty)
            ?.average()
            ?.roundToInt()
        val userAnswers = request.conversation.filter { it.role == VsAiConversationRole.USER }
        val fallbackScore = scoreAnswer(
            request.levelContext.levelId,
            userAnswers.lastOrNull()?.content.orEmpty()
        )
        val score = (signalScore ?: fallbackScore).coerceIn(0, 100)
        val strengths = request.evaluationSignals
            .flatMap(VsAiEvaluationSignal::strengths)
            .distinct()
            .joinToString(" ")
            .ifBlank { "Odgovorio si na početno pitanje i ostao u okviru ciljnog koncepta." }
        val weaknesses = request.evaluationSignals
            .flatMap(VsAiEvaluationSignal::weaknesses)
            .distinct()
            .joinToString(" ")
            .ifBlank { "Nije ostala velika konceptualna praznina u lokalnoj proceni." }

        VsAiFinalAnalysis(
            score = score,
            resultSummary = buildString {
                append("Ovo je lokalna VS AI procena jer API ključ nije podešen. ")
                append(completionText(request.completionReason))
            },
            strengths = strengths,
            weakerPoints = weaknesses,
            reasoningDevelopment = if (userAnswers.size > 1) {
                "Kroz ${userAnswers.size} odgovora dopunjavao si početno obrazloženje nakon konkretnih provokacija."
            } else {
                "Procena se zasniva na jednom odgovoru, pa razvoj razmišljanja još nije mogao pouzdano da se vidi."
            },
            keyConcepts = listOf(request.challenge.targetConcept),
            nextConcreteStep = nextStepFor(
                levelId = request.levelContext.levelId,
                targetConcept = request.challenge.targetConcept
            )
        )
    }

    private fun scoreAnswer(levelId: String, answer: String): Int {
        val words = answer.split(Regex("\\s+")).count { it.isNotBlank() }
        val targetWords = when (levelId) {
            "beginner" -> 8
            "junior" -> 15
            "medior" -> 24
            "senior" -> 32
            else -> 40
        }
        val lengthScore = ((words.toDouble() / targetWords) * 55).roundToInt().coerceIn(0, 55)
        val normalized = answer.lowercase()
        val markerHits = markersFor(levelId).count(normalized::contains)
        val conceptScore = (markerHits * 9).coerceAtMost(32)
        val reasoningScore = if (containsReasoningConnector(answer)) 13 else 0
        return (lengthScore + conceptScore + reasoningScore).coerceIn(0, 100)
    }

    private fun containsReasoningConnector(answer: String): Boolean {
        val normalized = answer.lowercase()
        return listOf("jer", "zato", "posledic", "rizik", "ako", "dok").any(normalized::contains)
    }

    private fun markersFor(levelId: String): List<String> {
        return when (levelId) {
            "beginner" -> listOf("interfejs", "klasa", "objekat", "skriva", "odgovornost")
            "junior" -> listOf("strategy", "interfejs", "implement", "zavisnost", "odgovornost")
            "medior" -> listOf("asinhron", "read model", "cache", "izvor", "kašn", "posledic")
            "senior" -> listOf("trade-off", "rizik", "fallback", "kvar", "pouzdan", "audit")
            else -> listOf(
                "source of truth",
                "izvor istine",
                "autoritativ",
                "read model",
                "granica",
                "eventual",
                "trade-off",
                "fallback"
            )
        }
    }

    private fun weaknessFor(levelId: String): String {
        return when (levelId) {
            "beginner" -> "jasnije razlikuj osnovne uloge pojmova iz pitanja"
            "junior" -> "objasni zašto odgovornost pripada konkretnoj klasi ili obrascu"
            "medior" -> "poveži odluku sa tokom podataka i jednom posledicom"
            "senior" -> "navedi rizik, fallback i uslov pod kojim bi promenio odluku"
            else -> "preciziraj granicu, autoritativno stanje i trade-off odluke"
        }
    }

    private fun followUpFor(levelId: String): String {
        return when (levelId) {
            "beginner" -> "Objasni istu razliku jednom kratkom rečenicom i dodaj jednostavan primer."
            "junior" -> "Koja klasa poseduje tu odgovornost i zašto je ne bi trebalo smestiti u klijentski kod?"
            "medior" -> "Koja posledica nastaje ako taj podatak kasni i koji deo sistema ostaje autoritativan?"
            "senior" -> "Koji failure mode prvo očekuješ, koji fallback biraš i kada bi promenio odluku?"
            else -> "Precizno odvoji izvor istine od izvedenog prikaza i odbrani kako sistem radi pri parcijalnom kvaru."
        }
    }

    private fun nextStepFor(levelId: String, targetConcept: String): String {
        return when (levelId) {
            "beginner" -> "Napiši dve rečenice koje porede uloge u pojmu „$targetConcept“ i dodaj jedan mali primer."
            "junior" -> "Napiši jednu odgovornost glavne klase i jednu odgovornost njene Strategy implementacije za „$targetConcept“."
            "medior" -> "U tri stavke navedi tok, autoritativno stanje i posledicu kašnjenja za „$targetConcept“."
            "senior" -> "Zapiši odluku, korist, rizik i uslov promene odluke za „$targetConcept“."
            else -> "Napiši mini-ADR od četiri reda za „$targetConcept“: odluka, izvor istine, prihvaćeni trade-off i signal za promenu."
        }
    }

    private fun completionText(reason: VsAiCompletionReason): String {
        return when (reason) {
            VsAiCompletionReason.AI_SATISFIED -> "Izazov je završen jer je odgovor dostigao očekivani nivo."
            VsAiCompletionReason.USER_STOPPED -> "Izazov je prekinut na tvoj zahtev."
            VsAiCompletionReason.MAX_ROUNDS -> "Izazov je završen dostizanjem maksimalnog broja rundi."
            VsAiCompletionReason.ERROR -> "Izazov je završen zbog nedostupnosti AI servisa."
        }
    }

    private companion object {
        val mockChallenges = mapOf(
            "beginner" to VsAiGeneratedChallenge(
                question = "Koja je razlika između interfejsa i konkretne klase i zašto je ta razlika korisna?",
                targetConcept = "interfejs i konkretna implementacija",
                expectedAnswerGuide = "Interfejs definiše ugovor, konkretna klasa ga sprovodi; razdvajanje olakšava zamenu implementacije.",
                difficultyLabel = "osnovno",
                reasonChosen = "Pitanje proverava osnovno razdvajanje ugovora i implementacije."
            ),
            "junior" to VsAiGeneratedChallenge(
                question = "Kako bi Strategy obrazac uklonio rastući if-else za različite načine obračuna popusta?",
                targetConcept = "Strategy i raspodela odgovornosti",
                expectedAnswerGuide = "Zajednički interfejs, posebna implementacija po algoritmu i kontekst koji zavisi od apstrakcije.",
                difficultyLabel = "primena",
                reasonChosen = "Pitanje proverava praktičnu primenu obrasca i uloge klasa."
            ),
            "medior" to VsAiGeneratedChallenge(
                question = "Sistem prikazuje status narudžbine iz read modela. Šta se menja ako taj prikaz kasni nekoliko sekundi?",
                targetConcept = "read model i eventualna konzistentnost",
                expectedAnswerGuide = "Read model je izveden i može kasniti; autoritativni servis poseduje stanje; UI treba da podnese zastarelost.",
                difficultyLabel = "sistemski tok",
                reasonChosen = "Pitanje traži posledicu, a ne samo definiciju read modela."
            ),
            "senior" to VsAiGeneratedChallenge(
                question = "Notification servis je nedostupan tokom potvrde narudžbine. Kako štitiš osnovni tok i koji rizik prihvataš?",
                targetConcept = "parcijalni kvar i asinhroni fallback",
                expectedAnswerGuide = "Narudžbina ostaje autoritativno potvrđena; obaveštenje je asinhrono, ponovljivo i nadzirano; navesti rizik kašnjenja.",
                difficultyLabel = "trade-off",
                reasonChosen = "Pitanje proverava pouzdanost i poslovnu posledicu parcijalnog kvara."
            ),
            "architect" to VsAiGeneratedChallenge(
                question = "Pretraga leka koristi availability snapshot. Kako sprečavaš da informativna dostupnost bude pogrešno predstavljena kao potvrđena rezervacija?",
                targetConcept = "izvedeni prikaz i autoritativna domenska odluka",
                expectedAnswerGuide = "Snapshot/read model je informativan; apoteka autoritativno potvrđuje rezervaciju; potreban je jasan fallback pri promeni zalihe.",
                difficultyLabel = "odbrana arhitekture",
                reasonChosen = "Pitanje proverava granice, izvor istine i ponašanje pri zastarelom prikazu."
            )
        )
    }
}
