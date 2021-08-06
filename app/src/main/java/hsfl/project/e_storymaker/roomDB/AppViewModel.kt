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
}