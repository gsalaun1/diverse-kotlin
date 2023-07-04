package gsalaun.diverse

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(AllTestsFixtures::class)
class LoremFuzzerShould {

    @Test
    fun `generate words`() {
        val fuzzer = Fuzzer(1317467515)

        val words = fuzzer.generateWords(3)

        assertThat(words).containsExactly("sed", "ducimus", "non")
    }

    @Test
    fun `generate 5 words by default`() {
        val fuzzer = Fuzzer()

        val words = fuzzer.generateWords()

        assertThat(words).hasSize(5)
    }

    @Test
    fun `generate a sentence`() {
        val fuzzer = Fuzzer(1981116596)

        val sentence = fuzzer.generateSentence(nbOfWords = 4)

        assertThat(sentence).isEqualTo("Quia dolor repellat quidem.")
    }

    @Test
    fun `throw when generating a sentence with 1 word`() {
        val fuzzer = Fuzzer()

        assertThatThrownBy {
            fuzzer.generateSentence(1)
        }.isInstanceOf(ArgumentOutOfRangeException::class.java)
            .hasMessageContaining("A sentence must have more than 1 word. (Parameter 'nbOfWords')")
    }

    @Test
    fun `generate a paragraph`() {
        val fuzzer = Fuzzer(1811180938)

        val sentence = fuzzer.generateParagraph(nbOfSentences = 5)

        assertThat(sentence).isEqualTo("Necessitatibus molestiae rerum voluptates necessitatibus quia officia voluptatem et necessitatibus. Aspernatur iure. Temporibus libero itaque. Est laudantium delectus qui quia qui necessitatibus suscipit fugiat consequuntur. Amet recusandae animi nobis laboriosam.")
    }

    @Test
    fun `generate Paragraphs`() {
        val fuzzer = Fuzzer(1811180938)

        val paragraphs = fuzzer.generateParagraphs(3)

        assertThat(paragraphs).hasSize(3)
    }

    @Test
    fun `generate a text`() {
        val fuzzer = Fuzzer(1811180938)
        val text = fuzzer.generateText(3)
        assertThat(text).isEqualTo(
            """
Necessitatibus molestiae rerum voluptates necessitatibus quia officia voluptatem et necessitatibus. Aspernatur iure. Temporibus libero itaque. Est laudantium delectus qui quia qui necessitatibus suscipit fugiat consequuntur. Amet recusandae animi nobis laboriosam.

Magni sed expedita voluptatibus fugiat. Atque atque voluptate nulla necessitatibus quia suscipit. Culpa repellat et veritatis. Est et voluptatem enim assumenda est impedit quasi sunt omnis. Quibusdam velit veritatis recusandae quas nostrum in.

Eligendi quam temporibus enim aut. Delectus deleniti exercitationem magni totam nesciunt soluta quia voluptas eum. Aut placeat facere dolore consequatur culpa harum voluptas quam. Voluptatem est aut provident debitis repudiandae. Rem repellendus aspernatur exercitationem possimus voluptate eum rem perferendis expedita.
        """.trimIndent()
        )
    }

    @Test
    fun `generate letter`() {
        val fuzzer = Fuzzer(1117624570)

        var letter = fuzzer.generateLetter()
        assertThat(letter).isEqualTo('t')

        letter = fuzzer.generateLetter()
        assertThat(letter).isEqualTo('p')
    }
}