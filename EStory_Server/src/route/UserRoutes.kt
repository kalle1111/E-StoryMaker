package com.eStory.route

import authentication.JwtService
import com.eStory.model.user.LoginRequest
import com.eStory.model.user.RegisterRequest
import com.eStory.model.SimpleResponse
import com.eStory.service.UserService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import java.lang.Exception


@Location(REGISTER_REQUEST)
class UserRegisterRoute

@Location(LOGIN_REQUEST)
class UserLoginRoute

fun Route.UserRoutes(
    userService: UserService,
    jwtService: JwtService,
    hashFunction: (String) -> String
) {

    post<UserRegisterRoute> {
        val registerRequest = try {
            call.receive<RegisterRequest>()
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "Missing some Fields.."))
            return@post
        }
        try {
            /* val user = User(registerRequest.email, hashFunction(registerRequest.password), registerRequest.name)
             db.addUser(user)*/
            val user = userService.insert(
                registerRequest.firstname,
                registerRequest.lastname,
                registerRequest.userName,
                registerRequest.birthday,
                registerRequest.description,
                hashFunction(registerRequest.password)
            )
            call.respond(HttpStatusCode.OK, SimpleResponse(true, jwtService.generateToken(user)))
        } catch (e: Exception) {
            call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "some Problems Occurred.."))

        }

    }

    post<UserLoginRoute> {
        val loginRequest = try {
            call.receive<LoginRequest>()
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "Missing some Fields.."))
            return@post
        }
        try {

            val user = userService.getByUUID(userService.getIdByUserName(loginRequest.userName).toString())
            if (user == null) {
                call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "Wrong username Id"))

            } else {
                if (user.hashPassword == hashFunction(loginRequest.password)) {
                    call.respond(HttpStatusCode.OK, SimpleResponse(true, jwtService.generateToken((user))))
                } else {
                    call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "Password Incorrect !"))
                }
            }
        } catch (e: Exception) {
            call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "some Problems Occurred.."))
        }
    }


}
