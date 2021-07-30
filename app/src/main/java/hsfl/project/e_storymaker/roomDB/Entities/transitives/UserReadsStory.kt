package hsfl.project.e_storymaker.roomDB.Entities.transitives

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import hsfl.project.e_storymaker.roomDB.Entities.story.Story
import hsfl.project.e_storymaker.roomDB.Entities.user.User

@Entity
data class UserReadsStory (
    @PrimaryKey(autoGenerate = false) val UserReadsStory_uuid: String,
    @Embedded(prefix = "user_") val UserReadsStory_user: User,
    @Embedded(prefix = "story_") val UserReadsStory_story: Story,
    @ColumnInfo(name = "chapter") val chapter: Int
)