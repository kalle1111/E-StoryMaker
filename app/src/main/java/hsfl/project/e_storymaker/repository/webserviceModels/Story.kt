package hsfl.project.e_storymaker.repository.webserviceModels

data class Story(
    val uuid: String,
    val user: User,
    val storyTitle: String,
    val description: String,
    val createTime: String
)