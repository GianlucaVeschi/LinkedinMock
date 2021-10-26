package com.gianlucaveschi.linkedinmock.ui.main

import androidx.lifecycle.ViewModel
import com.gianlucaveschi.linkedinmock.usecases.GetLinkedinUsersListUseCase
import com.gianlucaveschi.linkedinmock.usecases.GetLinkedinUsersListUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LinkedinUsersListViewModel  @Inject constructor(
    private val getLinkedinUsersListUseCase: GetLinkedinUsersListUseCaseImpl
) : ViewModel() {

    fun getLinkedinUsersList(){
        getLinkedinUsersListUseCase.run()
    }
}