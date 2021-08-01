package hsfl.project.e_storymaker.models.remoteDataSource

import android.app.Application
import android.util.Log
import com.google.gson.Gson
import hsfl.project.e_storymaker.repository.webserviceModels.*
import hsfl.project.e_storymaker.roomDB.AppDatabase
import hsfl.project.e_storymaker.roomDB.AppRepository
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*


class UserRepository(application: Application) {

    private final val TAG = "User Rep"
    private val repository:AppRepository

    init {

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

    /*********User Related Functions*********/
    suspend fun registerRequest(registerRequest: RegisterRequest): Boolean {
        val client = getHttpClient()
        return try {
            val response: HttpResponse = client.post(REGISTER_REQUEST){
                contentType(ContentType.Application.Json)
                body = registerRequest
            }
            val stringBody: String = response.receive()
            client.close()
            val gson = Gson()
            val webResponse = gson.fromJson(stringBody, WebResponse::class.java)
            //jwt Token
            Log.d(TAG, webResponse.message)
            return webResponse.success
        } catch (e: Exception){
            client.close()
            return false
        }
    }

    suspend fun loginRequest(loginRequest: LoginRequest): Boolean {
        val client = getHttpClient()
        return try {
            val response: HttpResponse = client.post(LOGIN_REQUEST){
                contentType(ContentType.Application.Json)
                body = loginRequest
            }
            val stringBody: String = response.receive()
            client.close()
            val webResponse = Gson().fromJson(stringBody, WebResponse::class.java)
            //jwt Token
            Log.d(TAG, webResponse.message)
            return webResponse.success
        } catch (e: Exception){
            client.close()
            return false
        }
    }

    suspend fun getAllUsers(): List<hsfl.project.e_storymaker.roomDB.Entities.user.User>{
        val client = HttpClient(CIO)
        val response: HttpResponse = client.get(GET_ALL_USERS)
        val jsonString: String = response.receive()
        println(jsonString)
        client.close()
        val users = Gson().fromJson(jsonString, Array<User>::class.java).toList()
        val dbUsers = users.map {
            convertWebserviceUserToDBUser(it)
        }
        return dbUsers
    }

    suspend fun getMyProfile(authToken: String): hsfl.project.e_storymaker.roomDB.Entities.user.User {
        val client = getAuthHttpClient(authToken)
        val response: HttpResponse = client.get(GET_PROFILE)
        val jsonString: String = response.receive()
        client.close()
        val myProfile = Gson().fromJson(jsonString, User::class.java)
        return convertWebserviceUserToDBUser(myProfile)
    }

    /*********Friendship Related Functions*********/
    suspend fun getMyFriendships(authToken: String): List<hsfl.project.e_storymaker.roomDB.Entities.friendship.Friendship>{
        val client = getAuthHttpClient(authToken)
        val response: HttpResponse = client.get(MY_FRIENDSHIPS)
        val jsonString: String = response.receive()
        client.close()
        val friendships = Gson().fromJson(jsonString, Array<Friendship>::class.java).toList()
        val dbFriendships = friendships.map {
            convertWebserviceFriendshipToDbFriendship(it)
        }
        return dbFriendships
    }

    suspend fun requestFriendship(authToken: String, friendshipRequestModel: FriendshipServiceModel): Boolean {
        val client = getAuthHttpClient(authToken)
        return try {
            val response: HttpResponse = client.post(REQUEST_FRIENDSHIPS){
                contentType(ContentType.Application.Json)
                body = friendshipRequestModel
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

    suspend fun getMyOutgoingFriendshipRequests(authToken: String): List<hsfl.project.e_storymaker.roomDB.Entities.friendship.Friendship>{
        val client = getAuthHttpClient(authToken)
        val response: HttpResponse = client.get(FRIENDSHIPS_GET_REQUESTS_TO_ME)
        val jsonString: String = response.receive()
        client.close()
        val friendships = Gson().fromJson(jsonString, Array<Friendship>::class.java).toList()
        val dbFriendships = friendships.map {
            convertWebserviceFriendshipToDbFriendship(it)
        }
        return dbFriendships
    }

    suspend fun getMyIncomingFriendshipRequests(authToken: String): List<hsfl.project.e_storymaker.roomDB.Entities.friendship.Friendship>{
        val client = getAuthHttpClient(authToken)
        val response: HttpResponse = client.get(FRIENDSHIPS_GET_REQUESTS_FROM_ME)
        val jsonString: String = response.receive()
        client.close()
        val friendships = Gson().fromJson(jsonString, Array<Friendship>::class.java).toList()
        val dbFriendships = friendships.map {
            convertWebserviceFriendshipToDbFriendship(it)
        }
        return dbFriendships
    }

    suspend fun rejectFriendship(authToken: String, friendshipRequestModel: FriendshipServiceModel): Boolean {
        val client = getAuthHttpClient(authToken)
        return try {
            val response: HttpResponse = client.post(REJECT_FRIENDSHIPS){
                contentType(ContentType.Application.Json)
                body = friendshipRequestModel
            }
            val stringBody: String = response.receive()
            client.close()
            val webResponse = Gson().fromJson(stringBody, WebResponse::class.java)
            return webResponse.success
        }  catch (e: Exception){
            client.close()
            return false
        }
    }

    suspend fun acceptFriendship(authToken: String, friendshipRequestModel: FriendshipServiceModel): Boolean {
        val client = getAuthHttpClient(authToken)
        return try {
            val response: HttpResponse = client.post(ACCEPT_FRIENDSHIPS){
                contentType(ContentType.Application.Json)
                body = friendshipRequestModel
            }
            val stringBody: String = response.receive()
            client.close()
            val webResponse = Gson().fromJson(stringBody, WebResponse::class.java)
            webResponse.success
        } catch (e: Exception){
            client.close()
            return false
        }
    }

    suspend fun deleteFriendship(authToken: String, friendshipRequestModel: FriendshipServiceModel): Boolean {
        val client = getAuthHttpClient(authToken)
        return try {
            val response: HttpResponse = client.delete(DELETE_FRIENDSHIPS){
                contentType(ContentType.Application.Json)
                body = friendshipRequestModel
            }
            val stringBody: String = response.receive()
            client.close()
            val webResponse = Gson().fromJson(stringBody, WebResponse::class.java)
            return webResponse.success
        }catch (e: Exception){
            client.close()
            return false
        }
    }

    suspend fun cancelFriendship(authToken: String, friendshipRequestModel: FriendshipServiceModel): Boolean {
        val client = getAuthHttpClient(authToken)
        return try {
            val response: HttpResponse = client.post(CANCEL_FRIENDSHIPS){
                contentType(ContentType.Application.Json)
                body = friendshipRequestModel
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

