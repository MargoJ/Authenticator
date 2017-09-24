package pl.margoj.authenticator.entities.response

import pl.margoj.authenticator.entities.database.Account

class AccountPresentationEntity(account: Account)
{
    var username = account.username

    var creationDate = account.creationDate

    var role = account.accountRole

    var roleId = account.accountRole!!.ordinal

    var lastSeen = account.lastSeen
}