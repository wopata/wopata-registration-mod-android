package com.wopata.register_core.managers

import android.content.Context
import com.wopata.register_core.models.User
import com.wopata.register_core.preferences.UserPreferences

/**
 * Created by stephenvinouze on 07/06/2017.
 */
object RegisterManager {

    private lateinit var signInBlock: (user: User, success: () -> Unit, failure: () -> Unit) -> Unit
    private lateinit var signUpBlock: (user: User, success: () -> Unit, failure: () -> Unit) -> Unit
    private lateinit var resetBlock: (user: User, success: () -> Unit, failure: () -> Unit) -> Unit

    fun initialize(signInBlock: (user: User, success: () -> Unit, failure: () -> Unit) -> Unit,
                   signUpBlock: (user: User, success: () -> Unit, failure: () -> Unit) -> Unit,
                   resetBlock: (user: User, success: () -> Unit, failure: () -> Unit) -> Unit) {
        this.signInBlock = signInBlock
        this.signUpBlock = signUpBlock
        this.resetBlock = resetBlock
    }

    fun user(context: Context): User {
        return UserPreferences.read(context)
    }

    fun signIn(user: User, success: () -> Unit, failure: () -> Unit) {
        try {
            signInBlock(user, success, failure)
        } catch (e: UninitializedPropertyAccessException) {
            throw IllegalStateException("SignInBlock not initialized. Call RegisterManager.initialize() first and configure your blocks")
        }
    }

    fun signUp(user: User, success: () -> Unit, failure: () -> Unit) {
        try {
            signUpBlock(user, success, failure)
        } catch (e: UninitializedPropertyAccessException) {
            throw IllegalStateException("SignUpBlock not initialized. Call RegisterManager.initialize() first and configure your blocks")
        }
    }

    fun reset(user: User, success: () -> Unit, failure: () -> Unit) {
        try {
            resetBlock(user, success, failure)
        } catch (e: UninitializedPropertyAccessException) {
            throw IllegalStateException("resetBlock not initialized. Call RegisterManager.initialize() first and configure your blocks")
        }
    }

}