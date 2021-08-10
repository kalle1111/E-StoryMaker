package hsfl.project.e_chaptermaker.roomDB.Entities.chapter

import androidx.lifecycle.LiveData
import androidx.room.*
import hsfl.project.e_storymaker.roomDB.Entities.chapter.Chapter

@Dao
abstract class ChapterDao {

    @Delete
    abstract fun delete(chapter: Chapter)

    @Query("SELECT * FROM chapter WHERE chapter_uuid LIKE :chapter_uuid")
    abstract fun getChapterByUuid(chapter_uuid : String): Chapter

    @Query("SELECT * FROM chapter ORDER BY chapter_uuid ASC")
    abstract fun getAllChapters(): LiveData<List<Chapter>>

    @Update
    abstract fun changeChapter(chapter: Chapter)

    @Transaction
    open fun getChaptersByUuids(chapter_uuids : List<String>): List<Chapter>{

        val chaptersList = mutableListOf<Chapter>()

        for(chapter_uuid in chapter_uuids) chaptersList.add(getChapterByUuid(chapter_uuid))

        return chaptersList
    }

    @Query("SELECT * FROM chapter WHERE story_uuid LIKE :story_uuid ORDER BY chapter_index ASC")
    abstract fun getChaptersByStoryUuid(story_uuid: String): LiveData<List<Chapter>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertChapter(chapter: Chapter)

    fun insertWithTimestamp(chapter: Chapter) {
        insertChapter(chapter.apply{
            cachedTime = System.currentTimeMillis()
        })
    }

    fun cacheChapters(chapters: List<Chapter>) {
        chapters.forEach { insertWithTimestamp(it) }
    }
}