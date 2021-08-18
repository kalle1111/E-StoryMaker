package com.eStory.route

import io.ktor.locations.*

/**
Beim Login bekommt man den Access-Token als message von dem Server.
dies wird gespeichert, und immer beim Anfrafen so anhängen:

val authHeaderValue = "Bearer " + message;
connection.setRequestProperty("Authorization", authHeaderValue)


Ein User darf Anfragen senden, nur wenn er Online ist.
Beim Anhängen dieses Messages, zeigt der User, dass er online ist
 */

const val API_VERSION = "/v1"
const val USERS = "$API_VERSION/users"
const val GET_ALL_USERS = "$USERS/getAll"
const val REGISTER_REQUEST = "$USERS/register"
const val LOGIN_REQUEST = "$USERS/login"
const val GET_PROFILE = "$USERS/getMyProfile"
const val UPDATE_PROFILE = "$USERS/updateProfile"
const val USER_GET_BY_ID_LAST_UPDATE = "$USERS/getLastUpdateById"
const val USERS_GET_LAST_UPDATE_VALUES = "$USERS/getLastUpdates"
const val GET_USER_BY_USERNAME = "$USERS/getByUsername" // username als Query parameter eingeben

const val STORIES = "$API_VERSION/stories"
const val MY_STORIES = "$STORIES/myStories"
const val CREAT_STORIES = "$STORIES/create"
const val UPDATE_STORIES = "$STORIES/update"
const val DELETE_STORIES = "$STORIES/delete"
const val STORY_GET_BY_ID_LAST_UPDATE = "$STORIES/getLastUpdateById"
const val STORIES_GET_LAST_UPDATE_VALUES = "$STORIES/getLastUpdates"

const val SEARCH_BY_TITLE_STORIES = "$STORIES/searchByTitle"

const val READ_CHAPTERS = "$API_VERSION/ReadChapters"
const val UPDATE_READ_CHAPTER = "$READ_CHAPTERS/updateReadChapter"

const val Tags = "$API_VERSION/tags"
const val GET_TAGGED_STORIES = "$Tags/getTaggedStories"
const val SEARCH_BY_Tag_STORIES = "$Tags/searchByTag"
const val MAP_Story_To_Tag = "$Tags/mapStoryToTag"
const val GET_ALL_TAGS_TO_STORY = "$Tags/getTagsToStory"

const val RATE_STORIES = "$STORIES/rateStory"
const val UPDATE_RATED_STORIES = "$STORIES/updateRatedStory"
const val RATED_STORIES = "$STORIES/ratedStories"
const val FROM_ME_RATED_STORIES = "$STORIES/fromMeRatedStories"
const val RATED_STORY_GET_BY_ID_LAST_UPDATE = "$STORIES/ratedStoryGetLastUpdateById"
const val RATED_STORIES_GET_LAST_UPDATE_VALUES = "$STORIES/ratedStoriesGetLastUpdates"

const val FAVORITE_STORIES = "$STORIES/favoriteStories"
const val MY_FAVORITE_STORIES = "$FAVORITE_STORIES/getMyFavoriteStories"
const val SET_FAVORITE_STORIES = "$FAVORITE_STORIES/setFavorite"
const val SET_AS_NOT_FAVORITE_STORIES = "$FAVORITE_STORIES/setNotFavorite"

const val FRIENDSHIPS = "$API_VERSION/friendships"
const val MY_FRIENDSHIPS = "$FRIENDSHIPS/myFriendships"
const val REQUEST_FRIENDSHIPS = "$FRIENDSHIPS/request"
const val FRIENDSHIPS_GET_REQUESTS_TO_ME = "$FRIENDSHIPS/getRequestsToMe"
const val FRIENDSHIPS_GET_REQUESTS_From_ME = "$FRIENDSHIPS/getRequestsFromMe"
const val REJECT_FRIENDSHIPS = "$FRIENDSHIPS/reject"
const val ACCEPT_FRIENDSHIPS = "$FRIENDSHIPS/accept"
const val CANCEL_FRIENDSHIPS = "$FRIENDSHIPS/cancel"
const val DELETE_FRIENDSHIPS = "$FRIENDSHIPS/delete"

const val UPLOAD_FILE_POST = "$API_VERSION/upload"
const val DOWNLOAD_FILE_POST = "$API_VERSION/download"