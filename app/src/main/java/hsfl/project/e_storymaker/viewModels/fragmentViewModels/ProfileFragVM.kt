package hsfl.project.e_storymaker.viewModels.fragmentViewModels

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hsfl.project.e_storymaker.models.remoteDataSource.UserRepository
import hsfl.project.e_storymaker.roomDB.Entities.user.User

import hsfl.project.e_storymaker.viewModels.AuthVM
import hsfl.project.e_storymaker.viewModels.MainVM
import hsfl.project.e_storymaker.views.activities.AuthActivity
import kotlinx.coroutines.*

class ProfileFragVM : MainVM() {

    private var applicaion: Application? = null
    private var userRep: UserRepository? = null

    private var currentUser: User = User("MAX_MUSTERMANN", "MAX", "Mustermann", "/DESCR/\n...\n\n\n\n\n\n/END DESCR/",
        "1.1.0000", "why?", 2)


    fun setApplicationContext(application: Application, user: String?){
        this.applicaion = applicaion
        userRep = application?.let { UserRepository.getStoryRepository(it) }!!
        getUser(user)
    }

    fun getUser(user: String?){
        viewModelScope.async(Dispatchers.IO){
            if (userRep != null){
                var potUser: User? = null
                    potUser = userRep?.getMyProfile()
                    Log.d("ProfileFragVM", potUser.toString())

                if(potUser != null){
                    currentUser = potUser!!
                    Log.d("ProfileFragVM", "User Assigned!")
                }else{
                    Log.e("ProfileFragVM", "No actual User returned!")
                }
            }else{
                Log.e("ProfileFragVM", "UserRep not found!")
                //val intent: Intent = Intent(applicaion?.baseContext, AuthActivity::class.java)
                //applicaion?.baseContext.startActivity(intent)
            }
        }
        Log.d("ProfileFragVM", "LAUNCH FINISHED!")
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