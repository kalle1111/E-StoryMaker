package com.eStory.route

import com.eStory.model.friendship.FriendshipServiceModel
import com.eStory.model.SimpleResponse
import com.eStory.model.user.User
import com.eStory.service.FriendshipService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import java.lang.Exception

/**
 * @author Mohammad
 * probabilities:
 * 1. User sends a request to a friend  ==> insertRequest()
 * 2. User wants to know all his friends ( its normal, if the requester is the user himself or another friend) ==> getMyFriendsByUserName()
 * 3. User has to know regularly, if he got Requests from others ( if others sent to him requests) ==> getAllRequestsToMe() , this fun has to be called continually through a timer in the client
 * 4. User wants to Know all his requests to others ==> getAllRequestsFromMe()
 * 5. User rejects a request from another user ==> rejectRequestFromOther()
 * 6. User accepts a request from another user ==> acceptRequestFromOther()
 * 7. User cancels a request he has sent to another user ==> cancelARequestFromMeTOAnother()
 * 8. User deletes a friendship with a friend ==> deleteAFriendship()
 */


@Location(REQUEST_FRIENDSHIPS)
class FriendshipRequestRoute

@Location(MY_FRIENDSHIPS)
class MyFriendshipsGetRoute

@Location(FRIENDSHIPS_GET_REQUESTS_TO_ME)
class FriendshipsRequestsTOMeGetRoute


@Location(FRIENDSHIPS_GET_REQUESTS_From_ME)
class FriendshipsRequestsFromMeGetRoute


@Location(REJECT_FRIENDSHIPS)
class FriendshipRejectRequestRoute

@Location(ACCEPT_FRIENDSHIPS)
class FriendshipAcceptRequestRoute

@Location(CANCEL_FRIENDSHIPS)
class FriendshipCancelRequestRoute


@Location(DELETE_FRIENDSHIPS)
class FriendshipDeleteRoute

@Location(GET_FRIENDSHIP_BY_UUID)
class GetFriendshipByUUIDRoute

fun Route.FriendshipRoutes(
    friendshipService: FriendshipService
) {
    get<GetFriendshipByUUIDRoute> {
        val uuid = try {
            call.request.queryParameters["uuid"]!!
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "QueryParameter:uuid is not present"))
            return@get
        }
        try {
            val friendship = friendshipService.getByUUID(uuid)!!
            call.respond(HttpStatusCode.OK, friendship)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "Some Problems occurred"))
        }
    }
    authenticate("jwt") {

        post<FriendshipRequestRoute> {

            val friendshipRequest = try {

                call.receive<FriendshipServiceModel>()


            } catch (e: Exception) {

                call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "Missing Fields.."))
                return@post
            }
            try {
                val username = call.principal<User>()!!.userName
                friendshipService.insertRequest(username, friendshipRequest.friendUserName)
                call.respond(HttpStatusCode.OK, SimpleResponse(true, "Friendship requested Successfully!"))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "Some Problems occurred"))
            }

        }

        get<MyFriendshipsGetRoute> {
            try {
                val userName = call.principal<User>()!!.userName
                val myFriendships = friendshipService.getMyFriendsByUserName(userName)
                call.respond(HttpStatusCode.OK, myFriendships)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, e.message ?: "Some Problems Occurred!")

            }

        }
        get<FriendshipsRequestsTOMeGetRoute> {
            try {
                val userName = call.principal<User>()!!.userName
                val requestedFriendshipsToMe = friendshipService.getAllRequestsToMe(userName)
                call.respond(HttpStatusCode.OK, requestedFriendshipsToMe)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, e.message ?: "Some Problems Occurred!")
            }
        }


        get<FriendshipsRequestsFromMeGetRoute> {
            try {
                val userName = call.principal<User>()!!.userName
                val requestedFriendshipsFromMe = friendshipService.getAllRequestsFromMe(userName)
                call.respond(HttpStatusCode.OK, requestedFriendshipsFromMe)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, e.message ?: "Some Problems Occurred!")
            }
        }

        post<FriendshipRejectRequestRoute> {

            val friendshipReject = try {

                call.receive<FriendshipServiceModel>()


            } catch (e: Exception) {

                call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "Missing Fields.."))
                return@post
            }
            try {
                val username = call.principal<User>()!!.userName
                friendshipService.rejectRequestFromOther(username, friendshipReject.friendUserName)
                call.respond(HttpStatusCode.OK, SimpleResponse(true, "Friendship rejected Successfully!"))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "Some Problems occurred"))
            }

        }

        post<FriendshipAcceptRequestRoute> {

            val friendshipAccept = try {

                call.receive<FriendshipServiceModel>()


            } catch (e: Exception) {

                call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "Missing Fields.."))
                return@post
            }
            try {
                val username = call.principal<User>()!!.userName
                friendshipService.acceptRequestFromOther(username, friendshipAccept.friendUserName)
                call.respond(HttpStatusCode.OK, SimpleResponse(true, "Friendship accepted Successfully!"))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "Some Problems occurred"))
            }

        }

        post<FriendshipCancelRequestRoute> {

            val friendshipCancel = try {

                call.receive<FriendshipServiceModel>()


            } catch (e: Exception) {

                call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "Missing Fields.."))
                return@post
            }
            try {
                val username = call.principal<User>()!!.userName
                friendshipService.cancelARequestFromMeTOAnother(username, friendshipCancel.friendUserName)
                call.respond(HttpStatusCode.OK, SimpleResponse(true, "Friendship canceled Successfully!"))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "Some Problems occurred"))
            }

        }

        delete<FriendshipDeleteRoute> {
            val friendshipDelete = try {
                call.receive<FriendshipServiceModel>()
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "Missing Fields.."))
                return@delete
            }
            try {
                val username = call.principal<User>()!!.userName
                friendshipService.deleteAFriendship(username, friendshipDelete.friendUserName)
                call.respond(HttpStatusCode.OK, SimpleResponse(true, "friendship deleted Successfully!"))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, SimpleResponse(false, e.message ?: "Some Problems occurred"))
            }
        }

    }
}