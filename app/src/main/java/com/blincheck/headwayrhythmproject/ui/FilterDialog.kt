package com.blincheck.headwayrhythmproject.ui

import com.blincheck.headwayrhythmproject.R
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.filter_dialog.view.*


class FilterDialog : DialogFragment() {
    lateinit var dialogView : View
    private var filterMap : Map<String, String>? = null

    @Override
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = requireActivity().layoutInflater
        dialogView = inflater.inflate(R.layout.filter_dialog, null)

        val btnAdd1: Button = dialogView.findViewById(R.id.clearBtn) as Button
        btnAdd1.setOnClickListener() {
            dialogView.trackName?.setText("")
            dialogView.trackAuthor?.setText("")
            dialogView.startDate?.setText("")
            dialogView.endDate?.setText("")
            dialogView.genres?.setText("")
        }

        builder.setView(dialogView).setPositiveButton("Filter"
        ) { dialog, which ->
            mListener!!.onDialogPositiveClickFilter(this)
        }

        if (filterMap != null)
        {
            dialogView.trackName?.setText(filterMap!!["name"].toString())
            dialogView.trackAuthor?.setText(filterMap!!["author"].toString())
            dialogView.startDate?.setText(filterMap!!["startYear"].toString())
            dialogView.endDate?.setText(filterMap!!["endYear"].toString())
            dialogView.genres?.setText(filterMap!!["genres"].toString())
        }

        return builder.create()
    }

    fun setFilterMap(map: Map<String, String>)
    {
        filterMap = map
    }

    interface FilterDialogListener {
        fun onDialogPositiveClickFilter(dialog: DialogFragment?)
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
