package pl.margoj.authenticator.service

interface RegistrationService
{
    fun register(username: String, password: String, email: String, registeredIp: String)
}