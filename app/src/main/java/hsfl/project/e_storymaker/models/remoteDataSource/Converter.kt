package hsfl.project.e_storymaker.models.remoteDataSource

import hsfl.project.e_storymaker.repository.webserviceModels.*

fun convertWebserviceUserToDBUser(user: User): hsfl.project.e_storymaker.roomDB.Entities.user.User{
    return hsfl.project.e_storymaker.roomDB.Entities.user.User(user.userName, user.firstname, user.lastname, user.description, user.birthday, user.hashPassword, user.lastUpdate.toLong())
}

//TODO: Parameter und Storyklasse anpassen
fun convertWebserviceStoryToDbStory(story: Story): hsfl.project.e_storymaker.roomDB.Entities.story.Story{
    return hsfl.project.e_storymaker.roomDB.Entities.story.Story(story.uuid, story.user.userName, story.storyTitle, story.description, story.createTime.toString(), 0, ByteArray(0), story.averageRating, story.lastUpdate)
}

//fun convertDbStoryToWebserviceStory (story:  hsfl.project.e_storymaker.roomDB.Entities.story.Story, user: User): Story {
//    return Story(story.story_uuid, user, story.storyTitle, story.storyDescription,story.releaseDate)
//}

fun convertWebserviceRatedStoryToDbRatedStory(ratedStory: RatedStory): hsfl.project.e_storymaker.roomDB.Entities.rating.Rating{
    return hsfl.project.e_storymaker.roomDB.Entities.rating.Rating(ratedStory.uuid, ratedStory.ratingOverallValue,ratedStory.ratingStyleValue, ratedStory.ratingStoryValue, ratedStory.ratingGrammarValue, ratedStory.ratingCharacterValue, ratedStory.user.uuid, ratedStory.story.uuid, ratedStory.ratingTitle, ratedStory.ratingDescription, ratedStory.lastUpdate)
}

fun convertWebserviceFavoriteToDbFavorite(storyAsFavorite: StoryAsFavorite): hsfl.project.e_storymaker.roomDB.Entities.favoring.Favoring{
    return hsfl.project.e_storymaker.roomDB.Entities.favoring.Favoring(storyAsFavorite.uuid, true, storyAsFavorite.user.uuid,storyAsFavorite.story.uuid, 0.toLong())
}

fun convertWebserviceFriendshipToDbFriendship(friendship: Friendship): hsfl.project.e_storymaker.roomDB.Entities.friendship.Friendship{
    return hsfl.project.e_storymaker.roomDB.Entities.friendship.Friendship(friendship.uuid, friendship.requesterUser.uuid, friendship.friend.uuid, friendship.isAccepted, friendship.requestTime, 0.toLong())
}

fun convertWebserviceChapterToDbChapter(chapter: Chapter): hsfl.project.e_storymaker.roomDB.Entities.chapter.Chapter {
    return hsfl.project.e_storymaker.roomDB.Entities.chapter.Chapter(chapter.uuid, chapter.storyId, chapter.title, chapter.content, chapter.index, chapter.lastUpdate)
}

fun convertWebserviceTagToDbTag(tag: Tag): hsfl.project.e_storymaker.roomDB.Entities.tag.Tag {
    return hsfl.project.e_storymaker.roomDB.Entities.tag.Tag(tag.uuid, tag.name, 0.toLong())
}