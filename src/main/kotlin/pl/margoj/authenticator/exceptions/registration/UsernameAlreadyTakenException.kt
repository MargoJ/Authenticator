package pl.margoj.authenticator.exceptions.registration

import org.springframework.http.HttpStatus
import pl.margoj.authenticator.exceptions.ApplicationException

class UsernameAlreadyTakenException(val username: String) : ApplicationException("Username $username is already taken")
{
    override fun getLocalizedMessage(): String = "Nazwa użytkownika '$username' jest juz zajęta"

    override val statusCode: HttpStatus = HttpStatus.CONFLICT
}