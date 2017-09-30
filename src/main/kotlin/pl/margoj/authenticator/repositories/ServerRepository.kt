package pl.margoj.authenticator.repositories

import org.springframework.data.repository.CrudRepository
import pl.margoj.authenticator.entities.database.Server

interface ServerRepository : CrudRepository<Server, Long>
{
    fun findByServerIdIgnoreCase(id: String): Server?
}