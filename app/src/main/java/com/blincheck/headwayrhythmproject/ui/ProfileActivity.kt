package com.blincheck.headwayrhythmproject.ui

import android.content.Intent
import android.os.Bundle
import com.blincheck.headwayrhythmproject.presenter.ProfilePresenter
import com.blincheck.headwayrhythmproject.ui.base.BaseActivity
import com.blincheck.headwayrhythmproject.R
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : BaseActivity<ProfileActivity, ProfilePresenter>() {

    override val layoutResId = R.layout.activity_profile

    override val presenter = ProfilePresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeImage.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        presenter.loadUserData()
    }

}