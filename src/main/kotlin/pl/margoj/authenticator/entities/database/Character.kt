package pl.margoj.authenticator.entities.database

import java.util.Date
import javax.persistence.*

@Entity
@Table(name = "characters")
data class Character @JvmOverloads constructor(
        @Column(unique = false, nullable = false, columnDefinition = "VARCHAR(32)")
        var name: String? = null,

        @Column(unique = false, nullable = false)
        var profession: Char? = null,

        @Column(unique = false, nullable = false)
        var gender: Char? = null,

        @Column(unique = false, nullable = false)
        var level: Int? = null,

        @ManyToOne(cascade = arrayOf(CascadeType.ALL))
        val server: Server? = null,

        @Column(unique = false, nullable = false)
        var creationDate: Date? = null,

        @Column(unique = false, nullable = true)
        var lastSeen: Date? = null,

        @ManyToOne(cascade = arrayOf(CascadeType.ALL))
        @JoinColumn(name = "owner_id")
        var ownerAccount: Account? = null,

        @OneToOne(cascade = arrayOf(CascadeType.ALL))
        var currentSession: GameSession? = null
) : BaseDatabaseEntity()