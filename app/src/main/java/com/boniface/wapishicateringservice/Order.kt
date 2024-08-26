package com.boniface.wapishicateringservice
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val guests: Int,
    val price: Int,
    val phoneNumber: String,
    val place: String,
    val date: String
)
