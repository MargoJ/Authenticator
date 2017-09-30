package pl.margoj.authenticator.exceptions.server

import org.springframework.http.HttpStatus

class ServerInvalidIdException(val id: String) : ServerException("Server id $id is invalid")
{
    override fun getLocalizedMessage(): String = "Id serwera $id jest nieprawidłowe"

    override val statusCode: HttpStatus = HttpStatus.BAD_REQUEST

    override fun populateEntity(map: MutableMap<String, Any?>)
    {
        map.put("id", id)
    }
}