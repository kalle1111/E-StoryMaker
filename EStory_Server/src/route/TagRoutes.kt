package com.eStory.route

import com.eStory.model.SimpleResponse
import com.eStory.model.tag.MapStoryToTagRequest
import com.eStory.service.TagService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import java.lang.Exception

@Location(GET_TAGGED_STORIES)
class GetTaggedStoriesRoute

@Location(SEARCH_BY_Tag_STORIES)
class GetStoriesByTag

@Location(Tags)
class GetAllTagsRoute

@Location(MAP_Story_To_Tag)
class MapStoryToTagRoute

@Location(GET_ALL_TAGS_TO_STORY)
class GetAllTagsToStoryRoute


fun Route.TagRoutes(
    tagService: TagService,
) {
    authenticate("jwt") {

        get<GetTaggedStoriesRoute> {
            try {
                val taggedStories = tagService.getAllTaggedStories()
                call.respond(HttpStatusCode.OK, taggedStories)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, e.message ?: "Some Problems Occurred!")

            }
        }



        get<GetStoriesByTag> {
            val tagName = try {
                call.request.queryParameters["tagName"]!!
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "QueryParameter:tagName is not present"))
                return@get
            }
            try {
                val storiesByTag = tagService.findStoriesByTag(tagName)
                call.respond(HttpStatusCode.OK, storiesByTag)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "Some Problems occurred"))
            }
        }



        get<GetAllTagsToStoryRoute> {
            val storyId = try {
                call.request.queryParameters["storyId"]!!
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "QueryParameter:storyId is not present"))
                return@get
            }
            try {
                val tagsToStory = tagService.getAllTagsToStory(storyId)
                call.respond(HttpStatusCode.OK, tagsToStory)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "Some Problems occurred"))
            }
        }

        get<GetAllTagsRoute> {
            try {
                val allTags = tagService.getAllTags()
                call.respond(HttpStatusCode.OK, allTags)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, e.message ?: "Some Problems Occurred!")

            }
        }

        get<GetAllTagsRoute> {
            try {
                val allTags = tagService.getAllTags()
                //     call.respond(bytes)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, e.message ?: "Some Problems Occurred!")

            }
        }


        post<MapStoryToTagRoute> {
            val mapStoryToTagRequest = try {

                call.receive<MapStoryToTagRequest>()

            } catch (e: Exception) {

                call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "Missing Fields.."))
                return@post
            }


            try {
                tagService.mapStoryToTag(mapStoryToTagRequest.storyId, mapStoryToTagRequest.tagName)
                call.respond(HttpStatusCode.OK, SimpleResponse(true, "Story has been tagged successfully!"))
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.Conflict,
                    SimpleResponse(false, e.message ?: "Some Problems occurred setFavorite")
                )
            }

        }


    }
}