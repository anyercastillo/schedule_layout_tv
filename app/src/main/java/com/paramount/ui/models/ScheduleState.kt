package com.paramount.ui.models

import com.paramount.domain.models.Channel

data class ScheduleState(
    val channels: List<Channel>,
    val selectedChannel: Channel,
    val prevChannel: Channel?,
)
