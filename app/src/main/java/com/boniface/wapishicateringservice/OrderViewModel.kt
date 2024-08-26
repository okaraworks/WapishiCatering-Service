package com.boniface.wapishicateringservice

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class OrderViewModel(application: Application) : AndroidViewModel(application) {
    private val orderDao: OrderDao
    val allOrders: LiveData<List<Order>>

    init {
        val database = AppDatabase.getDatabase(application)
        orderDao = database.orderDao()
        allOrders = orderDao.getAllOrders()
    }

    fun addOrder(guests: Int, phoneNumber: String, place: String, date: String) {
        val price = when (guests) {
            in 1..100 -> 20000
            in 101..200 -> 35000
            in 201..300 -> 45000
            else -> 60000
        }

        viewModelScope.launch {
            orderDao.insertOrder(Order(guests = guests, price = price, phoneNumber = phoneNumber, place = place, date = date))
        }
    }
}
