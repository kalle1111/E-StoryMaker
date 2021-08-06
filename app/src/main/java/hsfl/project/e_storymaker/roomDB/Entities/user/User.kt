package hsfl.project.e_storymaker.roomDB.Entities.user

import androidx.room.*
import kotlinx.serialization.Serializable

@Entity
@Serializable
@SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
data class User(
    @PrimaryKey(autoGenerate = false) val user_uuid: String,
    val firstname: String,
    val lastname: String,
    val userName: String,
    val user_description: String,
    val birthday: String,
    val hashPassword: String,
    var cachedTime: Long
)

