package pl.margoj.authenticator.entities.response.specific

import pl.margoj.authenticator.entities.database.Account
import pl.margoj.authenticator.entities.response.presentations.AccountPresentationEntity
import pl.margoj.authenticator.entities.response.standard.OkResponse

class ValidationEntity(account: Account) : OkResponse()
{
    val account = AccountPresentationEntity(account)
}