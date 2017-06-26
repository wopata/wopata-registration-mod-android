package com.wopata.register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import butterknife.bindView
import com.afollestad.materialdialogs.MaterialDialog
import com.wopata.register_core.managers.RegisterManager
import com.wopata.register_ui.activities.SignInActivity

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TIME_OUT = 2000L
    }

    private val registerButton: Button by bindView(R.id.register_button)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RegisterManager.initialize(
                signInBlock = { activity, user ->
                    val dialog = showWaitingDialog(activity)
                    val handler = Handler()
                    handler.postDelayed({
                        dialog.dismiss()
                        RegisterManager.finish(this, MainActivity::class)
                    }, TIME_OUT)
                },
                signUpBlock = { activity, user ->
                    val dialog = showWaitingDialog(activity)
                    val handler = Handler()
                    handler.postDelayed({
                        dialog.dismiss()
                        RegisterManager.finish(this, MainActivity::class)
                    }, TIME_OUT)
                },
                resetBlock = { activity, user ->
                    val dialog = showWaitingDialog(activity)
                    val handler = Handler()
                    handler.postDelayed({
                        dialog.dismiss()
                        RegisterManager.finish(this, MainActivity::class)
                    }, TIME_OUT)
                }
        )

        registerButton.setOnClickListener { startActivity(Intent(this, SignInActivity::class.java)) }
    }

    private fun showWaitingDialog(activity: Activity): MaterialDialog {
        return MaterialDialog.Builder(activity)
                .progress(true, 0)
                .show()
    }

}
