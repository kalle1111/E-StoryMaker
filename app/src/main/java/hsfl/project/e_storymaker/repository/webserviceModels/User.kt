package hsfl.project.e_storymaker.repository.webserviceModels

import java.util.*

data class User(
    val uuid: String,
    val firstname: String,
    val lastname: String,
    val userName: String,
    val description:String,
    val birthday: String,
    val password: String)