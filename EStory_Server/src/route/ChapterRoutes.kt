package com.eStory.route


import com.eStory.model.SimpleResponse
import com.eStory.model.chapter.InsertChapterRequest
import com.eStory.model.chapter.UpdateChapterRequest

import com.eStory.service.ChapterService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

import java.lang.Exception

@Location(INSERT_CHAPTER_ROUTE)
class InsertChapterRoute

@Location(GET_CHAPTER_BY_UUID_ROUTE)
class GetChapterByIdRoute

@Location(GET_CHAPTERS_BY_STORY_ID_ROUTE)
class GetChaptersByStoryIdRoute

@Location(GET_LAST_UPDATE_BY_CHAPTER_ID_ROUTE)
class GetLastUpdateByChapterIdRoute

@Location(GET_CHAPTERS_LAST_UPDATES_BY_STORY_ID_ROUTE)
class GetChaptersLastUpdatesByStoryIdRoute

@Location(UPDATE_CHAPTER_BY_ID_ROUTE)
class UpdateChapterByIdRoute

fun Route.chapterRoutes(
    chapterService: ChapterService,
) {

    authenticate("jwt") {
        post<InsertChapterRoute> {

            val chapterRequest = try {

                call.receive<InsertChapterRequest>()


            } catch (e: Exception) {

                call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "Missing Fields.."))
                return@post
            }
            try {

                chapterService.insertChapter(
                    chapterRequest.storyId,
                    chapterRequest.title,
                    chapterRequest.content,
                    chapterRequest.index
                )
                call.respond(HttpStatusCode.OK, SimpleResponse(true, "Chapter inserted Successfully!"))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "Some Problems occurred"))
            }

        }

        get<GetChapterByIdRoute> {
            val uuid = try {
                call.request.queryParameters["uuid"]!!
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    SimpleResponse(false, "QueryParameter:uuid ist not present")
                )
                return@get
            }
            try {
                val chapter = chapterService.getByUUID(uuid)!!
                call.respond(HttpStatusCode.OK, chapter)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "Some Problems occurred"))
            }
        }


        //get all chapters of A story by story id
        get<GetChaptersByStoryIdRoute> {
            val storyId = try {
                call.request.queryParameters["storyId"]!!
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    SimpleResponse(false, "QueryParameter:storyId ist not present")
                )
                return@get
            }
            try {
                val chapters = chapterService.getChaptersByStoryId(storyId)
                call.respond(HttpStatusCode.OK, chapters)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "Some Problems occurred"))
            }
        }

        get<GetLastUpdateByChapterIdRoute> {
            val uuid = try {
                call.request.queryParameters["uuid"]!!
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    SimpleResponse(false, "QueryParameter:uuid ist not present")
                )
                return@get
            }
            try {
                val lastUpdate = chapterService.getLastUpdateByChapterId(uuid)
                call.respond(HttpStatusCode.OK, lastUpdate)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "Some Problems occurred"))
            }
        }

        get<GetChaptersLastUpdatesByStoryIdRoute> {
            val storyId = try {
                call.request.queryParameters["storyId"]!!
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    SimpleResponse(false, "QueryParameter:storyId ist not present")
                )
                return@get
            }
            try {
                val lastUpdates = chapterService.getChaptersLastUpdatesByStoryId(storyId)
                call.respond(HttpStatusCode.OK, lastUpdates)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "Some Problems occurred"))
            }
        }

        post<UpdateChapterByIdRoute> {

            val updateChapterRequest = try {

                call.receive<UpdateChapterRequest>()


            } catch (e: Exception) {

                call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "Missing Fields.."))
                return@post
            }
            try {

                chapterService.updateChapterById(
                    updateChapterRequest.chapterId,
                    updateChapterRequest.title,
                    updateChapterRequest.content
                )
                call.respond(HttpStatusCode.OK, SimpleResponse(true, "Chapter updated Successfully!"))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "Some Problems occurred"))
            }

        }

    }
}