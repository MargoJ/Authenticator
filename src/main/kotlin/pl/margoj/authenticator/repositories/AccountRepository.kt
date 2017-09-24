package pl.margoj.authenticator.repositories

import org.springframework.data.repository.CrudRepository
import pl.margoj.authenticator.entities.database.Account

interface AccountRepository : CrudRepository<Account, Long>
{
    override fun findAll(): MutableIterable<Account>

    fun findByUsernameIgnoreCase(username: String): Account?
}