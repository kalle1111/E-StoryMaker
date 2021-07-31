package hsfl.project.e_storymaker.roomDB.Entities.story

import androidx.room.*

@Dao
interface StoryDao {

    @Delete
    fun delete(story: Story)

    @Query("SELECT * FROM story WHERE storyTitle LIKE :title LIMIT 1")
    fun getStory(title: String): Story

    @Query("SELECT * FROM story ORDER BY story_uuid ASC")
    fun getAllStories(): List<Story>

    @Update
    fun changeStory(story: Story)

}