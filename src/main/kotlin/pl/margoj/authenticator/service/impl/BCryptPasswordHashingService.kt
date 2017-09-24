package pl.margoj.authenticator.service.impl

import org.mindrot.jbcrypt.BCrypt
import org.springframework.stereotype.Service
import pl.margoj.authenticator.service.PasswordHashingService

@Service
class BCryptPasswordHashingService : PasswordHashingService
{
    override fun hash(input: String): String = BCrypt.hashpw(input, BCrypt.gensalt(10))

    override fun check(password: String, hashed: String): Boolean = BCrypt.checkpw(password, hashed)
}