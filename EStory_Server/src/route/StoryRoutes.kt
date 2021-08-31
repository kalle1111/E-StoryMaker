package com.eStory.route

import com.eStory.model.story.InsertStoryRequest
import com.eStory.model.SimpleResponse
import com.eStory.model.story.RateStoryRequest
import com.eStory.model.story.StoryAsFavoriteRequest
import com.eStory.model.story.UpdateStoryRequest
import com.eStory.model.user.User
import com.eStory.service.ChapterService
import com.eStory.service.StoryService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import java.lang.Exception

@OptIn(KtorExperimentalLocationsAPI::class)
@Location(CREAT_STORIES)
class StoryCreateRoute

@OptIn(KtorExperimentalLocationsAPI::class)
@Location(MY_STORIES)
class MyStoriesGetRoute

@OptIn(KtorExperimentalLocationsAPI::class)
@Location(STORIES)
class StoriesGetRoute

@OptIn(KtorExperimentalLocationsAPI::class)
@Location(UPDATE_STORIES)
class StoryUpdateRoute

@OptIn(KtorExperimentalLocationsAPI::class)
@Location(DELETE_STORIES)
class StoryDeleteRoute

@OptIn(KtorExperimentalLocationsAPI::class)
@Location(RATE_STORIES)
class StoryRateRoute

@OptIn(KtorExperimentalLocationsAPI::class)
@Location(UPDATE_RATED_STORIES)
class StoryUpdateRateRoute

@OptIn(KtorExperimentalLocationsAPI::class)
@Location(RATED_STORIES)
class RatedStoriesGetRoute

@OptIn(KtorExperimentalLocationsAPI::class)
@Location(FROM_ME_RATED_STORIES)
class FromMeRatedStoriesGetRoute

@OptIn(KtorExperimentalLocationsAPI::class)
@Location(FAVORITE_STORIES)
class FavoriteStoriesGetRoute

@OptIn(KtorExperimentalLocationsAPI::class)
@Location(MY_FAVORITE_STORIES)
class MyFavoriteStoriesGetRoute

@OptIn(KtorExperimentalLocationsAPI::class)
@Location(SET_FAVORITE_STORIES)
class SetFavoriteStoryRoute

@OptIn(KtorExperimentalLocationsAPI::class)
@Location(SET_AS_NOT_FAVORITE_STORIES)
class SetAsNotFavoriteStoryRoute

@OptIn(KtorExperimentalLocationsAPI::class)
@Location(SEARCH_BY_TITLE_STORIES)
class StorySearchByTitleRoute

@OptIn(KtorExperimentalLocationsAPI::class)
@Location(STORY_GET_BY_ID_LAST_UPDATE)
class StoryGetLastUpdateRoute

@OptIn(KtorExperimentalLocationsAPI::class)
@Location(STORIES_GET_LAST_UPDATE_VALUES)
class StoriesGetLastUpdateValuesRoute


@OptIn(KtorExperimentalLocationsAPI::class)
@Location(RATED_STORY_GET_BY_ID_LAST_UPDATE)
class RatedStoryGetLastUpdateRoute

@OptIn(KtorExperimentalLocationsAPI::class)
@Location(RATED_STORIES_GET_LAST_UPDATE_VALUES)
class RatedStoriesGetLastUpdateValuesRoute

@OptIn(KtorExperimentalLocationsAPI::class)
@Location(MY_STORIES_GET_LAST_UPDATE_VALUES)
class GetLastUpdatesMyStoriesRoute

@OptIn(KtorExperimentalLocationsAPI::class)
@Location(GET_STORY_BY_UUID)
class GetStoryByUUIDRoute

@OptIn(KtorExperimentalLocationsAPI::class)
@Location(GET_FAVORITE_STORY_BY_UUID)
class GetFavoriteStoryByUUIDRoute

@OptIn(KtorExperimentalLocationsAPI::class)
@Location(GET_RATED_STORY_BY_UUID)
class GetRatedStoryByUUIDRoute

@OptIn(KtorExperimentalLocationsAPI::class)
@Location(GET_RATED_STORY_BY_STORY_ID)
class GetRatedStoryByStoryIdRoute

@OptIn(KtorExperimentalLocationsAPI::class)
@Location(FROM_RATED_STORIES_GET_LAST_UPDATES)
class GetLastUpdatesFromMeRatedStories

@OptIn(KtorExperimentalLocationsAPI::class)
@Location(MY_FAVORITE_STORIES_GET_LAST_UPDATES)
class GetLastUpdatesFromMyFavoriteStories

@Location (GET_LAST_UPDATES_RATED_STORIES_BY_STORY_ID)
class GetLastUpdatesRatedStoriesByStoryId

fun Route.storyRoutes(

    storyService: StoryService,
    chapterService: ChapterService
) {
    get<GetStoryByUUIDRoute> {
        val uuid = try {
            call.request.queryParameters["uuid"]!!
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "QueryParameter:uuid is not present"))
            return@get
        }
        try {
            val story = storyService.getByUUID(uuid)!!
            call.respond(HttpStatusCode.OK, story)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "Some Problems occurred"))
        }
    }


    get<GetRatedStoryByUUIDRoute> {
        val uuid = try {
            call.request.queryParameters["uuid"]!!
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "QueryParameter:uuid is not present"))
            return@get
        }
        try {
            val ratedStory = storyService.getRatedStoryByUUID(uuid)!!
            call.respond(HttpStatusCode.OK, ratedStory)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "Some Problems occurred"))
        }
    }

    get<GetRatedStoryByStoryIdRoute> {
        val storyId = try {
            call.request.queryParameters["storyId"]!!
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "QueryParameter: storyId is not present"))
            return@get
        }
        try {
            val ratedStory = storyService.getRatedStoryByStoryId(storyId)!!
            call.respond(HttpStatusCode.OK, ratedStory)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "Some Problems occurred"))
        }
    }

    get<GetFavoriteStoryByUUIDRoute> {
        val uuid = try {
            call.request.queryParameters["uuid"]!!
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "QueryParameter:uuid is not present"))
            return@get
        }
        try {
            val favoriteStory = storyService.getFavoriteStoryByUUID(uuid)!!
            call.respond(HttpStatusCode.OK, favoriteStory)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "Some Problems occurred"))
        }
    }


    authenticate("jwt") {

        post<StoryCreateRoute> {

            val story = try {

                call.receive<InsertStoryRequest>()


            } catch (e: Exception) {

                call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "Missing Fields.."))
                return@post
            }
            try {
                val username = call.principal<User>()!!.userName
                //storyService.insert(username, story.storyTitle, story.description, story.storyChapters, story.cover)

                storyService.insert(username, story.storyTitle, story.description, story.cover)
                chapterService.insertChapter(story.insertFirstChapter.storyId, story.insertFirstChapter.title, story.insertFirstChapter.content, story.insertFirstChapter.index)
                call.respond(HttpStatusCode.OK, SimpleResponse(true, "Story created Successfully!"))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "Some Problems occurred"))
            }

        }
        get<MyStoriesGetRoute> {
            try {
                val userName = call.principal<User>()!!.userName
                val stories = storyService.getAllByUserName(userName)
                call.respond(HttpStatusCode.OK, stories)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, e.message ?: "Some Problems Occurred!")

            }

        }

        get<StoriesGetRoute> {
            try {
                val stories = storyService.getAll()
                call.respond(HttpStatusCode.OK, stories)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, e.message ?: "Some Problems Occurred!")

            }

        }

        post<StoryUpdateRoute> {
            // val uuid = call.parameters["uuid"]!!
            val story = try {
                call.receive<UpdateStoryRequest>()

            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "Missing Fields.."))
                return@post
            }
            try {
                storyService.updateByUUID(
                    story.uuid,
                    story.storyTitle,
                    story.description,
                 //   story.storyChapters,
                    story.cover!!
                )
                call.respond(HttpStatusCode.OK, SimpleResponse(true, "Story updated Successfully!"))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "Some Problems occurred"))
            }
        }

        delete<StoryDeleteRoute> {
            val storyId = try {
                call.request.queryParameters["id"]!!
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "QueryParameter:id ist not present"))
                return@delete
            }
            try {
                storyService.deleteByUUID(storyId)
                call.respond(HttpStatusCode.OK, SimpleResponse(true, "Story deleted Successfully!"))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "Some Problems occurred"))
            }
        }
        get<GetLastUpdatesMyStoriesRoute> {
            val username = try {
                call.request.queryParameters["username"]!!
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    SimpleResponse(false, "QueryParameter:username ist not present")
                )
                return@get
            }
            try {
                val myLastUpdates = storyService.getLastUpdatesMyStories(username)
                call.respond(HttpStatusCode.OK, myLastUpdates)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "Some Problems occurred"))
            }
        }
        /****************Searching by title ************/
        get<StorySearchByTitleRoute> {
            val storyTitle = try {
                call.request.queryParameters["title"]!!
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "QueryParameter:title is not present"))
                return@get
            }
            try {
                val story = storyService.getStoryBySubTitle(storyTitle)
                call.respond(HttpStatusCode.OK, story)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "Some Problems occurred"))
            }
        }
        /****************Set Story as Favorite ************/
        //We dont need it, it is not so important in the app
        get<FavoriteStoriesGetRoute> {
            try {
                val favoriteStories = storyService.getAllStoriesAsFavorite()
                call.respond(HttpStatusCode.OK, favoriteStories)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, e.message ?: "Some Problems Occurred!")

            }
        }
        // this is more important to see just my important Stories
        get<MyFavoriteStoriesGetRoute> {
            try {
                val userName = call.principal<User>()!!.userName
                val myFavoriteStories = storyService.getMyFavoriteStories(userName)
                call.respond(HttpStatusCode.OK, myFavoriteStories)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, e.message ?: "Some Problems Occurred!")

            }
        }

        post<SetFavoriteStoryRoute> {
            val setFavorite = try {

                call.receive<StoryAsFavoriteRequest>()

            } catch (e: Exception) {

                call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "Missing Fields.."))
                return@post
            }


            try {
                val username = call.principal<User>()!!.userName
                storyService.setStoryAsFavorite(username, setFavorite.storyId)
                call.respond(HttpStatusCode.OK, SimpleResponse(true, "Story is now as favorite!"))
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.Conflict,
                    SimpleResponse(false, e.message ?: "Some Problems occurred setFavorite")
                )
            }

        }
        post<SetAsNotFavoriteStoryRoute> {
            val setNotFavorite = try {

                call.receive<StoryAsFavoriteRequest>()

            } catch (e: Exception) {

                call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "Missing Fields.."))
                return@post
            }
            try {
                val username = call.principal<User>()!!.userName
                storyService.setStoryAsFavorite(username, setNotFavorite.storyId)
                call.respond(HttpStatusCode.OK, SimpleResponse(true, "Story is now as not favorite!"))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "Some Problems occurred"))
            }

        }

        /**************Rating Story*******************/
        post<StoryRateRoute> {
            val rateStory = try {

                call.receive<RateStoryRequest>()

            } catch (e: Exception) {

                call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "Missing Fields.."))
                return@post
            }
            try {
                val username = call.principal<User>()!!.userName
                storyService.rateStory(
                    username, rateStory.storyId,
                    rateStory.ratingTitle,
                    rateStory.ratingDescription,
                    rateStory.ratingOverallValue,
                    rateStory.ratingStyleValue,
                    rateStory.ratingStoryValue,
                    rateStory.ratingGrammarValue,
                    rateStory.ratingCharacterValue
                )
                call.respond(HttpStatusCode.OK, SimpleResponse(true, "Story has been rated Successfully!"))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "Some Problems occurred"))
            }
        }

        post<StoryUpdateRateRoute> {

            val ratedStory = try {
                call.receive<RateStoryRequest>()

            } catch (e: Exception) {

                call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "Missing Fields.."))
                return@post
            }
            try {
                val username = call.principal<User>()!!.userName
                storyService.updateRatedStory(
                    username,
                    ratedStory.storyId,
                    ratedStory.ratingTitle,
                    ratedStory.ratingDescription,
                    ratedStory.ratingOverallValue,
                    ratedStory.ratingStyleValue,
                    ratedStory.ratingStoryValue,
                    ratedStory.ratingGrammarValue,
                    ratedStory.ratingCharacterValue
                )
                call.respond(HttpStatusCode.OK, SimpleResponse(true, "Story has been rated Successfully!"))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "Some Problems occurred"))
            }
        }


        get<RatedStoriesGetRoute> {
            try {
                val ratedStories = storyService.getAllRatedStories()
                call.respond(HttpStatusCode.OK, ratedStories)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, e.message ?: "Some Problems Occurred!")

            }

        }

        get<FromMeRatedStoriesGetRoute> {
            try {
                val userName = call.principal<User>()!!.userName
                val fromMeRatedStories = storyService.getFromMeRatedStories(userName)
                call.respond(HttpStatusCode.OK, fromMeRatedStories)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, e.message ?: "Some Problems Occurred!")

            }

        }


    }

    get<StoryGetLastUpdateRoute> {
        val uuid = try {
            call.request.queryParameters["uuid"]!!
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "QueryParameter:uuid is not present"))
            return@get
        }
        try {
            val lastUpdate = storyService.getLastUpdateByUUID(uuid)!!
            call.respond(HttpStatusCode.OK, lastUpdate)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "Some Problems occurred"))
        }
    }





    get<StoriesGetLastUpdateValuesRoute> {
        try {
            val lastUpdateValues = storyService.getAllLastUpdateValues()
            call.respond(HttpStatusCode.OK, lastUpdateValues)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.Conflict, e.message ?: "Some Problems Occurred!")

        }
    }

    get<RatedStoryGetLastUpdateRoute> {
        val uuid = try {
            call.request.queryParameters["uuid"]!!
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "QueryParameter:uuid is not present"))
            return@get
        }
        try {
            val lastUpdate = storyService.ratedStory_getLastUpdateByUUID(uuid)!!
            call.respond(HttpStatusCode.OK, lastUpdate)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "Some Problems occurred"))
        }
    }

    get<RatedStoriesGetLastUpdateValuesRoute> {
        try {
            val lastUpdateValues = storyService.ratedStory_getAllLastUpdateValues()
            call.respond(HttpStatusCode.OK, lastUpdateValues)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.Conflict, e.message ?: "Some Problems Occurred!")

        }
    }

    get<GetLastUpdatesFromMeRatedStories> {
        val userName = try {
            call.request.queryParameters["username"]!!
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "QueryParameter: username is not present"))
            return@get
        }
        try {
            val lastUpdateValues = storyService.getLastUpdatesFromMeRatedStories(userName)
            call.respond(HttpStatusCode.OK, lastUpdateValues)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.Conflict, e.message ?: "Some Problems Occurred!")

        }
    }

    get<GetLastUpdatesFromMyFavoriteStories> {

        val userName = try {
            call.request.queryParameters["username"]!!
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "QueryParameter: username is not present"))
            return@get
        }
        try {
            val lastUpdateValues = storyService.getLastUpdatesFromMyFavoriteStories(userName)
            call.respond(HttpStatusCode.OK, lastUpdateValues)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.Conflict, e.message ?: "Some Problems Occurred!")

        }
    }

    get<GetLastUpdatesRatedStoriesByStoryId> {
        val storyId = try {
            call.request.queryParameters["storyId"]!!
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "QueryParameter: storyId is not present"))
            return@get
        }
        try {
            val lastUpdateValues = storyService.getLastUpdatesRatedStoriesByStoryId(storyId)
            call.respond(HttpStatusCode.OK, lastUpdateValues)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.Conflict, e.message ?: "Some Problems Occurred!")

        }
    }

}