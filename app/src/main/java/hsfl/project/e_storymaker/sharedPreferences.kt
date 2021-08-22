package hsfl.project.e_storymaker

import android.app.Activity
import android.app.Application
import android.content.Context
import android.preference.PreferenceManager

class sharedPreferences {

    companion object
    {
        private val USERNAME = "USERNAME"
        private val JWT_TOKEN = "JWT_TOKEN"

        fun saveJWT(application: Application, jwtToken: String){
            //val sharedPreferences = activity?.getPreferences(Context.MODE_PRIVATE)
            val sharedPreferences = application.getSharedPreferences("Settings", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString(JWT_TOKEN, jwtToken)
            editor.apply()
        }

        fun saveUsername(application: Application, user_uuid: String){
            val sharedPreferences = application.getSharedPreferences("Settings", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString(USERNAME, user_uuid)
            editor.apply()
        }

        fun getJWT(application: Application): String?{

            val sharedPrefs = application.getSharedPreferences("Settings", Context.MODE_PRIVATE)
            val savedJWT = sharedPrefs.getString(JWT_TOKEN, null)

            return savedJWT
        }

        fun getUsername(application: Application): String?{

            val sharedPrefs = application.getSharedPreferences("Settings", Context.MODE_PRIVATE)
            val savedUUID = sharedPrefs.getString(USERNAME, null)

            return savedUUID
        }

        fun clearPrefs(application: Application){

            val sharedPreferences = application.getSharedPreferences("Settings", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            editor.remove(JWT_TOKEN)
            editor.remove(USERNAME)

            editor.apply()

        }
    }
}
