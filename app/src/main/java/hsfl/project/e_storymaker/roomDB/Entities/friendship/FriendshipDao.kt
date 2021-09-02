package hsfl.project.e_storymaker.roomDB.Entities.friendship

import androidx.lifecycle.LiveData
import androidx.room.*
import hsfl.project.e_storymaker.roomDB.Entities.favoring.Favoring
import hsfl.project.e_storymaker.roomDB.Entities.story.Story
import hsfl.project.e_storymaker.roomDB.Entities.friendship.Friendship
import hsfl.project.e_storymaker.roomDB.Entities.user.User

@Dao
abstract class FriendshipDao {

    @Query("SELECT requester_username FROM friendship WHERE NOT isAccepted AND target_username = :user_username ")
    abstract fun getRequestsToMe(user_username: String): LiveData<List<String>>

    @Query("SELECT target_username FROM friendship WHERE NOT isAccepted AND requester_username = :user_username ")
    abstract fun getMyRequests(user_username: String): LiveData<List<String>>

    @Query("UPDATE friendship SET isAccepted = 1 WHERE NOT isAccepted AND requester_username = :friend_username AND target_username = :user_username ")
    abstract fun acceptRequest(user_username: String, friend_username: String)

    @Query("DELETE FROM friendship WHERE NOT isAccepted AND requester_username = :friend_username AND target_username = :user_username ")
    abstract fun rejectRequest(user_username: String, friend_username: String)

    @Query("DELETE FROM friendship WHERE NOT friendship.isAccepted AND requester_username = :user_username AND target_username = :friend_username")
    abstract fun cancelRequest(user_username: String, friend_username: String)

    @Query("DELETE FROM friendship WHERE friendship.isAccepted AND (requester_username = :user_username AND target_username = :friend_username) OR (requester_username = :friend_username AND target_username = :user_username)")
    abstract fun deleteFriendship(user_username: String, friend_username: String)

    @Query("SELECT target_username FROM friendship WHERE friendship.isAccepted AND requester_username = :user_username")
    abstract fun getFriendsAsRequester(user_username: String): List<String>

    @Query("SELECT requester_username FROM friendship WHERE friendship.isAccepted AND target_username = :user_username")
    abstract fun getFriendsAsTarget(user_username: String): List<String>

    @Query("SELECT * FROM friendship WHERE friendship_uuid LIKE :friendship_uuid")
    abstract fun getFriendshipByUuid(friendship_uuid : String): Friendship

    @Transaction
    open fun getFriendshipsByUuids(friendship_uuids : List<String>): List<Friendship>{

        val friendshipsList = mutableListOf<Friendship>()

        for(friendship_uuid in friendship_uuids) friendshipsList.add(getFriendshipByUuid(friendship_uuid))

        return friendshipsList
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertFriendship(friendship: Friendship)

    fun insertWithTimestamp(friendship: Friendship) {
        insertFriendship(friendship.apply{
            cachedTime = System.currentTimeMillis()
        })
    }

    fun cacheFriendships(friendships: List<Friendship>) {
        friendships.forEach { insertWithTimestamp(it) }
    }

}
