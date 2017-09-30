package pl.margoj.authenticator.entities.response.specific

import pl.margoj.authenticator.entities.response.presentations.ServerPresentationEntity
import pl.margoj.authenticator.entities.response.standard.OkResponse

data class ServerEntryEntity
(
        val server: ServerPresentationEntity
) : OkResponse()