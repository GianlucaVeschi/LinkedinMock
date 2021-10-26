package com.gianlucaveschi.linkedinmock.usecases.detail

import com.gianlucaveschi.linkedinmock.domain.users.LinkedinUserBasic
import com.gianlucaveschi.linkedinmock.domain.users.LinkedinUserExtended
import com.gianlucaveschi.util.DataState
import com.gianlucaveschi.linkedinmock.network.LinkedinService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class GetLinkedinUserDetailUseCaseImpl(
    private val linkedinService: LinkedinService,
) : GetLinkedinUserDetailUseCase {

    override suspend fun run(uid: Int): Flow<DataState<LinkedinUserExtended>> = flow {
        emit(DataState.loading())
        emit(getLinkedinUserDetail(uid))
    }

    private suspend fun getLinkedinUserDetail(uid: Int): DataState<LinkedinUserExtended> = try {
        Timber.d("Trying to get User Detail from the NETWORK...")
        val response = linkedinService.getLinkedinUserDetail(uid)
        response.takeIf { it.isSuccessful }?.body()?.let { userDetail ->
            Timber.d("$userDetail")
            DataState(userDetail)
        } ?: handleError(response.message())
    } catch (exception: Exception) {
        handleError(exception.message)
    }

    private fun handleError(exceptionMessage: String?): DataState<LinkedinUserExtended> {
        Timber.d("retrieval failed.")
        return DataState.error(exceptionMessage ?: "Unknown Error")
    }
}