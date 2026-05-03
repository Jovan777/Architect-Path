package com.example.pmuprojekat.data.seed.senior

import com.example.pmuprojekat.data.seed.SeedQuestion

object SeniorOptimizationStrategySeed {
    val questions: List<SeedQuestion> = listOf(
        SeniorSeedBuilders.optimizationQuestion(
            questionId = "S2.1",
            title = "Izaberi najbolju strategiju za ubrzanje product feed-a",
            prompt = """
            Marketplace platforma ima endpoint za listu proizvoda. U špicevima saobraćaja prosečno vreme odgovora raste na preko 2.5 sekunde. Analiza pokazuje da se pri svakom zahtevu rade:
            •	složeni upiti nad bazom, 
            •	obračun promocija i dostupnosti, 
            •	pozivi ka servisima za rejting i oznake proizvoda. 
            Sistem radi stabilno, ali endpoint postaje preskup pod većim opterećenjem. Tim razmatra tri strategije optimizacije.
            """.trimIndent(),
            strategyOptions = listOf(
                """
                A. Dodavanje cache sloja za feed
                Pamti rezultat često traženih kombinacija i smanjuje broj direktnih upita.
                """.trimIndent(),
                """
                B. Uvođenje asinhronog precompute procesa
                Unapred računa deo podataka i čuva ih spremne za brzo čitanje.
                """.trimIndent(),
                """
                C. Horizontalno povećanje broja instanci API servisa
                Povećava kapacitet obrade bez promene logike endpoint-a.
                """.trimIndent()
            ),
            correctStrategy = "B. Uvođenje asinhronog precompute procesa",
            gains = listOf(
                "manji trošak računanja u trenutku korisničkog zahteva",
                "bolji odziv pod opterećenjem",
                "rasterećenje baze i zavisnih servisa pri čitanju"
            ),
            risks = listOf(
                "moguća zastarelost dela podataka",
                "složeniji pipeline za osvežavanje podataka",
                "veća potreba za monitoringom precompute procesa"
            ),
            aiFollowUp = """
            Zašto cache deluje kao prirodan prvi izbor, a ipak u nekim sistemima nije najbolja početna optimizacija?

            3. Treći tip — Incident analiza u produkciji
            """.trimIndent(),
            wave = 1,
            orderIndex = 2
        ),

        SeniorSeedBuilders.optimizationQuestion(
            questionId = "S2.2",
            title = "Izaberi strategiju za stabilniji tok praćenja napretka korisnika",
            prompt = """
            EdTech platforma beleži napredak korisnika kroz veliki broj događaja:
            •	start lekcije, 
            •	pauza, 
            •	završetak segmenta, 
            •	rezultat kviza, 
            •	povratak na prethodni deo sadržaja. 
            Trenutno se svaki događaj odmah upisuje, obrađuje i agregira u istom online toku, kako bi profesor i korisnik što pre videli osvežen napredak. Pod većim brojem istovremenih korisnika sistem ostaje funkcionalan, ali:
            •	endpoint za čuvanje napretka usporava, 
            •	dashboard kasni, 
            •	baza trpi nagle skokove upisa, 
            •	deo događaja je tehnički validan, ali poslovno nije hitan za trenutno prikazivanje.
            """.trimIndent(),
            strategyOptions = listOf(
                """
                A. Dodavanje agresivnog write-through cache sloja
                Smanjuje direktan pritisak na bazu, ali uvodi dodatni sloj sinhronizacije.
                """.trimIndent(),
                """
                B. Uvođenje event queue i odvajanje hitnih i nehitatnih ažuriranja
                Kritični podaci ostaju brzi, a deo agregacija i sekundarne obrade prelazi u asinhroni tok.
                """.trimIndent(),
                """
                C. Horizontalno povećanje broja instanci progress servisa
                Povećava kapacitet prijema zahteva bez većih promena arhitekture.
                """.trimIndent()
            ),
            correctStrategy = "B. Uvođenje event queue i odvajanje hitnih i nehitatnih ažuriranja",
            gains = listOf(
                "smanjuje pritisak da svaka obrada bude završena u korisničkom request-u",
                "omogućava razdvajanje kritičnih i sekundarnih obrada napretka",
                "poboljšava stabilnost glavnog toka pri skokovima opterećenja"
            ),
            risks = listOf(
                "uvodi potrebu za boljim praćenjem kašnjenja i obrade reda",
                "može povećati vremensko kašnjenje za deo agregiranih prikaza",
                "zahteva jasnije definisanje koji podaci moraju biti „odmah tačni”, a koji smeju kasniti"
            ),
            aiFollowUp = """
            Zašto je u nekim sistemima najveća optimizacija zapravo pravilna podela na „šta mora odmah“ i „šta sme malo kasniti“?
            """.trimIndent(),
            wave = 2,
            orderIndex = 7
        ),

        SeniorSeedBuilders.optimizationQuestion(
            questionId = "S2.3",
            title = "Izaberi najbolju strategiju za ubrzanje pretrage uz očuvanje kvaliteta rezultata",
            prompt = """
            Pravni informacioni sistem omogućava pretragu kroz veliku bazu ugovora, presuda i službenih akata. Trenutno pretraga radi nad operativnom bazom i za složenije upite:
            •	odgovor kasni, 
            •	rangiranje rezultata nije uvek dosledno, 
            •	opterećenje baze raste u radnom vremenu, 
            •	deo korisnika traži brzu pretragu, ali drugi deo zahteva visoku preciznost filtriranja.
            """.trimIndent(),
            strategyOptions = listOf(
                """
                A. Uvođenje posebnog search indeksa optimizovanog za čitanje
                Pretraga se odvaja od operativne baze i dobija poseban model indeksiranja.
                """.trimIndent(),
                """
                B. Agresivno keširanje najčešćih upita
                Najčešće pretrage se pamte i brzo vraćaju bez dodatnog izvršavanja.
                """.trimIndent(),
                """
                C. Dodavanje više instanci API sloja za pretragu
                Povećava broj zahteva koje sistem može da primi paralelno.
                """.trimIndent()
            ),
            correctStrategy = "A. Uvođenje posebnog search indeksa optimizovanog za čitanje",
            gains = listOf(
                "smanjuje direktno opterećenje operativne baze",
                "omogućava bolju kontrolu nad modelom pretrage i rangiranja",
                "povećava fleksibilnost za specijalizovane filtere i pretragu"
            ),
            risks = listOf(
                "uvodi kašnjenje ili složenost u sinhronizaciji između izvora i indeksa",
                "zahteva dodatni operativni nadzor nad pipeline-om indeksiranja",
                "može zakomplikovati analizu greške kada korisnik ne vidi dokument koji je upravo dodat"
            ),
            aiFollowUp = """
            Zašto je kod pretrage često važnije odvojiti „sistem istine“ od „sistema za efikasno čitanje“, čak i kada to uvodi dodatnu složenost?
            """.trimIndent(),
            wave = 3,
            orderIndex = 12
        ),

        SeniorSeedBuilders.optimizationQuestion(
            questionId = "S2.4",
            title = "Izaberi najbolju strategiju za brži prikaz velikih geosnimaka",
            prompt = """
            Sistem za analizu satelitskih snimaka omogućava korisnicima da pregledaju velike geografske snimke u web i mobilnom interfejsu. Trenutno se pri otvaranju snimka učitava veliki deo originalnog fajla, a zatim se na serverskoj strani priprema prikaz za trenutno zumiranje i pomeranje mape.
            Sistem radi, ali tim primećuje:
            •	prvi prikaz snimka često kasni kod velikih fajlova; 
            •	korisnici najčešće gledaju samo mali region, ali sistem priprema mnogo širi deo snimka; 
            •	zumiranje i pomeranje mape proizvode skupe ponovljene obrade; 
            •	povećanje broja servera pomaže samo delimično; 
            •	najsporiji deo nije sama analiza modelom, već priprema prikaza velikog rastera za korisnički interfejs.
            """.trimIndent(),
            strategyOptions = listOf(
                """
                A. Dodati više instanci servisa za prikaz snimaka
                Povećava kapacitet obrade, ali zadržava isti način pripreme velikih fajlova.
                """.trimIndent(),
                """
                B. Uvesti tile/pyramid model prikaza i učitavanje samo regiona koji je korisniku trenutno potreban
                Snimak se unapred deli na nivoe zumiranja i manje delove, pa se pri prikazu učitava samo relevantan deo.
                """.trimIndent(),
                """
                C. Smanjiti kvalitet svih satelitskih snimaka pri upload-u
                Smanjuje veličinu podataka, ali trajno gubi deo korisnih informacija.
                """.trimIndent()
            ),
            correctStrategy = "B. Uvesti tile/pyramid model prikaza i učitavanje samo regiona koji je korisniku trenutno potreban",
            gains = listOf(
                "korisnik brže dobija prvi vidljiv prikaz snimka",
                "smanjuje se količina podataka koja se obrađuje za trenutno vidljivi deo mape",
                "zumiranje i pomeranje postaju predvidljiviji i jeftiniji za sistem"
            ),
            risks = listOf(
                "uvodi se dodatna složenost generisanja i čuvanja tile-ova",
                "moguće je kašnjenje između prijema novog snimka i dostupnosti svih nivoa prikaza",
                "zahteva jasnu strategiju invalidacije kada se snimak ili metapodaci promene"
            ),
            aiFollowUp = """
            Zašto je kod velikih geosnimaka često važnije optimizovati količinu podataka koja se čita i prikazuje, nego samo dodavati više serverskog kapaciteta?
            """.trimIndent(),
            wave = 4,
            orderIndex = 17
        ),

        SeniorSeedBuilders.optimizationQuestion(
            questionId = "S2.5",
            title = "Izaberi najbolju strategiju za ubrzanje i stabilizaciju rule engine-a",
            prompt = """
            Sistem za obračun polisa osiguranja koristi veliki skup poslovnih pravila koja zavise od:
            •	vrste polise, 
            •	regiona, 
            •	istorije šteta, 
            •	starosti klijenta, 
            •	dodatnih regulatornih izuzetaka. 
            Trenutno se sva pravila evaluiraju kroz jedan centralni rule engine pri svakom obračunu. Sistem funkcioniše, ali tim primećuje:
            •	vreme obračuna raste sa brojem pravila, 
            •	teško je razumeti koja pravila su stvarno relevantna za dati slučaj, 
            •	deo pravila se skoro nikada ne aktivira, ali se i dalje prolazi kroz njihovu evaluaciju, 
            •	promene u pravilima povećavaju rizik regresije u neočekivanim granama sistema.
            """.trimIndent(),
            strategyOptions = listOf(
                """
                A. Dodati više instanci rule engine servisa
                Povećava kapacitet, ali ostavlja istu internu složenost evaluacije.
                """.trimIndent(),
                """
                B. Uvesti prethodnu klasifikaciju slučaja i aktivirati samo relevantne rule set-ove
                Smanjuje broj pravila koja se razmatraju po zahtevu, ali uvodi dodatni sloj selekcije.
                """.trimIndent(),
                """
                C. Prevesti sva pravila u jedan agresivno optimizovan kompajlirani bundle
                Smanjuje overhead izvršavanja, ali otežava razumevanje i parcijalno menjanje pravila.
                """.trimIndent()
            ),
            correctStrategy = "B. Uvesti prethodnu klasifikaciju slučaja i aktivirati samo relevantne rule set-ove",
            gains = listOf(
                "smanjuje broj pravila koja se evaluiraju po konkretnom slučaju",
                "olakšava segmentaciju testiranja po grupama pravila",
                "može poboljšati razumljivost ponašanja sistema po domenskim zonama"
            ),
            risks = listOf(
                "povećava značaj ispravne klasifikacije pre same evaluacije",
                "uvodi rizik da slučaj završi u pogrešnom skupu pravila",
                "zahteva jasniju disciplinu u organizaciji rule set-ova"
            ),
            aiFollowUp = """
            Zašto je u ovakvom sistemu opasno optimizovati samo brzinu izvršavanja, a ne i samu strukturu odlučivanja koja određuje koja pravila uopšte ulaze u igru?
            3. Treći tip — Incident analiza u produkciji
            """.trimIndent(),
            wave = 5,
            orderIndex = 22
        ),

        SeniorSeedBuilders.optimizationQuestion(
            questionId = "S2.6",
            title = "Izaberi najbolju strategiju za smanjenje nepotrebnih update-a u velikom MMO svetu",
            prompt = """
            MMO igra ima veliki otvoreni svet u kome server stalno šalje klijentima informacije o:
            •	poziciji drugih igrača; 
            •	kretanju neprijatelja; 
            •	promenama objekata u okolini; 
            •	borbenim efektima; 
            •	događajima u zoni. 
            Trenutno veliki deo update-a prolazi kroz isti tok i previše klijenata dobija informacije koje im nisu stvarno relevantne. Sistem radi stabilno, ali tim primećuje:
            •	mrežni saobraćaj naglo raste u gusto naseljenim zonama; 
            •	klijenti dobijaju update-e o objektima i igračima koji su predaleko da bi uticali na njih; 
            •	povećanje server kapaciteta pomaže samo delimično; 
            •	latencija raste kada se mnogo igrača okupi u istom regionu; 
            •	deo problema nastaje zato što sistem ne filtrira dovoljno dobro ko treba da dobije koji update.
            """.trimIndent(),
            strategyOptions = listOf(
                """
                A. Dodati više server instanci za slanje update-a
                Povećava kapacitet, ali ne smanjuje količinu nepotrebnih poruka.
                """.trimIndent(),
                """
                B. Uvesti interest management i prostorno filtriranje update-a
                Server šalje klijentu samo događaje i entitete koji su relevantni za njegovu lokaciju, vidljivost ili zonu interesa.
                """.trimIndent(),
                """
                C. Smanjiti učestalost svih update-a za sve igrače
                Smanjuje mrežni saobraćaj, ali može pogoršati osećaj real-time odziva.
                """.trimIndent()
            ),
            correctStrategy = "B. Uvesti interest management i prostorno filtriranje update-a",
            gains = listOf(
                "smanjuje se broj nepotrebnih mrežnih poruka po klijentu",
                "server može bolje da kontroliše koji događaji su relevantni za kog igrača",
                "korisničko iskustvo može ostati stabilnije u gusto naseljenim zonama"
            ),
            risks = listOf(
                "uvodi se složenija logika određivanja vidljivosti i zone interesa",
                "greške u filtriranju mogu dovesti do toga da igrač ne vidi važan entitet ili događaj",
                "potrebno je pažljivije testirati granične slučajeve pri kretanju između zona"
            ),
            aiFollowUp = """
            Zašto je kod velikih multiplayer sistema često bolje smanjiti broj nepotrebnih poruka nego samo povećavati infrastrukturu koja ih šalje? 

            3. Treći tip — Incident analiza u produkciji
            """.trimIndent(),
            wave = 6,
            orderIndex = 27
        ),

        SeniorSeedBuilders.optimizationQuestion(
            questionId = "S2.7",
            title = "Izaberi najbolju strategiju za stabilniji AI inference pod mešovitim opterećenjem",
            prompt = """
            AI platforma u produkciji opslužuje više vrsta zahteva:
            •	kratke embedding zahteve, 
            •	srednje duge klasifikacione pozive, 
            •	duge generativne zahteve, 
            •	batch scoring za interne timove. 
            Trenutno svi zahtevi prolaze kroz istu inference infrastrukturu i dele isti fond GPU resursa, bez jasnih kvota, prioriteta i odvojenih SLA ciljeva po tipu posla. Sistem radi, ali tim primećuje:
            •	kratki zahtevi povremeno čekaju disproporcionalno dugo, 
            •	batch poslovi umeju da „pojedu“ kapacitet u periodima kad su interaktivni korisnici najaktivniji, 
            •	prosečna iskorišćenost GPU-a je visoka, ali korisnički SLA nije stabilan, 
            •	povećanje broja GPU instanci pomaže samo delimično i skupo je.
            """.trimIndent(),
            strategyOptions = listOf(
                """
                A. Dodati još GPU kapaciteta bez promene raspodele zahteva
                Povećava sirovi kapacitet, ali zadržava isti model konkurencije.
                """.trimIndent(),
                """
                B. Uvesti razdvajanje klasa inference poslova sa zasebnim scheduling pravilima i kvotama
                Interaktivni i batch tokovi više ne dele istu cenu čekanja.
                """.trimIndent(),
                """
                C. Spustiti maksimalnu dužinu svih generativnih zahteva
                Smanjuje deo opterećenja, ali menja funkcionalnost proizvoda.
                """.trimIndent()
            ),
            correctStrategy = "B. Uvesti razdvajanje klasa inference poslova sa zasebnim scheduling pravilima i kvotama",
            gains = listOf(
                "smanjuje verovatnoću da batch poslovi uguše interaktivne AI zahteve",
                "omogućava različite SLA ciljeve za različite tipove inference poslova",
                "može poboljšati predvidivost korisničkog iskustva bez promene samih modela"
            ),
            risks = listOf(
                "povećava složenost upravljanja kapacitetom i pravilima raspodele",
                "zahteva da tim jasno definiše klase prioriteta i njihove granice",
                "uvodi rizik da loše podešene kvote naprave neefikasno neiskorišćen kapacitet"
            ),
            aiFollowUp = """
            Zašto je kod inference platformi ponekad važnije uvesti bolju raspodelu nego samo dodavati više skupih resursa?
            """.trimIndent(),
            wave = 7,
            orderIndex = 32
        )
    )
}
