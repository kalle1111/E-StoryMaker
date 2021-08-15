package com.eStory.model.readChapter

import com.eStory.model.story.Story
import com.eStory.model.user.User

class ReadChapter(val uuid: String, val story: Story, val user: User, val chapterNumber: Int)
