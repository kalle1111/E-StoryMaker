package hsfl.project.e_storymaker.repository.webserviceModels.user

data class User(
    val uuid: String,
    val firstname: String,
    val lastname: String,
    val userName: String,
    val description:String,
    val birthday: String,
    val hashPassword: String,
    val image: ByteArray,
    val lastUpdate: String)