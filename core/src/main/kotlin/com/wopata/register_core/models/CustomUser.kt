package com.wopata.register_core.models

import io.mironov.smuggler.AutoParcelable

/**
 * Created by stephenvinouze on 07/06/2017.
 */
data class CustomUser(
        val username: String,
        val password: String
) : AutoParcelable