package com.blincheck.headwayrhythmproject.ui

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.blincheck.headwayrhythmproject.R


class FilterDialog : DialogFragment() {
    lateinit var dialogView : View

    @Override
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = requireActivity().layoutInflater
        dialogView = inflater.inflate(R.layout.filter_dialog, null)

        builder.setView(dialogView).setPositiveButton("Filter"
        ) { dialog, which ->
            mListener!!.onDialogPositiveClick(this)
        }


        return builder.create()
    }

    interface FilterDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment?)
    }

    var mListener: FilterDialogListener? = null

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mListener = try {
            activity as FilterDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                activity.toString()
                        + " must implement NoticeDialogListener"
            )
        }
    }

}
