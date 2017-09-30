package pl.margoj.authenticator.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pl.margoj.authenticator.entities.request.CharacterAddRequestData
import pl.margoj.authenticator.entities.response.presentations.CharacterPresentationEntity
import pl.margoj.authenticator.entities.response.presentations.GameSessionPresentationEntity
import pl.margoj.authenticator.entities.response.specific.CharacterEntryEntity
import pl.margoj.authenticator.entities.response.specific.GameSessionEntity
import pl.margoj.authenticator.exceptions.character.CharacterInvalidGameTokenException
import pl.margoj.authenticator.service.AuthenticationService
import pl.margoj.authenticator.service.CharacterService
import pl.margoj.authenticator.service.GameService
import pl.margoj.authenticator.service.ServerService

@RestController
@RequestMapping("/character/")
class CharacterController @Autowired constructor
(
        val characterService: CharacterService,
        val authenticationService: AuthenticationService,
        val serverService: ServerService,
        val gameService: GameService
)
{
    @RequestMapping("/add", method = arrayOf(RequestMethod.POST))
    fun add(@RequestBody requestData: CharacterAddRequestData): ResponseEntity<CharacterEntryEntity>
    {
        requestData.validate()
        val account = this.authenticationService.validate(requestData.token!!)
        val server = this.serverService.requireServer(requestData.serverId!!)

        val character = this.characterService.createCharacter(account, server, requestData.name!!, requestData.profession!!, requestData.gender!!)

        return ResponseEntity(CharacterEntryEntity(CharacterPresentationEntity(character)), HttpStatus.CREATED)
    }

    @RequestMapping("/identifyGame")
    fun identifyGame(@RequestParam("account_token") accountToken: String, @RequestParam("game_token") gameToken: String): ResponseEntity<GameSessionEntity>
    {
        val account = this.authenticationService.validate(accountToken)
        val session = this.gameService.validateSessionTokenOnly(gameToken)

        if(account != session.character!!.ownerAccount)
        {
            throw CharacterInvalidGameTokenException()
        }

        return ResponseEntity(GameSessionEntity(GameSessionPresentationEntity(session)), HttpStatus.OK)
    }
}