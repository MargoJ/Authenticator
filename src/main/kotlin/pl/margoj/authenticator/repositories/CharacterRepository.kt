package pl.margoj.authenticator.repositories

import org.springframework.data.repository.CrudRepository
import pl.margoj.authenticator.entities.database.Character
import pl.margoj.authenticator.entities.database.Server

interface CharacterRepository : CrudRepository<Character, Long>
{
    fun findByServerAndNameIgnoreCase(server: Server, name: String): Character?
}