package com.eStory.service

import com.eStory.model.story.RatedStory
import com.eStory.model.story.StoryAsFavorite
import com.eStory.model.story.Story
import com.eStory.table.*
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.util.*

class StoryService {

    fun getAll(): List<Story> = transaction { StoryEntity.all().map { it.toDTO() } }

    fun getByUUID(uuid: String): Story? = transaction { StoryEntity.findById(UUID.fromString(uuid))?.toDTO() }

    fun getLastUpdateByUUID(uuid: String): Long? =
        transaction { StoryEntity.findById(UUID.fromString(uuid))?.lastUpdate }

    fun getAllLastUpdateValues(): List<Pair<String, Long>> =
        transaction { StoryEntity.all().map { Pair(it.id.toString(), it.lastUpdate) } }

    fun getLastUpdatesMyStories(userName: String): List<Pair<String, Long>> = transaction {
        StoryEntity.all().filter { it.userEntity.userName == userName }.map { Pair(it.id.toString(), it.lastUpdate) }
    }

    fun getAllByUserName(userName: String): List<Story> = getAll().filter { it.user.userName == userName }

    fun getStoriesBySubTitle(subTitle: String): List<Story> = getAll().filter { it.storyTitle.contains(subTitle) }

    fun getLastUpdatesBySubTitle(subTitle: String): List<Pair<String, Long>> =
        getStoriesBySubTitle(subTitle).map { Pair(it.uuid, it.lastUpdate) }

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
        description: String,
        cover: ByteArray?
    ): Story =
        transaction {
            StoryEntity.new {
                this.userEntity = UserEntity.find { UsersTable.userName eq userName }.first()
                this.description = description
                this.storyTitle = storyTitle
                this.createTime = Date().time
                this.averageRating = 0.0
                if (cover != null) {
                    this.cover = cover
                }
                this.lastUpdate = Date().time
            }.toDTO()
        }

    fun updateByUUID(
        uuid: String,
        storyTitle: String? = null,
        description: String? = null,
        cover: ByteArray
    ) {
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
                st[this.cover] = cover

                st[this.lastUpdate] = Date().time
            }
        }
    }

    /****************RATING_STORY****************/


    fun rateStory(
        userName: String, storyId: String,
        ratingTitle: String,
        ratingDescription: String,
        ratingOverallValue: Int,
        ratingStyleValue: Int,
        ratingStoryValue: Int,
        ratingGrammarValue: Int,
        ratingCharacterValue: Int
    ): RatedStory {
        val ratedStory = transaction {
            RatedStoryEntity.new {
                this.userEntity = UserEntity.find { UsersTable.userName eq userName }.first()
                this.storyEntity = StoryEntity[UUID.fromString(storyId)]
                this.ratingTitle = ratingTitle
                this.ratingDescription = ratingDescription
                this.ratingOverallValue = ratingOverallValue
                this.ratingStyleValue = ratingStyleValue
                this.ratingStoryValue = ratingStoryValue
                this.ratingGrammarValue = ratingGrammarValue
                this.ratingCharacterValue = ratingCharacterValue
                this.lastUpdate = Date().time

            }

        }
        updateAverageRatingToStory(storyId)
        return ratedStory.toDTO()
    }

    private fun updateAverageRatingToStory(storyId: String) {
        var r = 0
        val ratings = getAllRatedStories().filter { it.story.uuid == storyId }
        ratings.forEach { r += it.ratingOverallValue }
        val averageValue = r.toDouble() / ratings.size.toDouble()
        transaction {
            StoriesTable.update(
                where = {
                    StoriesTable.id.eq(UUID.fromString(storyId))
                }
            ) { rs ->
                rs[this.averageRating] = averageValue
                rs[this.lastUpdate] = Date().time
            }
        }
    }

    //updating the rating of a Story by a specific user name and Story id
    fun updateRatedStory(
        userName: String, storyId: String,
        ratingTitle: String?,
        ratingDescription: String?,
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
                if (ratingTitle != null) {
                    rs[this.ratingTitle] = ratingTitle
                }
                if (ratingDescription != null) {
                    rs[this.ratingDescription] = ratingDescription
                }

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
                rs[this.lastUpdate] = Date().time
            }
        }
        updateAverageRatingToStory(storyId)

    }

    fun getRatedStoryByUUID(uuid: String): RatedStory? =
        transaction { RatedStoryEntity.findById(UUID.fromString(uuid))?.toDTO() }

    fun getRatedStoryByStoryId(storyId: String): List<RatedStory> =
        getAllRatedStories().filter { it.story.uuid == storyId }

    fun getAllRatedStories(): List<RatedStory> = transaction { RatedStoryEntity.all().map { it.toDTO() } }

    fun getFromMeRatedStories(userName: String): List<RatedStory> =
        getAllRatedStories().filter { it.user.userName == userName }

    fun ratedStory_getLastUpdateByUUID(uuid: String): Long? =
        transaction { RatedStoryEntity.findById(UUID.fromString(uuid))?.lastUpdate }

    fun ratedStory_getAllLastUpdateValues(): List<Pair<String, Long>> =
        transaction { RatedStoryEntity.all().map { Pair(it.id.toString(), it.lastUpdate) } }

    fun getLastUpdatesFromMeRatedStories(userName: String): List<Pair<String, Long>> = transaction {
        RatedStoryEntity.all().filter { it.userEntity.userName == userName }
            .map { Pair(it.id.toString(), it.lastUpdate) }
    }

    fun getLastUpdatesRatedStoriesByStoryId(storyId: String): List<Pair<String, Long>> =
        getRatedStoryByStoryId(storyId).map { Pair(it.uuid, it.lastUpdate) }

    /******************Story as Favorite *****************/
    // insert a new entity to the table when the story is favorite
    fun setStoryAsFavorite(userName: String, storyId: String) {
        transaction {
            StoryAsFavoriteEntity.new {
                this.userEntity = UserEntity.find { UsersTable.userName eq userName }.first()
                this.storyEntity = StoryEntity[UUID.fromString(storyId)]
            }.toDTO()
        }
    }

    fun getFavoriteStoryByUUID(uuid: String): StoryAsFavorite? =
        transaction { StoryAsFavoriteEntity.findById(UUID.fromString(uuid))?.toDTO() }

    // get all stories that have been marked as favorite
    fun getAllStoriesAsFavorite(): List<StoryAsFavorite> =
        transaction { StoryAsFavoriteEntity.all().map { it.toDTO() } }

    // get all stories that hab been marked as favorite by username
    fun getMyFavoriteStories(userName: String): List<StoryAsFavorite> =
        getAllStoriesAsFavorite().filter { it.user.userName == userName }

    fun getLastUpdatesFromMyFavoriteStories(username: String): List<Pair<String, Long>> =
        getMyFavoriteStories(username).map { Pair(it.story.uuid, it.story.lastUpdate) }

    //Delete the Entity from the table, when the is not favorite
    fun setStoryAsNotFavorite(userName: String, storyId: String): StoryAsFavorite {
        val storyAsFavorite = getMyFavoriteStories(userName).first { it.story.uuid == storyId }
        transaction {
            StoryAsFavoriteEntity.find {

                StoriesAsFavoriteTable.userName.eq(userName) and (StoriesAsFavoriteTable.storyId.eq(
                    UUID.fromString(
                        storyId
                    )
                ))

            }.first().delete()
        }
        return storyAsFavorite
    }

}