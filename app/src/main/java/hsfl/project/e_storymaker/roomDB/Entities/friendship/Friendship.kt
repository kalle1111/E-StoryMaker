package hsfl.project.e_storymaker.roomDB.Entities.friendship

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import hsfl.project.e_storymaker.roomDB.Entities.user.User

@Entity
data class Friendship(
    @PrimaryKey(autoGenerate = false) val uuid: String,
    @Embedded(prefix = "requester_") val requester: User,
    @Embedded(prefix = "target_") val target: User,
    @ColumnInfo(name = "isAccepted") val isAccepted: Boolean,
    @ColumnInfo(name = "requestTime") val requestTime: String
)