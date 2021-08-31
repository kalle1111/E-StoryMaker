package com.eStory.route

import io.ktor.locations.*

const val API_VERSION = "/v1"
const val USERS = "$API_VERSION/users"
const val GET_ALL_USERS = "$USERS/getAll"
const val REGISTER_REQUEST = "$USERS/register"
const val LOGIN_REQUEST = "$USERS/login"
const val GET_PROFILE = "$USERS/getMyProfile"
const val UPDATE_PROFILE = "$USERS/updateProfile"
const val USER_GET_BY_USERNAME_LAST_UPDATE = "$USERS/getLastUpdateByUserName"  // username als Query parameter eingeben
const val USERS_GET_LAST_UPDATE_VALUES = "$USERS/getLastUpdates"
const val GET_USER_BY_USERNAME = "$USERS/getByUsername" // username als Query parameter eingeben
const val GET_USER_BY_UUID = "$USERS/getByUUID" // uuid als Query parameter eingeben

const val STORIES = "$API_VERSION/stories"
const val MY_STORIES = "$STORIES/myStories"
const val CREAT_STORIES = "$STORIES/create"
const val UPDATE_STORIES = "$STORIES/update"
const val DELETE_STORIES = "$STORIES/delete"
const val STORY_GET_BY_ID_LAST_UPDATE = "$STORIES/getLastUpdateById"
const val STORIES_GET_LAST_UPDATE_VALUES = "$STORIES/getLastUpdates"
const val MY_STORIES_GET_LAST_UPDATE_VALUES = "$MY_STORIES/getLastUpdates" // username als Query parameter eingeben
const val GET_STORY_BY_UUID = "$STORIES/getByUUID"  // uuid als Query parameter eingeben

const val CHAPTERS = "$API_VERSION/CHAPTERS"
const val INSERT_CHAPTER_ROUTE = "$CHAPTERS/insertChapter"
const val GET_CHAPTER_BY_UUID_ROUTE = "$CHAPTERS/getChapterById" // uuid als Query parameter eingeben
const val GET_CHAPTERS_BY_STORY_ID_ROUTE = "$CHAPTERS/getChaptersByStoryId" // storyId als Query parameter eingeben
const val GET_LAST_UPDATE_BY_CHAPTER_ID_ROUTE = "$CHAPTERS/getLastUpdateById" // uuid als Query parameter eingeben
const val GET_CHAPTERS_LAST_UPDATES_BY_STORY_ID_ROUTE =
    "$CHAPTERS/getLastUpdatesByStoryId" // storyId als Query parameter eingeben
const val UPDATE_CHAPTER_BY_ID_ROUTE = "$CHAPTERS/updateChapterById" // siehe die Klasse UpdateChapterRequest

const val SEARCH_BY_TITLE_STORIES = "$STORIES/searchByTitle"

const val READ_CHAPTERS = "$API_VERSION/ReadChapters"
const val UPDATE_READ_CHAPTER = "$READ_CHAPTERS/updateReadChapter"
const val GET_READ_CHAPTER_BY_UUID = "$READ_CHAPTERS/getByUUID" // uuid als Query parameter eingeben

const val TAGS = "$API_VERSION/tags"
const val GET_TAGGED_STORIES = "$TAGS/getTaggedStories"
const val SEARCH_BY_Tag_STORIES = "$TAGS/searchByTag" // tag als Query parameter eingeben
const val MAP_Story_To_Tag = "$TAGS/mapStoryToTag"
const val GET_ALL_TAGS_TO_STORY = "$TAGS/getTagsToStory"
const val GET_TAG_BY_UUID = "$TAGS/getByUUID"  // uuid als Query parameter eingeben
const val GET_BY_TAGS_STORIES = "$TAGS/getByTags"
const val GET_BY_TAGS_AND_TITLE_STORIES = "$TAGS/getByTagsAndTitle"
const val GET_LAST_UPDATES_BY_TAGS = "$TAGS/getLastUpdatesByTags"

const val RATE_STORIES = "$STORIES/rateStory"
const val UPDATE_RATED_STORIES = "$STORIES/updateRatedStory"
const val RATED_STORIES = "$STORIES/ratedStories"
const val FROM_ME_RATED_STORIES = "$STORIES/fromMeRatedStories"
const val FROM_RATED_STORIES_GET_LAST_UPDATES =
    "$FROM_ME_RATED_STORIES/getLastUpdates" // username als Query parameter eingeben
const val GET_LAST_UPDATES_RATED_STORIES_BY_STORY_ID =
    "$RATED_STORIES/getByStoryId" // storyId als Query Parameter eingeben
const val RATED_STORY_GET_BY_ID_LAST_UPDATE = "$STORIES/ratedStoryGetLastUpdateById"
const val RATED_STORIES_GET_LAST_UPDATE_VALUES = "$STORIES/ratedStoriesGetLastUpdates"
const val GET_RATED_STORY_BY_UUID = "$STORIES/getRatedStoryByUUID" // uuid als Query parameter eingeben
const val GET_RATED_STORY_BY_STORY_ID = "$STORIES/getRatedStoryByStoryId"  // storyId als Query parameter eingeben


const val FAVORITE_STORIES = "$STORIES/favoriteStories"
const val MY_FAVORITE_STORIES = "$FAVORITE_STORIES/getMyFavoriteStories"
const val SET_FAVORITE_STORIES = "$FAVORITE_STORIES/setFavorite"
const val SET_AS_NOT_FAVORITE_STORIES = "$FAVORITE_STORIES/setNotFavorite"
const val GET_FAVORITE_STORY_BY_UUID = "$FAVORITE_STORIES/getByUUID"  // uuid als Query parameter eingeben
const val MY_FAVORITE_STORIES_GET_LAST_UPDATES =
    "$MY_FAVORITE_STORIES/getLastUpdates" // username als Query parameter eingeben


const val FRIENDSHIPS = "$API_VERSION/friendships"
const val MY_FRIENDSHIPS = "$FRIENDSHIPS/myFriendships"
const val REQUEST_FRIENDSHIPS = "$FRIENDSHIPS/request"
const val FRIENDSHIPS_GET_REQUESTS_TO_ME = "$FRIENDSHIPS/getRequestsToMe"
const val FRIENDSHIPS_GET_REQUESTS_From_ME = "$FRIENDSHIPS/getRequestsFromMe"
const val REJECT_FRIENDSHIPS = "$FRIENDSHIPS/reject"
const val ACCEPT_FRIENDSHIPS = "$FRIENDSHIPS/accept"
const val CANCEL_FRIENDSHIPS = "$FRIENDSHIPS/cancel"
const val DELETE_FRIENDSHIPS = "$FRIENDSHIPS/delete"
const val GET_FRIENDSHIP_BY_UUID = "$FRIENDSHIPS/getByUUID" // uuid als Query parameter eingeben

const val UPLOAD_FILE_POST = "$API_VERSION/upload"
const val DOWNLOAD_FILE_POST = "$API_VERSION/download"
