package hsfl.project.e_storymaker.roomDB.Entities.story

import androidx.room.*

@Dao
abstract class StoryDao {

    @Delete
    abstract fun delete(story: Story)

    @Query("SELECT * FROM story WHERE storyTitle LIKE :title LIMIT 1")
    abstract fun getStory(title: String): Story

    @Query("SELECT * FROM story ORDER BY story_uuid ASC")
    abstract fun getAllStories(): List<Story>

    @Update
    abstract fun changeStory(story: Story)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun cacheStories(stories: List<Story>)

    @Query("SELECT * FROM story WHERE story_uuid LIKE :story_uuid")
    abstract fun getStoryByUuid(story_uuid : String): Story

    @Transaction
    open fun getStoriesByUuids(story_uuids : List<String>): List<Story>{

        val storiesList = mutableListOf<Story>()

        for(story_uuid in story_uuids) storiesList.add(getStoryByUuid(story_uuid))

        return storiesList
    }
}