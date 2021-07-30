package hsfl.project.e_storymaker.roomDB.Entities.transitives

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import hsfl.project.e_storymaker.roomDB.Entities.user.User

@Entity
data class UserSubscribesUser (
    @PrimaryKey(autoGenerate = false) val uuid: String,
    @Embedded val subscriber: User,
    @Embedded val subscribed: User
)