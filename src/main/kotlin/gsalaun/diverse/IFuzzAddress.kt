package gsalaun.diverse

import gsalaun.diverse.address.Address
import gsalaun.diverse.address.geography.Country

interface IFuzzAddress {
    /**
     * Randomly generates an {@link Address}
     *
     * @param country The {@link Country} of the address to generate.
     * @return The generated Address.
     */
    fun generateAddress(country: Country? = null): Address
}
