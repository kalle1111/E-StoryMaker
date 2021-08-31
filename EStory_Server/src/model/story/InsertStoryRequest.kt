package com.eStory.model.story

import com.eStory.model.chapter.Chapter
import com.eStory.model.chapter.InsertChapterRequest

data class InsertStoryRequest(
    val storyTitle: String,
    val description: String,
    val insertFirstChapter: InsertChapterRequest,
    val cover: ByteArray?
)
