package com.eStory.model.tag

import com.eStory.model.story.Story
import java.util.*

data class TaggedStory(val uuid: String, val tag: Tag, val story: Story)
