package hsfl.project.e_storymaker.models.remoteDataSource

import hsfl.project.e_storymaker.repository.webserviceModels.chapter.Chapter
import hsfl.project.e_storymaker.repository.webserviceModels.friendship.Friendship
import hsfl.project.e_storymaker.repository.webserviceModels.ratedStory.RatedStory
import hsfl.project.e_storymaker.repository.webserviceModels.story.Story
import hsfl.project.e_storymaker.repository.webserviceModels.tag.Tag
import hsfl.project.e_storymaker.repository.webserviceModels.user.User

fun convertWebserviceUserToDBUser(user: User): hsfl.project.e_storymaker.roomDB.Entities.user.User{
    return hsfl.project.e_storymaker.roomDB.Entities.user.User(user.userName,  user.description, user.hashPassword, user.image!!, user.lastUpdate.toLong())
}

fun convertWebserviceStoryToDbStory(story: Story): hsfl.project.e_storymaker.roomDB.Entities.story.Story{
    return hsfl.project.e_storymaker.roomDB.Entities.story.Story(story.uuid, story.user.userName, story.storyTitle, story.description, story.createTime.toString(), 0, story.cover!!, story.averageRating, story.lastUpdate)
}

fun convertWebserviceRatedStoryToDbRatedStory(ratedStory: RatedStory): hsfl.project.e_storymaker.roomDB.Entities.rating.Rating{
    return hsfl.project.e_storymaker.roomDB.Entities.rating.Rating(ratedStory.uuid, ratedStory.ratingOverallValue,ratedStory.ratingStyleValue, ratedStory.ratingStoryValue, ratedStory.ratingGrammarValue, ratedStory.ratingCharacterValue, ratedStory.user.uuid, ratedStory.story.uuid, ratedStory.ratingTitle, ratedStory.ratingDescription, ratedStory.lastUpdate)
}

fun convertWebserviceFriendshipToDbFriendship(friendship: Friendship): hsfl.project.e_storymaker.roomDB.Entities.friendship.Friendship{
    return hsfl.project.e_storymaker.roomDB.Entities.friendship.Friendship(friendship.uuid, friendship.requesterUser.uuid, friendship.friend.uuid, friendship.isAccepted, friendship.requestTime, 0.toLong())
}

fun convertWebserviceChapterToDbChapter(chapter: Chapter): hsfl.project.e_storymaker.roomDB.Entities.chapter.Chapter {
    return hsfl.project.e_storymaker.roomDB.Entities.chapter.Chapter(chapter.uuid, chapter.storyId, chapter.title, chapter.content, chapter.index, chapter.lastUpdate)
}

fun convertWebserviceTagToDbTag(tag: Tag): hsfl.project.e_storymaker.roomDB.Entities.tag.Tag {
    return hsfl.project.e_storymaker.roomDB.Entities.tag.Tag(tag.name, 0.toLong())
}