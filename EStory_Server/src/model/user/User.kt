package com.eStory.model.user

import io.ktor.auth.*
import kotlinx.serialization.Serializable


@Serializable
class User(
    val uuid: String,
    val userName: String,
    val description: String,
    val hashPassword: String,
    val image: ByteArray?,
    val lastUpdate: Long
) : Principal