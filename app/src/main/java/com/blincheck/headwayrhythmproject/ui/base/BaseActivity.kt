package com.blincheck.headwayrhythmproject.ui.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.blincheck.headwayrhythmproject.presenter.BasePresenter

abstract class BaseActivity<V : BaseView, P : BasePresenter<V>> :
    AppCompatActivity(), BaseView {

    protected abstract val presenter: P

    @get:LayoutRes
    protected abstract val layoutResId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId)
        presenter.onAttach(this as V)
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }

    override fun showError(errorResource: Int) {
        Toast.makeText(applicationContext, errorResource, Toast.LENGTH_LONG).show()
    }

    override fun showProgress() {
        ProgressFragment().show(supportFragmentManager,
            PROGRESS_FRAGMENT_TAG
        )
    }

    override fun hideProgress() {
        (supportFragmentManager.findFragmentByTag(PROGRESS_FRAGMENT_TAG) as ProgressFragment?)?.dismiss()
    }

    companion object {

        private const val PROGRESS_FRAGMENT_TAG = "progress fragment tag"
    }
}