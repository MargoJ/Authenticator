package pl.margoj.authenticator.exceptions.registration

import org.springframework.http.HttpStatus
import pl.margoj.authenticator.exceptions.ApplicationException

class ServerRegistrationClosedException(val serverId: String, val serverName: String) : ApplicationException("Server $serverName has closed registration")
{
    override fun getLocalizedMessage(): String = "Serwer $serverName ma teraz wyłączoną możliwość rejestracji"

    override val statusCode: HttpStatus = HttpStatus.FORBIDDEN

    override fun populateEntity(map: MutableMap<String, Any?>)
    {
        map.put("server", this.serverId)
        map.put("server_name", this.serverName)
    }
}