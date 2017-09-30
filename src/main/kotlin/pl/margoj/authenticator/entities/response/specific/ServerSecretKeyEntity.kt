package pl.margoj.authenticator.entities.response.specific

import pl.margoj.authenticator.entities.response.standard.OkResponse

data class ServerSecretKeyEntity
(
        val serverId: String,
        val secretKey: String
) : OkResponse()