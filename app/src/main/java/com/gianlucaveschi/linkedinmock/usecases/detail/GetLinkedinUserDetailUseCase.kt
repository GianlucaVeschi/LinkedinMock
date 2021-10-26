package com.gianlucaveschi.linkedinmock.usecases.detail

import com.gianlucaveschi.linkedinmock.domain.LinkedinUser
import com.gianlucaveschi.linkedinmock.domain.util.DataState
import kotlinx.coroutines.flow.Flow

interface GetLinkedinUserDetailUseCase {
    suspend fun run(uid: Int): Flow<DataState<LinkedinUser>>
}