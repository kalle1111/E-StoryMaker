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
import io.ktor.locations.*
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
    val chapterService = ChapterService()
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
            call.respondText("HELLO STORY_MAKER!", contentType = ContentType.Text.Plain)
        }

        userRoutes(userService, jwtService, hashFunction)
        storyRoutes(storyService)
        friendshipRoutes(friendshipService)
        tagRoutes(tagService)
        readChapterRoutes(readChapterService)
        chapterRoutes(chapterService)

        get("/session/increment") {
            val session = call.sessions.get<MySession>() ?: MySession()
            call.sessions.set(session.copy(count = session.count + 1))
            call.respondText("Counter is ${session.count}. Refresh to increment.")
        }
    }
}

data class MySession(val count: Int = 0)
