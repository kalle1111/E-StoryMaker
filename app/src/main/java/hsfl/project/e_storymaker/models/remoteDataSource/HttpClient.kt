package hsfl.project.e_storymaker.models.remoteDataSource

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.auth.*
import io.ktor.client.features.auth.providers.*
import io.ktor.client.features.json.*
import io.ktor.client.statement.*

fun getHttpClient(): HttpClient {
    return HttpClient(CIO){
        install(JsonFeature){
            serializer = GsonSerializer() {
                setPrettyPrinting()
                disableHtmlEscaping()
            }
        }
    }
}

fun getAuthHttpClient(authToken: String): HttpClient {
    return HttpClient(CIO){
        install(JsonFeature){
            serializer = GsonSerializer() {
                setPrettyPrinting()
                disableHtmlEscaping()
            }
        }
        install(Auth) {
            bearer {
                loadTokens {
                    BearerTokens(accessToken = authToken, refreshToken = "")
                }

                refreshTokens { response: HttpResponse ->
                    BearerTokens(accessToken = authToken, refreshToken = "")
                }
            }
        }
    }
}