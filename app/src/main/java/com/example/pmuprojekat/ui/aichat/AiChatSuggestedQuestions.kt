package com.example.pmuprojekat.ui.aichat

import kotlin.random.Random

object AiChatSuggestedQuestions {
    const val VisibleCount = 6

    val all: List<String> = listOf(
        "Šta znači source of truth?",
        "Objasni mi razliku između cache-a i read modela",
        "Kada ima smisla koristiti Strategy obrazac?",
        "Kako da prepoznam da sistem treba asinhronu obradu?",
        "Zašto mikroservisi nisu uvek najbolje rešenje?",
        "Šta je razlika između autentikacije i autorizacije?",
        "Kako da objasnim Observer obrazac?",
        "Kada je bolje koristiti Adapter nego menjati postojeći kod?",
        "Šta je razlika između Factory Method i Abstract Factory?",
        "Kako da prepoznam code smell?",
        "Šta znači loose coupling?",
        "Kako SOLID pomaže u dizajnu klasa?",
        "Objasni mi Open/Closed Principle jednostavno",
        "Kada je nasleđivanje loš izbor?",
        "Zašto je kompozicija često bolja od nasleđivanja?",
        "Kako da čitam UML class diagram?",
        "Šta UML sequence diagram treba da pokaže?",
        "Koja je razlika između klase i interfejsa?",
        "Kako da odredim odgovornost klase?",
        "Šta znači separation of concerns?",
        "Kako da prepoznam granicu modula?",
        "Šta je modularni monolit?",
        "Kada modularni monolit ima više smisla od mikroservisa?",
        "Kako da razmišljam o bounded context-u?",
        "Šta je arhitektonski kompromis?",
        "Kako da objasnim trade-off u zadatku?",
        "Kako da razlikujem kritični tok od sporednog toka?",
        "Kada koristiti message broker?",
        "Šta message broker sme, a šta ne sme da radi?",
        "Kako događaji pomažu da sistem bude labavije povezan?",
        "Šta je eventual consistency?",
        "Kada je eventual consistency prihvatljiva?",
        "Šta je transakcija u bazi?",
        "Šta znači atomicity?",
        "Kako da izbegnem duplu obradu poruke?",
        "Šta je idempotentnost?",
        "Kako cache može da napravi pogrešan prikaz?",
        "Kako da odlučim šta ide u bazu, a šta u object storage?",
        "Šta je read model?",
        "Kada je dashboard samo informativan?",
        "Šta znači projection u arhitekturi?",
        "Kako da razlikujem snapshot od autoritativnog stanja?",
        "Šta je audit log?",
        "Zašto je audit važan u finansijskim sistemima?",
        "Kako RBAC radi u praksi?",
        "Kada sama uloga nije dovoljna za pristup?",
        "Šta znači principle of least privilege?",
        "Kako da nacrtam osnovnu arhitekturu sistema?",
        "Šta treba označiti na arhitektonskom dijagramu?",
        "Kako da branim arhitektonsku odluku?",
        "Kako da napišem mini ADR?",
        "Šta je fallback ponašanje?",
        "Kako da razmišljam o partial failure?",
        "Kako monitoring i observability nisu isto?",
        "Šta je distributed tracing?",
        "Kako da izaberem metrike za incident?",
        "Šta je RAG?",
        "Šta je chunking?",
        "Kako embedding pomaže u pretrazi?",
        "Šta znači grounding AI odgovora?",
        "Šta je razlika između fine-tuning-a i RAG-a?",
        "Kako da procenim AI odgovor u zadatku?",
        "Šta je OCR i zašto nije izvor istine?",
        "Kako AI predlog razlikovati od potvrđene odluke?",
        "Kako da vežbam Junior pseudo-kod zadatke?",
        "Kako da rešavam zadatke sa redosledom koraka?",
        "Kako da priđem Senior trade-off zadatku?",
        "Kako da rešavam Architect zadatke o source of truth?",
        "Kako da prepoznam distraktor u zadatku?",
        "Kako da nakon greške izvučem sledeći korak za učenje?"
    )

    fun randomSelection(
        count: Int = VisibleCount,
        random: Random = Random.Default
    ): List<String> {
        return all.shuffled(random).take(count.coerceAtMost(all.size))
    }
}
