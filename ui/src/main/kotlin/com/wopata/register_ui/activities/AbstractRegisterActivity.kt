package com.wopata.register_ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import butterknife.bindOptionalView
import butterknife.bindView
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.rengwuxian.materialedittext.MaterialEditText
import com.rengwuxian.materialedittext.validation.METValidator
import com.wopata.register_core.managers.RegisterManager
import com.wopata.register_core.models.RegisterSource
import com.wopata.register_core.models.User
import com.wopata.register_ui.R
import com.wopata.register_ui.managers.ConfigurationManager

/**
 * Created by stephenvinouze on 07/06/2017.
 */
abstract class AbstractRegisterActivity : AppCompatActivity() {

    protected val toolbar: Toolbar by bindView(R.id.register_toolbar)
    protected val usernameEditText: MaterialEditText by bindView(R.id.login_username_edittext)
    protected val passwordEditText: MaterialEditText? by bindOptionalView(R.id.login_password_edittext)
    protected val facebookButton: Button? by bindOptionalView(R.id.register_facebook)
    protected val googleButton: Button? by bindOptionalView(R.id.register_google)
    private val container: FrameLayout by bindView(R.id.register_activity_container)

    private val callbackManager: CallbackManager = CallbackManager.Factory.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        LayoutInflater.from(this).inflate(getContentLayout(), container, true)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val sources = ConfigurationManager.sharedInstance(this).sources
        facebookButton?.visibility = if (sources.contains(RegisterSource.FACEBOOK)) View.VISIBLE else View.GONE
        googleButton?.visibility = if (sources.contains(RegisterSource.GOOGLE)) View.VISIBLE else View.GONE

        LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        RegisterManager.signInBlock?.invoke(this@AbstractRegisterActivity, User(token = loginResult.accessToken.token, source = RegisterSource.FACEBOOK))
                    }

                    override fun onCancel() {}

                    override fun onError(exception: FacebookException) {}
                })

        facebookButton?.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(this, RegisterManager.facebookPermissions)
        }
        googleButton?.setOnClickListener {
            TODO()
        }
    }

    @LayoutRes
    abstract fun getContentLayout(): Int

    protected open fun checkFields(): Boolean {
        val emptyEmailValidator = object : METValidator(getString(R.string.ErrorEmailEmpty)) {
            override fun isValid(text: CharSequence, isEmpty: Boolean): Boolean {
                return !isEmpty
            }
        }
        val emptyPasswordValidator = object : METValidator(getString(R.string.ErrorPasswordEmpty)) {
            override fun isValid(text: CharSequence, isEmpty: Boolean): Boolean {
                return !isEmpty
            }
        }
        val emailValidator = object : METValidator(getString(R.string.ErrorEmailInvalid)) {
            override fun isValid(text: CharSequence, isEmpty: Boolean): Boolean {
                return Patterns.EMAIL_ADDRESS.matcher(text).matches()
            }
        }

        var isValid = usernameEditText.validateWith(emailValidator)
        isValid = isValid and (usernameEditText.validateWith(emptyEmailValidator))
        passwordEditText?.let {
            isValid = isValid and (it.validateWith(emptyPasswordValidator))
        }

        return isValid
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

}