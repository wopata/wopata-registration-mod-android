package com.wopata.register_ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.annotation.LayoutRes
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.SpannableString
import android.text.Spanned
import android.text.style.DynamicDrawableSpan
import android.text.style.ImageSpan
import android.text.style.StyleSpan
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
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.rengwuxian.materialedittext.MaterialEditText
import com.rengwuxian.materialedittext.validation.METValidator
import com.wopata.register_core.managers.RegisterManager
import com.wopata.register_core.models.RegisterSource
import com.wopata.register_core.models.User
import com.wopata.register_ui.R
import com.wopata.register_ui.managers.ConfigurationManager
import java.util.regex.Pattern


/**
 * Created by stephenvinouze on 07/06/2017.
 */
abstract class AbstractRegisterActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {

    companion object {
        private const val REQUEST_GOOGLE_REGISTER = 1
    }

    protected val toolbar: Toolbar by bindView(R.id.register_toolbar)
    protected val usernameEditText: MaterialEditText by bindView(R.id.login_username_edittext)
    protected val passwordEditText: MaterialEditText? by bindOptionalView(R.id.login_password_edittext)
    protected val facebookButton: Button? by bindOptionalView(R.id.register_facebook)
    protected val googleButton: Button? by bindOptionalView(R.id.register_google)
    private val container: FrameLayout by bindView(R.id.register_activity_container)

    private var callbackManager: CallbackManager? = null
    private var googleApiClient: GoogleApiClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        LayoutInflater.from(this).inflate(getContentLayout(), container, true)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val sources = ConfigurationManager.sharedInstance(this).sources

        if (sources.contains(RegisterSource.FACEBOOK)) {
            facebookButton?.visibility = View.VISIBLE
            configureSocialButton(facebookButton, getString(R.string.LoginWithFacebook), R.drawable.ic_facebook, "Facebook")

            callbackManager = CallbackManager.Factory.create()
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
        } else {
            facebookButton?.visibility = View.GONE
        }

        if (sources.contains(RegisterSource.GOOGLE)) {
            googleButton?.visibility = View.VISIBLE
            configureSocialButton(googleButton, getString(R.string.LoginWithGoogle), R.drawable.ic_google, "Google")

            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken("879551982001-v9m6vgoj5dak0sb2tprbv72mcnjpfiju.apps.googleusercontent.com")
                    .build()

            googleApiClient = GoogleApiClient.Builder(this)
                    .enableAutoManage(this, this)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build()

            googleButton?.setOnClickListener {
                val signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
                startActivityForResult(signInIntent, REQUEST_GOOGLE_REGISTER)
            }
        } else {
            googleButton?.visibility = View.GONE
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

    private fun configureSocialButton(button: Button?, text: String, @DrawableRes iconRes: Int, bold: String) {
        val offset = "    "
        val span = SpannableString("$offset$text")
        val icon = ContextCompat.getDrawable(this, iconRes)
        icon.setBounds(0, 0, icon.intrinsicWidth, icon.intrinsicHeight)

        val imageSpan = ImageSpan(icon, DynamicDrawableSpan.ALIGN_BOTTOM)
        span.setSpan(imageSpan, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)

        val pattern = Pattern.compile(bold, Pattern.CASE_INSENSITIVE)
        val match = pattern.matcher(text)
        while (match.find()) {
            val boldSpan = StyleSpan(android.graphics.Typeface.BOLD)
            span.setSpan(boldSpan, offset.length + match.start(), offset.length + match.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        button?.text = span
    }

    override fun onConnectionFailed(p0: ConnectionResult) {}

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_GOOGLE_REGISTER -> {
                val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
                if (result.isSuccess) {
                    RegisterManager.signInBlock?.invoke(this@AbstractRegisterActivity, User(token = result.signInAccount?.idToken, source = RegisterSource.GOOGLE))
                }
            }
            else -> callbackManager?.onActivityResult(requestCode, resultCode, data)
        }
    }

}