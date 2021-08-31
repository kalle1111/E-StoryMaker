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
            SchemaUtils.create(ChaptersTable)
            SchemaUtils.create(FriendshipsTable)
            SchemaUtils.create(RatedStoriesTable)
            SchemaUtils.create(StoriesAsFavoriteTable)
            SchemaUtils.create(TagsTable)
            SchemaUtils.create(TaggedStoriesTable)
            SchemaUtils.create(FilesTable)
            SchemaUtils.create(ReadChaptersTable)
        }

        TagService().insertAllTags()


    }


    //TODO: STILL NOT USED
    suspend fun <T> dbQuery(block: () -> T): T =
        withContext(Dispatchers.IO) {
            transaction { block() }
        }
}