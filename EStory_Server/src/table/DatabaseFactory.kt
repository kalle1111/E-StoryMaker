package com.eStory.table

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
        Database.connect("jdbc:sqlite:data.db", "org.sqlite.JDBC")
        transaction {
            SchemaUtils.create(UsersTable)
            SchemaUtils.create(StoriesTable)
            SchemaUtils.create(FriendshipsTable)
            SchemaUtils.create(RatedStoriesTable)
            SchemaUtils.create(StoriesAsFavoriteTable)
/*
            UsersTable.deleteAll()
            StoriesTable.deleteAll()
            FriendshipsTable.deleteAll()
            RatedStoriesTable.deleteAll()*/
        }
    }

    //TODO: STILL NOT USED
    suspend fun <T> dbQuery(block: () -> T): T =
        withContext(Dispatchers.IO) {
            transaction { block() }
        }
}