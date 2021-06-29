package com.eStory.model.friendship

import com.eStory.model.user.User

data class FriendshipRequest(val friend: User,val isAccepted:Boolean , val requestTime:String )
