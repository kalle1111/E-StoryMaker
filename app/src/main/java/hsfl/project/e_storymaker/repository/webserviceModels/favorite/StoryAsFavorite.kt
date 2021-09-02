package hsfl.project.e_storymaker.repository.webserviceModels.favorite

import hsfl.project.e_storymaker.repository.webserviceModels.story.Story
import hsfl.project.e_storymaker.repository.webserviceModels.user.User

data class StoryAsFavorite (val uuid: String, val user: User, val story: Story)