package pl.margoj.authenticator.entities.request

import pl.margoj.authenticator.util.NonNullValidator

data class CharacterAddRequestData @JvmOverloads constructor
(
    val token: String? = null,
    val name: String? = null,
    val profession: Char? = null,
    val gender: Char? = null,
    val serverId: String? = null
)
{
    fun validate()
    {
        NonNullValidator().let {
            it.check(this.token, "token")
            it.check(this.name, "name")
            it.check(this.profession, "profession")
            it.check(this.gender, "gender")
            it.check(this.serverId, "server_id")
            it.validate()
        }
    }
}