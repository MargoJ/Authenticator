package pl.margoj.authenticator.service

import pl.margoj.authenticator.entities.database.Character
import pl.margoj.authenticator.entities.database.GameSession
import pl.margoj.authenticator.entities.database.Server
import pl.margoj.authenticator.exceptions.character.CharacterInvalidGameTokenException

interface GameService
{
    fun joinServer(character: Character, remoteIp: String): GameSession

    fun checkIfSessionStillIsValid(session: GameSession?): Boolean

    @Throws(CharacterInvalidGameTokenException::class)
    fun validateSessionTokenOnly(gameToken: String): GameSession

    @Throws(CharacterInvalidGameTokenException::class)
    fun validateSession(server: Server, gameToken: String): GameSession

    fun invalidateSession(session: GameSession)

    fun bulkSessionGet(ids: Collection<Long>): Iterable<GameSession>
}