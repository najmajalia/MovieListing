package com.id.data.movie.mapper

import com.id.data.movie.response.MovieDataResponse
import com.id.data.movie.response.MoviesResponse
import com.id.domain.model.MovieDataModel
import com.id.domain.model.MoviesModel

object MovieResponseToDomain {
    private fun movieResponseToModel(q: MovieDataResponse): MovieDataModel = MovieDataModel(
        id = q.id, overview = q.overview ?: "", posterPath = q.posterPath ?: "", vote = q.vote, title = q.title
    )

    fun moviesResponseToModels(q: MoviesResponse): MoviesModel = MoviesModel(
        q.results.map {
            movieResponseToModel(it)
        }
    )
}