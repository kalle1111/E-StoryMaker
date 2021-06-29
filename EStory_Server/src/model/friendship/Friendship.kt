package com.eStory.model.friendship

import com.eStory.model.user.User
import kotlinx.serialization.Serializable

@Serializable
class Friendship (val uuid:String, val requesterUser: User, val friend: User, val isAccepted:Boolean, val requestTime:String )