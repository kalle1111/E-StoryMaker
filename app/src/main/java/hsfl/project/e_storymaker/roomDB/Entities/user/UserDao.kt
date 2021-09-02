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


    @Query("SELECT * FROM user WHERE username LIKE :username LIMIT 1 ")
    abstract fun getUserByUsername(username: String): User

    @Query("SELECT EXISTS(SELECT * FROM user WHERE username = :username)")
    abstract fun rowExistByUsername(username : String) : Boolean

    fun insertWithTimestamp(user:User) {
        insertUser(user.apply{
            cachedTime = System.currentTimeMillis()
        })
    }
}