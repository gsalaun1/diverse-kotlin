package gsalaun.diverse.persons

enum class Gender(val value: String) {
    MALE("Male"),
    FEMALE("Female"),
    NON_BINARY("Non binary");

    override fun toString() = value


}


enum class GenderTitle(val value: String) {
    /**
     * Mister
     */
    MR("Mr"),

    /**
     * Missus
     */
    MRS("Mrs"),

    /**
     * Addressing a grown woman as "Ms." is safer than "Miss" or "Mrs."
     */
    MS("Ms"),

    /**
     * Miks or Muks (used for Non-binary people).
     */
    MX("Mx")
}
