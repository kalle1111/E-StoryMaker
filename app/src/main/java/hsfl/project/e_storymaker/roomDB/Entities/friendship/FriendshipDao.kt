package hsfl.project.e_storymaker.roomDB.Entities.friendship

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import hsfl.project.e_storymaker.roomDB.Entities.story.Story
import hsfl.project.e_storymaker.roomDB.Entities.user.User

@Dao
interface FriendshipDao {

    @Query("SELECT req.* FROM friendship JOIN user AS req ON requester_uuid = req.user_uuid WHERE NOT isAccepted AND target_uuid = :user_uuid ")
    fun getRequestsToMe(user_uuid: String): List<User>

    //TODO: build relations

    @Query("SELECT tar.* FROM friendship JOIN user AS tar ON target_uuid = tar.user_uuid WHERE NOT isAccepted AND requester_uuid = :user_uuid ")
    fun getMyRequests(user_uuid: String): List<User>

    //TODO: build relations

    @Query("UPDATE friendship SET isAccepted = 1 WHERE NOT isAccepted AND requester_uuid = :friend_uuid AND target_uuid = :user_uuid ")
    fun acceptRequest(user_uuid: String, friend_uuid: String)

    @Query("DELETE FROM friendship WHERE NOT isAccepted AND requester_uuid = :friend_uuid AND target_uuid = :user_uuid ")
    fun rejectRequest(user_uuid: String, friend_uuid: String)

    @Query("DELETE FROM friendship WHERE NOT friendship.isAccepted AND requester_uuid = :user_uuid AND target_uuid = :friend_uuid")
    fun cancelRequest(user_uuid: String, friend_uuid: String)

    @Query("DELETE FROM friendship WHERE friendship.isAccepted AND (requester_uuid = :user_uuid AND target_uuid = :friend_uuid) OR (requester_uuid = :friend_uuid AND target_uuid = :user_uuid)")
    fun deleteFriendship(user_uuid: String, friend_uuid: String)

    @Query("SELECT tar.* FROM friendship JOIN user AS tar ON target_uuid = tar.user_uuid WHERE friendship.isAccepted AND requester_uuid = :user_uuid")
    fun getFriendsAsRequester(user_uuid: String): List<User>

    //TODO: build relations

    @Query("SELECT req.* FROM friendship JOIN user AS req ON requester_uuid = req.user_uuid WHERE friendship.isAccepted AND target_uuid = :user_uuid")
    fun getFriendsAsTarget(user_uuid: String): List<User>

    //TODO: build relations

}