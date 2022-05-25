package com.paramount

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paramount.ui.models.Channel

private const val VIEW_TYPE_FIRST_ITEM = 0
private const val VIEW_TYPE_LAST_ITEM = 1
private const val VIEW_TYPE_ITEM = 2

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
    private val viewType: Int
) : RecyclerView.ViewHolder(view) {
    private val title = itemView.findViewById<TextView>(R.id.channel_item_title)

    fun bind(channel: Channel) = when (viewType) {
        VIEW_TYPE_FIRST_ITEM -> bindInvisibleRow()
        VIEW_TYPE_LAST_ITEM -> bindPlaceHolder()
        else -> bindVisibleRow(channel)
    }

    private fun bindPlaceHolder() {
        title.visibility = View.INVISIBLE
    }

    private fun bindVisibleRow(channel: Channel) {
        title.text = channel.title
    }

    private fun bindInvisibleRow() {
        itemView.visibility = View.INVISIBLE
    }
}

class ChannelsGridAdapter : ListAdapter<Channel, ChannelViewHolder>(ChannelDiffCallback) {
    private var recyclerView: RecyclerView? = null
    private var originalList: List<Channel>? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        this.recyclerView = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.channel_item, parent, false)

        return ChannelViewHolder(view, viewType)
    }

    override fun onBindViewHolder(holder: ChannelViewHolder, position: Int) {
        val channel = getItem(position)
        holder.bind(channel)
    }

    override fun submitList(list: List<Channel>?) {
        originalList = list
        val virtualList = list?.plus(fakeChannelPlaceHolder())
        super.submitList(virtualList)
    }

    override fun getItemViewType(position: Int) = when (position) {
        0 -> VIEW_TYPE_FIRST_ITEM
        itemCount - 1 -> VIEW_TYPE_LAST_ITEM
        else -> VIEW_TYPE_ITEM
    }

    /**
     * Resolves the [Channel] at position [position]
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
    fun getChannelAt(position: Int): Channel? {
        val virtualPosition = (position - 1).coerceAtLeast(0) // The first item is not visible
        return originalList?.getOrNull(virtualPosition)
    }

    private fun fakeChannelPlaceHolder(): Channel = Channel(
        title = ""
    )
}