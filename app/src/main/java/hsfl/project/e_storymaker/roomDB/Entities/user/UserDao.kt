package hsfl.project.e_storymaker.roomDB.Entities.user

import androidx.lifecycle.LiveData
import androidx.room.*
import hsfl.project.e_storymaker.roomDB.Entities.friendship.Friendship
import hsfl.project.e_storymaker.roomDB.Entities.story.Story

@Dao
abstract class UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertUser(user: User)

    @Update
    abstract fun updateUser(user: User)

    @Delete
    abstract fun deleteUser(user:User)

    @Query("SELECT * FROM user " +
            "WHERE firstname LIKE :firstname " +
            "AND lastname LIKE :lastname LIMIT 1 ")
    abstract fun getUserByName(firstname: String, lastname: String): LiveData<User>

    @Query("SELECT * FROM user WHERE username LIKE :username LIMIT 1 ")
    abstract fun getUserByUsername(username: String):LiveData<User>

    @Query("SELECT * FROM user WHERE user_uuid LIKE :user_uuid LIMIT 1 ")
    abstract fun getUserByUuid(user_uuid : String): LiveData<User>

    fun insertWithTimestamp(user:User) {
        insertUser(user.apply{
            cachedTime = System.currentTimeMillis()
        })
    }

    fun cacheUsers(users: List<User>) {
        users.forEach { insertWithTimestamp(it) }
    }
}