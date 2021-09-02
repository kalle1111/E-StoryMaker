package hsfl.project.e_storymaker.repository.webserviceModels.friendship

import hsfl.project.e_storymaker.repository.webserviceModels.user.User

data class Friendship (val uuid:String, val requesterUser: User, val friend: User, val isAccepted:Boolean, val requestTime:String )