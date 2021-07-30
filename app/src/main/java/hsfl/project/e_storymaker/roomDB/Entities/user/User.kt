package hsfl.project.e_storymaker.roomDB.Entities.user

import androidx.room.*
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class User(
    @PrimaryKey(autoGenerate = false) val uuid: String,
    @ColumnInfo(name = "firstname") val firstname: String,
    @ColumnInfo(name = "lastname") val lastname: String,
    @ColumnInfo(name = "userName") val userName: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "birthday") val birthday: String,
    @ColumnInfo(name = "hashPassword") val hashPassword: String
)

