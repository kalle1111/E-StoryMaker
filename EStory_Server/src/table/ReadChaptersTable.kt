package com.eStory.table

import com.eStory.model.readChapter.ReadChapter
import com.eStory.table.RatedStoriesTable.references
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*


object ReadChaptersTable : UUIDTable() {
    val userName = varchar("userName", 512).references(UsersTable.userName)
    val storyId = reference("storyID", StoriesTable.id)
    val chapterNumber = integer("chapterNumber")
}


class ReadChapterEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<ReadChapterEntity>(ReadChaptersTable)

    var userEntity by UserEntity referencedOn ReadChaptersTable.userName
    var storyEntity by StoryEntity referencedOn ReadChaptersTable.storyId

    var chapterNumber by ReadChaptersTable.chapterNumber


    fun toDTO(): ReadChapter =
        ReadChapter(this.id.toString(), this.storyEntity.toDTO(), this.userEntity.toDTO(), this.chapterNumber)


}

