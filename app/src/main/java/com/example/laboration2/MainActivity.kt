package com.example.laboration2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.laboration2.ui.theme.Laboration2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Laboration2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HabitTrackerApp()
                }
            }
        }
    }
}
@Composable
fun HabitTrackerApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HabitTrackerScreen(navController) }
        composable("create") { HabitCreationScreen() }
    }
}

@Composable
fun HabitTrackerScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Habit Tracker", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("create") }) {
            Text("Create New Habit")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitCreationScreen() {
    var habitName by remember { mutableStateOf("")}
    var frequency by remember { mutableStateOf("")}

    val habitList = remember { mutableStateListOf<Habit>() }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Create a New Habit", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = habitName,
            onValueChange = { newValue -> habitName = newValue },
            label = { Text("Habit Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = frequency,
            onValueChange = { newValue -> frequency = newValue },
            label = { Text("Frequency") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            // This creates the habit and adds it into a list
            if (habitName.isNotEmpty() && frequency.isNotEmpty()) {
                habitList.add(Habit(habitName, frequency))
                // Clearing the input fields
                habitName = ""
                frequency = ""
            }
        }) {
            Text("Save Habit")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column {
            for (habit in habitList) {
                Text(text = "Habit: ${habit.name}, Frequency: ${habit.frequency}")
            }
        }
    }
}
