package pl.margoj.authenticator.entities.database

import java.util.Objects
import java.util.UUID
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class BaseDatabaseEntity
{
    @Id
    @GeneratedValue
    var id: Long? = null

    @Column(nullable = false, unique = true, columnDefinition = "CHAR(32)")
    val uuid = this.generateUniqueId()

    private fun generateUniqueId(): String
    {
        return UUID.randomUUID().toString().replace("-", "")
    }

    override final fun equals(other: Any?): Boolean
    {
        if (other == null)
        {
            return false
        }
        if (other === this)
        {
            return true
        }
        if (other.javaClass != this.javaClass)
        {
           return  false
        }

        return this.uuid == (other as BaseDatabaseEntity).uuid
    }

    override final fun hashCode(): Int
    {
        return Objects.hash(this.uuid)
    }

    override final fun toString(): String
    {
        return "${this.javaClass.name}#${this.uuid}"
    }
}