package com.listing.movie.auth.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.id.domain.repository.IUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var userRepository: IUserRepository

    private lateinit var loginViewModel: LoginViewModel

    @Mock
    private lateinit var loginObserver: Observer<Boolean>

    @Mock
    private lateinit var isLoginObserver: Observer<Boolean>

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        loginViewModel = LoginViewModel(userRepository)
        loginViewModel.loginSuccess.observeForever(loginObserver)
        loginViewModel.isLogin.observeForever(isLoginObserver)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun checkUserLogin_userLoggedIn_isLoginTrue() = runTest {
        val response = "logged_in"
        Mockito.`when`(userRepository.checkLogin()).thenReturn(response)

        loginViewModel.checkUserLogin()

        Mockito.verify(isLoginObserver).onChanged(true)
    }

    @Test
    fun checkUserLogin_userNotLoggedIn_isLoginFalse() = runTest {
        val response = ""
        Mockito.`when`(userRepository.checkLogin()).thenReturn(response)

        loginViewModel.checkUserLogin()

        Mockito.verify(isLoginObserver).onChanged(false)
    }

    @Test
    fun login_successfulLogin_loginSuccessTrue() = runTest {
        val email = "test@example.com"
        val password = "password"
        Mockito.`when`(userRepository.login(email, password)).thenReturn(true)

        loginViewModel.login(email, password)

        Mockito.verify(loginObserver).onChanged(true)
    }

    @Test
    fun login_failedLogin_loginSuccessFalse() = runTest {
        val email = "test@example.com"
        val password = "password"
        Mockito.`when`(userRepository.login(email, password)).thenReturn(false)

        loginViewModel.login(email, password)

        Mockito.verify(loginObserver).onChanged(false)
    }
}