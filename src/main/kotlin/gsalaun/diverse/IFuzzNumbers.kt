package gsalaun.diverse

interface IFuzzNumbers {
    /**
     * Generates a random integer value between a min (inclusive) and a max (exclusive) value.
     *
     * @param minValue The inclusive lower bound of the random number returned.
     * @param maxValue The inclusive upper bound of the random number returned.
     * @return An integer value generated randomly.
     */
    fun generateInteger(minValue: Int = Int.MIN_VALUE, maxValue: Int = Int.MAX_VALUE): Int

    /**
     * Generates a random positive integer value.
     *
     * @param maxValue The inclusive upper bound of the random number returned.
     * @return A positive integer value generated randomly.
     */
    fun generatePositiveInteger(maxValue: Int = Int.MAX_VALUE): Int

    /**
     * Generates a random positive double value.
     *
     * @param minValue (optional) The inclusive positive lower bound of the random number returned.
     * @param maxValue (optional) The inclusive positive upper bound of the random number returned
     * @return A double value generated randomly.
     * @throws ArgumentOutOfRangeException if minValue is greater than maxValue
     */
    fun generateDouble(minValue: Double = Double.MIN_VALUE, maxValue: Double = Double.MAX_VALUE): Double

    /**
     * Generates a random positive double value.
     *
     * @param minValue (optional) The inclusive positive lower bound of the random number returned.
     * @param maxValue (optional) The inclusive positive upper bound of the random number returned
     * @return A positive double value generated randomly.
     * @throws ArgumentOutOfRangeException if minValue is greater than maxValue
     * @throws ArgumentOutOfRangeException if minValue is negative
     */
    fun generatePositiveDouble(minValue: Double = 0.0, maxValue: Double = Double.MAX_VALUE): Double

    /**
     * Generates a random long value.
     *
     * @param minValue The inclusive lower bound of the random number returned.
     * @param maxValue The inclusive upper bound of the random number returned.
     * @return A long value generated randomly.
     */
    fun generateLong(minValue: Long = Long.MIN_VALUE, maxValue: Long = Long.MAX_VALUE): Long
}
