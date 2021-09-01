package hsfl.project.e_storymaker.repository.webserviceModels

data class RatedStory (
    val uuid: String,
    val user: User,
    val story: Story,
    val ratingTitle: String,
    val ratingDescription: String,
    val ratingOverallValue: Int,
    val ratingStyleValue: Int,
    val ratingStoryValue: Int,
    val ratingGrammarValue: Int,
    val ratingCharacterValue: Int,
    val lastUpdate: Long
)