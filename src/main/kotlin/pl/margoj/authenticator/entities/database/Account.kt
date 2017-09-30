package pl.margoj.authenticator.entities.database

import java.util.Date
import javax.persistence.*

@Entity
@Table(name = "accounts")
data class Account @JvmOverloads constructor
(
        @Column(unique = true, nullable = false, columnDefinition = "VARCHAR(32)")
        var username: String? = null,

        @Column(unique = false, nullable = false, columnDefinition = "CHAR(60)")
        var password: String? = null,

        @OneToOne(cascade = arrayOf(CascadeType.ALL))
        var currentSession: AccountSession? = null,

        @OneToOne(cascade = arrayOf(CascadeType.ALL))
        var currentGameSession: GameSession? = null,

        @Enumerated(EnumType.ORDINAL)
        @Column(unique = false, nullable = true)
        var accountRole: AccountRole? = null,

        @Column(unique = false, nullable = false)
        var creationDate: Date? = null,

        @Column(unique = false, nullable = true)
        var lastSeen: Date? = null,

        @Column(unique = false, nullable = false, columnDefinition = "VARCHAR(45)")
        var creatingIp: String? = null,

        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "ownerAccount")
        var characters: MutableSet<Character>? = null
) : BaseDatabaseEntity()

enum class AccountRole
{
    USER,
    GAME_MASTER,
    SUPER_GAME_MASTER,
    ADMINISTRATOR;

    operator fun contains(role: AccountRole): Boolean
    {
        return this.ordinal >= role.ordinal
    }
}