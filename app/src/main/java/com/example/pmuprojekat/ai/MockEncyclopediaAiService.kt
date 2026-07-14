package com.example.pmuprojekat.ai

import javax.inject.Inject

class MockEncyclopediaAiService @Inject constructor() : EncyclopediaAiService {
    override suspend fun explain(request: EncyclopediaAiRequest): Result<String> {
        val term = request.term
        val content = when (request.mode) {
            EncyclopediaExplanationMode.DETAILED -> {
                "${term.shortExplanation}\n\nU praksi je važno da pojam „${term.titleSr}” posmatraš u kontekstu celog sistema: koji podatak koristi, kada nastaje rezultat i ko taj rezultat kasnije proverava ili koristi."
            }

            EncyclopediaExplanationMode.SIMPLE -> {
                "Najjednostavnije rečeno, ${term.titleSr.lowercase()} je način da sistem obradi ili predstavi podatke tako da može da obavi konkretan zadatak. Ključna ideja je: ${term.shortExplanation.substringBefore('.').lowercase()}."
            }

            EncyclopediaExplanationMode.PRACTICAL_EXAMPLE -> {
                "Na platformi za učenje, tim može da primeni pojam „${term.titleSr}” kada gradi AI funkciju koja obrađuje sadržaj lekcija ili ponašanje korisnika. Primer pokazuje kako se osnovna definicija koristi u stvarnom softverskom toku, a ne samo kao teorijski termin."
            }
        }

        return Result.success(
            "Ovo je lokalni primer odgovora jer API ključ nije podešen.\n\n$content"
        )
    }
}
