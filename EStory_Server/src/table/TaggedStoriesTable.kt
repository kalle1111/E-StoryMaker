package com.eStory.table

import com.eStory.model.tag.TaggedStory
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object TaggedStoriesTable : UUIDTable() {
    val tagId = reference("tagId", TagsTable.id)
    val storyId = reference("storyID", StoriesTable.id)

}

class TaggedStoriesEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<TaggedStoriesEntity>(TaggedStoriesTable)

    var tagEntity by TagEntity referencedOn TaggedStoriesTable.tagId
    var storyEntity by StoryEntity referencedOn TaggedStoriesTable.storyId


    fun toDTO(): TaggedStory = TaggedStory(this.id.toString(), tagEntity.toDTO(), storyEntity.toDTO())


}