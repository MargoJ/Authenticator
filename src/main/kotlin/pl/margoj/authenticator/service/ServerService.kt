package pl.margoj.authenticator.service

import pl.margoj.authenticator.entities.database.Server
import pl.margoj.authenticator.entities.request.SessionData
import pl.margoj.authenticator.entities.response.specific.HeartbeatEntity
import pl.margoj.authenticator.exceptions.server.ServerException
import pl.margoj.authenticator.exceptions.server.ServerInvalidSecretException
import pl.margoj.authenticator.exceptions.server.ServerNotFoundException

interface ServerService
{
    fun getAllServers(): Iterable<Server>

    @Throws(ServerException::class)
    fun addServer(id: String, name: String, url: String, open: Boolean, stats: Int)

    fun getServerById(id: String): Server?

    fun updateServer(server: Server, name: String?, url: String?, stats: Int?, open: Boolean?, enabled: Boolean?)

    @Throws(ServerNotFoundException::class)
    fun requireServer(serverId: String): Server

    @Throws(ServerInvalidSecretException::class)
    fun validateServerSecret(server: Server, secret: String)

    fun isJoinable(server: Server): Boolean

    fun canEveryoneRegister(server: Server): Boolean
}