package com.paramount.ui

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import com.paramount.R
import com.paramount.ui.models.Channel

private val HORIZONTAL_KEY_EVENTS = listOf(
    KeyEvent.KEYCODE_DPAD_LEFT,
    KeyEvent.KEYCODE_DPAD_RIGHT
)

private val VERTICAL_KEY_EVENTS = listOf(
    KeyEvent.KEYCODE_DPAD_UP,
    KeyEvent.KEYCODE_DPAD_DOWN
)

class ScheduleView(ctx: Context, attrs: AttributeSet) : FrameLayout(ctx, attrs) {
    private val channels: ChannelListView
    private val listings: ListingListView
    private val channelText: TextView
    private val listingText: TextView

    private var lastChannelIndexSelected: Int = 0
    private var lastListingIndexSelected: Int = 0

    init {
        LayoutInflater.from(ctx).inflate(R.layout.view_schedule, this, true)
        channels = findViewById(R.id.channels)
        listings = findViewById(R.id.listings)
        channelText = findViewById(R.id.schedule_selected_box_channel)
        listingText = findViewById(R.id.schedule_selected_box_listing)

        isFocusable = true
        isFocusableInTouchMode = true

        channels.setOnChildSelectedListener { _, _, position, _ ->
            lastChannelIndexSelected = position

            updateSelectedView()
        }

        listings.setOnChildSelectedListener { _, _, position, _ ->
            lastListingIndexSelected = position

            updateSelectedView()
        }
    }

    private fun updateSelectedView() {
        val channelsAdapter = channels.adapter as ChannelsAdapter
        val listingsAdapter = listings.adapter as ListingsAdapter
        val channelItem = channelsAdapter.currentList[lastChannelIndexSelected] ?: return
        val listingItem = listingsAdapter.currentList[lastListingIndexSelected] ?: return

        channelText.text = channelItem.title
        listingText.text = listingItem.title
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode in VERTICAL_KEY_EVENTS) {
            return channelsOnKeyDown(keyCode)
        }

        if (keyCode in HORIZONTAL_KEY_EVENTS) {
            return listingsOnKeyDown(keyCode)
        }

        return super.onKeyUp(keyCode, event)
    }

    private fun channelsOnKeyDown(keyCode: Int): Boolean {
        val itemsCount = channels.adapter?.itemCount ?: return false
        if (itemsCount == 0) return false

        val nextIndex = when (keyCode) {
            KeyEvent.KEYCODE_DPAD_UP -> lastChannelIndexSelected - 1
            KeyEvent.KEYCODE_DPAD_DOWN -> lastChannelIndexSelected + 1
            else -> -1
        }

        if (nextIndex !in 0..itemsCount) return false

        channels.setSelectedPositionSmooth(nextIndex)

        return true
    }

    private fun listingsOnKeyDown(keyCode: Int): Boolean {
        val itemsCount = listings.adapter?.itemCount ?: return false
        if (itemsCount == 0) return false

        val nextIndex = when (keyCode) {
            KeyEvent.KEYCODE_DPAD_LEFT -> lastListingIndexSelected - 1
            KeyEvent.KEYCODE_DPAD_RIGHT -> lastListingIndexSelected + 1
            else -> -1
        }

        if (nextIndex !in 0..itemsCount) return false

        listings.setSelectedPositionSmooth(nextIndex)

        return true
    }

    fun setChannelsAdapter(adapter: ChannelsAdapter) {
        channels.adapter = adapter
    }

    fun setListingsAdapter(adapter: ListingsAdapter) {
        listings.adapter = adapter
    }
}