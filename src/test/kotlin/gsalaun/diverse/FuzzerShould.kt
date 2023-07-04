package gsalaun.diverse

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(AllTestsFixtures::class)
class FuzzerShould {

    @Test
    fun `indicate what to do in the DuplicationException message when using NoDuplication mode`() {
        val fuzzer = Fuzzer(noDuplication = true)

        val set = (0 until 5).map {
            fuzzer.generateInteger(1, 5)
        }

        assertThat(set).containsExactlyInAnyOrder(1, 2, 3, 4, 5)

        // So far we have called 5 times fuzzer.GenerateInteger(1, 5)
        // But since the fuzzer could find 5 different values, we are OK

        Assertions.assertThatThrownBy {
            // But this will be the call one should not make ;-)
            fuzzer.generateInteger(1, 5)
        }.isInstanceOf(DuplicationException::class.java)
            .hasMessage(
                """
                    Couldn't find a non-already provided value of kotlin.Int after 100 attempts. Already provided values: 1, 2, 3, 4, 5. You can either:
                    - Generate a new specific fuzzer to ensure no duplication is provided for a sub-group of fuzzed values (anytime you want through the IFuzz.generateNoDuplicationFuzzer() method of your current Fuzzer instance. E.g.: val tempFuzzer = fuzzer.generateNoDuplicationFuzzer())
                    - Increase the value of the Fuzzer.maxFailingAttemptsForNoDuplication property for your IFuzz instance.
                """.trimIndent(),
            )
    }

    @Test
    fun `allow us to avoid duplication but only for various sub groups of fuzzed elements via the generateFuzzerProvidingNoDuplication method`() {
        val fuzzer = Fuzzer()

        val specificFuzzerWithNoDuplication = fuzzer.generateNoDuplicationFuzzer()
        val brandAAllKindOfStarsHotelGroup = HotelGroupBuilder(specificFuzzerWithNoDuplication, Brand.BRAND_A)
            .withHotelIn("Paris")
            .withHotelIn("Aubervilliers")
            .withHotelIn("Versailles")
            .withHotelIn("Nogent sur marne")
            .withHotelIn("Vincennes")
            .build(); // (the build() method will call 5 times fuzzer.generateInteger(1, 5))

        assertThat(brandAAllKindOfStarsHotelGroup.hotels.map { it.stars }).containsExactlyInAnyOrder(1, 2, 3, 4, 5)
        Assertions.assertThatThrownBy {
            specificFuzzerWithNoDuplication.generateInteger(1, 5)
        }.isInstanceOf(DuplicationException::class.java)

        // Another one.
        val anotherSpecificFuzzerWithNoDuplication = specificFuzzerWithNoDuplication.generateNoDuplicationFuzzer()
        val brandBAllKindOfStarsHotelGroup =
            HotelGroupBuilder(anotherSpecificFuzzerWithNoDuplication, Brand.BRAND_B)
                .withHotelIn("Amsterdam")
                .withHotelIn("Barcelona")
                .withHotelIn("Los Angeles")
                .withHotelIn("Athens")
                .withHotelIn("Roma")
                .build(); // (will also call 5 times fuzzer.generateInteger(1, 5). One should not throw DuplicationException)

        assertThat(brandBAllKindOfStarsHotelGroup.hotels.map { it.stars }).containsExactlyInAnyOrder(1, 2, 3, 4, 5)
        Assertions.assertThatThrownBy {
            anotherSpecificFuzzerWithNoDuplication.generateInteger(1, 5)
        }.isInstanceOf(DuplicationException::class.java)

        val itDoesNotThrow = fuzzer.generateInteger(1, 5)
    }

    private enum class Brand {
        BRAND_A,
        BRAND_B,
    }

    private class HotelGroupBuilder(val fuzzer: IFuzz, val brand: Brand) {
        private val cities: MutableSet<String> = mutableSetOf()

        fun withHotelIn(city: String): HotelGroupBuilder {
            cities.add(city)
            return this
        }

        public fun build(): HotelGroup {
            val hotelGroup = HotelGroup()
            cities.forEach { city ->
                val hotelName = "Hôtel $fuzzer.generateFirstName()}"
                val numberOfStars = fuzzer.generateInteger(1, 5)
                val hotelDescription = HotelDescription(brand, hotelName, city, numberOfStars)

                hotelGroup.addHotel(hotelDescription)
            }
            return hotelGroup
        }
    }

    private class HotelGroup(val hotels: MutableSet<HotelDescription> = mutableSetOf()) {

        fun addHotel(hotel: HotelDescription) {
            hotels.add(hotel)
        }
    }

    private data class HotelDescription(val brand: Brand, val name: String, val city: String, val stars: Int)
}
