package hsfl.project.e_storymaker.roomDB.Entities.friendship

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import hsfl.project.e_storymaker.roomDB.Entities.story.Story
import hsfl.project.e_storymaker.roomDB.Entities.user.User

@Dao
interface FriendshipDao {

    @Query("SELECT user.* " +
            "FROM friendship JOIN user AS req ON friendship.requester = req.uuid" +
            "JOIN user AS tar ON friendship.target = tar.uuid")
    fun getFriendshipsByUserName(myUserName: String): List<Friendship>

    @Query("SELECT req.* FROM friendship" +
            "JOIN user AS req ON friendship.requester = req.uuid" +
            "JOIN user AS tar ON friendship.target = tar.uuid" +
            "WHERE !friendship.isAccepted" +
            "AND tar.name = :userName")
    fun getRequestsToMe(userName: String): List<Friendship>

    @Query("SELECT tar.* FROM friendship" +
            "JOIN user AS req ON friendship.requester = req.uuid" +
            "JOIN user AS tar ON friendship.target = tar.uuid" +
            "WHERE NOT friendship.isAccepted" +
            "AND req.name = :userName")
    fun getMyRequests(UserName: String): List<Friendship>

    @Query("UPDATE friendship" +
            "JOIN user AS req ON friendship.requester = req.uuid" +
            "JOIN user AS tar ON friendship.target = tar.uuid" +
            "SET friendship.isAccepted = true" +
            "WHERE NOT friendship.isAccepted" +
            "AND req.name = :friendUserName" +
            "AND tar.name = :myUserName")
    fun acceptRequest(myUserName: String, friendUserName: String)

    @Query("DELETE FROM friendship" +
            "JOIN user AS req ON friendship.requester = req.uuid" +
            "JOIN user AS tar ON friendship.target = tar.uuid" +
            "WHERE NOT friendship.isAccepted" +
            "AND req.name = :friendUserName" +
            "AND tar.name = :myUserName")
    fun rejectRequest(myUserName: String, friendUserName: String)

    @Query("DELETE FROM friendship" +
            "JOIN user AS req ON friendship.requester = req.uuid" +
            "JOIN user AS tar ON friendship.target = tar.uuid" +
            "WHERE NOT friendship.isAccepted" +
            "AND req.name = :myUserName" +
            "AND tar.name = :friendUserName")
    fun cancelRequest(myUserName: String, friendUserName: String)

    @Query("DELETE FROM friendship" +
            "JOIN user AS req ON friendship.requester = req.uuid" +
            "JOIN user AS tar ON friendship.target = tar.uuid" +
            "WHERE friendship.isAccepted" +
            "AND (req.name = :myUserName AND tar.name = :friendUserName)" +
            "OR (req.name = :friendUserName AND tar.name = :myUserName)")
    fun deleteFriendship(myUserName: String, friendUserName: String)

    @Query("SELECT tar.* FROM friendship" +
            "JOIN user AS req ON friendship.requester = req.uuid" +
            "JOIN user AS tar ON friendship.target = tar.uuid" +
            "WHERE friendship.isAccepted" +
            "AND req.name = :userName")
    fun getFriendsAsRequester(userName: String): List<User>

    @Query("SELECT req.* FROM friendship" +
            "JOIN user AS req ON friendship.requester = req.uuid" +
            "JOIN user AS tar ON friendship.target = tar.uuid" +
            "WHERE friendship.isAccepted" +
            "AND tar.name = :userName")
    fun getFriendsAsTarget(userName: String): List<User>

}