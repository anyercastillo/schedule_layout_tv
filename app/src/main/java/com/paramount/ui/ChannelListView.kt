package com.paramount.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.leanback.widget.OnChildViewHolderSelectedListener
import androidx.leanback.widget.VerticalGridView
import androidx.recyclerview.widget.RecyclerView
import com.paramount.R

class ChannelListView(ctx: Context, attrs: AttributeSet) : VerticalGridView(ctx, attrs) {
    private val channelItemSpacing = resources.getDimensionPixelSize(R.dimen.channel_item_space)
    private val channelItemHeight = resources.getDimensionPixelSize(R.dimen.channel_item_width)

    init {
        windowAlignment = WINDOW_ALIGN_LOW_EDGE
        windowAlignmentOffset = channelItemHeight + channelItemSpacing
        windowAlignmentOffsetPercent = 0f
        itemAlignmentOffset = 0
        itemAlignmentOffsetPercent = 0f
        isWindowAlignmentPreferKeyLineOverLowEdge = false
        setItemSpacing(channelItemSpacing)

        setOnChildViewHolderSelectedListener(object : OnChildViewHolderSelectedListener() {
            override fun onChildViewHolderSelected(
                parent: RecyclerView,
                child: ViewHolder?,
                position: Int,
                subposition: Int
            ) {
                if (position == 0) {
                    child?.let { configureFirstItem(it) }
                }

                super.onChildViewHolderSelected(parent, child, position, subposition)
            }
        })
    }

    private fun configureFirstItem(viewHolder: ViewHolder) {
        if (viewHolder.isRecyclable) {
            viewHolder.setIsRecyclable(false)
        }

        updateViewTopMargin(viewHolder.itemView, windowAlignmentOffset)
    }

    private fun updateViewTopMargin(view: View, topMarginValue: Int) {
        val lp = view.layoutParams as MarginLayoutParams

        view.layoutParams = lp.apply {
            topMargin = topMarginValue
        }
    }
}