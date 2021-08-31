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

    @Query("SELECT * FROM tag WHERE tag_uuid LIKE :tag_uuid ")
    abstract fun getTagByUUID(tag_uuid: String):Tag

    @Query("SELECT EXISTS(SELECT * FROM tag WHERE tag_uuid = :tag_uuid)")
    abstract fun rowExistByUUID(tag_uuid : String) : Boolean

    fun insertWithTimestamp(tag: Tag) {
        insertTag(tag.apply{
            cachedTime = System.currentTimeMillis()
        })
    }

    fun cacheTags(tags: List<Tag>) {
        tags.forEach { insertWithTimestamp(it) }
    }
}