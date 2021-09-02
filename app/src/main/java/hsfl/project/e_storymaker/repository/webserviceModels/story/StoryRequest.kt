package hsfl.project.e_storymaker.repository.webserviceModels.story

class StoryRequest(
    val storyTitle: String,
    val description: String,
    val cover: ByteArray?
)