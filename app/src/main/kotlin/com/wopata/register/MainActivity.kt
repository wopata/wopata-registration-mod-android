package com.wopata.register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.wopata.register_core.managers.RegisterManager
import com.wopata.register_core.models.RegisterSource
import com.wopata.register_core.models.User
import com.wopata.register_ui.activities.LoginActivity
import com.wopata.register_ui.managers.ConfigurationManager
import kotterknife.bindView

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TIME_OUT = 2000L
    }

    private val registerButton: Button by bindView(R.id.register_button)
    private val registerUser: TextView by bindView(R.id.register_user)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val configuration = ConfigurationManager.sharedInstance(this)
        configuration.sources = arrayOf(RegisterSource.NATIVE, RegisterSource.FACEBOOK, RegisterSource.GOOGLE)
        configuration.landingBackground = ContextCompat.getDrawable(this, R.drawable.login_background)
        configuration.landingText = getString(R.string.landing_text)

        RegisterManager.googleIdToken = getString(R.string.google_token_id)
        RegisterManager.signIn = { activity, user ->
            registerUser.text = user.toString()
            validateRegistration(activity, user)
        }
        RegisterManager.signUp = { activity, user ->
            registerUser.text = user.toString()
            validateRegistration(activity, user)
        }
        RegisterManager.reset = { activity, user ->
            validateRegistration(activity, user)
        }

        registerButton.setOnClickListener { startActivity(Intent(this, LoginActivity::class.java)) }
    }

    private fun showWaitingDialog(activity: Activity): MaterialDialog {
        return MaterialDialog.Builder(activity)
                .progress(true, 0)
                .show()
    }

    private fun validateRegistration(activity: Activity, user: User) {
        if (user.source == RegisterSource.NATIVE) {
            val dialog = showWaitingDialog(activity)
            val handler = Handler()
            handler.postDelayed({
                dialog.dismiss()
                RegisterManager.finish(this)
            }, TIME_OUT)
        } else {
            RegisterManager.finish(this)
        }
    }

}
