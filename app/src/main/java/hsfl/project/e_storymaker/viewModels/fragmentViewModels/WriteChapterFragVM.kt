package hsfl.project.e_storymaker.viewModels.fragmentViewModels


import android.util.Log
import hsfl.project.e_storymaker.viewModels.MainVM
import hsfl.project.e_storymaker.viewModels.WritingVM

class WriteChapterFragVM : WritingVM() {


    fun createChapter(title: String, content: String): Boolean {
        Log.d("WriteChapter", "ch title: " + title + " ; " + "ch content: " + content)


        return true
    }
}