package hsfl.project.e_storymaker.roomDB.Entities.story

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Tag (
    @PrimaryKey(autoGenerate = false) val uuid: String,
    @ColumnInfo(name = "name") val name: String
)