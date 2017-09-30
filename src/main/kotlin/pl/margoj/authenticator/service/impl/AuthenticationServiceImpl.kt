package pl.margoj.authenticator.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.margoj.authenticator.entities.database.Account
import pl.margoj.authenticator.entities.database.AccountRole
import pl.margoj.authenticator.entities.database.AccountSession
import pl.margoj.authenticator.exceptions.InsufficientPermissionsException
import pl.margoj.authenticator.exceptions.auth.BadCredentialsException
import pl.margoj.authenticator.exceptions.validation.ExpiredAccountTokenException
import pl.margoj.authenticator.exceptions.validation.InvalidAccountTokenException
import pl.margoj.authenticator.repositories.AccountRepository
import pl.margoj.authenticator.repositories.AccountSessionRepository
import pl.margoj.authenticator.service.AuthenticationService
import pl.margoj.authenticator.service.PasswordHashingService
import pl.margoj.authenticator.service.SessionGeneratorService
import java.util.Date
import java.util.concurrent.TimeUnit

@Service
class AuthenticationServiceImpl @Autowired constructor
(
        val accountRepository: AccountRepository,
        val sessionRepository: AccountSessionRepository,
        val passwordHashingService: PasswordHashingService,
        val sessionGeneratorService: SessionGeneratorService
) : AuthenticationService
{
    override fun authenticate(username: String, password: String, requestingIp: String): AccountSession
    {
        val account = accountRepository.findByUsernameIgnoreCase(username) ?: throw BadCredentialsException()

        if (!passwordHashingService.check(password, account.password!!))
        {
            throw BadCredentialsException()
        }

        val session = AccountSession(
                token = sessionGeneratorService.generateToken(),
                account = account,
                activeSince = Date(),
                activeUntil = Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(2L)), // TODO: CONFIGURABLE
                requestingIp = requestingIp
        )

        account.currentSession = session
        account.lastSeen = Date()

        accountRepository.save(account)

        return session
    }

    override fun validate(accountToken: String): Account
    {
        val session = this.sessionRepository.findByToken(accountToken)

        this.validate(session)

        return session!!.account!!
    }

    override fun validate(session: AccountSession?)
    {
        session ?: throw InvalidAccountTokenException()

        if (session.account!!.currentSession != session || session.activeUntil!!.time < System.currentTimeMillis() || session.invalidated == true)
        {
            throw ExpiredAccountTokenException()
        }
    }

    override fun renew(accountToken: String): AccountSession
    {
        val account = this.validate(accountToken)
        account.currentSession!!.activeUntil = Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(2L)) // TODO: Configurable

        this.accountRepository.save(account)
        return account.currentSession!!
    }

    override fun invalidate(accountToken: String): AccountSession
    {
        val account = this.validate(accountToken)
        account.currentSession!!.invalidated = true

        this.accountRepository.save(account)
        return account.currentSession!!
    }

    override fun requireRole(account: Account, required: AccountRole)
    {
        val current = account.accountRole!!

        if(required !in current)
        {
            throw InsufficientPermissionsException(current, required)
        }
    }
}