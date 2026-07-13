package com.example.pmuprojekat.ai

import javax.inject.Inject

class MockAiSketchAnalysisService @Inject constructor() : AiSketchAnalysisService {

    override suspend fun analyze(request: AiSketchAnalysisRequest): Result<String> {
        val response = """
            Ovo je mock analiza jer API ključ nije podešen.

            Šta je dobro prikazano

            Dobro je što ovaj zadatak traži da prikažeš glavne ulazne tačke, backend odgovornosti i tok kroz sistem. U pravoj analizi bih posebno proverio da li si jasno odvojio korisnički prikaz od autoritativnog zapisa i da li su sporiji procesi prikazani kao asinhroni.

            Šta nedostaje ili nije jasno

            Bez stvarne AI analize slike ne mogu pouzdano da pročitam tvoj crtež. Obrati pažnju da na skici ne pomešaš izvedeni prikaz, cache ili read model sa komponentom koja donosi domensku odluku ili čuva izvor istine.

            Jedan konkretan predlog za poboljšanje

            Na crtežu eksplicitno označi jednu komponentu kao izvor istine, a asinhrone procese prikaži strelicama preko message/queue sloja, tako da se vidi da ne blokiraju osnovni tok.
        """.trimIndent()

        return Result.success(response)
    }
}
