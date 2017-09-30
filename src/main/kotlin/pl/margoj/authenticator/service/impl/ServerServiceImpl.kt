package pl.margoj.authenticator.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.margoj.authenticator.entities.database.Server
import pl.margoj.authenticator.exceptions.server.ServerDuplicatedIdException
import pl.margoj.authenticator.exceptions.server.ServerInvalidIdException
import pl.margoj.authenticator.exceptions.server.ServerInvalidSecretException
import pl.margoj.authenticator.exceptions.server.ServerNotFoundException
import pl.margoj.authenticator.repositories.ServerRepository
import pl.margoj.authenticator.service.ServerService
import pl.margoj.authenticator.service.SessionGeneratorService

@Service
class ServerServiceImpl @Autowired constructor
(
        val serverRepository: ServerRepository,
        val sessionGeneratorService: SessionGeneratorService
) : ServerService
{
    companion object
    {
        val SERVER_ID_PATTERN = "[a-z0-9_]{3,32}".toRegex()
    }

    override fun getAllServers(): Iterable<Server>
    {
        return this.serverRepository.findAll()
    }

    override fun addServer(id: String, name: String, url: String, open: Boolean, stats: Int)
    {
        val existing = this.serverRepository.findByServerIdIgnoreCase(id)
        if (existing != null)
        {
            throw ServerDuplicatedIdException(existing.serverId!!)
        }

        if (!SERVER_ID_PATTERN.matches(id))
        {
            throw ServerInvalidIdException(id)
        }

        val server = Server(
                serverId = id,
                serverName = name,
                open = open,
                serverURL = url,
                stats = stats,
                secretKey = this.sessionGeneratorService.generateToken(),
                enabled = false
        )

        this.serverRepository.save(server)
    }

    override fun getServerById(id: String): Server?
    {
        return this.serverRepository.findByServerIdIgnoreCase(id)
    }

    override fun updateServer(server: Server, name: String?, url: String?, stats: Int?, open: Boolean?, enabled: Boolean?)
    {
        server.takeIf { name != null }?.serverName = name
        server.takeIf { url != null }?.serverURL = url
        server.takeIf { stats != null }?.stats = stats
        server.takeIf { open != null }?.open = open
        server.takeIf { enabled != null }?.enabled = enabled

        this.serverRepository.save(server)
    }

    override fun requireServer(serverId: String): Server
    {
        return this.getServerById(serverId) ?: throw ServerNotFoundException(serverId)
    }

    override fun validateServerSecret(server: Server, secret: String)
    {
        if (server.secretKey!! != secret)
        {
            throw ServerInvalidSecretException()
        }
    }

    override fun isJoinable(server: Server): Boolean
    {
        return server.enabled == true
    }

    override fun canEveryoneRegister(server: Server): Boolean
    {
        return this.isJoinable(server) && server.open == true
    }
}