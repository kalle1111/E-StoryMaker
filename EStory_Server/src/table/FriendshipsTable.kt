package com.eStory.table

import com.eStory.model.friendship.Friendship
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*


object FriendshipsTable : UUIDTable() {
    val requesterUserName = varchar("userName", 512).references(UsersTable.userName)
    val friendName = varchar("friendName", 512).references(UsersTable.userName)
    val isAccepted = bool("isAccepted")
    val requestTime = varchar("createTime", 255)
}

class FriendshipEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<FriendshipEntity>(FriendshipsTable)

    var requesterUserEntity by UserEntity referencedOn FriendshipsTable.requesterUserName
    var friendEntity by UserEntity referencedOn FriendshipsTable.friendName
    var isAccepted by FriendshipsTable.isAccepted
    var requestTime by FriendshipsTable.requestTime

    fun toDTO(): Friendship = Friendship(this.id.toString(), requesterUserEntity.toDTO(), friendEntity.toDTO(),isAccepted ,requestTime)

}