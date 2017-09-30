package pl.margoj.authenticator.exceptions.character

import org.springframework.http.HttpStatus

class CharacterNotExistsException(val name: String, val server: String, val serverName: String) : CharacterException("Character name $name on server $serverName doesn't exist or is not owned by you")
{
    override fun getLocalizedMessage(): String = "Postać nicku $name na serwerze $serverName nie istnieje lub nie należy do ciebie!"

    override val statusCode: HttpStatus = HttpStatus.NOT_FOUND

    override fun populateEntity(map: MutableMap<String, Any?>)
    {
        map.put("name", name)
        map.put("server", server)
        map.put("server_name", serverName)
    }
}