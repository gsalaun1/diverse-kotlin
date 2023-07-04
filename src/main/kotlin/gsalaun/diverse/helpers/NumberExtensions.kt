package gsalaun.diverse.helpers

object NumberExtensions {

    /**
     * Computes the number of long included in the considered range.
     *
     * @param minValue (optional) the minimum value.
     * @param maxValue (optional) the maximum value
     * @return The number of elements in the considered range.
     */
    fun computeRange(minValue: Long = Long.MIN_VALUE, maxValue: Long = Long.MAX_VALUE): Long {
        val inclusiveMaxBound =
            if (maxValue == Long.MAX_VALUE) {
                Long.MAX_VALUE
            } else {
                maxValue + 1
            }
        return inclusiveMaxBound - minValue
    }
}
