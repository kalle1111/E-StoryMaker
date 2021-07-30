package hsfl.project.e_storymaker.roomDB

import androidx.lifecycle.LiveData
import hsfl.project.e_storymaker.roomDB.Entities.friendship.Friendship
import hsfl.project.e_storymaker.roomDB.Entities.friendship.FriendshipDao
import hsfl.project.e_storymaker.roomDB.Entities.story.Story
import hsfl.project.e_storymaker.roomDB.Entities.story.StoryDao
import hsfl.project.e_storymaker.roomDB.Entities.transitives.UserRatesStory
import hsfl.project.e_storymaker.roomDB.Entities.user.User
import hsfl.project.e_storymaker.roomDB.Entities.user.UserDao

class AppRepository(private val userDao: UserDao,
                    private val storyDao: StoryDao,
                    private val friendshipDao: FriendshipDao) {

    // User

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }

    suspend fun updateUser(user: User){
        userDao.updateUser(user)
    }

    suspend fun deleteUser(user: User){
        userDao.deleteUser(user)
    }

    suspend fun getUserByName(firstname: String, lastname: String): User{
        return userDao.getUserByName(firstname, lastname)
    }

    // Stories

    suspend fun deleteStory(story: Story){
        storyDao.delete(story)
    }

    suspend fun getStory(title: String): Story{
        return storyDao.getStory(title)
    }

    suspend fun getAllStories(): List<Story>{
        return storyDao.getAllStories()
    }

    suspend fun getStoryChapter(userName: String, storyTitle: String): Int{
        return storyDao.getStoryChapter(userName, storyTitle)
    }

    suspend fun changeStory(story: Story){
        storyDao.changeStory(story)
    }

    suspend fun changeStoryChapter(userName: String, storyTitle: String, newChapter: Int){
        storyDao.changeStoryChapter(userName, storyTitle, newChapter)
    }

    suspend fun getStoryReviews(storyTitle: String): List<UserRatesStory>{
        return storyDao.getStoryReviews(storyTitle)
    }

    suspend fun changeStoryReview(userName: String, storyTitle: String, newRating: Int){
        storyDao.changeStoryReview(userName, storyTitle, newRating)
    }

    suspend fun deleteReview(userName: String, storyTitle: String){
        storyDao.deleteReview(userName, storyTitle)
    }

    // Friendships

    suspend fun getFriendshipsByUserName(myUserName: String): List<Friendship>{
        return friendshipDao.getFriendshipsByUserName(myUserName)
    }

    suspend fun getRequestsToMe(myUserName: String): List<Friendship>{
        return friendshipDao.getRequestsToMe(myUserName)
    }

    suspend fun getMyRequests(myUserName: String): List<Friendship>{
        return friendshipDao.getMyRequests(myUserName)
    }

    suspend fun acceptRequest(myUserName: String, friendUserName: String){
        friendshipDao.acceptRequest(myUserName, friendUserName)
    }

    suspend fun rejectRequest(myUserName: String, friendUserName: String){
        friendshipDao.rejectRequest(myUserName, friendUserName)
    }

    suspend fun cancelRequest(myUserName: String, friendUserName: String){
        friendshipDao.cancelRequest(myUserName, friendUserName)
    }

    suspend fun deleteFriendship(myUserName: String, friendUserName: String){
        friendshipDao.deleteFriendship(myUserName, friendUserName)
    }

    suspend fun getFriendsAsRequester(userName: String): List<User>{
        return friendshipDao.getFriendsAsRequester(userName)
    }

    suspend fun getFriendsAsTarget(userName: String): List<User>{
        return friendshipDao.getFriendsAsTarget(userName)
    }

    suspend fun getFriends(userName: String): List<User>{

        val friendsAsRequester = getFriendsAsRequester(userName)
        val friendsAsTarget = getFriendsAsTarget(userName)

        return friendsAsRequester + friendsAsTarget
    }

}