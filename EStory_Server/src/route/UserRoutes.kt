package com.eStory.route

import authentication.JwtService
import com.eStory.model.user.LoginRequest
import com.eStory.model.user.RegisterRequest
import com.eStory.model.SimpleResponse
import com.eStory.model.story.UpdateStoryRequest
import com.eStory.model.user.UpdateProfile
import com.eStory.model.user.User
import com.eStory.service.UserService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import java.lang.Exception

@Location(GET_ALL_USERS)
class UsersGetRoute

@Location(REGISTER_REQUEST)
class UserRegisterRoute

@Location(LOGIN_REQUEST)
class UserLoginRoute

@Location(GET_PROFILE)
class UserGetProfile

@Location(UPDATE_PROFILE)
class UpdateProfileRoute

@Location(USER_GET_BY_USERNAME_LAST_UPDATE)
class UserGetLastUpdateRoute

@Location(USERS_GET_LAST_UPDATE_VALUES)
class UsersGetLastUpdateRouteValuesRoute

@Location(GET_USER_BY_USERNAME)
class GetUserByUsernameRoute

@Location(GET_USER_BY_UUID)
class GetUserByUUIDRoute

fun Route.UserRoutes(
    userService: UserService,
    jwtService: JwtService,
    hashFunction: (String) -> String
) {

    get<GetUserByUUIDRoute> {
        val uuid = try {
            call.request.queryParameters["uuid"]!!
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "QueryParameter:uuid is not present"))
            return@get
        }
        try {
            val user = userService.getByUUID(uuid)!!
            call.respond(HttpStatusCode.OK, user)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "Some Problems occurred"))
        }
    }


    authenticate("jwt") {
        get<UserGetProfile> {
            try {
                val username = call.principal<User>()!!.userName
                val user = userService.getByUserName(username)
                call.respond(HttpStatusCode.OK, user)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, e.message ?: "Some Problems Occurred!")

            }
        }


        post<UpdateProfileRoute> {
            // val uuid = call.parameters["uuid"]!!
            val user = try {
                call.receive<UpdateProfile>()

            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "Missing Fields.."))
                return@post
            }
            try {
                val username = call.principal<User>()!!.userName
                userService.updateByUserName(
                    username,
                    user.firstname,
                    user.lastname,
                    user.description,
                    user.birthday,
                    user.image
                )
                call.respond(HttpStatusCode.OK, SimpleResponse(true, "Users profile updated Successfully!"))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "Some Problems occurred"))
            }
        }
    }

    get<UsersGetRoute> {
        try {
            val allUsers = userService.getAll()
            call.respond(HttpStatusCode.OK, allUsers)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.Conflict, e.message ?: "Some Problems Occurred!")

        }
    }
    post<UserRegisterRoute> {
        val registerRequest = try {
            call.receive<RegisterRequest>()
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "Missing some Fields.."))
            return@post
        }
        try {
            val user = userService.insert(
                registerRequest.firstname,
                registerRequest.lastname,
                registerRequest.userName,
                registerRequest.birthday,
                registerRequest.description,
                hashFunction(registerRequest.password),
                registerRequest.image
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



    get<UserGetLastUpdateRoute> {
        val username = try {
            call.request.queryParameters["username"]!!
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "QueryParameter:username is not present"))
            return@get
        }
        try {
            val lastUpdate = userService.getLastUpdateByUserName(username)!!
            call.respond(HttpStatusCode.OK, lastUpdate)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "Some Problems occurred"))
        }
    }





    get<UsersGetLastUpdateRouteValuesRoute> {
        try {
            val lastUpdateValues = userService.getAllLastUpdateValues()
            call.respond(HttpStatusCode.OK, lastUpdateValues)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.Conflict, e.message ?: "Some Problems Occurred!")

        }
    }

    get<GetUserByUsernameRoute> {
        val username = try {
            call.request.queryParameters["username"]!!
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "QueryParameter:username is not present"))
            return@get
        }
        try {
            val user = userService.getByUserName(username)
            call.respond(HttpStatusCode.OK, user)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "Some Problems occurred"))
        }
    }

}
