package com.listing.movie.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.id.domain.model.UserModel
import com.id.domain.repository.IUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRepository: IUserRepository
): ViewModel() {

    private val _isRegistered = MutableLiveData<Boolean>()
    val isRegistered: LiveData<Boolean> = _isRegistered

    fun register(email: String, username: String, password: String) {
        viewModelScope.launch {
            val userModel = UserModel(
                photo = null,
                email = email, username = username, password = password,
                nama = null, tanggalLahir = null, alamat = null
            )
            userRepository.saveUser(userModel)
        }
    }

    fun isUserRegistered(email: String) {
        viewModelScope.launch {
            val response = userRepository.getUser(email)
            _isRegistered.value = response != null
        }
    }

}