package com.boniface.wapishicateringservice

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun OrderScreen(orderViewModel: OrderViewModel = viewModel()) {
    var guests by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var place by remember { mutableStateOf("") }
    var selectedDateText by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        FancyText()

        TextField(
            value = guests,
            onValueChange = { guests = it },
            label = { Text("Number of Guests") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Phone Number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = place,
            onValueChange = { place = it },
            label = { Text("Place") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = selectedDateText,
            onValueChange = { selectedDateText = it },
            label = { Text("Date (yyyy-MM-dd)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            // Validate and convert text input to Date
            val selectedDate = try {
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(selectedDateText)
            } catch (e: Exception) {
                null
            }

            // Validate all fields and add order if valid
            val guestsInt = guests.toIntOrNull()
            if (guestsInt != null && phoneNumber.isNotEmpty() && place.isNotEmpty() && selectedDate != null) {
                orderViewModel.addOrder(
                    guestsInt,
                    phoneNumber,
                    place,
                    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDate)
                )
                Toast.makeText(context, "Order Added", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Invalid Input", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Add Order")
        }
    }
}



@Composable
fun OrdersScreen(orderViewModel: OrderViewModel = viewModel()) {
    val orders by orderViewModel.allOrders.observeAsState(initial = emptyList())

    LazyColumn(
        modifier = Modifier.padding(16.dp)
    ) {
        items(orders) { order ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text("Guests: ${order.guests}")
                Text("Price: ${order.price}")
                Text("Phone: ${order.phoneNumber}")
                Text("Place: ${order.place}")
                Text("Date: ${order.date}")
            }
            Divider()
        }
    }
}
@Composable
fun FancyText() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Wapishi",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF6200EE), // Custom color
            style = TextStyle(
                textAlign = TextAlign.Center,
                letterSpacing = 2.sp,
                fontFamily = FontFamily.Serif
            )
        )
        Text(
            text = "Catering Service",
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF3700B3), // Custom color
            style = TextStyle(
                textAlign = TextAlign.Center,
                letterSpacing = 1.sp,
                fontFamily = FontFamily.SansSerif
            )
        )
    }
}

