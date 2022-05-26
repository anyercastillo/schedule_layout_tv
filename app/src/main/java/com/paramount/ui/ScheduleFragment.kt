package com.paramount.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.paramount.R
import com.paramount.ui.models.ScheduleEvent
import com.paramount.ui.models.ScheduleState
import com.paramount.ui.views.ChannelVerticalGridView
import com.paramount.ui.views.ChannelsGridAdapter
import com.paramount.ui.views.ListingHorizontalGridView
import com.paramount.ui.views.ListingsGridAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ScheduleFragment : Fragment(R.layout.fragment_main) {
    private val viewModel by viewModels<ScheduleViewModel>()
    private val channelsGridAdapter = ChannelsGridAdapter()
    private val listingsGridAdapter = ListingsGridAdapter()

    private lateinit var boxChannel: TextView
    private lateinit var boxListing: TextView
    private lateinit var channelsGrid: ChannelVerticalGridView
    private lateinit var prevChannelTitle: TextView
    private lateinit var listingsGrid: ListingHorizontalGridView
    private lateinit var prevListingTitle: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findViewsFromInflatedLayout(view)
        setupChannelsGrid()
        setupListingsGrid()
        collectUiState()
    }

    private fun findViewsFromInflatedLayout(view: View) {
        boxChannel = view.findViewById(R.id.box_channel)
        boxListing = view.findViewById(R.id.box_listing)
        channelsGrid = view.findViewById(R.id.channels_grid)
        prevChannelTitle = view.findViewById(R.id.channel_item_title)
        listingsGrid = view.findViewById(R.id.listings_grid)
        prevListingTitle = view.findViewById(R.id.listing_item_title)
    }

    private fun setupChannelsGrid() = channelsGrid.apply {
        setOnChildSelectedListener { _, _, position, _ ->
            val channel =
                channelsGridAdapter.getItemAtPosition(position) ?: return@setOnChildSelectedListener
            viewModel.onEvent(ScheduleEvent.ChannelSelected(channel))
        }
        adapter = channelsGridAdapter
    }

    private fun setupListingsGrid() = listingsGrid.apply {
        setOnChildSelectedListener { _, _, position, _ ->
            val listing =
                listingsGridAdapter.getItemAtPosition(position) ?: return@setOnChildSelectedListener
            viewModel.onEvent(ScheduleEvent.ListingSelected(listing))
        }
        adapter = listingsGridAdapter
    }

    private fun collectUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest { scheduleState ->
                    renderState(scheduleState)
                }
            }
        }
    }

    private fun renderState(scheduleState: ScheduleState) {
        boxChannel.text = scheduleState.selectedChannel.title
        boxListing.text = scheduleState.selectedListing.title

        prevChannelTitle.text = scheduleState.prevChannel?.title
        channelsGridAdapter.submitList(scheduleState.channels)

        prevListingTitle.text = scheduleState.prevListing?.title
        listingsGridAdapter.submitList(scheduleState.listings)
    }
}