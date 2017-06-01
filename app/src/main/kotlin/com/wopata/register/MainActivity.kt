package com.wopata.register

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import butterknife.bindView
import com.wopata.register_ui.activities.LoginActivity

class MainActivity : AppCompatActivity() {

    val registerButton by bindView<Button>(R.id.register_button)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerButton.setOnClickListener { startActivity(Intent(this, LoginActivity::class.java)) }
    }

}
