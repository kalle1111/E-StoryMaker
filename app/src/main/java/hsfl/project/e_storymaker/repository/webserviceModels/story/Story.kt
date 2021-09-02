package hsfl.project.e_storymaker.repository.webserviceModels.story

import hsfl.project.e_storymaker.repository.webserviceModels.user.User

data class Story(
    val uuid: String,
    val user: User,
    val storyTitle: String,
    val description: String,
    val createTime: Long,
    val lastUpdate: Long,
    val storyChapters: String,
    val averageRating: Double,
    val cover: ByteArray?
)