package com.gianlucaveschi.linkedinmock.usecases

import android.util.Log

class GetLinkedinUsersListUseCase(
    //private val linkedinService: LinkedinService,
) {

    fun run(){
        Log.d(TAG, "run: ")
    }

    companion object{
        private const val TAG = "GetLinkedinUsersList"
    }
}