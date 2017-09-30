package pl.margoj.authenticator.entities.response.presentations

import pl.margoj.authenticator.entities.database.GameSession

class GameSessionPresentationEntity(gameSession: GameSession)
{
    var character = CharacterPresentationEntity(gameSession.character!!)
}