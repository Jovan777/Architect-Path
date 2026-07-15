package com.example.pmuprojekat.ai

import com.example.pmuprojekat.core.model.LearningLevel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VsAiLevelContextProvider @Inject constructor() {

    fun get(levelId: String): VsAiLevelContext {
        return contexts[levelId]
            ?: throw IllegalArgumentException("Nepoznat VS AI nivo: $levelId")
    }

    fun getAll(): List<VsAiLevelContext> {
        return LearningLevel.entries.map { get(it.id) }
    }

    private companion object {
        val contexts = listOf(
            VsAiLevelContext(
                levelId = LearningLevel.BEGINNER.id,
                displayName = "Početnik",
                difficultyDescription = "Osnovni pojmovi, prepoznavanje obrazaca i jednostavne dobre prakse.",
                expectedReasoningDepth = "Kratko i direktno objašnjenje sa jednim jasnim razlogom ili primerom.",
                typicalConcepts = listOf(
                    "klasa i objekat",
                    "interfejs",
                    "enkapsulacija",
                    "osnovni obrasci projektovanja",
                    "razdvajanje odgovornosti"
                ),
                allowedQuestionStyles = listOf(
                    "jednostavna definicija sopstvenim rečima",
                    "poređenje dva bliska osnovna pojma",
                    "prepoznavanje dobre prakse u kratkom primeru"
                ),
                forbiddenQuestionStyles = listOf(
                    "složena distribuirana arhitektura",
                    "višeslojni trade-off sa parcijalnim kvarovima",
                    "dugačak višekoračni zadatak"
                ),
                satisfactionCriteria = listOf(
                    "suština pojma je tačna",
                    "odgovor ne meša osnovne uloge",
                    "jednostavan primer ili razlog je dovoljan"
                ),
                maximumRounds = 3,
                minimumExpectedAnswerQuality = 70,
                suitableQuestionExamples = listOf(
                    "Koja je razlika između interfejsa i konkretne klase?",
                    "Zašto je korisno sakriti unutrašnje stanje objekta?"
                )
            ),
            VsAiLevelContext(
                levelId = LearningLevel.JUNIOR.id,
                displayName = "Junior",
                difficultyDescription = "Primena obrazaca, pseudo-kod i raspodela odgovornosti među klasama.",
                expectedReasoningDepth = "Ispravna primena uz kratko objašnjenje zašto odgovornost pripada tom delu koda.",
                typicalConcepts = listOf(
                    "Strategy",
                    "Factory Method",
                    "Dependency Injection",
                    "odgovornost klase",
                    "programiranje prema interfejsu"
                ),
                allowedQuestionStyles = listOf(
                    "kratak tehnički scenario",
                    "objašnjenje jedne linije pseudo-koda",
                    "izbor obrasca i obrazloženje odgovornosti"
                ),
                forbiddenQuestionStyles = listOf(
                    "odbrana enterprise arhitekture",
                    "neograničen dizajn kompletnog sistema",
                    "pitanje koje traži samo napamet naučenu definiciju bez primene"
                ),
                satisfactionCriteria = listOf(
                    "rešenje je primenljivo na dati scenario",
                    "uloge klasa ili komponenti nisu pomešane",
                    "naveden je bar jedan razlog za odluku"
                ),
                maximumRounds = 4,
                minimumExpectedAnswerQuality = 75,
                suitableQuestionExamples = listOf(
                    "Kako bi Strategy obrazac uklonio grananje po tipu popusta?",
                    "Zašto zavisnost treba proslediti kroz konstruktor?"
                )
            ),
            VsAiLevelContext(
                levelId = LearningLevel.MEDIOR.id,
                displayName = "Medior",
                difficultyDescription = "Komponente, tok podataka, sinhrona i asinhrona obrada i osnovni sistemski kompromisi.",
                expectedReasoningDepth = "Odluka mora biti povezana sa posledicom, tokom podataka i ponašanjem pri kašnjenju ili kvaru.",
                typicalConcepts = listOf(
                    "sinhrona i asinhrona komunikacija",
                    "event-driven flow",
                    "cache",
                    "read model",
                    "eventual consistency",
                    "source of truth"
                ),
                allowedQuestionStyles = listOf(
                    "fokusiran scenario sa tokom podataka",
                    "pitanje o posledici tehničke odluke",
                    "razdvajanje izvedenog prikaza od autoritativnog stanja"
                ),
                forbiddenQuestionStyles = listOf(
                    "puka definicija bez posledice",
                    "neograničena enterprise transformacija",
                    "više nepovezanih problema u jednom pitanju"
                ),
                satisfactionCriteria = listOf(
                    "objašnjeno je zašto odluka ima smisla",
                    "navedena je najmanje jedna posledica",
                    "tok i vlasništvo nad stanjem su dovoljno jasni"
                ),
                maximumRounds = 5,
                minimumExpectedAnswerQuality = 80,
                suitableQuestionExamples = listOf(
                    "Kada bi status narudžbine čitao iz read modela, a kada iz autoritativnog servisa?",
                    "Koji deo obrade može biti asinhron i kakvu posledicu to uvodi?"
                )
            ),
            VsAiLevelContext(
                levelId = LearningLevel.SENIOR.id,
                displayName = "Senior",
                difficultyDescription = "Trade-off odluke, performanse, pouzdanost, bezbednost i parcijalni kvarovi.",
                expectedReasoningDepth = "Odbranjiva odluka sa koristi, rizikom, fallback ponašanjem i uslovom pod kojim bi se promenila.",
                typicalConcepts = listOf(
                    "partial failure",
                    "fallback",
                    "scalability",
                    "reliability",
                    "idempotency",
                    "security and audit",
                    "operational complexity"
                ),
                allowedQuestionStyles = listOf(
                    "trade-off u konkretnom produkcionom scenariju",
                    "posledica kvara spoljne zavisnosti",
                    "odluka pod ograničenjem performansi ili bezbednosti"
                ),
                forbiddenQuestionStyles = listOf(
                    "pitanje bez konkretnog ograničenja",
                    "definicija koju je dovoljno reprodukovati",
                    "nepovezana lista više arhitektonskih tema"
                ),
                satisfactionCriteria = listOf(
                    "prepoznate su korist i cena odluke",
                    "obrađen je relevantan failure mode ili fallback",
                    "naveden je uslov pod kojim odluku treba preispitati"
                ),
                maximumRounds = 6,
                minimumExpectedAnswerQuality = 85,
                suitableQuestionExamples = listOf(
                    "Kako bi zaštitio osnovni tok narudžbine kada notification servis ne radi?",
                    "Koji rizik prihvataš uvođenjem cache-a i kada bi promenio tu odluku?"
                )
            ),
            VsAiLevelContext(
                levelId = LearningLevel.ARCHITECT.id,
                displayName = "Arhitekta",
                difficultyDescription = "Sistemsko razmišljanje, domenske granice, autoritativno stanje i evolucija arhitekture.",
                expectedReasoningDepth = "Precizna odbrana granica, tokova i trade-off-a uz razlikovanje izvedenih prikaza od domenskih odluka.",
                typicalConcepts = listOf(
                    "domain boundaries",
                    "source of truth",
                    "read model and projection",
                    "asynchronous workflow",
                    "eventual consistency",
                    "architecture evolution",
                    "decision ownership"
                ),
                allowedQuestionStyles = listOf(
                    "jedan fokusiran sistemski scenario",
                    "odbrana arhitektonske odluke",
                    "razjašnjenje granice, izvora istine ili evolucije rešenja"
                ),
                forbiddenQuestionStyles = listOf(
                    "vagano pitanje bez poslovnog konteksta",
                    "pitanje koje traži samo naziv tehnologije",
                    "kompletan dizajn velikog sistema u jednom odgovoru"
                ),
                satisfactionCriteria = listOf(
                    "domenske odgovornosti i granice su precizne",
                    "autoritativno stanje nije pomešano sa projekcijom, cache-om ili sporednim efektom kada je to relevantno",
                    "trade-off je odbranjiv i uključuje posledice, kvarove ili evoluciju"
                ),
                maximumRounds = 7,
                minimumExpectedAnswerQuality = 90,
                suitableQuestionExamples = listOf(
                    "Kako bi razdvojio dostupnost iz read modela od autoritativne potvrde rezervacije?",
                    "Ko poseduje domensku odluku, a koje komponente samo reaguju na događaj?"
                )
            )
        ).associateBy(VsAiLevelContext::levelId)
    }
}
