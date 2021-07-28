package hsfl.project.e_storymaker.roomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import hsfl.project.e_storymaker.roomDB.Entities.friendship.Friendship
import hsfl.project.e_storymaker.roomDB.Entities.friendship.FriendshipDao

import hsfl.project.e_storymaker.roomDB.Entities.story.Story
import hsfl.project.e_storymaker.roomDB.Entities.story.Tag
import hsfl.project.e_storymaker.roomDB.Entities.story.Audiobook
import hsfl.project.e_storymaker.roomDB.Entities.story.StoryDao
import hsfl.project.e_storymaker.roomDB.Entities.transitives.StoryHasTag
import hsfl.project.e_storymaker.roomDB.Entities.transitives.UserFavorsStory
import hsfl.project.e_storymaker.roomDB.Entities.transitives.UserRatesStory
import hsfl.project.e_storymaker.roomDB.Entities.transitives.UserReadsStory
import hsfl.project.e_storymaker.roomDB.Entities.transitives.UserSubscribesUser

import hsfl.project.e_storymaker.roomDB.Entities.user.User
import hsfl.project.e_storymaker.roomDB.Entities.user.UserDao



@Database(
    entities = [User::class, Story::class, Friendship::class,
        Audiobook::class, Tag::class, StoryHasTag::class,
        UserFavorsStory::class, UserRatesStory::class, UserReadsStory::class,
        UserSubscribesUser::class],
    version = 1,
    exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun storyDao(): StoryDao
    abstract fun friendshipDao(): FriendshipDao

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