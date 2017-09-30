package pl.margoj.authenticator.entities.response.specific

import pl.margoj.authenticator.entities.response.presentations.CharacterPresentationEntity
import pl.margoj.authenticator.entities.response.standard.OkResponse

data class CharacterEntryEntity
(
        val character: CharacterPresentationEntity
) : OkResponse()