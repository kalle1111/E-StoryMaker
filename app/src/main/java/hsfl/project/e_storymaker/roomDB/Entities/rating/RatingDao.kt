package hsfl.project.e_storymaker.roomDB.Entities.rating

import androidx.room.Dao
import androidx.room.Query

@Dao
interface RatingDao {

    @Query("SELECT * FROM rating WHERE rating.user_uuid LIKE :user_uuid ")
    fun getRatingsOfUser(user_uuid: String): List<Rating>

    @Query("SELECT * FROM rating WHERE rating.story_uuid LIKE :story_uuid ")
    fun getRatingsOfStory(story_uuid: String): List<Rating>

    @Query("SELECT * FROM rating WHERE rating.user_uuid LIKE :user_uuid AND rating.story_uuid LIKE :story_uuid ")
    fun getUsersRatingOfStory(user_uuid: String, story_uuid: String): List<Rating>

    @Query("UPDATE rating SET rating = :newRating WHERE rating.user_uuid LIKE :user_uuid AND rating.story_uuid LIKE :story_uuid ")
    fun changeRating(user_uuid: String, story_uuid: String, newRating: Int)

    @Query("DELETE FROM rating WHERE rating.user_uuid LIKE :user_uuid AND rating.story_uuid LIKE :story_uuid ")
    fun deleteRating(user_uuid: String, story_uuid: String)
}
