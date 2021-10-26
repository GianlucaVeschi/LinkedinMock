package com.gianlucaveschi.linkedinmock.network

import com.gianlucaveschi.linkedinmock.domain.LinkedinUser
import retrofit2.Response
import retrofit2.http.GET

interface LinkedinService {

    @GET("dev/random/list/")
    suspend fun getLinkedinUsersList() : Response<List<LinkedinUser>>
}