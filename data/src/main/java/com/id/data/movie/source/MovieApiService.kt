package com.id.data.movie.source

import com.id.data.movie.response.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MovieApiService {
    @GET("movie/popular")
    suspend fun getNowPlaying(
        @Header("Authorization") apiKey: String): MoviesResponse

    @GET("search/movie")
    suspend fun getSearch(
        @Header("Authorization") apiKey: String,
        @Query("query") query: String?
    ): MoviesResponse
}