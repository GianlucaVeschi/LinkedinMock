package com.gianlucaveschi.linkedinmock.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gianlucaveschi.linkedinmock.domain.LinkedinUser
import com.gianlucaveschi.linkedinmock.domain.util.DataState
import com.gianlucaveschi.linkedinmock.usecases.GetLinkedinUsersListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
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