package hsfl.project.e_storymaker.roomDB.Entities.story


import androidx.lifecycle.LiveData
import androidx.room.*
import hsfl.project.e_storymaker.roomDB.Entities.transitives.UserRatesStory
import hsfl.project.e_storymaker.roomDB.Entities.user.User

@Dao
interface StoryDao {

    @Delete
    fun delete(story: Story)

    @Query("SELECT * FROM story ORDER BY story_uuid ASC")
    fun getAllStories(): List<Story>

    @Query("SELECT * FROM story WHERE storyTitle LIKE :title LIMIT 1")
    fun getStory(title: String): Story

    @Query("SELECT * FROM userreadsstory " +
            "JOIN user AS author ON userreadsstory.user = author.user_uuid" +
            "JOIN story ON userreadsstory.story = story.story_uuid" +
            "WHERE story.storyTitle LIKE :storyTitle " +
            "AND author.userName LIKE :userName " +
            "LIMIT 1")
    fun getStoryChapter(userName: String, storyTitle: String): Int

    @Update
    fun changeStory(story: Story)

    @Query("UPDATE userreadsstory" +
            "JOIN user AS author ON userreadsstory.user = author.user_uuid" +
            "JOIN story ON userreadsstory.story = story.story_uuid" +
            "SET userreadsstory.chapter = :newChapter" +
            "WHERE story.storyTitle LIKE :storyTitle " +
            "AND author.userName LIKE :userName ")
    fun changeStoryChapter(userName: String, storyTitle: String, newChapter: Int)

    @Query("SELECT * FROM userratesstory " +
            "JOIN story ON userratesstory.story = story.story_uuid" +
            "WHERE story.storyTitle LIKE :storyTitle ")
    fun getStoryReviews(storyTitle: String): List<UserRatesStory>

    @Query("UPDATE userratesstory" +
            "JOIN user ON userratesstory.user = user.user_uuid" +
            "JOIN story ON userratesstory.story = story.story_uuid" +
            "SET userratesstory.ratingValue = :newRating" +
            "WHERE story.storyTitle LIKE :storyTitle " +
            "AND user.userName LIKE :userName")
    fun changeStoryReview(userName: String, storyTitle: String, newRating: Int)

    @Query("DELETE FROM userratesstory" +
            "JOIN user ON userratesstory.user = user.user_uuid" +
            "JOIN story ON userratesstory.story = story.story_uuid" +
            "WHERE story.storyTitle LIKE :storyTitle " +
            "AND user.userName LIKE :userName")
    fun deleteReview(userName: String, storyTitle: String)

}