// data module
package com.id.data.user.source

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.core.remove
import androidx.datastore.preferences.createDataStore
import com.id.data.user.response.UserResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import java.util.Date
import javax.inject.Inject

class UserDataStore @Inject constructor(@ApplicationContext private val context: Context) {
    suspend fun saveUser(user: UserResponse) {
        val dataStore = context.createDataStore("users")
        dataStore.edit { settings ->
            settings[preferencesKey<String>("photo_${user.email}")] = user.photo?:""
            settings[preferencesKey<String>("email_${user.email}")] = user.email
            settings[preferencesKey<String>("username_${user.email}")] = user.username
            settings[preferencesKey<String>("password_${user.email}")] = user.password
            settings[preferencesKey<String>("nama_${user.email}")] = user.nama ?: ""
            settings[preferencesKey<Long>("tanggal_lahir_${user.email}")] = user.tanggalLahir?.time ?: 0
            settings[preferencesKey<String>("alamat_${user.email}")] = user.alamat ?: ""
        }
    }

    suspend fun getUser(email: String): UserResponse? {

        val dataStore = context.createDataStore("users")
        val preferences = dataStore.data.first()
        val photo:String = preferences[preferencesKey("photo_$email")]?:""
        val emailData: String = preferences[preferencesKey("email_$email")] ?: ""
        val username: String = preferences[preferencesKey("username_$email")] ?: ""
        val password: String = preferences[preferencesKey("password_$email")] ?: ""
        val nama: String = preferences[preferencesKey("nama_$email")] ?: ""
        val tanggalLahir: Long = preferences[preferencesKey("tanggal_lahir_$email")] ?: 0
        val alamat: String = preferences[preferencesKey("alamat_$email")] ?: ""
        if (emailData == "") {
            return null
        }
        return UserResponse(photo, emailData, username, password, nama, Date(tanggalLahir), alamat)
    }

    suspend fun getLoggedUser(): UserResponse {
        val dataStore = context.createDataStore("users")
        val preferences = dataStore.data.first()
        val email: String = preferences[preferencesKey("login")] ?: ""
        val photo:String = preferences[preferencesKey("photo_$email")]?:""

        val emailData: String = preferences[preferencesKey("email_$email")] ?: ""
        val username: String = preferences[preferencesKey("username_$email")] ?: ""
        val password: String = preferences[preferencesKey("password_$email")] ?: ""
        val tanggalLahir: Long = preferences[preferencesKey("tanggal_lahir_$email")] ?: 0
        val alamat: String = preferences[preferencesKey("alamat_$email")] ?: ""
        val nama: String = preferences[preferencesKey("nama_$email")] ?: ""
        return UserResponse(photo, emailData, username, password, nama, Date(tanggalLahir), alamat)
    }

    suspend fun login(email: String, password: String): Boolean {
        val dataStore = context.createDataStore("users")
        val preferences = dataStore.data.first()
        val dataEmail: String = preferences[preferencesKey("email_$email")] ?: ""
        val dataPassword: String = preferences[preferencesKey("password_$email")] ?: ""
        if (dataEmail != "" && password == dataPassword) {
            dataStore.edit { settings ->
                settings[preferencesKey<String>("login")] = email
            }
            return true
        }
        return false
    }

    suspend fun logout() {
        val dataStore = context.createDataStore("users")
        dataStore.edit {
            it.remove(preferencesKey<String>("login"))
        }
    }

    suspend fun checkLogin(): String {
        val dataStore = context.createDataStore("users")
        val preferences = dataStore.data.first()
        return preferences[preferencesKey<String>("login")] ?: ""
    }
}
