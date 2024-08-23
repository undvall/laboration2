package com.example.laboration2

import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.TimePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.laboration2.data.models.Frequency
import com.example.laboration2.data.models.Habit
import com.example.laboration2.ui.HabitTrackerScaffold
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
        composable("stats") { StatsScreen()}
    }
}

@Composable
fun HabitTrackerScreen(navController: NavHostController) {
    HabitTrackerScaffold(navController)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("create") }) {
            Text("Create New Habit")
        }
    }
}

@Composable
fun StatsScreen() {
    val habitList = remember { mutableStateListOf<Habit>() }  // Use this or pass the actual list of habits

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Statistics", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))

        if (habitList.isEmpty()) {
            Text("No habits created yet.")
        } else {
            LazyColumn {
                items(habitList) { habit ->
                    Text("Habit: ${habit.name}, Frequency: ${habit.frequency}")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitCreationScreen() {
    var habitName by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf<Calendar?>(null) }
    var repetitionBasedTimes by remember { mutableStateOf("") }

    val habitList = remember { mutableStateListOf<Habit>() }

    val context = LocalContext.current

    // State for showing the TimePickerDialog
    var showTimePicker by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Create a New Habit",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = habitName,
            onValueChange = { newValue -> habitName = newValue },
            label = { Text("Habit Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))


        // Frequency Type Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .padding(8.dp)
                .background(Color(0xFFF5F5F5)) // Light gray background
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = "Frequency Type",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    RadioButton(
                        selected = selectedTime != null,
                        onClick = { showTimePicker = true }
                    )
                    Text("Time Based")
                    Spacer(modifier = Modifier.width(16.dp))
                    RadioButton(
                        selected = repetitionBasedTimes.isNotEmpty(),
                        onClick = { selectedTime = null }
                    )
                    Text("Repetition Based")
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Show selected time or allow user to pick time
        if (selectedTime != null) {
            Text(
                text = "Selected Time: ${
                    String.format(
                        "%02d:%02d",
                        selectedTime!!.get(Calendar.HOUR_OF_DAY),
                        selectedTime!!.get(Calendar.MINUTE)
                    )
                }",
                style = MaterialTheme.typography.bodyMedium
            )
        } else if (showTimePicker) {
            // Launch the TimePickerDialog
            TimePickerDialog(
                context,
                { _: TimePicker, hour: Int, minute: Int ->
                    selectedTime = Calendar.getInstance().apply {
                        set(Calendar.HOUR_OF_DAY, hour)
                        set(Calendar.MINUTE, minute)
                    }
                    showTimePicker = false
                },
                selectedTime?.get(Calendar.HOUR_OF_DAY) ?: 0,
                selectedTime?.get(Calendar.MINUTE) ?: 0,
                true
            ).show()
        }

        if (selectedTime == null) {
            OutlinedTextField(
                value = repetitionBasedTimes,
                onValueChange = { newValue -> repetitionBasedTimes = newValue },
                label = { Text("Times per Week") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Save Habit Button
        Button(onClick = {
            val frequency = if (selectedTime != null) {
                Frequency.TimeBased(
                    hours = selectedTime!!.get(Calendar.HOUR_OF_DAY),
                    minutes = selectedTime!!.get(Calendar.MINUTE)
                )
            } else {
                Frequency.RepetitionBased(repetitionBasedTimes.toIntOrNull() ?: 0)
            }

            if (habitName.isNotEmpty() && frequency != null) {
                habitList.add(Habit(habitName, frequency))
                // Clear the input fields
                habitName = ""
                selectedTime = null
                repetitionBasedTimes = ""
            }
        }) {
            Text("Save Habit")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display the list of saved habits
        Column {
            for (habit in habitList) {
                Text(text = "Habit: ${habit.name}, Frequency: ${habit.frequency}")
            }
        }
    }
}


// Could be good for some cases but not for selection a frequency
//@Composable
//fun ElevatedCardExample(
//    text: String
//) {
//    ElevatedCard(
//        elevation = CardDefaults.cardElevation(
//            defaultElevation = 6.dp
//        ),
//        modifier = Modifier
//            .size(width = 240.dp, height = 100.dp)
//    ) {
//        Text(
//            text = text,
//            modifier = Modifier
//                .padding(16.dp),
//            textAlign = TextAlign.Center,
//        )
//    }
//}
@Preview(showBackground = true)
@Composable
fun PreviewFullApp() {
    Laboration2Theme {
        HabitTrackerApp()
    }
}
