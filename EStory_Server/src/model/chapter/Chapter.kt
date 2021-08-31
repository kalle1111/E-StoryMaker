package com.eStory.model.chapter

import kotlinx.serialization.Serializable

@Serializable
class Chapter(
    val uuid: String,
    val storyId: String,
    val title: String,
    val content: String,
    val index: Int,
    val createTime: Long,
    var lastUpdate: Long
)