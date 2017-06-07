package com.wopata.register_core.managers

import com.wopata.register_core.models.CustomUser

/**
 * Created by stephenvinouze on 07/06/2017.
 */
object RegisterManager {

    private lateinit var signInBlock: (user: CustomUser, success: () -> Unit, failure: () -> Unit) -> Unit
    private lateinit var signUpBlock: (user: CustomUser, success: () -> Unit, failure: () -> Unit) -> Unit

    fun initialize(signInBlock: (user: CustomUser, success: () -> Unit, failure: () -> Unit) -> Unit,
                   signUpBlock: (user: CustomUser, success: () -> Unit, failure: () -> Unit) -> Unit) {
        this.signInBlock = signInBlock
        this.signUpBlock = signUpBlock
    }

    fun signIn(registerType: RegisterType, user: CustomUser, success: () -> Unit, failure: () -> Unit) {
        if (signInBlock != null) {
            signInBlock(user, success, failure)
        }
    }

    fun signUp(registerType: RegisterType, user: CustomUser, success: () -> Unit, failure: () -> Unit) {
        signUpBlock(user, success, failure)
    }

    fun signOut(registerType: RegisterType) {

    }

    enum class RegisterType {
        CUSTOM, FACEBOOK, GOOGLE
    }

}