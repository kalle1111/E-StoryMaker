package hsfl.project.e_storymaker.viewModels.fragmentViewModels

import android.app.Application
import android.util.Log
import hsfl.project.e_storymaker.models.remoteDataSource.UserRepository
import hsfl.project.e_storymaker.repository.webserviceModels.register.RegisterRequest
import hsfl.project.e_storymaker.viewModels.AuthVM

class RegisterFragVM : AuthVM() {


    private var application: Application? = null
    private var userRep: UserRepository? = null
    val TAG: String = "RegisterVM"

    fun setApplicationContext(application: Application){
        this.application = application
        userRep = application.let { UserRepository.getStoryRepository(it) }!!
    }

    fun register(username: String, email: String, password: String, passwordConf: String, image: ByteArray): Boolean{
        return if (userRep != null){
            Log.d(TAG, "username: " + username + " email: " + email + " password: " + password + "passwordCONF: " + passwordConf)

            val test:Boolean? = userRep?.registerRequest(RegisterRequest(username, "No description yet!", password, image))
            Log.d(TAG, test.toString())

            Log.d(TAG, "REPOSITORY QUERRIED!")

            (test != null && test)
        }else{
            Log.e("RegisterFragVM", "UserRep not found!")
            false
        }

    }
}