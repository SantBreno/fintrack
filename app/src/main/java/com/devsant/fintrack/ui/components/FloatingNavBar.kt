package com.devsant.fintrack.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.devsant.fintrack.R
import com.devsant.fintrack.ui.theme.AppColors
import com.devsant.fintrack.ui.theme.FintrackTheme

@Composable
fun FloatingNavBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth(0.9f)
                .padding(8.dp)
                .height(60.dp),
            shape = RoundedCornerShape(30.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = AppColors.Primary
            )
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                NavBarItem(
                    icon = NavBarIcon.VectorIcon(Icons.Default.Home),
                    label = "Home",
                    isSelected = currentRoute == "home",
                    onClick = { navController.navigate("home") }
                )
                NavBarItem(
                    icon = NavBarIcon.DrawableIcon(R.drawable.income_icon),
                    label = "Income",
                    isSelected = currentRoute == "incomeDetailScreen",
                    onClick = { navController.navigate("incomeDetailScreen") }
                )

                Spacer(modifier = Modifier.width(56.dp))

                NavBarItem(
                    icon = NavBarIcon.DrawableIcon(R.drawable.expense_icon),
                    label = "Expense",
                    isSelected = currentRoute == "expenseDetailScreen",
                    onClick = { navController.navigate("expenseDetailScreen") }
                )

                NavBarItem(
                    icon = NavBarIcon.VectorIcon(Icons.Default.Info),
                    label = "Stats",
                    isSelected = currentRoute == "stats",
                    onClick = { navController.navigate("stats") }
                )
            }
        }

        FloatingActionButton(
            onClick = { navController.navigate("addTransactionScreen") },
            modifier = Modifier
                .size(56.dp)
                .offset(y = (-28).dp),
            containerColor = AppColors.Accent,
            elevation = FloatingActionButtonDefaults.elevation(8.dp),
            shape = CircleShape
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Transaction",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }


}

sealed class NavBarIcon{
    data class VectorIcon(val imageVector: ImageVector) :  NavBarIcon()
    data class DrawableIcon(@DrawableRes val resId: Int) : NavBarIcon()
}

@Composable
fun NavBarItem(
    icon: NavBarIcon,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        when (icon) {
            is NavBarIcon.VectorIcon -> {
                Icon(
                    imageVector = icon.imageVector,
                    contentDescription = label,
                    tint = if (isSelected) Color.White else AppColors.Accent,
                    modifier = Modifier.size(24.dp)
                )
            }
            is NavBarIcon.DrawableIcon -> {
                Icon(
                    painter = painterResource(id = icon.resId),
                    contentDescription = label,
                    tint = if (isSelected) Color.White else AppColors.Accent,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = if (isSelected) Color.White else AppColors.TextWhite
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FloatingNavBarPreview() {
    FintrackTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            FloatingNavBar(navController = rememberNavController())
        }
    }
}