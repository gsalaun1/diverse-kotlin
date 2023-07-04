package gsalaun.diverse.address.geography

import gsalaun.diverse.address.geography.countries.China
import gsalaun.diverse.address.geography.countries.France
import gsalaun.diverse.address.geography.countries.UnitedStatesOfAmerica

object GeographyExpert {
    private val streetsOfCountries: Map<Country, List<String>> =
        mapOf(
            Country.FRANCE to France.streetNames,
            Country.CHINA to China.streetNames,
            Country.USA to UnitedStatesOfAmerica.streetNames,
        )

    private var _citiesOfTheWorld: List<CityWithRelatedInformation>? =
        null

    private val citiesOfTheWorld: List<CityWithRelatedInformation>
        get() {
            if (_citiesOfTheWorld == null) {
                _citiesOfTheWorld = France.cities + China.cities + UnitedStatesOfAmerica.cities
            }
            return _citiesOfTheWorld as List<CityWithRelatedInformation>
        }

    /**
     * Gives an array with all the cities related to a {@Link Country}.
     *
     * @param country The {@Link Country.
     * @return An array with all the cities related to a {@Link Country}.
     */
    fun giveMeStreetsOf(country: Country) = streetsOfCountries[country] ?: emptyList()

    fun giveMeCitiesOf(country: Country) =
        citiesOfTheWorld
            .filter { it.country == country }
            .map { it.cityName }
            .distinct()

    /**
     * Gives an array with all the {@Link StateProvinceArea} related to a <see cref="Country"/>.
     *
     * @param country The {@Link Country}.
     * @return An array of all the {@Link StateProvinceArea} registered for this {@Link Country} in the Diverse lib.
     */
    fun giveMeStateProvinceAreaOf(country: Country) =
        citiesOfTheWorld
            .filter { it.country == country }
            .map { it.stateProvinceArea }
            .distinct()

    /**
     * Gives the {@Link StateProvinceArea} of a given city name.
     *
     * @param cityName The name of the city.
     * @return The {@Link StateProvinceArea} where this city belongs.
     */
    fun giveMeStateProvinceAreaOf(cityName: String) =
        citiesOfTheWorld.first { it.cityName == cityName }.stateProvinceArea

    /**
     * Gives the ZipCode format registered for this city in Diverse.
     *
     * @param cityName The name of the city you want to get the ZipCode format.
     * @return The ZipCode format registered for this city in Diverse.
     */

    fun giveMeZipCodeFormatOf(cityName: String): String {
        return citiesOfTheWorld.first { c -> c.cityName == cityName }.zipCodeFormat;
    }
}
