package hsfl.project.e_storymaker.roomDB.Entities.chapterProgress

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ChapterProgressDao {

    @Query("SELECT chapter FROM chapterprogress WHERE chapterprogress.user_uuid LIKE :user_uuid AND chapterProgress.story_uuid LIKE :story_uuid ")
    fun getChapter(user_uuid: String, story_uuid: String): Int


    @Query("UPDATE chapterprogress SET chapter = :newChapter WHERE chapterprogress.user_uuid LIKE :user_uuid AND chapterProgress.story_uuid LIKE :story_uuid ")
    fun changeChapter(user_uuid: String, story_uuid: String, newChapter: Int)
}