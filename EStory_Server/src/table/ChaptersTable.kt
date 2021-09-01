package com.eStory.table

import com.eStory.model.chapter.Chapter

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object ChaptersTable : UUIDTable() {
    val storyId = reference("storyID", StoriesTable.id)
    val title = text("title")
    val content = text("content")
    val index = integer("index")
    val createTime = long("createTime")
    val lastUpdate = long("lastUpdate")
}

class ChapterEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<ChapterEntity>(ChaptersTable)

    var storyEntity by StoryEntity referencedOn ChaptersTable.storyId
    var title by ChaptersTable.title
    var content by ChaptersTable.content
    var index by ChaptersTable.index
    var createTime by ChaptersTable.createTime
    var lastUpdate by ChaptersTable.lastUpdate

    fun toDTO(): Chapter =
        Chapter(
            this.id.toString(),
            this.storyEntity.id.toString(),
            this.title,
            this.content,
            this.index,
            this.createTime,
            this.lastUpdate
        )

}