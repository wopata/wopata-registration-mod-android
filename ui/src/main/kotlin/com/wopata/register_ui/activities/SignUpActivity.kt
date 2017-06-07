package com.wopata.register_ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import butterknife.bindView
import com.wopata.register_core.models.CustomUser
import com.wopata.register_ui.R

/**
 * Created by stephenvinouze on 31/05/2017.
 */
class SignUpActivity : AbstractRegisterActivity() {

    val signUpButton: Button by bindView(R.id.login_sign_up_button)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        signUpButton.setOnClickListener {
            if (checkLogin()) {
                val intent = Intent()
                val user = CustomUser(username = usernameEditText.text.toString(), password = passwordEditText.text.toString())
                intent.putExtra("toto", user)
                setResult(SIGNUP_CUSTOM_RESULT_CODE, intent)
            }
        }
    }

}