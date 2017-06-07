package com.wopata.register_core.managers

import com.wopata.register_core.models.CustomUser

/**
 * Created by stephenvinouze on 07/06/2017.
 */
object RegisterManager {

    private var signInBlock: (user: CustomUser) -> Unit = { }
    private var signUpBlock: (user: CustomUser) -> Unit = { }

    fun initialize(signInBlock: (user: CustomUser) -> Unit,
                   signUpBlock: (user: CustomUser) -> Unit) {
        this.signInBlock = signInBlock
        this.signUpBlock = signUpBlock
    }

    fun signIn(registerType: RegisterType, user: CustomUser) {
        signInBlock(user)
    }

    fun signUp(registerType: RegisterType, user: CustomUser) {
        signUpBlock(user)
    }

    fun signOut(registerType: RegisterType) {

    }

    enum class RegisterType {
        CUSTOM, FACEBOOK, GOOGLE
    }

}