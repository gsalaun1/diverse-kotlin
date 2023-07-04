package gsalaun.diverse

import gsalaun.diverse.persons.Gender
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(AllTestsFixtures::class)
class PersonFuzzerShould {

    @Test
    fun `be able to fuzz firstNames for male`() {
        val fuzzer = Fuzzer(381025586)

        val firstNames = (0 until 10).map { fuzzer.generateFirstName(Gender.MALE) }

        assertThat(firstNames).containsExactly(
            "Enok",
            "Aata",
            "Isley",
            "Jin",
            "Simba",
            "Jorrel",
            "Moussa",
            "Esteban",
            "Davon",
            "Mugisa"
        );
    }

    @Test
    fun `be able to fuzz firstNames for female`() {
        val fuzzer = Fuzzer(1834164083)

        val firstNames = (0 until 10).map {
            fuzzer.generateFirstName(Gender.FEMALE)
        }

        assertThat(firstNames).containsExactly(
            "Meirav",
            "Ayisha",
            "Thenjiwe",
            "Georgia",
            "Tandice",
            "Orazia",
            "Noémie",
            "Anja",
            "Esther",
            "Eliszabeth"
        );
    }

    @Test
    fun `be able to fuzz firstNames for every genders`() {
        val fuzzer = Fuzzer(1340516487)

        val firstNames = (0 until 10).map { fuzzer.generateFirstName() }

        assertThat(firstNames).containsExactly(
            "Luzita",
            "Eun-Kyung",
            "Dae",
            "Jiao",
            "Davyd",
            "Govind",
            "Choon-He",
            "Hyo",
            "Khaled",
            "Chiquin"
        );
    }

    @Test
    fun `be able to fuzz lastName for every firstName`() {
        val fuzzer = Fuzzer(2000210944)

        val lastNames = (0 until 10).map {
            val firstName = fuzzer.generateFirstName()
            "$firstName ${fuzzer.generateLastName(firstName)}"
        }

        assertThat(lastNames).containsExactly(
            "Davyd Bhundoo",
            "Mentari Liáng",
            "Amruta Féng",
            "Dakarai Banda",
            "Rokhaya Diak",
            "Kwanza Sissoko",
            "Louise Roccaserra",
            "Anneke Gomes",
            "Nicolas Dupont",
            "Zayneb Diabaté"
        )
    }

    @Test
    fun `be able to fuzz diverse persons`() {
        val fuzzer = Fuzzer(722752479)

        val persons = (0 until 10).map {
            fuzzer.generatePerson()
        }

        assertThat(persons.map { it.toString() })
            .containsExactly(
                """Mx. Trini RUUD (Non binary) truud@microsoft.com (married -  age: 95 years)
53 bd Maréchal de Lattre de Tassigny, Provence-Alpes-Côte d'Azur, Nice 06300, France
                """.trimIndent(),
                """Ms. Nkechi TESFAYE (Female) nkechi.tesfaye@aol.com ( age: 25 years)
352 North 44th Street, Pennsylvania, Philadelphia 19118, USA
                """.trimIndent(),
                """Mrs. Lashae WILLIAMS (Female) lashae.williams@aol.com (married -  age: 83 years)
58 4th Avenue, Florida, Miami 33144, USA
                """.trimIndent(),
                """Mr. Kinta GREENWOOD (Male) kinta.greenwood@microsoft.com ( age: 43 years)
364 Collins Avenue, Pennsylvania, Philadelphia 19158, USA
                """.trimIndent(),
                """Mrs. Li Mei MADAN (Female) lmadan@ibm.com (married -  age: 31 years)
83 Changjiang Binjiang Road，Yuzhong District, Yu Zhong, Chinese Municipal, Tianjin 300691, China
                """.trimIndent(),
                """Mrs. Ulrike DE FUNES (Female) ude-funes@yahoo.fr (married -  age: 23 years)
174 rue des Caisseries, Ile de France, Saint-Denis 93200, France
                """.trimIndent(),
                """Mr. Tanja MOREAU (Male) tmoreau@aol.com ( age: 36 years)
226 rue des Caisseries, Bretagne, Brest 29200, France
                """.trimIndent(),
                """Mrs. Sunita NAKAMURA (Female) sunita.nakamura@yopmail.com (married -  age: 36 years)
247 E Man Town, Chinese Municipal, Shanghai 200046, China
                """.trimIndent(),
                """Ms. João MUÑOZ (Female) jmunoz@yopmail.com ( age: 35 years)
376 quai Claude-Bernard, Ile de France, Paris 75019, France
                """.trimIndent(),
                """Mx. Nicolas ZIMMERMAN (Non binary) nzimmerman@microsoft.com ( age: 40 years)
19 quai Claude-Bernard, Provence-Alpes-Côte d'Azur, Marseille 13019, France
                """.trimIndent()
            )
    }

    @Test
    fun `be able to generate email from scratch`() {
        val fuzzer = Fuzzer(1996243347)

        val email = fuzzer.generateEMail()
        val email2 = fuzzer.generateEMail()
        val email3 = fuzzer.generateEMail()

        assertThat(email).isEqualTo("jensson.jacobsen@protonmail.com")
        assertThat(email2).isEqualTo("rfantasia@kolab.com")
        assertThat(email3).isEqualTo("enrico.lopes@protonmail.com")
    }

    @Test
    fun `generate email with dashes instead of spaces and pure ascii char`() {
        val fuzzer = Fuzzer(40816378)

        val email = fuzzer.generateEMail("Saïd Ef", "Ben Achour")

        assertThat(email).isEqualTo("said-ef.ben-achour@gmail.com")
    }

    @RepeatedTest(10000)
    fun `generate age between 18 and 97 years`() {
        val fuzzer = Fuzzer()

        val age = fuzzer.generateAge()

        assertThat(age).isGreaterThan(17).isLessThan(98)
    }

    @Test
    fun `generate address with french format`() {
        val fuzzer = Fuzzer()

        val person = fuzzer.generatePerson()

        assertThat(person.address.streetNumber).isInstanceOf(String::class.java).isNotEmpty()
        assertThat(person.address.streetName).isNotEmpty()
        assertThat(person.address.street).isNotEmpty()
        assertThat(person.address.city).isNotEmpty()
        assertThat(person.address.countryLabel).isNotEmpty()
    }
}