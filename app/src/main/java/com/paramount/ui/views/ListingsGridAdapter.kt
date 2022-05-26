package com.paramount.ui.views

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import com.paramount.R
import com.paramount.domain.models.Listing

private object ListingDiffCallback : DiffUtil.ItemCallback<Listing>() {
    override fun areItemsTheSame(oldItem: Listing, newItem: Listing): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Listing, newItem: Listing): Boolean {
        return oldItem == newItem
    }
}

class ListingViewHolder(
    view: View,
    viewType: Int
) : ScheduleViewHolder<Listing>(view, viewType) {
    private val title = itemView.findViewById<TextView>(R.id.listing_item_title)

    override fun bindPlaceHolder() {
        title.visibility = View.INVISIBLE
        itemView.setBackgroundColor(Color.TRANSPARENT)
    }

    override fun bindVisibleRow(item: Listing) {
        title.text = item.title
    }
}

class ListingsGridAdapter : ScheduleGridAdapter<Listing, ListingViewHolder>(ListingDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListingViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.listing_item, parent, false)

        return ListingViewHolder(view, viewType)
    }

    override fun fakeItemPlaceHolder(): Listing = Listing(
        title = ""
    )
}