package pl.margoj.authenticator.exceptions.validation

import org.springframework.http.HttpStatus

class InvalidAccountTokenException : ValidationException("Invalid token")
{
    override fun getLocalizedMessage(): String = "Niepoprawny token"

    override val statusCode: HttpStatus = HttpStatus.FORBIDDEN
}