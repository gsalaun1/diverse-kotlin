package gsalaun.diverse

import gsalaun.diverse.persons.Gender
import gsalaun.diverse.persons.Person


interface IFuzzPersons {

    /**
     * Generates a 'Diverse' first name (i.e. from all around the world and different cultures).
     *
     * @param gender The {@link Gender} to be used as indication (optional).
     * @return A 'Diverse' first name.
     */
    fun generateFirstName(gender: Gender? = null): String

    /**
     * Generates a 'Diverse' last name (i.e. from all around the world and different cultures).
     *
     * @param firstName The first name of this person.
     * @return A 'Diverse' last name.
     */
    fun generateLastName(firstName: String): String

    /**
     * Generates a 'Diverse' {@Link Person} (i.e. from all around the world and different cultures).
     *
     * @param gender The (optional) {@Link Gender} of this {@Link Person}
     * @return A 'Diverse' {@Link Person} instance.
     */
    fun generatePerson(gender: Gender? = null): Person

    /**
     * Generates a random Email.
     *
     * @param firstName">The (optional) first name for this Email
     * @param lastName">The (optional) last name for this Email.
     * @return A random Email.
     */
    fun generateEMail(firstName: String? = null, lastName: String? = null): String

    /**
     * Generates a password following some common rules asked on the internet.
     *
     * @return The generated password
     */
    fun generatePassword(minSize: Int? = null, maxSize: Int? = null, includeSpecialCharacters: Boolean = false): String

    /**
     * Generates the number of year to be associated with a person.
     *
     * @return The number of year to be associated with a person.
     */
    fun generateAge(): Int
}
