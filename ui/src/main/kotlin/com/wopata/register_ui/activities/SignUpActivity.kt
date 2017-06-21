package com.wopata.register_ui.activities

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import butterknife.bindView
import com.rengwuxian.materialedittext.validation.METValidator
import com.wopata.register_core.managers.RegisterManager
import com.wopata.register_core.models.CustomUser
import com.wopata.register_ui.R

/**
 * Created by stephenvinouze on 31/05/2017.
 */
class SignUpActivity : AbstractRegisterActivity() {

    private val signUpButton: Button by bindView(R.id.login_sign_up_button)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toolbar.title = "S'inscrire"

        signUpButton.setOnClickListener {
            signUp()
        }
        passwordConfirmationEditText?.setOnEditorActionListener { _, _, _ ->
            signUp()
            true
        }
    }

    override fun getContentLayout(): Int {
        return R.layout.activity_signup
    }

    override fun checkFields(): Boolean {
        var isValid =  super.checkFields()

        val passwordValidator = object : METValidator("Passwords not identical") {
            override fun isValid(text: CharSequence, isEmpty: Boolean): Boolean {
                return passwordEditText.text == passwordConfirmationEditText?.text
            }
        }

        passwordConfirmationEditText?.let {
            isValid = isValid and (it.validateWith(emptyValidator))
            isValid = isValid and (it.validateWith(passwordValidator))
        }

        return isValid
    }

    private fun signUp() {
        if (checkFields()) {
            val dialog = showWaitingDialog()

            val user = CustomUser(username = usernameEditText.text.toString(), password = passwordEditText.text.toString())
            RegisterManager.signUp(RegisterManager.RegisterType.CUSTOM, user,
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