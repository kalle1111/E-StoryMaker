package hsfl.project.e_storymaker.roomDB.Entities.story

import androidx.room.Embedded
import androidx.room.Relation
import hsfl.project.e_storymaker.roomDB.Entities.user.User

data class AuthorWithStories (
    @Embedded val author: User,
    @Relation(
        parentColumn = "user_uuid",
        entityColumn = "author_uuid"
    )
    val stories: List<Story>
)