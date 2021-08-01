package hsfl.project.e_storymaker.repository.webserviceModels

data class Friendship (val uuid:String, val requesterUser: User, val friend: User, val isAccepted:Boolean, val requestTime:String )