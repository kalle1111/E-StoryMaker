package com.eStory.service

import com.eStory.model.user.User

import com.eStory.table.UserEntity
import com.eStory.table.UsersTable

import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.util.*

class UserService {

    fun getAll(): List<User> = transaction { UserEntity.all().map { it.toDTO() } }
    fun getByUUID(uuid: String): User? = transaction { UserEntity.findById(UUID.fromString(uuid))?.toDTO() }
    fun getByUserName(userName: String): User = transaction {
        UserEntity.find { UsersTable.userName eq userName }.first().toDTO()
    }

    fun getIdByUserName(userName: String): UUID = transaction {
        UserEntity.find { UsersTable.userName eq userName }.first().id.value
    }

    //private val users = listOf(testUser).associateBy(User::id)
    fun deleteByUUID(uuid: String): User? {
        val user = getByUUID(uuid)
        transaction {
            UserEntity.findById(UUID.fromString(uuid))?.delete()

        }
        return user
    }

    // deleting a user by username
    fun deleteByUsername(userName: String, hashPassword: String): User {
        val user = getByUserName(userName)
        transaction {
            transaction {
                UserEntity.find { (UsersTable.userName eq userName) and (UsersTable.hashPassword eq hashPassword) }
                    .first().delete()

            }

        }
        return user
    }

    // inserting new user
    fun insert(
        userName: String,
        description: String,
        password: String,
        image: ByteArray?
    ): User =
        transaction {
            UserEntity.new {
                this.hashPassword = password
                this.userName = userName
                this.description = description
                if (image != null) {
                    this.image = image
                }
                this.lastUpdate = Date().time

            }.toDTO()
        }

    //TODO: the function works not well, see update story !!
    fun updateByUUID(uuid: String, firstname: String? = null, lastname: String? = null) {
        UsersTable.update({ UsersTable.id eq UUID.fromString(uuid) }) {
        }
    }

    // update user profile by user name
    fun updateByUserName(
        username: String,
        firstname: String? = null,
        lastname: String? = null,
        description: String? = null,
        birthday: String? = null,
        image: ByteArray? = null
    ) {
        transaction {
            UsersTable.update(
                where = {
                    UsersTable.userName.eq(username)
                }
            ) { ut ->
                if (image != null) {
                    ut[this.image] = image
                }
                ut[this.lastUpdate] = Date().time

            }
        }


    }

    /*****************get Date ***************/

    // the attribute lastUpdate is refreshed when the profile updated
    fun getLastUpdateByUserName(userName: String): Long =
        transaction { UserEntity.find { UsersTable.userName eq userName }.first().lastUpdate }

    fun getAllLastUpdateValues(): List<Pair<String, Long>> =
        transaction { UserEntity.all().map { Pair(it.userName, it.lastUpdate) } }
}