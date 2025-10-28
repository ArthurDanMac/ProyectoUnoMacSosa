package edu.pdm.proyectounomacsosa.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import edu.pdm.proyectounomacsosa.model.Task
import edu.pdm.proyectounomacsosa.viewmodel.TaskViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(viewModel: TaskViewModel, onSearch: () -> Unit, navController: NavHostController) {
    val tareas by viewModel.tasks.collectAsState()

    LaunchedEffect(Unit) { viewModel.loadTasks() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Listas de Tarea por Hacer") }
            )
        }
    ) { padding ->
        LazyColumn(Modifier.padding(padding)) {
            items(tareas) { tarea ->
                TaskItem (
                    task = tarea,
                    onCheckedChange = { isChecked ->
                        tareas.map {
                            if (it.id == tarea.id)
                                it.copy(status = isChecked)
                            else
                                it
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun TaskItem(task: Task, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp,4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = task.name,
            fontSize = 18.sp,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        Checkbox(
            checked = task.status,
            onCheckedChange = onCheckedChange
        )
    }
}

fun checar( check: (Boolean) -> Unit,task: Task ): ((Boolean) -> Unit)? {
    if(!task.status)
        return check
    else
        return null
}