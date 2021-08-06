package hsfl.project.e_storymaker.roomDB.Entities.favoring

import androidx.room.*
import hsfl.project.e_storymaker.roomDB.Entities.story.Story

@Dao
interface FavoringDao {

    @Query("SELECT * FROM favoring WHERE favoring.user_uuid LIKE :user_uuid")
    fun getFavoritesOfUser(user_uuid: String): List<Favoring>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun cacheFavorites(favorites: List<Favoring>)

    @Query("SELECT * FROM favoring WHERE favoring_uuid LIKE :favorite_uuid")
    abstract fun getFavoritesByUuid(favorite_uuid : String): Favoring

    @Transaction
    fun getFavoritesByUuids(favorite_uuids : List<String>): List<Favoring>{

        val favoritesList = mutableListOf<Favoring>()

        for(favorite_uuid in favorite_uuids) favoritesList.add(getFavoritesByUuid(favorite_uuid))

        return favoritesList
    }


}