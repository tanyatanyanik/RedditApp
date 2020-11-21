package io.redditapp.api

import io.redditapp.api.pesponses.ResponseTopPublicatiosModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiInterface {

    @GET("top.json")
    suspend fun getTopPublications(): Response<ResponseTopPublicatiosModel>

}