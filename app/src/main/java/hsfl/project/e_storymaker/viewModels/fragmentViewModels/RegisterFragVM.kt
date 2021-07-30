package hsfl.project.e_storymaker.viewModels.fragmentViewModels

import android.graphics.ColorSpace
import android.util.Log
import androidx.lifecycle.ViewModel
import hsfl.project.e_storymaker.viewModels.AuthVM

class RegisterFragVM : AuthVM() {

    private var model: ColorSpace.Model? = null

    fun register(username: String, email: String, password: String, passwordConf: String): Boolean{
        Log.d("Main", "username: " + username + " email: " + email + " password: " + password + "passwordCONF: " + passwordConf)
        //return model.register(username, email, password, passwordConf)
        return false
    }
}