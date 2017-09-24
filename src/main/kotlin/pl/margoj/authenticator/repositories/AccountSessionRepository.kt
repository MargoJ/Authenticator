package pl.margoj.authenticator.repositories

import org.springframework.data.repository.CrudRepository
import pl.margoj.authenticator.entities.database.AccountSession

interface AccountSessionRepository : CrudRepository<AccountSession, Long>
{
    override fun findAll(): MutableIterable<AccountSession>

    fun findByToken(token: String): AccountSession?
}