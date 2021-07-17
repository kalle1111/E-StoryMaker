package hsfl.project.e_storymaker.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hsfl.project.e_storymaker.R
import hsfl.project.e_storymaker.views.fragments.ReadingOverviewFragment

class ReadingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reading_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ReadingOverviewFragment.newInstance())
                .commitNow()
        }
    }
}