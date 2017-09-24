package pl.margoj.authenticator.service

import pl.margoj.authenticator.entities.database.Account
import pl.margoj.authenticator.entities.database.AccountSession
import pl.margoj.authenticator.exceptions.auth.AuthorizationException
import pl.margoj.authenticator.exceptions.validation.ValidationException

interface AuthenticationService
{
    @Throws(AuthorizationException::class)
    fun authenticate(username: String, password: String, requestingIp: String): AccountSession

    @Throws(ValidationException::class)
    fun validate(accountToken: String): Account

    @Throws(ValidationException::class)
    fun renew(accountToken: String): AccountSession

    @Throws(ValidationException::class)
    fun invalidate(accountToken: String): AccountSession
}