package com.example.smarthouse

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.PostgresAction
import kotlinx.serialization.Serializable
import org.jetbrains.annotations.Async.Execute

val supabase = createSupabaseClient(
    supabaseUrl = "https://dvqmltvcctihmjunrjlm.supabase.co",
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImR2cW1sdHZjY3RpaG1qdW5yamxtIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzE0ODU1ODEsImV4cCI6MjA0NzA2MTU4MX0.uEgEwhiwHj-VglPp7Emz_dnlsXHaW6E_DcUfJdi9RPI"
) {
    install(Postgrest)
    install(Auth)
}

data class room(
    val room_id: Int,
    val home_id: Int,
    val name: String,
    val type_id: Int
)

data class room_types(
    val room_id : Int,
    val type : String,
    val icon : Int
)

data class user(
    val user_id: Int,
    val username: String,
    val profile_image: Int,
    val address: String
)

data class device(
    val device_id: Int,
    val room_id: Int,
    val name: String,
    val is_on: Boolean,
    val identifier: Int
)



