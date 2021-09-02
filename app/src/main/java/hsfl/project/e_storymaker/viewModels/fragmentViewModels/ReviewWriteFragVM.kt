package hsfl.project.e_storymaker.viewModels.fragmentViewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import hsfl.project.e_storymaker.models.remoteDataSource.StoryRepository
import hsfl.project.e_storymaker.repository.webserviceModels.ratedStory.RateStoryRequest

class ReviewWriteFragVM : ViewModel() {

    private var application: Application? = null
    private var storyRep: StoryRepository? = null

    private var storyID: String? = null

    fun setApplicationContext(application: Application, storyID: String){
        this.application = application
        storyRep = application?.let { StoryRepository.getStoryRepository(it) }!!
        this.storyID = storyID
    }

    fun SendReview(storyName: String, reviewTitle: String, overall: Float, style: Float, story: Float, grammar: Float, characters: Float, reviewText: String){
        //val newReview: RateStoryRequest = RateStoryRequest(storyName, overall, style, story, grammar, characters, reviewText)
        storyRep?.rateStory(RateStoryRequest(storyID!!, reviewTitle, overall.toInt(), style.toInt(), story.toInt(), grammar.toInt(), characters.toInt(), reviewText))
    }
}