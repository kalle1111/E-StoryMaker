package hsfl.project.e_storymaker.roomDB.Entities.friendship

import androidx.lifecycle.LiveData
import androidx.room.*
import hsfl.project.e_storymaker.roomDB.Entities.favoring.Favoring
import hsfl.project.e_storymaker.roomDB.Entities.story.Story
import hsfl.project.e_storymaker.roomDB.Entities.friendship.Friendship
import hsfl.project.e_storymaker.roomDB.Entities.user.User

@Dao
abstract class FriendshipDao {

    @Query("SELECT req.* FROM friendship JOIN user AS req ON requester_uuid = req.user_uuid WHERE NOT isAccepted AND target_uuid = :user_uuid ")
    abstract fun getRequestsToMe(user_uuid: String): LiveData<List<User>>

    @Query("SELECT tar.* FROM friendship JOIN user AS tar ON target_uuid = tar.user_uuid WHERE NOT isAccepted AND requester_uuid = :user_uuid ")
    abstract fun getMyRequests(user_uuid: String): LiveData<List<User>>

    @Query("UPDATE friendship SET isAccepted = 1 WHERE NOT isAccepted AND requester_uuid = :friend_uuid AND target_uuid = :user_uuid ")
    abstract fun acceptRequest(user_uuid: String, friend_uuid: String)

    @Query("DELETE FROM friendship WHERE NOT isAccepted AND requester_uuid = :friend_uuid AND target_uuid = :user_uuid ")
    abstract fun rejectRequest(user_uuid: String, friend_uuid: String)

    @Query("DELETE FROM friendship WHERE NOT friendship.isAccepted AND requester_uuid = :user_uuid AND target_uuid = :friend_uuid")
    abstract fun cancelRequest(user_uuid: String, friend_uuid: String)

    @Query("DELETE FROM friendship WHERE friendship.isAccepted AND (requester_uuid = :user_uuid AND target_uuid = :friend_uuid) OR (requester_uuid = :friend_uuid AND target_uuid = :user_uuid)")
    abstract fun deleteFriendship(user_uuid: String, friend_uuid: String)

    @Query("SELECT tar.* FROM friendship JOIN user AS tar ON target_uuid = tar.user_uuid WHERE friendship.isAccepted AND requester_uuid = :user_uuid")
    abstract fun getFriendsAsRequester(user_uuid: String): List<User>

    @Query("SELECT req.* FROM friendship JOIN user AS req ON requester_uuid = req.user_uuid WHERE friendship.isAccepted AND target_uuid = :user_uuid")
    abstract fun getFriendsAsTarget(user_uuid: String): List<User>

    fun getFriends(user_uuid: String): Set<User>{

        val requesterList = getFriendsAsRequester(user_uuid)

        val targetList = getFriendsAsTarget(user_uuid)

        return (requesterList+targetList).toSet()
    }

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
