package com.id.data.user.response

import java.util.Date

data class UserResponse(
    var photo: String?,
    val email:String,
    var username:String,
    var password:String,
    var nama:String?,
    var tanggalLahir: Date?,
    var alamat:String?
)
