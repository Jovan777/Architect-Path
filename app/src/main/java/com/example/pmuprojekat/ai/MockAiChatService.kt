package com.example.pmuprojekat.ai

import javax.inject.Inject

class MockAiChatService @Inject constructor() : AiChatService {
    override suspend fun sendMessage(request: AiChatRequest): Result<String> {
        val topTerm = request.relevantTerms.firstOrNull()
        val contextLine = if (topTerm != null) {
            "Pronašao sam povezan pojam iz enciklopedije: ${topTerm.titleSr}. ${topTerm.shortExplanation}"
        } else {
            "Nisam pronašao direktan pojam iz enciklopedije, ali mogu da dam opšti mentorski odgovor u okviru oblasti aplikacije."
        }

        val response = """
            Ovo je lokalni primer odgovora jer AI servis trenutno nije dostupan.

            $contextLine

            Ako pitaš o softverskom dizajnu ili arhitekturi, dobar prvi korak je da razdvojiš odgovornosti: šta je izvor istine, šta je izvedeni prikaz, koji tok mora biti brz i pouzdan, a koji može da radi asinhrono. Zatim proveri koji trade-off prihvataš: jednostavnost, skalabilnost, konzistentnost, latencija ili operativna složenost.
        """.trimIndent()

        return Result.success(response)
    }
}
