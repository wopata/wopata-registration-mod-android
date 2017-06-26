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

    protected val toolbar: Toolbar by bindView(R.id.register_toolbar)
    protected val usernameEditText: MaterialEditText by bindView(R.id.login_username_edittext)
    protected val passwordEditText: MaterialEditText? by bindOptionalView(R.id.login_password_edittext)
    private val container: FrameLayout by bindView(R.id.register_activity_container)

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

    protected open fun showWaitingDialog(): MaterialDialog {
        return MaterialDialog.Builder(this)
                .progress(true, 0)
                .show()
    }

}