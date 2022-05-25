package com.paramount.ui

import android.content.Context
import android.util.AttributeSet
import androidx.leanback.widget.VerticalGridView
import com.paramount.R

class ChannelVerticalGridView(
    context: Context,
    attrs: AttributeSet
) : VerticalGridView(context, attrs) {
    private val channelItemSpacing = context
        .resources
        .getDimensionPixelSize(R.dimen.channel_item_spacing)

    init {
        windowAlignment = WINDOW_ALIGN_LOW_EDGE
        windowAlignmentOffset = channelItemSpacing
        windowAlignmentOffsetPercent = 0f
        itemAlignmentOffset = 0
        itemAlignmentOffsetPercent = 0f
        isWindowAlignmentPreferKeyLineOverLowEdge = false

        setItemSpacing(channelItemSpacing)
    }
}