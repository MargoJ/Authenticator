package pl.margoj.authenticator.service

import pl.margoj.authenticator.exceptions.ReCaptchaException

interface ReCaptchaService
{
    @Throws(ReCaptchaException::class)
    fun validate(response: String, remoteIp: String)
}