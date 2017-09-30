package pl.margoj.authenticator.entities.request

import pl.margoj.authenticator.util.NonNullValidator

data class ServerAddRequestData @JvmOverloads constructor
(
    val token: String? = null,
    val id: String? = null,
    val name: String? = null,
    val url: String? = null,
    val stats: Int? = null,
    val open: Boolean? = null
)
{
    fun validate()
    {
        NonNullValidator().let {
            it.check(this.token, "token")
            it.check(this.id, "id")
            it.check(this.name, "name")
            it.check(this.url, "url")
            it.check(this.stats, "stats")
            it.check(this.open, "open")
            it.validate()
        }
    }
}