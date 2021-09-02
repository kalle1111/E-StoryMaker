package hsfl.project.e_storymaker.viewModels.fragmentViewModels



import android.app.Application
import android.util.Log
import hsfl.project.e_storymaker.models.remoteDataSource.StoryRepository
import hsfl.project.e_storymaker.repository.webserviceModels.story.StoryRequest
import hsfl.project.e_storymaker.roomDB.Entities.story.Story
import hsfl.project.e_storymaker.viewModels.WritingVM

class WriteDetailsFragVM() : WritingVM() {

    private var application: Application? = null

    private var storyRep: StoryRepository? = null

    private var story: Story = Story("", "", "", "", "", 0, ByteArray(2), 2.0, 2)

    var selectedTags: MutableList<String> = mutableListOf()

    fun setApplicationContext(application: Application, storyID: String?){
        this.application = application
        storyRep = application?.let { StoryRepository.getStoryRepository(it) }!!
        if (storyID != null){
            story = storyRep?.getStory(storyID!!)!!
        }
    }


    fun createStory(image: ByteArray?, title: String, desciption: String): String {
        //Log.d("WriteDetails", "title: " + title + " ; " + "descr: " + desciption)
        Log.d("WriteDetailsFragVM", selectedTags.toString()+ "<---------------------------")
        return storyRep?.createStory(StoryRequest(title, desciption, image), selectedTags)!!
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

    fun title(): String{
        return story?.storyTitle!!
    }

    fun description(): String{
        return story?.description!!
    }
}