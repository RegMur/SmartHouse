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
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoomFragment : Fragment() {

   /* private lateinit var roomAdapter: RoomAdapter
    private lateinit var roomRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_room, container, false)
        roomRecyclerView = view.findViewById(R.id.roomRecyclerView)
        roomAdapter = RoomAdapter(emptyList())
        roomRecyclerView.adapter = roomAdapter
        roomRecyclerView.layoutManager = LinearLayoutManager(context)

        loadRooms()
        return view
    }

    private fun loadRooms() {
        lifecycleScope.launch {
            val rooms = supabase.postgrest["room"].select().decodeList<room>()
            roomAdapter.updateData(rooms)
        }
    }*/
}