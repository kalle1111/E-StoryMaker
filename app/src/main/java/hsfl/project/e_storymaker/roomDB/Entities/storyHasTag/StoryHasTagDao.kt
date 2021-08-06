package hsfl.project.e_storymaker.roomDB.Entities.storyHasTag

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
abstract class StoryHasTagDao {

    @Query("SELECT tag_uuid FROM storyhastag WHERE story_uuid LIKE :story_uuid")
    abstract fun getTagUuidsOfStory(story_uuid: String): List<String>

    @Query("SELECT story_uuid FROM storyHasTag WHERE tag_uuid LIKE :tag_uuid")
    abstract fun getStoryUuidsWithTag(tag_uuid: String): List<String>

    @Transaction
    open fun getStoryUuidsWithTags(tag_uuids: List<String>): List<String>{
        val storiesList = mutableListOf<String>()

        for (tag_uuid in tag_uuids) storiesList.addAll(getStoryUuidsWithTag(tag_uuid))

        return storiesList.toSet().toList()
    }

}