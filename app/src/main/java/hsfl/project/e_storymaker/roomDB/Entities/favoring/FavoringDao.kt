package hsfl.project.e_storymaker.roomDB.Entities.favoring

import androidx.lifecycle.LiveData
import androidx.room.*
import hsfl.project.e_storymaker.roomDB.Entities.story.Story
import hsfl.project.e_storymaker.roomDB.Entities.tag.Tag

@Dao
interface FavoringDao {

    @Query("SELECT * FROM favoring WHERE favoring.user_uuid LIKE :user_uuid")
    fun getFavoritesOfUser(user_uuid: String): LiveData<List<Favoring>>

    @Query("SELECT * FROM favoring WHERE favoring_uuid LIKE :favorite_uuid")
    abstract fun getFavoritesByUuid(favorite_uuid : String): Favoring

    @Transaction
    fun getFavoritesByUuids(favorite_uuids : List<String>): List<Favoring>{

        val favoritesList = mutableListOf<Favoring>()

        for(favorite_uuid in favorite_uuids) favoritesList.add(getFavoritesByUuid(favorite_uuid))

        return favoritesList
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertFavoring(favoring: Favoring)

    fun insertWithTimestamp(favoring: Favoring) {
        insertFavoring(favoring.apply{
            cachedTime = System.currentTimeMillis()
        })
    }

    fun cacheFavorites(favorings: List<Favoring>) {
        favorings.forEach { insertWithTimestamp(it) }
    }

}