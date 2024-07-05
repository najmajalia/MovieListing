package com.id.domain.repository

import com.id.domain.model.UserModel

interface IUserRepository {
    suspend fun saveUser(user: UserModel)
    suspend fun getUser(email: String): UserModel?
    suspend fun getLoggedUser(): UserModel
    suspend fun login(mail: String, password: String): Boolean
    suspend fun logout()
    suspend fun checkLogin(): String
}