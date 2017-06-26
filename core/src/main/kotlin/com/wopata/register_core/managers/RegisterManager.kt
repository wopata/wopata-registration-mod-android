package com.wopata.register_core.managers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import com.wopata.register_core.models.User
import com.wopata.register_core.preferences.UserPreferences

/**
 * Created by stephenvinouze on 07/06/2017.
 */
object RegisterManager {

    var landingBackground: Drawable? = null
    var landingBrand: Drawable? = null
    var landingText: String? = null

    var signInBlock: ((activity: Activity, user: User) -> Unit)? =  null
    var signUpBlock: ((activity: Activity, user: User) -> Unit)? =  null
    var resetBlock: ((activity: Activity, user: User) -> Unit)? =  null

    fun user(context: Context): User {
        return UserPreferences.read(context)
    }

    fun signIn(activity: Activity, user: User) {
        signInBlock?.invoke(activity, user)
    }

    fun signUp(activity: Activity, user: User) {
        signUpBlock?.invoke(activity, user)
    }

    fun reset(activity: Activity, user: User) {
        resetBlock?.invoke(activity, user)
    }

    fun finish(activity: Activity) {
        val intent = Intent(activity, activity.javaClass)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NO_ANIMATION)
        activity.startActivity(intent)
    }

}