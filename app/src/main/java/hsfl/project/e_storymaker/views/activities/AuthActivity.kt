package hsfl.project.e_storymaker.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hsfl.project.e_storymaker.R
import hsfl.project.e_storymaker.views.fragments.LoginFragment

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.auth_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, LoginFragment.newInstance())
                .commitNow()
        }
    }
}