package pl.margoj.authenticator.exceptions

import org.springframework.http.HttpStatus
import pl.margoj.authenticator.entities.database.AccountRole

class InsufficientPermissionsException(val current: AccountRole, val required: AccountRole) : ApplicationException("You don not have permissions for that!")
{
    override fun getLocalizedMessage(): String = "Nie masz do tego uprawnień!"

    override val statusCode: HttpStatus = HttpStatus.FORBIDDEN

    override fun populateEntity(map: MutableMap<String, Any?>)
    {
        map.put("current_role", current)
        map.put("required_role", required)
        map.put("current_role_id", current.ordinal)
        map.put("required_role_id", required.ordinal)
    }
}