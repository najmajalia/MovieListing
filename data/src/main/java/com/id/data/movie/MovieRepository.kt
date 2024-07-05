package com.id.data.movie

import com.id.data.BuildConfig
import com.id.data.movie.mapper.MovieResponseToDomain.moviesResponseToModels
import com.id.data.movie.source.MovieApiService
import com.id.domain.Resource
import com.id.domain.model.MoviesModel
import com.id.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieApiService: MovieApiService
): IMovieRepository{
    override fun getNowPlayingMovies(): Flow<Resource<MoviesModel>> = flow {
        emit(Resource.Loading())
        emit(
            try {
                val response = movieApiService.getNowPlaying("Bearer " + BuildConfig.API_KEY)
                Resource.Success(moviesResponseToModels(response))
            } catch (e: Exception) {
                Resource.Error(e.message.toString())
            }
        )

    }

    override fun searchMovies(title: String): Flow<Resource<MoviesModel>>  = flow {
        emit(Resource.Loading())
        emit(try {
            val response = movieApiService.getSearch("Bearer " + BuildConfig.API_KEY, title)
            Resource.Success(moviesResponseToModels(response))
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        })
    }

}