package hsfl.project.e_storymaker.roomDB.Entities.chapter

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Chapter (
    @PrimaryKey val chapter_uuid: String,
    val story_uuid: String,
    val chapter_Title: String,
    val content: String,
    val chapter_index: Int,
    var cachedTime: Long
)