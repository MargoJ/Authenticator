package pl.margoj.authenticator.entities.response

import java.util.Date

data class TokenEntity
(
        val token: String,

        val sessionActiveUntil: Date
) : OkResponse()