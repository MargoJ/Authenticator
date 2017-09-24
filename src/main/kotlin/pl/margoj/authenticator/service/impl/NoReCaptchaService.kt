package pl.margoj.authenticator.service.impl

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import pl.margoj.authenticator.service.ReCaptchaService

@Service
@Profile("!use-recaptcha")
class NoReCaptchaService : ReCaptchaService
{
    private val logger = LoggerFactory.getLogger(NoReCaptchaService::class.java)

    override fun validate(response: String, remoteIp: String)
    {
        logger.info("Letting use '$remoteIp' in with response '$response'")
    }
}