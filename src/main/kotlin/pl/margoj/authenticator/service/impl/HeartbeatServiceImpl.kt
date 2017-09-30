package pl.margoj.authenticator.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.margoj.authenticator.entities.database.Account
import pl.margoj.authenticator.entities.request.SessionData
import pl.margoj.authenticator.entities.response.specific.HeartbeatEntity
import pl.margoj.authenticator.repositories.AccountRepository
import pl.margoj.authenticator.service.GameService
import pl.margoj.authenticator.service.HeartbeatService
import java.util.Date
import java.util.concurrent.TimeUnit

@Service
class HeartbeatServiceImpl @Autowired constructor
(
        val gameService: GameService,
        val accountsRepository: AccountRepository
) : HeartbeatService
{

    override fun performServerHeartbeat(sessions: Map<Long, SessionData>): HeartbeatEntity
    {
        val databaseSessions = this.gameService.bulkSessionGet(sessions.keys)
        val iterator = databaseSessions.iterator()

        val toProcess = ArrayList(sessions.keys)
        val accountsToSave = ArrayList<Account>()
        val invalidSessions = HashSet<Long>()

        while (iterator.hasNext())
        {
            val currentSession = iterator.next()
            val sessionObject = sessions[currentSession.id]!!
            var needsSaving = false

            if (!this.gameService.checkIfSessionStillIsValid(currentSession))
            {
                invalidSessions.add(currentSession.id!!)
            }
            else
            {
                currentSession.currentSession!!.account!!.currentSession!!.activeUntil = Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(2L)) // TODO
                needsSaving = true
            }

            toProcess.remove(currentSession.id)

            if (sessionObject.level != null && currentSession.character!!.level != sessionObject.level)
            {
                currentSession.character.level = sessionObject.level
                needsSaving = true
            }

            if (needsSaving)
            {
                accountsToSave.add(currentSession.character!!.ownerAccount!!)
            }
        }

        if (accountsToSave.isNotEmpty())
        {
            this.accountsRepository.save(accountsToSave)
        }

        invalidSessions.addAll(toProcess) // add unprocessed

        return HeartbeatEntity(invalidSessions.toTypedArray())
    }
}