package com.blincheck.headwayrhythmproject.presenter

import com.blincheck.headwayrhythmproject.enity.LoginResponse
import com.blincheck.headwayrhythmproject.repository.UserRepository
import com.blincheck.headwayrhythmproject.ui.SignUpActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SignUpPresenter : BasePresenter<SignUpActivity>() {

    private val userRepository = UserRepository()

    private var currentUsername: String = ""
    private var currentPassword: String = ""

    fun onSignUpClicked(name: String, password: String) {
        currentUsername = name
        currentPassword = password

        userRepository
            .registerUser(name, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::onSignUpSuccess, ::onError)
    }

    private fun onSignUpSuccess() {
        userRepository
            .loginUser(currentUsername, currentPassword)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::onLoginSuccess, ::onError)
    }

    private fun onLoginSuccess(response: LoginResponse) {
        view?.onSignUpSuccess(response.userId)
    }

    override fun onError(error: Throwable) {
        super.onError(error)
        view?.onSigUpError()
    }
}