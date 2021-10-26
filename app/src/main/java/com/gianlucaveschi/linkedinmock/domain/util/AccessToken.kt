package com.gianlucaveschi.linkedinmock.domain.util

data class AccessToken(
    val consumer: Any,
    val params: Params,
    val response: Any,
    val secret: String,
    val token: String
)