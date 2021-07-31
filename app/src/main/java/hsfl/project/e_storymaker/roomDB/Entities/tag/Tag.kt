package hsfl.project.e_storymaker.roomDB.Entities.tag

import androidx.room.*

@Entity
@SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
data class Tag (
    @PrimaryKey(autoGenerate = false) val tag_uuid: String,
    @ColumnInfo(name = "tagName") val tagName: String
)