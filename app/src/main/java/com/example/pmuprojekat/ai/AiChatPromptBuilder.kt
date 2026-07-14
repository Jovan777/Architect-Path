package com.example.pmuprojekat.ai

object AiChatPromptBuilder {
    const val MAX_HISTORY_MESSAGES = 8
    const val MAX_RELEVANT_TERMS = 5

    private val systemPrompt = """
        Ti si AI mentor unutar Android aplikacije za učenje projektovanja softvera.

        Oblast aplikacije:
        - softverski dizajn i refaktorisanje;
        - design patterns i uloge u obrascima;
        - UML i modelovanje sistema;
        - softverska arhitektura i system design;
        - backend/frontend arhitektura;
        - baze podataka, cache, read model, projekcije i source of truth;
        - asinhrona komunikacija, događaji, message broker-i i otpornost;
        - bezbednost, audit i kontrola pristupa;
        - AI, ML, RAG i pojmovi iz enciklopedije aplikacije;
        - zadaci, nivoi i način razmišljanja u ovoj aplikaciji.

        Pravila:
        - Piši isključivo na srpskom jeziku, latinicom i sa slovima č, ć, š, đ i ž.
        - Obraćaj se korisniku direktno, prijateljski i mentorski.
        - Odgovor treba da bude kratak, praktičan i edukativan, sa primerom kada pomaže.
        - Za arhitektonska pitanja objasni trade-off, odgovornost komponente i rizik loše granice.
        - Za source of truth, read model, cache, snapshot ili projekciju budi precizan o tome šta je autoritativno, a šta izvedeno.
        - Za obrasce objasni nameru, strukturu i kada ih koristiti ili izbegavati.
        - Za UML objasni šta dijagram treba da komunicira.
        - Za AI/RAG pojmove objasni praktično značenje u kontekstu softverskog sistema ili ove aplikacije.
        - Koristi relevantne pojmove iz enciklopedije samo ako zaista pomažu odgovoru.
        - Ne izmišljaj izvore, knjige, radove, URL-ove ili tvrdnju da si pretražio internet.
        - Ne odgovaraj kao opšti asistent za nepovezane teme.
        - Ako je pitanje van oblasti aplikacije, ljubazno preusmeri korisnika: "Ovaj chat je namenjen pitanjima iz projektovanja softvera, arhitekture, obrazaca i pojmova iz aplikacije. Mogu da ti pomognem ako pitanje povežeš sa tim oblastima."
    """.trimIndent()

    fun build(request: AiChatRequest): AiChatPrompt {
        val history = request.recentMessages
            .takeLast(MAX_HISTORY_MESSAGES)
            .joinToString(separator = "\n") { message ->
                val role = when (message.role) {
                    AiChatRole.USER -> "Korisnik"
                    AiChatRole.ASSISTANT -> "AI mentor"
                }
                "$role: ${message.content}"
            }
            .ifBlank { "Nema prethodnih poruka u ovom razgovoru." }

        val relevantTerms = request.relevantTerms
            .take(MAX_RELEVANT_TERMS)
            .mapIndexed { index, term ->
                """
                ${index + 1}. ${term.titleSr} — ${term.titleEn}
                   Oblast: ${term.categoryTitle}
                   Kratko objašnjenje: ${term.shortExplanation}
                """.trimIndent()
            }
            .joinToString(separator = "\n\n")
            .ifBlank { "Nema posebno pronađenih pojmova iz enciklopedije za ovo pitanje." }

        val userPrompt = """
            Razgovor do sada:
            $history

            Relevantni pojmovi iz enciklopedije:
            $relevantTerms

            Instrukcija:
            Koristi pojmove iz enciklopedije ako su relevantni, ali ih ne forsiraj. Odgovori na trenutno pitanje korisnika mentorski, jasno i u okviru oblasti aplikacije.

            Trenutno pitanje korisnika:
            ${request.userMessage}
        """.trimIndent()

        return AiChatPrompt(
            systemPrompt = systemPrompt,
            userPrompt = userPrompt
        )
    }
}
