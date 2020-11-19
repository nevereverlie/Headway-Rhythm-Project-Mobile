package com.blincheck.headwayrhythmproject.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.blincheck.headwayrhythmproject.R
import com.blincheck.headwayrhythmproject.presenter.LoginPresenter
import com.blincheck.headwayrhythmproject.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_log_in.*

class LoginActivity : BaseActivity<LoginActivity, LoginPresenter>() {

    override val presenter = LoginPresenter()

    override val layoutResId = R.layout.activity_log_in

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        submitButton.setOnClickListener {
            presenter.onLoginClicked(
                nameEditText.text.toString(),
                passwordEditText.text.toString()
            )
        }

        signUpTextView.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        if (getSharedPreferences("myPrefs", Context.MODE_PRIVATE).getInt("userId", -1) != -1) {
            navigateToMainScreen()
        }
    }

    fun onLoginError() {
        Toast.makeText(this, "Invalid name or password, couldn't authorize", Toast.LENGTH_LONG)
            .show()
    }

    fun onLoginSuccess(userId: Int, token: String) {
        val sharedPref = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putInt("userId", userId)
            putString("token", token)
            apply()
        }

        navigateToMainScreen()
    }

    private fun navigateToMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}