package hsfl.project.e_storymaker.models.remoteDataSource

import hsfl.project.e_storymaker.repository.webserviceModels.*

fun convertWebserviceUserToDBUser(user: User): hsfl.project.e_storymaker.roomDB.Entities.user.User{
    return hsfl.project.e_storymaker.roomDB.Entities.user.User(user.uuid, user.firstname, user.lastname, user.userName, user.description, user.birthday,user.password)
}

fun convertWebserviceStoryToDbStory(story: Story): hsfl.project.e_storymaker.roomDB.Entities.story.Story{
    return hsfl.project.e_storymaker.roomDB.Entities.story.Story(story.uuid,story.user.uuid,story.storyTitle,story.createTime,0,story.description,"??","??")
}

fun convertDbStoryToWebserviceStory (story:  hsfl.project.e_storymaker.roomDB.Entities.story.Story, user: User): Story {
    return Story(story.story_uuid, user, story.storyTitle, story.storyDescription,story.releaseDate)
}

fun convertWebserviceRatedStoryToDbRatedStory(ratedStory: RatedStory): hsfl.project.e_storymaker.roomDB.Entities.rating.Rating{
    return hsfl.project.e_storymaker.roomDB.Entities.rating.Rating(ratedStory.uuid, ratedStory.ratingOverallValue, ratedStory.user.uuid, ratedStory.story.uuid)
}

fun convertWebserviceFavoriteToDbFavorite(storyAsFavorite: StoryAsFavorite): hsfl.project.e_storymaker.roomDB.Entities.favoring.Favoring{
    return hsfl.project.e_storymaker.roomDB.Entities.favoring.Favoring(storyAsFavorite.uuid, true, storyAsFavorite.user.uuid,storyAsFavorite.story.uuid)
}

fun convertWebserviceFriendshipToDbFriendship(friendship: Friendship): hsfl.project.e_storymaker.roomDB.Entities.friendship.Friendship{
    return hsfl.project.e_storymaker.roomDB.Entities.friendship.Friendship(friendship.uuid, friendship.requesterUser.uuid, friendship.friend.uuid, friendship.isAccepted, friendship.requestTime)
}