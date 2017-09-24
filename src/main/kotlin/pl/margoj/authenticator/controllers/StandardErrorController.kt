package pl.margoj.authenticator.controllers

import org.springframework.boot.autoconfigure.web.ErrorController
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.margoj.authenticator.entities.response.ExceptionEntity
import pl.margoj.authenticator.exceptions.ApplicationException
import pl.margoj.authenticator.exceptions.MethodNotSupportedException
import pl.margoj.authenticator.exceptions.ResourceNotFoundException
import pl.margoj.authenticator.exceptions.UnknownErrorException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletRequestWrapper
import javax.servlet.http.HttpServletResponse

@ControllerAdvice
@RestController
class StandardErrorController : ErrorController
{
    companion object
    {
        const val PATH = "/error"
    }

    @RequestMapping(PATH)
    fun error(request: HttpServletRequest, response: HttpServletResponse): Nothing
    {
        when (response.status)
        {
            404 ->
            {
                throw ResourceNotFoundException(((request as? HttpServletRequestWrapper)?.request as? HttpServletRequest)?.requestURI ?: "unknown")
            }
            else -> throw UnknownErrorException(response.status)
        }
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun onMethodNotSupportedException(exception: HttpRequestMethodNotSupportedException): ResponseEntity<ExceptionEntity>
    {
        return this.onException(MethodNotSupportedException(exception.method, exception.supportedMethods))
    }

    @ExceptionHandler(Exception::class)
    fun onException(exception: Exception): ResponseEntity<ExceptionEntity>
    {
        val entity = ExceptionEntity(
                message = "An exception has occured while executing your request (${exception.javaClass.simpleName})",
                exceptionClassSimple = exception.javaClass.simpleName,
                exceptionClassFull = exception.javaClass.name,
                exceptionMessage = exception.message,
                exceptionLocalizedMessage = exception.localizedMessage
        )

        var status = HttpStatus.INTERNAL_SERVER_ERROR

        if (exception is ApplicationException)
        {
            val properties = entity.properties
            properties.put("application_error", true);
            exception.pouplateEntity(properties)
            status = exception.statusCode
        }
        else
        {
            exception.printStackTrace()
        }

        return ResponseEntity(entity, status)
    }

    override fun getErrorPath(): String = PATH
}