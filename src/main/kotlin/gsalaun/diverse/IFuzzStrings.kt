package gsalaun.diverse

import gsalaun.diverse.strings.Feeling


interface IFuzzStrings {

    /**
     * Generates a random adjective based on a feeling.
     *
     * @param feeling The expected feeling of the adjective
     * @return An adjective based on a particular feeling or random one if not provided
     */
    fun generateAdjective(feeling: Feeling? = null): String

    /**
     * Generates a string from a given 'diverse' format
     * (# for a single digit number, X for upper-cased letter, x for lower-cased letter).
     *
     * @param diverseFormat The 'diverse' format to use
     *                      (# for a single digit number, X for upper-cased letter, x for lower-cased letter).
     * @return A randomly generated string following the 'diverse' format.
     */
    fun generateStringFromPattern(diverseFormat: String): String
}