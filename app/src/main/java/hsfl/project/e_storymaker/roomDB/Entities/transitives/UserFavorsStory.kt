package hsfl.project.e_storymaker.roomDB.Entities.transitives

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import hsfl.project.e_storymaker.roomDB.Entities.story.Story
import hsfl.project.e_storymaker.roomDB.Entities.user.User

@Entity
data class UserFavorsStory (
    @PrimaryKey(autoGenerate = false) val uuid: String,
    @Embedded val user: User,
    @Embedded val story: Story,
    @ColumnInfo(name = "isFavoring") val isFavoring: Boolean
)