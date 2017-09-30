package pl.margoj.authenticator.repositories

import org.springframework.data.repository.CrudRepository
import pl.margoj.authenticator.entities.database.GameSession

interface GameSessionRepository : CrudRepository<GameSession, Long>
{
    fun findByToken(token: String): GameSession?

    fun findByIdIn(idList: Collection<Long>): Iterable<GameSession>
}