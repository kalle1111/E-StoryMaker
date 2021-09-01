package hsfl.project.e_storymaker.models.remoteDataSource

import android.app.Application
import android.util.Log
import com.google.gson.Gson
import hsfl.project.e_storymaker.repository.webserviceModels.*
import hsfl.project.e_storymaker.roomDB.AppDatabase
import hsfl.project.e_storymaker.roomDB.Entities.chapterProgress.ChapterProgressDao
import hsfl.project.e_storymaker.roomDB.Entities.favoring.FavoringDao
import hsfl.project.e_storymaker.roomDB.Entities.friendship.FriendshipDao
import hsfl.project.e_storymaker.roomDB.Entities.rating.RatingDao
import hsfl.project.e_storymaker.roomDB.Entities.story.StoryDao
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.*


class UserRepository(application: Application) {

    private final val TAG = "User Rep"
    private val application: Application = application

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
    }

    /*********User Related Functions*********/
    fun registerRequest(registerRequest: RegisterRequest): Boolean = runBlocking {
        return@runBlocking withContext(Dispatchers.IO) {
            val client = getHttpClient()
            try {
                val response: HttpResponse = client.post(REGISTER_REQUEST) {
                    contentType(ContentType.Application.Json)
                    body = registerRequest
                }
                val stringBody: String = response.receive()
                val webResponse = Gson().fromJson(stringBody, WebResponse::class.java)
                //jwt Token
                if(webResponse.success){
                    sharedPreferences.saveJWT(application, webResponse.message)
                    sharedPreferences.saveUsername(application, registerRequest.userName)
                }
                client.close()
                webResponse.success
            } catch (e: Exception) {
                client.close()
                Log.d(TAG, e.toString());
                false
            }
        }
    }


    fun loginRequest(loginRequest: LoginRequest): Boolean = runBlocking {
        return@runBlocking withContext(Dispatchers.IO){
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
                if(webResponse.success){
                    sharedPreferences.saveJWT(application, webResponse.message)
                    sharedPreferences.saveUsername(application, loginRequest.userName)
                }
                webResponse.success
            } catch (e: Exception){
                client.close()
                false
            }
        }
    }

    fun getAllUsers(): List<hsfl.project.e_storymaker.roomDB.Entities.user.User> = runBlocking {
        return@runBlocking withContext(Dispatchers.IO) {
            val usersWithTimestamp = getAllUsersTimestamp()
            cacheUsers(usersWithTimestamp)
        }
    }



    private fun getAllUsersTimestamp() = runBlocking {
        val client = HttpClient(CIO)
        val response: HttpResponse = client.get(USERS_GET_LAST_UPDATE_VALUES)
        val jsonString: String = response.receive()
        Log.d(TAG, jsonString)
        client.close()
        val usersPairLastUpdate = Gson().fromJson(jsonString, Array<PairLastUpdate>::class.java).toList()
        return@runBlocking usersPairLastUpdate
    }

    private fun getUserByUserName(username: String): hsfl.project.e_storymaker.roomDB.Entities.user.User? = runBlocking {
        return@runBlocking withContext(Dispatchers.IO){
            val client = getHttpClient()
            try {
                val response: HttpResponse = client.delete(GET_USER_BY_USERNAME){
                    parameter("username", username)
                }
                val stringBody: String = response.receive()
                client.close()
                val user: User = Gson().fromJson(stringBody, User::class.java)
                convertWebserviceUserToDBUser(user)
            }catch (e: Exception){
                client.close()
                null
            }
        }
    }

    fun getUser(username: String): hsfl.project.e_storymaker.roomDB.Entities.user.User? = runBlocking {
        return@runBlocking withContext(Dispatchers.IO) {
            cacheUser(username)
        }
    }

    private fun getUserTimestamp(username: String): Long = runBlocking {
        return@runBlocking withContext(Dispatchers.IO){
            val authToken = sharedPreferences.getJWT(application)!!
            val client = getAuthHttpClient(authToken)

            val response: HttpResponse = client.get(USER_GET_BY_USERNAME_LAST_UPDATE){
                parameter("username", username)
            }
            val jsonString: String = response.receive()
            client.close()
            val userUpdate = Gson().fromJson(jsonString, Long::class.java)
            userUpdate
        }
    }

    fun getMyProfile(): hsfl.project.e_storymaker.roomDB.Entities.user.User? = runBlocking {
        return@runBlocking withContext(Dispatchers.IO) {
            val username = sharedPreferences.getUsername(application)!!
            cacheUser(username)
        }
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

    private fun cacheUsers(userTimestamps: List<PairLastUpdate>): List<hsfl.project.e_storymaker.roomDB.Entities.user.User> = runBlocking {
        return@runBlocking withContext(Dispatchers.IO){
            val dbUsers: MutableList<hsfl.project.e_storymaker.roomDB.Entities.user.User> = mutableListOf<hsfl.project.e_storymaker.roomDB.Entities.user.User>()
            userTimestamps.map{
                if(!userDao.rowExistByUsername(it.first)){
                    //Insert User
                    val user = getUserByUserName(it.first)!!
                    userDao.insertWithTimestamp(user)
                    dbUsers.add(userDao.getUserByUsername(user.username))
                } else {
                    if(userDao.getUserByUsername(it.first).cachedTime < it.second){
                        //Den User anfordern und in der Datenbank speichern
                        val user = getUserByUserName(it.first)!!
                        userDao.insertWithTimestamp(user)
                        dbUsers.add(userDao.getUserByUsername(user.username))
                    } else {
                        dbUsers.add(userDao.getUserByUsername(it.first))
                    }
                }
            }
            dbUsers
        }
    }

    private fun cacheUser(username: String): hsfl.project.e_storymaker.roomDB.Entities.user.User? = runBlocking {
        return@runBlocking withContext(Dispatchers.IO){
            val userTimestamp = getUserTimestamp(username)
            if (!userDao.rowExistByUsername(username)){
                val user = getUserByUserName(username)!!
                userDao.insertWithTimestamp(user)
                userDao.getUserByUsername(username)
            } else {
                if(userDao.getUserByUsername(username).cachedTime < userTimestamp){
                    val user = getUserByUserName(username)!!
                    userDao.insertWithTimestamp(user)
                    userDao.getUserByUsername(username)
                } else {
                    userDao.getUserByUsername(username)
                }
            }
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

