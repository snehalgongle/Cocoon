package com.codingwithmitch.mviexample.api

import com.snake.cocoon.data.network.Base
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {


    @GET("home.json")
    fun  getTopStories(
        @Query("api-key") apiKey: String
    ): retrofit2.Call<Base>
}