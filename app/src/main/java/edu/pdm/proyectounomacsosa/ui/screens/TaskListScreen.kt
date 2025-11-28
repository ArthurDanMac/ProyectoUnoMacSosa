package edu.pdm.proyectounomacsosa.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import edu.pdm.proyectounomacsosa.ui.components.TopRightMenu
import edu.pdm.proyectounomacsosa.viewmodel.TaskViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.pdm.proyectounomacsosa.model.Task

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(viewModel: TaskViewModel, onSearch: () -> Unit, navController: NavHostController) {
    val tareas by viewModel.tasks.collectAsState()
    var estadoInt by remember { mutableStateOf(0) }


    LaunchedEffect(Unit) { viewModel.loadTasks() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tasks List") },
                actions = {
                    TopRightMenu(navController, "Tasks List")
                }
            )
        }
    ) { padding ->
        LazyColumn(Modifier.padding(padding)) {
            items(tareas) { tarea ->

                // Parse due date
                val formatter = DateTimeFormatter.ofPattern("yyyy-M-d")
                val dueDate = LocalDate.parse(tarea.plannedD, formatter)
                val today = LocalDate.now()

                val daysLeft = ChronoUnit.DAYS.between(today, dueDate).coerceAtLeast(0)

                ListItem(
                    headlineContent = { Text(tarea.name) },
                    trailingContent = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Column(horizontalAlignment = Alignment.End) {
                                Text(tarea.plannedD, fontSize = 16.sp) // Fecha
                                Text(
                                    "($daysLeft days left)",
                                    fontSize = 14.sp,
                                    color = Color(0xFF808080)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            val estado = remember { mutableStateOf(tarea.status == 1) }
                            Checkbox(
                                checked = estado.value,
                                onCheckedChange = { checked ->
                                    estado.value = checked
                                    viewModel.idUpVM = tarea.id
                                    val taskState = Task(
                                        name = tarea.name,
                                        plannedD = tarea.plannedD,
                                        status = if (checked) 1 else 0,
                                        id = TODO(),
                                        idUser = TODO()
                                    )
                                    viewModel.updateTask(taskState)
                                }
                            )
                            Button(
                                onClick = {
                                    viewModel.idUpVM = tarea.id
                                    navController.navigate("update")
                                }
                            ) { Text("Edit") }
                        }
                    }
                )

            }
        }
    }


}

