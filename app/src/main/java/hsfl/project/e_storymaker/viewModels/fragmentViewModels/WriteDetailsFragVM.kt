package hsfl.project.e_storymaker.viewModels.fragmentViewModels



import android.app.Application
import android.content.Context
import android.util.Log
import androidx.annotation.UiThread
import hsfl.project.e_storymaker.models.remoteDataSource.StoryRepository
import hsfl.project.e_storymaker.repository.webserviceModels.StoryRequest
import hsfl.project.e_storymaker.viewModels.MainVM
import hsfl.project.e_storymaker.viewModels.WritingVM
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.coroutineContext

class WriteDetailsFragVM() : WritingVM() {

    private var application: Application? = null

    private var storyRep: StoryRepository? = null

    fun setApplicationContext(application: Application){
        this.application = application
        storyRep = application?.let { StoryRepository.getStoryRepository(it) }!!
    }

    @UiThread
    fun createStory(image: ByteArray?, title: String, desciption: String): Boolean {
        Log.d("WriteDetails", "title: " + title + " ; " + "descr: " + desciption)
        suspend {
            storyRep?.createStory("a", StoryRequest(title, desciption))
            Log.d("WriteDetails", "MODEL REQUEST SENT")
        }
        return true
    }
}