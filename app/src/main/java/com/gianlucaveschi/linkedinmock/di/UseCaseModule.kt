package com.gianlucaveschi.linkedinmock.di

import com.gianlucaveschi.linkedinmock.usecases.GetLinkedinUsersListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @ViewModelScoped
    @Provides
    fun provideGetLinkedinUsersListUseCase() = GetLinkedinUsersListUseCase()

}