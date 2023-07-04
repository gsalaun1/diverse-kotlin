package gsalaun.diverse.persons

import gsalaun.diverse.ArgumentOutOfRangeException
import gsalaun.diverse.IFuzz
import gsalaun.diverse.IFuzzPersons
import gsalaun.diverse.address.geography.Country
import gsalaun.diverse.helpers.removeDiacritics
import java.util.Locale


class PersonFuzzer(private val fuzzer: IFuzz) : IFuzzPersons {

    companion object {
        val specialCharacters = "+-_$%£&!?*$€'|[]()".toCharArray()
        val upperCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()
        val lowerCharacters = "abcdefghijklmnopqrstuvwxyz".toCharArray()
        val numericCharacters = "0123456789".toCharArray()
        const val DEFAULT_MIN_SIZE = 7
        const val DEFAULT_MAX_SIZE = 12
    }

    override fun generateFirstName(gender: Gender?): String {
        val firstNameCandidates: List<String> =
            if (gender == null) {
                val isFemale = fuzzer.headsOrTails()
                if (isFemale) Female.firstNames else Male.firstNames
            } else {
                if (gender === Gender.FEMALE) Female.firstNames else Male.firstNames
            }

        return fuzzer.pickOneFrom(firstNameCandidates)
    }

    override fun generateLastName(firstName: String): String {
        val continent = Locations.findContinent(firstName)
        val lastNames = LastNames.perContinent[continent] ?: emptyList()
        return fuzzer.pickOneFrom(lastNames)
    }

    override fun generateEMail(firstName: String?, lastName: String?): String {
        val emailFirsName = firstName ?: generateFirstName()
        val emailLastName = lastName ?: generateLastName(emailFirsName)

        val domainName = fuzzer.pickOneFrom(Tech.emailDomainNames)

        if (fuzzer.headsOrTails()) {
            var shortVersion =
                "${emailFirsName.substring(0, 1)}$emailLastName@$domainName".lowercase(Locale.getDefault())
            shortVersion = transformIntoValidEmailFormat(shortVersion)
            return shortVersion
        }

        var longVersion = "$emailFirsName.$emailLastName@$domainName".lowercase(Locale.getDefault())
        longVersion = transformIntoValidEmailFormat(longVersion)
        return longVersion
    }

    private fun transformIntoValidEmailFormat(eMail: String): String {
        val validFormat = eMail.replace(' ', '-')
        return validFormat.removeDiacritics()
    }

    override fun generatePassword(minSize: Int?, maxSize: Int?, includeSpecialCharacters: Boolean): String {
        val minimumSize = minSize ?: DEFAULT_MIN_SIZE
        val maximumSize = maxSize ?: DEFAULT_MAX_SIZE

        checkGuardMinAndMaximumSizes(minSize, maxSize, minimumSize, maximumSize)

        val passwordSize = fuzzer.random.nextInt(minimumSize, maximumSize + 1)

        val pwd = StringBuilder(passwordSize)
        for (i in 0 until passwordSize) {
            if ((i == 0 || i == 10) && (includeSpecialCharacters)) {
                pwd.append(specialCharacters[fuzzer.random.nextInt(0, specialCharacters.size)])
                continue
            }

            if (i == 4 || i == 14) {
                pwd.append(upperCharacters[fuzzer.random.nextInt(1, 26)])
                continue
            }

            if (i == 6 || i == 13) {
                pwd.append(numericCharacters[fuzzer.random.nextInt(4, 10)])
                continue
            }

            if (i == 3 || i == 9) {
                pwd.append(numericCharacters[fuzzer.random.nextInt(1, 5)])
                continue
            }

            // by default
            pwd.append(lowerCharacters[fuzzer.random.nextInt(1, 26)])
        }

        return pwd.toString()
    }

    override fun generateAge(): Int {
        val minValue = 18
        val maxValue = 97

        if (fuzzer.headsOrTails()) {
            return fuzzer.generateInteger(minValue, 28)
        }

        if (fuzzer.headsOrTails()) {
            return fuzzer.generateInteger(28, 42)
        }

        if (fuzzer.headsOrTails()) {
            return fuzzer.generateInteger(minValue, 42)
        }

        return fuzzer.generateInteger(42, maxValue)
    }

    private fun checkGuardMinAndMaximumSizes(
        minSize: Int?,
        maxSize: Int?,
        minimumSize: Int,
        maximumSize: Int
    ) {
        if (minimumSize > maximumSize) {
            var parameterName = if (minSize == null) {
                "maxSize"
            } else {
                "minSize"
            }
            if (minSize != null && maxSize != null) {
                parameterName = "maxSize"
            }

            throw ArgumentOutOfRangeException(
                parameterName,
                "maxSize ($maximumSize) can't be inferior to minSize($minimumSize). " +
                        "Specify 2 values if you don't want to use the default values of the library " +
                        "(i.e.: [$DEFAULT_MIN_SIZE, $DEFAULT_MAX_SIZE])."
            )
        }
    }

    override fun generatePerson(gender: Gender?): Person {
        val personGender = gender ?: pickAGenderRandomly()

        val firstName = generateFirstName(gender)
        val lastName = generateLastName(firstName)
        val eMail = generateEMail(firstName, lastName)
        val isMarried = fuzzer.headsOrTails()

        val age = generateAge()

        val country = findAssociatedCountry(lastName)

        val address = fuzzer.generateAddress(country)

        return Person(firstName, lastName, personGender, eMail, isMarried, age, address)
    }

    private fun pickAGenderRandomly(): Gender {
        val isFemale = fuzzer.headsOrTails()
        val gender = if (isFemale) {
            Gender.FEMALE
        } else {
            val isNonBinary = fuzzer.headsOrTails()
            if (isNonBinary) {
                Gender.NON_BINARY
            } else {
                Gender.MALE
            }
        }
        return gender
    }

    private fun findAssociatedCountry(lastName: String): Country {
        val continent = LastNames.findAssociatedContinent(lastName)

        return when (continent) {
            Continent.EUROPE -> Country.FRANCE
            Continent.NORTH_AMERICA -> Country.USA
            Continent.ASIA -> Country.CHINA
            else -> fuzzer.pickOneFrom(listOf(Country.USA, Country.FRANCE))
        }
    }
}
