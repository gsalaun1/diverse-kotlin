package gsalaun.diverse.address

import gsalaun.diverse.IFuzz
import gsalaun.diverse.IFuzzAddress
import gsalaun.diverse.address.geography.Country
import gsalaun.diverse.address.geography.GeographyExpert

class AddressFuzzer(private val fuzzer: IFuzz) : IFuzzAddress {
    override fun generateAddress(country: Country?): Address {
        val retainedCountry = country ?: Country.FRANCE

        val streetNumber = fuzzer.generateInteger(1, 390)
        val streetName = fuzzer.pickOneFrom(GeographyExpert.giveMeStreetsOf(retainedCountry))
        val city = fuzzer.pickOneFrom(GeographyExpert.giveMeCitiesOf(retainedCountry))

        val zipCode = fuzzer.generateStringFromPattern(GeographyExpert.giveMeZipCodeFormatOf(city));
        val stateProvinceArea = GeographyExpert.giveMeStateProvinceAreaOf(city)

        return Address(streetNumber.toString(), streetName, city, zipCode, stateProvinceArea, retainedCountry)
    }
}
