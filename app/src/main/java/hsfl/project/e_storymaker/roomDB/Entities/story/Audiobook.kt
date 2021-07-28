package hsfl.project.e_storymaker.roomDB.Entities.story

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import hsfl.project.e_storymaker.roomDB.Entities.user.User

@Entity
data class Audiobook (
    @PrimaryKey(autoGenerate = false) val uuid: String,
    @Embedded val story: Story,
    @ColumnInfo(name = "ratingValue") val ratingValue: Int,
    @ColumnInfo(name = "releaseDate") val releaseDate: String
)