package hsfl.project.e_storymaker.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hsfl.project.e_storymaker.R
import hsfl.project.e_storymaker.views.fragments.WriteDetailsFragment

class WritingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.writing_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, WriteDetailsFragment.newInstance())
                .commitNow()
        }
    }
}