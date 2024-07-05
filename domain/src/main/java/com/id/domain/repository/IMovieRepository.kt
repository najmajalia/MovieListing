package com.id.domain.repository

import com.id.domain.Resource
import com.id.domain.model.MoviesModel
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getNowPlayingMovies(): Flow<Resource<MoviesModel>>
    fun searchMovies(title: String): Flow<Resource<MoviesModel>>
}