package pl.margoj.authenticator.entities.response.specific

import pl.margoj.authenticator.entities.response.presentations.ServerPresentationEntity
import pl.margoj.authenticator.entities.response.standard.OkResponse

data class ServerListEntity
(
        val servers: Map<String, ServerPresentationEntity>
) : OkResponse()