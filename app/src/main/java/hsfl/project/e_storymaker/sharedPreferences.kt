package hsfl.project.e_storymaker

import android.app.Activity
import android.content.Context

class sharedPreferences {

    fun saveJWT(activity: Activity, jwtToken: String){
        val sharedPreferences = activity?.getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("JWT_TOKEN", jwtToken)
        editor.apply()
    }

    fun saveUser_uuid(activity: Activity, user_uuid: String){
        val sharedPreferences = activity?.getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("USER_UUID", user_uuid)
        editor.apply()
    }

    fun getJWT(activity: Activity): String?{

        val sharedPrefs = activity?.getPreferences(Context.MODE_PRIVATE)
        val savedJWT = sharedPrefs.getString("JWT_TOKEN", null)

        return savedJWT
    }

    fun getUser_uuid(activity: Activity): String?{

        val sharedPrefs = activity?.getPreferences(Context.MODE_PRIVATE)
        val savedUUID = sharedPrefs.getString("USER_UUID", null)

        return savedUUID
    }

    fun clearPrefs(activity: Activity){

        val sharedPreferences = activity?.getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.remove("JWT_TOKEN")
        editor.remove("USER_UUID")

        editor.apply()

    }
}
