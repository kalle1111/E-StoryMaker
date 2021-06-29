package com.eStory.model.user

data class RegisterRequest(
    val firstname: String,
    val lastname: String,
    val userName: String,
    val birthday: String,
    val password: String,
    val description: String
)