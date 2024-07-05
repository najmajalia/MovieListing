package com.listing.movie.auth.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.id.domain.model.UserModel
import com.id.domain.repository.IUserRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RegisterViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var userRepository: IUserRepository

    private lateinit var registerViewModel: RegisterViewModel

    @Mock
    private lateinit var isRegisteredObserver: Observer<Boolean>

    @Captor
    private lateinit var userModelCaptor: ArgumentCaptor<UserModel>

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        registerViewModel = RegisterViewModel(userRepository)
        registerViewModel.isRegistered.observeForever(isRegisteredObserver)
        MockitoAnnotations.openMocks(this)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // Register a user with an already registered email
    @Test
    fun register_user_with_already_registered_email() {
        // Unit Test This Function
        val userRepository = mock(IUserRepository::class.java)
        val viewModel = RegisterViewModel(userRepository)
        val email = "test@example.com"
        val username = "testuser"
        val password = "password123"

        runBlocking {
            `when`(userRepository.getUser(email)).thenReturn(UserModel(
                photo = null,
                email = email,
                username = username,
                password = password,
                nama = null,
                tanggalLahir = null,
                alamat = null
            ))

            viewModel.isUserRegistered(email)
            assertEquals(true, viewModel.isRegistered.value)
        }
    }

    // register should save a new user with valid email, username, and password
    @Test
    fun register_should_save_new_user_with_valid_email_username_and_password() {
        // Unit Test This Function
        runBlocking {
            // Arrange
            val mockUserRepository = mock(IUserRepository::class.java)
            val viewModel = RegisterViewModel(mockUserRepository)
            val email = "test@example.com"
            val username = "testuser"
            val password = "password123"

            // Act
            viewModel.register(email, username, password)

            // Assert
            verify(mockUserRepository).saveUser(
                UserModel(
                    photo = null,
                    email = email,
                    username = username,
                    password = password,
                    nama = null,
                    tanggalLahir = null,
                    alamat = null
                )
            )
        }
    }


}