package com.blincheck.headwayrhythmproject.ui

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.blincheck.headwayrhythmproject.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class PhotoSelectionFragmentDialog(
    private val onSelectFromGalleryListener: () -> Unit,
    private val onTakePhotoListener: () -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val selectionMethods = resources.getStringArray(R.array.photo_selection_methods)

        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.title_photo_selection_method_dialog))
            .setItems(selectionMethods) { _, index ->
                when (selectionMethods[index]) {
                    getString(R.string.selection_method_from_gallery) -> onSelectFromGalleryListener()
                }
            }
            .create()
    }
}