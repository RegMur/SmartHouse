package com.example.smarthouse

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class room(
    val room_id : Int,
    val name : String,
    val type_id : Int
)

class RoomFragment : Fragment() {

    private lateinit var roomRecyclerView: RecyclerView
    private lateinit var addRoomButton: ImageButton
    val supabase = createSupabaseClient(
        supabaseUrl = "https://dvqmltvcctihmjunrjlm.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImR2cW1sdHZjY3RpaG1qdW5yamxtIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzE0ODU1ODEsImV4cCI6MjA0NzA2MTU4MX0.uEgEwhiwHj-VglPp7Emz_dnlsXHaW6E_DcUfJdi9RPI"
    ) {
        install(Postgrest)
        install(Auth)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        lifecycleScope.launch{
            //декодирование дата класса
            val city = supabase.from("user").select().decodeSingle<user>()
            Log.e("!!!1", city.address)
        }

        val view = inflater.inflate(R.layout.fragment_room, container, false)
        roomRecyclerView = view.findViewById(R.id.roomRecyclerView)
        addRoomButton = view.findViewById(R.id.addRoomButton)

        roomRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        roomRecyclerView.adapter = RoomAdapter(fetchRooms())

        addRoomButton.setOnClickListener {
            // Переход на экран добавления комнаты
            val intent = Intent(activity, AddRoomActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    private fun fetchRooms(): List<Room> {
        // Здесь вызывается Supabase API для получения списка комнат
        return SupabaseClient.getInstance().getRooms()
    }
}