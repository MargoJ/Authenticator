package pl.margoj.authenticator.exceptions.server

import org.springframework.http.HttpStatus

class ServerNotFoundException(val id: String) : ServerException("Server with id $id doesn't exists")
{
    override fun getLocalizedMessage(): String = "Serwer z id $id nie istnieje"

    override val statusCode: HttpStatus = HttpStatus.NOT_FOUND
}