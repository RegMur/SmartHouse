package com.example.smarthouse

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RoomAdapter(private var rooms: List<room>) : RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {

    class RoomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val roomName: TextView = itemView.findViewById(R.id.livingRoomText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_room, parent, false)
        return RoomViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val room = rooms[position]
        holder.roomName.text = room.name

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, InDeviceActivity::class.java)
            intent.putExtra("ROOM_ID", room.room_id)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = rooms.size

    fun updateData(newRooms: List<room>) {
        rooms = newRooms
        notifyDataSetChanged()
    }
}