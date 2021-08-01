package hsfl.project.e_storymaker.roomDB.Entities.user

import androidx.room.*
import kotlinx.serialization.Serializable

@Entity
@Serializable
@SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
data class User(
    @PrimaryKey(autoGenerate = false) val user_uuid: String,
    @ColumnInfo(name = "firstname") val firstname: String,
    @ColumnInfo(name = "lastname") val lastname: String,
    @ColumnInfo(name = "userName") val userName: String,
    @ColumnInfo(name = "user_description") val user_description: String,
    @ColumnInfo(name = "birthday") val birthday: String,
    @ColumnInfo(name = "hashPassword") val hashPassword: String
)

