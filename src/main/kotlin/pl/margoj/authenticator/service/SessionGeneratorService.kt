package pl.margoj.authenticator.service

interface SessionGeneratorService
{
    fun generateToken(): String
}