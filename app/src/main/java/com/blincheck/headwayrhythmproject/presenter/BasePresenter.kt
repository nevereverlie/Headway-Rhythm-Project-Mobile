package com.blincheck.headwayrhythmproject.presenter

import android.util.Log
import androidx.annotation.VisibleForTesting
import com.blincheck.headwayrhythmproject.ui.base.BaseView
import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<V : BaseView>() {

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    var view: V? = null

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    val disposable: CompositeDisposable = CompositeDisposable()

    fun onAttach(view: V) {
        this.view = view
    }

    fun onDetach() {
        disposable.clear()
        view = null
    }

    fun onError(error: Throwable) {
        Log.d("FNP", "Error: $error")
    }
}