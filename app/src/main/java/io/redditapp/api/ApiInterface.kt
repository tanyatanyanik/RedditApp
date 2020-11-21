package io.redditapp.api

import io.redditapp.api.pesponses.ResponsePublicatiosModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("top.json")
    suspend fun getTopPublications(): Response<ResponsePublicatiosModel>

}