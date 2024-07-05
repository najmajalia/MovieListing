package com.id.data.movie.response

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("results")
    var results: List<MovieDataResponse>
)
