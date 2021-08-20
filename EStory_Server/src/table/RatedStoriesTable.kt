package com.eStory.table

import com.eStory.model.story.RatedStory
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object RatedStoriesTable : UUIDTable() {
    val userName = varchar("userName", 512).references(UsersTable.userName)
    val storyId = reference("storyID", StoriesTable.id)
    val ratingTitle = varchar("ratingTitle", 512)
    val ratingDescription = text("ratingDescription")
    val ratingOverallValue = integer("ratingOverallValue")
    val ratingStyleValue = integer("ratingStyleValue")
    val ratingStoryValue = integer("ratingStoryValue")
    val ratingGrammarValue = integer("ratingGrammarValue")
    val ratingCharacterValue = integer("ratingCharacterValue")
    val lastUpdate = long("lastUpdate")

}

class RatedStoryEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<RatedStoryEntity>(RatedStoriesTable)

    var userEntity by UserEntity referencedOn RatedStoriesTable.userName
    var storyEntity by StoryEntity referencedOn RatedStoriesTable.storyId
    var ratingTitle by RatedStoriesTable.ratingTitle
    var ratingDescription by RatedStoriesTable.ratingDescription
    var ratingOverallValue by RatedStoriesTable.ratingOverallValue
    var ratingStyleValue by RatedStoriesTable.ratingStyleValue
    var ratingStoryValue by RatedStoriesTable.ratingStoryValue
    var ratingGrammarValue by RatedStoriesTable.ratingGrammarValue
    var ratingCharacterValue by RatedStoriesTable.ratingCharacterValue
    var lastUpdate by RatedStoriesTable.lastUpdate

    fun toDTO(): RatedStory = RatedStory(
        this.id.toString(),
        userEntity.toDTO(),
        storyEntity.toDTO(),
        ratingTitle,
        ratingDescription,
        ratingOverallValue,
        ratingStyleValue,
        ratingStoryValue,
        ratingGrammarValue,
        ratingCharacterValue,
        lastUpdate
    )

}