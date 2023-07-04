package gsalaun.diverse.utils

data class SignUpRequest(
    val login: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String
)