package gsalaun.diverse

import gsalaun.diverse.address.geography.Country
import gsalaun.diverse.address.geography.GeographyExpert
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

@ExtendWith(AllTestsFixtures::class)
class AddressFuzzerShould {

    @ParameterizedTest
    @EnumSource(value = Country::class, names = ["FRANCE", "CHINA", "USA"])
    fun `be able to generate proper address for the Country of`(country: Country) {
        val fuzzer = Fuzzer()

        val address = fuzzer.generateAddress(country)

        assertThat(address.streetNumber).isNotEmpty()
        assertThat(address.street).isNotEmpty()

        assertThat(GeographyExpert.giveMeStreetsOf(country)).contains(address.streetName)
        assertThat(GeographyExpert.giveMeCitiesOf(country)).contains(address.city)
        assertThat(GeographyExpert.giveMeStateProvinceAreaOf(country)).contains(address.stateProvinceArea)
        assertThat(address.country).isEqualTo(country)

        assertThat(address.countryLabel).isNotEmpty()
    }
}
