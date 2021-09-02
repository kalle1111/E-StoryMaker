package hsfl.project.e_storymaker.roomDB.Entities.storyHasTag

import androidx.lifecycle.LiveData
import androidx.room.*
import hsfl.project.e_storymaker.roomDB.Entities.tag.Tag

@Dao
abstract class StoryHasTagDao {

    @Query("SELECT tag_uuid FROM storyhastag WHERE story_uuid LIKE :story_uuid")
    abstract fun getTagUuidsOfStory(story_uuid: String): LiveData<List<String>>

    @Query("SELECT story_uuid FROM storyHasTag WHERE tag_uuid LIKE :tag_uuid")
    abstract fun getStoryUuidsWithTag(tag_uuid: String): List<String>

    @Transaction
    open fun getStoryUuidsWithTags(tag_uuids: List<String>): List<String>{
        val storiesList = mutableListOf<String>()

        for (tag_uuid in tag_uuids) storiesList.addAll(getStoryUuidsWithTag(tag_uuid))

        return storiesList.toSet().toList()
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertStoryHasTag(sht: StoryHasTag)

    fun insertWithTimestamp(sht: StoryHasTag) {
        insertStoryHasTag(sht.apply{
            cachedTime = System.currentTimeMillis()
        })
    }

}