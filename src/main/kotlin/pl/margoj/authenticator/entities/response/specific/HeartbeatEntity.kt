package pl.margoj.authenticator.entities.response.specific

import pl.margoj.authenticator.entities.response.standard.OkResponse

data class HeartbeatEntity
(
        val expiredIds: Array<Long>
) : OkResponse()