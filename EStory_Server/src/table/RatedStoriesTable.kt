package com.eStory.table

import com.eStory.model.friendship.Friendship
import com.eStory.model.story.RatedStory
import com.eStory.models.story.Story
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.max
import java.util.*

object RatedStoriesTable : UUIDTable() {
    val userName = varchar("userName", 512).references(UsersTable.userName)
    val storyId =  reference("storyID", StoriesTable.id)
    val ratingValue = integer("ratingValue")
}

class RatedStoryEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<RatedStoryEntity>(RatedStoriesTable)

    var userEntity by UserEntity referencedOn RatedStoriesTable.userName
    var storyEntity by StoryEntity referencedOn RatedStoriesTable.storyId
    var ratingValue by RatedStoriesTable.ratingValue

    fun toDTO(): RatedStory = RatedStory(this.id.toString(), userEntity.toDTO(), storyEntity.toDTO(), ratingValue)

}