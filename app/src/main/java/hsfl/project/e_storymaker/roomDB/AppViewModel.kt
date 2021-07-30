package hsfl.project.e_storymaker.roomDB

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import hsfl.project.e_storymaker.roomDB.Entities.friendship.Friendship
import hsfl.project.e_storymaker.roomDB.Entities.story.Story
import hsfl.project.e_storymaker.roomDB.Entities.transitives.UserRatesStory
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

        repository = AppRepository(userDao, storyDao, friendshipDao)
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

    fun getStoryChapter(userName: String, storyTitle: String): LiveData<Int>{
        val result = MutableLiveData<Int>()
        viewModelScope.launch(Dispatchers.IO) {
            result.value = repository.getStoryChapter(userName, storyTitle)
        }
        return result
    }

    fun changeStory(story: Story){
        viewModelScope.launch(Dispatchers.IO) {
            repository.changeStory(story)
        }
    }

    fun changeStoryChapter(userName: String, storyTitle: String, newChapter: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.changeStoryChapter(userName, storyTitle, newChapter)
        }
    }

    fun getStoryReviews(storyTitle: String): LiveData<List<UserRatesStory>>{
        val result = MutableLiveData<List<UserRatesStory>>()
        viewModelScope.launch(Dispatchers.IO) {
            result.value = repository.getStoryReviews(storyTitle)
        }
        return result
    }

    fun changeStoryReview(userName: String, storyTitle: String, newRating: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.changeStoryReview(userName, storyTitle, newRating)
        }
    }

    fun deleteReview(userName: String, storyTitle: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteReview(userName, storyTitle)
        }
    }

    // Friendships

    fun getFriendshipsByUserName(myUserName: String): LiveData<List<Friendship>>{
        val result = MutableLiveData<List<Friendship>>()
        viewModelScope.launch(Dispatchers.IO) {
            result.value = repository.getFriendshipsByUserName(myUserName)
        }
        return result
    }

    fun getRequestsToMe(myUserName: String): LiveData<List<Friendship>>{
        val result = MutableLiveData<List<Friendship>>()
        viewModelScope.launch(Dispatchers.IO) {
            result.value = repository.getRequestsToMe(myUserName)
        }
        return result
    }

    fun getMyRequests(myUserName: String): LiveData<List<Friendship>>{
        val result = MutableLiveData<List<Friendship>>()
        viewModelScope.launch(Dispatchers.IO) {
            result.value = repository.getMyRequests(myUserName)
        }
        return result
    }

    fun acceptRequest(myUserName: String, friendUserName: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.acceptRequest(myUserName, friendUserName)
        }
    }

    fun rejectRequest(myUserName: String, friendUserName: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.rejectRequest(myUserName, friendUserName)
        }
    }

    fun cancelRequest(myUserName: String, friendUserName: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.cancelRequest(myUserName, friendUserName)
        }
    }

    fun deleteFriendship(myUserName: String, friendUserName: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFriendship(myUserName, friendUserName)
        }
    }

    fun getFriendsAsRequester(userName: String): LiveData<List<User>>{
        val result = MutableLiveData<List<User>>()
        viewModelScope.launch(Dispatchers.IO) {
            result.value = repository.getFriendsAsRequester(userName)
        }
        return result
    }

    fun getFriendsAsTarget(userName: String): LiveData<List<User>>{
        val result = MutableLiveData<List<User>>()
        viewModelScope.launch(Dispatchers.IO) {
            result.value = repository.getFriendsAsTarget(userName)
        }
        return result
    }

    fun getFriends(userName: String): LiveData<List<User>>{
        val result = MutableLiveData<List<User>>()
        viewModelScope.launch(Dispatchers.IO) {
            result.value = repository.getFriends(userName)
        }
        return result
    }
}