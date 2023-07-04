package gsalaun.diverse

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.stream.Stream

@ExtendWith(AllTestsFixtures::class)
class DateTimeFuzzerShould {

    @Test
    fun `generate different dates`() {
        val fuzzer = Fuzzer()

        val nbOfGeneration = 1000
        val generatedDateTimes = (0 until nbOfGeneration).map {
            fuzzer.generateDateTime()
        }.toSet()

        checkThatGeneratedDatesAreDiverseAMinimum(generatedDateTimes, nbOfGeneration, 90)
    }

    @RepeatedTest(10000)
    fun `generate different dates between`() {
        val fuzzer = Fuzzer()

        val minDate = LocalDateTime.of(2020, 3, 28, 0, 0)
        val maxDate = LocalDateTime.of(2020, 11, 1, 0, 0)

        val generated = fuzzer.generateDateTimeBetween(minDate, maxDate)

        assertThat(generated).isAfterOrEqualTo(minDate).isBeforeOrEqualTo(maxDate)
    }

    @ParameterizedTest
    @MethodSource("provideWrongMinDate")
    fun `throws IllegalArgumentException when calling generateDateTimeBetween with empty or incorrect minDate`(
        minDate: String,
        maxDate: String,
    ) {
        val fuzzer = Fuzzer()

        assertThatThrownBy {
            fuzzer.generateDateTimeBetween(minDate, maxDate)
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("minDate is missing or incorrect. minDate: $minDate. minDate should follow the pattern: yyyy/MM/dd")
    }

    @ParameterizedTest
    @MethodSource("provideWrongMaxDate")
    fun `throws IllegalArgumentException when calling generateDateTimeBetween with_empty or incorrect maxDate`(
        minDate: String,
        maxDate: String,
    ) {
        val fuzzer = Fuzzer()

        assertThatThrownBy {
            fuzzer.generateDateTimeBetween(minDate, maxDate)
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("maxDate is missing or incorrect. maxDate: $maxDate. maxDate should follow the pattern: yyyy/MM/dd")
    }

    @ParameterizedTest
    @MethodSource("provideWrongMinDateAndMaxDate")
    fun `throws IllegalArgumentException when calling generateDateTimeBetween with two empty or incorrect dateRanges`(
        minDate: String,
        maxDate: String,
    ) {
        val fuzzer = Fuzzer()

        assertThatThrownBy {
            fuzzer.generateDateTimeBetween(minDate, maxDate)
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Min and Max dates are missing or incorrect. minDate: $minDate maxDate: $maxDate. minDate and maxDate should follow the pattern: yyyy/MM/dd")
    }

    @ParameterizedTest
    @MethodSource("provideCorrectMinDateAndMaxDate")
    fun `generate different dates between`(minDate: String, maxDate: String) {
        val fuzzer = Fuzzer()

        val nbOfGeneration = 1000
        val generatedDateTimes = (0 until nbOfGeneration).map {
            fuzzer.generateDateTimeBetween(minDate, maxDate)
        }.toSet()

        checkThatGeneratedDatesAreDiverseAMinimum(generatedDateTimes, nbOfGeneration, 90)

        // Check that every generated date is between our min and max boundaries
        checkThatEveryDateTimeBelongsToTheInclusiveTimeRange(generatedDateTimes, minDate, maxDate)
    }

    @Test
    fun `generate LocalDateTime between using inclusive boundaries`() {
        val fuzzer = Fuzzer()
        val minDate = LocalDate.of(1074, 6, 8).atStartOfDay()
        val maxDate = minDate

        val generateDateTime = fuzzer.generateDateTimeBetween(minDate, maxDate)

        assertThat(generateDateTime).isEqualTo(maxDate)
    }

    companion object {
        private fun checkThatGeneratedDatesAreDiverseAMinimum(
            generatedDateTimes: Set<LocalDateTime>,
            nbOfGeneration: Int,
            percentage: Int,
        ) {
            // Check that generated dates are diverse
            val actualPercentageOfDifferentDateTimes = generatedDateTimes.size * 100 / nbOfGeneration
            assertThat(actualPercentageOfDifferentDateTimes).isGreaterThan(percentage)
        }

        private fun checkThatEveryDateTimeBelongsToTheInclusiveTimeRange(
            generatedDateTimes: Set<LocalDateTime>,
            minDate: String,
            maxDate: String,
        ) {
            val minDateTime = LocalDate.parse(minDate, DateTimeFormatter.ofPattern("yyyy/MM/dd")).atStartOfDay()
            val maxDateTime = LocalDate.parse(maxDate, DateTimeFormatter.ofPattern("yyyy/MM/dd")).atStartOfDay()

            generatedDateTimes.forEach { generatedDateTime ->
                assertThat(generatedDateTime).isBefore(maxDateTime.plusDays(1)).isAfter(minDateTime.plusDays(-1))
            }
        }

        @JvmStatic
        private fun provideWrongMinDate(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("", "2020/11/01"),
                Arguments.of("2000/31/01", "2020/01/01"),
                Arguments.of("portnaouaq", "2020/01/01"),
            )
        }

        @JvmStatic
        private fun provideWrongMaxDate(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("2020/11/01", ""),
                Arguments.of("2020/01/01", "2000/31/01"),
                Arguments.of("2020/01/01", "portnaouaq"),
            )
        }

        @JvmStatic
        private fun provideWrongMinDateAndMaxDate(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("", "2000/31/01"),
                Arguments.of("2000 / 31 / 01", "portnaouaq"),
            )
        }

        @JvmStatic
        private fun provideCorrectMinDateAndMaxDate(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("1974/06/08", "2020/11/01"),
                Arguments.of("2000/01/01", "2020/01/01"),
            )
        }
    }
}
