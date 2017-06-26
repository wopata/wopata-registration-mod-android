package com.wopata.register_ui.activities

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import butterknife.bindView
import com.rengwuxian.materialedittext.MaterialEditText
import com.wopata.register_core.managers.RegisterManager
import com.wopata.register_core.models.User
import com.wopata.register_core.models.UserSource
import com.wopata.register_ui.R

/**
 * Created by stephenvinouze on 31/05/2017.
 */
class ResetPasswordActivity : AbstractRegisterActivity() {

    private val resetButton: Button by bindView(R.id.reset_button)
    private val resetEditText: MaterialEditText by bindView(R.id.reset_password_edittext)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toolbar.title = getString(R.string.ResetTitle)

        resetButton.setOnClickListener {
            signUp()
        }
        resetEditText.setOnEditorActionListener { _, _, _ ->
            signUp()
            true
        }
    }

    override fun getContentLayout(): Int {
        return R.layout.activity_reset_password
    }

    private fun signUp() {
        if (checkFields()) {
            val dialog = showWaitingDialog()

            RegisterManager.signUp(
                    user = User(username = usernameEditText.text.toString(), password = passwordEditText.text.toString(), token = null, source = UserSource.NATIVE),
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