package com.devsant.fintrack.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CategorySelector(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    borderColor: Color = Color.White
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
    ) {
        categories.forEach { category ->
            val isSelected = category == selectedCategory
            FilterChip(
                colors = FilterChipDefaults.filterChipColors(MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, borderColor),
                selected = isSelected,
                onClick = { onCategorySelected(category) },
                label = { Text(category, fontWeight = FontWeight.Bold) },
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp),
                elevation = FilterChipDefaults.filterChipElevation(4.dp)
            )
        }
    }
}
