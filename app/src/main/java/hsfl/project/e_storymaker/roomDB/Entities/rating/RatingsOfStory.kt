package hsfl.project.e_storymaker.roomDB.Entities.rating

import androidx.room.Embedded
import androidx.room.Relation
import hsfl.project.e_storymaker.roomDB.Entities.story.Story

data class RatingsOfStory (
    @Embedded val story: Story,
    @Relation(
        parentColumn = "story_uuid",
        entityColumn = "rating_uuid"
    )
    val ratings: List<Rating>
)