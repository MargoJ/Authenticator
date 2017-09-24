package pl.margoj.authenticator.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pl.margoj.authenticator.entities.request.AccountCredentials
import pl.margoj.authenticator.entities.request.TokenOnlyData
import pl.margoj.authenticator.entities.response.OkResponse
import pl.margoj.authenticator.entities.response.TokenEntity
import pl.margoj.authenticator.entities.response.ValidationEntity
import pl.margoj.authenticator.exceptions.NullParameterException
import pl.margoj.authenticator.service.AuthenticationService
import java.util.Collections
import javax.servlet.http.HttpServletRequest

@RestController
class AuthenticationController @Autowired constructor(val authenticationService: AuthenticationService)
{
    @RequestMapping("/authenticate", method = arrayOf(RequestMethod.POST))
    fun authenticate(@RequestBody credentials: AccountCredentials, request: HttpServletRequest): ResponseEntity<TokenEntity>
    {
        credentials.validate()
        val session = this.authenticationService.authenticate(credentials.username!!, credentials.password!!, request.remoteAddr)
        val entity = TokenEntity(session.token!!, session.activeUntil!!)
        return ResponseEntity(entity, HttpStatus.CREATED)
    }

    @RequestMapping("/validate", method = arrayOf(RequestMethod.GET))
    fun validate(@RequestParam("account_token") accountToken: String?, @RequestParam("character_token", required = false) characterToken: String?): ResponseEntity<ValidationEntity>
    {
        if (accountToken == null)
        {
            throw NullParameterException(Collections.singletonList("account_token"))
        }

        val account = this.authenticationService.validate(accountToken)
        return ResponseEntity(ValidationEntity(account), HttpStatus.OK)
    }

    @RequestMapping("/renew", method = arrayOf(RequestMethod.POST))
    fun renew(@RequestBody tokenOnlyData: TokenOnlyData): ResponseEntity<TokenEntity>
    {
        tokenOnlyData.validate()
        val session = this.authenticationService.renew(tokenOnlyData.token!!)
        return ResponseEntity(TokenEntity(tokenOnlyData.token, session.activeUntil!!), HttpStatus.OK)
    }

    @RequestMapping("/invalidate", method = arrayOf(RequestMethod.POST))
    fun invalidate(@RequestBody tokenOnlyData: TokenOnlyData): ResponseEntity<OkResponse>
    {
        tokenOnlyData.validate()
        this.authenticationService.invalidate(tokenOnlyData.token!!)
        return ResponseEntity(OkResponse(), HttpStatus.OK)
    }
}