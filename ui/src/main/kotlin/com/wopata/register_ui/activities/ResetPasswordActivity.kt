package com.wopata.register_ui.activities

import android.os.Bundle
import android.widget.Button
import butterknife.bindView
import com.wopata.register_core.managers.RegisterManager
import com.wopata.register_core.models.RegisterSource
import com.wopata.register_core.models.User
import com.wopata.register_ui.R

/**
 * Created by stephenvinouze on 31/05/2017.
 */
class ResetPasswordActivity : AbstractRegisterActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toolbar.title = getString(R.string.ResetTitle)

        ctaButton.setOnClickListener {
            reset()
        }
        usernameEditText.setOnEditorActionListener { _, _, _ ->
            reset()
            true
        }
    }

    override fun getContentLayout(): Int {
        return R.layout.activity_reset_password
    }

    private fun reset() {
        if (checkFields()) {
            RegisterManager.reset?.invoke(this, User(username = usernameEditText.text.toString(), password = null, token = null, source = RegisterSource.NATIVE))
        }
    }

}