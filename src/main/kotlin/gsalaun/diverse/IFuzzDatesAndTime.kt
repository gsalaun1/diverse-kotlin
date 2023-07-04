package gsalaun.diverse

import java.time.LocalDate
import java.time.LocalDateTime

interface IFuzzDatesAndTime {
    /**
     * Generates a random {@link LocalDateTime}.
     *
     * @return A {@link LocalDateTime} value generated randomly.
     */
    fun generateDateTime(): LocalDateTime

    /**
     * Generates a random {@link LocalDate}.
     *
     * @return A {@link LocalDate} value generated randomly.
     */
    fun generateDate(): LocalDate

    /**
     * Generates a random {@link LocalDateTime} in a Time Range
     *
     * @param minValue The minimum inclusive boundary of the Time Range for this {@link LocalDateTime} generation.
     * @param maxValue The maximum inclusive boundary of the Time Range for this {@link LocalDateTime} generation.
     * @return A {@link LocalDateTime} instance between the min and the max inclusive boundaries.
     */
    fun generateDateTimeBetween(minValue: LocalDateTime, maxValue: LocalDateTime): LocalDateTime

    /**
     * Generates a random {@link LocalDateTime} in a Time Range
     *
     * @param minDate The minimum inclusive boundary of the Time Range for this {@link LocalDateTime} generation,
     *                  specified as a yyyy/MM/dd string.
     * @param maxDate The maximum inclusive boundary of the Time Range for this {@link LocalDateTime} generation,
     *                  specified as a yyyy/MM/dd string.
     * @return A {@link LocalDateTime} instance between the min and the max inclusive boundaries.
     */
    fun generateDateTimeBetween(minDate: String, maxDate: String): LocalDateTime
}
