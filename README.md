# Diverse - Kotlin Version

Kotlin version of Diverse library - https://github.com/tpierrain/Diverse

Developed by [use case driven on twitter](https://twitter.com/tpierrain) - (thomas@42skillz.com)

## Sample

Example of a typical test using Fuzzers. Here, a test for the SignUp process of an API:

```kotlin
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
```