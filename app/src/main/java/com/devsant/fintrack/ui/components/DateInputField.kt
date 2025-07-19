package com.devsant.fintrack.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DateInputField(
    value: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Date"
) {
    val interactionSource = remember { MutableInteractionSource() }
    TextField(
        value = value,
        onValueChange = {},
        readOnly = true,
        label = { Text(label) },
        shape = RoundedCornerShape(12.dp),
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Date Picker",
                tint = Color(0xFF1B213F)
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .focusProperties { canFocus = false }
            .clickable (
                interactionSource = interactionSource,
                indication = null
            ) {
                onClick()
            },
        enabled = false,
        colors = TextFieldDefaults.colors(
            disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}