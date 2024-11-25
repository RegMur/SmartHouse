package com.example.smarthouse

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class RoomAdapter(private val onClick: (room) -> Unit) :
    ListAdapter<room, RoomAdapter.RoomViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_room, parent, false)
        return RoomViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class RoomViewHolder(itemView: View, private val onClick: (room) -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        private val roomName = itemView.findViewById<TextView>(R.id.add_room)

        fun bind(room: room) {
            roomName.text = room.name
            itemView.setOnClickListener { onClick(room) }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<room>() {
        override fun areItemsTheSame(oldItem: room, newItem: room) = oldItem.room_id == newItem.room_id
        override fun areContentsTheSame(oldItem: room, newItem: room) = oldItem == newItem
    }
}
