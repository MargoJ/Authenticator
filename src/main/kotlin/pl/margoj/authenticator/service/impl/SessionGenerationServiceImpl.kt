package pl.margoj.authenticator.service.impl

import org.springframework.stereotype.Service
import pl.margoj.authenticator.service.SessionGeneratorService
import java.lang.StringBuilder
import java.security.SecureRandom

@Service
class SessionGenerationServiceImpl : SessionGeneratorService
{
    companion object
    {
        const val TOKEN_LENGTH = 255
        val TOKEN_POOL = "abcdefghijklmnoprqstuvwxyzABCDEFGHIJKLMNOPRQSTUVWXYZ0123456789".toCharArray()
    }

    private val random = SecureRandom()

    override fun generateToken(): String
    {
        val builder = StringBuilder(TOKEN_LENGTH)

        for(i in 0 until TOKEN_LENGTH)
        {
            builder.append(TOKEN_POOL[random.nextInt(TOKEN_POOL.size)])
        }

        return builder.toString()
    }
}