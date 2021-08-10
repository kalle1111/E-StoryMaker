package hsfl.project.e_storymaker.roomDB.Entities.tag

import androidx.room.*
import hsfl.project.e_storymaker.roomDB.Entities.story.Story
import hsfl.project.e_storymaker.roomDB.Entities.user.User

@Dao
abstract class TagDao {

    @Delete
    abstract fun deleteTag(tag: Tag)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertTag(tag: Tag)

    fun insertWithTimestamp(tag: Tag) {
        insertTag(tag.apply{
            cachedTime = System.currentTimeMillis()
        })
    }

    fun cacheTags(tags: List<Tag>) {
        tags.forEach { insertWithTimestamp(it) }
    }
}