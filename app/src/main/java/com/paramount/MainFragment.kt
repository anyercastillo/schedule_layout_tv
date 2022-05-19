package com.paramount

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.paramount.ui.ChannelsAdapter
import com.paramount.ui.ListingsAdapter
import com.paramount.ui.ScheduleView
import com.paramount.ui.models.Channel
import com.paramount.ui.models.Listing

class MainFragment : Fragment(R.layout.fragment_main) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val channelsAdapter = ChannelsAdapter()
        val listingsAdapter = ListingsAdapter()

        val scheduleView = view.findViewById<ScheduleView>(R.id.schedule)
        scheduleView.setChannelsAdapter(channelsAdapter)
        scheduleView.setListingsAdapter(listingsAdapter)

        channelsAdapter.submitList(
            (1..20).map { Channel("Channel: $it") }
        )

        listingsAdapter.submitList(
            (1..20).map { Listing("Listing: $it") }
        )
    }
}