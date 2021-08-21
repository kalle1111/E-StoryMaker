package hsfl.project.e_storymaker.viewModels.fragmentViewModels


import android.app.Application
import android.util.Log
import hsfl.project.e_storymaker.models.remoteDataSource.StoryRepository
import hsfl.project.e_storymaker.models.remoteDataSource.UserRepository
import hsfl.project.e_storymaker.roomDB.Entities.favoring.Favoring
import hsfl.project.e_storymaker.roomDB.Entities.story.Story
import hsfl.project.e_storymaker.viewModels.MainVM
import kotlin.reflect.jvm.internal.impl.renderer.KeywordStringsGenerated

class LibraryFragVM : MainVM() {

    private var application: Application? = null
    private var storyRep: StoryRepository? = null
    private var userRep: UserRepository? = null

    private var currentStoryList: List<Story>? = null

    fun setApplicationContext(application: Application){
        this.application = application
        storyRep = application?.let { StoryRepository.getStoryRepository(it) }!!
        userRep = application?.let { UserRepository.getStoryRepository(it) }!!
    }

    fun CurrentStoryList(): List<Story>{
        return listOf(Story("0", "0", "MAX_TESTTITLE", "/Description/ \n\n\n /End Description/", "1.1.0000", 0, ByteArray(0), 4.0, 0),
            Story("0", "0", "TAEOFENOA", "/Description/ \n\n\n /End Description/", "1.1.0000", 0, ByteArray(0), 4.0, 0),
            Story("0", "0", "ABCDEFG", "/Description/ \n\n\n /End Description/", "1.1.0000", 0, ByteArray(0), 4.0, 0)
        )
    //return currentStoryList!!
    }

    var libraryMode: Int = 0
    //0 = unfiltered
    //1 = original
    //2 = favorited
    //3 = reviewed

    fun getStories(){
        if (storyRep != null && false){
            when(libraryMode){
                0 -> currentStoryList = storyRep?.getAllStories("2")

                1 -> currentStoryList = storyRep?.getMyStories("WHY thIS HERE")

                //2 -> currentStoryList = Favoring() storyRep?.getMyFavoriteStories("Why")

               //3 -> currentStoryList = storyRep?.getMyRatedStories("WHY")
                else -> Log.d("LibraryFrag", "Lib Mode Error")
            }
        }else{
            //Throw Error
        }
    }

    fun username(): String{
        if (userRep != null && false){
            return userRep?.getMyProfile("WHY")?.username!!
        }else{
            return "MAX_MUSTERMANNS's Library"
        }
    }



}