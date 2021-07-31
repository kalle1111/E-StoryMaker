package hsfl.project.e_storymaker.roomDB.Entities.rating

import androidx.room.Embedded
import androidx.room.Relation
import hsfl.project.e_storymaker.roomDB.Entities.user.User

data class RatingsFromUser (
    @Embedded val user: User,
    @Relation(
        parentColumn = "user_uuid",
        entityColumn = "rating_uuid"
    )
    val ratings: List<Rating>
)