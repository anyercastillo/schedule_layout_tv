package com.paramount.ui

import androidx.lifecycle.ViewModel
import com.paramount.ui.models.ScheduleEvent
import com.paramount.ui.models.ScheduleState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

val channels = (0..10).map { com.paramount.ui.models.Channel("Channel $it") }
private val initialState = ScheduleState(
    channels = channels,
    selectedChannel = channels.first()
)

class ScheduleViewModel : ViewModel() {
    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    fun onEvent(event: ScheduleEvent) = when (event) {
        is ScheduleEvent.ChannelSelected -> processEventChannelSelected(event)
    }

    private fun processEventChannelSelected(event: ScheduleEvent.ChannelSelected) {
        val newState = state.value.copy(
            selectedChannel = event.channel
        )
        _state.tryEmit(newState)
    }
}
