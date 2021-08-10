package hsfl.project.e_storymaker.roomDB.Entities.user

import androidx.lifecycle.LiveData
import androidx.room.*
import hsfl.project.e_storymaker.roomDB.Entities.friendship.Friendship
import hsfl.project.e_storymaker.roomDB.Entities.story.Story

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user:User)

    @Query("SELECT * FROM user " +
            "WHERE firstname LIKE :firstname " +
            "AND lastname LIKE :lastname LIMIT 1 ")
    fun getUserByName(firstname: String, lastname: String): LiveData<User>

    @Query("SELECT * FROM user WHERE username LIKE :username LIMIT 1 ")
    fun getUserByUsername(username: String):LiveData<User>

    @Query("SELECT * FROM user WHERE user_uuid LIKE :user_uuid LIMIT 1 ")
    fun getUserByUuid(user_uuid : String): LiveData<User>

    fun insertWithTimestamp(user:User) {
        insertUser(user.apply{
            cachedTime = System.currentTimeMillis()
        })
    }

    fun cacheUsers(users: List<User>) {
        users.forEach { insertWithTimestamp(it) }
    }
}