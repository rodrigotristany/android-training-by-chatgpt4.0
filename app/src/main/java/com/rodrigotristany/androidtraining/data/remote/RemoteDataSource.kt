package com.rodrigotristany.androidtraining.data.remote

import com.rodrigotristany.androidtraining.data.Movie
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {
    suspend fun getMovies() : List<Movie> {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MoviesService::class.java)
            .getMovies()
            .results
            .map { it.toMovie() }
    }
}