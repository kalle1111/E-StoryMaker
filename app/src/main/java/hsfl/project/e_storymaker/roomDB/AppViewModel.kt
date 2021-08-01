package hsfl.project.e_storymaker.roomDB

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import hsfl.project.e_storymaker.roomDB.Entities.rating.Rating
import hsfl.project.e_storymaker.roomDB.Entities.story.Story
import hsfl.project.e_storymaker.roomDB.Entities.user.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppViewModel(application: Application): AndroidViewModel(application) {

    private val repository: AppRepository

    init{

        val database = AppDatabase.getDatabase(application)

        val userDao = database.userDao()
        val storyDao = database.storyDao()
        val friendshipDao = database.friendshipDao()
        val chapterProgressDao = database.chapterProgressDao()
        val favoringDao = database.favoringDao()
        val ratingDao = database.ratingDao()

        repository = AppRepository(userDao, storyDao, friendshipDao,
            chapterProgressDao, favoringDao, ratingDao)
    }

    // User

    fun addUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    fun updateUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUser(user)
        }
    }

    fun deleteUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUser(user)
        }
    }

    fun getUserByName(firstname: String, lastname: String): LiveData<User>{
        val result = MutableLiveData<User>()
        viewModelScope.launch(Dispatchers.IO) {
            result.value = repository.getUserByName(firstname, lastname)
        }
        return result
    }

    // Stories

    fun deleteStory(story: Story){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteStory(story)
        }
    }

    fun getStory(title: String): LiveData<Story> {
        val result = MutableLiveData<Story>()
        viewModelScope.launch(Dispatchers.IO) {
            result.value = repository.getStory(title)
        }
        return result
    }

    fun getAllStories(): LiveData<List<Story>>{
        val result = MutableLiveData<List<Story>>()
        viewModelScope.launch(Dispatchers.IO) {
            result.value = repository.getAllStories()
        }
        return result
    }

    fun getStoryChapter(user_uuid
                        : String, storyTitle: String): LiveData<Int>{
        val result = MutableLiveData<Int>()
        viewModelScope.launch(Dispatchers.IO) {
            result.value = repository.getStoryChapter(user_uuid
                , storyTitle)
        }
        return result
    }

    fun changeStory(story: Story){
        viewModelScope.launch(Dispatchers.IO) {
            repository.changeStory(story)
        }
    }

    fun changeStoryChapter(user_uuid
                           : String, storyTitle: String, newChapter: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.changeStoryChapter(user_uuid
                , storyTitle, newChapter)
        }
    }

    fun getStoryReviews(storyTitle: String): LiveData<List<Rating>>{
        val result = MutableLiveData<List<Rating>>()
        viewModelScope.launch(Dispatchers.IO) {
            result.value = repository.getStoryReviews(storyTitle)
        }
        return result
    }

    fun changeStoryReview(user_uuid: String, storyTitle: String, newRating: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.changeStoryReview(user_uuid, storyTitle, newRating)
        }
    }

    fun deleteReview(user_uuid: String, storyTitle: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteReview(user_uuid, storyTitle)
        }
    }

    // Friendships

    fun getRequestsToMe(user_uuid: String): LiveData<List<User>>{
        val result = MutableLiveData<List<User>>()
        viewModelScope.launch(Dispatchers.IO) {
            result.value = repository.getRequestsToMe(user_uuid)
        }
        return result
    }

    fun getMyRequests(user_uuid: String): LiveData<List<User>>{
        val result = MutableLiveData<List<User>>()
        viewModelScope.launch(Dispatchers.IO) {
            result.value = repository.getMyRequests(user_uuid)
        }
        return result
    }

    fun acceptRequest(user_uuid: String, friend_uuid: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.acceptRequest(user_uuid, friend_uuid)
        }
    }

    fun rejectRequest(user_uuid: String, friend_uuid: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.rejectRequest(user_uuid, friend_uuid)
        }
    }

    fun cancelRequest(user_uuid: String, friend_uuid: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.cancelRequest(user_uuid, friend_uuid)
        }
    }

    fun deleteFriendship(user_uuid: String, friend_uuid: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFriendship(user_uuid, friend_uuid)
        }
    }

    fun getFriendsAsRequester(user_uuid
                              : String): LiveData<List<User>>{
        val result = MutableLiveData<List<User>>()
        viewModelScope.launch(Dispatchers.IO) {
            result.value = repository.getFriendsAsRequester(user_uuid)
        }
        return result
    }

    fun getFriendsAsTarget(user_uuid
                           : String): LiveData<List<User>>{
        val result = MutableLiveData<List<User>>()
        viewModelScope.launch(Dispatchers.IO) {
            result.value = repository.getFriendsAsTarget(user_uuid)
        }
        return result
    }

    fun getFriends(user_uuid
                   : String): LiveData<List<User>>{
        val result = MutableLiveData<List<User>>()
        viewModelScope.launch(Dispatchers.IO) {
            result.value = repository.getFriends(user_uuid)
        }
        return result
    }
}