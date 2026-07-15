package com.example.pmuprojekat.ai

object VsAiEvaluationPolicy {
    fun normalize(
        levelContext: VsAiLevelContext,
        challenge: VsAiGeneratedChallenge,
        round: Int,
        result: VsAiEvaluationResult
    ): VsAiEvaluationResult {
        val maximumReached = round >= levelContext.maximumRounds
        val meetsLevelThreshold = result.isSatisfied &&
            result.satisfactionScore >= levelContext.minimumExpectedAnswerQuality
        val shouldContinue = !maximumReached && (result.shouldContinue || !meetsLevelThreshold)
        val nextQuestion = if (shouldContinue) {
            result.nextQuestion?.takeIf { it.isNotBlank() }
                ?: fallbackFollowUp(levelContext.levelId, challenge.targetConcept)
        } else {
            null
        }

        return result.copy(
            shouldContinue = shouldContinue,
            isSatisfied = meetsLevelThreshold,
            nextQuestion = nextQuestion
        )
    }

    private fun fallbackFollowUp(levelId: String, targetConcept: String): String {
        return when (levelId) {
            "beginner" ->
                "Pojasni svojim rečima šta znači $targetConcept i navedi jedan jednostavan primer."
            "junior" ->
                "Dopuni odgovor: koja komponenta ili klasa je odgovorna za $targetConcept i zašto?"
            "medior" ->
                "Objasni jednu konkretnu posledicu odluke vezane za $targetConcept i šta se dešava pri grešci."
            "senior" ->
                "Odbrani odluku za $targetConcept: navedi korist, glavni rizik i uslov pod kojim bi je promenio."
            "architect" ->
                "Preciziraj $targetConcept: odredi granicu odgovornosti, autoritativno stanje i glavni kompromis."
            else ->
                "Dopuni odgovor jednim konkretnim razlogom i primerom vezanim za $targetConcept."
        }
    }
}
