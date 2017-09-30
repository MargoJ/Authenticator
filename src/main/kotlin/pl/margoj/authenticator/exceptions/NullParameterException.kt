package pl.margoj.authenticator.exceptions

import org.springframework.http.HttpStatus

class NullParameterException(val parameters: Collection<String>) : ApplicationException(parameters.joinToString(", "))
{
    override fun populateEntity(map: MutableMap<String, Any?>)
    {
        map.put("parameters", this.parameters)
    }

    override val statusCode: HttpStatus = HttpStatus.BAD_REQUEST
}