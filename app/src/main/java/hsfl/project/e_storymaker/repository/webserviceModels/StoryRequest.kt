package hsfl.project.e_storymaker.repository.webserviceModels

class StoryRequest(
    val storyTitle: String,
    val description: String,
    val insertFirstChapter: InsertChapterRequest,
    val cover: ByteArray?
)