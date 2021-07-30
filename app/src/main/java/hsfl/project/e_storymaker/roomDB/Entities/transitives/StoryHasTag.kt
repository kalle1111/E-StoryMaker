package hsfl.project.e_storymaker.roomDB.Entities.transitives

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import hsfl.project.e_storymaker.roomDB.Entities.story.Story
import hsfl.project.e_storymaker.roomDB.Entities.story.Tag

@Entity
data class StoryHasTag (
    @PrimaryKey(autoGenerate = false) val uuid: String,
    @Embedded val story: Story,
    @Embedded val tag: Tag
)