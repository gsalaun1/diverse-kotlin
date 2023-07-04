package gsalaun.diverse.address

import gsalaun.diverse.address.geography.Country
import gsalaun.diverse.address.geography.StateProvinceArea

data class Address(
    val streetNumber: String,
    val streetName: String,
    val city: String,
    val zipCode: String,
    val stateProvinceArea: StateProvinceArea,
    val country: Country,
) {

    val countryLabel: String = country.toString()
    val street: String
        get() {
            return "$streetNumber $streetName"
        }

    override fun toString(): String {
        return "$streetNumber $streetName, $stateProvinceArea, $city $zipCode, $country"
    }


}
