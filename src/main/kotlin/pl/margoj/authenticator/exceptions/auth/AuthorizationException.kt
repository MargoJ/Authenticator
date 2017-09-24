package pl.margoj.authenticator.exceptions.auth

import pl.margoj.authenticator.exceptions.ApplicationException

abstract class AuthorizationException(message: String) : ApplicationException(message)