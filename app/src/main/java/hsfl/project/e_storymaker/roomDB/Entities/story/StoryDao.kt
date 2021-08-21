package hsfl.project.e_storymaker.roomDB.Entities.story

import androidx.lifecycle.LiveData
import androidx.room.*
import hsfl.project.e_storymaker.roomDB.Entities.tag.Tag
import hsfl.project.e_storymaker.roomDB.Entities.user.User

@Dao
abstract class StoryDao {

    @Delete
    abstract fun delete(story: Story)

    @Query("SELECT * FROM story WHERE storyTitle LIKE :title LIMIT 1")
    abstract fun getStory(title: String): LiveData<Story>

    @Query("SELECT * FROM story ORDER BY story_uuid ASC")
    abstract fun getAllStories(): LiveData<List<Story>>

    @Update
    abstract fun changeStory(story: Story)

    @Query("SELECT * FROM story WHERE story_uuid LIKE :story_uuid ")
    abstract fun getStoryByUuid(story_uuid : String): Story

    @Query("SELECT author_username FROM story WHERE story_uuid LIKE :story_uuid LIMIT 1 ")
    abstract fun getAuthorUuidByStoryUuid(story_uuid: String): LiveData<String>

    @Transaction
    open fun getStoriesByUuids(story_uuids : List<String>): List<Story>{

        val storiesList = mutableListOf<Story>()

        for(story_uuid in story_uuids) storiesList.add(getStoryByUuid(story_uuid))

        return storiesList
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertStory(story: Story)

    fun insertWithTimestamp(story: Story) {
        insertStory(story.apply{
            cachedTime = System.currentTimeMillis()
        })
    }

    fun cacheStories(stories: List<Story>) {
        stories.forEach { insertWithTimestamp(it) }
    }
}