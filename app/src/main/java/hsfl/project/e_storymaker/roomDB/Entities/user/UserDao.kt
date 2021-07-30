package hsfl.project.e_storymaker.roomDB.Entities.user

import androidx.lifecycle.LiveData
import androidx.room.*
import hsfl.project.e_storymaker.roomDB.Entities.friendship.Friendship

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addUser(user: User)

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user:User)

    @Query("SELECT * FROM user " +
            "WHERE firstname LIKE :firstname " +
            "AND lastname LIKE lastname LIMIT 1")
    fun getUserByName(firstname: String, lastname: String): User
}