package com.id.domain.model

import android.util.Base64
import java.util.Date

data class UserModel(
    val photo: String?,
    val email:String,
    var username:String,
    var password:String,
    var nama:String?,
    var tanggalLahir: Date?,
    var alamat:String?
) {
    fun photoByte(): ByteArray {
        return Base64.decode(photo, Base64.DEFAULT)
    }
}
