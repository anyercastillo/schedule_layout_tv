package com.paramount.ui

import android.content.Context
import android.util.AttributeSet
import androidx.leanback.widget.HorizontalGridView
import com.paramount.R

class ListingHorizontalGridView(
    context: Context,
    attrs: AttributeSet
) : HorizontalGridView(context, attrs) {
    private val listingItemSpacing = context
        .resources
        .getDimensionPixelSize(R.dimen.listing_item_spacing)

    init {
        windowAlignment = WINDOW_ALIGN_LOW_EDGE
        windowAlignmentOffset = listingItemSpacing
        windowAlignmentOffsetPercent = 0f
        itemAlignmentOffset = 0
        itemAlignmentOffsetPercent = 0f
        isWindowAlignmentPreferKeyLineOverLowEdge = false

        setItemSpacing(listingItemSpacing)
    }
}