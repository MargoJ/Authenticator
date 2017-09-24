package pl.margoj.authenticator.entities.request

import pl.margoj.authenticator.util.NonNullValidator

data class RegistrationData @JvmOverloads constructor
(
        val username: String? = null,
        val password: String? = null,
        val email: String? = null,
        val captchaResponse: String? = null
)
{
    fun validate()
    {
        NonNullValidator().let {
            it.check(this.username, "username")
            it.check(this.password, "password")
            it.check(this.email, "email")
            it.check(this.captchaResponse, "captcha_response")
            it.validate()
        }
    }
}