package edu.pdm.proyectounomacsosa.ui

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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(viewModel: TaskViewModel, onSearch: () -> Unit, navController: NavHostController) {
    val tareas by viewModel.tasks.collectAsState()

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
                ListItem(
                    { Text(tarea.name) }
                )

            }


        }
    }


}


