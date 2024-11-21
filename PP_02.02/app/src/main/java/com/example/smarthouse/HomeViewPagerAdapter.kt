package com.example.smarthouse

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class HomeViewPagerAdapter (fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        private val fragmentList = listOf(RoomFragment(), DeviceFragment(), UserFragment())
        private val fragmentTitleList = listOf("Комнаты", "Устройства", "Пользователи")

        override fun getCount() = fragmentList.size

        override fun getItem(position: Int) = fragmentList[position]

        override fun getPageTitle(position: Int) = fragmentTitleList[position]
}