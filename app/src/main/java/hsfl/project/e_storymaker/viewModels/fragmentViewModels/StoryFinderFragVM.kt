package hsfl.project.e_storymaker.viewModels.fragmentViewModels


import android.app.Application
import android.util.Log
import hsfl.project.e_storymaker.models.remoteDataSource.StoryRepository
import hsfl.project.e_storymaker.roomDB.Entities.story.Story
import hsfl.project.e_storymaker.viewModels.MainVM

class StoryFinderFragVM : MainVM() {

    private var application: Application? = null
    private var storyRep: StoryRepository? = null


    var selectedTags: MutableList<String> = mutableListOf()

    private var currentStoryList: List<Story>? = null

    fun setApplicationContext(application: Application){
        this.application = application
        storyRep = application?.let { StoryRepository.getStoryRepository(it) }!!
    }

    fun CurrentStoryList(): List<Story>{
        return currentStoryList!!
        /*
        return listOf(Story("0", "0", "MAX_TESTTITLE", "/Description/ \n\n\n /End Description/", "1.1.0000", 0, ByteArray(0), 4.0, 0),
            Story("0", "0", "TAEOFENOA", "/Description/ \n\n\n /End Description/", "1.1.0000", 0, ByteArray(0), 4.0, 0),
            Story("0", "0", "ABCDEFG", "/Description/ \n\n\n /End Description/", "1.1.0000", 0, ByteArray(0), 4.0, 0)
        )*/
    }

    fun search(){
        if(selectedTags.count() == 0){
            //currentStoryList = storyRep?.get
        }else{

        }
    }

    fun allTags(): List<String>{
        val tList: MutableList<String> = mutableListOf()
        Log.d("WriteDetailsFRAGVM", (storyRep?.getAllTags() == null).toString())
        storyRep?.getAllTags()?.forEach {
            //Log.d("WriteDetailsFRAGVM", it.tagName)
            tList.add(it.tagName)
        }
        return tList
    }

    fun getStories(){
        if (storyRep != null){
            //when() to detect different kinds of search!
            currentStoryList = storyRep?.getAllStories()
        }else{
            //Throw error
        }
    }


}