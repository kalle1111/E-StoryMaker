package com.eStory.model.story

data class RateStoryRequest(val storyId:String , val isFavorite: Boolean, val ratingValue: Int )
