package pl.margoj.authenticator.entities.response.presentations

import pl.margoj.authenticator.entities.database.Character

class CharacterServerPresentationEntity(character: Character)
{
    val accountId = character.ownerAccount!!.id

    val id = character.id

    val name = character.name

    val profession = character.profession

    val gender = character.gender

    val level = character.level

    val role = character.ownerAccount!!.accountRole

    val roleId = character.ownerAccount!!.accountRole!!.ordinal
}