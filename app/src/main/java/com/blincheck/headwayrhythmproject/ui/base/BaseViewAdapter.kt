package com.blincheck.headwayrhythmproject.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.blincheck.headwayrhythmproject.util.BaseDiffCallBack

abstract class BaseViewAdapter<T> : RecyclerView.Adapter<BaseViewAdapter.ViewHolder<T>>() {

    protected val list: MutableList<T> = mutableListOf()

    protected abstract fun getViewHolderByViewType(
        inflater: LayoutInflater,
        viewType: Int,
        parent: ViewGroup
    ): ViewHolder<T>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<T> =
        getViewHolderByViewType(LayoutInflater.from(parent.context), viewType, parent)

    override fun onBindViewHolder(holder: ViewHolder<T>, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    fun updateList(newList: List<T>) {
        val diffResult = getDiffResult(newList)
        applyUpdates(newList, diffResult)
    }

    protected open fun getDiffResult(newList: List<T>) =
        DiffUtil.calculateDiff(BaseDiffCallBack(list, newList))

    private fun applyUpdates(newList: List<T>, diffResult: DiffUtil.DiffResult) {
        list.clear()
        list.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    abstract class ViewHolder<R>(view: View) : RecyclerView.ViewHolder(view) {

        protected var savedData: R? = null

        fun bind(data: R) {
            savedData = data
            setContent(data)
        }

        protected abstract fun setContent(data: R)
    }
}