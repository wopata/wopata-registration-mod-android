package com.wopata.register_ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import butterknife.bindView
import com.wopata.register_core.managers.RegisterManager
import com.wopata.register_ui.R

/**
 * Created by stephenvinouze on 31/05/2017.
 */
class LoginActivity : AppCompatActivity() {

    private val brandbackgroundView: ImageView by bindView(R.id.login_background)
    private val brandImageView: ImageView by bindView(R.id.login_brand)
    private val textView: TextView by bindView(R.id.login_text)
    private val startButton: Button by bindView(R.id.login_start_button)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        startButton.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }

        brandbackgroundView.setImageDrawable(RegisterManager.landingBackground)
        brandImageView.setImageDrawable(RegisterManager.landingBrand)
        textView.text = RegisterManager.landingText
    }

}