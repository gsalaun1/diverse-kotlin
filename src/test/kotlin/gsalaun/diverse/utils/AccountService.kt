package gsalaun.diverse.utils

class AccountService {

    fun signUp(signUpRequest: SignUpRequest): SignUpResponse {
        val response = SignUpResponse(signUpRequest.login)
        if (signUpRequest.phoneNumber.isBlank()) {
            response.status = SignUpStatus.INVALIDPHONENUMBER
        }
        return response
    }
}