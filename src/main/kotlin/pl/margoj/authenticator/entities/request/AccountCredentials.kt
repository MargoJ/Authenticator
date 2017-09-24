package pl.margoj.authenticator.entities.request

import pl.margoj.authenticator.util.NonNullValidator

data class AccountCredentials @JvmOverloads constructor
(
    val username: String? = null,
    val password: String? = null
)
{
    fun validate()
    {
        NonNullValidator().let {
            it.check(this.username, "username")
            it.check(this.password, "password")
            it.validate()
        }
    }
}