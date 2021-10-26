package com.gianlucaveschi.linkedinmock.domain.users

import com.gianlucaveschi.linkedinmock.domain.util.Credentials
import com.gianlucaveschi.linkedinmock.domain.util.Extra
import com.gianlucaveschi.linkedinmock.domain.util.Info

data class LinkedinUserExtended(
    val credentials: Credentials,
    val extra: Extra,
    val info: Info,
    val provider: String,
    val uid: String
)