package com.eStory.table

import com.eStory.service.TagService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
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
            SchemaUtils.create(TagsTable)
            SchemaUtils.create(TaggedStoriesTable)

/*
            UsersTable.deleteAll()
            StoriesTable.deleteAll()
            FriendshipsTable.deleteAll()
            RatedStoriesTable.deleteAll()*/
        }

      //  TagService().insertAllTags()

    }


    //TODO: STILL NOT USED
    suspend fun <T> dbQuery(block: () -> T): T =
        withContext(Dispatchers.IO) {
            transaction { block() }
        }
}