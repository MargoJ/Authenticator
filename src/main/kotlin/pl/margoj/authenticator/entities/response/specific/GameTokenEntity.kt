package pl.margoj.authenticator.entities.response.specific

import pl.margoj.authenticator.entities.response.presentations.GameSessionPresentationEntity
import pl.margoj.authenticator.entities.response.standard.OkResponse

data class GameTokenEntity
(
        val gameToken: String,
        val game: GameSessionPresentationEntity
) : OkResponse()