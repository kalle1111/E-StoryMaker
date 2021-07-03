package com.eStory.model.story

import com.eStory.model.user.User
import com.eStory.models.story.Story


class StoryAsFavorite(val uuid: String, val user: User, val story: Story)