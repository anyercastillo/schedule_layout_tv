package com.paramount.ui.models

sealed class ScheduleEvent {
    data class ChannelSelected(val channel: Channel) : ScheduleEvent()
}
