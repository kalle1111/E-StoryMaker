package hsfl.project.e_storymaker.roomDB.Entities.favoring

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favoring (
    @PrimaryKey val favoring_uuid: String,
    val isFavoring: Boolean,
    val user_uuid: String,
    val story_uuid: String,
    var cachedTime: Long
)