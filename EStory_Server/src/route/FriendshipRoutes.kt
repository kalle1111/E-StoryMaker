package com.eStory.route

import com.eStory.model.friendship.FriendshipRequest
import com.eStory.model.SimpleResponse
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


fun Route.FriendshipRoutes(
    friendshipService: FriendshipService
) {
    authenticate("jwt") {

        //TODO: define the routes .. 
    }
}