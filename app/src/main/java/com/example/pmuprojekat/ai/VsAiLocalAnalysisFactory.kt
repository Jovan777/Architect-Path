package com.example.pmuprojekat.ai

import kotlin.math.roundToInt

object VsAiLocalAnalysisFactory {
    fun create(request: VsAiFinalAnalysisRequest): VsAiFinalAnalysis {
        val userAnswers = request.conversation.filter { it.role == VsAiConversationRole.USER }
        val scores = request.evaluationSignals.map(VsAiEvaluationSignal::satisfactionScore)
        val score = scores.takeIf(List<Int>::isNotEmpty)
            ?.average()
            ?.roundToInt()
            ?.coerceIn(0, 100)
            ?: localScoreFromAnswers(userAnswers)
        val strengths = request.evaluationSignals
            .flatMap(VsAiEvaluationSignal::strengths)
            .distinct()
            .joinToString(" ")
            .ifBlank {
                if (userAnswers.isEmpty()) {
                    "Nije unet odgovor na početno pitanje."
                } else {
                    "Odgovorio si na pitanje i zadržao fokus na temi „${request.challenge.targetConcept}“."
                }
            }
        val weaknesses = request.evaluationSignals
            .flatMap(VsAiEvaluationSignal::weaknesses)
            .distinct()
            .joinToString(" ")
            .ifBlank {
                "Bez završnog AI poziva ne mogu pouzdano da izdvojim dodatnu konceptualnu slabost."
            }

        return VsAiFinalAnalysis(
            score = score,
            resultSummary = "AI servis nije bio dostupan za završnu procenu, pa je pokušaj sačuvan uz ograničenu lokalnu analizu. ${completionText(request.completionReason)}",
            strengths = strengths,
            weakerPoints = weaknesses,
            reasoningDevelopment = when {
                userAnswers.isEmpty() -> "Izazov je završen pre prvog odgovora."
                userAnswers.size == 1 -> "Dostupan je jedan odgovor, pa razvoj razmišljanja nije moguće pouzdano uporediti kroz više rundi."
                else -> "Odgovarao si kroz ${userAnswers.size} runde; lokalna procena čuva tok, ali ne izmišlja zaključak koji AI nije potvrdio."
            },
            keyConcepts = listOf(request.challenge.targetConcept),
            nextConcreteStep = nextStep(request.levelContext, request.challenge.targetConcept)
        )
    }

    private fun localScoreFromAnswers(answers: List<VsAiConversationTurn>): Int {
        if (answers.isEmpty()) return 0
        val averageLength = answers.map { it.content.length }.average()
        return (25 + (averageLength / 12.0)).roundToInt().coerceIn(25, 65)
    }

    private fun completionText(reason: VsAiCompletionReason): String {
        return when (reason) {
            VsAiCompletionReason.AI_SATISFIED -> "Poslednja procena je označila odgovor kao dovoljan."
            VsAiCompletionReason.USER_STOPPED -> "Izazov je prekinut na tvoj zahtev."
            VsAiCompletionReason.MAX_ROUNDS -> "Dostignut je maksimalan broj rundi."
            VsAiCompletionReason.ERROR -> "Izazov je završen zbog greške servisa."
        }
    }

    private fun nextStep(level: VsAiLevelContext, targetConcept: String): String {
        return when (level.levelId) {
            "beginner" -> "U dve rečenice objasni pojam „$targetConcept“ i dodaj jedan jednostavan primer."
            "junior" -> "Napiši koja klasa poseduje odgovornost u temi „$targetConcept“ i zašto."
            "medior" -> "Za „$targetConcept“ zapiši autoritativno stanje, izvedeni prikaz i jednu posledicu kašnjenja."
            "senior" -> "Za „$targetConcept“ zapiši odluku, korist, rizik i uslov pod kojim bi odluku promenio."
            else -> "Napiši mini-ADR za „$targetConcept“: odluka, granica odgovornosti, trade-off i signal za promenu."
        }
    }
}
