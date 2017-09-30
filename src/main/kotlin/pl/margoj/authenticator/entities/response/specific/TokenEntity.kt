package pl.margoj.authenticator.entities.response.specific

import pl.margoj.authenticator.entities.response.standard.OkResponse
import java.util.Date

data class TokenEntity
(
        val token: String,

        val sessionActiveUntil: Date
) : OkResponse()