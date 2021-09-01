package hsfl.project.e_storymaker.viewModels.fragmentViewModels

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hsfl.project.e_storymaker.models.remoteDataSource.UserRepository
import hsfl.project.e_storymaker.roomDB.Entities.user.User
import hsfl.project.e_storymaker.sharedPreferences

import hsfl.project.e_storymaker.viewModels.AuthVM
import hsfl.project.e_storymaker.viewModels.MainVM
import hsfl.project.e_storymaker.views.activities.AuthActivity
import kotlinx.coroutines.*

class ProfileFragVM : MainVM() {

    private var applicaion: Application? = null
    private var userRep: UserRepository? = null

    private var currentUser: User = User("MAX_MUSTERMANN", "/DESCR/\n...\n\n\n\n\n\n/END DESCR/",
        "",ByteArray(0) , 2)


    fun setApplicationContext(application: Application, user: String?){
        this.applicaion = application
        userRep = application?.let { UserRepository.getStoryRepository(it) }!!
        getUser(user)
    }

    fun ownProfile(): Boolean{
        Log.d("ProfileFragVM", "Your username: " + sharedPreferences.getUsername(applicaion!!)+ " / CurrentProfile: " + currentUser.username)
        Log.d("ProfileFragVM", sharedPreferences.getUsername(applicaion!!).equals(currentUser.username).toString())
        return sharedPreferences.getUsername(applicaion!!).equals(currentUser.username)
    }

    private fun getUser(user: String?){
        runBlocking{
            if (userRep != null && user != null){
                var potUser: User? = null
                    potUser = userRep?.getUser(user!!)
                    Log.d("ProfileFragVM", potUser.toString())

                    currentUser = potUser!!
                    Log.d("ProfileFragVM", "User Assigned!")

            }else{
                Log.e("ProfileFragVM", "No actual User requested, getting ownProfile()!")
                currentUser = userRep?.getMyProfile()!!
            }
        }
    }

    fun userImage(): ByteArray{
        return currentUser.image
    }

    fun username(): String{
        return currentUser.username
    }

    fun description(): String{
        return currentUser.user_description
    }

    fun storyButtonDescr(): String {
        return currentUser.username + "'s stories"
    }
}