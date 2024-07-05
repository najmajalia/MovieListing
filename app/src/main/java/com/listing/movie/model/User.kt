package com.listing.movie.model

import java.util.Date

data class User (
    val email:String,
    var username:String,
    var password:String,
    var nama:String?,
    var tanggalLahir:Date?,
    var alamat:String?
)
