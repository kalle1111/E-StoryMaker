package hsfl.project.e_storymaker.viewModels.fragmentViewModels

import android.app.Application
import android.graphics.ColorSpace
import android.util.Log
import androidx.lifecycle.ViewModel
import hsfl.project.e_storymaker.models.remoteDataSource.UserRepository
import hsfl.project.e_storymaker.repository.webserviceModels.RegisterRequest
import hsfl.project.e_storymaker.viewModels.AuthVM

class RegisterFragVM : AuthVM() {


    private var application: Application? = null
    private var userRep: UserRepository? = null

    fun setApplicationContext(application: Application){
        this.application = application
        userRep = application?.let { UserRepository.getStoryRepository(it) }!!
    }

    fun register(username: String, email: String, password: String, passwordConf: String): Boolean{
        Log.d("Main", "username: " + username + " email: " + email + " password: " + password + "passwordCONF: " + passwordConf)
        //return model.register(username, email, password, passwordConf)

        userRep?.registerRequest(RegisterRequest("a", "b",username, "", "22.18.20323", password))
        Log.d("RegisterVM", "REPOSITORY QUERRIED!")

        return false
    }
}