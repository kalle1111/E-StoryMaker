package hsfl.project.e_storymaker.viewModels.fragmentViewModels


import android.util.Log
import hsfl.project.e_storymaker.viewModels.MainVM
import hsfl.project.e_storymaker.viewModels.WritingVM

class WriteDetailsFragVM : WritingVM() {
    fun createStory(image: ByteArray?, title: String, desciption: String): Boolean {
        Log.d("WriteDetails", "title: " + title + " ; " + "descr: " + desciption)
        //TODO("SEND TO MODEL TO ATTEMPT CREATION!")
        return true
    }
}