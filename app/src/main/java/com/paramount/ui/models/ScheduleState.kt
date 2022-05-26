package com.paramount.ui.models

data class ScheduleState(
    val channels: List<Channel>,
    val selectedChannel: Channel,
    val prevChannel: Channel?,
)
