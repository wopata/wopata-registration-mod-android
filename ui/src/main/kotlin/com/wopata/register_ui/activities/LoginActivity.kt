package com.wopata.register_ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import butterknife.bindView
import com.wopata.register_ui.R

/**
 * Created by stephenvinouze on 31/05/2017.
 */
class LoginActivity : AppCompatActivity() {

    val signInButton by bindView<Button>(R.id.login_sign_in_button)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signInButton.setOnClickListener { startActivity(Intent(this, SignUpActivity::class.java)) }
    }

}