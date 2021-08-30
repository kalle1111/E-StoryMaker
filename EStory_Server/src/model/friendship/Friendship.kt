package com.eStory.model.friendship

import com.eStory.model.user.User
import kotlinx.serialization.Serializable

// serializable class for inserting new friendships
@Serializable
class Friendship(
    val uuid: String,
    val requesterUser: User,
    val friend: User,
    val isAccepted: Boolean,
    val requestTime: Long
)