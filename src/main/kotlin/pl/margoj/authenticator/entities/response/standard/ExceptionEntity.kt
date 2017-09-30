package pl.margoj.authenticator.entities.response.standard

import com.fasterxml.jackson.annotation.JsonAnyGetter

data class ExceptionEntity(
        val message: String,
        var exceptionClassSimple: String,
        var exceptionClassFull: String,
        var exceptionMessage: String?,
        var exceptionLocalizedMessage: String?
)
{
    val error = true

    val properties: MutableMap<String, Any?> = hashMapOf()
        @JsonAnyGetter get
}