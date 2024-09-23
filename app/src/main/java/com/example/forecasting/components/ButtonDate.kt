package com.example.forecasting.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.forecasting.ui.theme.Purple25
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun ButtonDate(selectedDate: MutableState<String>) {
    var expanded by remember { mutableStateOf(false) }

    var dates by remember { mutableStateOf(listOf<String>()) }

    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val calendar = Calendar.getInstance()

    LaunchedEffect(Unit) {
        val newDates = mutableListOf<String>()
        for (i in 0..5) {
            newDates.add(dateFormat.format(calendar.time))
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }
        dates = newDates
    }

    Box(modifier = Modifier.padding(8.dp)) {
        Button(
            modifier = Modifier.size(width = 150.dp, height = 40.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Purple25,
            ),
            onClick = { expanded = true }) {
            Text(text = if (selectedDate.value.isEmpty()) "Select Date" else selectedDate.value)
        }
        DropdownMenu(
            modifier = Modifier
                .background(Purple25)
                .align(Alignment.BottomStart),
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            dates.forEach { date ->
                DropdownMenuItem(
                    text = { Text(text = date, color = Color.White) },
                    onClick = {
                        selectedDate.value = date
                        expanded = false
                    })
            }
        }
    }


}