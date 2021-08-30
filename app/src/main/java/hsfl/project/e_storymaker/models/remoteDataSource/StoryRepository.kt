package hsfl.project.e_storymaker.models.remoteDataSource

import android.app.Activity
import android.app.Application
import android.util.Log

import com.google.gson.Gson
import hsfl.project.e_storymaker.repository.webserviceModels.*
import hsfl.project.e_storymaker.roomDB.AppDatabase
import hsfl.project.e_storymaker.roomDB.AppRepository
import hsfl.project.e_storymaker.roomDB.Entities.chapterProgress.ChapterProgressDao
import hsfl.project.e_storymaker.roomDB.Entities.favoring.FavoringDao
import hsfl.project.e_storymaker.roomDB.Entities.friendship.FriendshipDao
import hsfl.project.e_storymaker.roomDB.Entities.rating.Rating
import hsfl.project.e_storymaker.roomDB.Entities.rating.RatingDao
import hsfl.project.e_storymaker.roomDB.Entities.story.StoryDao
import hsfl.project.e_storymaker.roomDB.Entities.tag.Tag
import hsfl.project.e_storymaker.roomDB.Entities.tag.TagDao
import hsfl.project.e_storymaker.roomDB.Entities.user.UserDao
import hsfl.project.e_storymaker.sharedPreferences
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.utils.io.concurrent.*
import io.reactivex.internal.util.AppendOnlyLinkedArrayList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.withTestContext
import kotlinx.coroutines.withContext
import kotlin.time.measureTimedValue

class StoryRepository(application: Application){

    private final val TAG = "Story Rep"

    private val repository: AppRepository
    private val application: Application = application

    val userDao: UserDao
    val storyDao: StoryDao
    val friendshipDao: FriendshipDao
    val chapterProgressDao: ChapterProgressDao
    val favoringDao: FavoringDao
    val ratingDao: RatingDao
    val tagDao: TagDao

    init{

        val database = AppDatabase.getDatabase(application)

        userDao = database.userDao()
        storyDao = database.storyDao()
        friendshipDao = database.friendshipDao()
        chapterProgressDao = database.chapterProgressDao()
        favoringDao = database.favoringDao()
        ratingDao = database.ratingDao()
        tagDao = database.tagDao()

        repository = AppRepository(userDao, storyDao, friendshipDao,
            chapterProgressDao, favoringDao, ratingDao)
    }

    /*********Story Related Functions*********/
    fun getAllStories():List<hsfl.project.e_storymaker.roomDB.Entities.story.Story> = runBlocking {
        return@runBlocking withContext(Dispatchers.IO) {
            //val authToken = sharedPreferences.getJWT(activity)
            val storiesWithTimestamp = getAllStoriesTimestamp()
            val dbStories: MutableList<hsfl.project.e_storymaker.roomDB.Entities.story.Story> = mutableListOf<hsfl.project.e_storymaker.roomDB.Entities.story.Story>()
            storiesWithTimestamp.map {
                if (!storyDao.rowExistByUUID(it.first)) {
                    //Insert Story
                    val storyToInsert = getStoryByUUID(it.first)
                    if (storyToInsert != null){
                        Log.d(TAG, "Story ist nicht in der DB!")
                        storyDao.insertWithTimestamp(storyToInsert)
                        dbStories.add(storyDao.getStoryByUuid(it.first))
                    } else {
                        Log.e(TAG, "EMPTY ELSE! 1")
                    }
                } else {
                    if (storyDao.getStoryByUuid(it.first).cachedTime < it.second) {
                        //Die Story anfordern und in der Datenbank speichern
                        val storyToInsert = getStoryByUUID(it.first)
                        if (storyToInsert != null){
                            Log.d(TAG, "Story is outdated, locally!")
                            storyDao.insertWithTimestamp(storyToInsert)
                            dbStories.add(storyDao.getStoryByUuid(it.first))
                        } else {
                            Log.e(TAG, "EMPTY ELSE! 2")
                        }
                    } else {
                        Log.d(TAG, "Story is up to date locally!")
                        dbStories.add(storyDao.getStoryByUuid(it.first))
                    }
                }
            }
            dbStories
        }
    }

    private fun getStoryByUUID(uuid: String): hsfl.project.e_storymaker.roomDB.Entities.story.Story? = runBlocking {
        return@runBlocking withContext(Dispatchers.IO){
            val client = getHttpClient()
            try {
                val response: HttpResponse = client.get(GET_STORY_BY_UUID){
                    parameter("uuid", uuid)
                }
                val stringBody: String = response.receive()
                client.close()
                val story: Story = Gson().fromJson(stringBody, Story::class.java)
                convertWebserviceStoryToDbStory(story)
            }catch (e: Exception){
                client.close()
                null
            }
        }
    }

    private fun getAllStoriesTimestamp() = runBlocking {
        return@runBlocking withContext(Dispatchers.IO){
            val client = HttpClient(CIO)
            val response: HttpResponse = client.get(STORIES_GET_LAST_UPDATE_VALUES)
            val jsonString: String = response.receive()
            Log.d(TAG, jsonString)
            client.close()
            val storiesPairLastUpdate = Gson().fromJson(jsonString, Array<PairLastUpdate>::class.java).toList()
            storiesPairLastUpdate
        }
    }

    fun getMyStories(): List<hsfl.project.e_storymaker.roomDB.Entities.story.Story> = runBlocking {
        return@runBlocking withContext(Dispatchers.IO) {
            val username = sharedPreferences.getUsername(application)!!
            val authToken = sharedPreferences.getJWT(application)!!
            val storiesWithTimestamp = getMyStoriesTimestamp(username, authToken)
            val dbStories: MutableList<hsfl.project.e_storymaker.roomDB.Entities.story.Story> = mutableListOf<hsfl.project.e_storymaker.roomDB.Entities.story.Story>()
            storiesWithTimestamp.map {
                if (!storyDao.rowExistByUUID(it.first)) {
                    //Insert Story
                    val storyToInsert = getStoryByUUID(it.first)
                    if (storyToInsert != null){
                        storyDao.insertWithTimestamp(storyToInsert)
                        dbStories.add(storyDao.getStoryByUuid(it.first))
                    } else {

                    }
                } else {
                    if (storyDao.getStoryByUuid(it.first).cachedTime > it.second) {
                        //Die Story anfordern und in der Datenbank speichern
                        val storyToInsert = getStoryByUUID(it.first)
                        if (storyToInsert != null){
                            storyDao.insertWithTimestamp(storyToInsert)
                            dbStories.add(storyDao.getStoryByUuid(it.first))
                        } else {

                        }
                    } else {
                        dbStories.add(storyDao.getStoryByUuid(it.first))
                    }
                }
            }
            dbStories
        }
    }

    private fun getMyStoriesTimestamp(username: String, authToken: String) = runBlocking {
        return@runBlocking withContext(Dispatchers.IO){
            val client = getAuthHttpClient(authToken)
            val response: HttpResponse = client.get(MY_STORIES_GET_LAST_UPDATE_VALUES){
                parameter("username", username)
            }
            val jsonString: String = response.receive()
            Log.d(TAG, jsonString)
            client.close()
            val storiesPairLastUpdate = Gson().fromJson(jsonString, Array<PairLastUpdate>::class.java).toList()
            storiesPairLastUpdate
        }
    }

    fun createStory(storyRequest: StoryRequest): Boolean = runBlocking {
        val authToken = sharedPreferences.getJWT(application)!!
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

    fun updateStory(updatedStory: UpdateStoryRequest): Boolean = runBlocking {
        return@runBlocking withContext(Dispatchers.IO){
            val authToken = sharedPreferences.getJWT(application)!!
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
    }

    fun deleteStory(deletedStory: hsfl.project.e_storymaker.roomDB.Entities.story.Story): Boolean = runBlocking {
        return@runBlocking withContext(Dispatchers.IO){
            val authToken = sharedPreferences.getJWT(application)!!
            val client = getAuthHttpClient(authToken)
            try {
                val response: HttpResponse = client.delete(DELETE_STORIES){
                    parameter("id", deletedStory.story_uuid)
                }
                val stringBody: String = response.receive()
                client.close()
                val webResponse: WebResponse = Gson().fromJson(stringBody, WebResponse::class.java)
                if (webResponse.success){
                    storyDao.delete(storyDao.getStoryByUuid(deletedStory.story_uuid))
                }
                webResponse.success
            }catch (e: Exception){
                client.close()
                false
            }
        }
    }

    private fun compareStoriesWithTimestamp(timestamps: List<PairLastUpdate>, authToken: String): List<hsfl.project.e_storymaker.roomDB.Entities.story.Story> = runBlocking {
        return@runBlocking withContext(Dispatchers.IO){
            val httpClient = getAuthHttpClient(authToken)
            val stories: MutableList<hsfl.project.e_storymaker.roomDB.Entities.story.Story> = mutableListOf()
            timestamps.map{
                if(!storyDao.rowExistByUUID(it.first)){
                    val storyToInsert = getStoryByUUID(it.first)
                    if (storyToInsert != null){
                        storyDao.insertWithTimestamp(storyToInsert)
                        stories.add(storyDao.getStoryByUuid(it.first))
                    } else {

                    }
                } else {
                    if(storyDao.getStoryByUuid(it.first).cachedTime < it.second){
                        val storyToInsert = getStoryByUUID(it.first)
                        if (storyToInsert != null){
                            storyDao.delete(storyDao.getStoryByUuid(it.first))
                            storyDao.insertWithTimestamp(storyToInsert)
                            stories.add(storyDao.getStoryByUuid(it.first))
                        } else {
                            stories.add(storyDao.getStoryByUuid(it.first))
                        }
                    } else {

                    }
                }
            }
            stories
        }
    }

    fun getStoryPerUUIDList(uuidList: List<String>) : List<hsfl.project.e_storymaker.roomDB.Entities.story.Story> = runBlocking {
        return@runBlocking withContext(Dispatchers.IO){
            val stories: MutableList<hsfl.project.e_storymaker.roomDB.Entities.story.Story> = mutableListOf()
            uuidList.forEach {
                val timestampOfStory = getStoryTimestampPerUUID(it)

                if (!storyDao.rowExistByUUID(it)) {
                    //Insert Story
                    val storyToInsert = getStoryByUUID(it)
                    if (storyToInsert != null){
                        storyDao.insertWithTimestamp(storyToInsert)
                        stories.add(storyDao.getStoryByUuid(it))
                    } else {

                    }
                } else {
                    if (storyDao.getStoryByUuid(it).cachedTime > timestampOfStory.toLong()) {
                        //Die Story anfordern und in der Datenbank speichern
                        val storyToInsert = getStoryByUUID(it)
                        if (storyToInsert != null){
                            storyDao.insertWithTimestamp(storyToInsert)
                            stories.add(storyDao.getStoryByUuid(it))
                        } else {

                        }
                    } else {
                        stories.add(storyDao.getStoryByUuid(it))
                    }
                }
            }
            stories
        }
    }



    private fun getStoryTimestampPerUUID(uuid: String): String = runBlocking {
        return@runBlocking withContext(Dispatchers.IO) {
            val client = HttpClient(CIO)
            val response: HttpResponse = client.get(STORY_GET_BY_ID_LAST_UPDATE){
                parameter("uuid", uuid)
            }
            val lastUpdate: String = response.receive()
            lastUpdate
        }
    }

    fun getStory(uuid: String): hsfl.project.e_storymaker.roomDB.Entities.story.Story? = runBlocking {
        return@runBlocking withContext(Dispatchers.IO){
            val storyTimestamp =getStoryTimestampPerUUID(uuid).toLong()
            if (!storyDao.rowExistByUUID(uuid)){
                val story = getStoryByUUID(uuid)!!
                storyDao.insertWithTimestamp(story)
                storyDao.getStoryByUuid(uuid)
            } else {
                if(storyDao.getStoryByUuid(uuid).cachedTime > storyTimestamp){
                    val story = getStoryByUUID(uuid)!!
                    storyDao.insertWithTimestamp(story)
                    storyDao.getStoryByUuid(uuid)
                } else {
                    storyDao.getStoryByUuid(uuid)
                }
            }
        }
    }


    /*********Rate Story Related Functions*********/
    fun rateStory(rateStoryRequest: RateStoryRequest): Boolean = runBlocking {
        withContext(Dispatchers.IO){
            val authToken = sharedPreferences.getJWT(application)!!
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
    }

    fun updateRateStory(rateStoryRequest: RateStoryRequest): Boolean = runBlocking {
        withContext(Dispatchers.IO){
            val authToken = sharedPreferences.getJWT(application)!!
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
    }

    fun getAllRatedStories(): List<hsfl.project.e_storymaker.roomDB.Entities.rating.Rating> = runBlocking {
        withContext(Dispatchers.IO){
            val ratedStoriesTimestamp = getAllRatedStoriesTimestamp()
            val dbRatedStories: MutableList<hsfl.project.e_storymaker.roomDB.Entities.rating.Rating> = mutableListOf<hsfl.project.e_storymaker.roomDB.Entities.rating.Rating>()
            ratedStoriesTimestamp.map {
                if (!ratingDao.rowExistByUUID(it.first)) {
                    //Insert Rating
                    val ratedStoryToInsert = getRatedStoryByUUID(it.first)
                    if(ratedStoryToInsert != null){
                        ratingDao.insertRating(ratedStoryToInsert)
                        dbRatedStories.add(ratingDao.getRatingByUuid(it.first))
                    } else {

                    }
                } else {
                    if (storyDao.getStoryByUuid(it.first).cachedTime > it.second) {
                        //Das Rating anfordern und in der Datenbank speichern
                        val ratedStoryToInsert = getRatedStoryByUUID(it.first)
                        if(ratedStoryToInsert != null){
                            ratingDao.deleteRating(ratedStoryToInsert.user_username, ratedStoryToInsert.story_uuid)
                            ratingDao.insertRating(ratedStoryToInsert)
                            dbRatedStories.add(ratingDao.getRatingByUuid(it.first))
                        } else {

                        }
                    } else {
                        dbRatedStories.add(ratingDao.getRatingByUuid(it.first))
                    }
                }
            }
            dbRatedStories
        }
    }

    private fun getAllRatedStoriesTimestamp() = runBlocking {
        return@runBlocking withContext(Dispatchers.IO){
            val client = HttpClient(CIO)
            val response: HttpResponse = client.get(RATED_STORIES_GET_LAST_UPDATE_VALUES)
            val jsonString: String = response.receive()
            Log.d(TAG, jsonString)
            client.close()
            val ratedStoriesPairLastUpdate = Gson().fromJson(jsonString, Array<PairLastUpdate>::class.java).toList()
            ratedStoriesPairLastUpdate
        }
    }

    private fun getRatedStoryByUUID(uuid: String): Rating? = runBlocking {
        return@runBlocking withContext(Dispatchers.IO){
            val client = getHttpClient()
            try {
                val response: HttpResponse = client.delete(GET_RATED_STORY_BY_UUID){
                    parameter("uuid", uuid)
                }
                val stringBody: String = response.receive()
                client.close()
                val ratedStory: RatedStory = Gson().fromJson(stringBody, RatedStory::class.java)
                convertWebserviceRatedStoryToDbRatedStory(ratedStory)
            }catch (e: Exception){
                client.close()
                null
            }
        }
    }

    fun getMyRatedStories(): List<hsfl.project.e_storymaker.roomDB.Entities.rating.Rating> = runBlocking {
        return@runBlocking withContext(Dispatchers.IO){
            val authToken = sharedPreferences.getJWT(application)!!
            val username = sharedPreferences.getUsername(application)!!
        val ratedStoriesTimestamp = getMyRatedStoriesTimestamp(authToken, username)
        val dbRatedStories: MutableList<hsfl.project.e_storymaker.roomDB.Entities.rating.Rating> = mutableListOf<hsfl.project.e_storymaker.roomDB.Entities.rating.Rating>()
        ratedStoriesTimestamp.map {
            if (!ratingDao.rowExistByUUID(it.first)) {
                //Insert Rating
                val ratedStoryToInsert = getRatedStoryByUUID(it.first)
                if(ratedStoryToInsert != null){
                    ratingDao.insertRating(ratedStoryToInsert)
                    dbRatedStories.add(ratingDao.getRatingByUuid(it.first))
                } else {

                }
            } else {
                if (storyDao.getStoryByUuid(it.first).cachedTime > it.second) {
                    //Das Rating anfordern und in der Datenbank speichern
                    val ratedStoryToInsert = getRatedStoryByUUID(it.first)
                    if(ratedStoryToInsert != null){
                        ratingDao.deleteRating(ratedStoryToInsert.user_username, ratedStoryToInsert.story_uuid)
                        ratingDao.insertRating(ratedStoryToInsert)
                        dbRatedStories.add(ratingDao.getRatingByUuid(it.first))
                    } else {

                    }
                } else {
                    dbRatedStories.add(ratingDao.getRatingByUuid(it.first))
                }
            }
        }
        dbRatedStories
    }
    }

    private fun getMyRatedStoriesTimestamp(authToken: String, username: String) = runBlocking {
        return@runBlocking withContext(Dispatchers.IO){
            val client = getAuthHttpClient(authToken)
            val response: HttpResponse = client.get(MY_STORIES_GET_LAST_UPDATE_VALUES){
                parameter("username", username)
            }
            val jsonString: String = response.receive()
            Log.d(TAG, jsonString)
            client.close()
            val ratedStoriesPairLastUpdate = Gson().fromJson(jsonString, Array<PairLastUpdate>::class.java).toList()
            ratedStoriesPairLastUpdate
        }
    }

    /*********Favorite Story Related Functions*********/
    fun getMyFavoriteStories(): List<hsfl.project.e_storymaker.roomDB.Entities.story.Story> = runBlocking {
        return@runBlocking withContext(Dispatchers.IO){
            val authToken = sharedPreferences.getJWT(application)!!
            val username = sharedPreferences.getUsername(application)!!
            val favoriteStoriesTimestamp = getMyFavoriteStoriesTimestamp(authToken, username)
            compareStoriesWithTimestamp(favoriteStoriesTimestamp, authToken)
        }
    }

    private fun getMyFavoriteStoriesTimestamp(authToken: String, username: String): List<PairLastUpdate> = runBlocking {
        return@runBlocking withContext(Dispatchers.IO){
            val client = getAuthHttpClient(authToken)
            val response: HttpResponse = client.get(MY_FAVORITE_STORIES_GET_LAST_UPDATES){
                parameter("username", username)
            }
            val jsonString: String = response.receive()
            Log.d(TAG, jsonString)
            client.close()
            val ratedStoriesPairLastUpdate = Gson().fromJson(jsonString, Array<PairLastUpdate>::class.java).toList()
            ratedStoriesPairLastUpdate
        }
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

    fun setStoryAsNotFavorite(storyAsFavoriteRequest: StoryAsFavoriteRequest): Boolean = runBlocking {
        val authToken = sharedPreferences.getJWT(application)!!
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

    /*********Tag Related Functions*********/


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

