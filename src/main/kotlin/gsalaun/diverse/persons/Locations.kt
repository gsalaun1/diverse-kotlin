package gsalaun.diverse.persons

object Locations {

    fun findContinent(firstName: String): Continent {
        val maleContextualizedFirstName = Male.contextualizedFirstNames.firstOrNull { it.firstName == firstName }
        return if (maleContextualizedFirstName != null) {
            maleContextualizedFirstName.origin
        } else {
            val femaleContextualizedFirstName =
                Female.contextualizedFirstNames.firstOrNull { it.firstName == firstName }
            femaleContextualizedFirstName?.origin ?: Continent.AFRICA
        }
    }
}