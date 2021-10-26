package com.gianlucaveschi.linkedinmock.domain

import com.google.gson.annotations.SerializedName

data class LinkedinUser (

    @SerializedName("uid") val uid : Int,
    @SerializedName("info") val info : Info
)

data class Info (

    @SerializedName("name") val name : String,
    @SerializedName("email") val email : String,
    @SerializedName("pictureUrl") val pictureUrl : String
)