package hsfl.project.e_storymaker.roomDB.Entities.favoring

import androidx.room.Embedded
import androidx.room.Relation
import hsfl.project.e_storymaker.roomDB.Entities.user.User

data class FavoritesOfUser(
    @Embedded val user: User,
    @Relation(
        parentColumn = "user_uuid",
        entityColumn = "favoring_uuid"
    )
    val favorings: List<Favoring>
)
