package com.eStory.table

import com.eStory.model.story.Story
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object StoriesTable : UUIDTable() {
    val userName = varchar("userName", 512).references(UsersTable.userName)
    val storyTitle = text("storyTitle")
    val description = text("description")
    val createTime = varchar("createTime", 255)
    val storyChapters = text("story")
    val averageRating = double("averageRating")

    //   val img = binary("image", Int.MAX_VALUE)
}

class StoryEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<StoryEntity>(StoriesTable)

    var userEntity by UserEntity referencedOn StoriesTable.userName
    var storyTitle by StoriesTable.storyTitle
    var description by StoriesTable.description
    var createTime by StoriesTable.createTime
    var storyChapters by StoriesTable.storyChapters
    var averageRating by StoriesTable.averageRating
    fun toDTO(): Story =
        Story(this.id.toString(), userEntity.toDTO(), storyTitle, description, createTime, storyChapters, averageRating)

}
