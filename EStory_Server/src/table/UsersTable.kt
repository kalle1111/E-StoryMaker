package com.eStory.table

import com.eStory.model.user.User
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object UsersTable : UUIDTable() {

    val userName = varchar("userName", 512)
    val description = text("description")
    val hashPassword = varchar("hashPassword", 512)
    val image = binary("image", Int.MAX_VALUE)
    val lastUpdate = long("lastUpdate")
    override val primaryKey: PrimaryKey = PrimaryKey(userName)
}

class UserEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<UserEntity>(UsersTable)

    var userName by UsersTable.userName
    var description by UsersTable.description
    var hashPassword by UsersTable.hashPassword
    var image by UsersTable.image
    var lastUpdate by UsersTable.lastUpdate

    fun toDTO(): User =
        User(this.id.toString(), userName, description, hashPassword, image, lastUpdate)
}