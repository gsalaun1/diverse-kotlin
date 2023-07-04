package gsalaun.diverse

import gsalaun.diverse.strings.Feeling
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(AllTestsFixtures::class)
class StringFuzzerShould {

    @Test
    fun `be able to generate a random adjective`() {
        val fuzzer = Fuzzer(438709238)

        val randomString = fuzzer.generateAdjective()

        assertThat(randomString).isEqualTo("beautiful")
    }

    @Test
    fun `be able to generate a positive adjective`() {
        val fuzzer = Fuzzer(43930430)

        val positiveAdjectives = (0 until 3).map { fuzzer.generateAdjective(Feeling.POSITIVE) }

        assertThat(positiveAdjectives).containsExactly("keen", "happy", "eloquent")
    }


    @RepeatedTest(100)
    fun `generate random string replacing x with lowerCaseLetter`() {
        val fuzzer = Fuzzer()

        val chars = fuzzer.generateStringFromPattern("x").toCharArray()

        assertThat(chars[0].toString()).matches("[a-z]")
    }

    @RepeatedTest(100)
    fun `generate random string replacing x with upperCaseLetter`() {
        val fuzzer = Fuzzer()

        val chars = fuzzer.generateStringFromPattern("X").toCharArray()

        assertThat(chars[0].toString()).matches("[A-Z]")
    }

    @RepeatedTest(100)
    fun `generate random string replacing sharpSign with a single digit number`() {
        val fuzzer = Fuzzer()

        val chars = fuzzer.generateStringFromPattern("#").toCharArray()

        assertThat(chars[0].toString()).matches("[0-9]")
    }

    @RepeatedTest(1000)
    fun `generate random string from pattern`() {
        val fuzzer = Fuzzer()

        val value = fuzzer.generateStringFromPattern("X#A02x") // U3A02k

        val chars = value.toCharArray()

        assertThat(chars[0].toString()).matches("[A-Z]")

        assertThat(chars[1].toString()).matches("[0-9]")

        assertThat(chars[2]).isEqualTo('A')
        assertThat(chars[3]).isEqualTo('0')
        assertThat(chars[4]).isEqualTo('2')

        assertThat(chars[5].toString()).matches("[a-z]")
    }

}