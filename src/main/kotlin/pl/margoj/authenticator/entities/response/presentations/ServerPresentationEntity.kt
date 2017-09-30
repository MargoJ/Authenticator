package pl.margoj.authenticator.entities.response.presentations

import pl.margoj.authenticator.entities.database.Server

class ServerPresentationEntity(server: Server)
{
    var id = server.serverId

    var uuid = server.uuid

    var name = server.serverName

    var url = server.serverURL

    var open = server.open

    var stats = server.stats

    var enabled = server.enabled
}