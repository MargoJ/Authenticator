package pl.margoj.authenticator.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import pl.margoj.authenticator.entities.remote.ReCaptchaResponse
import pl.margoj.authenticator.exceptions.ReCaptchaException
import pl.margoj.authenticator.service.ReCaptchaService

@Service
@Profile("use-recaptcha")
class ReCaptchaServiceImpl @Autowired constructor(val restTemplate: RestTemplate) : ReCaptchaService
{
    companion object
    {
        const val VALIDATION_SITE = "https://www.google.com/recaptcha/api/siteverify"
    }

    @Value("\${recaptcha.secret}")
    private lateinit var token: String

    override fun validate(response: String, remoteIp: String)
    {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED

        val map = LinkedMultiValueMap<String, String>()
        map.add("secret", this.token)
        map.add("response", response)
        map.add("remoteip", remoteIp)

        val request = HttpEntity<MultiValueMap<String, String>>(map, headers)

        val reCaptchaResponse = this.restTemplate.postForObject(VALIDATION_SITE, request, ReCaptchaResponse::class.java)

        if (!reCaptchaResponse.success)
        {
            throw ReCaptchaException(reCaptchaResponse.errorCodes)
        }
    }
}