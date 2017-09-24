package pl.margoj.authenticator.exceptions

import org.springframework.http.HttpStatus

class MethodNotSupportedException(val method: String, val supportedMethods: Array<String>) : ApplicationException("Method '$method' is not supported")
{
    override fun getLocalizedMessage(): String = "Metoda '$method' nie jest wspierana"

    override fun pouplateEntity(map: MutableMap<String, Any?>)
    {
        map.put("method", this.method)
        map.put("supported_methods", this.supportedMethods)
    }

    override val statusCode: HttpStatus = HttpStatus.METHOD_NOT_ALLOWED
}