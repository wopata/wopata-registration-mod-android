package com.wopata.register

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import butterknife.bindView
import com.wopata.register_core.managers.RegisterManager
import com.wopata.register_ui.activities.LoginActivity

class MainActivity : AppCompatActivity() {

    val registerButton: Button by bindView(R.id.register_button)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RegisterManager.initialize(
                signInBlock = { user, success, failure ->
                    // DO YOUR SIGN IN LOGIC HERE THEN YIELD SUCCESS AND FAILURE BLOCKS
                    val handler = Handler()
                    handler.postDelayed({
                        Log.d("test", "Sign in")
                        success()
                    }, 2000)

                },
                signUpBlock = { user, success, failure ->
                    // DO YOUR SIGN UP LOGIC HERE THEN YIELD SUCCESS AND FAILURE BLOCKS
                    val handler = Handler()
                    handler.postDelayed({
                        Log.d("test", "Sign up")
                        success()
                    }, 2000)
                }
        )

        registerButton.setOnClickListener { startActivity(Intent(this, LoginActivity::class.java)) }
    }

}
