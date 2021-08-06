package hsfl.project.e_storymaker.models.remoteDataSource

import android.app.Application
import android.util.Log
import com.google.gson.Gson
import hsfl.project.e_storymaker.repository.webserviceModels.*
import hsfl.project.e_storymaker.roomDB.AppDatabase
import hsfl.project.e_storymaker.roomDB.AppRepository
import hsfl.project.e_storymaker.roomDB.Entities.chapterProgress.ChapterProgressDao
import hsfl.project.e_storymaker.roomDB.Entities.favoring.FavoringDao
import hsfl.project.e_storymaker.roomDB.Entities.friendship.FriendshipDao
import hsfl.project.e_storymaker.roomDB.Entities.rating.RatingDao
import hsfl.project.e_storymaker.roomDB.Entities.story.StoryDao
import hsfl.project.e_storymaker.roomDB.Entities.user.UserDao
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking


class UserRepository(application: Application) {

    private final val TAG = "User Rep"
    private val repository:AppRepository

    val userDao: UserDao
    val storyDao: StoryDao
    val friendshipDao: FriendshipDao
    val chapterProgressDao: ChapterProgressDao
    val favoringDao: FavoringDao
    val ratingDao: RatingDao


    init {

        val database = AppDatabase.getDatabase(application)

        userDao = database.userDao()
        storyDao = database.storyDao()
        friendshipDao = database.friendshipDao()
        chapterProgressDao = database.chapterProgressDao()
        favoringDao = database.favoringDao()
        ratingDao = database.ratingDao()

        repository = AppRepository(userDao, storyDao, friendshipDao,
            chapterProgressDao, favoringDao, ratingDao)
    }

    /*********User Related Functions*********/
    fun registerRequest(registerRequest: RegisterRequest): Boolean = runBlocking {
        val client = getHttpClient()
        try {
            val response: HttpResponse = client.post(REGISTER_REQUEST){
                contentType(ContentType.Application.Json)
                body = registerRequest
            }
            val stringBody: String = response.receive()
            Log.d(TAG, ("RegisterResponse: " + stringBody))
            val webResponse = Gson().fromJson(stringBody, WebResponse::class.java)
            //jwt Token
            Log.d(TAG, webResponse.message)
            client.close()
            return@runBlocking webResponse.success
        } catch (e: Exception){
            client.close()
            return@runBlocking false
        }
    }

    fun loginRequest(loginRequest: LoginRequest): Boolean = runBlocking {
        val client = getHttpClient()
        try {
            val response: HttpResponse = client.post(LOGIN_REQUEST){
                contentType(ContentType.Application.Json)
                body = loginRequest
            }
            val stringBody: String = response.receive()
            client.close()
            val webResponse = Gson().fromJson(stringBody, WebResponse::class.java)
            //jwt Token
            Log.d(TAG, webResponse.message)
            return@runBlocking webResponse.success
        } catch (e: Exception){
            client.close()
            return@runBlocking false
        }
    }

    fun getAllUsers(): List<hsfl.project.e_storymaker.roomDB.Entities.user.User> = runBlocking {
        val client = HttpClient(CIO)
        val response: HttpResponse = client.get(GET_ALL_USERS)
        val jsonString: String = response.receive()
        println(jsonString)
        client.close()
        val users = Gson().fromJson(jsonString, Array<User>::class.java).toList()
        val dbUsers = users.map {
            convertWebserviceUserToDBUser(it)
        }
        caheAllUsers(dbUsers);
        return@runBlocking dbUsers
    }

    fun getMyProfile(authToken: String): hsfl.project.e_storymaker.roomDB.Entities.user.User = runBlocking {
        val client = getAuthHttpClient(authToken)
        val response: HttpResponse = client.get(GET_PROFILE)
        val jsonString: String = response.receive()
        client.close()
        val myProfile = Gson().fromJson(jsonString, User::class.java)
        return@runBlocking convertWebserviceUserToDBUser(myProfile)
    }

    /*********Friendship Related Functions*********/
    fun getMyFriendships(authToken: String): List<hsfl.project.e_storymaker.roomDB.Entities.friendship.Friendship> = runBlocking {
        val client = getAuthHttpClient(authToken)
        val response: HttpResponse = client.get(MY_FRIENDSHIPS)
        val jsonString: String = response.receive()
        client.close()
        val friendships = Gson().fromJson(jsonString, Array<Friendship>::class.java).toList()
        val dbFriendships = friendships.map {
            convertWebserviceFriendshipToDbFriendship(it)
        }
        return@runBlocking dbFriendships
    }

    fun requestFriendship(authToken: String, friendshipRequestModel: FriendshipServiceModel): Boolean = runBlocking {
        val client = getAuthHttpClient(authToken)
        try {
            val response: HttpResponse = client.post(REQUEST_FRIENDSHIPS){
                contentType(ContentType.Application.Json)
                body = friendshipRequestModel
            }
            val stringBody: String = response.receive()
            client.close()
            val webResponse = Gson().fromJson(stringBody, WebResponse::class.java)
            return@runBlocking webResponse.success
        } catch (e: Exception){
            client.close()
            return@runBlocking false
        }
    }

    fun getMyOutgoingFriendshipRequests(authToken: String): List<hsfl.project.e_storymaker.roomDB.Entities.friendship.Friendship> = runBlocking {
        val client = getAuthHttpClient(authToken)
        val response: HttpResponse = client.get(FRIENDSHIPS_GET_REQUESTS_TO_ME)
        val jsonString: String = response.receive()
        client.close()
        val friendships = Gson().fromJson(jsonString, Array<Friendship>::class.java).toList()
        val dbFriendships = friendships.map {
            convertWebserviceFriendshipToDbFriendship(it)
        }
        return@runBlocking dbFriendships
    }

    fun getMyIncomingFriendshipRequests(authToken: String): List<hsfl.project.e_storymaker.roomDB.Entities.friendship.Friendship> = runBlocking {
        val client = getAuthHttpClient(authToken)
        val response: HttpResponse = client.get(FRIENDSHIPS_GET_REQUESTS_FROM_ME)
        val jsonString: String = response.receive()
        client.close()
        val friendships = Gson().fromJson(jsonString, Array<Friendship>::class.java).toList()
        val dbFriendships = friendships.map {
            convertWebserviceFriendshipToDbFriendship(it)
        }
        return@runBlocking dbFriendships
    }

    fun rejectFriendship(authToken: String, friendshipRequestModel: FriendshipServiceModel): Boolean = runBlocking {
        val client = getAuthHttpClient(authToken)
        try {
            val response: HttpResponse = client.post(REJECT_FRIENDSHIPS){
                contentType(ContentType.Application.Json)
                body = friendshipRequestModel
            }
            val stringBody: String = response.receive()
            client.close()
            val webResponse = Gson().fromJson(stringBody, WebResponse::class.java)
            return@runBlocking webResponse.success
        }  catch (e: Exception){
            client.close()
            return@runBlocking false
        }
    }

    fun acceptFriendship(authToken: String, friendshipRequestModel: FriendshipServiceModel): Boolean = runBlocking {
        val client = getAuthHttpClient(authToken)
        try {
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
            false
        }
    }

    fun deleteFriendship(authToken: String, friendshipRequestModel: FriendshipServiceModel): Boolean = runBlocking {
        val client = getAuthHttpClient(authToken)
        try {
            val response: HttpResponse = client.delete(DELETE_FRIENDSHIPS){
                contentType(ContentType.Application.Json)
                body = friendshipRequestModel
            }
            val stringBody: String = response.receive()
            client.close()
            val webResponse = Gson().fromJson(stringBody, WebResponse::class.java)
            webResponse.success
        }catch (e: Exception){
            client.close()
            false
        }
    }

    fun cancelFriendship(authToken: String, friendshipRequestModel: FriendshipServiceModel): Boolean = runBlocking {
        val client = getAuthHttpClient(authToken)
        try {
            val response: HttpResponse = client.post(CANCEL_FRIENDSHIPS){
                contentType(ContentType.Application.Json)
                body = friendshipRequestModel
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

    fun caheAllUsers(allUsers: List<hsfl.project.e_storymaker.roomDB.Entities.user.User>){
        allUsers.map{
            userDao.insertWithTimestamp(it)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserRepository? = null

        fun getStoryRepository(application: Application): UserRepository? {
            val instance = INSTANCE
            if (instance != null){
                return instance
            }

            INSTANCE = UserRepository(application)
            return INSTANCE
        }
    }
}

