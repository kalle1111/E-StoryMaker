package com.eStory.model.chapter

data class InsertChapterRequest(val storyId: String,
                                val title: String,
                                val content: String,
                                val index: Int,
                                )
