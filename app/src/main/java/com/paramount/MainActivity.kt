package com.paramount

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.paramount.ui.ScheduleFragment

/**
 * Loads [ScheduleFragment].
 */
class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment, ScheduleFragment())
                .commitNow()
        }
    }
}