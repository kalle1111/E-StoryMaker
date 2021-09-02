package com.eStory.service

import com.eStory.model.chapter.Chapter
import com.eStory.table.ChapterEntity
import com.eStory.table.ChaptersTable
import com.eStory.table.StoryEntity
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

import java.util.*

class ChapterService {

    fun getByUUID(uuid: String): Chapter? = transaction { ChapterEntity.findById(UUID.fromString(uuid))?.toDTO() }
    fun getChaptersByStoryId(storyId: String): List<Chapter> =
        transaction {
            ChapterEntity.find { ChaptersTable.storyId.eq(UUID.fromString(storyId)) }.map { it.toDTO() }
        }.sortedBy { it.index }

    fun insertChapter(
        storyId: String,
        title: String,
        content: String,
        index: Int
    ): Chapter = transaction {
        ChapterEntity.new {
            this.storyEntity = StoryEntity.findById(UUID.fromString(storyId))!!
            this.title = title
            this.content = content
            this.index = index
            this.createTime = Date().time
            this.lastUpdate = Date().time
        }.toDTO()
    }

    fun getLastUpdateByChapterId(chapterId: String): Pair<String, Long> {
        val chapter = getByUUID(chapterId)!!
        return Pair(chapter.uuid, chapter.lastUpdate)
    }

    fun getChaptersLastUpdatesByStoryId(storyId: String): List<Pair<String, Long>> =
        getChaptersByStoryId(storyId).map { Pair(it.uuid, it.lastUpdate) }

    fun updateChapterById(chapterId: String, title: String?, content: String?) {
        transaction {
            ChaptersTable.update(
                where = {
                    ChaptersTable.id.eq(UUID.fromString(chapterId))
                }
            ) { ch ->
                if (title != null) {
                    ch[this.title] = title
                }
                if (content != null) {
                    ch[this.content] = content
                }
                ch[this.lastUpdate] = Date().time
            }
        }
    }
}