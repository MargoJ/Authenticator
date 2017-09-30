package pl.margoj.authenticator.exceptions.character

import org.springframework.http.HttpStatus
import pl.margoj.authenticator.exceptions.validation.ValidationException

class CharacterInvalidGameTokenException : ValidationException("Invalid token")
{
    override fun getLocalizedMessage(): String = "Niepoprawny token"

    override val statusCode: HttpStatus = HttpStatus.FORBIDDEN
}