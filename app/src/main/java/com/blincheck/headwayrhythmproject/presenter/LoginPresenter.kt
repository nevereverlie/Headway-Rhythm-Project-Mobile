package com.blincheck.headwayrhythmproject.presenter

import com.blincheck.headwayrhythmproject.enity.LoginResponse
import com.blincheck.headwayrhythmproject.repository.UserRepository
import com.blincheck.headwayrhythmproject.ui.LoginActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginPresenter : BasePresenter<LoginActivity>() {

    private val userRepository = UserRepository()

    fun onLoginClicked(name: String, password: String) {
        userRepository
            .loginUser(name, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::onLoginSuccess, ::onError)
    }

    private fun onLoginSuccess(loginResponse: LoginResponse) {
        view?.onLoginSuccess(loginResponse.userId, loginResponse.token)
    }

    override fun onError(error: Throwable) {
        super.onError(error)
        view?.onLoginError()
    }
}