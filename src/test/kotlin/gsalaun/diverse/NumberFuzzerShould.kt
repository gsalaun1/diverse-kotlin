package gsalaun.diverse

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@ExtendWith(AllTestsFixtures::class)
class NumberFuzzerShould {

    @ParameterizedTest
    @ValueSource(ints = [500])
    fun `generate positive integers with an inclusive upper bound`(attempts: Int) {
        val fuzzer = Fuzzer()

        val maxValue = 3

        val generatedPositiveNumbers = (0 until attempts).map { fuzzer.generatePositiveInteger(maxValue) }

        assertThat(generatedPositiveNumbers.any { it == 3 }).isTrue()
        assertThat(generatedPositiveNumbers.any { it > 3 }).isFalse()
    }

    @ParameterizedTest
    @ValueSource(ints = [500])
    fun `generate integers with an inclusive upper bound`(attempts: Int) {
        val fuzzer = Fuzzer()

        val maxValue = 3

        val generatedPositiveNumbers = (0 until attempts).map { fuzzer.generateInteger(-2, maxValue) }

        assertThat(generatedPositiveNumbers.any { it == 3 }).isTrue()
        assertThat(generatedPositiveNumbers.any { it > 3 }).isFalse()
    }

    @RepeatedTest(100)
    fun `generatePositiveDecimal respecting the specified very tight range`() {
        val fuzzer = Fuzzer()

        val minValue = 1.1074696394971609377302661681
        val maxValue = 1.1074696394971609377302661682
        val number = fuzzer.generatePositiveDouble(minValue, maxValue)

        assertThat(number <= maxValue).withFailMessage("number: $number should be lower or equal to maxValue: $maxValue")
            .isTrue()

        assertThat(number <= minValue).withFailMessage("number: $number should be greater or equal to minValue: $minValue")
            .isTrue()
    }

    @RepeatedTest(10000)
    fun `generate decimal respecting the specified ranges within 42`() {
        val fuzzer = Fuzzer()

        val minValue = 42.30266168232
        val maxValue = 42.9999999999999
        val number = fuzzer.generateDouble(minValue, maxValue)

        assertThat(number <= maxValue).withFailMessage("number: $number should be lower or equal to maxValue: $maxValue")
            .isTrue()

        assertThat(number >= minValue).withFailMessage("number: $number should be greater or equal to minValue: $minValue")
            .isTrue()
    }

    @RepeatedTest(10000)
    fun `generate decimal respecting the specified ranges`() {
        val fuzzer = Fuzzer()

        val minValue = -23023456564.3332323234
        val maxValue = 7777777099232.999
        val number = fuzzer.generateDouble(minValue, maxValue)

        assertThat(number <= maxValue).withFailMessage("number: $number should be lower or equal to maxValue: $maxValue")
            .isTrue()

        assertThat(number >= minValue).withFailMessage("number: $number should be greater or equal to minValue: $minValue")
            .isTrue()
    }

    @Test
    fun `generatePositiveDecimal throws exception when maxValue is greater minValue`() {
        val fuzzer = Fuzzer()

        assertThatThrownBy {
            fuzzer.generatePositiveDouble(10.0, 1.0)
        }.isInstanceOf(ArgumentOutOfRangeException::class.java)
            .hasMessage("Specified argument was out of the range of valid values. (Parameter 'maxValue should be greater than minValue. minValue: 10.0 - maxValue: 1.0')")
    }

    @Test
    fun `generatePositiveDecimal throws exception when minValue is negative`() {
        val fuzzer = Fuzzer()

        assertThatThrownBy {
            fuzzer.generatePositiveDouble(-1.0, 2.0)
        }.isInstanceOf(ArgumentOutOfRangeException::class.java)
            .hasMessage("minValue should be positive. minValue: -1.0 (Parameter 'minValue')")
    }

}