package com.gianlucaveschi.linkedinmock.usecases.list

import com.gianlucaveschi.linkedinmock.domain.users.LinkedinUserBasic
import com.gianlucaveschi.util.DataState
import com.gianlucaveschi.linkedinmock.network.LinkedinService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class GetLinkedinUsersListUseCaseImpl(
    private val linkedinService: LinkedinService,
) : GetLinkedinUsersListUseCase {

    override suspend fun run() : Flow<DataState<List<LinkedinUserBasic>>> = flow {
            emit(DataState.loading())
            emit(getLinkedinUsersList())
    }

    private suspend fun getLinkedinUsersList(): DataState<List<LinkedinUserBasic>> = try {
        Timber.d("Trying to get Users from the NETWORK...")
        val response = linkedinService.getLinkedinUsersList()
        response.takeIf { it.isSuccessful }?.body()?.let { usersList ->
            Timber.d("$usersList")
            DataState(usersList)
        } ?: handleError(response.message())
    } catch (exception: Exception) {
        handleError(exception.message)
    }

    private fun handleError(exceptionMessage: String?): DataState<List<LinkedinUserBasic>> {
        Timber.d("retrieval failed.")
        return DataState.error(exceptionMessage ?: "Unknown Error")
    }
}