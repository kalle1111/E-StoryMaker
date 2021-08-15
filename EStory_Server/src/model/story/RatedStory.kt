package com.eStory.model.story

import com.eStory.model.user.User

class RatedStory(
    val uuid: String,
    val user: User,
    val story: Story,
    val ratingOverallValue: Int,
    val ratingStyleValue: Int,
    val ratingStoryValue: Int,
    val ratingGrammarValue: Int,
    val ratingCharacterValue: Int,
    val lastUpdate: Long
)