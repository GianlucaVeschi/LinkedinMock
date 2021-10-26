package com.gianlucaveschi.linkedinmock.ui.list

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gianlucaveschi.linkedinmock.domain.LinkedinUser
import com.gianlucaveschi.linkedinmock.domain.util.DataState
import com.gianlucaveschi.linkedinmock.usecases.GetLinkedinUsersListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LinkedinUsersListViewModel @Inject constructor(
    private val getLinkedinUsersListUseCase: GetLinkedinUsersListUseCase
) : ViewModel() {

    private val _linkedinUsers: MutableStateFlow<List<LinkedinUser>> =
        MutableStateFlow(listOf())
    val linkedinUsers: StateFlow<List<LinkedinUser>> = _linkedinUsers

    fun getLinkedinUsersList() {
        viewModelScope.launch {
            getLinkedinUsersListUseCase.run().collect { dataState ->
                dataState.loading.let {
                    Timber.d(" onLoading")
                }

                dataState.data?.let { list ->
                    Timber.d("onSuccess $list")
                    _linkedinUsers.value = list
                }

                dataState.error?.let { error ->
                    Timber.e("onError: $error")
                }
            }
        }
    }
}