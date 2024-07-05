package com.listing.movie.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.id.domain.Resource
import com.id.domain.model.MoviesModel
import com.id.domain.model.UserModel
import com.id.domain.repository.IMovieRepository
import com.id.domain.repository.IUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: IMovieRepository,
    private val userRepository: IUserRepository
): ViewModel() {
    private val _movieList = MutableLiveData<MoviesModel>()
    val movieList: LiveData<MoviesModel> = _movieList

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _currentUser = MutableLiveData<UserModel>()
    val currentUser: LiveData<UserModel> = _currentUser

    init {
        fetchCurrentUser()
        fetchMovies()
    }

    fun fetchCurrentUser() {
        viewModelScope.launch {
            val response = userRepository.getLoggedUser()
            _currentUser.value = response
        }
    }

    fun fetchMovies() {
        viewModelScope.launch {
            movieRepository.getNowPlayingMovies().collect {
                when (it) {
                    is Resource.Error -> {
                        _isLoading.value = false
                        _isError.value = true
                        _errorMessage.value = it.message ?: "Unknown Error"
                    }
                    is Resource.Loading -> {
                        _isLoading.value = true
                    }
                    is Resource.Success -> {
                        _isLoading.value = false
                        _movieList.value = it.data!!
                    }
                }
            }
        }
    }
}