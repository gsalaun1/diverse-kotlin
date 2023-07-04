package gsalaun.diverse

/// <summary>
/// Fuzz texts in Latin (see. Lorem Ipsum... https://www.lipsum.com/).
/// </summary>
/**
 * Fuzz texts in Latin (see. Lorem Ipsum... https://www.lipsum.com/).
 *
 */
interface IFuzzLorem {

    /**
     * Generates a random letter.
     *
     * @return The generated letter.
     */
    fun generateLetter(): Char

    /**
     * Generates random latin words.
     *
     * @param number (optional) Number of words to generate.
     * @return The generated latin words.
     */
    fun generateWords(number: Int = 5): Collection<String>

    /**
     * Generate a sentence in latin.
     *
     * @param nbOfWords (optional) Number of words for this sentence.
     * @return The generated sentence in latin.
     */
    fun generateSentence(nbOfWords: Int = 6): String

    /**
     * Generates a paragraph in latin.
     *
     * @param nbOfSentences (optional) Number of sentences for this paragraph.
     * @return The generated paragraph in latin.
     */
    fun generateParagraph(nbOfSentences: Int = 5): String

    /**
     * Generates a collection of paragraphs.
     *
     * @param nbOfParagraphs (optional) Number of paragraphs to generate.
     * @return The collection of paragraphs.
     */
    fun generateParagraphs(nbOfParagraphs: Int = 3): Collection<String>

    /// <summary>
    /// Generates a text in latin with a specified number of paragraphs.
    /// </summary>
    /// <param name="nbOfParagraphs">(optional) Number of paragraphs to generate.</param>
    /// <returns>The generated text in latin.</returns>
    fun generateText(nbOfParagraphs: Int = 3): String
}