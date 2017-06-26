package com.wopata.register_core.managers

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.wopata.register_core.models.User
import com.wopata.register_core.preferences.UserPreferences
import kotlin.reflect.KClass

/**
 * Created by stephenvinouze on 07/06/2017.
 */
object RegisterManager {

    private lateinit var signInBlock: (activity: Activity, user: User) -> Unit
    private lateinit var signUpBlock: (activity: Activity, user: User) -> Unit
    private lateinit var resetBlock: (activity: Activity, user: User) -> Unit

    fun initialize(signInBlock: (activity: Activity, user: User) -> Unit,
                   signUpBlock: (activity: Activity, user: User) -> Unit,
                   resetBlock: (activity: Activity, user: User) -> Unit) {
        this.signInBlock = signInBlock
        this.signUpBlock = signUpBlock
        this.resetBlock = resetBlock
    }

    fun finish(context: Context, clazz: KClass<*>) {
        val intent = Intent(context, clazz.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NO_ANIMATION)
        context.startActivity(intent)
    }

    fun user(context: Context): User {
        return UserPreferences.read(context)
    }

    fun signIn(activity: Activity, user: User) {
        try {
            signInBlock(activity, user)
        } catch (e: UninitializedPropertyAccessException) {
            throw IllegalStateException("SignInBlock not initialized. Call RegisterManager.initialize() first and configure your blocks")
        }
    }

    fun signUp(activity: Activity, user: User) {
        try {
            signUpBlock(activity, user)
        } catch (e: UninitializedPropertyAccessException) {
            throw IllegalStateException("SignUpBlock not initialized. Call RegisterManager.initialize() first and configure your blocks")
        }
    }

    fun reset(activity: Activity, user: User) {
        try {
            resetBlock(activity, user)
        } catch (e: UninitializedPropertyAccessException) {
            throw IllegalStateException("resetBlock not initialized. Call RegisterManager.initialize() first and configure your blocks")
        }
    }

}