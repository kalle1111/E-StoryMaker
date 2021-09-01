package hsfl.project.e_storymaker.roomDB.Entities.user

import androidx.room.*
import kotlinx.serialization.Serializable

@Entity
@Serializable
@SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
data class User(
    @PrimaryKey(autoGenerate = false) val username: String,
    val user_description: String,
    val hashPassword: String,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB) val image: ByteArray,
    var cachedTime: Long
)

