package com.wopata.register_core.managers

import android.content.Context
import com.wopata.register_core.models.CustomUser
import com.wopata.register_core.preferences.UserPreferences

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

    fun user(context: Context): CustomUser {
        return UserPreferences.read(context)
    }

    fun signIn(registerType: RegisterType, user: CustomUser, success: () -> Unit, failure: () -> Unit) {
        try {
            signInBlock(user, success, failure)
        } catch (e: UninitializedPropertyAccessException) {
            throw IllegalStateException("SignInBlock not initialized. Call RegisterManager.initialize() first and configure your blocks")
        }
    }

    fun signUp(registerType: RegisterType, user: CustomUser, success: () -> Unit, failure: () -> Unit) {
        try {
            signUpBlock(user, success, failure)
        } catch (e: UninitializedPropertyAccessException) {
            throw IllegalStateException("SignUpBlock not initialized. Call RegisterManager.initialize() first and configure your blocks")
        }
    }

    fun signOut(registerType: RegisterType) {

    }

    enum class RegisterType {
        CUSTOM, FACEBOOK, GOOGLE
    }

}