package com.eStory.model.story

data class UpdateStoryRequest(
    val uuid: String,
    val storyTitle: String,
    val description: String,
    val storyChapters: String,
    val cover: ByteArray?
) {

}
