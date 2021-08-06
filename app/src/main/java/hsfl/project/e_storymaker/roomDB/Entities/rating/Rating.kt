package hsfl.project.e_storymaker.roomDB.Entities.rating

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Rating (
    @PrimaryKey val rating_uuid: String,
    val rating_overall: Int,
    val rating_style: Int,
    val rating_story: Int,
    val rating_grammar: Int,
    val rating_character: Int,
    val user_uuid: String,
    val story_uuid: String
)