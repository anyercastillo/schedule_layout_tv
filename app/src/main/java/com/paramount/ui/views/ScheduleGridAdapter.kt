package com.paramount.ui.views

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

private const val VIEW_TYPE_FIRST_ITEM = 0
private const val VIEW_TYPE_LAST_ITEM = 1
private const val VIEW_TYPE_ITEM = 2

abstract class ScheduleViewHolder<T>(
    view: View,
    private val viewType: Int
) : RecyclerView.ViewHolder(view) {
    fun bind(item: T) = when (viewType) {
        VIEW_TYPE_FIRST_ITEM -> bindInvisibleRow()
        VIEW_TYPE_LAST_ITEM -> bindPlaceHolder()
        else -> bindVisibleRow(item)
    }

    abstract fun bindPlaceHolder()

    abstract fun bindVisibleRow(item: T)

    open fun bindInvisibleRow() {
        itemView.visibility = View.INVISIBLE
    }
}

abstract class ScheduleGridAdapter<T, VH : ScheduleViewHolder<T>>(
    diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, VH>(diffCallback) {
    private var originalList: List<T>? = null

    abstract fun fakeItemPlaceHolder(): T

    /**
     * Submits a list to the adapter.
     *
     * Prepares the given [list] to be submitted to the adapter.
     * This is the list received by the view.
     * [ pos 0 (invisible) ]
     * [ pos 1 ]
     * [ pos 2 ]
     * [-placeholder-]
     */
    override fun submitList(list: List<T>?) {
        originalList = list
        val virtualList = list?.plus(fakeItemPlaceHolder())
        super.submitList(virtualList)
    }

    override fun getItemViewType(position: Int) = when (position) {
        0 -> VIEW_TYPE_FIRST_ITEM
        itemCount - 1 -> VIEW_TYPE_LAST_ITEM
        else -> VIEW_TYPE_ITEM
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    /**
     * Resolves [T] at position [position]
     *
     * Because the first row is not visible, the position received by the view is shifted by one.
     * Example: position 1 for the view is position 0 in the adapter.
     *
     * This is the list received by the view.
     * [ pos 0 (invisible) ]
     * [ pos 1 ]  --> When user selects this row, we want to communicate "pos 0"
     * [ pos 2 ]
     * [-placeholder-]
     * The given [position] is shifted to the right,
     */
    fun getItemAtPosition(position: Int): T? {
        val virtualPosition = (position - 1).coerceAtLeast(0) // The first item is not visible
        return originalList?.getOrNull(virtualPosition)
    }
}