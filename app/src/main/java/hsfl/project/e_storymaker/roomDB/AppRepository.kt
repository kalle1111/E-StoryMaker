package hsfl.project.e_storymaker.roomDB

import hsfl.project.e_storymaker.roomDB.Entities.chapterProgress.ChapterProgressDao
import hsfl.project.e_storymaker.roomDB.Entities.favoring.FavoringDao
import hsfl.project.e_storymaker.roomDB.Entities.friendship.FriendshipDao
import hsfl.project.e_storymaker.roomDB.Entities.rating.Rating
import hsfl.project.e_storymaker.roomDB.Entities.rating.RatingDao
import hsfl.project.e_storymaker.roomDB.Entities.story.Story
import hsfl.project.e_storymaker.roomDB.Entities.story.StoryDao
import hsfl.project.e_storymaker.roomDB.Entities.user.User
import hsfl.project.e_storymaker.roomDB.Entities.user.UserDao

class AppRepository(private val userDao: UserDao,
                    private val storyDao: StoryDao,
                    private val friendshipDao: FriendshipDao,
                    private val chapterProgressDao: ChapterProgressDao,
                    private val favoringDao: FavoringDao,
                    private val ratingDao: RatingDao,
) {
}