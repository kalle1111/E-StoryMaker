package hsfl.project.e_storymaker.viewModels.fragmentViewModels


import android.app.Application
import android.util.Log
import hsfl.project.e_storymaker.models.remoteDataSource.StoryRepository
import hsfl.project.e_storymaker.roomDB.Entities.story.Story
import hsfl.project.e_storymaker.viewModels.ReadingVM
import io.ktor.auth.*

class ReadOverviewFragVM : ReadingVM() {

    private var application: Application? = null
    private var storyRep: StoryRepository? = null
    var storyID: String? = null

    private var currentStory: Story = Story("0", "Max_Mustermann", "Story_Title", "/Description/ \n\n\n /End Description/", "1.1.0000", 0, ByteArray(0), 3.2, 0)

    fun setApplicationContext(application: Application, storyID: String?){
        this.application = application
        storyRep = application?.let { StoryRepository.getStoryRepository(it) }!!
        getStory(storyID)
    }

    private fun getStory(storyID: String?){
        if (storyRep != null){
            if (storyID != null){
                this.storyID = storyID
                currentStory = storyRep?.getStory(storyID)!!
                Log.d("ReadOverviewFragVM", currentStory.toString())
            }else{
                //Throw Error
            }
        }else{
            //THROW ERROR
        }
    }

    fun storyCover(): ByteArray{
        return currentStory.cover
    }

    fun username(): String{
        return currentStory.author_username
    }

    fun author(): String{
        return "by " + currentStory.author_username
    }

    fun title(): String{
        return currentStory.storyTitle
    }

    fun tagList(): String{
        return "/TAGS/\n\n/END TAGS/"
        TODO("GET ACTUAL TAGLIST")
    }

    fun description(): String{
        return currentStory.description
    }

    fun rating(): Float{
        return currentStory.avgRating.toFloat()
    }

    fun ratingLabel(): String{
        return rating().toString()
    }

}