package com.eStory.service

import com.eStory.model.story.Story
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

    // list of tags for Stories
    private val tags = listOf(
        "Adventure",
        "Tragedy",
        "Comedy",
        "Love",
        "Horror",
        "Religion",
        "Science",
        "Kids",
        "Nature",
        "Political"
    )


    fun getByUUID(uuid: String): Tag? = transaction { TagEntity.findById(UUID.fromString(uuid))?.toDTO() }

    fun getAllTags(): List<Tag> = transaction { TagEntity.all().map { it.toDTO() } }

    private fun insertTag(name: String): Tag = transaction { TagEntity.new { this.name = name }.toDTO() }

    // inserting all tags into the database
    fun insertAllTags() {
        if (getAllTags().size != tags.size) {   // insert all tags again, when the database is empty
            transaction { TagsTable.deleteAll() }
            tags.forEach { insertTag(it) }
        }
    }

    // mapping a story to tags. A story can be mapped to more than one tag
    fun mapStoryToTag(storyId: String, listOfTags: List<String>): List<TaggedStory> {
        val taggedStories = mutableListOf<TaggedStory>()
        transaction {
            listOfTags.forEach { tagName ->
                val taggedStory = TaggedStoriesEntity.new {
                    this.tagEntity = TagEntity.find { TagsTable.name eq tagName }.first()
                    this.storyEntity = StoryEntity.findById(UUID.fromString(storyId))!!
                }.toDTO()
                taggedStories.add(taggedStory)
            }
        }
        return taggedStories
    }

    fun getAllTaggedStories(): List<TaggedStory> = transaction { TaggedStoriesEntity.all().map { it.toDTO() } }


    fun getAllTagsToStory(storyId: String): List<Tag> =
        getAllTaggedStories().filter { it.story.uuid == storyId }.map { it.tag }

    // client can search for stories by tags, parameter is list of tags
    fun getStoriesByTags(listOfTags: List<String>): List<Story> {
        val allTaggedStories = getAllTaggedStories()
        val AllList: MutableList<List<TaggedStory>> = mutableListOf()
        var filteredLists: MutableList<TaggedStory> = mutableListOf()

        listOfTags.forEach { tagName ->
            AllList.add(allTaggedStories.filter { it.tag.name == tagName })
        }

        AllList[0].forEach { taggedStory ->
            val story = taggedStory.story.uuid
            if (AllList.all { list -> list.any { it.story.uuid == story } }) {
                filteredLists.add(taggedStory)
            }
        }
        // remove duplicated elements and return list of stories
        return filteredLists.distinctBy { it.story.uuid }.map { it.story }
    }

    // client can search for stories by tags and title of stories, parameter is list of tags and title
    fun getStoriesByTagsAndTitle(listOfTags: List<String>, subTitle: String): List<Story> =
        getStoriesByTags(listOfTags).filter { it.storyTitle.contains(subTitle) }

    fun getLastUpdatesByTags(listOfTags: List<String>): List<Pair<String, Long>> =
        getStoriesByTags(listOfTags).map { Pair(it.uuid, it.lastUpdate) }

    fun getLastUpdatesByTagsAndTitle(listOfTags: List<String>, subTitle: String): List<Pair<String, Long>> =
        getStoriesByTagsAndTitle(listOfTags, subTitle).map { Pair(it.uuid, it.lastUpdate) }
}