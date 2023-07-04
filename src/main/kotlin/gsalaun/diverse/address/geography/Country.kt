package gsalaun.diverse.address.geography

enum class Country(val value: String) {
    FRANCE("France"),

    ENGLAND("England"),

    USA("USA"),

    CHINA("China"),

    INDIA("India"),

    AUSTRALIA("Australia"),

    NEW_ZEALAND("New-Zealand"),

    SWEDEN("Sweden"),

    CENTRAL_AFRICAN_REPUBLIC("Central African Republic"),

    SENEGAL("Senegal"),

    NIGERIA("Nigeria"),

    RUSSIA("Russia"),

    UKRAINE("Ukraine"),

    JAPAN("Japan"),

    THAILAND("Thailand");

    override fun toString() = value
}
