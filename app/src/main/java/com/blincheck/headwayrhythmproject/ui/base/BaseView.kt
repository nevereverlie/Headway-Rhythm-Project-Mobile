package com.blincheck.headwayrhythmproject.ui.base

import androidx.annotation.StringRes

interface BaseView {

    fun showError(@StringRes errorResource: Int)

    fun showProgress()

    fun hideProgress()
}