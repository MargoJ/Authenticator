package pl.margoj.authenticator.exceptions

import org.springframework.http.HttpStatus

class UnknownErrorException(val status: Int) : ApplicationException("An unknown error has occurred (code: ${status})")
{
    override fun populateEntity(map: MutableMap<String, Any?>)
    {
        map.put("status", this.status)
        map.put("status_code", HttpStatus.valueOf(this.status))
    }

    override val statusCode: HttpStatus = HttpStatus.valueOf(status) ?: HttpStatus.INTERNAL_SERVER_ERROR
}