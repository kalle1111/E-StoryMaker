package hsfl.project.e_storymaker.repository.webserviceModels

data class UpdateStoryRequest (val uuid: String, val storyTitle: String, val description: String, val storyChapters: String, val cover:ByteArray)