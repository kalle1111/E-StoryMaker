package hsfl.project.e_storymaker.viewModels.fragmentViewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import hsfl.project.e_storymaker.models.remoteDataSource.StoryRepository
import hsfl.project.e_storymaker.repository.webserviceModels.RateStoryRequest
import hsfl.project.e_storymaker.roomDB.Entities.rating.Rating
import hsfl.project.e_storymaker.roomDB.Entities.rating.RatingsFromUser

class ReviewWriteFragVM : ViewModel() {

    private var application: Application? = null
    private var storyRep: StoryRepository? = null

    fun setApplicationContext(application: Application){
        this.application = application
        storyRep = application?.let { StoryRepository.getStoryRepository(it) }!!
    }

    fun SendReview(storyName: String, reviewTitle: String, overall: Float, style: Float, story: Float, grammar: Float, characters: Float, reviewText: String){
        //val newReview: RateStoryRequest = RateStoryRequest(storyName, overall, style, story, grammar, characters, reviewText)
        //storyRep?.rateStory("WHY IS THIS FRONTEND!", newReview)
    }
}