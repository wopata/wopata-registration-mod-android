package com.wopata.register_ui.activities

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Patterns
import android.view.LayoutInflater
import android.widget.FrameLayout
import butterknife.bindOptionalView
import butterknife.bindView
import com.afollestad.materialdialogs.MaterialDialog
import com.rengwuxian.materialedittext.MaterialEditText
import com.rengwuxian.materialedittext.validation.METValidator
import com.wopata.register_ui.R

/**
 * Created by stephenvinouze on 07/06/2017.
 */
abstract class AbstractRegisterActivity : AppCompatActivity() {

    companion object {
        internal const val SIGNUP_REQUEST_CODE = 101
    }

    protected val toolbar: Toolbar by bindView(R.id.register_toolbar)
    protected val usernameEditText: MaterialEditText by bindView(R.id.login_username_edittext)
    protected val passwordEditText: MaterialEditText by bindView(R.id.login_password_edittext)
    protected val passwordConfirmationEditText: MaterialEditText? by bindOptionalView(R.id.login_password_confirmation_edittext)
    private val container: FrameLayout by bindView(R.id.register_activity_container)

    protected val emptyValidator = object : METValidator("Cannot be empty") {
        override fun isValid(text: CharSequence, isEmpty: Boolean): Boolean {
            return !isEmpty
        }
    }
    protected val emailValidator = object : METValidator("Email not valid") {
        override fun isValid(text: CharSequence, isEmpty: Boolean): Boolean {
            return Patterns.EMAIL_ADDRESS.matcher(text).matches()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        LayoutInflater.from(this).inflate(getContentLayout(), container, true)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    @LayoutRes
    abstract fun getContentLayout(): Int

    protected open fun checkFields(): Boolean {
        var isValid = usernameEditText.validateWith(emptyValidator)
        isValid = isValid and (usernameEditText.validateWith(emailValidator))
        isValid = isValid and (passwordEditText.validateWith(emptyValidator))
        isValid = isValid and (passwordConfirmationEditText == null || passwordConfirmationEditText?.validateWith(emptyValidator) == true)

        return isValid
    }

    protected open fun showWaitingDialog(): MaterialDialog {
        return MaterialDialog.Builder(this)
                .progress(true, 0)
                .show()
    }

}