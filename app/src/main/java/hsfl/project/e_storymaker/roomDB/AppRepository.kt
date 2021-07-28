package hsfl.project.e_storymaker.roomDB

import androidx.lifecycle.LiveData
import hsfl.project.e_storymaker.roomDB.Entities.friendship.FriendshipDao
import hsfl.project.e_storymaker.roomDB.Entities.story.Story
import hsfl.project.e_storymaker.roomDB.Entities.story.StoryDao
import hsfl.project.e_storymaker.roomDB.Entities.user.User
import hsfl.project.e_storymaker.roomDB.Entities.user.UserDao

class AppRepository(private val userDao: UserDao,
                    private val storyDao: StoryDao,
                    private val friendshipDao: FriendshipDao) {

    // User

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }

    suspend fun updateUser(user: User){
        userDao.updateUser(user)
    }

    suspend fun deleteUser(user: User){
        userDao.deleteUser(user)
    }

    suspend fun getUserByName(firstname: String, lastname: String): User{
        return userDao.getUserByName(firstname, lastname)
    }

    // Stories

    suspend fun getAllStories(): List<Story>{
        return storyDao.getAll()
    }

    suspend fun deleteStory(story: Story){
        storyDao.delete(story)
    }

    suspend fun getStory(title: String): Story{
        return storyDao.getStory(title)
    }


}