package com.eStory.model.user

import io.ktor.auth.*
import kotlinx.serialization.Serializable



@Serializable
class User(
    val uuid: String,
    val firstname: String,
    val lastname: String,
    val userName: String,
    val description:String,
    val birthday: String,
    val hashPassword: String
) : Principal