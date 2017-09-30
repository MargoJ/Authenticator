package pl.margoj.authenticator.entities.response.presentations

import pl.margoj.authenticator.entities.database.Account

class AccountPresentationEntity(account: Account)
{
    var username = account.username

    var uuid = account.uuid

    var creationDate = account.creationDate

    var role = account.accountRole

    var roleId = account.accountRole!!.ordinal

    var lastSeen = account.lastSeen

    var characters = account.characters!!.map { CharacterPresentationEntity(it) }
}