package com.gianlucaveschi.linkedinmock.usecases

import com.gianlucaveschi.linkedinmock.domain.LinkedinUser
import com.gianlucaveschi.linkedinmock.domain.util.DataState


interface GetLinkedinUsersListUseCase {
    suspend fun run() : DataState<List<LinkedinUser>>
}