package pl.margoj.authenticator.exceptions

import org.springframework.http.HttpStatus

class ResourceNotFoundException(val resource: String) : ApplicationException("Resource '$resource' couldn't be find")
{
    override fun pouplateEntity(map: MutableMap<String, Any?>)
    {
        map.put("resource", this.resource)
    }

    override val statusCode: HttpStatus = HttpStatus.NOT_FOUND
}