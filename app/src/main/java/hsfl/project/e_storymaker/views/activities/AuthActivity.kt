package hsfl.project.e_storymaker.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import hsfl.project.e_storymaker.R
import hsfl.project.e_storymaker.databinding.AuthActivityBinding
import hsfl.project.e_storymaker.views.fragments.LoginFragment

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: AuthActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AuthActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, LoginFragment.newInstance())
                .commitNow()
        }*/
    }
}