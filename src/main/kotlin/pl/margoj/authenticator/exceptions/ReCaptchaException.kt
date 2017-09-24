package pl.margoj.authenticator.exceptions

import org.springframework.http.HttpStatus

class ReCaptchaException(val rawErrors: Collection<String>?) : ApplicationException()
{
    override val message: String
    private val localizedMessage: String

    override fun getLocalizedMessage(): String = this.localizedMessage

    init
    {
        if (rawErrors == null)
        {
            message = "Captcha not solved"
            localizedMessage = "Niepoprawnie wykonana captcha!"
        }
        else
        {
            val messageBuilder = StringBuilder("Following errors have occurred while sending a captcha request: \n")
            val messageBuilderLocalized = StringBuilder("Następujące błędy wystąpiły podczas wykonywania zapytania: \n")

            rawErrors.forEach {
                val (error, errorLocalized) = errorCodes[it] ?: Pair(it, it)

                messageBuilder.append(error).append("\n")
                messageBuilderLocalized.append(errorLocalized).append("\n")
            }

            this.message = messageBuilder.toString()
            this.localizedMessage = messageBuilderLocalized.toString()
        }
    }

    override fun pouplateEntity(map: MutableMap<String, Any?>)
    {
        if (this.rawErrors != null)
        {
            map.put("raw_errors", this.rawErrors)
            map.put("user_fault", this.rawErrors.all { userFaults.contains(it) })
        }
        else
        {
            map.put("user_fault", true)
        }
    }

    private companion object
    {
        var errorCodes = HashMap<String, Pair<String, String>>()
        var userFaults = arrayOf("missing-input-response", "invalid-input-response", "timeout-or-duplicate")

        init
        {
            errorCodes.put("missing-input-secret", Pair("The secret parameter is missing", "Paremeter 'secret' nie został znaleziony"))
            errorCodes.put("invalid-input-secret", Pair("The secret parameter is invalid or malformed", "Paremeter 'secret' jest niepoprawny"))
            errorCodes.put("missing-input-response", Pair("The response  parameter is missing", "Parameter 'response' nie został znaleziony"))
            errorCodes.put("invalid-input-response", Pair("The response  parameter is missing", "Parameter 'response' jest niepoprawny."))
            errorCodes.put("bad-request", Pair("The secret parameter is missing", "Paremeter 'secret' nie został znaleziony"))
            errorCodes.put("timeout-or-duplicate", Pair("This challenge has timed out or is duplicated", "Ta captcha jest przedawniona, lub została już wykorzystana"))
        }
    }

    override val statusCode: HttpStatus = HttpStatus.FORBIDDEN
}