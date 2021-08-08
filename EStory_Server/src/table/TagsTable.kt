package com.eStory.table

import com.eStory.model.story.Story
import com.eStory.model.tag.Tag
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object TagsTable : UUIDTable() {
    val name = varchar("name", 255)
    override val primaryKey: PrimaryKey = PrimaryKey(name)
}

class TagEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<TagEntity>(TagsTable)

    var name by TagsTable.name
    fun toDTO(): Tag = Tag(this.id.toString(), this.name)

}
