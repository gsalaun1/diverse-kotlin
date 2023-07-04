package gsalaun.diverse

import gsalaun.diverse.utils.AccountService
import gsalaun.diverse.utils.ChessClub
import gsalaun.diverse.utils.ChessPlayerWithPublicConstructor
import gsalaun.diverse.utils.Country
import gsalaun.diverse.utils.Ingredient
import gsalaun.diverse.utils.SignUpRequest
import gsalaun.diverse.utils.SignUpStatus
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Condition
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDate
import kotlin.reflect.KClass

/**
 * Naive test only to be used to publish valid C# code samples in our Readme.md documentation file.
 *
 */
@ExtendWith(AllTestsFixtures::class)
class FuzzerShouldBeDocumented {

    @Test
    public fun `simplest usage with numbers`() {
        // Instantiates the Fuzzer
        val fuzzer = Fuzzer()

        val results = (0 until 50).map {
            fuzzer.generatePositiveInteger(maxValue = 3)
        }

        val findGreaterNumberThanMaxValue = results.any { it > 3 }
        val findAtLeastOneNumberWithTheMaxValue = results.any { it == 3 }

        assertThat(findGreaterNumberThanMaxValue).isFalse()
        assertThat(findAtLeastOneNumberWithTheMaxValue).isTrue()
    }

    @Test
    fun `simple usages`() {
        val fuzzer = Fuzzer()

        fuzzer.generateDateTime()
        fuzzer.generateEnum<Ingredient>()
        fuzzer.generateDateTimeBetween("1974/06/08", "2020/11/01")
        fuzzer.generateDateTimeBetween(
            LocalDate.of(1974, 6, 8).atStartOfDay(),
            LocalDate.of(2020, 11, 1).atStartOfDay(),
        )

        val player = fuzzer.generateInstanceOf<ChessPlayerWithPublicConstructor>()

        assertThat(player).isInstanceOf(ChessPlayerWithPublicConstructor::class.java)
        assertThat(player).isNotNull()
        assertThat(player?.chessLevel).isNotNull()
        assertThat(player?.formerClubs?.map { it::class }).`is`(isNullOrEmptyOrContainsClass(ChessClub::class))
    }

    @Test
    fun `simplest usage with Persons and passwords`() {
        // Instantiates the Fuzzer
        val fuzzer = Fuzzer();

        // Uses it for various usages
        val randomPositiveNumber = fuzzer.generatePositiveInteger(8);
        println("Positive Number: $randomPositiveNumber");

        val password =
            fuzzer.generatePassword(); // speed up the creation of something relatively 'complicated' with random values
        println("password: $password");

        val person = fuzzer.generatePerson(); // avoid always using the same hard-coded values
        println("First name: ${person.firstName}");
        println("Last name: ${person.lastName}");
        println("Gender: ${person.gender}");
        println("FavoriteNumber: ${person.age}");
        println("IsMarried: ${person.isMarried}");
    }

    @Test
    fun `return InvalidPhoneNumber status when SignUp with an empty PhoneNumber`() {
        val fuzzer = Fuzzer()

        // Uses the Fuzzer
        val person = fuzzer.generatePerson() // speed up the creation of someone with random values
        val password = fuzzer.generatePassword() // avoid always using the same hard-coded values
        val invalidPhoneNumber = ""

        // Do your domain stuff
        val signUpRequest = SignUpRequest(
            login = person.eMail, password = password,
            firstName = person.firstName, lastName = person.lastName,
            phoneNumber = invalidPhoneNumber
        )

        // Here, the quality of the password won't be a blocker for this
        // SignUp process. We just want to check the behaviour with empty phone number
        val signUpResponse = AccountService().signUp(signUpRequest)

        // Assert
        assertThat(signUpResponse.login).isEqualTo(person.eMail)
        assertThat(signUpResponse.status).isEqualTo(SignUpStatus.INVALIDPHONENUMBER)
    }

    private fun isNullOrEmptyOrContainsClass(clazz: KClass<*>) = Condition<Iterable<KClass<*>>?>(
        { collection -> collection == null || collection.toList().isEmpty() || collection.toList().contains(clazz) },
        "is empty or contains Class",
    )
}
