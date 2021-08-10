package hsfl.project.e_storymaker.roomDB.Entities.chapterProgress

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hsfl.project.e_storymaker.roomDB.Entities.tag.Tag

@Dao
interface ChapterProgressDao {

    @Query("SELECT chapter FROM chapterprogress WHERE chapterprogress.user_uuid LIKE :user_uuid AND chapterProgress.story_uuid LIKE :story_uuid ")
    fun getChapter(user_uuid: String, story_uuid: String): LiveData<Int>

    @Query("UPDATE chapterprogress SET chapter = :newChapter WHERE chapterprogress.user_uuid LIKE :user_uuid AND chapterProgress.story_uuid LIKE :story_uuid ")
    fun changeChapter(user_uuid: String, story_uuid: String, newChapter: Int)

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