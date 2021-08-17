package hsfl.project.e_storymaker.viewModels.fragmentViewModels


import android.app.Application
import android.util.Log
import hsfl.project.e_storymaker.models.remoteDataSource.StoryRepository
import hsfl.project.e_storymaker.roomDB.AppDatabase_Impl
import hsfl.project.e_storymaker.viewModels.MainVM
import hsfl.project.e_storymaker.viewModels.WritingVM

class WriteChapterFragVM : WritingVM() {

    private var application: Application? = null
    private var storyRep: StoryRepository? = null

    fun setApplicationContext(application: Application){
        this.application = application
        storyRep = application?.let { StoryRepository.getStoryRepository(it) }!!
    }

    fun createChapter(title: String, content: String): Boolean {
        Log.d("WriteChapter", "ch title: " + title + " ; " + "ch content: " + content)
        //storyRep?.chapter
        return true
        TODO("CREATE CHAPTER? HENRY?")
    }
}