package com.eStory.route

import com.eStory.model.SimpleResponse
import com.eStory.model.tag.MapStoryToTagRequest
import com.eStory.model.tag.SearchByTags
import com.eStory.model.tag.SearchByTagsAndTitle
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

@Location(GET_BY_TAGS_STORIES)
class GetStoriesByTags

@Location(GET_BY_TAGS_AND_TITLE_STORIES)
class GetStoriesByTagsAndTitle

@Location(TAGS)
class GetAllTagsRoute

@Location(MAP_Story_To_Tag)
class MapStoryToTagRoute

@Location(GET_ALL_TAGS_TO_STORY)
class GetAllTagsToStoryRoute

@Location(GET_TAG_BY_UUID)
class GetTagByUUIDRoute

fun Route.TagRoutes(
    tagService: TagService,
) {
    get<GetTagByUUIDRoute> {
        val uuid = try {
            call.request.queryParameters["uuid"]!!
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "QueryParameter:uuid is not present"))
            return@get
        }
        try {
            val tag = tagService.getByUUID(uuid)!!
            call.respond(HttpStatusCode.OK, tag)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "Some Problems occurred"))
        }
    }


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

        get<GetStoriesByTags> {
            val searchByTags = try {

                call.receive<SearchByTags>()

            } catch (e: Exception) {

                call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "Missing Fields.."))
                return@get
            }
            try {
                val stories = tagService.getStoriesByTags(searchByTags.tags)
                call.respond(HttpStatusCode.OK, stories)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.Conflict,
                    SimpleResponse(false, e.message ?: "Some Problems occurred setFavorite")
                )
            }

        }
        get<GetStoriesByTagsAndTitle> {
            val searchByTagsAndTitle = try {

                call.receive<SearchByTagsAndTitle>()

            } catch (e: Exception) {

                call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "Missing Fields.."))
                return@get
            }
            try {
                val stories = tagService.getStoriesByTagsAndTitle(searchByTagsAndTitle.tags, searchByTagsAndTitle.title)
                call.respond(HttpStatusCode.OK, stories)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.Conflict,
                    SimpleResponse(false, e.message ?: "Some Problems occurred setFavorite")
                )
            }
        }

    }
}