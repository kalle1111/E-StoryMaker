package com.eStory.table

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object FilesTable : UUIDTable() {

    val binaryFile = binary("binaryFile", Int.MAX_VALUE)

}

class FileEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<FileEntity>(FilesTable)

    var binaryFile by FilesTable.binaryFile
}