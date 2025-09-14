import com.rohil.network1.vo.ResponseVO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class KtorClient {
    private val client = HttpClient(OkHttp) {
        install(Logging) {
            logger = Logger.SIMPLE
        }

        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                }
            )
        }
    }

    suspend fun getStock(): ApiResponse<ResponseVO> {
        return makeApiCall {
            client
                .get("https://35dee773a9ec441e9f38d5fc249406ce.api.mockbin.io/")
                .body()
        }
    }

    private inline fun <T> makeApiCall(apiCall: () -> T): ApiResponse<T> {
        return try {
            ApiResponse.Success(data = apiCall())
        } catch (e: Exception) {
            ApiResponse.Failure(exception = e)
        }
    }
}

sealed interface ApiResponse<T> {
    data class Success<T>(val data: T) : ApiResponse<T>
    data class Failure<T>(val exception: Exception) : ApiResponse<T>

    suspend fun onSuccess(block: suspend (T) -> Unit): ApiResponse<T> {
        if (this is Success) block(data)
        return this
    }

    suspend fun onFailure(block: suspend (Exception) -> Unit): ApiResponse<T> {
        if (this is Failure) block(exception)
        return this
    }
}