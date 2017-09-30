package pl.margoj.authenticator.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.margoj.authenticator.entities.database.Character
import pl.margoj.authenticator.entities.database.GameSession
import pl.margoj.authenticator.entities.database.Server
import pl.margoj.authenticator.exceptions.character.CharacterInvalidGameTokenException
import pl.margoj.authenticator.exceptions.server.ServerNotJoinableException
import pl.margoj.authenticator.repositories.AccountRepository
import pl.margoj.authenticator.repositories.GameSessionRepository
import pl.margoj.authenticator.service.AuthenticationService
import pl.margoj.authenticator.service.GameService
import pl.margoj.authenticator.service.ServerService
import pl.margoj.authenticator.service.SessionGeneratorService
import java.util.Date

@Service
class GameServiceImpl @Autowired constructor
(
        val authenticationService: AuthenticationService,
        val sessionGeneratorService: SessionGeneratorService,
        val gameSessionRepository: GameSessionRepository,
        val accountRepository: AccountRepository,
        val serverService: ServerService
) : GameService
{
    override fun joinServer(character: Character, remoteIp: String): GameSession
    {
        val account = character.ownerAccount!!
        this.authenticationService.validate(account.currentSession)

        if (!this.serverService.isJoinable(character.server!!))
        {
            throw ServerNotJoinableException(character.server.serverId!!, character.server.serverName!!)
        }

        val accountSession = account.currentSession!!

        val gameSession = GameSession(
                token = this.sessionGeneratorService.generateToken(),
                character = character,
                activeSince = Date(),
                requestingIp = remoteIp,
                currentSession = accountSession
        )

        account.currentGameSession = gameSession
        character.currentSession = gameSession
        character.lastSeen = Date()

        this.accountRepository.save(account)

        return gameSession
    }

    override fun checkIfSessionStillIsValid(session: GameSession?): Boolean
    {
        if (session == null || session.invalidated == true || session.character!!.currentSession != session || session.character.ownerAccount!!.currentGameSession != session)
        {
            return false
        }
        return true
    }

    override fun validateSessionTokenOnly(gameToken: String): GameSession
    {
        val session = this.gameSessionRepository.findByToken(gameToken)

        if(!this.checkIfSessionStillIsValid(session))
        {
            throw CharacterInvalidGameTokenException()
        }

        this.authenticationService.validate(session!!.currentSession)
        return session
    }

    override fun validateSession(server: Server, gameToken: String): GameSession
    {
        val session = this.gameSessionRepository.findByToken(gameToken)

        if(!this.checkIfSessionStillIsValid(session))
        {
            throw CharacterInvalidGameTokenException()
        }

        if (session!!.character!!.server != server)
        {
            throw CharacterInvalidGameTokenException()
        }

        this.authenticationService.validate(session.currentSession)

        return session
    }

    override fun invalidateSession(session: GameSession)
    {
        session.invalidated = true
        this.accountRepository.save(session.character!!.ownerAccount!!)
    }

    override fun bulkSessionGet(ids: Collection<Long>): Iterable<GameSession>
    {
        return this.gameSessionRepository.findByIdIn(ids)
    }
}