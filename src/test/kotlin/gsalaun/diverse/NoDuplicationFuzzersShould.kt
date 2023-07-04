package gsalaun.diverse

import gsalaun.diverse.persons.LastNames
import gsalaun.diverse.persons.Locations
import gsalaun.diverse.strings.Adjectives
import gsalaun.diverse.strings.Feeling
import gsalaun.diverse.strings.LoremFuzzer
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.EnumSource
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import java.util.stream.Stream

/**
 * All about some intrinsic behaviours of the {#Link Fuzzer}.
 * Note: we use {@Link RepeatAttribute} only for every test that has a small number of combinations (e.g. 10 or enum values or...).
 */
@ExtendWith(AllTestsFixtures::class)
class NoDuplicationFuzzersShould {


    @RepeatedTest(200)
    fun `be able to provide always different values of MagnificentSeven Enum`() {
        val fuzzer = Fuzzer(noDuplication = true)

        val maxNumberOfElements = MagnificentSeven.entries.size

        checkThatNoDuplicationIsMadeWhileGenerating(
            maxNumberOfElements
        ) { fuzzer.generateEnum<MagnificentSeven>() }
    }

    @RepeatedTest(200)
    fun `be able to provide always different values of MagnificentSeven Enum when instantiating from fuzzer`() {
        val fuzzer = Fuzzer()

        val duplicationFuzzer = fuzzer.generateNoDuplicationFuzzer()

        val maxNumberOfElements = MagnificentSeven.entries.size
        checkThatNoDuplicationIsMadeWhileGenerating(
            maxNumberOfElements
        ) { duplicationFuzzer.generateEnum<MagnificentSeven>() }
    }


    @RepeatedTest(200)
    fun `be able to provide always different values of The good the bad and the ugly Enum`() {
        val fuzzer = Fuzzer(noDuplication = true)

        val maxNumberOfElements = TheGoodTheBadAndTheUgly.entries.size
        checkThatNoDuplicationIsMadeWhileGenerating(
            maxNumberOfElements
        ) { fuzzer.generateEnum<TheGoodTheBadAndTheUgly>() }
    }


    @Test
    fun `be able to provide always different values of Uuid`() {
        val fuzzer = Fuzzer(noDuplication = true)

        val maxNumberOfElements = 100000
        checkThatNoDuplicationIsMadeWhileGenerating(
            maxNumberOfElements
        ) { fuzzer.generateUuid() }
    }

    @RepeatedTest(200)
    fun `be able to provide always different values of integers within a range`() {
        val fuzzer = Fuzzer(noDuplication = true)

        val maxNumberOfElements = 10
        checkThatNoDuplicationIsMadeWhileGenerating(
            maxNumberOfElements
        ) { fuzzer.generateInteger(0, maxNumberOfElements); }
    }


    @Test
    fun `be able to provide always different values of integers`() {
        val fuzzer = Fuzzer(noDuplication = true)

        val maxNumberOfElements = 1000
        checkThatNoDuplicationIsMadeWhileGenerating(
            maxNumberOfElements
        ) { fuzzer.generateInteger() }
    }


    @RepeatedTest(200)
    fun `be able to provide always different values of positive integers`() {
        val fuzzer = Fuzzer(noDuplication = true)

        val maxNumberOfElements = 10
        checkThatNoDuplicationIsMadeWhileGenerating(
            maxNumberOfElements
        ) { fuzzer.generatePositiveInteger(10) }
    }


    @RepeatedTest(100)
    fun `be able to provide always different values of long within a small range`() {
        val fuzzer = Fuzzer(noDuplication = true)

        val minValue = 0L
        val maxValue = 3L
        val maxNumberOfElements = maxValue - minValue + 1 // +1 since it is upper bound inclusive
        checkThatNoDuplicationIsMadeWhileGenerating(
            maxNumberOfElements.toInt()
        ) { fuzzer.generateLong(minValue, maxValue) }
    }

    @ParameterizedTest
    @MethodSource("provideVariousLong")
    fun `be able to provide always different values of long within a wide range`(
        minValue: Long,
        maxValue: Long
    ) {
        val fuzzer = Fuzzer(noDuplication = true)

        val maxNumberOfElements = maxValue - minValue + 1 // +1 since it is upper bound inclusive
        checkThatNoDuplicationIsMadeWhileGenerating(
            maxNumberOfElements.toInt()
        ) { fuzzer.generateLong(minValue, maxValue) }
    }


    @Test
    fun `be able to provide always different values of long within a huge range`() {
        val fuzzer = Fuzzer(noDuplication = true)

        val minValue = Long.MIN_VALUE
        val maxValue = Long.MAX_VALUE

        val maxNumberOfElements = 1 // we won't test all possible long here! ;-)
        // TODO Above value was 100000 in C# but it results in a Java heap space crash with sch a value
        //  => find where is the bottleneck
        checkThatNoDuplicationIsMadeWhileGenerating(
            maxNumberOfElements
        ) { fuzzer.generateLong(minValue, maxValue); }
    }


    @Test
    fun `Be able to pick different values from two enumerables of string containing the same values`() {
        val noDupFuzzer = Fuzzer(884871408).generateNoDuplicationFuzzer()

        val rateCodes =
            listOf(
                "PRBA",
                "LRBAMC",
                "AVG1",
                "PRBB",
                "LRBA",
                "BBSP",
                "PRBA2",
                "LRBA2MC",
                "PRPH",
                "PBCITE_tes",
                "PRPH2",
                "PRP2N2",
                "PR3NS1",
                "PBHS",
                "PBSNWH",
                "PBTHE",
                "PBVPOM",
                "PBZOO",
                "PHBO",
                "PHBPP",
                "PAB01",
                "PHB01",
                "PH3P",
                "LH3PMC",
                "CAMI",
                "FRCAMIF",
                "GENERICRATECODE",
                "PBGGG",
                "PBPPBRAZIL",
                "PBSENIOR"
            )
        val identicalRateCodes =
            listOf(
                "PRBA",
                "LRBAMC",
                "AVG1",
                "PRBB",
                "LRBA",
                "BBSP",
                "PRBA2",
                "LRBA2MC",
                "PRPH",
                "PBCITE_tes",
                "PRPH2",
                "PRP2N2",
                "PR3NS1",
                "PBHS",
                "PBSNWH",
                "PBTHE",
                "PBVPOM",
                "PBZOO",
                "PHBO",
                "PHBPP",
                "PAB01",
                "PHB01",
                "PH3P",
                "LH3PMC",
                "CAMI",
                "FRCAMIF",
                "GENERICRATECODE",
                "PBGGG",
                "PBPPBRAZIL",
                "PBSENIOR"
            )

        val firstValue = noDupFuzzer.pickOneFrom(rateCodes)
        val secondValue = noDupFuzzer.pickOneFrom(identicalRateCodes)

        assertThat(secondValue).isNotEqualTo(firstValue)
    }

    @Test
    fun `be able to pick different values from two enumerables of int containing the same values`() {
        val noDupFuzzer = Fuzzer(884871408).generateNoDuplicationFuzzer()

        val rateCodes = listOf(1, 56, 99, 75, 423, 569, 42)
        val identicalRateCodes = listOf(1, 56, 99, 75, 423, 569, 42)

        val firstValue = noDupFuzzer.pickOneFrom(rateCodes)
        val secondValue = noDupFuzzer.pickOneFrom(identicalRateCodes)

        assertThat(secondValue).isNotEqualTo(firstValue)
    }

    @Test
    fun `be able to pick always different values from a list of string even when providing those values in another collection instance`() {
        val fuzzer = Fuzzer(noDuplication = true)

        val candidates = listOf("un", "dos", "tres", "quatro", "cinquo", "seis")

        checkThatNoDuplicationIsMadeWhileGenerating(
            candidates.size
        ) { fuzzer.pickOneFrom(candidates) }
    }


    @Test
    fun `be able to pick always different values from a list of int even when providing those values in another collection instance`() {
        val fuzzer = Fuzzer(noDuplication = true)

        val candidates = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

        checkThatNoDuplicationIsMadeWhileGenerating(
            candidates.size
        ) { fuzzer.pickOneFrom(candidates) }
    }

    @Test
    fun `be able to pick always different values from a medium size list of int`() {
        val fuzzer = Fuzzer(noDuplication = true)

        val candidates = (-10000..10000).toList()

        checkThatNoDuplicationIsMadeWhileGenerating(
            candidates.size
        ) { fuzzer.pickOneFrom(candidates) }
    }


    @Test
    fun `be able to pick always different values from a list of enum even when providing those values in another collection instance`() {
        val fuzzer = Fuzzer(noDuplication = true)

        val candidates =
            listOf(TheGoodTheBadAndTheUgly.TheGood, TheGoodTheBadAndTheUgly.TheBad, TheGoodTheBadAndTheUgly.TheUgly)

        checkThatNoDuplicationIsMadeWhileGenerating(
            candidates.size
        ) { fuzzer.pickOneFrom(candidates) }
    }


    @Test
    fun `be able to provide always different values of firstName`() {
        val fuzzer = Fuzzer(noDuplication = true)
        checkThatNoDuplicationIsMadeWhileGenerating(
            80
        ) {
            fuzzer.generateFirstName()
        }
    }

    @Test
    fun `be able to provide always different values of lastName`() {
        val fuzzer = Fuzzer(noDuplication = true)

        val firstName = fuzzer.generateFirstName()
        val continent = Locations.findContinent(firstName)

        val maxNumberOfLastNamesForThisContinent = LastNames.perContinent[continent]?.size ?: 0

        checkThatNoDuplicationIsMadeWhileGenerating(
            maxNumberOfLastNamesForThisContinent
        ) {
            fuzzer.generateLastName(firstName)
        }
    }

    @RepeatedTest(100)
    fun `be able to provide always different values of emails`() {
        val fuzzer = Fuzzer(noDuplication = true)

        val firstName = fuzzer.generateFirstName()
        val lastName = fuzzer.generateLastName(firstName)

        val maxNumberOfElements = 16
        checkThatNoDuplicationIsMadeWhileGenerating(
            maxNumberOfElements
        ) {
            fuzzer.generateEMail(firstName, lastName)
        }
    }

    @Test
    fun `be able to provide always different values of passwords`() {
        val fuzzer = Fuzzer(noDuplication = true)

        val maxNumberOfElements = 100000
        checkThatNoDuplicationIsMadeWhileGenerating(
            maxNumberOfElements
        ) {
            fuzzer.generatePassword()
        }
    }

    @Test
    fun `be able to provide always different values of age`() {
        val fuzzer = Fuzzer(noDuplication = true)

        val maxNumberOfElements = 97 - 18
        checkThatNoDuplicationIsMadeWhileGenerating(
            maxNumberOfElements
        ) {
            fuzzer.generateAge()
        }
    }

    @ParameterizedTest
    @EnumSource(Feeling::class)
    fun `be able to provide always different values of adjective`(feeling: Feeling) {
        val fuzzer = Fuzzer(2023547856, noDuplication = true)

        val maxNumberOfAdjectives = Adjectives.perFeeling[feeling]?.distinct()?.size ?: 0
        checkThatNoDuplicationIsMadeWhileGenerating(
            maxNumberOfAdjectives
        ) {
            val adjective = fuzzer.generateAdjective(feeling)
            println(adjective)
            adjective
        }
    }

    @RepeatedTest(5000)
    fun `be able to provide always different letters`() {
        val fuzzer = Fuzzer(noDuplication = true);

        val maxNumberOfElements = LoremFuzzer.alphabet.size
        checkThatNoDuplicationIsMadeWhileGenerating(
            maxNumberOfElements
        ) {
            fuzzer.generateLetter()
        }
    }

    @Test
    fun `be able to provide always different values of dateTime`() {
        val fuzzer = Fuzzer(noDuplication = true)

        val maxNumberOfElements = 10000
        checkThatNoDuplicationIsMadeWhileGenerating(
            maxNumberOfElements
        ) {
            fuzzer.generateDateTime()
        }
    }


    @Test
    fun `throw duplicationException with fix explanation when number of attempts is too low`() {
        val fuzzer = Fuzzer(707888174, noDuplication = true)

        // Make it fail on purpose
        fuzzer.maxFailingAttemptsForNoDuplication = 1

        Assertions.assertThatThrownBy {
            val maxValue = 50;
            val moreAttemptsThanMaxValue = (maxValue + 2);
            (0 until moreAttemptsThanMaxValue).map { fuzzer.generateInteger(0, maxValue) }
        }.isInstanceOf(DuplicationException::class.java)
            .hasMessageStartingWith("Couldn't find a non-already provided value of kotlin.Int after ${fuzzer.maxFailingAttemptsForNoDuplication} attempts. Already provided values:")
            .hasMessageEndingWith(
                """
                You can either:
                - Generate a new specific fuzzer to ensure no duplication is provided for a sub-group of fuzzed values (anytime you want through the IFuzz.generateNoDuplicationFuzzer() method of your current Fuzzer instance. E.g.: val tempFuzzer = fuzzer.generateNoDuplicationFuzzer())
                - Increase the value of the Fuzzer.maxFailingAttemptsForNoDuplication property for your IFuzz instance.
            """.trimIndent()
            )
    }

    private fun <T> checkThatNoDuplicationIsMadeWhileGenerating(
        maxNumberOfElements: Int,
        fuzzingFunction: () -> T
    ) {
        val returnedElements = (0 until maxNumberOfElements).map {
            try {
                fuzzingFunction()
            } catch (_: DuplicationException) {
            }
        }.toHashSet()

        assertThat(returnedElements)
            .withFailMessage("The fuzzer was not able to generate the maximum number of expected entries")
            .hasSize(maxNumberOfElements)
    }

    companion object {
        @JvmStatic
        private fun provideVariousLong(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(-1, 10),
                Arguments.of(-1, 1),
                Arguments.of(-5, 5),
                Arguments.of(0, 600),
                Arguments.of(-1000, 1000),
                Arguments.of(-10000, 10000)
            )
        }
    }

}

/**
 * Name of the Good, the Bad and the Ugly (https://en.wikipedia.org/wiki/The_Good,_the_Bad_and_the_Ugly)
 *
 */
enum class TheGoodTheBadAndTheUgly {
    TheGood,
    TheBad,
    TheUgly
}


/**
 * Name of the magnificent seven (https://en.wikipedia.org/wiki/The_Magnificent_Seven_(2016_film))
 *
 */
enum class MagnificentSeven {
    SamChisolm,
    JoshFaraday,
    GoodnightRobicheaux,
    JackHorne,
    BillyRocks,
    Vasquez,
    RedHarvest
}