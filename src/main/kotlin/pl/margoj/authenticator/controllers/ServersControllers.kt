package pl.margoj.authenticator.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pl.margoj.authenticator.entities.database.AccountRole
import pl.margoj.authenticator.entities.database.GameSession
import pl.margoj.authenticator.entities.request.ServerAddRequestData
import pl.margoj.authenticator.entities.response.presentations.ServerPresentationEntity
import pl.margoj.authenticator.entities.response.specific.ServerListEntity
import pl.margoj.authenticator.entities.response.standard.OkResponse
import pl.margoj.authenticator.exceptions.character.CharacterInvalidGameTokenException
import pl.margoj.authenticator.service.AuthenticationService
import pl.margoj.authenticator.service.GameService
import pl.margoj.authenticator.service.ServerService

@RestController
@RequestMapping("/servers/")
class ServersControllers @Autowired constructor
(
        val serverService: ServerService,
        val authenticationService: AuthenticationService
)
{
    @RequestMapping("/list")
    fun list(): ResponseEntity<ServerListEntity>
    {
        val servers = serverService.getAllServers()
        val map = HashMap<String, ServerPresentationEntity>()
        val iterator = servers.iterator()

        while (iterator.hasNext())
        {
            val server = iterator.next()
            map.put(server.serverId!!, ServerPresentationEntity(server))
        }

        val serverListEntity = ServerListEntity(map)
        return ResponseEntity(serverListEntity, HttpStatus.OK)
    }

    @RequestMapping("/add", method = arrayOf(RequestMethod.POST))
    fun add(@RequestBody serverAddRequestData: ServerAddRequestData): ResponseEntity<OkResponse>
    {
        serverAddRequestData.validate()

        val account = this.authenticationService.validate(serverAddRequestData.token!!)
        this.authenticationService.requireRole(account, AccountRole.ADMINISTRATOR)

        this.serverService.addServer(
                serverAddRequestData.id!!,
                serverAddRequestData.name!!,
                serverAddRequestData.url!!,
                serverAddRequestData.open!!,
                serverAddRequestData.stats!!
        )

        return ResponseEntity(OkResponse(), HttpStatus.CREATED)
    }
}