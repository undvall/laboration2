package com.example.laboration2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.laboration2.data.models.Frequency
import com.example.laboration2.data.models.Habit
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
    var frequencyType by remember { mutableStateOf<Frequency?>(null) }

    var timeBasedMinutes by remember { mutableStateOf("") }
    var repetitionBasedTimes by remember { mutableStateOf("") }

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

        // Radio buttons to select the type of frequency
        Text(text = "Frequency Type")
        Row {
            RadioButton(
                selected = frequencyType is Frequency.TimeMinuteBased,
                onClick = { frequencyType = Frequency.TimeMinuteBased(0) }
            )
            Text("Time Based (e.g., 20 minutes)")
            Spacer(modifier = Modifier.width(8.dp))
            RadioButton(
                selected = frequencyType is Frequency.RepetitionBased,
                onClick = { frequencyType = Frequency.RepetitionBased(0) }
            )
            Text("Repetition Based (e.g., 3 times a week)")
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Display the appropriate input field based on the selected frequency type
        when (frequencyType) {
            is Frequency.TimeMinuteBased -> {
                OutlinedTextField(
                    value = timeBasedMinutes,
                    onValueChange = { newValue -> timeBasedMinutes = newValue },
                    label = { Text("Minutes per Session") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
            }
            is Frequency.RepetitionBased -> {
                OutlinedTextField(
                    value = repetitionBasedTimes,
                    onValueChange = { newValue -> repetitionBasedTimes = newValue },
                    label = { Text("Times per Week") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
            }
            else -> {}
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Save Habit Button
        Button(onClick = {
            // Create the frequency based on user input
            val frequency = when (frequencyType) {
                is Frequency.TimeMinuteBased -> Frequency.TimeMinuteBased(timeBasedMinutes.toIntOrNull() ?: 0)
                is Frequency.RepetitionBased -> Frequency.RepetitionBased(repetitionBasedTimes.toIntOrNull() ?: 0)
                else -> null
            }

            if (habitName.isNotEmpty() && frequency != null) {
                habitList.add(Habit(habitName, frequency))
                // Clear the input fields
                habitName = ""
                frequencyType = null
                timeBasedMinutes = ""
                repetitionBasedTimes = ""
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
