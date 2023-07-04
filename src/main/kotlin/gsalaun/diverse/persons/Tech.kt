package gsalaun.diverse.persons

/**
 * Information related to tech things.
 */
object Tech {

    /**
     * Gets a list of domain names for emails (e.g.: protonmail.com, yahoo.fr, etc).
     */
    val emailDomainNames: List<String>
        get() {
            return _domainNames.distinct()
        }

    private val _domainNames = listOf(
        "kolab.com", "protonmail.com", "gmail.com", "yahoo.fr", "42skillz.com", "gmail.com", "ibm.com",
        "gmail.com", "yopmail.com", "microsoft.com", "gmail.com", "aol.com"
    )
}