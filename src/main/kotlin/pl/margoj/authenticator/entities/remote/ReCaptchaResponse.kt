package pl.margoj.authenticator.entities.remote

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Date

data class ReCaptchaResponse
(
        @JsonProperty("success")
        var success: Boolean,
        @JsonProperty("challenge_ts")
        var challengeTs: Date?,
        @JsonProperty("hostname")
        var hostname: String?,
        @JsonProperty("error-codes")
        var errorCodes: Collection<String>?
)