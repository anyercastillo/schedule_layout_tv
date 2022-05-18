package com.paramount

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.paramount.ui.ChannelListView
import com.paramount.ui.models.Channel

class MainFragment : Fragment(R.layout.fragment_main) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val channelsAdapter = ChannelsAdapter()
        val channels = view.findViewById<ChannelListView>(R.id.channels)
        channels.adapter = channelsAdapter

        channelsAdapter.submitList(
            (1..20).map { Channel("Item: $it") }
        )
    }
}