package gsalaun.diverse.address.geography

import gsalaun.diverse.persons.Continent

data class CityWithRelatedInformation(
    val cityName: String,
    val stateProvinceArea: StateProvinceArea,
    val country: Country,
    val continent: Continent,
    val zipCodeFormat: String = "######",
)
