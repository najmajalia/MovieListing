package com.id.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class MovieDataModel(
    var id: Int,
    var overview: String,
    var posterPath: String,
    var vote: Double,
    var title: String
): java.io.Serializable {
    val stringVote: String
        get() = "$vote/10"
}
