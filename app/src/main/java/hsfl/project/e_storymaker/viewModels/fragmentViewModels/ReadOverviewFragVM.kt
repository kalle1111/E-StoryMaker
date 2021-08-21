package hsfl.project.e_storymaker.viewModels.fragmentViewModels


import android.app.Application
import hsfl.project.e_storymaker.models.remoteDataSource.StoryRepository
import hsfl.project.e_storymaker.roomDB.Entities.story.Story
import hsfl.project.e_storymaker.viewModels.ReadingVM

class ReadOverviewFragVM : ReadingVM() {

    private var application: Application? = null
    private var storyRep: StoryRepository? = null


    private var currentStory: Story = Story("0", "0", "Story_Title", "/Description/ \n\n\n /End Description/", "1.1.0000", 0, ByteArray(0), 3.2, 0)

    fun setApplicationContext(application: Application){
        this.application = application
        storyRep = application?.let { StoryRepository.getStoryRepository(it) }!!
        getStory()
    }

    private fun getStory(){
        if (storyRep != null && false){
        //currentStory = storyRep.getOverview()
            currentStory = //storyRep?.get
        TODO("WHERE IS THE ACCESS FUNCTION!???")
        }else{
            //THROW ERROR
        }
    }

    fun author(): String{
        return "by " + "Max_Mustermann"
        TODO("Get actual author: either through uuid...... or better yet, through direkt username as primary_key")
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