package com.gianlucaveschi.linkedinmock.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewModelScope
import com.gianlucaveschi.linkedinmock.domain.users.LinkedinUserBasic
import com.gianlucaveschi.linkedinmock.domain.users.LinkedinUserExtended
import com.gianlucaveschi.linkedinmock.usecases.detail.GetLinkedinUserDetailUseCase
import com.gianlucaveschi.linkedinmock.usecases.list.GetLinkedinUsersListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val getUsersListUseCase: GetLinkedinUsersListUseCase,
    private val getUserDetailUseCase: GetLinkedinUserDetailUseCase
) : ViewModel() {

    private val _linkedinUsersBasic: MutableStateFlow<List<LinkedinUserBasic>?> =
        MutableStateFlow(listOf())
    val linkedinUsersBasic: StateFlow<List<LinkedinUserBasic>?> = _linkedinUsersBasic

    private val _linkedinUserExtendedDetail: MutableStateFlow<LinkedinUserExtended?> =
        MutableStateFlow(null)
    val linkedinUserExtendedDetail: StateFlow<LinkedinUserExtended?> = _linkedinUserExtendedDetail

    suspend fun getLinkedinUsersList() {
        viewModelScope.launch {
            getUsersListUseCase.run()
                .onEach { dataState ->
                    dataState.loading.let {
                        Timber.d(" onLoading")
                    }

                    dataState.data?.let { list ->
                        Timber.d("onSuccess $list")
                        _linkedinUsersBasic.value = list
                    }

                    dataState.error?.let { error ->
                        Timber.e("onError: $error")
                    }
                }.launchIn(viewModelScope)
        }
    }

    suspend fun getLinkedinUserDetail(uid: Int) {
        viewModelScope.launch {
            getUserDetailUseCase.run(uid).onEach { dataState ->
                dataState.loading.let {
                    Timber.d(" onLoading")
                }

                dataState.data?.let { user ->
                    Timber.d("onSuccess $user")
                    _linkedinUserExtendedDetail.value = user
                }

                dataState.error?.let { error ->
                    Timber.e("onError: $error")
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun launchSocialActivity(context: Context, socialType: String) {
        val linkedInUrl = "https://www.linkedin.com/in/gianlucaveschi/"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkedInUrl))
        context.startActivity(intent)
    }

    fun onVisitLinkedinClicked() {
        Timber.d("button clicked")
    }
}