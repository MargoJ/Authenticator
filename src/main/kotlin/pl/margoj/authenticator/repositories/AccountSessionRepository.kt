package pl.margoj.authenticator.repositories

import org.springframework.data.repository.CrudRepository
import pl.margoj.authenticator.entities.database.AccountSession

interface AccountSessionRepository : CrudRepository<AccountSession, Long>
{
    fun findByToken(token: String): AccountSession?
}