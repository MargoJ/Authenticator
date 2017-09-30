package pl.margoj.authenticator.entities.database

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "servers")
class Server @JvmOverloads constructor
(
        @Column(unique = true, nullable = false, columnDefinition = "VARCHAR(32)")
        var serverId: String? = null,

        @Column(unique = false, nullable = true, columnDefinition = "VARCHAR(32)")
        var serverName: String? = null,

        @Column(unique = false, nullable = false, columnDefinition = "VARCHAR(100)")
        var serverURL: String? = null,

        @Column(unique = false, nullable = false)
        var stats: Int? = null,

        @Column(unique = false, nullable = false)
        var enabled: Boolean? = null,

        @Column(unique = false, nullable = false)
        var open: Boolean? = null,

        @Column(unique = true, nullable = false, columnDefinition = "CHAR(255)")
        var secretKey: String? = null
) : BaseDatabaseEntity()