package hsfl.project.e_storymaker.viewModels.fragmentViewModels


import hsfl.project.e_storymaker.viewModels.MainVM
import hsfl.project.e_storymaker.viewModels.ReadingVM

class ReadOverviewFragVM : ReadingVM() {
    val title = "/Story Title/"
    val tagList = "/TAGS/\n\n/END TAGS/"
    val description = "/Description/ \n\n\n /End Description/"
    val _rating = 4.2
    val Rating get() = _rating.toString()
}