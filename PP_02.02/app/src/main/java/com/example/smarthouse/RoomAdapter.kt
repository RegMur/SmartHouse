/*
package com.example.smarthouse

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView

class RoomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    var TitleText: TextView?
    var Image: ImageView?

    init {
        TitleText = itemView?.findViewById<TextView>(R.id.roomText)
        Image = itemView?.findViewById(R.id.roomIcon);
        itemView.setOnClickListener(this)
    }

    var mClickListener: ItemClickListener? = null
    public fun setOnClickListener(clickListener: ItemClickListener) {
        mClickListener = clickListener
    }

    override fun onClick(p0: View?) {
        mClickListener?.onItemClick(p0, adapterPosition)
    }
}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_room, parent, false)
        return RoomViewHolder(view)
    }

    */
/*override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val room = rooms[position]
        holder.roomName.text = room.name*//*

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
    val TitleText: String = data!![position]
    val Type: Int = typeData!![position]

holder.TitleText?.setText(TitleText)

when (Type) {
    1 -> holder.Image?.setImageDrawable(R.drawable.sofa.toDrawable())
    2 -> holder.Image?.setImageDrawable(R.drawable.bathroom.toDrawable())
    3 -> holder.Image?.setImageDrawable(R.drawable.kitchen.toDrawable())
    4 -> holder.Image?.setImageDrawable(R.drawable.bedroom.toDrawable())
    5 -> holder.Image?.setImageDrawable(R.drawable.parlor.toDrawable())
    else -> {
        Log.e("ERROR", "Room image type is missing!")
    }
}

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, RoomFragment::class.java)
            intent.putExtra("ROOM_ID", room.room_id)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = rooms.size

    fun updateData(newRooms: List<room>) {
        rooms = newRooms
        notifyDataSetChanged()
    }
interface ItemClickListener {
    fun onItemClick(view: View?, position: Int)
}
}*/
