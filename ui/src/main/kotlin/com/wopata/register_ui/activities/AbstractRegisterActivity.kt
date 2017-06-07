package com.wopata.register_ui.activities

import android.support.v7.app.AppCompatActivity
import android.util.Patterns
import butterknife.bindOptionalView
import butterknife.bindView
import com.rengwuxian.materialedittext.MaterialEditText
import com.rengwuxian.materialedittext.validation.METValidator
import com.wopata.register_ui.R

/**
 * Created by stephenvinouze on 07/06/2017.
 */
abstract class AbstractRegisterActivity : AppCompatActivity() {

    companion object {
        const val SIGNIN_REQUEST_CODE = 100
        internal const val SIGNUP_REQUEST_CODE = 101
        internal const val SIGNUP_CUSTOM_RESULT_CODE = 1000
        internal const val SIGNIN_CUSTOM_RESULT_CODE = 1001
        internal const val SIGNIN_FACEBOOK_RESULT_CODE = 1002
        internal const val SIGNIN_GOOGLE_RESULT_CODE = 1003
    }

    protected val usernameEditText: MaterialEditText by bindView(R.id.login_username_edittext)
    protected val passwordEditText: MaterialEditText by bindView(R.id.login_password_edittext)
    protected val passwordConfirmationEditText: MaterialEditText? by bindOptionalView(R.id.login_password_confirmation_edittext)

    protected fun checkLogin(): Boolean {
        val emptyValidator = object : METValidator("Cannot be empty") {
            override fun isValid(text: CharSequence, isEmpty: Boolean): Boolean {
                return !isEmpty
            }
        }
        val emailValidator = object : METValidator("Email not valid") {
            override fun isValid(text: CharSequence, isEmpty: Boolean): Boolean {
                return Patterns.EMAIL_ADDRESS.matcher(text).matches()
            }
        }

        var isValid = usernameEditText.validateWith(emptyValidator)
        isValid = isValid and (usernameEditText.validateWith(emailValidator))
        isValid = isValid and (passwordEditText.validateWith(emptyValidator))
        isValid = isValid and (passwordConfirmationEditText == null || passwordConfirmationEditText?.validateWith(emptyValidator) == true)

        return isValid
    }

}