package com.wopata.register_ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import butterknife.bindView
import com.wopata.register_core.managers.RegisterManager
import com.wopata.register_core.models.CustomUser
import com.wopata.register_ui.R

/**
 * Created by stephenvinouze on 31/05/2017.
 */
class LoginActivity : AbstractRegisterActivity() {

    private val signInButton: Button by bindView(R.id.login_sign_in_button)
    //private val signUpButton: TextView by bindView(R.id.login_sign_up_button)
    private val requestPasswordButton: TextView by bindView(R.id.login_forgot_button)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toolbar.title = "Se connecter"

        signInButton.setOnClickListener {
            signIn()
        }
        passwordEditText.setOnEditorActionListener { _, _, _ ->
            signIn()
            true
        }
        requestPasswordButton.setOnClickListener {

        }
/*        signUpButton.setOnClickListener {
            startActivityForResult(Intent(this, SignUpActivity::class.java), SIGNUP_REQUEST_CODE)
        }*/
    }

    override fun getContentLayout(): Int {
        return R.layout.activity_login
    }

    private fun signIn() {
        if (checkFields()) {
            val dialog = showWaitingDialog()

            val user = CustomUser(username = usernameEditText.text.toString(), password = passwordEditText.text.toString())
            RegisterManager.signIn(RegisterManager.RegisterType.CUSTOM, user,
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            SIGNUP_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    finish()
                }
            }
        }
    }

}