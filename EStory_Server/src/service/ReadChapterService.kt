package com.eStory.service

import com.eStory.model.readChapter.ReadChapter
import com.eStory.table.*
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.util.*

class ReadChapterService {
    fun getAll(): List<ReadChapter> = transaction { ReadChapterEntity.all().map { it.toDTO() } }

    fun getByUUID(uuid: String): ReadChapter? =
        transaction { ReadChapterEntity.findById(UUID.fromString(uuid))?.toDTO() }

    // updating the read chapter of stories
    fun updateReadChapter(userName: String, storyId: String, chapterNumber: Int): ReadChapter? = transaction {
        val readChapterEntity =
            ReadChapterEntity.find { ReadChaptersTable.storyId.eq(UUID.fromString(storyId)) }.singleOrNull()
        if (readChapterEntity == null) {
            return@transaction ReadChapterEntity.new {
                this.userEntity = UserEntity.find { UsersTable.userName eq userName }.first()
                this.storyEntity = StoryEntity[UUID.fromString(storyId)]
                this.chapterNumber = chapterNumber
            }.toDTO()
        } else {

            ReadChaptersTable.update(
                where = {
                    RatedStoriesTable.storyId.eq(UUID.fromString(storyId)) and
                            RatedStoriesTable.userName.eq(userName)
                }
            ) { rc ->

                rc[this.chapterNumber] = chapterNumber
            }

            return@transaction ReadChapterEntity.find { ReadChaptersTable.storyId.eq(UUID.fromString(storyId)) }
                .singleOrNull()?.toDTO()
        }
    }

}