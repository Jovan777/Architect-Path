package com.example.pmuprojekat.ai

object EncyclopediaPromptBuilder {
    private val systemPrompt = """
        Ti si strpljiv i precizan mentor u obrazovnoj Android aplikaciji.
        Objašnjavaš pojmove iz softverskog inženjerstva, arhitekture i AI oblasti.

        Pravila:
        - Piši isključivo na srpskom jeziku, latinicom i sa pravilnim slovima č, ć, š, đ i ž.
        - Koristi dati pojam i kratko objašnjenje kao glavni izvor konteksta.
        - Ne protivreči datom objašnjenju i ne menjaj značenje pojma.
        - Ne izmišljaj izvore, članke, radove, autore, reference ili internet linkove.
        - Odgovor mora biti sažet, jasan, koristan i prilagođen traženom načinu objašnjenja.
        - Ne piši veliki teorijski esej i ne koristi markdown naslove.
        - Kada je prikladno, poveži pojam sa softverskim sistemima, arhitekturom, Android aplikacijama,
          AI sistemima ili platformama za učenje.
    """.trimIndent()

    fun build(request: EncyclopediaAiRequest): EncyclopediaAiPrompt {
        val modeInstruction = when (request.mode) {
            EncyclopediaExplanationMode.DETAILED -> {
                "Proširi objašnjenje u 2 do 4 kratka pasusa. Dodaj kontekst i objasni zašto je pojam važan."
            }

            EncyclopediaExplanationMode.SIMPLE -> {
                "Objasni pojam kao nekome ko ga prvi put sreće. Koristi jednostavan jezik u 1 do 3 kratka pasusa."
            }

            EncyclopediaExplanationMode.PRACTICAL_EXAMPLE -> {
                "Daj jedan konkretan primer iz prakse i zatim u 1 do 2 rečenice objasni vezu sa pojmom."
            }
        }

        val userPrompt = """
            Oblast: ${request.categoryTitle}
            Pojam na srpskom: ${request.term.titleSr}
            Engleski naziv: ${request.term.titleEn}

            Osnovno objašnjenje:
            ${request.term.shortExplanation}

            Zahtev:
            $modeInstruction
        """.trimIndent()

        return EncyclopediaAiPrompt(
            systemPrompt = systemPrompt,
            userPrompt = userPrompt
        )
    }

    fun buildCustomQuestion(request: EncyclopediaCustomQuestionRequest): EncyclopediaAiPrompt {
        val userPrompt = """
            Oblast: ${request.categoryTitle}
            Pojam na srpskom: ${request.term.titleSr}
            Engleski naziv: ${request.term.titleEn}

            Osnovno objašnjenje:
            ${request.term.shortExplanation}

            Korisnikovo pitanje:
            ${request.question}

            Zahtev:
            Odgovori direktno na korisnikovo pitanje koristeći samo ovaj pojam i dato objašnjenje kao
            glavni kontekst. Odgovor napiši na srpskom latinicom, jasno i praktično, bez dugog eseja.
            Ako je korisno, dodaj kratak primer iz softverskog inženjerstva, arhitekture, Android aplikacije,
            AI sistema ili platforme za učenje.

            Ako pitanje nije direktno vezano za pojam koji je korisnik otvorio, nemoj širiti temu.
            Reci: "Ovo pitanje nije direktno vezano za pojam koji trenutno gledaš. Mogu da ti pomognem ako ga povežeš sa ovim pojmom."
            Ne izmišljaj izvore, linkove, pretragu interneta ili reference.
        """.trimIndent()

        return EncyclopediaAiPrompt(
            systemPrompt = systemPrompt,
            userPrompt = userPrompt
        )
    }
}
