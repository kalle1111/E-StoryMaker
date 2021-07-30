package hsfl.project.e_storymaker.viewModels.fragmentViewModels

import android.graphics.ColorSpace
import android.util.Log
import androidx.lifecycle.ViewModel
import hsfl.project.e_storymaker.viewModels.AuthVM

class LoginFragVM : AuthVM() {

    private var model: ColorSpace.Model? = null //replace Dummy with ACTUAL Model


    fun autoLogin(): Boolean{
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
        //return model.login(username, password)?
        return true
    }

    fun forgotPassword(){
        //send resetPasswordRequest
    }
}