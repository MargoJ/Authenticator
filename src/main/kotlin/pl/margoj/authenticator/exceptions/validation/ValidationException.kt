package pl.margoj.authenticator.exceptions.validation

import pl.margoj.authenticator.exceptions.ApplicationException

abstract class ValidationException(message: String) : ApplicationException(message)