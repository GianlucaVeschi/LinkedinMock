package com.gianlucaveschi.linkedinmock.network

import retrofit2.Response
import retrofit2.http.GET

interface LinkedinService {

    @GET("dev/random/list/")
    suspend fun getLinkedinUsersList()
}