package hsfl.project.e_storymaker.roomDB.Entities.story

import androidx.room.*
import hsfl.project.e_storymaker.roomDB.Entities.user.User

@Entity
@SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
data class Story(
    @PrimaryKey(autoGenerate = false) val story_uuid: String,
    val author_uuid: String,
    val storyTitle: String,
    val description: String,
    val releaseDate: String,
    val ageRestriction: Int,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB) val cover: ByteArray,
    val avgRating: Double,
    var cachedTime: Long
)

