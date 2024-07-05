package com.id.data.user.mapper

import com.id.data.user.response.UserResponse
import com.id.domain.model.UserModel

object UserMapper {
    fun mapUserResponseToModel(q: UserResponse) = UserModel(
        photo = q.photo, email = q.email, username = q.username, password = q.password, nama = q.nama, tanggalLahir = q.tanggalLahir, alamat = q.alamat
    )
    fun mapUserModelToResponse(q: UserModel) = UserResponse(
        photo = q.photo, email = q.email, username = q.username, password = q.password, nama = q.nama, tanggalLahir = q.tanggalLahir, alamat = q.alamat
    )
}