package hsfl.project.e_storymaker.roomDB.Entities.tag

import androidx.room.*
import hsfl.project.e_storymaker.roomDB.Entities.rating.Rating
import hsfl.project.e_storymaker.roomDB.Entities.story.Story
import hsfl.project.e_storymaker.roomDB.Entities.user.User

@Dao
abstract class TagDao {

    @Delete
    abstract fun deleteTag(tag: Tag)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertTag(tag: Tag)

    @Query("SELECT * FROM tag WHERE tagName LIKE :tagName ")
    abstract fun getTagByTagname(tagName: String): Tag

    @Query("SELECT EXISTS(SELECT * FROM tag WHERE tagName = :tagName)")
    abstract fun rowExistByTagname(tagName : String) : Boolean

    fun insertWithTimestamp(tag: Tag) {
        insertTag(tag.apply{
            cachedTime = System.currentTimeMillis()
        })
    }

    fun cacheTags(tags: List<Tag>) {
        tags.forEach { insertWithTimestamp(it) }
    }
}