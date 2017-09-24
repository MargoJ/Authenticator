package pl.margoj.authenticator.exceptions

import org.springframework.http.HttpStatus

abstract class ApplicationException : Exception
{
    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(cause: Throwable?) : super(cause)
    constructor(message: String?, cause: Throwable?, enableSuppression: Boolean, writableStackTrace: Boolean) : super(message, cause, enableSuppression, writableStackTrace)

    open fun pouplateEntity(map: MutableMap<String, Any?>)
    {
    }

    open val statusCode: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR
}