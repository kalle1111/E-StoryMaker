package hsfl.project.e_storymaker.roomDB.Entities.transitives

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import hsfl.project.e_storymaker.roomDB.Entities.story.Story
import hsfl.project.e_storymaker.roomDB.Entities.story.Tag

@Entity
data class StoryHasTag (
    @PrimaryKey(autoGenerate = false) val StoryHasTag_uuid: String,
    @Embedded val StoryHasTag_story: Story,
    @Embedded val StoryHasTag_tag: Tag
)