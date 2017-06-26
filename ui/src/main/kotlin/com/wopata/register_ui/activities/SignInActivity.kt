package com.wopata.register_ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import butterknife.bindView
import com.wopata.register_core.managers.RegisterManager
import com.wopata.register_core.models.User
import com.wopata.register_core.models.UserSource
import com.wopata.register_ui.R

/**
 * Created by stephenvinouze on 31/05/2017.
 */
class SignInActivity : AbstractRegisterActivity() {

    private val signInButton: Button by bindView(R.id.sign_in_button)
    private val signUpButton: TextView by bindView(R.id.sign_in_sign_up_button)
    private val requestPasswordButton: TextView by bindView(R.id.sign_in_forgot_button)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toolbar.title = getString(R.string.SignInTitle)

        signInButton.setOnClickListener {
            signIn()
        }
        passwordEditText?.setOnEditorActionListener { _, _, _ ->
            signIn()
            true
        }
        requestPasswordButton.setOnClickListener {
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
            val dialog = showWaitingDialog()

            RegisterManager.signIn(
                    user = User(username = usernameEditText.text.toString(), password = passwordEditText?.text.toString(), token = null, source = UserSource.NATIVE),
                    success = {
                        Toast.makeText(this, "Sign in success", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                        finish()
                    },
                    failure = {
                        Toast.makeText(this, "Sign in failure", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    })
        }
    }

}