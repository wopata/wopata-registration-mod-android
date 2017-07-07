package com.wopata.register_ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import butterknife.bindView
import com.wopata.register_core.managers.RegisterManager
import com.wopata.register_core.models.RegisterSource
import com.wopata.register_core.models.User
import com.wopata.register_ui.R

/**
 * Created by stephenvinouze on 31/05/2017.
 */
class SignInActivity : AbstractRegisterActivity() {

    private val signUpButton: TextView by bindView(R.id.sign_in_sign_up_button)
    private val resetPasswordButton: TextView by bindView(R.id.sign_in_reset_button)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toolbar.title = getString(R.string.SignInTitle)

        ctaButton.setOnClickListener {
            signIn()
        }
        passwordEditText?.setOnEditorActionListener { _, _, _ ->
            signIn()
            true
        }
        resetPasswordButton.setOnClickListener {
            startActivity(Intent(this, ResetPasswordActivity::class.java))
        }
        signUpButton.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    override fun getContentLayout(): Int {
        return R.layout.activity_signin
    }

    private fun signIn() {
        if (checkFields()) {
            RegisterManager.signIn?.invoke(this, User(username = usernameEditText.text.toString(), password = passwordEditText?.text.toString(), source = RegisterSource.NATIVE))
        }
    }

}