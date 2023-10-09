package com.rodrigotristany.androidtraining.data.remote

import retrofit2.http.GET

interface MoviesService {
    @GET("discover/movie?api_key=8db61aa9a20efcccd9dfb3973ce755c0")
    suspend fun getMovies(): MovieResult
}