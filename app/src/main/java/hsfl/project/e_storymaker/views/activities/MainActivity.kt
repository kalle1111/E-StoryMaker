package hsfl.project.e_storymaker.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hsfl.project.e_storymaker.R
import hsfl.project.e_storymaker.views.fragments.StoryFinderFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, StoryFinderFragment.newInstance())
                .commitNow()
        }
    }
}