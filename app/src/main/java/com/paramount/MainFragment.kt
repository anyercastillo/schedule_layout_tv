package com.paramount

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.leanback.widget.VerticalGridView
import com.paramount.models.Channel


class MainFragment : Fragment(R.layout.fragment_main) {
    private val channelsGridAdapter = ChannelsGridAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val channelsGrid = view.findViewById<VerticalGridView>(R.id.channels_grid)
        channelsGrid.adapter = channelsGridAdapter

        loadData()
    }

    private fun loadData() {
        Handler(Looper.getMainLooper()).postDelayed({
            val channels = (0..10).map { Channel("Channel $it") }
            channelsGridAdapter.submitList(channels)
        }, 1000)
    }
}