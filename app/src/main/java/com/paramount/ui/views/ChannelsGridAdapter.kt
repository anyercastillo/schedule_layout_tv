package com.paramount.ui.views

import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import com.paramount.R
import com.paramount.domain.models.Channel

private object ChannelDiffCallback : DiffUtil.ItemCallback<Channel>() {
    override fun areItemsTheSame(oldItem: Channel, newItem: Channel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Channel, newItem: Channel): Boolean {
        return oldItem == newItem
    }
}

class ChannelViewHolder(
    view: View,
    viewType: Int
) : ScheduleViewHolder<Channel>(view, viewType) {
    private val title = itemView.findViewById<TextView>(R.id.channel_item_title)

    override fun bindPlaceHolder() {
        title.visibility = View.INVISIBLE
    }

    override fun bindVisibleRow(item: Channel) {
        title.text = item.title
    }
}

class ChannelsGridAdapter(
    private val onHorizontalKeyListener: () -> Boolean
) : ScheduleGridAdapter<Channel, ChannelViewHolder>(ChannelDiffCallback) {
    private val horizontalKeyCode = listOf(KeyEvent.KEYCODE_DPAD_RIGHT, KeyEvent.KEYCODE_DPAD_LEFT)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.channel_item, parent, false)

        view.setOnKeyListener { _, keyCode, _ ->
            if (keyCode in horizontalKeyCode) return@setOnKeyListener onHorizontalKeyListener()
            return@setOnKeyListener false
        }

        return ChannelViewHolder(view, viewType)
    }

    override fun fakeItemPlaceHolder(): Channel = Channel(
        title = ""
    )
}