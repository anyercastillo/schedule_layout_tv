package com.paramount.ui.models

import com.paramount.domain.models.Channel
import com.paramount.domain.models.Listing

sealed class ScheduleEvent {
    data class ChannelSelected(val channel: Channel) : ScheduleEvent()
    data class ListingSelected(val listing: Listing) : ScheduleEvent()
}
