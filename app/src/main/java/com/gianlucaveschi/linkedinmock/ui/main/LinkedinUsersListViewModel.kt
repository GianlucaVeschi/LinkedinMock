package com.gianlucaveschi.linkedinmock.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gianlucaveschi.linkedinmock.usecases.GetLinkedinUsersListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LinkedinUsersListViewModel  @Inject constructor(
    private val getLinkedinUsersListUseCase: GetLinkedinUsersListUseCase
) : ViewModel() {

    fun getLinkedinUsersList(){
        viewModelScope.launch {
            getLinkedinUsersListUseCase.run()
        }
    }
}