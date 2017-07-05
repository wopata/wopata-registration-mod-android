package com.wopata.register_core.managers

import android.app.Activity
import android.content.Intent
import com.facebook.login.LoginManager
import com.wopata.register_core.models.RegisterSource
import com.wopata.register_core.models.User


/**
 * Created by stephenvinouze on 07/06/2017.
 */
object RegisterManager {

    var facebookPermissions: List<String> = arrayListOf("public_profile")
    var signInBlock: ((activity: Activity, user: User) -> Unit)? =  null
    var signUpBlock: ((activity: Activity, user: User) -> Unit)? =  null
    var resetBlock: ((activity: Activity, user: User) -> Unit)? =  null

    fun finish(activity: Activity) {
        val intent = Intent(activity, activity.javaClass)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NO_ANIMATION)
        activity.startActivity(intent)
    }

}