package gsalaun.diverse.datetimes

import gsalaun.diverse.IFuzz
import gsalaun.diverse.IFuzzDatesAndTime
import java.lang.IllegalArgumentException
import java.time.DateTimeException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class DateTimeFuzzer(private val fuzzer: IFuzz) : IFuzzDatesAndTime {
    override fun generateDateTime() = generateDateTimeBetween(LocalDateTime.MIN, LocalDateTime.MAX)
    override fun generateDate() = generateDateTime().toLocalDate()

    override fun generateDateTimeBetween(minValue: LocalDateTime, maxValue: LocalDateTime): LocalDateTime {
        val nbDays = minValue.until(maxValue, ChronoUnit.DAYS)
        val midInterval = minValue.plusDays(nbDays / 2)
        val maxDaysAllowedBefore = minValue.until(midInterval, ChronoUnit.DAYS)
        val maxDaysAllowedAfter = midInterval.until(maxValue, ChronoUnit.DAYS)
        val maxDays = Math.min(maxDaysAllowedBefore, maxDaysAllowedAfter)
        return midInterval.plusDays(fuzzer.generateInteger(-maxDays.toInt(), maxDays.toInt()).toLong())
    }

    override fun generateDateTimeBetween(minDate: String, maxDate: String): LocalDateTime {
        val minDateTime =
            try {
                LocalDate.parse(minDate, DateTimeFormatter.ofPattern("yyyy/MM/dd")).atStartOfDay()
            } catch (exception: DateTimeException) {
                null
            }
        val maxDateTime =
            try {
                LocalDate.parse(maxDate, DateTimeFormatter.ofPattern("yyyy/MM/dd")).atStartOfDay()
            } catch (exception: DateTimeException) {
                null
            }

        if (minDateTime == null || maxDateTime == null) {
            throw buildProperArgumentException(minDateTime != null, minDate, maxDateTime != null, maxDate)
        }

        return generateDateTimeBetween(minDateTime, maxDateTime)
    }

    companion object {
        private fun buildProperArgumentException(
            minDateOk: Boolean,
            minDate: String,
            maxDateOk: Boolean,
            maxDate: String,
        ): IllegalArgumentException {
            val message: String?

            if (!minDateOk && !maxDateOk) {
                message =
                    "Min and Max dates are missing or incorrect. minDate: $minDate maxDate: $maxDate. minDate and maxDate should follow the pattern: yyyy/MM/dd"
            } else {
                var paramName: String? = null
                var incorrectValue: String? = null

                if (!minDateOk) {
                    paramName = "minDate"
                    incorrectValue = minDate
                }

                if (!maxDateOk) {
                    paramName = "maxDate"
                    incorrectValue = maxDate
                }

                message =
                    "$paramName is missing or incorrect. $paramName: $incorrectValue. $paramName should follow the pattern: yyyy/MM/dd"
            }

            return IllegalArgumentException(message)
        }
    }
}
