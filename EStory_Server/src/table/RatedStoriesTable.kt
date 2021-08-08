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
    val ratingOverallValue = integer("ratingOverallValue")
    val ratingStyleValue = integer("ratingStyleValue")
    val ratingStoryValue = integer("ratingStoryValue")
    val ratingGrammarValue = integer("ratingGrammarValue")
    val ratingCharacterValue = integer("ratingCharacterValue")
}

class RatedStoryEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<RatedStoryEntity>(RatedStoriesTable)

    var userEntity by UserEntity referencedOn RatedStoriesTable.userName
    var storyEntity by StoryEntity referencedOn RatedStoriesTable.storyId
    var ratingOverallValue by RatedStoriesTable.ratingOverallValue
    var ratingStyleValue by RatedStoriesTable.ratingStyleValue
    var ratingStoryValue by RatedStoriesTable.ratingStoryValue
    var ratingGrammarValue by RatedStoriesTable.ratingGrammarValue
    var ratingCharacterValue by RatedStoriesTable.ratingCharacterValue


    fun toDTO(): RatedStory = RatedStory(
        this.id.toString(),
        userEntity.toDTO(),
        storyEntity.toDTO(),
        ratingOverallValue,
        ratingStyleValue,
        ratingStoryValue,
        ratingGrammarValue,
        ratingCharacterValue
    )

}