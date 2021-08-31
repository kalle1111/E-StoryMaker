package com.eStory.service

import com.eStory.model.chapter.Chapter
import com.eStory.table.ChapterEntity
import com.eStory.table.ChaptersTable
import com.eStory.table.StoriesTable
import com.eStory.table.StoryEntity
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.sql.Time
import java.util.*

/**
 * fun getByUUID(uuid: String): Tag? = transaction { TagEntity.findById(UUID.fromString(uuid))?.toDTO() }


fun getAllTags(): List<Tag> = transaction { TagEntity.all().map { it.toDTO() } }

private fun insertTag(name: String): Tag = transaction { TagEntity.new { this.name = name }.toDTO() }

// inserting all tags into the database
fun insertAllTags() {
if (getAllTags().size != tags.size) {
transaction { TagsTable.deleteAll() }
tags.forEach { insertTag(it) }
}
}

// mapping a story to a tag. A story can be mapped to more than one tag
fun mapStoryToTag(storyId: String, tagName: String): TaggedStory = transaction {
TaggedStoriesEntity.new {
this.tagEntity = TagEntity.find { TagsTable.name eq tagName }.first()
this.storyEntity = StoryEntity.findById(UUID.fromString(storyId))!!
}.toDTO()
}


fun getAllTaggedStories(): List<TaggedStory> = transaction { TaggedStoriesEntity.all().map { it.toDTO() } }

fun findStoriesByTag(tagName: String): List<TaggedStory> = getAllTaggedStories().filter { it.tag.name == tagName }
fun getAllTagsToStory(storyId: String): List<Tag> =
getAllTaggedStories().filter { it.story.uuid == storyId }.map { it.tag }

// client can search for stories by tags, parameter is list of tags
fun getStoriesByTags(listOfTags: List<String>): List<Story> {

val temporarilyList = mutableListOf<TaggedStory>()

val allTaggedStories = getAllTaggedStories()
listOfTags.forEach { tagName ->
val filteredList =
allTaggedStories.filter { it.tag.name == tagName } // the list can have duplicated elements
temporarilyList.addAll(filteredList)
}

// remove duplicated elements and return list of stories
return temporarilyList.distinctBy { it.story.uuid }.map { it.story }
}

// client can search for stories by tags and title of stories, parameter is list of tags and title
fun getStoriesByTagsAndTitle(listOfTags: List<String>, subTitle: String): List<Story> =
getStoriesByTags(listOfTags).filter { it.storyTitle.contains(subTitle) }

transaction {
StoriesTable.update(
where = {
StoriesTable.id.eq(UUID.fromString(uuid))
}
) { st ->
if (storyTitle != null) {
st[this.storyTitle] = storyTitle
}

if (description != null) {
st[this.description] = description
}
/* if (storyChapters != null) {
st[this.storyChapters] = storyChapters
}*/
st[this.cover] = cover

st[this.lastUpdate] = Date().time
}
}
 *
 *
 */
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
        index: Int,
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