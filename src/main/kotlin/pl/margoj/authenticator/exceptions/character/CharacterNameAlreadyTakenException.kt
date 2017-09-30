package pl.margoj.authenticator.exceptions.character

import org.springframework.http.HttpStatus

class CharacterNameAlreadyTakenException(val name: String) : CharacterException("Character name $name is already taken")
{
    override fun getLocalizedMessage(): String = "Gracz o nicku $name już istnieje!"

    override val statusCode: HttpStatus = HttpStatus.CONFLICT

    override fun populateEntity(map: MutableMap<String, Any?>)
    {
        map.put("name", name)
    }
}