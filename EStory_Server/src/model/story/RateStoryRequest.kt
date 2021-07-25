package com.eStory.model.story

data class RateStoryRequest(
    val storyId: String,
    val ratingOverallValue: Int,
    val ratingStyleValue: Int,
    val ratingStoryValue: Int,
    val ratingGrammarValue: Int,
    val ratingCharacterValue: Int
)
