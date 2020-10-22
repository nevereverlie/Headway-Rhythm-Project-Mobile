package com.blincheck.headwayrhythmproject.presenter

import android.net.Uri
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.blincheck.headwayrhythmproject.enity.User
import com.blincheck.headwayrhythmproject.repository.PermissionsRepository
import com.blincheck.headwayrhythmproject.repository.UserRepository
import com.blincheck.headwayrhythmproject.ui.ProfileActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class ProfilePresenter : BasePresenter<ProfileActivity>() {

    private val userRepository = UserRepository()

    private val permissionsRepository = PermissionsRepository()

    private lateinit var currentUser: User

    fun loadUserData() {
        userRepository
            .getUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::onUsersLoaded, ::onError)
    }

    private fun onUsersLoaded(users: List<User>) {
        onUserLoaded(users.first())
    }

    fun onSubmitClicked(newUserName: String) {
        userRepository
            .updateUser(currentUser.copy(userName = newUserName), null)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::onUserLoaded, ::onError)
    }

    private fun onUserLoaded(user: User) {
        currentUser = user.copy(
            userName = user.userName?.replace("\"", ""),
            description = user.description?.replace("\"", "")
        )
        view?.showUserInfo(currentUser)
    }

    fun onSetMainPhotoFromCameraSelected() {
        permissionsRepository.requestPermissions(
            view as FragmentActivity,
            android.Manifest.permission.CAMERA
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::openCamera, ::onError)
    }

    private fun openCamera() {
        view?.openCamera()
    }

    fun onSetMainPhoto(photoUri: Uri) {
        Log.d("FNP", "Photo uri: $photoUri")
        userRepository
            .updateUser(currentUser, photoUri)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::onUserLoaded, ::onError)
    }
}