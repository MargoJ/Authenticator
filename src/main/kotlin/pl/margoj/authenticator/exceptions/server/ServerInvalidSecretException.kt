package pl.margoj.authenticator.exceptions.server

import org.springframework.http.HttpStatus
import pl.margoj.authenticator.exceptions.validation.ValidationException

class ServerInvalidSecretException : ValidationException("Invalid secret key")
{
    override fun getLocalizedMessage(): String = "Niepoprawny secret key"

    override val statusCode: HttpStatus = HttpStatus.FORBIDDEN
}