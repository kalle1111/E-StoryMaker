package com.eStory.service

import com.eStory.model.tag.Tag
import com.eStory.model.tag.TaggedStory
import com.eStory.table.StoryEntity
import com.eStory.table.TagEntity
import com.eStory.table.TaggedStoriesEntity
import com.eStory.table.TagsTable
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class TagService {


    private val tags = listOf<String>(
        "adventure",
        "Tragedy",
        "Comedy",
        "Love",
        "horror",
        "religion",
        "science",
        "kids",
        "nature",
        "political"
    )

    fun getAllTags(): List<Tag> = transaction { TagEntity.all().map { it.toDTO() } }

    private fun insertTag(name: String): Tag = transaction { TagEntity.new { this.name = name }.toDTO() }

    fun insertAllTags() {
        transaction { TagsTable.deleteAll() }
        tags.forEach { insertTag(it) }
    }

    fun mapStoryToTag(storyId: String, tagName: String): TaggedStory = transaction {
        TaggedStoriesEntity.new {
            this.tagEntity = TagEntity.find { TagsTable.name eq tagName }.first()
            this.storyEntity = StoryEntity.findById(UUID.fromString(storyId))!!
        }.toDTO()
    }

    fun getAllTaggedStories(): List<TaggedStory> = transaction { TaggedStoriesEntity.all().map { it.toDTO() } }

    fun findStoriesByTag(tagName: String): List<TaggedStory> = getAllTaggedStories().filter { it.tag.name == tagName }
    fun getAllTagsToStory(storyId: String): List<Tag> = getAllTaggedStories().filter { it.story.uuid == storyId }.map { it.tag }


}