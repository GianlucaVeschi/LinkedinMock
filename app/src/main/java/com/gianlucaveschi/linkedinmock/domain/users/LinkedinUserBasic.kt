package com.gianlucaveschi.linkedinmock.domain.users

data class LinkedinUserBasic (

    val uid : Int,
    val info : Info
)

data class Info (
    val name : String,
    val email : String,
    val pictureUrl : String?
)