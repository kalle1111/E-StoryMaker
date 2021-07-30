package hsfl.project.e_storymaker.roomDB.Entities.transitives

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import hsfl.project.e_storymaker.roomDB.Entities.story.Story
import hsfl.project.e_storymaker.roomDB.Entities.user.User

@Entity
data class UserRatesStory (
    @PrimaryKey(autoGenerate = false) val UserRatesStory_uuid: String,
    @Embedded val UserRatesStory_user: User,
    @Embedded val UserRatesStory_story: Story,
    @ColumnInfo(name = "ratingValue") val ratingValue: Int
)