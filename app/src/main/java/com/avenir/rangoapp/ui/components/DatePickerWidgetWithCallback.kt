package com.avenir.rangoapp.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePickerWidgetWithCallback(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    val formattedDate = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(selectedDate)
    val dateState = rememberMaterialDialogState()

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Date")
            Text(
                text = formattedDate,
                modifier = Modifier.clickable {
                    dateState.show()
                }
            )
        }
        MaterialDialog(
            dialogState = dateState,
            buttons = {
                positiveButton("Ok")
                negativeButton("Cancel")
            }
        ) {
            datepicker(
                initialDate = selectedDate,
                title = "Select date",
                allowedDateValidator = {
                    it.isBefore(LocalDate.now().plusDays(1))
                }
            ) {
                onDateSelected(it)
            }
        }
    }
}



