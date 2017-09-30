package pl.margoj.authenticator.entities.response.presentations

import pl.margoj.authenticator.entities.database.Character

class CharacterPresentationEntity(character: Character)
{
    var name = character.name

    var profession = character.profession

    var gender = character.gender

    var level = character.level

    var server = ServerPresentationEntity(character.server!!)

    var uuid = character.uuid

    var creationDate = character.creationDate

    var lastSeen = character.lastSeen
}