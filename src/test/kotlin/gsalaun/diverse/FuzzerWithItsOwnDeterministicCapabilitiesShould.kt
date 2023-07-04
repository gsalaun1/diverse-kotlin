package gsalaun.diverse

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

/**
 * All about the deterministic capabilities of the {#Link Fuzzer}.
 * This test fixture has lot of tests using a specific seed provided to
 * the Fuzzer instance.
 *
 * This is not representative on how to use {@Link Fuzzer} instances in your code
 * base (i.e. without fixing a seed in order to go full random), but made
 * for deterministic results instead.
 *
 */
@ExtendWith(AllTestsFixtures::class)
class FuzzerWithItsOwnDeterministicCapabilitiesShould {

    @Test
    fun `be able to expose the Seed we provide but also the one we did not provide`() {
        val providedSeed = 428
        val fuzzer = Fuzzer(seed = 428)

        assertThat(fuzzer.seed).isEqualTo(providedSeed)

        val otherFuzzer = Fuzzer()
        assertThat(fuzzer.seed).isNotEqualTo(otherFuzzer.seed)
    }

    @Test
    fun `be Deterministic when specifying an existing seed`() {
        val seed = 1226354269;
        val fuzzer = Fuzzer(seed = seed);

        val fuzzedIntegers = (0 until 10).map {
            fuzzer.generatePositiveInteger()
        }

        assertThat(fuzzedIntegers).containsExactly(
            332654768,
            613899625,
            1585149849,
            362909599,
            324894837,
            833157377,
            1735405637,
            979189230,
            943145857,
            508712912
        )
    }

    @Test
    fun `provide different values when using different Fuzzer instances`() {
        val deterministicFuzzer = Fuzzer(seed = 1226354269, name = "first")
        val randomFuzzer = Fuzzer(name = "second")
        val anotherRandomFuzzer = Fuzzer(name = "third")

        val deterministicInteger = deterministicFuzzer.generatePositiveInteger()
        val randomInteger = randomFuzzer.generatePositiveInteger()
        val anotherRandomInteger = anotherRandomFuzzer.generatePositiveInteger()

        assertThat(deterministicInteger).isEqualTo(332654768)
        assertThat(deterministicInteger).isNotEqualTo(randomInteger)
        assertThat(deterministicInteger).isNotEqualTo(anotherRandomInteger)
        assertThat(randomInteger).isNotEqualTo(anotherRandomInteger)
    }

    @Test
    fun `be Deterministic when specifying an existing seed whatever the specified name of the fuzzer`() {
        val seed = 1226354269
        val fuzzer = Fuzzer(seed)
        val fuzzerWithSameFeedButDifferentName = Fuzzer(seed, "Monte-Cristo")

        val valueFuzzer = fuzzer.generateInteger()
        val valueFuzzerWithNameSpecified = fuzzerWithSameFeedButDifferentName.generateInteger()

        assertThat(fuzzerWithSameFeedButDifferentName.name).isNotEqualTo(fuzzer.name)
        assertThat(valueFuzzerWithNameSpecified).isEqualTo(valueFuzzer)
    }
}