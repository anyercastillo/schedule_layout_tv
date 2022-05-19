package com.paramount.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.leanback.widget.HorizontalGridView
import androidx.leanback.widget.OnChildViewHolderSelectedListener
import androidx.recyclerview.widget.RecyclerView
import com.paramount.R

class ListingListView(ctx: Context, attrs: AttributeSet) : HorizontalGridView(ctx, attrs) {
    private val listingItemSpacing = resources.getDimensionPixelSize(R.dimen.listing_item_space)
    private val listingsItemWidth = resources.getDimensionPixelSize(R.dimen.listing_item_width)
    private val boxWidth = resources.getDimensionPixelSize(R.dimen.schedule_selected_item_width)
    private val gap = boxWidth - listingsItemWidth

    init {
        windowAlignment = WINDOW_ALIGN_LOW_EDGE
        windowAlignmentOffset = gap
        windowAlignmentOffsetPercent = 0f
        itemAlignmentOffset = 0
        itemAlignmentOffsetPercent = 0f
        isWindowAlignmentPreferKeyLineOverLowEdge = false
        setItemSpacing(listingItemSpacing)

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

    private fun updateViewTopMargin(view: View, leftMarginValue: Int) {
        val lp = view.layoutParams as MarginLayoutParams

        view.layoutParams = lp.apply {
            leftMargin = leftMarginValue
        }
    }
}