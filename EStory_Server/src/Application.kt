package com.eStory

import authentication.JwtService
import com.eStory.table.DatabaseFactory
import authentication.hash
import com.eStory.route.*
import com.eStory.service.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import java.io.File

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    DatabaseFactory.init()
    val userService = UserService()
    val storyService = StoryService()
    val readChapterService = ReadChapterService()
    val friendshipService = FriendshipService()
    val tagService = TagService()
    val jwtService = JwtService()
    val hashFunction = { s: String -> hash(s) }

    install(Sessions) {
        cookie<MySession>("MY_SESSION") {
            cookie.extensions["SameSite"] = "lax"
        }
    }

    install(Authentication) {
        jwt("jwt") {
            verifier(jwtService.varifier)
            realm = "Story Server"
            validate {
                val payload = it.payload
                val userName = payload.getClaim("userName").asString()
                val user = userService.getByUserName(userName)
                user
            }
        }
    }
    install(Locations)
    install(ContentNegotiation) {
        gson {
        }
    }

    routing {
        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }

        UserRoutes(userService, jwtService, hashFunction)
        StoryRoutes(storyService)
        FriendshipRoutes(friendshipService)
        TagRoutes(tagService)
        ReadChapterRoutes(readChapterService)
        UploadRoutes()

        get("/session/increment") {
            val session = call.sessions.get<MySession>() ?: MySession()
            call.sessions.set(session.copy(count = session.count + 1))
            call.respondText("Counter is ${session.count}. Refresh to increment.")
        }

        get("/json/gson") {
            call.respond(mapOf("hello" to "world"))
        }
    }




        routing {
            get("/download") {
                val file = File("uploads/ktor_logo.png")
                call.response.header(
                    HttpHeaders.ContentDisposition,
                    ContentDisposition.Attachment.withParameter(ContentDisposition.Parameters.FileName, "ktor_logo.png")
                        .toString()
                )
                call.respondFile(file)
            }

    }
}

data class MySession(val count: Int = 0)
