package com.paramount.ui

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.paramount.ChannelsAdapter
import com.paramount.R

private val VERTICAL_KEY_EVENTS = listOf(
    KeyEvent.KEYCODE_DPAD_UP,
    KeyEvent.KEYCODE_DPAD_DOWN
)

class ScheduleView(ctx: Context, attrs: AttributeSet) : FrameLayout(ctx, attrs) {
    private val channels: ChannelListView

    init {
        LayoutInflater.from(ctx).inflate(R.layout.view_schedule, this, true)
        channels = findViewById(R.id.channels)
        isFocusable = true
        isFocusableInTouchMode = true

        setOnKeyListener { _, _, keyEvent ->
            if (keyEvent.keyCode in VERTICAL_KEY_EVENTS) {
                if (!channels.hasFocus()) {
                    channels.requestFocus()
                }
            }

            false
        }
    }

    fun setChannelsAdapter(adapter: ChannelsAdapter) {
        channels.adapter = adapter
    }
}