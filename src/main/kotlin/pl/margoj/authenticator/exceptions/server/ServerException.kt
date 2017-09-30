package pl.margoj.authenticator.exceptions.server

import pl.margoj.authenticator.exceptions.ApplicationException

abstract class ServerException(message: String) : ApplicationException(message)