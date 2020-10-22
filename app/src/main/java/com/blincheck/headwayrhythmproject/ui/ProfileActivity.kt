package com.blincheck.headwayrhythmproject.ui

import android.app.Activity
import android.content.ClipData
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.TextView
import com.blincheck.headwayrhythmproject.presenter.ProfilePresenter
import com.blincheck.headwayrhythmproject.ui.base.BaseActivity
import com.blincheck.headwayrhythmproject.R
import com.blincheck.headwayrhythmproject.enity.User
import com.blincheck.headwayrhythmproject.util.FileManager
import com.blincheck.headwayrhythmproject.util.MimeType
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : BaseActivity<ProfileActivity, ProfilePresenter>() {

    override val layoutResId = R.layout.activity_profile

    override val presenter = ProfilePresenter()

    private lateinit var photoSelectionDialog: PhotoSelectionFragmentDialog

    private var photoUri: Uri? = null

    private val fileManager = FileManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        profileImageView.setOnClickListener { onSetProfilePhotoClicked() }

        photoSelectionDialog =
            PhotoSelectionFragmentDialog(
                ::openGallery,
                presenter::onSetMainPhotoFromCameraSelected
            )

        submitButton.setOnClickListener {
            presenter.onSubmitClicked(editTextName.text.toString())
        }

        homeImage.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        presenter.loadUserData()
    }

    fun showUserInfo(user: User) {
        editTextName.setText(user.userName, TextView.BufferType.EDITABLE)

        Glide.with(this)
            .load(user.photoUrl)
            .into(profileImageView)
    }

    private fun onSetProfilePhotoClicked() {
        photoSelectionDialog.show(supportFragmentManager, null)
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = MimeType.PHOTO
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf(MimeType.PHOTO_JPG, MimeType.PHOTO_PNG))
        }
        startActivityForResult(intent, SELECT_FROM_GALLERY_REQUEST_CODE)
    }

    fun openCamera() {
        photoUri =
            fileManager
                .getTempFile(FileManager.PHOTO_FILE_FORMAT_JPEG)
                ?.let(fileManager::getImageUriForCamera)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
            clipData = ClipData.newRawUri("", photoUri)
            putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
        }
        startActivityForResult(intent, TAKE_PHOTO_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                SELECT_FROM_GALLERY_REQUEST_CODE -> {
                    photoUri = Uri.fromFile(data?.data?.let { fileManager.copyFileFromGallery(it) })
                    photoUri?.let { presenter.onSetMainPhoto(it) }
                }

                TAKE_PHOTO_REQUEST_CODE -> {
                    photoUri?.let { presenter.onSetMainPhoto(it) }
                }
            }
        }
    }

    companion object {

        private const val SELECT_FROM_GALLERY_REQUEST_CODE = 1

        private const val TAKE_PHOTO_REQUEST_CODE = 2
    }

}