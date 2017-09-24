package pl.margoj.authenticator.service

interface PasswordHashingService
{
    fun hash(input: String): String

    fun check(password: String, hashed: String): Boolean
}