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
class SignUpActivity : AbstractRegisterActivity() {

    private val signUpButton: Button by bindView(R.id.sign_up_button)
    private val signInButton: TextView by bindView(R.id.sign_up_sign_in_button)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toolbar.title = getString(R.string.SignUpTitle)

        signUpButton.setOnClickListener {
            signUp()
        }
        passwordEditText?.setOnEditorActionListener { _, _, _ ->
            signUp()
            true
        }
        signInButton.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }

    override fun getContentLayout(): Int {
        return R.layout.activity_signup
    }

    private fun signUp() {
        if (checkFields()) {
            val dialog = showWaitingDialog()

            RegisterManager.signUp(
                    user = User(username = usernameEditText.text.toString(), password = passwordEditText?.text.toString(), token = null, source = UserSource.NATIVE),
                    success = {
                        Toast.makeText(this, "Sign up success", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                        finish()
                    },
                    failure = {
                        Toast.makeText(this, "Sign up failure", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    })
        }
    }

}