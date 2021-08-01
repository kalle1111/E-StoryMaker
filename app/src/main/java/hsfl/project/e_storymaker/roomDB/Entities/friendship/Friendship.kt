package hsfl.project.e_storymaker.roomDB.Entities.friendship

import androidx.room.*
import hsfl.project.e_storymaker.roomDB.Entities.user.User

@Entity
data class Friendship(
    @PrimaryKey val friendShip_uuid: String,
    val requester_uuid: String,
    val target_uuid: String,
    val isAccepted: Boolean,
    val requestTime: String
)