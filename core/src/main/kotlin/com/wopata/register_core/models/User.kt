package com.wopata.register_core.models

/**
 * Created by stephenvinouze on 07/06/2017.
 */
data class User(
        val username: String?,
        val password: String?,
        val token: String?,
        val source: UserSource
)