package hsfl.project.e_storymaker.viewModels.fragmentViewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import hsfl.project.e_storymaker.models.remoteDataSource.UserRepository
import hsfl.project.e_storymaker.roomDB.Entities.user.User

import hsfl.project.e_storymaker.viewModels.AuthVM
import hsfl.project.e_storymaker.viewModels.MainVM

class ProfileFragVM : MainVM() {

    private var applicaion: Application? = null
    private var userRep: UserRepository? = null

    private var currentUser: User = User("0", "MAX", "Mustermann", "MAX_MUSTERMANN", "/DESCR/\n...\n\n\n\n\n\n/END DESCR/",
        "1.1.0000", 2)


    fun setApplicationContext(application: Application){
        this.applicaion = applicaion
        userRep = application?.let { UserRepository.getStoryRepository(it) }!!
        getUser()
    }

    private fun getUser(){
        if (userRep != null && false){
            currentUser = userRep?.getMyProfile("MOVE THIS TO BACKED!")!!
            TODO(" Move this to backend!")
        }else{
            //THrow ERROR
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