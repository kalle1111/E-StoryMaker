package hsfl.project.e_storymaker.roomDB.Entities.chapterProgress

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ChapterProgress (
    @PrimaryKey val chapterProgress_uuid: String,
    val chapter: Int,
    val user_username: String,
    val story_uuid: String,
    var cachedTime: Long
)