package io.redditapp.api

import com.squareup.moshi.JsonDataException
import io.redditapp.api.pesponses.ResponsePublicationsModel
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ApiRepository {

    val apiInterface by lazy { ApiFactory.generateApi() }

    suspend fun getTopPublications(): Result<Any>? {
        val call: suspend () -> Response<ResponsePublicationsModel> =
            {
                apiInterface.getTopPublications()
            }
        return safetyCall(call)
    }

    suspend fun <T : Any> safetyCall(call: suspend () -> Response<T>): Result<Any>? {
        var response: Result<Any>?
        try {
            val result: Response<T> = call.invoke()
            if (result.isSuccessful) {
                response = Result.Success<T>(result.body()!!)
            } else {
                response = Result.Error(result.code(), result.toString())
            }
        } catch (e: Exception) {
            response = Result.Error(errMsg = e.message ?: "")
            when (e) {
                is SocketTimeoutException -> response = Result.Error(ERROR_TIMEOUT, e.message ?: "")
                is UnknownHostException -> response =
                    Result.Error(ERROR_NO_INTERNET, e.message ?: "")
                is JsonDataException -> response = Result.Error(ERROR_JSON_PARSE, e.message ?: "")
            }
        }
        return response
    }

}