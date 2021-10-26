package com.gianlucaveschi.linkedinmock.di

import com.gianlucaveschi.linkedinmock.network.LinkedinService
import com.gianlucaveschi.linkedinmock.usecases.detail.GetLinkedinUserDetailUseCase
import com.gianlucaveschi.linkedinmock.usecases.detail.GetLinkedinUserDetailUseCaseImpl
import com.gianlucaveschi.linkedinmock.usecases.list.GetLinkedinUsersListUseCase
import com.gianlucaveschi.linkedinmock.usecases.list.GetLinkedinUsersListUseCaseImpl
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
    fun provideGetLinkedinUsersListUseCase(
        linkedinService: LinkedinService,
    ): GetLinkedinUsersListUseCase {
        return GetLinkedinUsersListUseCaseImpl(
            linkedinService = linkedinService
        )
    }

    @ViewModelScoped
    @Provides
    fun provideGetLinkedinUserDetailUseCase(
        linkedinService: LinkedinService,
    ): GetLinkedinUserDetailUseCase {
        return GetLinkedinUserDetailUseCaseImpl(
            linkedinService = linkedinService
        )
    }

}