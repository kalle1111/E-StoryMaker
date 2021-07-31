package hsfl.project.e_storymaker.roomDB

import androidx.lifecycle.LiveData
import hsfl.project.e_storymaker.roomDB.Entities.chapterProgress.ChapterProgressDao
import hsfl.project.e_storymaker.roomDB.Entities.favoring.FavoringDao
import hsfl.project.e_storymaker.roomDB.Entities.friendship.Friendship
import hsfl.project.e_storymaker.roomDB.Entities.friendship.FriendshipDao
import hsfl.project.e_storymaker.roomDB.Entities.rating.Rating
import hsfl.project.e_storymaker.roomDB.Entities.rating.RatingDao
import hsfl.project.e_storymaker.roomDB.Entities.story.Story
import hsfl.project.e_storymaker.roomDB.Entities.story.StoryDao
import hsfl.project.e_storymaker.roomDB.Entities.user.User
import hsfl.project.e_storymaker.roomDB.Entities.user.UserDao

class AppRepository(private val userDao: UserDao,
                    private val storyDao: StoryDao,
                    private val friendshipDao: FriendshipDao,
                    private val chapterProgressDao: ChapterProgressDao,
                    private val favoringDao: FavoringDao,
                    private val ratingDao: RatingDao,
) {

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

    suspend fun getStoryChapter(user_uuid: String, story_uuid: String): Int{
        return chapterProgressDao.getChapter(user_uuid, story_uuid)
    }

    suspend fun changeStory(story: Story){
        storyDao.changeStory(story)
    }

    suspend fun changeStoryChapter(user_uuid: String, story_uuid: String, newChapter: Int){
        chapterProgressDao.changeChapter(user_uuid, story_uuid, newChapter)
    }

    suspend fun getStoryReviews(story_uuid: String): List<Rating>{
        return ratingDao.getRatingsOfStory(story_uuid)
    }

    suspend fun changeStoryReview(user_uuid: String, story_uuid: String, newRating: Int){
        ratingDao.changeRating(user_uuid, story_uuid, newRating)
    }

    suspend fun deleteReview(user_uuid: String, story_uuid: String){
        ratingDao.deleteRating(user_uuid, story_uuid)
    }

    // Friendships

    suspend fun getRequestsToMe(user_uuid: String): List<User>{
        return friendshipDao.getRequestsToMe(user_uuid)
    }

    suspend fun getMyRequests(user_uuid: String): List<User>{
        return friendshipDao.getMyRequests(user_uuid)
    }

    suspend fun acceptRequest(user_uuid: String, friend_uuid: String){
        friendshipDao.acceptRequest(user_uuid, friend_uuid)
    }

    suspend fun rejectRequest(user_uuid: String, friend_uuid: String){
        friendshipDao.rejectRequest(user_uuid, friend_uuid)
    }

    suspend fun cancelRequest(user_uuid: String, friend_uuid: String){
        friendshipDao.cancelRequest(user_uuid, friend_uuid)
    }

    suspend fun deleteFriendship(user_uuid: String, friend_uuid: String){
        friendshipDao.deleteFriendship(user_uuid, friend_uuid)
    }

    suspend fun getFriendsAsRequester(user_uuid: String): List<User>{
        return friendshipDao.getFriendsAsRequester(user_uuid)
    }

    suspend fun getFriendsAsTarget(user_uuid: String): List<User>{
        return friendshipDao.getFriendsAsTarget(user_uuid)
    }

    suspend fun getFriends(user_uuid: String): List<User>{

        val friendsAsRequester = getFriendsAsRequester(user_uuid)
        val friendsAsTarget = getFriendsAsTarget(user_uuid)

        return friendsAsRequester + friendsAsTarget
    }

}