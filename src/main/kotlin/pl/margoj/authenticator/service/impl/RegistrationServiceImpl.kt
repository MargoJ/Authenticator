package pl.margoj.authenticator.service.impl

import org.apache.log4j.LogManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import pl.margoj.authenticator.entities.database.Account
import pl.margoj.authenticator.entities.database.AccountRole
import pl.margoj.authenticator.exceptions.InvalidParameterException
import pl.margoj.authenticator.exceptions.registration.UsernameAlreadyTakenException
import pl.margoj.authenticator.repositories.AccountRepository
import pl.margoj.authenticator.service.PasswordHashingService
import pl.margoj.authenticator.service.RegistrationService
import java.util.Date

@Service
class RegistrationServiceImpl @Autowired constructor
(
    val accountRepository: AccountRepository,
    val hashingService: PasswordHashingService
) : RegistrationService
{
    private val logger=  LogManager.getLogger(RegistrationServiceImpl::class.java)

    private companion object
    {
        const val USERNAME_REGEXP_ERROR = "Username must contain only letters, numbers and '_' characters"
        const val USERNAME_RANGE_ERROR = "Username must contain of minimum __MIN and maximum __MAX charcters"
        const val PASSWORD_ERROR = "Password must contain of at least __MIN characters"
    }

    @Value("\${margoj.validate.username.range.min}")
    private var usernameRangeMin: Int? = null

    @Value("\${margoj.validate.username.range.max}")
    private var usernameRangeMax: Int? = null

    @Value("\${margoj.validate.username.regexp}")
    private lateinit var usernameRegexpString: String

    @Value("\${margoj.validate.password.min}")
    private var passwordMin: Int? = null

    @Value("\${margoj.validate.password.max}")
    private var passwordMax: Int? = null

    @Value("\${margoj.validate.username.regexp.error}")
    private lateinit var usernameErrorRegexp: String

    @Value("\${margoj.validate.username.range.error}")
    private lateinit var usernameErrorRange: String

    @Value("\${margoj.validate.password.error}")
    private lateinit var passwordError: String

    private val usernameRegexp: Regex by lazy { this.usernameRegexpString.toRegex() }

    override fun register(username: String, password: String, email: String, registeredIp: String)
    {
        val existingAccount = this.accountRepository.findByUsernameIgnoreCase(username)

        if(existingAccount != null)
        {
            throw UsernameAlreadyTakenException(existingAccount.username!!)
        }

        if(username.length !in this.usernameRangeMin!!..this.usernameRangeMax!!)
        {
            throw InvalidParameterException(
                    "username",
                    USERNAME_RANGE_ERROR.replace("__MIN", this.usernameRangeMin!!.toString()).replace("__MAX", this.usernameRangeMax!!.toString()),
                    this.usernameErrorRange
            )
        }

        if(!username.matches(this.usernameRegexp))
        {
            throw InvalidParameterException("username", USERNAME_REGEXP_ERROR, this.usernameErrorRegexp)
        }

        if(password.length !in this.passwordMin!!..this.passwordMax!!)
        {
            throw InvalidParameterException("password", PASSWORD_ERROR, this.passwordError)
        }

        val newAccount = Account(
                username = username,
                password = this.hashingService.hash(password),
                accountRole = AccountRole.USER,
                creationDate = Date(),
                creatingIp = registeredIp
        )

        this.accountRepository.save(newAccount)

        logger.info("${newAccount.username}: registered (ip: $registeredIp)")
    }
}