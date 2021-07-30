package com.eStory.table


import com.eStory.model.story.StoryAsFavorite
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object StoriesAsFavoriteTable : UUIDTable() {
    val userName = varchar("userName", 512).references(UsersTable.userName)
    val storyId = reference("storyID", StoriesTable.id)
}

class StoryAsFavoriteEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<StoryAsFavoriteEntity>(StoriesAsFavoriteTable)

    var userEntity by UserEntity referencedOn StoriesAsFavoriteTable.userName
    var storyEntity by StoryEntity referencedOn StoriesAsFavoriteTable.storyId


    fun toDTO(): StoryAsFavorite =
        StoryAsFavorite(this.id.toString(), userEntity.toDTO(), storyEntity.toDTO())

 }