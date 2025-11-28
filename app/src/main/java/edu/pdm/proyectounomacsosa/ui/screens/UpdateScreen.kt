package edu.pdm.proyectounomacsosa.ui.screens

import android.app.DatePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.AccountCircle
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import edu.pdm.proyectounomacsosa.model.Task
import edu.pdm.proyectounomacsosa.ui.components.TopRightMenu
import edu.pdm.proyectounomacsosa.viewmodel.TaskViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScreen(viewModel: TaskViewModel, onSearch: () -> Unit, navController: NavHostController) {
    val tareas by viewModel.tasks.collectAsState()

    // Form state
    var taskName by remember { mutableStateOf("") }
    var dueDate by remember { mutableStateOf("") }
    var status by remember { mutableStateOf(0) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }

    val idUpd=viewModel.idUpVM

    LaunchedEffect(Unit) { viewModel.loadTasks() }
    LaunchedEffect(idUpd) {
        taskName=tareas[idUpd-1].name
        dueDate=tareas[idUpd-1].plannedD
        status=tareas[idUpd-1].status
        println("Entra a update screen por 1ra vez")
        println("nombre ${taskName}")
        println("fecha ${dueDate}")
        println("estado ${status}")
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Update Task") },
                actions = {
                    TopRightMenu(navController, "Update Task")
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            // --- FORM ---
            OutlinedTextField(
                value = taskName,
                onValueChange = { taskName = it },
                label = { Text("Task Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = dueDate,
                onValueChange = { dueDate = it },
                label = { Text("Due Date") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = false,
                trailingIcon = {
                    IconButton(onClick = { showDatePicker = true }) {
                        Icon(Icons.Default.Add, contentDescription = "Pick date")
                    }
                }
            )

            if (showDatePicker) {
                // Simple DatePickerDialog
                val context = LocalContext.current
                val calendar = Calendar.getInstance()
                DatePickerDialog(
                    context,
                    { _, year, month, dayOfMonth ->
                        dueDate = "$year-${month + 1}-$dayOfMonth" // Store as string
                        showDatePicker = false

                        val formatter = DateTimeFormatter.ofPattern("yyyy-M-d")
                        val selectedDate = LocalDate.parse(dueDate, formatter)
                        val today = LocalDate.now()

                        if (selectedDate.isBefore(today)) {
                            println("Fecha La fecha es anterior a hoy")
                            dueDate = ""
                            showErrorDialog = true
                        }
                        if (selectedDate.isEqual(today) || selectedDate.isAfter(today)) {
                            println("Fecha La fecha es posterior a hoy")
                        }


                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
            if (showErrorDialog) {
                AlertDialog(
                    onDismissRequest = { showErrorDialog = false },
                    title = { Text("Error de Fecha") },
                    text = { Text("No puedes seleccionar una fecha anterior a la de hoy.") },
                    confirmButton = {
                        Button(onClick = { showErrorDialog = false }) {
                            Text("Aceptar")
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.height(50.dp)) // pushes button down
            Button(
                onClick = {
                    if (taskName.isNotBlank() && dueDate.isNotBlank()) {

                        println("Entra a update task antes de llamar el VM")
                        println("nombre ${taskName}")
                        println("fecha ${dueDate}")
                        println("estado ${status}")
                        val TaskUPD = Task(
                            name = taskName,
                            plannedD = dueDate,
                            status = status,
                            id =2,
                            idUser = 2
                        )
                        viewModel.updateTask(TaskUPD)
                        navController.navigate("seeTasks")
                    }
                },
                modifier =
                    Modifier.align(Alignment.CenterHorizontally)
                        .width(200.dp)                        // button width
                        .height(50.dp)
                        .fillMaxWidth(0.5f)  // 50% of the column width
                        .height(50.dp)


            ) {
                Text("Update Task")
            }



        }
    }
}
