package pl.margoj.authenticator.entities.response

import pl.margoj.authenticator.entities.database.Account

class ValidationEntity(account: Account) : OkResponse()
{
    val account = AccountPresentationEntity(account)

    val character: Any? = null // TODO
}