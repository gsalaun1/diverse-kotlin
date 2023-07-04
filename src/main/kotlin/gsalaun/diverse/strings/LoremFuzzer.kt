package gsalaun.diverse.strings

import gsalaun.diverse.ArgumentOutOfRangeException
import gsalaun.diverse.IFuzz
import gsalaun.diverse.IFuzzLorem
import java.util.Locale

class LoremFuzzer(val fuzzer: IFuzz) : IFuzzLorem {

    companion object {
        private const val MIN_NUMBER_OF_WORDS_IN_A_SENTENCE = 2
        private val _alphabet = "abcdefghijklmnopqrstuvwxyz".toList()

        /**
         * Gets the letters used by the {@Link generateLetter} method.
         */
        val alphabet: List<Char>
            get() {
                return _alphabet
            }
    }

    override fun generateLetter() = fuzzer.pickOneFrom(alphabet)

    override fun generateWords(number: Int) =
        (0 until number).map {
            fuzzer.pickOneFrom(Latin.words)
        }

    override fun generateSentence(nbOfWords: Int): String {
        if (nbOfWords < MIN_NUMBER_OF_WORDS_IN_A_SENTENCE) {
            throw ArgumentOutOfRangeException("nbOfWords", "A sentence must have more than 1 word.")
        }

        val words = generateWords(nbOfWords)

        return "${
            words.joinToString(" ")
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        }.";
    }

    override fun generateParagraph(nbOfSentences: Int) =
        (0 until nbOfSentences).map {
            val nbWords = fuzzer.generateInteger(MIN_NUMBER_OF_WORDS_IN_A_SENTENCE, 10)
            generateSentence(nbWords)
        }.joinToString(" ")

    override fun generateParagraphs(nbOfParagraphs: Int) =
        (0 until nbOfParagraphs).map { generateParagraph() }

    override fun generateText(nbOfParagraphs: Int): String {
        val paragraphs = generateParagraphs(nbOfParagraphs)
        return paragraphs.joinToString(System.lineSeparator() + System.lineSeparator())
    }
}