/*
package com.example.smarthouse

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView_
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AddDeviceAdapter {
    private val devices: List<device>,
    private val onDeviceSelected: (device) -> Unit
    ) : RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder>() {

        private var selectedPosition = 0

        inner class DeviceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val icon: ImageView = itemView.findViewById(R.id.device_icon)
            val name: TextView = itemView.findViewById(R.id.device_name)

            fun bind(device: device, isSelected: Boolean) {
                icon.setImageResource(device.icon)
                name.text = device.name
                itemView.isSelected = isSelected

                itemView.setOnClickListener {
                    val previousPosition = selectedPosition
                    selectedPosition = adapterPosition
                    notifyItemChanged(previousPosition)
                    notifyItemChanged(selectedPosition)
                    onDeviceSelected(device)
                }
            }
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_device, parent, false)
            return DeviceViewHolder(view)

            override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
                holder.bind(devices[position], position == selectedPosition)
            }

            override fun getItemCount(): Int = devices.size
            private fun saveDevice() {
                val name = deviceName.text.toString().trim().capitalize()
                val id = deviceId.text.toString().trim()
                val selectedRoom = roomSpinner.selectedItem.toString()

                if (name.isEmpty() || id.isEmpty() || selectedRoom.isEmpty() || selectedDevice == null) {
                    Toast.makeText(this, "Заполните все поля!", Toast.LENGTH_SHORT).show()
                    return
                }

                lifecycleScope.launch {
                    val response = supabaseClient.postgrest["device"]
                        .insert(
                            mapOf(
                                "name" to name,
                                "identifier" to id,
                                "room" to selectedRoom,
                                "type" to selectedDevice?.name
                            )
                        )
                        .execute()

                    if (response.error != null) {
                        Toast.makeText(this@AddDeviceActivity, "Ошибка: ${response.error.message}", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@AddDeviceActivity, "Устройство сохранено!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }

        }*/
