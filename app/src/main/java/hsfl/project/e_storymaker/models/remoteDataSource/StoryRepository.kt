package hsfl.project.e_storymaker.models.remoteDataSource

import android.app.Application
import com.google.gson.Gson
import hsfl.project.e_storymaker.repository.webserviceModels.*
import hsfl.project.e_storymaker.roomDB.AppDatabase
import hsfl.project.e_storymaker.roomDB.AppRepository
import io.ktor.client.call.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class StoryRepository(application: Application){

    private val repository: AppRepository

    init{

        val database = AppDatabase.getDatabase(application)

        val userDao = database.userDao()
        val storyDao = database.storyDao()
        val friendshipDao = database.friendshipDao()
        val chapterProgressDao = database.chapterProgressDao()
        val favoringDao = database.favoringDao()
        val ratingDao = database.ratingDao()

        repository = AppRepository(userDao, storyDao, friendshipDao,
            chapterProgressDao, favoringDao, ratingDao)
    }

    /*********Story Related Functions*********/
    suspend fun getAllStories(authToken: String):List<hsfl.project.e_storymaker.roomDB.Entities.story.Story>{
        val client = getAuthHttpClient(authToken)
        val response: HttpResponse = client.get(STORIES)
        val jsonString: String = response.receive()
        client.close()
        val stories: List<Story> = Gson().fromJson(jsonString, Array<Story>::class.java).toList()
        val dbStories: List<hsfl.project.e_storymaker.roomDB.Entities.story.Story> = stories.map {
            convertWebserviceStoryToDbStory(it)
        }
        cacheDataStories(dbStories)
        return dbStories
    }

    suspend fun getMyStories(authToken: String): List<hsfl.project.e_storymaker.roomDB.Entities.story.Story>{
        val client = getAuthHttpClient(authToken)
        val response: HttpResponse = client.get(MY_STORIES)
        val jsonString: String = response.receive()
        client.close()
        val stories: List<Story> = Gson().fromJson(jsonString, Array<Story>::class.java).toList()
        val myDbStories: List<hsfl.project.e_storymaker.roomDB.Entities.story.Story> = stories.map {
            convertWebserviceStoryToDbStory(it)
        }
        cacheDataStories(myDbStories)
        return myDbStories
    }

    suspend fun createStory(authToken: String, storyRequest: StoryRequest): Boolean {
        val client = getAuthHttpClient(authToken)
        return try {
            val response: HttpResponse = client.post(CREATE_STORIES){
                contentType(ContentType.Application.Json)
                body = storyRequest
            }
            val stringBody: String = response.receive()
            client.close()
            val webResponse: WebResponse = Gson().fromJson(stringBody, WebResponse::class.java)
            return webResponse.success
        } catch (e: Exception){
            client.close()
            return false
        }
    }

    suspend fun updateStory(authToken: String, updatedStory: Story): Boolean {
        val client = getAuthHttpClient(authToken)
        return try {
            val response: HttpResponse = client.post(UPDATE_STORIES){
                contentType(ContentType.Application.Json)
                body = updatedStory
            }
            val stringBody: String = response.receive()
            client.close()
            val webResponse:WebResponse = Gson().fromJson(stringBody, WebResponse::class.java)
            return webResponse.success
        } catch (e: Exception){
            client.close()
            return false
        }
    }

    suspend fun deleteStory(authToken: String, deletedStory: Story): Boolean {
        val client = getAuthHttpClient(authToken)
        return try {
            val response: HttpResponse = client.delete(DELETE_STORIES){
                parameter("id", deletedStory.uuid)
            }
            val stringBody: String = response.receive()
            client.close()
            val webResponse: WebResponse = Gson().fromJson(stringBody, WebResponse::class.java)
            return webResponse.success
        }catch (e: Exception){
            client.close()
            return false
        }
    }

    /*********Rate Story Related Functions*********/
    suspend fun rateStory(authToken: String, rateStoryRequest: RateStoryRequest): Boolean {
        val client = getAuthHttpClient(authToken)
        return try {
            val response: HttpResponse = client.post(RATE_STORIES){
                contentType(ContentType.Application.Json)
                body = rateStoryRequest
            }
            val stringBody: String = response.receive()
            client.close()
            val webResponse: WebResponse = Gson().fromJson(stringBody, WebResponse::class.java)
            return webResponse.success
        } catch (e: Exception){
            client.close()
            return false
        }
    }

    suspend fun updateRateStory(authToken: String, rateStoryRequest: RateStoryRequest): Boolean {
        val client = getAuthHttpClient(authToken)
        return try {
            val response: HttpResponse = client.post(UPDATE_RATED_STORIES){
                contentType(ContentType.Application.Json)
                body = rateStoryRequest
            }
            val stringBody: String = response.receive()
            client.close()
            val webResponse: WebResponse =Gson().fromJson(stringBody, WebResponse::class.java)
            return webResponse.success
        }  catch (e: Exception){
            client.close()
            return false
        }
    }

    suspend fun getAllRatedStories(authToken: String): List<hsfl.project.e_storymaker.roomDB.Entities.rating.Rating>{
        val client = getAuthHttpClient(authToken)
        val response: HttpResponse = client.get(RATED_STORIES)
        val jsonString: String = response.receive()
        client.close()
        val ratedStories = Gson().fromJson(jsonString, Array<RatedStory>::class.java).toList()
        val dbRatedStories = ratedStories.map {
            convertWebserviceRatedStoryToDbRatedStory(it)
        }
        return dbRatedStories
    }

    suspend fun getMyRatedStories(authToken: String): List<hsfl.project.e_storymaker.roomDB.Entities.rating.Rating>{
        val client = getAuthHttpClient(authToken)
        val response: HttpResponse = client.get(FROM_ME_RATED_STORIES)
        val jsonString: String = response.receive()
        client.close()
        val myRatedStories = Gson().fromJson(jsonString, Array<RatedStory>::class.java).toList()
        val myDbRatedStories = myRatedStories.map {
            convertWebserviceRatedStoryToDbRatedStory(it)
        }
        return myDbRatedStories
    }

    /*********Favorite Story Related Functions*********/
    suspend fun getMyFavoriteStories(authToken: String): List<hsfl.project.e_storymaker.roomDB.Entities.favoring.Favoring>{
        val client = getAuthHttpClient(authToken)
        val response: HttpResponse = client.get(MY_FAVORITE_STORIES)
        val jsonString: String = response.receive()
        client.close()
        val myFavoriteStories = Gson().fromJson(jsonString, Array<StoryAsFavorite>::class.java).toList()
        val myDbFavoriteStories = myFavoriteStories.map {
            convertWebserviceFavoriteToDbFavorite(it)
        }
        return myDbFavoriteStories
    }

    suspend fun setStoryAsFavorite(authToken: String, storyAsFavoriteRequest: StoryAsFavoriteRequest): Boolean {
        val client = getAuthHttpClient(authToken)
        return try {
            val response: HttpResponse = client.post(SET_FAVORITE_STORIES){
                contentType(ContentType.Application.Json)
                body = storyAsFavoriteRequest
            }
            val stringBody: String = response.receive()
            client.close()
            val webResponse = Gson().fromJson(stringBody, WebResponse::class.java)
            return webResponse.success
        } catch (e: Exception){
            client.close()
            return false
        }
    }

    suspend fun setStoryAsNotFavorite(authToken: String, storyAsFavoriteRequest: StoryAsFavoriteRequest): Boolean {
        val client = getAuthHttpClient(authToken)
        return try {
            val response: HttpResponse = client.post(SET_AS_NOT_FAVORITE_STORIES){
                contentType(ContentType.Application.Json)
                body = storyAsFavoriteRequest
            }
            val stringBody: String = response.receive()
            client.close()
            val webResponse = Gson().fromJson(stringBody, WebResponse::class.java)
            return webResponse.success
        } catch (e: Exception){
            client.close()
            return false
        }
    }


    fun cacheDataStories(stories: List<hsfl.project.e_storymaker.roomDB.Entities.story.Story>) {
        //repository.addStories(stories)
    }

    fun deleteData() {
    }

    fun updateData(){}


    companion object {
        @Volatile
        private var INSTANCE: StoryRepository? = null

        fun getStoryRepository(application: Application): StoryRepository? {
            val instance = INSTANCE
            if (instance != null){
                return instance
            }

            INSTANCE = StoryRepository(application)
            return INSTANCE
            }
        }
    }

