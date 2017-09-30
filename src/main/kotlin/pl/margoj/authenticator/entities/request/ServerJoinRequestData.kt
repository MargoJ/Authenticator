package pl.margoj.authenticator.entities.request

import pl.margoj.authenticator.util.NonNullValidator

data class ServerJoinRequestData @JvmOverloads constructor
(
    val token: String? = null,
    val characterName: String? = null
)
{
    fun validate()
    {
        NonNullValidator().let {
            it.check(this.token, "token")
            it.check(this.characterName, "character_name")
            it.validate()
        }
    }
}