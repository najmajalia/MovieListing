package com.id.data.user

import com.id.data.user.mapper.UserMapper.mapUserModelToResponse
import com.id.data.user.mapper.UserMapper.mapUserResponseToModel
import com.id.data.user.source.UserDataStore
import com.id.domain.model.UserModel
import com.id.domain.repository.IUserRepository
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val dataStore: UserDataStore
): IUserRepository {
    override suspend fun saveUser(user: UserModel) {
        dataStore.saveUser(mapUserModelToResponse(user))
    }

    override suspend fun getUser(email: String): UserModel? = dataStore.getUser(email)
        ?.let { mapUserResponseToModel(it) }

    override suspend fun getLoggedUser(): UserModel = mapUserResponseToModel(dataStore.getLoggedUser())

    override suspend fun login(mail: String, password: String): Boolean = dataStore.login(mail,password)

    override suspend fun logout() {
        dataStore.logout()
    }

    override suspend fun checkLogin(): String = dataStore.checkLogin()
}