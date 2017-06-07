package com.wopata.register_ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.TextView
import butterknife.bindView
import com.afollestad.materialdialogs.MaterialDialog
import com.wopata.register_core.managers.RegisterManager
import com.wopata.register_core.models.CustomUser
import com.wopata.register_ui.R

/**
 * Created by stephenvinouze on 31/05/2017.
 */
class LoginActivity : AbstractRegisterActivity() {

    val signInButton: Button by bindView(R.id.login_sign_in_button)
    val signUpButton: TextView by bindView(R.id.login_sign_up_button)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signInButton.setOnClickListener {
            signIn()
        }
        passwordEditText.setOnEditorActionListener { _, _, _ ->
            signIn()
            true
        }
        signUpButton.setOnClickListener {
            startActivityForResult(Intent(this, SignUpActivity::class.java), SIGNUP_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            SIGNUP_REQUEST_CODE -> {
                if (resultCode == SIGNUP_CUSTOM_RESULT_CODE) {
                    setResult(SIGNUP_REQUEST_CODE, data)
                    finish()
                }
            }
        }
    }

    private fun signIn() {
        if (checkLogin()) {
            val dialog = MaterialDialog.Builder(this)
                    .progress(true, 0)
                    .show()

            val handler = Handler()
            handler.postDelayed({
                val user = CustomUser(username = usernameEditText.text.toString(), password = passwordEditText.text.toString())
                RegisterManager.signIn(RegisterManager.RegisterType.CUSTOM, user,
                        success = {
                            Log.d("test", "Sign in success")
                            dialog.dismiss()
                        },
                        failure = {
                            Log.d("test", "Sign in failure")
                            dialog.dismiss()
                        })
            }, 2000)
        }
    }

}