package com.blincheck.headwayrhythmproject.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.blincheck.headwayrhythmproject.presenter.BasePresenter

abstract class BaseFragment<V : BaseView, P : BasePresenter<V>> :
    Fragment(), BaseView {

    protected lateinit var presenter: P

    @get:LayoutRes
    protected abstract val layoutResId: Int

    @get:StringRes
    protected abstract val titleResId: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(layoutResId, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initPresenter()
        presenter.onAttach(this as V)
    }

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }

    override fun showError(errorResource: Int) {
        Toast.makeText(context, errorResource, Toast.LENGTH_LONG).show()
    }

    override fun showProgress() {
        fragmentManager?.let { ProgressFragment()
            .show(it,
                PROGRESS_FRAGMENT_TAG
            ) }
    }

    override fun hideProgress() {
        (fragmentManager?.findFragmentByTag(PROGRESS_FRAGMENT_TAG) as ProgressFragment?)?.dismiss()
    }

    protected fun setToolbar() {
        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(titleResId)
        setHasOptionsMenu(true)
    }

    protected abstract fun initPresenter()

    companion object {

        private const val PROGRESS_FRAGMENT_TAG = "progress fragment tag"
    }
}