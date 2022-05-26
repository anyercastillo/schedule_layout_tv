package com.paramount.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.paramount.ChannelsGridAdapter
import com.paramount.R
import com.paramount.ui.models.ScheduleEvent
import com.paramount.ui.models.ScheduleState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ScheduleFragment : Fragment(R.layout.fragment_main) {
    private val viewModel by viewModels<ScheduleViewModel>()
    private val channelsGridAdapter = ChannelsGridAdapter()

    private lateinit var boxChannel: TextView
    private lateinit var channelsGrid: ChannelVerticalGridView
    private lateinit var prevChannelTitle: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findViewsFromInflatedLayout(view)
        setupChannelsGrid()
        collectUiState()
    }

    private fun findViewsFromInflatedLayout(view: View) {
        boxChannel = view.findViewById(R.id.box_channel)
        channelsGrid = view.findViewById(R.id.channels_grid)
        prevChannelTitle = view.findViewById(R.id.channel_item_title)
    }

    private fun setupChannelsGrid() = channelsGrid.apply {
        setOnChildSelectedListener { _, _, position, _ ->
            val channel =
                channelsGridAdapter.getChannelAt(position) ?: return@setOnChildSelectedListener
            viewModel.onEvent(ScheduleEvent.ChannelSelected(channel))
        }
        adapter = channelsGridAdapter
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
        channelsGridAdapter.submitList(scheduleState.channels)
        prevChannelTitle.text = scheduleState.prevChannel?.title
    }
}