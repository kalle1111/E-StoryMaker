package hsfl.project.e_storymaker.viewModels.fragmentViewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import hsfl.project.e_storymaker.models.remoteDataSource.StoryRepository
import hsfl.project.e_storymaker.roomDB.Entities.rating.Rating
import hsfl.project.e_storymaker.roomDB.Entities.story.Story
import hsfl.project.e_storymaker.roomDB.Entities.user.User

class ReviewReadFragVM : ViewModel() {

    private var application: Application? = null
    private var storyRep: StoryRepository? = null

    private var currentReviewList: List<Rating>? = null

    fun setApplicationContext(application: Application){
        this.application = application
        storyRep = application?.let { StoryRepository.getStoryRepository(it) }!!
    }

    fun CurrentReviewList(): List<Rating>{
        return listOf(Rating("0", 5, 5, 5, 5, 5, "GG", "WP", 0),
            Rating("1", 1, 2, 3, 4, 5, "GG2", "WP2", 0))
    }

    fun getReviews(){
        if (storyRep != null && false){
            //currentReviewList = storyRep?.getAllRatedStoriesOfStory(storyID)
        }else{
            //Throw error
        }
    }

    fun storyName(): String{
        if (storyRep != null && false){
            //return getCurrentStoryName? or with constructor?!
            return ""
        }else{
            return "REVIEWS OF STORY_NAME"
        }
    }
}