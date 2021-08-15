package com.eStory.service

import com.eStory.model.friendship.Friendship
import com.eStory.table.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.text.SimpleDateFormat
import java.util.*

class FriendshipService {
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

    private fun getAll(): List<Friendship> = transaction { FriendshipEntity.all().map { it.toDTO() } }
    fun insertRequest(
        requesterUserName: String,
        friendUserName: String
    ): Friendship =
        transaction {
            FriendshipEntity.new {
                this.requesterUserEntity = UserEntity.find { UsersTable.userName eq requesterUserName }.first()
                this.friendEntity = UserEntity.find { UsersTable.userName eq friendUserName }.first()
                this.isAccepted = false
                this.requestTime =  Date().time
            }.toDTO()
        }

    fun getMyFriendsByUserName(myUserName: String): List<Friendship> =
        getAll().filter { (it.requesterUser.userName == myUserName || it.friend.userName == myUserName) && it.isAccepted }

    // this has be called continually through a timer in the client to know if the client has got new friendship requests or not !
    fun getAllRequestsToMe(userName: String): List<Friendship> =

        getAll().filter { it.friend.userName == userName && !it.isAccepted }

    fun getAllRequestsFromMe(myUserName: String): List<Friendship> =
        getAll().filter { it.requesterUser.userName == myUserName && !it.isAccepted }

    fun acceptRequestFromOther(myUserName: String, friendUserName: String) {
        transaction {
            FriendshipsTable.update(
                where = {
                    FriendshipsTable.requesterUserName.eq(friendUserName) and FriendshipsTable.friendName.eq(
                        myUserName
                    )
                }
            ) { fs ->
                fs[this.isAccepted] = true
            }
        }
    }

    fun rejectRequestFromOther(myUserName: String, friendUserName: String): Friendship {
        val friendship = getAllRequestsToMe(myUserName).first { it.requesterUser.userName == friendUserName }
        transaction {
            FriendshipEntity.find {

                FriendshipsTable.requesterUserName.eq(friendUserName) and (FriendshipsTable.friendName.eq(myUserName))

            }.first().delete()
        }
        return friendship
    }

    fun cancelARequestFromMeTOAnother(myUserName: String, friendUserName: String): Friendship {
        val friendship =
            getAllRequestsFromMe(myUserName).first { it.friend.userName == friendUserName }
        transaction {
            FriendshipEntity.find {

                FriendshipsTable.requesterUserName.eq(myUserName) and (FriendshipsTable.friendName.eq(friendUserName))

            }.first().delete()
        }
        return friendship
    }

    fun deleteAFriendship(myUserName: String, friendUserName: String): Friendship {
        val friendship = getMyFriendsByUserName(myUserName).first { it.friend.userName == friendUserName }
        transaction {
            FriendshipEntity.find {
                val bool1 =
                    FriendshipsTable.requesterUserName.eq(myUserName) and (FriendshipsTable.friendName.eq(friendUserName))
                val bool2 = FriendshipsTable.requesterUserName.eq(friendUserName) and (FriendshipsTable.friendName.eq(
                    friendUserName
                ))
                bool1.or(bool2)

            }.first().delete()
        }
        return friendship
    }

}