package com.listing.movie.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.id.domain.model.UserModel
import com.id.domain.repository.IUserRepository
import com.listing.movie.utils.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: IUserRepository
): ViewModel() {
    private val _user = MutableLiveData<UserModel>()
    val user: LiveData<UserModel> = _user

    init {
        fetchUser()
    }

    private fun fetchUser() {
        viewModelScope.launch {
            val response = userRepository.getLoggedUser()
            _user.value = response
        }
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }

    fun updateUserData(
        photo: String?, userName: String, name: String, tanggalLahir: String, alamat: String) {
        viewModelScope.launch {
            val userModel = _user.value?.copy(
                photo = photo,
                username = userName,
                nama = name,
                tanggalLahir = DateUtils.parseDate(tanggalLahir),
                alamat = alamat
            )
            if (userModel != null) {
                userRepository.saveUser(userModel)
            }
        }
    }
}