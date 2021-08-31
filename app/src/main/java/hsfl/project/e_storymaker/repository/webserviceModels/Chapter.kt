package hsfl.project.e_storymaker.repository.webserviceModels

class Chapter (
    val uuid: String,
    val storyId: String,
    val title: String,
    val content: String,
    val index: Int,
    val createTime: Long,
    var lastUpdate: Long
)