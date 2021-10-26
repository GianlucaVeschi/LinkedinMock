package com.gianlucaveschi.linkedinmock.usecases.list

import com.gianlucaveschi.linkedinmock.domain.users.LinkedinUserBasic
import com.gianlucaveschi.util.DataState
import kotlinx.coroutines.flow.Flow


interface GetLinkedinUsersListUseCase {
    suspend fun run() : Flow<DataState<List<LinkedinUserBasic>>>
}