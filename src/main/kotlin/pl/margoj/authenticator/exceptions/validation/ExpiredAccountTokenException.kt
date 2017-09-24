package pl.margoj.authenticator.exceptions.validation

import org.springframework.http.HttpStatus

class ExpiredAccountTokenException : ValidationException("Token expired")
{
    override fun getLocalizedMessage(): String = "Token wygasł"

    override val statusCode: HttpStatus = HttpStatus.FORBIDDEN
}