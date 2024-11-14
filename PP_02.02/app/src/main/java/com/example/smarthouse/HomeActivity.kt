package com.example.smarthouse

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
            /*val viewPager = findViewById<ViewPager>(R.id.viewPager)

            val tabLayout = findViewById<TabLayout>(R.id.tabLayout)

            // Устанавливаем адаптер для ViewPager
            val adapter = MyPagerAdapter(supportFragmentManager)
            viewPager.adapter = adapter

            // Настраиваем TabLayout с ViewPager
            tabLayout.setupWithViewPager(viewPager)
        }

        inner class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            private val tabTitles = arrayOf("Комнаты", "Устройства", "Пользователи")

            override fun getItem(position: Int): Fragment {
                return when (position) {
                    0 -> RoomsFragment()
                    1 -> DevicesFragment()
                    2 -> UsersFragment()
                    else -> RoomsFragment()
                }
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return tabTitles[position]
            }

            override fun getCount(): Int {
                return tabTitles.size
            }
        }*/
        }
    }
}