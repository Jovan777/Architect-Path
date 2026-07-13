package com.example.pmuprojekat.ai

object AiSketchAnalysisPromptBuilder {

    private val systemPrompt = """
        Ti si iskusan mentor softverske arhitekture koji analizira fotografiju ručno nacrtane arhitekture sistema.
        Piši na srpskom latinicom sa č, ć, š, đ i ž. Obraćaj se direktno korisniku: "dobro si prikazao",
        "nedostaje", "probaj da označiš".

        Analiza treba da bude kratka, praktična i zasnovana na vidljivim elementima crteža i tekstu zadatka.
        Ako fotografija nije jasna ili ne možeš pouzdano da pročitaš deo crteža, reci to direktno i daj ograničen
        savet na osnovu onoga što se vidi.

        Posebno pazi na razliku između autoritativnog izvora istine i izvedenih/read model/cache/snapshot prikaza,
        kao i na razliku između kritičnog poslovnog toka i asinhronih sekundarnih procesa.

        Koristi tačno ove naslove bez markdown oznaka:

        Šta je dobro prikazano

        Šta nedostaje ili nije jasno

        Jedan konkretan predlog za poboljšanje
    """.trimIndent()

    fun build(request: AiSketchAnalysisRequest): AiSketchAnalysisPrompt {
        return AiSketchAnalysisPrompt(
            systemPrompt = systemPrompt,
            taskContextPrompt = buildString {
                appendLine("Analiziraj fotografiju crteža arhitekture za sledeći zadatak.")
                appendLine()
                appendLine("ID zadatka: ${request.questionId}")
                appendLine("Naslov: ${request.title}")
                appendLine()
                appendLine("Opis sistema:")
                appendLine(request.systemDescription)
                appendLine()
                appendLine("Zadatak za korisnika:")
                appendLine(request.drawingInstruction)
                appendLine()
                appendLine("Na crtežu treba označiti:")
                appendLine(request.drawingChecklist)
                appendLine()
                appendLine("Interna smernica za analizu:")
                appendLine(request.internalRubric)
                appendLine()
                appendLine("Odgovori kratko i mentorski. Nemoj pisati dugu teoriju. Navedi šta je dobro, šta nedostaje ili nije jasno i tačno jedan konkretan predlog za poboljšanje crteža.")
            }
        )
    }
}
