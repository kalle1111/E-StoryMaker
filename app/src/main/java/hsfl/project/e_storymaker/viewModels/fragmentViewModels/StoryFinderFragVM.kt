package hsfl.project.e_storymaker.viewModels.fragmentViewModels


import android.app.Application
import hsfl.project.e_storymaker.models.remoteDataSource.StoryRepository
import hsfl.project.e_storymaker.roomDB.Entities.story.Story
import hsfl.project.e_storymaker.viewModels.MainVM

class StoryFinderFragVM : MainVM() {

    private var application: Application? = null
    private var storyRep: StoryRepository? = null


    private var currentStoryList: List<Story>? = null

    fun setApplicationContext(application: Application){
        this.application = application
        storyRep = application?.let { StoryRepository.getStoryRepository(it) }!!
    }

    fun CurrentStoryList(): List<Story>{
        return listOf(Story("0", "0", "MAX_TESTTITLE", "/Description/ \n\n\n /End Description/", "1.1.0000", 0, ByteArray(0), 4.0, 0),
            Story("0", "0", "TAEOFENOA", "/Description/ \n\n\n /End Description/", "1.1.0000", 0, ByteArray(0), 4.0, 0),
            Story("0", "0", "ABCDEFG", "/Description/ \n\n\n /End Description/", "1.1.0000", 0, ByteArray(0), 4.0, 0)
        )
        //return currentStoryList
    }

    fun getStories(){
        if (storyRep != null && false){
            //when() to detect different kinds of search!
            currentStoryList = storyRep?.getAllStories()
        }else{
            //Throw error
        }
    }


}