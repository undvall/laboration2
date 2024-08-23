package com.example.laboration2.ui

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitTrackerScaffold(
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Habit Tracker",
                            style = MaterialTheme.typography.headlineLarge,
                            textAlign = TextAlign.Center
                        )
                    }
                },
                actions = {
                    // Handle interaction state
                    val interactionSource = remember { MutableInteractionSource() }
                    val isPressed by interactionSource.collectIsPressedAsState()

                    // Change color and elevation when pressed
                    val buttonColor = if (isPressed) MaterialTheme.colorScheme.primary else Color.Green
                    val buttonElevation = if (isPressed) 8.dp else 0.dp
                    IconButton(
                        onClick = {
                            navController.navigate("stats")
                        },
                        interactionSource = interactionSource
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Statistics",
                            tint = buttonColor,
                            modifier = Modifier.padding(buttonElevation)
                        )
                    }
                },
                modifier = Modifier.padding(end = 8.dp) // Adds some padding to the right side
            )
        }
    ) { innerPadding ->
        // Content of your screen goes here
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Example content
            Text(
                text = "Your content here",
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}
