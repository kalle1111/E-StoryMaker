package com.eStory.route

import com.eStory.model.SimpleResponse
import com.eStory.model.readChapter.ReadChapterRequest
import com.eStory.model.story.InsertStoryRequest
import com.eStory.model.user.User
import com.eStory.service.ReadChapterService
import com.eStory.service.TagService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.routing.post
import java.lang.Exception

@Location(UPDATE_READ_CHAPTER)
class UpdateReadChapterRoute

@Location(READ_CHAPTERS)
class GetALlReadChapterRoute

@Location(GET_READ_CHAPTER_BY_UUID)
class GetReadChapterByUUIDRoute

fun Route.ReadChapterRoutes(
    readChapterService: ReadChapterService
) {

    get<GetReadChapterByUUIDRoute> {
        val uuid = try {
            call.request.queryParameters["uuid"]!!
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "QueryParameter:uuid is not present"))
            return@get
        }
        try {
            val friendship = readChapterService.getByUUID(uuid)!!
            call.respond(HttpStatusCode.OK, friendship)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "Some Problems occurred"))
        }
    }


    authenticate("jwt") {

        post<UpdateReadChapterRoute> {


            val readChapter = try {

                call.receive<ReadChapterRequest>()


            } catch (e: Exception) {

                call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "Missing Fields.."))
                return@post
            }
            try {
                val username = call.principal<User>()!!.userName
                readChapterService.updateReadChapter(username, readChapter.storyId, readChapter.chapterNumber)
                call.respond(HttpStatusCode.OK, SimpleResponse(true, "ReadChapter was updated Successfully!"))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "Some Problems occurred"))
            }


        }

        get<GetALlReadChapterRoute> {
            try {
                val allReadChapters = readChapterService.getAll()
                call.respond(HttpStatusCode.OK, allReadChapters)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, e.message ?: "Some Problems Occurred!")

            }
        }

    }

}
