package hsfl.project.e_storymaker.viewModels.fragmentViewModels

import android.graphics.ColorSpace
import android.util.Log
import androidx.lifecycle.ViewModel
import hsfl.project.e_storymaker.viewModels.AuthVM

class LoginFragVM : AuthVM() {




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
        Log.d("Main","username: " + username + " password: " + password)
        //TODO("CONNECT TO MODEL")
        //return model.login(username, password)?
        return true
    }

    fun forgotPassword(){
        //TODO("NOT IMPLEMENTED!")
        //send resetPasswordRequest
    }
}