package pl.margoj.authenticator.entities.database

import java.util.Date
import javax.persistence.*

@Entity
@Table(name = "game_sessions")
class GameSession @JvmOverloads constructor
(
        @Column(unique = true, nullable = false, columnDefinition = "CHAR(255)")
        val token: String? = null,

        @ManyToOne(cascade = arrayOf(CascadeType.ALL))
        val character: Character? = null,

        @Column(unique = false, nullable = false)
        var activeSince: Date? = null,

        @Column(unique = false, nullable = false, columnDefinition = "VARCHAR(45)")
        var requestingIp: String? = null,

        @Column(unique = false, nullable = true)
        var invalidated: Boolean? = null,

        @ManyToOne(cascade = arrayOf(CascadeType.ALL))
        val currentSession: AccountSession? = null
) : BaseDatabaseEntity()