package pl.margoj.authenticator.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import pl.margoj.authenticator.entities.request.RegistrationData
import pl.margoj.authenticator.entities.response.standard.OkResponse
import pl.margoj.authenticator.service.ReCaptchaService
import pl.margoj.authenticator.service.RegistrationService
import javax.servlet.http.HttpServletRequest

@Controller
class RegistrationController @Autowired constructor(val registrationService: RegistrationService, val captchaService: ReCaptchaService)
{
    @RequestMapping("/register", method = arrayOf(RequestMethod.POST))
    fun register(@RequestBody registrationData: RegistrationData, request: HttpServletRequest): ResponseEntity<OkResponse>
    {
        registrationData.validate()
        captchaService.validate(registrationData.captchaResponse!!, request.remoteAddr)
        this.registrationService.register(registrationData.username!!, registrationData.password!!, registrationData.email!!, request.remoteAddr)
        return ResponseEntity(OkResponse(), HttpStatus.CREATED)
    }
}