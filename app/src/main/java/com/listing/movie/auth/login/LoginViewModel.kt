package com.listing.movie.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.id.domain.repository.IUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: IUserRepository
): ViewModel() {
    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> = _loginSuccess

    private val _isLogin = MutableLiveData<Boolean>()
    val isLogin: LiveData<Boolean> = _isLogin

    init {
        checkUserLogin()
    }
    fun checkUserLogin() {
        viewModelScope.launch {
            val response = userRepository.checkLogin()
            _isLogin.value = !response.isNullOrEmpty()
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val response = userRepository.login(email, password)
            _loginSuccess.value = response
        }
    }
}