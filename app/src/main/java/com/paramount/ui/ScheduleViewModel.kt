package com.paramount.ui

import androidx.lifecycle.ViewModel
import com.paramount.ui.models.ScheduleEvent
import com.paramount.ui.models.ScheduleState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

val channels = (0..10).map { com.paramount.ui.models.Channel("Channel $it") }
private val initialState = ScheduleState(
    channels = channels,
    selectedChannel = channels.first(),
    prevChannel = null,
)

class ScheduleViewModel : ViewModel() {
    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    fun onEvent(event: ScheduleEvent) = when (event) {
        is ScheduleEvent.ChannelSelected -> processEventChannelSelected(event)
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
}
