package com.gianlucaveschi.linkedinmock.usecases.list

import com.gianlucaveschi.linkedinmock.domain.LinkedinUser
import com.gianlucaveschi.linkedinmock.domain.util.DataState
import kotlinx.coroutines.flow.Flow


interface GetLinkedinUsersListUseCase {
    suspend fun run() : Flow<DataState<List<LinkedinUser>>>
}