package com.gianlucaveschi.linkedinmock.domain.util

data class Params(
    val oauth_authorization_expires_in: Int,
    val oauth_expires_in: Int,
    val oauth_token: String,
    val oauth_token_secret: String
)