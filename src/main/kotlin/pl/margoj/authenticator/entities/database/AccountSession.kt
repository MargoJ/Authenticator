package pl.margoj.authenticator.entities.database

import java.util.Date
import javax.persistence.*

@Entity
@Table(name = "account_sessions")
class AccountSession @JvmOverloads constructor
(
        @Column(unique = true, nullable = false, columnDefinition = "CHAR(255)")
        val token: String? = null,

        @ManyToOne(cascade = arrayOf(CascadeType.ALL))
        val account: Account? = null,

        @Column(unique = false, nullable = false)
        var activeUntil: Date? = null,

        @Column(unique = false, nullable = false)
        var activeSince: Date? = null,

        @Column(unique = false, nullable = false, columnDefinition = "VARCHAR(45)")
        var requestingIp: String? = null,

        @Column(unique = false, nullable = true)
        var invalidated: Boolean? = null
) : BaseDatabaseEntity()