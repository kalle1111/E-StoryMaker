package hsfl.project.e_storymaker.roomDB.Entities.chapterProgress

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
abstract class ChapterProgressDao {

    @Query("SELECT chapter FROM chapterprogress WHERE chapterprogress.user_username LIKE :user_username AND chapterProgress.story_uuid LIKE :story_uuid ")
    abstract fun getChapter(user_username: String, story_uuid: String): LiveData<Int>

    @Query("UPDATE chapterprogress SET chapter = :newChapter WHERE chapterprogress.user_username LIKE :user_username AND chapterProgress.story_uuid LIKE :story_uuid ")
    abstract fun changeChapter(user_username: String, story_uuid: String, newChapter: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertChapterProgress(progress: ChapterProgress)

    fun insertWithTimestamp(progress: ChapterProgress) {
        insertChapterProgress(progress.apply{
            cachedTime = System.currentTimeMillis()
        })
    }

    fun cacheTags(progresses: List<ChapterProgress>) {
        progresses.forEach { insertWithTimestamp(it) }
    }
}