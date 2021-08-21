package hsfl.project.e_storymaker

import android.app.Activity
import android.content.Context

class sharedPreferences {

    companion object
    {
        private val USERNAME = "USERNAME"
        private val JWT_TOKEN = "JWT_TOKEN"

        fun saveJWT(activity: Activity, jwtToken: String){
            val sharedPreferences = activity?.getPreferences(Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString(JWT_TOKEN, jwtToken)
            editor.apply()
        }

        fun saveUsername(activity: Activity, user_uuid: String){
            val sharedPreferences = activity?.getPreferences(Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString(USERNAME, user_uuid)
            editor.apply()
        }

        fun getJWT(activity: Activity): String?{

            val sharedPrefs = activity?.getPreferences(Context.MODE_PRIVATE)
            val savedJWT = sharedPrefs.getString(JWT_TOKEN, null)

            return savedJWT
        }

        fun getUsername(activity: Activity): String?{

            val sharedPrefs = activity?.getPreferences(Context.MODE_PRIVATE)
            val savedUUID = sharedPrefs.getString(USERNAME, null)

            return savedUUID
        }

        fun clearPrefs(activity: Activity){

            val sharedPreferences = activity?.getPreferences(Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            editor.remove(JWT_TOKEN)
            editor.remove(USERNAME)

            editor.apply()

        }
    }
}
