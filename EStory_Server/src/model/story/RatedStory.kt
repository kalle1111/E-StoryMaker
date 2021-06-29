package com.eStory.model.story

import com.eStory.models.story.Story
import com.eStory.model.user.User

class RatedStory(val uuid: String, val user: User, val story: Story, val isFavorite: Boolean, val ratingValue: Int)