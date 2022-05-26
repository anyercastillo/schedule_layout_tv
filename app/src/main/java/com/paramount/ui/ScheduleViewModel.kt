package com.paramount.ui

import androidx.lifecycle.ViewModel
import com.paramount.domain.models.Channel
import com.paramount.domain.models.Listing
import com.paramount.ui.models.ScheduleEvent
import com.paramount.ui.models.ScheduleState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

val channels = (0..10).map { Channel("Channel $it") }
val listings = (0..10).map { Listing("Listing $it") }
private val initialState = ScheduleState(
    channels = channels,
    selectedChannel = channels.first(),
    prevChannel = null,
    listings = listings,
    selectedListing = listings.first(),
    prevListing = null
)

class ScheduleViewModel : ViewModel() {
    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    fun onEvent(event: ScheduleEvent) = when (event) {
        is ScheduleEvent.ChannelSelected -> processEventChannelSelected(event)
        is ScheduleEvent.ListingSelected -> processEventListingSelected(event)
    }

    private fun processEventChannelSelected(event: ScheduleEvent.ChannelSelected) {
        val selectedIndex = channels.indexOf(event.channel)
        val prevIndex = selectedIndex - 1
        val prevChannel = if (prevIndex >= 0) channels[prevIndex] else null
        val newState = state.value.copy(
            selectedChannel = event.channel,
            prevChannel = prevChannel
        )
        _state.tryEmit(newState)
    }

    private fun processEventListingSelected(event: ScheduleEvent.ListingSelected) {
        val selectedIndex = listings.indexOf(event.listing)
        val prevIndex = selectedIndex - 1
        val prevListing = if (prevIndex >= 0) listings[prevIndex] else null
        val newState = state.value.copy(
            selectedListing = event.listing,
            prevListing = prevListing
        )
        _state.tryEmit(newState)
    }
}
