package hsfl.project.e_storymaker.roomDB.Entities.rating

import androidx.lifecycle.LiveData
import androidx.room.*
import hsfl.project.e_storymaker.roomDB.Entities.rating.Rating
import hsfl.project.e_storymaker.roomDB.Entities.story.Story
import hsfl.project.e_storymaker.roomDB.Entities.tag.Tag

@Dao
abstract class RatingDao {

    @Query("SELECT * FROM rating WHERE rating.user_username LIKE :user_uuid ")
    abstract fun getRatingsOfUser(user_uuid: String):List<Rating>

    @Query("SELECT * FROM rating WHERE rating.story_uuid LIKE :story_uuid ")
    abstract fun getRatingsOfStory(story_uuid: String): List<Rating>

    @Query("SELECT * FROM rating WHERE rating.user_username LIKE :user_uuid AND rating.story_uuid LIKE :story_uuid ")
    abstract fun getUsersRatingOfStory(user_uuid: String, story_uuid: String): List<Rating>

    @Query("DELETE FROM rating WHERE rating.user_username LIKE :user_uuid AND rating.story_uuid LIKE :story_uuid ")
    abstract fun deleteRating(user_uuid: String, story_uuid: String)

    @Query("SELECT * FROM rating WHERE rating_uuid LIKE :rating_uuid")
    abstract fun getRatingByUuid(rating_uuid : String): Rating

    @Query("SELECT EXISTS(SELECT * FROM rating WHERE rating_uuid = :uuid)")
    abstract fun rowExistByUUID(uuid : String) : Boolean

    @Transaction
    open fun getRatingsByUuids(rating_uuids : List<String>): List<Rating>{

        val ratingsList = mutableListOf<Rating>()

        for(rating_uuid in rating_uuids) ratingsList.add(getRatingByUuid(rating_uuid))

        return ratingsList
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertRating(rating: Rating)

    fun insertWithTimestamp(rating: Rating) {
        insertRating(rating.apply{
            cachedTime = System.currentTimeMillis()
        })
    }

}
