package hsfl.project.e_storymaker.viewModels.fragmentViewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import hsfl.project.e_storymaker.models.remoteDataSource.StoryRepository

class ReviewWriteFragVM : ViewModel() {

    private var application: Application? = null
    private var storyRep: StoryRepository? = null

    fun setApplicationContext(application: Application){
        this.application = application
        storyRep = application?.let { StoryRepository.getStoryRepository(it) }!!
    }

    fun SendReview(){

    }
}