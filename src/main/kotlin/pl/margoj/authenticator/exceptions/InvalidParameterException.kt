package pl.margoj.authenticator.exceptions

import org.springframework.http.HttpStatus

class InvalidParameterException(val parameter: String, error: String, val errorLocalized: String) : ApplicationException(error)
{
    override fun populateEntity(map: MutableMap<String, Any?>)
    {
        map.put("parameter", this.parameter)
    }

    override fun getLocalizedMessage(): String = this.errorLocalized

    override val statusCode: HttpStatus = HttpStatus.BAD_REQUEST
}