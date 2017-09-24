package pl.margoj.authenticator.exceptions.auth

import org.springframework.http.HttpStatus
import pl.margoj.authenticator.exceptions.ApplicationException

class BadCredentialsException : AuthorizationException("Invalid username or/and password")
{
    override fun getLocalizedMessage(): String = "Błędna nazwa użytkownika i/lub hasło"

    override val statusCode: HttpStatus = HttpStatus.FORBIDDEN
}