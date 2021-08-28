package hsfl.project.e_storymaker.roomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hsfl.project.e_storymaker.roomDB.Entities.chapter.Chapter
import hsfl.project.e_storymaker.roomDB.Entities.chapter.ChapterDao
import hsfl.project.e_storymaker.roomDB.Entities.chapterProgress.ChapterProgress
import hsfl.project.e_storymaker.roomDB.Entities.chapterProgress.ChapterProgressDao
import hsfl.project.e_storymaker.roomDB.Entities.favoring.Favoring
import hsfl.project.e_storymaker.roomDB.Entities.favoring.FavoringDao

import hsfl.project.e_storymaker.roomDB.Entities.friendship.Friendship
import hsfl.project.e_storymaker.roomDB.Entities.friendship.FriendshipDao
import hsfl.project.e_storymaker.roomDB.Entities.rating.Rating
import hsfl.project.e_storymaker.roomDB.Entities.rating.RatingDao

import hsfl.project.e_storymaker.roomDB.Entities.story.Story
import hsfl.project.e_storymaker.roomDB.Entities.tag.Tag
import hsfl.project.e_storymaker.roomDB.Entities.story.StoryDao
import hsfl.project.e_storymaker.roomDB.Entities.tag.TagDao

import hsfl.project.e_storymaker.roomDB.Entities.user.User
import hsfl.project.e_storymaker.roomDB.Entities.user.UserDao



@Database(
    entities = [
        User::class,
        Story::class,
        Friendship::class,
        Tag::class,
        ChapterProgress::class,
        Favoring::class,
        Rating::class,
        Chapter::class],
    version = 1,
    exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun storyDao(): StoryDao
    abstract fun friendshipDao(): FriendshipDao
    abstract fun chapterProgressDao(): ChapterProgressDao
    abstract fun favoringDao(): FavoringDao
    abstract fun ratingDao(): RatingDao
    abstract fun chapterDao(): ChapterDao
    abstract fun tagDao(): TagDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}