package pl.margoj.authenticator.exceptions

import org.springframework.http.HttpStatus

class UnknownErrorException(val status: Int) : ApplicationException("An unknown error has occured (code: ${status})")
{
    override fun pouplateEntity(map: MutableMap<String, Any?>)
    {
        map.put("status", this.status)
        map.put("status_code", HttpStatus.valueOf(this.status))
    }

    override val statusCode: HttpStatus = HttpStatus.valueOf(status) ?: HttpStatus.INTERNAL_SERVER_ERROR
}