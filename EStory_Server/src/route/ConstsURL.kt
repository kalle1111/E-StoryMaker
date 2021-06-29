package com.eStory.route

import io.ktor.locations.*

const val API_VERSION = "/v1"
const val USERS = "$API_VERSION/users"
const val REGISTER_REQUEST = "$USERS/register"
const val LOGIN_REQUEST = "$USERS/login"

const val STORIES = "$API_VERSION/stories"
const val MY_STORIES = "$STORIES/myStories"
const val CREAT_STORIES = "$STORIES/create"
const val UPDATE_STORIES = "$STORIES/update"
const val DELETE_STORIES = "$STORIES/delete"
const val RATE_STORIES = "$STORIES/rateStory"
const val UPDATE_RATED_STORIES = "$STORIES/updateRatedStory"
const val RATED_STORIES = "$STORIES/ratedStories"
const val FROM_ME_RATED_STORIES = "$STORIES/fromMeRatedStories"

const val FRIENDSHIPS = "$API_VERSION/friendships"
const val MY_FRIENDSHIPS = "$FRIENDSHIPS/myFriendships"
const val REQUEST_FRIENDSHIPS = "$FRIENDSHIPS/request"
const val FRIENDSHIPS_GET_REQUESTS_TO_ME = "$FRIENDSHIPS/getRequestsToMe"
const val FRIENDSHIPS_GET_REQUESTS_From_ME = "$FRIENDSHIPS/getRequestsFromMe"
const val REJECT_FRIENDSHIPS = "$FRIENDSHIPS/reject"
const val ACCEPT_FRIENDSHIPS = "$FRIENDSHIPS/accept"
const val CANCEL_FRIENDSHIPS = "$FRIENDSHIPS/cancel"
const val DELETE_FRIENDSHIPS = "$FRIENDSHIPS/delete"