package com.listing.movie.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.id.domain.model.UserModel
import com.id.domain.repository.IUserRepository
import com.listing.movie.auth.login.LoginViewModel
import com.listing.movie.util.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.Date

class ProfileViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var userRepository: IUserRepository

    private lateinit var profileViewModel: ProfileViewModel

    @Mock
    private lateinit var loginObserver: Observer<Boolean>

    @Mock
    private lateinit var isLoginObserver: Observer<Boolean>

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        profileViewModel = ProfileViewModel(userRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    // Fetch user data successfully when ViewModel is initialized
    @Test
    fun fetch_user_data_successfully_when_viewmodel_is_initialized() {
        // Unit Test This Function
        runBlocking {
            // Arrange
            val mockUserRepository = userRepository
            val expectedUser = UserModel(
                photo = "photoString",
                email = "test@example.com",
                username = "testUser",
                password = "password",
                nama = "Test Name",
                tanggalLahir = Date(),
                alamat = "Test Address"
            )
            `when`(mockUserRepository.getLoggedUser()).thenReturn(expectedUser)

            // Act
            val profileViewModel = ProfileViewModel(mockUserRepository)

            // Assert
            val actualUser = profileViewModel.user.getOrAwaitValue()
            assertEquals(expectedUser, actualUser)
        }
    }


}