package hsfl.project.e_storymaker.roomDB.Entities.myData

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Profile(
    @PrimaryKey val not_important:Boolean,
    val jwt: String,
    val user_uuid: String
)