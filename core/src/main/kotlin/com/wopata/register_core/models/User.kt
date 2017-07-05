package com.wopata.register_core.models

/**
 * Created by stephenvinouze on 07/06/2017.
 */
data class User(
        val username: String? = null,
        val password: String? = null,
        val token: String? = null,
        val source: RegisterSource
)