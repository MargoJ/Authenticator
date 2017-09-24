package pl.margoj.authenticator.entities.request

import pl.margoj.authenticator.util.NonNullValidator

data class TokenOnlyData @JvmOverloads constructor
(
    val token: String? = null
)
{
    fun validate()
    {
        NonNullValidator().let {
            it.check(this.token, "token")
            it.validate()
        }
    }
}