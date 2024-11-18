package com.example.smarthouse

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class HomeActivity : AppCompatActivity() {
    private lateinit var homeAddress: TextView
    private lateinit var settingsIcon: ImageButton
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home)) { v, insets ->*/
            // Инициализация UI компонентов
            homeAddress = findViewById(R.id.homeAddress)
            settingsIcon = findViewById(R.id.settingsIcon)
            tabLayout = findViewById(R.id.tabLayout)
            viewPager = findViewById(R.id.viewPager)

            // Установка заголовка и адреса из базы данных Supabase
            loadUserData()

            // Настройка ViewPager и вкладок
            setupViewPager()

            // Обработчик нажатия на кнопку настроек
            settingsIcon.setOnClickListener {
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
            }
        }

        private fun loadUserData() {
            // Пример получения данных пользователя из Supabase
            SupabaseClient.getInstance().fetchUser { user ->
                homeAddress.text = user.homeAddress
            }
        }

        private fun setupViewPager() {
            val adapter = HomeViewPagerAdapter(supportFragmentManager)
            adapter.addFragment(RoomFragment(), "Комнаты")
            adapter.addFragment(DeviceFragment(), "Устройства")
            adapter.addFragment(UserFragment(), "Пользователи")

            viewPager.adapter = adapter
            tabLayout.setupWithViewPager(viewPager)
        }
    }