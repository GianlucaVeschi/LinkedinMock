package com.gianlucaveschi.linkedinmock.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gianlucaveschi.linkedinmock.domain.LinkedinUser
import com.gianlucaveschi.linkedinmock.usecases.detail.GetLinkedinUserDetailUseCase
import com.gianlucaveschi.linkedinmock.usecases.list.GetLinkedinUsersListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val getUsersListUseCase: GetLinkedinUsersListUseCase,
    private val getUserDetailUseCase: GetLinkedinUserDetailUseCase
) : ViewModel() {

    private val _linkedinUsers: MutableStateFlow<List<LinkedinUser>?> = MutableStateFlow(listOf())
    val linkedinUsers: StateFlow<List<LinkedinUser>?> = _linkedinUsers

    private val _linkedinUserDetail: MutableStateFlow<LinkedinUser?> = MutableStateFlow(null)
    val linkedinUserDetail: StateFlow<LinkedinUser?> = _linkedinUserDetail

    fun getLinkedinUsersList() {
        viewModelScope.launch {
            getUsersListUseCase.run().collect { dataState ->
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

    fun getLinkedinUserDetail(uid: Int) {
        viewModelScope.launch {
            getUserDetailUseCase.run(uid).collect { dataState ->
                dataState.loading.let {
                    Timber.d(" onLoading")
                }

                dataState.data?.let { user ->
                    Timber.d("onSuccess $user")
                    _linkedinUserDetail.value = user
                }

                dataState.error?.let { error ->
                    Timber.e("onError: $error")
                }
            }
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