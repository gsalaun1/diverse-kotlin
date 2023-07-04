package gsalaun.diverse

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import java.util.regex.Matcher
import java.util.regex.Pattern
import java.util.stream.Stream

@ExtendWith(AllTestsFixtures::class)
class PersonFuzzerWithPasswordShould {

    @ParameterizedTest
    @ValueSource(ints = [500])
    fun `generatePasswords of various sizes`(attempts: Int) {
        val fuzzer = Fuzzer()

        val generatedPasswords = (0 until attempts).map { fuzzer.generatePassword() }

        val generatedSizes = generatedPasswords.map { it.length }.distinct()
        assertThat(generatedSizes.size).isGreaterThan(2)
    }

    @ParameterizedTest
    @MethodSource("valuesToGeneratePasswordsOfVariousSizes")
    fun `generatePasswords of various sizes respecting the min and max size specified`(
        minSize: Int?,
        maxSize: Int?
    ) {
        val fuzzer = Fuzzer()

        (0 until 200).forEach { _ ->
            val password = fuzzer.generatePassword(minSize = minSize, maxSize = maxSize)
            if (minSize != null) {
                assertThat(password.length).isGreaterThanOrEqualTo(minSize)
            }
            if (maxSize != null) {
                assertThat(password.length).isLessThanOrEqualTo(maxSize)
            }
        }
    }

    @ParameterizedTest
    @MethodSource("valuesToGeneratePasswordsOf7AsMinimumSize")
    fun `take 7 as minimum size when generatePasswords without specifying a minSize`(minSize: Int?, maxSize: Int?) {
        val fuzzer = Fuzzer()

        (0 until 200).forEach { _ ->
            val password = fuzzer.generatePassword(minSize = minSize, maxSize = maxSize)
            assertThat(password.length).isGreaterThanOrEqualTo(7)
        }
    }

    @ParameterizedTest
    @MethodSource("valuesToGeneratePasswordsOf12AsMaximumSize")
    fun `take 12 as maximum size when generatePasswords without specifying a maxSize`(minSize: Int?, maxSize: Int?) {
        val fuzzer = Fuzzer()

        (0 until 200).forEach { _ ->
            val password = fuzzer.generatePassword(minSize = minSize, maxSize = maxSize)
            assertThat(password.length).isLessThanOrEqualTo(12)
        }
    }


    @ParameterizedTest
    @MethodSource("valuesToGeneratePasswordsWithWrongMaxSize")
    fun `throw argumentOutOfRangeException when calling generatePassword specifying a maxSize inferior to the minSize`(
        minSize: Int?,
        maxSize: Int?
    ) {
        val fuzzer = Fuzzer()

        (0 until 200).forEach { _ ->
            Assertions.assertThatThrownBy {
                fuzzer.generatePassword(minSize = minSize, maxSize = maxSize)
            }.isInstanceOf(ArgumentOutOfRangeException::class.java)
        }
    }

    @ParameterizedTest
    @MethodSource("valuesToGeneratePasswordsWithWrongMinSize")
    fun `throw argumentOutOfRangeException when calling generatePassword specifying a minSize superior to the maxSize`(
        minSize: Int?, maxSize: Int?
    ) {
        val fuzzer = Fuzzer()

        (0 until 200).forEach { _ ->
            Assertions.assertThatThrownBy {
                fuzzer.generatePassword(minSize = minSize, maxSize = maxSize)
            }.isInstanceOf(ArgumentOutOfRangeException::class.java)

        }
    }

    @ParameterizedTest
    @MethodSource("valuesToGeneratePasswordsDowngradingTheMinSize")
    fun `downgrade the minSize to at least the specified maxSize when specifying a maxSize below the default minSize of 7`(
        minSize: Int,
        maxSize: Int
    ) {
        val fuzzer = Fuzzer()

        (0 until 200).forEach { _ ->
            val password = fuzzer.generatePassword(minSize = minSize, maxSize = maxSize)
            assertThat(password.length).isLessThanOrEqualTo(maxSize)
        }
    }

    @Test
    fun `contains only alphanumerical characters by default`() {
        val fuzzer = Fuzzer()

        val password = fuzzer.generatePassword()

        assertThat(password).doesNotMatch("[^A-Za-z0-9]+")
    }

    @Test
    fun `contains at least a special character`() {
        val fuzzer = Fuzzer()

        val password = fuzzer.generatePassword(includeSpecialCharacters = true)

        assertThat(password).matches(".*[^A-Za-z0-9]+.*")
    }

    @Test
    fun `contains at least a lowercase character`() {
        val fuzzer = Fuzzer()

        val password = fuzzer.generatePassword(includeSpecialCharacters = true)

        assertThat(password).matches(".*[a-z]+.*")
    }

    @Test
    fun `contains at least an uppercase character`() {
        val fuzzer = Fuzzer()

        val password = fuzzer.generatePassword(includeSpecialCharacters = true)

        assertThat(password).matches(".*[A-Z]+.*")
    }

    @Test
    fun `contains at least a number`() {
        val fuzzer = Fuzzer()

        val password = fuzzer.generatePassword(includeSpecialCharacters = true)

        assertThat(password).matches(".*[0-9]+.*")
    }

    companion object {
        @JvmStatic
        private fun valuesToGeneratePasswordsOfVariousSizes(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(12, null),
                Arguments.of(7, null),
                Arguments.of(0, 1),
                Arguments.of(9, 13),
                Arguments.of(3, 24),
                Arguments.of(8, 12),
                Arguments.of(7, 16),
                Arguments.of(null, 8),
                Arguments.of(8, 15)
            )
        }

        @JvmStatic
        private fun valuesToGeneratePasswordsOf7AsMinimumSize(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(null, 7),
                Arguments.of(null, 8),
                Arguments.of(null, 12),
                Arguments.of(null, 18)
            )
        }


        @JvmStatic
        private fun valuesToGeneratePasswordsOf12AsMaximumSize(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(1, null),
                Arguments.of(12, null),
                Arguments.of(null, null)
            )
        }


        @JvmStatic
        private fun valuesToGeneratePasswordsWithWrongMaxSize(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(2, 1),
                Arguments.of(8, 7),
                Arguments.of(null, 3),
                Arguments.of(null, 6)
            )
        }

        @JvmStatic
        private fun valuesToGeneratePasswordsWithWrongMinSize(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(2, 1),
                Arguments.of(7, 3),
                Arguments.of(8, 7),
                Arguments.of(13, null)
            )
        }

        @JvmStatic
        private fun valuesToGeneratePasswordsDowngradingTheMinSize(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(0, 1),
                Arguments.of(0, 2),
                Arguments.of(0, 3),
                Arguments.of(0, 4),
                Arguments.of(0, 5),
                Arguments.of(0, 6),
                Arguments.of(0, 7),
                Arguments.of(0, 8)
            )
        }


    }
}