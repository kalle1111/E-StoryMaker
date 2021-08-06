package hsfl.project.e_storymaker.models.remoteDataSource

const val BASE_URL: String = "http://localhost:8080"

const val API_VERSION = "$BASE_URL/v1"
const val USERS = "$API_VERSION/users"
const val GET_ALL_USERS = "$USERS/getAll"
const val REGISTER_REQUEST = "$USERS/register"
const val LOGIN_REQUEST = "$USERS/login"
const val GET_PROFILE = "$USERS/getMyProfile"

const val STORIES = "$API_VERSION/stories"
const val MY_STORIES = "$STORIES/myStories"
const val CREATE_STORIES = "$STORIES/create"
const val UPDATE_STORIES = "$STORIES/update"
const val DELETE_STORIES = "$STORIES/delete"

const val RATE_STORIES = "$STORIES/rateStory"
const val UPDATE_RATED_STORIES = "$STORIES/updateRatedStory"
const val RATED_STORIES = "$STORIES/ratedStories"
const val FROM_ME_RATED_STORIES = "$STORIES/fromMeRatedStories"

const val FAVORITE_STORIES = "$STORIES/favoriteStories"
const val MY_FAVORITE_STORIES = "$FAVORITE_STORIES/getMyFavoriteStories"
const val SET_FAVORITE_STORIES = "$FAVORITE_STORIES/setFavorite"
const val SET_AS_NOT_FAVORITE_STORIES = "$FAVORITE_STORIES/setNotFavorite"

const val FRIENDSHIPS = "$API_VERSION/friendships"
const val MY_FRIENDSHIPS = "$FRIENDSHIPS/myFriendships"
const val REQUEST_FRIENDSHIPS = "$FRIENDSHIPS/request"
const val FRIENDSHIPS_GET_REQUESTS_TO_ME = "$FRIENDSHIPS/getRequestsToMe"
const val FRIENDSHIPS_GET_REQUESTS_FROM_ME = "$FRIENDSHIPS/getRequestsFromMe"
const val REJECT_FRIENDSHIPS = "$FRIENDSHIPS/reject"
const val ACCEPT_FRIENDSHIPS = "$FRIENDSHIPS/accept"
const val CANCEL_FRIENDSHIPS = "$FRIENDSHIPS/cancel"
const val DELETE_FRIENDSHIPS = "$FRIENDSHIPS/delete"