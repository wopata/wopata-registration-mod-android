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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toolbar.title = getString(R.string.ResetTitle)

        resetButton.setOnClickListener {
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
            val dialog = showWaitingDialog()

            RegisterManager.reset(
                    user = User(username = usernameEditText.text.toString(), password = null, token = null, source = UserSource.NATIVE),
                    success = {
                        Toast.makeText(this, "Reset password success", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                        finish()
                    },
                    failure = {
                        Toast.makeText(this, "Reset password failure", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    })
        }
    }

}