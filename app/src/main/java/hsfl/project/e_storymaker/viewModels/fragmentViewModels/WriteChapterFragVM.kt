package hsfl.project.e_storymaker.viewModels.fragmentViewModels


import android.app.Application
import android.util.Log
import hsfl.project.e_storymaker.models.remoteDataSource.StoryRepository
import hsfl.project.e_storymaker.repository.webserviceModels.InsertChapterRequest
import hsfl.project.e_storymaker.roomDB.AppDatabase_Impl
import hsfl.project.e_storymaker.viewModels.MainVM
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
        return storyRep?.createChapter(InsertChapterRequest(storyID, title, content, 0))!!
    }
}