package com.eStory.model.user

data class UpdateProfile(
    val firstname: String,
    val lastname: String,
    val description: String,
    val birthday: String,
    val image: ByteArray
)
