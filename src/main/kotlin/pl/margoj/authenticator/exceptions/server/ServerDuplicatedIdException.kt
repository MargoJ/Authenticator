package pl.margoj.authenticator.exceptions.server

import org.springframework.http.HttpStatus

class ServerDuplicatedIdException(val id: String) : ServerException("Server id $id already exists")
{
    override fun getLocalizedMessage(): String = "Id serwera $id już istnieje"

    override val statusCode: HttpStatus = HttpStatus.CONFLICT

    override fun populateEntity(map: MutableMap<String, Any?>)
    {
        map.put("id", id)
    }
}