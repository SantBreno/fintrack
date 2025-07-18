package com.devsant.fintrack.ui.components

import android.app.DatePickerDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar
import java.util.Locale

@Composable
fun DatePickerField(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onDateSelected: (String) -> Unit


) {
    val context = LocalContext.current

    if (showDialog) {
        DisposableEffect(Unit) {
            val calendar = Calendar.getInstance()

            val datePickerDialog = DatePickerDialog(
                context,
                { _, year, month, day ->
                    val formattedDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", day, month + 1, year)
                    onDateSelected(formattedDate)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            datePickerDialog.setOnDismissListener { onDismiss()
            }

            datePickerDialog.show()

            onDispose {
                datePickerDialog.setOnDismissListener(null)
            }
        }
    }

}