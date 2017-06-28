package com.wopata.register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import butterknife.bindView
import com.afollestad.materialdialogs.MaterialDialog
import com.wopata.register_core.managers.RegisterManager
import com.wopata.register_ui.activities.LoginActivity
import com.wopata.register_ui.managers.ConfigurationManager

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TIME_OUT = 2000L
    }

    private val registerButton: Button by bindView(R.id.register_button)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val configuration = ConfigurationManager.sharedInstance(this)
        configuration.landingBackground = ContextCompat.getDrawable(this, R.drawable.login_background)
        configuration.landingText = "Configurer ce texte pour attirer vos futurs clients"

        RegisterManager.signInBlock = { activity, user ->
            validateRegistration(activity)
        }
        RegisterManager.signUpBlock = { activity, user ->
            validateRegistration(activity)
        }
        RegisterManager.resetBlock = { activity, user ->
            validateRegistration(activity)
        }

        registerButton.setOnClickListener { startActivity(Intent(this, LoginActivity::class.java)) }
    }

    private fun showWaitingDialog(activity: Activity): MaterialDialog {
        return MaterialDialog.Builder(activity)
                .progress(true, 0)
                .show()
    }

    private fun validateRegistration(activity: Activity) {
        val dialog = showWaitingDialog(activity)
        val handler = Handler()
        handler.postDelayed({
            dialog.dismiss()
            RegisterManager.finish(this)
        }, TIME_OUT)
    }

}
