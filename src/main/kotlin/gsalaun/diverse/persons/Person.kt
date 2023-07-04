package gsalaun.diverse.persons

import gsalaun.diverse.address.Address
import java.util.Locale

data class Person(
    val firstName: String,
    val lastName: String,
    val gender: Gender,
    val eMail: String,
    val isMarried: Boolean,
    val age: Int,
    val address: Address
) {
    private val title: String =
        when (gender) {
            Gender.MALE -> GenderTitle.MR.value

            Gender.FEMALE -> if (isMarried) {
                GenderTitle.MRS.value
            } else {
                GenderTitle.MS.value
            }

            Gender.NON_BINARY -> GenderTitle.MX.value
        }

    override fun toString(): String {
        val shortVersion = toStringShortVersion();

        return """
            $shortVersion
            $address
        """.trimIndent();
    }

    /**
     * Returns a short string version of this instance.
     *
     * @return The short string version of this instance.
     */
    private fun toStringShortVersion(): String {
        val marriageStatus = if (isMarried) {
            "married - "
        } else {
            ""
        };
        val status = "($marriageStatus age: $age years)";

        return "$title. $firstName ${lastName.uppercase(Locale.getDefault())} ($gender) $eMail $status";
    }
}