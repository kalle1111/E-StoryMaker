package hsfl.project.e_storymaker.roomDB.Entities.favoring

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface FavoringDao {

    @Query("SELECT * FROM favoring WHERE favoring.user_uuid LIKE :user_uuid")
    fun getFavoritesOfUser(user_uuid: String): List<Favoring>

}