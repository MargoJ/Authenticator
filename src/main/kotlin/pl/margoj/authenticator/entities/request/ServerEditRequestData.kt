package pl.margoj.authenticator.entities.request

import pl.margoj.authenticator.util.NonNullValidator

data class ServerEditRequestData @JvmOverloads constructor
(
    val token: String? = null,
    val name: String? = null,
    val url: String? = null,
    val stats: Int? = null,
    val open: Boolean? = null,
    val enabled: Boolean? = null
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