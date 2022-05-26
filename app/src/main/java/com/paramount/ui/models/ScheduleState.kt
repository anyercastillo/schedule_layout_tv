package com.paramount.ui.models

import com.paramount.domain.models.Channel
import com.paramount.domain.models.Listing

data class ScheduleState(
    val channels: List<Channel>,
    val selectedChannel: Channel,
    val prevChannel: Channel?,
    val listings: List<Listing>,
    val selectedListing: Listing,
    val prevListing: Listing?,
)