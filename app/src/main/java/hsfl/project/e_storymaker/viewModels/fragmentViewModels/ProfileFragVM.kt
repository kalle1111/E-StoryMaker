package hsfl.project.e_storymaker.viewModels.fragmentViewModels

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import hsfl.project.e_storymaker.models.remoteDataSource.UserRepository
import hsfl.project.e_storymaker.roomDB.Entities.user.User

import hsfl.project.e_storymaker.viewModels.AuthVM
import hsfl.project.e_storymaker.viewModels.MainVM
import hsfl.project.e_storymaker.views.activities.AuthActivity
import kotlinx.coroutines.runBlocking

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

    private fun getUser(user: String?){
        if (userRep != null){
            val potUser: User?
            runBlocking {
                potUser = userRep?.getUser("c")
            }

            if(potUser != null){
                currentUser = potUser
            }else{
                Log.e("ProfileFragVM", "No actual User returned!")
            }
        }else{
            //val intent: Intent = Intent(applicaion?.baseContext, AuthActivity::class.java)
            //applicaion?.baseContext.startActivity(intent)
        }

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