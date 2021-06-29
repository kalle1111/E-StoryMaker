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

    fun insert(
        firstname: String,
        lastname: String,
        userName: String,
        birthday: String,
        description: String,
        password: String
    ): User =
        transaction {
            UserEntity.new {
                this.firstname = firstname
                this.lastname = lastname
                this.hashPassword = password
                this.userName = userName
                this.description = description
                this.birthday = birthday

            }.toDTO()
        }

    //TODO: the function works not well, see update story !!
    fun updateByUUID(uuid: String, firstname: String? = null, lastname: String? = null) {
        UsersTable.update({ UsersTable.id eq UUID.fromString(uuid) }) {
            if (firstname != null) {
                it[this.firstname] = firstname
            }
            if (lastname != null) {
                it[this.lastname] = lastname
            }
        }
    }
}
