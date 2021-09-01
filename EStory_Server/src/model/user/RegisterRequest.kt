package com.eStory.model.user

data class RegisterRequest(
    val userName: String,
    val password: String,
    val description: String,
    val image: ByteArray?
)