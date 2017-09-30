package pl.margoj.authenticator.controllers

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pl.margoj.authenticator.entities.database.AccountRole
import pl.margoj.authenticator.entities.request.HeartbeatData
import pl.margoj.authenticator.entities.request.ServerEditRequestData
import pl.margoj.authenticator.entities.request.ServerJoinRequestData
import pl.margoj.authenticator.entities.request.TokenOnlyData
import pl.margoj.authenticator.entities.response.presentations.CharacterServerPresentationEntity
import pl.margoj.authenticator.entities.response.presentations.GameSessionPresentationEntity
import pl.margoj.authenticator.entities.response.presentations.ServerPresentationEntity
import pl.margoj.authenticator.entities.response.specific.*
import pl.margoj.authenticator.entities.response.standard.OkResponse
import pl.margoj.authenticator.exceptions.character.CharacterNotExistsException
import pl.margoj.authenticator.service.AuthenticationService
import pl.margoj.authenticator.service.GameService
import pl.margoj.authenticator.service.HeartbeatService
import pl.margoj.authenticator.service.ServerService
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/server/")
class ServerController
(
        val authenticationService: AuthenticationService,
        val serverService: ServerService,
        val gameService: GameService,
        val heartbeatService: HeartbeatService
)
{
    @RequestMapping("{serverId}", method = arrayOf(RequestMethod.GET))
    fun index(@PathVariable("serverId") serverId: String): ResponseEntity<ServerEntryEntity>
    {
        val server = this.serverService.requireServer(serverId)
        return ResponseEntity(ServerEntryEntity(ServerPresentationEntity(server)), HttpStatus.OK)
    }

    @RequestMapping("{serverId}", method = arrayOf(RequestMethod.PATCH))
    fun index(@PathVariable("serverId") serverId: String, @RequestBody serverEditRequestData: ServerEditRequestData): ResponseEntity<ServerEntryEntity>
    {
        serverEditRequestData.validate()

        val account = this.authenticationService.validate(serverEditRequestData.token!!)
        this.authenticationService.requireRole(account, AccountRole.ADMINISTRATOR)

        val server = this.serverService.requireServer(serverId)

        this.serverService.updateServer(
                server,
                serverEditRequestData.name,
                serverEditRequestData.url,
                serverEditRequestData.stats,
                serverEditRequestData.open,
                serverEditRequestData.enabled
        )

        return ResponseEntity(ServerEntryEntity(ServerPresentationEntity(server)), HttpStatus.OK)
    }

    @RequestMapping("{serverId}/join", method = arrayOf(RequestMethod.POST))
    fun join(@PathVariable("serverId") serverId: String, @RequestBody requestData: ServerJoinRequestData, request: HttpServletRequest): ResponseEntity<GameTokenEntity>
    {
        requestData.validate()

        val account = this.authenticationService.validate(requestData.token!!)
        val server = this.serverService.requireServer(serverId)

        val character = account.characters!!
                .find { it.server == server && it.name == requestData.characterName }
                ?: throw CharacterNotExistsException(requestData.characterName!!, server.serverId!!, server.serverName!!)

        val session = this.gameService.joinServer(character, request.remoteAddr)

        return ResponseEntity(GameTokenEntity(session.token!!, GameSessionPresentationEntity(session)), HttpStatus.CREATED)
    }

    @RequestMapping("{serverId}/validateToken", method = arrayOf(RequestMethod.GET))
    fun validateToken(@PathVariable("serverId") serverId: String, @RequestParam("game_token") token: String): ResponseEntity<OkResponse>
    {
        val server = this.serverService.requireServer(serverId)
        this.gameService.validateSession(server, token)

        return ResponseEntity(OkResponse(), HttpStatus.OK)
    }

    @RequestMapping("{serverId}/invalidateToken", method = arrayOf(RequestMethod.POST))
    fun invalidateToken(@PathVariable("serverId") serverId: String, @RequestBody requestData: TokenOnlyData): ResponseEntity<OkResponse>
    {
        requestData.validate()

        val server = this.serverService.requireServer(serverId)
        val session = this.gameService.validateSession(server, requestData.token!!)

        this.gameService.invalidateSession(session)

        return ResponseEntity(OkResponse(), HttpStatus.OK)
    }

    @RequestMapping("{serverId}/hasJoined", method = arrayOf(RequestMethod.GET))
    fun hasJoined(@PathVariable("serverId") serverId: String, @RequestParam("game_token") gameToken: String, @RequestParam("server_secret") serverSecret: String): ResponseEntity<CharacterServerDataEntity>
    {
        val server = this.serverService.requireServer(serverId)
        this.serverService.validateServerSecret(server, serverSecret)
        val session = this.gameService.validateSession(server, gameToken)

        return ResponseEntity(CharacterServerDataEntity(session.id!!, CharacterServerPresentationEntity(session.character!!)), HttpStatus.OK)
    }

    @RequestMapping("{serverId}/secret", method = arrayOf(RequestMethod.GET))
    fun getSecret(@PathVariable("serverId") serverId: String, @RequestParam("account_token") accountToken: String): ResponseEntity<ServerSecretKeyEntity>
    {
        val server = this.serverService.requireServer(serverId)
        val account = this.authenticationService.validate(accountToken)
        this.authenticationService.requireRole(account, AccountRole.ADMINISTRATOR)

        return ResponseEntity(ServerSecretKeyEntity(server.serverId!!, server.secretKey!!), HttpStatus.OK)
    }

    @RequestMapping("{serverId}/heartbeat", method = arrayOf(RequestMethod.POST))
    fun heartbeat(@PathVariable("serverId") serverId: String, @RequestBody heartbeatData: HeartbeatData): ResponseEntity<HeartbeatEntity>
    {
        heartbeatData.validate()

        val server = this.serverService.requireServer(serverId)
        this.serverService.validateServerSecret(server, heartbeatData.secret!!)

        val entity = this.heartbeatService.performServerHeartbeat(heartbeatData.sessions!!)

        return ResponseEntity(entity, HttpStatus.OK)
    }
}