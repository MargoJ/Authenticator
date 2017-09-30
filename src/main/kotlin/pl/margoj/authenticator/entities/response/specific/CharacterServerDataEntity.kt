package pl.margoj.authenticator.entities.response.specific

import pl.margoj.authenticator.entities.response.presentations.CharacterServerPresentationEntity
import pl.margoj.authenticator.entities.response.standard.OkResponse

data class CharacterServerDataEntity
(
        val currentSessionId: Long,

        val character: CharacterServerPresentationEntity
) : OkResponse()