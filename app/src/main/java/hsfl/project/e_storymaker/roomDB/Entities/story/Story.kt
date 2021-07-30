package hsfl.project.e_storymaker.roomDB.Entities.story

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import hsfl.project.e_storymaker.roomDB.Entities.user.User

@Entity
data class Story(
    @PrimaryKey(autoGenerate = false) val story_uuid: String,
    @Embedded val story_author: User,
    @ColumnInfo(name = "storyTitle") val storyTitle: String,
    @ColumnInfo(name = "releaseDate") val releaseDate: String,
    @ColumnInfo(name = "ageRestriction") val ageRestriction: Int,
    @ColumnInfo(name = "description") val story_description: String,
    @ColumnInfo(name = "storyUrl") val storyUrl: String,
    @ColumnInfo(name = "coverUrl") val coverUrl: String
)