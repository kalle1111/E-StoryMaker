package hsfl.project.e_storymaker.roomDB.Entities.story

import androidx.room.*
import hsfl.project.e_storymaker.roomDB.Entities.user.User

@Entity
@SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
data class Story(
    @PrimaryKey(autoGenerate = false) val story_uuid: String,
    @ColumnInfo(name = "author_uuid") val author_uuid: String,
    @ColumnInfo(name = "storyTitle") val storyTitle: String,
    @ColumnInfo(name = "releaseDate") val releaseDate: String,
    @ColumnInfo(name = "ageRestriction") val ageRestriction: Int,
    @ColumnInfo(name = "storyDescription") val storyDescription: String,
    @ColumnInfo(name = "storyUrl") val storyUrl: String,
    @ColumnInfo(name = "coverUrl") val coverUrl: String
)