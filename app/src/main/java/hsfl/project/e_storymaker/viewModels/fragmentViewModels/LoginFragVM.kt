package hsfl.project.e_storymaker.viewModels.fragmentViewModels

import android.app.Application
import android.util.Log
import hsfl.project.e_storymaker.models.remoteDataSource.UserRepository
import hsfl.project.e_storymaker.repository.webserviceModels.login.LoginRequest
import hsfl.project.e_storymaker.viewModels.AuthVM

class LoginFragVM : AuthVM() {

    private var application: Application? = null
    private var userRep: UserRepository? = null

    fun setApplicationContext(application: Application){
        this.application = application
        userRep = application?.let { UserRepository.getStoryRepository(it) }!!
    }


    fun autoLogin(): Boolean{
        //TODO("CONNECT TO MODEL")
        /*
        if (model.getStoredJWTKey != null){
            return true
        }else{
            return false
        }
         */
        return false
    }

    fun login(username: String, password: String): Boolean{
        return if(userRep != null){
            Log.d("Main","username: " + username + " password: " + password)
            val authorized = userRep?.loginRequest(LoginRequest(username, password))
            (authorized != null && authorized)
        }else{
            Log.e("LoginFragVM", "UserRep is not initialized!")
            false
        }

    }

    fun forgotPassword(){
        //TODO("NOT IMPLEMENTED!")
        //send resetPasswordRequest
    }
}