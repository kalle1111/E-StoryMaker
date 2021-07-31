package hsfl.project.e_storymaker.roomDB.Entities.rating

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Rating (
    @PrimaryKey val rating_uuid: String,
    val rating: Int,
    val user_uuid: String,
    val story_uuid: String
)