package com.blincheck.headwayrhythmproject.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.blincheck.headwayrhythmproject.R
import com.blincheck.headwayrhythmproject.presenter.SignUpPresenter
import com.blincheck.headwayrhythmproject.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : BaseActivity<SignUpActivity, SignUpPresenter>() {

    override val presenter = SignUpPresenter()

    override val layoutResId = R.layout.activity_sign_up

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        submitButton.setOnClickListener {
            presenter.onSignUpClicked(
                nameEditText.text.toString(),
                passwordEditText.text.toString()
            )
        }
    }

    fun onSigUpError() {
        Toast.makeText(this, "This user already exists", Toast.LENGTH_LONG).show()
    }

    fun onSignUpSuccess(userId: Int, token: String) {
        val sharedPref = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putInt("userId", userId)
            putString("token", token)
            apply()
        }

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}