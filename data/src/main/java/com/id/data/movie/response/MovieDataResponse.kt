package com.id.data.movie.response

import com.google.gson.annotations.SerializedName

data class MovieDataResponse(
    @SerializedName("id")
    var id: Int,
    @SerializedName("overview")
    var overview: String?,
    @SerializedName("poster_path")
    var posterPath: String?,
    @SerializedName("vote_average")
    var vote: Double,
    @SerializedName("title")
    var title: String
)
