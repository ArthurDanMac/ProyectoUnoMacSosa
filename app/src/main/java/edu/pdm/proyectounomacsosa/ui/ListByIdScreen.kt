package edu.pdm.proyectounomacsosa.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import edu.pdm.proyectounomacsosa.viewmodel.TaskViewModel

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import edu.pdm.proyectounomacsosa.ui.components.TopRightMenu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListByIdScreen(viewModel: TaskViewModel, onSearch: () -> Unit, navController: NavHostController) {
    var taskID by remember { mutableStateOf("") }
    val task = viewModel.selectedTask.collectAsState().value  // observe the current task



    LaunchedEffect(Unit) { viewModel.loadTasks() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Task By ID List") },
                actions = {
                    TopRightMenu(navController, "Task by ID List")
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = taskID,
                onValueChange = { taskID = it },
                label = { Text("Task ID") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = false,
                trailingIcon = {
                    IconButton(onClick = {
                        val id = taskID.toIntOrNull()
                        if (id != null) {
                            viewModel.findTaskById(id)
                        }
                    }
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Pick date")
                    }
                }
            )
            task?.let { t ->

                val checkedState = remember { mutableStateOf(t.status) } // assuming Task has isDone: Boolean

                Row {
                    ListItem(
                        headlineContent = { Text(t.name) },
                        supportingContent = { Text(t.plannedD) } // or format it nicely
                    )
                    var estado: Boolean
                    if(t.status==0){
                        println("Incomplete")
                        estado=false
                    }
                    else{
                        println("Complete")
                        estado=true
                    }

                    Checkbox(
                        checked = estado,
                        onCheckedChange = { checkedState.value = t.status }
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // small space between checkbox and text

                }

                /**/
            }


            }
        }

    }