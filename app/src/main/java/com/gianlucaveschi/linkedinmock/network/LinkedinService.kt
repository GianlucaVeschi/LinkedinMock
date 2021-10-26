package com.gianlucaveschi.linkedinmock.network

import com.gianlucaveschi.linkedinmock.domain.users.LinkedinUserBasic
import com.gianlucaveschi.linkedinmock.domain.users.LinkedinUserExtended
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LinkedinService {

    @GET("dev/random/list/")
    suspend fun getLinkedinUsersList() : Response<List<LinkedinUserBasic>>

    @GET("dev/random")
    suspend fun getLinkedinUserDetail(@Query("uid") uid: Int) : Response<LinkedinUserExtended>
}