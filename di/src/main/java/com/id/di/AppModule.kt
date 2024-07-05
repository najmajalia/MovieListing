package com.id.di

import com.id.data.movie.MovieRepository
import com.id.data.user.UserRepository
import com.id.domain.repository.IMovieRepository
import com.id.domain.repository.IUserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {
    @Binds
    abstract fun bindRepository(
        repository: MovieRepository
    ): IMovieRepository

    @Binds
    abstract fun bindUserRepository(
        userRepository: UserRepository
    ): IUserRepository
}