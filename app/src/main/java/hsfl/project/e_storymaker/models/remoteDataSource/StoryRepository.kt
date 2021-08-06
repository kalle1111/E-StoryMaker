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
import kotlinx.coroutines.runBlocking

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
    fun getAllStories(authToken: String):List<hsfl.project.e_storymaker.roomDB.Entities.story.Story> = runBlocking {
        val client = getAuthHttpClient(authToken)
        val response: HttpResponse = client.get(STORIES)
        val jsonString: String = response.receive()
        client.close()
        val stories: List<Story> = Gson().fromJson(jsonString, Array<Story>::class.java).toList()
        val dbStories: List<hsfl.project.e_storymaker.roomDB.Entities.story.Story> = stories.map {
            convertWebserviceStoryToDbStory(it)
        }
        cacheDataStories(dbStories)
        dbStories
    }

    fun getMyStories(authToken: String): List<hsfl.project.e_storymaker.roomDB.Entities.story.Story> = runBlocking {
        val client = getAuthHttpClient(authToken)
        val response: HttpResponse = client.get(MY_STORIES)
        val jsonString: String = response.receive()
        client.close()
        val stories: List<Story> = Gson().fromJson(jsonString, Array<Story>::class.java).toList()
        val myDbStories: List<hsfl.project.e_storymaker.roomDB.Entities.story.Story> = stories.map {
            convertWebserviceStoryToDbStory(it)
        }
        cacheDataStories(myDbStories)
        myDbStories
    }

    fun createStory(authToken: String, storyRequest: StoryRequest): Boolean = runBlocking {
        val client = getAuthHttpClient(authToken)
        try {
            val response: HttpResponse = client.post(CREATE_STORIES){
                contentType(ContentType.Application.Json)
                body = storyRequest
            }
            val stringBody: String = response.receive()
            client.close()
            val webResponse: WebResponse = Gson().fromJson(stringBody, WebResponse::class.java)
            webResponse.success
        } catch (e: Exception){
            client.close()
            false
        }
    }

    fun updateStory(authToken: String, updatedStory: Story): Boolean = runBlocking {
        val client = getAuthHttpClient(authToken)
        try {
            val response: HttpResponse = client.post(UPDATE_STORIES){
                contentType(ContentType.Application.Json)
                body = updatedStory
            }
            val stringBody: String = response.receive()
            client.close()
            val webResponse:WebResponse = Gson().fromJson(stringBody, WebResponse::class.java)
            webResponse.success
        } catch (e: Exception){
            client.close()
            false
        }
    }

    fun deleteStory(authToken: String, deletedStory: Story): Boolean = runBlocking {
        val client = getAuthHttpClient(authToken)
        try {
            val response: HttpResponse = client.delete(DELETE_STORIES){
                parameter("id", deletedStory.uuid)
            }
            val stringBody: String = response.receive()
            client.close()
            val webResponse: WebResponse = Gson().fromJson(stringBody, WebResponse::class.java)
            webResponse.success
        }catch (e: Exception){
            client.close()
            false
        }
    }

    /*********Rate Story Related Functions*********/
    fun rateStory(authToken: String, rateStoryRequest: RateStoryRequest): Boolean = runBlocking {
        val client = getAuthHttpClient(authToken)
        try {
            val response: HttpResponse = client.post(RATE_STORIES){
                contentType(ContentType.Application.Json)
                body = rateStoryRequest
            }
            val stringBody: String = response.receive()
            client.close()
            val webResponse: WebResponse = Gson().fromJson(stringBody, WebResponse::class.java)
            webResponse.success
        } catch (e: Exception){
            client.close()
            false
        }
    }

    fun updateRateStory(authToken: String, rateStoryRequest: RateStoryRequest): Boolean = runBlocking {
        val client = getAuthHttpClient(authToken)
        try {
            val response: HttpResponse = client.post(UPDATE_RATED_STORIES){
                contentType(ContentType.Application.Json)
                body = rateStoryRequest
            }
            val stringBody: String = response.receive()
            client.close()
            val webResponse: WebResponse =Gson().fromJson(stringBody, WebResponse::class.java)
            webResponse.success
        }  catch (e: Exception){
            client.close()
            false
        }
    }

    fun getAllRatedStories(authToken: String): List<hsfl.project.e_storymaker.roomDB.Entities.rating.Rating> = runBlocking {
        val client = getAuthHttpClient(authToken)
        val response: HttpResponse = client.get(RATED_STORIES)
        val jsonString: String = response.receive()
        client.close()
        val ratedStories = Gson().fromJson(jsonString, Array<RatedStory>::class.java).toList()
        val dbRatedStories = ratedStories.map {
            convertWebserviceRatedStoryToDbRatedStory(it)
        }
        dbRatedStories
    }

    fun getMyRatedStories(authToken: String): List<hsfl.project.e_storymaker.roomDB.Entities.rating.Rating> = runBlocking {
        val client = getAuthHttpClient(authToken)
        val response: HttpResponse = client.get(FROM_ME_RATED_STORIES)
        val jsonString: String = response.receive()
        client.close()
        val myRatedStories = Gson().fromJson(jsonString, Array<RatedStory>::class.java).toList()
        val myDbRatedStories = myRatedStories.map {
            convertWebserviceRatedStoryToDbRatedStory(it)
        }
        myDbRatedStories
    }

    /*********Favorite Story Related Functions*********/
    fun getMyFavoriteStories(authToken: String): List<hsfl.project.e_storymaker.roomDB.Entities.favoring.Favoring> = runBlocking {
        val client = getAuthHttpClient(authToken)
        val response: HttpResponse = client.get(MY_FAVORITE_STORIES)
        val jsonString: String = response.receive()
        client.close()
        val myFavoriteStories = Gson().fromJson(jsonString, Array<StoryAsFavorite>::class.java).toList()
        val myDbFavoriteStories = myFavoriteStories.map {
            convertWebserviceFavoriteToDbFavorite(it)
        }
        myDbFavoriteStories
    }

    fun setStoryAsFavorite(authToken: String, storyAsFavoriteRequest: StoryAsFavoriteRequest): Boolean = runBlocking {
        val client = getAuthHttpClient(authToken)
        try {
            val response: HttpResponse = client.post(SET_FAVORITE_STORIES){
                contentType(ContentType.Application.Json)
                body = storyAsFavoriteRequest
            }
            val stringBody: String = response.receive()
            client.close()
            val webResponse = Gson().fromJson(stringBody, WebResponse::class.java)
            webResponse.success
        } catch (e: Exception){
            client.close()
            false
        }
    }

    fun setStoryAsNotFavorite(authToken: String, storyAsFavoriteRequest: StoryAsFavoriteRequest): Boolean = runBlocking {
        val client = getAuthHttpClient(authToken)
        try {
            val response: HttpResponse = client.post(SET_AS_NOT_FAVORITE_STORIES){
                contentType(ContentType.Application.Json)
                body = storyAsFavoriteRequest
            }
            val stringBody: String = response.receive()
            client.close()
            val webResponse = Gson().fromJson(stringBody, WebResponse::class.java)
            webResponse.success
        } catch (e: Exception){
            client.close()
            false
        }
    }

    //getStoryOverview
    ///getStoryChapter

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

