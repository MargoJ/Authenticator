package pl.margoj.authenticator.exceptions.server

import org.springframework.http.HttpStatus

class ServerNotJoinableException(val serverId: String, val serverName: String) : ServerException("Server $serverName is not joinable now")
{
    override fun getLocalizedMessage(): String = "Serwer $serverName jest tymczasowo zamknięty"

    override val statusCode: HttpStatus = HttpStatus.FORBIDDEN

    override fun populateEntity(map: MutableMap<String, Any?>)
    {
        map.put("server", this.serverId)
        map.put("server_name", this.serverName)
    }
}