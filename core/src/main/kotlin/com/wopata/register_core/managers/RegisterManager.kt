package com.wopata.register_core.managers

import android.app.Activity
import android.content.Intent
import android.support.v4.app.FragmentActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.wopata.register_core.models.RegisterSource
import com.wopata.register_core.models.User


/**
 * Created by stephenvinouze on 07/06/2017.
 */
object RegisterManager {

    private const val REQUEST_GOOGLE_REGISTER = 1

    var facebookPermissions: List<String> = arrayListOf("public_profile")
    var googleIdToken: String? = null
    var signIn: ((activity: Activity, user: User) -> Unit)? =  null
    var signUp: ((activity: Activity, user: User) -> Unit)? =  null
    var reset: ((activity: Activity, user: User) -> Unit)? =  null

    private val callbackManager: CallbackManager = CallbackManager.Factory.create()

    fun login(activity: FragmentActivity, source: RegisterSource) {
        when (source) {
            RegisterSource.NATIVE -> throw IllegalStateException("Use your signIn/signUp methods to handle your registration for NATIVE source")
            RegisterSource.FACEBOOK -> {
                if (facebookPermissions.isNotEmpty()) {
                    LoginManager.getInstance().registerCallback(callbackManager,
                            object : FacebookCallback<LoginResult> {
                                override fun onSuccess(loginResult: LoginResult) {
                                    RegisterManager.signIn?.invoke(activity, User(token = loginResult.accessToken.token, source = RegisterSource.FACEBOOK))
                                }

                                override fun onCancel() {}

                                override fun onError(exception: FacebookException) {}
                            })
                    LoginManager.getInstance().logInWithReadPermissions(activity, facebookPermissions)
                } else {
                    throw IllegalStateException("At least one permission is required to connect with Facebook")
                }
            }
            RegisterSource.GOOGLE -> {
                if (googleIdToken != null) {
                    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken(googleIdToken)
                            .build()

                    val googleApiClient = GoogleApiClient.Builder(activity)
                            .enableAutoManage(activity, null)
                            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                            .build()

                    val signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
                    activity.startActivityForResult(signInIntent, REQUEST_GOOGLE_REGISTER)
                } else {
                    throw IllegalStateException("GoogleIdToken is required to connect with Google")
                }
            }
        }
    }

    fun handleResult(activity: Activity, requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        when (requestCode) {
            REQUEST_GOOGLE_REGISTER -> {
                val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
                if (result.isSuccess) {
                    RegisterManager.signIn?.invoke(activity, User(token = result.signInAccount?.idToken, source = RegisterSource.GOOGLE))
                }
                return result.isSuccess
            }
            else -> return callbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun finish(activity: Activity) {
        val intent = Intent(activity, activity.javaClass)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NO_ANIMATION)
        activity.startActivity(intent)
    }

}