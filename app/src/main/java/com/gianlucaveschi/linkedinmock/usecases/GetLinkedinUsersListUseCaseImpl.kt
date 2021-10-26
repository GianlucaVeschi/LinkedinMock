package com.gianlucaveschi.linkedinmock.usecases

import android.util.Log
import com.gianlucaveschi.linkedinmock.network.LinkedinService

class GetLinkedinUsersListUseCaseImpl(
    private val linkedinService: LinkedinService,
) : GetLinkedinUsersListUseCase {

    override suspend fun run() {
        Log.d(TAG, "run: ")
        getLinkedinUsersList()
    }

    private suspend fun getLinkedinUsersList() {
        try {
            Log.d(TAG, "Trying to get Users from the NETWORK...")
            val response = linkedinService.getLinkedinUsersList()
            Log.d(TAG, "getLinkedinUsersList: $response ")
            Log.d(TAG, "getLinkedinUsersList: ${response.body()} ")

        } catch (exception: Exception) {
            handleError(exception.message)
        }
    }

    private fun handleError(exceptionMessage: String?) {
        Log.d(TAG, "retrieval failed.")
        //todo
        //return DataState.error(exceptionMessage ?: "Unknown Error")
    }

    companion object {
        private const val TAG = "GetLinkedinUsersList"
    }
}