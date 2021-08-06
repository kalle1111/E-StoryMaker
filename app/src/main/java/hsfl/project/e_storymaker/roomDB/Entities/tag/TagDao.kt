package hsfl.project.e_storymaker.roomDB.Entities.tag

import androidx.room.*
import hsfl.project.e_storymaker.roomDB.Entities.story.Story

@Dao
abstract class TagDao {

    @Delete
    abstract fun deleteTag(tag: Tag)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun cacheTags(ratings: List<Tag>)
}