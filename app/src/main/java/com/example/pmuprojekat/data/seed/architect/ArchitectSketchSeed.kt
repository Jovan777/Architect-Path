package com.example.pmuprojekat.data.seed.architect

import com.example.pmuprojekat.core.model.LearningLevel
import com.example.pmuprojekat.core.model.QuestionType
import com.example.pmuprojekat.core.model.StepType
import com.example.pmuprojekat.data.seed.SeedQuestion
import com.example.pmuprojekat.data.seed.SeedStep

object ArchitectSketchSeed {

    val questions: List<SeedQuestion> = listOf(
        sketchQuestion(
            questionId = "A7.1",
            title = "Sistem za prijavu i rešavanje kvarova u stambenim zgradama",
            systemDescription = """
                Kompanija koja upravlja stambenim zgradama želi platformu preko koje stanari mogu da prijave kvarove u zajedničkim prostorijama. Prijava može da se odnosi na lift, rasvetu, vodovod, grejanje, ulazna vrata ili drugi problem u zgradi. Stanar unosi opis problema, bira lokaciju i po potrebi dodaje fotografiju. Upravnik zgrade pregleda prijavu, određuje njen prioritet i dodeljuje je odgovarajućoj servisnoj firmi. Serviser preko mobilne aplikacije vidi dodeljene intervencije i menja status tokom rada. Jedan kvar može imati statuse: prijavljen, pregledan, dodeljen, intervencija u toku, čeka deo i završen. Zvanični status prijave mora da se čuva u jednom autoritativnom sistemu. Stanari mogu da vide napredak prijave, ali njihov prikaz može kratko da kasni. Fotografije i izveštaji servisera treba da se čuvaju odvojeno od osnovnih podataka o kvaru. Obaveštenja o promeni statusa mogu se slati asinhrono i njihov kvar ne sme da zaustavi rešavanje prijave.
            """.trimIndent(),
            drawingChecklist = """
                * aplikaciju za stanare, interfejs upravnika i mobilnu aplikaciju servisera;
                * glavne backend komponente za prijave, dodelu intervencija i upravljanje statusima;
                * gde se čuva autoritativni zapis o kvaru i njegovom trenutnom statusu;
                * storage za fotografije, servisne izveštaje i druge priloge;
                * read model ili izvedeni prikaz koji stanarima pokazuje napredak prijave;
                * asinhrona obaveštenja i ponašanje kada notification servis nije dostupan.
            """.trimIndent(),
            internalAiRubric = "Proveri da li su prijava kvara, dodela intervencije i izvršenje servisa predstavljeni kao jasno razdvojene odgovornosti. Utvrdi da li postoji jedan izvor istine za status prijave i da li korisnički prikaz nije pogrešno predstavljen kao autoritativan. Fotografije ne treba čuvati u istoj ulozi kao poslovne podatke o kvaru. Kvar notification servisa ne sme blokirati promenu statusa. Navedi šta je dobro, šta nedostaje i jedan konkretan predlog za poboljšanje.",
            orderIndex = 31
        ),
        sketchQuestion(
            questionId = "A7.2",
            title = "Sistem za upravljanje narudžbinama u lancu restorana",
            systemDescription = """
                Lanac restorana želi jedinstven sistem za prijem i pripremu narudžbina. Gosti mogu da naruče hranu preko mobilne aplikacije, samouslužnog kioska ili zaposlenog na kasi. Sve narudžbine treba da stignu do odgovarajućeg restorana i njegove kuhinje. Kuhinjsko osoblje vidi stavke koje treba pripremiti i menja njihov status tokom rada. Narudžbina može biti primljena, potvrđena, u pripremi, spremna ili završena. Sistem mora da zna koji restoran i koja kuhinjska stanica pripremaju svaku stavku. Plaćanje može biti izvršeno karticom preko spoljnog providera ili neposredno u restoranu. Prikaz prosečnog vremena čekanja može biti približan, ali status konkretne narudžbine mora imati jedan autoritativni zapis. Slanje potvrda gostu i ažuriranje analitičkih izveštaja ne treba da blokiraju rad kuhinje. Ako payment provider privremeno ne odgovara, sistem ne sme pogrešno označiti narudžbinu kao plaćenu.
            """.trimIndent(),
            drawingChecklist = """
                * mobilnu aplikaciju, kiosk, kasu i kuhinjski ekran kao ulazne ili korisničke tačke;
                * glavne backend komponente za narudžbine, meni, plaćanje i kuhinjski tok;
                * gde se čuva autoritativni status narudžbine;
                * kako se narudžbina prosleđuje odgovarajućoj kuhinji;
                * koje obrade mogu biti asinhrone, poput potvrda i analitike;
                * šta se dešava ako payment ili notification servis nije dostupan.
            """.trimIndent(),
            internalAiRubric = "Proveri da li različiti kanali naručivanja ulaze u kontrolisan zajednički tok. Kuhinjski ekran može prikazivati izvedeno stanje, ali glavni sistem mora posedovati status narudžbine. Plaćanje, priprema hrane i obaveštavanje ne treba mešati u jednu odgovornost. Navedi dobre odluke, nedostatke i jedan predlog za poboljšanje.",
            orderIndex = 32
        ),
        sketchQuestion(
            questionId = "A7.3",
            title = "Platforma za povrat robe i refundaciju kupaca",
            systemDescription = """
                Internet prodavnica želi poseban sistem za obradu povrata kupljene robe. Kupac preko aplikacije bira porudžbinu, proizvod koji vraća i razlog povrata. Sistem proverava da li je rok za povrat još važeći i da li proizvod ispunjava osnovne uslove. Nakon prihvatanja zahteva kupac dobija oznaku ili instrukcije za slanje paketa. Kurirska služba dostavlja paket skladištu, gde zaposleni proverava stanje vraćenog proizvoda. Proizvod može biti vraćen u prodaju, poslat na popravku, otpisan ili odbijen zbog oštećenja. Refundacija se pokreće tek kada poslovna pravila dozvole povraćaj novca. Payment provider može kasniti ili vratiti nejasan odgovor, pa sistem mora razlikovati zahtev za refundaciju od potvrđeno izvršene refundacije. Kupac treba da vidi razumljiv status procesa, ali njegov prikaz može kratko kasniti. Fotografije oštećenja i dokumentacija čuvaju se odvojeno od osnovnog zapisa povrata. Sve važne odluke treba evidentirati radi kasnijeg rešavanja reklamacija.
            """.trimIndent(),
            drawingChecklist = """
                * korisničku aplikaciju i interfejs za zaposlene u skladištu;
                * glavne komponente za zahteve za povrat, pregled robe i refundaciju;
                * gde se čuva autoritativni status procesa povrata;
                * integracije sa kurirskom službom i payment providerom;
                * storage za fotografije, dokaze i prateću dokumentaciju;
                * asinhrone procese i ponašanje kada status refundacije nije poznat.
            """.trimIndent(),
            internalAiRubric = "Proveri da li su prijem zahteva, fizički prijem robe, odluka o povratu i refundacija predstavljeni kao različite faze. Odgovor payment providera ne sme samostalno menjati stanje robe. Obrati pažnju na nejasna i delimično završena stanja. Navedi šta je dobro, šta nedostaje i jedan konkretan predlog za poboljšanje.",
            orderIndex = 33
        ),
        sketchQuestion(
            questionId = "A7.4",
            title = "Platforma za prijavljivanje i objavljivanje izmena na digitalnoj mapi",
            systemDescription = """
                Kompanija održava digitalnu mapu grada koju koriste građani i druge aplikacije. Registrovani korisnici mogu da prijave novi put, zatvorenu ulicu, promenu naziva objekta ili pogrešno označenu lokaciju. Uz prijavu mogu poslati opis, koordinate i fotografiju. Prijava se ne objavljuje odmah, već je pregledaju moderatori ili stručnjaci za mapu. Jednu oblast može istovremeno da menja više korisnika, pa predlozi mogu biti međusobno suprotni. Odobrena izmena ulazi u glavni model geografskih podataka i dobija novu verziju. Nakon toga sistem generiše izvedene podatke potrebne za pretragu, prikaz mape i navigaciju. Generisanje novih map tiles-a i ažuriranje pretrage mogu trajati duže i treba da budu asinhroni. Korisnici mogu privremeno videti stariju verziju mape, ali mora biti poznato koja verzija predstavlja zvanično objavljeno stanje. Sistem treba da čuva istoriju prijava, odluka moderatora i prethodnih verzija podataka.
            """.trimIndent(),
            drawingChecklist = """
                * aplikaciju za korisnike i interfejs za moderatore;
                * glavne komponente za prijave, pregled i objavljivanje izmena;
                * autoritativni geografski model i njegove verzije;
                * storage za fotografije i druge dokaze;
                * asinhrono generisanje map tiles-a, pretrage ili navigacionih podataka;
                * način na koji sistem radi ako neki izvedeni prikaz još nije ažuriran.
            """.trimIndent(),
            internalAiRubric = "Proveri da li je jasno razdvojena korisnička prijava od odobrene i objavljene izmene. Tile cache, search indeks i navigacioni model ne smeju biti izvor istine za geografsku izmenu. Obrati pažnju na verzionisanje i konfliktne predloge. Navedi dobre strane, nedostatke i jedan predlog za poboljšanje.",
            orderIndex = 34
        ),
        sketchQuestion(
            questionId = "A7.5",
            title = "Sistem za registraciju internet domena i objavljivanje DNS podataka",
            systemDescription = """
                Organizacija upravlja registrom internet domena za jednu nacionalnu domensku zonu. Ovlašćeni registrari u ime korisnika proveravaju dostupnost naziva i šalju zahtev za registraciju domena. Sistem mora da spreči da dva registrara uspešno registruju isti naziv. Nakon registracije čuvaju se vlasnik domena, registrar, period važenja i DNS serveri povezani sa domenom. Korisnik kasnije može da obnovi domen, promeni DNS podatke ili prenese domen drugom registraru. Promena u registru ne postaje trenutno vidljiva na svim DNS serverima, jer objavljivanje i propagacija mogu da potraju. Centralni registar mora ostati autoritativni izvor podataka o vlasništvu i statusu domena. Poseban proces generiše i distribuira DNS zonu autoritativnim DNS serverima. Brza provera dostupnosti može koristiti read model ili cache, ali konačna registracija mora ponovo proveriti autoritativno stanje. Ako DNS objavljivanje ne uspe, registracija domena ne sme biti izgubljena, već sistem treba da evidentira problem i ponovi objavljivanje. Sve promene vlasništva, statusa i DNS podataka moraju imati proverljivu istoriju.
            """.trimIndent(),
            drawingChecklist = """
                * registrare ili njihove aplikacije kao ulazne tačke;
                * glavne backend komponente za dostupnost, registraciju, obnovu i prenos domena;
                * centralni registar kao izvor istine za domen i njegovo vlasništvo;
                * read model ili cache za brzu proveru dostupnosti;
                * asinhrono generisanje i objavljivanje DNS zone;
                * audit i fallback kada objavljivanje na DNS serverima ne uspe.
            """.trimIndent(),
            internalAiRubric = "Proveri da li arhitektura sprečava dve uspešne registracije istog domena. Cache dostupnosti ne sme odlučivati o konačnoj registraciji, a DNS serveri ne treba da budu autoritet za vlasništvo nad domenom. Kritični registracioni tok treba da bude odvojen od sporije propagacije DNS podataka. Navedi šta je dobro, šta nedostaje i jedan praktičan predlog za poboljšanje.",
            orderIndex = 35
        )
    )

    private fun sketchQuestion(
        questionId: String,
        title: String,
        systemDescription: String,
        drawingChecklist: String,
        internalAiRubric: String,
        orderIndex: Int
    ): SeedQuestion {
        return SeedQuestion(
            questionId = questionId,
            level = LearningLevel.ARCHITECT.id,
            type = QuestionType.ARCHITECTURE_SKETCH.id,
            title = title,
            prompt = systemDescription,
            aiFollowUp = null,
            wave = 7,
            difficulty = "expert",
            orderIndex = orderIndex,
            estimatedMinutes = 12,
            steps = listOf(
                SeedStep(
                    stepId = "${questionId}_s1",
                    type = StepType.FREE_TEXT.id,
                    title = "Arhitektonsko skiciranje sistema",
                    instruction = "Nacrtaj predlog arhitekture sistema na papiru, fotografiši crtež i pošalji ga na AI analizu.",
                    codeBlock = drawingChecklist,
                    explanation = internalAiRubric
                )
            )
        )
    }
}
