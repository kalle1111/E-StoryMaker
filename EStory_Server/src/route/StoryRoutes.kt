package com.eStory.route

import com.eStory.model.story.InsertStoryRequest
import com.eStory.model.SimpleResponse
import com.eStory.model.story.RateStoryRequest
import com.eStory.model.story.UpdateStoryRequest
import com.eStory.model.user.User
import com.eStory.service.StoryService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import java.lang.Exception


@Location(CREAT_STORIES)
class StoryCreateRoute

@Location(MY_STORIES)
class MyStoriesGetRoute

@Location(STORIES)
class StoriesGetRoute

@Location(UPDATE_STORIES)
class StoryUpdateRoute

@Location(DELETE_STORIES)
class StoryDeleteRoute

@Location(RATE_STORIES)
class StoryRateRoute

@Location(UPDATE_RATED_STORIES)
class StoryUpdateRateRoute

@Location(RATED_STORIES)
class RatedStoriesGetRoute

@Location(FROM_ME_RATED_STORIES)
class FromMeRatedStoriesGetRoute



fun Route.StoryRoutes(
    storyService: StoryService
) {
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
                storyService.insert(username, story.storyTitle, story.description)
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
                storyService.updateByUUID(story.uuid, story.storyTitle, story.description)
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

        /**************Rating Story*******************/
        post<StoryRateRoute> {
            print("HiHIHIHIH")
            val rateStory = try {

                call.receive<RateStoryRequest>()

            } catch (e: Exception) {

                call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "Missing Fields.."))
                return@post
            }
            try {
                val username = call.principal<User>()!!.userName
                storyService.rateStory(username, rateStory.storyId, rateStory.isFavorite, rateStory.ratingValue)
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
                    ratedStory.isFavorite,
                    ratedStory.ratingValue
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
}