package com.eStory.models.story

import com.eStory.model.user.User
import kotlinx.serialization.Serializable

@Serializable
class Story(
    val uuid: String,
    val user: User,
    val storyTitle: String,
    val description: String,
    val createTime: String
)