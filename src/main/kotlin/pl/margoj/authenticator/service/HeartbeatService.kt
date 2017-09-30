package pl.margoj.authenticator.service

import pl.margoj.authenticator.entities.request.SessionData
import pl.margoj.authenticator.entities.response.specific.HeartbeatEntity

interface HeartbeatService
{
    fun performServerHeartbeat(sessions: Map<Long, SessionData>): HeartbeatEntity
}