package hsfl.project.e_storymaker.viewModels.fragmentViewModels


import android.app.Application
import android.util.Log
import hsfl.project.e_storymaker.models.remoteDataSource.StoryRepository
import hsfl.project.e_storymaker.repository.webserviceModels.chapter.InsertChapterRequest
import hsfl.project.e_storymaker.viewModels.WritingVM

class WriteChapterFragVM : WritingVM() {

    private var application: Application? = null
    private var storyRep: StoryRepository? = null
    var storyID: String? = null

    fun setApplicationContext(application: Application){
        this.application = application
        storyRep = application?.let { StoryRepository.getStoryRepository(it) }!!
    }

    fun createChapter(storyID: String, title: String, content: String, index: Int): Boolean {
        Log.d("WriteChapter", "ch title: " + title + " ; " + "ch content: " + content + " sID: " +storyID)
        Log.d("WriteChapterFragVM", storyRep?.getAllChaptersOfStory(storyID)?.count()!!.toString())
        return storyRep?.createChapter(InsertChapterRequest(storyID, title, content, storyRep?.getAllChaptersOfStory(storyID)?.count()!!))!!
    }
}