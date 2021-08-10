package hsfl.project.e_storymaker.roomDB.Entities.storyHasTag

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StoryHasTag (
    @PrimaryKey val storyHasTag_uuid: String,
    val tag_uuid: String,
    val story_uuid: String,
    var cachedTime: Long
    )