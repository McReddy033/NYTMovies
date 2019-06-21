package com.example.nytmovies.data

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NytStartApiService{
    @GET("reviews/all.json")
    fun searchMovies(@Query("offset") offset: String,
                     @Query("api-key") api_key: String): Call<Model.Result>

    companion object{
        fun create(): NytStartApiService{
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.nytimes.com/svc/movies/v2/")
                .build()

            return retrofit.create(NytStartApiService::class.java)
        }
    }
}