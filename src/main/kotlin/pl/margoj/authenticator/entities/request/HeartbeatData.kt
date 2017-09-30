package pl.margoj.authenticator.entities.request

import pl.margoj.authenticator.util.NonNullValidator

data class HeartbeatData @JvmOverloads constructor
(
        var secret: String? = null,

        var sessions: Map<Long, SessionData>? = null
)
{
    fun validate()
    {
        NonNullValidator().let {
            it.check(this.secret, "secret")
            it.check(this.sessions, "sessions")
            it.validate()
        }
    }
}

data class SessionData @JvmOverloads constructor
(
        var level: Int? = null
)