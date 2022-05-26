package com.paramount.ui.models

import com.paramount.domain.models.Channel

sealed class ScheduleEvent {
    data class ChannelSelected(val channel: Channel) : ScheduleEvent()
}
