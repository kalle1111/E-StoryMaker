package com.eStory.service

import com.eStory.model.story.RatedStory
import com.eStory.model.story.StoryAsFavorite
import com.eStory.models.story.Story
import com.eStory.table.*
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.text.SimpleDateFormat
import java.util.*

class StoryService {

    fun getAll(): List<Story> = transaction { StoryEntity.all().map { it.toDTO() } }

    fun getByUUID(uuid: String): Story? = transaction { StoryEntity.findById(UUID.fromString(uuid))?.toDTO() }

    fun getAllByUserName(userName: String): List<Story> = getAll().filter { it.user.userName == userName }

    fun getStoryByTitle(title: String): List<Story> = getAll().filter { it.storyTitle == title }

    //private val users = listOf(testUser).associateBy(User::id)
    fun deleteByUUID(uuid: String): Story? {
        val story = getByUUID(uuid)
        transaction {
            StoryEntity.findById(UUID.fromString(uuid))?.delete()

        }
        return story
    }


    fun insert(
        userName: String,
        storyTitle: String,
        description: String
    ): Story =
        transaction {
            StoryEntity.new {
                this.userEntity = UserEntity.find { UsersTable.userName eq userName }.first()
                this.description = description
                this.storyTitle = storyTitle
                this.createTime = getDate()
            }.toDTO()
        }

    fun updateByUUID(uuid: String, storyTitle: String? = null, description: String? = null) {
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

            }
        }
    }

    /****************RATING_STORY****************/
    fun rateStory(
        userName: String, storyId: String,
        ratingOverallValue: Int,
        ratingStyleValue: Int,
        ratingStoryValue: Int,
        ratingGrammarValue: Int,
        ratingCharacterValue: Int
    ): RatedStory =
        transaction {
            RatedStoryEntity.new {
                this.userEntity = UserEntity.find { UsersTable.userName eq userName }.first()
                this.storyEntity = StoryEntity[UUID.fromString(storyId)]
                this.ratingOverallValue = ratingOverallValue
                this.ratingStyleValue = ratingStyleValue
                this.ratingStoryValue = ratingStoryValue
                this.ratingGrammarValue = ratingGrammarValue
                this.ratingCharacterValue = ratingCharacterValue
            }.toDTO()
        }

    fun updateRatedStory(
        userName: String, storyId: String,
        ratingOverallValue: Int?,
        ratingStyleValue: Int?,
        ratingStoryValue: Int?,
        ratingGrammarValue: Int?,
        ratingCharacterValue: Int?
    ) {

        transaction {
            RatedStoriesTable.update(
                where = {
                    RatedStoriesTable.storyId.eq(UUID.fromString(storyId)) and
                            RatedStoriesTable.userName.eq(userName)
                }
            ) { rs ->
                if (ratingOverallValue != null) {
                    rs[this.ratingOverallValue] = ratingOverallValue
                }
                if (ratingStyleValue != null) {
                    rs[this.ratingStyleValue] = ratingStyleValue
                }
                if (ratingStoryValue != null) {
                    rs[this.ratingStoryValue] = ratingStoryValue
                }
                if (ratingGrammarValue != null) {
                    rs[this.ratingGrammarValue] = ratingGrammarValue
                }
                if (ratingCharacterValue != null) {
                    rs[this.ratingCharacterValue] = ratingCharacterValue
                }

            }
        }
    }

    fun getAllRatedStories(): List<RatedStory> = transaction { RatedStoryEntity.all().map { it.toDTO() } }

    fun getFromMeRatedStories(userName: String): List<RatedStory> =
        getAllRatedStories().filter { it.user.userName == userName }

    //fun getTopRatedStory(): List<RatedStory> =  transaction { RatedStoryEntity.all(). }

    /******************Story as Favorite *****************/
    fun setStoryAsFavorite(userName: String, storyId: String) {
        transaction {
            StoryAsFavoriteEntity.new {
                this.userEntity = UserEntity.find { UsersTable.userName eq userName }.first()
                this.storyEntity = StoryEntity[UUID.fromString(storyId)]
            }.toDTO()
        }
    }

    fun getAllStoriesAsFavorite(): List<StoryAsFavorite> =
        transaction { StoryAsFavoriteEntity.all().map { it.toDTO() } }

    fun getMyFavoriteStories(userName: String): List<StoryAsFavorite> =
        getAllStoriesAsFavorite().filter { it.user.userName == userName }

    fun setStoryAsNotFavorite(userName: String, storyId: String): StoryAsFavorite {
        val storyAsFavorite = getMyFavoriteStories(userName).first { it.story.uuid.toString() == storyId }
        transaction {
            StoryAsFavoriteEntity.find {

                StoriesAsFavoriteTable.userName.eq(userName) and (StoriesAsFavoriteTable.storyId.eq(
                    UUID.fromString(
                        storyId
                    )
                ))

            }.first().delete()
        }

        return storyAsFavorite;

    }

    /*****************get Date ***************/
    private fun getDate(): String {
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        return sdf.format(Date())
    }
}