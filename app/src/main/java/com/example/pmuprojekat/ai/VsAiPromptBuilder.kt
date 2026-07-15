package com.example.pmuprojekat.ai

object VsAiPromptBuilder {

    fun buildStart(request: VsAiStartRequest): VsAiPrompt {
        return VsAiPrompt(
            systemPrompt = systemPrompt,
            userPrompt = buildString {
                appendLine("ZADATAK: Generiši početno pitanje za novu VS AI sesiju.")
                appendLine("Postavi tačno jedno fokusirano pitanje. Ne otkrivaj odgovor ili internu rubriku.")
                appendLine()
                appendLevelContext(request.levelContext)
                appendLine()
                appendLearnerContext(request.learnerContext)
                appendLine()
                appendRelevantTerms(request.relevantTerms)
                appendLine()
                appendLine("Vrati isključivo jedan JSON objekat bez markdown ograda:")
                appendLine(
                    """
                    {
                      "question": "jedno pitanje na srpskom latinicom",
                      "targetConcept": "jedan uzak koncept",
                      "expectedAnswerGuide": "interna kratka rubrika koju korisnik ne vidi",
                      "difficultyLabel": "kratka oznaka težine",
                      "reasonChosen": "zašto pitanje odgovara nivou i dostupnom kontekstu"
                    }
                    """.trimIndent()
                )
            }
        )
    }

    fun buildEvaluation(request: VsAiEvaluationRequest): VsAiPrompt {
        val isLastRound = request.currentRound >= request.levelContext.maximumRounds
        return VsAiPrompt(
            systemPrompt = systemPrompt,
            userPrompt = buildString {
                appendLine("ZADATAK: Proceni poslednji odgovor i odluči da li VS AI izazov treba da se nastavi.")
                appendLine("Ne vraćaj samo 'tačno' ili 'netačno'. Ne daj kompletan idealan odgovor.")
                appendLine("Strogost mora odgovarati izabranom nivou, ne višem ili nižem nivou.")
                appendLine("Trenutna runda: ${request.currentRound}/${request.levelContext.maximumRounds}")
                appendLine("Poslednja dozvoljena runda: $isLastRound")
                if (isLastRound) {
                    appendLine("Pošto je ovo poslednja runda, shouldContinue mora biti false.")
                }
                appendLine()
                appendLevelContext(request.levelContext)
                appendLine()
                appendLearnerContext(request.learnerContext)
                appendLine()
                appendLine("POČETNI IZAZOV I INTERNA RUBRIKA")
                appendLine("Pitanje: ${request.challenge.question}")
                appendLine("Ciljni koncept: ${request.challenge.targetConcept}")
                appendLine("Očekivani smer odgovora: ${request.challenge.expectedAnswerGuide}")
                appendLine()
                appendConversation(request.conversation)
                appendLine()
                appendEvaluationSignals(request.previousEvaluationSignals)
                appendLine()
                appendRelevantTerms(request.relevantTerms)
                appendLine()
                appendLine("Pravila odluke:")
                appendLine("- Nastavi samo ako odgovor još nije dovoljno jak za prag ovog nivoa.")
                appendLine("- Ako nastavljaš, postavi samo jedno oštrije i konkretno podpitanje.")
                appendLine("- Ne zahtevaj Architect dubinu od Početnika.")
                appendLine("- Ne prihvataj neodređen Architect odgovor bez granica, vlasništva i trade-off-a.")
                appendLine("- Proveri source of truth/read model/cache razliku samo kada je relevantna za pitanje.")
                appendLine()
                appendLine("Vrati isključivo jedan JSON objekat bez markdown ograda:")
                appendLine(
                    """
                    {
                      "visibleMessage": "kratka mentorska reakcija koju korisnik vidi",
                      "shouldContinue": true,
                      "isSatisfied": false,
                      "satisfactionScore": 0,
                      "nextQuestion": "jedno podpitanje ili null",
                      "targetConcepts": ["koncept"],
                      "detectedStrengths": ["konkretna dobra tačka"],
                      "detectedWeaknesses": ["konkretna praznina"],
                      "reasonForContinuingOrStopping": "interna kratka odluka"
                    }
                    """.trimIndent()
                )
            }
        )
    }

    fun buildFinalAnalysis(request: VsAiFinalAnalysisRequest): VsAiPrompt {
        return VsAiPrompt(
            systemPrompt = systemPrompt,
            userPrompt = buildString {
                appendLine("ZADATAK: Daj završnu procenu cele VS AI sesije.")
                appendLine("Procena mora biti kraća od standardne analize završenog zadatka, ali evidence-based.")
                appendLine("Razlog završetka: ${request.completionReason}")
                appendLine()
                appendLevelContext(request.levelContext)
                appendLine()
                appendLearnerContext(request.learnerContext)
                appendLine()
                appendLine("POČETNO PITANJE")
                appendLine(request.challenge.question)
                appendLine("Ciljni koncept: ${request.challenge.targetConcept}")
                appendLine()
                appendConversation(request.conversation)
                appendLine()
                appendEvaluationSignals(request.evaluationSignals)
                appendLine()
                appendLine("Ocena 0-100 mora biti kalibrisana prema izabranom nivou.")
                appendLine("Navedi stvarni razvoj ili ponovljenu slabost iz razgovora; ne izmišljaj napredak.")
                appendLine("Sledeći korak mora biti jedna konkretna vežba od 2-5 minuta.")
                appendLine()
                appendLine("Vrati isključivo jedan JSON objekat bez markdown ograda:")
                appendLine(
                    """
                    {
                      "score": 0,
                      "resultSummary": "kratak zaključak i razlog završetka kada je relevantan",
                      "strengths": "šta je konkretno dobro urađeno",
                      "weakerPoints": "šta je konkretno ostalo slabo ili nejasno",
                      "reasoningDevelopment": "kako se razmišljanje menjalo kroz odgovore",
                      "keyConcepts": ["pojam 1", "pojam 2"],
                      "nextConcreteStep": "jedna praktična vežba vezana za ovaj izazov"
                    }
                    """.trimIndent()
                )
            }
        )
    }

    private fun StringBuilder.appendLevelContext(context: VsAiLevelContext) {
        appendLine("LEVEL CONTEXT")
        appendLine("- Nivo: ${context.displayName} (${context.levelId})")
        appendLine("- Težina: ${context.difficultyDescription}")
        appendLine("- Očekivana dubina: ${context.expectedReasoningDepth}")
        appendLine("- Tipični koncepti: ${context.typicalConcepts.joinToString()}")
        appendLine("- Dozvoljeni stilovi: ${context.allowedQuestionStyles.joinToString()}")
        appendLine("- Zabranjeni stilovi: ${context.forbiddenQuestionStyles.joinToString()}")
        appendLine("- Kriterijumi zadovoljstva: ${context.satisfactionCriteria.joinToString()}")
        appendLine("- Prag: ${context.minimumExpectedAnswerQuality}/100")
        appendLine("- Maksimalno rundi: ${context.maximumRounds}")
        appendLine("- Primeri odgovarajućih pitanja: ${context.suitableQuestionExamples.joinToString(" | ")}")
    }

    private fun StringBuilder.appendLearnerContext(context: VsAiLearnerContext) {
        appendLine("LEARNER CONTEXT")
        appendLine("- Status: ${context.status.displayName}")
        appendLine("- Završeni zadaci na nivou: ${context.solvedTasks}/${context.totalTasks}")
        appendLine("- Prosečan najbolji rezultat: ${context.averageScorePercent ?: "nije dostupan"}")
        appendLine("- Najbolji rezultat: ${context.bestScorePercent ?: "nije dostupan"}")
        appendLine("- Jači tipovi: ${context.strongerTaskTypes.joinToString().ifBlank { "nisu pouzdano utvrđeni" }}")
        appendLine("- Slabiji tipovi: ${context.weakerTaskTypes.joinToString().ifBlank { "nisu pouzdano utvrđeni" }}")
        appendLine("- Ponavljani obrasci grešaka: ${context.repeatedMistakePatterns.joinToString().ifBlank { "nisu dostupni" }}")
        appendLine("- Nedavni rezultati: ${context.recentResults.joinToString { "${it.questionId}/${it.taskType}=${it.bestScorePercent}%" }.ifBlank { "nema" }}")
        appendLine("- Napomena: ${context.personalizationNote}")
    }

    private fun StringBuilder.appendRelevantTerms(terms: List<AiChatRelevantTerm>) {
        appendLine("RELEVANTNI POJMOVI IZ LOKALNE ENCIKLOPEDIJE")
        if (terms.isEmpty()) {
            appendLine("Nema izdvojenih pojmova. Ne izmišljaj lokalni kontekst.")
            return
        }

        terms.take(MAX_RELEVANT_TERMS).forEachIndexed { index, term ->
            appendLine("${index + 1}. ${term.titleSr} — ${term.titleEn} (${term.categoryTitle})")
            appendLine("   ${term.shortExplanation}")
        }
        appendLine("Koristi pojmove samo ako su stvarno korisni za izabrano pitanje.")
    }

    private fun StringBuilder.appendConversation(turns: List<VsAiConversationTurn>) {
        appendLine("TOK IZAZOVA")
        if (turns.isEmpty()) {
            appendLine("Nema poruka.")
            return
        }
        turns.forEach { turn ->
            val role = if (turn.role == VsAiConversationRole.USER) "KORISNIK" else "AI"
            appendLine("- Runda ${turn.roundNumber}, $role: ${turn.content}")
        }
    }

    private fun StringBuilder.appendEvaluationSignals(signals: List<VsAiEvaluationSignal>) {
        appendLine("PRETHODNI INTERNI SIGNALI PROCENE")
        if (signals.isEmpty()) {
            appendLine("Nema prethodnih procena.")
            return
        }
        signals.forEachIndexed { index, signal ->
            appendLine("- Procena ${index + 1}: ${signal.satisfactionScore}/100")
            appendLine("  Ciljni koncepti: ${signal.targetConcepts.joinToString().ifBlank { "nisu izdvojeni" }}")
            appendLine("  Dobro: ${signal.strengths.joinToString().ifBlank { "nije izdvojeno" }}")
            appendLine("  Slabo: ${signal.weaknesses.joinToString().ifBlank { "nije izdvojeno" }}")
            appendLine("  Razlog: ${signal.reason}")
        }
    }

    private val systemPrompt = """
        Ti si AI sparing mentor u obrazovnoj Android aplikaciji za projektovanje softvera.
        Kontrolišeš adaptivni izazov: postavljaš jedno fokusirano pitanje, procenjuješ odgovor, po potrebi
        postavljaš jedno oštrije podpitanje i završavaš kada je odgovor dovoljno jak za izabrani nivo.

        OBAVEZNA PRAVILA
        - Piši isključivo na srpskom latinicom sa č, ć, š, đ i ž.
        - Obraćaj se korisniku direktno i mentorski, bez formalnog izveštajnog tona.
        - Ostani u oblastima softverskog dizajna, obrazaca, UML-a, arhitekture, sistemskog dizajna,
          baza, asinhrone komunikacije, bezbednosti i AI/RAG pojmova iz aplikacije.
        - Ne koristi internet, ne izmišljaj izvore, istoriju korisnika ili činjenice koje nisu u kontekstu.
        - Ne daj odgovor unapred i ne prikazuj internu rubriku, prag ili skrivenu evaluaciju korisniku.
        - Postavljaj jedno pitanje odjednom. Ne pravi multiple-choice niti veliki višekoračni zadatak.
        - Prilagodi strogost nivou. Jednostavan tačan odgovor može zadovoljiti Početnika; neodređen odgovor
          nije dovoljan za Arhitektu.
        - Za Senior i Architect nivo, kada je relevantno, proveri razliku između source of truth-a i
          read modela, cache-a, projekcije, dashboarda, preporuke, AI sugestije, notifikacije ili drugog
          asinhronog sporednog efekta. Ne forsiraj tu temu u nepovezana ili početnička pitanja.
        - Vrati isključivo JSON tražen u korisničkoj poruci, bez markdown ograda i bez dodatnog teksta.
    """.trimIndent()

    private const val MAX_RELEVANT_TERMS = 5
}
