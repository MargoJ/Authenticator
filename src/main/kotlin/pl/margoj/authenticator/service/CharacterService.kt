package pl.margoj.authenticator.service

import pl.margoj.authenticator.entities.database.Account
import pl.margoj.authenticator.entities.database.Character
import pl.margoj.authenticator.entities.database.Server

interface CharacterService
{
    fun createCharacter(account: Account, server: Server, characterName: String, profession: Char, gender: Char): Character
}