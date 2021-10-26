package com.gianlucaveschi.linkedinmock.usecases.detail

import com.gianlucaveschi.linkedinmock.domain.users.LinkedinUserBasic
import com.gianlucaveschi.linkedinmock.domain.users.LinkedinUserExtended
import com.gianlucaveschi.util.DataState
import kotlinx.coroutines.flow.Flow

interface GetLinkedinUserDetailUseCase {
    suspend fun run(uid: Int): Flow<DataState<LinkedinUserExtended>>
}