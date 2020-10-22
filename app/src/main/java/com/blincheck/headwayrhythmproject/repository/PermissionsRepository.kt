package com.blincheck.headwayrhythmproject.repository

import androidx.fragment.app.FragmentActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Completable

class PermissionsRepository {

    fun requestPermissions(
        fragmentActivity: FragmentActivity,
        vararg permissions: String
    ): Completable =
        RxPermissions(fragmentActivity)
            .request(*permissions)
            .flatMapCompletable {
                if (it) Completable.complete()
                else Completable.error(Error("Permissions not granted"))
            }
}