package com.eStory.service

import com.eStory.model.story.RatedStory
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
    fun rateStory(userName: String, storyId: String, isFavorite: Boolean, ratingValue: Int): RatedStory =
        transaction {
            RatedStoryEntity.new {
                this.userEntity = UserEntity.find { UsersTable.userName eq userName }.first()
                this.storyEntity = StoryEntity[UUID.fromString(storyId)]
                this.isFavorite = isFavorite
                this.ratingValue = ratingValue
            }.toDTO()
        }

    fun updateRatedStory(userName: String, storyId: String, isFavorite: Boolean?, ratingValue: Int?) {

        transaction {
            RatedStoriesTable.update(
                where = {
                    RatedStoriesTable.storyId.eq(UUID.fromString(storyId)) and
                            RatedStoriesTable.userName.eq(userName)
                }
            ) { rs ->
                if (isFavorite != null) {
                    rs[this.isFavorite] = isFavorite
                }

                if (ratingValue != null) {
                    rs[this.ratingValue] = ratingValue
                }

            }
        }
    }

    fun getAllRatedStories(): List<RatedStory> = transaction { RatedStoryEntity.all().map { it.toDTO() } }

    fun getFromMeRatedStories(userName: String): List<RatedStory> =
        getAllRatedStories().filter { it.user.userName == userName }

    private fun getDate(): String {
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        return sdf.format(Date())
    }
}