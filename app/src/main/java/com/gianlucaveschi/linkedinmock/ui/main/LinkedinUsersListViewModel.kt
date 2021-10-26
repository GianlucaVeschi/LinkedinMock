package com.gianlucaveschi.linkedinmock.ui.main

import androidx.lifecycle.ViewModel
import com.gianlucaveschi.linkedinmock.usecases.GetLinkedinUsersListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LinkedinUsersListViewModel  @Inject constructor(
    private val getLinkedinUsersListUseCase: GetLinkedinUsersListUseCase
) : ViewModel() {

    fun getLinkedinUsersList(){
        getLinkedinUsersListUseCase.run()
    }
}